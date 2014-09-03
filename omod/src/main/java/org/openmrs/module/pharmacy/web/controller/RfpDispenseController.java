package org.openmrs.module.pharmacy.web.controller;

import java.io.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.Privilege;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Collection;
import org.openmrs.Role;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


import javax.servlet.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.json.simple.JSONArray;
@Controller
public class RfpDispenseController {
    private static final Log log = LogFactory.getLog(RfpDispenseController.class);
    private ContainerFactory containerFactory;
    public PharmacyService service;
    private boolean found = false;
    private JSONArray supplierNames;
    private JSONArray datad2;
    private List<PharmacySupplier> pharmacySupplier;
    private int size;
    private JSONArray jsonArray;
    private Person person;
    private ArrayList<DrugExtra> dispensedModel;
    private PharmacyEncounter pharmacyEncounter;
    private   List<PharmacyDrugOrder>    listPharmacyDrugOrder ;
    private List<PharmacyObs> listAnotherPharmacyObs;
    private PharmacyDrugOrder drugOrder;
    private boolean allowDispense=false;
    private DrugExtra drugExtraToUpdate;
    private UserContext userService;
    private  Set<Privilege> userPrivileges;
    private Date encDate;
    private String prescriber = null;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/printReceipt")
    public void pageLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

            Document doc = new Document();
           response.setContentType("application/pdf");
           response.setHeader("Content-Disposition", "attachment; filename=" + "sampleDoc" + ".pdf");
            PdfWriter docWriter = null;

            DecimalFormat df = new DecimalFormat("0.00");
            File tmpFile = File.createTempFile("paymentReceipt", ".pdf");

