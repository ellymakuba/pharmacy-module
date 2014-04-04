package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * Created with IntelliJ IDEA.
 * User: elly
 * Date: 11/20/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class RfpGeneralReport extends BaseOpenmrsData {
    private Integer id;
    private Drug drug;

    public RfpGeneralReport() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
