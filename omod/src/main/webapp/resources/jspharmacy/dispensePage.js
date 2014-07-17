
var arrayOne=new Array();
var arrayTwo=new Array();
var arrayThree=new Array();
var countGlobal=0;
var counter=0;
var message="";
var formType="";
function fnSetKey(aoData, sKey, mValue) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            aoData[i].value = mValue;
        }
    }
}
function fnGetKey(aoData, sKey) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            return aoData[i].value;
        }
    }
    return null;
}
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
var binTable;
var uoTable;
var patientID;
var patientName;
var dateGiven;
var quantity;
var price;
var valgiven;
var method;
var priceOne;
var valgivenOne;
var methodOne;
var id = jQuery.Pid.id;
var url = 'dispense.form?patientID=' + id+"&form=xxxx";
var urljson = '' + jQuery.Page.context + 'module/jforms/view.form?id=' + id;
function getPatientAge(){
 var patientAge =$j.getJSON("dispense.form?age=" + id, function (result)
    {
        $j.each(result, function (key, val){
            jQuery.Age = {age:val};
        });
    });
    return patientAge;
}
var patienteEncounters = $j('#pencounters').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:"dispense.form?patientEncounters=pEn&patientID=" + id,
    "fnServerData":fnDataTablesPipeline2
});
function getDataForms() {
    $j.getJSON("drugDetails.form?drop=forms", function (result) {
        $j("#formVal").get(0).options.length = 0;
        $j("#formVal").get(0).options[0] = new Option("Select",
            "-1");
        $j.each(result, function (index, value) { //bincard"stateList
                $j("#formVal").get(0).options[$j("#formVal").get(0).options.length] = new Option( value, value);
            });
    });
}
uoTable = $j('#tunits').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:url,
    "fnServerData":fnDataTablesPipeline2,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});
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


$j('#formFilter').live('click', function() {
    var e = document.getElementById("formVal");
    var strForm = e.options[e.selectedIndex].value;
    if(strForm!=null){
        url = 'dispense.form?patientID=' + id+"&form="+strForm;
        $j('#tunits').dataTable().fnReloadAjax(url);
    }
    return false;
});
binTable = $j('#tforms').dataTable(
    {
        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        bPaginate:false,
        bFilter:false,
        bInfo:false,
        "fnRowCallback":function (nRow, aData, iDisplayIndex) {
            $j('td:eq(0)', nRow)
                .html('<img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />');
            $j('td:eq(5)', nRow).html(aData[4]);
            return nRow;
        },
        sAjaxSource:urljson,
        "aoColumnDefs":[
            {
                "bVisible":false,
                "aTargets":[ 1 ]
            }
        ]
    });
