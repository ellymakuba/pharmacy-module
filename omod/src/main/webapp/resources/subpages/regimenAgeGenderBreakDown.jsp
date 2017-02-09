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
        table.visibleBorder td{
            border:1px solid white;
            cell-padding:5px;
        }
         table.visibleBorder th{
                    border:1px solid white;
                    cell-padding:5px;
                }
   #regimenBreakDownReportDIV{
             margin:5px;
             border:5px #AAAAAA;
           }
            #regimenBreakDownReportDIV table{
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
   $j('#west_panel_content').load("resources/subpages/regimenAgeGenderBreakDown.form?startDate="+$j("#startDate").val()+"&endDate="+$j("#endDate").val(), function () {});
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
    List<PharmacyEncounter> pharmacyEncounterOnRegimen;

	String location=(String)session.getAttribute("location");
	PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
    String reportStartDate=(String)session.getAttribute("regimenBreakDownReportStartDate");
    String reportEndDate=(String)session.getAttribute("regimenBreakDownReportEndDate");
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = null;
    Date endDate = null;

    Integer totalNumberOfPatientsOnRegimen=0;
    Integer numberOfPatientsOnARTAdultMale=0;
    Integer numberOfPatientsOnPEPAdultMale=0;
    Integer numberOfPatientsOnPMTCTAdultMale=0;
    Integer numberOfPatientsOnARTAdultFemale=0;
    Integer numberOfPatientsOnPEPAdultFemale=0;
    Integer numberOfPatientsOnPMTCTAdultFemale=0;
    Integer numberOfPatientsOnARTChildrenMale=0;
    Integer numberOfPatientsOnPEPChildrenMale=0;
    Integer numberOfPatientsOnPMTCTChildrenMale=0;
    Integer numberOfPatientsOnARTChildrenFemale=0;
    Integer numberOfPatientsOnPEPChildrenFemale=0;
    Integer numberOfPatientsOnPMTCTChildrenFemale=0;
%>

<div id="regimenBreakDownReportDIV">
   start Date &nbsp;<input type="text" name="startDate" id="startDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="endDate" id="endDate" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('regimenBreakDownReportTable', 'Export Data')" value="Export to Excel"/></br></br></br>
