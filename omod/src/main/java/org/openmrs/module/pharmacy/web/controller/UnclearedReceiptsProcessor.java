package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Patient;
import org.openmrs.Person;
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
public class UnclearedReceiptsProcessor {
    private static final Log log = LogFactory.getLog(UnclearedReceiptsProcessor.class);
    public PharmacyService service;
    private List<PharmacyEncounter> pharmacyEncounters,unclearedReceipts;
    private List<DrugExtra> receiptToProcess;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private JSONArray datad2;
    private PharmacyDose dose;
    private Integer doseID;
    private String doseName;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/unProcessedReceipts")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String drop = request.getParameter("drop");
        String encounter=request.getParameter("encounterUUID");
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        jsonArray = new JSONArray();
        jsonObject=new JSONObject();

        try {
            if (drop != null) {
                unclearedReceipts=service.getPharmacyEncounterListByLocationUUID(locationUUID);
                int unclearedReceiptsSize=unclearedReceipts.size();
                for(int i=0; i<unclearedReceiptsSize; i++){
                    jsonArray = new JSONArray();
                    jsonArray.put(""+unclearedReceipts.get(i).getUuid());
                    jsonArray.put(""+unclearedReceipts.get(i).getPerson().getPatientIdentifier());
                    jsonArray.put(""+unclearedReceipts.get(i).getTotalAmount());
                    jsonArray.put(""+unclearedReceipts.get(i).getPerson().getGivenName()+" "+unclearedReceipts.get(i).getPerson().getFamilyName());
                    jsonArray.put(""+unclearedReceipts.get(i).getDateCreated());
                    if(unclearedReceipts.get(i).getPaymentStatus() ==1) {
                        jsonArray.put("Paid");
                    }
                    else{
                        jsonArray.put("Not Paid");
                    }
                    jsonArray.put("<input type='radio' name='printItem'/>");
                    jsonObject.accumulate("aaData", jsonArray);
                }
                if (!jsonObject.has("aaData")) {
                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    jsonObject.accumulate("aaData", datad2);

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            }
            else if(encounter !=null){
                receiptToProcess=service.getUnprocessedReceiptsByEncounterUUID(encounter);
                PharmacyEncounter pharmacyEncounter=service.getPharmacyEncounterByUuid(encounter);
                jsonObject=new JSONObject();
                int itemSize=receiptToProcess.size();
                for(int i=0; i<itemSize; i++){
                    PharmacyStore pharmacyStore=service.getPharmacyInventoryByDrugUuid(receiptToProcess.get(i).getDrug().getUuid(),service.getPharmacyLocationsByName(locationVal));
                    jsonArray=new JSONArray();
                    if(receiptToProcess.get(i).getDose() !=null){
                        dose=service.getPharmacyDoseByUuid(receiptToProcess.get(i).getDose().getUuid());
                        doseName=dose.getName();
                        doseID=dose.getId();
                    }
                    else{
                        doseName="";
                        doseID=-1;
                    }
                    jsonArray.put(""+receiptToProcess.get(i).getDrug().getName());
                    jsonArray.put(""+receiptToProcess.get(i).getQuantitysold());
                    jsonArray.put(""+receiptToProcess.get(i).getAmount());
                    jsonArray.put(""+receiptToProcess.get(i).getUuid());
                    jsonArray.put(""+pharmacyStore.getUnitPrice());
                    jsonArray.put(""+pharmacyStore.getQuantity());
                    jsonArray.put(""+receiptToProcess.get(i).getPreviouslySoldQuantity());
                    jsonArray.put(""+receiptToProcess.get(i).getAmountw());
                    jsonArray.put(""+receiptToProcess.get(i).getDiscount());
                    jsonArray.put(""+doseName);
                    jsonArray.put(""+doseID);
                    jsonObject.accumulate("aaData", jsonArray);

                }
                response.getWriter().print(jsonObject);
            }

        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/patientUnclearedRecords")
    public synchronized void showUnclearedPatientRecords(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        String patientIdentifier=request.getParameter("patientIdentifier");
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        jsonArray = new JSONArray();
        jsonObject=new JSONObject();

        try {
            service = Context.getService(PharmacyService.class);
            String patientID=service.getPatientByIdentifier(patientIdentifier);
            if(patientID !=null){
            Person person=Context.getPatientService().getPatient(Integer.parseInt(patientID));
            unclearedReceipts=service.getUnclearedPharmacyEncountersListByPersonID(person.getPersonId());
            int unclearedReceiptsSize=unclearedReceipts.size();
                for(int i=0; i<unclearedReceiptsSize; i++){
                    jsonArray = new JSONArray();
                    jsonArray.put(""+unclearedReceipts.get(i).getUuid());
                    jsonArray.put(""+unclearedReceipts.get(i).getPerson().getPatientIdentifier());
                    jsonArray.put(""+unclearedReceipts.get(i).getTotalAmount());
                    jsonArray.put(""+unclearedReceipts.get(i).getPerson().getGivenName()+" "+unclearedReceipts.get(i).getPerson().getFamilyName());
                    jsonArray.put(""+unclearedReceipts.get(i).getDateCreated());
                    if(unclearedReceipts.get(i).getPaymentStatus() ==1) {
                        jsonArray.put("Paid");
                    }
                    else{
                        jsonArray.put("Not Paid");
                    }
                    jsonArray.put("<input type='radio' name='printItem'/>");
                    jsonObject.accumulate("aaData", jsonArray);
                }
                if (!jsonObject.has("aaData")) {
                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    jsonObject.accumulate("aaData", datad2);

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            }
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/unProcessedReceipts")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws ParseException {

    }
}
