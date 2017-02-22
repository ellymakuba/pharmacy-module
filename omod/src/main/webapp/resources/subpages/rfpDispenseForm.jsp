<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<head>
    <style type="text/css">.align_right {
    float: right;
    width: 40%;
    color:red;
    font-weight:bold;
     padding;10px;
      text-align:right;
      margin:10px;
  }
   .align_left {
       float: left;
       width: 40%;
       color:red;
       font-weight:bold;
       padding;10px;
       margin:10px;
  }
  .clear{
        clear:both;
     }

           .newRowClass{
               border:1px solid black;
           }
           table.visibleBorder td{
               border:1px solid black;
               background-color:#EBEBFF;
           }
           table.tdbordered td{
                       cell-padding:5px;
                       border:1px solid white;
                   }
              #dispenseFormDIV{
                border:5px #AAAAAA;
              }
              #dispenseFormDIV table{
                  background-color:#EBEBFF;
                  width:100%;
               }
              h2.centered{
               text-align:center;
              }
       </style>

</head>
<body>
<%
PharmacyService service = Context.getService(PharmacyService.class);
service=Context.getService(PharmacyService.class);
String locationVal = (String)session.getAttribute("location");
PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
String identifier = (String)session.getAttribute("pharmacyPatientIdentifier");
String fullName = (String)session.getAttribute("pharmacyPatientName");
List<WaiverReason> reasons=service.getAllWaiverReasons();
List<PharmacyLocations> locations=service.getPharmacyLocations();
%>
<DIV id="dispenseFormDIV">
<DIV id="align_left" class="align_left"></DIV>
<DIV id="align_right" class="align_right"></DIV>
<div class="clear"></div>
<div id="queueFormDIV" class="queueFormDIVClass">
<form id="dispensingForm" name="dispensingForm">
<fieldset>
<h2 class="centered">RFP Prescitpion Form</h2>
<input id="viewHistory" type="button" value="View Patient History" onClick="viewPatientHistory()"/>
<table id="patientInfoTable" class="tdbordered">
<tr>
  <td>Type Patient Identifier</td>
  <% if(identifier !=null){%>
  <td><input type="text" name="patientId" id="patientId" value=<%=identifier %> class="required"/></td>
  <td><input type="text" name="patientName" id="patientName" value="<%=fullName %>" style="width:200px;"  readonly/></td>
  <%} else{ %>
  <td><input type="text" name="patientId" id="patientId" class="required"/></td>
  <td><input type="text" name="patientName" id="patientName" style="width:200px;"  readonly/></td>
  <%}%>
  <td>Search Identifier On Queue:<input type="text" name="patientIdentifier" id="patientIdentifier" /></td>
</tr>
<tr><td colspan=2>Date Prescribe</td><td colspan=2><input type="text" name="date_prescribed" id="date_prescribed" required readonly=""/></td></tr>
<tr id="waiver_site_tr"><td colspan=2>Site</td><td><select name="waiver_site" id="waiver_site">
<option value="0">Select Site</option>
<%if(locations.size()>0){
for(PharmacyLocations location: locations){%>
<option value=<%=location.getUuid() %> ><%=location.getName()%> </option>
<%}
}%>
</select></td><tr>
<tr id="waiver_reason_tr"><td colspan=2>Reason for waiving</td><td><select name="waiver_reason" id="waiver_reason">
<option value="0">Select Reason</option>
<%if(reasons.size()>0){
for(WaiverReason reason: reasons){%>
<option value=<%=reason.getUuid() %> ><%=reason.getReason()%> </option>
<%}
}%>
</select>
</td></tr>
<tr id="social_worker_tr"><td colspan=2>Social Work Officer</td><td><input type="text" name="social_worker" id="social_worker"/></td></tr>
</table>
<table id="tableDispense" class="tdbordered">
<thead>
<tr class="newRowClass" style="width:100%;"><th>Drug </th>
<th>stock </th>
<th>Dose </th>
<th>UnitPrice</th>
<th>Qnty</th>
<th>Amt(KSH)</th>
<th>Disc</th>
<th>Waived(ksh)</th>
<th>Remove</th></tr>
</thead>
<tbody>
<tr class="newRowClass" >
<td ><input type="text" name="dispenseFormDrug" id="dispenseFormDrug" style="width: 350px; "/></td>
<td><input type="text" name="quantityInStock" id="quantityInStock" style="width:50px;" readonly  /></td>
<td>
<select id="dosage"  name="dosage" ></select>
</td>
<td><input type="text" name="unitPrice" id="unitPrice" style="width:50px;"  readonly/></td>
<td><input type="text"  name="quantity" id="quantity" style="width:80px;" /></td>
<td><input type="text" name="amount" id="amount"  style="width:80px;"/></td>
<td><input type="text" name="discount" id="discount" value="0" style="width:50px;"/></td>
<td><input type="text" name="itemAmountWaived" id="itemAmountWaived"  style="width:50px;" value="0"/></td>
<td><a href="#">del</a></td>
</tr>
</tbody>
</table>
<table class="tdbordered"><tr><td>Amount Expected</td><td><input type="text" name="totalAmount" id="totalAmount" readonly style="width: 100px;"></td>
<td>Cash Received</td><td><input type="text" name="amountPaid" id="amountPaid" style="width: 100px;"></td>
<td>Total Discount</td><td><input type="text" name="totalDiscount" id="totalDiscount" value="0.0" style="width: 100px;"></td>
<td>Total Waived</td><td><input type="text" name="amountWaived" id="amountWaived" value="0" style="width: 100px;" readonly></td>
<td>Balance</td> <td><input type="text" name="balance" id="balance" style="width: 100px;" readonly></td>
</tr></table></br>
<input type="button" value="Add A New Row" onclick="addRow()"/>

<% if(pharmacyLocation.getCashierPresent()==1){ %>
<input id="queue" style="text-align: right;" type="button" value="Queue for payment" onclick="addNewInvoiceOnQueue()"/>
<input id="requeue" type="button" value="Update & Requeue" onClick="updateAndRequeue()"/>
<input style="text-align: right;" type="button" value="Complete Encounter" onclick="confirmInvoiceAndIssueDrugs()"/>

<% }else{ %>
<input id="save_encounter" style="text-align: right;" type="button" value="Save Encounter" onclick="saveEncounter()"/>
<% } %>
</fieldset>
</form>
</div>
<div id="unClearedReceiptsDIV" >
<h3 style="text-align: center">Uncleared Patient Queue</h3>
<table width="100%" id="unClearedReceipts" class="tdbordered">
<thead>
<th>EncounterUUID</th>
<th>Patient ID</th>
<th>Total</th>
<th>Patient Name</th>
<Th>Time</Th>
<Th>Status</Th>
<Th>Print</Th>
</thead>
<tbody>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tbody>
</table>
</div>
<div id="allPatientEncountersDIV">
<h3 style="text-align: center">Previous Patient Encounters</h3>
<table cellpadding="0" cellspacing="0" border="0" class="display"  id="allPatientEncounters">
<thead>
<tr>
<th width="4%">UUID</th>
<th width="4%">Encounter Date</th>
<th>Form Name</th>
<th>Issued Medication</th>
<th>Location</th>
<th>User</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
</tbody>
</table>
</DIV>
</DIV><div id="printSection"> </div>
</DIV>
</body>
</html>
<openmrs:htmlInclude file="/moduleResources/pharmacy/jspharmacy/rfpDispense.js"/>