package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ComprehensiveRFPReportController{
    private static  final Log log=LogFactory.getLog(ComprehensiveRFPReportController.class);


    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/comprehensiveRFPReport")
    public void displayComprehensiveRFPReport(ModelMap map,HttpServletRequest request) throws ParseException, IOException {

        String locationVal = request.getSession().getAttribute("location").toString();
        String sDate=request.getParameter("startDate");
        String eDate=request.getParameter("endDate");

            if(sDate !=null && eDate!=null) {
                request.getSession().setAttribute("reportStartDate", sDate);
                request.getSession().setAttribute("reportEndDate", eDate);
            }


    }

}
