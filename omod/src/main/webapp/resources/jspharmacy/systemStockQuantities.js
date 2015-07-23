$j.getJSON('getSystemInventoryQuantity.form', function (data) {
    $j.each(data.aaData, function(idx, elem){
        vals= elem.toString().split(",");
        $j('table#systemStockQuantitiesTable TBODY').append('' +
            '<tr>' +
            '<td>'+vals[2]+'</td>' +
            '<td>' +vals[3] +'</td>' +
            '<td>'+vals[4]+'</td>'+
            '<td>'+vals[5]+'</td>'+
            '<td>'+vals[9]+'</td>' +
            '<td>'+vals[7]+'</td> '+
            '<td></td>' +
            '</tr>');
    })
});