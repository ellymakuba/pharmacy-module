package org.openmrs.module.pharmacy.web.controller;

/**
 * Created with IntelliJ IDEA.
 * User: nelson
 * Date: 12/28/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class EncounterProcessor {


    private String encounterType;
    private String prescriber;
    private String encounterDate;
    private String form;
    private String formVersion;
    private String patientId;
    private  String nextVisitDate;
    private String duration;






    /** default constructor */
    public EncounterProcessor() {
    }

    /**
     * @return  id
     */
    public String getEncounterType() {

        return encounterType;
    }

    /**
     * @param  encounterType
     */

    public void setEncounterType(String encounterType) {

        this.encounterType = encounterType;

    }

    /**
     * @return  encounterDate
     */
    public String getEncounterDate() {

        return encounterDate;
    }

    /**
     * @param  encounterDate
     */

    public void setEncounterDate(String encounterDate) {

        this.encounterDate = encounterDate;


    }

    /**
     * @return  form
     */
    public String getForm() {

        return form;
    }

    /**
     * @param  form
     */

    public void setForm(String form) {

        this.form = form;


    }
    /**
     * @return  formVersion
     */
    public String getFormVersion() {

        return formVersion;
    }

    /**
     * @param  formVersion
     */

    public void setFormVersion(String formVersion) {

        this.formVersion = formVersion;


    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(String nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrescriber() {
        return prescriber;
    }

    public void setPrescriber(String prescriber) {
        this.prescriber = prescriber;
    }
}
