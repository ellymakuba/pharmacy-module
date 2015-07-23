var binTable;
var dialog;
var filterString;
var editTr;
var dataString;
var link;
var dataStringValin;
var dataStringValinData;
var myResult;
var oCache = {
    iCacheLower:-1
};
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
                if(newcell.childNodes[0].name=="incomingdrug"){
                    newcell.childNodes[0].id="incomingdrug_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="incomingquantityin"){
                    newcell.childNodes[0].id="incomingquantityin_" + rowCounter;
                }
                if(newcell.childNodes[0].name=="date"){
                    newcell.childNodes[0].id="date_" + rowCounter;
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
    $j("#tableincoming").find("tr:gt(1)").remove();
}
$j("#transactions").change(function (){
    if($j("#transactions").val()=="New Stock")
    {
        $j(".hidable").show();
    }
    else{
        $j(".hidable").hide();
    }
})
$j("#incomingperm").hide();//
$j("popIncoming").hide();
$j("#incoming").hide();   //incoming
$j("#incomingvoid").hide();
$j("#incomingextrain").validate({
    rules:{
        supplier:{
            selectNone:true
        }
    }
});
getDataCategory();
getDataLocation();
getDataTransactionType();
//$j("#date").datepicker();

$j('#btnAdd').click(function () {
    var num = $j('.clonedInput').length;
    var newNum = new Number(num + 1);
    var newElem = $j('#input' + num).clone().attr('id', 'input' + newNum);
    newElem.children(':first').attr('id', 'answers' + newNum).attr('answers', 'answers');
    var idtag = "#answers" + newNum;
    $j('#input' + num).after(newElem);
    $j('#btnDel').attr('disabled', '');
});
$j('#btnDel').click(function () {
    var num = $j('.clonedInput').length;
    $j('#input' + num).remove();
    $j('#btnAdd').attr('disabled', '');
    if (num - 1 == 1)
        $j('#btnDel').attr('disabled', 'disabled');
});
$j('#btnDel').attr('disabled', 'disabled');
$j("#incomingInfo").hide();//
$j("#incomingvoid").validate();
$j("#incoming").validate({
    rules:{
        incomingdrug:{
            selectNone:true
        },
        location:{
            selectNone:true
        },
        transactions:{
            selectNone:true
        }
    }
});
$j("#incomingextrain").hide();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable(
    );
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tincoming', 'drugIncoming.form');
}
function fnFormatDetails(nTr) {
    var aData = binTable.fnGetData(nTr);
    var oFormObject = document.forms['incoming'];
    oFormObject.elements["incomingquantityin"].value = aData[4];
    oFormObject.elements["incomingedit"].value = 'true';
    oFormObject.elements["incominguuid"].value = aData[2];
    $j("#incoming").show();
}

