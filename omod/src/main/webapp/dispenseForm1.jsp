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
    </style>

</head>
<body>
<DIV id="dtab_1">
    <div id="queueFormDIV">
        <h3>RFP Drug Dispensing Form</h3>
        <form id="dispensingForm" name="dispensingForm">
            <fieldset>
                <table>
                    <tr>
                        <td>Search Patient Identifier</td>
                        <td><input type="text" name="patientId" id="patientId" class="required"/></td>
                        <td><input type="text" name="patientName" id="patientName" style="width:350px;"  readonly/></td>
                    </tr>
                </table>
                <table id="tableDispense">
                    <thead>
                    <tr class="newRowClass" ><th>Drug </th><th style="width:100px;">Quantity In stock </th><th>Dose </th><th>UnitPrice</th>
                        <th style="width:100px;">Quantity to Dispense</th><th>Amount</th></tr>
                    </thead>
                      <tbody>
                    <tr class="newRowClass" >
                        <td ><input type="text" name="dispenseFormDrug" id="dispenseFormDrug" style="width: 350px; "/></td>
                        <td><input type="text" name="quantityInStock" id="quantityInStock" style="width:100px;" readonly  /></td>
                        <td>
                            <select id="dosage"  name="dosage" ></select>
                        </td>
                        <td><input type="text" name="unitPrice" id="unitPrice" style="width:100px;"  readonly/></td>
                        <td><input type="text"  name="quantity" id="quantity" style="width:100px;" /></td>
                        <td><input type="text" name="amount" id="amount"  style="width:100px;"/></td>
                    </tr>
                    </tbody>
                </table>
                <table><tr><td>Total Amount</td><td><input type="text" name="totalAmount" id="totalAmount" readonly style="width: 100px;"></td>
                    <td>Amount Paid</td><td><input type="text" name="amountPaid" id="amountPaid" style="width: 100px;"></td>
                    <td>Amount Waived</td><td><input type="text" name="amountWaived" id="amountWaived" value="0" style="width: 100px;"></td>
                   <td>Balance</td> <td><input type="text" name="balance" id="balance" style="width: 100px;"></td>
                </tr></table>
                <input type="button" value="Add Row" onclick="addRow('tableDispense')"/>
                <input type="button" value="Remove Row" onclick="deleteRow('tableDispense')"/> </br>
                <input type="button" value="Queue for payment" onclick="addNewInvoiceOnQueue()"/>
                <%
                    if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin")) {
                %>
                <input  type="button" value="Process Payment" onClick="processInvoicePayment()"/>
                <%
                    }
                %>
                <input  type="button" value="Close Encounter" onclick="confirmInvoiceAndIssueDrugs()"/>
            </fieldset>
        </form>
    </div>

    <table width="100%" id="unClearedReceipts" >
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
</body>
</html>