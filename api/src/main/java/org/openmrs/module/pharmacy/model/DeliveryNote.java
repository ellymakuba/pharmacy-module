package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * @author Ampath Developers PharmacyStore
 */
public class DeliveryNote extends BaseOpenmrsData {
    private Integer id;
    private Drug drugs;
    private Integer quantityIn;
    private Integer quantityOut;
    private PharmacyLocations location;
    private Date expireDate;
    private Double buyingPrice;
    private String batchNo;
    private  Double unitPrice;
    private DeliveryNoteTracker deliveryNoteTracker;
    /** default constructor */
    public DeliveryNote() {
    }

    /**
     * @return  id
     */
    public Integer getId() {

        return id;
    }

    /**
     * @param  id
     */

    public void setId(Integer id) {

        this.id = id;

    }

    public DeliveryNoteTracker getDeliveryNoteTracker() {
        return deliveryNoteTracker;
    }

    public void setDeliveryNoteTracker(DeliveryNoteTracker deliveryNoteTracker) {
        this.deliveryNoteTracker = deliveryNoteTracker;
    }

    /**
     * @return drug
     */
    public Drug getDrugs() {

        return drugs;
    }

    /**
     * @param drugsId
     */
    public void setDrugs(Drug drugsId) {

        this.drugs = drugsId;

    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }



    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @param location
     */
    public void setLocation(PharmacyLocations location) {

        this.location = location;

    }

    /**
     * @return location
     */
    public PharmacyLocations getLocation() {

        return location;
    }


    /**
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {

        this.expireDate = expireDate;

    }

    /**
     * @return expireDate
     */
    public Date getExpireDate() {

        return expireDate;
    }


    /**
     * @return quantityIn
     */
    public Integer getQuantityIn() {

        return quantityIn;
    }

    /**
     * @param quantityIn
     */
    public void setQuantityIn(Integer quantityIn) {

        this.quantityIn = quantityIn;

    }

    /**
     * @return quantityOut
     */
    public Integer getQuantityOut() {

        return quantityOut;
    }

    /**
     * @param quantityOut
     */
    public void setQuantityOut(Integer quantityOut) {

        this.quantityOut = quantityOut;

    }


    /**
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {

        this.batchNo = batchNo;

    }

    /**
     * @return batchNo
     */
    public String getBatchNo() {

        return batchNo;
    }

}
