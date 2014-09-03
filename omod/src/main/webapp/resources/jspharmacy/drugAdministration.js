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
var rowCounter = 0 ;
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

$j("#incomingInfo").hide();//
$j("#incomingvoid").validate();
$j("#incoming").validate({
    rules:{
        drugdispense:{
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
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable(
    );
    oCache.iCacheLower = -1;
    table.fnDraw();
}
function removeRows() {
    $j('#tabledrugAdministration tr:not(.newRowClass)').remove();
}
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j("#dosage").get(0).options.length = 0;
    $j("#dosage").get(0).options[0] = new Option("Select","-1");
    $j.each(result,function (index, value) {
        $j("#dosage").get(0).options[$j("#dosage").get(0).options.length] = new Option(value,value);
    });
});
$j("form#drugAdministrationForm").unbind('submit').submit(function(){
    var proceed = confirm("Are you sure you want to complete this transaction");
    if(proceed==true){
    var json = [];
    $j('#dtab_1').find('tr').each(function(){
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
    $j.ajax ({
       type:"POST",
        url:"addTemporaryInventory.form",
        data:{values:JSON.stringify(json) },
        success:function(){
            removeRows();
            document.getElementById("drugAdministrationForm").reset();
        }

    })
    }
    return false;
})