$j('#proceed').live('click',function()
{
    fnGetSelectedVal();
    return false;
});
function fnGetSelectedVal()
{
    arrayOne=new Array();
    arrayTwo=new Array();
    arrayThree=new Array();
    countGlobal=0;
    counterTwo=0;
    counter=0;
    $j('#tforms').find('input').each(function()
    {
        if ($j(this).is(':checked'))
        {
            nTrIn = this.parentNode.parentNode;
            var aData = binTable.fnGetData(nTrIn);
            arrayOne[counter]= aData[5];
            arrayTwo[counter]= aData[3];
            arrayThree[counter]= aData[1];
            counter++;
        }
    });
    countGlobal=0;
    fnShow();
}
function fnShow() {
    if(countGlobal<=counter)
    {
        var pid =  arrayOne[countGlobal];
        var formid =  arrayTwo[countGlobal];
        $j.getJSON("" + jQuery.Page.context+ "module/jforms/view.form?datajson=" +   arrayThree[countGlobal],
            function (result) {
                getDataDispense(result, pid, formid);
            });
    }
    else
    {
        arrayOne=new Array();
        arrayTwo=new Array();
        arrayThree=new Array();
        countGlobal=0;
        counterTwo=0;
        counter=0;
        $j('#tforms').find('input').each(function()
        {
            if ($j(this).is(':checked'))
            {
                $j(this).attr('checked', false);

            }
        });
    }
    countGlobal++;
}
function getArray(form)
{
    var json = "[";
    var myObject = new Object();
    $j(form).find('input').each(function ()
    {
        if ($j(this).attr('type')=="text")
        {
            myObject.name = $j(this).attr('name');
            myObject.value = $j(this).val();
            var myString = JSON.stringify(myObject);
            json += myString+",";
        }
        else  if ($j(this).attr('type')=="hidden")
        {
            myObject.name = $j(this).attr('name');
            myObject.value = $j(this).val();
            var myString = JSON.stringify(myObject);
            json += myString+",";
        }
        else if  ($j(this).attr('type')=="checkbox")
        {
            if ($j(this).is(':checked'))
            {
                myObject.name = $j(this).attr('name');
                myObject.value = $j(this).val();
                var myString = JSON.stringify(myObject);
                json += myString+",";
            }
            else{
                myObject.name = $j(this).attr('name');
                myObject.value = '';
                var myString = JSON.stringify(myObject);
                json += myString+",";
            }
        }
    });
    json += "]";
    return json;
}
$j("form#hivform").unbind('submit').submit(function ()
{
    var v= validateHivForm();
    if(v==2)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==0)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1)
    {
        //
        var numbers="";
        var values = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')){
                numbers+= $j(this).val().substring($j(this).val().indexOf('|')+1)+",";
                return $j(this).val().substring($j(this).val().indexOf('|')+1);
            }
        }).get();
        var drugid = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')) {
                return $j(this).val().substring(0,$j(this).val().indexOf('|'));
            }
        }).get();
        var values2 = $j("input[title='Dispensed']").map(
            function () {
                if($j(this).is(':checked'))
                {
                    return $j(this).val();
                }
                else if($j(this).val()!="" && ($j(this).attr("name")=="otherd") )
                {
                    return $j(this).val();
                }
            }).get();
        var vals = values.toString().split(",");
        var vals2 = values2.toString().split(",");
        var drugs = drugid.toString().split(",");
        var ans=checkHivRegimen(drugid);
        if(ans==true)  {
            var regimens=regimenFilter(drugid);
            var regimenCode= regimens[0];
            var regimenName=regimens[1];
            var val= checkHivForm(regimenName);
            if(val==true)
            {
                var qnty = vals2.toString().split(",");
                var ids = vals.toString().split(",");
                var size = vals.length;
                var json = {};
                for (i = 0; i < size; i++)
                {
                    json[ids[i]] = qnty[i];
                }
                $j.ajax({
                    type:"GET",
                    url:"dispense.form",
                    data:{drugCheck:JSON.stringify(json) },
                    dataType:"json",
                    success:function (result){
                        if (result.toString() == 'true'){
                            var fields = $j("#hivform").serializeArray();
                            $j.ajax({
                                type:"POST",
                                url:"hivProcessor.form?" + "regimenCode=" + regimenCode+"&regimenName="+regimenName,
                                data:{values:JSON.stringify(fields) },
                                dataType:"json",
                                beforeSend:function (x) {
                                    if (x && x.overrideMimeType) {
                                        x.overrideMimeType("application/j-son;charset=UTF-8");
                                    }
                                },
                                success:function () {
                                    fnShow();
                                    $j("#dispenseform").dialog("close");
                                    RefreshTable("#tunits");
                                    $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                                    $j("#dataDiv").show("slow");//
                                    $j("#dataDiv").delay(5000).hide("slow");
                                }
                            });
                        }
                        else {
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }

                    }
                });

            }

        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "Invalid Regimen selection" + '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open");
        }
    }
    return false;
});


$j("form#hivAdultOiForm").unbind('submit').submit(function () {
    var v= validateHivOiForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var values = $j("input[title='drug']").map(
            function () {
                if ($j(this).is(':checked')) {
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
                if(this.id=="drug6" && $j(this).val()!=""){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                } //
                if(this.id=="drug7" && $j(this).val()!="" ){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
            }).get();
        var values2 = $j("input[title='dispensed']").map(
            function () {
                if($j(this).val()!=""){
                    return $j(this).val();
                }
            }).get();
        var vals = values.toString().split(",")+",";
        var vals2 = values2.toString().split(",");
        var qnty = vals2.toString().split(",");
        var ids = vals.toString().split(",");
        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {
            json[ids[i]] = qnty[i];
        }
        $j.ajax({
            type:"GET",
            url:"dispense.form",
            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                if (result.toString() == 'true') {
                    var fields = $j("#hivAdultOiForm").serializeArray();
                    $j.ajax({
                        type:"POST",
                        url:"hivAdultOiProcessor.form",
                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {
                            fnShow();
                            $j("#dispenseform").dialog("close");
                            RefreshTable("#tunits");
                            $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                            $j("#dataDiv").show("slow");//
                            $j("#dataDiv").delay(5000).hide("slow");

                        }
                    });
                }
                else {
                    $j("#errorDialog").empty();
                    $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }
            }
        });

    }  else{
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }

    return false;
});

