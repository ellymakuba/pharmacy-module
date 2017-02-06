package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.ui.ModelMap;

@Controller
public class RetireInventoryBatchesController{
    private static  final Log log=LogFactory.getLog(RetireInventoryBatchesController.class);
    private PharmacyService service;
    private PharmacyStore pharmacyStore;
    private List<PharmacyStore> pharmacyStoreList;
    private ContainerFactory containerFactory;
    JSONParser parser=new JSONParser();

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/retireInventoryBatches")
    public void displayComprehensiveRFPReport(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {


    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/retireInventoryBatches")
    public void processRetiringInventoryBatches(HttpServletRequest request,HttpServletResponse response) throws java.text.ParseException, IOException {
        String jsonText = request.getParameter("values");
        pharmacyStoreList=new ArrayList<PharmacyStore>();
        List<PharmacyStore> pharmacyStoreListToSave=new ArrayList<PharmacyStore>();
        PharmacyStore pharmacyStoreItemFromUUID;
        service = Context.getService(PharmacyService.class);
        try {
            Object object=parser.parse(jsonText);
            JSONArray jsonArrayConvertedFromPushedRows=(JSONArray)object;
            int numberOfRowsInJsonArray=jsonArrayConvertedFromPushedRows.size();
            for(int pushedRowInstance=0; pushedRowInstance<numberOfRowsInJsonArray; pushedRowInstance++){
                pharmacyStore=new PharmacyStore();
                JSONArray currentRowElements=(JSONArray)jsonArrayConvertedFromPushedRows.get(pushedRowInstance);
                for(int currentInputElement=0; currentInputElement<currentRowElements.size(); currentInputElement++){
                    String myValues[]=exractKeyAndValue(currentRowElements.get(currentInputElement).toString());
                    String key=myValues[0];
                    String value=myValues[1];

                    if(key.equalsIgnoreCase("inventoryItemUUID")){
                        pharmacyStore.setUuid(value);
                    }
                     if(key.equalsIgnoreCase("retire")){
                         if(value.equalsIgnoreCase("true")){
                             pharmacyStore.setVoided(true);
                         }
                    }
                }
                pharmacyStoreList.add(pharmacyStore);
            }
            for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
                if(pharmacyStoreInstance.getUuid() !=null){
                   pharmacyStoreItemFromUUID=service.getPharmacyInventoryByUuid(pharmacyStoreInstance.getUuid());
                    if(pharmacyStoreItemFromUUID!=null && pharmacyStoreInstance.getVoided()==true){
                        pharmacyStoreItemFromUUID.setVoided(true);
                        pharmacyStoreListToSave.add(pharmacyStoreItemFromUUID);
                    }
                }
            }
            service.savePharmacyInventory(pharmacyStoreListToSave);

        } catch (ParseException e) {
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
