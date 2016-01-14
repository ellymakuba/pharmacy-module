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
<%@ page import="org.openmrs.Person" %>
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
         table.visibleBorder{
          background-color:#EBEBFF;
          width:100%;
         }
         #drugHistoryDiv{
           margin:5px;
           border:5px #AAAAAA;
         }
    </style>
    <style type="text/css">
    	.dataTables_wrapper{
    		overflow: auto; /* this is what fixes the expansion */
    	}
    </style>
<script type="text/javascript">
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
  PharmacyService service=Context.getService(PharmacyService.class);
  String location=(String)session.getAttribute("location");
  PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
  String reportStartDate=(String)session.getAttribute("reportStartDate");
  String reportEndDate=(String)session.getAttribute("reportEndDate");
  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
  Date startDate = formatter.parse(reportStartDate);
  Date endDate = formatter.parse(reportEndDate);
  String sessionDrugIdToCheckHistory=(String)session.getAttribute("sessionDrugId");
  Drug drugFromContext = Context.getConceptService().getDrugByNameOrId(sessionDrugIdToCheckHistory);
  PharmacyDrugOrder pharmacyDrugOrder;
  Person person;
  Integer totalQuantity=0;
 List<DrugExtra> drugExtraList=service.getDrugExtraRangeByDrugAndLocation(pharmacyLocation.getUuid(),drugFromContext.getDrugId().toString(),startDate,endDate);
%>
<DIV id="drugHistoryDiv">
<input type="button" onclick="tableToExcel('drugHistoryTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<table id="drugHistoryTable" class="visibleBorder">
<thead>
<tr><th colspan=4><h1>Drug Transaction History For <%=drugFromContext.getName()%></h1></th></tr>
 <tr>
   <th>Patient ID</th>
   <th>Patient Name</th>
   <th>Quantity</th>
   <th>Date Dispensed</th>
  </th>
</thead>
<tbody>
<% for(DrugExtra drugExtraInstance:drugExtraList){
 pharmacyDrugOrder=service.getPharmacyDrugOrdersByDrugExtraUUID(drugExtraInstance);
 if(pharmacyDrugOrder !=null){
     totalQuantity=totalQuantity+drugExtraInstance.getQuantitysold();
     person=Context.getPatientService().getPatient(pharmacyDrugOrder.getPerson().getPatientId());
        %>
        <tr>
          <td><%=pharmacyDrugOrder.getPerson().getPatientIdentifier()%></td>
          <td><%=Context.getPatientService().getPatient(pharmacyDrugOrder.getPerson().getPatientId()).getPersonName()%></td>
          <td><%=drugExtraInstance.getQuantitysold()%></td>
          <td><%=drugExtraInstance.getDateCreated()%></td>
         </tr>
        <%
    }
}
%>
<tr><td>Total Dispensed</td><td></td><td><%=totalQuantity%></td><td></td></tr>
 </tbody>
 </table>
</DIV>
</body>