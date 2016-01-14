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

<script type="text/javascript">
     function postInventoryBatchesToRetire(){
	var json = [];
	var rowIsChecked;
    $j('#retireInventoryBatchesDIV').find('tr.drugRowClass').each(function(){
        var rowObject=[];
        rowIsChecked=$j(this).find('input[type="checkbox"]').attr('checked');
        $j(this).find('td').each(function(){
            var obj = {}
            var  td = $j(this).find('input');
            if(td.is('input[type="text"]')){
              var key = td.attr('name');
              var val = td.val();
            }
            else if(td.is('input[type="checkbox"]')){
			 var key = td.attr('name');
              var val = rowIsChecked;
            }
            obj[key] = val;
            rowObject.push(obj);
        });
        json.push(rowObject);
    });
        	 $j.ajax({
                    	 type:"POST",
                    	 url:"resources/subpages/retireInventoryBatches.form",
                    	 data:{values:JSON.stringify(json)}
                    	 })
        	}
 var tableToExcel = (function() {
        			var uri = 'data:application/vnd.ms-excel;base64,'
        					, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        					, base64 = function(s) {
        				return window.btoa(unescape(encodeURIComponent(s)))
        			}
        			, format = function(s, c) {
        				return s.replace(/{(\w+)}/g, function(m, p) {
        					return c[p];
        				})
        			}
        			return function(table, name) {
        				if (!table.nodeType)
        					table = document.getElementById(table)
        				var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        				window.location.href = uri + base64(format(template, ctx))
        			}
        		}()
);
</script>
<%
	PharmacyService service = Context.getService(PharmacyService.class);
    List<PharmacyStore> pharmacyStoreList;
	String location=(String)session.getAttribute("location");
	PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
	pharmacyStoreList = service.getAllPharmacyStorePlusRetired(pharmacyLocation);
	Integer count=0;
%>

<div id="retireInventoryBatchesDIV">
<form name="retireInventoryForm" action="#" method="POST" id="retireInventoryForm">
<% if(pharmacyStoreList!=null) {

%>
<fieldset>
<h1><a>Retire Inventory Batches Form</h1>
	<table id="retireInventoryTable" style="width:100%;">
			<tr>
			<th style="width:1%;"></th>
			<th style="width:20%;">Medication</th>
			<th style="width:10%;">Batch NO</th>
            <th style="width:10%;">Quantity</th>
            <th style="width:10%;">Buying Price</th>
            <th style="width:10%;">Selling Price</th>
            <th style="width:10%;">Expiry Date</th>
            <th style="width:10%;">Retire</th>
			</tr>

				<% for(PharmacyStore pharmacyStoreInstance:pharmacyStoreList){
						Drug drugFromContext = Context.getConceptService().getDrugByNameOrId(pharmacyStoreInstance.getDrugs().getId().toString());
						%>
						<tr class="drugRowClass">
						<td><input type="hidden" name="drugUUID" value=<%=pharmacyStoreInstance.getUuid()%> style="width:10%;" /></td>
						<td><input type="text" name="drugName" value=<%=pharmacyStoreInstance.getDrugs().getName()%> style="width:100%;"/></td>
						<td><input type="text" name="batchNO" value=<%=pharmacyStoreInstance.getBatchNo() %> style="width:100%;"/></td>
						<td><input type="text" name="quantity" value=<%=pharmacyStoreInstance.getQuantity() %> style="width:100%;"/></td>
						<td><input type="text" name="buyingPrice" value=<%=pharmacyStoreInstance.getBuyingPrice() %> style="width:100%;"/></td>
						<td><input type="text" name="sellingPrice" value=<%=pharmacyStoreInstance.getUnitPrice() %> style="width:100%;"/></td>
						<td><input type="text" name="expiryDate" value=<%=pharmacyStoreInstance.getExpireDate() %> style="width:100%;"/></td>
						<% if(pharmacyStoreInstance.getVoided()){ %>
						<td><input type="checkbox" name="retire" checked /></td>
						<% }
						else{ %>
						<td><input type="checkbox" name="retire" /></td>
						<%}
						%>
						</tr>
				<% count++;
				}%>
				<tr><td></td><td></td><td></td><td></td><td></td><td></td><td><input type="button" value="Retire Batches" onclick="postInventoryBatchesToRetire()"/></td></tr>
	</table>
	</fieldset>
</form>
<% } %>
</div>