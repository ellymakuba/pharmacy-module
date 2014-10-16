package org.openmrs.module.pharmacy.web.controller;

import java.io.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.Privilege;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Collection;
import org.openmrs.Role;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


import javax.servlet.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.json.simple.JSONArray;
@Controller
public class TBFormController {
    private static final Log log = LogFactory.getLog(TBFormController.class);
    private ContainerFactory containerFactory;
    public PharmacyService service;
    private boolean found = false;
    private JSONArray supplierNames;
    private JSONArray datad2;
    private List<PharmacySupplier> pharmacySupplier;
    private int size;
    private JSONArray jsonArray;
    private Person person;
    private ArrayList<DrugExtra> dispensedModel;
    private PharmacyEncounter pharmacyEncounter;
    private boolean allowDispense=false;
    private DrugExtra drugExtraToUpdate;
    private UserContext userService;
    private  Set<Privilege> userPrivileges;
    private PharmacyObs observations;
    private LinkedList concepts;
    private int dose;
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/tbFormProcessor")
    public void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String quantity = request.getParameter("quantity");
        String checkBooleanToProceedWithDispense=request.getParameter("checkBoolean");
        String print="1";
        String receipt=request.getParameter("receipt");
        String displayUnclearedReceipts=request.getParameter("unclearedReceipts");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        List<PharmacyObs> listPharmacyObs= new ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        Collection<Role> userContextCollection = userService.getAuthenticatedUser().getAllRoles();
        for(Role roleWithPrivileges: userContextCollection ){
            userPrivileges=roleWithPrivileges.getPrivileges();
            //log.info("log all the role privileges+++++++++++++++++++++++++++"+roleWithPrivileges.getRole());
        }
        for(Privilege p: userPrivileges){
            //log.info("session user privileges +++++++++++++++++++++++++++++++++++++"+p.getName());
        }

