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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

@Controller
public class StockTakeFormController {
    private static final Log log = LogFactory.getLog(StockTakeFormController.class);
    public PharmacyService service;
    private String[] stockTakeFormDrugsArray,stockTakeFormDrugUUIDsArray;
    private String[] stockTakeFormNewQuantitiesArray;
    private ContainerFactory containerFactory;
    JSONParser parser=new JSONParser();
    PharmacyStore pharmacyStore;

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/postStockTakeFormRequest")
    public synchronized void postStockTakeFormRequest(HttpServletRequest request, HttpServletResponse response) {
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

                    if(key.equalsIgnoreCase("stockTakeFormDrugUUID")){
                        pharmacyStore = service.getPharmacyInventoryByUuid(value);
                    }
                    if(key.equalsIgnoreCase("stockTakeFormNewQuantity") && pharmacyStore !=null){
                        if(value !="" && value.length()>0){
                            pharmacyStore.setQuantity(Integer.valueOf(value));
                        }
                    }
                    if(key.equalsIgnoreCase("stockTakeFormunitPrice") && pharmacyStore !=null){
                        if(value !="" && value.length()>0){
                            pharmacyStore.setUnitPrice(Double.valueOf(value));
                        }
                    }
                    if(key.equalsIgnoreCase("stockTakeFormBuyingPrice") && pharmacyStore !=null){
                        if(value !="" && value.length()>0){
                            pharmacyStore.setBuyingPrice(Double.valueOf(value));
                        }
                    }
                  service.savePharmacyInventory(pharmacyStore);
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