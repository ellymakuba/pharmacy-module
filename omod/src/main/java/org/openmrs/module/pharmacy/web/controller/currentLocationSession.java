package org.openmrs.module.pharmacy.web.controller;

import org.openmrs.module.pharmacy.model.PharmacyLocations;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.service.PharmacyService;


/**
 * Created by elly on 7/16/15.
 */
public class currentLocationSession extends HttpServlet {
    private String locationName;
    private PharmacyLocations pharmacyLocation;
    private PharmacyService service = Context.getService(PharmacyService.class);
    public PharmacyLocations getCurrentLocationSession(HttpServletRequest request){
        locationName=request.getSession().getAttribute("location").toString();
        pharmacyLocation=service.getPharmacyLocationsByName(locationName);
       return pharmacyLocation;
    }
}
