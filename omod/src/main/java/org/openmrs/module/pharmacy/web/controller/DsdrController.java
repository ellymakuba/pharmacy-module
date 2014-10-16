package org.openmrs.module.pharmacy.web.controller;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
@Controller
public class DsdrController {
    private static final Log log =LogFactory.getLog(DsdrController.class);
    private PharmacyService service;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private UserContext userService;
    private List<PharmacyStore> inventoryItems;

    @RequestMapping(method=RequestMethod.GET,value = "module/pharmacy/displaydsdrReport")
    public synchronized void displaydsdrRecordsWithinRange(HttpServletRequest request,HttpServletResponse response) throws ParseException{
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
        try {
        inventoryItems=service.getPharmacyStoreByLocation(pharmacyLocations.getUuid());
        jsonObject=new JSONObject();
        for(PharmacyStore pharmacyStore:inventoryItems)
        {
          jsonArray=new JSONArray();
            jsonArray.put(pharmacyStore.getDrugs().getName());
            jsonArray.put("");
            jsonArray.put("");
            jsonArray.put("");
            jsonArray.put("");
            jsonArray.put("");
            jsonArray.put("");
            jsonObject.accumulate("aaData",jsonArray);
        }
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);
            response.getWriter().print(jsonObject);
            response.flushBuffer();
        }
        catch (Exception e) {
            log.error("error generated",e);
        }

    }
}
