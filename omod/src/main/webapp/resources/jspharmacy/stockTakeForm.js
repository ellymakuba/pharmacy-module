$j.getJSON('drugBincard.form', function (data) {
        $j.each(data.aaData, function(idx, elem){
            vals= elem.toString().split(",");
            $j('table#stockTakeTable TBODY').append('' +
                '<tr>' +
                '<input type="hidden" name="stockTakeFormDrugUUID" id="stockTakeFormDrugUUID_'+idx+'" value="'+vals[1]+'" />'+
                '<td><input type="text" name="stockTakeFormDrug" id="stockTakeFormDrug_'+idx+'"  style="width:350px;" readonly value="'+vals[2]+'" /></td>' +
                '<td><input type="text" name="stockTakeFormQuantity" style="width:50px;" id="stockTakeFormQuantity_'+idx+'" readonly  value="' +vals[3]+'"/>' +'</td>' +
                '<td><input type="text" name="stockTakeFormExpiryDate" id="stockTakeFormExpiryDate_'+idx+'" readonly style="width:100px" value="'+vals[4]+'"/></td>'+
                '<td><input type="text"  name="stockTakeFormBatchNo" style="width:80px;" id="stockTakeFormBatchNo_'+idx+'" value="'+vals[5]+'" /></td>'+
                '<td><input type="text" name="stockTakeFormDrugID" id="stockTakeFormDrugID_'+idx+'" style="width:80px;" readonly value="'+vals[6]+'" /></td>' +
                '<td><input type="text" name="stockTakeFormunitPrice" style="width:50px;" value="'+vals[7]+'" id="stockTakeFormunitPrice_'+idx+'"/></td> '+
                '<td><input type="text" name="stockTakeFormDose" style="width:80px;" value="'+vals[8]+'" id="stockTakeFormDose_'+idx+'"/></td> '+
                '<td><input type="text" name="stockTakeFormNewQuantity" id="stockTakeFormNewQuantity" value="" style="width:80px;" </td></tr>');

    })
    $j('table#stockTakeTable TBODY').append('<tr><td><input type="button" value="Update Inventory" onclick="updateInventoryDetails()"/></td></tr>');
});
function updateInventoryDetails(){
    var jsonStockTakeData = [];
    $j('#stockTakeFormDIV').find('tr').each(function(){
        var rowObject=[];
        $j(this).find('input').each(function(){
        var obj = {}
        var key = $j(this).attr('name');
        var val = $j(this).val();
        obj[key] = val;
        rowObject.push(obj);
        })
        jsonStockTakeData.push(rowObject);
    });
    $j.ajax({
        type:"POST",
        url:"postStockTakeFormRequest.form",
        data:{values:JSON.stringify(jsonStockTakeData) },
        success:function(){
            window.location.reload(true);
        }
    });
}