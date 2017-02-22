function saveForm(){
    var json = [];
    $j('#reasons').find('tr').each(function(){
        var obj = {}
        var  td = $j(this).find('input,select,hidden');
        var key = td.attr('name');
        var val = td.val();
        obj[key] = val;
        json.push(obj);
    });
    $j.ajax({
        type:"POST",
        url:"waiver.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {
        }
    });
}
var oCache = {
    iCacheLower:-1
};

var waiver_reason_table;
var userEditTr;
var userLink;
var uuid;
var data;
$j("#waiverForm").validate();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
function fnFormatDetails(nTr) {
    document.getElementById("reason").value=data[1];
}
waiver_reason_table = $j('#treasons').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    sAjaxSource:'waiver_reasons.form',
    "fnServerData":fnDataTablesPipeline,
    fnRowCallback:function (nRow, data, iDisplayIndex){
        var htm = '<ul class="popLocationUsers">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popLocationUsers" id=' + "popLocationUsers" + data[0] + '>';
        if (data[0] == "edit") {
            htm += '<li> <a href=""  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';
        }
        htm += '<li> <a href="" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';
        htm += '</ul></li></ul>';
        $j('td:eq(0)', nRow).html(htm);
        return nRow;
    }
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:3,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });
$j('#treasons tbody tr').live('click', function () {
    var record = waiver_reason_table.fnGetData(this);
    uuid = record[2];
});
$j('#edit').live('click', function () {
    $j("ul .popLocationUsers").hide();
    fnFormatDetails(userEditTr);
    return false;
});
$j('#cancel').live('click', function () {
    $j("ul .popLocationUsers").hide();
    return false;
});
$j('#treasons tbody td ul').live('click', function () {
    userEditTr = this.parentNode.parentNode;
    data = waiver_reason_table.fnGetData(userEditTr);
    userLink = "#popLocationUsers" + data[2];
    $j(userLink).show();
});