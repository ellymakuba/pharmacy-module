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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class HivPedsProcessorController {
    private static final Log log = LogFactory.getLog(HivPedsProcessorController.class);
    private ContainerFactory containerFactory;
    private String[][] encdata;
    private ConceptService conceptService;
    private PharmacyService service;
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private Date encDate;
    private PharmacyObs pharmacyObs;
    private Date endDate;
    private ArrayList<MedicationProcessor> listMedicationProcessors;
    private boolean savedOrders=false,savedObs=false;
    private PharmacyDrugOrder drugOrder;
    private String comment;

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/hivPedsProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();
        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        String regimenCode=request.getParameter("regimenCode");
        String regimenName=request.getParameter("regimenName");
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
        List<PharmacyObs> listPharmacyObs= new ArrayList<PharmacyObs>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
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
                obsProcessor = new ObsProcessor();
                JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("patientId")){
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
                    if(key.equalsIgnoreCase("Enc7*PEDIATRICARV |0.1#1")){
                        encounterProcessor.setForm(value);
                    }
                    if(key.contains("Obs")){

                        String concept=key.substring(key.indexOf("|") + 1, (key.indexOf("#")));
                        String conceptAnswer=value.substring(value.indexOf("|") + 1);
                        String conceptTextValue=value.substring(0,value.indexOf("|"));


                        obsProcessor.setConcept(concept);
                        obsProcessor.setConceptAnswer(conceptAnswer);
                        obsProcessor.setValueText(conceptTextValue);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }
                    if(key.equalsIgnoreCase("comment")){
                        comment=value;

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept("7222");
                        obsProcessor.setComment(comment);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }
                    if(key.contains("drug")){
                        medicationProcessor.setConcept(value.substring(0,value.indexOf("|")));
                        medicationProcessor.setFrequency("1");
                        String drugExtract= value.substring(value.indexOf("|")+1);
                        medicationProcessor.setConceptAnswer(drugExtract);
                        Drug drug = Context.getConceptService().getDrugByNameOrId(drugExtract);
                        medicationProcessor.setDrug(drug);

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept(value.substring(0,value.indexOf("|")));
                        obsProcessor.setConceptAnswer(drugExtract);
                        obsProcessor.setValueDrug(Context.getConceptService().getDrugByNameOrId(drugExtract));
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }

                    if(key.equalsIgnoreCase("PillCount"))
                    {
                        medicationProcessor.setPillcount(value);

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept("7906");
                        obsProcessor.setValueNumeric(value);
                        obsProcessor.setValueText(key);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }
                    if(key.contains("requested"))
                    {
                        medicationProcessor.setquantity(value);

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept("1898");
                        obsProcessor.setValueNumeric(value);
                        obsProcessor.setValueText(key);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }
                    if(key.contains("dose"))
                    {
                        medicationProcessor.setDose(value);

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept("1182");
                        obsProcessor.setValueText(key);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
                    }

                    if(key.equalsIgnoreCase("other"))
                    {
                        if(value !=null) {
                            medicationProcessor.setquantity(value);

                            obsProcessor = new ObsProcessor();
                            obsProcessor.setConcept("5622");
                            obsProcessor.setValueNumeric(value);
                            obsProcessor.setValueText(key);
                            obsProcessor.setRowCounter(i);
                            listObsProcessor.add(obsProcessor);
                        }
                    }
                    if(key.equalsIgnoreCase("Quantity"))
                    {
                        medicationProcessor.setDispensed(value);

                        obsProcessor = new ObsProcessor();
                        obsProcessor.setConcept("1758");
                        obsProcessor.setValueNumeric(value);
                        obsProcessor.setValueText(key);
                        obsProcessor.setRowCounter(i);
                        listObsProcessor.add(obsProcessor);
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
            pharmacyEncounter.setComment(comment);
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
            if(regimenName ==""){
                Patient patient=Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId()));
                PharmacyEncounter previousPatientEncounter=service.getLastPharmacyEncounterByPatientUUID(patient);
                pharmacyEncounter.setRegimenCode(previousPatientEncounter.getRegimenCode());
                pharmacyEncounter.setRegimenName(previousPatientEncounter.getRegimenName());
            }
            else{
                pharmacyEncounter.setRegimenCode(regimenCode);
                pharmacyEncounter.setRegimenName(regimenName);
            }
            pharmacyEncounter.setFormName("PEDIATRICARV");
            service.savePharmacyEncounter(pharmacyEncounter);


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

                for (ObsProcessor obsProcessorInstance:listObsProcessor){

                    if(obsProcessorInstance.getRowCounter() ==i) {
                        pharmacyObs = new PharmacyObs();
                        pharmacyObs.setConcept(obsProcessorInstance.getConcept());
                        pharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                        pharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                        pharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                        pharmacyObs.setValueDatetime(null);
                        if(obsProcessorInstance.getValueNumeric() !=null && obsProcessorInstance.getValueNumeric().length()>0) {
                            pharmacyObs.setValueNumeric(Double.valueOf(obsProcessorInstance.getValueNumeric()));
                        }
                        pharmacyObs.setValueText(obsProcessorInstance.getValueText());
                        pharmacyObs.setValueGroupId(obsProcessorInstance.getRowCounter());
                        pharmacyObs.setPharmacyOrder(pharmacyOrders);
                        pharmacyObs.setValueCodedName(null);
                        if(obsProcessorInstance.getConceptAnswer() !=null && obsProcessorInstance.getConceptAnswer().length()>0) {
                            pharmacyObs.setValueCoded(Integer.valueOf(obsProcessorInstance.getConceptAnswer()));
                        }
                        pharmacyObs.setValueModifier(null);
                        pharmacyObs.setValueBoolen(false);
                        pharmacyObs.setComment(obsProcessorInstance.getComment());
                        if(listMedicationProcessors.get(i).getDrug() !=null) {
                            pharmacyObs.setValue_drug(obsProcessorInstance.getValueDrug());
                        }
                        pharmacyObs.setDateStarted(null);
                        pharmacyObs.setDateStopped(null);
                        listPharmacyObs.add(pharmacyObs);
                    }
                }

                    if(listMedicationProcessors.get(i).getDrug() !=null){
                        PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                        //pharmacyDrugOrder.setDose(CheckIfDoubleNull(listMedicationProcessors.get(i).getDose()));
                        pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugIdAndLocation(listMedicationProcessors.get(i).getDrug().getDrugId(),locationUUID).getInventoryId());
                        pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                        pharmacyDrugOrder.setEquivalentDailyDose(0);
                        pharmacyDrugOrder.setFormName("pediatricHIV");
                        pharmacyDrugOrder.setFrequency(CheckIfStringNull(listMedicationProcessors.get(i).getFrequency()));
                        pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                        pharmacyDrugOrder.setDrug(listMedicationProcessors.get(i).getDrug());
                        pharmacyDrugOrder.setPharmacyLocation(service.getPharmacyLocationsByName(locationVal));
                        pharmacyDrugOrder.setQuantityPrescribed(CheckIfIntNull(listMedicationProcessors.get(i).getQuantity()));
                        pharmacyDrugOrder.setQuantityGiven(Integer.valueOf(listMedicationProcessors.get(i).getDispensed()));
                        pharmacyDrugOrder.setDose(listMedicationProcessors.get(i).getDose());
                        String date_s = "2000-01-18 00:00:00.0";
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date1 = null;
                        try {
                            date1 = dt.parse(date_s);
                        } catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                        //drugOrder= service.getHIVPatientLastVisitPharmacyDrugOrder(Integer.valueOf(encounterProcessor.getPatientId()),"RFP");
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
                       /* if(drugOrder !=null){
                            daysToNextVisit= drugOrder.getExpected_next_visit_date().getTime()/DaysInMillSec;
                            today=actualDate.getTime()/DaysInMillSec;
                            DiffInDays=daysToNextVisit-today;
                        } */
                        int no_of_days_to_last= CheckIfIntNull(listMedicationProcessors.get(i).getDispensed())/2+(int) DiffInDays;
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
                        //listAnotherPharmacyObs.add(createPharmacyObs(listMedicationProcessors.get(i).getConcept(),listMedicationProcessors.get(i).getConceptAnswer(),"",listMedicationProcessors.get(i).getDrug().getDrugId().toString(),encounterProcessor.getPrescriber(),locationVal,encounterProcessor.getPatientId(),pharmacyEncounter,pharmacyOrders));

                    }

                }
            }
            service.savePharmacyOrders(listPharmacyOrders);
            savedOrders=service.savePharmacyDrugOrders(listPharmacyDrugOrders);
            //savedObs= service.savePharmacyObs(listAnotherPharmacyObs);
            savedObs= service.savePharmacyObs(listPharmacyObs);
            if(savedObs && savedOrders){
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
        pharmacyObs.setValueGroupId(Integer.valueOf(concept));
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
        //System.out.println("one++++++++++++++++++inside subtractfrom inventory +++++++++++++++++++"+drugId+" Qnty +++"+Qnty+" val "+val);
        if(drugDispenseSettings !=null){
            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
            if(pharmacyStore!=null ){
                //System.out.println("two++++++++++++++++++inside subtractfrom inventory +++++++++++++++++++"+drugId+" Qnty +++"+Qnty+" val "+val);
                if(pharmacyStore.getQuantity() > Qnty || pharmacyStore.getQuantity() == Qnty){
                   //System.out.println("three++++++++++++++++++inside subtractfrom inventory +++++++++++++++++++"+drugId+" Qnty +++"+Qnty+" val "+val);
                    pharmacyStore.setQuantity(pharmacyStore.getQuantity()-Qnty);
                    service.savePharmacyInventory(pharmacyStore);
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
