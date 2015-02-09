<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>

<DIV id="dnames">

    <DIV id="incss">
        <form id="voidDose" method="POST" action="">
            <fieldset>
                <legend>Name Void reason</legend>
                <label for="engine">Reason</label>
                <input type="text" name="reason" id="reason" class="required"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="doseForm" action="#">
            <fieldset>
                <label for="engine">Dose Descritpion</label> <input type="text"  name="doseName" id="doseName"  class="required"/>
                <input type="hidden" name="doseEdit" id="doseEdit" value="false"/>
                <input type="hidden" name="doseUUID" id="doseUUID"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="doseTable">
        <thead>
        <tr>
            <th width="4%">Action</th>
            <th>name</th>
            <th>UUID</th>
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
</body>
</html>