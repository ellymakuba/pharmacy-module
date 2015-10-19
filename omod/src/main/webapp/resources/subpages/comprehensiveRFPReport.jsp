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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
        table.visibleBorder td{
            border:1px solid black;
            background-color:#EBEBFF;
        }
         table.visibleBorder th{
                    border:1px solid black;
                    background-color:#EBEBFF;
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

        	 $j('#west_panel_content').load("resources/subpages/comprehensiveRFPReport.form?startDate="+$j("#startDate").val()+"&endDate="+$j("#endDate").val(), function () {});
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
$j("table").delegate("#comprehensiveReportTable tbody tr :first-child","click",function(){
    var TableRow = this.parentNode;
	$j('#west_panel_content').load("resources/subpages/detailedDrugHistory.form?drugID="+$j(this).html(), function () {});
    $j('#west_panel_content').empty();
})
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


%>

<div id="comprehensiveRFPReportDIV">
   start Date &nbsp;<input type="text" name="startDate" id="startDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="endDate" id="endDate" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('resultsTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<% if(reportStartDate !=null && reportEndDate!=null) {
                startDate = formatter.parse(reportStartDate);
                endDate = formatter.parse(reportEndDate);

                    Date dateInstance=new Date(startDate.getTime()+(1000 * 60 * 60 * 24));
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                    String tommorowStringDate=simpleDateFormat.format(dateInstance);
                    Date formatedDate = simpleDateFormat.parse(tommorowStringDate);
                pharmacyStoreList = service.getDrugTransactionsBetweenRange(startDate, endDate, pharmacyLocation);
                 List<S11> s11List=service.getDistinctS11WithinDateRange(pharmacyLocation.getUuid(),startDate,endDate);
                 Integer openingStock;
                 Integer drugTransactionQuantity;
                 Integer drugTransferedQuantity;
                 Integer drugTotalInventory;
                 Integer drugTotalS11;
                 Integer drugTotalTransfers;
                 Integer cumilativeQuantityOfDrugDispensed;
                 String distinctS11;
                 List<DrugTransactions> transferedDrugTransactions=service.getTransferedTransactionsdBetweenDates(startDate,endDate,pharmacyLocation);
%>
<h1><a>RFP Comprehensive Report From <%=reportStartDate %> To <%=reportEndDate%></a></h1>
	<table id="comprehensiveReportTable" class="visibleBorder">
		<thead>
			<tr>
			<th>Drug ID</th>
			<th>Drug Description</th>
			<th>Opening Stock</th>
			<% for(S11 s11Instance:s11List){%>
					<th>S11_<%=s11Instance.getS11No() %>(<%=s11Instance.getDateCreated()%>)</th>
			<% }
			for(DrugTransactions drugTransactionInstance:transferedDrugTransactions){%>
            		<th>Transfered on (<%=drugTransactionInstance.getDateCreated()%>)</th>
            <% }%>
            <th>Expected Closing Stock (system)</th>
            <th>Actual Closing Stock( Physical Count)</th>
            <th>% Deviation</th>
            <th>Expected Quantities dispensed at the pharmacy</th>
            <th>Actual Quantities dispensed at the pharmacy</th>
			</tr>
		</thead>
		<tbody>

				<% for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
				drugTotalInventory=0;
				drugTotalS11=0;
				drugTotalTransfers=0;
				openingStock=0;
						Drug drugFromContext = Context.getConceptService().getDrugByNameOrId(pharmacyStoreInstance.getDrugs().getId().toString());
						cumilativeQuantityOfDrugDispensed=service.getDrugsDispensedWithinPeriodRange(startDate,endDate,drugFromContext.getDrugId(),pharmacyLocation.getUuid());

						GeneratePharmacyInventoryQuantities drugToFindOpeningStock=service.getDrugInventoryOpeningStockByDateAndLocation(drugFromContext.getUuid(),startDate,formatedDate,pharmacyLocation.getUuid());
						if(drugToFindOpeningStock!=null){
							openingStock=drugToFindOpeningStock.getStockQuantities();
						}

						%>
						<tr>
						<td><a href="#"><%=pharmacyStoreInstance.getDrugs().getDrugId() %></a></td>
						<td><%=pharmacyStoreInstance.getDrugs().getName() %> </td>
						<td><%=openingStock %> </td>
						<% for(S11 s11Instance:s11List)
						   {
								drugTransactionQuantity=0;
								if(service.getDrugTransactionByS11AndDrug(pharmacyLocation,drugFromContext,s11Instance.getS11No()) !=null){
								distinctS11=service.getDrugTransactionByS11AndDrug(pharmacyLocation,drugFromContext,s11Instance.getS11No());
									drugTransactionQuantity=service.getDrugS11QuantityReceived(pharmacyLocation,drugFromContext,distinctS11);
								}
								drugTotalS11=drugTotalS11+drugTransactionQuantity;
								%>
								<td><%=drugTransactionQuantity%></td>
						<% }
						for(DrugTransactions drugTransactionInstance:transferedDrugTransactions){
								drugTransferedQuantity=0;
								DrugTransactions drugTransactionObjectTransfered=service.getDrugTransactionTransferedByLocationAndDrug(pharmacyLocation,drugFromContext,drugTransactionInstance.getUuid());
								if(drugTransactionObjectTransfered !=null){
									drugTransferedQuantity=drugTransactionObjectTransfered.getQuantityIn();
								}
								drugTotalTransfers=drugTotalTransfers+drugTransferedQuantity;
								%>
								<td><%=drugTransferedQuantity%></td>
                        <%
                        drugTotalInventory=drugTotalInventory+openingStock+drugTotalS11-drugTotalTransfers;
                        }%>
                        <td><%=drugTotalInventory %></td>
                        <td></td>
                        <td></td>
                        <td><%=cumilativeQuantityOfDrugDispensed%></td>
                        <td></td>
						</tr>
				<% }%>
		</tbody>
	</table>
<% } %>
</div>
</body>