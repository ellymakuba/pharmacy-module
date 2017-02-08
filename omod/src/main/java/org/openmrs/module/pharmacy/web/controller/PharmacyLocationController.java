package org.openmrs.module.pharmacy.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyOpeningStock;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PharmacyLocationController {
    public boolean checked;
    private static final Log log = LogFactory.getLog(PharmacyLocationController.class);
    public PharmacyService service;
    private UserContext userService;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size;
    private boolean setLocation = false;
    private JSONArray jsonArray;
    private List<PharmacyLocationUsers> pharmacyLocationUsers;
    private JSONObject jsonObject;
    private List<PharmacyOpeningStock> openingStockList;
    private Date lastOpeningStockGeneration;
    private PharmacyLocations pharmacyLocation;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/locationSetter")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        response.setContentType("application/json");
        String drop = request.getParameter("drop");

        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size = pharmacyLocationUsersByUserName.size();
        JSONArray jsons = new JSONArray();
        try {
            if (drop != null) {
                jsons.put("" + request.getSession().getAttribute("location"));
            } else {


                if (size > 1) {

                    if (request.getSession().getAttribute("location") != null)
                        jsons.put("" + request.getSession().getAttribute("location"));
                    else
                        jsons.put("none");

                } else if (size == 1) {

                    jsons.put("" + pharmacyLocationUsersByUserName.get(0).getLocation());

                } else
                    jsons.put("none");

            }
            response.getWriter().print(jsons);

            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/locationSetter")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();
        request.getSession().removeAttribute("otherTransactingSiteUUID");
        request.getSession().removeAttribute("transactionTypeSelectedOption");
        request.getSession().removeAttribute("transactionTrackerUUIDOptionSelected");
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
            try {
                String location = request.getParameter("selectedLocation");
                if (location != null) {
                    request.getSession().setAttribute("location", location);
                    response.getWriter().print(location);
                    pharmacyLocation=service.getPharmacyLocationsByName(location);
                    lastOpeningStockGeneration=service.getLastOpeningStockGenerationDate(pharmacyLocation);
                    String todayString=formatter.format(date);
                    if(lastOpeningStockGeneration !=null){
                        String lastDate=formatter.format(lastOpeningStockGeneration);
                        if(!todayString.equalsIgnoreCase(lastDate)){
                            DateTime date1=new DateTime(lastOpeningStockGeneration.getTime());
                            DateTime date2=new DateTime(date.getTime());
                            int days= Days.daysBetween(date1,date2).getDays();
                            if(days<2){
                                generateOpeningStock(date,pharmacyLocation);
                            }
                            else{
                                Date currentDate=lastOpeningStockGeneration;
                                for(int i=0;i<days;i++){
                                    currentDate=new Date(currentDate.getTime()+(1000 * 60 * 60 * 24));
                                    generateOpeningStock(currentDate,pharmacyLocation);
                                }
                            }
                        }
                    }
                    else{
                        generateOpeningStock(date,pharmacyLocation);
                    }
                }
                response.flushBuffer();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("Error generated", e);
            }
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/getLocations")
    public void getAllowedUserLocations(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        service=Context.getService(PharmacyService.class) ;
        String name = Context.getAuthenticatedUser().getUsername();
        List <PharmacyLocationUsers> allowedLocationsForCurrentUser=service.getPharmacyLocationUsersByUserName(name);
        jsonArray=new JSONArray();
        jsonObject=new JSONObject();
        if(allowedLocationsForCurrentUser.size() > 0){
            for(PharmacyLocationUsers locationUser: allowedLocationsForCurrentUser)
            {
                jsonArray.put(""+locationUser.getLocation());
            }
        }
        else {
            jsonArray.put("");
        }
        response.getWriter().print(jsonArray);
    }
    public String getDropDownLocation(List<PharmacyLocationUsers> list2, int size, String name) {
        if (list2.get(size).getUserName().equalsIgnoreCase(name)) {
            return list2.get(size).getLocation();
        } else
            return "null";

    }
    public void generateOpeningStock(Date currentDate,PharmacyLocations location){
        service=Context.getService(PharmacyService.class);
        List<PharmacyStore> pharmacyStoreList=service.getPharmacyStoreByLocation(location);
        openingStockList=new ArrayList<PharmacyOpeningStock>();
        for(PharmacyStore pharmacyStore:pharmacyStoreList){
            PharmacyOpeningStock openingStock=new PharmacyOpeningStock();
            openingStock.setBatchNo(pharmacyStore.getBatchNo());
            openingStock.setDate(currentDate);
            openingStock.setDrug(pharmacyStore.getDrugs());
            openingStock.setLocation(location);
            openingStock.setQuantity(pharmacyStore.getQuantity());
            openingStockList.add(openingStock);
        }
        service.saveOpeningStockList(openingStockList);
    }
}
