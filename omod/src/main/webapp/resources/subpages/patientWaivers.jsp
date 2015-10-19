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
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<html xmlns="http://www.w3.org/1999/xhtml">

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
$j("#patientWaiverStartDate").datepicker();
$j("#patientWaiverEndDate").datepicker();
     function submitToFetchRecords(){

        	 $j('#west_panel_content').load("resources/subpages/patientWaivers.form?startDate="+$j("#patientWaiverStartDate").val()+"&endDate="+$j("#patientWaiverEndDate").val(), function () {});
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
<%
	PharmacyService service = Context.getService(PharmacyService.class);
    List<DrugExtra> drugExtraList;

	String location=(String)session.getAttribute("location");
	PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
    String reportStartDate=(String)session.getAttribute("patientWaiverStartDate");
    String reportEndDate=(String)session.getAttribute("patientWaiverEndDate");
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = null;
    Date endDate = null;
    PharmacyEncounter pharmacyEncounter;
	Double totalWaiver=0.0;

%>

<div id="patientWaiversRFPReportDIV">
   start Date &nbsp;<input type="text" name="patientWaiverStartDate" id="patientWaiverStartDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="patientWaiverEndDate" id="patientWaiverEndDate" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('patientWaiversTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<% if(reportStartDate !=null && reportEndDate!=null) {
 startDate = formatter.parse(reportStartDate);
 endDate = formatter.parse(reportEndDate);
 drugExtraList=service.getWaiversWithinDateRange(pharmacyLocation.getUuid(),startDate,endDate);
%>
 <h1><a>RFP Patient Waivers Report From <%=reportStartDate %> To <%=reportEndDate%></a></h1>
 <table id="patientWaiversTable" style="width:100%;" class="visibleBorder">
 <thead>
 <tr>
 		<th>Patient ID</th>
 		<th>Medication</th>
 		<th>Quantity Dispensed</th>
 		<th>Amount Waived</th>
 		<th>Date</th>
 </tr>
 </thead>
 <tbody>
 <%
 for(DrugExtra drugExtraInstance:drugExtraList){
   pharmacyEncounter=service.getPharmacyEncounterByUuid(drugExtraInstance.getPharmacyEncounter().getUuid());
   %>
   <tr>
	<td><%=pharmacyEncounter.getPerson().getPatientIdentifier()%></td>
	<td><%=drugExtraInstance.getDrug().getName()%></td>
	<td><%=drugExtraInstance.getQuantitysold()%></td>
	<td><%=drugExtraInstance.getAmountw()%></td>
	<td><%=drugExtraInstance.getDateCreated()%></td>
   </tr>
<% totalWaiver=totalWaiver+drugExtraInstance.getAmountw();
 }%>
</tbody>
<tfoot>
<tr><td>Total:</td><td></td><td></td><td><%=totalWaiver%></td><td></td></tr>
</tfoot>
</table>
<% } %>
</div>