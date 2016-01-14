/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugDispenseSettings;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 *
 */
@Controller
public class DrugDispense {
    private static final Log log = LogFactory.getLog(DrugDispenseSettings.class);
    private String dialogShow;
    private PharmacyService service;
    private int size;
    private LocationService serviceLocation;
    private JSONArray datad2;
    JSONObject json;
    private ContainerFactory containerFactory;
    private JSONArray datadFrm;
    private String dialog;
    private Calendar currentDate;
    private Calendar readDate;
    private Date dateC;
    private GregorianCalendar gregorianCalendar;
    private GregorianCalendar calendar;
    private String batchNo;
    private String batchName;
    private String drugName;
    private String delivery;
    private int sizeDrug;
    private String inventoryNo;
    private String voiduuid;
    private String voidreason;
    private String uuidedit;
    private String dispenseedit;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private String days;
    private String amount;
    private String dose = null;
    private String option;
    private Integer b;
    private String quantity;
    private String form;
    private String front;
    private String back;
    private boolean found;
    private String drugID;
    private List<PharmacyLocationUsers> pharmacyLocationUserses;
    private int sizeUsers;
    private List<DrugDispenseSettings> drugDispenseSettings;
    private DrugDispenseSettings drugDispense;
    private Drug drugObject;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/listOfDrugsToSetBatch")
    public synchronized void displayDrugsToSetBatch(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUserses = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUserses.size();
        drugID = request.getParameter("drug");

        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUserses.get(0).getLocation();


        }
        PharmacyLocations location=service.getPharmacyLocationsByName(locationVal);
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        serviceLocation = Context.getLocationService();
        datadFrm = new JSONArray();
        drugDispenseSettings = service.getDrugDispenseSettings();
        size = drugDispenseSettings.size();
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();
        dateC = new Date();
        currentDate.setTime(dateC);
        gregorianCalendar = new GregorianCalendar();
        calendar = new GregorianCalendar();
        gregorianCalendar.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
        currentDate.get(currentDate.DAY_OF_MONTH));
        json = new JSONObject();
        //drugObject = Context.getConceptService().getDrugByNameOrId(drugID);

        DrugDispenseSettings currentSetBatch=service.getDrugDispenseSettingsByDrugIdAndLocation(Integer.valueOf(drugID),location.getUuid());
        if(currentSetBatch !=null){
            datad2=new JSONArray();
            datad2.put(currentSetBatch.getUuid());
            datad2.put(currentSetBatch.getDrugId().getDrugId());
            datad2.put(currentSetBatch.getDrugId().getName());
            datad2.put(currentSetBatch.getInventoryId().getQuantity());
            datad2.put(currentSetBatch.getBatchId());
            datad2.put(currentSetBatch.getInventoryId().getExpireDate().toString().substring(0, 10));
            if (datad2!= null) {
                json.accumulate("aaData", datad2);
        }
        }
        if (!json.has("aaData")) {
            datad2 = new JSONArray();
            datad2.put("None");
            datad2.put("None");
            datad2.put("None");
            datad2.put("None");
            datad2.put("None");
            datad2.put("None");
            json.accumulate("aaData", datad2);
        }

        json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
        json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
        json.accumulate("iDisplayStart", 0);
        json.accumulate("iDisplayLength", 10);
        response.getWriter().print(json);
        response.flushBuffer();
    }

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDispense")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUserses = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUserses.size();
        drugName = request.getParameter("drug");
        dialog = request.getParameter("uuid");
        dose = request.getParameter("dose");
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUserses.get(0).getLocation();


        }
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();
        datadFrm = new JSONArray();
        drugDispenseSettings = service.getDrugDispenseSettings();
        size = drugDispenseSettings.size();
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();
        dateC = new Date();
        currentDate.setTime(dateC);
        gregorianCalendar = new GregorianCalendar();
        calendar = new GregorianCalendar();
        gregorianCalendar.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
        currentDate.get(currentDate.DAY_OF_MONTH));
        try {
            json = new JSONObject();
            if (dialogShow != null && dialog == null) {
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        if(drugDispenseSettings.get(i).getLocation() !=null){
                        if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(i).getLocation().getUuid()).getName().equalsIgnoreCase(locationVal)) {
                            datadFrm = new JSONArray();
                            datadFrm = getArrayDialog(drugDispenseSettings, i, dialogShow, locationVal);
                            if (datadFrm != null)
                             json.accumulate("aaData", datadFrm);
                        }
                        }
                    }
                }
                if (!json.has("aaData")) {
                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("");
                    json.accumulate("aaData", datad2);
                }
                dialogShow = null;
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);
                response.getWriter().print(json);
                response.flushBuffer();
            } else if (dialog != null && dialogShow == null) {
                List<PharmacyStore> listStore = service.getPharmacyInventory();
                int sizeStore = listStore.size();

                for (int i = 0; i < sizeStore; i++) {

                    if (listStore.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {
                        datadFrm = new JSONArray();

                        datadFrm = getArray(listStore, i, dialog, locationVal);

                        if (datadFrm != null)
                            json.accumulate("aaData", datadFrm);
                    }

                    datad2 = new JSONArray();
                }

                if (!json.has("aaData")) {
                    datad2 = new JSONArray();

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    json.accumulate("aaData", datad2);
                }
                dialog = null;
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);

                response.flushBuffer();
            } else if (dose != null) {

                DrugDispenseSettings drugDispense = new DrugDispenseSettings();
                //change calendar
                datad2 = new JSONArray();
                if (Context.getConceptService().getDrugByNameOrId(dose) != null && service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(
                        dose)) != null) {

                    drugDispense = service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(
                            dose));
                    datad2.put(drugDispense.getOption().getName());
                    datad2.put(drugDispense.getValue());

                    datad2.put(drugDispense.getAmount());
                } else {
                    datad2.put(0);
                    datad2.put(0);

                    datad2.put(0);
                }

                //json.accumulate("aaData", datad2);

                response.getWriter().print(datad2);

                response.flushBuffer();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDispense")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        service = Context.getService(PharmacyService.class);
        String data = request.getParameter("datas"); //inventory
        JSONParser passedJSON=new JSONParser();
        Object obj = null;
        try {
            obj=passedJSON.parse(data);
            JSONArray jsonArray=(JSONArray) obj;
            for(int i=0; i<jsonArray.length(); i++){
                String myValues[]= exractKeyAndValue(jsonArray.get(i).toString());
                String key = myValues[0];
                String value=myValues[1];
                log.info("key is+++++++++++++++++++++++++++++++++++++"+key);
                log.info("value is+++++++++++++++++++++++++++++++++++++"+value);
                if(key.equalsIgnoreCase("inventoryNo")){
                    inventoryNo =value;
                }
                else if(key.equalsIgnoreCase("dispensedrug")){
                    drugName =value;
                }
                else if(key.equalsIgnoreCase("optionval")){
                    option =value;
                }
                else if(key.equalsIgnoreCase("dispenseedit")){
                    dispenseedit =value;
                }
                else if(key.equalsIgnoreCase("dispenseuuid")){
                    uuidedit =value;
                }
                else if(key.equalsIgnoreCase("value")){
                    days =value;
                }
                else if(key.equalsIgnoreCase("price")){
                    amount =value;
                }
                else if(key.equalsIgnoreCase("quantity")){
                    quantity =value;
                }
                else if(key.equalsIgnoreCase("form")){
                    form =value;
                }
                else if(key.equalsIgnoreCase("front")){
                    front =value;
                }
                else if(key.equalsIgnoreCase("back")){
                    back =value;
                }
                else if(key.equalsIgnoreCase("dispensereason")){
                    voidreason =value;
                }
                else if(key.equalsIgnoreCase("dispenseuuidvoid")){
                    voiduuid =value;
                }
                else if(key.equalsIgnoreCase("drugID")){
                    drugID =value;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

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
        DrugDispenseSettings drugDispenseSettings1 = service.getDrugDispenseSettingsByDrugIdAndLocation(Integer.valueOf(drugID),pharmacyLocations.getUuid());
        if (drugDispenseSettings1 ==null) {
            drugDispense = new DrugDispenseSettings();
            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
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
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
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

    public synchronized JSONArray getArrayDialog(List<DrugDispenseSettings> drugDispenseSettings, int size, String drugIdString, String location) {
        datad2 = new JSONArray();
         if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(size).getLocation().getUuid()).getName().equalsIgnoreCase(location)) {
        if (drugIdString != null && drugDispenseSettings !=null && drugDispenseSettings.get(size).getDrugId().getName().equalsIgnoreCase(drugIdString)) {

           Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
           for (Role rl : xvc) {
               if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {

                            editPharmacy = true;
                            deletePharmacy = true;
                        }
                        if (rl.hasPrivilege("Edit Pharmacy")) {
                            editPharmacy = true;
                        }
                        if (rl.hasPrivilege("Delete Pharmacy")) {
                            deletePharmacy = true;
                        }

                    }
                    if (editPharmacy) {
                        datad2.put("edit");
                        editPharmacy = false;
                    } else
                        datad2.put("");
                    datad2.put("");
                    datad2.put(drugDispenseSettings.get(size).getUuid());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getName());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getDrugId());
                    datad2.put(drugDispenseSettings.get(size).getOption().getName());
                    datad2.put(drugDispenseSettings.get(size).getValue());
                    try {
                        datad2.put(drugDispenseSettings.get(size).getAmount());
                    } catch (JSONException e) {
                        log.error("Error generated", e);
                    }
                    datad2.put(drugDispenseSettings.get(size).getBatchId());
                    datad2.put(drugDispenseSettings.get(size).getInventoryId().getExpireDate().toString().substring(0, 10));
                    if (deletePharmacy) {
                        datad2.put("void");
                        deletePharmacy = false;
                    } else
                        datad2.put("");
                }

    }
        return datad2;
    }

    public synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size, String id, String location) {
        if (pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location)) {
            if (dialog != null) {
                if (pharmacyStore.get(size).getDrugs().getName().equalsIgnoreCase(id)) {
                    readDate.setTime(pharmacyStore.get(size).getExpireDate());
                    calendar.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));
                    int num = daysBetween(calendar.getTime(), gregorianCalendar.getTime());
                    datad2 = new JSONArray();
                    datad2.put(pharmacyStore.get(size).getUuid());
                    datad2.put(pharmacyStore.get(size).getDrugs().getName());
                    datad2.put(pharmacyStore.get(size).getQuantity());
                    datad2.put(pharmacyStore.get(size).getBatchNo());
                    datad2.put(num);
                    datad2.put("<input type='checkbox' id='check1' />");
                    return datad2;
                } else
                    return null;

            } else
                return null;

        } else {

            return null;
        }
    }

    public synchronized int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public synchronized boolean getCheck(List<DrugDispenseSettings> DrugDispenseSettings, int size, String location, String drug) {


        if (DrugDispenseSettings.get(size).getLocation().getName().equalsIgnoreCase(location) && (DrugDispenseSettings.get(size).getDrugId().getName().equals(drug))) {

            return true;

        } else
            return false;

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
