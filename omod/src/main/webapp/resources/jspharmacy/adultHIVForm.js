$j("#align_left").empty();
$j("#align_right").empty();
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
$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j("#nextvisit").datepicker();
var pRegimen;
var pRegimenCode;
var cRegimen;
var patientEncountersDatatable;
$j("#dataSection").hide();
var drugConcepts = [
    [6467],
    [630,631],
    [797,628,631],
    [6964],
    [1400,633],
    [792],
    [6965,631],
    [630,633],
    [630,814],
    [1400,631],
    [1400,797],
    [6965,633],
    [6965,814],
    [630,794],
    [630,6159],
    [1400,794],
    [1400,6159],
    [6965,794],
    [630],
    [797,628],
    [6965],
    [625,628],
    [802,628],
    [1400],
    [628,814,633]
];
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j.each(result,function (index, value) {
        $j("#adultHIVFormDose1,#adultHIVFormDose2").append($j("<option></option>").attr("value",index).text(value));
    });
});

$j("input[name='dispenseForm_medication']").live("focus", function () {
    $j(this).autocomplete({
        source:function (request, response) {
            dataString = "searchDrug=" + request.term;
            $j.getJSON("drugBincard.form?drop=dispenseDrug&" + dataString, function (result) {
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
            $j(this).closest('tr').addClass('display');
            var val =ui.item.value;
            var json = {drugName:val};
            var itemID=$j(this).attr('id');
            var idExtract=itemID.substring(itemID.indexOf("_")+1);
            $j.ajax({
                type:"GET",
                url:"stockInventory.form?checkBoolean=checkBoolean",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (result) {
                    if (result.toString() == 'false'){
                        $j("#errorDialog").empty();
                        $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                        $j("#errorDialog").dialog("open");
                    }
                }
            })

            $j.ajax({
                type:"GET",
                url:"stockInventory.form?select=drugConcept",
                data: { "jsonDrugObject" :JSON.stringify(json) },
                dataType:"json",
                success:function (data) {
                    document.getElementById('h_'+idExtract).value=data.aaData;
                }
            })
        }
    });
});
$j("input[name='dispenseForm_medication']").live("blur", function () {
    if($j(this).val() ==''){
        $j(this).closest('tr').removeClass('display');
        var itemID=$j(this).attr('id');
        var idExtract=itemID.substring(itemID.indexOf("_")+1);
        document.getElementById('h_'+idExtract).value="";
    }
})
$j("input[title='drug']").live('click',function(){
    if($j(this).attr('checked')==false){
        $j(this).closest('tr').removeClass('display');
        $j(this).closest('tr').removeClass('viewRow');
    }
    if($j(this).attr('checked')==true){
        $j(this).closest('tr').addClass('display');
        $j(this).closest('tr').addClass('viewRow');
        var values = $j(this).map(function () {
            return $j(this).val().substring($j(this).val().indexOf('|')+1);

        }).get();
        var values2 =$j(this).map(function () {
            return $j(this).val().substring($j(this).val().indexOf('|')+1);
        }).get();
        var vals = values.toString().split(",");
        var drugQ = values2.toString().split(",");
        var size = vals.length;
        var json = [];
        for (i = 0; i < size; i++) {
          var drugPropertiesObject={};
          drugPropertiesObject[vals[i]] = drugQ[i];
          json.push(drugPropertiesObject);
        }
        $j.ajax({
                    type:"POST",
                    url:"drugBatchHasBeenSet.form",
                    data:{drugCheck:JSON.stringify(json) },
                    dataType:"json",
                    success:function (result){
                        if (result.toString() == 'false'){
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or not enough quantity in store !!!!!" + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }
                    }
                })

    }
});
$j("#patientId").autocomplete({
      search:function () {
           $j(this).addClass('working');
       },
    source:function (request, response) {
        $j.getJSON("patientSearch.form?patientSearch=" + request.term, function (result) {
         $j("#patientId").removeClass('working');
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
        var patient=ui.item.value;
        var pharmacyEncounterPropeties={};
        $j.ajax({
            type:"GET",
            url:"patientLastName.form?patientToFind="+patient,
            async:false,
            dataType:"json",
            success:function (result) {
                document.getElementById("patientName").value=result;
                $j("#currentRegimen").val()=="";
                 $j.ajax({
                            type:"GET",
                            url:"getPatientSummaryDetails.form?patientUUID="+patient,
                            async:false,
                            dataType:"json",
                            success:function(result){
                            if(result ==""){
                                 pRegimen="";
                                 pRegimenCode="";
                                 $j("#currentRegimen").val("Not given");
                            }
                            else{
                                pharmacyEncounterPropeties= result.toString().split(",");
                                 pRegimen=pharmacyEncounterPropeties[0];
                                 pRegimenCode=pharmacyEncounterPropeties[0];
                                 $j("#currentRegimen").val(pharmacyEncounterPropeties[0]);

                                var currentRegimen=pharmacyEncounterPropeties[0];
                                var lastVisitDate=pharmacyEncounterPropeties[1];
                                var nextVisitDate= pharmacyEncounterPropeties[2];
                                var numberOfDaysToStockOut= pharmacyEncounterPropeties[3];
                                var remainingStock= pharmacyEncounterPropeties[5];
                                 $j("#align_left").append("Current Regimen: "+currentRegimen+"</br>");
                                 $j("#align_left").append("last Visit Date: "+lastVisitDate+"</br>");
                                 $j("#align_right").append("next Visit Date: "+nextVisitDate+"</br>");
                                 $j("#align_right").append("Days To Next Visit: "+numberOfDaysToStockOut+"</br>");

                            }

                            }
                        })

                $j("#dataSection").show();
            }
        });
            patientEncountersDatatable=$j('#patientEncounters').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bServerSide:true,
            bProcessing:true,
            sAjaxSource:"patientEncountersByIdentifier.form?identifier=" +patient,
            "fnServerData":fnDataTablesPipeline2,
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                var htm="<ul>";
                if(aData[6]=="Edit"){
                    htm += '<li> <a href="#"  id="edit">Edit</a></li>';
                }
                htm += '</ul>';
                $j('td:eq(7)', nRow).html(htm);
                return nRow;
            } ,
            "aoColumnDefs":[
            {
               "bVisible":false,
               "aTargets":[ 0 ]
             }
               ]
              });
              patientEncountersDatatable.fnDraw();

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
$j("input[type='radio']").click(function()
{
    var previousValue = $j(this).attr('previousValue');
    var name = $j(this).attr('name');

    if (previousValue == 'checked')
    {
        $j(this).removeAttr('checked');
        $j(this).attr('previousValue', false);
    }
    else
    {
        $j("input[name="+name+"]:radio").attr('previousValue', false);
        $j(this).attr('previousValue', 'checked');
    }
});

$j('#nextvisit').change(function () {
    var givenDate = document.getElementById("nextvisit").value;
    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();
    t1 = givenDate;
    t2 = day + "/" + month + "/" + year;
    var one_day = 1000 * 60 * 60 * 24;
    var one_week = 1000 * 60 * 60 * 24 * 7;
    var one_month = 1000 * 60 * 60 * 24 * 7;
    var x = t1.split("/");
    var y = t2.split("/");
    dateGiven = new Date(x[2], (x[0] - 1), x[1]);
    var date2 = new Date(y[2], (y[1] - 1), y[0]);
    var dated = new Date(x[2], (x[1] - 1), x[0]);
    var month1 = x[1] - 1;
    var month2 = y[1] - 1;
    _Diff = Math.ceil((dateGiven.getTime() - date2.getTime())
        / (one_day));
    quantity = _Diff;
    var inputTags = document.getElementsByTagName('INPUT');
    document.getElementById("noofmonths").value = _Diff + 2;

});

$j("#noofmonths").change(function () {
    var givenDays = document.getElementById("noofmonths").value;
    quantity = givenDays;
    var date = new Date();
    var d = addays(date, givenDays);
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1;
    var curr_year = d.getFullYear();
    var dateFinal = curr_month + "/" + curr_date + "/" + curr_year;

    document.getElementById("nextvisit").value = dateFinal;
});

function addays(myDate, days) {
    return new Date(myDate.getTime() + days * 24 * 60 * 60 * 1000);
}
/*
$j("#prescriber").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },
    source:function (request, response) {
        dataString = "q=" + request.term;
        $j.getJSON("dispense.form?" + dataString, function (result) {
            $j("#prescriber").removeClass('working');
            response($j.each(result, function (index, item) {
                return {
                    label:item,
                    value:item
                }
            }));
        });
    },
    minLength:2,
    select:function (event, ui) {
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
*/
function checkHivForm(val,reg){
   /* if (!$j("#regimenchange").is(':checked') && (!$j("#arvtype1").is(':checked'))){
        $j.ajax({
            type:"GET",
            url:"currentPatientRegimen.form?patientIdentifier="+$j("#patientId").val(),
            async:false,
            success:function (result) {
                regimenVals=JSON.parse(result);
                regimenN=regimenVals.regimenNam;
                regimenC=regimenVals.regimenCod;

            }
        });

    } */
    cRegimen=val;
    if(reg !=pRegimen && pRegimen !="" && reg !="OI")
    {

        if($j("#regimenchange").is(':checked') || pRegimen =="undefined"){
            return true;
        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "current regimen is different from previous regimen , must select regimen change\n"+ '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open")
            return false
        }
    }
    else if(reg !=pRegimen && pRegimen =="")
    {
        if(($j("#arvtype1").is(':checked') ) || ($j("#arvtype5").is(':checked') )){
            return true;
         }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "Choose either Regimen initiation or continue regimen\n"+ '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open")
            return false
        }

    }
    else {
        return true;
    }
}
function validateHivForm(){
    var num=1;
    var regimenChangeIsChecked=0;
    message="";
    if (!$j("#patienttype1").is(':checked')&&!$j("#patienttype2").is(':checked')&&!$j("#patienttype3").is(':checked'))
    {
        message+="Error:Atleast one patient type must be selected  "+"<br/>";
    }
    if ($j("#regimenchange").is(':checked')) {
        regimenChangeIsChecked=1;
        if ((!$j("#regimenchange1").is(':checked'))&&(!$j("#regimenchange2").is(':checked'))&&(!$j("#regimenchange3").is(':checked')))
        {
            message+="Error:Atleast one regimen change reason must be selected "+"<br/>";
        }
    }
    if(regimenChangeIsChecked==0){
    if ((!$j("#arvtype1").is(':checked'))&&(!$j("#arvtype2").is(':checked'))&&(!$j("#arvtype3").is(':checked'))&&(!$j("#arvtype4").is(':checked'))&&(!$j("#arvtype5").is(':checked')))
    {
        message+="Error:Atleast one ARV type must be selected "+"<br/>";
    }
    }
    if(regimenChangeIsChecked==1){
        if (($j("#arvtype1").is(':checked')) || ($j("#arvtype2").is(':checked')) || ($j("#arvtype3").is(':checked')) || ($j("#arvtype4").is(':checked')) || ($j("#arvtype5").is(':checked')))
        {
            message+="Error:When changing patient regimen don't select any ARV type"+"<br/>";
        }
    }
    if (($j("#arvtype1").is(':checked')) || ($j("#arvtype2").is(':checked')) || ($j("#arvtype3").is(':checked')) || ($j("#arvtype4").is(':checked')) || ($j("#arvtype5").is(':checked')))
    {
        if (($j("#arvtype1").is(':checked'))){
            if (($j("#arvtype2").is(':checked')) || ($j("#arvtype4").is(':checked')) || ($j("#arvtype5").is(':checked'))){
                message+="Error:Make sure to select the ARV types correctly "+"<br/>";
            }
        }
        if (($j("#arvtype2").is(':checked'))){
            if (($j("#arvtype1").is(':checked')) || ($j("#arvtype4").is(':checked'))){
                message+="Error:Make sure to select the ARV types correctly "+"<br/>";
            }
        }
        if (($j("#arvtype3").is(':checked'))){
            if (($j("#arvtype4").is(':checked'))){
                message+="Error:Make sure to select the ARV types correctly "+"<br/>";
            }
        }
        if (($j("#arvtype4").is(':checked'))){
            if (($j("#arvtype1").is(':checked')) || ($j("#arvtype2").is(':checked')) || ($j("#arvtype3").is(':checked'))|| ($j("#arvtype5").is(':checked'))){
                message+="Error:Make sure to select the ARV types correctly "+"<br/>";
            }
        }

    }

    if ($j("#datePicker").val()=="") {
        message+="Error:Give Encounter date "+"<br/>";
    }
    if ($j("#nextvisit").val()=="") {
        message+="Error:No Next visit date "+"<br/>";
    }/*
    if ($j("#prescriber").val()=="") {
        message+="Error:Give the  prescriber "+"<br/>";

    }*/
    return num;
}

var regimen ='';
var splicedRegimen=''
function checkHivRegimen(val)
{
    regimen=val;
    splicedRegimen=val.slice(0);
    var septrinPosition=splicedRegimen.indexOf('916');
    var dapsonePosition=splicedRegimen.indexOf('92');
    var isoniazidPosition=splicedRegimen.indexOf('656');
    var isRegimenValid = false ;
    for (numbersCounter = 0 ;
         numbersCounter < drugConcepts.length ;
         numbersCounter ++ )
    {
        if(septrinPosition>=0 || dapsonePosition>=0 || isoniazidPosition>=0){
            if(septrinPosition>=0)
            {
                splicedRegimen.splice(septrinPosition,1);
            }
            if(dapsonePosition>=0)
            {
                splicedRegimen.splice(dapsonePosition,1);
            }
            if(isoniazidPosition>=0)
            {
                splicedRegimen.splice(isoniazidPosition,1);
            }

        }
        currNumber = drugConcepts[numbersCounter] ; // curr array
        lengthOfCurrentRegimenUnderTest = currNumber.length ;
        if(lengthOfCurrentRegimenUnderTest != splicedRegimen.length)
            continue ;
        for(regimenCounter = 0 ;
            regimenCounter < splicedRegimen.length ;
            regimenCounter++ ) {
            if(!contains(splicedRegimen[regimenCounter], currNumber)) {
                isRegimenValid = false ;
                break ;
            }

            isRegimenValid = true ;
        }
        if(isRegimenValid)
            break ;

    }
    if(regimen>splicedRegimen){
        isRegimenValid = true ;
    }
    var regimenVals;
    var regimenN;
    var regimenC;
    if (!$j("#arvtype1").is(':checked') && (!$j("#arvtype2").is(':checked')) && ($j("#arvtype3").is(':checked'))&& (!$j("#arvtype4").is(':checked')) && (!$j("#arvtype5").is(':checked')) ){
        $j.ajax({
            type:"GET",
            url:"currentPatientRegimen.form?patientIdentifier="+$j("#patientId").val(),
            async:false,
            success:function (result) {
                regimenVals=JSON.parse(result);
                regimenN=regimenVals.regimenNam;
                regimenC=regimenVals.regimenCod;
                if(result.toString() !=""){
                    isRegimenValid = true ;
                }
            }
        });
    }
    return isRegimenValid ;
}

function contains(needle, haystack) {

    for (i = 0 ; i < haystack.length; i++)
    {
        if(needle == haystack[i])
            return true ;
    }

    return false ;
}
function regimenFilter(val){
    regimen=val;
    splicedRegimen=val;
    var septrinPosition=regimen.indexOf('916');
    var dapsonePosition=regimen.indexOf('92');
    var isoniazidPosition=regimen.indexOf('656');
    var positionOfEquity = 1000 ;
    var regimenC='';
    var regimenN='';
    var regimenVals;

    for (numbersCounter = 0 ; numbersCounter < drugConcepts.length ;numbersCounter ++ )
    {
        if(septrinPosition>=0 || dapsonePosition>=0 || isoniazidPosition>=0){
            if(septrinPosition>=0)
            {
                splicedRegimen.splice(septrinPosition,1);
            }
            if(dapsonePosition>=0)
            {
                splicedRegimen.splice(dapsonePosition,1);
            }
            if(isoniazidPosition>=0)
            {
                splicedRegimen.splice(isoniazidPosition,1);
            }

        }
        currNumber = drugConcepts[numbersCounter] ; // curr array
        lengthOfCurrentRegimenUnderTest = currNumber.length ;
        if(lengthOfCurrentRegimenUnderTest != splicedRegimen.length)
            continue ;
        var sortedCurrNumber=currNumber.sort();
        var sortedSPlicedRegimen=splicedRegimen.sort();
        if(sortedCurrNumber.toString()==sortedSPlicedRegimen.toString()){
            positionOfEquity=numbersCounter;
            break;
        }

    }
    if(positionOfEquity < 3){
        if($j("#patienttype1").is(':checked')){
            regimenN='AZT/3TC/NVP';
            regimenC='PM3';
        }
        else{
            regimenN='AZT/3TC/NVP';
            regimenC='AF1A';
        }
    }
    else if(positionOfEquity < 5) {
        regimenN='TDF/3TC/EFV';
        regimenC='AF2B';
    }
    else if(positionOfEquity < 7) {
        regimenN='d4T/3TC/NVP';
        regimenC='AF3A';
    }
    else if(positionOfEquity ==7) {
        if($j("#patienttype1").is(':checked')) {
            regimenN='AZT/3TC/EFV';
            regimenC='PM4';
        }
        else{
            regimenN='AZT/3TC/EFV';
            regimenC='AF1B';
        }
    }
    else if(positionOfEquity ==8) {
        regimenN='AZT/3TC/ABC';
        regimenC='AF1C';
    }
    else if(positionOfEquity ==9) {
        if($j("#patienttype1").is(':checked')) {
            regimenN='TDF/3TC/NVP';
            regimenC='PM6';
        }
        else if($j("#patienttype3").is(':checked')) {
            regimenN='TDF/3TC/NVP';
            regimenC='PA3B';
        }
        else{
            regimenN='TDF/3TC/NVP';
            regimenC='AF2A';
        }
    }
    else if(positionOfEquity ==10) {
        regimenN='TDF/3TC/AZT';
        regimenC='AF2C';
    }
    else if(positionOfEquity ==11) {
        regimenN='d4T/3TC/EFV';
        regimenC='AF3B';
    }
    else if(positionOfEquity ==12) {
        regimenN='d4T/3TC/ABC';
        regimenC='AF3C';
    }
    else if(positionOfEquity ==13) {
        if($j("#patienttype1").is(':checked')) {
            regimenN='AZT/3TC/LPV/r';
            regimenC='PM5';
        }
        else if($j("#patienttype3").is(':checked')){
            regimenN='AZT/3TC/LPV/r';
            regimenC='PA1B';
        }
        else{
            regimenN='AZT/3TC/LPV/r';
            regimenC='AS1A';
        }
    }
    else if(positionOfEquity ==14) {
        regimenN='AZT/3TC/ATV/r';
        regimenC='AS1B';
    }
    else if(positionOfEquity ==15) {
        regimenN='TDF/3TC/LPV/r';
        regimenC='AS2A';
    }
    else if(positionOfEquity ==16) {
        regimenN='TDF/3TC/ATV/r';
        regimenC='AS2C';
    }
    else if(positionOfEquity ==17) {
        if($j("#patienttype3").is(':checked')){
            regimenN='d4T/3TC/LPV/r';
            regimenC='PA2B';
        }
        else{
            regimenN='d4T/3TC/LPV/r';
            regimenC='AS4A';
        }

    }
    else if(positionOfEquity==18 || positionOfEquity==19){
        regimenN='AZT/3TC';
        regimenC='PA1A';
    }
    else if (positionOfEquity==20 || positionOfEquity==21){
        regimenN='D4T/3TC';
        regimenC='PA2A';
    }
    else if(positionOfEquity==22 || positionOfEquity==23){
        regimenN='TDF/3TC';
        regimenC='PA3A'
    }
    else if(positionOfEquity==24){
        regimenN='ABC/3TC/EFV';
        regimenC='PA1B'
    }
     if(regimen.length>splicedRegimen.length && splicedRegimen.length ==0)
         {
             regimenNam='OI';
             regimenCod='OI';
         }
    return [regimenC,regimenN];
}

function processForm(){
    var drugIsChecked;
    var quantityIsFilled;
    var requestIsFilled;
    var drugVSRequest=0;
    var drugVSQuantity=0;
    inputErrors="";
    $j('#adultHIVFormDiv').find('tr.viewRow').each(function(){
        drugIsChecked=0;
        requestIsFilled=0;
        quantityIsFilled=0;
        $j(this).find('td').each(function(){
            $j(this).find("input[title='drug']").each(function(){
                if($j(this).is(':checked')){
                    drugIsChecked=1;
                }
            });
            $j(this).find("input[title='Requested']").each(function(){
                if($j(this).is(':checked')){
                    requestIsFilled=1;
                }
            });
            $j(this).find("input[name='otherR']").each(function(){
                if($j(this).val() !=''){
                    requestIsFilled=1;
                }
            });
            $j(this).find("input[title='dispensed']").each(function(){
                if($j(this).is(':checked')){
                    quantityIsFilled=1;
                }
            });
            $j(this).find("input[name='Quantity']").each(function(){
                if($j(this).val() !=''){
                    quantityIsFilled=1;
                }
            });
        });

        if(drugIsChecked==1 && requestIsFilled==0){
            drugVSRequest=1;

        }
        if(drugIsChecked==1 && quantityIsFilled==0){
            drugVSQuantity=1;

        }
    });
    if(drugVSRequest==1){
        inputErrors+="Error:You have not selected/entered quantity prescribed for one or more drugs"+"<br/>";
    }
    if(drugVSQuantity==1){
        inputErrors+="Error:You have not selected quantity dispensed for one or more drugs"+"<br/>";
    }
    var otherDrugSelected;
    var otherDrugRequestSelected;
    var otherDrugQuantitySelected;
    var allSelectedCheck=0;
    $j('#adultHIVFormDiv').find('tr.otherDrug').each(function(){
        otherDrugSelected=0;
        otherDrugRequestSelected=0;
        otherDrugQuantitySelected=0;
        $j(this).find('td').each(function(){
            $j(this).find("input[name='dispenseForm_medication']").each(function(){
                if($j(this).val() !=''){
                    otherDrugSelected=1;
                }
            })
            $j(this).find("input[title='Requested']").each(function(){
                if($j(this).val() !=''){
                    otherDrugRequestSelected=1;
                }
            })
            $j(this).find("input[name='Quantity']").each(function(){
                if($j(this).val() !=''){
                    otherDrugQuantitySelected=1;
                }
            })
        })
        if(otherDrugSelected==1 && otherDrugRequestSelected==0){
            allSelectedCheck=1;
        }
        if(otherDrugSelected==1 && otherDrugQuantitySelected==0){
            allSelectedCheck=1;
        }
    });
    if(allSelectedCheck==1){
        inputErrors+="Error:You have not entered either quantity dispensed or quantity prescribed for one or more  of the extra drugs"+"<br/>";
    }
    var adultHIVData = [];
    $j('#adultHIVFormDiv').find('tr.display').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            $j(this).find('input').each(function(){
                var obj = {}
                if($j(this).is(':checkbox') || $j(this).is(':radio')){
                    if($j(this).is(':checked')){
                        var  input = $j(this);
                        var key = input.attr('name');
                        var val = input.val();
                        obj[key] = val;
                        rowObject.push(obj);
                    }
                }
                else{
                    var  input = $j(this);
                    var key = input.attr('name');
                    var val = input.val();
                    obj[key] = val;
                    rowObject.push(obj);
                }
            })
        });
        adultHIVData.push(rowObject);
    });
    var v= validateHivForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
        $j('<dl><dt></dt><dd >' + "Info: "  +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(inputErrors.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +inputErrors+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }
    else if(message.length>0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + " \n" +message+ '</dd></dl> ').appendTo('#errorDialog');
        $j("#errorDialog").dialog("open")
    }

    else  if(v==1){
        var drugNum="";
        var otherDrugID="";
        var otherDrugID2="";
        var  otherDrugIDArray=[];
        var  otherDrugIDArray2=[];
        var values = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')){
                drugNum+= $j(this).val().substring($j(this).val().indexOf('|')+1)+",";
                return $j(this).val().substring($j(this).val().indexOf('|')+1);
            }
        }).get();

        if($j('#h_19').val() !=""){
            otherDrugIDArray =$j('#h_19').map(function ()
            {    otherDrugID= $j('#h_19').val().substring($j('#h_19').val().indexOf('|')+1);
                return otherDrugID
            }).get();

        }
        if($j('#h_20').val() !=""){
            otherDrugIDArray2 =$j('#h_20').map(function ()
            {    otherDrugID2= $j('#h_20').val().substring($j('#h_20').val().indexOf('|')+1);
                return otherDrugID2
            }).get();

        }
        values=values.concat(otherDrugIDArray);
        values=values.concat(otherDrugIDArray2);

        var values2 = $j("input[title='dispensed'],input[title='qnty']").map(
            function () {
                if($j(this).is(':checked'))
                {
                    return $j(this).val();
                }
                else if($j(this).val()!="" && ($j(this).attr("name")=="Quantity") )
                {
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
        if(ans==true){
            var regimens=regimenFilter(drugid);
            var regimenCode= regimens[0];
            var regimenName=regimens[1];
            var regimenVals;
            if(regimenName=="OI" && pRegimen !="OI" && pRegimen !="" && pRegimen !="undefined"){
                regimenName=pRegimen;
                regimenCode=pRegimenCode;
            }
            if(regimenName==""){
                $j.ajax({
                    type:"GET",
                    url:"currentPatientRegimen.form?patientIdentifier="+$j("#patientId").val(),
                    async:false,
                    success:function (result) {
                        if(result.toString() !=""){
                        regimenVals=JSON.parse(result);
                        regimenName=regimenVals.regimenNam;
                        regimenCode=regimenVals.regimenCod;

                        }
                    }
                });
            }
            var val= checkHivForm(drugNum,regimenName);
            if(val==true){
                var size = vals.length;
                var json = [];
                for (i = 0; i < size; i++) {
                    var drugPropertiesObject={};
                    drugPropertiesObject[vals[i]] = drugQ[i];
                    json.push(drugPropertiesObject);
                }
                var regimenChanged=0;
              if(regimenName !=pRegimen && pRegimen !="" && regimenName !="OI"){
                 regimenChanged=1;
              }
                $j.ajax({
                    type:"GET",
                    url:"checkDrugAvailability.form",
                    data:{drugCheck:JSON.stringify(json) },
                    dataType:"json",
                    success:function (result) {
                        if (result.toString() == 'true') {
                            $j.ajax({
                                type:"POST",
                                url:"adultHIVProcessor.form?"+ "regimenCode=" + regimenCode+"&regimenName="+regimenName+"&regimenChanged="+regimenChanged,
                                data:{values:JSON.stringify(adultHIVData) },
                                dataType:"json",
                                success:function () {
                                    document.getElementById("adultHIVForm").reset();
                                    $j("#dataSection").hide();
                                    $j("#successDialog").empty();
                                    $j('<dl><dt></dt><dd >' +  "Dispensed successfuly" + '</dd></dl> ').appendTo('#successDialog');
                                    $j("#successDialog").dialog("open");
                                    setTimeout(function() {
                                        $j('#successDialog').dialog("close");
                                    }, 2000);
                                }
                            });
                        }
                        else {
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Error: " + "Either you have not set the batch no or you do not have enough quantity in stock for one or more drugs " + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }
                    }
                });
            }
        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Error: " + drugs+" Is an invalid regimen" + '</dd></dl> ').appendTo('#errorDialog');
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
}
$j("table").delegate("#patientEncounters tbody tr :last-child","click",function(){
    document.getElementById("adultHIVForm").reset();
    var tr = this.parentNode;
    trData=patientEncountersDatatable.fnGetData(tr);
    if(confirm("Are you sure you want to delete this encounter "+trData[3])){
    $j.ajax({
    type:"GET",
    url:"patientEncounterDetailsVoid.form?encounterUUID="+trData[0],
    async:false,
    success:function(result){
    }
})
}
})
