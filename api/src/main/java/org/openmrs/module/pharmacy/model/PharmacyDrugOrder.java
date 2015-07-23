package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;
import java.util.Date;

public class PharmacyDrugOrder extends BaseOpenmrsData {
    private Integer id;
    private Date expected_next_visit_date;
    private PharmacyOrders orderUuid;
    private PharmacyStore drugInventoryUuid;
    private DrugExtra drugUuid;
    private double dose;
    private double equivalentDailyDose;
    private String units;
    private String frequency;
    private Integer quantityGiven;
    private Integer quantityPrescribed;
    private Patient person;
    private String formName;

    public Integer getQuantityGiven() {
        return quantityGiven;
    }

    public void setQuantityGiven(Integer quantityGiven) {
        this.quantityGiven = quantityGiven;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Patient getPerson() {
        return person;
    }

    public void setPerson(Patient person) {
        this.person = person;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Date getExpected_next_visit_date() {
        return expected_next_visit_date;
    }

    public void setExpected_next_visit_date(Date expected_next_visit_date) {
        this.expected_next_visit_date = expected_next_visit_date;
    }

    public double getEquivalentDailyDose() {
        return equivalentDailyDose;
    }

    public void setEquivalentDailyDose(double equivalent_daily_dose) {
        this.equivalentDailyDose = equivalent_daily_dose;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public DrugExtra getDrugUuid() {
        return drugUuid;
    }

    public void setDrugUuid(DrugExtra drug_extra_uuid) {
        this.drugUuid = drug_extra_uuid;
    }

    public PharmacyStore getDrugInventoryUuid() {
        return drugInventoryUuid;
    }

    public void setDrugInventoryUuid(PharmacyStore drug_inventory_uuid) {
        this.drugInventoryUuid = drug_inventory_uuid;
    }

    public PharmacyOrders getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(PharmacyOrders order_uuid) {
        this.orderUuid = order_uuid;
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


    public Integer getQuantityPrescribed() {
        return quantityPrescribed;
    }

    public void setQuantityPrescribed(Integer quantityPrescribed) {
        this.quantityPrescribed = quantityPrescribed;
    }
}
