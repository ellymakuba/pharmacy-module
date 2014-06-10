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
public class FmapReportController {

    private static final Log log = LogFactory.getLog(FmapReportController.class);
    private JSONArray drugStrengthA;
    public PharmacyService service;
    private boolean found = false;
    private List<PharmacyObs> listObsConcepts;
    private JSONArray supplierNames;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private List<PharmacyEncounter> list = null;
    private List<PharmacyOrders> listDrugs;
    private JSONArray datad2;
    private JSONObject jsonObject;
    private List<DrugExtra> items;
    private int size;
    private JSONArray jsonArray;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/fmapReport")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        userService = Context.getUserContext();
        String sDate  = request.getParameter("datef");
        String eDate = request.getParameter("datet");
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        String locationUID=null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date minDate = null;
        Date maxDate=null;
        try {
            minDate = formatter.parse(sDate);
            maxDate = formatter.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        locationUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        list = service.getEncountersRange(minDate,maxDate,locationUID);
        size = list.size();
        try {
            jsonObject = new JSONObject();
            for(int i=0; i<size; i++)
            {

                jsonObject.accumulate("aaData", getArray(list, i,minDate,maxDate));

            }
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);
            response.getWriter().print(jsonObject);

        } catch (JSONException e) {
            log.error("JSON Error generated", e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/fmapReport")
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
    public synchronized JSONArray getArray(List<PharmacyEncounter> encounters,int count,Date startDate,Date endDate) throws JSONException {
        jsonArray = new JSONArray();
        int patientOnRegimenCount=0;
        patientOnRegimenCount=service.getNumberOfPatientsOnRegimen(startDate,endDate,encounters.get(count).getRegimenCode());
        jsonArray.put(encounters.get(count).getRegimenCode());
        jsonArray.put(encounters.get(count).getRegimenName());
        jsonArray.put(patientOnRegimenCount);
        return jsonArray;
    }
}