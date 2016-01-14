package org.openmrs.module.pharmacy.web.controller;

import org.openmrs.Drug;

/**
 * Created with IntelliJ IDEA.
 * User: nelson
 * Date: 12/28/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MedicationProcessor {
    private String concept;
    private String conceptAnswer;
    private Drug drug;
    private String drugName;
    private String dose;
    private String other;
    private String units;
    private String frequency;
    private String quantity;
    private String pillcount;
    private String prescriber;
    private String dispensed;
    private String change;
    private String route;
    private String routeAnswer;
    private String frequencyAnswer;
    private String frequencyCodedAnswer;
    private String indicationId;
    private String indicationAnswerId;
    private String indicationCodedAnswerId;
    private String durationId;
    private String durationAnswerId;
    private String durationCodedAnswerId;
    private String amount;
    private String receiptNo;
    private String waiverNo;
    private String waiverAmount;
    private String invoiceNumber;
    private String dispensedQuantity;
    private String days;
    private String numberPerPack;
    private String dailyDose;
    private String packets;
    private String dosingCalculation;
    /** default constructor */
    public MedicationProcessor() {
    }

    /**
     * @return  concept
     */
    public String getConcept() {
        return concept;
    }

    public String getDispensedQuantity() {
        return dispensedQuantity;
    }

    public void setDispensedQuantity(String dispensedQuantity) {
        this.dispensedQuantity = dispensedQuantity;
    }

    /**
     * @param  concept
     */

    public void setConcept(String concept) {
        this.concept = concept;

    }

    /**
     * @return  conceptAnswer
     */
    public String getConceptAnswer() {

        return conceptAnswer;
    }

    /**
     * @param  conceptAnswer
     */

    public void setConceptAnswer(String conceptAnswer) {

        this.conceptAnswer = conceptAnswer;


    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    /**
     * @return  drugName
     */
    public String getDrugName() {

        return drugName;
    }

    /**
     * @param  drugname
     */

    public void setDrugName(String drugname) {

        this.drugName = drugname;


    }

    /**
     * @return  dose
     */
    public String getDose() {

        return dose;
    }




    /**
     * @param  dose
     */

    public void setDose(String dose) {

        this.dose = dose;

    }

    /**
     * @return  units
     */
    public String getUnits() {

        return units;
    }




    /**
     * @param  units
     */

    public void setUnits(String units) {

        this.units = units;

    }

    /**
     * @return  frequency
     */
    public String getFrequency() {

        return frequency;
    }

    /**
     * @param  frequency
     */

    public void setFrequency(String frequency) {

        this.frequency = frequency;


    }



    /**
     * @return  prescriber
     */
    public String getPrescriber() {

        return prescriber;
    }

    /**
     * @param  prescriber
     */

    public void setPrescriber(String prescriber) {

        this.prescriber = prescriber;


    }


    /**
     * @return  dispensed
     */
    public String getDispensed() {

        return dispensed;
    }

    /**
     * @param  dispensed
     */

    public void setDispensed(String dispensed) {

        this.dispensed = dispensed;


    }



    /**
     * @return  quantity
     *
     */
    public String getQuantity() {

        return quantity;
    }

    /**
     *
     * @param  quantity
     */

    public void setquantity(String quantity) {

        this.quantity = quantity;


    }

    /**
     * @return  pillcount
     *
     */
    public String getPillcount() {

        return pillcount;
    }

    /**
     *
     * @param  pillcount
     */

    public void setPillcount(String pillcount) {

        this.pillcount = pillcount;


    }


    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteAnswer() {
        return routeAnswer;
    }

    public void setRouteAnswer(String routeAnswer) {
        this.routeAnswer = routeAnswer;
    }

    public String getFrequencyAnswer() {
        return frequencyAnswer;
    }

    public void setFrequencyAnswer(String frequencyAnswer) {
        this.frequencyAnswer = frequencyAnswer;
    }

    public String getFrequencyCodedAnswer() {
        return frequencyCodedAnswer;
    }

    public void setFrequencyCodedAnswer(String frequencyCodedAnswer) {
        this.frequencyCodedAnswer = frequencyCodedAnswer;
    }

    public String getIndicationId() {
        return indicationId;
    }

    public void setIndicationId(String indicationId) {
        this.indicationId = indicationId;
    }

    public String getIndicationAnswerId() {
        return indicationAnswerId;
    }

    public void setIndicationAnswerId(String indicationAnswerId) {
        this.indicationAnswerId = indicationAnswerId;
    }

    public String getIndicationCodedAnswerId() {
        return indicationCodedAnswerId;
    }

    public void setIndicationCodedAnswerId(String indicationCodedAnswerId) {
        this.indicationCodedAnswerId = indicationCodedAnswerId;
    }

    public String getWaiverAmount() {
        return waiverAmount;
    }

    public void setWaiverAmount(String waiverAmount) {
        this.waiverAmount = waiverAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getWaiverNo() {
        return waiverNo;
    }

    public void setWaiverNo(String waiverNo) {
        this.waiverNo = waiverNo;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDurationId() {
        return durationId;
    }

    public void setDurationId(String durationId) {
        this.durationId = durationId;
    }

    public String getDurationAnswerId() {
        return durationAnswerId;
    }

    public void setDurationAnswerId(String durationAnswerId) {
        this.durationAnswerId = durationAnswerId;
    }

    public String getDurationCodedAnswerId() {
        return durationCodedAnswerId;
    }

    public void setDurationCodedAnswerId(String durationCodedAnswerId) {
        this.durationCodedAnswerId = durationCodedAnswerId;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getNumberPerPack() {
        return numberPerPack;
    }

    public void setNumberPerPack(String numberPerPack) {
        this.numberPerPack = numberPerPack;
    }

    public String getDailyDose() {
        return dailyDose;
    }

    public void setDailyDose(String dailyDose) {
        this.dailyDose = dailyDose;
    }

    public String getPackets() {
        return packets;
    }

    public void setPackets(String packets) {
        this.packets = packets;
    }

    public String getDosingCalculation() {
        return dosingCalculation;
    }

    public void setDosingCalculation(String d) {
        dosingCalculation = d;
    }
}
