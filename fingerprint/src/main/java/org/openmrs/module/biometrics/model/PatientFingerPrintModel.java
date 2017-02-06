package org.openmrs.module.biometrics.model;

public class PatientFingerPrintModel {
    private String patientUUID;
    private String fingerprintTemplate;
    private int id;
    private String givenName;
    private String familyName;
    private String gender;

    public PatientFingerPrintModel() {
    }

    public PatientFingerPrintModel(String patientUUID, String fingerprintTemplate, int id,
                                   String givenName, String familyName, String gender) {
        this.patientUUID = patientUUID;
        this.fingerprintTemplate = fingerprintTemplate;
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
    }

    public String getPatientUUID() {
        return patientUUID;
    }

    public String getFingerprintTemplate() {
        return fingerprintTemplate;
    }

    public int getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }


    public void setPatientUUID(String patientUUID) {
        this.patientUUID = patientUUID;
    }

    public void setFingerprintTemplate(String fingerprintTemplate) {
        this.fingerprintTemplate = fingerprintTemplate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
