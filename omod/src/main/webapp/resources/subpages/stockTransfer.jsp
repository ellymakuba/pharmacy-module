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
           #stockTransferFormDIV{
             margin:10px;
             border:5px #AAAAAA;
           }
           #stockTransferFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
</style>
<title>Stock Transfer Form</title>
<script type="text/javascript">
var rowCounter = document.getElementById("stockTransferTable").rows.length+1;
function addRow(tableID) {
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var colCount = table.rows[0].cells.length;
    for(var i=0; i<colCount; i++) {
        var newcell = row.insertCell(i);
        newcell.innerHTML = table.rows[1].cells[i].innerHTML;
        //alert(newcell.childNodes);
        switch(newcell.childNodes[0].type) {
            case "text":
                newcell.childNodes[0].value = "";
                if(newcell.childNodes[0].name=="outgoingDrug"){
                    newcell.childNodes[0].id="outgoingDrug_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="quantityInStock"){
                    newcell.childNodes[0].id="quantityInStock_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="expiryDate"){
                    newcell.childNodes[0].id="expiryDate_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="batchNo"){
                    newcell.childNodes[0].id="batchNo_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="buyingPrice"){
                    newcell.childNodes[0].id="buyingPrice_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="unitPrice"){
                    newcell.childNodes[0].id="unitPrice_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="outgoingQuantity"){
                    newcell.childNodes[0].id="outgoingQuantity_" + rowCounter;
                }
                break;
            case "checkbox":
                newcell.childNodes[0].checked = false;
                break;
            case "select-one":
                newcell.childNodes[0].selectedIndex = 0;
                break;
        }
    }
    rowCounter ++ ;
}
function deleteRow(tableID) {
    try {
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;
        if(rowCount <= 1) {
            alert("Cannot delete all the rows.");
        }
        else{
            table.deleteRow(rowCount -1);
        }

    }catch(e) {
        alert(e);
    }
}
function removeRows() {
    $j("#stockTransferTable").find("tr:gt(1)").remove();
}

