<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style type="text/css">
        table.visibleBorder td{
            border:1px solid white;
            cell-padding:5px;
        }
         table.visibleBorder th{
                    border:1px solid white;
                    cell-padding:5px;
                }
   #generalRFPReportDIV{
             margin:5px;
             border:5px #AAAAAA;
           }
            #generalRFPReportDIV table{
                        background-color:#EBEBFF;
                        width:100%;
                      }
</style>
<script type="text/javascript">
$j("#datef").datepicker();
$j("#datet").datepicker();
function submitToFetchRecords(){
   	 $j('#west_panel_content').load("resources/subpages/rfpGeneralReport.form?datef="+$j("#datef").val()+"&datet="+$j("#datet").val(), function () {});
  	 $j('#west_panel_content').empty();
}
 var tableToExcel = (function() {
      var uri = 'data:application/vnd.ms-excel;base64,'
        					, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        					, base64 = function(s) {
        				return window.btoa(unescape(encodeURIComponent(s)))
        			}
        			, format = function(s, c) {
        				return s.replace(/{(\w+)}/g, function(m, p) {
        					return c[p];
        				})
        			}
        			return function(table, name) {
        				if (!table.nodeType)
        					table = document.getElementById(table)
        				var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        				window.location.href = uri + base64(format(template, ctx))
        			}
        		}()
);
</script>
</head>
<body>
<%
	PharmacyService service = Context.getService(PharmacyService.class);
    List<PharmacyStore> pharmacyStoreList;

	String location=(String)session.getAttribute("location");
	PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
    String reportStartDate=(String)session.getAttribute("reportStartDate");
    String reportEndDate=(String)session.getAttribute("reportEndDate");
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = null;
    Date endDate = null;
    double unitPrice=0;
    Date date1=null;
    Double quantitySold=0.0;
    double amountWaived=0.0;
    double countDispensed=0;
    Double cashClaimedOnReceipt=0.0;
    double discount=0.0;
    double openingStock=0.0;
    Double cashExpected=0.0;
    Double quantity=0.0;

    Double totalWaived=0.0;
    Double totalCashExpected=0.0;
    Double totalDiscount=0.0;
    Double totalCashReceived=0.0;

%>
<div id="generalRFPReportDIV">
   start Date &nbsp;<input type="text" name="datefrom" id="datef" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="dateto" id="datet" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('generalRFPReportTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<% if(reportStartDate !=null && reportEndDate!=null) {
                startDate = formatter.parse(reportStartDate);
                endDate = formatter.parse(reportEndDate);

                 Date dateInstance=new Date(startDate.getTime()+(1000 * 60 * 60 * 24));
                 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                 String tommorowStringDate=simpleDateFormat.format(dateInstance);
                 Date formatedDate = simpleDateFormat.parse(tommorowStringDate);
                 List<DrugExtra> drugExtraList=service.getDrugRange(startDate, endDate,pharmacyLocation.getUuid());

%>
<h1><a>RFP Cashier Report From(mm/dd/yyyy): <%=reportStartDate %> To <%=reportEndDate%></a></h1>
<table id="generalRFPReportTable" class="visibleBorder">
<tr>
      <th width="4%">Medication</th>
      <th>Quantity(system)</th>
      <th>Quantity(Stock)</th>
      <th>Difference</th>
      <th>%difference</th>
      <th>Quantity Receieved</th>
      <th>Unit cost</th>
      <th>Quantity Sold</th>
      <th>Amount Waived</th>
      <th>#Waived</th>
      <th>#Drug Dispensed</th>
      <th>cash expected</th>
      <th>Discount</th>
      <th>Actual Cash</th>
</tr>

<%
    for(DrugExtra drugExtraInstance:drugExtraList){
     DrugDispenseSettings drugDispenseSettings=service.getDrugDispenseSettingsByDrugIdAndLocation(drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid());
     quantity= Double.valueOf(0);
     Double count=Double.valueOf(service.getNumberOfTimesDrugWaivedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid()));
     Drug drug = Context.getConceptService().getDrugByNameOrId(drugExtraInstance.getDrug().getDrugId().toString());
     if(drugDispenseSettings !=null){
              PharmacyStore pharmacyStore = drugDispenseSettings.getInventoryId();
              if(pharmacyStore!=null ){
                  quantity= Double.valueOf(pharmacyStore.getQuantity());
                  unitPrice=pharmacyStore.getUnitPrice();
                  date1=pharmacyStore.getLastEditDate();
              }
          }

        List<DrugTransactions> list2 = service.getDrugTransactions();
        Double quantityFromStore= Double.valueOf(0);
        int size2=list2.size();
        for (int j = 0; j < size2; j++) {
            if(list2.get(j).getLocation().getName().equalsIgnoreCase(pharmacyLocation.getName())){
                if(list2.get(j).getDrugs() !=null){
                    if(list2.get(j).getDrugs().getDrugId() == drugExtraInstance.getDrug().getDrugId()){
                        quantityFromStore=quantityFromStore+list2.get(j).getQuantityIn();
                    }
                }
            }
        }
        Integer productsSold= service.getDrugsDispensedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid());

        if(productsSold !=null){
            quantitySold= Double.valueOf(productsSold);
        }

        if(service.getAmountWaivedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid()) !=null){
            amountWaived=Double.valueOf(service.getAmountWaivedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid()));
        }

        cashExpected=service.getDrugTotalCashCollectedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid());

        if(cashExpected !=null){
            cashClaimedOnReceipt= cashExpected;
        }
        Double cashExpectedLessW=(cashClaimedOnReceipt)-amountWaived;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();

        if(service.getDiscountOnDrugsWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId().toString(),pharmacyLocation.getUuid()) !=null){
            discount=service.getDiscountOnDrugsWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId().toString(),pharmacyLocation.getUuid());
        }
        cashExpectedLessW=cashExpectedLessW-discount;
        if(service.getDrugInventoryOpeningStockByDateAndLocation(drug.getUuid(),startDate,formatedDate,pharmacyLocation.getUuid()) !=null){
           openingStock= service.getDrugInventoryOpeningStockByDateAndLocation(drug.getUuid(),startDate,formatedDate,pharmacyLocation.getUuid()).getStockQuantities();
        }
        if(service.getDrugsDispensedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid())!=null){
          countDispensed=service.getDrugsDispensedWithinPeriodRange(startDate,endDate,drugExtraInstance.getDrug().getDrugId(),pharmacyLocation.getUuid());
        }
        totalWaived=totalWaived+amountWaived;
        totalCashExpected=totalCashExpected+cashExpected;
        totalDiscount=totalDiscount+discount;
        totalCashReceived=totalCashReceived+cashExpectedLessW;
         %>
      <tr>
      <td><%=drugExtraInstance.getDrug().getName()%></td>
      <td><%=quantity%></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td><%=unitPrice%></td>
      <td><%=quantitySold%></td>
      <td><%=amountWaived%></td>
      <td><%=count%></td>
      <td><%=countDispensed%></td>
      <td><%=cashExpected%></td>
      <td><%=discount%></td>
      <td><%=cashExpectedLessW%></td>
      </tr>
    <%}%>
    <tr><td><b>Totals:</b></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><%=totalWaived%></td><td></td><td></td><td><%=totalCashExpected%></td><td><%=totalDiscount%></td><td><%=totalCashReceived%></td></tr>
  </table>

<% }%>
</div>
</body>
</html>