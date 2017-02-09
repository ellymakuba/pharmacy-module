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
           #stockTransferApprovalFormDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #stockTransferApprovalFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<script type="text/javascript">
$j('#stockTransferTracker').change(function(){
    $j('#west_panel_content').load("resources/subpages/stockTransferApprovalForm.form?transactionTrackerUUIDSelected="+$j("#stockTransferTracker").val(), function () {});
    $j('#west_panel_content').empty();
});
function approveStockTransfer(){
    var jsonStockTransferData = [];
    var rowIsChecked;
    $j('#stockTransferApprovalFormDIV').find('tr.drugRowClass').each(function(){
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
        url:"approveStockTransfer.form",
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
String transactionTrackerUUIDOptionSelected=(String)session.getAttribute("transactionTrackerUUIDOptionSelected");
PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(location);
List<StockTransferTracker> stockTransferTrackerList = service.getStockTransferTrackerNotApproved(pharmacyLocation);
StockTransferTracker stockTransferTracker=service.getStockTransferTrackerByUUID(transactionTrackerUUIDOptionSelected);
List<PharmacyStoreOutgoing> pharmacyStoreOutgoingList=service.getPharmacyStoreOutgoingByStockTransferTracker(stockTransferTracker);
Integer sumQuantityOfStockForDrug=0;

%>
<DIV id="stockTransferApprovalFormDIV">
<fieldset>
        <h2 class="centered">Stock Transfer Approval Form</h2>
        <form id="stockTransferApprovalForm" name="stockTransferApprovalForm">
                <table id="stockTransferApprovalTable" class="tdbordered">
                    <tr>
                      <td><label>Select Transaction Details</label></td>
                      <td><select id="stockTransferTracker" name="stockTransferTracker"><option value="0">Select Stock Transfer</option>
                      <%if(stockTransferTrackerList.size()>0){
                      for(StockTransferTracker stockTransferInstance: stockTransferTrackerList){%>
                        <option value=<%=stockTransferInstance.getUuid() %> ><%=stockTransferInstance.getDateCreated()%> </option>
                       <%}
                       }%>
                    </tr>
                    <tr>
                        <th>Drug Name</th>
                        <th>Batch No</th>
                        <th>System Quantity</th>
                        <th>Quantity Requested</th>
                        <th>Approve</th>
                    </tr>
                    <%if(pharmacyStoreOutgoingList.size()>0){
                    for(PharmacyStoreOutgoing pharmacyStoreOutgoingInstance:pharmacyStoreOutgoingList){
                        sumQuantityOfStockForDrug=0;
                        List<PharmacyStore> inventoryBatchesForSingleDrug=service.getPharmacyStoreByNameAndLocation(pharmacyStoreOutgoingInstance.getDrug().getName(),pharmacyLocation);
                        if(inventoryBatchesForSingleDrug.size()>0){
                        for(PharmacyStore pharmacyStoreInstance:inventoryBatchesForSingleDrug){
                            sumQuantityOfStockForDrug=sumQuantityOfStockForDrug+pharmacyStoreInstance.getQuantity();
                        }
                    }%>
                    <tr class="drugRowClass">
                        <input type="hidden" name="drugUUID" value=<%= pharmacyStoreOutgoingInstance.getDrug().getUuid()%> />
                        <input type="hidden" name="pharmacyOutgoingUUID" value=<%= pharmacyStoreOutgoingInstance.getUuid()%> />
                        <input type="hidden" name="issueingLocation" value=<%= pharmacyStoreOutgoingInstance.getLocation().getUuid()%> />
                        <input type="hidden" name="receivingLocation" value=<%= pharmacyStoreOutgoingInstance.getDestination().getUuid()%> />
                        <input type="hidden" name="drugQuantityRequested" value=<%= pharmacyStoreOutgoingInstance.getQuantityIn()%> />
                        <input type="hidden" name="expiryDate" value=<%= pharmacyStoreOutgoingInstance.getExpireDate()%> />
                        <input type="hidden" name="serial_number" value=<%= pharmacyStoreOutgoingInstance.getS11()%> />
                        <td><%= pharmacyStoreOutgoingInstance.getDrug().getName()%> </td>
                        <td><%= pharmacyStoreOutgoingInstance.getBatchNo()%> </td>
                        <td><%= sumQuantityOfStockForDrug%> </td>
                        <td><%= pharmacyStoreOutgoingInstance.getQuantityIn()%> </td>
                        <% if(pharmacyStoreOutgoingInstance.getApproved()==true){%>
                         <td></td>
                        <% }else{ %>
                        <td><input type="checkbox" name="approved"/></td>
                        <%}%>
                    </tr>
                    <%}
                    }else{%>
                    <tr><td colspan=5>There is no drug transactions for selected transaction tracker</td></tr>
                    <%}%>
                </table></br>
                <%if(pharmacyStoreOutgoingList.size()>0){%>
                    <tr><td><input type="button" value="Approve Stock Transfer" onclick="approveStockTransfer()"/></td></tr>
                <%}%>
        </form>
</fieldset>
</DIV>
</body>
</html>