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
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


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
    private boolean allowDispense=false;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/rfpDispenseFormProcessor")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/rfpDispenseFormProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String jsonText = request.getParameter("values");
        String quantity = request.getParameter("quantity");
        String checkBooleanToProceedWithDispense=request.getParameter("checkBoolean");
        String print="1";
        String receipt=request.getParameter("receipt");
        String displayUnclearedReceipts=request.getParameter("unclearedReceipts");


        JSONParser parser=new JSONParser();
        String locationVal = null;
        jsonArray=new JSONArray();

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }

       /* if(print !=null){

            // creation of the document with a certain size and certain margins
            // may want to use PageSize.LETTER instead
            // Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            Document doc = new Document();
            PdfWriter docWriter = null;

            DecimalFormat df = new DecimalFormat("0.00");
            File tmpFile = File.createTempFile("paymentReceipt", ".pdf");

            try {

                //special font sizes
                Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
                Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

                //file path


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

                response.setHeader("Content-disposition", "attachment; filename=" + "sampleDoc" + ".pdf");
                response.setContentType("application/pdf");
                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(tmpFile);

                IOUtils.copy(fileInputStream, outputStream);
                fileInputStream.close();
                outputStream.flush();

                tmpFile.delete();
            }

        }
        else{ */
        if(receipt !=null){
            dispensedModel = new ArrayList<DrugExtra>();
            List<DrugExtra> listDrugExtra= new ArrayList<DrugExtra>();
            Object obj = null;
            try {
                obj = parser.parse(jsonText);
                JSONArray dispenseInstanceArray = (JSONArray)obj;
                //log.info("dispenseInstanceArray++++++++++++++++++++++ ++++++ "+dispenseInstanceArray);
                int dispenseInstanceArraySize=dispenseInstanceArray.size();
                for(int i=0; i<dispenseInstanceArraySize; i++){
                    DrugExtra drugExtra=new DrugExtra();
                    JSONArray rowInstance=(JSONArray)dispenseInstanceArray.get(i);
                    for(int j=0; j<rowInstance.size(); j++){
                        String myValues[]= exractKeyAndValue(rowInstance.get(j).toString());
                        String key = myValues[0];
                        String value=myValues[1];
                        //log.info("key is++++++++++++++++++++++++++++ "+key+" value is+++ "+value);
                        if(key.equalsIgnoreCase("drugReceipt")){
                            Drug drug = Context.getConceptService().getDrugByNameOrId(value);
                            drugExtra.setDrug(drug);
                        }
                        if(key.equalsIgnoreCase("quantityToSubtract")){
                            drugExtra.setQuantitysold(Integer.valueOf(value));
                        }

                    }
                    dispensedModel.add(drugExtra);
                }
                for(int i=0; i<dispensedModel.size(); i++){
                    if(dispensedModel.get(i).getDrug() !=null) {
                        log.info("drug is++++++++++++++++++++++++++++++++"+dispensedModel.get(i).getDrug().getName()+"("+dispensedModel.get(i).getDrug().getDrugId()+")");
                        log.info("quantity++++++++++++++++++++++++++++++++"+dispensedModel.get(i).getQuantitysold());
                      substractFromInventory(dispensedModel.get(i).getDrug().getDrugId(),dispensedModel.get(i).getQuantitysold(),locationVal);
                    }
                }
                pharmacyEncounter =service.getPharmacyEncounterByUuid(receipt);
                pharmacyEncounter.setDisplay(1);
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
                        if(key.equalsIgnoreCase("drugdispense")){
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
                     if(key.equalsIgnoreCase("drugdispense")){
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
            pharmacyEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(encounterProcessor.getPatientId())));
            service.savePharmacyEncounter(pharmacyEncounter);
            for(int i=0; i<dispensedModel.size(); i++){
             DrugExtra drugExtra = new DrugExtra();
                 if(dispensedModel.get(i).getDrug() !=null) {
                     drugExtra.setPharmacyEncounter(pharmacyEncounter);
                     drugExtra.setDrug(dispensedModel.get(i).getDrug());
                     drugExtra.setQuantitysold(dispensedModel.get(i).getQuantitysold());
                     drugExtra.setAmount(dispensedModel.get(i).getAmount());
                     listDrugExtra.add(drugExtra);
              }

            }
            service.saveDrugExtra(listDrugExtra);
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
        DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugId(drug);
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