function fnDetails(nTr) {
    if (confirm('Are you sure?')) {
        var aData = binTable.fnGetData(nTr);
        dataString = "bindrug=" + aData[2] + "&" + "binquantityin=" + aData[3] + "&binmax=" + aData[4] + "&binmin=" + aData[5] + "&location=" + aData[11] + "&date=" + aData[8] + "&incoming=" + aData[1] + "&binedit=" + false;
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

dialog = $j("#dialog-confirm").dialog({
    resizable:false,
    height:140,
    modal:true,
    buttons:{
        "Are you sure":function () {
            $j(this).dialog('close');
        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});

getDrugFilter();
var num = 0;
binTable = $j('#tincoming').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
        var htm = '<ul class="popIncoming">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popIncoming" id=' + "popIncoming" + aData[10] + '>';
        if (aData[0] == "edit") {
            htm += '<li> <a href="#"  id="editincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }
        if (aData[15] == "Approve") {
            htm += '<li> <a href="#" id="approveincoming" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/approve.png" />Approve from suppliers</a></li>';
        }
        if (aData[16] == "Delete") {
            htm += '<li> <a href="#" id="voidincoming" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }
        htm += '<li> <a href="#" id="detailsincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/details.png" />Details</a></li>';
        htm += '<li> <a href="#" id="cancelincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';
        htm += '</ul></li></ul>';
        $j('td:eq(0)', nRow).html(htm);
        return nRow;
    },
    sAjaxSource:'drugIncoming.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 0 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 2 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 7 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 8 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 9 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 10 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 12 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 13 ]
        },
        {
            "bVisible":false,

            "aTargets":[ 14 ]
        },
        {
            "bVisible":false,

            "aTargets":[ 15 ]
        } ,
        {
            "bVisible":false,

            "aTargets":[ 16 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:11,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });
//$j('select#filterdrug').change( function() { binTable.fnFilter( $j(this).val() ); } );

$j("#editincoming").live('click', function () {
    $j("ul .popIncoming").hide();
    //
    getDataLocation();
    getDataDrug();
    getDataSupplier();
    getDataCategory();
    getDataTransactionType();
    getDataLocationTwo();
    fnFormatDetails(editTr);

});


$j("#approveincoming").live('click', function () {
    $j("ul .popIncoming").hide();
    getDataSupplier();
    getUsers();
    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['incomingextrain'];
    oFormObject.elements["incominguuidextra"].value = aData[2];
    oFormObject.elements["incomingdrugg"].value = aData[3];
    $j("#incomingextrain").show();

});
$j("#voidincoming").live('click', function () {
    $j("ul .popIncoming").hide();
    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['incomingvoid'];
    oFormObject.elements["incominguuidvoid"].value = aData[2];
    $j("#incomingvoid").show();
});


$j("#cancelincoming").live('click', function () {
    $j("ul .popIncoming").hide();
});

$j('#tincoming tbody td ul').live('click', function () {
    editTr = this.parentNode.parentNode;
    var aData = binTable.fnGetData(editTr);
    link = "#popIncoming" + aData[10];
    $j(link).show();
});
function fnGetSelected(oTAble) {
    $j('#tincoming').find('td').each(function () {
        if ($j(this).hasClass('sorting_1')) {
        }
    });
}
$j('#tincoming tbody td  dfn').live('click', function () {
    $j("#incomingInfo").show("slow");//
    $j('#incomingInfo .loc').replaceWith("<div id='red'>Its already approved</div>");
    $j("#incomingInfo").delay(5000).hide();

});


function getUsers() {
    $j.getJSON("dispense.form?users=users",
        function (result) {
            $j("#requisition").get(0).options.length = 0;
            $j("#requisition").get(0).options[0] = new Option("Select","-1");
            $j("#issued").get(0).options.length = 0;
            $j("#issued").get(0).options[0] = new Option("Select","-1");
            $j("#authorized").get(0).options.length = 0;
            $j("#authorized").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) {
                $j("#requisition").get(0).options[$j("#requisition").get(0).options.length] = new Option( value, value);
                $j("#issued").get(0).options[$j("#issued").get(0).options.length] = new Option( value, value);
                $j("#authorized").get(0).options[$j("#authorized").get(0).options.length] = new Option(value, value);
            });
        });
}

function voidData(nTr) {
    var aData = binTable.fnGetData(nTr);
    var dataVal = "incominguuidvoid=" + aData[1] + "&incomingreason=" + "Dataadded";
    $j.ajax({
        type:"POST",
        url:"drugIncoming.form",
        data:dataVal,
        success:function () {
            AutoReload();
        }
    });
}


$j("#encountformDet").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        OK:function () {
            $j(this).dialog('close');
        }
    }
});
$j('#detailsincoming').live('click', function () {
    $j("ul .popIncoming").hide();
    $j("#encountformDet").empty();
    var aData = binTable.fnGetData(editTr);
    $j('<dl><dt></dt><dd > -' + "Drug:" + aData[3] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Quantity:" + aData[4] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Destination:" + aData[5] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Transactions type:" + aData[6] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Supplier:" + aData[7] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Min level:" + aData[8] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Max level:" + aData[9] + '</dd></dl> ').appendTo('#encountformDet');
    $j("#encountformDet").dialog("open");
});

$j("#detailsformValin").dialog({
    autoOpen:false,
    height:200,
    width:400,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');
            $j.ajax({
                type:"POST",
                url:"addOrUpdateInventoryBatches.form",
                data:dataStringValin,
                success:function () {
                    close();
                }
            });
        },
        Cancel:function () {
            $j(this).dialog('close');         },
        "Print":function () {
            demoTwoPageDocument(dataStringValin);
        }
    }
});
$j("#detailsformValinT").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');

            $j.ajax({
                type:"POST",
                url:"drugIncoming.form",
                data:dataStringValinData,
                success:function () {

                    close();
                }
            });

        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});
