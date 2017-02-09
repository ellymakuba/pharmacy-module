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
           #supplierDIV{
             margin:10px;
             font-weight:bold;
             border:5px #AAAAAA;
           }
           #supplierDIV table{
             background-color:#EBEBFF;
           }
           h2.centered{
            text-align:center;
           }
    </style>
<script type="text/javascript">
function editForm(){
    var json = [];
    $j('#supplierTable').find('tr').each(function(){
        var obj = {}
        var  td = $j(this).find('input,select,hidden');
        var key = td.attr('name');
        var val = td.val();
        obj[key] = val;
        json.push(obj);
    });
    $j.ajax({
        type:"POST",
        url:"editsupplier.form?supplierUUID="+$j("#supplierActiveUUID").val(),
        data:{values:JSON.stringify(json) },
        success:function (result) {
        }
    });
}




function submitForm(){
    var json = [];
    $j('#supplierTable').find('tr').each(function(){
        var obj = {}
        var  td = $j(this).find('input,select,hidden');
        var key = td.attr('name');
        var val = td.val();
        obj[key] = val;
        json.push(obj);
    });
    $j.ajax({
        type:"POST",
        url:"supplier.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {}
    });

     $j('#west_panel_content').load("resources/subpages/supplier.form", function () {});
     $j('#west_panel_content').empty();
}
$j("input[name='supply_search']").live("focus", function () {
    $j(this).autocomplete({
            search:function () {
                $j(this).addClass('working');         },
            source:function (request, response) {
                $j.getJSON("getDrugForAutocomplete.form?searchDrug=" +request.term, function (result) {
                    $j("#incomingdrug").removeClass('working');
                    response($j.each(result, function (index, item) {
                        return {
                            label:item,
                            value:item
                        }
                    }));

                });

            },
        minLength:3,
        select:function (event, ui){
        $j.ajax({
            type:"POST",
            url:"activeSupplier.form",
            data:{"supplierName":ui.item.value},
            dataType:"json",
            success:function(result){
                $j('#west_panel_content').load("resources/subpages/supplier.form", function () {});
                $j('#west_panel_content').empty();
            }
        })
        }
    });
});
</script>
</head>
<body>
<%
 PharmacyService service = Context.getService(PharmacyService.class);
 List <PharmacyCategory> categoryList=service.getPharmacyCategory();
  String locationVal = (String)session.getAttribute("location");
  PharmacyLocations pharmacyLocation=service.getPharmacyLocationsByName(locationVal);
  String supplierActiveUUID=(String)session.getAttribute("supplierActiveUUID");

%>
<div id="supplierDIV">
<form id="supplierForm"/>
<h2 class="centered">Inventory MetaData SetUp</h2>
<% if(supplierActiveUUID !=null){
PharmacySupplier pharmacySupplier=service.getPharmacySupplierByUuid(supplierActiveUUID);
if(pharmacySupplier !=null){
%>
<table style="width:100%;" id="supplierTable" class="tdbordered">
    <tr><td>Supplier Name</td><td><input type="text" name="supplier_name"  value=<%=pharmacySupplier.getName()%> /></td></tr>
    <tr><td>Supplier Description</td><td><input type="text" name="supplier_description"  value=<%=pharmacySupplier.getDescription()%> /></td></tr>
    <tr><td><input type="hidden" name="supplierActiveUUID" id="supplierActiveUUID" value=<%=supplierActiveUUID%> /></td><td></td></tr>
    <tr><td></td><td><input type="button" onclick="editForm()" value="Edit Data"/></td></tr>
</table></br></br>
<% }
}
else{ %>
<table style="width:100%;" id="supplierTable" class="tdbordered">
    <tr><td>Supplier Name</td><td><input type="text" name="supplier_name" /></td></tr>
    <tr><td>Supplier Description</td><td><input type="text" name="supplier_description"  style="width:90%;"/></td></tr>
    <tr><td></td><td><input type="button" onclick="submitForm()" value="Save Data"/></td></tr>
</table></br></br>
<% } %>
<table id="supplySearchResult" style="width:100%;" class="tdbordered">
<tr><td>Type Supplier to search</td><td><input type="text" name="supply_search" style="width:90%;"/></td></tr>
</table>
</form>
</div>
</body>