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
           #deliveryNoteFormDIV{
             margin:10px;
             font-weight:bold;
             border:5px #AAAAAA;
           }
           #deliveryNoteFormDIV table{
             background-color:#EBEBFF;
             width:100%;
           }
           h2.centered{
            text-align:center;
           }
    </style>
<script type="text/javascript">
$j('#date').datepicker({changeMonth:true,changeYear:true,yearRange: "2015:2025"});
$j(".myDate").live('focus', function(){
    var $jthis = $j(this);
    if(!$jthis.data('datepicker')) {
         $jthis.removeClass("hasDatepicker");
         $jthis.datepicker({changeMonth:true,changeYear:true,yearRange: "2015:2025"});
         $jthis.datepicker("show");
     }
 });
var rowCounter = document.getElementById("tableincoming").rows.length+1;
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
                if(newcell.childNodes[0].name=="delivery_note_drug"){
                    newcell.childNodes[0].id="delivery_note_drug_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="quantity"){
                    newcell.childNodes[0].id="quantity_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="date"){
                    newcell.childNodes[0].id="date_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="batchNo"){
                    newcell.childNodes[0].id="batchNo_" + rowCounter;
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

$j('#tableincoming').delegate(' tr td a', 'click', function(e){
    e.preventDefault();
    try {
        var table = document.getElementById("tableincoming");
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
$j("input[name=delivery_note_drug]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');         },
        source:function (request, response) {
           // dataString = "searchDrug=" + request.term;
            $j.getJSON("getDrugForAutocomplete.form?searchDrug=" +request.term, function (result) {
                $j("#delivery_note_drug").removeClass('working');
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
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });


});
$j("form#deliveryNote").submit(function () {
    $j("#errorDialog").empty();
    var quantityNotEntered=false;
    var jsonDeliveryNoteData = [];
    var dateNotEntered=false;
    var batchNoNotEntered=false;
    $j('#deliveryNote').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var td=$j(this).find('input,select');
            var key = td.attr('name');
            var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        jsonDeliveryNoteData.push(rowObject);
    });
    $j("input[name='quantity']").each(function(){
        if($j(this).val()==""){
            quantityNotEntered=true;
        }
    })
    $j("input[name='date']").each(function(){
        if($j(this).val()==""){
            dateNotEntered=true;
        }
    })
    $j("input[name='batchNo']").each(function(){
        if($j(this).val()==""){
            batchNoNotEntered=true;
        }
    })
    if ($j("#deliveryNoteNumber").val() =="") {
        $j('<dl><dt></dt><dd >' + "Info: " + "please enter the delivery note number" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if ($j("#supplierUUID option:selected").val()==0) {
        $j('<dl><dt></dt><dd >' + "Info: " + "please select supplier issueing stock" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if(quantityNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter quantities for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
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
    else{
    $j.ajax({
        type:"POST",
        dataType:"json",
        url:"addNewDeliveryNote.form",
        data:{jsonObject:JSON.stringify(jsonDeliveryNoteData)},
        success:function(result){
            document.getElementById("deliveryNote").reset();
            removeRows();
        }
    })
    }
   return false;
});
</script>
<body>
<%
PharmacyService service = Context.getService(PharmacyService.class);
service=Context.getService(PharmacyService.class);
String location=(String)session.getAttribute("location");
PharmacyLocations loggedInLocation=service.getPharmacyLocationsByName(location);
List<PharmacySupplier> pharmacySuppliers=service.getPharmacySupplier();
%>
<DIV id="deliveryNoteFormDIV">
<h2 class="centered">Receive New Delivery Note Form</h2>
<form id="deliveryNote">
<fieldset>
  <table class="tdbordered" >
     <tr>
         <td>Delivery Note No. </td>
         <td><input type="text" name="deliveryNoteNumber" id="deliveryNoteNumber" /> </td>
     </tr>
     <tr>
         <td><input type="hidden" name="dateCreated" id="dateCreated" /> </td>
     </tr>
     <tr>
         <td><label>Supplier</label></td>
         <td><select id="supplierUUID" name="supplierUUID"><option value="0">Select Supplier</option>
          <% for(PharmacySupplier pharmacySupplierInstance:pharmacySuppliers){%>
          <option value=<%=pharmacySupplierInstance.getUuid()%> ><%=pharmacySupplierInstance.getName()%></option>
          <%  }%>
          </select></td>
     </tr>
</table>
<table id="tableincoming" class="tdbordered" >
  <tr>
      <th>Drug</th>
      <th>Quantity</th>
      <th>Expiry Date</th>
      <th>Batch No</th>
  </tr>
  <tr>
      <td style="width:50%;"><input type="text" name="delivery_note_drug" id="delivery_note_drug" style="width:100%;"/> </td>
      <td><input type="text" name="quantity" id="quantity" style="width:100px;"/> </td>
      <td><input type="text"  name="date" id="date" style="width:100px;"  class="myDate" readonly="true"/></td>
      <td><input type="text" name="batchNo" id="batchNo" style="width:100px;" /> </td>
      <td><a href="#">Remove</a></td>
  </tr>
</table></br>
<input type="button"  value="Add A New Row" onclick="addRow('tableincoming')"/>
<input class="submit" type="submit" value="Save Delivery Note"/>
</fieldset>
</form>
</DIV>
</body>
