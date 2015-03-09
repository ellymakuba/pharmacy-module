package org.openmrs.module.pharmacy.web.controller;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.EncounterService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class CheckDrugAvailabillity{
    private static final Log log = LogFactory.getLog(CheckDrugAvailabillity.class);
    private JSONArray drugStrengthA;
    public PharmacyService service;
    public EncounterService patientService;
    private boolean found = false;
    private JSONArray drugNamess;
    private String formulation;
    private List<PharmacyEncounter> list = null;
    private int size = 0;
    private UserService usersService;
    private List<PharmacyOrders> listOrders;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private int psize, sizeList;
    private List<PharmacyOrders> listDrugs;
    private int listDrugsSize;
    private ContainerFactory containerFactory;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int sizePharmacyLocationUsers;
    private List<PharmacyObs> listObs;
    private List<PharmacyObs> listObsConcepts;
    private  List<PharmacyOrders> listPharmacyOrders   ;
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private   List<DrugExtra> listPharmacyDrugExtra    ;
    private int sizeRegimen;
    private JSONObject jsonObject, jsonObject1,jsonObject2;
    private JSONArray jsonArray, jsonArray1, jsonArray2;
    private PharmacyEncounter pharmacyEncounterByUuid;
    private Iterator iterator,iterator2;
    private Person person;
    private List<User> userList;
    private PharmacyTransactionTypes pharmacyTransactionTypes;
    private int size1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/checkDrugAvailability")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        String drugID = request.getParameter("drugCheck");
        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        userService = Context.getUserContext();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        listPharmacyDrugOrder=service.getPharmacyDrugOrders();
        try {
            jsonObject1 = new JSONObject(drugID); // this parses the jsonObject
            iterator = jsonObject1.keys(); //gets all the keys
            boolean booleanCheck =false;
            while (iterator.hasNext()) {
                String key = iterator.next().toString(); // get key
                Object jsonObj = jsonObject1.get(key); // get value
                System.out.println("jsonObj++++++++++++++++++++++++++++++++++++++++++++++++++"+jsonObj);
                Integer quantityDispensed= Integer.valueOf(jsonObj.toString());
                PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
                DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(Context.getConceptService().getDrug(Integer.parseInt(key)).getDrugId(),pharmacyLocations.getUuid());
                if(drugDispenseSettings==null){
                    booleanCheck = false;
                    break;
                }
                else{
                    PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                    if(pharmacyStore.getQuantity() < quantityDispensed){
                        booleanCheck = false;
                        System.out.println("pharmacyStore quantity++++++++++++++++++++++++++++++++++++++++++++++++++"+pharmacyStore.getQuantity()+" quantityDispensed++ "+quantityDispensed);
                        break;
                    }
                    else{
                        booleanCheck = true;
                    }

                }

            }
            response.getWriter().print("" + booleanCheck);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error generated"+e.getLocalizedMessage());
        }
    }
}
