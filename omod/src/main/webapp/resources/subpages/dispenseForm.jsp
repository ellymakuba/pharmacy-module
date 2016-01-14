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
              #dispenseFormDIV{
                margin:10px;
                font-weight:bold;
                border:5px #AAAAAA;
              }
              #dispenseFormDIV table{
                background-color:#EBEBFF;
                width:100%;
              }
              h2.centered{
               text-align:center;
              }
       </style>

</head>
<script type="text/javascript">
var binTable;
var dialog;
var filterString;
var editTr;
var dataString;
var link;
var unClearedReceiptsTable;
var patienteEncounters;
var myResult;
var oCache = {
    iCacheLower:-1
};
function fnDataTablesPipeline2(sSource, aoData, fnCallback) {
    var iPipe = 5;
    var bNeedServer = false;
    var sEcho = fnGetKey(aoData, "sEcho");
    var iRequestStart = fnGetKey(aoData, "iDisplayStart");
    var iRequestLength = fnGetKey(aoData, "iDisplayLength");
    var iRequestEnd = iRequestStart + iRequestLength;
    oCache.iDisplayStart = iRequestStart;
    bNeedServer = true;
    if (oCache.lastRequest && !bNeedServer) {
        for (var i = 0, iLen = aoData.length; i < iLen; i++) {
            if (aoData[i].name != "iDisplayStart" && aoData[i].name != "iDisplayLength" && aoData[i].name != "sEcho") {
                if (aoData[i].value != oCache.lastRequest[i].value) {
                    bNeedServer = true;
                    break;
                }
            }
        }
    }
    oCache.lastRequest = aoData.slice();
    if (bNeedServer) {
        if (iRequestStart < oCache.iCacheLower) {
            iRequestStart = iRequestStart - (iRequestLength * (iPipe - 1));
            if (iRequestStart < 0) {
                iRequestStart = 0;
            }
        }
        oCache.iCacheLower = iRequestStart;
        oCache.iCacheUpper = iRequestStart + (iRequestLength * iPipe);
        oCache.iDisplayLength = fnGetKey(aoData, "iDisplayLength");
        fnSetKey(aoData, "iDisplayStart", iRequestStart);
        fnSetKey(aoData, "iDisplayLength", iRequestLength * iPipe);
        $j.getJSON(sSource, aoData, function (json) {
            /* Callback processing */
            oCache.lastJson = jQuery.extend(true, {}, json);
            if (oCache.iCacheLower != oCache.iDisplayStart) {
                json.aaData.splice(0, oCache.iDisplayStart - oCache.iCacheLower);
            }
            json.aaData.splice(oCache.iDisplayLength, json.aaData.length);
            fnCallback(json)
        });
    }
    else {
        json = jQuery.extend(true, {}, oCache.lastJson);
        json.sEcho = sEcho;
        /* Update the echo for each response */
        json.aaData.splice(0, iRequestStart - oCache.iCacheLower);
        json.aaData.splice(iRequestLength, json.aaData.length);
        fnCallback(json);
        return;
    }
}
function countR(){
    var tableLength=document.getElementById("tableDispense").rows.length;
    alert(tableLength);
}

