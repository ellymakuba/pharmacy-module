package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.PharmacyDrugOrder;
import org.openmrs.module.pharmacy.model.PharmacyEncounter;
import org.openmrs.module.pharmacy.model.PharmacyObs;
import org.openmrs.module.pharmacy.model.PharmacyOrders;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PatientEncounterEditor {
    private static final Log log= LogFactory.getLog(PatientEncounterEditor.class);
    public PharmacyService service;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<PharmacyOrders> pharmacyOrdersListByEncounter;
    private PharmacyDrugOrder pharmacyDrugOrder;


    @RequestMapping(method = RequestMethod.GET,value="module/pharmacy/encounterFormToView")
     public synchronized void displayEncounterDetails(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
        service= Context.getService(PharmacyService.class);

        String encounterUUID = request.getParameter("encounterUUID");
        jsonObject=new JSONObject();
        PharmacyEncounter pharmacyEncounter=service.getPharmacyEncounterByUuid(encounterUUID);
        pharmacyOrdersListByEncounter=service.getPharmacyOrdersByEncounterId(pharmacyEncounter);
        if(pharmacyOrdersListByEncounter.size() >0){
         for(int i=0; i<pharmacyOrdersListByEncounter.size(); i++){
             jsonArray=new JSONArray();
             pharmacyDrugOrder=service.getPharmacyDrugOrdersByOrders(pharmacyOrdersListByEncounter.get(i));
             jsonArray.put(pharmacyOrdersListByEncounter.get(i).getPharmacyEncounter().getUuid());
             jsonArray.put(pharmacyDrugOrder.getDrugInventoryUuid().getDrugs().getName());
             jsonArray.put(pharmacyDrugOrder.getDrugInventoryUuid().getDrugs().getName());
             jsonObject.accumulate("aaData",jsonArray);

         }
        }
        else{
            jsonArray=new JSONArray();
            jsonArray.put("None");
            jsonArray.put("None");
            jsonArray.put("None");
            jsonObject.accumulate("aaData",jsonArray);
        }
        response.getWriter().print(jsonObject);
    }
}