$j("form#cadiovascularform").unbind('submit').submit(function () {
    var v= validateCadiovascularForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var values = $j("input[title='drug']").map(
            function () {
                if ($j(this).is(':checked')) {
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
                if(this.id=="drug29" && $j(this).val()!=""){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
                if(this.id=="drug30" && $j(this).val()!="" ){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
            }).get();
        var sig = $j("input[title='sig']").map(
            function () {
                return  $j(this).attr('name').substring($j(this).attr('name').indexOf('#')+1,$j(this).attr('name').indexOf('$'));
            }).get();
        var values2 = $j("input[name='Quantity']").map(
            function () {
                if($j(this).val()!=""){
                    return $j(this).val();
                }
            }).get();
        var vals = values.toString().split(",");
        var drugQ = values2.toString().split(",");
        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {
            json[vals[i]] = drugQ[i];
        }
        $j.ajax({
            type:"GET",
            url:"dispense.form",

            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                if (result.toString() == 'true') {
                    var fields = $j("#cadiovascularform").serializeArray();
                    $j.ajax({
                        type:"POST",
                        url:"cadiovascularProcessor.form",
                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {
                            fnShow();
                            $j("#dispenseform").dialog("close");
                            RefreshTable("#tunits");
                            $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                            $j("#dataDiv").show("slow");//
                            $j("#dataDiv").delay(5000).hide("slow");
                        }
                    });
                }
                else {
                    $j("#errorDialog").empty();
                    $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" +
                        '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }
            }
        });
    }
    else{
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    return false;
});
$j("form#revolvingPedsform").unbind('submit').submit(function () {
    var v= validateRevolvingPedsForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0){
        $j("#errorDialog").empty();

        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');

        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var values = $j("input[title='drug']").map(
            function () {
                if ($j(this).is(':checked')) {

                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
                if(this.id=="drug24" && $j(this).val()!=""){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);

                }

            }).get();

        var values2 = $j("input[title='gnty']").map(
            function () {
                if($j(this).val()!=""){

                    return $j(this).val();
                }

            }).get();
        var vals = values.toString().split(",");
        var drugQ = values2.toString().split(",");
        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {
            json[vals[i]] = drugQ[i];
        }

        $j.ajax({
            type:"GET",
            url:"dispense.form",
            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                if (result.toString() == 'true') {
                    var fields = $j("#revolvingPedsform").serializeArray();
                    $j.ajax({
                        type:"POST",
                        url:"revolvingPedsProcessor.form",
                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {
                            fnShow();
                            $j("#dispenseform").dialog("close");
                            RefreshTable("#tunits");
                            $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                            $j("#dataDiv").show("slow");//
                            $j("#dataDiv").delay(5000).hide("slow");
                        }
                    });
                }
                else {
                    $j("#errorDialog").empty();
                    $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }
            }
        });
    }
    else{
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    return false;
});
$j("form#revolvingAdultform").unbind('submit').submit(function () {
    var v= validateRevolvingAdultForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var values = $j("input[title='drug']").map(
            function () {
                if ($j(this).is(':checked')) {
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }
                if(this.id=="drug29" && $j(this).val()!=""){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);

                }
                if(this.id=="drug30" && $j(this).val()!="" ){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);

                }
            }).get();
        var values2 = $j("input[name='Quantity']").map(
            function () {
                if($j(this).val()!=""){

                    return $j(this).val();
                }

            }).get();
        var vals = values.toString().split(",");
        var drugQ = values2.toString().split(",");
        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {
            json[vals[i]] = drugQ[i];
        }
        $j.ajax({
            type:"GET",
            url:"dispense.form",
            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                if (result.toString() == 'true') {
                    var fields = $j("#revolvingAdultform").serializeArray();
                    $j.ajax({
                        type:"POST",
                        url:"revolvingAdultProcessor.form",
                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {
                            fnShow();
                            $j("#dispenseform").dialog("close");
                            RefreshTable("#tunits");
                            $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                            $j("#dataDiv").show("slow");//
                            $j("#dataDiv").delay(5000).hide("slow");
                        }
                    });
                }
                else {
                    $j("#errorDialog").empty();
                    $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                    $j("#errorDialog").dialog("open");
                }

            }
        });
    }
    else{
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    return false;
});

