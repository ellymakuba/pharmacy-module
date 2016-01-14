var binTable;
var binTable2;
var oFormObjectForm;
var totalElements = 0;
var nTrIn;
var batchEditTr;
var batchaData;
var batchLink
var oCache = {
    iCacheLower:-1
};
$j("#errorDivStore").hide();
$j("#dispensevoid").hide();
$j("#parent_div_2").hide();
$j("#parent_div_1").hide();
$j("#lower").hide();
//$j("#dispenseextra").validate();
$j("#dispensevoid").validate();
$j("#dispenseVal").validate();
$j("#batchSetterDIV").hide();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
function AutoReload() {
    RefreshTable('#toutgoing', 'drugOutgoing.form');
}
function RefreshInventory(tableId) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
function fnFormatDetails(nTr) {
    var batchaData = binTable.fnGetData(nTr);
    show();
}
function fnDetails(nTr) {
    if (confirm('Are you sure?')) {
        var batchaData = binTable.fnGetData(nTr);
        dataString = "bindrug=" + batchaData[2] + "&" + "binquantityin=" + batchaData[3] + "&binmax=" + batchaData[4] + "&binmin=" + batchaData[5] + "&location=" + batchaData[11] + "&date=" + batchaData[8] + "&outgoing=" + batchaData[1] + "&binedit=" + false + "&less=" + false;

        $j.ajax({
            type:"POST",
            url:"drugBincard.form",
            data:dataString,
            success:function () {


            }
        });

    }
    else {

    }
}
function SetDispenseTable(id) {
    oCache.iCacheLower = -1;
    var url = 'listOfDrugsToSetBatch.form?drug=' + id;
    if ($j('#lower').is(':visible')) {
        oCache.iCacheLower = -1;
        $j('#tdispense').dataTable().fnReloadAjax(url);

    }
    else {
        binTable = $j('#tdispense').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bAutoWidth:false,
            bServerSide:true,
            bProcessing:true,
            sAjaxSource:url,
            "fnServerData":fnDataTablesPipeline,
            "aoColumnDefs":[
                {
                    "bVisible":false,
                    "aTargets":[ 0 ]
                }
            ]
        });


    }

    $j("#lower").show();

}

$j('#tdispense tbody td ul').live('click', function () {
    nTrIn = this.parentNode.parentNode;
    batchaData = binTable.fnGetData(nTrIn);
    batchLink = "#popSet" + batchaData[2];
    $j(batchLink).show();

});
$j('#cancelBatch').live('click', function () {
    $j("ul .popSet").hide();
});
$j('#deleteBatch').live('click', function () {
    $j("ul .popSet").hide();
    var oFormObjectB = document.forms['dispensevoid'];
    oFormObjectB.elements["dispenseuuidvoid"].value = batchaData[2];
    $j("#dispensevoid").show();
});
$j('#editBatch').live('click', function () {
});
$j("form#filteroutgoing").submit(function () {
    if ($j("#filteroutgoing").valid()) {
        AutoReload();
        return false;
    }
});
getDrugFilter();
$j.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings, sNewSource) {
    oSettings.sAjaxSource = sNewSource;
    this.fnClearTable(this);
    this.oApi._fnProcessingDisplay(oSettings, true);
    var that = this;

    $j.getJSON(oSettings.sAjaxSource, null, function (json) {
        /* Got the data - add it to the table */
        for (var i = 0; i < json.aaData.length; i++) {
            that.oApi._fnAddData(oSettings, json.aaData[i]);
        }

        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        that.fnDraw(that);
        that.oApi._fnProcessingDisplay(oSettings, false);
    });
}
function setTable(uuid) {
    var url = 'drugDispenseStore.form?uuid=' + uuid;
    if ($j('#parent_div_1').is(':visible')) {
        oCache.iCacheLower = -1;
        $j('#tinventoryset').dataTable().fnReloadAjax(url);

    }
    else {
        binTable2 = $j('#tinventoryset').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bAutoWidth:false,
            bServerSide:true,
            bProcessing:true,
            sAjaxSource:url,
            "aoColumnDefs":[
                {
                    "bVisible":false,
                    "aTargets":[ 0 ]
                }
            ]
        });

    }
    $j("#parent_div_2").show();
    $j("#parent_div_1").show();
}
function voidData(nTr) {
    var batchaData = binTable.fnGetData(nTr);
    var dataVal = "outgoinguuidvoid=" + batchaData[1] + "&outgoingreason=" + "Dataadded";
    $j.ajax({
        type:"POST",
        url:"drugIncoming.form",
        data:dataVal,
        success:function () {
            AutoReload();
        }
    });
}
$j('#tinventoryset').delegate(' tbody td  input', 'click', function () {
    nTrIn = this.parentNode.parentNode;
    var oFormObjectForm = document.forms['dispenseextra'];
    var batchaData = binTable2.fnGetData(nTrIn);
    oFormObjectForm.elements['inventoryNo'].value = batchaData[0];
});