toggleQueueButtons();
var rowCounter = document.getElementById("tableDispense").rows.length+1;
$j("#unClearedReceiptsDIV").show();
$j("#allPatientEncountersDIV").hide();
function addRow() {
    var table = document.getElementById("tableDispense");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var colCount = table.rows[0].cells.length;
    for(var i=0; i<colCount; i++) {
        var newcell = row.insertCell(i);
        newcell.innerHTML = table.rows[1].cells[i].innerHTML;
        switch(newcell.childNodes[0].type) {
            case "text":
                newcell.childNodes[0].value = "";
                if(newcell.childNodes[0].name=="dispenseFormDrug"){
                    newcell.childNodes[0].id="dispenseFormDrug_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="quantity"){
                    newcell.childNodes[0].id="quantity_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="unitPrice"){
                    newcell.childNodes[0].id="unitPrice_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="amount"){
                    newcell.childNodes[0].id="amount_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="quantityInStock"){
                    newcell.childNodes[0].id="quantityInStock_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="itemAmountWaived"){
                    newcell.childNodes[0].id="itemAmountWaived_" + rowCounter;
                    newcell.childNodes[0].value=0;
                }
                if(newcell.childNodes[0].name=="discount"){
                    newcell.childNodes[0].id="discount_" + rowCounter;
                    newcell.childNodes[0].value=0;
                }
                if(newcell.childNodes[0].name=="dosage"){
                    newcell.childNodes[0].id="dosage_" + rowCounter;
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
$j("#tableDispense").hide();
function deleteRow() {
    try {
        var table = document.getElementById("tableDispense");
        var rowCount = table.rows.length;
        if(rowCount <= 2) {
            alert("Cannot delete all the rows.");
            //break;
        }
        else{
            table.deleteRow($j(this));
        }

    }catch(e) {
        alert(e);
    }
}
function removeRows() {
    $j('#tableDispense tr:not(.newRowClass)').remove();
}
$j('#tableDispense').hide();
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
var doseArrayObject=[];
    $j("#dosage").get(0).options.length = 0;
    $j("#dosage").get(0).options[0] = new Option("Select","-1");
    $j.each(result.aaData,function (index, value) {
    doseArrayObject=value.toString().split(",");
        $j("#dosage").get(0).options[$j("#dosage").get(0).options.length] = new Option(doseArrayObject[0],doseArrayObject[1]);
    });
});
$j("#dispensingForm").validate();
unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    "bAutoWidth": false,
    sAjaxSource:"unProcessedReceipts.form?drop=displayUnclearedReceipts" ,
    "fnServerData":fnDataTablesPipeline2,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 0 ]
        }
    ]
});