$j("form#hivpedsform").unbind('submit').submit(function () {
    var v= validateHivPedsForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var numbers="";
        var values = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')){
                numbers+= $j(this).val().substring($j(this).val().indexOf('|')+1)+",";
                return $j(this).val().substring($j(this).val().indexOf('|')+1);
            }
        }).get();
        var values2 = $j("input[title='qnty']").map(
            function () {
                if ($j(this).val()!="") {
                    return $j(this).val();
                }
            }).get();
        var vals = values.toString().split(",");
        var vals2 = values2.toString().split(",");
        var drugQ = vals2.toString().split(",");
        var drugid = $j("input[title='drug']").map(function () {
                if ($j(this).is(':checked')) {
                    return $j(this).val().substring(0,$j(this).val().indexOf('|'));
                }
            }).get();
        var drugs = (drugid).toString().split(",");
        var ans=checkHivRegimen(drugid);
        if(ans==true)  {
            var regimens=regimenFilter(drugid);
            var regimenCode= regimens[0];
            var regimenName=regimens[1];
            var val= checkHivPedsForm(numbers);
            if(val==true){
                var size = vals.length;
                var json = {};
                for (i = 0; i < size; i++) {
                    json[vals[i]] = drugQ[i];
                }
                $j.ajax({
                    type:"GET",
                    url:"dispense.form",
                    data:{drugCheck:JSON.stringify(json) },
                    dataType:"json",
                    success:function (result) {
                        if (result.toString() == 'true') {
                            var fields = $j("#hivpedsform").serializeArray();
                            $j.ajax({
                                type:"POST",
                                url:"hivPedsProcessor.form?"+ "regimenCode=" + regimenCode+"&regimenName="+regimenName,
                                data:{values:JSON.stringify(fields) },
                                dataType:"json",
                                beforeSend:function (x) {
                                    if (x && x.overrideMimeType) {
                                        x.overrideMimeType("application/j-son;charset=UTF-8");
                                    }
                                },
                                success:function () {
                                    fnShow();
                                    $j("#dispenseform").dialog("close");
                                    RefreshTable("#tunits");
                                    $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                                    $j("#dataDiv").show("slow");//
                                    $j("#dataDiv").delay(5000).hide("slow");
                                }
                            });
                        }
                        else {
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }
                    }
                });
            }
        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "Invalid regimen selection !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open");
        }
    }
    else{
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    return false;
});
/*Psychiatry form  on submit function*/
$j("form#psychiatryform").unbind('submit').submit(function () {
    var v= validatePsychiatryForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")     }
    else if(message.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1){
        var values = $j("select[name='ObsDrugDose*MEDICATION DOSAGE (DRUG DOSE)|1900#5']")
            .map(
            function () {

                if ($j(this).val()!="null") {



                    return $j(this).val().substring($j(this).val().indexOf("#") + 1);
                }

            }).get();

        var values2 = $j("input[name='QD']").map(
            function () {
                return $j(this).val();
            }).get();
        var vals = values.toString().split(",");
        var vals2 = values2.toString().split(",");

        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {

            if(vals2[i].length==0)
                json[vals[i]] = vals2[i+1];
            else
                json[vals[i]] = vals2[i];



        }

        var dataString = $j("#psychiatryform").serialize();
        var fields = $j("#psychiatryform").serializeArray();

        var dataString = $j("#psychiatryform").serialize();
        var fields = $j("#psychiatryform").serializeArray();

        $j.ajax({
            type:"GET",
            url:"dispense.form",

            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {

                if (result.toString() == 'true') {
                    $j.ajax({
                        type:"POST",
                        url:"psychiatryProcessor.form",

                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {

                            $j("#dispenseform").dialog("close");

                            RefreshTable("#tunits");

                            $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                            $j("#dataDiv").show("slow");//
                            $j("#dataDiv").delay(5000).hide("slow");

                            fnShow();
                        }
                    });

                }
                else {


                    $j("#errorDialog").empty();

                    $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');


                    $j("#errorDialog").dialog("open");
                }

            }
        });

    }
    else{

        $j("#errorDialog").empty();

        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');


        $j("#errorDialog").dialog("open")
    }
    return false;
});
$j("form#tuberculosisform").unbind('submit').submit(function ()
{     var v= validateTBCard();
    if(v==2)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "No drug selected for dispensing \n"+ '</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==0)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0)
    {
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else  if(v==1)
    {
        //
        var numbers="";
        var values = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')){
                numbers+= $j(this).val().substring($j(this).val().indexOf('|')+1)+",";
                return $j(this).val().substring($j(this).val().indexOf('|')+1);
            }
        }).get();
        var drugid = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')) {
                return $j(this).val().substring(0,$j(this).val().indexOf('|'));
            }
        }).get();
        var values2 = $j("input[title='Dispensed']").map(
            function () {
                if($j(this).is(':checked'))
                {
                    return $j(this).val();
                }
                else if($j(this).val()!="" && ($j(this).attr("name")=="otherd") )
                {
                    return $j(this).val();
                }
            }).get();
        var vals = values.toString().split(",");
        var vals2 = values2.toString().split(",");
        var drugs = drugid.toString().split(",");
        var qnty = vals2.toString().split(",");
        var ids = vals.toString().split(",");
        var size = vals.length;
        var json = {};
                for (i = 0; i < size; i++)
                {
                    json[ids[i]] = qnty[i];
                }
                $j.ajax({
                    type:"GET",
                    url:"dispense.form",
                    data:{drugCheck:JSON.stringify(json) },
                    dataType:"json",
                    success:function (result){
                        if (result.toString() == 'true'){
                            var fields = $j("#tuberculosisform").serializeArray();
                            $j.ajax({
                                type:"POST",
                                url:"tuberculosisProcessor.form?",
                                data:{values:JSON.stringify(fields) },
                                dataType:"json",
                                beforeSend:function (x) {
                                    if (x && x.overrideMimeType) {
                                        x.overrideMimeType("application/j-son;charset=UTF-8");
                                    }
                                },
                                success:function () {
                                    fnShow();
                                    $j("#dispenseform").dialog("close");
                                    RefreshTable("#tunits");
                                    $j('#dataDiv .loc').replaceWith("<div id='red'>Data saved<div>");
                                    $j("#dataDiv").show("slow");//
                                    $j("#dataDiv").delay(5000).hide("slow");
                                }
                            });
                        }
                        else {
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }

                    }
                });
    }
    return false;
});

