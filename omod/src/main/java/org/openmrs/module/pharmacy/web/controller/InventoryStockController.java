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
public class InventoryStockController {
    private static final Log log = LogFactory.getLog(InventoryStockController.class);
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
    private boolean addMiddle;
    private PharmacyDrugOrder pharmacyDrugOrder;
    private List<PharmacyStore> drugsInInventory;
    private Integer quantity;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/stockInventory")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        String locationVal = null;
        String drugID = request.getParameter("jsonDrugObject");
        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }
        int drugsQuantityInStore=0;
        jsonObject2 = new JSONObject(drugID); // this parses the jsonObject
        iterator2 = jsonObject2.keys(); //gets all the keys
        while (iterator2.hasNext()) {
            drugsQuantityInStore=0;
            String key2 = iterator2.next().toString(); // get key
            drugsInInventory=service.getPharmacyInventory();
            int drugsInInventorySize=drugsInInventory.size();
            for(int inInventory=0; inInventory<drugsInInventorySize; inInventory++){
                if(drugsInInventory.get(inInventory).getLocation().equals(service.getPharmacyLocationsByName(locationVal).getUuid())){
                    if(drugsInInventory.get(inInventory).getDrugs().getUuid().equals(Context.getConceptService().getDrug(Integer.parseInt(key2)).getUuid())){
                        drugsQuantityInStore=drugsQuantityInStore+drugsInInventory.get(inInventory).getQuantity();
                    }
                }
            }
            //log.info("drug ID is +++++++++++++++++++++++++++++++++++++++++++++"+Context.getConceptService().getDrug(Integer.parseInt(key2)).getDrugId()+"quantity is "+drugsQuantityInStore);
        }

        response.getWriter().print("" + drugsQuantityInStore);

    }


}
