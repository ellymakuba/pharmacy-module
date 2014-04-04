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
public class PsychiatryProcessorController {
    private static final Log log = LogFactory.getLog(PsychiatryProcessorController.class);
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
    private boolean savedOrders=false,savedObs=false;
    private     int numbersInventtory[][];
    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/psychiatryProcessor")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/psychiatryProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();


        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        NonObsProcessor nonObsProcessor = new NonObsProcessor();
        MedicationProcessor medicationProcessor = new MedicationProcessor();

        //
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


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsNo")) {

                        nonObsProcessor = new NonObsProcessor();
                        nonObsProcessor.setConcept(value.substring(value.indexOf("*") + 1, value.indexOf("|")));
                        nonObsProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("|") + 1));

                        listNonObsProcessor.add(nonObsProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Obs")) {

                        if(value.substring(value.indexOf("@") + 1, value.length() - 1).trim().length()>0){


            obsProcessor = new ObsProcessor();


                        obsProcessor.setConcept(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        obsProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listObsProcessor.add(obsProcessor);

                        }

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDrug")) {

                        listMedicationProcessors = new ArrayList<MedicationProcessor>();

                        addToBigList = true;


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setConcept(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDrugChange")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setChange(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("|") + 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDrugDose")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setDose(value.substring(value.indexOf("|") + 1, value.indexOf("#")));

                         /*our changes*/
                        Integer drugId = Integer.valueOf(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("#") + 1));
                        Drug drug = new Drug(drugId);

                        medicationProcessor.setDrug(drug);
                        //medicationProcessor.setDrugId(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("#") + 1));
                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsRoute")) {


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setRoute(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setRouteAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("|") + 1));

                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsFreq")) {

                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setFrequency(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setFrequencyAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("|") + 1));
                        listMedicationProcessors.add(medicationProcessor);
                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsFreqOther")) {


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setFrequencyCodedAnswer(value.substring(value.indexOf("@") + 1, value.length() - 1));
                        listMedicationProcessors.add(medicationProcessor);
                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsInd")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setIndicationId(value.substring(value.indexOf("#") + 1, value.indexOf("@")));
                        medicationProcessor.setIndicationAnswerId(value.substring(value.indexOf("@") + 1, value.length() - 1).substring(value.substring(value.indexOf("@") + 1, value.length() - 1).indexOf("|") + 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsIndiCoded")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setIndicationCodedAnswerId(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDuration1")) {

                        medicationProcessor = new MedicationProcessor();


                        medicationProcessor.setDurationId(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setDurationAnswerId(value.substring(value.indexOf("#") + 1, value.indexOf("@")));
                        medicationProcessor.setDurationCodedAnswerId(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDuration2")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setDurationId(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setDurationAnswerId(value.substring(value.indexOf("#") + 1, value.indexOf("@")));
                        medicationProcessor.setDurationCodedAnswerId(value.substring(value.indexOf("@") + 1, value.length() - 1));


                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("ObsDuration3")) {
                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setDurationId(value.substring(value.indexOf("|") + 1, value.indexOf("#")));
                        medicationProcessor.setDurationAnswerId(value.substring(value.indexOf("#") + 1, value.indexOf("@")));
                        medicationProcessor.setDurationCodedAnswerId(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    }


                } else {
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("QD")) {


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setquantity(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("PD")) {


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setPillcount(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("AM")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setAmount(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("RN")) {

                        medicationProcessor = new MedicationProcessor();
                        medicationProcessor.setReceiptNo(value.substring(value.indexOf("@") + 1, value.length() - 1));


                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("WN")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setWaiverNo(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("IN")) {

                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setInvoiceNumber(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);

                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("WA")) {


                        medicationProcessor = new MedicationProcessor();

                        medicationProcessor.setWaiverAmount(value.substring(value.indexOf("@") + 1, value.length() - 1));

                        listMedicationProcessors.add(medicationProcessor);


                    } else if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("prescriber")) {


                        encounterProcessor.setPrescriber(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    }


                }

                if (addToBigList) {
                    bigListMedicationProcessors.add(listMedicationProcessors);
                    addToBigList = false;
                }
            }


            PharmacyEncounter pharmacyEncount = new PharmacyEncounter();



           // pharmacyEncount.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor.getEncounterType()));
            pharmacyEncount.setFormName(encounterProcessor.getForm());
            try {

                encDate = new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getEncounterDate());

                endDate = new SimpleDateFormat("MM/dd/yyyy").parse(encounterProcessor.getNextVisitDate());
                pharmacyEncount.setDateTime(encDate);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            pharmacyEncount.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncount.setNextVisitDate(endDate);
//            pharmacyEncount.setEncounter();
            pharmacyEncount.setDuration(Integer.parseInt(encounterProcessor.getDuration()));

            pharmacyEncount.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));

            PharmacyEncounter  pharmacyEncounter=null;
            if(pharmacyEncount!=null)  {
             pharmacyEncounter= service.savePharmacyEncounter(pharmacyEncount);
            }

           if(pharmacyEncounter!=null)  {

            for (int y = 0; y < listObsProcessor.size(); y++) {

                ppharmacyObs = new PharmacyObs();

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
            listPharmacyDrugOrderExtra2 = new ArrayList<DrugExtra>();
            if (listNonObsProcessor.get(0).getConceptAnswer().equalsIgnoreCase("1065"))
            {
                if ((listNonObsProcessor.get(0).getConceptAnswer() != null)) {
                    listPharmacyDrugOrderExtra2.add(createDrug(listNonObsProcessor.get(0).getConcept(), listNonObsProcessor.get(0).getConceptAnswer()));
                }
                if ((listNonObsProcessor.get(1).getConceptAnswer() != null)) {

                    listPharmacyDrugOrderExtra2.add(createDrug(listNonObsProcessor.get(1).getConcept(), listNonObsProcessor.get(1).getConceptAnswer()));
                }
                if ((listNonObsProcessor.get(2).getConceptAnswer() != null)) {

                    listPharmacyDrugOrderExtra2.add(createDrug(listNonObsProcessor.get(2).getConcept(), listNonObsProcessor.get(2).getConceptAnswer()));
                }
                service.saveDrugExtra(listPharmacyDrugOrderExtra2);

            } else {


                if ((listNonObsProcessor.get(0).getConceptAnswer() != null)) {

                    DrugExtra drugExtra = new DrugExtra();

                    drugExtra.setOption(service.getPharmacyGeneralVariablesByName(listNonObsProcessor.get(0).getConcept()));


                    drugExtra.setReceipt(Integer.parseInt("0"));
                    drugExtra.setAmount(Double.parseDouble("0"));


                    drugExtra.setPill(Integer.parseInt("0"));
                    drugExtra.setAmountw(Double.parseDouble("0"));
                    drugExtra.setDrugChange("");
                    drugExtra.setInvoice(Integer.parseInt("0"));
                    drugExtra.setPill(Integer.parseInt("0"));
                    drugExtra.setChosenValue(listNonObsProcessor.get(0).getConceptAnswer());
                    drugExtra.setWaiverNo(Integer.parseInt("0"));


                    listPharmacyDrugOrderExtra2.add(drugExtra);
                }
                service.saveDrugExtra(listPharmacyDrugOrderExtra2);

            }


            Iterator<ArrayList<MedicationProcessor>> v = bigListMedicationProcessors.iterator();

            numbersInventtory= new int[bigListMedicationProcessors.size()][2];

            int position=0;
            while (v.hasNext()) {
                ArrayList<MedicationProcessor> c = v.next();


                DrugExtra drugExtra = new DrugExtra();
                drugExtra.setOption(null);
                drugExtra.setChosenValue(null);

                drugExtra.setReceipt(CheckIfIntNull(c.get(14).getReceiptNo()));
                drugExtra.setAmount(CheckIfDoubleNull(c.get(13).getAmount()));

                drugExtra.setAmountw(CheckIfDoubleNull(c.get(17).getWaiverAmount()));
                drugExtra.setDrugChange(CheckIfStringNull(c.get(1).getChange()));
                drugExtra.setInvoice((CheckIfIntNull(c.get(16).getInvoiceNumber())));
                drugExtra.setPill(CheckIfIntNull(c.get(12).getPillcount()));

                drugExtra.setWaiverNo(CheckIfIntNull(c.get(15).getWaiverNo()));

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
                pharmacyDrugOrder.setDose(CheckIfDoubleNull(c.get(2).getDose()));
                pharmacyDrugOrder.setDrugUuid(drugExtra);
                pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(c.get(0).getDrug()).getInventoryId());
                pharmacyDrugOrder.setEquivalentDailyDose(CheckIfDoubleNull(c.get(4).getFrequencyAnswer()));
                pharmacyDrugOrder.setFrequency(CheckIfStringNull(c.get(4).getFrequency()));
                pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                pharmacyDrugOrder.setQuantityGiven(CheckIfIntNull(c.get(11).getQuantity()));
                pharmacyDrugOrder.setUnits(null);
                pharmacyDrugOrder.setFormName(encounterProcessor.getForm());

                //add to the array for inventory substractions

                numbersInventtory[position][0]= c.get(2).getDrug().getDrugId();

                numbersInventtory[position][1]= CheckIfIntNull(c.get(11).getQuantity());


                listPharmacyDrugOrders.add(pharmacyDrugOrder);

                //createPharmacyObs(String concept,String valueCoded,String valueNumeric,String valueDrug,String prescriber, String locationVal,String patientID, PharmacyEncounter pharmacyEncounter, PharmacyOrders  pharmacyOrders){
                //route
                listAnotherPharmacyObs.add(createPharmacyObs(c.get(3).getRoute(), c.get(3).getRouteAnswer(), "", c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugRoute"));

                //ObsDrug
                listAnotherPharmacyObs.add(createPharmacyObs(c.get(0).getConcept(), c.get(0).getConceptAnswer(), "", c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, pharmacyOrders,null));

                //freq
                listAnotherPharmacyObs.add(createPharmacyObs(c.get(4).getFrequency(), c.get(4).getFrequencyAnswer(), c.get(5).getFrequencyCodedAnswer(),c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugFrequency"));

                //indi
                listAnotherPharmacyObs.add(createPharmacyObs(c.get(6).getIndicationId(), c.get(6).getIndicationAnswerId(), c.get(7).getIndicationCodedAnswerId(), c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugIndication"));


                if (c.get(8).getDurationCodedAnswerId() != null) {

                    if (c.get(8).getDurationCodedAnswerId().length() != 0) {
                        listAnotherPharmacyObs.add(createPharmacyObs(c.get(8).getDurationId(), c.get(8).getDurationAnswerId(), c.get(8).getDurationCodedAnswerId(),c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugDuration"));
                    }

                }


                if (c.get(9).getDurationCodedAnswerId() != null) {
                    if (c.get(9).getDurationCodedAnswerId().length() != 0) {
                        listAnotherPharmacyObs.add(createPharmacyObs(c.get(9).getDurationId(), c.get(9).getDurationAnswerId(), c.get(9).getDurationCodedAnswerId(), c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugDuration"));
                    }

                }
                if (c.get(10).getDurationCodedAnswerId() != null) {
                    if (c.get(10).getDurationCodedAnswerId().length() != 0) {
                        listAnotherPharmacyObs.add(createPharmacyObs(c.get(10).getDurationId(), c.get(10).getDurationAnswerId(), c.get(10).getDurationCodedAnswerId(), c.get(2).getDrug().getDrugId().toString(), encounterProcessor.getPrescriber(), locationVal, encounterProcessor.getPatientId(), pharmacyEncounter, null,"DrugDuration"));
                    }

                }

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

            
           }
            
            

        } catch (org.json.simple.parser.ParseException e) {
            e.getMessage();
        }
        JSONArray array = (JSONArray) obj;
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

//
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

    public synchronized Date DateHelper(String outgoingexpire) {


        Date date2 = null;
        try {
            if (outgoingexpire != null) {
                date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire.substring(0, 9));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }
        return date2;
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
                        service.savePharmacyInventory(pharmacyStore);


                    }
                }
            }
        }

        return true;
    }

}
