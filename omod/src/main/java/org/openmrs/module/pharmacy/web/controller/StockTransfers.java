package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: elly
 * Date: 6/11/15
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class StockTransfers {
    private ContainerFactory containerFactory;
    private static final Log log = LogFactory.getLog(DrugIncomingController.class);
    public PharmacyService service;
    private PharmacyStore pharmacyStore;
    private DrugTransactions drugTransactions;
    private Boolean drugExistsInInventory=false;
    private PharmacyStoreOutgoing pharmacyStoreOutgoing;
    private PharmacyLocations destination;
    private String locationUuid="";
    private Boolean thereIsEnoughQuantityToIssueStock=false;

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/stockTransfer")
    public void inventoryMetaDataGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/stockTransferRequest")
    public synchronized void addNewBatchForDrug(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException{
        String locationVal = null;
        String jsonObject = request.getParameter("jsonObject");
        service = Context.getService(PharmacyService.class);
        locationVal = request.getSession().getAttribute("location").toString();

        //System.out.print("jsonobject in stocktranfer is ======================================================"+jsonObject);
        List<DrugTransactions> listDrugTransactions = new ArrayList<DrugTransactions>();
        List<PharmacyStore> listPharmacyStore = new ArrayList<PharmacyStore>();
        List<PharmacyStore> pharmacyStoreListToSave = new ArrayList<PharmacyStore>();
        List<PharmacyStore> inventoryListToAddToNewLocationSavable= new ArrayList<PharmacyStore>();
        List<DrugTransactions> drugTransactionListToSave = new ArrayList<DrugTransactions>();
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoingList=new ArrayList<PharmacyStoreOutgoing>();
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoingListToSave=new ArrayList<PharmacyStoreOutgoing>();

        JSONParser parser=new JSONParser();
        Object obj=null;
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        List<PharmacyStore> pharmacyStoreListToCompare=service.getPharmacyStoreByLocation(pharmacyLocations);
        obj = parser.parse(jsonObject);
        org.json.simple.JSONArray inventoryInstanceArray = (org.json.simple.JSONArray)obj;
        int inventoryInstanceArraySize=inventoryInstanceArray.size();
        //System.out.println("inventoryInstanceArraySize +++++++++++++++++++++++++++++++++++++++++++++"+inventoryInstanceArraySize);
        for(int i=0; i<inventoryInstanceArraySize; i++){
            drugTransactions = new DrugTransactions();
            pharmacyStore=new PharmacyStore();
            pharmacyStoreOutgoing=new PharmacyStoreOutgoing();
            org.json.simple.JSONArray rowInstance=(org.json.simple.JSONArray)inventoryInstanceArray.get(i);
            int intTest=0;
            for(int j=0; j<rowInstance.size(); j++){
                intTest++;
                String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                String key = myValues[0];
                String value=myValues[1];
                if(key.equalsIgnoreCase("location")){
                    locationUuid=value;
                }
                if(key.equalsIgnoreCase("outgoingDrug")){
                    Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                    pharmacyStore.setDrugs(drug);
                    drugTransactions.setDrugs(drug);
                    pharmacyStoreOutgoing.setDrugs(drug);
                }
                if(key.equalsIgnoreCase("outgoingQuantity")){
                    pharmacyStore.setQuantity(Integer.parseInt(value));
                    drugTransactions.setQuantityIn(Integer.valueOf(value));
                    pharmacyStoreOutgoing.setQuantityIn(Integer.valueOf(value));
                }
                if(key.equalsIgnoreCase("expiryDate")){
                    try{
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                    drugTransactions.setexpireDate(date);
                    pharmacyStore.setExpireDate(date);
                    pharmacyStoreOutgoing.setExpireDate(date);
                    }
                    catch (ParseException e) {
                        log.error("Error generated", e);
                    }
                }
                if(key.equalsIgnoreCase("batchNo")){
                    pharmacyStore.setBatchNo(value);
                    pharmacyStoreOutgoing.setBatchNo(value);
                }
                if(key.equalsIgnoreCase("buyingPrice")){
                    pharmacyStore.setBuyingPrice(Double.valueOf(value));
                }
                if(key.equalsIgnoreCase("unitPrice")){
                    pharmacyStore.setUnitPrice(Double.valueOf(value));
                }

            }
            drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal));
            drugTransactions.setComment("Transfer");
            pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyStore.setMaxLevel(1000);
            pharmacyStore.setMinLevel(10);
            pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(locationVal));
            listDrugTransactions.add(drugTransactions);
            listPharmacyStore.add(pharmacyStore);
            pharmacyStoreOutgoingList.add(pharmacyStoreOutgoing);
        }
        for(PharmacyStore pharmacyStoreInstance: listPharmacyStore){
            if(pharmacyStoreInstance.getDrugs() !=null){
                if(pharmacyStoreListToCompare.size() > 0){
                    for(PharmacyStore pharmacyStoreListItem: pharmacyStoreListToCompare){
                        if(pharmacyStoreInstance.getDrugs().getDrugId() == pharmacyStoreListItem.getDrugs().getDrugId()){
                            Integer totalQuantity= pharmacyStoreListItem.getQuantity()-pharmacyStoreInstance.getQuantity();
                            pharmacyStoreListItem.setQuantity(totalQuantity);
                            if((pharmacyStoreInstance.getUnitPrice()) !=null){
                                pharmacyStoreListItem.setUnitPrice(pharmacyStoreInstance.getUnitPrice());
                            }
                            if((pharmacyStoreInstance.getBuyingPrice()) !=null){
                                pharmacyStoreListItem.setBuyingPrice(pharmacyStoreInstance.getBuyingPrice());
                            }
                            if(pharmacyStoreInstance.getBatchNo() !=null){
                                pharmacyStoreListItem.setBatchNo(pharmacyStoreInstance.getBatchNo());
                            }
                            pharmacyStoreListItem.setExpireDate(pharmacyStoreInstance.getExpireDate());
                            pharmacyStoreListToSave.add(pharmacyStoreListItem);
                            break;
                        }
                    }
                }
            }
        }

        //begin of add drugs to requesting site
        PharmacyLocations selectedLocationObject=service.getPharmacyLocationsByUuid(locationUuid);
        if(selectedLocationObject !=null) {
            List<PharmacyStore> inventoryListFromSelectedLocationToCampare = service.getPharmacyStoreByLocation(selectedLocationObject);
            for (PharmacyStore pharmacyStoreInstance : listPharmacyStore) {
                drugExistsInInventory = false;
                if (pharmacyStoreInstance.getDrugs() != null) {
                    if (pharmacyStoreListToCompare.size() > 0) {
                        for (PharmacyStore pharmacyStoreListItem : inventoryListFromSelectedLocationToCampare) {
                            if (pharmacyStoreInstance.getDrugs().getDrugId() == pharmacyStoreListItem.getDrugs().getDrugId()) {
                                drugExistsInInventory = true;
                                Integer totalQuantity = pharmacyStoreInstance.getQuantity() + pharmacyStoreListItem.getQuantity();
                                pharmacyStoreListItem.setQuantity(totalQuantity);
                                inventoryListToAddToNewLocationSavable.add(pharmacyStoreListItem);
                                break;
                            }
                        }
                        if (drugExistsInInventory == false) {
                            pharmacyStore = new PharmacyStore();
                            pharmacyStore.setDrugs(pharmacyStoreInstance.getDrugs());
                            pharmacyStore.setLocation(service.getPharmacyLocationsByUuid(locationUuid));
                            pharmacyStore.setQuantity(pharmacyStoreInstance.getQuantity());
                            pharmacyStore.setQuantityIn(0);
                            pharmacyStore.setQuantityOut(0);
                            pharmacyStore.setChangeReason(null);
                            pharmacyStore.setMaxLevel(1000);
                            pharmacyStore.setMinLevel(10);
                            pharmacyStore.setS11(Integer.valueOf("001"));
                            pharmacyStore.setUnitPrice(pharmacyStoreInstance.getUnitPrice());
                            pharmacyStore.setExpireDate(pharmacyStoreInstance.getExpireDate());
                            pharmacyStore.setBuyingPrice(pharmacyStoreInstance.getBuyingPrice());
                            if (pharmacyStoreInstance.getBatchNo() == null)
                                pharmacyStore.setBatchNo("0");
                            else {
                                pharmacyStore.setBatchNo(pharmacyStoreInstance.getBatchNo());
                            }
                            inventoryListToAddToNewLocationSavable.add(pharmacyStore);
                        }
                    } else {
                        pharmacyStore = new PharmacyStore();
                        pharmacyStore.setDrugs(pharmacyStoreInstance.getDrugs());
                        pharmacyStore.setLocation(service.getPharmacyLocationsByUuid(locationUuid));
                        pharmacyStore.setQuantity(pharmacyStoreInstance.getQuantity());
                        pharmacyStore.setQuantityIn(0);
                        pharmacyStore.setQuantityOut(0);
                        pharmacyStore.setChangeReason(null);
                        pharmacyStore.setMaxLevel(1000);
                        pharmacyStore.setMinLevel(10);
                        pharmacyStore.setS11(Integer.valueOf("001"));
                        pharmacyStore.setUnitPrice(pharmacyStoreInstance.getUnitPrice());
                        pharmacyStore.setBuyingPrice(pharmacyStoreInstance.getBuyingPrice());
                        pharmacyStore.setExpireDate(pharmacyStoreInstance.getExpireDate());
                        if (pharmacyStoreInstance.getBatchNo() == null)
                            pharmacyStore.setBatchNo("0");
                        else {
                            pharmacyStore.setBatchNo(pharmacyStoreInstance.getBatchNo());
                        }
                        inventoryListToAddToNewLocationSavable.add(pharmacyStore);
                    }
                }
            }

            //end of add drug to requesting site
            for (DrugTransactions drugTransactionInstance : listDrugTransactions) {
                if (drugTransactionInstance.getDrugs() != null) {
                    drugTransactions = new DrugTransactions();
                    drugTransactions.setDrugs(drugTransactionInstance.getDrugs());
                    drugTransactions.setQuantityIn(drugTransactionInstance.getQuantityIn());
                    drugTransactions.setexpireDate(drugTransactionInstance.getExpireDate());
                    drugTransactions.setLocation(drugTransactionInstance.getLocation());
                    drugTransactions.setComment(drugTransactionInstance.getComment());
                    drugTransactions.setS11("0000");
                    drugTransactionListToSave.add(drugTransactionInstance);
                }
            }
            for (PharmacyStoreOutgoing inventoryOutGoingInstance : pharmacyStoreOutgoingList) {
                if (inventoryOutGoingInstance.getDrugs() != null) {
                    destination = service.getPharmacyLocationsByUuid(locationUuid);
                    pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                    pharmacyStoreOutgoing.setDrugs(inventoryOutGoingInstance.getDrugs());
                    pharmacyStoreOutgoing.setBatchNo(inventoryOutGoingInstance.getBatchNo());
                    pharmacyStoreOutgoing.setExpireDate(inventoryOutGoingInstance.getExpireDate());
                    pharmacyStoreOutgoing.setQuantityIn(inventoryOutGoingInstance.getQuantityIn());
                    pharmacyStoreOutgoing.setApproved(true);
                    pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));
                    pharmacyStoreOutgoing.setDestination(destination);
                    pharmacyStoreOutgoing.setDeliveryNo(00);
                    pharmacyStoreOutgoing.setMaxLevel(5000);
                    pharmacyStoreOutgoing.setMinLevel(0);
                    pharmacyStoreOutgoing.setS11(0);
                    pharmacyStoreOutgoing.setLocation(inventoryOutGoingInstance.getLocation());
                    pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName("Request From another site"));
                    pharmacyStoreOutgoingListToSave.add(pharmacyStoreOutgoing);
                }
            }

            service.saveDrugTransactions(drugTransactionListToSave);
            service.savePharmacyInventory(pharmacyStoreListToSave);
            service.savePharmacyInventory(inventoryListToAddToNewLocationSavable);
            service.savePharmacyStoreOutgoing(pharmacyStoreOutgoingListToSave);
        }
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
