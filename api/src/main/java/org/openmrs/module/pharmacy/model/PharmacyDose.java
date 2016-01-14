package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
/**
 *
 *  @author Ampath elly makuba
 */
public class PharmacyDose  extends BaseOpenmrsData{
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
