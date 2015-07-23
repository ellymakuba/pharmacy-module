package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * Created with IntelliJ IDEA.
 * User: elly
 * Date: 7/6/15
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class S11 extends BaseOpenmrsData{
    private Integer id;
    private String s11No;
    private PharmacyLocations location;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getS11No() {
        return s11No;
    }

    public void setS11No(String s11No) {
        this.s11No = s11No;
    }

    public PharmacyLocations getLocation() {
        return location;
    }

    public void setLocation(PharmacyLocations location) {
        this.location = location;
    }
}