//$j('#tunits').delegate('tbody td a','click', function () {
$j('#tunits tbody td a').live('click', function() {
//        alert("ok");
    var nTr = this.parentNode.parentNode;
//        getData(nTr);
    var aData = uoTable.fnGetData(nTr);
    var e = document.getElementById("formVal");
    var strForm = e.options[e.selectedIndex].value;



    $j.ajax({
        type:"GET",
        url:"dispense.form?encounterDetails=" + aData[1],

        data:{values:getArray("#hivform") },
        dataType:"text",
        success:function (result) {
            $j("#encountform").empty();

            $j(result).appendTo('#encountform');



            $j("#encountform").dialog("open");


        }
    });



    return false;

});

function RefreshTable(tableId) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
$j("#dispenseform").dialog({
    autoOpen:false,
    height:600,
    width:1300,
    cache:false,
    modal:true
});
$j("#encountform").dialog({

    title:"Patient",
    autoOpen:false,
    height:400,
    width:600,
    cache:false,
    modal:true
});
function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth()
        + (12 * (date2.getFullYear() - date1.getFullYear()));
}

function getDrugDispense(drug) {

    $j.getJSON("drugDetails.form?drop=drug&id=" + drug, function (result) {

        $j.each(result, function (index, value) { //bincard"stateList

        });

    });

}

