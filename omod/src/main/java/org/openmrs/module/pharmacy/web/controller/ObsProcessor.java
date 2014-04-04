package org.openmrs.module.pharmacy.web.controller;

/**
 * Created with IntelliJ IDEA.
 * User: nelson
 * Date: 12/28/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ObsProcessor {


    private String concept;

    private String conceptAnswer;
    private String conceptCodedAnswer;



    /** default constructor */
    public ObsProcessor() {
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


    public String getConceptCodedAnswer() {
        return conceptCodedAnswer;
    }

    public void setConceptCodedAnswer(String conceptCodedAnswer) {
        this.conceptCodedAnswer = conceptCodedAnswer;
    }
}