            try {

                //special font sizes
                Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
                Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
                docWriter = PdfWriter.getInstance(doc , new FileOutputStream(tmpFile));

                //document header attributes
                doc.addAuthor("betterThanZero");
                doc.addCreationDate();
                doc.addProducer();
                doc.addCreator("MySampleCode.com");
                doc.addTitle("Report with Column Headings");
                doc.setPageSize(PageSize.LETTER);

                //open document
                doc.open();

                //create a paragraph
                Paragraph paragraph = new Paragraph("iText ® is a library that allows you to create and " +
                        "manipulate PDF documents. It enables developers looking to enhance web and other " +
                        "applications with dynamic PDF document generation and/or manipulation.");


                //specify column widths
                float[] columnWidths = {1.5f, 2f, 5f, 2f};
                //create PDF table with the given widths
                PdfPTable table = new PdfPTable(columnWidths);
                // set table width a percentage of the page width
                table.setWidthPercentage(90f);

                //insert column headings
                insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
                table.setHeaderRows(1);

                //insert an empty row
                insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
                //create section heading by cell merging
                insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
                double orderTotal, total = 0;

                //just some random data to fill
                for(int x=1; x<5; x++){

                    insertCell(table, "10010" + x, Element.ALIGN_RIGHT, 1, bf12);
                    insertCell(table, "ABC00" + x, Element.ALIGN_LEFT, 1, bf12);
                    insertCell(table, "This is Customer Number ABC00" + x, Element.ALIGN_LEFT, 1, bf12);

                    orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                    total = total + orderTotal;
                    insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

                }
                //merge the cells to create a footer for that section
                insertCell(table, "New York Total...", Element.ALIGN_RIGHT, 3, bfBold12);
                insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

                //repeat the same as above to display another location
                insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
                insertCell(table, "California Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
                orderTotal = 0;

                for(int x=1; x<7; x++){

                    insertCell(table, "20020" + x, Element.ALIGN_RIGHT, 1, bf12);
                    insertCell(table, "XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);
                    insertCell(table, "This is Customer Number XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);

                    orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                    total = total + orderTotal;
                    insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

                }
                insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

                //add the PDF table to the paragraph
                paragraph.add(table);
                // add the paragraph to the document
                doc.add(paragraph);

            }
            catch (DocumentException dex)
            {
                dex.printStackTrace();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                if (doc != null){
                    //close the document
                    doc.close();


                }
                if (docWriter != null){
                    //close the writer
                    docWriter.close();
                }

                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(tmpFile);
                IOUtils.copy(fileInputStream, outputStream);
                outputStream.flush();
                response.flushBuffer();
                fileInputStream.close();
               // tmpFile.delete();
            }

        }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormProcessor")
    public void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String quantity = request.getParameter("quantity");
        String checkBooleanToProceedWithDispense=request.getParameter("checkBoolean");
        String print="1";
        String receipt=request.getParameter("receipt");
        String displayUnclearedReceipts=request.getParameter("unclearedReceipts");
        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        List<PharmacyDrugOrder> listPharmacyDrugOrders = new ArrayList<PharmacyDrugOrder>();
        List<PharmacyOrders> listPharmacyOrders = new ArrayList<PharmacyOrders>();
        listAnotherPharmacyObs     = new  ArrayList<PharmacyObs>();
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        Collection<Role> userContextCollection = userService.getAuthenticatedUser().getAllRoles();
        for(Role roleWithPrivileges: userContextCollection ){
         userPrivileges=roleWithPrivileges.getPrivileges();
         //log.info("log all the role privileges+++++++++++++++++++++++++++"+roleWithPrivileges.getRole());
        }
        for(Privilege p: userPrivileges){
            //log.info("session user privileges +++++++++++++++++++++++++++++++++++++"+p.getName());
        }
        if(receipt !=null){
            Double totalReceipt=0.0;
            Double amountWaived=0.0;
            Integer waiverNo=0;
            dispensedModel = new ArrayList<DrugExtra>();
            List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
            Object obj = null;
            try {
                obj = parser.parse(jsonText);
                JSONArray dispenseInstanceArray = (JSONArray)obj;
                int dispenseInstanceArraySize=dispenseInstanceArray.size();
                for(int i=0; i<dispenseInstanceArraySize; i++){
                    DrugExtra drugExtra=new DrugExtra();
                    JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                    for(int j=0; j<rowInstance.size(); j++){
                        String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                        String key = myValues[0];
                        String value=myValues[1];
                        if(key.equalsIgnoreCase("drugReceipt")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantityToSubtract")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("drugExtraUUID")){
                            drugExtra.setUuid(value);
                        }
                        if(key.equalsIgnoreCase("receiptTotal")){
                            totalReceipt= Double.valueOf(value);
                        }
                        if(key.equalsIgnoreCase("waiverNo")){
                            if(value.length() > 0){
                             waiverNo= Integer.valueOf(value);
                            }
                        }
                        if(key.equalsIgnoreCase("amountWaived")){
                            amountWaived=Double.valueOf(value);
                        }
                    }
                    dispensedModel.add(drugExtra);
                }
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {

                        drugExtraToUpdate=service.getDrugExtraByUuid(dispensedModel.get(i).getUuid());
                        drugExtraToUpdate.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                        if(waiverNo !=0){
                        drugExtraToUpdate.setWaiverNo(waiverNo);
                        }
                        drugExtraToUpdate.setAmountw(amountWaived);
                        service.saveDrugExtraObject(drugExtraToUpdate);
                      substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal);
                    }
                }
                pharmacyEncounter =service.getPharmacyEncounterByUuid(receipt);
                pharmacyEncounter.setDisplay(1);
                pharmacyEncounter.setTotalAmount(totalReceipt);
                service.savePharmacyEncounter(pharmacyEncounter);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } //end of complete receipt model
        else if(checkBooleanToProceedWithDispense !=null){
            allowDispense=false;
            dispensedModel = new ArrayList<DrugExtra>();
            EncounterProcessor encounterProcessor = new EncounterProcessor();
            try {
                Object obj = parser.parse(jsonText);
                JSONArray dispenseInstanceArray = (JSONArray)obj;
                int dispenseInstanceArraySize=dispenseInstanceArray.size();
                for(int i=0; i<dispenseInstanceArraySize; i++){
                    DrugExtra drugExtra=new DrugExtra();
                    JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                    for(int j=0; j<rowInstance.size(); j++){
                        String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                        String key = myValues[0];
                        String value=myValues[1];
                        if(key.equalsIgnoreCase("patientId")){
                            String patient=service.getPatientByIdentifier(value) ;
                            person=Context.getPatientService().getPatient(Integer.parseInt(patient));
                            String patientID=person.getPersonId().toString();
                            encounterProcessor.setPatientId(patientID);
                        }
                        if(key.equalsIgnoreCase("dispenseFormDrug")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantity")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }
                        if(key.equalsIgnoreCase("amount")){
                            drugExtra.setAmount(Double.valueOf(value));
                        }

                    }
                    dispensedModel.add(drugExtra);
                }
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {
                        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug());
                        if(drugDispenseSettings !=null){
                        PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
                        if(pharmacyStore.getQuantity() > dispensedModel.get(i).getQuantitysold()) {
                            allowDispense=true;
                        }
                        }
                    }
                }
                response.getWriter().print("" + allowDispense);
            }
            catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }//end if checkbooleantop
        else{
        dispensedModel = new ArrayList<DrugExtra>();
        List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        Double receiptTotal=0.0;
        try {
            Object obj = parser.parse(jsonText);
            JSONArray dispenseInstanceArray = (JSONArray)obj;
            int dispenseInstanceArraySize=dispenseInstanceArray.size();
             for(int i=0; i<dispenseInstanceArraySize; i++){
                 DrugExtra drugExtra=new DrugExtra();
                 JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                 for(int j=0; j<rowInstance.size(); j++){
                     String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                     String key = myValues[0];
                     String value=myValues[1];
                      if(key.equalsIgnoreCase("patientId")){
                          String patient=service.getPatientByIdentifier(value) ;
                          person=Context.getPatientService().getPatient(Integer.parseInt(patient));
                          String patientID=person.getPersonId().toString();
                          encounterProcessor.setPatientId(patientID);
                      }
                     if(key.equalsIgnoreCase("totalAmount")){
                         receiptTotal=Double.valueOf(value);
                     }
                     if(key.equalsIgnoreCase("dispenseFormDrug")){
                         Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                         drugExtra.setDrug(drug);
                     }
                     if(key.equalsIgnoreCase("quantity")){
                         drugExtra.setQuantitysold(Integer.valueOf(value));
                     }
                     if(key.equalsIgnoreCase("amount")){
                         drugExtra.setAmount(Double.valueOf(value));
                     }
                 }
              dispensedModel.add(drugExtra);
             }
            PharmacyEncounter pharmacyEncounter = new PharmacyEncounter();
            String date_s = "2000-01-18 00:00:00.0";
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = null;
            try {
                date1 = dt.parse(date_s);
                pharmacyEncounter.setDateTime(date1);
                pharmacyEncounter.setNextVisitDate(date1);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
             EncounterProcessor encounterProcessor1=new EncounterProcessor();
            encounterProcessor1.setEncounterType("ADULTINITIAL");
            pharmacyEncounter.setEncounter(service.getPharmacyEncounterTypeByName(encounterProcessor1.getEncounterType()));
            pharmacyEncounter.setFormName("RFP");
            pharmacyEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));
            pharmacyEncounter.setDuration(10);
            pharmacyEncounter.setDisplay(0);
            pharmacyEncounter.setTotalAmount(receiptTotal);
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
            service.savePharmacyEncounter(pharmacyEncounter);
            for(int i=0; i<dispensedModel.size(); i++){
             DrugExtra drugExtra = new DrugExtra();
                 if(dispensedModel.get(i).getDrug() !=null) {
                     drugExtra.setPharmacyEncounter(pharmacyEncounter);
                     drugExtra.setDrug(dispensedModel.get(i).getDrug());
                     drugExtra.setPharmacyLocations(service.getPharmacyLocationsByName(locationVal));
                     drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                     drugExtra.setAmount(dispensedModel.get(i).getAmount());
                     listDrugExtra.add(drugExtra);

                     PharmacyOrders pharmacyOrders = new PharmacyOrders();
                     pharmacyOrders.setAutoEndDate(null);
                     pharmacyOrders.setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                     pharmacyOrders.setDiscontinued(false);
                     pharmacyOrders.setDiscontinuedDate(null);
                     pharmacyOrders.setDispensed(true);
                     pharmacyOrders.setInstructions(null);
                     try {
                         encDate=new SimpleDateFormat("MM/dd/yyyy").parse("00/00/0000");
                         pharmacyEncounter.setDateTime(encDate);
                     } catch (Exception e) {
                         e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                     }
                     pharmacyOrders.setStartDate(encDate);
                     pharmacyOrders.setPharmacyEncounter(pharmacyEncounter);
                     listPharmacyOrders.add(pharmacyOrders);

                     PharmacyDrugOrder pharmacyDrugOrder = new PharmacyDrugOrder();
                     pharmacyDrugOrder.setDose(1900);
                     pharmacyDrugOrder.setDrugUuid(drugExtra);
                     pharmacyDrugOrder.setDrugInventoryUuid(service.getDrugDispenseSettingsByDrugId(dispensedModel.get(i).getDrug()).getInventoryId());
                     pharmacyDrugOrder.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                     pharmacyDrugOrder.setEquivalentDailyDose(0);
                     pharmacyDrugOrder.setFormName("RFP");
                     Integer frequency=1888;
                     String units="1 tablet";
                     try{
                     SimpleDateFormat todayDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                     Date realDate=new Date();
                     String temporaryDateString =todayDate.format(realDate);
                     realDate=todayDate.parse(temporaryDateString);
                     pharmacyDrugOrder.setExpected_next_visit_date(realDate);
                     }
                     catch (ParseException e) {
                         e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                     }
                     pharmacyDrugOrder.setFrequency(frequency.toString());
                     pharmacyDrugOrder.setOrderUuid(pharmacyOrders);
                     pharmacyDrugOrder.setQuantityPrescribed(dispensedModel.get(i).getQuantitysold());
                     pharmacyDrugOrder.setQuantityGiven(dispensedModel.get(i).getQuantitysold());
                     pharmacyDrugOrder.setUnits(units);
                     listPharmacyDrugOrders.add(pharmacyDrugOrder);

                     PharmacyObs pharmacyObs = new PharmacyObs();
                     pharmacyObs.setConcept(dispensedModel.get(i).getDrug().getConcept().toString());
                     pharmacyObs.setDateStarted(null);
                     pharmacyObs.setDateStopped(null);
                     pharmacyObs.setComment(null);
                     pharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                     pharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
                     pharmacyObs.setPharmacyEncounter(pharmacyEncounter);
                     pharmacyObs.setPharmacyOrder(pharmacyOrders);
                     pharmacyObs.setValueCoded(0);
                     pharmacyObs.setValue_drug(Context.getConceptService().getDrugByNameOrId(dispensedModel.get(i).getDrug().getDrugId().toString()));
                     pharmacyObs.setValueBoolen(false);
                     pharmacyObs.setValueGroupId(null);
                     pharmacyObs.setValueText(null);
                     pharmacyObs.setValueNumeric(0.0);
                     pharmacyObs.setValueModifier(null);
                     pharmacyObs.setValueCodedName(null);
                     pharmacyObs.setValueDatetime(encDate);
                     listAnotherPharmacyObs.add(pharmacyObs);
              }

            }
            service.saveDrugExtra(listDrugExtra);
            service.savePharmacyOrders(listPharmacyOrders);
            service.savePharmacyDrugOrders(listPharmacyDrugOrders);
            service.savePharmacyObs(listAnotherPharmacyObs);
        }
        catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    }
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }


    public synchronized String[] exractKeyAndValue(String jsonText) {
        String value = "";
        String key="";
        JSONParser parser = new JSONParser();
        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                key+=entry.getKey();
                value+=entry.getValue();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        String myvals[]={key,value};
        return myvals;
    }
    public boolean  substractFromInventory(Integer drugId,int Qnty,String val){
        Drug drug = Context.getConceptService().getDrugByNameOrId(drugId.toString());
        String locationUUID=service.getPharmacyLocationsByName(val).getUuid();
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drug,locationUUID);
        if(drugDispenseSettings !=null){
        PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
        if(pharmacyStore!=null ){
            if(pharmacyStore.getDrugs().getDrugId()==drugId && pharmacyStore.getQuantity() > Qnty ){
                pharmacyStore.setQuantity( (pharmacyStore.getQuantity()-Qnty));
                service.savePharmacyInventory(pharmacyStore);
            }
        }
        }
     return true;
    }
}
