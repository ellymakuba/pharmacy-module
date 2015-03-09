<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">
    </script>
</head>
<body>
<DIV id="rfpCashierReportDiv">
    <form id="rfpCashierReportForm" action="#">
        <fieldset>
            <legend>Name</legend>
            <label for="engine">Date From</label> <input type="text"  name="cashierReportDateFrom" id="cashierReportDateFrom" class="required"/><br/>
            <label for="engine">Date To</label> <input type="text" name="cashierReportDateTo" id="cashierReportDateTo" class="required"/>
            <input class="submit" type="submit" value="Click To View Report"/>
        </fieldset>
    </form>
</DIV>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="cashierReportTable">
    <button type="button" id="export" style="background-color: #FF884D;"  >Export to excel</button>
    <thead>
    <tr>
        <th width="4%">Medication</th>
        <th>Unit cost</th>
        <th>Quantity Sold</th>
        <th>Amount Waived</th>
        <th>No of Waivers</th>
        <th>cash expected</th>
        <th>Discount</th>
        <th>Cash Collected</th>
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
        <td></td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
    </tr>
    </tfoot>
</table>
<div id="reps"></div>
</body>
</html>