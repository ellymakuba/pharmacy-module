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
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.joda.time.Months" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
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
   #FCDRReportDIV{
             margin:5px;
             border:5px #AAAAAA;
           }
            #FCDRReportDIV table{
                        background-color:#EBEBFF;
                        width:100%;
                      }
    </style>
    <style type="text/css">
    	.dataTables_wrapper{
    		overflow: auto; /* this is what fixes the expansion */
    	}
    </style>
<script type="text/javascript">
$j("#startDate").datepicker();
$j("#endDate").datepicker();
function submitToFetchRecords(){
   $j('#west_panel_content').load("resources/subpages/F-CDRR.form?startDate="+$j("#startDate").val()+"&endDate="+$j("#endDate").val(), function () {});
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
    List<PharmacyDrugOrder> pharmacyDrugOrderList;
    Integer openingStock=0;
    Integer stockReceivedForDrug=0;
    Integer quantityOfDrugDispensed=0;
    Integer quantityOfDrugWithLessThanSixMonthsToExpire=0;
    InventoryMetaData inventoryMetaData;
    Integer drugTransferedQuantity=0;
    Integer drugReceivedQuantity=0;
    Integer drugTransferedQuantityTotal;
    Integer drugReceivedQuantityTotal;
    Integer quantityRequiredForResupply=0;
%>

<div id="FCDRReportDIV">
   start Date &nbsp;<input type="text" name="startDate" id="startDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="endDate" id="endDate" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('FCDRReportTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<% if(reportStartDate !=null && reportEndDate!=null) {
                startDate = formatter.parse(reportStartDate);
                endDate = formatter.parse(reportEndDate);
                pharmacyStoreList = service.getDrugTransactionsBetweenRange(startDate, endDate, pharmacyLocation.getUuid());
                List<DrugTransactions> transferedDrugTransactions=service.getTransferedTransactionsdBetweenDates(startDate,endDate,pharmacyLocation.getUuid());
                List<DrugTransactions> receivedDrugTransactionsList=service.getReceivedDrugTransactionsdBetweenDates(startDate,endDate,pharmacyLocation.getUuid());

%>
<h1><a>FCDR Report For Satellite Sites Between <%=reportStartDate %> To <%=reportEndDate%></a></h1>
	<table id="FCDRReportTable" class="visibleBorder">
		<thead>
			<tr>
			    <th width="4%">Drug Name</th>
                <th>Unit Pack Size</th>
                <th>Beginning Balance</th>
                <th>Quantity Received</th>
                <th>Quantity Dispensed</th>
                <th>Losses (Damages,Expiries,Missing)</th>
                <th>Positive Adjustments(Borrowed from or Issued out to Other Facilities)</th>
                <th>Negative Adjustments(Borrowed from or Issued out to Other Facilities)</th>
                <th>Physical Count</th>
                <th colspan=2>Drugs with less than six month to expiry</th>
                <th>Days out of stock</th>
                <th>Quantity Required for ressuply</th>
			</tr>
			<tr>
                <th></th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>In Units</th>
                <th>Quantity</th>
                <th>Expiry Date</th>
                <th></th>
                <th>In Units</th>
			</tr>
		</thead>
		<tbody>
           <% for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
           Drug drugFromContext = Context.getConceptService().getDrugByNameOrId(pharmacyStoreInstance.getDrugs().getId().toString());
                        openingStock=0;
                        stockReceivedForDrug=0;
                        quantityOfDrugDispensed=0;
                        drugReceivedQuantity=0;
                        drugTransferedQuantity=0;
                        drugTransferedQuantityTotal=0;
                        drugReceivedQuantityTotal=0;
                        quantityRequiredForResupply=0;
                        inventoryMetaData=service.getInventoryMetaDataByDrugName(Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId()),pharmacyLocation);
                        quantityOfDrugWithLessThanSixMonthsToExpire=0;
                        DateTime dateTimeInstance=new DateTime(startDate);
                        Date dateInstance=new Date(startDate.getTime()+(1000 * 60 * 60 * 24));
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                        String tommorowStringDate=simpleDateFormat.format(dateInstance);
                        Date formatedDate = simpleDateFormat.parse(tommorowStringDate);
                        if(service.getDrugInventoryOpeningStockByDateAndLocation(pharmacyStoreInstance.getDrugs().getUuid(),startDate,formatedDate,pharmacyLocation.getUuid()) !=null){
                            openingStock= service.getDrugInventoryOpeningStockByDateAndLocation(pharmacyStoreInstance.getDrugs().getUuid(),startDate,formatedDate,pharmacyLocation.getUuid()).getStockQuantities();
                        }

                        if(service.getCumilativeS11ReceivedForDrugAtLocationBetweenTwoDates(startDate,endDate,pharmacyStoreInstance.getDrugs().getUuid(),pharmacyLocation.getUuid()) !=null){
                            stockReceivedForDrug=service.getCumilativeS11ReceivedForDrugAtLocationBetweenTwoDates(startDate,endDate,pharmacyStoreInstance.getDrugs().getUuid(),pharmacyLocation.getUuid());
                        }

                        for(DrugTransactions drugTransactionInstance:receivedDrugTransactionsList){

								DrugTransactions drugTransactionObjectReceived=service.getDrugTransactionByLocationAndDrug(pharmacyLocation,drugFromContext,drugTransactionInstance.getUuid());
								if(drugTransactionObjectReceived !=null){
									drugReceivedQuantity=drugTransactionObjectReceived.getQuantityIn();
									drugReceivedQuantityTotal=drugReceivedQuantityTotal+drugReceivedQuantity;
								}
                        }
                       for(DrugTransactions drugTransactionInstance:transferedDrugTransactions){

								DrugTransactions drugTransactionObjectTransfered=service.getDrugTransactionByLocationAndDrug(pharmacyLocation,drugFromContext,drugTransactionInstance.getUuid());
								if(drugTransactionObjectTransfered !=null){
									drugTransferedQuantity=drugTransactionObjectTransfered.getQuantityIn();
									drugTransferedQuantityTotal=drugTransferedQuantityTotal+drugTransferedQuantity;
								}
                        }
                        DateTime expiryDateTimeInstance = new DateTime(pharmacyStoreInstance.getExpireDate());
                        Months monthsDateTimeInstance = Months.monthsBetween( new DateTime(), expiryDateTimeInstance);
                        int numberOfMonthsToExpiry = monthsDateTimeInstance.getMonths();
                        pharmacyDrugOrderList=service.pharmacyDrugOrdersBetweenTwoDates(startDate,endDate,pharmacyLocation,Context.getConceptService().getDrug(pharmacyStoreInstance.getDrugs().getDrugId()));
                        for(PharmacyDrugOrder pharmacyDrugOrderInstance:pharmacyDrugOrderList){
                        quantityOfDrugDispensed=quantityOfDrugDispensed+pharmacyDrugOrderInstance.getQuantityGiven();
                        }
                        quantityRequiredForResupply=((quantityOfDrugDispensed*3)-(pharmacyStoreInstance.getQuantity()));
                        %>
                       <tr>
                        <td><%=pharmacyStoreInstance.getDrugs().getName()%></td>
                        <% if(inventoryMetaData!=null){ %>
                            <td><%=inventoryMetaData.getUnitsInPack() %></td>
                        <%}else{%>
                        <td></td><%}%>
                        <td><%=openingStock%></td>
                        <td><%=stockReceivedForDrug%></td>
                        <td><%=quantityOfDrugDispensed%></td>
                        <td></td>
                        <td><%=drugReceivedQuantityTotal%></td>
                        <td><%=drugTransferedQuantityTotal%></td>
                        <td><%=pharmacyStoreInstance.getQuantity()%></td>
                       <% if(numberOfMonthsToExpiry < 6) {%>
                            <td><%=pharmacyStoreInstance.getQuantity()%></td>
                            <td><%=pharmacyStoreInstance.getExpireDate()%></td>
                        <% }
                        else {%>
                            <td></td>
                            <td></td>
                        <%}%>
                        <td></td>
                        <td><%=quantityRequiredForResupply%></td>
                        </tr>

           <%} %>
		</tbody>
	</table>
<% } %>
</div>
<div id="generationDIV"></div>
</body>
