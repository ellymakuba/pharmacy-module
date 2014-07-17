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

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
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
