package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ContainerFactory;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@Authorized("Manage Pharmacy")
public class hivController {

    private static final Log log = LogFactory.getLog(hivController.class);
    private ContainerFactory containerFactory;
    private String[][] encdata;
    private ConceptService conceptService;
    private PharmacyService service;
    private String patientID = null;
    private String prescriber = null;
    private String pharmacyUser = null;
    private String question;
    private String question_ans;
    private boolean morethanOne = false;
    private String questionTwo;
    private String question_ansTwo;
    private String questionThree;
    private String question_ansThree;
    private String date;
    private String nextVisitDate;
    private String noOfMonths;
    private ArrayList<String> drugDispensed;
    private ArrayList<ArrayList<String>> drugAll;
    private boolean obsSave;
    private boolean drugsSave;
    private String save;
    private String userName;

    private String dispensedBy;
    private PharmacyEncounter pEncounter;

    PharmacyOrders ppharmacyOrders = null;
    PharmacyObs ppharmacyObs = null;
    List<PharmacyOrders> pharmacyOrders;
    List<PharmacyObs> pharmacyObs;
    List<PharmacyExtra> pharmacyExt;
    private int two;
    private int three;
    private int four;
    private int one;
    private int currentRegimen;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/saveEnc")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        // save all the ecounters needed
         save= request.getParameter("saveEnc");
        service = Context.getService(PharmacyService.class);
        List<DrugExtra> listdrug=service.getDrugExtra();
        List<PharmacyEncounterType> pharmacyEncounterType =service.getPharmacyEncounterType();

        if(pharmacyEncounterType.size()==0){

            PharmacyEncounterType   pharmacyEncounterType1 = new PharmacyEncounterType();
            pharmacyEncounterType1.setName("save");
            service.savePharmacyEncounterType(pharmacyEncounterType1);


        }
        else

        {
            for(int y=0;y<pharmacyEncounterType.size();y++){



                if(pharmacyEncounterType.get(y).getName().contentEquals(save))
                {

                }
                else
                {
                    PharmacyEncounterType   pharmacyEncounterType1 = new PharmacyEncounterType();
                    pharmacyEncounterType1.setName(save);
                    service.savePharmacyEncounterType(pharmacyEncounterType1);

                }



            }

        }


    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/hiv")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
    }

}
