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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class TuberculosisController {
    private static final Log log = LogFactory.getLog(TuberculosisController.class);
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
    private List<PharmacyDrugOrder> listPharmacyDrugOrder;
    private Date encDate;
    private PharmacyObs ppharmacyObs;
    private Date endDate;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private List<DrugExtra> listPharmacyDrugOrderExtra2;
    private boolean addToBigList = false;
    private boolean RequestedFirstPass = false;
    private DrugExtra drugExtra;
    private boolean DispensedFirstPass = false;
    private boolean savedOrders = false, savedObs = false;
    private int numbersInventtory[][];
    private PharmacyDrugOrder drugOrder;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/tuberculosisProcessor")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/tuberculosisProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();
        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        String regimenCode = request.getParameter("regimenCode");
        String regimenName = request.getParameter("regimenName");
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
        List<ObsProcessor> listObsProcessor = new ArrayList<ObsProcessor>();
        List<NonObsProcessor> listNonObsProcessor = new ArrayList<NonObsProcessor>();
        List<MedicationProcessor> listMedicationProcessors = new ArrayList<MedicationProcessor>();
        List<ArrayList<MedicationProcessor>> bigListMedicationProcessors = new ArrayList<ArrayList<MedicationProcessor>>();
        List<PharmacyObs> listPharmacyObs = new ArrayList<PharmacyObs>();
        List<DrugExtra> listPharmacyDrugOrderExtra = new ArrayList<DrugExtra>();
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        listAnotherPharmacyObs = new ArrayList<PharmacyObs>();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;
            int medicationCount = 0,
                    doseCount = 0 ,
                    pillCount = 0 ,
                    dispensedCount = 0;

            for (int i = 0; i < array.size(); i++) {
                String value = ArrayDataOne(array.get(i).toString());
                log.info("\n" + value) ;
                if (value.contains("*")) {
                    if (value.substring(0, value.indexOf("*")).equalsIgnoreCase("Enc2")) {
                        encounterProcessor.setPatientId(value.substring(value.indexOf("@") + 1, value.length() - 1));
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
                        if ((value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|") + 1)).length() != 0) {
                            obsProcessor.setConcept(value.substring(value.indexOf("|") + 1, (value.indexOf("#"))));
                            obsProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|") + 1));

                            listObsProcessor.add(obsProcessor);
                        }
                    }

                } else {
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("medication")) {
                        if (value.indexOf("|") > 0) {
                            medicationCount += 1;

                            String drugValue = value.substring(value.indexOf("|") + 1, ((value.length() - 1)));
                            String drugConcept = value.substring(value.indexOf("@") + 1, value.indexOf("|"));
                            Integer drugId = Integer.valueOf(drugValue);
                            Drug drug = new Drug(drugId);

                            medicationProcessor = new MedicationProcessor();
                            medicationProcessor.setConcept(drugConcept);
                            medicationProcessor.setDrug(drug);

                            log.info("Adding medical processor [" + i + "]" + medicationProcessor);
                            listMedicationProcessors.add(medicationProcessor);
                            //listMedicationProcessors.add(medicationProcessor);
                        }
                    }
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("dose")) {
                        doseCount = doseCount + 1;
                        if (doseCount == medicationCount) {
                            medicationProcessor.setDose(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                            //listMedicationProcessors.add(medicationProcessor);
                        }

                    }

                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("PillCount")) {
                        pillCount = pillCount + 1;
                        if (pillCount == medicationCount) {
                            medicationProcessor.setPillcount(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                            //listMedicationProcessors.add(medicationProcessor);
                        }
                    }
                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("otherd")) {
                        dispensedCount = dispensedCount + 1;
                        if (dispensedCount == medicationCount) {
                            if (value.substring(value.indexOf("@") + 1, (value.length() - 1)).length() > 0) {
                                medicationProcessor.setDispensed(value.substring(value.indexOf("@") + 1, (value.length() - 1)));
                                //listMedicationProcessors.add(medicationProcessor);
                            }
                        }
                    }

                    if (value.substring(0, value.indexOf("@")).equalsIgnoreCase("Prescriber")) {
                        encounterProcessor.setPrescriber(value.substring(value.indexOf("@") + 1, value.length() - 1));

                    }
                    /* else
                    {
                        medicationProcessor = new MedicationProcessor();
                        listMedicationProcessors.add(medicationProcessor);
                    } */

                    log.info("Adding medication processor[" + i + "]: " + medicationProcessor.toString());


                }


            }

            log.info("Number of medication instances: " + listMedicationProcessors.size());

            PharmacyEncounter pharmacyEncounter = new PharmacyEncounter();
            pharmacyEncounter.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor.getEncounterType()));
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
            pharmacyEncounter.setRegimenCode(regimenCode);
            pharmacyEncounter.setRegimenName(regimenName);
            service.savePharmacyEncounter(pharmacyEncounter);
            for (int y = 0; y < listObsProcessor.size(); y++) {
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
                listPharmacyObs.add(ppharmacyObs);
            }
            service.savePharmacyObs(listPharmacyObs);
            Iterator<ArrayList<MedicationProcessor>> v = bigListMedicationProcessors.iterator();
            numbersInventtory = new int[bigListMedicationProcessors.size()][2];
            int position = 0;

            log.info(">> Number of medication instances: " + listMedicationProcessors.size());


            Iterator<MedicationProcessor> medicationInstance = listMedicationProcessors.iterator();
             /*while (medicationInstance.hasNext()) {
                //log.info("medication is ++++++++++++++++++++++++++++++++++" + medicationInstance.next().getConcept());
            }
            while(medicationInstance0.hasNext())
            {
                MedicationProcessor medicationInstance=medicationInstance0.next();
                log.info("dispensed is ++++++++++++++++++++++++++++++++++++++++++++"+listMedicationProcessors.get(medicationInstance).getDrug().getDrugId());
                PharmacyOrders pharmacyOrders = new PharmacyOrders();
                pharmacyOrders.setAutoEndDate(null);
                pharmacyOrders.setConcept(CheckIfStringNull(listMedicationProcessors.get(medicationInstance).getConcept()));
                pharmacyOrders.setDiscontinued(false);
                pharmacyOrders.setDiscontinuedDate(null);
                pharmacyOrders.setDispensed(true);
                pharmacyOrders.setInstructions(null);
                pharmacyOrders.setStartDate(encDate);
                pharmacyOrders.setPharmacyEncounter(pharmacyEncounter);
                listPharmacyOrders.add(pharmacyOrders);
                PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                //pharmacyDrugOrder.setDose(CheckIfDoubleNull(c.get(1).getDose()));
                pharmacyDrugOrder.setDrugUuid(drugExtra);
                pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(listMedicationProcessors.get(medicationInstance).getDrug()).getInventoryId());
                pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                pharmacyDrugOrder.setEquivalentDailyDose(0);
                pharmacyDrugOrder.setFormName(encounterProcessor.getForm());
                //pharmacyDrugOrder.setFrequency(CheckIfStringNull(c.get(1).getFrequency()));
                pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
               // pharmacyDrugOrder.setQuantityPrescribed(CheckIfIntNull(c.get(2).getQuantity()));
                pharmacyDrugOrder.setQuantityGiven(CheckIfIntNull(listMedicationProcessors.get(medicationInstance).getDispensed()));
                //pharmacyDrugOrder.setUnits(CheckIfStringNull(c.get(1).getUnits()));
                String date_s = "2000-01-18 00:00:00.0";
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date1 = null;
                try {
                    date1 = dt.parse(date_s);
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                String pharmacyDrugOrderUUId=null;
                listPharmacyDrugOrder=service.getPharmacyDrugOrders();
                if(listPharmacyDrugOrder !=null ){
                    for(PharmacyDrugOrder pharmacyDrugOrder2:listPharmacyDrugOrder ){
                        if(pharmacyDrugOrder2.getPerson().getPatientId().toString().equals(encounterProcessor.getPatientId())){
                            Date encDate= null;
                            try {
                                encDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(pharmacyDrugOrder2.getDateCreated().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            if(date1.before(encDate)){
                                pharmacyDrugOrderUUId=pharmacyDrugOrder2.getUuid();
                            }
                        }

                    }
                }
                drugOrder= service.getPharmacyDrugOrdersByUuid(pharmacyDrugOrderUUId);
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
                int no_of_days_to_last= CheckIfIntNull(listMedicationProcessors.get(medicationInstance).getDispensed())/2+(int) DiffInDays;
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
                numbersInventtory[position][0]= listMedicationProcessors.get(medicationInstance).getDrug().getDrugId();
                numbersInventtory[position][1]= CheckIfIntNull(listMedicationProcessors.get(medicationInstance).getDispensed());
                listPharmacyDrugOrders.add(pharmacyDrugOrder);//
                listAnotherPharmacyObs.add(createPharmacyObs("1896",listMedicationProcessors.get(medicationInstance).getFrequency(),"",listMedicationProcessors.get(medicationInstance).getDrug().getDrugId().toString(),encounterProcessor.getPrescriber(),locationVal,encounterProcessor.getPatientId(),pharmacyEncounter,null));

                listAnotherPharmacyObs.add(createPharmacyObs(listMedicationProcessors.get(medicationInstance).getConcept(),listMedicationProcessors.get(medicationInstance).getConceptAnswer(),"",listMedicationProcessors.get(medicationInstance).getDrug().getDrugId().toString(),encounterProcessor.getPrescriber(),locationVal,encounterProcessor.getPatientId(),pharmacyEncounter,pharmacyOrders));
                position++;
//
            } */
            //service.saveDrugExtra(listPharmacyDrugOrderExtra);
            // service.savePharmacyOrders(listPharmacyOrders);
            //savedOrders=service.savePharmacyDrugOrders(listPharmacyDrugOrders);
            // savedObs= service.savePharmacyObs(listAnotherPharmacyObs);
            if (savedObs && savedOrders) {
                for (int y = 0; y < numbersInventtory.length; y++) {
                    substractFromInventory(numbersInventtory[y][0], numbersInventtory[y][1], locationVal);
                }
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // JSONArray array = (JSONArray) obj;
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

    public PharmacyObs createPharmacyObs(String concept, String valueCoded, String valueNumeric, String valueDrug, String prescriber, String locationVal, String patientID, PharmacyEncounter pharmacyEncounter, PharmacyOrders pharmacyOrders) {
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

    public int CheckIfIntNull(String data) {
        int object;
        if (data == null) {
            object = 0;

        } else {
            if (data.length() > 0) {
                object = Integer.parseInt(data);

            } else
                object = 0;


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

    public boolean substractFromInventory(int drugId, int Qnty, String val) {
        List<DrugDispenseSettings> list = service.getDrugDispenseSettings();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).getLocation().getName().equalsIgnoreCase(val)) {
                PharmacyStore pharmacyStore = list.get(i).getInventoryId();
                if (pharmacyStore != null) {
                    if (pharmacyStore.getDrugs().getDrugId() == drugId && pharmacyStore.getQuantity() > Qnty) {
                        pharmacyStore.setQuantity((pharmacyStore.getQuantity() - Qnty));
                        service.savePharmacyInventory(pharmacyStore);

                    }
                }
            }
        }

        return true;
    }


}
