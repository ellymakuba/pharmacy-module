var binTable;
var dialog;
var filterString;
var editTr;
var dataString;
var link;
var unClearedReceiptsTable;
var myResult;
var oCache = {
    iCacheLower:-1
};
var rowCounter = 0 ;
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
                if(newcell.childNodes[0].name=="drugdispense"){
                    newcell.childNodes[0].id="drugdispense_" + rowCounter;
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
        if(rowCount <= 2) {
            alert("Cannot delete all the rows.");
            //break;
        }
        else{
            table.deleteRow(rowCount -1);
        }

    }catch(e) {
        alert(e);
    }
}
function removeRows(tableID) {
    $j('#tableDispense tr:not(.newRowClass)').remove();
}
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j("#dosage").get(0).options.length = 0;
    $j("#dosage").get(0).options[0] = new Option("Select","-1");
    $j.each(result,function (index, value) {
        $j("#dosage").get(0).options[$j("#dosage").get(0).options.length] = new Option(value,value);
    });
});
$j("#dispensingForm").validate();
$j("#receiptDIV").hide();
unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:"unProcessedReceipts.form?drop=displayUnclearedReceipts" ,
    "fnServerData":fnDataTablesPipeline2
});
$j('#unClearedReceipts tbody').delegate("tr","click", function () {
    editTr = this;
    aData = unClearedReceiptsTable.fnGetData(editTr);
    fnFormatDetails(editTr);
    $j("#queueFormDIV").hide();
    $j("#receiptToComplete TBODY tr").remove();
    $j("#receiptDIV").show();
    var vals=[];
    var a={};
    $j.getJSON('unProcessedReceipts.form?encounterUUID='+aData[0], function (data) {
        $j.each(data.aaData, function(idx, elem){
            vals= elem.toString().split(",");
            $j('table#receiptToComplete TBODY').append('<tr><td><input name="drugReceipt"  style="width:350px;"  readonly value="'+vals[0]+'" /></td><td><input name="quantityToSubtract" style="width:100px;" readonly value="'+vals[1]+'"/></td><td style="width:100px;">'+vals[2]+'</td></tr>');
        });
    });
})
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function AutoReload() {
    unClearedReceiptsTable=$j("#unClearedReceipts").dataTable({
        bJQueryUI:true,
        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        sAjaxSource:"unProcessedReceipts.form?drop=displayUnclearedReceipts" ,
        "fnServerData":fnDataTablesPipeline2
})}
function fnFormatDetails(nTr) {
    var oFormObject = document.forms['completeReceipt'];
    oFormObject.elements["patient"].value = aData[1];
}

$j("input[name=drugdispense]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },
        source:function (request, response) {
            dataString = "searchDrug=" + request.term;
            $j.getJSON("drugBincard.form?drop=dispenseDrug&" + dataString, function (result) {
                $j("#drugdispense").removeClass('working');
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
                    if(idExtract =="drugdispense") {
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
                    if(idExtract =="drugdispense") {
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
            dataString = "searchPatient=" + request.term;
            $j.getJSON("drugDetails.form?drop=patientSearch&" + dataString, function (result) {
                $j("#drugdispense").removeClass('working');
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
            var patient=ui.item.value;
            $j.ajax({
                type:"GET",
                url:"drugDetails.form?drop=patientLastName&patientToFind="+patient,
                data:patient,
                dataType:"json",
                success:function (result) {
                        document.getElementById("patientName").value=result;

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

$j("input[name='quantity']").live("blur", function () {
        var id=$j(this).attr('id');
        var idExtract=id.substring(id.indexOf("_")+1);
    if(idExtract=="quantity"){
        document.getElementById("amount").value = parseInt($j("input[id='quantity']").val())
            * parseInt($j("input[id='unitPrice']").val());
    }  else{
        document.getElementById("amount_"+idExtract).value = parseInt(document.getElementById("quantity_"+idExtract).value)
            * parseInt(document.getElementById("unitPrice_"+idExtract).value);
    }
});

//$j("input[name='drugdispense']").live('blur',function(){

//}) ;
$j("input[name='amount']").live("blur",function () {
    var sumpaid = 0;
    $j("input[name='amount']").each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("totalAmount").value=Number(sumpaid);
 });
$j("form#dispensingForm").unbind('submit').submit(function(){
    var dataString = $j("#dispensingForm").serializeArray();
    var json = [];
    $j('#queueFormDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var  td = $j(this).find('input');

              var key = td.attr('name');
              var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        json.push(rowObject);
    });
    $j.ajax({
                type:"POST",
                url:"rfpDispenseFormProcessor.form?checkBoolean=checkBoolean",
                data:{values:JSON.stringify(json) },
                success:function (result) {
                    if(result.toString()=='true') {
                        $j.ajax({
                                type:"POST",
                                url:"rfpDispenseFormProcessor.form",
                                data:{values:JSON.stringify(json) },
                                success:function () {
                                    document.getElementById("dispensingForm").reset();
                                    removeRows("tableDispense");
                                    aData="";
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
    return false;
})
$j("form#completeReceipt").unbind('submit').submit(function(){
    var jsonReceiptData = [];
    $j('#receiptDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            var obj = {}
            var  td = $j(this).find('input');

            var key = td.attr('name');
            var val = td.val();
            obj[key] = val;
            rowObject.push(obj);
        });
        jsonReceiptData.push(rowObject);
    });
   //alert("json is"+JSON.stringify(jsonReceiptData));
    $j.ajax({
        type:"POST",
        url:"rfpDispenseFormProcessor.form?receipt="+aData[0],
        data:{values:JSON.stringify(jsonReceiptData) },
        success:function () {
            $j('#receiptDIV').hide();
            $j('#receiptDIV').innerHTML="";
            document.getElementById("completeReceipt").reset();
            $j('#queueFormDIV').show();
        }
    });
    return false;
})
