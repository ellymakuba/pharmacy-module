package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Months;
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
public class FCDRRController {
    private static final Log log = LogFactory.getLog(FCDRRController.class);
    public PharmacyService service;
    private JSONArray drugExtraProperties;
    private JSONObject jsonObject;
    private List<PharmacyStore> pharmacyStoreList;
    private int size;
    private JSONArray jsonArray;
    @RequestMapping(method=RequestMethod.GET, value = "module/pharmacy/FCDRRProperties")
    public synchronized  void displayFCDRRProperties(HttpServletRequest request, HttpServletResponse response) throws ParseException{
        String sDate  = request.getParameter("dateFrom");
        String eDate = request.getParameter("dateTo");

        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();

        }
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = formatter.parse(sDate);
            endDate = formatter.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       pharmacyStoreList = service.getDrugTransactionsBetweenRange(startDate, endDate, pharmacyLocation);
        jsonObject = new JSONObject();
        try{
            if (pharmacyStoreList.size() != 0) {
                for (PharmacyStore pharmacyStoreInstance:pharmacyStoreList) {
                    if(pharmacyStoreInstance.getDrugs() !=null) {
                        jsonArray = new JSONArray();
                        Integer openingStock=0;
                        DateTime dateTimeInstance=new DateTime(startDate);
                        //DateTime sDatePlusOneDay=sDate.plusDays(1);
                        Date dateInstance=new Date(startDate.getTime()+(1000 * 60 * 60 * 24));
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                        String tommorowStringDate=simpleDateFormat.format(dateInstance);
                        Date formatedDate = simpleDateFormat.parse(tommorowStringDate);
                        if(service.getDrugInventoryOpeningStockByDateAndLocation(pharmacyStoreInstance.getDrugs(),startDate,formatedDate,pharmacyLocation.getUuid()) !=null){
                            openingStock= service.getDrugInventoryOpeningStockByDateAndLocation(pharmacyStoreInstance.getDrugs(),startDate,formatedDate,pharmacyLocation.getUuid()).getStockQuantities();
                        }
                        Integer stockReceivedForDrug=0;
                        if(service.computeQuantityOfDrugsReceivedWithinDateRange(startDate,formatedDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId())) !=null){
                            stockReceivedForDrug=service.computeQuantityOfDrugsReceivedWithinDateRange(startDate,formatedDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId()));
                        }
                        Integer quantityOfDrugDispensed=0;
                        if(service.computeQuantityOfDrugsDispensedWithinDateRange(startDate,formatedDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId())) !=null){
                            quantityOfDrugDispensed=service.computeQuantityOfDrugsDispensedWithinDateRange(startDate,formatedDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId()));
                        }
                        Integer drugQuantityTransfered=0;
                        if(service.computeQuantityOfDrugsTransferedWithinDateRange(startDate,endDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId())) !=null){
                            drugQuantityTransfered=service.computeQuantityOfDrugsTransferedWithinDateRange(startDate,endDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId()));
                        }
                        DateTime expiryDateTimeInstance = new DateTime(pharmacyStoreInstance.getExpireDate());
                        Months monthsDateTimeInstance = Months.monthsBetween( new DateTime(), expiryDateTimeInstance);
                        int numberOfMonthsToExpiry = monthsDateTimeInstance.getMonths();
                        jsonArray.put(pharmacyStoreInstance.getDrugs().getName());
                        //jsonArray.put(Context.getConceptService().getConceptName(pharmacyStoreInstance.getDrugs().getDosageForm().getConceptId()));
                        jsonArray.put("");
                        jsonArray.put(openingStock);
                        jsonArray.put(stockReceivedForDrug);
                        jsonArray.put(quantityOfDrugDispensed);
                        jsonArray.put("");
                        jsonArray.put(drugQuantityTransfered);
                        jsonArray.put("");

                        if(numberOfMonthsToExpiry < 6) {
                            jsonArray.put(numberOfMonthsToExpiry);
                        }
                        else {
                            jsonArray.put("");
                        }
                        jsonArray.put("");
                        jsonArray.put("");
                        jsonObject.accumulate("aaData", jsonArray);
                    }
                    else{
                        jsonArray = new JSONArray();
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonArray.put("None");
                        jsonObject.accumulate("aaData", jsonArray);
                    }
                }
            }
            else{
                jsonArray = new JSONArray();
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonArray.put("None");
                jsonObject.accumulate("aaData", jsonArray);
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

}