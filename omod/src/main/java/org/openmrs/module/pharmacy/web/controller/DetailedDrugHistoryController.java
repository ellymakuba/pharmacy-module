package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class DetailedDrugHistoryController{
    private static  final Log log=LogFactory.getLog(DetailedDrugHistoryController.class);
    public PharmacyService service;


    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/detailedDrugHistory")
    public void displayComprehensiveRFPReport(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

        String drugId=request.getParameter("drugID");
        request.getSession().setAttribute("sessionDrugId", drugId);
    }

}