function demoTwoPageDocument(dataStringValin) {
    var doc = new jsPDF();


    myResult = dataStringValin.split("&");
    var val = 20;
    var right = 70;

    doc.setProperties({
        title:'Pharmacy request ',
        subject:'Incoming '
    });

    for (i = 0; i < myResult.length; i++) {
        if (i == 0) {
            doc.text(20, 20, "S11 No" + myResult[i].substring(myResult[i].indexOf("=")) + " ");

        }
        else if (i == 1)
            doc.text(20, 30, "Date" + myResult[i].substring(myResult[i].indexOf("=")) + " ");
        else if (i == 2)
            doc.text(20, 40, "Transaction type" + myResult[i].substring(myResult[i].indexOf("=")) + " ");
        else if (i == 3) {
            doc.text(20, 50, "Requesting from" + myResult[i].substring(myResult[i].indexOf("=")) + " ");
            doc.text(20, 60, "Drug");
            doc.text(140, 60, "Category");
            doc.text(180, 60, "Quantity");
        }
        else {
            doc.text(20, right, myResult[i].substring(myResult[i].indexOf("=") + 1, 30) + " ");
            doc.text(140, right, myResult[i + 1].substring(myResult[i + 1].indexOf("=") + 1) + " ");
            doc.text(180, right, myResult[i + 2].substring(myResult[i + 2].indexOf("=") + 1) + " ");
            right += 10;
            i += 2;
        }
    }
    doc.output('datauri');
}
function close() {
    $j("#incoming").hide();
    clear_form_elements("#s11");
    clear_form_elements("#incoming");
    $j("#incomingform").show();
    $j("#incoming").hide();
    $j("#tincoming").show();//
    $j("#incomingextrain").hide();
    AutoReload();

}


