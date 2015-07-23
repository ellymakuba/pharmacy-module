package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Controller
public class DrugOutgoingController {

    private static final Log log = LogFactory.getLog(DrugOutgoingController.class);
    private List<PharmacyStoreOutgoing> listStoreOutgoing;
    private String originalbindrug;
    private String uuidfilter = null;
    private String reload = null;
    private String filterDrug = null;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private LocationService serviceLocation;
    private ConceptService serviceDrugs;
    private List<Drug> allDrugs;
    private String userLocation = null;
    private UserContext userService;
    public PharmacyService service;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int userLocationsize, sizeOfOutgoingEntries, sizeForAllDrugs;
    private JSONArray data;
    private JSONArray datad;
    private boolean found =false;
    private boolean exit = false;
    private boolean editPharmacy = false;
    private boolean deletePharmacy = false;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();
        serviceDrugs = Context.getConceptService();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        userLocationsize = pharmacyLocationUsersByUserName.size();
        userLocation = getUserLocation(userLocationsize, request);
        reload = request.getParameter("reload");
        filterDrug = request.getParameter("sSearch");
        listStoreOutgoing = service.getPharmacyStoreOutgoing();
        sizeOfOutgoingEntries = listStoreOutgoing.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");
        if (filterDrug.length() > 2) {
            originalbindrug = filterDrug;
            uuidfilter = getUuidFilter();
        }
        try {
            if (reload != null) {
                for (int i = 0; i < sizeOfOutgoingEntries; i++) {
                    jsonObject.accumulate("aaData", getArrayDialog(listStoreOutgoing, i));
                }

            } else {
                for (int i = 0; i < sizeOfOutgoingEntries; i++) {
                    // get only location that the user has selected
                    if (listStoreOutgoing.get(i).getDestination().getName().equalsIgnoreCase(userLocation)) {
                        jsonArray = getArray(listStoreOutgoing, i, userLocation);
                        if (jsonArray != null)
                            jsonObject.accumulate("aaData", jsonArray);

                    }
                    if (exit)
                        break;
                    data = new JSONArray();
                }
                if (!jsonObject.has("aaData")) {

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
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    jsonObject.accumulate("aaData", data);
                }

            }
            exit = false;
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);

            response.getWriter().print(jsonObject);

