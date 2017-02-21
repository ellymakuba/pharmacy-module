package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class LocationUsersController {
    private static final Log log = LogFactory.getLog(LocationUsersController.class);
    public PharmacyService service;
    private ContainerFactory containerFactory;
    private User amrsUser;
    private UserContext userService;
    private List<PharmacyLocationUsers> locationUsers;
    private int size;
    private JSONObject jsonObject;
    private org.json.JSONArray jsonArray,datad2;
    private org.json.JSONArray drugNamess;
    private List<PharmacyStore> pharmacyInventory;
    private int sizeList;
    private List<User> userList;
    private List<PharmacyLocations> pharmacyLocations;
    private PharmacyLocationUsers pharmacyLocationUser,pharmacyLocationUserToAddOrUpdate;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private boolean amrsUserAlreadyAPharmacyUser=false;
    private PharmacyLocations pharmacyLocation;
    private boolean postedUserNameAndPharmacyLocationAreAuthentic=false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/locationUsers")
    public synchronized void locationUsersGetProcessor(ModelMap map,HttpServletRequest request) {


    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/displayPharmacyLocationUsers")
    public synchronized void displayPharmacyLocationUsers(HttpServletRequest request,HttpServletResponse response) {
        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        String user = request.getParameter("user");
        String locations = request.getParameter("locations");
        int psize;

        service = Context.getService(PharmacyService.class);
        locationUsers = service.getPharmacyLocationUsers();
        size = locationUsers.size();
        jsonObject = new JSONObject();
        jsonArray = new org.json.JSONArray();
        try {
            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsonArray.put("" + getDropDown(locationUsers, i));
                        }
                    } else
                        jsonArray.put("" + null);
                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("total")) {
                    pharmacyInventory = service.getPharmacyInventory();
                    sizeList = pharmacyInventory.size();
                    if (size != 0) {
                        for (int i = 0; i < sizeList; i++) {
                            int val = getCheckSize(pharmacyInventory, i, drug);
                            if (val == 0) {
                            } else {
                                jsonArray.put("" + val);
                            }
                            if (val != 0)
                                break;
                        }
                    } else
                        jsonArray.put("" + null);
                    response.getWriter().print(jsonArray);
                }
            } else if (user != null) {
                userList = Context.getUserService().getUsers(user, Context.getUserService().getRoles(), true);
                psize = userList.size();
                jsonArray = new org.json.JSONArray();
                for (int i = 0; i < psize; i++) {
                    jsonArray.put(userList.get(i).getUsername());
                }
                response.getWriter().print(jsonArray);
            } else if (locations != null) {
                pharmacyLocations = service.getPharmacyLocations();
                psize = pharmacyLocations.size();
                jsonArray = new org.json.JSONArray();
                for (int i = 0; i < psize; i++) {
                    jsonArray.put(pharmacyLocations.get(i).getName());
                }
                response.getWriter().print(jsonArray);
            } else {
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        jsonObject.accumulate("aaData", getArray(locationUsers, i));
                    }
                }
                if (!jsonObject.has("aaData")) {
                    datad2 = new org.json.JSONArray();
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
            response.flushBuffer();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/locationUsers")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String jsonText=request.getParameter("values");
        JSONParser jsonParser=new JSONParser();
        Object object=jsonParser.parse(jsonText);
        JSONArray jsonArrayInstance=(JSONArray)object;
        pharmacyLocationUser = new PharmacyLocationUsers();
        for(int i=0;i<jsonArrayInstance.size();i++){
            String myValues[] = exractKeyAndValue(jsonArrayInstance.get(i).toString());
            String key = myValues[0];
            String value = myValues[1];
            if(key.equalsIgnoreCase("username")){
                pharmacyLocationUser.setUserName(value);
            }
            if(key.equalsIgnoreCase("locationname")){
                pharmacyLocationUser.setLocation(value);
            }
        }
        amrsUser = Context.getUserService().getUserByUsername(pharmacyLocationUser.getUserName());
        pharmacyLocation=service.getPharmacyLocationsByName(pharmacyLocationUser.getLocation());
        if(amrsUser !=null && pharmacyLocation !=null){
           pharmacyLocationUserToAddOrUpdate=service.getPharmacyLocationUserByAmrsUserNameAndLocation(pharmacyLocationUser.getUserName(), pharmacyLocation.getName());
            postedUserNameAndPharmacyLocationAreAuthentic=true;
        }
        if(pharmacyLocationUserToAddOrUpdate !=null){
            amrsUserAlreadyAPharmacyUser=true;
            System.out.println("pharmacyLocationUserToAddOrUpdate +++++++++++++++++++++++++++++++++++++++++++++++"+pharmacyLocationUserToAddOrUpdate);
        }
        if(amrsUserAlreadyAPharmacyUser){
            pharmacyLocationUserToAddOrUpdate.setAmrsUser(amrsUser);
        }
        else if(postedUserNameAndPharmacyLocationAreAuthentic){
            pharmacyLocationUserToAddOrUpdate=new PharmacyLocationUsers();
            pharmacyLocationUserToAddOrUpdate.setAmrsUser(amrsUser);
            pharmacyLocationUserToAddOrUpdate.setLocation(pharmacyLocationUser.getLocation());
            pharmacyLocationUserToAddOrUpdate.setUserName(pharmacyLocationUser.getUserName());
        }
        service.savePharmacyLocationUsers(pharmacyLocationUserToAddOrUpdate);
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
    public synchronized org.json.JSONArray getArray(List<PharmacyLocationUsers> pharmacyLocations, int size) {
        drugNamess = new org.json.JSONArray();
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {
            if ((rl.equals("Pharmacy Administrator"))) {
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
            drugNamess.put("edit");
            editPharmacy = false;
        } else
            drugNamess.put("");
        drugNamess.put("");
        drugNamess.put(pharmacyLocations.get(size).getUuid());
        drugNamess.put(pharmacyLocations.get(size).getUserName());
        drugNamess.put(pharmacyLocations.get(size).getLocation());

        if (deletePharmacy) {
            drugNamess.put("void");
            deletePharmacy = false;
        } else
            drugNamess.put("");
        return drugNamess;
    }

    public synchronized String getDropDown(List<PharmacyLocationUsers> pharmacyLocations, int size) {
        return pharmacyLocations.get(size).getUserName();
    }
    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {
        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {
            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;
    }
    public boolean getCheck(List<PharmacyLocationUsers> pharmacyLocations, int size, String drugNamess) {
        if (pharmacyLocations.get(size).getUserName().equalsIgnoreCase(drugNamess)) {
            return true;

        } else
            return false;

    }
}
