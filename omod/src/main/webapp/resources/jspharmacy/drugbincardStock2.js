var stockTable;
var url;
var min;
var max;
var dataString;
var eSignCheck = false;
var link;
var editTr;
var uuid;
var oCache = {
    iCacheLower:-1
};
$j("#check").validate({});
$j("#stockcard").validate({
    rules:{
        stockdrug:{
            selectNone:true
        }
    }
});
stockTable=$j('#tstockcard').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    bLengthChange:false,
    bPaginate:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
        var htm = '<ul class="popSupplier">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popSupplier" id=' + "popSupplier" + aData[1] + '>';
        htm += '<li> <a href="#"  id="editS"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';
        if (aData[6] == "void") {
            htm += '<li> <a href="#" id="void" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }
        htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';
        htm += '</ul></li></ul>';
        $j('td:eq(0)', nRow).html(htm);
        return nRow;
    },
    bInfo:false,
    sAjaxSource:'drugBincard.form',
    "fnServerData":fnDataTablesPipeline
});
$j('#tstockcard tbody tr').live('click', function () {
    var data = stockTable.fnGetData(this);
    uuid = data[2];
    // ... do something with the array / object of data for the row
});
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j("#dose").get(0).options.length = 0;
    $j("#dose").get(0).options[0] = new Option("Select","-1");
    $j.each(result,function (index, value) {
        $j("#dose").get(0).options[$j("#dose").get(0).options.length] = new Option(value,value);
    });
});
var currentTime = new Date();
var month = currentTime.getMonth() + 1;
var day = currentTime.getDate();
var year = currentTime.getFullYear();
t2 = day + "/" + month + "/" + year;
var y = t2.split("/");
var dateCurrent = new Date(y[2], (y[1] - 1), y[0]);
$j("#stockvoid").validate();
getDrugCategory();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
function AutoReload() {
    RefreshTable('#tstockcard', 'drugBincard.form');

}
function fnFormatDetails(nTr) {
    var oFormObject = document.forms['bincardform'];
    oFormObject.elements["drugname"].value = aData[2];
    oFormObject.elements["cQuantity"].value = aData[3];
    oFormObject.elements["drug_id"].value = aData[6];
    oFormObject.elements["uuid"].value = aData[1];
    oFormObject.elements["unitP"].value = aData[7];
}
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

$j('select#filterstock').change(function () {
    var e = document.getElementById("filterstock");
    var str = e.options[e.selectedIndex].value;
    if (str !== "-1") {
        tableSetUp(str);
    }
});
getDrugFilter();
$j('select#filterdrugstock').change(function () {
});
function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth() + (12 * (date2.getFullYear() - date1.getFullYear()));
}
function getDrugCategory() {
    $j.getJSON("categoryName.form?drop=drop",
        function (result) {
            $j("#filterstock").get(0).options.length = 0;
            $j("#filterstock").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) { //bincard"stateList
                $j("#filterstock").get(0).options[$j("#filterstock").get(0).options.length] = new Option(value, value);
            });
        });
}
$j("#filterdrugstock").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },
    source:function (request, response) {
        dataString = "searchDrug=" + request.term;
        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {
            $j("#filterdrugstock").removeClass('working');
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
        stockTable.fnFilter($j(this).val());
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
$j('#editS').live('click', function () {
    $j("ul .popSupplier").hide();
    fnFormatDetails(editTr);

});
$j('#cancel').live('click', function () {
    $j("ul .popSupplier").hide();

});
$j('#tstockcard tbody td ul').live('click', function () {
    editTr = this.parentNode.parentNode;
    aData = stockTable.fnGetData(editTr);
    link = "#popSupplier" + aData[1];
    $j(link).show();
});

$j("form#bincardform").submit(function () {
    if ($j("#bincardform").valid()) {
        dataString = $j("#bincardform").serialize();
        $j.ajax({
            type:"POST",
            url:"drugBincard.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['bincardform'];
                oFormObject.elements["drugname"].value = '';
                oFormObject.elements["drug_id"].value = '';
                oFormObject.elements["cQuantity"].value = "";
                oFormObject.elements["aStcock"].value = "";
                oFormObject.elements["uuid"].value = "";
                oFormObject.elements["unitP"].value = "";

            }
        });
        return false;
    }
});