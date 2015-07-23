package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Controller
public class DrugDispenseProcessor {
    private static final Log log = LogFactory.getLog(DrugDispenseProcessor.class);
    private PharmacyService service;
    private ContainerFactory containerFactory;
    private Calendar currentDate;
    private Date dateC;
    private GregorianCalendar gregorianCalendar;
    private GregorianCalendar calendar;
    private String batchNo;
    private String batchName;
    private String drugName;
    private String inventoryNo;
    private Calendar readDate;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private String days;
    private String amount;
    private String dose = null;
    private String option;
    private String quantity;
    private String form;
    private String front;
    private String back;
    private String drugID;
    private List<PharmacyLocationUsers> pharmacyLocationUserses;
    private DrugDispenseSettings drugDispense;
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDispenseRequest")
    public synchronized void drugExtraFormProcessor(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        days= "2"; //inventory
        drugID=request.getParameter("drugID");
        inventoryNo=request.getParameter("inventoryNo");
        option="tablets";
        amount="1";
        quantity="1";
        form="Tablets";
        back="a";
        front="a";
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();
        dateC = new Date();
        currentDate.setTime(dateC);
        gregorianCalendar = new GregorianCalendar();
        calendar = new GregorianCalendar();
        gregorianCalendar.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
        currentDate.get(currentDate.DAY_OF_MONTH));
        System.out.println("drugID in from drug dispensesetting is++++++++++++++++++++++++++++++++++"+drugID);
        DrugDispenseSettings drugDispenseSettings1 = service.getDrugDispenseSettingsByDrugIdAndLocation(Integer.valueOf(drugID),pharmacyLocations.getUuid());
        if (drugDispenseSettings1 ==null) {
            drugDispense = new DrugDispenseSettings();
            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugID));
            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
            drugDispense.setOption(service.getPharmacyGeneralVariablesByName(option));
            drugDispense.setValue(Integer.parseInt(days));
            drugDispense.setAmount(Double.parseDouble(amount));
            drugDispense.setQuantity(Integer.parseInt(quantity));
            drugDispense.setForm(form);
            drugDispense.setBack(back);
            drugDispense.setFront(front);
            service.saveDrugDispenseSettings(drugDispense);
        } else{
            drugDispense =drugDispenseSettings1;
            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugID));
            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
            drugDispense.setOption(service.getPharmacyGeneralVariablesByName(option));
            drugDispense.setValue(Integer.parseInt(days));
            drugDispense.setAmount(Double.parseDouble(amount));
            drugDispense.setQuantity(Integer.parseInt(quantity));
            drugDispense.setForm(form);
            drugDispense.setBack(back);
            drugDispense.setFront(front);
            service.saveDrugDispenseSettings(drugDispense);

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
