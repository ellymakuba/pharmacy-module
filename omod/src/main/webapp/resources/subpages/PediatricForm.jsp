<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head><title>Pediatric ARV Prescription form</title>
    <style type="text/css">
        .pediatricFormDivClass table tr td{
            border:1px solid white;
            background-color:#EBEBFF;
        }
        .align_right {
                    float: right;
                    width: 40%;
                    color:red;
                    font-weight:bold;
                    padding;10px;
                    text-align:right;
                    margin:10px;
                }
                .align_left {
                    float: left;
                    width: 40%;
                    color:red;
                    font-weight:bold;
                    padding;10px;
                    margin:10px;
                }
                #patientSummaryDashBoardPediatric{
                 width:100%;
                 margin-bottom:10px;
                 border:5px #AAAAAA;
                 border-style:outset;
                }
                .clear{
                   clear:both;
                }
    </style>
<script type="text/javascript">
var isPEPSelected=0;
var isPMTCTSelected=0;
var regimenChanged=0;
var regimenInitition=0;
var isOnlyOIRefill=0;
$j("#align_left").empty();
$j("#align_right").empty();
$j("#viewHistory").hide();
$j("#patientEncounters").hide();
var trData;
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
var code;
var name;
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
    [1400,9026],
    [1400,797],
    [6965,633],
    [6965,814],
    [630,794],
    [630,9026],
    [1400,794],
    [1400,6159],
    [6965,794],
    [630],
    [797,628],
    [6965],
    [625,628],
    [802,628],
    [1400],
    [628,814,631],
    [631,6679],
    [797,814,633],
    [814,628,633],
    [814,628,9026],
    [802,628,633],
    [802,628,631],
    [802,628,9026],
    [797,628,9026],
    [814,628,9026],
    [6679,633],
    [6679,9026]
];
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j.each(result,function (index, value) {
        $j("#pediatricFormDose1,#pediatricFormDose2").append($j("<option></option>").attr("value",index).text(value));
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

/* $j("#weight") .change(function () {
    $j(".viewRow").removeClass('viewRow').addClass('hideRow');
    $j(".hideRow").hide();

    $j("#drug4row1").removeClass('hideRow').addClass('viewRow');
    var quantityPrescribed = document.getElementById("weight").value;
    if(quantityPrescribed<6){
        $j("#drug1row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug7row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug8row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug9row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug10row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug11row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    else if(quantityPrescribed<10){
        $j("#drug1row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug7row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug8row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug9row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug10row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug11row2").removeClass('hideRow').addClass('viewRow');
        $j("#drug12row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row2").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    else if(quantityPrescribed<14){
        $j("#drug1row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug7row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug8row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug9row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug10row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug11row3").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row3").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    else if(quantityPrescribed<20){
        $j("#drug1row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug7row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug8row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug9row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug10row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug11row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row4").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    else if(quantityPrescribed<25){
        $j("#drug1row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug7row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug8row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug9row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug10row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug11row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row5").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    else if(quantityPrescribed>25){
        $j("#drug1row6").removeClass('hideRow').addClass('viewRow');
        $j("#drug2row6").removeClass('hideRow').addClass('viewRow');
        $j("#drug3row6").removeClass('hideRow').addClass('viewRow');
        $j("#drug5row6").removeClass('hideRow').addClass('viewRow');
        $j("#drug6row6").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed<5){
        $j("#drug20row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }

    if(quantityPrescribed>5 && quantityPrescribed<10){
        $j("#drug22row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>10 && quantityPrescribed<30){
        $j("#drug22row2").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>8 && quantityPrescribed<14){
        $j("#drug21row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>5 && quantityPrescribed<9){
        $j("#drug20row2").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>10 && quantityPrescribed<15){
        $j("#drug20row3").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>15 && quantityPrescribed<30){
        $j("#drug20row4").removeClass('hideRow').addClass('viewRow');
        $j("#drug21row2").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>10 && quantityPrescribed<11){
        $j("#drug12row2").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>11 && quantityPrescribed<14){
        $j("#drug12row3").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>14 && quantityPrescribed<17){
        $j("#drug12row4").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>17 && quantityPrescribed<20){
        $j("#drug12row5").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>20 && quantityPrescribed<60){
        $j("#drug12row6").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }

    if(quantityPrescribed>25 && quantityPrescribed<30){
        $j("#drug7row6").removeClass('hideRow').addClass('viewRow');
        $j("#drug13row6").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>30 && quantityPrescribed<40){
        $j("#drug7row7").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>30 || quantityPrescribed==30){
        $j("#drug13row7").removeClass('hideRow').addClass('viewRow');
        $j("#drug20row5").removeClass('hideRow').addClass('viewRow');
        $j("#drug21row3").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>25 || quantityPrescribed==25){
        $j("#drug14row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug15row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug16row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug17row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug18row1").removeClass('hideRow').addClass('viewRow');
        $j("#drug19row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>10){
        $j("#drug23row1").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>20){
        $j("#drug22row3").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>40){
        $j("#drug7row8").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
    if(quantityPrescribed>60){
        $j("#drug12row7").removeClass('hideRow').addClass('viewRow');
        $j(".viewRow").show();
    }
}); */
$j("input[title='drug']").live('click',function(){
    if($j(this).attr('checked')==false){
        $j(this).closest('tr').removeClass('display');
    }
    if($j(this).attr('checked')==true){
        $j(this).closest('tr').addClass('display');
        var drugIndex=$j(this).attr('name').substring($j(this).attr('name').indexOf('#')+1);
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
$j("#patientIdPediatricHIVForm").autocomplete({
    source:function (request, response) {
        $j.getJSON("patientSearch.form?patientSearch=" + request.term, function (result) {
            response($j.each(result, function (index, item) {
                return {
                    label:item,
                    value:item
                }
            }));
        });
    },
    minLength:7,
    select:function (event, ui) {
    $j("#viewHistory").show();
       var patient=ui.item.value;
       var patientNameAndAgeDetails={};
       var name;
       var age;
        $j.ajax({
            type:"GET",
            async:false,
            url:"patientLastName.form?patientToFind="+patient,
            success:function (result) {
                patientNameAndAgeDetails= result.toString().split(",");
                name=patientNameAndAgeDetails[0];
                age=patientNameAndAgeDetails[1];
                document.getElementById("patientName").value=name;
                document.getElementById("patientAge").value=age;
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
                              pRegimenCode=pharmacyEncounterPropeties[4];
                              $j("#currentRegimen").val(pharmacyEncounterPropeties[0]);
                              var currentRegimen=pharmacyEncounterPropeties[0];
                              var lastVisitDate=pharmacyEncounterPropeties[1];
                              var nextVisitDate= pharmacyEncounterPropeties[2];
                              var numberOfDaysToStockOut= pharmacyEncounterPropeties[3];
                              var remainingStock= pharmacyEncounterPropeties[5];
                              $j("#align_left").append("Current Regimen: "+currentRegimen+"</br>");
                              $j("#align_left").append("last Visit Date: "+lastVisitDate);
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
});*/

function checkHivPedsForm(val,reg,regChanged){
    cRegimen=val;
    if(reg !=pRegimen && pRegimen !="" && reg !="OI" && pRegimen !="OI")
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
    else if(pRegimen==reg && regChanged==1){
           $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "When you select regimen change ensure the current regimen is different from the previous regimen \n"+ '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open")
            return false
    }
    else if(reg !=pRegimen && pRegimen =="")
    {
        if(($j("#arvtype1").is(':checked') ) || ($j("#arvtype5").is(':checked')) || reg=="OI"){
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
function validateHivPedsForm(){
    var num=1;
    var regimenChangeIsChecked=0;
    message="";
    if (!$j("#patienttype1").is(':checked')&&!$j("#patienttype2").is(':checked')&&!$j("#patienttype3").is(':checked'))
    {
        message+="Error:Atleast one patient type must be selected  "+"<br/>";
    }
    if ($j("#patienttype3").is(':checked') && $j("#arvtype2").is(':checked')){
         message+="Post Exposure Prophylaxis cannot be dispensed as an ARV Refill "+"<br/>";
    }
    if ($j("#regimenchange").is(':checked')) {
        regimenChangeIsChecked=1;
        if ((!$j("#regimenchange1").is(':checked'))&&(!$j("#regimenchange2").is(':checked'))&&(!$j("#regimenchange3").is(':checked'))&&(!$j("#regimenchange4").is(':checked')))
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
        if (($j("#arvtype1").is(':checked')) || ($j("#arvtype4").is(':checked')) || ($j("#arvtype5").is(':checked')))
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
function validateHivRegimen(val)
{
    regimen=val;
    splicedRegimen=val.slice(0);
    var septrinPosition=splicedRegimen.indexOf('916');
    var dapsonePosition=splicedRegimen.indexOf('92');
    var isoniazidPosition=splicedRegimen.indexOf('656');
    var fluconazolePosition=splicedRegimen.indexOf('747');
        var acyclovirPosition=splicedRegimen.indexOf('732');
        var pyridoxinePosition=splicedRegimen.indexOf('766');
        var isRegimenValid = false ;
        for (numbersCounter = 0 ; numbersCounter < drugConcepts.length ;numbersCounter ++ )
        {
            if(septrinPosition>=0 || dapsonePosition>=0 || isoniazidPosition>=0 || fluconazolePosition>=0 || acyclovirPosition>=0 || pyridoxinePosition>=0){
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
                if(fluconazolePosition>=0){
                    splicedRegimen.splice(fluconazolePosition,1);
                }
                if(acyclovirPosition>=0){
                    splicedRegimen.splice(acyclovirPosition,1);
                }
                if(pyridoxinePosition>=0){
                    splicedRegimen.splice(pyridoxinePosition,1);
                }

            }
        currNumber = drugConcepts[numbersCounter] ; // curr array
        lengthOfCurrentRegimenUnderTest = currNumber.length ;
        if(lengthOfCurrentRegimenUnderTest != splicedRegimen.length)
            continue ;
        for(regimenCounter = 0 ;regimenCounter < splicedRegimen.length;regimenCounter++ ) {
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
    if(regimen.length==0){
        isRegimenValid = true ;
    }
    var regimenVals;
    var regimenN;
    var regimenC;
    if ((!$j("#arvtype1").is(':checked')) && (!$j("#arvtype2").is(':checked')) && ($j("#arvtype3").is(':checked'))&& (!$j("#arvtype4").is(':checked')) && (!$j("#arvtype5").is(':checked')) ){
        $j.ajax({
            type:"GET",
            url:"currentPatientRegimen.form?patientIdentifier="+$j("#patientIdPediatricHIVForm").val(),
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
    splicedRegimen=val.slice(0);
    var septrinPosition=regimen.indexOf('916');
    var dapsonePosition=regimen.indexOf('92');
    var isoniazidPosition=regimen.indexOf('656');
     var fluconazolePosition=splicedRegimen.indexOf('747');
        var acyclovirPosition=splicedRegimen.indexOf('732');
        var pyridoxinePosition=splicedRegimen.indexOf('766');
        var positionOfEquity = 1000;
        var regimenC='';
        var regimenN='';
        var regimenVals;
        var ARVDrugSelected=0;
        var OIDrugSelected=0;
        var noDrugSelected=0;

        for (numbersCounter = 0 ; numbersCounter < drugConcepts.length ;numbersCounter ++ )
        {
            if(septrinPosition>=0 || dapsonePosition>=0 || isoniazidPosition>=0 || fluconazolePosition>=0 || acyclovirPosition>=0 || pyridoxinePosition>=0){
                if(septrinPosition>=0)
                {
                    splicedRegimen.splice(septrinPosition,1);
                    OIDrugSelected=1;
                }
                if(dapsonePosition>=0)
                {
                    splicedRegimen.splice(dapsonePosition,1);
                    OIDrugSelected=1;
                }
                if(isoniazidPosition>=0)
                {
                    splicedRegimen.splice(isoniazidPosition,1);
                    OIDrugSelected=1;
                }
                if(fluconazolePosition>=0){
                    splicedRegimen.splice(fluconazolePosition,1);
                    OIDrugSelected=1;
                }
                if(acyclovirPosition>=0){
                    splicedRegimen.splice(acyclovirPosition,1);
                    OIDrugSelected=1;
                }
                if(pyridoxinePosition>=0){
                    splicedRegimen.splice(pyridoxinePosition,1);
                    OIDrugSelected=1;
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
            regimenNam='AZT/3TC/NVP';
            regimenCod='PM3';
        }
        else{
            regimenNam='AZT/3TC/NVP';
            regimenCod='CF1A';
        }
            ARVDrugSelected=1;
    }
    else if(positionOfEquity < 5) {
        regimenNam='TDF/3TC/EFV';
        regimenCod='AF2B';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity < 7) {
        regimenNam='d4T/3TC/NVP';
        regimenCod='CF3A';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==7) {
        if($j("#patienttype1").is(':checked')) {
            regimenNam='AZT/3TC/EFV';
            regimenCod='PM4';
        }
        else{
            regimenNam='AZT/3TC/EFV';
            regimenCod='CF1B';
        }
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==8) {
        regimenNam='AZT/3TC/ABC';
        regimenCod='CF2C';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==9) {
        if($j("#patienttype1").is(':checked')) {
            regimenNam='TDF/3TC/NVP';
            regimenCod='PM6';
        }
        else if($j("#patienttype3").is(':checked')) {
            regimenNam='TDF/3TC/LPV/r';
            regimenCod='PA3B';
        }
        else{
            regimenNam='TDF/3TC/NVP';
            regimenCod='AF2A';
        }
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==10) {
        regimenNam='TDF/3TC/AZT';
        regimenCod='AF2C';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==11) {
        regimenNam='d4T/3TC/EFV';
        regimenCod='AF3B';
    }
    else if(positionOfEquity ==12) {
        regimenNam='d4T/3TC/ABC';
        regimenCod='AF3C';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==13) {
        if($j("#patienttype1").is(':checked')) {
            regimenNam='AZT/3TC/LPV/r';
            regimenCod='PM5';
        }
        else if($j("#patienttype3").is(':checked')){
            regimenNam='AZT/3TC/LPV/r';
            regimenCod='PA1B';
        }
        else{
            regimenNam='AZT/3TC/LPV/r';
            regimenCod='AS1A';
        }
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==14) {
        regimenNam='AZT/3TC/LPV/r';
        regimenCod='CF1C';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==15) {
        regimenNam='TDF/3TC/LPV/r';
        regimenCod='AS2A';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==16) {
        regimenNam='TDF/3TC/ATV/r';
        regimenCod='AS2C';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity ==17) {
        if($j("#patienttype3").is(':checked')){
            regimenNam='d4T/3TC/LPV/r';
            regimenCod='CA2B';
        }
        else{
            regimenNam='d4T/3TC/LPV/r';
            regimenCod='CS3A';
        }
        ARVDrugSelected=1;
    }
    else if(positionOfEquity==18 || positionOfEquity==19){
        regimenNam='AZT/3TC';
        regimenCod='CA1A';
        ARVDrugSelected=1;
    }
    else if (positionOfEquity==20 || positionOfEquity==21){
        regimenNam='D4T/3TC';
        regimenCod='CA2A';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity==22 || positionOfEquity==23){
        regimenNam='TDF/3TC';
        regimenCod='CA3A';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity==24 || positionOfEquity==25){
        regimenNam='ABC/3TC/NVP';
        regimenCod='CF2A';
        ARVDrugSelected=1;
    }
    else if(positionOfEquity==26){
        regimenNam='AZT/3TC/EFV';
        regimenCod='CF1B';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==27 || positionOfEquity==34){
        regimenNam='ABC/3TC/EFV';
        regimenCod='CF2B';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==28 || positionOfEquity==35){
        regimenNam='ABC/3TC/LPV/r';
        regimenCod='CF2D';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==29){
        regimenNam='TDF/3TC/EFV';
        regimenCod='CNS1A';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==30){
        regimenNam='TDF/3TC/NVP';
        regimenCod='CNS1B';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==31){
        regimenNam='TDF/3TC/LPV/r';
        regimenCod='CNS2A';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==32){
        regimenNam='AZT/3TC/LPV/r';
        regimenCod='CS1A';
        ARVDrugSelected=1;
    }
else if(positionOfEquity==33){
        regimenNam='ABC/3TC/LPV/r';
        regimenCod='CS2A';
        ARVDrugSelected=1;
    }
     if(regimen.length>splicedRegimen.length && splicedRegimen.length ==0)
     {
         regimenNam='OI';
         regimenCod='OI';
         OIDrugSelected=1;
         ARVDrugSelected=0;
     }
  if(regimen.length==0){
     noDrugSelected=1;
  }
    return [regimenCod,regimenNam,ARVDrugSelected,OIDrugSelected,noDrugSelected];
}

function processForm(){
if($j("#patienttype1").is(':checked')){
isPMTCTSelected=1;
}
if($j("#patienttype3").is(':checked')){
isPEPSelected=1;
}
if($j("#regimenchange").is(':checked')){
regimenChanged=1;
}
if($j("#arvtype1").is(':checked')){
regimenInitition=1;
}
    var drugIsChecked;
    var quantityIsFilled;
    var requestIsFilled;
    var drugVSRequest=0;
    var drugVSQuantity=0;
    inputErrors="";
    $j('#pediatricFormDiv').find('tr.viewRow').each(function(){
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
            $j(this).find("input[name='other']").each(function(){
                if($j(this).val() !=''){
                    requestIsFilled=1;
                }
            });
            $j(this).find("input[name='Quantity']").each(function(){
                if($j(this).val() !=''){
                    quantityIsFilled=1;
                }
            });
        });
        //alert(" drugIsChecked "+drugIsChecked+" requestIsFilled "+requestIsFilled+" quantityIsFilled "+quantityIsFilled);
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
    $j('#pediatricFormDiv').find('tr.otherDrug').each(function(){
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
    var hivPedsData = [];
    $j('#pediatricFormDiv').find('tr.display').each(function(){
        var rowObject=[];
        $j(this).find('td').each(function(){
            $j(this).find('input,select').each(function(){
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
        hivPedsData.push(rowObject);
    });
    var v= validateHivPedsForm();
    if(v==0){
        $j("#errorDialog").empty();
        $j('<dl><dt></dt><dd >' + "Info: " + "Error:Please check all elements for the drug checked all Compulsory columns \n" +'</dd></dl> ').appendTo('#errorDialog');
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

        if($j('#h_24').val() !=""){
            otherDrugIDArray =$j('#h_24').map(function ()
            {    otherDrugID= $j('#h_24').val().substring($j('#h_24').val().indexOf('|')+1);
                return otherDrugID
            }).get();

        }
        if($j('#h_25').val() !=""){
            otherDrugIDArray2 =$j('#h_25').map(function ()
            {    otherDrugID2= $j('#h_25').val().substring($j('#h_25').val().indexOf('|')+1);
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
                    else if($j(this).attr('type') == 'hidden'){
                      if($j(this).val() !="" && $j(this).val() !="undefined"){
                        return $j(this).val().substring(0,$j(this).val().indexOf('|'));
                      }
                    }
        }).get();
        var drugs = (drugid).toString().split(",");
        var ans=validateHivRegimen(drugid);
        if(ans==true){
            var regimens=regimenFilter(drugid);
            var regimenCode= regimens[0];
            var regimenName=regimens[1];
            var anARVdrugIsSelected=regimens[2];
            var anOIDrugIsSelected=regimens[3];
            var noDrugSelectedForDispensing=regimens[4];
            if(noDrugSelectedForDispensing==1){
              $j("#errorDialog").empty();
              $j('<dl><dt></dt><dd >' + "Error: " + "You have not selected any drug to dispense" + '</dd></dl> ').appendTo('#errorDialog');
              $j("#errorDialog").dialog("open");
            }
             else if((anARVdrugIsSelected==1 && !$j("#arvtype2").is(':checked')) || (anARVdrugIsSelected==0 && $j("#arvtype2").is(':checked'))){
                  $j("#errorDialog").empty();
                  $j('<dl><dt></dt><dd >' + "Error: " + "Either you have selected ARV refill option without selecting ARV drug(s), or you are trying to dispense ARV drug(s) without selecting ARV refill option" + '</dd></dl> ').appendTo('#errorDialog');
                  $j("#errorDialog").dialog("open");
             }
             else if((anOIDrugIsSelected==1 && !$j("#arvtype3").is(':checked')) || (anOIDrugIsSelected==0 && $j("#arvtype3").is(':checked'))){
                $j("#errorDialog").empty();
                $j('<dl><dt></dt><dd >' + "Error: " + "Either you have selected OI refill option without selecting OI drug(s), or you are trying to dispense OI drug(s) without selecting OI refill option" + '</dd></dl> ').appendTo('#errorDialog');
                $j("#errorDialog").dialog("open");
             }
           else{
             if(regimenName=="OI" && pRegimen !="OI" && pRegimen !="undefined" && pRegimen !=""){
               isOnlyOIRefill=1;
             }
            var regimenVals;
            if(regimenName=="OI" && pRegimen !="OI" && pRegimen !="" && pRegimen !="undefined" && pRegimen !="NULL"){
            regimenName=pRegimen;
            regimenCode=pRegimenCode;
            }
            if(regimenName==""){
                $j.ajax({
                    type:"GET",
                    url:"currentPatientRegimen.form?patientIdentifier="+$j("#patientIdPediatricHIVForm").val(),
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
            var val= checkHivPedsForm(drugNum,regimenName,regimenChanged);
            if(val==true){
                var size = vals.length;
                var json = [];
                for (i = 0; i < size; i++) {
                    var drugPropertiesObject={};
                    drugPropertiesObject[vals[i]] = drugQ[i];
                    json.push(drugPropertiesObject);
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
                                url:"hivPedsProcessor.form?"+ "regimenCode=" + regimenCode+"&regimenName="+regimenName+"&regimenChanged="+regimenChanged+"&pepSelected="+isPEPSelected+"&pmtctSelected="+isPMTCTSelected+"&regimenInitiation="+regimenInitition+"&isOnlyOIRefill="+isOnlyOIRefill,
                                data:{values:JSON.stringify(hivPedsData) },
                                dataType:"json",
                                beforeSend:function (x) {
                                    if (x && x.overrideMimeType) {
                                        x.overrideMimeType("application/j-son;charset=UTF-8");
                                    }
                                },
                                success:function () {
                                    $j("#successDialog").empty();
                                    $j('<dl><dt></dt><dd >' +  "Dispensed successfuly" + '</dd></dl> ').appendTo('#successDialog');
                                    $j("#successDialog").dialog("open");
                                    setTimeout(function() {
                                         $j('#successDialog').dialog("close");
                                     }, 2000);
                                    $j('#west_panel_content').empty();
                                       $j('#west_panel_content').load('resources/subpages/PediatricForm.form', function () {
                                       });
                                }
                            });
                        }
                        else {
                            $j("#errorDialog").empty();
                            $j('<dl><dt></dt><dd >' + "Info: " + "Either you have not set the batch no or you do not have enough quantity in stock for one or more drugs" + '</dd></dl> ').appendTo('#errorDialog');
                            $j("#errorDialog").dialog("open");
                        }
                    }
                });
            }
           }
        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + drugs+" Is either incomplete or wrong regimen" + '</dd></dl> ').appendTo('#errorDialog');
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
/*
$j("table").delegate("#patientEncounters tbody tr td:nth-child(4)","mouseover",function(){
    var drugsIssuedArray={};
    var tr = this.parentNode;
    var trContent=patientEncountersDatatable.fnGetData(tr);
    drugsIssuedArray =trContent[3].split(',');
    alert(" drugsIssuedArray "+drugsIssuedArray);

})*/
$j("table").delegate("#patientEncounters tbody tr :last-child","click",function(){
    document.getElementById("pediatricForm").reset();
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
function viewPatientHistory(){
$j("#patientEncounters").show();
patienteEncounters = $j('#patientEncounters').dataTable({
  bJQueryUI:true,
  bRetrieve:true,
   bServerSide:true,
   bProcessing:true,
   sAjaxSource:"patientEncountersByIdentifier.form?identifier=" + $j('#patientIdPediatricHIVForm').val(),
    "fnServerData":fnDataTablesPipeline2,
    "aoColumnDefs":[
    {
        "bVisible":false,
        "aTargets":[ 0 ]
     }
     ]
 });
}


</script>
</head>
<body>
<%
PharmacyService service = Context.getService(PharmacyService.class);
String location=(String)session.getAttribute("location");
PharmacyLocations pharmacyLocation = service.getPharmacyLocationsByName(location);
List<PharmacyDose> pharmacyDoseList=service.getPharmacyDose();
%>
<DIV id="pediatricFormDiv" class="pediatricFormDivClass">
<h1><a>Pediatric ARV Prescription form</a></h1>
<DIV id="patientSummaryDashBoardPediatric">
<DIV id="align_left" class="align_left"></DIV>
<DIV id="align_right" class="align_right"></DIV>
<div class="clear"></div>
</DIV>
<form id="pediatricForm" action="#">
<fieldset>
<input id="viewHistory" type="button" value="View Patient History" onClick="viewPatientHistory()"/>
<table   id="patientSection">
    <tr class="display">
        <td>Patient ID</td><td><input name="patientIdPediatricHIVForm" id="patientIdPediatricHIVForm" class="required"  style="width:100px;"/></td>
        <td colspan="2">Patient name</td><td><input type="text" name="patientName" id="patientName" style="width:200px;"  readonly/></td>
        <td>Current Regimen</td><td><input type="text" name="currentRegimen" id="currentRegimen" style="width:150px;"  readonly/></td>
        <td>Patient Age</td><td><input type="text" name="patientAge" id="patientAge" style="width:50px;"  readonly/></td>
    </tr>
</table>
<table id="dataSection">
<tr class="display">
    <td>Encounter date</td><td><input id="datePicker" name="Enc4*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
    <td>No of days </td><td><input id="noofmonths" name="Enc6*noOfMonths|6#6" type="text"  value="" style="width:100px;"/></td>
    <td> Next visit date</td><td><input id="nextvisit" name="Enc5*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
</tr>
<tr class="display">
    <td>Weight<input type="hidden" name="rowCounter" value="3" /></td><td><input id="weight" name="weight" type="text"  value="" style="width:100px;"/></td></td>
</tr>
<tr class="display">
    <td>Patient type(Compulsory)</td>
    <td> <input type="radio" id="patienttype1" name="Obs*PATIENT TYPE|1724#10" value="PMTCT CHILD BORN|1360" > PMTCT</td>
    <td><input type="radio" id="patienttype2" name="Obs*PATIENT TYPE|1724#10" value="ROUTINE|432" > ROUTINE </td>
    <td colspan="2"><input type="radio" id="patienttype3" name="Obs*PATIENT TYPE|1724#10" value="POST EXPOSURE PROPHYLAXIS|2090"> POST EXPOSURE PROPHYLAXI</td>
</tr>
<tr class="display">
    <td>Regimen Line</td>
    <td colspan="2"> <input type="radio" name="regimen_line" value="Obs*Check|1705#10" > 1ST LINE ARV REGIMEN REFILL</td>
    <td colspan="2"><input type="radio" name="regimen_line" value="Obs*Check|1705#10" >2ND LINE ARV REGIMEN REFILL </td>
</tr>
<tr class="display">
    <td>ARV types </td>
    <td> <input type="checkbox" id="arvtype1" name="Obs*CHECK|1705#10" value="INITIATION|1703" >INITIATION OF ARVs</td>
    <td><input type="checkbox" id="arvtype2" name="Obs*CHECK|1705#10" value="In patient|7625" >In patient refill</td>
    <td><input type="checkbox" id="arvtype3" name="Obs*CHECK|1705#10" value="OI DRUG REFILL|7626">OI  REFILL</td>
    <td colspan="2"><input type="checkbox" id="arvtype4" name="Obs*CHECK|1705#10" value="Initiation of OI prophylaxis|7627">Initiation of OI prophylaxis</td>
    <td><input type="checkbox" id="arvtype5" name="Obs*CHECK|1705#10" value="CONTINUE REGIMEN|1257">CONTINUE REGIMEN</td>
</tr>
<tr class="display">
    <td>Regimen change</td><td><input type="radio" id="regimenchange" name="regimenchange" value="OREGIMEN FAILURE (TREATMENT FAILURE)|843" />Regimen Change</td>
    <td>Reason for regimen Change</td>
    <td><input type="radio" id="regimenchange1" name="Obs*CHANGE REGIMEN|1252#10" value="SIDE EFFECTS FROM TAKING MEDICATIONS|1664" >SIDE EFFECTS</td>
    <td><input type="radio" id="regimenchange2" name="Obs*CHANGE REGIMEN|1252#10" value="REGIMEN FAILURE (TREATMENT FAILURE)|843" >REGIMEN FAILURE (TREATMENT FAILURE)</td>
    <td><input type="radio" id="regimenchange3" name="Obs*CHANGE REGIMEN|1252#10" value="PREGNANCY|44" >PREGNANCY</td>
    <td><input type="radio" id="regimenchange4" name="Obs*CHANGE REGIMEN|1252#10" value="OTHER|5622" >OTHER</td>
</tr>
<tr class="display">
    <td>Comments<input type="hidden" name="rowCounter" value="8" /></td><td><input type="text"  name="comment" id="comment" colspan="5"/></td>
</tr>
<tr>
    <th>Medication</th><th>Weight Range</th><th>Form</th><th>Dose</th><th style="width:25%;">Quantity Prescribed</th><th>Other</th><th>Pill Count</th><th>Quantity Dispensed</th>
</tr>
<tr class="hideRow" id="drug1row1">
    <td>Lamivudine (3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="first_drug_line" title="drug" value="628|137" />Syrup</td>
    <td>3ml BD<input name="first_first_line_dose" type="hidden" value="3ml BD" /></td>
    <td style="width: 200px"><input type="radio" name="first_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="first_first_line_requested" title="Requested" value="2" />2
        <input  type="radio" name="first_first_line_requested" title="Requested" value="1" /> 3
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="first_drug_line" title="drug" value="628|137" />Syrup</td>
    <td>4ml BD<input name="first_second_line_dose" type="hidden"  value="4ml BD" /></td>
    <td><input type="radio" name="first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="first_line_requested" title="Requested" value="2" />2
        <input type="radio" name="first_line_requested" title="Requested" value="1" /> 3
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="first_drug_line" title="drug" value="628|137" />Syrup</td>
    <td>6ml BD<input name="first_third_line_dose" type="hidden" value="6ml BD" /></td>
    <td><input type="radio" name="first_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="first_line_requested" title="Requested" value="3" />3
        <input type="radio" name="first_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="first_drug_line" title="drug" value="628|137"/>Syrup</br>
        <input type="radio"  name="first_drug_fourth_line" title="drug" value="628|23"/>150mg tab
    </td>
    <td><input type="radio" value="7ml BD" name="first_line_dose"/>7ml BD</br>
        <input type="radio" value="1/2 tab BD" name="first_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="first_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="first_line_requested" title="Requested" value="4" />4
        <input type="radio" name="first_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="first_line_requested" title="Requested" value="30" />30
        <input type="radio" name="first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="first_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="first_drug_line" title="drug" value="628|137"/>Syrup</br>
        <input type="radio"  name="first_drug_line" title="drug" value="628|23"/>150mg tab
    </td>
    <td><input type="radio" value="9ml BD" name="first_line_dose"/>9ml BD</br>
        <input type="radio" value="1 tab am 1/2 tab pm" name="first_line_dose"/>1 tab am 1/2 tab pm
    </td>
    <td><input type="radio" name="first_line_requested" title="Requested" value="3" />3
        <input type="radio" name="first_line_requested" title="Requested" value="5" />5
        <input type="radio" name="first_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="first_line_requested" title="Requested" value="45" />45
        <input type="radio" name="first_line_requested" title="Requested" value="90" />90
        <input type="radio" name="first_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row6">
    <td></td>
    <td>>25kg</td>
    <td><input type="radio" name="first_drug_line" title="drug" value="628|23" />150mg tab</td>
    <td>1 tab BD<input name="first_line_dose" type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="first_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="first_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--second drug begins-->

<tr class="hideRow" id="drug2row1">
    <td>Abacavir (ABC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="second_drug_line" title="drug" value="814|138" />Syrup</td>
    <td>3ml BD<input name="second_line_dose" type="hidden" value="3ml BD" /></td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="second_line_requested" title="Requested" value="1" />1
        <input type="radio" name="second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="second_drug_line" title="drug" value="814|138" />Syrup</td>
    <td>4ml BD<input name="second_line_dose" type="hidden" value="4ml BD" /></td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="second_drug_third_line" title="drug" value="814|138" />Syrup</td>
    <td>6ml BD<input name="second_line_dose" type="hidden" value="6ml BD" /></td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="second_line_requested" title="Requested" value="3" />3
        <input type="radio" name="second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="second_drug_line" title="drug" value="814|138"/>Syrup</br>
        <input type="radio"  name="second_drug_line" title="drug" value="814|28"/>300mg tab
    </td>
    <td><input type="radio" value="7ml BD" name="second_line_dose"/>7ml BD</br>
        <input type="radio" value="1/2 tab BD" name="second_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="second_line_requested" title="Requested" value="4" />4
        <input type="radio" name="second_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="second_line_requested" title="Requested" value="60" />60
        <input type="radio" name="second_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="second_drug_line" title="drug" value="814|138"/>Syrup</br>
        <input type="radio"  name="second_drug_line" title="drug" value="814|28"/>300mg tab
    </td>
    <td><input type="radio" value="9ml BD" name="second_line_dose"/>9ml BD</br>
        <input type="radio" value="1 tab am 1/2 tab pm" name="second_fifth_line_dose"/>1 tab am 1/2 tab pm
    </td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="3" />3
        <input type="radio" name="second_line_requested" title="Requested" value="5" />5
        <input type="radio" name="second_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="second_line_requested" title="Requested" value="45" />45
        <input type="radio" name="second_line_requested" title="Requested" value="90" />90
        <input type="radio" name="second_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row6">
    <td></td>
    <td>>25kg</td>
    <td><input type="radio" name="second_drug_line" title="drug" value="814|28" />300mg tab</td>
    <td>1 tab BD<input name="second_line_dose" type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="second_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="second_line_requested" title="Requested" value="120" />120
        <input type="radio" name="second_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--third drug begins-->
<tr class="hideRow" id="drug3row1">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="third_drug_line" title="drug" value="797|139" />Syrup</td>
    <td>3ml BD<input name="third_line_dose" type="hidden" value="3ml BD" /></td>
    <td><input type="radio" name="third_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="third_line_requested" title="Requested" value="1" />1
        <input type="radio" name="third_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="third_drug_line" title="drug" value="797|139" />Syrup</td>
    <td>9ml BD<input name="third_line_dose" type="hidden" value="9ml BD" /></td>
    <td><input type="radio" name="third_line_requested" title="Requested" value="3" /> 3
        <input type="radio" name="third_line_requested" title="Requested" value="6" />6
        <input type="radio" name="third_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="third_drug_line" title="drug" value="797|139" />Syrup</td>
    <td>6ml BD<input name="third_line_dose" type="hidden" value="12ml BD" /></td>
    <td><input type="radio" name="third_line_requested" title="Requested" value="3" />3
        <input type="radio" name="third_line_requested" title="Requested" value="6" />6
        <input type="radio" name="third_line_requested" title="Requested" value="2" />2
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="third_drug_line" title="drug" value="797|139"/>Syrup</br>
        <input type="radio"  name="third_drug_line" title="drug" value="797|78"/>300mg tab
    </td>
    <td><input type="radio" value="15ml BD" name="third_line_dose"/>15ml BD</br>
        <input type="radio" value="1/2 tab BD" name="third_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="third_line_requested" title="Requested" value="5" /> 5
        <input type="radio" name="third_line_requested" title="Requested" value="10" />10
        <input type="radio" name="third_line_requested" title="Requested" value="3" /> 3 </br>
        <input type="radio" name="third_line_requested" title="Requested" value="30" />30
        <input type="radio" name="third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="third_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="third_drug_line" title="drug" value="797|78"/>300mg tab</td>
    <td><input type="radio" value="1 tab am 1/2 tab pm" name="third_line_dose"/>1 tab am 1/2 tab pm</td>
    <td>
        <input type="radio" name="third_line_requested" title="Requested" value="45" />45
        <input type="radio" name="third_line_requested" title="Requested" value="90" />90
        <input type="radio" name="third_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row6">
    <td></td>
    <td>>25kg</td>
    <td><input type="radio" name="third_drug_sixth_line" title="drug" value="797|78" />300mg tab</td>
    <td>1 tab BD<input name="third_line_dose" type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="third_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="third_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fourth drug begins-->
<tr class="hideRow" id="drug4row1">
    <td>ZDV for PMTC</td>
    <td><input type="text" name="patient_weight" value="" style="width: 50px"></td>
    <td><input type="radio" name="fourth_drug_line" title="drug" value="797|139" />Syrup</td>
    <td><input type="radio" name="fourth_line_requested" title="Requested" value="10mg/ml" /> 10mg/ml</td>
    <td>200 ml bottle for 42 days<input name="fourth_line_dose" type="hidden" value="200 ml bottle for 42 days" /></td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fifth drug begins(was missing)-->
<tr class="hideRow" id="drug5MissingRow">
    <td>NVP for PMTC</td>
    <td><input type="text" name="patient_weight_NVP" value="" style="width: 50px"></td>
    <td><input type="radio" name="fifth_drug_line_missing" title="drug" value="631|140" />Syrup</td>
    <td><input type="radio" name="fifth_line_missing_requested" title="Requested" value="10mg/ml" /> 10mg/ml</td>
    <td>200 ml bottle for 42 days<input name="fifth_missing_line_dose" type="hidden" value="200 ml bottle for 42 days" /></td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fifth drug begins-->
<tr class="hideRow" id="drug5row1">
    <td>Nevirapine (Initiation)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="fifth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_line" title="drug" value="631|141" />50mg
    </td>
    <td><input type="radio" value="2ml OD" name="fifth_line_dose"/>2ml OD</br>
        <input type="radio" value="1/2 tab OD" name="fifth_line_dose"/>1/2 tab OD
    </td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="fifth_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="fifth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_line" title="drug" value="631|141" />50mg
    </td>
    <td><input type="radio" value="4ml OD" name="fifth_line_dose"/>4ml OD</br>
        <input type="radio" value="1 tab OD" name="fifth_line_dose"/>1 tab OD
    </td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="fifth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="fifth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_line" title="drug" value="631|141" />50mg
    </td>
    <td><input type="radio" value="5ml OD" name="fifth_line_dose"/>5ml OD</br>
        <input type="radio" value="1 tab OD" name="fifth_line_dose"/>1 tab OD
    </td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="fifth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="fifth_drug_line" title="drug" value="631|140"/>Syrup</br>
        <input type="radio"  name="fifth_drug_line" title="drug" value="631|141"/>50mg tab
    </td>
    <td><input type="radio" value="6ml OD" name="fifth_line_dose"/>6ml OD</br>
        <input type="radio" value="11/2 tab OD" name="fifth_line_dose"/>11/2 tab BD
    </td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="fifth_line_requested" title="Requested" value="22" />22
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="fifth_drug_line" title="drug" value="631|141"/>50mg tab</td>
    <td><input type="radio" value="2 tab OD" name="fifth_line_dose"/>2 tab OD</td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="30" />30</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row6">
    <td></td>
    <td>>25kg</td>
    <td><input type="radio" name="fifth_drug_line" title="drug" value="631|22" />200mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 tab OD" /></td>
    <td><input type="radio" name="fifth_line_requested" title="Requested" value="15" />15</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>

<!--sixth drug begins-->
<tr class="hideRow" id="drug6row1">
    <td>Nevirapine (Maintenance)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="sixth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_line" title="drug" value="631|141" />50mg
    </td>
    <td><input type="radio" value="2ml BD" name="sixth_first_line_dose"/>2ml BD</br>
        <input type="radio" value="1/2 tab BD" name="sixth_first_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="sixth_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="sixth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="sixth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_line" title="drug" value="631|141" />50mg
    </td>
    <td><input type="radio" value="4ml BD" name="sixth_second_line_dose"/>4ml BD</br>
        <input type="radio" value="1 tab BD" name="sixth_second_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="2" />2
        <input type="radio" name="sixth_line_requested" title="Requested" value="4" /> 4
        <input type="radio" name="sixth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="sixth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="sixth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="sixth_drug_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_line" title="drug" value="631|141" />50mg</br>
        <input type="radio" name="sixth_drug_line" title="drug" value="631|22" />200mg tab
    </td>
    <td><input type="radio" value="5ml BD" name="sixth_line_dose"/>5ml BD</br>
        <input type="radio" value="1 tab BD" name="sixth_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="3" />1
        <input type="radio" name="sixth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="sixth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_line_requested" title="Requested" value="150" />150
        <input type="radio" name="sixth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="sixth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="sixth_drug_line" title="drug" value="631|140"/>Syrup</br>
        <input type="radio"  name="sixth_drug_line" title="drug" value="631|141"/>50mg tab</br>
        <input type="radio"  name="sixth_drug_line" title="drug" value="631|22" />200mg tab
    </td>
    <td><input type="radio" value="6ml BD" name="sixth_fourth_line_dose"/>6ml BD</br>
        <input type="radio" value="11/2 tab BD" name="sixth_fourth_line_dose"/>1 tab am 1/2 tab pm
    </td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="sixth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="sixth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_line_requested" title="Requested" value="150" />150
        <input type="radio" name="sixth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="sixth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row5">
    <td></td>
    <td>20-24.9kg</td>
    <td>
        <input type="radio"  name="sixth_drug_line" title="drug" value="631|141"/>50mg tab</br>
        <input type="radio" name="sixth_drug_line" title="drug" value="631|22" />200mg tab
    </td>
    <td>
        <input type="radio" value="2 tab BD" name="sixth_fifth_dose"/>3 tab BD
        <input type="radio" value="1 tab AM 1/2 tab PM" name="sixth_fifth_dose"/>1 tab AM 1/2 tab PM
    </td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="sixth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="sixth_line_requested" title="Requested" value="45" />45</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row6">
    <td></td>
    <td>>25kg</td>
    <td><input type="radio" name="sixth_drug_line" title="drug" value="631|22" />200mg tab</td>
    <td>1 tab BD<input name="sixth_line_dose" type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixth_line_requested" title="Requested" value="2" />2</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--seventh drug begins-->

<tr class="hideRow" id="drug7row1">
    <td>Efavirenz (EFV)</td>
    <td><5kg</td>
    <td><input type="radio" name="seventh_drug_line" title="drug" value="633|142" />50mg cap</td>
    <td>1 cap OD<input name="seventh_line_dose" type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row2">
    <td></td>
    <td>5-9.9kg</td>
    <td><input type="radio" name="second_drug_line" title="drug" value="633|143" />200mg cap</td>
    <td>1 cap OD<input name="seventh_line_dose" type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="seventh_drug_line" title="drug" value="633|143" />200mg cap</td>
    <td>1 cap OD<input name="seventh_line_dose" type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="seventh_drug_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="50mg OD" name="second_line_dose"/>50mg OD</br>
        <input type="radio" value="200mg OD" name="second_line_dose"/>200 mg OD</br>
        <input type="radio" value="300mg OD" name="second_line_dose"/>300 mg OD
    </td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="seventh_drug_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="100mg OD" name="seventh_line_dose"/>100mg OD</br>
        <input type="radio" value="200mg OD" name="seventh_line_dose"/>200mg OD</br>
        <input type="radio" value="300mg OD" name="seventh_line_dose"/>300 mg OD
    </td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row6">
    <td></td>
    <td>25-29.9kg</td>
    <td><input type="radio"  name="seventh_drug_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="100mg OD" name="seventh_line_dose"/>100mg OD</br>
        <input type="radio" value="200mg OD" name="seventh_line_dose"/>200mg OD</br>
        <input type="radio" value="300mg OD" name="seventh_line_dose"/>300 mg OD
    </td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_line_requested" title="Requested" value="90" />90
        <input type="radio" name="seventh_line_requested" title="Requested" value="180" />180
        <input type="radio" name="seventh_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row7">
    <td></td>
    <td>30-39.9kg</td>
    <td><input type="radio"  name="seventh_drug_line" title="drug" value="633|143"/>200mg tab</td>
    <td><input type="radio" value="2 caps OD" name="seventh_line_dose"/>2 caps OD</td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row8">
    <td></td>
    <td> >40kg</td>
    <td><input type="radio"  name="seventh_drug_line" title="drug" value="633|143"/>600mg tab</td>
    <td><input type="radio" value="1 tab OD" name="seventh_line_dose"/>1 tab OD</td>
    <td><input type="radio" name="seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eighth drug begins-->
<tr class="hideRow" id="drug8row1">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="eighth_drug_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>1tab BD<input name="eighth_line_dose" type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="eighth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="eighth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eighth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="eighth_drug_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>1.5 tab BD<input name="eighth_line_dose" type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="eighth_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="eighth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="eighth_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="eighth_drug_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>2 tabs BD<input name="eighth_line_dose" type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="eighth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eighth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="eighth_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="eighth_drug_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>2.5 tabs BD<input name="eighth_line_dose" type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="eighth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="eighth_line_requested" title="Requested" value="60" />300
        <input type="radio" name="eighth_line_requested" title="Requested" value="15" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="eighth_drug_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="eighth_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="eighth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="eighth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="eighth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--ninth drug begins-->

<tr class="hideRow" id="drug9row1">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="ninth_drug_line" title="drug" value="6679|158" />combo tab</td>
    <td>1tab BD<input name="ninth_line_dose" type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="ninth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="ninth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="ninth_drug_line" title="drug" value="6679|158" />combo tab</td>
    <td>1.5 tab BD<input name="ninth_line_dose" type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="ninth_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="ninth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="ninth_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="ninth_drug_line" title="drug" value="6679|158" />combo tab</td>
    <td>2 tabs BD<input name="ninth_line_dose" type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="ninth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="ninth_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="ninth_drug_line" title="drug" value="6679|158" />combo tab</td>
    <td>2.5 tabs BD<input name="ninth_line_dose" type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="ninth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="ninth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="ninth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="tenth_drug_line" title="drug" value="6679|158" />combo tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="tenth_line_dose"/></td>
    <td>
        <input type="radio" name="tenth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="tenth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--tenth drug begins-->

<tr class="hideRow" id="drug10row1">
    <td>ZDV/3TC/NVP</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="tenth_drug_line" title="drug" value="6467|159" />combo tab</td>
    <td>1tab BD<input name="tenth_line_dose" type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="tenth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="tenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="tenth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="tenth_drug_line" title="drug" value="6467|159" />combo tab</td>
    <td>1.5 tab BD<input name="tenth_line_dose" type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="tenth_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="tenth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="tenth_drug_line" title="drug" value="6467|159" />combo tab</td>
    <td>2 tabs BD<input name="tenth_line_dose" type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="tenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="tenth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="tenth_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="tenth_drug_line" title="drug" value="6467|159" />combo tab</td>
    <td>2.5 tabs BD<input name="tenth_line_dose" type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="tenth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="tenth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="tenth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="tenth_drug_line" title="drug" value="6467|159" />combo tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="eighth_line_dose"/></td>
    <td>
        <input type="radio" name="tenth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="tenth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eleventh drug begins-->
<tr class="hideRow" id="drug11row1">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="eleventh_drug_line" title="drug" value="792|160" />combo tab</td>
    <td>1tab BD<input name="eleventh_line_dose" type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="eleventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="eleventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eleventh_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="eleventh_drug_line" title="drug" value="792|160" />combo tab</td>
    <td>1tab am 0.5 tab pm<input name="eleventh_line_dose" type="hidden" value="1tab am 0.5 tab pm" /></td>
    <td><input type="radio" name="eleventh_line_requested" title="Requested" value="45" />45
        <input type="radio" name="eleventh_line_requested" title="Requested" value="90" />90
        <input type="radio" name="eleventh_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="eleventh_drug_line" title="drug" value="792|160" />combo tab</td>
    <td>1 tab BD<input name="eleventh_line_dose" type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="eleventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eleventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eleventh_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="eleventh_drug_line" title="drug" value="792|160" />combo tab</td>
    <td>1.5 tab am 1 tab pm<input name="eleventh_line_dose" type="hidden" value="1.5 tab am 1 tab pm" /></td>
    <td>
        <input type="radio" name="eleventh_line_requested" title="Requested" value="75" />75
        <input type="radio" name="eleventh_line_requested" title="Requested" value="150" />150
        <input type="radio" name="eleventh_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="eleventh_drug_line" title="drug" value="792|160" />combo tab</td>
    <td>1.5 tab BD<input name="eleventh_line_dose" type="hidden" value="1.5 tab BD" name="eighth_line_dose"/></td>
    <td>
        <input type="radio" name="eleventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eleventh_line_requested" title="Requested" value="240" />240
        <input type="radio" name="eleventh_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twelfth drug begins-->
<tr class="hideRow" id="drug12row1">
    <td>Didanosine (DDI)</td>
    <td><5-9.9kg</td>
    <td><input type="radio" name="twelfth_drug_line" title="drug" value="796|149" />25mg tab</td>
    <td>50mg tab BD<input name="twelfth_line_dose" type="hidden" value="50mg tab BD" /></td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row2">
    <td></td>
    <td>10-10.9kg</td>
    <td><input type="radio" name="twelfth_drug_line" title="drug" value="796|149" />200mg cap</td>
    <td>50mg tab BD<input name="tenth_line_dose" type="hidden" value="50mg tab BD" /></td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row3">
    <td></td>
    <td>11-13.9kg</td>
    <td><input type="radio"  name="twelfth_drug_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>25mg tab
    </td>
    <td><input type="radio" value="50mg BD" name="twelfth_line_dose"/>50mg BD</br>
        <input type="radio" value="25mg BD" name="twelfth_line_dose"/>25mg BD
    </td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row4">
    <td></td>
    <td>14-16.9kg</td>
    <td><input type="radio"  name="twelfth_drug_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>25mg tab
    </td>
    <td><input type="radio" value="50mg BD" name="twelfth_line_dose"/>50mg BD</br>
        <input type="radio" value="25mg BD" name="twelfth_line_dose"/>25mg BD
    </td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twelfth_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="twelfth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twelfth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="twelfth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row5">
    <td></td>
    <td>17-19.9kg</td>
    <td><input type="radio"  name="twelfth_drug_line" title="drug" value="633|142"/>50mg tab</td>
    <td>100mg (2tabs) BD<input type="radio" value="100mg (2tabs) BD" name="seventh_line_dose"/></td>
    <td>
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row6">
    <td></td>
    <td>20-60kg</td>
    <td><input type="radio"  name="twelfth_drug_line" title="drug" value="633|142"/>100mg tab</br>
        <input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>25mg tab</br>
        <input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>250mg EC cap
    </td>
    <td><input type="radio" value="100mg BD" name="seventh_line_dose"/>100mg BD</br>
        <input type="radio" value="25mg BD" name="seventh_line_dose"/>25mg BD</br>
        <input type="radio" value="1 tab OD" name="seventh_line_dose"/>1 tab OD
    </td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row7">
    <td></td>
    <td>>60</td>
    <td><input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>200mg tab </br>
        <input type="radio"  name="twelfth_drug_line" title="drug" value="633|143"/>400mg EC cap
    </td>
    <td>
        <input type="radio" value="1 tab BD" name="seventh_line_dose"/>1 tab BD</br>
        <input type="radio" value="400mg EC cap OD" name="seventh_line_dose"/>400mg EC cap OD
    </td>
    <td><input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twelfth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--thirteenth drug begins-->
<tr class="hideRow" id="drug13row1">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="thirteenth_drug_line" title="drug" value="794|151" />Syrup</td>
    <td>1ml BD<input name="thirteen_line_dose" type="hidden" value="1ml BD" /></td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow"  id="drug13row2">
    <td></td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="thirteenth_drug_line" title="drug" value="794|151" />Syrup</td>
    <td>1.5ml BD<input name="thirteen_line_dose" type="hidden" value="1.5ml BD" /></td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="2" />2
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row3">
    <td></td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="thirteenth_drug_line" title="drug" value="794|151" />Syrup</td>
    <td>2ml BD<input name="thirteen_line_dose" type="hidden" value="2ml BD" /></td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="2" />2
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="4" />4
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row4">
    <td></td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="thirteenth_drug_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_line" title="drug" value="794|150"/>200mg
    </td>
    <td><input type="radio" value="2.5ml BD" name="second_line_dose"/>2.5ml BD</br>
        <input type="radio" value="1 tab BD" name="second_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="2" />2 </br>
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row5">
    <td></td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="thirteenth_drug_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_line" title="drug" value="794|150"/>200mg tab
    </td>
    <td><input type="radio" value="3ml BD" name="thirteenth_line_dose"/>3ml BD</br>
        <input type="radio" value="1 tab BD" name="thirteenth_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="6" />6
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row6">
    <td></td>
    <td>25-29.9</td>
    <td><input type="radio"  name="thirteenth_drug_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_line" title="drug" value="794|150"/>200mg tab
    </td>
    <td><input type="radio" value="3.5ml BD" name="thirteenth_line_dose"/>3.5ml BD</br>
        <input type="radio" value="2 tabs am 1 tab pm" name="thirteenth_line_dose"/>2 tabs am 1 tab pm
    </td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="4" />4
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="7" />7
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row7">
    <td></td>
    <td>>=30</td>
    <td><input type="radio" name="thirteenth_drug_line" title="drug" value="794|150" />200mg tab</td>
    <td>2 tabs BD<input type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="thirteenth_line_requested" title="Requested" value="120" /> 120
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="thirteenth_line_requested" title="Requested" value="60" /> 60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fourteenth drug begins-->

<tr class="hideRow" id="drug14row1">
    <td>TDF/3TC(Tenofovir/Lamivudine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="fourteenth_drug_line" title="drug" value="1400|30" />300mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="fourteenth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="fourteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="fourteenth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fifteenth drug begins-->

<tr class="hideRow" id="drug15row1">
    <td>TDF/3TC/EFV<br>(Tenofovir/Lamivudine/Efavirenz)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="fifteenth_drug_line" title="drug" value="6964|38" />combo tab</td>
    <td>1 tab OD(Nocte)<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="fifteenth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="fifteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="fifteenth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--sixteenth drug begins-->

<tr class="hideRow" id="drug16row1">
    <td>ZDV/3TC(Zidovudine/Lamivudine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="sixteenth_drug_line" title="drug" value="630|26" />combo tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="sixteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixteenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixteenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--seventeenth drug begins-->

<tr class="hideRow" id="drug17row1">
    <td>ZDV/3TC/NVP<br>(Zidovudine/Lamivudine/Nevirapine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="seventeenth_drug_line" title="drug" value="6467|18" />combo tab</td>
    <td>1 tab BD<input name="seveteenth_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="seveteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventeenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventeenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eiteenth drug begins-->

<tr class="hideRow" id="drug18row1">
    <td>D4T/3TC/NVP<br>(Stavudine/Lamivudine/Nevirapine) Triomune 30</td>
    <td>>=25kg</td>
    <td><input type="radio" name="eigteenth_drug_line" title="drug" value="792|21" />combo tab</td>
    <td>1 tab BD<input name="eigteenth_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="eigteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eigteenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eigteenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--ninteenth drug begins-->

<tr class="hideRow" id="drug19row1">
    <td>D4T/3TC<br>(Stavudine/Lamivudine)30mg/150mg</td>
    <td>>=25kg</td>
    <td><input type="radio" name="ninteenth_drug_line" title="drug" value="6965|39" />30/150mg tab</td>
    <td>1 tab BD<input name="ninteenth_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="ninteenth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="niteenth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninteenth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentythird drug begins-->

<tr class="hideRow" id="drug23row1">
    <td>Ritonovir</td>
    <td>>10kg</td>
    <td><input type="radio" name="twentyThird_drug_line" title="drug" value="795|130" />100mg tab</td>
    <td>1 tab BD<input name="twenty_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="twentyThird_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentyThird_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twentyThird_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr>
    <td colspan=3><h3>OI PROPHYLAXIS</h3></td>
</tr>
<!--Twentienth drug begins-->

<tr class="hideRow" id="drug20row1">
    <td>Co-trimaxazole</td>
    <td><5kg</td>
    <td><input type="radio" name="twentieth_drug_line" title="drug" value="916|110"/>Syrup
    <td>2.5ml BD<input name="twentienth_line_dose" type="hidden" value="2.5ml Daily" /></td>
    <td><input type="radio" name="twentieth_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="twentieth_line_requested" title="Requested" value="2" />2
        <input type="radio" name="twentieth_line_requested" title="Requested" value="1" />1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row2">
    <td></td>
    <td>5-9kg</td>
    <td><input type="radio" name="twentieth_drug_line" title="drug" value="916|110" />Syrup</br>
        <input type="radio" name="twentieth_drug_line" title="drug" value="916|65" />SS Tab
    </td>
    <td><input type="radio" value="5ml Daily" name="twentieth_line_dose"/>5ml Daily</br>
        <input type="radio" value="1/2 tab Daily" name="twentieth_line_dose"/>1/2 tab Daily
    </td>
    <td><input type="radio" name="twentieth_line_requested" title="Requested" value="2" />2
        <input type="radio" name="twentieth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="twentieth_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="twentieth_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row3">
    <td></td>
    <td>10-15kg</td>
    <td><input type="radio" name="twentieth_drug_line" title="drug" value="916|110" />Syrup</br>
        <input type="radio" name="twentieth_drug_line" title="drug" value="916|65" />SS  tab
    </td>
    <td><input type="radio" value="10ml Daily" name="twentieth_line_dose"/>10ml Daily</br>
        <input type="radio" value="1 tab Daily" name="twentieth_line_dose"/>1 tab Daily
    </td>
    <td><input type="radio" name="twentieth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="twentieth_line_requested" title="Requested" value="6" />6
        <input type="radio" name="twentieth_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row4">
    <td></td>
    <td>15-30kg</td>
    <td><input type="radio"  name="twentieth_drug_line" title="drug" value="916|61"/>DS Tab</br>
        <input type="radio"  name="twentieth_drug_line" title="drug" value="916|65"/>SS Tab
    </td>
    <td><input type="radio" value="1/2 DS Tab OD" name="twentieth_line_dose"/>1/2 DS Tab OD</br>
        <input type="radio" value="1 SS tab OD" name="twentieth_line_dose"/>1 SS tab OD
    </td>
    <td><input type="radio" name="twentieth_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_line_requested" title="Requested" value="8" />8</br>
        <input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row5">
    <td></td>
    <td>>30kg</td>
    <td><input type="radio"  name="twentieth_drug_line" title="drug" value="916|61"/>DS Tab</br>
        <input type="radio"  name="twentieth_drug_line" title="drug" value="916|65"/>SS Tab
    </td>
    <td><input type="radio" value="1 DS Tab OD" name="twentieth_line_dose"/>1 DS Tab OD</br>
        <input type="radio" value="2 SS tab OD" name="twentieth_line_dose"/>2 SS tab OD
    </td>
    <td><input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_line_requested" title="Requested" value="15" />15</br>
        <input type="radio" name="twentieth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twentieth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentyfirst drug begins-->

<tr class="hideRow" id="drug21row1">
    <td>Dapsone</td>
    <td>8-14</td>
    <td><input type="radio" name="twentyfirst_drug_line" title="drug" value="92|62" />100mg tab</td>
    <td>1/2 tab once weekly<input name="twentyfirst_line_dose" type="hidden" value="1/2 tab once weekly" /></td>
    <td><input type="radio" name="twentyfirst_line_requested" title="Requested" value="5" />5
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="10" />10
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="2" />2
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug21row2">
    <td></td>
    <td>>15-30kg</td>
    <td><input type="radio" name="twentyfirst_drug_line" title="drug" value="92|62" />100mg tab</td>
    <td>1/2 tab BD<input name="twentyfirst_line_dose" type="hidden" value="1/2 Tab BD" /></td>
    <td><input type="radio" name="twentyfirst_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug21row3">
    <td></td>
    <td>>30kg</td>
    <td><input type="radio" name="twentyfirst_drug_line" title="drug" value="92|62" />100mg tab</td>
    <td>1 tab BD<input name="twentyfirst_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="twentyfirst_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentyfirst_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentysecond drug begins -->

<tr class="hideRow" id="drug22row1">
    <td>Isoniazid</td>
    <td>5-10</td>
    <td><input type="radio" name="twentysecond_drug_line" title="drug" value="656|59" />100mg tab</td>
    <td>1 Tab BD<input name="twentysecond_line_dose" type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="twentysecond_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentysecond_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug22row2">
    <td></td>
    <td>>10.1-30</td>
    <td><input type="radio" name="twentysecond_drug_line" title="drug" value="656|59" />100mg tab</td>
    <td>1/2 tab BD<input name="twentysecond_line_dose" type="hidden" value="1/2 Tab BD" /></td>
    <td><input type="radio" name="twentysecond_second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentysecond_second_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug22row3">
    <td></td>
    <td>>20kg</td>
    <td><input type="radio" name="twentysecond_drug_line" title="drug" value="656|59" />100mg tab </br>
        <input type="radio" name="twentysecond_drug_line" title="drug" value="656|60" />300mg tab
    </td>
    <td><input type="radio" value="1 Tab BD" name="twentieth_line_dose"/>1 Tab BD</br>
        <input type="radio" value="3 tab BD" name="twentieth_line_dose"/>3 tab BD
    </td>
    <td><input type="radio" name="twentysecond_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twentysecond_line_requested" title="Requested" value="180" />180</br>
        <input type="radio" name="twentysecond_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentysecond_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="otherDrug" id="drug24row1">
    <td><input type="text" name="dispenseForm_medication" id=d_24  style="width:150px;"/></td>
    <td><input type="hidden" name="twentyfourth_drug_line" id=h_24 title="drug" value="" /></td>
    <td></td>
    <td><select id="pediatricFormDose1"  name="dose">
    <% for(PharmacyDose pharmacyDoseInstance:pharmacyDoseList){%>
         <option value=<%=pharmacyDoseInstance.getName()%> ><%=pharmacyDoseInstance.getName()%></option>
        <%}%>
    </select></td>
    <td><input type="text" name="twentyfourth_line_requested" title="Requested" value="" style="width:100px;"/></td>
    <td></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="otherDrug" id="drug25row1">
    <td><input type="text" name="dispenseForm_medication" id=d_25  style="width:150px;"/></td>
    <td><input type="hidden" name="twentyfifth_drug_line" id=h_25 title="drug" value="" /></td>
    <td></td>
    <td><select id="pediatricFormDose2"  name="dose">
    <% for(PharmacyDose pharmacyDoseInstance:pharmacyDoseList){%>
         <option value=<%=pharmacyDoseInstance.getName()%> ><%=pharmacyDoseInstance.getName()%></option>
        <%}%>
        </select></td>
    <td><input type="text" name="twentyfifth_line_requested" title="Requested" value="" style="width:100px;"/></td>
    <td></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="display">
    <td>Prescribed By</td><td><input type="text" id="prescriber" name="Prescriber" style="width:100px;" value=""/></td><td><input type="button" value="Dispense" onclick="processForm()"></td>
</tr>
</div>
</table>
</fieldset>
</form>

    <table cellpadding="0" cellspacing="0" border="0" class="display"  id="patientEncounters">
    <h3 class="boxHeaderP">Patient Encounters</h3>
        <thead>
        <tr>
            <th width="4%">Action</th>
            <th width="4%">Encounter Date</th>
            <th>Comments</th>
            <th>Issued Medication</th>
            <th>Location</th>
            <th>User</th>
            <th width="4%">Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>