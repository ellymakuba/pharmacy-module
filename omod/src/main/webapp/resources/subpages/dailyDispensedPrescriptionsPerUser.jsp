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
<%@ page import="org.openmrs.User" %>
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
   #dispensedByUserDIV{
             margin:5px;
             border:5px #AAAAAA;
           }
            #dispensedByUserDIV table{
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
   $j('#west_panel_content').load("resources/subpages/dailyDispensedPrescriptionsPerUser.form?startDate="+$j("#startDate").val()+"&endDate="+$j("#endDate").val()+"&user="+$j("#user").val(), function () {});
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
    List<PharmacyLocationUsers> siteUsers=service.getPharmacyLocationUsers();

	String location=(String)session.getAttribute("location");
	PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
    String reportStartDate=(String)session.getAttribute("reportStartDate");
    String reportEndDate=(String)session.getAttribute("reportEndDate");
    String selectedUserSession=(String)session.getAttribute("selectedUser");
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = null;
    Date endDate = null;
    Double totalAmounts=0.0;
    Integer numberOfTransactions=0;
%>

<div id="dispensedByUserDIV">
   start Date &nbsp;<input type="text" name="startDate" id="startDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="endDate" id="endDate" value="" />&nbsp;&nbsp;<select name="user" id="user"><option value="0">Select User</option> %>
   <% for(PharmacyLocationUsers userInstance:siteUsers){%>
     <option value=<%=userInstance.getUserName()%> ><%=userInstance.getUserName()%> </option>
  <% } %>
    &nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('dispensedByUserTable', 'Export Data')" value="Export to Excel"/></br></br>

<% if(reportStartDate !=null && reportEndDate!=null && selectedUserSession!=null) {
                startDate = formatter.parse(reportStartDate);
                endDate = formatter.parse(reportEndDate);
                User pharmacyUser=Context.getUserService().getUserByUsername(selectedUserSession);
                List<PharmacyEncounter> pharmacyEncounterList=service.getPharmacyEncounterListByUserBetweenTwoDate(startDate,endDate,pharmacyUser,pharmacyLocation);

%>
<h1><a>Entered Prescriptions Between <%=reportStartDate %> To <%=reportEndDate%> For <%=pharmacyUser%></a></h1>
	<table id="dispensedByUserTable" class="visibleBorder">
		<thead>
			<tr>
			    <th>Patient Identifier</th>
                <th>Patient Name</th>
                <th>Encounter Amount</th>
                <th>Date</th>
			</tr>
		</thead>
		<tbody>
           <% for(PharmacyEncounter pharmacyEncounterInstance:pharmacyEncounterList){
           totalAmounts=totalAmounts+pharmacyEncounterInstance.getTotalAmount();
           %><tr>
             <td><%=pharmacyEncounterInstance.getPerson().getPatientIdentifier()%></td>
             <td><%=pharmacyEncounterInstance.getPerson().getPersonName()%></td>
             <td><%=pharmacyEncounterInstance.getTotalAmount()%></td>
             <td><%=pharmacyEncounterInstance.getDateCreated()%></td>
              </tr>
           <% numberOfTransactions++;
           } %>
           <tr><td>Total</td> <td>Number of Transactions (<%=numberOfTransactions%>)</td> <td><%=totalAmounts%></td> <td></td></tr>
		</tbody>
	</table>
<% }
 %>
</div>
</body>