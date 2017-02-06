package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

import org.openmrs.Patient;

import java.util.Date;

/**
 * @author Ampath Developers Pharmacy encounters
 */
public class PharmacyEncounter extends BaseOpenmrsData {
    private Integer id;
    private String formName;
    private Patient person;
    private PharmacyLocations location;
    private PharmacyEncounterType encounter;
    private Date dateTime;
    private Date nextVisitDate;
    private int duration;
    private String regimenCode;
    private String regimenName;
    private Integer display;
    private Double totalAmount;
    private Integer paymentStatus;
    private Double discount;
    private String comment;
    private Integer PMTCTChecked;
    private Integer PEPChecked;
    private Integer regimenChanged;
    private Integer regimenInitiation;

    public Integer getRegimenInitiation() {
        return regimenInitiation;
    }

    public void setRegimenInitiation(Integer regimenInitiation) {
        this.regimenInitiation = regimenInitiation;
    }

    public Integer getRegimenChanged() {
        return regimenChanged;
    }

    public void setRegimenChanged(Integer regimenChanged) {
        this.regimenChanged = regimenChanged;
    }

    public Integer getPMTCTChecked() {
        return PMTCTChecked;
    }

    public void setPMTCTChecked(Integer PMTCTChecked) {
        this.PMTCTChecked = PMTCTChecked;
    }

    public Integer getPEPChecked() {
        return PEPChecked;
    }

    public void setPEPChecked(Integer PEPChecked) {
        this.PEPChecked = PEPChecked;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double disount) {
        this.discount = disount;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRegimenName() {
        return regimenName;
    }

    public void setRegimenName(String regimenName) {
        this.regimenName = regimenName;
    }

    public String getRegimenCode() {
        return regimenCode;
    }

    public void setRegimenCode(String regimenCode) {
        this.regimenCode = regimenCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public Date getNextVisitDate() {
        return nextVisitDate;
    }
    public void setNextVisitDate(Date nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /** default constructor */
    public PharmacyEncounter() {
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
     * @return dateTime
     */
    public Date getDateTime() {

        return dateTime;
    }

    /**
     * @param  dateTime
     */

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }


    /**
     * @return encounter
     */
    public PharmacyEncounterType getEncounter() {

        return encounter;
    }

    /**
     * @param  encounter
     */

    public void setEncounter(PharmacyEncounterType encounter) {

        this.encounter = encounter;

    }

    /**
     * @return person
     */
    public Patient getPerson() {

        return person;
    }

    /**
     * @param person
     */

    public void setPerson(Patient person) {

        this.person = person;

    }

    /**
     * @return location
     */
    public PharmacyLocations getLocation() {

        return location;
    }

    /**
     * @param location
     */

    public void setLocation(PharmacyLocations location) {

        this.location = location;

    }

    /**
     * @return form name
     */
    public String getFormName() {

        return formName;
    }

    /**
     * @param formName
     */

    public void setFormName(String formName) {

        this.formName = formName;

    }

}
