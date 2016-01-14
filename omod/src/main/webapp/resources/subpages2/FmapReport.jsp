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
        <form id="fmapform" action="#">
            <fieldset>
                <legend>Name</legend>
                <label for="engine">Date From</label> <input type="text"  name="datefrom" id="datef" class="required"/><br/>
                <label for="engine">Date To</label> <input type="text" name="dateto" id="datet"/>
                <input class="submit" type="submit" value="Click To View Reports"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="fmaptable">
        <button type="button" id="export" style="background-color: #FF884D;">Export to excel</button>
        <thead id='headers'>
        <tr>
            <th width="4%">Regimen Code</th>
            <th>Regimen Name</th>
            <th>Patient Count</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

</DIV>
<div id="reps"></div>
</body>
</html>