function getDataDispense(data, pid, formid) {
    if (formid.substring(0, 7) == "General") {       // ...
        $j("#dispenseform").dialog("open");
        $j("#generalform").empty();
        $j("#generalform").buildForm(JSON.parse(data));
        $j.getScript("" + jQuery.Page.context + "moduleResources/pharmacy/jspharmacy/generalFormScript.js", function () {});
        jQuery.Pid = {Pi:pid };
        $j.getJSON("dispense.form?Pid=" + pid, function (result) {
            $j.each(result, function (index, value) { //bincard"stateList
            oFormObject = document.forms['generalform'];
            oFormObject.elements["patient|1#1"].value = value;
        });
        });
        $j(".ui-dform-tr input").attr("style", "width: 100px;");
        var oFormObject = document.forms['generalform'];
        oFormObject.elements["Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "Psychia") {
        // ...
        $j('#hivAdultOiForm').empty();
        $j("#dispenseform").dialog("open");
        $j("#hivform").empty();
        $j('#tuberculosisform').empty();
        $j("#psychiatryform").empty();
        $j("#psychiatryform").empty();
        $j("#hivpedsform").empty();
        $j("#cadiovascularform").empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j("#psychiatryform").buildForm(JSON.parse(data));
        $j.getScript("" + jQuery.Page.context+ "moduleResources/pharmacy/jspharmacy/psychiatryformData.js",function () { });
        $j.getJSON("dispense.form?Pid=" + pid, function (result) {$j.each(result, function (index, value)
        { //bincard"stateList
            oFormObject = document.forms['psychiatryform'];
            oFormObject.elements["Enc1*patient|1#1"].value = value;
        });
        });
        $j(".ui-dform-tr input").attr("style", "width: 100px;");
        var oFormObject = document.forms['psychiatryform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
        $j('#psychiatryform').delegate('input', 'focus',
            function () {
                $j(this).closest('.ui-dform-tr').css('background-color', '#dfdfdf');
            }).delegate('input', 'blur', function () {
                $j(this).closest('.ui-dform-tr').css('background-color', 'white');
            });

    }
    else if (formid.substring(0, 7) == "Barcode") {
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j('#tuberculosisform').empty();
        $j("#psychiatryform").buildForm(JSON.parse(data));
        $j.getScript("" + jQuery.Page.context+ "moduleResources/pharmacy/jspharmacy/barcodeFormScript.js",function(){});
        $j.getJSON("dispense.form?Pid=" + pid, function (result)
        {$j.each(result, function (index, value){
            oFormObject = document.forms['psychiatryform'];
            oFormObject.elements["patient|1#1"].value = value;
        });
        });
        $j(".ui-dform-tr input").attr("style", "width: 100px;");
        var oFormObject = document.forms['psychiatryform'];
        oFormObject.elements["Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "Cadiova") {
        eval('var obj=' + data);
        formType="CADIOVASCULAR";
        $j('#hivAdultOiForm').empty();
        $j('#tuberculosisform').empty();
        $j('#cadiovascularform').empty();
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#hivform").empty();
        $j("#hivpedsform").empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j("#hivform").empty();
        $j('#cadiovascularform').jForm(obj);
        $j.getScript("" + jQuery.Page.context+ "moduleResources/pharmacy/jspharmacy/cadiovascularData.js",function () {});
        $j.getJSON("dispense.form?Pid=" + pid, function (result)         {
            $j.each(result, function (index, value) {
                oFormObject = document.forms['cadiovascularform'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;
            });
        });
        var oFormObject = document.forms['cadiovascularform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "Tubercu") {
        eval('var obj=' + data);
        formType="tuberculosisform";
        $j('#tuberculosisform').empty();
        $j('#hivAdultOiForm').empty();
        $j('#cadiovascularform').empty();
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#hivform").empty();
        $j("#hivpedsform").empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j("#hivform").empty();
        $j('#tuberculosisform').jForm(obj);
        $j.getScript("" + jQuery.Page.context+ "moduleResources/pharmacy/jspharmacy/tuberculosis.js",function () {});
        $j.getJSON("dispense.form?Pid=" + pid, function (result)         {
            $j.each(result, function (index, value) {
                oFormObject = document.forms['tuberculosisform'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;
            });
        });
        var oFormObject = document.forms['tuberculosisform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "AdultHi"){
        eval('var obj=' + data);
        formType="ADULTHIV";
        $j('#hivAdultOiForm').empty();
        $j('#tuberculosisform').empty();
        $j("#cadiovascularform").empty();
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#hivform").empty();
        $j("#hivpedsform").empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j('#hivform').jForm(obj);
        $j.getScript("" + jQuery.Page.context + "moduleResources/pharmacy/jspharmacy/hivformData.js",function(){});
        $j.getJSON("dispense.form?Pid=" + pid, function (result)
        {
            $j.each(result, function (index, value) {
                oFormObject = document.forms['hivform'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;
            });
        });
        var oFormObject = document.forms['hivform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "AdultOi") {
        eval('var obj=' + data);
        $j("#dispenseform").dialog("open");
        $j('#tuberculosisform').empty();
        $j("#psychiatryform").empty();
        $j("#hivAdultOiForm").empty();
        $j("#hivform").empty();
        $j("#hivpedsform").empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j('#hivAdultOiForm').jForm(obj);
        $j.getScript("" + jQuery.Page.context + "moduleResources/pharmacy/jspharmacy/hivAdultOiFormData.js",function(){});
        $j.getJSON("dispense.form?Pid=" + pid, function (result)
        {
            $j.each(result, function (index, value) {
                oFormObject = document.forms['hivAdultOiForm'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;
            });
        });
        var oFormObject = document.forms['hivAdultOiForm'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 7) == "Pediatr") {
        eval('var obj=' + data);
        formType="PEDIATRICARV";
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j('#tuberculosisform').empty();
        $j("#hivpedsform").empty();
        $j("#cadiovascularform").empty();
        $j("#psychiatryform").empty();
        $j('#hivform').empty();
        $j('#hivAdultOiForm').empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j('#hivpedsform').jForm(obj);
        $j.getScript("" + jQuery.Page.context+ "moduleResources/pharmacy/jspharmacy/hivPedsFormData.js",function(){});
        $j.getJSON("dispense.form?Pid=" + pid,function(result)
        { $j.each(result, function (index, value)
        {
            oFormObject = document.forms['hivpedsform'];
            oFormObject.elements["Enc1*patient|1#1"].value = value;
        });

        });
        var oFormObject = document.forms['hivpedsform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else if (formid.substring(0, 10) == "RevolvingA") {
        eval('var obj=' + data);
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j('#tuberculosisform').empty();
        $j("#hivpedsform").empty();
        $j("#cadiovascularform").empty();
        $j("#psychiatryform").empty();
        $j('#hivAdultOiForm').empty();
        $j('#hivform').empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j('#revolvingAdultform').jForm(obj);
        $j.getScript("" + jQuery.Page.context
            + "moduleResources/pharmacy/jspharmacy/revolvingAdultformData.js",
            function () {

            });
        $j.getJSON("dispense.form?Pid=" + pid, function (result) {

            $j.each(result, function (index, value) { //bincard"stateList

                oFormObject = document.forms['revolvingAdultform'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;

            });

        });
        var oFormObject = document.forms['revolvingAdultform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;


    } else if (formid.substring(0, 10) == "RevolvingP") {
        eval('var obj=' + data);
        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j('#tuberculosisform').empty();
        $j("#hivpedsform").empty();
        $j("#cadiovascularform").empty();
        $j("#psychiatryform").empty();
        $j('#hivAdultOiForm').empty();
        $j('#hivform').empty();
        $j("#revolvingPedsform").empty();
        $j("#revolvingAdultform").empty();
        $j('#revolvingPedsform').jForm(obj);
        $j.getScript("" + jQuery.Page.context
            + "moduleResources/pharmacy/jspharmacy/revolvingPedsformData.js",
            function () {

            });
        $j.getJSON("dispense.form?Pid=" + pid, function (result) {
            $j.each(result, function (index, value) { //bincard"stateList
                oFormObject = document.forms['revolvingPedsform'];
                oFormObject.elements["Enc1*patient|1#1"].value = value;

            });

        });
        var oFormObject = document.forms['revolvingPedsform'];
        oFormObject.elements["Enc2*Patient_id|2#2"].value = pid;
    }
    else {

    }

}

$j(".month1").live('click', function () {

    if ($j(this).is(":checked")) {
        var group = "input:checkbox[name='" + $j(this).attr("name") + "']";
        $j(group).attr("checked", false);
        $j(this).attr("checked", true);
    } else {
        $j(this).attr("checked", false);
    }
});
$j(".dispensed1").live('click', function () {

    if ($j(this).is(":checked")) {
        var group = "input:checkbox[name='" + $j(this).attr("name") + "']";
        $j(group).attr("checked", false);
        $j(this).attr("checked", true);
    } else {
        $j(this).attr("checked", false);
    }
});
$j(".radios1").live('click', function () {
    if ($j(this).is(":checked")) {
        $j(this).attr("checked", false);
    }
    else{
        $j(this).attr("checked", true);
    }
});
$j("#row1ma").live('click', function () {

    if ($j(this).is(":checked")) {
        $j(this).attr("checked", true);

    }
    else{
        $j(this).attr("checked", false);

    }

});