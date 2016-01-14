package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.Drug;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.simple.parser.ContainerFactory;

import org.springframework.ui.ModelMap;
@Controller
public class S11FormController {
    private static  final Log log=LogFactory.getLog(InventoryMetaDataController.class);
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/resources/subpages/s11Form")
    public void inventoryMetaDataGetProcessor(ModelMap map,HttpServletRequest request) throws java.text.ParseException, IOException {

    }
}
