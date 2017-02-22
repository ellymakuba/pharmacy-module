package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * Created by root on 2/20/17.
 */
public class WaiverReason extends BaseOpenmrsData{
    private Integer id;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