function loadImage(url, type, msg, height, moduleWidth, wideFactor, format, qz, hrp) {
    $j("#spinner").show();
    var imageSource = url + "moduleServlet/pharmacy/Barcode?type=" + type + "&msg=" + msg + "&height=" + height + "&mw=" + moduleWidth + "&wf=" + wideFactor + "&fmt=" + format + "&qz=" + qz + "&hrp=" + hrp;
    showImage(imageSource);
    return false;
}
function showPrint() {
    var content = $j("#barcodeImg").html();
    var pwin = window.open('', 'print_content', 'width=300,height=200');
    pwin.document.open();
    pwin.document.write('<html><body onload="window.print()">' + content
        + '</body></html>');
    pwin.document.close();
    setTimeout(function () {
        pwin.close();
    }, 1000);
}
function showImage(src) {
    $j("#barcodeImg").empty();
    $j("#cmdDownDiv").empty();
    $j("#barcodeImg").append("<img id='theImg' src='" + src + "'/>");
    $j("#spinner").hide();
}
$j("#hideoutgoingform").click(function () {
    $j("#hideoutgoingform").hide();
    $j("#parent_div_1").hide();
    $j("#parent_div_2").hide();
});
$j("#outgoingform").click(function () {
    getDataLocation();
    getDataDrug();
    getDataSupplier();
    getDataTransactionType();
    getDataLocationTwo();
    show();
});
function getDataDrug() {
    $j.getJSON(
        "drop.form",
        function (result) {
            $j("#outgoingdrug").get(0).options.length = 0;
            $j("#outgoingdrug").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) { //bincard"stateList
                $j("#outgoingdrug").get(0).options[$j(
                    "#outgoingdrug").get(0).options.length] = new Option(
                    value, value);
            });
        });
}
function getDataLocation() {
    $j.getJSON( "locationSelect.form",
        function (result) {
            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select", "-1");
            $j.each(result,function (index, value) { //bincard"stateList
                $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
            });
        });
}
function getDataLocationTwo() {
    $j.getJSON("locationSelect.form", function (result) {
        $j("#destination").get(0).options.length = 0;
        $j("#destination").get(0).options[0] = new Option("Select", "-1");
        $j.each(result, function (index, value) { //bincard"stateList
            $j("#destination").get(0).options[$j("#destination").get(0).options.length] = new Option(value, value);
        });
    });
}
function getDataSupplier() {
    $j.getJSON("supplierName.form?drop=drop",function (result) {
        $j("#supplierout").get(0).options.length = 0;
        $j("#supplierout").get(0).options[0] = new Option("Select","-1");
        $j.each(result,function (index, value) { //bincard"stateList
            $j("#supplierout").get(0).options[$j(
                "#supplierout").get(0).options.length] = new Option(
                value, value);
        });
    });
}
function getDataTransactionType() {
    $j.getJSON("transactionsName.form?drop=drop",function (result) {
        $j("#transactions").get(0).options.length = 0;
        $j("#transactions").get(0).options[0] = new Option("Select","-1");
        $j.each(result,function (index, value) { //bincard"stateList
            $j("#transactions").get(0).options[$j(
                "#transactions").get(0).options.length] = new Option(value, value);
        });
    });
}
function getDataTotal(drug) {
    var url = "transactionsName.form?drop=total&drug=" + drug;
    $j.getJSON(url, function (result) {
        var oFormObject = document.forms['dispenseVal'];
        oFormObject.elements["totalstore"].value = result;
    });
}
$j("#dispensedrug").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },
    source:function (request, response) {
        dataString = "searchDrug=" + request.term;
        $j.getJSON("pharmacyDrugsRequest.form?" + dataString, function (result) {
            $j("#dispensedrug").removeClass('working');
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
        var drugNameToLoad= ui.item.label;
        var drugId=drugNameToLoad.substring(drugNameToLoad.indexOf(">")+1);
        setTable(drugId);
        SetDispenseTable(drugId);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
function getDrugFilter() {
}
function show() {
    $j("#outgoingVal").show("slow");
}
$j('#tinventoryset').delegate('tbody tr', 'click', function (){
    var clickedRow=this;
    var batchaData = binTable2.fnGetData(clickedRow);
    var inventoryNO=batchaData[0];
    var drugNameToLoad= $j("#dispensedrug").val();
    var drugId=drugNameToLoad.substring(drugNameToLoad.indexOf(">")+1);

        var drug = drugNameToLoad.substring(0,drugNameToLoad.indexOf(">"));
        dataString = "&dispensedrug=" + drug +"&drugID="+drugId;
        $j.ajax({
            type:"POST",
            url:"drugDispenseRequest.form?inventoryNo="+inventoryNO+"&dispensedrug=" + drug+"&drugID="+drugId,
            data:{datas:JSON.stringify(dataString) },
            dataType:"json",
            async:false,
            success:function () {
                SetDispenseTable(drugId);
            }
        });
});