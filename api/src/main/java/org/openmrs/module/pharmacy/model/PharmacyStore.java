package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * @author Ampath Developers PharmacyStore
 */
public class PharmacyStore extends BaseOpenmrsData {
    private Integer id;
    private Drug drugs;
    private Integer quantity;
    private Integer quantityIn;
    private Integer quantityOut;
    private Integer maxLevel;
    private Integer minLevel;
    private String changeReason;
    private String stockReason;
    private PharmacyLocations location;
    private Date expireDate;
    private PharmacyStoreIncoming incoming;
    private PharmacyStoreOutgoing outgoing;
    private PharmacyCategory category;
    private Double buyingPrice;
    private Double sellingPrice;
    private String batchNo;
    private Integer s11;
    private Integer deliveryNo;
    private Date lastEditDate;
    private  Double unitPrice;
    private PharmacyDose dose;
    private PharmacySupplier supplier;
    private PharmacyTransactionTypes transaction;
    private Integer unitsPerPack;
    private Integer stockTakeQuantityWaitingForApproval;
    private Integer stockTakeApprovalStatus;
    private Date lastStockTakeDate;
    private Integer systemQuantityOnLastStockTake;
    /** default constructor */
    public PharmacyStore() {
    }

    public Integer getStockTakeApprovalStatus() {
        return stockTakeApprovalStatus;
    }

    public void setStockTakeApprovalStatus(Integer stockTakeApprovalStatus) {
        this.stockTakeApprovalStatus = stockTakeApprovalStatus;
    }

    public Integer getStockTakeQuantityWaitingForApproval() {
        return stockTakeQuantityWaitingForApproval;
    }

    public void setStockTakeQuantityWaitingForApproval(Integer stockTakeQuantityWaitingForApproval) {
        this.stockTakeQuantityWaitingForApproval = stockTakeQuantityWaitingForApproval;
    }

    public Integer getUnitsPerPack() {
        return unitsPerPack;
    }

    public void setUnitsPerPack(Integer unitsPerPack) {
        this.unitsPerPack = unitsPerPack;
    }

    public PharmacyDose getDose() {
        return dose;
    }

    public void setDose(PharmacyDose dose) {
        this.dose = dose;
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

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
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
     * @return storeIncoming
     */
    public PharmacyStoreIncoming getIncoming() {

        return incoming;
    }

    /**
     * @param incoming
     */
    public void setIncoming(PharmacyStoreIncoming incoming) {

        this.incoming = incoming;

    }

    /**
     * @return storeOutgoing
     */
    public PharmacyStoreOutgoing getOutgoing() {

        return outgoing;
    }

    /**
     * @param outgoing
     */
    public void setOutgoing(PharmacyStoreOutgoing outgoing) {

        this.outgoing = outgoing;

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
     * @return changeReason
     */
    public String getChangeReason() {

        return changeReason;
    }

    /**
     * @param changeReason
     */
    public void setChangeReason(String changeReason) {

        this.changeReason = changeReason;

    }

    /**
     * @return stockReason
     */
    public String getStockReason() {

        return stockReason;
    }

    /**
     * @param stockReason
     */
    public void setStockReason(String stockReason) {

        this.stockReason = stockReason;

    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {

        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {

        this.quantity = quantity;

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
     * @return maxLevel
     */
    public Integer getMaxLevel() {

        return maxLevel;
    }

    /**
     * @param maxlevel
     */
    public void setMaxLevel(Integer maxlevel) {

        this.maxLevel = maxlevel;

    }

    /**
     * @return minLevel
     */
    public Integer getMinLevel() {

        return minLevel;
    }

    /**
     * @param minLevel
     */
    public void setMinLevel(Integer minLevel) {

        this.minLevel = minLevel;

    }


    /**
     * @return  deliveryNo
     */
    public Integer getDeliveryNo() {

        return deliveryNo;
    }

    /**
     * @param  DeliveryNo
     */

    public void setDeliveryNo(Integer DeliveryNo) {

        this.deliveryNo = DeliveryNo;

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


    public void setS11(Integer s11) {
        this.s11 = s11;
    }
    public Integer getS11() {
        return s11;
    }

    /**
     * @param supplier
     */
    public void setSupplier(PharmacySupplier supplier) {

        this.supplier = supplier;

    }

    /**
     * @return supplier
     */
    public PharmacySupplier getSupplier() {

        return supplier;
    }



    /**
     * @param transaction
     */
    public void setTransaction(PharmacyTransactionTypes transaction) {

        this.transaction = transaction;

    }

    /**
     * @return supplier
     */
    public PharmacyTransactionTypes getTransaction() {

        return transaction;
    }


    /**
     * @param category
     */
    public void setCategory(PharmacyCategory category) {

        this.category = category;

    }

    /**
     * @return category
     */
    public PharmacyCategory getCategory() {

        return category;
    }

    public Date getLastStockTakeDate() {
        return lastStockTakeDate;
    }

    public void setLastStockTakeDate(Date lastStockTakeDate) {
        this.lastStockTakeDate = lastStockTakeDate;
    }

    public Integer getSystemQuantityOnLastStockTake() {
        return systemQuantityOnLastStockTake;
    }

    public void setSystemQuantityOnLastStockTake(Integer systemQuantityOnLastStockTake) {
        this.systemQuantityOnLastStockTake = systemQuantityOnLastStockTake;
    }
}
