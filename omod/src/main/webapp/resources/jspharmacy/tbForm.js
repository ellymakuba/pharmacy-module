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
var patientEncountersDatatable;
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
                if(newcell.childNodes[0].name=="tbdrugdispense"){
                    newcell.childNodes[0].id="tbdrugdispense_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="tbquantity"){
                    newcell.childNodes[0].id="tbquantity_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="tbunitPrice"){
                    newcell.childNodes[0].id="tbunitPrice_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="tbamount"){
                    newcell.childNodes[0].id="tbamount_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="tbquantityInStock"){
                    newcell.childNodes[0].id="tbquantityInStock_" + rowCounter;
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
function removeRows() {
    $j('#tbTableDispense tr:not(.newRowClass)').remove();
}
$j('#tbTableDispense').hide();
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j("#tbdosage").get(0).options.length = 0;
    $j("#tbdosage").get(0).options[0] = new Option("Select","-1");
    $j.each(result,function (index, value) {
        $j("#tbdosage").get(0).options[$j("#tbdosage").get(0).options.length] = new Option(value,value);
    });
});
$j("#tbdispensingForm").validate();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}


$j("input[name=tbdrugdispense]").live("focus", function () {
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
                    if(idExtract =="tbdrugdispense") {
                        document.getElementById("tbquantityInStock").value=result;
                    }
                    else{
                        document.getElementById("tbquantityInStock_"+idExtract).value=result;
                    }
                }
            })
            $j.ajax({
                type:"GET",
                url:"stockInventory.form?select=unitPrice",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (result) {
                    if(idExtract =="tbdrugdispense") {
                        document.getElementById("tbunitPrice").value=result;
                    }
                    else{
                        document.getElementById("tbunitPrice_"+idExtract).value=result;
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
$j("input[name=tbPatientID]").live("focus", function () {
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
                    document.getElementById("tbPatientName").value=result;

                }
            })
            $j('#tbTableDispense').show();
            patientEncountersDatatable=$j('#patientEncounters').dataTable({
                bJQueryUI:true,
                bRetrieve:true,
                bServerSide:true,
                bProcessing:true,
                sAjaxSource:"patientEncountersByIdentifier.form?identifier=" + $j('#tbPatientID').val(),
                "fnServerData":fnDataTablesPipeline2
            });
            patientEncountersDatatable.fnDraw();
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });
});

$j("input[name='tbquantity']").live("blur", function () {
    var id=$j(this).attr('id');
    var idExtract=id.substring(id.indexOf("_")+1);
    if(idExtract=="tbquantity"){
        document.getElementById("tbamount").value = parseInt($j("input[id='tbquantity']").val())
            * parseInt($j("input[id='tbunitPrice']").val());
    }  else{
        document.getElementById("tbamount_"+idExtract).value = parseInt(document.getElementById("tbquantity_"+idExtract).value)
            * parseInt(document.getElementById("tbunitPrice_"+idExtract).value);
    }
});

//$j("input[name='drugdispense']").live('blur',function(){

//}) ;
$j("input[name='tbamount']").live("blur",function () {
    var sumpaid = 0;
    $j("input[name='tbamount']").each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("tbtotalAmount").value=Number(sumpaid);
});
$j("form#tbForm").unbind('submit').submit(function(){
    if (confirm('Do you want to proceed with dispensing?')) {
    var json = [];
    $j('#tbFormDIV').find('tr').each(function(){
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
        url:"tbFormDrugsAvailability.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {
            if(result.toString()=='true') {
                $j.ajax({
                    type:"POST",
                    url:"tbFormProcessor.form",
                    data:{values:JSON.stringify(json) },
                    success:function () {
                        removeRows();
                        document.getElementById("tbForm").reset();
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
    }
    return false;
})