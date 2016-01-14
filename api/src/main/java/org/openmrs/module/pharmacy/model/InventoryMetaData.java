package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * Created by elly on 11/3/15.
 */
public class InventoryMetaData extends BaseOpenmrsData {
    private Integer id;
    private Drug drug;
    private PharmacyCategory category;
    private Double buyingPrice;
    private Double sellingPrice;
    private PharmacyLocations location;
    private Integer unitsInPack;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PharmacyCategory getCategory() {
        return category;
    }

    public void setCategory(PharmacyCategory category) {
        this.category = category;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public PharmacyLocations getLocation() {
        return location;
    }

    public void setLocation(PharmacyLocations location) {
        this.location = location;
    }

    public Integer getUnitsInPack() {
        return unitsInPack;
    }

    public void setUnitsInPack(Integer unitsInPack) {
        this.unitsInPack = unitsInPack;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
