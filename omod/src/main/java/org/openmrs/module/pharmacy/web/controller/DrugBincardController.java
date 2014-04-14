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

    private String druguuid;

    private String druguuidshow = null;

    private String drugname;

    private JSONArray jsonsAll;

    private Date newDate;

    private Date givenDate;

    private int monthsDiff;

    private String category;

    private UserContext userService;

    private AdministrationService userAdmin;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONObject json;

    private String value, s11number;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private List<PharmacyStore> pharmacyStores;
    private List<PharmacyStore> pharmacyStoreList;
    private List<Drug> allDrugs;
    private JSONArray jsonArray;
    private PharmacyStore pharmacyStore;
    private DrugTransactions drugTransactions;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugBincard")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


        String locationVal = null;
        serviceLocation = Context.getLocationService();

        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = pharmacyLocationUsersByUserName.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }
        userService = Context.getUserContext();
        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        dialogShow = request.getParameter("dialog");
        druguuidshow = request.getParameter("druguuidshow");
        s11number = request.getParameter("s11number");
        bintotal = request.getParameter("bintotal");
        uuiddialog = request.getParameter("uuid");
        filter = request.getParameter("sSearch");


        category = request.getParameter("category");

        if (filter == null)
            filter = "a";

        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        Date dateC = new Date();

        currentDate.setTime(dateC);

        one = new GregorianCalendar();
        two = new GregorianCalendar();

        one.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        pharmacyStores = service.getPharmacyInventory();

        pharmacyStoreList = service.getPharmacyInventoryByCategory(service.getPharmacyCategoryByName(category));

        allDrugs = Context.getConceptService().getAllDrugs();
        int size = pharmacyStores.size();
        int drugSize = allDrugs.size();
        json = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/json");

        if (filter.startsWith("s11"))
            s11number = filter.substring(4);

        if (uuiddialog == null && druguuidshow == null && filter != null) {

            if (filter.length() > 2 && !filter.startsWith("s11")) {

                originalbindrug = filter;

                serviceLocation = Context.getLocationService();
                allDrugs = serviceDrugs.getAllDrugs();
                int dnames = allDrugs.size();
                for (int i = 0; i < dnames; i++) {
                    uuidfilter = getString(allDrugs, i, originalbindrug);
                    if (uuidfilter != null)
                        break;

                }

            }
        }
        try {

            if (dialog != null) {

                for (int i = 0; i < size; i++) {
                    if (service.getPharmacyLocationsByUuid(pharmacyStores.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {

                        datad2 = new JSONArray();
                        datad2 = getArrayDialog(pharmacyStores, i, locationVal);
                        if (datad2 != null)
                            json.accumulate("aaData", datad2);
                    }

                }

                if (!json.has("aaData")) {
                    datad2 = new JSONArray();

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    json.accumulate("aaData", datad2);
                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            } else if (druguuidshow != null) {
                jsonsAll = new JSONArray();
                for (int i = 0; i < size; i++) {


                    if ((service.getPharmacyLocationsByUuid(pharmacyStores.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal) && (pharmacyStores.get(i).getCategory().getName().equalsIgnoreCase(category)))) {

                        if (druguuidshow.equals(pharmacyStores.get(i).getDrugs().getUuid())) {

                            jsonArray = new JSONArray();

                            jsonArray.put(pharmacyStores.get(i).getDrugs().getName());
                            jsonArray.put(pharmacyStores.get(i).getQuantity());
                            jsonArray.put(10);
                            jsonArray.put(pharmacyStores.get(i).getMaxLevel());
                            jsonArray.put(pharmacyStores.get(i).getMinLevel());

                            jsonArray.put(pharmacyStores.get(i).getExpireDate().toString().substring(0, 10));

                            jsonArray.put(pharmacyStores.get(i).getBatchNo());
                            jsonArray.put(pharmacyStores.get(i).getS11());
                            jsonArray.put(pharmacyStores.get(i).getDeliveryNo());

                            if (pharmacyStores.get(i).getSupplier() == null)
                                jsonArray.put(pharmacyStores.get(i).getSupplier());
                            else
                                jsonArray.put(pharmacyStores.get(i).getSupplier().getName());

                            jsonsAll.put(jsonArray);

                        }

                    }

                }

                response.getWriter().print(jsonsAll);
            } else if (bintotal != null) {
                total = 0;
                druguuid = null;
                drugname = null;
                HashMap test = new HashMap();
                HashMap add = new HashMap();


                size = pharmacyStoreList.size();
                for (int i = 0; i < size; i++) {
                    druguuid = null;
                    drugname = null;

                    if ((service.getPharmacyLocationsByUuid(pharmacyStoreList.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal))) {

                        total =0;

                        druguuid = pharmacyStoreList.get(i).getDrugs().getUuid();
                        drugname = pharmacyStoreList.get(i).getDrugs().getName();


                        for (int t = 0; t < size; t++) {
                            if (pharmacyStoreList.get(i).getDrugs().getName().equalsIgnoreCase(pharmacyStoreList.get(t).getDrugs().getName())) {

                                if ((service.getPharmacyLocationsByUuid(pharmacyStoreList.get(t).getLocation()).getName()
                                        .equalsIgnoreCase(locationVal))) {
                                total += pharmacyStoreList.get(t).getQuantity();

                                    System.out.println(pharmacyStoreList.get(i).getDrugs().getName()+"total=="+druguuid+"== "+total);
                                test.put(druguuid, total);
                                }

                            }

                        }
                    }


//

                }
//
                if (druguuid != null && drugname != null) {

                }
                for (int ch = 0; ch < size; ch++) {



                    if ((service.getPharmacyLocationsByUuid(pharmacyStoreList.get(ch).getLocation()).getName()
                            .equalsIgnoreCase(locationVal))) {

                        if (!add.containsKey(pharmacyStoreList.get(ch).getDrugs().getUuid())) {


                            if (test.containsKey(pharmacyStoreList.get(ch).getDrugs().getUuid())) {


                                add.put(pharmacyStoreList.get(ch).getDrugs().getUuid(), pharmacyStoreList.get(ch).getDrugs().getUuid());


                            data = new JSONArray();
                            data.put("<img src='/openmrs/moduleResources/pharmacy/images/details_open.png'/>");
                            data.put(pharmacyStoreList.get(ch).getDrugs().getUuid());
                            data.put(pharmacyStoreList.get(ch).getDrugs().getName());

                            data.put(test.get(pharmacyStoreList.get(ch).getDrugs().getUuid()));


                                newDate = new Date();
                            givenDate = pharmacyStoreList.get(ch).getExpireDate();

                            monthsDiff = ((givenDate.getYear() - newDate.getYear()) * 12)
                                    + (givenDate.getMonth() - newDate.getMonth());


                            if (monthsDiff <= 6) {

                                data.put(monthsDiff);
                            } else {
                                data.put("7");


                            }
                            if (pharmacyStoreList.get(ch).getQuantity() <= pharmacyStoreList.get(ch).getMinLevel()) {
                                data.put("8");

                            }
                                else
                            {
                                data.put("7");

                            }

                            json.accumulate("aaData", data);

                        }


                    }
                    }
                }

                if (!json.has("aaData")) {

                    data = new JSONArray();
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");


                    json.accumulate("aaData", data);
                }


                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            } else {


                for (int i = 0; i < size; i++) {

                    if (pharmacyStores.get(i).getCategory() != null) {
                        if (category.equals("-111")) {

                        } else if ((service.getPharmacyLocationsByUuid(pharmacyStores.get(i).getLocation()).getName()
                                .equalsIgnoreCase(locationVal))
                                && (pharmacyStores.get(i).getCategory().getName().equalsIgnoreCase(category))) {

                            JSONArray val = getArray(pharmacyStores, i, locationVal);
                            if (val != null)
                                json.accumulate("aaData", val);

                        }
                        if (exit)
                            break;
                    }

                    data = new JSONArray();
                }

                if (!json.has("aaData")) {

                    data = new JSONArray();
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    json.accumulate("aaData", data);
                }

                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            }
            exit = false;


            response.flushBuffer();
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
        unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
        uuid= request.getParameter("uuid");
        serviceLocation = Context.getLocationService();

        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
         //log.info("the drug id is+++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+unitPrice);

        updateInventory(drugId,quantity,unitPrice,locationVal);

    }

    public synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size, String location) {
        // show all details for bincard
        if (s11number != null) {

            if (!s11number.equalsIgnoreCase("all")) {
                if ((Integer.parseInt(s11number) == pharmacyStore.get(size).getS11())) {
                    if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                            .equalsIgnoreCase(location)) {

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
                        data.put(pharmacyStore.get(size).getUuid());

                        data.put(pharmacyStore.get(size).getDrugs().getName());

                        data.put(pharmacyStore.get(size).getQuantity());

                        data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                        data.put(pharmacyStore.get(size).getBatchNo());
                        data.put(pharmacyStore.get(size).getDrugs().getDrugId());
                        data.put(pharmacyStore.get(size).getUnitPrice());

                        if (deletePharmacy) {
                            data.put("void");
                            deletePharmacy = false;
                        } else
                            data.put("");
                        return data;
                    }
                }
            } else {

                if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                        .equalsIgnoreCase(location)) {

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
                    data.put(pharmacyStore.get(size).getUuid());

                    data.put(pharmacyStore.get(size).getDrugs().getName());

                    data.put(pharmacyStore.get(size).getQuantity());

                    data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getDrugs().getDrugId());
                    data.put(pharmacyStore.get(size).getUnitPrice());

                    if (deletePharmacy) {
                        data.put("void");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    return data;
                }


            }
        } else if (filter.length() > 2) {
            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {
                if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                        .equalsIgnoreCase(location)) {

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
                    data.put(pharmacyStore.get(size).getUuid());

                    data.put(pharmacyStore.get(size).getDrugs().getName());

                    data.put(pharmacyStore.get(size).getQuantity());

                    data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getDrugs().getDrugId());
                    data.put(pharmacyStore.get(size).getUnitPrice());

                    if (deletePharmacy) {
                        data.put("void");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    return data;
                }
            } else {
                //exit=true;

                return null;

            }

        } else {


            if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                    .equalsIgnoreCase(location)) {
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


                data = new JSONArray();
                if (editPharmacy) {

                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");
                data.put(pharmacyStore.get(size).getUuid());

                data.put(pharmacyStore.get(size).getDrugs().getName());

                data.put(pharmacyStore.get(size).getQuantity());

                data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                data.put(pharmacyStore.get(size).getBatchNo());
                data.put(pharmacyStore.get(size).getDrugs().getDrugId());
                data.put(pharmacyStore.get(size).getUnitPrice());

                if (deletePharmacy) {
                    data.put("void");
                    deletePharmacy = false;
                } else
                    data.put("");
                return data;
            }

        }
        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStore> pharmacyStore, int size, String location) {
        if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                .equalsIgnoreCase(location)) {
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

                if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid()
                        .equals(pharmacyStore.get(size).getDrugs().getUuid())) {
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

    public synchronized String getDropDown(List<PharmacyStore> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
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
    public boolean  updateInventory(int drugId,int Qnty,Double unitP,String val){

        DrugDispenseSettings dispenseSettings;
        Drug drug = new Drug(drugId);
        Date date = new Date();
        dispenseSettings= service.getDrugDispenseSettingsByDrugId(drug);
       // log.info("the date now is++++++++++++++++++++++++++++++++"+date);

        if(dispenseSettings.getLocation().getName().equalsIgnoreCase(val)){
            PharmacyStore pharmacyStore = dispenseSettings.getInventoryId();
            //log.info("drug id++++++++++++++++++++++++++++++++"+pharmacyStore.getDrugs().getDrugId());
         if(pharmacyStore!=null ){
            if(pharmacyStore.getDrugs().getDrugId()==drugId && Qnty<=0 || Qnty>0){

                pharmacyStore.setQuantity((Qnty));
                pharmacyStore.setLastEditDate(date);
                pharmacyStore.setUnitPrice(unitP);
                service.savePharmacyInventory(pharmacyStore);
            }

            }

        }


        return true;
    }
}
