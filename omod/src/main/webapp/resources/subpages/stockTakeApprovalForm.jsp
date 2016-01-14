<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
           #stockTakeApprovalFormDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #stockTakeApprovalFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<script type="text/javascript">
function approveStockTake(){
    var jsonStockTakeData = [];
    var rowIsChecked;
    $j('#stockTakeApprovalFormDIV').find('tr.drugRowClass').each(function(){
        var rowObject=[];
        rowIsChecked=$j(this).find('input[type="checkbox"]').attr('checked');
        $j(this).find('input').each(function(){
            var obj = {}
            if($j(this).is('input[type="hidden"]')){
              var key = $j(this).attr('name');
              var val = $j(this).val();
            }
            else if($j(this).is('input[type="checkbox"]')){
			 var key = $j(this).attr('name');
              var val = rowIsChecked;
            }
            obj[key] = val;
            rowObject.push(obj);
        });
        jsonStockTakeData.push(rowObject);
    });
    if(confirm("Are you sure you want to approve the stock take?")){
    $j.ajax({
        type:"POST",
        url:"postStockTakeApprovalForm.form",
        data:{values:JSON.stringify(jsonStockTakeData) },
        success:function(){
            window.location.reload(true);
        }
    });
    }
}
</script>
</head>
<body>
<%
PharmacyService service = Context.getService(PharmacyService.class);
service=Context.getService(PharmacyService.class);
String location=(String)session.getAttribute("location");
PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(location);
List<PharmacyStore> pharmacyStores = service.getPharmacyStoreForApprovalByLocation(pharmacyLocation);
%>
<DIV id="stockTakeApprovalFormDIV">
<fieldset>
        <h2 class="centered">Stock Take Approval Form</h2>
        <form id="stockTakeApprovalForm" name="stockTakeApprovalForm">
                <table id="stockTakeApprovalTable" class="tdbordered">
                    <tr>
                        <th>Drug Name</th>
                        <th>Batch No</th>
                        <th>System Quantity</th>
                        <th>Stock Take Quantity</th>
                        <th>Approve</th>
                    </tr>
                    <% if(pharmacyStores.size() > 0){
                     for(PharmacyStore pharmacyStoreInstance:pharmacyStores){%>
                    <tr class="drugRowClass">
                       <input type="hidden" name="inventoryItemUUID" value=<%=pharmacyStoreInstance.getUuid()%> />
                       <td><%=pharmacyStoreInstance.getDrugs().getName()%></td>
                       <td><%=pharmacyStoreInstance.getBatchNo()%></td>
                       <td><%=pharmacyStoreInstance.getQuantity()%></td>
                       <td><%=pharmacyStoreInstance.getStockTakeQuantityWaitingForApproval()%></td>
                       <td><input type="checkbox" name="approved"/></td>
                    </tr>
                    <% }%>
                     <tr><td><input type="button" value="Approve Stock Take" onclick="approveStockTake()"/></td></tr>
                   <%}
                    else{%>
                    <tr><td>There are no products waiting for approval</td><td>None</td><td>None</td><td>None</td></tr>
                    <% }%>
                </table>
        </form>
</fieldset>
</DIV>
</body>
</html>