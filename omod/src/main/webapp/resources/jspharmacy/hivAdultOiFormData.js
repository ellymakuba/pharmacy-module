
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
$j('#drug1').live('click',function(){
    if($j('#drug1').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug1"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug1"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
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
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                document.getElementById("quantityInStore1").value=result;
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
$j('#drug2').live('click',function(){
    if($j('#drug2').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug2"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug2"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
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
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                document.getElementById("quantityInStore2").value=result;
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
}) ;
$j('#drug3').live('click',function(){
    if($j('#drug3').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug3"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug3"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
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
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                document.getElementById("quantityInStore3").value=result;
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
$j('#drug4').live('click',function(){
    if($j('#drug4').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug4"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug4"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
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
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                document.getElementById("quantityInStore4").value=result;
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
}) ;
$j('#drug5').live('click',function(){
    if($j('#drug5').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug5"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug5"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
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
            url:"stockInventory.form",
            data: { "jsonDrugObject" :JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                document.getElementById("quantityInStore5").value=result;
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
}) ;
$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j("#nextvisit").datepicker();
var pRegimen;
var cRegimen;
var id = jQuery.Pid.id;
var formDose;
$j("input[title='gnty1']").change(
    function () {

        if (($j("#1439").is(':checked')))
        {

            document.getElementById("qnty1").value  =parseInt($j("input[title='gnty1']").val())* 42;
        }
        if (($j("#836").is(':checked')))
        {

            document.getElementById("qnty1").value  =parseInt($j("input[title='gnty1']").val())* 160;
        }

    });


$j("input[title='gnty5']").change(
    function () {

        if (($j("#821").is(':checked')))
        {

            document.getElementById("qnty5").value  =parseInt($j("input[title='gnty5']").val())* 30;
        }

    });
$j("input[title='gnty4']").change(
    function () {

        if (($j("#8823").is(':checked')))
        {

            document.getElementById("qnty4").value  =parseInt($j("input[title='gnty4']").val())* 126;
        }
        if (($j("#78553").is(':checked')))
        {

            document.getElementById("qnty4").value  =parseInt($j("input[title='gnty4']").val())* 28;
        }


        if (($j("#53553").is(':checked')))
        {

            document.getElementById("qnty4").value  =parseInt($j("input[title='gnty4']").val())* 112;
        }


    });


$j("input[title='gnty3']").change(
    function () {

        if (($j("#882").is(':checked')))
        {

            document.getElementById("qnty3").value  =parseInt($j("input[title='gnty3']").val())* 252;
        }
        if (($j("#7855").is(':checked')))
        {

            document.getElementById("qnty3").value  =parseInt($j("input[title='gnty3']").val())* 56;
        }


        if (($j("#5355").is(':checked')))
        {

            document.getElementById("qnty3").value  =parseInt($j("input[title='gnty3']").val())* 224;
        }


    });


$j("input[title='gnty2']").change(
    function () {

        if (($j("#8395").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 56;
        }
        if (($j("#8396").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 56;
        }


        if (($j("#1435").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 14;
        }
        if (($j("#5334").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 14;
        }
        if (($j("#6670").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 14;
        }
        if (($j("#8397").is(':checked')))
        {

            document.getElementById("qnty2").value  =parseInt($j("input[title='gnty2']").val())* 14;
        }

    });

$j('#nextvisit').change(
    function () {

        var givenDate = document.getElementById("nextvisit").value;

        var currentTime = new Date();
        var month = currentTime.getMonth() + 1;
        var day = currentTime.getDate();
        var year = currentTime.getFullYear();

        //compare to get the diffrence

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



        //3 weeks

    });

$j('#noofmonths')
    .change(
    function () {
        var inputTags = document.getElementsByTagName('INPUT');
        var givenDays = document.getElementById("noofmonths").value;
        quantity = givenDays;



        var date = new Date();
        var d = addDays(date, givenDays);
        var curr_date = d.getDate();
        var curr_month = d.getMonth() + 1;
        var curr_year = d.getFullYear();
        var dateFinal = curr_month + "/" + curr_date + "/"
            + curr_year;

        document.getElementById("nextvisit").value = dateFinal;

    });

function addDays(myDate, days) {
    return new Date(myDate.getTime() + days * 24 * 60 * 60 * 1000);
}

$j("#prescriber").autocomplete({

    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {
        //   http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form

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

//$j('#qnty')
//    .change(
//    function () {
//
//           var number;
//        if($j("#1439").is(':checked'))  {
//            var quantityPrescribed = document.getElementById("qnty").value;
//            var qnty=  quantityPrescribed*42;
//
//            document.getElementById("qnty").value =qnty;
//        }
//        else if($j("#836").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty").value;
//            var qnty=  quantityPrescribed*160;
//
//            document.getElementById("qnty").value =qnty;
//        }  else if(document.getElementById("row1m").value!=null){
//
//            var quantityPrescribed = document.getElementById("qnty").value;
//            var qnty=  quantityPrescribed*document.getElementById("row1m").value;
//
//            document.getElementById("qnty").value =qnty;
//        }
//
//
//
//
//
//    });

//$j('#qnty2')
//    .change(
//    function () {
//
//        var number;
//        if($j("#8395").is(':checked'))  {
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*56;
//
//            document.getElementById("qnty2").value =qnty;
//        }
//        else if($j("#8396").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*56;
//
//            document.getElementById("qnty2").value =qnty;
//        }  else if($j("#1435").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*14;
//
//            document.getElementById("qnty2").value =qnty;
//        }
//        else if($j("#5334").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*14;
//
//            document.getElementById("qnty2").value =qnty;
//        }
//        else if($j("#6670").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*14;
//
//            document.getElementById("qnty2").value =qnty;
//        }
//
//        else if($j("#8397").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty2").value;
//            var qnty=  quantityPrescribed*14;
//
//            document.getElementById("qnty2").value =qnty;
//        }
//
//
//
//
//
//    });
//
//
//
//$j('#qnty3')
//    .change(
//    function () {
//
//        var number;
//        if($j("#882").is(':checked'))  {
//            var quantityPrescribed = document.getElementById("qnty3").value;
//            var qnty=  quantityPrescribed*252;
//
//            document.getElementById("qnty3").value =qnty;
//        }
//        else if($j("#7855").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty3").value;
//            var qnty=  quantityPrescribed*56;
//
//            document.getElementById("qnty3").value =qnty;
//        }  else if($j("#5355").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty3").value;
//            var qnty=  quantityPrescribed*224;
//
//            document.getElementById("qnty3").value =qnty;
//        }
//
//
//
//    });
//
//
//$j('#qnty4')
//    .change(
//    function () {
//
//        var number;
//        if($j("#8823").is(':checked'))  {
//            var quantityPrescribed = document.getElementById("qnty4").value;
//            var qnty=  quantityPrescribed*126;
//
//            document.getElementById("qnty4").value =qnty;
//        }
//        else if($j("#78553").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty4").value;
//            var qnty=  quantityPrescribed*28;
//
//            document.getElementById("qnty4").value =qnty;
//        }  else if($j("#53553").is(':checked'))  {
//
//            var quantityPrescribed = document.getElementById("qnty4").value;
//            var qnty=  quantityPrescribed*112;
//
//            document.getElementById("qnty4").value =qnty;
//        }
//
//
//
//    });
//
//
//$j('#qnty5')
//    .change(
//    function () {
//
//        var number;
//
//            var quantityPrescribed = document.getElementById("qnty5").value;
//            var qnty=  quantityPrescribed*30;
//
//            document.getElementById("qnty5").value =qnty;
//
//
//
//    });

function validateHivOiForm(){
    var value;

    //drug1 row1s row1m  row1d

//    drug1 1439 or 836 qnty
//    drug2 8395 or 8396 0r(5334,6670,8397) qnty2
//    drug3 882 or 7855 or 5355 qnty3
//    drug4 8823 or 78553 or 53553 qnty4
//    drug5 821  qnty5

    for(var t=1;t<=7;t++){

        if(t>=6){

            if ($j("#drug"+t+"").val()!="") {

                if (($j("input#indi"+t+"").val()!="")&&($j("input#qnty"+t+"").val()!="")){

                    value=1;

                }
                else  {
                    value=0;

                }

            }

        }
        else {
            if ($j("#drug"+t+"").is(':checked')) {


                if(t==3) {

                    if ((!$j("#882").is(':checked'))&&(!$j("#7855").is(':checked'))&&(!$j("#5355").is(':checked')))
                    {
                        value=0;
                    }
                    else
                        value=1;

                    if($j("input#qnty3").val()!="")
                        value=1;
                    else
                        value=0;
                }
                if(t==5) {

                    if ((!$j("#821").is(':checked')))
                    {
                        value=0;
                    }
                    else
                        value=1;

                    if($j("input#qnty5").val()!="")
                        value=1;
                    else
                        value=0;
                }
                if(t==4) {

                    if ((!$j("#8823").is(':checked'))&&(!$j("#78553").is(':checked'))&&(!$j("#53553").is(':checked')))
                    {
                        value=0;
                    }
                    else
                        value=1;

                    if($j("input#qnty4").val()!="")
                        value=1;
                    else
                        value=0;
                }
                if(t==1) {

                    if ((!$j("#1439").is(':checked'))&&(!$j("#836").is(':checked')))
                    {
                        value=0;
                    }
                    else
                        value=1;


                    if($j("input#qnty1").val()!="")
                        value=1;
                    else
                        value=0;

                }
                if(t==2) {

                    if ((!$j("#8395").is(':checked'))&&(!$j("#8396").is(':checked')))
                    {
                        value=0;
                    }
                    else
                        value=1;

                    if ((!$j("#5334").is(':checked'))&&(!$j("#6670").is(':checked')&&(!$j("#8397").is(':checked'))))
                    {
                        value=0;
                    }
                    else
                        value=1;

                    if($j("input#qnty2").val()!="")
                        value=1;
                    else
                        value=0;
                }


                continue;

            }


        }




    }

    message="";



    if ($j("#datePicker").val()=="") {

        message+="Give Encounter date "+"<br/>";
    }

    if ($j("#nextvisit").val()=="") {
        message+="No Next visit date "+"<br/>";

    }

    if ($j("#prescriber").val()=="") {
        message+="Give the  prescriber "+"<br/>";

    }





    return value;





}



$j("input[name=ObsDrug*1895#10]").live("focus", function () {


    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },

        source:function (request, response) {

            dataString = "searchDrug=" + request.term;

            $j.getJSON("drugDetails.form?drop=drop&bar=bar&" + dataString, function (result) {

                $j("#dispensedrug").removeClass('working');

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
            //  binTable.fnFilter( ui.item.label );
            // log( ui.item ?
            // "Selected: " + ui.item.label :
            // "Nothing selected, input was " + this.value);
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });






});



var check1=0;
var check2=0;
var check3=0;
var check4=0;


var numbers = [
    [4,8] ,
    [22, 564, 987, 456],
    [4,8,10],
    [4, 8,9] ];



function checkRegimen(one, two,three,four){

    for (var index = 0; index < numbers.length; index++)
    {

        var size =numbers[index].length;


        if(size==1)
        {
            {


                for (var innerIndex = 0; innerIndex < numbers[index].length; innerIndex++)
                {

                    if(innerIndex==0 && numbers[index][0]!=0)
                    {

                        if(numbers[index][innerIndex]==one){

                            check1=1;

                        }
                    }




                }


                if(check1==1 )
                {
                    return true;

                    break;


                }
            }

        }
        else if(size==2)
        {


            for (var innerIndex = 0; innerIndex < numbers[index].length; innerIndex++)
            {

                if(innerIndex==0 && numbers[index][0]!=0)
                {

                    if(numbers[index][innerIndex]==one){

                        check1=1;

                    }
                }

                if(innerIndex==1 && numbers[index][1]!=0)
                {

                    if(numbers[index][innerIndex]==two){

                        check2=1;

                    }
                }


            }


            if(check1==1&&check2==1 )
            {
                return true;

                break;


            }else
            {

                var newr= new Array();

                newr=numbers[index].reverse();


                for (var innerIndex = 0; innerIndex <newr.length; innerIndex++)
                {

                    if(innerIndex==0 && newr[0]!=0)
                    {

                        if(newr[innerIndex]==one){

                            check1=1;

                        }
                    }

                    if(innerIndex==1 && newr[1]!=0)
                    {

                        if(newr[innerIndex]==two){

                            check2=1;

                        }
                    }
                }

                if(check1==1&&check2==1 )
                {
                    return true;
                    break;

                }

            }

        }
        else if(size==3)
        {

            {


                for (var innerIndex = 0; innerIndex < numbers[index].length; innerIndex++)
                {

                    if(innerIndex==0 && numbers[index][0]!=0)
                    {

                        if(numbers[index][innerIndex]==one){

                            check1=1;

                        }
                    }

                    if(innerIndex==1 && numbers[index][1]!=0)
                    {

                        if(numbers[index][innerIndex]==two){

                            check2=1;

                        }
                    }

                    if(innerIndex==2 && numbers[index][2]!=0)
                    {
                        if(numbers[index][2]==three){

                            check3=1;

                        }
                    }


                }


                if(check1==1&&check2==1&&check3==1 )
                {

                    return true;
                    break;


                }else
                {

                    var newr= new Array();

                    newr=numbers[index].reverse();


                    for (var innerIndex = 0; innerIndex <newr.length; innerIndex++)
                    {

                        if(innerIndex==0 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==one){

                                check1=1;

                            }
                        }

                        if(innerIndex==1 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==two){

                                check2=1;

                            }
                        }
                        if(innerIndex==2 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==three
                                ){

                                check3=1;

                            }
                        }


                    }

                    if(check1==1&&check2==1&&check3==1)
                    {
                        return true;
                        break;

                    }

                }

            }
        }
        else if(size==4)
        {

            {


                for (var innerIndex = 0; innerIndex < numbers[index].length; innerIndex++)
                {

                    if(innerIndex==0 && numbers[index][0]!=0)
                    {

                        if(numbers[index][innerIndex]==one){

                            check1=1;

                        }
                    }

                    if(innerIndex==1 && numbers[index][1]!=0)
                    {

                        if(numbers[index][innerIndex]==two){

                            check2=1;

                        }
                    }

                    if(innerIndex==2 && numbers[index][2]!=0)
                    {
                        if(numbers[index][2]==three){

                            check3=1;

                        }
                    }

                    if(innerIndex==3 && numbers[index][3]!=0)
                    {
                        if(numbers[index][innerIndex]==four){

                            check4=1;

                        }
                    }
                }


                if(check1==1&&check2==1&&check3==1&&check4==1 )
                {

                    return true;
                    break;


                }else
                {

                    var newr= new Array();

                    newr=numbers[index].reverse();


                    for (var innerIndex = 0; innerIndex <newr.length; innerIndex++)
                    {

                        if(innerIndex==0 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==one){

                                check1=1;

                            }
                        }

                        if(innerIndex==1 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==two){

                                check2=1;

                            }
                        }
                        if(innerIndex==2 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==three
                                ){

                                check3=1;

                            }
                        }

                        if(innerIndex==3 && newr[innerIndex]!=0)
                        {

                            if(newr[innerIndex]==four){

                                check3=1;

                            }
                        }
                    }

                    if(check1==1&&check2==1&&check3==1&&check4==1 )
                    {
                        return true;
                        break;

                    }

                }

            }
        }

    }

}







