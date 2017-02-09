package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * Created by elly on 2/29/16.
 */
public class DeliveryNoteTracker extends BaseOpenmrsData{
    private Integer id;
    private PharmacyLocations pharmacyLocation;
    private PharmacySupplier pharmacySupplier;
    private Integer deliveryNoteNumber;
    public DeliveryNoteTracker(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PharmacySupplier getPharmacySupplier() {
        return pharmacySupplier;
    }

    public void setPharmacySupplier(PharmacySupplier pharmacySupplier) {
        this.pharmacySupplier = pharmacySupplier;
    }

    public Integer getDeliveryNoteNumber() {
        return deliveryNoteNumber;
    }

    public void setDeliveryNoteNumber(Integer deliveryNoteNumber) {
        this.deliveryNoteNumber = deliveryNoteNumber;
    }

    public PharmacyLocations getPharmacyLocation() {
        return pharmacyLocation;
    }

    public void setPharmacyLocation(PharmacyLocations pharmacyLocation) {
        this.pharmacyLocation = pharmacyLocation;
    }

}
