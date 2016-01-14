package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elly on 8/3/15.
 */
@Controller
public class patientEncounterDetailsController {

    private static final Log log= LogFactory.getLog("patientEncounterDetailsController.class");
    public PharmacyService service;
    private PharmacyEncounter pharmacyEncounter;
    private PharmacyDrugOrder pharmacyDrugOrder;

    @RequestMapping(method= RequestMethod.GET,value="module/pharmacy/patientEncounterDetailsVoid")
    public void loadPatientEncounterDetailsByEncounterUUID(HttpServletRequest request,HttpServletResponse response){
        service=Context.getService(PharmacyService.class);
        String encounterUUID =request.getParameter("encounterUUID");

        List<PharmacyOrders> pharmacyOrdersList;
        List<PharmacyOrders> pharmacyOrdersListToSave=new ArrayList<PharmacyOrders>();
        List<PharmacyObs> pharmacyObsList;
        List<PharmacyObs> pharmacyObsListToSave=new ArrayList<PharmacyObs>();
        List<PharmacyDrugOrder> pharmacyDrugOrderList=new ArrayList<PharmacyDrugOrder>();
        List<PharmacyStore> pharmacyStoreList=new ArrayList<PharmacyStore>();

        pharmacyEncounter=service.getPharmacyEncounterByUuid(encounterUUID);
        pharmacyOrdersList=service.getPharmacyOrdersByEncounterId(pharmacyEncounter);
        for(PharmacyOrders pharmacyOrderInstance:pharmacyOrdersList) {
            if (pharmacyOrderInstance.getConcept() !=null) {
                pharmacyDrugOrder = service.getPharmacyDrugOrdersByOrders(pharmacyOrderInstance);
                pharmacyOrderInstance.setVoided(true);
                pharmacyOrdersListToSave.add(pharmacyOrderInstance);

                pharmacyDrugOrder.setVoided(true);
                pharmacyDrugOrderList.add(pharmacyDrugOrder);

                pharmacyObsList = service.getPharmacyObsByPharmacyOrder(pharmacyOrderInstance);
                for (PharmacyObs pharmacyObsInstance : pharmacyObsList) {
                    if (pharmacyObsInstance.getValue_drug() != null) {
                        Drug drug = Context.getConceptService().getDrugByUuid(pharmacyObsInstance.getValue_drug().getUuid());
                        DrugDispenseSettings drugDispenseSettings = service.getDrugDispenseSettingsByDrugIdAndLocation(drug.getDrugId(), pharmacyEncounter.getLocation().getUuid());
                        if (drugDispenseSettings != null) {
                            PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                            if (pharmacyStore != null) {
                                pharmacyStore.setQuantity(pharmacyStore.getQuantity() + pharmacyDrugOrder.getQuantityGiven());
                                pharmacyStoreList.add(pharmacyStore);
                            }
                        }
                    }
                    pharmacyObsInstance.setVoided(true);
                    pharmacyObsListToSave.add(pharmacyObsInstance);
                }
            }
            else{
                pharmacyOrderInstance.setVoided(true);
                pharmacyOrdersListToSave.add(pharmacyOrderInstance);
                pharmacyObsList = service.getPharmacyObsByPharmacyOrder(pharmacyOrderInstance);
                for (PharmacyObs pharmacyObsInstance : pharmacyObsList) {
                    pharmacyObsInstance.setVoided(true);
                    pharmacyObsListToSave.add(pharmacyObsInstance);
                }
            }
        }
        pharmacyEncounter.setVoided(true);
        service.savePharmacyEncounter(pharmacyEncounter);
        service.savePharmacyOrders(pharmacyOrdersListToSave);
        service.savePharmacyDrugOrders(pharmacyDrugOrderList);
        service.savePharmacyObs(pharmacyObsListToSave);
        service.savePharmacyInventory(pharmacyStoreList);

    }

}
