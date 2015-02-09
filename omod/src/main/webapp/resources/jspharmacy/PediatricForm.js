
$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j("#nextvisit").datepicker();
var pRegimen;
var cRegimen;
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
    [628,814,631],
    [631,6679]
];
$j.getJSON("drugBincard.form?selectDose=doseSelect",function (result) {
    $j.each(result,function (index, value) {
        $j("#pediatricFormDose1,#pediatricFormDose2").append($j("<option></option>").attr("value",index).text(value));
    });
});
$j(".hideRow").hide();
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
$j("#weight") .change(function () {
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
});
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
        var json = {};
        for (i = 0; i < size; i++) {
            json[vals[i]] = drugQ[i];
        }
        $j.ajax({
            type:"GET",
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                // document.getElementById("quantityInStore"+drugIndex).value=result;
                $j.ajax({
                    type:"GET",
                    url:"dispense.form",
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
        })
    }
});
$j("#patientId").autocomplete({
    source:function (request, response) {
        dataString = "searchPatient=" + request.term;
        $j.getJSON("drugDetails.form?drop=patientSearch&" + dataString, function (result) {
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
        $j.ajax({
            type:"GET",
            url:"drugDetails.form?drop=patientLastName&patientToFind="+patient,
            data:patient,
            dataType:"json",
            success:function (result) {
                document.getElementById("patientName").value=result;
                $j("#currentRegimen").val()=="";
                $j.getJSON("dispense.form?patientUUIDToFindRegimen=" +patient, function(data) {
                    if(jQuery.isEmptyObject(data)){
                        pRegimen="";
                        $j("#currentRegimen").val("Not given");
                    }
                    else{
                        $j.each(data, function(key, val) {
                            var strv= val.toString();
                            pRegimen=strv;
                            $j("#currentRegimen").val(strv);

                        });
                    }
                });

                $j("#dataSection").show();
            }
        });

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

function checkHivPedsForm(val){
    cRegimen=val;
    if(regimenName !=pRegimen)
    {
        if($j("#regimenchange").is(':checked') || ($j("#arvtype1").is(':checked') )|| ($j("#arvtype5").is(':checked') ))  {
            return true;
        }
        else{
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "Regimen Not ok Or Select Regimen change\n"+ '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open")
            return false
        }
    }
    else {
        return true;
        $j("#infodiv").html("Regimen ok");

    }
}
function validateHivPedsForm(){
    var num=1;
    message="";
    if ($j("#weight").val()=="")
    {
        message+="No weight entered";
        return num;
    }
    if (!$j("#patienttype1").is(':checked')&&!$j("#patienttype2").is(':checked')&&!$j("#patienttype3").is(':checked'))
    {
        message+="Atleast one patient type must be selected  "+"<br/>";
    }
    if ((!$j("#arvtype1").is(':checked'))&&(!$j("#arvtype2").is(':checked'))&&(!$j("#arvtype3").is(':checked'))&&(!$j("#arvtype4").is(':checked'))&&(!$j("#arvtype5").is(':checked')))
    {
        message+="Atleast one ARV type must be selected "+"<br/>";
    }
    if ($j("#regimenchange").is(':checked')) {
        if ((!$j("#regimenchange1").is(':checked'))&&(!$j("#regimenchange2").is(':checked'))&&(!$j("#regimenchange3").is(':checked')))
        {
            message+="Atleast one regimen change reason must be selected "+"<br/>";
        }
    }
    if ($j("#datePicker").val()=="") {
        message+="Give Encounter date "+"<br/>";
    }
    if ($j("#nextvisit").val()=="") {
        message+="No Next visit date "+"<br/>";
    }
    if ($j("#prescriber").val()=="") {
        message+="Give the  prescriber "+"<br/>";

    }
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
    var regimenCode='';
    var regimenName='';
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
        var sortedCurrNumber=currNumber.sort();
        var sortedSPlicedRegimen=splicedRegimen.sort();
        if(sortedCurrNumber.toString()==sortedSPlicedRegimen.toString()){
            positionOfEquity=numbersCounter;
            break;
        }

    }
    if(positionOfEquity < 3){
        if($j("#patienttype1").is(':checked')){
            regimenName='AZT/3TC/NVP';
            regimenCode='PM3';
        }
        else{
            regimenName='AZT/3TC/NVP';
            regimenCode='CF1A';
        }
    }
    else if(positionOfEquity < 5) {
        regimenName='TDF/3TC/EFV';
        regimenCode='AF2B';
    }
    else if(positionOfEquity < 7) {
        regimenName='d4T/3TC/NVP';
        regimenCode='CF3A';
    }
    else if(positionOfEquity ==7) {
        if($j("#patienttype1").is(':checked')) {
            regimenName='AZT/3TC/EFV';
            regimenCode='PM4';
        }
        else{
            regimenName='AZT/3TC/EFV';
            regimenCode='CF1B';
        }
    }
    else if(positionOfEquity ==8) {
        regimenName='AZT/3TC/ABC';
        regimenCode='CF2C';
    }
    else if(positionOfEquity ==9) {
        if($j("#patienttype1").is(':checked')) {
            regimenName='TDF/3TC/NVP';
            regimenCode='PM6';
        }
        else if($j("#patienttype3").is(':checked')) {
            regimenName='TDF/3TC/NVP';
            regimenCode='PA3B';
        }
        else{
            regimenName='TDF/3TC/NVP';
            regimenCode='AF2A';
        }
    }
    else if(positionOfEquity ==10) {
        regimenName='TDF/3TC/AZT';
        regimenCode='AF2C';
    }
    else if(positionOfEquity ==11) {
        regimenName='d4T/3TC/EFV';
        regimenCode='AF3B';
    }
    else if(positionOfEquity ==12) {
        regimenName='d4T/3TC/ABC';
        regimenCode='AF3C';
    }
    else if(positionOfEquity ==13) {
        if($j("#patienttype1").is(':checked')) {
            regimenName='AZT/3TC/LPV/r';
            regimenCode='PM5';
        }
        else if($j("#patienttype3").is(':checked')){
            regimenName='AZT/3TC/LPV/r';
            regimenCode='PA1B';
        }
        else{
            regimenName='AZT/3TC/LPV/r';
            regimenCode='AS1A';
        }
    }
    else if(positionOfEquity ==14) {
        regimenName='AZT/3TC/ATV/r';
        regimenCode='CF1C';
    }
    else if(positionOfEquity ==15) {
        regimenName='TDF/3TC/LPV/r';
        regimenCode='AS2A';
    }
    else if(positionOfEquity ==16) {
        regimenName='TDF/3TC/ATV/r';
        regimenCode='AS2C';
    }
    else if(positionOfEquity ==17) {
        if($j("#patienttype3").is(':checked')){
            regimenName='d4T/3TC/LPV/r';
            regimenCode='CA2B';
        }
        else{
            regimenName='d4T/3TC/LPV/r';
            regimenCode='CS3A';
        }

    }
    else if(positionOfEquity==18 || positionOfEquity==19){
        regimenName='AZT/3TC';
        regimenCode='CA1A';
    }
    else if (positionOfEquity==20 || positionOfEquity==21){
        regimenName='D4T/3TC';
        regimenCode='CA2A';
    }
    else if(positionOfEquity==22 || positionOfEquity==23){
        regimenName='TDF/3TC';
        regimenCode='CA3A'
    }
    else if(positionOfEquity==24 || positionOfEquity==25){
        regimenName='ABC/3TC/NVP';
        regimenCode='CF2A'
    }
    else{
        regimenName='';
        regimenCode=''
    }
    return [regimenCode,regimenName];
}

function processForm(){
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
        inputErrors+="You have not selected/entered quantity prescribed for one or more drugs"+"<br/>";
    }
    if(drugVSQuantity==1){
        inputErrors+="You have not selected quantity dispensed for one or more drugs"+"<br/>";
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
        inputErrors+="You have not entered either quantity dispensed or quantity prescribed for one or more  of the extra drugs"+"<br/>";
    }
    var hivPedsData = [];
    $j('#pediatricFormDiv').find('tr.display').each(function(){
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
        hivPedsData.push(rowObject);
    });
    var v= validateHivPedsForm();
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
        var values = $j("input[title='drug']").map(function ()
        {
            if ($j(this).is(':checked')){
                drugNum+= $j(this).val().substring($j(this).val().indexOf('|')+1)+",";
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
            regimenCode= regimens[0];
            regimenName=regimens[1];
            var val= checkHivPedsForm(drugNum);
            if(val==true){
                var size = vals.length;
                var json = {};
                for (i = 0; i < size; i++) {
                    json[vals[i]] = drugQ[i];
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
                                url:"hivPedsProcessor.form?"+ "regimenCode=" + regimenCode+"&regimenName="+regimenName,
                                data:{values:JSON.stringify(hivPedsData) },
                                dataType:"json",
                                beforeSend:function (x) {
                                    if (x && x.overrideMimeType) {
                                        x.overrideMimeType("application/j-son;charset=UTF-8");
                                    }
                                },
                                success:function () {
                                    $j('#pediatricFormDiv').replaceWith("<div id='red'>Data saved<div>");
                                    $j("#red").delay(5000).hide("slow");
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
            $j('<dl><dt></dt><dd >' + "Info: " + drugs+" Is an invalid regimen" + '</dd></dl> ').appendTo('#errorDialog');
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
