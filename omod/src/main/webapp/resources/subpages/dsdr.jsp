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
        <form id="dsdrform" action="#">
            <fieldset>
                <legend>Name</legend>
                <label for="engine">Date From</label> <input type="text"  name="datefrom" id="datef" class="required"/><br/>
                <label for="engine">Date To</label> <input type="text" name="dateto" id="datet"/>
                <input class="submit" type="submit" value="Click To View Reports"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="dsdrTable">
        <button type="button" id="export" style="background-color: #FF884D;"  >Export to excel</button>
        <thead>
        <tr>
            <th width="4%">Drug name</th>
            <th>Opening Stock</th>
            <th>Stock adjustment</th>
            <th>Quantity Dispensed</th>
            <th>Physical Stock Count</th>
            <th>Quantity Receieved</th>
            <th>current System stock count</th>
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
<div id="reps"></div>
</body>
</html>