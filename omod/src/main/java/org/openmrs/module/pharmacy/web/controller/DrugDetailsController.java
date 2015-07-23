package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class DrugDetailsController {
    private static final Log log = LogFactory.getLog(DrugDetailsController.class);
    private JSONArray data;
    public PharmacyService service;
    public LocationService serviceLocation;
    private boolean found = false;
    private ConceptService serviceDrugs;
    private JSONArray supplierNames;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private boolean setLocation = false;
    private String bar = null;
    private String drug = null;
    private String id;
    private List<Drug> allDrugs;
    private Drug drugObject;
    private List<PharmacyLocations> pharmacyLocations;
    private List<PharmacyLocationUsers> pharmacyLocationUsers;
    private int size, size1, size2;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Drug> listDrugs;
    private Drug drugByNameOrId;
    private PharmacyStore pharmacyStore;
    private List<PharmacyEncounter> pharmacyFormsSaved;
    private List<PharmacyStore> pharmacyStoreList;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/patientSearch")
    public synchronized void patientSearch(HttpServletRequest request, HttpServletResponse response){
            service = Context.getService(PharmacyService.class);
            String patientToDispenseTo = request.getParameter("patientSearch");
            jsonArray = new JSONArray();
            jsonObject = new JSONObject();
        try {

            String patient = service.getPatientByIdentifier(patientToDispenseTo);
            jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(patient)).getPatientIdentifier());

            response.getWriter().print(jsonArray);
            response.flushBuffer();
        }
        catch (Exception e) {
            // drugs
            log.error("Error generated", e);
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/patientLastName")
    public synchronized void patientLastName(HttpServletRequest request, HttpServletResponse response) {
        String patientToFind=request.getParameter("patientToFind");
        service = Context.getService(PharmacyService.class);
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
        try {
            String  patient= service.getPatientByIdentifier(patientToFind);
            jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(patient)).getNames());
            response.getWriter().print(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drop")
    public synchronized void getDrop(HttpServletRequest request, HttpServletResponse response) {
        String searchDrug=request.getParameter("searchDrug");
        service = Context.getService(PharmacyService.class);
        listDrugs = serviceDrugs.getDrugs(searchDrug);
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();

        try {
            int sizeD = listDrugs.size();
            if (bar != null) {
                for (int i = 0; i < sizeD; i++) {
                    jsonArray.put("" + listDrugs.get(i).getName() +">"+listDrugs.get(i).getConcept().getConceptId()+ "|" + listDrugs.get(i).getId());
                }
            } else {
                for (int i = 0; i < sizeD; i++) {
                    jsonArray.put("" + listDrugs.get(i).getName());
                }
            }

            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drug")
    public synchronized void getDrug(HttpServletRequest request, HttpServletResponse response) {
           String id=request.getParameter("id");
            service = Context.getService(PharmacyService.class);
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
        try {
            drugByNameOrId = Context.getConceptService().getDrugByNameOrId(id);
            jsonArray.put("" + drugByNameOrId.getName());
            jsonArray.put("" + drugByNameOrId.getConcept().getId());
            jsonArray.put("" + drugByNameOrId.getConcept().getDisplayString());

            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/forms")
    public synchronized void getForms(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        pharmacyFormsSaved= service.getPharmacyEncounter();
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
        try {
            for (int ii = 0; ii < pharmacyFormsSaved.size(); ii++) {
                String val = pharmacyFormsSaved.get(ii).getFormName();
                if (!val.contentEquals("null"))
                    jsonArray.put("" + val);
                else {
                    jsonArray.put("");
                }

            }
            jsonObject.accumulate("", jsonArray);
            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/locationSelect")
    public synchronized void getLocations(HttpServletRequest request, HttpServletResponse response) throws JSONException, ParseException {
            service = Context.getService(PharmacyService.class);
            String name = Context.getAuthenticatedUser().getUsername();
            Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
            pharmacyLocationUsers = service.getPharmacyLocationUsers();
            jsonArray = new JSONArray();
            jsonObject = new JSONObject();
              try {
            for (Role rl : xvc) {
                if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))) {
                    setLocation = true;
                }
                if (rl.hasPrivilege("Set Location")) {
                    setLocation = true;
                }
            }
            if (setLocation) {
                for (int ii = 0; ii < size1; ii++) {
                    String val = getDropDownLocation(pharmacyLocationUsers, ii, name);
                    if (!val.contentEquals("null"))
                        jsonArray.put("" + val);
                }
            } else {

                jsonArray.put("No permission");
            }

            jsonObject.accumulate("", jsonArray);

            response.getWriter().print(jsonArray);
            } catch (Exception e) {
            e.printStackTrace();
             }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/locationAll")
    public synchronized void getAllLocation(HttpServletRequest request, HttpServletResponse response) throws JSONException, ParseException {

        service = Context.getService(PharmacyService.class);
        pharmacyLocations = service.getPharmacyLocations();
        try {
            for (int ii = 0; ii < pharmacyLocations.size(); ii++) {
                jsonArray.put("" + pharmacyLocations.get(ii).getName());
            }
            jsonObject.accumulate("", jsonArray);

            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/pharmacyDrugsRequest")
    public synchronized void getDrugsFromPharmacy(HttpServletRequest request, HttpServletResponse response) {
        String searchDrug = request.getParameter("searchDrug");
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        service = Context.getService(PharmacyService.class);

        try {
            listDrugs = Context.getConceptService().getDrugs(searchDrug);
            int sizeD = listDrugs.size();

            for (int i = 0; i < sizeD; i++) {
                jsonArray.put("" + listDrugs.get(i).getName());
            }
            response.getWriter().print(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/pharmacyDrugIDRequest")
    public synchronized void getPharmacyDrugID(HttpServletRequest request, HttpServletResponse response) throws JSONException, ParseException {
        String jsonResult = request.getParameter("drugDetails");
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        service = Context.getService(PharmacyService.class);
        String extractedDrugName="";
        try {

            //drugName= URLDecoder.decode(drugName,"UTF-8");
            //JSONParser parser=new JSONParser();
            //Object object=parser.parse(drugDetails);
            jsonObject=new JSONObject(jsonResult);
            extractedDrugName=jsonObject.getString("drugName");
            drugObject = Context.getConceptService().getDrugByNameOrId(extractedDrugName);
            jsonArray.put("" + drugObject.getDrugId());
            response.getWriter().print(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        id = request.getParameter("id");

        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();
        allDrugs = serviceDrugs.getAllDrugs();
        size = allDrugs.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        try {
                for (int i = 0; i < size; i++)
                {
                    jsonObject.accumulate("aaData", getArray(allDrugs, i));

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            response.flushBuffer();

        } catch (Exception e) {
            // drugs
            log.error("Error generated", e);
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/getAllLocations")
    public void getAllLocations(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        service=Context.getService(PharmacyService.class);
        String locationVal = request.getSession().getAttribute("location").toString();
        jsonArray=new JSONArray();
        pharmacyLocations = service.getPharmacyLocations();
        if(pharmacyLocations.size() > 0){
            for(PharmacyLocations pharmacyLocation: pharmacyLocations)
            {
                System.out.println("locationVal is ++++++++++++++++++++++++++++++++++++++++++"+locationVal+" pharmacyLocation.getName()+++++++++++++++++++++++++++++++++++++++++++++===="+pharmacyLocation.getName());
                if(!pharmacyLocation.getName().equalsIgnoreCase(locationVal))
                {
                    jsonArray.put(""+pharmacyLocation.getName());
                }
            }
        }
        else {
            jsonArray.put("");
        }
        response.getWriter().print(jsonArray);
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/getDrugForAutocomplete")
    public void getDrugForAutocomplete(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        service=Context.getService(PharmacyService.class);
        String searchDrug = request.getParameter("searchDrug");
        jsonArray=new JSONArray();
        listDrugs = Context.getConceptService().getDrugs(searchDrug);
        if(listDrugs.size() > 0){
            for(Drug drug1: listDrugs)
            {
                jsonArray.put(""+drug1.getName());
            }
        }
        else {
            jsonArray.put("");
        }
        response.getWriter().print(jsonArray);
    }
    public synchronized JSONArray getArray(List<Drug> drug, int size) {
        data = new JSONArray();
        Collection<Privilege> xc = userService.getAuthenticatedUser().getPrivileges();

        for (Privilege p : xc) {
            if (p.getPrivilege().equalsIgnoreCase("Edit Pharmacy")) {
                editPharmacy = true;
            }

            if (p.getPrivilege().equalsIgnoreCase("Delete Pharmacy")) {
                deletePharmacy = true;
            }

        }

        if (editPharmacy) {

            data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            data.put("");
        data.put(drug.get(size).getUuid());
        data.put(drug.get(size).getName());
        data.put("");
        data.put("");
        data.put("");
        if (deletePharmacy) {
            data.put("<a href=#?uuid=" + drug.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            data.put("");
        return data;
    }

    public String getDropDownLocation(List<PharmacyLocationUsers> list2, int size, String name) {
        if (list2.get(size).getUserName().equalsIgnoreCase(name)) {
            return list2.get(size).getLocation();
        } else
            return "null";


    }
     public Integer multiply(Integer x,Integer y){
         Integer z=x/y;
         return z;
     }

}
