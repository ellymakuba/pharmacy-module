package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class StockTakeFormController {
    private static final Log log = LogFactory.getLog(StockTakeFormController.class);
    public PharmacyService service;
    private ContainerFactory containerFactory;
    private List<PharmacyStore> pharmacyStoreList,pharmacyStoreListToSave;
    JSONParser parser=new JSONParser();
    PharmacyStore pharmacyStore,pharmacyStoreItemToSave;
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/stockTakeForm")
    public void stockTakeGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException,NullPointerException {

    }

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/stockTakeApprovalForm")
    public void stockTakeApprovalGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/postStockTakeApprovalForm")
    public synchronized void postStockTakeApprovalFormRequest(HttpServletRequest request, HttpServletResponse response) {
         String jsonText = request.getParameter("values");
        service = Context.getService(PharmacyService.class);
        pharmacyStoreList=new ArrayList<PharmacyStore>();
        pharmacyStoreListToSave=new ArrayList<PharmacyStore>();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);
            JSONArray rowInstanceArray = (JSONArray)obj;
            int rowInstanceArraySize=rowInstanceArray.size();
            for(int i=0; i<rowInstanceArraySize; i++){
                JSONArray rowInstance=(JSONArray)rowInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];

                    if(key.equalsIgnoreCase("inventoryItemUUID")){
                        pharmacyStore=service.getPharmacyInventoryByUuid(value);
                    }
                    if(key.equalsIgnoreCase("approved") && pharmacyStore !=null){
                        if(value.equalsIgnoreCase("true")){
                            pharmacyStore.setStockTakeApprovalStatus(0);
                        }
                    }
                    pharmacyStoreList.add(pharmacyStore);

                }
            }
            for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
                pharmacyStoreItemToSave=service.getPharmacyInventoryByUuid(pharmacyStoreInstance.getUuid());
                if(pharmacyStoreInstance.getStockTakeApprovalStatus()==0){
                   pharmacyStoreItemToSave.setQuantity(pharmacyStoreInstance.getStockTakeQuantityWaitingForApproval());
                    pharmacyStoreItemToSave.setStockTakeApprovalStatus(0);
                    pharmacyStoreListToSave.add(pharmacyStoreItemToSave);
                }
            }
            service.savePharmacyInventory(pharmacyStoreListToSave);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/postStockTakeFormRequest")
    public synchronized void postStockTakeForm(HttpServletRequest request, HttpServletResponse response) {
        String jsonText = request.getParameter("values");
        service = Context.getService(PharmacyService.class);
        Object obj = null;
        try {
            obj = parser.parse(jsonText);
            JSONArray rowInstanceArray = (JSONArray)obj;
            int rowInstanceArraySize=rowInstanceArray.size();
            for(int i=0; i<rowInstanceArraySize; i++){
                DrugExtra drugExtra=new DrugExtra();
                JSONArray rowInstance=(JSONArray)rowInstanceArray.get(i);
                for(int j=0; j<rowInstance.size(); j++){
                    String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                    String key = myValues[0];
                    String value=myValues[1];

                    if(key.equalsIgnoreCase("inventoryItemUUID")){
                        pharmacyStore = service.getPharmacyInventoryByUuid(value);
                    }
                    if(key.equalsIgnoreCase("newQuantity") && pharmacyStore !=null){
                        if(value !="" && value.length()>0){
                            pharmacyStore.setStockTakeQuantityWaitingForApproval(Integer.valueOf(value));
                            pharmacyStore.setStockTakeApprovalStatus(1);
                        }
                    }
                    service.savePharmacyInventoryItem(pharmacyStore);
                }
            }
        } catch (ParseException e) {
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