<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>

<DIV id="dispensed">

    <DIV id="incss">

        <form id="dispensedform" action="#">
            <fieldset>

                <legend>Name</legend>

                <label for="engine">Date From</label> <input type="text"  name="datefrom" id="datefrom" class="required"/><br/>
                <label for="engine">Date To</label> <input type="text" name="dateto" id="dateto"/>
                <input class="submit" type="submit" value="Click To View Reports"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="tdispensed">
        <button type="button" id="export_dispensed" style="background-color: #FF884D;"  >Export to excel</button>
        <thead>
        <tr>
            <th width="4%">Recipt NO</th>
            <th>PatientID</th>
            <th>Medication</th>
            <th>Amount Waived</th>
            <th>Amount Discounted</th>
            <th>Dispensed By</th>
            <th>Date Dispensed</th>
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
        <tfoot>
        <tr>
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

</body>
</html>