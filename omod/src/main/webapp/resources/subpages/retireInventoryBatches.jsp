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
           #retireInventoryBatchesFormDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #retireInventoryBatchesFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<script type="text/javascript">
function retireInventoryBatches(){
    var jsonRetireInventoryBatchesData = [];
    $j('#retireInventoryBatchesFormDIV').find('tr').each(function(){
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
        jsonRetireInventoryBatchesData.push(rowObject);
    });
    if(confirm("Are you sure you want to retire selected batches?")){
    $j.ajax({
        type:"POST",
        url:"retireInventoryBatches.form",
        data:{values:JSON.stringify(jsonRetireInventoryBatchesData) },
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
List<PharmacyStore> pharmacyStoreList = service.getPharmacyStoreByLocationPlusRetired(pharmacyLocation);
%>
<DIV id="retireInventoryBatchesFormDIV">
<fieldset>
        <h2 class="centered">Retire Inventory Batches Form</h2>
        <form id="retireInventoryBatchesForm" name="retireInventoryBatchesForm">
                <table id="retireInventoryBatchesTable" class="tdbordered">
                    <tr class="newRowClass" >
                        <th>Drug Name</th>
                        <th>Quantity</th>
                        <th>Expiry Date</th>
                        <th>Batch No</th>
                        <th>Buying Price</th>
                        <th>Selling Price</th>
                        <th>Retired</th>
                        <th>Retire</th>
                    </tr>
                    <% for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){%>
                    <tr>
                      <input type="hidden" name="inventoryItemUUID" value=<%=pharmacyStoreInstance.getUuid()%> />
                      <td><%=pharmacyStoreInstance.getDrugs().getName()%> </td>
                      <td><%=pharmacyStoreInstance.getQuantity()%> </td>
                      <td><%=pharmacyStoreInstance.getExpireDate().toString().substring(0, 10)%> </td>
                      <td><%=pharmacyStoreInstance.getBatchNo()%> </td>
                      <td><%=pharmacyStoreInstance.getBuyingPrice()%> </td>
                      <td><%=pharmacyStoreInstance.getUnitPrice()%> </td>
                      <td><%=pharmacyStoreInstance.getVoided()%> </td>
                      <td><input type="checkbox" name="retire"/></td>
                    </tr>
                    <% } %>
                    <tr><td><input type="button" value="Retire Batches" onclick="retireInventoryBatches()"/></td></tr>
                </table>
        </form>
</fieldset>
</DIV>
</body>
</html>