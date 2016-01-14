<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
        .visibleBorder td{
            border:1px solid black;
            background-color:#EBEBFF;
        }
    </style>

</head>
<body>
<% out.println("Your IP address is " + request.getRemoteAddr()); %>
<style type="text/css">
	.dataTables_wrapper{
		overflow: auto; /* this is what fixes the expansion */
	}
</style>
<div id="comprehensiveRFPReportDIV">
   startDate &nbsp;<input type="text" name="startDate" value="" />&nbsp;&nbsp;&nbsp;End Date &nbsp;<input type="text" name="endDate" value="" />&nbsp;&nbsp;<input type="button" onclick="submitToFetchRecords()" value="Load Records"/>&nbsp;&nbsp;
   <input type="button" onclick="tableToExcel('resultsTable', 'Export Data')" value="Export to Excel"/></br></br></br>
	<table id="resultsTable" border="0">
		<thead>
			<tr>
				<th>Drug Description</th>
				<th>Opening Stock</th>
				<th>Sll list Received</th>
				<th>Sll list Transfered</th>
				<th>Expected Closing Stock</th>
				<th>Actual Closing Stock</th>
				<th>% Deviation</th>
				<th>Expected Dispensed Quantities</th>
				<th>Actual Dispensed Quantities</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${allData}" var="column" varStatus="ind">
				<tr>
					<td>${column[2]}</td>
					<td>${column[3]}</td>
					<td>${column[4]}</td>
					<td>${column[5]}</td>
					<td>${column[6]}</td>
					<td>${column[7]}</td>
					<td>${column[8]}</td>
					<td>${column[2]}</td>
					<td>${column[2]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>