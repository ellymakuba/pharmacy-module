package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * Created by root on 2/8/17.
 */
public class PharmacyStockAdjustment extends BaseOpenmrsData {
    private Integer id;
    private PharmacyLocations location;
    private Date date;
    private Drug drug;
    private String batchNo;
    private int quantity;
    private String comment;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PharmacyLocations getLocation() {
        return location;
    }

    public void setLocation(PharmacyLocations location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
