<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
    </style>

</head>
<body>
<DIV id="tbJSPDIV">
    <div id="tbFormDIV">
        <form id="tbForm" name="tbForm">
            <fieldset>
                <table>
                    <tr>
                        <td>Search Patient Identifier</td>
                        <td><input type="text" name="tbPatientID" id="tbPatientID" class="required"/></td>
                        <td><input type="text" name="tbPatientName" id="tbPatientName" style="width:350px;"  readonly/></td>
                    </tr>
                </table>
                <table id="tbTableDispense">
                    <tr class="newRowClass" ><th>Drug </th><th style="width:100px;">Quantity In stock </th><th>Dose</th>
                        <th style="width:100px;">Quantity to Dispense</th></tr>
                    <tr class="newRowClass" >
                        <td ><input type="text" name="tbdrugdispense" id="tbdrugdispense" style="width: 350px; "/></td>
                        <td><input type="text" name="tbquantityInStock" id="tbquantityInStock" style="width:100px;" readonly  /></td>
                        <td>
                            <select id="tbdosage"  name="tbdosage" ></select>
                        </td>
                        <!-- <td><input type="text" name="tbunitPrice" id="tbunitPrice" style="width:100px;"  readonly/></td> -->
                        <td><input type="text"  name="tbquantity" id="tbquantity" style="width:100px;" /></td>
                        <!-- <td><input type="text" name="tbamount" id="tbamount"  style="width:100px;"/></td>... -->
                    </tr>
                </table>
                <input type="button" value="Add Row" onclick="addRow('tbTableDispense')"/>
                <input type="button" value="Remove Row" onclick="deleteRow('tbTableDispense')"/>
                <input class="submit" type="submit" value="Dispense"/>
            </fieldset>
        </form>
    </div>
    <h3 class="boxHeaderP">Patient Encounters</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display"  id="patientEncounters">
        <thead>
        <tr>
            <th width="4%">Action</th>
            <th width="4%">Encounter Date</th>
            <th>Form Name</th>
            <th>Issued Medication</th>
            <th>Location</th>
            <th>User</th>
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
        </tr>
        </tbody>
    </table>
</DIV>