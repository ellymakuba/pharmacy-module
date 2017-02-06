package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.User;
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
@Controller
public class UserDispensedPrescriptionsController {

    private static final Log log = LogFactory.getLog(FmapReportController.class);
    public PharmacyService service;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/dailyDispensedPrescriptionsPerUser")
    public synchronized void fmapReportGetProcessor(ModelMap map, HttpServletRequest request) {

        String sDate = request.getParameter("startDate");
        String eDate = request.getParameter("endDate");
        String user = request.getParameter("user");

        if (sDate != null && eDate != null && user!=null) {
            request.getSession().setAttribute("reportStartDate", sDate);
            request.getSession().setAttribute("reportEndDate", eDate);
            request.getSession().setAttribute("selectedUser", user);
        }

    }
}