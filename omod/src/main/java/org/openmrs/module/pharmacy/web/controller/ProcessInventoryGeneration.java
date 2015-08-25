package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.pharmacy.model.GeneratePharmacyInventoryQuantities;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.openmrs.api.context.Context;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by elly on 7/14/15.
 */
public class ProcessInventoryGeneration extends AbstractTask {
    public static final Log log=LogFactory.getLog(ProcessInventoryGeneration.class);
    private PharmacyService service= Context.getService(PharmacyService.class);
    private GeneratePharmacyInventoryQuantities generation;
    private List<PharmacyStore> pharmacyStoreList;

    private List<GeneratePharmacyInventoryQuantities> listOfInventoryToUpdate=new ArrayList<GeneratePharmacyInventoryQuantities>();
    public ProcessInventoryGeneration(){
        this.generation=new GeneratePharmacyInventoryQuantities();
    }
    @Override
    public void execute(){
        Context.openSession();
        pharmacyStoreList=service.getPharmacyInventory();
        for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
            generation=new GeneratePharmacyInventoryQuantities();
            generation.setDrug(pharmacyStoreInstance.getDrugs());
            generation.setStockQuantities(pharmacyStoreInstance.getQuantity());
            generation.setPharmacyLocationUUID(pharmacyStoreInstance.getLocation().getUuid());
            listOfInventoryToUpdate.add(generation);
        }
        service.saveGeneratedInventoryQuantities(listOfInventoryToUpdate);
        Context.closeSession();

    }
}
