package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.simple.parser.ContainerFactory;

import org.springframework.ui.ModelMap;

@Controller
public class SupplierController{
    private static  final Log log=LogFactory.getLog(SupplierController.class);
    private ContainerFactory containerFactory;
    private PharmacySupplier pharmacySupplierModel;
    public PharmacyService service;
    public String supplierName="";

    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/supplier")
    public void inventoryMetaDataGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }

    @RequestMapping(method = RequestMethod.POST,value="module/pharmacy/supplier")
    public void inventoryMetaDataPostProcessor(HttpServletRequest request,HttpServletResponse response) throws ParseException,NullPointerException {
        String jsonText = request.getParameter("values");
        JSONParser jsonParser=new JSONParser();
        service = Context.getService(PharmacyService.class);
        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        Object object = jsonParser.parse(jsonText);
        JSONArray jsonArrayInstance = (JSONArray) object;

        pharmacySupplierModel=new PharmacySupplier();
        for (int i = 0; i < jsonArrayInstance.size(); i++) {
            String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
            String key = myValues[0];
            String value = myValues[1];
            if (key.equalsIgnoreCase("supplier_name")) {
                pharmacySupplierModel.setName(value);
                supplierName=value;
            }
            if (key.equalsIgnoreCase("supplier_description")) {
                pharmacySupplierModel.setDescription(value);
            }
        }
        PharmacySupplier pharmacySupplierObject=service.getPharmacySupplierByName(supplierName);
        if(pharmacySupplierObject !=null){
            pharmacySupplierObject.setName(pharmacySupplierModel.getName());
            pharmacySupplierObject.setDescription(pharmacySupplierModel.getDescription());
            service.savePharmacySupplier(pharmacySupplierObject);
        }
        else {
            if(pharmacySupplierModel.getName()!=null) {
                PharmacySupplier pharmacySupplier=new PharmacySupplier();
                pharmacySupplier.setName(pharmacySupplierModel.getName());
                pharmacySupplier.setDescription(pharmacySupplierModel.getDescription());
                service.savePharmacySupplier(pharmacySupplier);
            }
        }
    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/activeSupplier")
    public void activeInventoryPostMetaData(HttpServletRequest request,HttpServletResponse response){
        service=Context.getService(PharmacyService.class);
        String supplierName=request.getParameter("supplierName");
        String supplierActiveUUIDHolder=null;

        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
        PharmacySupplier pharmacySupplier=service.getPharmacySupplierByName(supplierName);
        if(pharmacySupplier !=null){
            supplierActiveUUIDHolder=pharmacySupplier.getUuid();
        }
        request.getSession().setAttribute("supplierActiveUUID", supplierActiveUUIDHolder);

    }
    @RequestMapping(method=RequestMethod.POST,value="module/pharmacy/editsupplier")
    public void editInventoryMetaDataPostProcessor(ModelMap map,HttpServletRequest request) {
        String supplierUUID = request.getParameter("supplierUUID");
        String jsonText = request.getParameter("values");

        JSONParser jsonParser = new JSONParser();
        service = Context.getService(PharmacyService.class);
        String locationVal = request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(locationVal);
        Object object = null;
        try {
            object = jsonParser.parse(jsonText);
            JSONArray jsonArrayInstance = (JSONArray) object;
            if (supplierUUID != null) {
                pharmacySupplierModel = service.getPharmacySupplierByUuid(supplierUUID);
                for (int i = 0; i < jsonArrayInstance.size(); i++) {
                    String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
                    String key = myValues[0];
                    String value = myValues[1];
                    if (key.equalsIgnoreCase("supplier_name")) {
                        pharmacySupplierModel.setName(value);
                    }
                    if (key.equalsIgnoreCase("supplier_description")) {
                        pharmacySupplierModel.setDescription(value);
                    }
                }
                service.savePharmacySupplier(pharmacySupplierModel);
            }
            request.getSession().removeAttribute("supplierActiveUUID");
        }
        catch (ParseException e) {
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
            log.info(pe);
        }
        String myvals[]={key,value};
        return myvals;
    }

}
