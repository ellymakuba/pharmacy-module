<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<DIV id="FCDRRDIV">
    <DIV id="incss">
        <form id="FCDRRForm" action="#">
            <fieldset>
                <legend>Name</legend>
                <label for="engine">Date From</label> <input type="text"  name="FCDRRDateFrom" id="FCDRRDateFrom" class="required"/><br/>
                <label for="engine">Date To</label> <input type="text" name="FCDRRDateTo" id="FCDRRDateTo" class="required"/>
                <input class="submit" type="submit" value="Click To View Reports"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="FCDRRTable">
        <button type="button" id="export" style="background-color: #FF884D;"  >Export to excel</button>
        <thead>
        <tr>
            <th width="4%">Drug Name</th>
            <th>Basic Unit</th>
            <th>Beginning Balance</th>
            <th>Quantity Received</th>
            <th>Quantity Dispensed</th>
            <th>Losses</th>
            <th>Adjustments</th>
            <th>Physical Count</th>
            <th>Drugs with less than six month to expiry</th>
            <th>Days out of stock</th>
            <th>Quantity Required for ressuply</th>
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
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </tfoot>
    </table>

</DIV>
<div id="generationDIV"></div>
</body>
</html>