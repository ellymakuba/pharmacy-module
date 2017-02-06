package org.openmrs.module.pharmacy.web.controller;

import org.json.JSONException;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by elly on 2/25/16.
 */
@Controller
public class DeliveryNoteController {
    private ContainerFactory containerFactory;
    private static final Log log = LogFactory.getLog(DeliveryNoteController.class);
    private PharmacyService service;
    private DeliveryNote deliveryNote;
    private DeliveryNoteTracker deliveryNoteTracker;
    private Integer deliveryNoteNumber=0;
    private PharmacySupplier pharmacySupplier;
    private String supplierUUID;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/deliveryNote")
    public synchronized void loadDeliveryNoteGetProcessor(ModelMap map,HttpServletRequest request) throws org.json.simple.parser.ParseException, IOException {


    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/addNewDeliveryNote")
    public synchronized void addNewDeliveryNotePostProcessor(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException {
        String locationVal = null;
        String jsonObject = request.getParameter("jsonObject");
        service = Context.getService(PharmacyService.class);

        locationVal = request.getSession().getAttribute("location").toString();
        List<DeliveryNote> listPharmacyDeliveryNote = new ArrayList<DeliveryNote>();
        List<DeliveryNote> pharmacyDeliveryNoteListToSave = new ArrayList<DeliveryNote>();
        JSONParser parser=new JSONParser();
        Object obj=null;
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        List<PharmacyStore> pharmacyStoreListToCompare=service.getPharmacyStoreByLocation(pharmacyLocations);
        obj = parser.parse(jsonObject);
        org.json.simple.JSONArray inventoryInstanceArray = (org.json.simple.JSONArray)obj;
        int inventoryInstanceArraySize=inventoryInstanceArray.size();
        for(int i=0; i<inventoryInstanceArraySize; i++){
            deliveryNote=new DeliveryNote();
            org.json.simple.JSONArray rowInstance=(org.json.simple.JSONArray)inventoryInstanceArray.get(i);
            for(int j=0; j<rowInstance.size(); j++){
                String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                String key = myValues[0];
                String value=myValues[1];
                if(key.equalsIgnoreCase("delivery_note_drug")){
                    Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                    deliveryNote.setDrugs(drug);
                }
                if(key.equalsIgnoreCase("supplierUUID")){
                    supplierUUID=value;
                }
                if(key.equalsIgnoreCase("deliveryNoteNumber")){
                    deliveryNoteNumber=Integer.valueOf(value);
                }
                if(key.equalsIgnoreCase("quantity")){
                    deliveryNote.setQuantityIn(Integer.parseInt(value));
                }
                if(key.equalsIgnoreCase("date")){
                    try{
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                        deliveryNote.setExpireDate(date);
                    }
                    catch (ParseException e) {
                        log.error("Error generated", e);
                    }
                }
                if(key.equalsIgnoreCase("batchNo")){
                    deliveryNote.setBatchNo(value);
                }

            }
            listPharmacyDeliveryNote.add(deliveryNote);
        }
        pharmacySupplier=service.getPharmacySupplierByUuid(supplierUUID);
        deliveryNoteTracker=new DeliveryNoteTracker();
        deliveryNoteTracker.setPharmacyLocation(pharmacyLocations);
        deliveryNoteTracker.setPharmacySupplier(pharmacySupplier);
        deliveryNoteTracker.setDeliveryNoteNumber(deliveryNoteNumber);
        if(listPharmacyDeliveryNote.size() >0) {
            service.saveDeliveryNoteTracker(deliveryNoteTracker);
            for (DeliveryNote deliveryNoteInstance : listPharmacyDeliveryNote) {
                if (deliveryNoteInstance.getDrugs() != null) {
                    deliveryNote = new DeliveryNote();
                    deliveryNote.setDeliveryNoteTracker(deliveryNoteTracker);
                    deliveryNote.setDrugs(deliveryNoteInstance.getDrugs());
                    deliveryNote.setLocation(service.getPharmacyLocationsByName(locationVal));
                    deliveryNote.setQuantityIn(deliveryNoteInstance.getQuantityIn());
                    deliveryNote.setQuantityOut(0);
                    deliveryNote.setUnitPrice(deliveryNoteInstance.getUnitPrice());
                    deliveryNote.setBuyingPrice(deliveryNote.getBuyingPrice());
                    deliveryNote.setExpireDate(deliveryNoteInstance.getExpireDate());
                    if (deliveryNoteInstance.getBatchNo() == null)
                        deliveryNote.setBatchNo("0");
                    else {
                        deliveryNote.setBatchNo(deliveryNoteInstance.getBatchNo());
                    }
                    pharmacyDeliveryNoteListToSave.add(deliveryNote);
                }
            }
        }
        service.savePharmacyDeliveryNotes(pharmacyDeliveryNoteListToSave);

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/issueDeliveredGoods")
    public synchronized void issueDeliveredGoodsGetProcessor(ModelMap map,HttpServletRequest request) throws org.json.simple.parser.ParseException, IOException {
        String deliveryNoteTrackerUUIDSelected=request.getParameter("deliveryNoteNumberSelected");
        if(deliveryNoteTrackerUUIDSelected !=null) {
            request.getSession().setAttribute("deliveryNoteTrackerUUID", deliveryNoteTrackerUUIDSelected);
        }
    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/issueDeliveredGoods")
    public synchronized void issueDeliveredGoodsPostProcessor(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException {
        String locationVal = null;
        String jsonObject = request.getParameter("jsonObject");
        service = Context.getService(PharmacyService.class);
        String deliveryNoteTrackerUUIDSession="";
        locationVal = request.getSession().getAttribute("location").toString();
        List<DeliveryNote> listPharmacyDeliveryNote = new ArrayList<DeliveryNote>();
        List<DeliveryNote> pharmacyDeliveryNoteListToSave = new ArrayList<DeliveryNote>();
        JSONParser parser=new JSONParser();
        Object obj=null;
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        List<PharmacyStore> pharmacyStoreListToCompare=service.getPharmacyStoreByLocation(pharmacyLocations);
        obj = parser.parse(jsonObject);
        org.json.simple.JSONArray inventoryInstanceArray = (org.json.simple.JSONArray)obj;
        int inventoryInstanceArraySize=inventoryInstanceArray.size();
        for(int i=0; i<inventoryInstanceArraySize; i++){
            deliveryNote=new DeliveryNote();
            org.json.simple.JSONArray rowInstance=(org.json.simple.JSONArray)inventoryInstanceArray.get(i);
            for(int j=0; j<rowInstance.size(); j++){
                String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                String key = myValues[0];
                String value=myValues[1];
                if(key.equalsIgnoreCase("deliveryNoteItemUUID")){
                    Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                    deliveryNote.setDrugs(drug);
                }
                if(key.equalsIgnoreCase("quantity_to_issue")){
                    deliveryNote.setQuantityIn(Integer.parseInt(value));
                }
                if(key.equalsIgnoreCase("deliveryNoteTrackerUUID")){
                    deliveryNoteTrackerUUIDSession=value;
                }

            }
            listPharmacyDeliveryNote.add(deliveryNote);
        }
        DeliveryNoteTracker deliveryNoteTrackerObject=service.getDeliveryNoteTrackerByUUID(deliveryNoteTrackerUUIDSession);
        deliveryNoteTracker=new DeliveryNoteTracker();
        deliveryNoteTracker.setPharmacyLocation(pharmacyLocations);
        if(deliveryNoteTrackerObject!=null){
            deliveryNoteTracker.setPharmacySupplier(deliveryNoteTrackerObject.getPharmacySupplier());
            deliveryNoteTracker.setDeliveryNoteNumber(deliveryNoteTrackerObject.getDeliveryNoteNumber());
        }
        if(listPharmacyDeliveryNote.size() >0) {
            service.saveDeliveryNoteTracker(deliveryNoteTracker);
            for (DeliveryNote deliveryNoteInstance : listPharmacyDeliveryNote) {
                if (deliveryNoteInstance.getDrugs() != null) {
                    deliveryNote = new DeliveryNote();
                    deliveryNote.setDeliveryNoteTracker(deliveryNoteTracker);
                    deliveryNote.setDrugs(deliveryNoteInstance.getDrugs());
                    deliveryNote.setLocation(service.getPharmacyLocationsByName(locationVal));
                    deliveryNote.setQuantityIn(0);
                    deliveryNote.setQuantityOut(deliveryNoteInstance.getQuantityIn());
                    deliveryNote.setUnitPrice(deliveryNoteInstance.getUnitPrice());
                    deliveryNote.setBuyingPrice(deliveryNote.getBuyingPrice());
                    deliveryNote.setExpireDate(deliveryNoteInstance.getExpireDate());
                    if (deliveryNoteInstance.getBatchNo() == null)
                        deliveryNote.setBatchNo("0");
                    else {
                        deliveryNote.setBatchNo(deliveryNoteInstance.getBatchNo());
                    }
                    pharmacyDeliveryNoteListToSave.add(deliveryNote);
                }
            }
        }
        service.savePharmacyDeliveryNotes(pharmacyDeliveryNoteListToSave);

    }
    public synchronized String[] exractKeyAndValue(String jsonText) {
        String value = "";
        String key="";
        JSONParser parser = new JSONParser();
        try {
            Map json = (Map) parser.parse(jsonText,containerFactory);
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
