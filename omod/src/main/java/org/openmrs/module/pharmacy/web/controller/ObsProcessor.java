package org.openmrs.module.pharmacy.web.controller;

import org.openmrs.Drug;

public class ObsProcessor {
    private String concept;
    private String conceptAnswer;
    private String conceptCodedAnswer;
    private String valueText;
    private String valueCoded;
    private String obsGroupID;
    private String valueNumeric;
    private String comment;
    private Drug valueDrug;
    private Integer rowCounter;
    /** default constructor */
    public ObsProcessor() {
    }

    public Integer getRowCounter() {
        return rowCounter;
    }

    public void setRowCounter(Integer rowCounter) {
        this.rowCounter = rowCounter;
    }

    public Drug getValueDrug() {
        return valueDrug;
    }

    public void setValueDrug(Drug valueDrug) {
        this.valueDrug = valueDrug;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(String valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getObsGroupID() {
        return obsGroupID;
    }

    public void setObsGroupID(String obsGroupID) {
        this.obsGroupID = obsGroupID;
    }

    public String getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(String valueCoded) {
        this.valueCoded = valueCoded;
    }

    /**
     * @return  concept
     */
    public String getConcept() {

        return concept;
    }

    /**
     * @param  concept
     */

    public void setConcept(String concept) {

        this.concept = concept;

    }

    /**
     * @return  conceptAnswer
     */
    public String getConceptAnswer() {

        return conceptAnswer;
    }

    /**
     * @param  conceptAnswer
     */

    public void setConceptAnswer(String conceptAnswer) {

        this.conceptAnswer = conceptAnswer;


    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getConceptCodedAnswer() {
        return conceptCodedAnswer;
    }

    public void setConceptCodedAnswer(String conceptCodedAnswer) {
        this.conceptCodedAnswer = conceptCodedAnswer;
    }
}
