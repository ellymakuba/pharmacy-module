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
        ;
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
            doc.addTitle("RFP INVOICE");
            doc.setPageSize(PageSize.B7);
            //open document
            doc.open();
            //create a paragraph
            Paragraph paragraph = new Paragraph("RFP PHARMACY INVOICE");

            //specify column widths
            float[] columnWidths = {5f, 1.5f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Patient Identifier", Element.ALIGN_RIGHT, 2, bfBold12);
            insertCell(table, pharmacyEncounter.getPerson().getPatientIdentifier().getIdentifier(), Element.ALIGN_LEFT, 2, bfBold12);
            insertCell(table, "Patient Name", Element.ALIGN_RIGHT, 2, bfBold12);
            insertCell(table,person.getGivenName()+" "+ person.getFamilyName(), Element.ALIGN_LEFT, 2, bfBold12);
            insertCell(table, "Medication Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Unit Price", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Quantity", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Amount", Element.ALIGN_RIGHT, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            //insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
            //create section heading by cell merging

            receiptToProcess=service.getUnprocessedReceiptsByEncounterUUID(encounter);
            int itemSize=receiptToProcess.size();
            Double receiptTotal=0.0;
            for(int x=0; x<itemSize; x++){
                PharmacyStore pharmacyStore=service.getPharmacyInventoryByDrugUuid(receiptToProcess.get(x).getDrug().getUuid(),locationUUID);
                insertCell(table, receiptToProcess.get(x).getDrug().getName(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+pharmacyStore.getUnitPrice(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+receiptToProcess.get(x).getQuantitysold(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, ""+receiptToProcess.get(x).getAmount(), Element.ALIGN_RIGHT, 1, bf12);
                receiptTotal = receiptTotal + receiptToProcess.get(x).getAmount();
            }
            insertCell(table, "Total:", Element.ALIGN_LEFT, 2, bfBold12);
            insertCell(table, df.format(receiptTotal), Element.ALIGN_LEFT, 2, bfBold12);
            insertCell(table, "We Treat God Heals ...", Element.ALIGN_LEFT, 4, bfBold12);
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

            response.setHeader("Content-disposition", "attachment; filename=" + "RFPReceipt" + ".pdf");
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


}
