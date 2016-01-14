package org.openmrs.module.pharmacy.web.controller;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
@Controller
public class FmapReportController {

    private static final Log log = LogFactory.getLog(FmapReportController.class);
    private JSONArray drugStrengthA;
    public PharmacyService service;
    private boolean found = false;
    private String[] FMAPArray;
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/resources/subpages/FmapReport")
    public synchronized void fmapReportGetProcessor(ModelMap map,HttpServletRequest request){

        String sDate=request.getParameter("startDate");
        String eDate=request.getParameter("endDate");

        if(sDate !=null && eDate!=null) {
            request.getSession().setAttribute("reportStartDate", sDate);
            request.getSession().setAttribute("reportEndDate", eDate);
        }
        //File fileObject=new File("/home/elly/workspace/amrspharmacy/omod/src/main/java/org/openmrs/module/pharmacy/web/controller/newly.xlsx");

    }
    @RequestMapping(method=RequestMethod.GET,value="module/pharmacy/generateExcel")
    public void generateFMAPExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
       /* response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","inline; filename=newly.xls");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Print Area");

        /*CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        XSSFRow row;
        Map < String, Object[] > empinfo = new TreeMap < String, Object[] >();
        empinfo.put( "1", new Object[] {"", "MINISTRY OF HEALTH" });
        empinfo.put( "2", new Object[] {"FACILITY MONTHLY ARV PATIENT SUMMARY (F-MAPS) REPORT" });
        empinfo.put( "3", new Object[] {"Facility Name:","","Facility code:","" });
        empinfo.put( "4", new Object[] {"Province: ","","District:",""});
        empinfo.put( "5", new Object[] {"Period of Reporting:","Beginning:","","Ending:",""});
        empinfo.put( "6", new Object[] {"Programme Sponsor: (Please Tick)","GoK","","PEPFAR","","MSF",""});

        PharmacyService service = Context.getService(PharmacyService.class);
        List<PharmacyEncounter> pharmacyEncounterOnRegimen;
        String location=request.getSession().getAttribute("location").toString();
        PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
        String reportStartDate=request.getSession().getAttribute("reportStartDate").toString();
        String reportEndDate=request.getSession().getAttribute("reportEndDate").toString();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate = null;
        Integer numberOfPatientsOnRegimen=0;


        //Iterate over data and write to sheet
        Set < String > keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid)
        {
            row = spreadsheet.createRow(rowid++);
            Object [] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr)
            {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        Integer rowCount=7;
        if(reportStartDate !=null && reportEndDate!=null) {
            Map < Integer, Object[] > FMAPData = new TreeMap < Integer, Object[] >();
            startDate = formatter.parse(reportStartDate);
            endDate = formatter.parse(reportEndDate);
            List<PharmacyEncounter> pharmacyEncounterList = service.getEncountersRange(startDate, endDate, pharmacyLocation.getUuid());
            for(PharmacyEncounter pharmacyEncounterInstance:pharmacyEncounterList){
                    numberOfPatientsOnRegimen = service.getNumberOfPatientsOnRegimen(startDate, endDate, pharmacyEncounterInstance.getRegimenCode(), pharmacyLocation.getUuid());
                    FMAPData.put(rowCount, new Object[]{pharmacyEncounterInstance.getRegimenCode(), pharmacyEncounterInstance.getRegimenName(), String.valueOf(numberOfPatientsOnRegimen)});
                    System.out.println("rowcount is "+rowCount);
                rowCount++;
            }

        Set <Integer> keyData=FMAPData.keySet();
            Integer[] sorted = keyData.toArray(new Integer[keyData.size()]);
           // Arrays.sort(sorted);
            //System.out.println("index one=================="+sorted[0]+" index two "+sorted[1]);
            for(Integer keyItem:keyData){
                row=spreadsheet.createRow(rowid);
                Object[] objectsArray=FMAPData.get(keyItem);
                int colunmNum=0;
                for(Object object:objectsArray){
                    Cell cell=row.createCell(colunmNum);
                    cell.setCellValue((String)object);
                    colunmNum++;
                }
                rowid++;
            }
        }

        spreadsheet.addMergedRegion(new CellRangeAddress(0,0,1,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(1,1,0,5));
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        try {
            workbook.write(outByteStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] outArray = outByteStream.toByteArray();
        OutputStream outputStream= null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(outArray);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/fmapReport")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();
        }

    }

}