$j("form#stockTransferForm").submit(function () {
    $j("#errorDialog").empty();
    var quantityToTransferNotEntered=false;
    var jsonS11Data = [];
    var dateNotEntered=false;
    var batchNoNotEntered=false;
    var buyingPriceNotEntered=false;
    var sellingPriceNotEntered=false;
    $j('#stockTransferFormDIV').find('tr:not(eq(1))').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var td=$j(this).find('input,select');
            var key = td.attr('name');
            var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        jsonS11Data.push(rowObject);
    });
    $j("input[name='expiryDate']").each(function(){
        if($j(this).val()==""){
            dateNotEntered=true;
        }
    })
    $j("input[name='batchNo']").each(function(){
        if($j(this).val()==""){
            batchNoNotEntered=true;
        }
    })
    $j("input[name='buyingPrice']").each(function(){
        if($j(this).val()==""){
            buyingPriceNotEntered=true;
        }
    })
    $j("input[name='unitPrice']").each(function(){
        if($j(this).val()==""){
            sellingPriceNotEntered=true;
        }
    })
    $j("input[name='outgoingQuantity']").each(function(){
        if($j(this).val()==""){
            quantityToTransferNotEntered=true;
        }
    })
    if(quantityToTransferNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please enter quantities for all the drugs" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if(dateNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter expiry dates for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if(batchNoNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter batch numbers for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
     else if(buyingPriceNotEntered==true){
     $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter buying prices for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
     $j("#errorDialog").dialog("open");
     }
     else if(sellingPriceNotEntered==true){
     $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter selling price for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
     $j("#errorDialog").dialog("open");
     }
    else if ($j("#location option:selected").val()==-1) {
        $j('<dl><dt></dt><dd >' + "Info: " + "please select site you want to tranfer to" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else{
        $j.ajax({
            type:"POST",
            dataType:"json",
            url:"stockTransferRequest.form",
            data:{jsonObject:JSON.stringify(jsonS11Data)},
            success:function(result){
                document.getElementById("stockTransferForm").reset();
                removeRows();
            }
        })
    }
    return false;
});
$j("input[name=outgoingDrug]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');         },
        source:function (request, response) {
            // dataString = "searchDrug=" + request.term;
            $j.getJSON("getDrugForAutocomplete.form?searchDrug=" +request.term, function (result) {
                response($j.each(result, function (index, item) {
                    return {
                        label:item,
                        value:item
                    }
                }));

            });

        },
        minLength:3,
        select:function (event, ui) {
            var itemID=$j(this).attr('id');
            var idExtract=itemID.substring(itemID.indexOf("_")+1);
            var inventoryPropeties={};
            var dateProperties={};
            var drugNameToLoad= ui.item.label;
            var json={drugName:drugNameToLoad};
            $j.ajax({
                type:"GET",
                url:"getBuyingAndSellingPriceOfSelectedDrug.form",
                async:false,
                data:{"drugName":JSON.stringify(json)},
                dataType:"json",
                success:function(data){
                    inventoryPropeties= data.toString().split(",");
                    var buyingPrice=inventoryPropeties[0];
                    var sellingPrice=inventoryPropeties[1];
                    var batchNO=inventoryPropeties[2];
                    var expiryDate=inventoryPropeties[3];
                    var quantityInStock=inventoryPropeties[4];
                    var dateProperties=expiryDate.split("-");
                    var separateDay=dateProperties[2].split(" ");
                    var day=separateDay[0];
                    var month=dateProperties[1];
                    var year=dateProperties[0];
                    var convertedDate=month+"/"+separateDay[0]+"/"+year;
                    if(idExtract =="outgoingDrug") {
                        document.getElementById("buyingPrice").value=buyingPrice;
                        document.getElementById("unitPrice").value=sellingPrice;
                        document.getElementById("quantityInStock").value=quantityInStock;
                        document.getElementById("expiryDate").value=convertedDate;
                        document.getElementById("batchNo").value=batchNO;
                    }
                    else{
                        document.getElementById("buyingPrice_"+idExtract).value=buyingPrice;
                        document.getElementById("unitPrice_"+idExtract).value=sellingPrice;
                        document.getElementById("quantityInStock_"+idExtract).value=quantityInStock;
                        document.getElementById("expiryDate_"+idExtract).value=convertedDate;
                        document.getElementById("batchNo_"+idExtract).value=batchNO;
                    }
                }
            })
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });


});
$j('#stockTransferTable').delegate(' tr td a', 'click', function(e){
    e.preventDefault();
    try {
        var table = document.getElementById("stockTransferTable");
        var rowCount = table.rows.length;
        if(rowCount <= 2) {
            alert("Cannot delete all the rows.");
            //break;
        }
        else{
            $j(this).closest('tr').remove();
        }

    }catch(e) {
        alert(e);
    }

});
</script>
</head>
<body>
<%
PharmacyService service = Context.getService(PharmacyService.class);
service=Context.getService(PharmacyService.class);
String location=(String)session.getAttribute("location");
PharmacyLocations loggedInLocation=service.getPharmacyLocationsByName(location);
List<PharmacyLocations> pharmacyLocations=service.getPharmacyLocations();
%>
<DIV id="stockTransferFormDIV">
<fieldset>
<h2 class="centered">Stock Transfer Form</h2>
        <form id="stockTransferForm">
                <table class="tdbordered">
                    <tr>
                        <td><label>Tranfer To</label></td>
                         <td><select id="location" name="location"><option value="0">Select Location</option>
                         <% for(PharmacyLocations pharmacyLocationInstance:pharmacyLocations){
                         if(loggedInLocation.getUuid()==pharmacyLocationInstance.getUuid()){
                         }
                         else{%>
                          <option value=<%=pharmacyLocationInstance.getUuid()%> ><%=pharmacyLocationInstance.getName()%></option>
                          <%}
                          }%>
                    </tr>
                </table>
                <table id="stockTransferTable" class="tdbordered">
                    <tr>
                        <th>Drug</th>
                        <th>Quantity In Stock</th>
                        <th>Expiry date</th>
                        <th>Batch NO</th>
                        <th>BP</th>
                        <th>SP</th>
                        <th>Quantity To transfer</th>
                        <th></th>
                    </tr>
                    <tr><td style="width:50%;"><input type="text" name="outgoingDrug" id="outgoingDrug" style="width:100%;"/> </td>
                        <td><input type="text" id="quantityInStock" name="quantityInStock" style="width:50px;" readonly=''> </td>
                        <td><input type="text"  name="expiryDate" id="expiryDate" style="width:100px;" readonly='' /></td>
                        <td><input type="text" name="batchNo" id="batchNo" style="width:100px;" readonly=''/> </td>
                        <td><input type="text" name="buyingPrice" id="buyingPrice" style="width:50px;" value="" readonly=''/> </td>
                        <td><input type="text" name="unitPrice" id="unitPrice" style="width:50px;"  value="" readonly=''/> </td>
                        <td><input type="text" name="outgoingQuantity" id="outgoingQuantity" style="width:50px;"/> </td>
                        <td><a href="#">Remove</a></td>
                    </tr>
                </table></br>
                <input type="button"  value="Add A New Row" onclick="addRow('stockTransferTable')"/>
                <input class="submit" type="submit" value="Transfer Stock"/>
        </form>
</fieldset>
</DIV>
</body>
</html>