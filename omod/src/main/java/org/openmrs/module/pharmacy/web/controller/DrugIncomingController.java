package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.json.simple.JSONArray;
//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class DrugIncomingController {
    private ContainerFactory containerFactory;
    private static final Log log = LogFactory.getLog(DrugIncomingController.class);
    public PharmacyService service;
    String drop = null;
    private boolean safeToSaveDrugTransactions = false;
    private boolean safeToSavePharmacyStoreList = false;
    private String originalbindrug;
    private String dialog = null;
    private LocationService serviceLocation;
    private String incomings11 = null;
    private String incomingdrug;
    private String filter = null;
    private String uuidfilter = null;
    private boolean exit = false;
    private ConceptService serviceDrugs;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private String incomingdrugg;
    private boolean approvePharmacy;
    private String[] incomingdrugArray;
    private String[] incomingquantityinArray,unitPriceArray,buyingPricearray;
    private String[] categoryArray;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size, size1;
    private JSONObject jsonObject;
    private List<Drug> allDrugs;
    private List<PharmacyStoreIncoming> pStoreIncoming;
    private List<PharmacyStoreOutgoing> pStoreOutgoing;
    private PharmacyStoreIncoming pharmacyStoreIncoming;
    private DrugTransactions drugTransactions;
    private String incomingexpirea2;
    private String[] dateArray,batchArray;
    private double unitprice;
    private Boolean batchNoExists;
    private Boolean drugExistsInInventory=false;
    private String[] stockTakeFormDrugsArray,stockTakeFormDrugUUIDsArray;
    private String[] stockTakeFormNewQuantitiesArray;
    private String s11;
    private Drug drugObject;
    private org.json.JSONArray jsonArray,datad2;
    private PharmacyStore pharmacyStore;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugIncoming")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        filter = request.getParameter("sSearch");

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());

        locationVal = request.getSession().getAttribute("location").toString();
        List<PharmacyStoreIncoming> List = service.getPharmacyStoreIncoming();
        int size = List.size();
        jsonObject = new JSONObject();
        response.setContentType("application/jsonObject");

        if (filter.length() > 2) {
            originalbindrug = filter;
            serviceLocation = Context.getLocationService();
            serviceDrugs = Context.getConceptService();
            allDrugs = serviceDrugs.getAllDrugs();
            size1 = allDrugs.size();
            for (int i = 0; i < size1; i++) {
                uuidfilter = getString(allDrugs, i, originalbindrug);
                if (uuidfilter != null)
                    break;
            }
        }
        try {
            if (dialog != null) {
                for (int i = 0; i < size; i++) {
                    jsonObject.accumulate("aaData", getArrayDialog(List, i));
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (List.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {
                        org.json.JSONArray val = getArray(List, i, locationVal);
                        if (val != null)
                            jsonObject.accumulate("aaData", val);
                    }
                    if (exit)
                        break;
                    org.json.JSONArray  data = new org.json.JSONArray();
                }
            }
            exit = false;

            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);

            response.getWriter().print(jsonObject);

            response.flushBuffer();
        } catch (Exception e) {

            log.error("Error generated", e);
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/addOrUpdateInventoryBatches")
    public synchronized void addNewBatchForDrug(HttpServletRequest request, HttpServletResponse response) throws org.json.simple.parser.ParseException, JSONException {
        String locationVal = null;
        String jsonObject = request.getParameter("jsonObject");
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        locationVal = request.getSession().getAttribute("location").toString();
        serviceDrugs = Context.getConceptService();
        originalbindrug = incomingdrug;
        serviceLocation = Context.getLocationService();
        List<DrugTransactions> listDrugTransactions = new ArrayList<DrugTransactions>();
        List<PharmacyStore> listPharmacyStore = new ArrayList<PharmacyStore>();
        List<PharmacyStore> pharmacyStoreListToSave = new ArrayList<PharmacyStore>();
        List<DrugTransactions> drugTransactionListToSave = new ArrayList<DrugTransactions>();
        JSONParser parser=new JSONParser();
        Object obj=null;
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        List<PharmacyStore> pharmacyStoreListToCompare=service.getPharmacyStoreByLocation(pharmacyLocations.getUuid());
        obj = parser.parse(jsonObject);
        org.json.simple.JSONArray inventoryInstanceArray = (org.json.simple.JSONArray)obj;
            int inventoryInstanceArraySize=inventoryInstanceArray.size();
            for(int i=0; i<inventoryInstanceArraySize; i++){
                drugTransactions = new DrugTransactions();
                pharmacyStore=new PharmacyStore();
                org.json.simple.JSONArray rowInstance=(org.json.simple.JSONArray)inventoryInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];
                    if(key.equalsIgnoreCase("incomingdrug")){
                        Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                        drugTransactions.setDrugs(drug);
                        pharmacyStore.setDrugs(drug);
                    }
                    if(key.equalsIgnoreCase("incomingquantityin")){
                        drugTransactions.setQuantityIn(Integer.valueOf(value));
                        pharmacyStore.setQuantity(Integer.parseInt(value));
                    }
                    if(key.equalsIgnoreCase("incomings11")){
                         s11=value;
                    }
                    if(key.equalsIgnoreCase("date")){
                    try{
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                        drugTransactions.setexpireDate(date);
                        pharmacyStore.setExpireDate(date);
                        }
                        catch (ParseException e) {
                            log.error("Error generated", e);
                        }
                    }
                    if(key.equalsIgnoreCase("batchNo")){
                        pharmacyStore.setBatchNo(value);
                        drugTransactions.setBatchNo(value);
                    }
                    if(key.equalsIgnoreCase("buyingPrice")){
                        pharmacyStore.setBuyingPrice(Double.valueOf(value));
                    }
                    if(key.equalsIgnoreCase("unitPrice")){
                        pharmacyStore.setUnitPrice(Double.valueOf(value));
                    }

                }
                drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal));
                drugTransactions.setComment("New entry");
                pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());
                pharmacyStore.setMaxLevel(1000);
                pharmacyStore.setMinLevel(10);
                listDrugTransactions.add(drugTransactions);
                listPharmacyStore.add(pharmacyStore);
            }
         for(PharmacyStore pharmacyStoreInstance: listPharmacyStore){
             drugExistsInInventory=false;
             if(pharmacyStoreInstance.getDrugs() !=null){
                 if(pharmacyStoreListToCompare.size() > 0){
                for(PharmacyStore pharmacyStoreListItem: pharmacyStoreListToCompare){
                if(pharmacyStoreInstance.getDrugs().getDrugId() == pharmacyStoreListItem.getDrugs().getDrugId()){
                    drugExistsInInventory=true;
                    Integer totalQuantity= pharmacyStoreInstance.getQuantity()+ pharmacyStoreListItem.getQuantity();
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
                if(drugExistsInInventory==false){
                pharmacyStore=new PharmacyStore();
                pharmacyStore.setDrugs(pharmacyStoreInstance.getDrugs());
                pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());
                pharmacyStore.setQuantity(pharmacyStoreInstance.getQuantity());
                pharmacyStore.setQuantityIn(0);
                pharmacyStore.setQuantityOut(0);
                pharmacyStore.setChangeReason(null);
                pharmacyStore.setMaxLevel(1000);
                pharmacyStore.setMinLevel(10);
                pharmacyStore.setS11(Integer.valueOf(s11));
                pharmacyStore.setUnitPrice(pharmacyStoreInstance.getUnitPrice());
                pharmacyStore.setExpireDate(pharmacyStoreInstance.getExpireDate());
                pharmacyStore.setBuyingPrice(pharmacyStoreInstance.getBuyingPrice());
                if(pharmacyStoreInstance.getBatchNo()==null)
                    pharmacyStore.setBatchNo("0");
                else {
                    pharmacyStore.setBatchNo(pharmacyStoreInstance.getBatchNo());
                }
                    pharmacyStoreListToSave.add(pharmacyStore);
                }
            }
            else{
                pharmacyStore=new PharmacyStore();
                pharmacyStore.setDrugs(pharmacyStoreInstance.getDrugs());
                pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());
                pharmacyStore.setQuantity(pharmacyStoreInstance.getQuantity());
                pharmacyStore.setQuantityIn(0);
                pharmacyStore.setQuantityOut(0);
                pharmacyStore.setChangeReason(null);
                pharmacyStore.setMaxLevel(1000);
                pharmacyStore.setMinLevel(10);
                pharmacyStore.setS11(Integer.valueOf(s11));
                pharmacyStore.setUnitPrice(pharmacyStoreInstance.getUnitPrice());
                pharmacyStore.setBuyingPrice(pharmacyStoreInstance.getBuyingPrice());
                pharmacyStore.setExpireDate(pharmacyStoreInstance.getExpireDate());
                if(pharmacyStoreInstance.getBatchNo()==null)
                    pharmacyStore.setBatchNo("0");
                else {
                    pharmacyStore.setBatchNo(pharmacyStoreInstance.getBatchNo());
                }
                    pharmacyStoreListToSave.add(pharmacyStore);
            }
          }
         }
        for(DrugTransactions drugTransactionInstance: listDrugTransactions){
            if(drugTransactionInstance.getDrugs() !=null){
            drugTransactions = new DrugTransactions();
            drugTransactions.setDrugs(drugTransactionInstance.getDrugs());
            drugTransactions.setQuantityIn(drugTransactionInstance.getQuantityIn());
            drugTransactions.setexpireDate(drugTransactionInstance.getExpireDate());
            drugTransactions.setLocation(drugTransactionInstance.getLocation());
            drugTransactions.setComment(drugTransactionInstance.getComment());
            drugTransactions.setS11(s11);
            drugTransactions.setBatchNo(drugTransactionInstance.getBatchNo());
            drugTransactionListToSave.add(drugTransactions);
            }
        }
        S11 pharmacyS11=new S11();
        pharmacyS11.setLocation(service.getPharmacyLocationsByName(locationVal));
        pharmacyS11.setS11No(s11);

        service.saveDrugTransactions(drugTransactionListToSave);
        service.savePharmacyInventory(pharmacyStoreListToSave);
        service.savePharmacyS11(pharmacyS11);

    }
    @RequestMapping(method = RequestMethod.GET,value = "module/pharmacy/getBuyingAndSellingPriceOfSelectedDrug")
    public void getBuyingAndSellingPriceOfSelectedDrug(HttpServletRequest request,HttpServletResponse response) throws JSONException {
        String jsonResult = request.getParameter("drugName");
        jsonObject = new JSONObject();
        jsonArray = new org.json.JSONArray();

        service = Context.getService(PharmacyService.class);
        String locationVal=null;
        locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        String extractedDrugName="";
        try {
            jsonObject=new JSONObject(jsonResult);
            extractedDrugName=jsonObject.getString("drugName");
            drugObject = Context.getConceptService().getDrugByNameOrId(extractedDrugName);
            pharmacyStore=service.getPharmacyStoreByDrugName(pharmacyLocation.getUuid(),drugObject);

            if(pharmacyStore !=null){
            jsonArray.put("" + pharmacyStore.getBuyingPrice());
            jsonArray.put("" + pharmacyStore.getUnitPrice());
            jsonArray.put("" + pharmacyStore.getBatchNo());
            jsonArray.put("" + pharmacyStore.getExpireDate());
            jsonArray.put("" + pharmacyStore.getQuantity());
            response.getWriter().print(jsonArray);
             }
            else{
                jsonArray.put("");
                jsonArray.put("");
                response.getWriter().print(jsonArray);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="module/pharmacy/getDrugTransactionsByS11")
    public void getDrugTransactionsByS11(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
        service = Context.getService(PharmacyService.class);
        String s11=request.getParameter("s11");
        String locationName= request.getSession().getAttribute("location").toString();



        PharmacyLocations location=service.getPharmacyLocationsByName(locationName);
        List<DrugTransactions> drugTransactionsList=service.getPharmacyDrugTransactionsByS11NO(s11,location);
        jsonObject=new JSONObject();
        for(DrugTransactions drugTransactionInstance: drugTransactionsList){
            jsonArray=new org.json.JSONArray();
            jsonArray.put(""+drugTransactionInstance.getDrugs().getName());
            jsonArray.put(""+drugTransactionInstance.getQuantityIn());
            jsonArray.put(""+drugTransactionInstance.getBatchNo());
            jsonArray.put(""+drugTransactionInstance.getDateCreated());
            jsonArray.put(""+drugTransactionInstance.getCreator().getGivenName());
            try {
                jsonObject.accumulate("aaData",jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iDisplayStart", 0);
        jsonObject.accumulate("iDisplayLength", 10);
        response.getWriter().print(jsonObject);

    }

    public synchronized org.json.JSONArray getArray(List<PharmacyStoreIncoming> pharmacyStore, int size, String location) {
        if (filter.length() > 2) {
            if ((uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid()))
                    && (!pharmacyStore.get(size).getApproved())) {
                if (pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location)) {
                    org.json.JSONArray data = new org.json.JSONArray();
                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    for (Role rl : xvc) {
                        if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))
                                || (rl.getRole().equals("	Authenticated "))) {
                            editPharmacy = true;
                            deletePharmacy = true;
                            approvePharmacy = true;
                        }
                        if (rl.hasPrivilege("Edit Pharmacy")) {
                            editPharmacy = true;
                        }
                        if (rl.hasPrivilege("Approve Pharmacy")) {
                            approvePharmacy = true;
                        }
                        if (rl.hasPrivilege("Delete Pharmacy")) {
                            deletePharmacy = true;
                        }
                    }
                    if (editPharmacy) {
                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");
                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());
                    data.put(pharmacyStore.get(size).getDrugs().getName());
                    data.put(pharmacyStore.get(size).getQuantityIn());
                    data.put(pharmacyStore.get(size).getDestination().getName());
                    data.put(pharmacyStore.get(size).getTransaction().getName());
                    if (pharmacyStore.get(size).getSupplier() == null) {
                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());
                    data.put(pharmacyStore.get(size).getMinLevel());
                    data.put(pharmacyStore.get(size).getMaxLevel());
                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getExpireDate());
                    data.put(pharmacyStore.get(size).getDeliveryNo());
                    data.put("");
                    if (pharmacyStore.get(size).getApproved()) {
                        data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                    } else {
                        if (approvePharmacy) {
                            data.put("Approve");
                            approvePharmacy = false;
                        } else
                            data.put("");
                    }
                    if (deletePharmacy) {
                        data.put("<var>Delete<var/>");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    data.put(pharmacyStore.get(size).getStatus());
                    return data;
                }
            } else {
                return null;
            }
        } else {
            if ((pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location))
                    && (!pharmacyStore.get(size).getApproved())) {
                org.json.JSONArray data = new org.json.JSONArray();
                Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                for (Role rl : xvc) {
                    if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))
                            || (rl.getRole().equals("	Authenticated "))) {
                        editPharmacy = true;
                        deletePharmacy = true;
                        approvePharmacy = true;
                    }
                    if (rl.hasPrivilege("Edit Pharmacy")) {
                        editPharmacy = true;
                    }
                    if (rl.hasPrivilege("Approve Pharmacy")) {
                        approvePharmacy = true;
                    }
                    if (rl.hasPrivilege("Delete Pharmacy")) {
                        deletePharmacy = true;
                    }
                }
                if (editPharmacy) {
                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");
                data.put("");
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getDrugs().getName());
                data.put(pharmacyStore.get(size).getQuantityIn());
                data.put(pharmacyStore.get(size).getDestination().getName());
                data.put(pharmacyStore.get(size).getTransaction().getName());
                if (pharmacyStore.get(size).getSupplier() == null) {
                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());
                data.put(pharmacyStore.get(size).getMinLevel());
                data.put(pharmacyStore.get(size).getMaxLevel());
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getExpireDate());
                data.put(pharmacyStore.get(size).getDeliveryNo());
                data.put("");
                if (pharmacyStore.get(size).getApproved()) {
                    data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                } else {
                    if (approvePharmacy) {
                        data.put("Approve");
                        approvePharmacy = false;
                    } else
                        data.put("");
                }
                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");
                data.put(pharmacyStore.get(size).getStatus());
                return data;
            }
        }
        return null;
    }
    public synchronized org.json.JSONArray getArrayDialog(List<PharmacyStoreIncoming> pharmacyStore, int size) {
        org.json.JSONArray datad = new org.json.JSONArray();
        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());
        datad.put("");
        datad.put("");
        return datad;
    }
    public synchronized String getDropDown(List<PharmacyStoreIncoming> pharmacyStore, int size) {
        return pharmacyStore.get(size).getUuid();
    }
    public synchronized boolean getCheck(List<PharmacyStoreIncoming> pharmacyStore, int size, String name) {
        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {
            return true;
        } else
            return false;
    }
    public synchronized String getDrug(List<PharmacyStoreIncoming> pharmacyStore, int size, String name) {
        service = Context.getService(PharmacyService.class);
        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {
            return pharmacyStore.get(size).getUuid();
        } else
            return null;
    }
    public synchronized String getString(List<Drug> dname, int size, String text) {
        if ((dname.get(size).getName().equalsIgnoreCase(text))) {
            return dname.get(size).getUuid();
        }
        return null;
    }
    public Date createDate(String val){
        Date    date=null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(val);

        } catch (ParseException e) {
            log.error("Error generated", e);
        }
        return date;
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