$j("form#incoming").submit(function () {
    $j("#incomingperm").hide();
    if ($j("#incoming").valid()) {
        dataStringValinData = $j("#incoming").serialize();
        myResult = dataStringValinData.split("&");
        $j("#detailsformValinT").empty();
        for (i = 0; i < myResult.length; i++) {
            if (i == 2)
                $j('<dl><dt></dt><dd > -' + "Drug " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 3)
                $j('<dl><dt></dt><dd > -' + "Location " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 4)
                $j('<dl><dt></dt><dd > -' + "Quantity " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 5)
                $j('<dl><dt></dt><dd > -' + "Transaction Type " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 6)
                $j('<dl><dt></dt><dd > -' + "Category " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');


        }
        $j("#detailsformValinT").dialog("open");
        return false;
    }
});
$j("form#s11").submit(function () {
    $j("#errorDialog").empty();
    var categoryNotSelected=false;
    var productWithNoSelectedCategory=0;
    var quantityNotEntered=false;
    var jsonS11Data = [];
    var dateNotEntered=false;
    var batchNoNotEntered=false;
    var buyingPriceNotEntered=false;
    var sellingPriceNotEntered=false;
    $j('#s11').find('tr').each(function(){
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
    $j("select[name='incomingcategory'] option:selected").each(function(){
        productWithNoSelectedCategory=productWithNoSelectedCategory+1;
        if($j(this).val()==-1){
             categoryNotSelected=true;
        }
    })
    $j("input[name='incomingquantityin']").each(function(){
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
    if ($j("#incomings11").val() =="") {
        $j('<dl><dt></dt><dd >' + "Info: " + "please enter the s11 number" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if ($j("#transactions option:selected").val()==-1) {
        //$j("#mySelect option[value='3']").attr('selected', 'selected');
        $j('<dl><dt></dt><dd >' + "Info: " + "please select transaction type" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if ($j("#location option:selected").val()==-1) {
        $j('<dl><dt></dt><dd >' + "Info: " + "please select site you are requesting stock" + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if(categoryNotSelected==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Choose category for product " + '</dd></dl></br>').appendTo('#errorDialog');
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
    else if(buyingPriceNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter buying prices for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else if(sellingPriceNotEntered==true){
        $j('<dl><dt></dt><dd >' + "Info: " + "Please make sure to enter selling price for all the drugs " + '</dd></dl></br>').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open");
    }
    else{
    $j.ajax({
        type:"POST",
        dataType:"json",
        url:"addOrUpdateInventoryBatches.form",
        data:{jsonObject:JSON.stringify(jsonS11Data)},
        success:function(result){
            document.getElementById("s11").reset();
            removeRows();
        }
    })
    }
   return false;
});

function clear_form_elements(ele) {
    $j(ele).find(':input').each(function () {
        switch (this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'hidden':
            case 'textarea':
                $j(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });
}
$j("#detailsformin").dialog({
    autoOpen:false,
    height:400,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');
            $j.ajax({
                type:"POST",
                url:"drugIncoming.form",
                data:dataString,
                success:function () {
                    closeExtra();
                }
            });
        },
        Cancel:function () {
            closeExtra();
            $j(this).dialog('close');
            $j('#in').after("<label>Quantity in</label> ");
        }
    }
});

function closeExtra() {
    AutoReload();
    $j("#incoming").hide();//
    clear_form_elements("#incomingextrain");
    clear_form_elements("#Permissionincoming");
    $j("#incomingform").show();
    $j("#incoming").hide();
    $j("#tincoming").show();//
    $j("#incomingextrain").hide();
    $j("#incomingperm").hide();
}

$j("form#incomingvoid").submit(function () {
    if ($j("#incomingvoid").valid()) {
        dataString = $j("#incomingvoid").serialize();
        $j.ajax({
            type:"POST",
            url:"drugIncoming.form",
            data:dataString,
            success:function () {
                $j("#incomingvoid").hide();//
                AutoReload();
                var oFormObject = document.forms['incomingvoid'];
                oFormObject.elements["incominguuidvoid"].value = "";
                oFormObject.elements["incomingreason"].value = "";
            }
        });
        return false;
    }
});
function change() {
    var e = document.getElementById("incomingdrug");
    var strUser = e.options[e.selectedIndex].value;
    getDataTotal(strUser);
}
$j("input[name=incomingdrug]").live("focus", function () {
    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');         },
        source:function (request, response) {
           // dataString = "searchDrug=" + request.term;
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
        select:function (event, ui) {
            var itemID=$j(this).attr('id');
            var idExtract=itemID.substring(itemID.indexOf("_")+1);
            var inventoryPropeties={};
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

                        if(idExtract =="incomingdrug") {
                            document.getElementById("buyingPrice").value=buyingPrice;
                            document.getElementById("unitPrice").value=sellingPrice;
                        }
                        else{
                            document.getElementById("buyingPrice_"+idExtract).value=buyingPrice;
                            document.getElementById("unitPrice_"+idExtract).value=sellingPrice;
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


$j("#filterdrug").autocomplete({
    search:function () {
        $j(this).addClass('working');    },
    source:function (request, response) {
        $j.getJSON("drop.form?searchDrug=" + request.term, function (result) {
            $j("#filterdrug").removeClass('working');
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
        binTable.fnFilter(ui.item.label);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
function getDataDrug() {
}
function getDrugFilter() {
}
function getDataLocation() {
    $j.getJSON("getAllLocations.form",function (result) {
            $j("#location1").get(0).options.length = 0;
            $j("#location1").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) {
                    $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
                    $j("#location1").get(0).options[$j("#location1").get(0).options.length] = new Option(value, value);
                });
        });

}
function getDataTotal(drug) {
    var url = "transactionsName.form?drop=totalTwo&drug=" + drug;
    $j.getJSON(url,function (result) {
            var oFormObject = document.forms['incoming'];
            oFormObject.elements["totalstore"].value = result;
        });

}
function getDataLocationTwo() {
    $j.getJSON("locationSelect.form",function (result) {
            $j("#destination").get(0).options.length = 0;
            $j("#destination").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) { //bincard"stateList
                    $j("#destination").get(0).options[$j("#destination").get(0).options.length] = new Option(value, value);
                });
        });
}
function getDataSupplier() {
    $j.getJSON("supplierName.form?drop=drop",function (result) {
            $j("#supplier").get(0).options.length = 0;
            $j("#supplier").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) { //bincard"stateList
                    $j("#supplier").get(0).options[$j(
                        "#supplier").get(0).options.length] = new Option(
                        value, value);
            });
        });
}
function getDataCategory() {
    $j.getJSON("categoryName.form?drop=drop",
        function (result) {
            $j("#incomingcategory").get(0).options.length = 0;
            $j("#incomingcategory").get(0).options[0] = new Option("Select","-1");
            $j.each(result,function (index, value) {
                    $j("#incomingcategory").get(0).options[$j("#incomingcategory").get(0).options.length] = new Option(value, value);
                });
        });
}
function getDataTransactionType() {
    $j.getJSON("transactionsName.form?drop=drop",function (result) {
        $j("#transactions").get(0).options.length = 0;
        $j("#transactions").get(0).options[0] = new Option("Select","-1");
        $j.each(result,function (index, value) {
            $j("#transactions").get(0).options[$j("#transactions").get(0).options.length] = new Option( value, value);
        });
    });
}
function show() {
    // $j("#incoming").show("slow");

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
