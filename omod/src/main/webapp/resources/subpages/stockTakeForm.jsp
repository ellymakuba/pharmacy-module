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
           #stockTakeFormDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #stockTakeFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<script type="text/javascript">
function updateInventoryDetails(){
    var jsonStockTakeData = [];
    $j('#stockTakeFormDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('input').each(function(){
        var obj = {}
        var key = $j(this).attr('name');
        var val = $j(this).val();
        obj[key] = val;
        rowObject.push(obj);
        })
        jsonStockTakeData.push(rowObject);
    });
    if(confirm("Are you sure you want to save the stock take for approval?")){
    $j.ajax({
        type:"POST",
        url:"postStockTakeFormRequest.form",
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
List<PharmacyStore> pharmacyStores = service.getPharmacyStoreByLocation(pharmacyLocation);
%>
<DIV id="stockTakeFormDIV">
<fieldset>
        <h2 class="centered">Stock Take Form</h2>
        <form id="stockTakeForm" name="stockTakeForm">
                <table id="stockTakeTable" class="tdbordered">
                    <tr class="newRowClass" >
                        <th>Drug Name</th>
                        <th>Quantity</th>
                        <th>Expiry Date</th>
                        <th>Batch No</th>
                        <th>Buying Price</th>
                        <th>Selling Price</th>
                        <th>Units per Pack</th>
                        <th>New Quantity</th>
                    </tr>
                    <% for(PharmacyStore pharmacyStoreInstance:pharmacyStores){%>
                    <tr>
                       <input type="hidden" name="inventoryItemUUID" value=<%=pharmacyStoreInstance.getUuid()%> />
                       <td><%=pharmacyStoreInstance.getDrugs().getName()%></td>
                       <td><%=pharmacyStoreInstance.getQuantity()%></td>
                       <td><%=pharmacyStoreInstance.getExpireDate().toString().substring(0, 10)%></td>
                       <td><%=pharmacyStoreInstance.getBatchNo()%></td>
                       <td><%=pharmacyStoreInstance.getBuyingPrice()%></td>
                       <td><%=pharmacyStoreInstance.getUnitPrice()%></td>
                       <td><%=pharmacyStoreInstance.getUnitsPerPack()%></td>
                       <td><input type="text" name="newQuantity" style="width:100px;"/></td>
                    </tr>
                    <% } %>
                    <tr><td><input type="button" value="Save Stock Take For Approval" onclick="updateInventoryDetails()"/></td></tr>
                </table>
        </form>
</fieldset>
</DIV>
</body>
</html>