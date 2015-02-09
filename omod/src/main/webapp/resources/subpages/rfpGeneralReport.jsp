<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>

<DIV id="report">
    <DIV id="incss">
        <form id="reportform" action="#">
            <fieldset>
                <legend>Name</legend>
                <label for="engine">Date From</label> <input type="text"  name="datefrom" id="datef" class="required"/><br/>
                <label for="engine">Date To</label> <input type="text" name="dateto" id="datet"/>
                <input class="submit" type="submit" value="Click To View Reports"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="treport">
        <button type="button" id="export" style="background-color: #FF884D;"  >Export to excel</button>
        <thead>
        <tr>
            <th width="4%">Medication</th>
            <th>Quantity(system)</th>
            <th>Quantity(Stock)</th>
            <th>Difference</th>
            <th>%difference</th>
            <th>Quantity Receieved</th>
            <th>Unit cost</th>
            <th>Quantity Sold</th>
            <th>Amount Waived</th>
            <th>#Waived</th>
            <th>#Drug Dispensed</th>
            <th>cash expected</th>
            <th>Discount</th>
            <th>Actual Cash</th>
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
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </tfoot>
    </table>

</DIV>
<div id="reps"></div>
</body>
</html>