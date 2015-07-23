package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.model.S11;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elly
 * Date: 7/6/15
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class S11Controller {
 private static final Log log= LogFactory.getLog(S11Controller.class);
    private PharmacyService service;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    @RequestMapping(method= RequestMethod.GET,value="module/pharmacy/displayS11WithinDateRange")
    public void findAllS11WithinDateRange(HttpServletRequest request,HttpServletResponse response){
        service = Context.getService(PharmacyService.class);
        String startDate=request.getParameter("s11Datefrom");
        String endDate=request.getParameter("s11Dateto");
        String locationName = request.getSession().getAttribute("location").toString();
        System.out.println("location name is ++++++++++++++++++++++++++++++++++++"+locationName);
        PharmacyLocations location=service.getPharmacyLocationsByName(locationName);

        Date sDate = null;
        try {
            sDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Date eDate = null;
        try {
            eDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        List<S11> s11List=service.getS11WithinDateRange(location,sDate,eDate);

        jsonObject=new JSONObject();
        for(S11 s11Instance: s11List){
            jsonArray=new JSONArray();
            jsonArray.put(""+s11Instance.getS11No());
            jsonArray.put(""+s11Instance.getDateCreated());
            try {
                jsonObject.accumulate("aaData", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        try {
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
