package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class RevolvingAdultProcessorController {
    private static final Log log = LogFactory.getLog(RevolvingAdultProcessorController.class);
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
    private ArrayList<String> drugDispensed;
    private ArrayList<ArrayList<String>> drugAll;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size;
    private JSONParser parser;
    private PharmacyEncounter pEncounter;

    private Date encDate;
    private PharmacyObs ppharmacyObs;
    private Date endDate;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private List<DrugExtra> listPharmacyDrugOrderExtra2;
    private ArrayList<MedicationProcessor> listMedicationProcessors;
    private boolean addToBigList = false;
    private boolean RequestedFirstPass = false;
    private boolean savedOrders=false,savedObs=false;
    private     int numbersInventtory[][];
    private String receiptNo,waiverNo,dQuantity;
    private boolean aded=false;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/revolvingAdultProcessor")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/revolvingAdultProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();


        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        MedicationProcessor medicationProcessor = new MedicationProcessor();  //
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        List<ObsProcessor> listObsProcessor = new ArrayList<ObsProcessor>();
        List<NonObsProcessor> listNonObsProcessor = new ArrayList<NonObsProcessor>();
        //List<MedicationProcessor> listMedicationProcessors=  new ArrayList<MedicationProcessor>();
        List<ArrayList<MedicationProcessor>> bigListMedicationProcessors = new ArrayList<ArrayList<MedicationProcessor>>();
        List<PharmacyObs> listPharmacyObs = new ArrayList<PharmacyObs>();
        List<DrugExtra> listPharmacyDrugOrderExtra = new ArrayList<DrugExtra>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        listAnotherPharmacyObs = new ArrayList<PharmacyObs>();
        listMedicationProcessors = new ArrayList<MedicationProcessor>();
        boolean addData=false ;
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);

            JSONArray array = (JSONArray) obj;

            for (int i = 0; i < array.size(); i++) {
                String value = ArrayDataOne(array.get(i).toString());

                if (value.contains("*")) {

                    if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc2")) {

                        encounterProcessor.setPatientId(value.substring(value.indexOf("@")+1,value.length()-1));

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc3")) {


                        encounterProcessor.setEncounterType(value.substring(value.indexOf("*") + 1, value.indexOf("|")));

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc4")) {

                        encounterProcessor.setEncounterDate(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc5")) {

                        encounterProcessor.setNextVisitDate(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc6")) {

                        encounterProcessor.setDuration(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc7")) {


                        encounterProcessor.setForm(value.substring(value.indexOf("@") + 1, value.length() - 1));


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Obs")) {
                        obsProcessor = new ObsProcessor();

                        obsProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                        obsProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|") + 1));

                        listObsProcessor.add(obsProcessor);
                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDrug")) {
                        if(value.indexOf("-")>0){
                            listMedicationProcessors = new ArrayList<MedicationProcessor>();
                            addToBigList = true;
                            String val=((value.substring(value.indexOf("@") + 1, (value.length() - 1))).substring((value.substring(value.indexOf("@") + 1, (value.length() - 1))).indexOf("-")+1));

                            aded=true;
                            medicationProcessor = new MedicationProcessor();
                            medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                            medicationProcessor.setConceptAnswer(val.substring(val.indexOf("|") + 1));
                            Integer drugId = Integer.valueOf(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|") + 1));
                            Drug drug = new Drug(drugId);
                            medicationProcessor.setDrug(drug);
                            listMedicationProcessors.add(medicationProcessor);
                        }else if(value.substring(value.indexOf("@") + 1, (value.length() - 1)).length()>1){
                            listMedicationProcessors = new ArrayList<MedicationProcessor>();
                            addToBigList = true;
                            medicationProcessor = new MedicationProcessor();

                            medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                            medicationProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(0, value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|")));

                            Integer drugId = Integer.valueOf(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|") + 1));
                            Drug drug = new Drug(drugId);
                            medicationProcessor.setDrug(drug);
                            listMedicationProcessors.add(medicationProcessor);
                        }
                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Drugs")) {}
                    else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsSig")) {
                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                        medicationProcessor.setConceptAnswer(value.substring(value.indexOf("$") + 1, (value.indexOf("@"))));
                        medicationProcessor.setquantity(value.substring(value.indexOf("#") + 1, (value.indexOf("$"))));
                        //medicationProcessor.setDailyDose(value.substring(value.indexOf("@") + 1, (value.indexOf(","))));
                        medicationProcessor.setDays(value.substring(value.indexOf(",") + 1,  (value.length()- 1)));
                        encounterProcessor.setDuration(value.substring(value.indexOf(",") + 1,(value.length()- 1)));                       listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Sig")) {
                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                        medicationProcessor.setConceptAnswer(value.substring(value.indexOf("$") + 1, (value.indexOf("@"))));
                        medicationProcessor.setquantity(value.substring(value.indexOf("#") + 1, (value.indexOf("$"))));
                        //medicationProcessor.setDailyDose(value.substring(value.indexOf("@") + 1, (value.indexOf(","))));
                        medicationProcessor.setDays(value.substring(value.indexOf(",") + 1,  (value.length()- 1)));
                        encounterProcessor.setDuration(value.substring(value.indexOf(",") + 1,(value.length()- 1)));                    listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("AMOUNT")) {
                        RequestedFirstPass = false;
                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                        medicationProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                        listMedicationProcessors.add(medicationProcessor);
                    }

                    else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("WAIVER")) {
                        RequestedFirstPass = false;
                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                        medicationProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                        listMedicationProcessors.add(medicationProcessor);
                    }
                }
                else {
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("DailyDose")) {
                        if(value.substring(value.indexOf("@") + 1, (value.length() - 1)).length()>0){
                            medicationProcessor =listMedicationProcessors.get(1);
                            medicationProcessor.setDailyDose(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                            listMedicationProcessors.set(1, medicationProcessor);
                        }
                    }
                    else
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Days")) {
                        if(listMedicationProcessors.size()>1){
                            medicationProcessor =listMedicationProcessors.get(1);
                            medicationProcessor.setDays(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                            listMedicationProcessors.set(1, medicationProcessor);

                        }

                    }
                    else  if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Quantity")) {
                        if(listMedicationProcessors.size()>1){
                            medicationProcessor =listMedicationProcessors.get(1);
                            if(value.substring(value.indexOf("@") + 1, (value.length() - 1)).length()>0){
                                medicationProcessor.setquantity(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                            }
                            listMedicationProcessors.set(1, medicationProcessor);
                        }
                    }
                    else
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("DailyDose1")) {
                        if(aded) {
                            if(listMedicationProcessors.size()>1){
                                medicationProcessor =listMedicationProcessors.get(1);
                                medicationProcessor.setDailyDose(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                                listMedicationProcessors.set(1, medicationProcessor);
                            }
                        }
                    }
                    else
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Days1")) {
                        if(aded) {
                            if(listMedicationProcessors.size()>1){
                                medicationProcessor =listMedicationProcessors.get(1);
                                medicationProcessor.setDays(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                                listMedicationProcessors.set(1, medicationProcessor);
                            }
                        }

                    }
                    else  if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Quantity")) {
                       dQuantity = value.substring(value.indexOf("@") + 1, (value.length() - 1));
                        if(aded) {

                            if(listMedicationProcessors.size()>1){
                                medicationProcessor =listMedicationProcessors.get(1);
                                medicationProcessor.setquantity(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                                listMedicationProcessors.set(1, medicationProcessor);

                            }}

                    }
                    else

                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("rno")) {
                        receiptNo=  value.substring(value.indexOf("@") + 1, (value.length() - 1));
                    }
                    else  if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("wno")) {
                        waiverNo=  value.substring(value.indexOf("@") + 1, (value.length() - 1));
                    }
                    else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Prescriber")) {
                        encounterProcessor.setPrescriber(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    }
                }
                if (addToBigList) {
                    bigListMedicationProcessors.add(listMedicationProcessors);
                    addToBigList = false;
                }
            }
            PharmacyEncounter pharmacyEncounter = new PharmacyEncounter();
            pharmacyEncounter.setFormName(encounterProcessor.getForm());
            try {
                encDate = new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getEncounterDate());
                endDate = new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getNextVisitDate());
                pharmacyEncounter.setDateTime(encDate);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            pharmacyEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncounter.setNextVisitDate(endDate);
            pharmacyEncounter.setDuration(Integer.parseInt(encounterProcessor.getDuration()));
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));

            service.savePharmacyEncounter(pharmacyEncounter);
            for (int y = 0; y < listObsProcessor.size(); y++) {
                ppharmacyObs = new PharmacyObs(); //
                ppharmacyObs.setConcept(listObsProcessor.get(y).getConcept());
                if (!encounterProcessor.getPrescriber().equals("null")) {
                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(encounterProcessor.getPrescriber()).getUuid());
                }
                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                ppharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                ppharmacyObs.setValueDatetime(null);
                ppharmacyObs.setValueNumeric(Double.parseDouble(listObsProcessor.get(y).getConceptAnswer()));
                ppharmacyObs.setValueText(null);
                ppharmacyObs.setValueCodedName(null);
                ppharmacyObs.setValueGroupId(null);
                ppharmacyObs.setValueModifier(null);
                ppharmacyObs.setValueText(null);
                ppharmacyObs.setValueBoolen(false);
                ppharmacyObs.setComment(null);
                ppharmacyObs.setDateStarted(null);
                ppharmacyObs.setDateStopped(null);
                listPharmacyObs.add(ppharmacyObs);
            }
            service.savePharmacyObs(listPharmacyObs);
            Iterator<ArrayList<MedicationProcessor>> v = bigListMedicationProcessors.iterator();
            numbersInventtory= new int[bigListMedicationProcessors.size()][2];
            int position=0;
            while (v.hasNext()) {
                ArrayList<MedicationProcessor> c = v.next();
                DrugExtra drugExtra = new DrugExtra();
                drugExtra.setOption(null);
                drugExtra.setChosenValue(null);
                drugExtra.setReceipt(CheckIfIntNull(receiptNo));
                drugExtra.setAmount(CheckIfDoubleNull(c.get(3).getConceptAnswer()));

                drugExtra.setAmountw(CheckIfDoubleNull(c.get(4).getConceptAnswer()));
                drugExtra.setDrugChange(null);
                drugExtra.setInvoice(null);
                drugExtra.setPill(0);

                Drug drugTwo=c.get(0).getDrug();
                drugExtra.setDrug(drugTwo);
                drugExtra.setPharmacy_drug_uuid(Context.getConceptService().getDrug(drugTwo.getDrugId()).getUuid());

                //log.info("new druuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuug uuid is ++++++++++++++"+Context.getConceptService().getDrug(drugTwo.getDrugId()).getUuid() + "plus ");
                drugExtra.setWaiverNo(CheckIfIntNull(waiverNo));
                int pId= Integer.parseInt(encounterProcessor.getPatientId());
                drugExtra.setPatientId(pId);
                //drugExtra.setQuantitysold(CheckIfIntNull(c.get(1).getNumberPerPack()));
                drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                listPharmacyDrugOrderExtra.add(drugExtra);
                PharmacyOrders pharmacyOrders = new PharmacyOrders();
                pharmacyOrders.setAutoEndDate(null);
                pharmacyOrders.setConcept(CheckIfStringNull(c.get(0).getConcept()));
                pharmacyOrders.setDiscontinued(false);
                pharmacyOrders.setDiscontinuedDate(null);
                pharmacyOrders.setDispensed(true);
                pharmacyOrders.setInstructions(null);
                pharmacyOrders.setStartDate(encDate);
                pharmacyOrders.setPharmacyEncounter(pharmacyEncounter);
                listPharmacyOrders.add(pharmacyOrders);
                PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                pharmacyDrugOrder.setDose("0");
                pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(c.get(0).getDrug()).getInventoryId());
                //pharmacyDrugOrder.setEquivalentDailyDose(CheckIfIntNull(c.get(1).getDailyDose()));
                pharmacyDrugOrder.setFrequency(CheckIfStringNull(c.get(1).getConceptAnswer()));
                pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                pharmacyDrugOrder.setFormName(encounterProcessor.getForm());
                pharmacyDrugOrder.setQuantityGiven(CheckIfIntNull(c.get(1).getQuantity()));
                pharmacyDrugOrder.setUnits(null);
                numbersInventtory[position][0]= c.get(0).getDrug().getDrugId();
                numbersInventtory[position][1]= CheckIfIntNull(c.get(1).getQuantity());
                listPharmacyDrugOrders.add(pharmacyDrugOrder);
                listAnotherPharmacyObs.add(createPharmacyObs(c.get(1).getConcept(), c.get(1).getConceptAnswer(), "", c.get(0).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugIndication"));

                if(c.get(0).getConceptAnswer().contains(">"))
                    listAnotherPharmacyObs.add(createPharmacyObs(c.get(0).getConcept(), c.get(0).getConceptAnswer().substring(c.get(0).getConceptAnswer().indexOf(">")+1), "", c.get(0).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, pharmacyOrders,null));
                else
                    listAnotherPharmacyObs.add(createPharmacyObs(c.get(0).getConcept(), c.get(0).getConceptAnswer(), "", c.get(0).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, pharmacyOrders,null));


                listAnotherPharmacyObs.add(createPharmacyObs(c.get(3).getConcept(), c.get(3).getConceptAnswer(), "", c.get(0).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugFrequency"));

//
                position++;
            }


            service.saveDrugExtra(listPharmacyDrugOrderExtra);

            service.savePharmacyOrders(listPharmacyOrders);

            savedOrders=service.savePharmacyDrugOrders(listPharmacyDrugOrders);

            savedObs= service.savePharmacyObs(listAnotherPharmacyObs);

            if( savedObs && savedOrders){

                for(int y=0;y<numbersInventtory.length;y++){

                    substractFromInventory(numbersInventtory[y][0],numbersInventtory[y][1],locationVal);
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
                value += entry.getValue() + "@";
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value;

    }

    public DrugExtra createDrug(String val1, String val) {
        DrugExtra drugExtra = new DrugExtra();
        drugExtra.setOption(service.getPharmacyGeneralVariablesByName(val1));
        drugExtra.setReceipt(Integer.parseInt("0"));
        drugExtra.setAmount(Double.parseDouble("0"));
        drugExtra.setPill(Integer.parseInt("0"));
        drugExtra.setAmountw(Double.parseDouble("0"));
        drugExtra.setDrugChange("");
        drugExtra.setInvoice(Integer.parseInt("0"));
        drugExtra.setPill(Integer.parseInt("0"));
        drugExtra.setChosenValue(val);
        drugExtra.setWaiverNo(Integer.parseInt("0"));
        return drugExtra;
    }


    public PharmacyObs createPharmacyObs(String concept, String valueCoded, String valueNumeric, String valueDrug, String prescriber, String locationVal, String patientID, PharmacyEncounter pharmacyEncounter, PharmacyOrders pharmacyOrders,String com) {
        PharmacyObs pharmacyObs = new PharmacyObs();
        pharmacyObs.setConcept(CheckIfStringNull(concept));
        pharmacyObs.setDateStarted(null);
        pharmacyObs.setDateStopped(null);
        pharmacyObs.setComment(com);
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
    public int CheckIfIntNull(String data) {
        int object;
        if (data.length() == 0) {
            object = 0;

        } else {

            object = Integer.parseInt(data);

        }
        return object;

    }

    public String CheckIfStringNull(String data) {
        String object;
        if (data.length() == 0)
            object = null;
        else {

            object = data;

        }
        return object;

    }

    public Double CheckIfDoubleNull(String data) {
        Double object;
        if (data.length() == 0)
            object = 0.0;
        else {
            object = Double.parseDouble(data);
        }
        return object;

    }

    public boolean  substractFromInventory(int drugId,int Qnty,String val){


        List<DrugDispenseSettings> list = service.getDrugDispenseSettings();

        int size = list.size();
        for (int i = 0; i < size; i++) {

            if(list.get(i).getLocation().getName().equalsIgnoreCase(val)){
//                PharmacyStore pharmacyStore=   service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(""+drugId)).getInventoryId();
                PharmacyStore pharmacyStore=   list.get(i).getInventoryId();


                if(pharmacyStore!=null ){

                    if(pharmacyStore.getDrugs().getDrugId()==drugId && pharmacyStore.getQuantity() > Qnty ){

                        pharmacyStore.setQuantity( (pharmacyStore.getQuantity()-Qnty));

                        service.savePharmacyInventoryItem(pharmacyStore);


                    }
                }
            }
        }

        return true;
    }

}
