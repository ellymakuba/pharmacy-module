package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyStoreApproved;
import org.openmrs.module.pharmacy.model.PharmacyStoreIncoming;
import org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Authorized("Manage Pharmacy")
public class HomeController {

    private static final Log log = LogFactory.getLog(HomeController.class);
    private PharmacyService service;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/home")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/home")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String approve=request.getParameter("approved");  // location request that have been approved
        String pending=request.getParameter("pending");    // location request that they have made but peding
        String requests=request.getParameter("requests");  // othe sites request to you.




        //
        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        try {
       if(approve!=null){

         String sOut="";

        List<PharmacyStoreApproved> listApprove=  service.getPharmacyStoreApprovedByLocation(service.getPharmacyLocationsByName(locationVal));
       for(int y=0;y<listApprove.size();y++){
           sOut += "<dl><dt></dt><dd >Drug :" +listApprove.get(y).getDrugs().getName()+"----"+listApprove.get(y).getQuantityIn()+"---Status:"+listApprove.get(y).getStatus()+"</dd></dl>";

       }
           response.getWriter().print(sOut);
       }
        else  if(requests!=null){

               String sOut="";

               List<PharmacyStoreIncoming> listApprove=  service.getPharmacyStoreIncomingByLocation(service.getPharmacyLocationsByName(locationVal));
               for(int y=0;y<listApprove.size();y++){
                   sOut += "<dl><dt></dt><dd >Drug :" +listApprove.get(y).getDrugs().getName()+"----"+listApprove.get(y).getQuantityIn()+"---Status:"+listApprove.get(y).getStatus()+"</dd></dl>";

               }
           response.getWriter().print(sOut);
       }
           else  if(pending!=null){

               String sOut="";

               List<PharmacyStoreOutgoing> listApprove=  service.getPharmacyStoreOutgoingByLocation(service.getPharmacyLocationsByName(locationVal));
               for(int y=0;y<listApprove.size();y++){
                   sOut += "<dl><dt></dt><dd >Drug :" +listApprove.get(y).getDrug().getName()+"----"+listApprove.get(y).getQuantityIn()+"---Status:"+listApprove.get(y).getStatus()+"</dd></dl>";

               }


               response.getWriter().print(sOut);

       }


       } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }













           }

}
