package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONArray;
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
public class StockTransferController {
    private ContainerFactory containerFactory;
    private static final Log log = LogFactory.getLog(StockTransferController.class);
    public PharmacyService service;
    private PharmacyStore pharmacyStore;
    private DrugTransactions drugTransactions,drugTransactionAtReceivingSite;
    private Boolean drugExistsInInventory=false;
    private PharmacyStoreOutgoing pharmacyStoreOutgoing;
    private PharmacyLocations destination;
    private String locationUuid="";
    private Boolean thereIsEnoughQuantityToIssueStock=false;
    private PharmacyLocations otherTransactingSite;
    private StockTransferTracker stockTransferTracker;
    private List<PharmacyStoreOutgoing> pharmacyStoreOutgoingList;
    private List<PharmacyStoreOutgoing> pharmacyStoreOutgoingListTosave;
    private String serialNumber;

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/stockTransfer")
    public void inventoryMetaDataGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {
        String transactionTypeSelected=request.getParameter("transactionType");
        String otherTransactingSiteUUID=request.getParameter("location");
        if(transactionTypeSelected !=null) {
            request.getSession().setAttribute("transactionTypeSelectedOption", transactionTypeSelected);
        }
        if(otherTransactingSiteUUID !=null) {
            request.getSession().setAttribute("otherTransactingSiteUUID", otherTransactingSiteUUID);
        }
    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/stockTransferApprovalForm")
    public void stockTransferApprovalGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {
        service = Context.getService(PharmacyService.class);
        String transactionTrackerUUIDSelected=request.getParameter("transactionTrackerUUIDSelected");
        if(transactionTrackerUUIDSelected !=null) {
            request.getSession().setAttribute("transactionTrackerUUIDOptionSelected", transactionTrackerUUIDSelected);
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/stockTransferRequest")
    public synchronized void addNewBatchForDrug(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException {
        String locationVal = null;
        String jsonObject = request.getParameter("jsonObject");
        service = Context.getService(PharmacyService.class);
        locationVal = request.getSession().getAttribute("location").toString();
        String transactionTypeSelectedOption = request.getSession().getAttribute("transactionTypeSelectedOption").toString();

        List<PharmacyStore> listPharmacyStore = new ArrayList<PharmacyStore>();
        List<PharmacyStore> inventoryListToAddToNewLocationSavable = new ArrayList<PharmacyStore>();
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoingListToSave = new ArrayList<PharmacyStoreOutgoing>();

        JSONParser parser = new JSONParser();
        Object obj = null;
        PharmacyLocations pharmacyLocations = service.getPharmacyLocationsByName(locationVal);
        obj = parser.parse(jsonObject);
        org.json.simple.JSONArray inventoryInstanceArray = (org.json.simple.JSONArray) obj;
        int inventoryInstanceArraySize = inventoryInstanceArray.size();
        for (int i = 0; i < inventoryInstanceArraySize; i++) {
            pharmacyStore = new PharmacyStore();
            org.json.simple.JSONArray rowInstance = (org.json.simple.JSONArray) inventoryInstanceArray.get(i);
            int intTest = 0;
            for (int j = 0; j < rowInstance.size(); j++) {
                intTest++;
                String myValues[] = exractKeyAndValue(rowInstance.get(j).toString());
                String key = myValues[0];
                String value = myValues[1];
                if (key.equalsIgnoreCase("location")) {
                    locationUuid = value;
                }
                if (key.equalsIgnoreCase("serial_no")) {
                    serialNumber= value;
                }
                if (key.equalsIgnoreCase("outgoingDrug")) {
                    Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                    pharmacyStore.setDrugs(drug);
                }
                if (key.equalsIgnoreCase("outgoingQuantity")) {
                    pharmacyStore.setQuantity(Integer.valueOf(value));

                }
                if (key.equalsIgnoreCase("expiryDate")) {
                    try {
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                        pharmacyStore.setExpireDate(date);
                    } catch (ParseException e) {
                        log.error("Error generated", e);
                    }
                }
                if (key.equalsIgnoreCase("batchNo")) {
                    pharmacyStore.setBatchNo(value);
                }
                if (key.equalsIgnoreCase("buyingPrice")) {
                    pharmacyStore.setBuyingPrice(Double.valueOf(value));
                }
                if (key.equalsIgnoreCase("unitPrice")) {
                    pharmacyStore.setUnitPrice(Double.valueOf(value));
                }

            }
            pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyStore.setMaxLevel(1000);
            pharmacyStore.setMinLevel(10);
            listPharmacyStore.add(pharmacyStore);
        }
        PharmacyLocations selectedLocationObject = service.getPharmacyLocationsByUuid(locationUuid);
        stockTransferTracker=new StockTransferTracker();
        stockTransferTracker.setApproved(false);
        if(Integer.valueOf(transactionTypeSelectedOption) == 1) {
            stockTransferTracker.setPharmacyLocation(selectedLocationObject);
        }else if(Integer.valueOf(transactionTypeSelectedOption) == 2){
            stockTransferTracker.setPharmacyLocation(pharmacyLocations);
        }
        service.saveStockTransferTracker(stockTransferTracker);

        if (Integer.valueOf(transactionTypeSelectedOption) == 1) {
            for (PharmacyStore pharmacyStoreInstance : listPharmacyStore) {
                if (pharmacyStoreInstance.getDrugs() != null) {
                    pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                    pharmacyStoreOutgoing.setDrug(pharmacyStoreInstance.getDrugs());
                    pharmacyStoreOutgoing.setBatchNo(pharmacyStoreInstance.getBatchNo());
                    pharmacyStoreOutgoing.setExpireDate(pharmacyStoreInstance.getExpireDate());
                    pharmacyStoreOutgoing.setQuantityIn(pharmacyStoreInstance.getQuantity());
                    pharmacyStoreOutgoing.setApproved(false);
                    pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));
                    pharmacyStoreOutgoing.setDestination(pharmacyLocations);
                    pharmacyStoreOutgoing.setDeliveryNo(00);
                    pharmacyStoreOutgoing.setMaxLevel(5000);
                    pharmacyStoreOutgoing.setMinLevel(0);
                    pharmacyStoreOutgoing.setS11(serialNumber);
                    pharmacyStoreOutgoing.setLocation(selectedLocationObject);
                    pharmacyStoreOutgoing.setStockTransferTracker(stockTransferTracker);
                    pharmacyStoreOutgoingListToSave.add(pharmacyStoreOutgoing);
                }
            }

        }//end of drugincoming if
        else if (Integer.valueOf(transactionTypeSelectedOption) == 2) {
            for (PharmacyStore pharmacyStoreInstance : listPharmacyStore) {
                if (pharmacyStoreInstance.getDrugs() != null) {
                    pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                    pharmacyStoreOutgoing.setDrug(pharmacyStoreInstance.getDrugs());
                    pharmacyStoreOutgoing.setBatchNo(pharmacyStoreInstance.getBatchNo());
                    pharmacyStoreOutgoing.setExpireDate(pharmacyStoreInstance.getExpireDate());
                    pharmacyStoreOutgoing.setQuantityIn(pharmacyStoreInstance.getQuantity());
                    pharmacyStoreOutgoing.setApproved(false);
                    pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));
                    pharmacyStoreOutgoing.setDestination(selectedLocationObject);
                    pharmacyStoreOutgoing.setDeliveryNo(00);
                    pharmacyStoreOutgoing.setMaxLevel(5000);
                    pharmacyStoreOutgoing.setMinLevel(0);
                    pharmacyStoreOutgoing.setS11(serialNumber);
                    pharmacyStoreOutgoing.setLocation(pharmacyLocations);
                    pharmacyStoreOutgoing.setStockTransferTracker(stockTransferTracker);
                    pharmacyStoreOutgoingListToSave.add(pharmacyStoreOutgoing);
                }
            }

        }//end of drugoutgoing if

        service.savePharmacyStoreOutgoing(pharmacyStoreOutgoingListToSave);
    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/approveStockTransfer")
    public synchronized void approveStockTransfer(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException{
        String jsonText = request.getParameter("values");
        service = Context.getService(PharmacyService.class);
        pharmacyStoreOutgoingList=new ArrayList<PharmacyStoreOutgoing>();
        pharmacyStoreOutgoingListTosave=new ArrayList<PharmacyStoreOutgoing>();
        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        PharmacyStore pharmacyStoreAtReceivingSite,pharmacyStoreAtIssueingSite;
        Integer inventoryQuantityAtReceivingSite=0;
        Integer inventoryQuantityAtIssueingSite=0;
        PharmacyStoreOutgoing pharmacyStoreOutgoingToSave;
        List<PharmacyStore> pharmacyStoreListToReceive = new ArrayList<PharmacyStore>();
        List<PharmacyStore> pharmacyStoreListToIssue= new ArrayList<PharmacyStore>();
        List<DrugTransactions> listDrugTransactions = new ArrayList<DrugTransactions>();
        List<DrugTransactions> drugTransactionListToSave = new ArrayList<DrugTransactions>();
        List<DrugTransactions> drugTransactionListAtReceivingSite = new ArrayList<DrugTransactions>();
        PharmacyStoreOutgoing pharmacyStoreOutgoingByUUID;

        Drug drugFromContext;
        Boolean allDrugsInTheOutGoingOrderHaveNotBeenApproved=false;
        PharmacyStoreOutgoing pharmacyStoreOutgoingToApproveTransferTracker;
        JSONParser parser=new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);
            JSONArray rowInstanceArray = (JSONArray)obj;
            int rowInstanceArraySize=rowInstanceArray.size();
            for(int i=0; i<rowInstanceArraySize; i++){
                JSONArray rowInstance=(JSONArray)rowInstanceArray.get(i);
                pharmacyStoreOutgoing=new PharmacyStoreOutgoing();
                drugTransactions = new DrugTransactions();
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("drugUUID")){
                        drugFromContext= Context.getConceptService().getDrugByUuid(value);
                        pharmacyStoreOutgoing.setDrug(drugFromContext);
                        drugTransactions.setDrugs(drugFromContext);
                    }
                    if(key.equalsIgnoreCase("drugQuantityRequested")){
                        pharmacyStoreOutgoing.setQuantityIn(Integer.valueOf(value));
                        drugTransactions.setQuantityIn(Integer.valueOf(value));
                    }
                    if (key.equalsIgnoreCase("serial_number")) {
                        serialNumber= value;
                    }
                    if(key.equalsIgnoreCase("issueingLocation")){
                        PharmacyLocations issuiengLocations=service.getPharmacyLocationsByUuid(value);
                        pharmacyStoreOutgoing.setLocation(issuiengLocations);
                    }
                    if(key.equalsIgnoreCase("receivingLocation")){
                        PharmacyLocations receivingLocations=service.getPharmacyLocationsByUuid(value);
                        pharmacyStoreOutgoing.setDestination(receivingLocations);
                    }
                    if(key.equalsIgnoreCase("pharmacyOutgoingUUID")){
                        pharmacyStoreOutgoing.setUuid(value);
                        pharmacyStoreOutgoingByUUID=service.getPharmacyStoreOutgoingByUuid(value);
                        drugTransactions.setOtherTransactingSite(pharmacyStoreOutgoingByUUID.getDestination());
                    }
                    if (key.equalsIgnoreCase("expiryDate")) {
                        try {
                            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                            drugTransactions.setexpireDate(date);
                        } catch (ParseException e) {
                            log.error("Error generated", e);
                        }
                    }
                    if(key.equalsIgnoreCase("approved")){
                        if(value.equalsIgnoreCase("true")){
                            pharmacyStoreOutgoing.setApproved(true);
                        }
                        else if(value.equalsIgnoreCase("false")){
                            pharmacyStoreOutgoing.setApproved(false);
                            allDrugsInTheOutGoingOrderHaveNotBeenApproved=true;
                        }
                        else{
                            pharmacyStoreOutgoing.setApproved(false);
                        }
                    }
                    drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal));

                }
                pharmacyStoreOutgoingList.add(pharmacyStoreOutgoing);
                listDrugTransactions.add(drugTransactions);
            }
            for(PharmacyStoreOutgoing pharmacyStoreOutgoingInstance:pharmacyStoreOutgoingList) {
                pharmacyStoreAtReceivingSite= service.getPharmacyInventoryByDrugUuid(pharmacyStoreOutgoingInstance.getDrug().getUuid(), pharmacyStoreOutgoingInstance.getDestination());
                pharmacyStoreAtIssueingSite=service.getPharmacyInventoryByDrugUuid(pharmacyStoreOutgoingInstance.getDrug().getUuid(),pharmacyStoreOutgoingInstance.getLocation());

                if(pharmacyStoreAtReceivingSite !=null && pharmacyStoreAtIssueingSite!=null){
                    if(pharmacyStoreOutgoingInstance.getApproved()==true){
                        pharmacyStoreOutgoingToSave=service.getPharmacyStoreOutgoingByUuid(pharmacyStoreOutgoingInstance.getUuid());
                        inventoryQuantityAtReceivingSite=pharmacyStoreAtReceivingSite.getQuantity();
                        inventoryQuantityAtIssueingSite=pharmacyStoreAtIssueingSite.getQuantity();
                        pharmacyStoreAtIssueingSite.setQuantity((inventoryQuantityAtIssueingSite)-pharmacyStoreOutgoing.getQuantityIn());
                        pharmacyStoreAtReceivingSite.setQuantity(inventoryQuantityAtReceivingSite + pharmacyStoreOutgoingInstance.getQuantityIn());
                        pharmacyStoreOutgoingToSave.setApproved(true);
                        pharmacyStoreOutgoingListTosave.add(pharmacyStoreOutgoingToSave);
                        pharmacyStoreOutgoingToApproveTransferTracker=service.getPharmacyStoreOutgoingByUuid(pharmacyStoreOutgoingInstance.getUuid());
                        if(pharmacyStoreOutgoingToApproveTransferTracker !=null) {
                            if(pharmacyStoreOutgoingToApproveTransferTracker.getStockTransferTracker() !=null) {
                                stockTransferTracker = service.getStockTransferTrackerByUUID(pharmacyStoreOutgoingToApproveTransferTracker.getStockTransferTracker().getUuid());
                                if (stockTransferTracker != null) {
                                    if (allDrugsInTheOutGoingOrderHaveNotBeenApproved == false) {
                                        stockTransferTracker.setApproved(true);
                                        service.saveStockTransferTracker(stockTransferTracker);
                                    }
                                }
                            }
                        }
                        pharmacyStoreListToReceive.add(pharmacyStoreAtReceivingSite);
                        pharmacyStoreListToIssue.add(pharmacyStoreAtIssueingSite);
                        if (pharmacyStoreOutgoingInstance.getDrug() != null) {
                            drugTransactions = new DrugTransactions();
                            drugTransactions.setDrugs(pharmacyStoreOutgoingInstance.getDrug());
                            drugTransactions.setQuantityIn(pharmacyStoreOutgoing.getQuantityIn());
                            drugTransactions.setexpireDate(pharmacyStoreOutgoing.getExpireDate());
                            drugTransactions.setLocation(pharmacyStoreOutgoing.getLocation());
                            drugTransactions.setComment("Issued");
                            drugTransactions.setS11(serialNumber);
                            drugTransactions.setOtherTransactingSite(pharmacyStoreOutgoing.getDestination());
                            drugTransactionListToSave.add(drugTransactions);

                            drugTransactionAtReceivingSite=new DrugTransactions();
                            drugTransactionAtReceivingSite.setDrugs(pharmacyStoreOutgoingInstance.getDrug());
                            drugTransactionAtReceivingSite.setQuantityIn(pharmacyStoreOutgoing.getQuantityIn());
                            drugTransactionAtReceivingSite.setexpireDate(pharmacyStoreOutgoing.getExpireDate());
                            drugTransactionAtReceivingSite.setLocation(pharmacyStoreOutgoing.getDestination());
                            drugTransactionAtReceivingSite.setComment("Received");
                            drugTransactionAtReceivingSite.setS11(serialNumber);
                            drugTransactionAtReceivingSite.setOtherTransactingSite(pharmacyStoreOutgoing.getLocation());
                            drugTransactionListAtReceivingSite.add(drugTransactionAtReceivingSite);
                        }
                    }

                }
            }
            service.savePharmacyInventory(pharmacyStoreListToReceive);
            service.savePharmacyInventory(pharmacyStoreListToIssue);
            service.saveDrugTransactions(drugTransactionListToSave);
            service.saveDrugTransactions(drugTransactionListAtReceivingSite);
            service.savePharmacyStoreOutgoing(pharmacyStoreOutgoingListTosave);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
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
