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
import org.springframework.ui.ModelMap;
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
    @RequestMapping(method=RequestMethod.GET, value = "module/pharmacy/resources/subpages/F-CDRR")
    public synchronized  void FCDRRGetProcessor(ModelMap map,HttpServletRequest request) throws ParseException{
        String sDate=request.getParameter("startDate");
        String eDate=request.getParameter("endDate");

        if(sDate !=null && eDate!=null) {
            request.getSession().setAttribute("reportStartDate", sDate);
            request.getSession().setAttribute("reportEndDate", eDate);
        }
    }
    @RequestMapping(method=RequestMethod.GET, value = "module/pharmacy/resources/subpages/F-CDRRStandAlone")
    public synchronized  void standALoneFCDRRGetProcessor(ModelMap map,HttpServletRequest request) throws ParseException{
        String sDate=request.getParameter("startDate");
        String eDate=request.getParameter("endDate");

        if(sDate !=null && eDate!=null) {
            request.getSession().setAttribute("reportStartDate", sDate);
            request.getSession().setAttribute("reportEndDate", eDate);
        }
    }
    @RequestMapping(method=RequestMethod.GET, value = "module/pharmacy/resources/subpages/D-CDRR")
    public synchronized  void DCDRRGetProcessor(ModelMap map,HttpServletRequest request) throws ParseException{
        String sDate=request.getParameter("startDate");
        String eDate=request.getParameter("endDate");

        if(sDate !=null && eDate!=null) {
            request.getSession().setAttribute("reportStartDate", sDate);
            request.getSession().setAttribute("reportEndDate", eDate);
        }
    }

}