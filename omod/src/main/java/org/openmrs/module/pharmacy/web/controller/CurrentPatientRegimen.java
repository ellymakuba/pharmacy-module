package org.openmrs.module.pharmacy.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Patient;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CurrentPatientRegimen {
    private static final Log log=LogFactory.getLog(CurrentPatientRegimen.class);
    private PharmacyService service;
    private String currentRegimenName;
    private String currentRegimenCode;
    private JSONObject jsonRegimenObject;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/currentPatientRegimen")
    public synchronized void findCurrentPatientRegimen(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        String patientIdentifier=request.getParameter("patientIdentifier");
        service = Context.getService(PharmacyService.class);
        String patientID=service.getPatientByIdentifier(patientIdentifier);
        Patient patient=Context.getPatientService().getPatient(Integer.valueOf(patientID));
        PharmacyEncounter pharmacyEncounter=service.getLastPharmacyEncounterByPatientUUID(patient);
        if(pharmacyEncounter !=null){
            currentRegimenName=pharmacyEncounter.getRegimenName();
            currentRegimenCode=pharmacyEncounter.getRegimenCode();
        }

            try{
                jsonRegimenObject=new JSONObject();
                jsonRegimenObject.accumulate("regimenCod",currentRegimenCode);
                jsonRegimenObject.accumulate("regimenNam",currentRegimenName);
                response.getWriter().print(jsonRegimenObject);
                response.flushBuffer();
            }
            catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }



    }
}
