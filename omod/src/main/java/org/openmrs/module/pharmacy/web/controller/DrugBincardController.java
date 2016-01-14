package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Location;
import org.openmrs.Role;
import org.openmrs.api.AdministrationService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class DrugBincardController {
    private static final Log log = LogFactory.getLog(DrugBincardController.class);
    private JSONArray data;
    private JSONArray datad;
    public PharmacyService service;
    String drop = null;
    private boolean found = false;
    private String uuid;
    private String drugstrength;
    private String drugunit;
    private String formulation;
    private String originalbindrug;
    private String dialog = null;
    private LocationService serviceLocation;
    private PharmacyStoreIncoming pharmacyStoreIncoming = null;
    private PharmacyStoreOutgoing pharmacyStoreOutgoing = null;
    private Location locationClass = null;
    private Date dateVal = null;
    private int inVal = 0;
    private int outVal = 0;
    private int totalVal = 0;
    private String filter = null;
    private String uuidfilter = null;
    private boolean exit = false;
    private Integer quantity;
    private Double unitPrice;
    private Integer drugId;
    private String uuiddialog;
    private Calendar currentDate;
    private Calendar readDate;
    private Date dateC;
    private GregorianCalendar one;
    private GregorianCalendar two;
    private String dialogShow;
    private JSONArray datad2;
    private ConceptService serviceDrugs;
    private String bintotal = null;
    private int total;
    private String druguuid,display;
    private String druguuidshow = null;
    private String drugname;
    private JSONArray jsonsAll,doseJsonArray;
    private Date newDate;
    private Date givenDate;
    private int monthsDiff;
    private String category,selectDose,doseUUID;
    private UserContext userService;
    private AdministrationService userAdmin;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    private JSONObject json,doseObject;
    private String value, s11number;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private List<PharmacyStore> pharmacyStores;
    private List<PharmacyStore> pharmacyStoreList,pharmacyStoreList2;
    List<PharmacyDose> doseList;
    private List<Drug> allDrugs;
    private JSONArray jsonArray;
    private PharmacyStore pharmacyStore;
    private PharmacyDose doseInstance;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/getSystemInventoryQuantity")
    public synchronized void systemInventoryQuantityProcessor(HttpServletRequest request, HttpServletResponse response){
        String locationVal = null;
        serviceLocation = Context.getLocationService();
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = pharmacyLocationUsersByUserName.size();
        if (sizeUsers > 1)
        {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1)
        {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        pharmacyStores = service.getInventoryByLocation(pharmacyLocations.getUuid());
        int size = pharmacyStores.size();
        int drugSize = allDrugs.size();
        json = new JSONObject();
        doseObject=new JSONObject();
        jsonArray = new JSONArray();
        try {
                jsonArray =new JSONArray();
                json = new JSONObject();
                for (int i = 0; i < size; i++)
                {
                    jsonArray = getArray(pharmacyStores,i);
                    json.accumulate("aaData",jsonArray);
                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);
                response.getWriter().print(json);
        } catch (Exception e) {
            log.error("Error generated", e);
        }

    }
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugBincard")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response){
        String locationVal = null;
        serviceLocation = Context.getLocationService();
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = pharmacyLocationUsersByUserName.size();
        if (sizeUsers > 1)
        {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1)
        {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }
        doseList=service.getPharmacyDose();
        userService = Context.getUserContext();
        selectDose = request.getParameter("selectDose");
        dialog = request.getParameter("dialog");
        dialogShow = request.getParameter("dialog");
        druguuidshow = request.getParameter("druguuidshow");
        s11number = request.getParameter("s11number");
        bintotal = request.getParameter("bintotal");
        uuiddialog = request.getParameter("uuid");
        filter = request.getParameter("sSearch");
        category = request.getParameter("category");
        String drop = request.getParameter("drop");
        String searchDrug = request.getParameter("searchDrug");
        display=request.getParameter("display");

        serviceDrugs = Context.getConceptService();
        pharmacyStoreList = service.getPharmacyInventoryByCategory(service.getPharmacyCategoryByName(category));
        allDrugs = Context.getConceptService().getAllDrugs();
        PharmacyLocations pharmacyLocations=service.getPharmacyLocationsByName(locationVal);
        pharmacyStores = service.getPharmacyStoreByLocation(pharmacyLocations);
        int size = pharmacyStores.size();
        int drugSize = allDrugs.size();
        json = new JSONObject();
        doseObject=new JSONObject();
        jsonArray = new JSONArray();
        try {
            if(selectDose !=null){
                json = new JSONObject();
                for(int i=0; i<doseList.size(); i++)
                {
                    jsonArray =new JSONArray();
                    jsonArray.put("" + doseList.get(i).getName());
                    jsonArray.put(""+doseList.get(i).getId());
                    json.accumulate("aaData",jsonArray);
                }
                response.getWriter().print(json);
            }
            else if (drop !=null) {
                String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
                List<Drug> drug = service.getPharmacyInventoryByNameAndLocation(searchDrug,service.getPharmacyLocationsByName(locationVal));
                for(int i=0; i<drug.size(); i++)
                {
                    jsonArray.put(""+getPharmacyDrugDropDown(drug,i));
                }
                response.getWriter().print(jsonArray);
            }
            else{
                jsonArray =new JSONArray();
                json = new JSONObject();
                for (int i = 0; i < size; i++)
                {
                   jsonArray = getArray(pharmacyStores, i);
                   json.accumulate("aaData",jsonArray);
                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            log.error("Error generated", e);
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugBincard")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        drugId = Integer.parseInt(request.getParameter("drug_id"));
        quantity = Integer.parseInt(request.getParameter("aStcock"));
        unitPrice = Double.parseDouble(request.getParameter("unitP"));
        doseUUID=request.getParameter("dose");
        uuid= request.getParameter("uuid");
        serviceLocation = Context.getLocationService();
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        PharmacyLocations location=service.getPharmacyLocationsByName(locationVal);
        doseInstance=service.getPharmacyDoseByName(doseUUID);
        updateInventory(drugId,quantity,unitPrice,doseInstance,location.getUuid());
    }
    public synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size) {
            data = new JSONArray();
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
                data.put("edit");
                editPharmacy = false;
            } else
            data.put("");
            data.put(""+pharmacyStore.get(size).getUuid());
            data.put(""+pharmacyStore.get(size).getDrugs().getName()+" - "+pharmacyStore.get(size).getDrugs().getDrugId());
            data.put(""+pharmacyStore.get(size).getQuantity());
            data.put(""+pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));
            data.put(""+pharmacyStore.get(size).getBatchNo());
            data.put(""+pharmacyStore.get(size).getDrugs().getDrugId());
            data.put(""+pharmacyStore.get(size).getUnitPrice());
            if(pharmacyStore.get(size).getDose() !=null) {
                data.put(""+pharmacyStore.get(size).getDose().getName());
            }
            else{
                data.put("Not set");
            }
            data.put(""+pharmacyStore.get(size).getBuyingPrice());
            data.put(""+pharmacyStore.get(size).getUnitsPerPack());
        return data;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStore> pharmacyStore, int size, String location) {
        if (pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location)) {
            if (uuiddialog != null) {
                if (uuiddialog.equals("123")) {

                    return null;
                } else if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid()
                        .equals(pharmacyStore.get(size).getDrugs().getUuid())) {
                    newDate = new Date();
                    int num = getMonthsDifference(pharmacyStore.get(size).getExpireDate(), newDate);
                    datad2 = new JSONArray();
                    datad2.put(pharmacyStore.get(size).getUuid());
                    datad2.put(pharmacyStore.get(size).getDrugs().getName());
                    datad2.put(pharmacyStore.get(size).getQuantity());
                    datad2.put(pharmacyStore.get(size).getBatchNo());
                    datad2.put(num);
                    datad2.put("<input type='checkbox' id='check1' />");
                    return datad2;
                } else {
                    return null;
                }
            } else {

                if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid().equals(pharmacyStore.get(size).getDrugs().getUuid())) {
                    datad = new JSONArray();
                    datad.put(pharmacyStore.get(size).getDrugs().getName());
                    datad.put(pharmacyStore.get(size).getQuantity());
                    return datad2;
                }
                return null;
            }

        } else
            return null;

    }

    public synchronized int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static final int getMonthsDifference(Date date1, Date date2) {
        int m1 = date1.getYear() * 12 + date1.getMonth();
        int m2 = date2.getYear() * 12 + date2.getMonth();
        return m1 - m2 + 1;
    }

    public synchronized String getDropDown(List<PharmacyDose> pharmacyDose, int size) {
        return pharmacyDose.get(size).getName();
    }
    public synchronized String getPharmacyDrugDropDown(List<Drug> pharmacyStore, int size) {
        return pharmacyStore.get(size).getName();
    }
    public synchronized boolean getCheck(List<PharmacyStore> pharmacyStore, int size, String name) {
        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {
            return true;
        } else
            return false;

    }
    public synchronized String getDrug(List<PharmacyStore> pharmacyStore, int size, String name) {
        service = Context.getService(PharmacyService.class);
        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {
            return pharmacyStore.get(size).getUuid();
        } else
            return null;
    }

    public synchronized String getString(List<Drug> dname, int size, String text) {
        if ((dname.get(size).getName().equalsIgnoreCase(text))) {
            return dname.get(size).getUuid();
        }
        return null;
    }

    public synchronized int months_betweens(Date date1, Date date2) {
        return date2.getMonth() - date1.getMonth() + (12 * (date2.getYear() - date1.getYear()));
    }
    public boolean  updateInventory(Integer drugId,int Qnty,Double unitP,PharmacyDose dose,String val){
        DrugDispenseSettings dispenseSettings;
        Drug drug = new Drug(drugId);
        Date date = new Date();
        dispenseSettings= service.getDrugDispenseSettingsByDrugIdAndLocation(drugId,val);
        if(dispenseSettings !=null){
            PharmacyStore pharmacyStore = dispenseSettings.getInventoryId();
                if(pharmacyStore.getDrugs().getDrugId()==drugId && Qnty<=0 || Qnty>0){
                    pharmacyStore.setDose(dose);
                    pharmacyStore.setQuantity((Qnty));
                    pharmacyStore.setLastEditDate(date);
                    pharmacyStore.setUnitPrice(unitP);
                    service.savePharmacyInventoryItem(pharmacyStore);
                }
        }
        return true;
    }
}
