<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <form id="drugAdministrationForm" name="drugAdministrationForm">
        <fieldset>
            <table id="tabledrugAdministration">
                <tr class="newRowClass"><th>Drug </th><th>Dose </th><th>UnitPrice</th></tr>
                <tr class="newRowClass">
                    <td style="border:1px solid black" ><input type="text" name="drug" id="drug" style="width: 350px; "/></td>
                    <td>
                        <select id="dosage"  name="dosage" ></select>
                    </td>
                    <td class="newRowClass"><input type="text" name="unitPrice" id="unitPrice" style="width:100px;"/></td>
                </tr>
            </table>
            <input type="button" value="Add Row" onclick="addRow('tabledrugAdministration')"/>
            <input type="button" value="Remove Row" onclick="deleteRow('tabledrugAdministration')"/>
            <br/>
            <input class="submit" type="submit" value="Submit"/>
        </fieldset>
    </form>
</DIV>
</body>
</html>