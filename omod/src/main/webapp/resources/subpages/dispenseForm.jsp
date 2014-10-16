
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
                    <tr class="newRowClass" ><th>Drug </th><th style="width:100px;">Quantity In stock </th><th>Dose </th><th>UnitPrice</th>
                        <th style="width:100px;">Quantity to Dispense</th><th>Amount</th></tr>
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
                </table>
                <table><tr><td>Total Amount</td><td></td><td></td><td></td><td></td><td><input type="text" name="totalAmount" id="totalAmount" readonly></td></tr></table>
                <input type="button" value="Add Row" onclick="addRow('tableDispense')"/>
                <input type="button" value="Remove Row" onclick="deleteRow('tableDispense')"/>
                <input class="submit" type="submit" value="Queue for payment"/>
            </fieldset>
        </form>
    </div>
    <div id="receiptDIV">
        <form id="completeReceipt" name="completeReceipt">
            <fieldset>
                <table>
                    <tr>
                        <td>Patient Identifier</td>
                        <td><input type="text" name="patient" id="patient" readonly/></td>
                        <td><input type="text" name="personName" id="personName" style="width:250px;"  readonly/></td>
                        <td>Waiver No</td>
                        <td><input type="text" name="waiverNo" id="waiverNO" style="width:50px;" /></td>
                    </tr>
                </table>
                <table id="receiptToComplete">
                    <thead><tr><th>Drug</th><th>Quantity</th><th>Unit Price</th><th>Amount</th><th>Amount waived</th></tr></thead>
                    <tbody></tbody>
                </table>
            </fieldset>
            <fieldset>
                <table id="waiverAndTotals">
                    <tr>
                        <th>Receipt Totals</th>
                        <th>Amount Paid</th>
                        <th>Waived Amount</th>
                        <th>Balance</th>
                    </tr>
                    <tr>
                        <td><input type="text" name="receiptTotal" id="receiptTotal" readonly></td>
                        <td><input type="text" name="amountPaid" id="amountPaid" ></td>
                        <td><input type="text" name="amountWaived" id="amountWaived" value="0"></td>
                        <td><input type="text" name="balance" id="balance" value="0"></td>
                    </tr>
                </table>
                <input class="submit" type="submit" value="Complete Transaction"/>
            </fieldset>
        </form>
    </div>
    <table width="100%" id="unClearedReceipts">
        <thead>
        <th>EncounterUUID</th>
        <th>Patient ID</th>
        <th>Total</th>
        <th>Patient Name</th>
        <Th>Time</Th>
        <Th>Print</Th>
        </thead>
        <tbody>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        </tbody>
    </table>
</DIV>
</body>
</html>