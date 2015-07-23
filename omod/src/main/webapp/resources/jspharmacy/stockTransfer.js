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
getDataLocation();
$j("form#stockTransferForm").submit(function () {
    $j("#errorDialog").empty();
    var quantityToTransferNotEntered=false;
    var jsonS11Data = [];
    var dateNotEntered=false;
    var batchNoNotEntered=false;
    var buyingPriceNotEntered=false;
    var sellingPriceNotEntered=false;
    $j('#stockTransferForm').find('tr:not(eq(1))').each(function(){
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
function getDataLocation() {
    $j.getJSON("getAllLocations.form",function (result) {
        $j("#location").get(0).options.length = 0;
        $j("#location").get(0).options[0] = new Option("Select","-1");
        $j.each(result,function (index, value) {
            $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
        });
    });

}
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