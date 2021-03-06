package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
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
@Authorized("Manage Pharmacy")
public class AdultHIVProcessor{
    private static final Log log = LogFactory.getLog(AdultHIVProcessor.class);
    private ContainerFactory containerFactory;
    private String[][] encdata;
    private ConceptService conceptService;
    private PharmacyService service;
    private String patientID = null;
    private String prescriber = null;
    private String pharmacyUser = null;
    private String question;
    private String question_ans;
    private boolean morethanOne = false;
    private String questionTwo;
    private String question_ansTwo;
    private String questionThree;
    private String question_ansThree;
    private String date;
    private String nextVisitDate;
    private String noOfMonths;
    private Person person;
    private ArrayList<String> drugDispensed;
    private ArrayList<ArrayList<String>> drugAll;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size;
    private JSONParser parser;
    private PharmacyEncounter pEncounter;
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private Date encDate;
    private PharmacyObs ppharmacyObs;
    private Date endDate;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private ArrayList<MedicationProcessor> listMedicationProcessors;
    private boolean savedOrders=false,savedObs=false;
    private     int numbersInventtory[][];
    private PharmacyDrugOrder drugOrder;
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/HivForm")
    public void hivFormGetServlet(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {
    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/adultHIVProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        conceptService = Context.getConceptService();
        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        String regimenCode=request.getParameter("regimenCode");
        String regimenName=request.getParameter("regimenName");
        String isPMTCTChecked=request.getParameter("pmtctSelected");
        String isPEPChecked=request.getParameter("pepSelected");
        String regimenChanged=request.getParameter("regimenChanged");
        String regimenInitition=request.getParameter("regimenInitiation");
        String isOnlyOIRefill=request.getParameter("isOnlyOIRefill");

        MedicationProcessor medicationProcessor = new MedicationProcessor();
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        List<ObsProcessor> listObsProcessor = new ArrayList<ObsProcessor>();
        List<NonObsProcessor> listNonObsProcessor = new ArrayList<NonObsProcessor>();
        //List<MedicationProcessor> listMedicationProcessors=  new ArrayList<MedicationProcessor>();
        List<  ArrayList<MedicationProcessor>> bigListMedicationProcessors   =   new ArrayList<ArrayList<MedicationProcessor>>();
        List<PharmacyObs> listPharmacyObs= new ArrayList<PharmacyObs>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
        listMedicationProcessors = new ArrayList<MedicationProcessor>();
        JSONParser parser= new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);
            JSONArray dispenseInstanceArray = (JSONArray)obj;
            obsProcessor = new ObsProcessor();
            int dispenseInstanceArraySize=dispenseInstanceArray.size();
            for(int i=0; i<dispenseInstanceArraySize; i++){
                medicationProcessor = new MedicationProcessor();
                JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("patientIdAdultHIVForm")){
                        String patient=service.getPatientByIdentifier(value);
                        encounterProcessor.setPatientId(patient);
                    }
                    if(key.equalsIgnoreCase("Enc3*ADULTINITIAL|1#6")){
                        encounterProcessor.setEncounterType(value);
                    }
                    if(key.equalsIgnoreCase("Enc4*EncounterDate#3")){
                        encounterProcessor.setEncounterDate(value);
                    }
                    if(key.equalsIgnoreCase("Enc5*EncounterDate#3")){
                        encounterProcessor.setNextVisitDate(value);
                    }
                    if(key.equalsIgnoreCase("Enc6*noOfMonths|6#6")){
                        encounterProcessor.setDuration(value);
                    }
                    if(key.equalsIgnoreCase("Obs")){
                        if(value.contains("|"))
                        {
                            obsProcessor.setConcept(key.substring(key.indexOf("|")+1,(key.indexOf("#"))));
                            obsProcessor.setConceptAnswer(value.substring(value.indexOf("|") + 1));
                            listObsProcessor.add(obsProcessor);
                        }
                    }
                    if(key.contains("drug")){
                        medicationProcessor.setConcept(value.substring(0,value.indexOf("|")));
                        medicationProcessor.setFrequency("1");
                        String drugExtract= value.substring(value.indexOf("|")+1);
                        Drug drug = Context.getConceptService().getDrugByNameOrId(drugExtract);
                        medicationProcessor.setDrug(drug);
                    }

                    if(key.equalsIgnoreCase("PillCount"))
                    {
                        medicationProcessor.setPillcount(value);
                    }
                    if(key.contains("requested"))
                    {
                        medicationProcessor.setquantity(value);
                    }
                    if(key.contains("dose"))
                    {
                        medicationProcessor.setDose(value);
                    }
                    if(key.equalsIgnoreCase("otherR"))
                    {
                        if(value != null && !value.isEmpty()) {
                            medicationProcessor.setquantity(value);
                        }
                    }
                    if(key.contains("dispensed"))
                    {
                        medicationProcessor.setDispensed(value);
                    }
                    if(key.equalsIgnoreCase("Quantity"))
                    {   if(value != null && !value.isEmpty()) {
                        medicationProcessor.setDispensed(value);
                    }
                    }
                    if(key.equalsIgnoreCase("Prescriber"))
                    {
                        encounterProcessor.setPrescriber(value);
                    }
                }
                listMedicationProcessors.add(medicationProcessor);
            }
            PharmacyEncounter pharmacyEncounter = new PharmacyEncounter();
            pharmacyEncounter.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor.getEncounterType()));
            pharmacyEncounter.setFormName(encounterProcessor.getForm());
            try {
                encDate=new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getEncounterDate());
                endDate=new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getNextVisitDate());
                pharmacyEncounter.setDateTime(encDate);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            pharmacyEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncounter.setNextVisitDate(endDate);
            pharmacyEncounter.setDuration(Integer.parseInt(encounterProcessor.getDuration()));
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
            Patient patient=Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId()));
            PharmacyEncounter previousPatientEncounter=service.getLastPharmacyEncounterByPatientUUID(patient);
            if(regimenName =="" && previousPatientEncounter !=null){
                if(previousPatientEncounter.getRegimenName() !=null){
                    pharmacyEncounter.setRegimenCode(previousPatientEncounter.getRegimenCode());
                    pharmacyEncounter.setRegimenName(previousPatientEncounter.getRegimenName());
                }
            }
            else{
                pharmacyEncounter.setRegimenCode(regimenCode);
                pharmacyEncounter.setRegimenName(regimenName);
            }
            pharmacyEncounter.setFormName("ADULTHIV");
            pharmacyEncounter.setPEPChecked(Integer.valueOf(isPEPChecked));
            pharmacyEncounter.setPMTCTChecked(Integer.valueOf(isPMTCTChecked));
            pharmacyEncounter.setRegimenChanged(Integer.valueOf(regimenChanged));
            pharmacyEncounter.setDisplay(0);
            service.savePharmacyEncounter(pharmacyEncounter);
            for (int y=0;y<listObsProcessor.size();y++){
                ppharmacyObs = new PharmacyObs(); //
                ppharmacyObs.setConcept(listObsProcessor.get(y).getConcept());
                // ppharmacyObs.setValueCoded(null);

                if (!encounterProcessor.getPrescriber().equals("null")) {
                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(encounterProcessor.getPrescriber()).getUuid());
                }
                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                ppharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                ppharmacyObs.setValueDatetime(null);
                ppharmacyObs.setValueNumeric(CheckIfDoubleNull(listObsProcessor.get(y).getConceptAnswer()));
                ppharmacyObs.setValueText(null);
                ppharmacyObs.setValueCodedName(null);
                ppharmacyObs.setValueGroupId(null);
                ppharmacyObs.setValueModifier(null);
                ppharmacyObs.setValueText(null);
                ppharmacyObs.setValueBoolen(false);
                ppharmacyObs.setComment(null);
                ppharmacyObs.setDateStarted(null);
                ppharmacyObs.setDateStopped(null);
                listPharmacyObs.add(ppharmacyObs) ;
            }
            service.savePharmacyObs(listPharmacyObs);
            numbersInventtory= new int[listMedicationProcessors.size()][2];
            int position=0;
            List<Integer> dispensedDrugsId=new ArrayList<Integer>();
            List<String> drugsDispensedQuantities=new ArrayList<String>();
            for(int i=0; i<listMedicationProcessors.size(); i++)
            {
                if(listMedicationProcessors.get(i) !=null){
                    PharmacyOrders pharmacyOrders = new PharmacyOrders();
                    pharmacyOrders.setAutoEndDate(null);
                    if(listMedicationProcessors.get(i).getConcept() !=null ) {
                        pharmacyOrders.setConcept(CheckIfStringNull(listMedicationProcessors.get(i).getConcept()));
                    }
                    pharmacyOrders.setDiscontinued(false);
                    pharmacyOrders.setDiscontinuedDate(null);
                    pharmacyOrders.setDispensed(true);
                    pharmacyOrders.setInstructions(null);
                    pharmacyOrders.setStartDate(encDate);
                    pharmacyOrders.setPharmacyEncounter(pharmacyEncounter);
                    listPharmacyOrders.add(pharmacyOrders);
                    if(listMedicationProcessors.get(i).getDrug() !=null){
                        PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                        //pharmacyDrugOrder.setDose(CheckIfDoubleNull(listMedicationProcessors.get(i).getDose()));
                        pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugIdAndLocation(listMedicationProcessors.get(i).getDrug().getDrugId(),locationUUID).getInventoryId());
                        pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                        pharmacyDrugOrder.setEquivalentDailyDose(0);
                        pharmacyDrugOrder.setDrug(listMedicationProcessors.get(i).getDrug());
                        pharmacyDrugOrder.setPharmacyLocation(service.getPharmacyLocationsByName(locationVal));
                        pharmacyDrugOrder.setFormName("ADULTHIV");
                        pharmacyDrugOrder.setFrequency(CheckIfStringNull(listMedicationProcessors.get(i).getFrequency()));
                        pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                        pharmacyDrugOrder.setDose(listMedicationProcessors.get(i).getDose());
                        pharmacyDrugOrder.setQuantityPrescribed(CheckIfIntNull(listMedicationProcessors.get(i).getQuantity()));
                        pharmacyDrugOrder.setQuantityGiven(Integer.valueOf(listMedicationProcessors.get(i).getDispensed()));
                        String date_s = "2000-01-18 00:00:00.0";
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date1 = null;
                        try {
                            date1 = dt.parse(date_s);
                        } catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }


                        drugOrder= service.getHIVPatientLastVisitPharmacyDrugOrder(encounterProcessor.getPatientId(),"RFP");
                        SimpleDateFormat todaysDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date realDate=new Date();
                        String dateFormat=todaysDate.format(realDate.getTime());
                        Date actualDate=null;
                        try {
                            actualDate=todaysDate.parse(dateFormat);
                        } catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        int DaysInMillSec= 1000 * 60 * 60 * 24;
                        long daysToNextVisit=0;
                        long today=0;
                        long DiffInDays=0;
                        int drugsPerDay=2;
                        if(drugOrder !=null){
                            daysToNextVisit= drugOrder.getExpected_next_visit_date().getTime()/DaysInMillSec;
                            today=actualDate.getTime()/DaysInMillSec;
                            DiffInDays=daysToNextVisit-today;

                        }
                        int numberOfTabletsInDose=1;
                        if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("1 tablet BD")){
                            numberOfTabletsInDose=2;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("1 tablet OD")){
                            numberOfTabletsInDose=1;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("1 tablet nocte")){
                            numberOfTabletsInDose=1;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("2 tablets BD")){
                            numberOfTabletsInDose=4;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("3 tablets BD")){
                            numberOfTabletsInDose=6;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("1 tab OD")){
                            numberOfTabletsInDose=1;
                        }
                        else if(listMedicationProcessors.get(i).getDispensed().equalsIgnoreCase("1 tab BD")){
                            numberOfTabletsInDose=1;
                        }
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        long currentDay=actualDate.getTime()/DaysInMillSec;

                        Date nextVisitDate=df.parse(encounterProcessor.getNextVisitDate());

                        long currentVisitDifferenceInDaysWithEnteredNextVisitDate=nextVisitDate.getTime()/DaysInMillSec;

                        currentVisitDifferenceInDaysWithEnteredNextVisitDate=currentVisitDifferenceInDaysWithEnteredNextVisitDate-currentDay;
                        int no_of_days_to_last= 0;

                        if(Integer.valueOf(regimenChanged)==1 || Integer.valueOf(regimenInitition)==1) {
                            no_of_days_to_last = (int) currentVisitDifferenceInDaysWithEnteredNextVisitDate;
                        }
                        else if(Integer.valueOf(isOnlyOIRefill)==1){
                            no_of_days_to_last =(int)DiffInDays;
                        }
                        else{
                            no_of_days_to_last = (int) currentVisitDifferenceInDaysWithEnteredNextVisitDate + (int) DiffInDays;
                        }

                        Calendar cal=new GregorianCalendar();
                        cal.add(Calendar.DAY_OF_MONTH, no_of_days_to_last);
                        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String dateFormated=fmt.format(cal.getTime());
                        Date expNextDateOfVisit= null;
                        try {
                            expNextDateOfVisit = fmt.parse(dateFormated);

                        } catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        pharmacyDrugOrder.setExpected_next_visit_date(expNextDateOfVisit);
                        listPharmacyDrugOrders.add(pharmacyDrugOrder);
                        dispensedDrugsId.add(listMedicationProcessors.get(i).getDrug().getDrugId());
                        drugsDispensedQuantities.add(listMedicationProcessors.get(i).getDispensed());
                        listAnotherPharmacyObs.add(createPharmacyObs("1896",listMedicationProcessors.get(i).getFrequency(),"",listMedicationProcessors.get(i).getDrug().getDrugId().toString(),encounterProcessor.getPrescriber(),locationVal,encounterProcessor.getPatientId(),pharmacyEncounter,null));
                        listAnotherPharmacyObs.add(createPharmacyObs(listMedicationProcessors.get(i).getConcept(),listMedicationProcessors.get(i).getConceptAnswer(),"",listMedicationProcessors.get(i).getDrug().getDrugId().toString(),encounterProcessor.getPrescriber(),locationVal,encounterProcessor.getPatientId(),pharmacyEncounter,pharmacyOrders));
                        position++;
                    }

                }
            }
            service.savePharmacyOrders(listPharmacyOrders);
            savedOrders=service.savePharmacyDrugOrders(listPharmacyDrugOrders);
            savedObs= service.savePharmacyObs(listAnotherPharmacyObs);
            if( savedObs && savedOrders){
                for(int y=0;y<dispensedDrugsId.size();y++){
                    substractFromInventory(dispensedDrugsId.get(y), Integer.valueOf(drugsDispensedQuantities.get(y)), locationVal);
                }
            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        JSONArray array = (JSONArray) obj;
    }
    public synchronized String ArrayDataOne(String jsonText) {
        String value = "";
        JSONParser parser = new JSONParser();
        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

                value+=entry.getValue()+"@";
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value;

    }
    public PharmacyObs createPharmacyObs(String concept,String valueCoded,String valueNumeric,String valueDrug,String prescriber, String locationVal,String patientID, PharmacyEncounter pharmacyEncounter, PharmacyOrders  pharmacyOrders){
        PharmacyObs pharmacyObs = new PharmacyObs();
        pharmacyObs.setConcept(CheckIfStringNull(concept));
        pharmacyObs.setDateStarted(null);
        pharmacyObs.setDateStopped(null);
        pharmacyObs.setComment(null);
        pharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
        pharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));
        pharmacyObs.setPharmacyEncounter(pharmacyEncounter);
        pharmacyObs.setPharmacyOrder(pharmacyOrders);
        pharmacyObs.setValueCoded(CheckIfIntNull(valueCoded));
        if (!prescriber.equals("null")) {
            pharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(prescriber).getUuid());
        }
        pharmacyObs.setValue_drug(Context.getConceptService().getDrugByNameOrId(valueDrug));
        pharmacyObs.setValueBoolen(false);
        pharmacyObs.setValueGroupId(null);
        pharmacyObs.setValueText(null);
        pharmacyObs.setValueNumeric(CheckIfDoubleNull(valueNumeric));
        pharmacyObs.setValueModifier(null);
        pharmacyObs.setValueCodedName(null);
        pharmacyObs.setValueDatetime(encDate);
        return pharmacyObs;
    }
    public int CheckIfIntNull(String data){
        int object;
        if(data==null)  {
            object=0;

        }
        else
        {
            if(data.length()>0)  {
                object=Integer.parseInt(data);

            }
            else
                object=0;


        }
        return object;

    }
    public String CheckIfStringNull(String data){
        String object;
        if(data.length()==0)
            object=null;
        else
        {
            object=data;

        }
        return object;

    }
    public Double CheckIfDoubleNull(String data){
        Double object;
        if(data.length()==0)
            object=0.0;
        else
        {
            object=Double.parseDouble(data);
        }
        return object;

    }
    public void  substractFromInventory(Integer drugId,int Qnty,String val){
        Drug drug = Context.getConceptService().getDrugByNameOrId(drugId.toString());
        String locationUUID=service.getPharmacyLocationsByName(val).getUuid();
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drug.getDrugId(),locationUUID);
        if(drugDispenseSettings !=null){
            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
            if(pharmacyStore!=null ){
                if(pharmacyStore.getQuantity() > Qnty || pharmacyStore.getQuantity() == Qnty){
                    pharmacyStore.setQuantity(pharmacyStore.getQuantity()-Qnty);
                    service.savePharmacyInventoryItem(pharmacyStore);
                }
            }
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
}
