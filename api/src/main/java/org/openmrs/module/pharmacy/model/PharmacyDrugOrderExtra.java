package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers PharmacyOrders
 */
public class PharmacyDrugOrderExtra extends BaseOpenmrsData {

    private Integer id;

    private double amount;
    private Integer pillCount;
    private Integer receiptNo;
    private Integer waiverNo;
    private double waiverAmount;
    private Integer invoiceNo;
    private String change;

    private String variableId;
    private String variableAns;


    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getVariableAns() {
        return variableAns;
    }

    public void setVariableAns(String variable_uuid_ans) {
        this.variableAns = variable_uuid_ans;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variable_uuid) {
        this.variableId = variable_uuid;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getWaiverAmount() {
        return waiverAmount;
    }

    public void setWaiverAmount(double waiverAmount) {
        this.waiverAmount = waiverAmount;
    }

    public int getWaiverNo() {
        return waiverNo;
    }

    public void setWaiverNo(int waiverNo) {
        this.waiverNo = waiverNo;
    }

    public int getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(int receiptNo) {
        this.receiptNo = receiptNo;
    }

    public int getPillCount() {
        return pillCount;
    }

    public void setPillCount(int pillCount) {
        this.pillCount = pillCount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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


}
