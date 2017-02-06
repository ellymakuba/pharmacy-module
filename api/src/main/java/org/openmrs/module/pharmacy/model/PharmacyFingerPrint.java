package org.openmrs.module.pharmacy.model;
import org.openmrs.BaseOpenmrsData;

import java.util.UUID;

public class PharmacyFingerPrint extends BaseOpenmrsData{
    private Integer id;
    private String patientUUID;
    private String fingerprint;



    public PharmacyFingerPrint(){
    }
    public PharmacyFingerPrint(String patientUUID, String fingerprint){

        if (getUuid()==null) {
            setUuid(UUID.randomUUID().toString());
        }
        this.patientUUID = patientUUID;
        this.fingerprint = fingerprint;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PharmacyFingerPrint pharmacyFingerPrint = (PharmacyFingerPrint) o;
        return this.getId().equals(pharmacyFingerPrint.getId());
    }

    @Override
    public String toString() {
        return "PharmacyFingerPrint{" +
                "id=" + id +
                ", patientUUID=" + getPatientUUID() +
                ", fingerprint='" + getFingerprint()+ '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientUUID() {
        return patientUUID;
    }

    public void setPatientUUID(String patientUUID) {
        this.patientUUID = patientUUID;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}

