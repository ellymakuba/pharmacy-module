package org.openmrs.module.pharmacy.web.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Controller
public class RfpDispenseController {
    private static final Log log = LogFactory.getLog(RfpDispenseController.class);
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
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private PharmacyDrugOrder drugOrder;
    private boolean allowDispense=false;
    private DrugExtra drugExtraToUpdate;
    private UserContext userService;
    private  Set<Privilege> userPrivileges;
    private Date encDate;
    private String prescriber = null;
    private String encounterUUID=null;
    private boolean isSaveObsSuccess=false,isSavePharmacyDrugOrderSucccess=false,isSavePharmacyOrdersSucces=false;
    private String UUIDForExistingDrugExtra="";
    private PharmacyDose pharmacyDose;
    private Boolean booleanCheck=false;
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/rfpDispenseForm")
    public void rfpDispenseFormGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormAddNewInvoice")
    public  void addNewInvoiceOnQueue(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String addNewInvoiceOnQueue=request.getParameter("AddNewInvoiceOnQueue");
        String encounter_date = request.getParameter("date_prescribed");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);

        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
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
                DrugExtra drugExtra=new DrugExtra();
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
                }
                dispensedModel.add(drugExtra);
            }
            PharmacyEncounter pharmacyEncounter=new PharmacyEncounter();
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
            pharmacyEncounter.setDisplay(0);
            pharmacyEncounter.setTotalAmount(receiptTotal);
            pharmacyEncounter.setDiscount(discount);
            pharmacyEncounter.setPaymentStatus(0);
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));

            service.savePharmacyEncounter(pharmacyEncounter);
            for(int i=0; i<dispensedModel.size(); i++){
                if(dispensedModel.get(i).getDrug() !=null)
                {
                    DrugExtra drugExtra = new DrugExtra();
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
                }
            }
            service.saveDrugExtra(listDrugExtra);
            encounterUUID=pharmacyEncounter.getUuid();
            response.getWriter().print(encounterUUID);
        }
        catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormProcessPayment")
    public void processPaymentForInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String encounterToProcessPayment=request.getParameter("encounterToProcessPayment");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }

            Double totalReceipt=0.0;
            Double amountWaived=0.0;
            Integer waiverNo=0;
            Double discount=0.0;
            dispensedModel = new ArrayList<DrugExtra>();
            List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
            Object obj = null;
            try {
                obj = parser.parse(jsonText);
                JSONArray dispenseInstanceArray = (JSONArray)obj;
                EncounterProcessor encounterProcessor=new EncounterProcessor();
                int dispenseInstanceArraySize=dispenseInstanceArray.size();
                for(int i=0; i<dispenseInstanceArraySize; i++){
                    DrugExtra drugExtra=new DrugExtra();
                    JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                    for(int j=0; j<rowInstance.size(); j++){
                        String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                        String key = myValues[0];
                        String value=myValues[1];

                        if(key.equalsIgnoreCase("dispenseFormDrug")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantity")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("drugExtraUUID")){
                            drugExtra.setUuid(value);
                        }
                        if(key.equalsIgnoreCase("totalAmount")){
                            totalReceipt= Double.valueOf(value);
                        }
                        if(key.equalsIgnoreCase("discount")){
                            discount=Double.valueOf(value);
                            drugExtra.setDiscount(discount);
                        }
                        if(key.equalsIgnoreCase("waiverNo")){
                            if(value.length() > 0){
                                waiverNo= Integer.valueOf(value);
                            }
                        }
                        if(key.equalsIgnoreCase("itemAmountWaived")){
                            amountWaived=Double.valueOf(value);
                            drugExtra.setAmountw(amountWaived);
                        }
                        if(key.equalsIgnoreCase("amount")){
                            drugExtra.setAmount(Double.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("dosage")){
                            pharmacyDose=service.getPharmacyDoseByID(Integer.valueOf(value));
                            drugExtra.setDose(pharmacyDose);
                        }
                    }
                    dispensedModel.add(drugExtra);
                }
                pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterToProcessPayment);
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {
                        drugExtraToUpdate=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                        if(drugExtraToUpdate !=null){
                            if(waiverNo !=0){
                                drugExtraToUpdate.setWaiverNo(waiverNo);
                            }
                            drugExtraToUpdate.setAmountw(dispensedModel.get(i).getAmountw());
                            drugExtraToUpdate.setDiscount(dispensedModel.get(i).getDiscount());
                            drugExtraToUpdate.setAmount(dispensedModel.get(i).getAmount());
                            drugExtraToUpdate.setDose(dispensedModel.get(i).getDose());
                            listDrugExtra.add(drugExtraToUpdate);
                        }
                    }
                }
                if(listDrugExtra.size() > 0){
                    service.saveDrugExtra(listDrugExtra);
                }
                pharmacyEncounter.setPaymentStatus(1);
                pharmacyEncounter.setTotalAmount(totalReceipt);
                service.savePharmacyEncounter(pharmacyEncounter);
                encounterUUID=pharmacyEncounter.getUuid();
                response.getWriter().print(encounterUUID);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormCheckInventoryAvailability")
    public void checkAllDrugsInventoryAvailability(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        dispensedModel = new ArrayList<DrugExtra>();
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
                        if(key.equalsIgnoreCase("dispenseFormDrug")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantity")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }
                    }
                    dispensedModel.add(drugExtra);
                }
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {
                        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(dispensedModel.get(i).getDrug().getDrugId(),locationUUID);
                        if(drugDispenseSettings==null){
                            booleanCheck = false;
                            break;
                        }
                        else{
                            //PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                            booleanCheck = true;
                        }
                    }
                }
                response.getWriter().print("" + booleanCheck);
            }
            catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormUpdateAndRequeue")
    public void UpdateAndRequeue(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String encounterToUpdate=request.getParameter("encounterToUpdate");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }

            Double totalReceipt=0.0;
            Double amountWaived=0.0;
            Integer waiverNo=0;
            Double discount=0.0;
            dispensedModel = new ArrayList<DrugExtra>();
            List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
            Object obj = null;
            try {
                obj = parser.parse(jsonText);
                JSONArray dispenseInstanceArray = (JSONArray)obj;
                EncounterProcessor encounterProcessor=new EncounterProcessor();
                int dispenseInstanceArraySize=dispenseInstanceArray.size();
                for(int i=0; i<dispenseInstanceArraySize; i++){
                    DrugExtra drugExtra=new DrugExtra();
                    JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                    for(int j=0; j<rowInstance.size(); j++){
                        String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                        String key = myValues[0];
                        String value=myValues[1];
                        if(key.equalsIgnoreCase("dispenseFormDrug")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantity")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("drugExtraUUID")){
                            drugExtra.setUuid(value);
                        }
                        if(key.equalsIgnoreCase("totalAmount")){
                            totalReceipt= Double.valueOf(value);
                        }
                        if(key.equalsIgnoreCase("discount")){
                            discount=Double.valueOf(value);
                            drugExtra.setDiscount(discount);
                        }
                        if(key.equalsIgnoreCase("waiverNo")){
                            if(value.length() > 0){
                                waiverNo= Integer.valueOf(value);
                            }
                        }
                        if(key.equalsIgnoreCase("itemAmountWaived")){
                            amountWaived=Double.valueOf(value);
                            drugExtra.setAmountw(amountWaived);
                        }
                        if(key.equalsIgnoreCase("amount")){
                            drugExtra.setAmount(Double.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("dosage")){
                            pharmacyDose=service.getPharmacyDoseByID(Integer.valueOf(value));
                            drugExtra.setDose(pharmacyDose);
                        }
                    }
                    dispensedModel.add(drugExtra);
                }
                pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterToUpdate);
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {
                        drugExtraToUpdate=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                        if(drugExtraToUpdate !=null){
                            if(waiverNo !=0){
                                drugExtraToUpdate.setWaiverNo(waiverNo);
                            }
                            drugExtraToUpdate.setAmountw(dispensedModel.get(i).getAmountw());
                            drugExtraToUpdate.setDiscount(dispensedModel.get(i).getDiscount());
                            drugExtraToUpdate.setAmount(dispensedModel.get(i).getAmount());
                            drugExtraToUpdate.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                            drugExtraToUpdate.setDose(dispensedModel.get(i).getDose());
                            listDrugExtra.add(drugExtraToUpdate);
                        }
                        else{
                            DrugExtra drugExtra=new DrugExtra();
                            drugExtra.setDrug(dispensedModel.get(i).getDrug());
                            drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                            if(waiverNo !=0){
                                drugExtra.setWaiverNo(waiverNo);
                            }
                            drugExtra.setAmountw(dispensedModel.get(i).getAmountw());
                            drugExtra.setPharmacyEncounter(pharmacyEncounter);
                            drugExtra.setDiscount(dispensedModel.get(i).getDiscount());
                            drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                            drugExtra.setAmount(dispensedModel.get(i).getAmount());
                            drugExtra.setDose(dispensedModel.get(i).getDose());
                            drugExtra.setPreviouslySoldQuantity(0);
                            listDrugExtra.add(drugExtra);
                            //service.saveDrugExtraObject(drugExtra);
                        }
                    }

             }
                if(listDrugExtra.size() > 0){
                    service.saveDrugExtra(listDrugExtra);
                }
                pharmacyEncounter.setPaymentStatus(0);
                pharmacyEncounter.setTotalAmount(totalReceipt);
                service.savePharmacyEncounter(pharmacyEncounter);
                encounterUUID=pharmacyEncounter.getUuid();
                response.getWriter().print(encounterUUID);
            }
            catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                 }


    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/amountAlreadyPaidForInvoice")
    public void confirmAmountAlreadyPaidForInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        service = Context.getService(PharmacyService.class);
        String encounterUUID=request.getParameter("encounterUUID");

        pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterUUID);
        List<DrugExtra> drugExtraList=service.getUnprocessedReceiptsByEncounterUUID(pharmacyEncounter.getUuid());
        Double amountAlreadyPaidForThisEncounter=0.0;
        for(int drugExtraObject=0; drugExtraObject<drugExtraList.size(); drugExtraObject++){
            amountAlreadyPaidForThisEncounter=amountAlreadyPaidForThisEncounter+drugExtraList.get(drugExtraObject).getAmount();
        }
        response.getWriter().print(amountAlreadyPaidForThisEncounter);
    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/reprocessEncounter")
    public void requeueInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        service = Context.getService(PharmacyService.class);
        String encounterUUID=request.getParameter("encounterUUID");

        pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterUUID);
        pharmacyEncounter.setDisplay(0);
        service.savePharmacyEncounter(pharmacyEncounter);
    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/temporaryCloseToPreviuosEncounters")
    public void temporaryCloseToPreviuosEncounters(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        service = Context.getService(PharmacyService.class);
        String encounterUUID=request.getParameter("encounterUUID");

        pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterUUID);
        pharmacyEncounter.setDisplay(1);
        service.savePharmacyEncounter(pharmacyEncounter);
    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/checkIfPaymentHasBeenMade")
    public void checkEncounterPaymentStatus(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        service = Context.getService(PharmacyService.class);
        String encounterToCheck=request.getParameter("encounterToCheck");

        pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterToCheck);
        Integer paymentValue=0;
        if(pharmacyEncounter !=null){
            paymentValue=pharmacyEncounter.getPaymentStatus();
        }
        response.getWriter().print(paymentValue);
    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/closePatientEncounter")
    public void closeEncounter(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        service = Context.getService(PharmacyService.class);
        String encounterToClose=request.getParameter("encounterToClose");

        PharmacyEncounter pharmacyEncounter1=service.getPharmacyEncounterByUuid(encounterToClose);
        person=pharmacyEncounter1.getPerson();
        String patientID=person.getPersonId().toString();
        EncounterProcessor encounterProcessor=new EncounterProcessor();
        encounterProcessor.setPatientId(patientID);
        Double totalReceipt=0.0;
        Double amountWaived=0.0;
        Double discount=0.0;
        Integer waiverNo=0;
        new ArrayList<DrugExtra>();
        List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
        Object obj=null;
        JSONParser parser=new JSONParser();
        String jsonText = request.getParameter("values");

        String locationVal= request.getSession().getAttribute("location").toString();
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();


        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        listAnotherPharmacyObs=new ArrayList<PharmacyObs>();
        pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterToClose);
        dispensedModel = (ArrayList)service.getDrugExtraByPharmacyEncounter(pharmacyEncounter);
        PharmacyOrders pharmacyOrderToUpdate=service.getPharmacyOrderByEncounter(pharmacyEncounter);
        if(pharmacyOrderToUpdate ==null){
            pharmacyOrderToUpdate = new PharmacyOrders();
            pharmacyOrderToUpdate.setAutoEndDate(null);
            pharmacyOrderToUpdate.setConcept("1800");
            pharmacyOrderToUpdate.setDiscontinued(false);
            pharmacyOrderToUpdate.setDiscontinuedDate(null);
            pharmacyOrderToUpdate.setDispensed(true);
            pharmacyOrderToUpdate.setInstructions(null);
            pharmacyEncounter.setDateTime(pharmacyEncounter.getDateTime());
            pharmacyOrderToUpdate.setStartDate(encDate);
            pharmacyOrderToUpdate.setPharmacyEncounter(pharmacyEncounter);

        }
        else{
            pharmacyOrderToUpdate.setStartDate(encDate);
        }
        listPharmacyOrders.add(pharmacyOrderToUpdate);
            for(int i=0; i<dispensedModel.size(); i++){
                if(dispensedModel.get(i).getDrug() !=null) {
                    drugExtraToUpdate=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                    drugExtraToUpdate.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                    if(waiverNo !=0){
                        drugExtraToUpdate.setWaiverNo(waiverNo);
                    }
                    drugExtraToUpdate.setAmountw(dispensedModel.get(i).getAmountw());
                    drugExtraToUpdate.setDiscount(dispensedModel.get(i).getDiscount());
                    if(dispensedModel.get(i).getDose() !=null){
                       drugExtraToUpdate.setDose(dispensedModel.get(i).getDose());

                    }
                    //service.saveDrugExtraObject(drugExtraToUpdate);
                    //substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal);

                    PharmacyDrugOrder pharmacyDrugOrderToUpdate=service.getPharmacyDrugOrdersByDrugExtraUUID(dispensedModel.get(i));
                    if(pharmacyDrugOrderToUpdate !=null){
                        pharmacyOrderToUpdate.setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                        pharmacyOrderToUpdate.setStartDate(encDate);
                        listPharmacyOrders.add(pharmacyOrderToUpdate);

                        pharmacyDrugOrderToUpdate.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug()).getInventoryId());
                        pharmacyDrugOrderToUpdate.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                        pharmacyDrugOrderToUpdate.setOrderUuid(pharmacyOrderToUpdate);
                        pharmacyDrugOrderToUpdate.setQuantityPrescribed(dispensedModel.get(i).getQuantitysold());
                        pharmacyDrugOrderToUpdate.setQuantityGiven(dispensedModel.get(i).getQuantitysold());
                        if(dispensedModel.get(i).getDose() !=null) {
                            pharmacyDrugOrderToUpdate.setDose(dispensedModel.get(i).getDose().getName());
                        }
                        listPharmacyDrugOrders.add(pharmacyDrugOrderToUpdate);

                        List<PharmacyObs> pharmacyObsToUpdate=service.getPharmacyObsByPharmacyOrder(pharmacyOrderToUpdate);
                        if(pharmacyObsToUpdate.size() < 0){
                            for(int currentPharmacyObservation=0; currentPharmacyObservation<pharmacyObsToUpdate.size(); currentPharmacyObservation++){
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setLocation(service.getPharmacyLocationsByName(locationVal));
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setPharmacyEncounter(pharmacyEncounter);
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setPharmacyOrder(pharmacyOrderToUpdate);
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setValue_drug(Context.getConceptService().getDrugByNameOrId(dispensedModel.get(i).getDrug().getDrugId().toString()));
                                pharmacyObsToUpdate.get(currentPharmacyObservation).setValueDatetime(encDate);
                                listAnotherPharmacyObs.add(pharmacyObsToUpdate.get(currentPharmacyObservation));
                            }
                        }

                    }
                    else{
                        PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                        pharmacyDrugOrder.setDrugUuid(drugExtraToUpdate);
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
                            SimpleDateFormat todayDate=new SimpleDateFormat("MM/dd/yyyy");
                            Date realDate=new Date();
                            String temporaryDateString =todayDate.format(realDate);
                            realDate=todayDate.parse(temporaryDateString);
                            pharmacyDrugOrder.setExpected_next_visit_date(realDate);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        pharmacyDrugOrder.setFrequency(frequency.toString());
                        pharmacyDrugOrder.setOrderUuid(pharmacyOrderToUpdate);
                        pharmacyDrugOrder.setQuantityPrescribed(dispensedModel.get(i).getQuantitysold());
                        pharmacyDrugOrder.setQuantityGiven(dispensedModel.get(i).getQuantitysold());
                        pharmacyDrugOrder.setUnits(units);
                        listPharmacyDrugOrders.add(pharmacyDrugOrder);

                        PharmacyObs pharmacyObs = new PharmacyObs();
                        pharmacyObs.setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                        pharmacyObs.setDateStarted(null);
                        pharmacyObs.setDateStopped(null);
                        pharmacyObs.setComment(null);
                        pharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                        pharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                        pharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                        pharmacyObs.setPharmacyOrder(pharmacyOrderToUpdate);
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

                        if(dispensedModel.get(i).getUuid() == null){
                            DrugExtra drugExtra=new DrugExtra();
                            drugExtra.setPharmacyEncounter(pharmacyEncounter);
                            drugExtra.setDrug(dispensedModel.get(i).getDrug());
                            drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                            drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                            drugExtra.setAmount(dispensedModel.get(i).getAmount());
                            drugExtra.setDose(dispensedModel.get(i).getDose());
                            drugExtra.setEncounterDate(pharmacyEncounter.getDateTime());
                            listDrugExtra.add(drugExtra);
                        }

                    }

                }
            }
            isSavePharmacyOrdersSucces=service.savePharmacyOrders(listPharmacyOrders);
            isSaveObsSuccess=service.savePharmacyObs(listAnotherPharmacyObs);
            pharmacyEncounter =service.getPharmacyEncounterByUuid(encounterToClose);
            pharmacyEncounter.setDisplay(1);
            if(listDrugExtra !=null){
                //service.saveDrugExtra(listDrugExtra);
            }
            service.savePharmacyEncounter(pharmacyEncounter);
            // if(isSavePharmacyOrdersSucces && isSavePharmacyDrugOrderSucccess && isSaveObsSuccess){
            for(int i=0; i<dispensedModel.size(); i++){
                if(dispensedModel.get(i).getDrug() !=null){
                    substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal,dispensedModel.get(i).getPreviouslySoldQuantity());
                    DrugExtra drugExtraToUpdateSoldValue=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                    if(drugExtraToUpdateSoldValue !=null){
                        drugExtraToUpdateSoldValue.setPreviouslySoldQuantity(dispensedModel.get(i).getQuantitysold());
                        service.saveDrugExtraObject(drugExtraToUpdateSoldValue);
                    }

                }
                //}

            }
            isSavePharmacyDrugOrderSucccess=service.savePharmacyDrugOrders(listPharmacyDrugOrders);

    }
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

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
}
