package org.openmrs.module.pharmacy.model;

import org.openmrs.Drug;
import org.openmrs.BaseOpenmrsData;
public class PharmacyTemporaryInventory extends BaseOpenmrsData{
    private Integer id;
    private String drugName;
    private String dose;
    private Double unitPrice;
    private PharmacyLocations locations;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PharmacyLocations getLocations() {
        return locations;
    }

    public void setLocations(PharmacyLocations locations) {
        this.locations = locations;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
