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
           #inventoryMetaDataDIV{
             margin:10px;
             font-weight:bold;
             border:5px #AAAAAA;
           }
           #inventoryMetaDataDIV table{
             background-color:#EBEBFF;
           }
           h2.centered{
            text-align:center;
           }
    </style>
<script type="text/javascript">
function submitForm(){
    var json = [];
    $j('#inventoryMetaDataTable').find('tr').each(function(){
        var obj = {}
        var  td = $j(this).find('input,select,hidden');
        var key = td.attr('name');
        var val = td.val();
        obj[key] = val;
        json.push(obj);
    });
    $j.ajax({
        type:"POST",
        url:"inventoryMetaData.form?metaDataItemUUID="+$j("#inventoryMetaDataActiveUUID").val(),
        data:{values:JSON.stringify(json) },
        success:function (result) {}
    });

     $j('#west_panel_content').load("resources/subpages/inventoryMetaData.form", function () {});
     $j('#west_panel_content').empty();
}
$j("input[name='drug_name']").live("focus", function () {
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
        select:function (event, ui){}
    });
});
$j("input[name='drug_search']").live("focus", function () {
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
            url:"activeInventoryMetaData.form",
            data:{"drugName":ui.item.value},
            dataType:"json",
            success:function(result){
                $j('#west_panel_content').load("resources/subpages/inventoryMetaData.form", function () {});
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
  String inventoryMetaDataActiveUUID=(String)session.getAttribute("inventoryMetaDataActiveUUID");

%>
<div id="inventoryMetaDataDIV">
<form id="inventoryMetaDataForm"/>
<h2 class="centered">Inventory MetaData SetUp</h2>
<% if(inventoryMetaDataActiveUUID !=null){
InventoryMetaData inventoryMetaData=service.getInventoryMetaDataByUUID(inventoryMetaDataActiveUUID);
if(inventoryMetaData !=null){
Drug drugFromContext = Context.getConceptService().getDrugByNameOrId(inventoryMetaData.getDrug().getId().toString());
%>
<table style="width:100%;" id="inventoryMetaDataTable" class="tdbordered">
    <tr><td>Drug Name</td><td><%=drugFromContext.getName()%></td></tr>
    <tr><td>Category</td><td><select name="category">
    <%
     for(PharmacyCategory pharmacyCategoryInstance:categoryList){
     if(pharmacyCategoryInstance.getUuid()==inventoryMetaData.getCategory().getUuid()){ %>
     <option selected value=<%=pharmacyCategoryInstance.getUuid() %> ><%=pharmacyCategoryInstance.getName()%></option>
    <% } %>
     <option value=<%=pharmacyCategoryInstance.getUuid() %> ><%=pharmacyCategoryInstance.getName()%></option>
      <%}
    %>
    </select></td></tr>
    <tr><td>Buying Price</td><td><input type="text" name="buying_price"  value=<%=inventoryMetaData.getBuyingPrice()%> /></td></tr>
    <tr><td>Selling Price</td><td><input type="text" name="selling_price"  value=<%=inventoryMetaData.getSellingPrice()%> /></td></tr>
    <tr><td>Units in a pack</td><td><input type="text" name="units_in_a_packs"  value=<%=inventoryMetaData.getUnitsInPack()%> /></td></tr>
    <tr><td><input type="hidden" name="inventoryMetaDataActiveUUID" id="inventoryMetaDataActiveUUID" value=<%=inventoryMetaDataActiveUUID%> /></td><td></td></tr>
    <tr><td></td><td><input type="button" onclick="submitForm()" value="Edit Data"/></td></tr>
</table></br></br>
<% }
}
else{ %>
<table style="width:100%;" id="inventoryMetaDataTable" class="tdbordered">
    <tr><td>Drug Name</td><td><input type="text" name="drug_name" style="width:90%;"/></td></tr>
    <tr><td>Category</td><td><select name="category"><option value="0">Select Category</option>
    <%
     for(PharmacyCategory pharmacyCategoryInstance:categoryList){ %>
       <option value=<%=pharmacyCategoryInstance.getUuid() %> ><%=pharmacyCategoryInstance.getName()%></option>
     <% }
    %>
    </select></td></tr>
    <tr><td>Buying Price</td><td><input type="text" name="buying_price"  /></td></tr>
    <tr><td>Selling Price</td><td><input type="text" name="selling_price"  /></td></tr>
    <tr><td>Units in a pack</td><td><input type="text" name="units_in_a_packs"  /></td></tr>
    <tr><td></td><td><input type="button" onclick="submitForm()" value="Save Data"/></td></tr>
</table></br></br>
<% } %>
<table id="inventorySearchResult" style="width:100%;" class="tdbordered">
<tr><td>Type drug to search</td><td><input type="text" name="drug_search" style="width:90%;"/></td></tr>
</table>
</form>
</div>
</body>