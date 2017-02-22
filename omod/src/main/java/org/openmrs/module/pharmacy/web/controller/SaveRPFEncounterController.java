package org.openmrs.module.pharmacy.web.controller;

import com.itextpdf.text.DocumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.biometrics.model.PatientFingerPrintModel;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SaveRPFEncounterController {
    private static final Log log = LogFactory.getLog(RfpDispenseController.class);
    public PharmacyService service;
    private boolean found = false;
    private Person person;
    private ArrayList<DrugExtra> dispensedModel;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private String encounterUUID=null;
    private PharmacyDose pharmacyDose;
    private Boolean booleanCheck=false;
    private ContainerFactory containerFactory;
    private JSONArray jsonArray;
    private DrugExtra drugExtra;
    private PharmacyDrugOrder pharmacyDrugOrder;
    private PharmacyOrders pharmacyOrder;
    private Date encDate;
    private List<PharmacyOrders> listPharmacyOrders;
    List<PharmacyDrugOrder> listPharmacyDrugOrders;
    private PharmacyEncounter pharmacyEncounter;
    private String waivingSite="NA";
    private String reason="NA";
    private String socialWorker="NA";
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/saveRFPEncounter")
    public  void saveRFPEncounter(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String encounter_date = request.getParameter("date_prescribed");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();
        service = Context.getService(PharmacyService.class);

        listAnotherPharmacyObs     = new ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        dispensedModel = new ArrayList<DrugExtra>();
        List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
        EncounterProcessor encounterProcessor = new EncounterProcessor();

        Double receiptTotal=0.0;
        Double discount=0.0;
        try {
            Object obj = parser.parse(jsonText);
            JSONArray dispenseInstanceArray = (JSONArray)obj;
            int dispenseInstanceArraySize=dispenseInstanceArray.size();
            for(int i=0; i<dispenseInstanceArraySize; i++){
                drugExtra=new DrugExtra();
                JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("patientId")){
                        String patient=service.getPatientByIdentifier(value) ;
                        person=Context.getPatientService().getPatient(Integer.parseInt(patient));
                        String patientID=person.getPersonId().toString();
                        encounterProcessor.setPatientId(patientID);
                    }
                    if(key.equalsIgnoreCase("totalAmount")){
                        receiptTotal=Double.valueOf(value);
                    }
                    if(key.equalsIgnoreCase("discount")){
                        drugExtra.setDiscount(Double.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("dispenseFormDrug")){
                        Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                        drugExtra.setDrug(drug);
                    }
                    if(key.equalsIgnoreCase("quantity")){
                        drugExtra.setQuantitysold(Integer.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("amount")){
                        drugExtra.setAmount(Double.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("drugExtraUUID")){
                        drugExtra.setUuid(value);
                    }
                    if(key.equalsIgnoreCase("itemAmountWaived")){
                        drugExtra.setAmountw(Double.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("dosage")){
                        pharmacyDose=service.getPharmacyDoseByID(Integer.valueOf(value));
                        drugExtra.setDose(pharmacyDose);
                    }
                    if(key.equalsIgnoreCase("waiver_site")){
                       waivingSite=value;
                    }
                    if(key.equalsIgnoreCase("waiver_reason")){
                        reason=value;
                    }
                    if(key.equalsIgnoreCase("social_worker")){
                        socialWorker=value;
                    }
                }
                dispensedModel.add(drugExtra);
            }
            pharmacyEncounter=new PharmacyEncounter();
            SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
            Date date1 = null;
            try {
                date1 = dt.parse(encounter_date);
                pharmacyEncounter.setDateTime(date1);
                pharmacyEncounter.setNextVisitDate(date1);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            EncounterProcessor encounterProcessor1=new EncounterProcessor();
            encounterProcessor1.setEncounterType("ADULTINITIAL");
            pharmacyEncounter.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor1.getEncounterType()));
            pharmacyEncounter.setFormName("RFP");
            pharmacyEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncounter.setDisplay(1);
            pharmacyEncounter.setTotalAmount(receiptTotal);
            pharmacyEncounter.setDiscount(discount);
            pharmacyEncounter.setPaymentStatus(1);
            pharmacyEncounter.setTotalAmount(receiptTotal);
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
            if(!waivingSite.equalsIgnoreCase("NA")){
                pharmacyEncounter.setSiteWaiving(service.getPharmacyLocationsByUuid(waivingSite));
            }
            else{
                pharmacyEncounter.setSiteWaiving(service.getPharmacyLocationsByUuid(waivingSite));
            }
            if(!socialWorker.equalsIgnoreCase("NA")){
                pharmacyEncounter.setSocialWorker(socialWorker);
            }
            if(!reason.equalsIgnoreCase("NA")){
                pharmacyEncounter.setReason(service.getWaiverReasonByUuId(reason));
            }
            service.savePharmacyEncounter(pharmacyEncounter);
            pharmacyOrder = new PharmacyOrders();
            pharmacyOrder.setAutoEndDate(null);
            pharmacyOrder.setConcept("1800");
            pharmacyOrder.setDiscontinued(false);
            pharmacyOrder.setDiscontinuedDate(null);
            pharmacyOrder.setDispensed(true);
            pharmacyOrder.setInstructions(null);
            try {
                encDate=new SimpleDateFormat("MM/dd/yyyy").parse(encounter_date);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            pharmacyOrder.setStartDate(encDate);
            pharmacyOrder.setPharmacyEncounter(pharmacyEncounter);
            listPharmacyOrders = new ArrayList<PharmacyOrders>();
            listPharmacyOrders.add(pharmacyOrder);
            service.savePharmacyOrders(listPharmacyOrders);

            drugExtra=new DrugExtra();
            for(int i=0; i<dispensedModel.size(); i++){
                if(dispensedModel.get(i).getDrug() !=null)
                {
                    drugExtra = new DrugExtra();
                    drugExtra.setPharmacyEncounter(pharmacyEncounter);
                    drugExtra.setDrug(dispensedModel.get(i).getDrug());
                    drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                    drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                    drugExtra.setAmount(dispensedModel.get(i).getAmount());
                    drugExtra.setAmountw(dispensedModel.get(i).getAmountw());
                    drugExtra.setDiscount(dispensedModel.get(i).getDiscount());
                    drugExtra.setDose(dispensedModel.get(i).getDose());
                    drugExtra.setPreviouslySoldQuantity(0);
                    drugExtra.setEncounterDate(pharmacyEncounter.getDateTime());
                    listDrugExtra.add(drugExtra);

                    PharmacyObs pharmacyObs = new PharmacyObs();
                    pharmacyObs.setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                    pharmacyObs.setDateStarted(null);
                    pharmacyObs.setDateStopped(null);
                    pharmacyObs.setComment(null);
                    pharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                    pharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                    pharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                    pharmacyObs.setPharmacyOrder(pharmacyOrder);
                    pharmacyObs.setValueCoded(0);
                    pharmacyObs.setValue_drug(Context.getConceptService().getDrugByNameOrId(dispensedModel.get(i).getDrug().getDrugId().toString()));
                    pharmacyObs.setValueBoolen(false);
                    pharmacyObs.setValueGroupId(null);
                    pharmacyObs.setValueText(null);
                    pharmacyObs.setValueNumeric(0.0);
                    pharmacyObs.setValueModifier(null);
                    pharmacyObs.setValueCodedName(null);
                    pharmacyObs.setValueDatetime(encDate);
                    listAnotherPharmacyObs.add(pharmacyObs);

                    substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal,dispensedModel.get(i).getPreviouslySoldQuantity());
                    DrugExtra drugExtraToUpdateSoldValue=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                    if(drugExtraToUpdateSoldValue !=null){
                        drugExtraToUpdateSoldValue.setPreviouslySoldQuantity(dispensedModel.get(i).getQuantitysold());
                        service.saveDrugExtraObject(drugExtraToUpdateSoldValue);
                    }
                }
            }
            service.savePharmacyObs(listAnotherPharmacyObs);
            service.saveDrugExtra(listDrugExtra);
            dispensedModel = (ArrayList)service.getDrugExtraByPharmacyEncounter(pharmacyEncounter);
            listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
            for(int i=0; i<dispensedModel.size(); i++) {
                if (dispensedModel.get(i).getDrug() != null) {
                    drugExtra=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                    pharmacyDrugOrder = new PharmacyDrugOrder();
                    pharmacyDrugOrder.setDrugUuid(drugExtra);
                    pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug()).getInventoryId());
                    pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                    pharmacyDrugOrder.setEquivalentDailyDose(0);
                    if(dispensedModel.get(i).getDose() !=null) {
                        pharmacyDrugOrder.setDose(dispensedModel.get(i).getDose().getName());
                    }
                    pharmacyDrugOrder.setFormName("RFP");
                    Integer frequency=1888;
                    String units="1 tablet";
                    try{
                        SimpleDateFormat todayDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date realDate=new Date();
                        String temporaryDateString =todayDate.format(realDate);
                        realDate=todayDate.parse(temporaryDateString);
                        pharmacyDrugOrder.setExpected_next_visit_date(realDate);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    pharmacyDrugOrder.setFrequency(frequency.toString());
                    pharmacyDrugOrder.setOrderUuid(pharmacyOrder);
                    pharmacyDrugOrder.setQuantityPrescribed(dispensedModel.get(i).getQuantitysold());
                    pharmacyDrugOrder.setQuantityGiven(dispensedModel.get(i).getQuantitysold());
                    pharmacyDrugOrder.setUnits(units);
                    listPharmacyDrugOrders.add(pharmacyDrugOrder);
                }
            }
            service.savePharmacyDrugOrders(listPharmacyDrugOrders);
            encounterUUID=pharmacyEncounter.getUuid();
            response.getWriter().print(encounterUUID);
        }
        catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        request.getSession().removeAttribute("pharmacyPatientIdentifier");
        request.getSession().removeAttribute("pharmacyPatientName");
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
    public boolean  substractFromInventory(Integer drugId,int Qnty,String val,Integer previouslySoldQuantity){
        Integer quantityToadjust=0;
        Drug drug = Context.getConceptService().getDrugByNameOrId(drugId.toString());
        String locationUUID=service.getPharmacyLocationsByName(val).getUuid();
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drugId,locationUUID);
        if(drugDispenseSettings !=null){
            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
            if(pharmacyStore!=null ){
                if(previouslySoldQuantity !=null){
                    quantityToadjust=Qnty-previouslySoldQuantity;
                }
                else{
                    quantityToadjust=Qnty;
                }
                if(pharmacyStore.getQuantity() > quantityToadjust ){
                    pharmacyStore.setQuantity( (pharmacyStore.getQuantity()-quantityToadjust));
                    service.savePharmacyInventoryItem(pharmacyStore);
                }
            }
        }
        return true;
    }
    @RequestMapping(value = "module/pharmacy/identifyPatient", method = RequestMethod.POST)
    public List<PatientFingerPrintModel> identifyPatient(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<PatientFingerPrintModel> patients = new ArrayList<PatientFingerPrintModel>();
        String fingerprint = request.getParameter("fingerprint");
        PatientFingerPrintModel patient =service.identifyPatient(fingerprint);
        if(patient != null) {
            patients.add(patient);
        }
        return patients;
    }
}

