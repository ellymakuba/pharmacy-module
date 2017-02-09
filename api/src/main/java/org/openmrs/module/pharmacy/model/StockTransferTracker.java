package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * Created by elly on 2/4/16.
 */
public class StockTransferTracker extends BaseOpenmrsData {
    private Integer id;
    private PharmacyLocations pharmacyLocation;
    private boolean approved;
    public StockTransferTracker(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PharmacyLocations getPharmacyLocation() {
        return pharmacyLocation;
    }

    public void setPharmacyLocation(PharmacyLocations pharmacyLocation) {
        this.pharmacyLocation = pharmacyLocation;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