$j('#unClearedReceipts tbody').delegate("tr","click", function () {
    editTr = this;
    aData = unClearedReceiptsTable.fnGetData(editTr);
    $j("#queueFormDIV").show();
    $j("#tableDispense TBODY tr").remove();
    document.getElementById("patientId").value=aData[1];
    document.getElementById("patientName").value=aData[3];
    var vals=[];
    var a={};
    var totalAmount=0;
    var totalAmountWaived=0;
    var totalDiscount=0;
    var doseArrayObject=[];
    document.getElementById("amountPaid").value='';
    $j.ajax({
        type:'GET',
        url: 'unProcessedReceipts.form?encounterUUID='+aData[0]+'&paymentStatus='+aData[5],
        dataType: 'json',
        async: false,
        data: {myname:"fetch"},
        success: function(data) {
            $j.each(data.aaData, function(idx, elem){
                vals= elem.toString().split(",");
                $j('table#tableDispense TBODY').append('<tr><td><input name="dispenseFormDrug" id="dispenseFormDrug_'+idx+'"  style="width:350px;" value="'
                    +vals[0]+'" /></td><td><input name="quantityInStock" style="width:50px;" id="quantityInStock_'+idx+'" readonly  value="' +vals[5]+'"/>' +'</td>' +
                    '<td><select id="dosage_'+idx+'"  name="dosage" ></select> </td>'+
                    '<td><input type="text" name="unitPrice" id="unitPrice_'+idx+'" style="width:50px" value="'+vals[4]+'"/></td>'+
                    '<td><input type="text"  name="quantity" style="width:80px;" id="quantity_'+idx+'" value="'+vals[1]+'" /></td>'+
                    '<td><input type="text" name="amount" id="amount_'+idx+'" style="width:80px;"  value="'+vals[2]+'" /></td>' +
                    '<td><input type="text" name="discount" style="width:50px;" value="'+vals[8]+'" id="discount_'+idx+'"/></td> '+
                    '<td><input type="text" name="itemAmountWaived" style="width:50px;" value="'+vals[7]+'" id="itemAmountWaived_'+idx+'"/></td> '+
                    '<td><a href="#">Rem</a></td>'+
                    '<td><input type="hidden" name="drugExtraUUID" value="'+vals[3]+'"</td>'+
                    '<td><input type="hidden" name="previouslySoldQuantity" value="'+vals[6]+'"</td></tr>');
                totalAmount=Number(totalAmount)+Number(vals[2]);
                totalAmountWaived=Number(totalAmountWaived)+Number(vals[7]);
                totalDiscount=Number(totalDiscount)+Number(vals[8]);
                $j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
                    $j("#dosage_"+idx).get(0).options.length = 0;
                    var convertDoseIdToInt=parseInt(vals[10]);
                   if(convertDoseIdToInt > -1){
                    $j("#dosage_"+idx).get(0).options[0] = new Option(vals[9],vals[10]);
                    }
                   $j.each(result.aaData,function (index, value) {
                          doseArrayObject=value.toString().split(",");
                          $j("#dosage_"+idx).get(0).options[$j("#dosage_"+idx).get(0).options.length] = new Option(doseArrayObject[0],doseArrayObject[1]);
                          });
                });
            })
        $j('table#tableDispense TBODY').append('<input type="hidden" name="previousEncounter" id="previousEncounter" value="'+aData[0]+'">');
        var totalToLess=parseFloat(totalAmountWaived)+parseFloat(totalDiscount);
        document.getElementById("totalAmount").value=parseFloat(totalAmount)-parseFloat(totalToLess);
        document.getElementById("amountWaived").value=totalAmountWaived;
        document.getElementById("totalDiscount").value=totalDiscount;
        var amount=0;
        if(aData[5]=="Paid"){
            $j.ajax({
                type:"GET",
                url:"amountAlreadyPaidForInvoice.form?encounterUUID="+aData[0],
                data:amount,
                success:function (result) {
                    document.getElementById("amountPaid").value=result;
                }
            });
        }
    }
    });
    toggleQueueButtons();
    $j("#tableDispense").show();
})
$j('#unClearedReceipts').delegate(' tbody td  input', 'click', function () {
    editTr=this.parentNode.parentNode;
    aData = unClearedReceiptsTable.fnGetData(editTr);
    var printingURL="printInvoice.form?encounterUUID="+aData[0];
    window.location=printingURL;
})
$j('#tableDispense').delegate(' tr td a', 'click', function(e){
    e.preventDefault();
    try {
        var table = document.getElementById("tableDispense");
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
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
$j("#amountPaid").live("blur",function(){
    document.getElementById("balance").value= parseFloat($j("#amountPaid").val())-parseFloat($j("#totalAmount").val());
})
$j("INPUT[NAME='itemAmountWaived']").live("blur",function(){
    var amountRequiredToPay = 0;
    var totalReceiptSum=0;
    var discountSum=0;
    var waivedSum=0;
    var totalToLess=0;
    $j("input[name='amount']").each(function() {
        totalReceiptSum += Number($j(this).val());
    });
    $j("input[name='discount']").each(function() {
        discountSum += Number($j(this).val());
    });
    $j("input[name='itemAmountWaived']").each(function() {
        waivedSum += Number($j(this).val());
    });
    totalToLess=parseFloat(discountSum)+parseFloat(waivedSum);
    amountRequiredToPay=parseFloat(totalReceiptSum)-parseFloat(totalToLess);
    document.getElementById("totalAmount").value=parseFloat(amountRequiredToPay);
    document.getElementById("amountWaived").value=Number(waivedSum);
    document.getElementById("balance").value= parseFloat($j("#amountPaid").val())-parseFloat($j("#totalAmount").val());
});
$j("INPUT[NAME='discount']").live("blur",function(){
    var amountRequiredToPay;
    var totalReceiptSum=0;
    var discountSum=0;
    var waivedSum=0;
    var totalToLess;
    $j("input[name='amount']").each(function() {
        totalReceiptSum += Number($j(this).val());
    });
    $j("input[name='discount']").each(function() {
        discountSum += Number($j(this).val());
    });
    $j("input[name='itemAmountWaived']").each(function() {
        waivedSum += Number($j(this).val());
    });
    totalToLess=parseFloat(discountSum)+parseFloat(waivedSum);
    amountRequiredToPay=parseFloat(totalReceiptSum)-parseFloat(totalToLess);
    document.getElementById("totalAmount").value=parseFloat(amountRequiredToPay);
    document.getElementById("totalDiscount").value=Number(discountSum);
    document.getElementById("balance").value= parseFloat($j("#amountPaid").val())-parseFloat($j("#totalAmount").val());
});
function AutoReload() {
    unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
        bJQueryUI:true,
        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        sAjaxSource:"unProcessedReceipts.form?drop=displayUnclearedReceipts" ,
        "fnServerData":fnDataTablesPipeline2,

    })}
function fnFormatDetails(nTr) {
    document.forms['dispensingForm'].elements["patientId"].value = aData[1];
    document.forms['dispensingForm'].elements["totalAmount"].value = aData[2];
}
$j("#patientIdentifier").live("focus", function () {
    $j(this).autocomplete({
        source:function (request, response) {
            $j.getJSON("patientSearch.form?patientSearch=" + request.term, function (result) {
                $j("#dispenseFormDrug").removeClass('working');
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
            unClearedReceiptsTable.fnDestroy();
            unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
                bJQueryUI:true,
                bRetrieve:true,
                bServerSide:true,
                bProcessing:true,
                "bAutoWidth": false,
                sAjaxSource:"patientUnclearedRecords.form?patientIdentifier="+ui.item.value,
                "fnServerData":fnDataTablesPipeline2,
                "aoColumnDefs":[
                    {
                        "bVisible":false,
                        "aTargets":[ 0 ]
                    }
                ]
            })
        }
    });
});
/*
$j("#patientIdentifier").live("blur", function () {
    unClearedReceiptsTable.fnDestroy();
    unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
        bJQueryUI:true,
        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        "bAutoWidth": false,
        sAjaxSource:"patientUnclearedRecords.form?patientIdentifier="+$j(this).val(),
        "fnServerData":fnDataTablesPipeline2,
        "aoColumnDefs":[
            {
                "bVisible":false,
                "aTargets":[ 0 ]
            }
        ]
    })
});
*/
$j("input[name=dispenseFormDrug]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },
        source:function (request, response) {
            dataString = "searchDrug=" + request.term;
            $j.getJSON("drugBincard.form?drop=dispenseDrug&" + dataString, function (result) {
                $j("#dispenseFormDrug").removeClass('working');
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
            var val =ui.item.value;
            var itemID=$j(this).attr('id');
            var idExtract=itemID.substring(itemID.indexOf("_")+1);
            var json = {drugName:val};
            $j.ajax({
                type:"GET",
                url:"stockInventory.form?select=stock",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (result) {
                    if(idExtract =="dispenseFormDrug") {
                        document.getElementById("quantityInStock").value=result;
                    }
                    else{
                        document.getElementById("quantityInStock_"+idExtract).value=result;
                    }
                }
            })
            $j.ajax({
                type:"GET",
                url:"stockInventory.form?select=unitPrice",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (result) {
                    if(idExtract =="dispenseFormDrug") {
                        document.getElementById("unitPrice").value=result;
                    }
                    else{
                        document.getElementById("unitPrice_"+idExtract).value=result;
                    }
                }
            })
            $j.ajax({
                type:"GET",
                url:"stockInventory.form?checkBoolean=checkBoolean",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (result) {
                    if (result.toString() == 'false'){
                        $j("#errorDialog").empty();
                        $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                        $j("#errorDialog").dialog("open");
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
$j("input[name=patientId]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },
        source:function (request, response) {
            $j.getJSON("patientSearch.form?patientSearch=" + request.term, function (result) {
                $j("#dispenseFormDrug").removeClass('working');
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
            $j("#tableDispense").show();
            $j("#unClearedReceiptsDIV").hide();
            $j("#allPatientEncountersDIV").show();
            var patient=ui.item.value;
            patienteEncounters = $j('#allPatientEncounters').dataTable({
                bJQueryUI:true,
                bRetrieve:true,
                bServerSide:true,
                bProcessing:true,
                sAjaxSource:"patientEncountersByIdentifier.form?identifier=" + $j('#patientId').val(),
                "fnServerData":fnDataTablesPipeline2,
                "aoColumnDefs":[
                    {
                        "bVisible":false,
                        "aTargets":[ 0 ]
                    }
                ]
            });
            $j.ajax({
                type:"GET",
                url:"patientLastName.form?patientToFind="+patient,
                data:patient,
                dataType:"json",
                success:function (result) {
                    document.getElementById("patientName").value=result;
                    patienteEncounters.fnDraw();
                }
            });

        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });
});

$j("input[name='quantity']").live("blur", function () {
    var sumpaid = 0;
    var id=$j(this).attr('id');
    var idExtract=id.substring(id.indexOf("_")+1);
    if(idExtract=="quantity"){
        document.getElementById("amount").value = parseInt($j("input[id='quantity']").val())
            * parseFloat($j("input[id='unitPrice']").val());
    }  else{
        document.getElementById("amount_"+idExtract).value = parseInt(document.getElementById("quantity_"+idExtract).value)
            * parseFloat(document.getElementById("unitPrice_"+idExtract).value);
    }
    $j("input[name='amount']").each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("totalAmount").value=Number(sumpaid);
});

$j("input[name='amount']").live("blur",function () {
    var amountRequiredToPay = 0;
    var totalReceiptSum=0;
    var discountSum=0;
    var waivedSum=0;
    var totalToLess=0;
    $j("input[name='amount']").each(function() {
        totalReceiptSum += Number($j(this).val());
    });
    $j("input[name='discount']").each(function() {
        discountSum += Number($j(this).val());
    });
    $j("input[name='itemAmountWaived']").each(function() {
        waivedSum += Number($j(this).val());
    });
    totalToLess=parseFloat(discountSum)+parseFloat(waivedSum);
    amountRequiredToPay=parseFloat(totalReceiptSum)-parseFloat(totalToLess);
    document.getElementById("totalAmount").value=parseFloat(amountRequiredToPay);
});
function addNewInvoiceOnQueue(){
    var json = [];
    $j('#queueFormDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var  td = $j(this).find('input,select');

            var key = td.attr('name');
            var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        json.push(rowObject);
    });
    $j.ajax({
        type:"POST",
        url:"rfpDispenseFormCheckInventoryAvailability.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {
            if(result.toString()=='true') {
                $j.ajax({
                    type:"POST",
                    url:"rfpDispenseFormAddNewInvoice.form?AddNewInvoiceOnQueue="+$j("#previousEncounter").val(),
                    data:{values:JSON.stringify(json) },
                    success:function (result) {
                        printInvoice(result.toString());

                        aData="";
                        document.getElementById("dispensingForm").reset();
                        removeRows();
                        unClearedReceiptsTable.fnDraw();
                    }
                });
            }
            else {
                $j("#errorDialog").empty();
                $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                $j("#errorDialog").dialog("open");
            }
        }
    });
    toggleQueueButtons();
    return false;
}
function updateAndRequeue(){
    var json = [];
    $j('#queueFormDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var  td = $j(this).find('input,select');

            var key = td.attr('name');
            var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        json.push(rowObject);
    });
    $j.ajax({
        type:"POST",
        url:"rfpDispenseFormCheckInventoryAvailability.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {
            if(result.toString()=='true') {
                $j.ajax({
                    type:"POST",
                    url:"rfpDispenseFormUpdateAndRequeue.form?encounterToUpdate="+aData[0],
                    data:{values:JSON.stringify(json) },
                    success:function (result) {
                        printInvoice(result.toString());

                        aData="";
                        document.getElementById("dispensingForm").reset();
                        removeRows();
                        unClearedReceiptsTable.fnDraw();
                    }
                });
            }
            else {
                $j("#errorDialog").empty();
                $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                $j("#errorDialog").dialog("open");
            }
        }
    });
    toggleQueueButtons();
    return false;

}
function processInvoicePayment(){
    if(parseFloat($j("#amountPaid").val()).toString() =="NaN" || parseFloat($j("#balance").val()).toString() =="NaN" || parseFloat($j("#balance").val())< 0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Amount paid cannot clear receipt amount" + '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else{
        var jsonReceiptData = [];
        $j('#queueFormDIV').find('tr').each(function(){
            var rowObject=[];
            $j(this).find('td').each(function(){
                var obj = {}
                var  td = $j(this).find('input,select');

                var key = td.attr('name');
                var val = td.val();
                obj[key] = val;
                rowObject.push(obj);
            });
            jsonReceiptData.push(rowObject);
        });
        $j.ajax({
            type:"POST",
            url:"rfpDispenseFormProcessPayment.form?encounterToProcessPayment="+aData[0],
            async:false,
            data:{values:JSON.stringify(jsonReceiptData) },
            success:function (result) {
               /*  var printingURL="printInvoice.form?encounterUUID="+result.toString();
                window.location=printingURL;
                document.getElementById("dispensingForm").reset();
                removeRows();
                unClearedReceiptsTable.fnDraw();  */

            }
        });
    }toggleQueueButtons();
    return false;
}
function confirmInvoiceAndIssueDrugs(){
    processInvoicePayment();
    /*if((parseFloat($j("#amountPaid").val()) < parseFloat($j("#totalAmount").val())) || parseFloat($j("#amountPaid").val()).toString() =="NaN"){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Cannot close Invoice before payment is done" + '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else{ */
        var jsonReceiptData = [];
        $j('#queueFormDIV').find('tr').each(function(){
            var rowObject=[];
            $j(this).find('td').each(function(){
                var obj = {}
                var  td = $j(this).find('input,select');

                var key = td.attr('name');
                var val = td.val();
                obj[key] = val;
                rowObject.push(obj);
            });
            jsonReceiptData.push(rowObject);
        });
        $j.ajax({
            type:"POST",
            url:"checkIfPaymentHasBeenMade.form?encounterToCheck="+aData[0],
            async:false,
            data:{values:JSON.stringify(jsonReceiptData) },
            success:function (result) {
                if(result==1){
                    $j.ajax({
                        type:"POST",
                        url:"closePatientEncounter.form?encounterToClose="+aData[0]+"&patientID="+$j('#patientId').val(),
                        data:{values:JSON.stringify(jsonReceiptData) },
                        success:function () {
                            document.getElementById("dispensingForm").reset();
                            removeRows();
                            unClearedReceiptsTable.fnDraw();
                        }
                    });
                }
                else{
                    $j('<dl><dt></dt><dd >' + "Info: " + "Please process Payment before you close form" + '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }
            }

        })

    //}
    toggleQueueButtons();
    return false;
}
// $j(".myBox").click(function(){
//window.location=$j(this).find("a").attr("href");
// window.location="printInvoice.form";
//return false;
//});
$j("table").delegate("#allPatientEncounters tbody tr :last-child","click",function(){
    var TableRow = this.parentNode;
    aData =patienteEncounters.fnGetData(TableRow);
    var amount;
    $j.ajax({
        type:"GET",
        url:"reprocessEncounter.form?encounterUUID="+aData[0],
        async:false,
        data:amount,
        success:function () {
            unClearedReceiptsTable.fnDraw();
            $j("#unClearedReceiptsDIV").show();
        }
    });

})
$j("table").delegate("#allPatientEncounters tbody tr :first-child","click",function(){
    var TableRow = this.parentNode;
    aData =patienteEncounters.fnGetData(TableRow);
    var amount;
    $j.ajax({
        type:"GET",
        url:"temporaryCloseToPreviuosEncounters.form?encounterUUID="+aData[0],
        async:false,
        data:amount,
        success:function () {
            unClearedReceiptsTable.fnDraw();
            $j("#unClearedReceiptsDIV").show();
        }
    });

})
/*
setInterval(function(){
    unClearedReceiptsTable.fnDraw();
}, 10000); */
function toggleQueueButtons(){
    if($j("#previousEncounter").val() =="" || typeof ($j("#previousEncounter").val())=="undefined"){
        $j("#queue").show();
        $j("#requeue").hide();
    }
    else{
        $j("#queue").hide();
        $j("#requeue").show();
    }
}
function printInvoice(encounter)
{
    var totalAmount=0;
    var totalAmountWaived=0;
    var totalDiscount=0;
    var totalAmountToLess=0;
   $j("#printSection").empty();
    $j('#printSection').append('<table id="receiptTable" class="visibleBorder"><thead>' +
        '<tr class="newRowClass"><td>patient ID</td><td></td><td></td><td></td><td>'+$j("#patientId").val()+'</td></tr>'+
        '<tr><td>patient Name</td><td></td><td></td><td></td><td>'+$j("#patientName").val()+'</td></tr>'+
        '<tr><th>Drug</th><th></th><th>UtP</th><th>Qty</th><th>Amt</th>' +
        '</tr></thead><tbody></tbody></table>');
    $j.ajax({
        type:'GET',
        url: 'unProcessedReceipts.form?encounterUUID='+encounter+'&paymentStatus=0',
        dataType: 'json',
        async: false,
        data: {myname:"fetch"},
        success: function(data) {
            $j.each(data.aaData, function(idx, elem){
                vals= elem.toString().split(",");
                $j('table#receiptTable TBODY').append('<tr>' +
                    '<td>'+vals[0]+'</td>' +
                    '<td></td>' +
                    '<td>'+vals[4]+'</td>'+
                    '<td>'+vals[1]+'</td>'+
                    '<td>'+vals[2]+'</td>' +
                    '</tr>');
                totalAmount=Number(totalAmount)+Number(vals[2]);
                totalAmountWaived=Number(totalAmountWaived)+Number(vals[7]);
                totalDiscount=Number(totalDiscount)+Number(vals[8]);
            })
            totalAmountToLess=parseFloat(totalAmountWaived)+parseFloat(totalDiscount);
            totalAmount=parseFloat(totalAmount)-parseFloat(totalAmountToLess);
            $j('table#receiptTable TBODY').append('<tr><td>Discount</td><td></td><td></td><td></td><td>'+totalDiscount+'</td></tr>'+
            '</br><tr><td>Amnt Waived</td><td></td><td></td><td></td><td>'+totalAmountWaived+'</td></tr>'+
            '<tr><td>Cash Expected</td><td></td><td></td><td></td><td>'+totalAmount+'</td></tr></table>')
        }
    });
    var prtContent = document.getElementById('printSection');
    var WinPrint = window.open('', '','width=600,height=650');
    $j("#errorDialog").empty();
    if(!WinPrint || WinPrint.closed || typeof WinPrint.closed=='undefined')
    {
        $j('<dl><dt></dt><dd >' + "please unblock pop ups in your browser to be able to print invoice" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else
    {
    var str =  prtContent.innerHTML;
    WinPrint.document.write(str);
    WinPrint.document.close();
    WinPrint.focus();
    WinPrint.print();
    WinPrint.close();
    }
}
</script>
<body>
<DIV id="dispenseFormDIV">
    <div id="queueFormDIV">
        <h3>RFP Drug Dispensing Form</h3>
        <form id="dispensingForm" name="dispensingForm">
            <fieldset>
                <table class="tdbordered">
                    <tr>
                        <td>Search Patient Identifier</td>
                        <td><input type="text" name="patientId" id="patientId" class="required"/></td>
                        <td><input type="text" name="patientName" id="patientName" style="width:350px;"  readonly/></td>
                    </tr>
                </table>
                <table id="tableDispense" class="tdbordered">
                    <thead>
                    <tr class="newRowClass" ><th>Drug </th><th style="width:100px;">Quantity In stock </th><th>Dose </th><th>UnitPrice</th>
                        <th style="width:100px;">Quantity to Dispense</th><th>Amount</th></tr>
                    </thead>
                    <tbody>
                    <tr class="newRowClass" >
                        <td ><input type="text" name="dispenseFormDrug" id="dispenseFormDrug" style="width: 350px; "/></td>
                        <td><input type="text" name="quantityInStock" id="quantityInStock" style="width:100px;" readonly  /></td>
                        <td>
                            <select id="dosage"  name="dosage" ></select>
                        </td>
                        <td><input type="text" name="unitPrice" id="unitPrice" style="width:100px;"  readonly/></td>
                        <td><input type="text"  name="quantity" id="quantity" style="width:100px;" /></td>
                        <td><input type="text" name="amount" id="amount"  style="width:100px;"/></td>
                    </tr>
                    </tbody>
                </table>
                <table><tr><td>Total Amount</td><td><input type="text" name="totalAmount" id="totalAmount" readonly style="width: 100px;"></td>
                    <td>Amount Paid</td><td><input type="text" name="amountPaid" id="amountPaid" style="width: 100px;"></td>
                    <td>Amount Waived</td><td><input type="text" name="amountWaived" id="amountWaived" value="0" style="width: 100px;"></td>
                    <td>Balance</td> <td><input type="text" name="balance" id="balance" style="width: 100px;"></td>
                </tr></table>
                <input type="button" value="Add Row" onclick="addRow('tableDispense')"/>
                <input type="button" value="Remove Row" onclick="deleteRow('tableDispense')"/> </br>
                <input type="button" value="Queue for payment" onclick="addNewInvoiceOnQueue()"/>
                <%
                    if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin") || org.openmrs.api.context.Context.hasPrivilege("System Developer")) {
                %>
                <input  type="button" value="Process Payment" onClick="processInvoicePayment()"/>
                <%
                    }
                %>
                <input  type="button" value="Close Encounter" onclick="confirmInvoiceAndIssueDrugs()"/>
            </fieldset>
        </form>
    </div>

    <table width="100%" id="unClearedReceipts" >
        <thead>
        <th>EncounterUUID</th>
        <th>Patient ID</th>
        <th>Total</th>
        <th>Patient Name</th>
        <Th>Time</Th>
        <Th>Status</Th>
        <Th>Print</Th>
        </thead>
        <tbody>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        </tbody>
    </table>
    <table cellpadding="0" cellspacing="0" border="0" class="display"  id="allPatientEncounters">
        <thead>
        <tr>
            <th width="4%">UUID</th>
            <th width="4%">Encounter Date</th>
            <th>Comment</th>
            <th>Issued Medication</th>
            <th>Location</th>
            <th>User</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</DIV>
</body>
</html>