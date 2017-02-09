package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * Created by elly on 7/14/15.
 */
public class GeneratePharmacyInventoryQuantities extends BaseOpenmrsData{
    private Integer id;
    private int stockQuantities;
    private Drug drug;
    private String pharmacyLocationUUID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStockQuantities() {
        return stockQuantities;
    }
    public void setStockQuantities(int stockQuantities) {
        this.stockQuantities = stockQuantities;
    }
    public Drug getDrug() {
        return drug;
    }
    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getPharmacyLocationUUID() {
        return pharmacyLocationUUID;
    }

    public void setPharmacyLocationUUID(String pharmacyLocationUUID) {
        this.pharmacyLocationUUID = pharmacyLocationUUID;
    }
}
