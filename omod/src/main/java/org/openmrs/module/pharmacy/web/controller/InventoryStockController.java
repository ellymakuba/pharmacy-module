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
    private JSONObject jsonObject2;
    private Iterator iterator,iterator2;
    private PharmacyTransactionTypes pharmacyTransactionTypes;
    private List<PharmacyStore> drugsInInventory;
    private PharmacyStore pharmacyStore;
    private boolean allowDispense=false;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/stockInventory")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        String locationVal = null;
        String drugID =request.getParameter("jsonDrugObject");
        String select=request.getParameter("select");
        String checkBoolean=request.getParameter("checkBoolean");

        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();

        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        locationVal = request.getSession().getAttribute("location").toString();
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        int drugsQuantityInStore=0;
        jsonObject2 = new JSONObject(drugID); // this parses the jsonObject
        String myVals[]=exractKeyAndValue(drugID);
        String drugName=myVals[1];
        if(select !=null){
            if(select.equalsIgnoreCase("stock")) {
                iterator2 = jsonObject2.keys();
                while (iterator2.hasNext()) {
                    drugsQuantityInStore=0;
                    String key2 = iterator2.next().toString(); // get key
                    drugsInInventory=service.getPharmacyInventory();
                    int drugsInInventorySize=drugsInInventory.size();
                    for(int inInventory=0; inInventory<drugsInInventorySize; inInventory++){
                        if(drugsInInventory.get(inInventory).getLocation().equals(service.getPharmacyLocationsByName(locationVal).getUuid())){
                            if(drugsInInventory.get(inInventory).getDrugs().getUuid().equals(Context.getConceptService().getDrugByNameOrId(drugName).getUuid())){
                                drugsQuantityInStore=drugsQuantityInStore+drugsInInventory.get(inInventory).getQuantity();
                            }
                        }
                    }
                }
                response.getWriter().print("" + drugsQuantityInStore);
            }

            else if(select.equalsIgnoreCase("unitPrice")){
                pharmacyStore=new PharmacyStore();
                String drugUUID=Context.getConceptService().getDrugByNameOrId(drugName).getUuid();
                pharmacyStore = service.getPharmacyInventoryByDrugUuid(drugUUID,service.getPharmacyLocationsByName(locationVal).getUuid());
                response.getWriter().print("" + pharmacyStore.getUnitPrice());
            }

            else if(select.equalsIgnoreCase("drugConcept")){
                jsonObject2=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                Drug drug=Context.getConceptService().getDrugByNameOrId(drugName);
                jsonArray.put(drug.getConcept()+"|"+drug.getDrugId());
                jsonObject2.accumulate("aaData",jsonArray);
                response.getWriter().print("" + jsonObject2);
            }
        }
        else if(checkBoolean !=null){
            allowDispense=false;
            if(Context.getConceptService().getDrugByNameOrId(drugName) !=null){
                DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(Context.getConceptService().getDrugByNameOrId(drugName).getDrugId(),locationUUID);
                if(drugDispenseSettings !=null){
                    PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                    if(pharmacyStore !=null) {
                        allowDispense=true;
                    }
                }
            }
            response.getWriter().print("" + allowDispense);
        }
        else{
            iterator2 = jsonObject2.keys();
            while (iterator2.hasNext()) {
                drugsQuantityInStore=0;
                String key2 = iterator2.next().toString(); // get key
                drugsInInventory=service.getPharmacyInventory();
                int drugsInInventorySize=drugsInInventory.size();
                for(int inInventory=0; inInventory<drugsInInventorySize; inInventory++){
                    if(drugsInInventory.get(inInventory).getLocation().equals(service.getPharmacyLocationsByName(locationVal).getUuid())){
                        if(drugsInInventory.get(inInventory).getDrugs().getUuid().equals(Context.getConceptService().getDrugByNameOrId(drugName).getUuid())){
                            drugsQuantityInStore=drugsQuantityInStore+drugsInInventory.get(inInventory).getQuantity();
                        }
                    }
                }
            }
            response.getWriter().print("" + drugsQuantityInStore);
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
