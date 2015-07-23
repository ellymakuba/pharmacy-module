package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
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
    @RequestMapping(method=RequestMethod.GET, value = "module/pharmacy/displayRFPReport")
    public synchronized  void displayRFPGeneralReport(HttpServletRequest request, HttpServletResponse response) throws ParseException{
        String sDate  = request.getParameter("datef");
        String eDate = request.getParameter("datet");

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);

        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();

        }
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date minDate = null;
        Date maxDate=null;
        try {
            minDate = formatter.parse(sDate);
            maxDate = formatter.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        items = service.getDrugRange(minDate, maxDate,pharmacyLocations.getUuid());
        size = items.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();

        try{
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    jsonObject.accumulate("aaData", getArray(items, i,locationVal,minDate,maxDate));
                }
            }
            else{
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
                datad2.put("None");
                datad2.put("None");
                datad2.put("None");
                datad2.put("None");
                jsonObject.accumulate("aaData", datad2);
            }
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 1000);
            response.getWriter().print(jsonObject);

            response.flushBuffer();
        }
        catch (Exception e){
            log.error("Error generated", e);
        }
    }
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
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date minDate = null;
        Date maxDate=null;
        try {
            minDate = formatter.parse(sDate);
            maxDate = formatter.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        items = service.getDrugRange(minDate, maxDate,pharmacyLocations.getUuid());
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
    public synchronized JSONArray getArray(List<DrugExtra> supplierNamee, int size,String val,Date s,Date e) throws JSONException {
        supplierNames = new JSONArray();
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

        Double myValues[]= new Double[0];
        try {
            myValues = findDrugQuantity(supplierNamee.get(size).getDrug().getDrugId(),val,s,e,supplierNamee.get(size).getPharmacyEncounter().getUuid());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        supplierNames.put(supplierNamee.get(size).getDrug().getName());
        supplierNames.put(myValues[0]);
        supplierNames.put(myValues[10]);
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
        supplierNames.put(myValues[9]);
        return supplierNames;
    }

    public synchronized int getDropDown(List<DrugExtra> supplierNamee, int size) {
        return supplierNamee.get(size).getCreator().getUserId();
    }
    public synchronized boolean getCheck(List<DrugExtra> supplierNamee, int size, String names) {
        return true;
    }
    public Double []findDrugQuantity(Integer drugId,String val,Date startDate,Date endDate,String encounterUUID) throws ParseException {
        String locationUUID=service.getPharmacyLocationsByName(val).getUuid();
        Drug drug = Context.getConceptService().getDrugByNameOrId(drugId.toString());
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drugId,locationUUID);
        Double quantity= Double.valueOf(0);
        double unitPrice=0;
        Date date1=null;
        if(drugDispenseSettings !=null){
            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
            if(pharmacyStore!=null ){
                quantity= Double.valueOf(pharmacyStore.getQuantity());
                unitPrice=pharmacyStore.getUnitPrice();
                date1=pharmacyStore.getLastEditDate();
            }
        }
        Double count=Double.valueOf(service.getNumberOfTimesDrugWaivedWithinPeriodRange(startDate,endDate,drugId,locationUUID));
        List<DrugTransactions> list2 = service.getDrugTransactions();
        Double quantityFromStore= Double.valueOf(0);
        int size2=list2.size();
        for (int j = 0; j < size2; j++) {
            if(list2.get(j).getLocation().getName().equalsIgnoreCase(val)){
                if(list2.get(j).getDrugs() !=null){
                    if(list2.get(j).getDrugs().getDrugId() == drugId){
                        quantityFromStore=quantityFromStore+list2.get(j).getQuantityIn();
                    }
                }
            }
        }
        Integer productsSold= service.getDrugsDispensedWithinPeriodRange(startDate,endDate,drugId,locationUUID);
        Double quantitySold=0.0;
        if(productsSold !=null){
            quantitySold= Double.valueOf(productsSold);
        }
        double amountWaived=0.0;
        if(service.getAmountWaivedWithinPeriodRange(startDate,endDate,drugId,locationUUID) !=null){
            amountWaived=Double.valueOf(service.getAmountWaivedWithinPeriodRange(startDate,endDate,drugId,locationUUID));
        }
        double countDispensed=0;
        Double cashExpected=service.getDrugTotalCashCollectedWithinPeriodRange(startDate,endDate,drugId,locationUUID);
        Double cashClaimedOnReceipt=0.0;
        if(cashExpected !=null){
            cashClaimedOnReceipt= cashExpected;
        }
        Double cashExpectedLessW=(cashClaimedOnReceipt)-amountWaived;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();

        double discount=0.0;
        if(service.getDiscountOnDrugsWithinPeriodRange(startDate,endDate,drugId.toString(),locationUUID) !=null){
            discount=service.getDiscountOnDrugsWithinPeriodRange(startDate,endDate,drugId.toString(),locationUUID);
        }
        cashExpectedLessW=cashExpectedLessW-discount;

        double openingStock=0.0;
        DateTime sDate=new DateTime(startDate);
        DateTime sDatePlusOneDay=sDate.plusDays(1);
        Date formatedDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(sDatePlusOneDay.toString());
        if(service.getDrugInventoryOpeningStockByDateAndLocation(drug,startDate,formatedDate,locationUUID) !=null){
           openingStock= service.getDrugInventoryOpeningStockByDateAndLocation(drug,startDate,formatedDate,locationUUID).getStockQuantities();
        }
        Double myVals[] = {quantity,quantityFromStore,unitPrice,quantitySold,amountWaived,count,countDispensed,cashExpected,discount,cashExpectedLessW,openingStock};
        return myVals;
    }
}