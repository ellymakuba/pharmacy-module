package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.InventoryMetaData;
import org.openmrs.module.pharmacy.model.PharmacyCategory;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Controller
public class InventoryMetaDataController{
    private static  final Log log=LogFactory.getLog(InventoryMetaDataController.class);
    private ContainerFactory containerFactory;
    private InventoryMetaData inventoryMetaDataModel;
    public PharmacyService service;
    private PharmacyStore pharmacyStore;

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/inventoryMetaData")
    public void inventoryMetaDataGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }

    @RequestMapping(method = RequestMethod.POST,value="module/pharmacy/inventoryMetaData")
    public void inventoryMetaDataPostProcessor(HttpServletRequest request,HttpServletResponse response) throws ParseException,NullPointerException {
        String jsonText = request.getParameter("values");
        JSONParser jsonParser=new JSONParser();
        service = Context.getService(PharmacyService.class);
        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        Object object = jsonParser.parse(jsonText);
        JSONArray jsonArrayInstance = (JSONArray) object;

        inventoryMetaDataModel=new InventoryMetaData();
        inventoryMetaDataModel.setLocation(pharmacyLocation);
            for (int i = 0; i < jsonArrayInstance.size(); i++) {
                String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
                String key = myValues[0];
                String value = myValues[1];
                if (key.equalsIgnoreCase("drug_name")) {
                    Drug drug=Context.getConceptService().getDrugByNameOrId(value);
                    inventoryMetaDataModel.setDrug(drug);
                }
                if (key.equalsIgnoreCase("category")) {
                    PharmacyCategory category = service.getPharmacyCategoryByUuid(value);
                    inventoryMetaDataModel.setCategory(category);
                }
                if (key.equalsIgnoreCase("buying_price")) {
                    Double buyingPrice = Double.valueOf(value);
                    inventoryMetaDataModel.setBuyingPrice(buyingPrice);
                }
                if (key.equalsIgnoreCase("selling_price")) {
                    Double sellingPrice = Double.valueOf(value);
                    inventoryMetaDataModel.setSellingPrice(sellingPrice);

                }
                if (key.equalsIgnoreCase("units_in_a_packs")) {
                    Integer unitsPerPack = Integer.valueOf(value);
                    inventoryMetaDataModel.setUnitsInPack(unitsPerPack);
                }
            }
            Drug drugObject=Context.getConceptService().getDrugByNameOrId(inventoryMetaDataModel.getDrug().getName());
            InventoryMetaData inventoryMetaDataObject=service.getInventoryMetaDataByDrugName(drugObject,pharmacyLocation);
            if(inventoryMetaDataObject !=null){
                inventoryMetaDataObject.setBuyingPrice(inventoryMetaDataModel.getBuyingPrice());
                inventoryMetaDataObject.setSellingPrice(inventoryMetaDataModel.getSellingPrice());
                inventoryMetaDataObject.setCategory(inventoryMetaDataModel.getCategory());
                inventoryMetaDataObject.setUnitsInPack(inventoryMetaDataModel.getUnitsInPack());

                pharmacyStore=service.getPharmacyStoreByDrugName(pharmacyLocation,drugObject);
                pharmacyStore.setCategory(inventoryMetaDataModel.getCategory());
                pharmacyStore.setBuyingPrice(inventoryMetaDataModel.getBuyingPrice());
                pharmacyStore.setUnitPrice(inventoryMetaDataModel.getSellingPrice());
                pharmacyStore.setUnitsPerPack(inventoryMetaDataModel.getUnitsInPack());

                service.savePharmacyInventoryItem(pharmacyStore);
                service.savePharmacyInventoryMetaData(inventoryMetaDataObject);

            }
            else {
                if(inventoryMetaDataModel.getDrug()!=null) {
                    try {
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse("02/24/2016");
                        pharmacyStore = new PharmacyStore();
                        pharmacyStore.setDrugs(inventoryMetaDataModel.getDrug());
                        pharmacyStore.setCategory(inventoryMetaDataModel.getCategory());
                        pharmacyStore.setBuyingPrice(inventoryMetaDataModel.getBuyingPrice());
                        pharmacyStore.setUnitPrice(inventoryMetaDataModel.getSellingPrice());
                        pharmacyStore.setUnitsPerPack(inventoryMetaDataModel.getUnitsInPack());
                        pharmacyStore.setLocation(pharmacyLocation);
                        pharmacyStore.setQuantityIn(0);
                        pharmacyStore.setQuantityOut(0);
                        pharmacyStore.setChangeReason(null);
                        pharmacyStore.setMaxLevel(1000);
                        pharmacyStore.setMinLevel(10);
                        pharmacyStore.setS11(000);
                        pharmacyStore.setQuantity(0);
                        pharmacyStore.setExpireDate(date);
                        service.savePharmacyInventoryItem(pharmacyStore);
                        service.savePharmacyInventoryMetaData(inventoryMetaDataModel);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                }
            }


    }

    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/activeInventoryMetaData")
    public void activeInventoryPostMetaData(HttpServletRequest request,HttpServletResponse response){
        service=Context.getService(PharmacyService.class);
        String drugNameMetaData=request.getParameter("drugName");
        String inventoryMetaDataActiveUUIDHolder=null;

        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        Drug drug=Context.getConceptService().getDrugByNameOrId(drugNameMetaData);
        InventoryMetaData inventoryMetaData=service.getInventoryMetaDataByDrugName(drug,pharmacyLocation);
        if(inventoryMetaData !=null){
            inventoryMetaDataActiveUUIDHolder=inventoryMetaData.getUuid();
        }
        request.getSession().setAttribute("inventoryMetaDataActiveUUID", inventoryMetaDataActiveUUIDHolder);

    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/editInventoryMetaData")
    public void editInventoryMetaDataPostProcessor(ModelMap map,HttpServletRequest request) {
        String metaDataItemUUID = request.getParameter("metaDataItemUUID");
        String jsonText = request.getParameter("values");

        JSONParser jsonParser = new JSONParser();
        service = Context.getService(PharmacyService.class);
        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(locationVal);
        Object object = null;
        try {
            object = jsonParser.parse(jsonText);
            JSONArray jsonArrayInstance = (JSONArray) object;
            if (metaDataItemUUID != null) {
                inventoryMetaDataModel = service.getInventoryMetaDataByUUID(metaDataItemUUID);
                for (int i = 0; i < jsonArrayInstance.size(); i++) {
                    String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
                    String key = myValues[0];
                    String value = myValues[1];
                    if (key.equalsIgnoreCase("category")) {
                        PharmacyCategory category = service.getPharmacyCategoryByUuid(value);
                        inventoryMetaDataModel.setCategory(category);
                    }
                    if (key.equalsIgnoreCase("buying_price")) {
                        Double buyingPrice = Double.valueOf(value);
                        inventoryMetaDataModel.setBuyingPrice(buyingPrice);
                    }
                    if (key.equalsIgnoreCase("selling_price")) {
                        Double sellingPrice = Double.valueOf(value);
                        inventoryMetaDataModel.setSellingPrice(sellingPrice);

                    }
                    if (key.equalsIgnoreCase("units_in_a_packs")) {
                        Integer unitsPerPack = Integer.valueOf(value);
                        inventoryMetaDataModel.setUnitsInPack(unitsPerPack);
                    }
                }

                pharmacyStore=service.getPharmacyStoreByDrugName(pharmacyLocation,inventoryMetaDataModel.getDrug());
                if(pharmacyStore !=null) {
                    pharmacyStore.setCategory(inventoryMetaDataModel.getCategory());
                    pharmacyStore.setBuyingPrice(inventoryMetaDataModel.getBuyingPrice());
                    pharmacyStore.setUnitPrice(inventoryMetaDataModel.getSellingPrice());
                    pharmacyStore.setUnitsPerPack(inventoryMetaDataModel.getUnitsInPack());

                    service.savePharmacyInventoryItem(pharmacyStore);
                }
                service.savePharmacyInventoryMetaData(inventoryMetaDataModel);

            }
            request.getSession().removeAttribute("inventoryMetaDataActiveUUID");
        }
        catch (ParseException e) {
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
