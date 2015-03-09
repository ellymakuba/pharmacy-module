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
    $j("#dosage").get(0).options.length = 0;
    $j("#dosage").get(0).options[0] = new Option("Select","-1");
    $j.each(result,function (index, value) {
        $j("#dosage").get(0).options[$j("#dosage").get(0).options.length] = new Option(value,value);
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
    document.getElementById("amountPaid").value='';
    $j.getJSON('unProcessedReceipts.form?encounterUUID='+aData[0]+'&paymentStatus='+aData[5], function (data) {
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
                '<td><a href="#">del</a></td>'+
                '<td><input type="hidden" name="drugExtraUUID" value="'+vals[3]+'"</td>'+
                '<td><input type="hidden" name="previouslySoldQuantity" value="'+vals[6]+'"</td></tr>');
            totalAmount=Number(totalAmount)+Number(vals[2]);
            totalAmountWaived=Number(totalAmountWaived)+Number(vals[7]);
            totalDiscount=Number(totalDiscount)+Number(vals[8]);
            $j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
                $j.each(result,function (index, value) {
                    $j("#dosage_"+idx).append($j("<option></option>").attr("value",index).text(value));
                });
            });
        });
        $j('table#tableDispense TBODY').append('<input type="hidden" name="previousEncounter" id="previousEncounter" value="'+aData[0]+'">');
        document.getElementById("totalAmount").value=totalAmount;
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
    });

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
    var cumilativePaid=parseFloat($j("#amountWaived").val())+parseFloat($j("#totalDiscount").val());
    cumilativePaid=parseFloat($j("#totalAmount").val())-cumilativePaid;
    document.getElementById("balance").value= parseFloat($j("#amountPaid").val())-parseFloat(cumilativePaid);
})
$j("INPUT[NAME='itemAmountWaived']").live("blur",function(){
    var waivedSumAmount=0;
    $j("INPUT[name='itemAmountWaived']").each(function() {
        waivedSumAmount += Number($j(this).val());
    });
    document.getElementById("amountWaived").value=Number(waivedSumAmount);
});
$j("INPUT[NAME='discount']").live("blur",function(){
    var discountAmount=0;
    $j("INPUT[name='discount']").each(function() {
        discountAmount += Number($j(this).val());
    });
    document.getElementById("totalDiscount").value=Number(discountAmount);
});
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
    document.forms['dispensingForm'].elements["patientId"].value = aData[1];
    document.forms['dispensingForm'].elements["totalAmount"].value = aData[2];
}
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
            dataString = "searchPatient=" + request.term;
            $j.getJSON("drugDetails.form?drop=patientSearch&" + dataString, function (result) {
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
                url:"drugDetails.form?drop=patientLastName&patientToFind="+patient,
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
    var sumpaid = 0;
    $j("input[name='amount']").each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("totalAmount").value=Number(sumpaid);
});
function addNewInvoiceOnQueue(){
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
                    url:"rfpDispenseFormProcessor.form?previousEncounter="+$j("#previousEncounter").val(),
                    data:{values:JSON.stringify(json) },
                    success:function (result) {
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
                var  td = $j(this).find('input');

                var key = td.attr('name');
                var val = td.val();
                obj[key] = val;
                rowObject.push(obj);
            });
            jsonReceiptData.push(rowObject);
        });
        $j.ajax({
            type:"POST",
            url:"rfpDispenseFormProcessor.form?receipt="+aData[0],
            data:{values:JSON.stringify(jsonReceiptData) },
            success:function (result) {
                var printingURL="printInvoice.form?encounterUUID="+result.toString();
                window.location=printingURL;
                document.getElementById("dispensingForm").reset();
                removeRows();
                unClearedReceiptsTable.fnDraw();

            }
        });
    }
    return false;
}
function confirmInvoiceAndIssueDrugs(){
    if((parseFloat($j("#amountPaid").val()) < parseFloat($j("#totalAmount").val())) || parseFloat($j("#amountPaid").val()).toString() =="NaN"){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Cannot close Invoice before payment is done" + '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else{
        var jsonReceiptData = [];
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
            jsonReceiptData.push(rowObject);
        });
        $j.ajax({
            type:"POST",
            url:"checkIfPaymentHasBeenMade.form?encounterToCheck="+aData[0],
            data:{values:JSON.stringify(jsonReceiptData) },
            success:function (result) {
                if(result==1){
                    $j.ajax({
                        type:"POST",
                        url:"closePatientEncounter.form?receipt="+aData[0]+"&patientID="+$j('#patientId').val(),
                        data:{values:JSON.stringify(jsonReceiptData) },
                        success:function () {
                            document.getElementById("dispensingForm").reset();
                            removeRows();
                            unClearedReceiptsTable.fnDraw();
                        }
                    });
                }
                else{
                    $j("#errorDialog").empty();
                    $j('<dl><dt></dt><dd >' + "Info: " + "Please process Payment before you close form" + '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }
            }

        })

    }
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
        data:amount,
        success:function () {
            unClearedReceiptsTable.fnDraw();
            $j("#unClearedReceiptsDIV").show();
        }
    });
})

setInterval(function(){
    unClearedReceiptsTable.fnDraw();
}, 10000);