        dispensedModel = new ArrayList<DrugExtra>();
        List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        concepts=new LinkedList();
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        Double receiptTotal=0.0;
        try {
            Object obj = parser.parse(jsonText);
            JSONArray dispenseInstanceArray = (JSONArray)obj;
            int dispenseInstanceArraySize=dispenseInstanceArray.size();
            for(int i=0; i<dispenseInstanceArraySize; i++){
                DrugExtra drugExtra=new DrugExtra();
                JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                encounterProcessor.setForm("TBForm");
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("tbPatientID")){
                        String patient=service.getPatientByIdentifier(value) ;
                        person=Context.getPatientService().getPatient(Integer.parseInt(patient));
                        String patientID=person.getPersonId().toString();
                        encounterProcessor.setPatientId(patientID);
                    }
                    if(key.equalsIgnoreCase("tbtotalAmount")){
                        receiptTotal=Double.valueOf(value);
                    }
                    if(key.equalsIgnoreCase("tbdrugdispense")){
                        Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                        drugExtra.setDrug(drug);
                        concepts.add(drug.getConcept().getConceptId());
                    }
                    if(key.equalsIgnoreCase("tbquantity")){
                        drugExtra.setQuantitysold(Integer.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("tbdosage")){
                       //dose=Integer.valueOf(value);
                    }
                    if(key.equalsIgnoreCase("tbamount")){
                        drugExtra.setAmount(Double.valueOf(value));
                    }
                }
                dispensedModel.add(drugExtra);
            }
            PharmacyEncounter pharmacyEncounter = new PharmacyEncounter();
            String date_s = "2000-01-18 00:00:00.0";
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = null;
            try {
                date1 = dt.parse(date_s);
                pharmacyEncounter.setDateTime(date1);
                pharmacyEncounter.setNextVisitDate(date1);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            EncounterProcessor encounterProcessor1=new EncounterProcessor();
            encounterProcessor1.setEncounterType("ADULTINITIAL");
            pharmacyEncounter.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor1.getEncounterType()));
            pharmacyEncounter.setFormName("TB");
            pharmacyEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncounter.setDuration(10);
            pharmacyEncounter.setDisplay(0);
            pharmacyEncounter.setTotalAmount(receiptTotal);
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
            pharmacyEncounter.setDisplay(1);
            service.savePharmacyEncounter(pharmacyEncounter);


            for(int i=0; i<dispensedModel.size(); i++){
                DrugExtra drugExtra = new DrugExtra();
                PharmacyOrders pharmacyOrders = new PharmacyOrders();
                observations=new PharmacyObs();
                if(dispensedModel.get(i).getDrug() !=null) {
                    //drugExtra.setPharmacyEncounter(pharmacyEncounter);
                    //drugExtra.setDrug(dispensedModel.get(i).getDrug());
                    //drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                    //drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                    //drugExtra.setAmount(dispensedModel.get(i).getAmount());
                   // listDrugExtra.add(drugExtra);

                    pharmacyOrders.setAutoEndDate(null);
                    pharmacyOrders.setConcept(dispensedModel.get(i).getDrug().getConcept().getConceptId().toString());
                    pharmacyOrders.setDiscontinued(false);
                    pharmacyOrders.setDiscontinuedDate(null);
                    pharmacyOrders.setDispensed(true);
                    pharmacyOrders.setInstructions(null);
                    pharmacyOrders.setStartDate(null);
                    pharmacyOrders.setPharmacyEncounter(pharmacyEncounter);
                    listPharmacyOrders.add(pharmacyOrders);
                    PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                    //pharmacyDrugOrder.setDose(CheckIfDoubleNull(c.get(2).getDose()));
                    //pharmacyDrugOrder.setDrugUuid(drugExtra);
                    pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug()).getInventoryId());
                    pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                    pharmacyDrugOrder.setEquivalentDailyDose(0);
                    pharmacyDrugOrder.setFormName(encounterProcessor.getForm());
                    //pharmacyDrugOrder.setFrequency(CheckIfStringNull(c.get(2).getFrequency()));
                    Calendar cal=new GregorianCalendar();
                    SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateFormated=fmt.format(cal.getTime());
                    Date expNextDateOfVisit= null;
                    try {
                        expNextDateOfVisit = fmt.parse(dateFormated);

                    } catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    pharmacyDrugOrder.setExpected_next_visit_date(expNextDateOfVisit);
                    pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                    pharmacyDrugOrder.setQuantityPrescribed(dispensedModel.get(i).getQuantitysold());
                    pharmacyDrugOrder.setQuantityGiven(dispensedModel.get(i).getQuantitysold());
                    //pharmacyDrugOrder.setUnits(CheckIfStringNull(c.get(2).getUnits()));
                    listPharmacyDrugOrders.add(pharmacyDrugOrder);

                    observations.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                    observations.setLocation(service.getPharmacyLocationsByName(locationVal));
                    observations.setConcept(dispensedModel.get(i).getDrug().getConcept().getConceptId().toString());
                    observations.setValue_drug(dispensedModel.get(i).getDrug());
                    observations.setPharmacyEncounter(pharmacyEncounter);
                    observations.setPharmacyOrder(pharmacyOrders);
                    listPharmacyObs.add(observations) ;
                    substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal);
                }

            }
            //service.saveDrugExtra(listDrugExtra);
            service.savePharmacyObs(listPharmacyObs);
            service.savePharmacyOrders(listPharmacyOrders);
            service.savePharmacyDrugOrders(listPharmacyDrugOrders);
        }
        catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

 @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/tbFormDrugsAvailability")
  public void tbCheckAvailabilityOfDrugs(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
     String jsonText = request.getParameter("values");
     JSONParser parser=new JSONParser();
     String locationVal = null;
     jsonArray=new JSONArray();

     userService = Context.getUserContext();
     service = Context.getService(PharmacyService.class);
     List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
     int sizeUsers = listUsers.size();
     if (sizeUsers > 1) {
         locationVal = request.getSession().getAttribute("location").toString();

     } else if (sizeUsers == 1) {
         locationVal = listUsers.get(0).getLocation();
     }

     allowDispense=false;
     dispensedModel = new ArrayList<DrugExtra>();
     EncounterProcessor encounterProcessor = new EncounterProcessor();
     try {
         Object obj = parser.parse(jsonText);
         JSONArray dispenseInstanceArray = (JSONArray)obj;
         int dispenseInstanceArraySize=dispenseInstanceArray.size();
         for(int i=0; i<dispenseInstanceArraySize; i++){
             DrugExtra drugExtra=new DrugExtra();
             JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
             for(int j=0; j<rowInstance.size(); j++){
                 String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                 String key = myValues[0];
                 String value=myValues[1];
                 if(key.equalsIgnoreCase("tbPatientId")){
                     String patient=service.getPatientByIdentifier(value) ;
                     person=Context.getPatientService().getPatient(Integer.parseInt(patient));
                     String patientID=person.getPersonId().toString();
                     encounterProcessor.setPatientId(patientID);
                 }
                 if(key.equalsIgnoreCase("tbdrugdispense")){
                     Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                     drugExtra.setDrug(drug);
                 }
                 if(key.equalsIgnoreCase("tbquantity")){
                     drugExtra.setQuantitysold(Integer.valueOf(value));
                 }
                 if(key.equalsIgnoreCase("tbamount")){
                     drugExtra.setAmount(Double.valueOf(value));
                 }

             }
             dispensedModel.add(drugExtra);
         }
         for(int i=0; i<dispensedModel.size(); i++){
             if(dispensedModel.get(i).getDrug() !=null) {
                 DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug());
                 if(drugDispenseSettings !=null){
                     PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                     if(pharmacyStore.getQuantity() > dispensedModel.get(i).getQuantitysold()) {
                         allowDispense=true;
                     }
                 }
             }
         }
         response.getWriter().print("" + allowDispense);
     }
     catch (org.json.simple.parser.ParseException e) {
         e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
     }

   }
    public synchronized String[] exractKeyAndValue(String jsonText) {
        String value = "";
        String key="";
        JSONParser parser = new JSONParser();
        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                key+=entry.getKey();
                value+=entry.getValue();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        String myvals[]={key,value};
        return myvals;
    }
    public boolean  substractFromInventory(Integer drugId,int Qnty,String val){
        Drug drug = Context.getConceptService().getDrugByNameOrId(drugId.toString());
        String locationUUID=service.getPharmacyLocationsByName(val).getUuid();
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drugId,locationUUID);
        if(drugDispenseSettings !=null){
            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
            if(pharmacyStore!=null ){
                if(pharmacyStore.getDrugs().getDrugId()==drugId && pharmacyStore.getQuantity() > Qnty ){
                    pharmacyStore.setQuantity( (pharmacyStore.getQuantity()-Qnty));
                    service.savePharmacyInventory(pharmacyStore);
                }
            }
        }
        return true;
    }
}