            response.flushBuffer();

        } catch (Exception e) {

            log.error("Error generated", e);
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String outgoingdrug = request.getParameter("outgoingdrug");
        String outgoingquantityin = request.getParameter("outgoingquantityin");
        String outgoingnumber = request.getParameter("outgoingnumber");
        String outgoingmax = request.getParameter("outgoingmax");
        String outgoingmin = request.getParameter("outgoingmin");
        String outgoingbatch = request.getParameter("outgoingbatch");
        String outgoings11 = request.getParameter("outgoings11");
        String requisition = request.getParameter("requisition");
        String issued = request.getParameter("issued");
        String authorized = request.getParameter("authorized");
        String answers = request.getParameter("answers");
        String outgoingexpire = request.getParameter("outgoingexpire");
        String outgoingreason = request.getParameter("outgoingreason");
        String outgoinguuidvoid = request.getParameter("outgoinguuidvoid");
        String outgoinguuidextra = request.getParameter("outgoinguuidextra");
        String destination = request.getParameter("destination");
        String location = request.getParameter("location");
        String supplier = request.getParameter("supplierout");
        String transactions = request.getParameter("transactions");
        String deliveryno = request.getParameter("delivery");
        String outgoingedit = request.getParameter("outgoingedit");
        String outgoinguuid = request.getParameter("outgoinguuid");
        String outgoingcom = request.getParameter("outgoingcom");
        DrugTransactions drugTransactions;
        PharmacyStore pharmacyStore;
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoing1;
        PharmacyStoreOutgoing pharmacyStoreOutgoing;
        listStoreOutgoing = new ArrayList<PharmacyStoreOutgoing>();
        originalbindrug = outgoingdrug;

        //Arrays of drug Ids that the user has given the approving a large number of drugs for outgoing purposes
        String[] drugId = request.getParameterValues("drugId");
        String[] drugQ = request.getParameterValues("quantity");
        String[] quantityToGive = request.getParameterValues("quantityToGive");
        service = Context.getService(PharmacyService.class);
        serviceLocation = Context.getLocationService();
        userService = Context.getUserContext();
        serviceDrugs = Context.getConceptService();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        userLocationsize = pharmacyLocationUsersByUserName.size();
        userLocation = getUserLocation(userLocationsize, request);
        if (outgoinguuidvoid == null) {

            uuidfilter = getUuidFilter();


        }
        if (outgoingedit != null) {

            if (outgoingedit.equalsIgnoreCase("false")) {

                pharmacyStoreOutgoing1 = service.getPharmacyStoreOutgoing();

                pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuidfilter));

                service.savePharmacyStoreOutgoing(outgoingEntryNewObject(pharmacyStoreOutgoing, outgoingquantityin, outgoingmax, transactions,
                        outgoingmin, outgoingbatch, outgoings11, outgoingexpire, destination, supplier));
            }
            else if (outgoingedit.equalsIgnoreCase("true")) {

                pharmacyStoreOutgoing = new PharmacyStoreOutgoing();

                // get the entry the user wants to edit using the outgoinguuid
                pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoinguuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreOutgoing.getCreator().getUserId())) {

                  service.savePharmacyStoreOutgoing(getPharmacyStoreOutgoingObject(pharmacyStoreOutgoing, outgoingquantityin, transactions, supplier, outgoingbatch, outgoings11, outgoingexpire, destination, location));

                }
            }

        } else if (outgoinguuidvoid != null) {

            pharmacyStoreOutgoing = new PharmacyStoreOutgoing();

            service.savePharmacyStoreOutgoing(getPharmacyStoreOutgoingObjectVoid(pharmacyStoreOutgoing, outgoinguuidvoid, outgoingreason));

        } else if (outgoinguuidextra != null) {

            log.info("ooooooooooooooooooooooooooooooone++++++++++++++++++++++++++++++++++++++++++++++++"+drugId+" "+quantityToGive);
             outGoingExtraDetailsForNewEntry(false, drugId, quantityToGive);
            log.info("twoooooooooooooooooooooooooooooo++++++++++++++++++++++++++++++++++++++++++++++++"+drugId+" "+quantityToGive);
        }

    }
    public synchronized JSONArray getArray(List<PharmacyStoreOutgoing> pharmacyStore, int size, String location) {

            if (filterDrug.length() > 2) {
            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {

                if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                        && (!pharmacyStore.get(size).getApproved())) {

                    data = new JSONArray();
                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    for (Role rl : xvc) {

                        if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))
                                || (rl.getRole().equals("	Authenticated "))) {

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

                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");

                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());
                    data.put(pharmacyStore.get(size).getDrugs().getName());
                    data.put(pharmacyStore.get(size).getQuantityIn());

                    data.put(pharmacyStore.get(size).getLocation().getName());
                    data.put(pharmacyStore.get(size).getDestination().getName());

                    data.put(pharmacyStore.get(size).getTransaction().getName());

                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getExpireDate());
                    data.put(pharmacyStore.get(size).getDeliveryNo());

                    data.put("");
                    if (pharmacyStore.get(size).getApproved()) {

                        data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                    } else
                        data.put("Approve");

                    if (deletePharmacy) {
                        data.put("Delete");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    data.put(pharmacyStore.get(size).getStatus());
                    data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                    data.put(pharmacyStore.get(size).getRequested().getUsername());
                    data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");
                    return data;
                }
            } else {
                return null;
            }

        } else {
            if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                    && (!pharmacyStore.get(size).getApproved())) {
                data = new JSONArray();
                Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                for (Role rl : xvc) {

                    if ((rl.getRole().equals("Pharmacy Administrator")) || (rl.getRole().equals("Provider"))
                            || (rl.getRole().equals("	Authenticated "))) {

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

                data.put("");
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getDrugs().getName());
                data.put(pharmacyStore.get(size).getQuantityIn());

                data.put(pharmacyStore.get(size).getLocation().getName());
                data.put(pharmacyStore.get(size).getDestination().getName());

                data.put(pharmacyStore.get(size).getTransaction().getName());

                if (pharmacyStore.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());

                data.put(pharmacyStore.get(size).getBatchNo());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getExpireDate());
                data.put(pharmacyStore.get(size).getDeliveryNo());

                data.put("");
                if (pharmacyStore.get(size).getApproved()) {

                    data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                } else
                    data.put("Approve");

                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");

                data.put(pharmacyStore.get(size).getStatus());
                data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                data.put(pharmacyStore.get(size).getRequested().getUsername());
                data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");

                return data;
            }
        }
        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStoreOutgoing> pharmacyStore, int size) {
        datad = new JSONArray();
        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());
        datad.put("");
        datad.put("");
        return datad;
    }
    public synchronized String getString(List<Drug> dname, int size, String text) {
        if ((dname.get(size).getName().equalsIgnoreCase(text))) {
            return dname.get(size).getUuid();
        }
        return null;
    }

    public String getUserLocation(int userLocationsize, HttpServletRequest request) {
        if (userLocationsize > 1) {
            userLocation = request.getSession().getAttribute("location").toString();

        } else {
            userLocation = pharmacyLocationUsersByUserName.get(0).getLocation();

        }

        return userLocation;
    }
    public PharmacyStoreOutgoing outgoingEntryNewObject(PharmacyStoreOutgoing pharmacyStoreOutgoing, String outgoingquantityin, String outgoingmax, String transactions,
                                                        String outgoingmin, String outgoingbatch, String outgoings11, String outgoingexpire, String destination, String supplier
    ) {
        pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuidfilter));
        pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));
        if (outgoingmax != null) {
            pharmacyStoreOutgoing.setMaxLevel(Integer.parseInt(outgoingmax));
        } else if (outgoingmax == null) {
            pharmacyStoreOutgoing.setMaxLevel(0);
        }
        if (outgoingmin != null) {
            pharmacyStoreOutgoing.setMinLevel(Integer.parseInt(outgoingmin));

        } else if (outgoingmin == null) {
            pharmacyStoreOutgoing.setMinLevel(0);
        }
        if (outgoingbatch != null) {
            pharmacyStoreOutgoing.setBatchNo(outgoingbatch);
        } else if (outgoingbatch == null) {
            pharmacyStoreOutgoing.setBatchNo("0");
        }
        if (outgoings11 != null) {
            pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));
        } else if (outgoings11 == null) {
            pharmacyStoreOutgoing.setS11(0);
        }
        Date date = null;
        try {
            if (outgoingexpire != null) {
                date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
            }
        } catch (ParseException e) {

            log.error("Error generated", e);
        }
        pharmacyStoreOutgoing.setExpireDate(date);
        serviceLocation = Context.getLocationService();
        pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
        pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(userLocation));
        pharmacyStoreOutgoing.setChangeReason(null);
        if (supplier == null) {
            pharmacyStoreOutgoing.setSupplier(null);

        } else
            pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));
        pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));
        return pharmacyStoreOutgoing;
    }


    public String getUuidFilter() {
        allDrugs = serviceDrugs.getAllDrugs();
        sizeForAllDrugs = allDrugs.size();
        for (int i = 0; i < sizeForAllDrugs; i++) {
            uuidfilter = getString(allDrugs, i, originalbindrug);
            if (uuidfilter != null)
                break;
        }

        return uuidfilter;
    }
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingObject(PharmacyStoreOutgoing pharmacyStoreOutgoing, String outgoingquantityin, String transactions, String supplier, String outgoingbatch, String outgoings11,
                                                                String outgoingexpire, String destination, String location) {


        pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));
        pharmacyStoreOutgoing.setMaxLevel(0);
        pharmacyStoreOutgoing.setMinLevel(0);
        if (outgoingbatch != null) {
            pharmacyStoreOutgoing.setBatchNo(outgoingbatch);

        } else if (outgoingbatch == null) {
            pharmacyStoreOutgoing.setBatchNo("0");
        }

        if (outgoings11 != null) {
            pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

        } else if (outgoings11 == null) {
            pharmacyStoreOutgoing.setS11(0);
        }

        Date date = null;
        try {
            if (outgoingexpire != null) {
                date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
            }
        } catch (ParseException e) {

            log.error("Error generated", e);
        }
        pharmacyStoreOutgoing.setExpireDate(date);
        serviceLocation = Context.getLocationService();

        pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
        pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(location));

        pharmacyStoreOutgoing.setChangeReason(null);

        if (supplier == null) {
            pharmacyStoreOutgoing.setSupplier(null);

        } else
            pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));

        pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));
        return pharmacyStoreOutgoing;

    }
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingObjectVoid(PharmacyStoreOutgoing pharmacyStoreOutgoingVoid, String outgoinguuidvoid, String outgoingreason) {
        pharmacyStoreOutgoingVoid = service.getPharmacyStoreOutgoingByUuid(outgoinguuidvoid);
        pharmacyStoreOutgoingVoid.setVoided(true);
        pharmacyStoreOutgoingVoid.setVoidReason(outgoingreason);
        return pharmacyStoreOutgoingVoid;
    }

    public void outGoingExtraDetailsForNewEntry(boolean canSave, String[] drugId, String[] quantityToGive) {
        PharmacyStoreApproved pharmacyStoreApproved;
        PharmacyStoreIncoming PharmacyStoreIncoming;
        List<DrugTransactions> listDrugTransactions = new ArrayList<DrugTransactions>();
        List<PharmacyStoreApproved> listStoreApproved = new ArrayList<PharmacyStoreApproved>();
        String outgoinguuidextra;
        DrugTransactions drugTransactions;
        PharmacyStore pharmacyStore;
        PharmacyStoreOutgoing pharmacyStoreOutgoing;

          log.info("drug id legnth is++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+drugId.length);
         for (int y = 0; y < drugId.length; y++) {
            pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
            pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(drugId[y]);
            pharmacyStore = new PharmacyStore();
            List<DrugDispenseSettings> list = service.getDrugDispenseSettings();
            int size = list.size();
            for (int i = 0; i < size; i++) {
               System.out.println(userLocation+ "---"+pharmacyStoreOutgoing.getDrugs().getDrugId());
                if((list.get(i).getLocation().getName().equalsIgnoreCase(userLocation))&&(pharmacyStoreOutgoing.getDrugs().getDrugId().equals(list.get(i).getDrugId().getDrugId()))){
               pharmacyStore = list.get(i).getInventoryId();
                }
            }
              if (Integer.parseInt(quantityToGive[y]) <= pharmacyStore.getQuantity()) {
                canSave = true;
                int num;
                if (Integer.parseInt(quantityToGive[y]) == pharmacyStoreOutgoing.getQuantityIn()) {
                    pharmacyStoreOutgoing.setApproved(true);
                    pharmacyStoreOutgoing.setQuantityIn(0);
                    num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));
                    pharmacyStore.setQuantity(num);
                    service.savePharmacyInventory(pharmacyStore);

                } else {

                    if (Integer.parseInt(quantityToGive[y]) < pharmacyStoreOutgoing.getQuantityIn()) {
                        pharmacyStoreOutgoing.setQuantityIn((pharmacyStoreOutgoing.getQuantityIn() - Integer.parseInt(quantityToGive[y])));
                    }

                   num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));


                    pharmacyStore.setQuantity(num);
                    service.savePharmacyInventory(pharmacyStore);

                }

                pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                pharmacyStoreOutgoing.setIssued(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                listStoreOutgoing.add(pharmacyStoreOutgoing);
                drugTransactions = new DrugTransactions();
                drugTransactions.setDrugs(pharmacyStoreOutgoing.getDrugs());
                drugTransactions.setQuantityIn(0);
                drugTransactions.setQuantityOut(Integer.parseInt(quantityToGive[y]));
                drugTransactions.setexpireDate(pharmacyStore.getExpireDate());
                drugTransactions.setComment("Give out");
                drugTransactions.setLocation(service.getPharmacyLocationsByName(userLocation));
                listDrugTransactions.add(drugTransactions);
                pharmacyStoreApproved = new PharmacyStoreApproved();
                pharmacyStoreApproved.setDrugs(pharmacyStoreOutgoing.getDrugs());
                pharmacyStoreApproved.setQuantityIn(Integer.parseInt(quantityToGive[y]));
                pharmacyStoreApproved.setCategory(pharmacyStoreOutgoing.getCategory());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getDestination());
                pharmacyStoreApproved.setLocation(pharmacyStoreOutgoing.getDestination());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getLocation());
                pharmacyStoreApproved.setTransaction(pharmacyStoreOutgoing.getTransaction());
                pharmacyStoreApproved.setIncoming(pharmacyStoreOutgoing.getIncoming());
                pharmacyStoreApproved.setOutgoing(pharmacyStoreOutgoing);
                pharmacyStoreApproved.setApproved(false);
                pharmacyStoreApproved.setS11(pharmacyStoreOutgoing.getS11());
                pharmacyStoreApproved.setVoided(pharmacyStoreOutgoing.getVoided());
                pharmacyStoreApproved.setMaxLevel(pharmacyStore.getMaxLevel());
                pharmacyStoreApproved.setMinLevel(pharmacyStore.getMinLevel());
                pharmacyStoreApproved.setBatchNo(pharmacyStore.getBatchNo());
                pharmacyStoreApproved.setExpireDate(pharmacyStore.getExpireDate());
                pharmacyStoreApproved.setDeliveryNo(pharmacyStore.getDeliveryNo());
                pharmacyStoreApproved.setRequested(pharmacyStoreOutgoing.getRequested());
                pharmacyStoreApproved.setAuthorized(pharmacyStoreOutgoing.getAuthorized());
                pharmacyStoreApproved.setIssued(pharmacyStoreOutgoing.getIssued());
                pharmacyStoreApproved.setStatus("Approved");
                PharmacyStoreIncoming = pharmacyStoreOutgoing.getIncoming();
                PharmacyStoreIncoming.setApproved(true);
                PharmacyStoreIncoming.setStatus("Apprroved");
                service.savePharmacyStoreIncoming(PharmacyStoreIncoming);
                listStoreApproved.add(pharmacyStoreApproved);
            }
        }
        outgoinguuidextra = null;

        if (canSave) {
            service.savePharmacyStoreOutgoing(listStoreOutgoing);
            service.savePharmacyStoreApproved(listStoreApproved);
            service.saveDrugTransactions(listDrugTransactions);
        }


    }

}
