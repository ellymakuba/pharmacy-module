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
           #issueDeliveredGoodsDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #issueDeliveredGoodsDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<script type="text/javascript">
$j('#deliveryNoteNumber').change(function(){
    $j('#west_panel_content').load("resources/subpages/issueDeliveredGoods.form?deliveryNoteNumberSelected="+$j("#deliveryNoteNumber").val(), function () {});
    $j('#west_panel_content').empty();
});
function issueStock(){
    var jsonStockTransferData = [];
    var rowIsChecked;
    $j('#issueDeliveredGoodsDIV').find('tr.drugRowClass').each(function(){
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
        jsonStockTransferData.push(rowObject);
    });
    if(confirm("Are you sure you want to approve the stock transfer?")){
    $j.ajax({
        type:"POST",
        url:"issueDeliveredGoods.form",
        data:{values:JSON.stringify(jsonStockTransferData) },
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
String deliveryNoteTrackerUUID=(String)session.getAttribute("deliveryNoteTrackerUUID");
PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(location);
List<DeliveryNoteTracker> deliveryNoteTrackerList = service.getDeliveryNoteTrackerByLocation(pharmacyLocation);
DeliveryNoteTracker deliveryNoteTracker=service.getDeliveryNoteTrackerByUUID(deliveryNoteTrackerUUID);
List<DeliveryNote> deliveryNoteList=service.getDeliveryNotesByDeliveryNoteTracker(deliveryNoteTracker);
%>
<DIV id="issueDeliveredGoodsDIV">
<fieldset>
        <h2 class="centered">Issue Stock</h2>
        <form id="issueDeliveredGoodsForm" name="issueDeliveredGoodsForm">
                <table id="issueDeliveredGoodsTable" class="tdbordered">
                    <tr>
                      <td><label>Select Delivery Number</label></td>
                      <td><select id="deliveryNoteNumber" name="deliveryNoteNumber"><option value="0">Select Stock Transfer</option>
                      <%if(deliveryNoteTrackerList.size()>0){
                      for(DeliveryNoteTracker DeliveryNoteTrackerInstance: deliveryNoteTrackerList){%>
                        <option value=<%=DeliveryNoteTrackerInstance.getUuid() %> ><%=DeliveryNoteTrackerInstance.getDeliveryNoteNumber()%> </option>
                       <%}
                       }%>
                    </tr>
                    <tr>
                        <th>Drug Name</th>
                        <th>Batch No</th>
                        <th>System Quantity</th>
                        <th>Quantity To Issue</th>
                    </tr>
                    <%if(deliveryNoteList.size()>0){
                    for(DeliveryNote deliveryNoteInstance:deliveryNoteList){%>
                    <tr class="drugRowClass">
                        <input type="hidden" name="deliveryNoteItemUUID" value=<%= deliveryNoteInstance.getUuid()%> />
                        <input type="hidden" name="deliveryNoteTrackerUUID" value=<%=deliveryNoteTrackerUUID%> />
                        <td><%= deliveryNoteInstance.getDrugs().getName()%> </td>
                        <td><%= deliveryNoteInstance.getBatchNo()%> </td>
                        <td><%= deliveryNoteInstance.getQuantityIn()-deliveryNoteInstance.getQuantityOut()%> </td>
                        <td><input type="text" name="quantity_to_issue" value=""/></td>
                    </tr>
                    <%}
                    }else{%>
                    <tr><td colspan=5>There is no delivery note products for selected delivery note</td></tr>
                    <%}%>
                </table></br>
                <%if(deliveryNoteList.size()>0){%>
                    <tr><td><input type="button" value="Issue Stock" onclick="issueStock()"/></td></tr>
                <%}%>
        </form>
</fieldset>
</DIV>
</body>
</html>