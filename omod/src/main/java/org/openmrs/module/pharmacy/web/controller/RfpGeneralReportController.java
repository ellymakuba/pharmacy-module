package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RfpGeneralReportController {

    private static final Log log = LogFactory.getLog(RfpGeneralReportController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray supplierNames;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;
    private JSONObject jsonObject;
    private List<DrugExtra> items;
    private int size;
    private JSONArray jsonArray;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/rfpGeneral")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        userService = Context.getUserContext();
        String sDate  = request.getParameter("datef");
        String eDate = request.getParameter("datet");
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);

        String locationVal = null;

        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }

        //log.info("the current location is ++++++++++++++++++++++++++++++++++++++++++++++++++"+ quant);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        Date minDate = null;
        Date maxDate=null;
        try {
            minDate = formatter.parse(sDate);
            maxDate = formatter.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        items = service.getDrugRange(minDate, maxDate);

        /*ensure the list is not empty*/

        size = items.size();


        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsonArray.put("" + getDropDown(items, i));
                        }
                    } else {
                        jsonArray.put("" + null);

                    }
                    response.getWriter().print(jsonArray);
                }

            } else {

                if (size != 0) {


                    for (int i = 0; i < size; i++) {

                        jsonObject.accumulate("aaData", getArray(items, i,locationVal));

                    }

                }
                if (!jsonObject.has("aaData")) {

                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");

                    jsonObject.accumulate("aaData", datad2);

                }

                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);

                response.getWriter().print(jsonObject);

            }
            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }


    public  Date fromSubmitString2Date(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        return dateFormat.parse(date);
    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpGeneral")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }

    }
    //List<PharmacyStoreIncoming> pharmacyStoreIncomings = service.getDrugQuantityAfterLastStockTake(Date minDate, Date maxDate,String uuid);
    public synchronized JSONArray getArray(List<DrugExtra> supplierNamee, int size,String val) throws JSONException {

        supplierNames = new JSONArray();

        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {

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
        Double myValues[]=findDrugQuantity(supplierNamee.get(size).getDrug().getDrugId(),val);
         //String[] myValues= quant.split("|");

        //for(Double s: myValues){
           // log.info("This is "+s);
        //}
        //log.info("my valueeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees"+myValues.length+" "+myValues[3]);
        supplierNames.put(supplierNamee.get(size).getDrug().getName());
        supplierNames.put(myValues[0]);
        supplierNames.put("");
        supplierNames.put("");
        supplierNames.put("");
        supplierNames.put(myValues[1]);
        supplierNames.put(myValues[2]);
        supplierNames.put(myValues[3]);
        supplierNames.put(myValues[4]);
        supplierNames.put(myValues[5]);
        supplierNames.put(myValues[6]);
        supplierNames.put(myValues[7]);
        supplierNames.put(myValues[8]);
       // supplierNames.put(supplierNamee.get(size).getDateCreated().toString());
       // supplierNames.put(supplierNamee.get(size).getCreator().getUserId());

        return supplierNames;
    }

    public synchronized int getDropDown(List<DrugExtra> supplierNamee, int size) {

        return supplierNamee.get(size).getCreator().getUserId();
    }

    public synchronized boolean getCheck(List<DrugExtra> supplierNamee, int size, String names) {


        return true;



    }

    public Double []findDrugQuantity(int drugId,String val){
        List<DrugDispenseSettings> list = service.getDrugDispenseSettings();
        Double quantity= Double.valueOf(0);
        int wAmount=0;
        double unitPrice=0;
        int size = list.size();
        Date date1=null;

        for (int i = 0; i < size; i++) {
            if(list.get(i).getLocation().getName().equalsIgnoreCase(val)){
//                PharmacyStore pharmacyStore=   service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(""+drugId)).getInventoryId();
                PharmacyStore pharmacyStore=   list.get(i).getInventoryId();
                if(pharmacyStore!=null ){
                    if(pharmacyStore.getDrugs().getDrugId()==drugId){
                        quantity= Double.valueOf(pharmacyStore.getQuantity());
                        unitPrice=pharmacyStore.getUnitPrice();
                         date1=pharmacyStore.getLastEditDate();
                        //log.info("laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaast edit date"+date1);
                    }
                }
            }
        }
        List<DrugTransactions> list2 = service.getDrugTransactions();
        Double quantityFromStore= Double.valueOf(0);

        int size2=list2.size();
        for (int j = 0; j < size2; j++) {
            if(list2.get(j).getLocation().getName().equalsIgnoreCase(val)){

               if(list2.get(j).getDrugs().getDrugId() !=null){
                 if(list2.get(j).getDrugs().getDrugId() == drugId){
                   quantityFromStore=quantityFromStore+list2.get(j).getQuantityIn();
                  }
               }
            }
        }
        List<DrugExtra> list3= service.getDrugExtra();
        Double quantitySold= Double.valueOf(0);
        double amountWaived=0;
        Double count= Double.valueOf(0);
        double countDispensed=0;
        int size3=list3.size();
        for(int k=0; k < size3; k++){
            if(list3.get(k).getPharmacyLocations().getName().equalsIgnoreCase(val)){

             if(list3.get(k).getDrug().getDrugId() !=null){
                 if(list3.get(k).getDrug().getDrugId() == drugId){
                     quantitySold=quantitySold+quantitySold+list3.get(k).getQuantitysold();
                     amountWaived=amountWaived+list3.get(k).getAmountw();
                     countDispensed=countDispensed+1;
                     if(list3.get(k).getAmountw() > 0){
                         count=count+1;
                     }
                 }
             }
            }
        }
        Double cashExpected=quantitySold*unitPrice;
        Double cashExpectedLessW=(quantitySold*unitPrice)-amountWaived;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        //int days = date1-date2;
        Double myVals[] = {quantity,quantityFromStore,unitPrice,quantitySold,amountWaived,count,countDispensed,cashExpected,cashExpectedLessW};
//        String finalVal= count+"|"+quantityFromStore+"|" +unitPrice+"|"+amountWaived;
//        log.info("The final val is =================="+finalVal);
        return myVals;
    }
}