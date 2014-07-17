var oCache = {
    iCacheLower:-1
};

var ooTable;
var editTr;
var link;
var aData;
$j("#doseForm").validate(); //
$j("#voidDose").validate(); //
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#doseTable','doseProcessor.form');
}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var oFormObject = document.forms['doseForm'];
    oFormObject.elements["name"].value = aData[0];
    oFormObject.elements["doseEdit"].value = 'true';
    oFormObject.elements["doseUUID"].value = aData[1];
}
ooTable = $j('#doseTable').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    sAjaxSource:'doseProcessor.form',
    "fnServerData":fnDataTablesPipeline
});
$j('#editS').live('click', function () {
    $j("ul .popSupplier").hide();
    fnFormatDetails(editTr);
});
$j('#cancel').live('click', function () {
    $j("ul .popSupplier").hide();
});
$j('#doseTable tbody td ul').live('click', function () {
    editTr = this.parentNode.parentNode;
    aData = ooTable.fnGetData(editTr);
    link = "#popSupplier" + aData[2];
    $j(link).show();
});
$j('#void').live('click', function () {
    $j("ul .popSupplier").hide();
    var oFormObject = document.forms['suppliervoid'];
    oFormObject.elements["supplieruuidvoid"].value = aData[2];
    $j("#suppliervoid").show();//
});

$j("form#doseForm").submit(function () {
    if ($j("#doseForm").valid()) {
        dataString = $j("#doseForm").serialize();
        $j.ajax({
            type:"POST",
            url:"doseProcessor.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['doseForm'];
                oFormObject.elements["name"].value ="";
                oFormObject.elements["doseEdit"].value ="";
            }
        });
        return false;
    }
});

$j("form#voidDose").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#voidDose").valid()) {
        dataString = $j("#voidDose").serialize();
        $j.ajax({
            type:"POST",
            url:"doseProcessor.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#voidDose").hide();//
                var oFormObject = document.forms['voidDose'];
                oFormObject.elements["reason"].value = "";
            }
        });
        return false;
    }
});
$j("#reason").autocomplete({
    minLength:0,
    source:formReasons,
    select:function (event, ui) {
        $j("#reason").removeClass("highlight");
        $j(this).autocomplete('close');
    },
    change:function (event, ui) {
        if (!ui.item) {
            $j('#reason').addClass('highlight');
            $j("#reason").val("");
        }
    }

});


