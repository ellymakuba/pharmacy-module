package org.openmrs.module.pharmacy.web.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.DrugExtra;
import org.openmrs.module.pharmacy.model.PharmacyEncounter;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterJob;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

@Controller
public class PrintInvoiceController {

    private Log log = LogFactory.getLog(this.getClass());
    public PharmacyService service;
    private List<DrugExtra> receiptToProcess;

    @RequestMapping(method = RequestMethod.GET, value = "/module/pharmacy/printInvoice")
    public void printReceipt(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        String encounter=request.getParameter("encounterUUID");
        //String encounter="43189350-a9e2-4591-8716-481d6adec829";
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        java.util.List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();
        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }
        String locationUUID=service.getPharmacyLocationsByName(locationVal).getUuid();
        Document doc = new Document();
        PdfWriter docWriter = null;

        PharmacyEncounter pharmacyEncounter=service.getPharmacyEncounterByUuid(encounter);
        String patient=service.getPatientByIdentifier(pharmacyEncounter.getPerson().getPatientIdentifier().getIdentifier()) ;
        Person person=Context.getPatientService().getPatient(Integer.parseInt(patient));

        DecimalFormat df = new DecimalFormat("0.00");
        File tmpFile = File.createTempFile("paymentReceipt", ".pdf");

        try{
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            docWriter = PdfWriter.getInstance(doc , new FileOutputStream(tmpFile));//document header attributes
            doc.addAuthor("RFP");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("RFP");
            doc.addTitle("RFP RECEIPT");
            doc.setPageSize(PageSize.B7);
            doc.setMargins(5,5,5,5);
            //open document
            doc.open();
            //Image image1 = Image.getInstance("ampath.jpeg");

            //specify column widths
            float[] columnWidths = {5f, 1.5f, 2f, 2f,2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Patient ID", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, pharmacyEncounter.getPerson().getPatientIdentifier().getIdentifier(), Element.ALIGN_LEFT, 4, bfBold12);
            insertCell(table, "Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table,person.getGivenName()+" "+ person.getFamilyName(), Element.ALIGN_LEFT, 4, bfBold12);
            insertCell(table, "Medication Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Prc", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Qty", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Disc", Element.ALIGN_LEFT, 1,bfBold12);
            insertCell(table, "Amt", Element.ALIGN_RIGHT, 1,bfBold12);
            table.setHeaderRows(1);

            //insertImage(table,image1,Element.ALIGN_RIGHT, 1,bfBold12);
            //insertCell(table, "elly", Element.ALIGN_RIGHT, 5, bf12);
            receiptToProcess=service.getUnprocessedReceiptsByEncounterUUID(encounter);
            int itemSize=receiptToProcess.size();
            Double receiptTotal=0.0;
            for(int x=0; x<itemSize; x++){
                Double amountLessDiscount=receiptToProcess.get(x).getAmount()-receiptToProcess.get(x).getDiscount();
                PharmacyStore pharmacyStore=service.getPharmacyInventoryByDrugUuid(receiptToProcess.get(x).getDrug().getUuid(),locationUUID);
                insertCell(table, receiptToProcess.get(x).getDrug().getName(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+pharmacyStore.getUnitPrice(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+receiptToProcess.get(x).getQuantitysold(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+receiptToProcess.get(x).getDiscount(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+amountLessDiscount, Element.ALIGN_RIGHT, 1, bf12);
                receiptTotal = receiptTotal + amountLessDiscount;
            }
            insertCell(table, "Total:", Element.ALIGN_LEFT, 2, bfBold12);
            insertCell(table, df.format(receiptTotal), Element.ALIGN_LEFT, 3, bfBold12);
            insertCell(table, "We Treat God Heals ...", Element.ALIGN_LEFT, 4, bfBold12);
            doc.add(table);


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

            response.setHeader("Content-disposition", "inline; filename=" + "RFPReceipt" + ".pdf");
            response.setContentType("application/pdf");
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(tmpFile);

            IOUtils.copy(fileInputStream, outputStream);
            fileInputStream.close();
            outputStream.flush();
            tmpFile.delete();
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
    private void insertImage(PdfPTable table, Image img, int align, int colspan, Font font){
        PdfPCell cell = new PdfPCell(img);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        table.addCell(cell);
    }

}
