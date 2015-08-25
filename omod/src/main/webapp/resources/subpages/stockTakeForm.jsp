<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
    </style>

</head>
<body>
<DIV id="dtab_1">
    <div id="stockTakeFormDIV" class="ui-layout-content">
        <h1>Update Stock Quantities</h1>
        <form id="stockTakeForm" name="stockTakeForm">
            <fieldset>
                <table id="stockTakeTable">
                    <thead>
                    <tr class="newRowClass" >
                        <th>Drug Name</th>
                        <th>Quantity</th>
                        <th>Expiry Date</th>
                        <th>Batch No</th>
                        <th>Buying Price</th>
                        <th>Selling Price</th>
                         <th>U@P</th>
                        <th>Dose</th>
                        <th>New Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </fieldset>
        </form>
    </div>
</DIV>
</body>
</html>