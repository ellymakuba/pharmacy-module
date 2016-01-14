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
$j("form#s11ReportForm").submit(function () {
    var vals=[];
    $j.ajax({
        type:'GET',
        url: 'displayS11WithinDateRange.form?s11Datefrom='+$j("#s11Datefrom").val()+"&s11Dateto="+$j("#s11Dateto").val(),
        dataType: 'json',
        async: false,
        data: {myname:"fetch"},
        success: function(data) {
            $j.each(data.aaData, function(idx, elem){
                vals= elem.toString().split(",");
                $j('table#s11RecordsTable TBODY').append(
                    '<tr><td style="width:50%;"><a href="#">'+vals[0]+'</a></td>'+
                    '<td style="width:50%;">'+vals[1]+'</td></tr>');
            })
            document.getElementById("s11ReportForm").reset();
        }
    });
    return false;
});
$j("#s11Datefrom").datepicker();
$j("#s11Dateto").datepicker();
$j("#reportS11").delegate("table tbody tr td a","click",function(){
    var hrefValue=$j(this).html();
    $j("#selectedS11RecordsTable").dataTable({
        bJQueryUI:true,
        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        "bAutoWidth": true,
        "sPaginationType": "full_numbers",
        sAjaxSource:"getDrugTransactionsByS11.form?s11="+hrefValue
    });

})