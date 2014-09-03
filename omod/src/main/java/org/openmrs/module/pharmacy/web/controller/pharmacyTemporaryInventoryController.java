package org.openmrs.module.pharmacy.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.itextpdf.text.DocumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
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
public class pharmacyTemporaryInventoryController {
    private static final Log log = LogFactory.getLog(dispenseController.class);
    public PharmacyService service;
    private ContainerFactory containerFactory;
    private UserContext userService;
    private JSONArray jsonArray;
    private PharmacyTemporaryInventory pharmacyTemporaryInventory;


    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/addTemporaryInventory")
    public synchronized void addTemporaryInventory(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        String jsonText = request.getParameter("values");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new org.json.simple.JSONArray();

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        List<PharmacyObs> listPharmacyObs= new ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }

        try {
            Object object=parser.parse(jsonText);
            JSONArray temporaryInventoryObject=(JSONArray)object;
            int temporaryInventoryObjectSize=temporaryInventoryObject.size();
            for(int i=0; i<temporaryInventoryObjectSize; i++){
                PharmacyTemporaryInventory pharmacyTemporaryInventory=new PharmacyTemporaryInventory();
                JSONArray rowInstance=(JSONArray)temporaryInventoryObject.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("drug")){
                     pharmacyTemporaryInventory.setDrugName(value);
                    }
                    if(key.equalsIgnoreCase("dosage")){
                        pharmacyTemporaryInventory.setDose(value);
                    }
                    if(key.equalsIgnoreCase("unitPrice")){
                        pharmacyTemporaryInventory.setUnitPrice(Double.valueOf(value));
                    }
                    if(pharmacyTemporaryInventory.getDrugName() !=null){
                        service.saveTemporaryInventory(pharmacyTemporaryInventory);
                    }
                }
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
