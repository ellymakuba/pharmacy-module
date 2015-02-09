package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
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
import java.util.Collection;
import java.util.List;

@Controller
public class DrugDetailsController {
    private static final Log log = LogFactory.getLog(DrugDetailsController.class);
    private JSONArray data;
    public PharmacyService service;
    public LocationService serviceLocation;
    private boolean found = false;
    private ConceptService serviceDrugs;
    private JSONArray supplierNames;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private boolean setLocation = false;
    private String bar = null;
    private String drug = null;
    private String id;
    private List<Drug> allDrugs;
    private Drug drugObject;
    private List<PharmacyLocations> pharmacyLocations;
    private List<PharmacyLocationUsers> pharmacyLocationUsers;
    private int size, size1, size2;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Drug> listDrugs;
    private Drug drugByNameOrId;
    private PharmacyStore pharmacyStore;
    private List<PharmacyEncounter> pharmacyFormsSaved;
    private List<PharmacyStore> pharmacyStoreList;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/pharmacyDrugsRequest")
    public synchronized void getDrugsFromPharmacy(HttpServletRequest request, HttpServletResponse response) {
        String searchDrug = request.getParameter("searchDrug");
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        service = Context.getService(PharmacyService.class);

        try {
            listDrugs = Context.getConceptService().getDrugs(searchDrug);
            int sizeD = listDrugs.size();

            for (int i = 0; i < sizeD; i++) {
                jsonArray.put("" + listDrugs.get(i).getName());
            }
            response.getWriter().print(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/pharmacyDrugIDRequest")
    public synchronized void getPharmacyDrugID(HttpServletRequest request, HttpServletResponse response) {
        String drugName = request.getParameter("drugName");
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        service = Context.getService(PharmacyService.class);

        try {
            drugObject = Context.getConceptService().getDrugByNameOrId(drugName);
            log.info("pharmacyDrugIDRequest drug+++++++++++++++++++++++++++++++++++++++++"+drugObject.getDrugId());
            jsonArray.put("" + drugObject.getDrugId());
            response.getWriter().print(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        String patientToDispenseTo = request.getParameter("searchPatient");
        String patientToFind=request.getParameter("patientToFind");
        String patientT=request.getParameter("patient");
        String drop = request.getParameter("drop");
        String form = request.getParameter("forms");
        bar = request.getParameter("bar");
        String searchDrug = request.getParameter("searchDrug");
        drug = request.getParameter("drug");
        id = request.getParameter("id");


        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();
        allDrugs = serviceDrugs.getAllDrugs();
        pharmacyLocations = service.getPharmacyLocations();
        pharmacyLocationUsers = service.getPharmacyLocationUsers();
        pharmacyFormsSaved= service.getPharmacyEncounter();
        size = allDrugs.size();
        size2 = pharmacyLocations.size();
        size1 = pharmacyLocationUsers.size();
       /* String locationVal = null;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }     */

        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        try {
            if (drop != null) {
                if(drop.equalsIgnoreCase("patientSearch")){
                    String  patient= service.getPatientByIdentifier(patientToDispenseTo);
                    jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(patient)).getPatientIdentifier());
                    response.getWriter().print(jsonArray);
                }
                if(drop.equalsIgnoreCase("patientLastName")){
                    String  patient= service.getPatientByIdentifier(patientToFind);
                    jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(patient)).getNames());
                    response.getWriter().print(jsonArray);
                }
                if (drop.equalsIgnoreCase("drug")) {
                    drugByNameOrId = Context.getConceptService().getDrugByNameOrId(id);
                    jsonArray.put("" + drugByNameOrId.getName());
                    jsonArray.put("" + drugByNameOrId.getConcept().getId());
                    jsonArray.put("" + drugByNameOrId.getConcept().getDisplayString());
                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("drop")) {
                    listDrugs = serviceDrugs.getDrugs(searchDrug);
                    int sizeD = listDrugs.size();
                    if (bar != null) {
                        for (int i = 0; i < sizeD; i++) {
                            jsonArray.put("" + listDrugs.get(i).getName() +">"+listDrugs.get(i).getConcept().getConceptId()+ "|" + listDrugs.get(i).getId());
                        }
                    } else {
                        for (int i = 0; i < sizeD; i++) {
                            jsonArray.put("" + listDrugs.get(i).getName());
                        }
                    }
                    response.getWriter().print(jsonArray);
                }
                /* else if (drop.equalsIgnoreCase("dispenseDrug")) {
                    pharmacyStoreList = service.getPharmacyInventoryByNameAndLocation(searchDrug,locationVal);
                    int sizeD = pharmacyStoreList.size();
                        for (int i = 0; i < sizeD; i++) {
                            jsonArray.put("" + pharmacyStoreList.get(i).getDrugs().getName());
                            log.info(" drug dispense drug++++++++++++++++++++++++++++++++++++"+pharmacyStoreList.get(i).getDrugs().getName()+" and search term "+searchDrug);
                        }

                    response.getWriter().print(jsonArray);
                } */
                else if (drop.equalsIgnoreCase("forms")) {
                    for (int ii = 0; ii < pharmacyFormsSaved.size(); ii++) {
                        String val = pharmacyFormsSaved.get(ii).getFormName();
                        if (!val.contentEquals("null"))
                            jsonArray.put("" + val);
                        else {
                            jsonArray.put("");
                        }

                    }
                    jsonObject.accumulate("", jsonArray);
                    response.getWriter().print(jsonArray);
                }
                else if (drop.equalsIgnoreCase("location")) {
                    String name = Context.getAuthenticatedUser().getUsername();
                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    /*for (Role rl : xvc) {
                        if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))) {
                            setLocation = true;
                        }
                        if (rl.hasPrivilege("Set Location")) {
                            setLocation = true;
                        }
                    }*/
                    setLocation = true;
                    if (setLocation) {
                        for (int ii = 0; ii < size1; ii++) {
                            String val = getDropDownLocation(pharmacyLocationUsers, ii, name);
                            if (!val.contentEquals("null"))
                                jsonArray.put("" + val);
                        }
                    } else {

                        jsonArray.put("No permission");
                    }

                    jsonObject.accumulate("", jsonArray);
                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("locationAll")) {
                    for (int ii = 0; ii < size2; ii++) {

                        jsonArray.put("" + pharmacyLocations.get(ii).getName());
                    }
                    jsonObject.accumulate("", jsonArray);
                    response.getWriter().print(jsonArray);
                }

            } else {
                for (int i = 0; i < size; i++) {
                    jsonObject.accumulate("aaData", getArray(allDrugs, i));

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();

        } catch (Exception e) {
            // drugs
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {


    }

    public synchronized JSONArray getArray(List<Drug> drug, int size) {
        data = new JSONArray();
        Collection<Privilege> xc = userService.getAuthenticatedUser().getPrivileges();

        for (Privilege p : xc) {
            if (p.getPrivilege().equalsIgnoreCase("Edit Pharmacy")) {
                editPharmacy = true;
            }

            if (p.getPrivilege().equalsIgnoreCase("Delete Pharmacy")) {
                deletePharmacy = true;
            }

        }

        if (editPharmacy) {

            data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            data.put("");
        data.put(drug.get(size).getUuid());
        data.put(drug.get(size).getName());
        data.put("");
        data.put("");
        data.put("");
        if (deletePharmacy) {
            data.put("<a href=#?uuid=" + drug.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            data.put("");
        return data;
    }

    public String getDropDownLocation(List<PharmacyLocationUsers> list2, int size, String name) {
        if (list2.get(size).getUserName().equalsIgnoreCase(name)) {
            return list2.get(size).getLocation();
        } else
            return "null";


    }


}
