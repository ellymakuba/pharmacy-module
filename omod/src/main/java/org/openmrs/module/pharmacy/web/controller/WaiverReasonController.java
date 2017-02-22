package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.WaiverReason;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.simple.parser.ContainerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2/20/17.
 */
@Controller
public class WaiverReasonController {
    private static final Log log = LogFactory.getLog(WaiverReason.class);
    private PharmacyService service;
    private List<WaiverReason> reasonList;
    private JSONArray jsonArray;
    private WaiverReason reason;
    private ContainerFactory containerFactory;
    private JSONObject jsonObject;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/waiverReason")
    public synchronized void waiverForm(ModelMap map,HttpServletRequest request) {
    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/waiver_reasons")
    public synchronized void reasonGET(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
        service= Context.getService(PharmacyService.class);
        reasonList=service.getAllWaiverReasons();

        jsonObject=new JSONObject();
        if(reasonList.size()>0){
            for(int i=0;i<reasonList.size();i++){
                jsonArray=new JSONArray();
                jsonArray.put("edit");
                jsonArray.put(""+reasonList.get(i).getReason());
                jsonArray.put("" + reasonList.get(i).getUuid());
                jsonObject.accumulate("aaData",jsonArray);
            }
        }else{
            jsonArray=new JSONArray();
            jsonArray.put("no data");
            jsonArray.put("no data");
            jsonArray.put("no data");
            jsonObject.accumulate("aaData",jsonArray);
        }
        jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
        jsonObject.accumulate("iDisplayStart", 0);
        jsonObject.accumulate("iDisplayLength", 10);
        response.getWriter().print(jsonObject);
        response.flushBuffer();
    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/waiver")
    public synchronized void reasonPOST(ModelMap map,HttpServletRequest request) throws ParseException, JSONException {
        String jsonText=request.getParameter("values");
        service=Context.getService(PharmacyService.class);
        JSONParser jsonParser=new JSONParser();
        Object object=jsonParser.parse(jsonText);
        org.json.simple.JSONArray jsonArrayInstance=(org.json.simple.JSONArray)object;
        reason=new WaiverReason();
        try {
            object = jsonParser.parse(jsonText);
            for(int i=0;i<jsonArrayInstance.size();i++){
                String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
                String key = myValues[0];
                String value = myValues[1];
                if(key.equalsIgnoreCase("reason")){
                    reason.setReason(value);
                }
            }
            service.saveWaiverReason(reason);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public synchronized String[] exractKeyAndValue(String jsonText) {
        String value = "";
        String key="";
        JSONParser parser = new JSONParser();
        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                key+=entry.getKey();
                value+=entry.getValue();
            }
        } catch (Exception pe) {
        }
        String myvals[]={key,value};
        return myvals;
    }
}
