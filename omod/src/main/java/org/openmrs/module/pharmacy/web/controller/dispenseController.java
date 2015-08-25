package org.openmrs.module.pharmacy.web.controller;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.*;
import org.openmrs.api.EncounterService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class dispenseController {
    private static final Log log = LogFactory.getLog(dispenseController.class);
    public PharmacyService service;
    public EncounterService patientService;
    private boolean found = false;
    private JSONArray drugNamess;
    private List<PharmacyEncounter> list = null;
    private int size = 0;
    private UserService usersService;
    private List<PharmacyOrders> listOrders;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private int psize, sizeList;
    private List<PharmacyOrders> pharmacyOrders;
    private ContainerFactory containerFactory;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int sizePharmacyLocationUsers;
    private List<PharmacyObs> listObs;
    private List<PharmacyObs> listObsConcepts;
    private  List<PharmacyOrders> listPharmacyOrders   ;
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private JSONObject jsonObject, jsonObject1,jsonObject2;
    private JSONArray jsonArray, jsonArray1, jsonArray2;
    private PharmacyEncounter pharmacyEncounterByUuid;
    private Iterator iterator,iterator2;
    private Person person;
    private List<User> userList;
    private PharmacyTransactionTypes pharmacyTransactionTypes;
    private PharmacyDrugOrder pharmacyDrugOrder;
    private List<PharmacyStore> drugsInInventory;
    @RequestMapping(method=RequestMethod.GET, value="module/pharmacy/checkQuantityDispensedVSQuantityInStock")
    public void compareQuantityInStockVsQUantityDispensed(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/patientEncountersByIdentifier")
    public synchronized void DisplaypatientEncountersByIdentifier(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        userService = Context.getUserContext();


        String patientIdentifier=request.getParameter("identifier");
        String patient=service.getPatientByIdentifier(patientIdentifier) ;
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role role : xvc) {
            if ((role.getRole().equals("Pharmacy Administrator")) ||  (role.getRole().equals("System Developer"))) {
                editPharmacy = true;
            }
            if (role.hasPrivilege("Edit Pharmacy")) {
                editPharmacy = true;
            }

        }

        person=Context.getPatientService().getPatient(Integer.parseInt(patient));
        list = Context.getService(PharmacyService.class).getPharmacyEncounterListByPatientId(person);
        sizeList = list.size();
        jsonObject= new JSONObject();
        if(sizeList >0){
            for (int i = 0; i < sizeList; i++) {
                jsonArray=new JSONArray();
                pharmacyOrders = service.getPharmacyOrdersByEncounterId(list.get(i));
                jsonObject1 = new JSONObject();
                String drugsIssuedToPatient="";
                String locationName=service.getPharmacyLocationsByUuid(list.get(i).getLocation().getUuid()).getName();
                for (int j = 0; j < pharmacyOrders.size(); j++) {//
                    if (pharmacyOrders.get(j).getConcept() != null) {
                        pharmacyDrugOrder=service.getPharmacyDrugOrdersByOrders(pharmacyOrders.get(j));
                        listObsConcepts =service.getPharmacyObsByPharmacyOrder(pharmacyOrders.get(j));
                        for (int y = 0; y < listObsConcepts.size(); y++) {
                            if(listObsConcepts.get(y).getValue_drug() !=null) {
                                drugsIssuedToPatient = drugsIssuedToPatient + listObsConcepts.get(y).getValue_drug().getName().toString() + "-" + pharmacyDrugOrder.getDose()+"- ("+pharmacyDrugOrder.getQuantityGiven()+") " + ",";
                            }
                        }
                    }
                }
                jsonArray.put(list.get(i).getUuid());
                jsonArray.put(list.get(i).getDateTime());
                //jsonArray.put(list.get(i).getFormName());
                jsonArray.put(list.get(i).getComment());
                jsonArray.put(drugsIssuedToPatient);
                jsonArray.put(locationName);
                jsonArray.put(list.get(i).getCreator());
                if(editPharmacy) {
                    if(list.get(i).getFormName().toString()=="PEDIATRICARV" || list.get(i).getFormName().toString() =="ADULTHIV"){
                        jsonArray.put("Delete");
                    }
                    else {
                        jsonArray.put("Edit");
                    }
                }
                else{
                    jsonArray.put("");
                }
                jsonObject.accumulate("aaData",jsonArray);
            }
        }
        else{
            jsonArray=new JSONArray();
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonObject.accumulate("aaData",jsonArray);
        }
        jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iDisplayStart", 0);
        jsonObject.accumulate("iDisplayLength", 10);
        response.getWriter().print(jsonObject);
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/dispense")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        String patientId = request.getParameter("patientID");//encounterDetails
        String form = request.getParameter("form");
        String encounterDetails = request.getParameter("encounterDetails");//
        String fname=request.getParameter("form");
        String users = request.getParameter("users");//
        String pId = request.getParameter("Pid");//
        String age = request.getParameter("age");//
        String drug = request.getParameter("drug");
        String passUserId = request.getParameter("pass");
        String query = request.getParameter("q");
        String drugs = request.getParameter("drugs");
        String values = request.getParameter("values");
        String regimen = request.getParameter("regimen");
        String filter = request.getParameter("filter");
        String encounter = request.getParameter("encounter");
        String patientEncounters= request.getParameter("patientEncounters");
        String pen = request.getParameter("Pen");
        String formtype = request.getParameter("formtype");
        String drugID = request.getParameter("drugCheck");
        String totVal = request.getParameter("total");

        //get openmrs variables
        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        userService = Context.getUserContext();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }
        if (patientId != null && patientEncounters == null) {
            list = service.getPharmacyEncounter();
            size = list.size();
        }
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        listPharmacyDrugOrder=service.getPharmacyDrugOrders();
        try {
            if (patientId != null && patientEncounters == null) {
                ArrayList<Date> dates=new ArrayList<Date>();
                jsonArray1 = new JSONArray();
                String date_s = "2000-01-18 00:00:00.0";
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date1 = dt.parse(date_s);
                String pharmacyDrugOrderUUId=null;
                for(PharmacyDrugOrder pharmacyDrugOrder:listPharmacyDrugOrder ){
                    if(pharmacyDrugOrder.getPerson().getPatientId().toString().equals(patientId) && pharmacyDrugOrder.getFormName().equalsIgnoreCase("ADULTHIV")){
                        dates.add(pharmacyDrugOrder.getDateCreated());
                        Date encDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(pharmacyDrugOrder.getDateCreated().toString());
                        if(date1.before(encDate)){
                            pharmacyDrugOrderUUId=pharmacyDrugOrder.getUuid();
                        }
                    }
                }
                pharmacyDrugOrder= service.getPharmacyDrugOrdersByUuid(pharmacyDrugOrderUUId);
                SimpleDateFormat todaysDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date realDate=new Date();
                String dateFormat=todaysDate.format(realDate.getTime());
                Date actualDate=null;
                try {
                    actualDate=todaysDate.parse(dateFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int DaysInMillSec= 1000 * 60 * 60 * 24;
                if(pharmacyDrugOrder !=null){
                    long largeDate=pharmacyDrugOrder.getExpected_next_visit_date().getTime()/DaysInMillSec;
                    long smallDate=actualDate.getTime()/DaysInMillSec;
                    long DiffInDays=largeDate-smallDate;
                    SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateFormated=fmt.format(pharmacyDrugOrder.getExpected_next_visit_date().getTime());
                    Date expNextDateOfVisit= null;
                    try {
                        expNextDateOfVisit = fmt.parse(dateFormated);
                    } catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    int drugsPerDay=2;
                    SimpleDateFormat fmtDate=new SimpleDateFormat("dd/MM/yyyy");
                    String formatedLastVisitDate=fmtDate.format(pharmacyDrugOrder.getDateCreated().getTime());
                    String formatedNextVisitDate=fmtDate.format(pharmacyDrugOrder.getExpected_next_visit_date().getTime());
                    Date dateC=null;
                    Date dateN=null;
                    dateC=fmtDate.parse(formatedLastVisitDate);
                    dateN=fmtDate.parse(formatedNextVisitDate);
                    jsonArray1 = new JSONArray();
                    jsonArray1.put(formatedLastVisitDate);
                    jsonArray1.put(pharmacyDrugOrder.getFrequency());
                    jsonArray1.put(formatedNextVisitDate);
                    jsonArray1.put(DiffInDays);
                    jsonArray1.put(DiffInDays*drugsPerDay);
                    if (jsonArray1 != null)
                        jsonObject.accumulate("aaData", jsonArray1);

                }
                else{
                    drugNamess = new JSONArray();
                    drugNamess.put("None");
                    drugNamess.put("None");
                    drugNamess.put("None");
                    drugNamess.put("None");
                    drugNamess.put("None");
                    jsonObject.accumulate("aaData", drugNamess);
                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
                //reset
                list = null;
                size = 0;
            } else if (pId != null) {
                jsonArray = new JSONArray();
                jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(pId)).getGivenName());
                response.getWriter().print(jsonArray);
                list = null;
                size = 0;
            } else if (drugID != null) {
                jsonObject1 = new JSONObject(drugID); // this parses the jsonObject
                iterator = jsonObject1.keys(); //gets all the keys
                boolean booleanCheck =false;
                while (iterator.hasNext()) {
                    String key = iterator.next().toString(); // get key
                    Object on = jsonObject1.get(key); // get value
                    PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
                    DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(Context.getConceptService().getDrug(Integer.parseInt(key)).getDrugId(),pharmacyLocations.getUuid());
                    if(drugDispenseSettings==null){
                        booleanCheck = false;
                    }
                    else{
                        booleanCheck = true;
                    }

                }
                response.getWriter().print("" + booleanCheck);
            }
            else if (age != null) {
                jsonArray = new JSONArray();
                jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(age)).getAge());
                response.getWriter().print(jsonArray);
                list = null;
                size = 0;
            }
            else if (patientEncounters != null) {
                person = Context.getPersonService().getPerson(Integer.parseInt(patientId));
                list = Context.getService(PharmacyService.class).getPharmacyEncounterListByPatientId(person);
                sizeList = list.size();
                jsonObject= new JSONObject();
                if(sizeList >0){
                    for (int i = 0; i < sizeList; i++) {
                        jsonArray=new JSONArray();
                        pharmacyOrders= service.getPharmacyOrdersByEncounterId(list.get(i));
                        jsonObject1 = new JSONObject();
                        String drugsIssuedToPatient="";
                        for (int j = 0; j < pharmacyOrders.size(); j++) {//
                            if (pharmacyOrders.get(j).getConcept() != null) {
                                listObsConcepts =service.getPharmacyObsByPharmacyOrder(pharmacyOrders.get(j));
                                for (int y = 0; y < listObsConcepts.size(); y++) {
                                    drugsIssuedToPatient=drugsIssuedToPatient +listObsConcepts.get(y).getValue_drug().getName().toString() ;
                                }
                            }
                        }
                        jsonArray.put("Edit");
                        jsonArray.put(list.get(i).getDateTime());
                        jsonArray.put(list.get(i).getFormName());
                        jsonArray.put(drugsIssuedToPatient);
                        jsonArray.put(list.get(i).getCreator());
                        jsonObject.accumulate("aaData",jsonArray);
                    }
                }
                else{
                    jsonArray=new JSONArray();
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonObject.accumulate("aaData",jsonArray);
                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            }
             else if (passUserId != null) {
                jsonArray = new JSONArray();
                jsonArray.put(Context.getUserContext().getAuthenticatedUser().getSystemId());
                response.getWriter().print(jsonArray);
                list = null;
                size = 0;
            } else if (query != null) {
                userList = Context.getUserService().getUsers(query, Context.getUserService().getRoles(), true);
                psize = userList.size();
                jsonArray2 = new JSONArray();
                for (int i = 0; i < psize; i++) {
                    jsonArray2.put(userList.get(i).getUsername());
                }
                response.getWriter().print(jsonArray2);
            } else if (regimen != null) {
                response.getWriter().print(jsonObject);
            } else if (drugs != null) {
                String[] numbersArray = values.split("/");
                int size = numbersArray.length;
                List<Drug> p = Context.getConceptService().getDrugs(drugs);
                psize = p.size();
                JSONArray temp = new JSONArray();
                for (int i = 0; i < psize; i++) {
                    for (int y = 0; y < size; y++) {
                        if (numbersArray[y].contains("|")) {
                            if (p.get(i).getName().equalsIgnoreCase((numbersArray[y].substring(0, numbersArray[y].indexOf("|")))))
                                temp.put(p.get(i).getName() + "|" + p.get(i).getConcept() + "#" + p.get(i).getId());

                        } else {
                            if (p.get(i).getName().equalsIgnoreCase((numbersArray[y])))
                                temp.put(p.get(i).getName() + "|" + p.get(i).getConcept() + "#" + p.get(i).getId());
                        }
                    }
                }
                response.getWriter().print(temp);
            } else if (users != null) {
                List<User> userlist = Context.getUserService().getAllUsers();
                for (User user : userlist) {
                    jsonArray.put(user);
                }
                response.getWriter().print(jsonArray);
                list = null;
                size = 0;
            }
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error generated"+e.getLocalizedMessage());
        }
    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/getPatientSummaryDetails")
    public void getPatientSummaryDetails(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
        String patientUUID=request.getParameter("patientUUID");
        service = Context.getService(PharmacyService.class);
        String patientID=service.getPatientByIdentifier(patientUUID) ;
        Patient patient=Context.getPatientService().getPatient(Integer.valueOf(patientID));
        PharmacyEncounter patientLastEncounter=service.getLastPharmacyEncounterByPatientUUID(patient);
        jsonObject= new JSONObject();
        jsonArray=new JSONArray();
        if(patientLastEncounter !=null){
            Date realDate=new Date();
            SimpleDateFormat todaysDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateFormat=todaysDate.format(realDate.getTime());
            Date actualDate=null;
            try {
                actualDate=todaysDate.parse(dateFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int DaysInMillSec= 1000 * 60 * 60 * 24;

            long largeDate=patientLastEncounter.getNextVisitDate().getTime()/DaysInMillSec;
            long smallDate=actualDate.getTime()/DaysInMillSec;
            long DiffInDays=largeDate-smallDate;
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateFormated=fmt.format(patientLastEncounter.getNextVisitDate().getTime());
            Date expNextDateOfVisit= null;
            try {
                expNextDateOfVisit = fmt.parse(dateFormated);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            int drugsPerDay=2;
            SimpleDateFormat fmtDate=new SimpleDateFormat("dd/MM/yyyy");
            String formatedLastVisitDate=fmtDate.format(patientLastEncounter.getDateCreated().getTime());
            String formatedNextVisitDate=fmtDate.format(patientLastEncounter.getNextVisitDate().getTime());
            //jsonArray.put(list.get(0).getNextVisitDate());
            jsonArray.put(patientLastEncounter.getRegimenName());
            jsonArray.put(formatedLastVisitDate);
            jsonArray.put(formatedNextVisitDate);
            jsonArray.put(DiffInDays);
            jsonArray.put(patientLastEncounter.getRegimenCode());
        }
        response.getWriter().print(jsonArray);

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/dispense")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String transactionsName = request.getParameter("transactionsname");
        String description = request.getParameter("description");
        String transactionsEdit = request.getParameter("transactionsEdit");
        String transactionsUuid = request.getParameter("transactionsUuid");
        String transactionsUuidVoid = request.getParameter("transactionsUuidVoid");
        String transactionsReason = request.getParameter("transactionsReason");
        if (transactionsEdit != null) {
            if (transactionsEdit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                List<PharmacyTransactionTypes> list = service.getPharmacyTransactionTypes();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    found = getCheck(list, i, transactionsName);
                    if (found)
                        break;
                }
                if (!found) {
                    PharmacyTransactionTypes transactionNamee = new PharmacyTransactionTypes();
                    transactionNamee.setName(transactionsName);
                    transactionNamee.setDescription(description);
                    service.savePharmacyTransactionTypes(transactionNamee);
                } else //do code to display to the user  for entry already entered entry
                {

                }

            } else if (transactionsEdit.equalsIgnoreCase("true")) {
                pharmacyTransactionTypes = service.getPharmacyTransactionTypesByUuid(transactionsUuid);
                pharmacyTransactionTypes.setName(transactionsName);
                pharmacyTransactionTypes.setDescription(description);
                service.savePharmacyTransactionTypes(pharmacyTransactionTypes);
            }

        } else if (transactionsUuidVoid != null) {
            pharmacyTransactionTypes = service.getPharmacyTransactionTypesByUuid(transactionsUuidVoid);
            pharmacyTransactionTypes.setVoided(true);
            pharmacyTransactionTypes.setVoidReason(transactionsReason);
            service.savePharmacyTransactionTypes(pharmacyTransactionTypes);

        }

    }

    public synchronized JSONArray getEncounterSummary(List<PharmacyEncounter> encounter, int size, String id,String form) {


        service = Context.getService(PharmacyService.class);

        //	get summarized encounter details
        if (encounter.get(size).getPerson().getId().equals(Integer.parseInt(id)) && encounter.get(size).getFormName().equalsIgnoreCase(form)) {



            String date = encounter.get(size).getDateTime().toString().substring(0, 10);
//            String string = encounter.get(size).getEncounter().getName();

            Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
            for (Role rl : xvc) {

                if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {

                    editPharmacy = true;
                    deletePharmacy = true;
                }


                if (rl.hasPrivilege("Edit Pharmacy")) {
                    editPharmacy = true;
                }

                if (rl.hasPrivilege("Delete Pharmacy")) {
                    deletePharmacy = true;
                }

            }
            drugNamess = new JSONArray();
            if (editPharmacy) {

                drugNamess.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                editPharmacy = false;
            } else
                drugNamess.put("");

            drugNamess.put(encounter.get(size).getUuid());
            drugNamess.put(date);
            drugNamess.put("");
            drugNamess.put("");
            return drugNamess;
        } else
            return null;

    }

    public synchronized String getDropDown(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size) {

        return pharmacyTransactionTypes.get(size).getName();
    }

    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;

    }

    public synchronized boolean getCheck(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size, String drugNamess) {
        if (pharmacyTransactionTypes.get(size).getName().equalsIgnoreCase(drugNamess)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDropDownUsers(List<User> list2, int size) {
        return list2.get(size).getUsername();

    }
    public synchronized String ArrayDataOne(String jsonText) {

        String value = "";
        JSONParser parser = new JSONParser();


        containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iterator1 = json.entrySet().iterator();

            while (iterator1.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator1.next();

                if (entry.getValue().toString().contains("|"))
                    value += "#" + entry.getValue().toString().substring(entry.getValue().toString().indexOf("|"));
                else
                    value += "#|" + entry.getValue().toString();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value.substring(2);

    }

    // get days diffrence
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }


}
