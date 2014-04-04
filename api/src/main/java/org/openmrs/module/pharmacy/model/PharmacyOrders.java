package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

import java.util.Date;

/**
 * @author Ampath Developers PharmacyOrders
 */
public class PharmacyOrders extends BaseOpenmrsData {




    private Integer id;

    private String concept;

    private PharmacyEncounter pharmacyEncounter;







    private String instructions;

    private Date startDate;

    private Date autoEndDate;

    private boolean discontinued;

    private Date discontinuedDate;

    private String discontinuedReason;


    private boolean dispensed;


    /** default constructor */
    public PharmacyOrders() {
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
     * @param concept
     */

    public void setConcept(String concept) {

        this.concept = concept;

    }


    /**
     * @return concept
     */
    public String getConcept() {

        return concept;
    }

    /**
     * @return pharmacyEncounter
     */
    public PharmacyEncounter getPharmacyEncounter() {

        return pharmacyEncounter;
    }

    /**
     * @param pharmacyEncounter
     */

    public void setPharmacyEncounter(PharmacyEncounter pharmacyEncounter) {

        this.pharmacyEncounter = pharmacyEncounter;

    }



    /**
     * @return instructions
     */
    public String getInstructions() {

        return instructions;
    }

    /**
     * @param instructions
     */

    public void setInstructions(String instructions) {

        this.instructions = instructions;

    }

    /**
     * @return startDate
     */
    public Date getStartDate() {

        return startDate;
    }

    /**
     * @param startDate
     */

    public void setStartDate(Date startDate) {

        this.startDate = startDate;

    }

    /**
     * @return AutoEndDate
     */
    public Date getAutoEndDate() {

        return autoEndDate;
    }

    /**
     * @param AutoEndDate
     */

    public void setAutoEndDate(Date AutoEndDate) {

        this.autoEndDate = AutoEndDate;

    }

    /**
     * @return discontinued
     */
    public boolean getDiscontinued() {

        return discontinued;
    }

    /**
     * @param discontinued
     */

    public void setDiscontinued(boolean discontinued) {

        this.discontinued = discontinued;

    }

    /*
      * @return discontinuedDate
      */
    public Date getDiscontinuedDate() {

        return discontinuedDate;
    }

    /**
     * @param discontinuedDate
     */

    public void setDiscontinuedDate(Date discontinuedDate) {

        this.discontinuedDate = discontinuedDate;

    }

    /**
     * @return discontinuedReason
     */
    public String getDiscontinuedReason() {

        return discontinuedReason;
    }

    /**
     * @param discontinuedReason
     */

    public void setDiscontinuedReason(String discontinuedReason) {

        this.discontinuedReason = discontinuedReason;

    }



    /**
     * @return dispensed
     */
    public boolean getDispensed() {

        return dispensed;
    }

    /**
     * @param dispensed
     */

    public void setDispensed(boolean dispensed) {

        this.dispensed = dispensed;

    }
















}
