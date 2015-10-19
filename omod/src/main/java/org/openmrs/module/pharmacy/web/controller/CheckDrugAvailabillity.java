package org.openmrs.module.pharmacy.web.controller;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class CheckDrugAvailabillity{
    private static final Log log = LogFactory.getLog(CheckDrugAvailabillity.class);
    public PharmacyService service;
    private ContainerFactory containerFactory;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int sizePharmacyLocationUsers;
    boolean booleanCheck =false;
    boolean drugBatchHasBeenSet=false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/checkDrugAvailability")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locationVal = null;
        String jsonTExt= request.getParameter("drugCheck");
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }

        JSONParser parser=new JSONParser();
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        try {
            Object object=parser.parse(jsonTExt);
            JSONArray jsonArrayObject=(JSONArray)object;
            for(int i=0; i<jsonArrayObject.size(); i++){
                String keyAndValue[]=exractKeyAndValue(jsonArrayObject.get(i).toString());
                Integer quantityDispensed= Integer.valueOf(keyAndValue[1]);
                DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(Context.getConceptService().getDrug(Integer.parseInt(keyAndValue[0])).getDrugId(),pharmacyLocations.getUuid());
                if(drugDispenseSettings==null){
                    booleanCheck = false;
                    break;
                }
                else{
                    PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                    if(pharmacyStore.getQuantity() < quantityDispensed){
                        booleanCheck = false;
                        break;
                    }
                    else{
                        booleanCheck = true;
                    }

                }

            }
            response.getWriter().print(booleanCheck);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/drugBatchHasBeenSet")
    public synchronized void checkIfDrugBatchIsSet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locationVal = null;
        String jsonTExt= request.getParameter("drugCheck");
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();
        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();
        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }

        JSONParser parser=new JSONParser();
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        try {
            Object object=parser.parse(jsonTExt);
            JSONArray jsonArrayObject=(JSONArray)object;
            for(int i=0; i<jsonArrayObject.size(); i++){
                String keyAndValue[]=exractKeyAndValue(jsonArrayObject.get(i).toString());
                DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(Context.getConceptService().getDrug(Integer.parseInt(keyAndValue[0])).getDrugId(),pharmacyLocations.getUuid());
                if(drugDispenseSettings==null){
                    drugBatchHasBeenSet = false;
                    break;
                }
                else{
                   drugBatchHasBeenSet=true;
                }

            }
            response.getWriter().print(drugBatchHasBeenSet);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
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
