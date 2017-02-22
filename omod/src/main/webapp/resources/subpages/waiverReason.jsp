<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
        table.visibleBorder td{
            border:1px solid black;
            background-color:#EBEBFF;
        }
        table.tdbordered td{
                    cell-padding:5px;
                    border:1px solid white;
                }
           #pharmacyLocationUserDIV{
             margin:10px;
             font-weight:bold;
             border:5px #AAAAAA;
           }
           #main_waiver_div table{
             background-color:#EBEBFF;
           }
           h2.centered{
            text-align:center;
           }
    </style>
<div id="main_waiver_div">
<form id="waiverForm"/>
<h2 class="centered">Waiver Reason Form</h2>
<table style="width:100%;" id="reasons" class="tdbordered">
    <tr><td>Reason</td><td><input type="text" name="reason" id="reason" value="" /></td></tr>
    <tr><td></td><td><input type="button" onclick="saveForm()" value="Save Data"/></td></tr>
</table>
</form>
</div>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="treasons">
        <thead>
        <tr>
            <th width="4%">Action</th>
            <th>UUID</th>
            <th>Reason</th>
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
<openmrs:htmlInclude file="/moduleResources/pharmacy/jspharmacy/waiver.js"/>