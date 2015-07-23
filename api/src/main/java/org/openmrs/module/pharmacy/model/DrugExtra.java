package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;
import org.openmrs.User;

import java.util.Date;

/**
 * @author Ampath Developers Pharmacy drug formulation
 */
public class DrugExtra extends BaseOpenmrsData {
    private Integer id;
    private Integer pill;
    private double amount;
    private Integer receipt;
    private PharmacyGeneralVariables option;
    private Integer waiverNo;
    private double Amountw;
    private double discount;
    private Integer invoice;
    private String drugChange;
    private String chosenValue;
    private String dosingCalculation;
    private Drug drug;
    private Integer patientId;
    private String pharmacy_drug_uuid;
    private PharmacyLocations pharmacyLocations;
    private Integer quantitysold;
    private double total_amount;
    private double expected_amount;
    private double total_waived;
    private Integer display;
    private PharmacyEncounter pharmacyEncounter;
    private Integer previouslySoldQuantity;
    public PharmacyEncounter getPharmacyEncounter() {
        return pharmacyEncounter;
    }

    public void setPharmacyEncounter(PharmacyEncounter pharmacyEncounter) {
        this.pharmacyEncounter = pharmacyEncounter;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Integer getPreviouslySoldQuantity() {
        return previouslySoldQuantity;
    }

    public void setPreviouslySoldQuantity(Integer previouslySoldQuantity) {
        this.previouslySoldQuantity = previouslySoldQuantity;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    /** default constructor */
    public DrugExtra() {
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getExpected_amount() {
        return expected_amount;
    }

    public void setExpected_amount(double expected_amount) {
        this.expected_amount = expected_amount;
    }

    public double getTotal_waived() {
        return total_waived;
    }

    public void setTotal_waived(double total_waived) {
        this.total_waived = total_waived;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer pid) {
        this.patientId= pid;
    }

    public PharmacyLocations getPharmacyLocations() {
        return pharmacyLocations;
    }

    public void setPharmacyLocations(PharmacyLocations pharmacyLocations) {
        this.pharmacyLocations = pharmacyLocations;
    }

    public Integer getQuantitysold() {
        return quantitysold;
    }

    public void setQuantitysold(Integer quantitysold) {
        this.quantitysold = quantitysold;
    }

    /**
     * @return  id
     */
    public Integer getId() {

        return id;
    }

    public String getPharmacy_drug_uuid() {
        return pharmacy_drug_uuid;
    }

    public void setPharmacy_drug_uuid(String pharmacy_drug_uuid) {
        this.pharmacy_drug_uuid = pharmacy_drug_uuid;
    }

    /**
     * @param  id
     */

    public void setId(Integer id) {

        this.id = id;

    }

    /**
     * @return  option
     */
    public PharmacyGeneralVariables getOption() {

        return option;
    }

    /**
     * @param  option
     */

    public void setOption(PharmacyGeneralVariables option) {

        this.option = option;

    }


    /**
     * @return  pill
     */
    public Integer getPill() {

        return pill;
    }

    /**
     * @param  value
     */

    public void setPill(Integer value) {

        this.pill = value;

    }

    /**
     * @return  amount
     */
    public double getAmount() {

        return amount;
    }

    /**
     * @param  amount
     */

    public void setAmount(double amount) {

        this.amount = amount;

    }


    /**
     * @return  receipt
     */
    public Integer getReceipt() {

        return receipt;
    }

    /**
     * @param  receipt
     */

    public void setReceipt(Integer receipt) {

        this.receipt = receipt;

    }

    public Integer getWaiverNo() {
        return waiverNo;
    }

    public void setWaiverNo(Integer waiverNo) {
        this.waiverNo = waiverNo;
    }

    public double getAmountw() {
        return Amountw;
    }

    public void setAmountw(double wAmount) {
        this.Amountw = wAmount;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }


    public String getDrugChange() {
        return drugChange;
    }

    public void setDrugChange(String drugChange) {
        this.drugChange = drugChange;
    }

    public String getChosenValue() {
        return chosenValue;
    }

    public void setChosenValue(String chosenValue) {
        this.chosenValue = chosenValue;
    }

    public String getDosingCalculation() {
        return dosingCalculation;
    }

    public void setDosingCalculation(String dosingCalculation) {
        this.dosingCalculation = dosingCalculation;
    }
}
