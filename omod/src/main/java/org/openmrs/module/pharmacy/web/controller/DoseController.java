package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyDose;
import org.openmrs.module.pharmacy.model.PharmacySupplier;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DoseController {
    private static final Log log = LogFactory.getLog(DoseController.class);
    private JSONArray drugStrengthA;
    public PharmacyService service;
    private boolean found = false;
    private JSONArray doseDescriptions;
    private UserContext userService;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private JSONArray datad2;
    private JSONObject jsonObject;
    private List<PharmacyDose> doseList;
    private int size;
    private JSONArray jsonArray;
    private PharmacyDose dose;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/doseProcessor")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String doseUUID = request.getParameter("doseUUID");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        doseList = service.getPharmacyDose();
        size = doseList.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        try {
            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsonArray.put("" + getDropDown(doseList, i));
                        }
                    } else {
                        jsonArray.put("" + null);
                    }
                    response.getWriter().print(jsonArray);
                }
            } else {
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        jsonObject.accumulate("aaData", getArray(doseList, i));
                    }
                }
                if (!jsonObject.has("aaData")) {
                    datad2 = new JSONArray();
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
            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/doseProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String doseDescription = request.getParameter("name");
        String edit = request.getParameter("doseEdit");
        String uuid = request.getParameter("doseUUID");
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
                doseList = service.getPharmacyDose();
                size = doseList.size();
                log.info("am executing+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                for (int i = 0; i < size; i++) {
                    found = getCheck(doseList, i, doseDescription);
                    if (found)
                        break;
                }
                if (!found) {
                    dose = new PharmacyDose();
                    dose.setName(doseDescription);
                    service.savePharmacyDose(dose);
                } else //do code to display to the user
                {

                }
            } else if (edit.equalsIgnoreCase("true")) {
                dose = new PharmacyDose();
                dose = service.getPharmacyDoseByUuid(uuid);
                if (userService.getAuthenticatedUser().getUserId().equals(dose.getCreator().getUserId())) {
                    // saving/updating a record
                    dose.setName(doseDescription);//(drugName);
                    service.savePharmacyDose(dose);
                }
            }

        }
    }
    public synchronized JSONArray getArray(List<PharmacyDose> pharmacyDoses, int size) {
        JSONArray jsonArrayDoses = new JSONArray();
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {
            if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {
                editPharmacy = true;
                deletePharmacy = true;
            }
            if (rl.hasPrivilege("Edit Pharmacy")) {
                editPharmacy = true;
            }
            if (rl.hasPrivilege("Delete Pharmacy")) {
                deletePharmacy = true;
            }

        }
        if (editPharmacy) {
            jsonArrayDoses.put("edit");
            editPharmacy = false;
        } else
            jsonArrayDoses.put("");
        jsonArrayDoses.put(pharmacyDoses.get(size).getName());
        jsonArrayDoses.put(pharmacyDoses.get(size).getUuid());
        return jsonArrayDoses;
    }

    public synchronized boolean getCheck(List<PharmacyDose> pharmacyDoses, int size, String names) {
        if (pharmacyDoses.get(size).getName().equalsIgnoreCase(names)) {

            return true;

        } else
            return false;

    }
    public synchronized String getDropDown(List<PharmacyDose> pharmacyDoses, int size) {
        return pharmacyDoses.get(size).getName();
    }
}