<% if(reportStartDate !=null && reportEndDate!=null) {
                startDate = formatter.parse(reportStartDate);
                endDate = formatter.parse(reportEndDate);
                List<PharmacyEncounter> pharmacyEncounterList=service.getEncountersRangeByRegimenName(startDate,endDate,pharmacyLocation.getUuid());

%>
<h1><a>FMAP Between <%=reportStartDate %> To <%=reportEndDate%></a></h1>
	<table id="regimenBreakDownReportTable" class="visibleBorder">
		<thead>
			<tr>
			    <th></th>
			    <th></th>
                <th colspan=6>Adults</th>
                <th colspan=6>Children</th>
			</tr>
			<tr>
			<th></th>
			<th></th>
            <th colspan=3>Male</th>
            <th colspan=3>Female</th>
            <th colspan=3>Male</th>
            <th colspan=3>Female</th>
			</tr>
			<tr>

			<th>Regimen Name</th>
			<th>Total No</th>
			<th>ART</th>
			<th>PEP</th>
			<th>PMTCT</th>

			<th>ART</th>
            <th>PEP</th>
            <th>PMTCT</th>

            <th>ART</th>
            <th>PEP</th>
            <th>PMTCT</th>

            <th>ART</th>
            <th>PEP</th>
            <th>PMTCT</th>
           </tr>
		</thead>
		<tbody>
           <%
            for(PharmacyEncounter pharmacyEncounterInstance:pharmacyEncounterList){
                totalNumberOfPatientsOnRegimen=0;
                numberOfPatientsOnARTAdultMale=0;
                numberOfPatientsOnPEPAdultMale=0;
                numberOfPatientsOnPMTCTAdultMale=0;
                numberOfPatientsOnARTAdultFemale=0;
                numberOfPatientsOnPEPAdultFemale=0;
                numberOfPatientsOnPMTCTAdultFemale=0;
                numberOfPatientsOnARTChildrenMale=0;
                numberOfPatientsOnPEPChildrenMale=0;
                numberOfPatientsOnPMTCTChildrenMale=0;
                numberOfPatientsOnARTChildrenFemale=0;
                numberOfPatientsOnPEPChildrenFemale=0;
                numberOfPatientsOnPMTCTChildrenFemale=0;
                pharmacyEncounterOnRegimen=service.getCountOfPatientsOnRegimen(pharmacyEncounterInstance.getRegimenName(),startDate,endDate,pharmacyLocation.getUuid());
                    for(int i=0; i<pharmacyEncounterOnRegimen.size(); i++){
                      if(pharmacyEncounterOnRegimen.get(i).getPerson().getAge()>15 && pharmacyEncounterOnRegimen.get(i).getPerson().getGender().equalsIgnoreCase("M")){

                          if(pharmacyEncounterOnRegimen.get(i).getPMTCTChecked()==1){
                          numberOfPatientsOnPMTCTAdultMale=numberOfPatientsOnPMTCTAdultMale+1;
                          totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                          }
                          else if(pharmacyEncounterOnRegimen.get(i).getPEPChecked()==1){
                          numberOfPatientsOnPEPAdultMale=numberOfPatientsOnPEPAdultMale+1;
                          totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                          }
                          else{
                          numberOfPatientsOnARTAdultMale=numberOfPatientsOnARTAdultMale+1;
                          totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                          }
                      }
                      else if(pharmacyEncounterOnRegimen.get(i).getPerson().getAge()>15 && pharmacyEncounterOnRegimen.get(i).getPerson().getGender().equalsIgnoreCase("F")){

                            if(pharmacyEncounterOnRegimen.get(i).getPMTCTChecked()==1){
                              numberOfPatientsOnPMTCTAdultFemale=numberOfPatientsOnPMTCTAdultFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else if(pharmacyEncounterOnRegimen.get(i).getPEPChecked()==1){
                              numberOfPatientsOnPEPAdultFemale=numberOfPatientsOnPEPAdultFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else{
                              numberOfPatientsOnARTAdultFemale=numberOfPatientsOnARTAdultFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                       }
                       else if(pharmacyEncounterOnRegimen.get(i).getPerson().getAge()<15 && pharmacyEncounterOnRegimen.get(i).getPerson().getGender().equalsIgnoreCase("M")){

                           if(pharmacyEncounterOnRegimen.get(i).getPMTCTChecked()==1){
                              numberOfPatientsOnPMTCTChildrenMale=numberOfPatientsOnPMTCTChildrenMale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else if(pharmacyEncounterOnRegimen.get(i).getPEPChecked()==1){
                              numberOfPatientsOnPEPChildrenMale=numberOfPatientsOnPEPChildrenMale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else{
                              numberOfPatientsOnARTChildrenMale=numberOfPatientsOnARTChildrenMale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                       }
                       else if(pharmacyEncounterOnRegimen.get(i).getPerson().getAge()<15 && pharmacyEncounterOnRegimen.get(i).getPerson().getGender().equalsIgnoreCase("F")){

                            if(pharmacyEncounterOnRegimen.get(i).getPMTCTChecked()==1){
                              numberOfPatientsOnPMTCTChildrenFemale=numberOfPatientsOnPMTCTChildrenFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else if(pharmacyEncounterOnRegimen.get(i).getPEPChecked()==1){
                              numberOfPatientsOnPEPChildrenFemale=numberOfPatientsOnPEPChildrenFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                              else{
                              numberOfPatientsOnARTChildrenFemale=numberOfPatientsOnARTChildrenFemale+1;
                              totalNumberOfPatientsOnRegimen=totalNumberOfPatientsOnRegimen+1;
                              }
                        }
                    } %>
           <tr>
           <td><%=pharmacyEncounterInstance.getRegimenName()%></td>
          <td><%=totalNumberOfPatientsOnRegimen%></td>
           <td><%=numberOfPatientsOnARTAdultMale%></td>
           <td><%=numberOfPatientsOnPEPAdultMale%></td>
           <td><%=numberOfPatientsOnPMTCTAdultMale%></td>
           <td><%=numberOfPatientsOnARTAdultFemale%></td>
           <td><%=numberOfPatientsOnPEPAdultFemale%></td>
           <td><%=numberOfPatientsOnPMTCTAdultFemale%></td>
           <td><%=numberOfPatientsOnARTChildrenMale%></td>
           <td><%=numberOfPatientsOnPEPChildrenMale%></td>
           <td><%=numberOfPatientsOnPMTCTChildrenMale%></td>
           <td><%=numberOfPatientsOnARTChildrenFemale%></td>
           <td><%=numberOfPatientsOnPEPChildrenFemale%></td>
           <td><%=numberOfPatientsOnPMTCTChildrenFemale%></td>
           </tr>
           <%}%>
		</tbody>
	</table>
<% } %>
</div>
</body>