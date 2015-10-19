$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j("#nextvisit").datepicker();
var pRegimen;
var cRegimen;
$j("input[title='drug']").live('click',function(){
    if($j(this).attr('checked')==true){
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
                    type:"GET",
                    url:"checkDrugAvailability.form",
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
var row1Dose = {
    dose1: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "3mL BD",
        "dosageID": "1"
    }, {
        "dosageName": "4mL BD",
        "dosageID": "2"
    }, {
        "dosageName": "6mL BD",
        "dosageID": "3"
    },
        {
            "dosageName": "7mL BD",
            "dosageID": "4"
        },
        {
            "dosageName": "1/2 tab BD",
            "dosageID": "5"
        },
        {
            "dosageName": "9mL BD",
            "dosageID": "6"
        },
        {
            "dosageName": "1 tab am 1/2 tab pm",
            "dosageID": "7"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "8"
        }]
};

$j.each(row1Dose.dose1, function (key, value) {
    //$j("#dose1").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose2").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');

});
var row3Dose = {
    dose3: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "6mL BD",
        "dosageID": "1"
    }, {
        "dosageName": "9mL BD",
        "dosageID": "2"
    }, {
        "dosageName": "12mL BD",
        "dosageID": "3"
    },
        {
            "dosageName": "15mL BD",
            "dosageID": "4"
        },
        {
            "dosageName": "1/2 tab BD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 tab am 1/2 tab pm",
            "dosageID": "6"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "7"
        }]
};
$j.each(row3Dose.dose3, function (key, value) {
    $j("#dose3").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');

});
var row4Dose = {
    dose4: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "2mL OD",
        "dosageID": "1"
    }, {
        "dosageName": "1/2 tab OD",
        "dosageID": "2"
    }, {
        "dosageName": "4mL OD",
        "dosageID": "3"
    },
        {
            "dosageName": "1 tab OD",
            "dosageID": "4"
        },
        {
            "dosageName": "5ml OD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 tab OD",
            "dosageID": "6"
        },
        {
            "dosageName": "5ml OD",
            "dosageID": "7"
        },
        {
            "dosageName": "6ml OD",
            "dosageID": "8"
        },
        {
            "dosageName": "1 1/2 tab OD",
            "dosageID": "9"
        },
        {
            "dosageName": "2 tab OD",
            "dosageID": "8"
        }]
};
$j.each(row4Dose.dose4, function (key, value) {
    $j("#dose6").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');

});
var row5Dose = {
    dose5: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "5mL BD",
        "dosageID": "1"
    }, {
        "dosageName": "1 tab BD",
        "dosageID": "2"
    }, {
        "dosageName": "8ml BD",
        "dosageID": "3"
    },
        {
            "dosageName": "1.5 tab BD",
            "dosageID": "4"
        },
        {
            "dosageName": "10ml BD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "6"
        },
        {
            "dosageName": "2 tab BD",
            "dosageID": "7"
        },
        {
            "dosageName": "1.5 tab BD",
            "dosageID": "8"
        },
        {
            "dosageName": "12ml BD",
            "dosageID": "9"
        },
        {
            "dosageName": "2.5 tab BD",
            "dosageID": "8"
        },
        {
            "dosageName": "3 tab BD",
            "dosageID": "8"
        }]
};
$j.each(row5Dose.dose5, function (key, value) {
    $j("#dose7").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');

});
var row6Dose = {
    dose6: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1 cap OD",
        "dosageID": "1"
    }, {
        "dosageName": "250mg OD",
        "dosageID": "2"
    }, {
        "dosageName": "300mg OD",
        "dosageID": "3"
    },
        {
            "dosageName": "350mg tab BD",
            "dosageID": "4"
        }]
};
$j.each(row6Dose.dose6, function (key, value) {
    $j("#dose8").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');

});
var row7Dose = {
    dose7: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1 tab BD",
        "dosageID": "1"
    }, {
        "dosageName": "1.5 tabs BD",
        "dosageID": "2"
    }, {
        "dosageName": "2 tabs BD",
        "dosageID": "3"
    },
        {
            "dosageName": "2.5 tabs BD",
            "dosageID": "4"
        },
        {
            "dosageName": "3 tabs BD",
            "dosageID": "5"
        }]
};
$j.each(row7Dose.dose7, function (key, value) {
    $j("#dose9").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose10").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose11").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row10Dose = {
    dose10: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "0.5 tab BD",
        "dosageID": "1"
    }, {
        "dosageName": "1 tab am 0.5 tab pm",
        "dosageID": "2"
    }, {
        "dosageName": "1 tab BD",
        "dosageID": "3"
    },
        {
            "dosageName": "1.5 tabs am 1 tab pm",
            "dosageID": "4"
        },
        {
            "dosageName": "1.5 tab BD",
            "dosageID": "5"
        }]
};
$j.each(row10Dose.dose10, function (key, value) {
    $j("#dose12").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row11Dose = {
    dose11: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "50mg tab BD",
        "dosageID": "1"
    }, {
        "dosageName": "50mg BD + 25 mg BD",
        "dosageID": "2"
    }, {
        "dosageName": "100mg am 75mg pm",
        "dosageID": "3"
    },
        {
            "dosageName": "100mg (2 tabs) BD",
            "dosageID": "4"
        },
        {
            "dosageName": "1 tab OD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "6"
        },
        {
            "dosageName": "400mg EC cap OD",
            "dosageID": "7"
        }]
};
$j.each(row11Dose.dose11, function (key, value) {
    $j("#dose13").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row12Dose = {
    dose12: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1ml BD",
        "dosageID": "1"
    }, {
        "dosageName": "1.5ml BD",
        "dosageID": "2"
    }, {
        "dosageName": "2ml BD",
        "dosageID": "3"
    },
        {
            "dosageName": "100mg (2 tabs) BD",
            "dosageID": "4"
        },
        {
            "dosageName": "2.5ml BD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "6"
        },
        {
            "dosageName": "3ml BD",
            "dosageID": "7"
        },
        {
            "dosageName": "1 tab BD",
            "dosageID": "8"
        },
        {
            "dosageName": "3.5ml BD",
            "dosageID": "9"
        },
        {
            "dosageName": "2 tabs am 1 tab pm",
            "dosageID": "10"
        },
        {
            "dosageName": "2 tabs BD",
            "dosageID": "11"
        }]
};
$j.each(row12Dose.dose12, function (key, value) {
    $j("#dose14").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row13Dose = {
    dose13: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1 tab OD",
        "dosageID": "1"
    }]
};
$j.each(row13Dose.dose13, function (key, value) {
    $j("#dose15").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose16").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row15Dose = {
    dose15: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1 tab BD",
        "dosageID": "1"
    }]
};
$j.each(row15Dose.dose15, function (key, value) {
    $j("#dose17").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose18").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose19").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose20").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    $j("#dose21").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row20Dose = {
    dose20: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "2.5ml BD",
        "dosageID": "1"
    }, {
        "dosageName": "1/2 tab BD",
        "dosageID": "2"
    }, {
        "dosageName": "5ml BD",
        "dosageID": "3"
    },
        {
            "dosageName": "10ml BD",
            "dosageID": "4"
        },
        {
            "dosageName": "1/2 DS tab OD",
            "dosageID": "5"
        },
        {
            "dosageName": "1 SS tab OD",
            "dosageID": "6"
        },
        {
            "dosageName": "2 SS tab OD",
            "dosageID": "7"
        }]
};
$j.each(row20Dose.dose20, function (key, value) {
    $j("#dose22").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row21Dose = {
    dose21: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1/2 tab weekly",
        "dosageID": "1"
    }, {
        "dosageName": "1/2 tab OD",
        "dosageID": "2"
    }, {
        "dosageName": "1 tab OD",
        "dosageID": "3"
    }]
};
$j.each(row21Dose.dose21, function (key, value) {
    $j("#dose23").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var row22Dose = {
    dose22: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1 tab OD",
        "dosageID": "1"
    }, {
        "dosageName": "2 tab OD",
        "dosageID": "2"
    }, {
        "dosageName": "3 tab OD",
        "dosageID": "3"
    }]
};
$j.each(row22Dose.dose22, function (key, value) {
    $j("#dose24").append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
});
var id = jQuery.Pid.id;
var formDose;
$j('#nextvisit').change(
    function () {
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

$j('#noofmonths')
    .change(
    function () {
        var inputTags = document.getElementsByTagName('INPUT');
        var givenDays = document.getElementById("noofmonths").value;
        quantity = givenDays;
        var date = new Date();
        var d = addays(date, givenDays);
        var curr_date = d.getDate();
        var curr_month = d.getMonth() + 1;
        var curr_year = d.getFullYear();
        var dateFinal = curr_month + "/" + curr_date + "/"
            + curr_year;

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
}); */

$j('#row1d')
    .change(
    function () {
        var quantityPrescribed = document.getElementById("row1d").value;
        var qnty=  parseInt(quantityPrescribed)*60;
        document.getElementById("row1d").value =qnty;
    });

$j('#row1o')
    .change(
    function () {
        var quantityPrescribed = document.getElementById("row1o").value;
        var qnty=  parseInt(quantityPrescribed)*2;
        document.getElementById("row1o").value =qnty;
    });
$j('#row1m')
    .change(
    function () {
        var quantityPrescribed = document.getElementById("row1m").value;
        var qnty=  parseInt(quantityPrescribed)*60;
        document.getElementById("row1m").value =qnty;
    });
$j.getJSON("dispense.form?encounter=enc&Pen=" + id+"&formtype="+formType, function(result) {
    if(jQuery.isEmptyObject(result)){
        pRegimen="";
        $j("#infodiv").append("Not given");
        //document.getElementById('pregimen').style.color = 'blue';
        $j("#infodiv").css("color","blue");
    }
    else{
        $j.each(result, function(key, val) {
            var strv= val.toString();
            var numbersArray = strv.split(",");
            pRegimen=strv;
            $j("#infodiv").html(strv);
            $j("#infodiv").css("color","blue");

        });
    }
});
function checkHivPedsForm(val){
    cRegimen=val;
    if(cRegimen!=pRegimen && pRegimen=="")
    {
        if($j("#regimenchange").is(':checked') || ($j("#arvtype3").is(':checked') )|| ($j("#arvtype7").is(':checked') ))  {
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

$j('#weight') .change(
    function () {
        var quantityPrescribed = document.getElementById("weight").value;
        if(quantityPrescribed<5 ) {
            row6FirstOption();
        }
        else if(quantityPrescribed>=5 &&  quantityPrescribed<=9.9) {
            row6SecondOption();

        }else if(quantityPrescribed>=10 &&  quantityPrescribed<=13.9) {
            row6ThirdOption();

        }else if(quantityPrescribed>=14 &&  quantityPrescribed<=19.9) {
            row6FourthOption();


        }else if(quantityPrescribed>=20 &&  quantityPrescribed<=24.9) {
            row6FifthOption();

        }else if(quantityPrescribed>=25 &&  quantityPrescribed<=29.9) {
            row6SixthOption();

        }else if(quantityPrescribed>=30 &&  quantityPrescribed<=39.9) {
            row6SeventhOption();
        }
        else if(quantityPrescribed>=40) {
            row6EightOption();

        }

        if(quantityPrescribed>=3 &&  quantityPrescribed<=5.9 ) {
            row12FirstOption();
        }
        else if(quantityPrescribed>=6 &&  quantityPrescribed<=9.9) {
            row12SecondOption();

        }else if(quantityPrescribed>=10 &&  quantityPrescribed<=13.9) {
            row12ThirdOption();

        }else if(quantityPrescribed>=14 &&  quantityPrescribed<=19.9) {
            row12FourthOption();


        }else if(quantityPrescribed>=20 &&  quantityPrescribed<=24.9) {
            row12FifthOption();

        }else if(quantityPrescribed>=25 &&  quantityPrescribed<=29.9) {
            row12SixthOption();

        }else if(quantityPrescribed>=30) {
            row12SeventhOption();
        }





        if(quantityPrescribed>=5 &&  quantityPrescribed<=9.9) {
            row11SecondOption();

        }else if(quantityPrescribed>=10 &&  quantityPrescribed<=10.9) {
            row11ThirdOption();

        }else if(quantityPrescribed>=11 &&  quantityPrescribed<=13.9) {
            row11FourthOption();


        }else if(quantityPrescribed>=14 &&  quantityPrescribed<=16.9) {
            row11FifthOption();

        }else if(quantityPrescribed>=17 &&  quantityPrescribed<=19.9) {
            row11SixthOption();

        }else if(quantityPrescribed>=20 &&  quantityPrescribed<=60) {
            row11SeventhOption();
        }
        else if(quantityPrescribed>60) {
            row11EightOption();

        }


        if(quantityPrescribed>=3 &&  quantityPrescribed<=5.9) {
            row1FirstOption();
            row2FirstOption();
            row3FirstOption();
            row51FirstOption();
            row5FirstOption();
            row7FirstOption();
            row8FirstOption();
            row9FirstOption();
            row10FirstOption();

        }
        else  if(quantityPrescribed>=6 &&  quantityPrescribed<=9.9) {
            row1SecondOption();
            row2SecondOption();
            row3SecondOption();
            row51SecondOption();
            row5SecondOption();
            row7SecondOption();
            row8SecondOption();
            row9SecondOption();
            row10SecondOption();
        }
        else  if(quantityPrescribed>=10 &&  quantityPrescribed<=13.9) {
            row1ThirdOption();

            row2ThirdOption();

            row3ThirdOption();
            row51ThirdOption();
            row5ThirdOption();
            row7ThirdOption();
            row8ThirdOption();
            row9ThirdOption();
            row10ThirdOption();


        }   else  if(quantityPrescribed>=14 &&  quantityPrescribed<=19.9) {

            row1FourthOption();

            row2FourthOption();

            row3FourthOption();
            row51FourthOption();
            row5FourthOption();
            row7FourthOption();
            row8FourthOption();
            row9FourthOption();
            row10FourthOption();

        }
        else  if(quantityPrescribed>=20 &&  quantityPrescribed<=24.9) {
            row1FifthOption();
            row2FifthOption();
            row3FifthOption();
            row51FifthOption();
            row5FifthOption();
            row7FifthOption();
            row8FifthOption();
            row9FifthOption();
            row10FifthOption();

        }
        else  if(quantityPrescribed>=25) {
            row1SixthOption();
            row2SixthOption();
            row3SixthOption();
            row51SixthOption();
            row5SixthOption();
        }


        if(quantityPrescribed<5) {

            row20FifthOption();
        }
        else  if(quantityPrescribed>=5 &&  quantityPrescribed<=9) {
            row20FirstOption();

        }
        else  if(quantityPrescribed>=10 &&  quantityPrescribed<=15) {
            row20SecondOption();


        }   else  if(quantityPrescribed>15 &&  quantityPrescribed<=30) {
            row20ThirdOption();

        }
        else  if(quantityPrescribed>30) {
            row20FourthOption();

        }
        if(quantityPrescribed>=8 &&  quantityPrescribed<=14.9) {
            row21FirstOption();
        }
        else  if(quantityPrescribed>=15 &&  quantityPrescribed<=30) {

            row21SecondOption();


        }   else  if(quantityPrescribed>30) {

            row21ThirdOption();


        }
        if(quantityPrescribed>=5 &&  quantityPrescribed<=10) {

            row22SecondOption();

        }
        else  if(quantityPrescribed>10 &&  quantityPrescribed<=20) {


            row22ThirdOption();


        }   else  if(quantityPrescribed>20) {

            row22FourthOption();


        }




    });
$j("#1,#2,#3,#4,#5,#6 , #1f, #2f, #3f, #4fa,#4fb,#5fa,#5fb,#6f,#doseLabel1,#doseLabel2,#doseLabel3,#dose5,#dose6,#dose7,#dose8").hide()

$j('label[for="1"],label[for="2"],label[for="3"],label[for="4"],label[for="5"] ,label[for="6"],label[for="1f"],label[for="2f"],label[for="3f"],label[for="4fa"],label[for="4fb"],label[for="5fa"],label[for="5fb"],label[for="6f"],label[for="dose4"],label[for="dose5"],label[for="dose6"],label[for="dose7"],label[for="dose8"]').hide();

$j("#1d,#2d, #3d, #4da,#4db,#5da,#5db,#6d").hide()

$j('label[for="1d"],label[for="2d"],label[for="3d"],label[for="4da"],label[for="4db"],label[for="5da"], label[for="5db"],label[for="6d"]').hide();

$j("#1ma,#1mb,#1mc, #2ma,#2mb,#2mc, #3ma,#3mb,#3mc, #4ma,#4mb,#4mc, #4ma1,#4mb2,#4mc3, #5ma,#5mb,#5mc, #5ma1,#5mb2,#5mc3,#6ma,#6mb,#6mc,").hide()

$j('label[for="1ma"],label[for="1mb"],label[for="1mc"],label[for="2ma"],label[for="2mb"],label[for="6ma"],label[for="6mb"],label[for="6mc"],label[for="5ma1"],label[for="5mb2"],label[for="5mc3"],label[for="5ma"],label[for="5mb"],label[for="5mc"], label[for="4ma1"],label[for="4mb2"],label[for="4mc3"],label[for="2mc"],label[for="3ma"],label[for="3mb"],label[for="3mc"],label[for="4ma"],label[for="4mb"],label[for="4mc"]').hide();

function row1FirstOption() {

    $j("#1,#1f, #1d, #1ma, #1mb,#1mc,#doseLabel1").show()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"]').show();

    $j("#2,#2f, #2d, #2ma, #2mb,#2mc,#doseLabel2,#doseLabel3,#dose4,#dose5,#dose6,#dose7").hide()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"], label[for="dose4"], label[for="dose5"], label[for="dose6"], label[for="dose7"]').hide();

    $j("#3,#3f, #3d, #3ma, #3mb,#3mc").hide()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').hide();

    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3").hide()

    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"], label[for="dose4"], label[for="dose5"], label[for="dose6"], label[for="dose7"]').hide();

    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").hide()

    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5dd"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"]').hide();

    $j("#6,#6f, #6d, #6ma, #6mb,#6mc").hide()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"]').hide();

}
function row1SecondOption() {
    $j("#2,#2f, #2d, #2ma, #2mb,#2mc,#doseLabel2").show()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"]').show();


    $j("#1,#1f, #1d, #1ma, #1mb,#1mc,#doseLabel1,#doseLabel3,#dose4,#dose5,#dose6,#dose7").hide()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"],label[for="dose4"],label[for="dose5"],label[for="dose6"],label[for="dose7"]').hide();


    $j("#3,#3f, #3d, #3ma, #3mb,#3mc").hide()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').hide();

    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3").hide()

    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"]').hide();

    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").hide()

    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5da"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"]').hide();

    $j("#6,#6f, #6d, #6ma, #6mb,#6mc").hide()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"]').hide();


}
function row1ThirdOption() {
    $j("#3,#3f, #3d, #3ma, #3mb,#3mc,#doseLabel3").show()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').show();

    $j("#1,#1f, #1d, #1ma, #1mb,#1mc,#doseLabel1,#doseLabel2,#dose4,#dose5,#dose6,#dose7").hide()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"],label[for="dose4"],label[for="dose5"],label[for="dose6"],label[for="dose7"]').hide();


    $j("#2,#2f, #2d, #2ma, #2mb,#2mc").hide()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"]').hide();


    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3").hide()

    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"]').hide();

    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").hide()

    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5da"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"]').hide();

    $j("#6,#6f, #6d, #6ma, #6mb,#6mc").hide()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"]').hide();


}

function row1FourthOption() {
    $j("#1,#1f, #1d, #1ma, #1mb,#1mc,#doseLabel1,#doseLabel2,#doseLabel3,#dose6,#dose7").hide()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"]').hide();

    $j("#2,#2f, #2d, #2ma, #2mb,#2mc").hide()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"],label[for="dose6"],label[for="dose7"]').hide();

    $j("#3,#3f, #3d, #3ma, #3mb,#3mc").hide()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').hide();

    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3,#dose4,#dose5").show()
    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"], label[for="dose4"],label[for="dose5"]').show();

    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").hide()

    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5da"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"]').hide();

    $j("#6,#6f, #6d, #6ma, #6mb,#6mc").hide()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"]').hide();


}
function row1FifthOption() {
    $j("#1,#1f, #1d, #1ma, #1mb,#1mc#doseLabel1,#doseLabel2,#doseLabel3,#dose4,#dose5").hide()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"],label[for="dose4"],label[for="dose5"]').hide();

    $j("#2,#2f, #2d, #2ma, #2mb,#2mc").hide()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"]').hide();

    $j("#3,#3f, #3d, #3ma, #3mb,#3mc").hide()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').hide();

    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3").hide()
    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"]').hide();



    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").show()
    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5da"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"], label[for="dose6"],label[for="dose7"]').show();


    $j("#6,#6f, #6d, #6ma, #6mb,#6mc").hide()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"]').hide();


}
function row1SixthOption() {
    $j("#6,#6f, #6d, #6ma, #6mb,#6mc,#dose8").show()
    $j('label[for="6"],label[for="6f"],label[for="6d"],label[for="6ma"],label[for="6mb"], label[for="6mc"], label[for="dose8"]').show();

    $j("#1,#1f, #1d, #1ma, #1mb,#1mc,#doseLabel1,#doseLabel2,#doseLabel3,#dose6,#dose7,#dose4,#dose5").hide()
    $j('label[for="1"],label[for="1f"],label[for="1d"],label[for="1ma"],label[for="1mb"], label[for="1mc"],label[for="dose4"],label[for="dose5"],label[for="dose6"],label[for="dose7"]').hide();

    $j("#2,#2f, #2d, #2ma, #2mb,#2mc").hide()
    $j('label[for="2"],label[for="2f"],label[for="2d"],label[for="2ma"],label[for="2mb"], label[for="2mc"]').hide();

    $j("#3,#3f, #3d, #3ma, #3mb,#3mc").hide()
    $j('label[for="3"],label[for="3f"],label[for="3d"],label[for="3ma"],label[for="3mb"], label[for="3mc"]').hide();

    $j("#4,#4fa,#4fb, #4da,#4db, #4ma, #4mb,#4mc,#4ma1, #4mb2,#4mc3").hide()
    $j('label[for="4"],label[for="4fa"],label[for="4fb"],label[for="4da"],label[for="4db"], label[for="4ma"], label[for="4mb"], label[for="4mc"], label[for="4ma1"], label[for="4mb2"], label[for="4mc3"]').hide();



    $j("#5,#5fa,#5fb, #5da, #5db, #5ma, #5mb,#5mc,#5ma1, #5mb2,#5mc3").hide()
    $j('label[for="5"],label[for="5fa"],label[for="5fb"],label[for="5da"],label[for="5db"], label[for="5ma"], label[for="5mb"], label[for="5mc"], label[for="5ma1"], label[for="5mb2"], label[for="5mc3"]').hide();

}

////////////////////////////////////


$j("#11,#12,#13,#14,#15,#16 , #11f,, #12f, #13f, #14fa,#14fb,#15fa,#15fb,#16f,#doseLabel2a,#doseLabel2b,#doseLabel2c,#dose2e,#dose2f,#dose2g,#dose2h,#dose2i").hide()

$j('label[for="11"],label[for="12"],label[for="13"],label[for="14"],label[for="15"] ,label[for="16"],label[for="11f"],label[for="12f"],label[for="13f"],label[for="14fa"],label[for="14fb"],label[for="15fa"],label[for="15fb"],label[for="16f"]').hide();

$j("#11d,, #12d, #13d, #14da,#14db,#15da,#15db,#16d").hide()

$j('label[for="11d"],label[for="12d"],label[for="13d"],label[for="14da"],label[for="14db"],label[for="15da"], label[for="15db"],label[for="16d"],label[for="dose2e"],label[for="dose2f"],label[for="dose2g"],label[for="dose2h"],label[for="dose2i"]').hide();

$j("#11ma,#11mb,#11mc, #12ma,#12mb,#12mc, #13ma,#13mb,#13mc, #14ma,#14mb,#14mc, #14ma1,#14mb2,#14mc3, #15ma,#15mb,#15mc, #15ma1,#15mb2,#15mc3,#16ma,#16mb,#16mc,").hide()

$j('label[for="11ma"],label[for="11mb"],label[for="11mc"],label[for="12ma"],label[for="12mb"],label[for="16ma"],label[for="16mb"],label[for="16mc"],label[for="15ma1"],label[for="15mb2"],label[for="15mc3"],label[for="15ma"],label[for="15mb"],label[for="15mc"], label[for="14ma1"],label[for="14mb2"],label[for="14mc3"],label[for="12mc"],label[for="13ma"],label[for="13mb"],label[for="13mc"],label[for="14ma"],label[for="14mb"],label[for="14mc"]').hide();
function row2FirstOption() {

    $j("#11,#11f, #11d, #11ma, #11mb,#11mc,#doseLabel2a").show()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"]').show();

    $j("#12,#12f, #12d, #12ma, #12mb,#12mc,#doseLabel2b,#doseLabel2c,#dose2c,#dose2f,#dose2g,dose2h,#dose2i").hide()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"]').hide();

    $j("#13,#13f, #13d, #13ma, #13mb,#13mc").hide()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"]').hide();

    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3").hide()

    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"],label[for="dose2e"],label[for="dose2f"],label[for="dose2g"],label[for="dose2h"],label[for="dose2i"]').hide();

    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3").hide()

    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15dd"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"]').hide();

    $j("#16,#16f, #16d, #16ma, #16mb,#16mc").hide()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"]').hide();

}
function row2SecondOption() {
    $j("#12,#12f, #12d, #12ma, #12mb,#12mc,#doseLabel2b").show()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"]').show();


    $j("#11,#11f, #11d, #11ma, #11mb,#11mc,#doseLabel2a,#doseLabel2c,#dose2c,#dose2f,#dose2g,dose2h,#dose2i").hide()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"]').hide();


    $j("#13,#13f, #13d, #13ma, #13mb,#13mc").hide()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"],label[for="dose2e"],label[for="dose2f"],label[for="dose2g"],label[for="dose2h"],label[for="dose2i"]').hide();

    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3").hide()

    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"]').hide();

    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3").hide()

    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15da"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"]').hide();

    $j("#16,#16f, #16d, #16ma, #16mb,#16mc").hide()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"]').hide();

}function row2ThirdOption() {
    $j("#13,#13f, #13d, #13ma, #13mb,#13mc,#doseLabel2c").show()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"]').show();

    $j("#11,#11f, #11d, #11ma, #11mb,#11mc,#doseLabel2a,#doseLabel2b,#dose2c,#dose2f,#dose2g,dose2h,#dose2i").hide()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"]label[for="dose2e"],label[for="dose2f"],label[for="dose2g"],label[for="dose2h"],label[for="dose2i"]').hide();


    $j("#12,#12f, #12d, #12ma, #12mb,#12mc").hide()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"]').hide();


    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3").hide()

    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"]').hide();

    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3").hide()

    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15da"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"]').hide();

    $j("#16,#16f, #16d, #16ma, #16mb,#16mc").hide()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"]').hide();


}function row2FourthOption() {
    $j("#11,#11f, #11d, #11ma, #11mb,#11mc,#doseLabel2a,#doseLabel2b,#doseLabel2c,#dose2g,#dose2h,#dose2i").hide()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"]').hide();

    $j("#12,#12f, #12d, #12ma, #12mb,#12mc").hide()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"], label[for="dose2g"], label[for="dose2h"], label[for="dose2i"]').hide();

    $j("#13,#13f, #13d, #13ma, #13mb,#13mc").hide()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"]').hide();

    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3,#dose2e,#dose2f").show()
    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"],label[for="dose2e"],label[for="dose2f"]').show();

    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3").hide()

    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15da"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"]').hide();

    $j("#16,#16f, #16d, #16ma, #16mb,#16mc").hide()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"]').hide();


}function row2FifthOption() {
    $j("#11,#11f, #11d, #11ma, #11mb,#11mc,#doseLabel2a,#doseLabel2b,#doseLabel2c,#dose2e,#dose2f,#dose2i").hide()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"]').hide();

    $j("#12,#12f, #12d, #12ma, #12mb,#12mc").hide()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"], label[for="dose2e"], label[for="dose2f"], label[for="dose2i"]').hide();

    $j("#13,#13f, #13d, #13ma, #13mb,#13mc").hide()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"]').hide();

    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3").hide()
    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"]').hide();



    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3,#dose2g,#dose2h").show()
    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15da"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"], label[for="dose2g"], label[for="dose2h"]').show();


    $j("#16,#16f, #16d, #16ma, #16mb,#16mc").hide()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"]').hide();

}function row2SixthOption() {
    $j("#16,#16f, #16d, #16ma, #16mb,#16mc,#dose2i").show()
    $j('label[for="16"],label[for="16f"],label[for="16d"],label[for="16ma"],label[for="16mb"], label[for="16mc"],label[for="dose2i"]').show();

    $j("#11,#11f, #11d, #11ma, #11mb,#11mc").hide()
    $j('label[for="11"],label[for="11f"],label[for="11d"],label[for="11ma"],label[for="11mb"], label[for="11mc"],#doseLabel2a,#doseLabel2b,#doseLabel2c,#dose2e,#dose2f,#dose2g,#dose2h').hide();

    $j("#12,#12f, #12d, #12ma, #12mb,#12mc").hide()
    $j('label[for="12"],label[for="12f"],label[for="12d"],label[for="12ma"],label[for="12mb"], label[for="12mc"]').hide();

    $j("#13,#13f, #13d, #13ma, #13mb,#13mc").hide()
    $j('label[for="13"],label[for="13f"],label[for="13d"],label[for="13ma"],label[for="13mb"], label[for="13mc"]').hide();

    $j("#14,#14fa,#14fb, #14da,#14db, #14ma, #14mb,#14mc,#14ma1, #14mb2,#14mc3").hide()
    $j('label[for="14"],label[for="14fa"],label[for="14fb"],label[for="14da"],label[for="14db"], label[for="14ma"], label[for="14mb"], label[for="14mc"], label[for="14ma1"], label[for="14mb2"], label[for="14mc3"], label[for="dose2e"], label[for="dose2f"], label[for="dose2g"],label[for="dose2h"]').hide();



    $j("#15,#15fa,#15fb, #15da, #15db, #15ma, #15mb,#15mc,#15ma1, #15mb2,#15mc3").hide()
    $j('label[for="15"],label[for="15fa"],label[for="15fb"],label[for="15da"],label[for="15db"], label[for="15ma"], label[for="15mb"], label[for="15mc"], label[for="15ma1"], label[for="15mb2"], label[for="15mc3"]').hide();

}


////////////////////////////////////////////////////////////



$j("#21,#22,#23,#24,#25,#26 , #21f,, #22f, #23f, #24fa,#24fb,#25fa,#25fb,#26f,#doseLabel3b,#doseLabel3d,#doseLabel3f,#dose3g,#dose3h,#dose3i,#dose3j").hide()

$j('label[for="21"],label[for="22"],label[for="23"],label[for="24"],label[for="25"] ,label[for="26"],label[for="21f"],label[for="22f"],label[for="23f"],label[for="24fa"],label[for="24fb"],label[for="25fa"],label[for="25fb"],label[for="26f"],label[for="dose3g"],label[for="dose3h"],label[for="dose3i"],label[for="dose3j"]').hide();


$j("#21d,, #22d, #23d, #24da,#24db,#25da,#25db,#26d").hide()

$j('label[for="21d"],label[for="22d"],label[for="23d"],label[for="24da"],label[for="24db"],label[for="25da"], label[for="25db"],label[for="26d"]').hide();


$j("#21ma,#21mb,#21mc, #22ma,#22mb,#22mc, #23ma,#23mb,#23mc, #24ma,#24mb,#24mc, #24ma1,#24mb2,#24mc3, #25ma,#25mb,#25mc, #25ma1,#25mb2,#25mc3,#26ma,#26mb,#26mc,").hide()

$j('label[for="21ma"],label[for="21mb"],label[for="21mc"],label[for="22ma"],label[for="22mb"],label[for="26ma"],label[for="26mb"],label[for="26mc"],label[for="25ma1"],label[for="25mb2"],label[for="25mc3"],label[for="25ma"],label[for="25mb"],label[for="25mc"], label[for="24ma1"],label[for="24mb2"],label[for="24mc3"],label[for="22mc"],label[for="23ma"],label[for="23mb"],label[for="23mc"],label[for="24ma"],label[for="24mb"],label[for="24mc"]').hide();
function row3FirstOption() {
    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b").show()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], label[for="21mc"]').show();

    $j("#22,#22f, #22d, #22ma, #22mb,#22mc,#doseLabel3d,#doseLabel3f,#dose3g,#dose3h,#dose3i,#dose3j").hide()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], label[for="22mc"]').hide();

    $j("#23,#23f, #23d, #23ma, #23mb,#23mc").hide()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"], ' +
        'label[for="dose3g"], label[for="dose3h"], label[for="dose3i"], label[for="dose3j"]').hide();

    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3").hide()

    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], ' +
        'label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"]').hide();

    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3").hide()

    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], ' +
        'label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"]').hide();

    $j("#26,#26f, #26d, #26ma, #26mb,#26mc").hide()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"]').hide();

}
function row3SecondOption() {
    $j("#22,#22f, #22d, #22ma, #22mb,#22mc,#doseLabel3d").show()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], ' +
        'label[for="22mc"], label[for="dose3c"]').show();


    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b,#doseLabel3f,#dose3g,#dose3h,#dose3i,#dose3j").hide()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], ' +
        'label[for="21mc"],label[for="dose3g"], label[for="dose3h"], label[for="dose3i"], label[for="dose3j"]').hide();


    $j("#23,#23f, #23d, #23ma, #23mb,#23mc").hide()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"]').hide();

    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3").hide()

    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"]').hide();

    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3").hide()

    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"]').hide();

    $j("#26,#26f, #26d, #26ma, #26mb,#26mc").hide()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"]').hide();

}function row3ThirdOption() {
    $j("#23,#23f, #23d, #23ma, #23mb,#23mc,#doseLabel3f").show()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"]').show();

    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b,#doseLabel3d,#dose3g,#dose3h,#dose3i,#dose3j").hide()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], label[for="21mc"], ' +
        'label[for="dose3g"], label[for="dose3h"], label[for="dose3i"], label[for="dose3j"]').hide();


    $j("#22,#22f, #22d, #22ma, #22mb,#22mc").hide()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], label[for="22mc"]').hide();


    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3").hide()

    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"]').hide();

    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3").hide()

    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"]').hide();

    $j("#26,#26f, #26d, #26ma, #26mb,#26mc").hide()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"]').hide();


}function row3FourthOption() {
    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b,#doseLabel3d,#doseLabel3f,#dose3i,#dose3j").hide()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], label[for="21mc"], label[for="dose3i"], label[for="dose3j"]').hide();

    $j("#22,#22f, #22d, #22ma, #22mb,#22mc").hide()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], label[for="22mc"]').hide();

    $j("#23,#23f, #23d, #23ma, #23mb,#23mc").hide()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"]').hide();

    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3,#dose3g,#dose3h").show()
    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"], label[for="dose3g"], label[for="dose3h"]').show();

    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3").hide()

    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"]').hide();

    $j("#26,#26f, #26d, #26ma, #26mb,#26mc").hide()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"]').hide();


}function row3FifthOption() {
    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b,#doseLabel3d,#doseLabel3f,#dose3g,#dose3h,#dose3j").hide()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], label[for="21mc"], label[for="dose3g"], label[for="dose3j"],label[for="dose3h"]').hide();

    $j("#22,#22f, #22d, #22ma, #22mb,#22mc").hide()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], label[for="22mc"]').hide();

    $j("#23,#23f, #23d, #23ma, #23mb,#23mc").hide()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"]').hide();

    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3").hide()
    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"]').hide();



    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3,#dose3i").show()
    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"],label[for="dose3i"]').show();


    $j("#26,#26f, #26d, #26ma, #26mb,#26mc").hide()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"]').hide();

}function row3SixthOption() {
    $j("#26,#26f, #26d, #26ma, #26mb,#26mc,#dose3j").show()
    $j('label[for="26"],label[for="26f"],label[for="26d"],label[for="26ma"],label[for="26mb"], label[for="26mc"], label[for="dose3j"]').show();

    $j("#21,#21f, #21d, #21ma, #21mb,#21mc,#doseLabel3b,#doseLabel3d,#doseLabel3f,#dose3g,#dose3h,#dose3i").hide()
    $j('label[for="21"],label[for="21f"],label[for="21d"],label[for="21ma"],label[for="21mb"], label[for="21mc"], label[for="dose3g"], label[for="dose3i"],label[for="dose3h"]').hide();

    $j("#22,#22f, #22d, #22ma, #22mb,#22mc").hide()
    $j('label[for="22"],label[for="22f"],label[for="22d"],label[for="22ma"],label[for="22mb"], label[for="22mc"]').hide();

    $j("#23,#23f, #23d, #23ma, #23mb,#23mc").hide()
    $j('label[for="23"],label[for="23f"],label[for="23d"],label[for="23ma"],label[for="23mb"], label[for="23mc"]').hide();

    $j("#24,#24fa,#24fb, #24da,#24db, #24ma, #24mb,#24mc,#24ma1, #24mb2,#24mc3").hide()
    $j('label[for="24"],label[for="24fa"],label[for="24fb"],label[for="24da"],label[for="24db"], label[for="24ma"], label[for="24mb"], label[for="24mc"], label[for="24ma1"], label[for="24mb2"], label[for="24mc3"]').hide();



    $j("#25,#25fa,#25fb, #25da, #25db, #25ma, #25mb,#25mc,#25ma1, #25mb2,#25mc3").hide()
    $j('label[for="25"],label[for="25fa"],label[for="25fb"],label[for="25da"],label[for="25db"], label[for="25ma"], label[for="25mb"], label[for="25mc"], label[for="25ma1"], label[for="25mb2"], label[for="25mc3"]').hide();

}



////////////////////////////////////////////////////////////



$j("#41,#42,#43,#44,#45,#46 , #41fa,#41fb, #42fa, #42fb,#43fa,#43fb, #44fa,#44fb,#45f,#46f,#dose6a,#dose6b," +
    "#dose6c,#dose6d,#dose6e,#dose6f,#dose6g,#dose6h,#dose6i,#dose6j,#dose6k").hide()

$j('label[for="41"],label[for="42"],label[for="43"],label[for="44"],label[for="45"] ,label[for="46"],label[for="41fa"],label[for="41fb"],' +
    'label[for="42fa"],label[for="42fb"],label[for="43fa"],label[for="43fb"],label[for="44fa"],label[for="44fb"],' +
    'label[for="45f"],label[for="46f"],label[for="dose6a"],label[for="dose6b"],label[for="dose6c"],' +
    'label[for="dose6d"],label[for="dose6e"],label[for="dose6f"],label[for="dose6g"],label[for="dose6h"]' +
    ',label[for="dose6i"],label[for="dose6j"],label[for="dose6k"]').hide();


$j("#41da,#41db, #42da, #42db,#43da, #43db,#44da,#44db,#45d,#46d").hide()

$j('label[for="41da"],label[for="41db"],label[for="42da"],label[for="42db"],label[for="43da"],label[for="43db"],' +
    'label[for="44da"],label[for="44db"],label[for="45d"], label[for="46d"]').hide();


$j("#41ma1,#41ma2,#41mb1,#41mb2,#41mc1,#41mc2, #42ma1,#42ma2,#42mb1,#42mb2,#42mc1,#42mc2, " +
    "#43ma1,#43ma2,#43mb1,#43mb2,#43mc1,#43mc2,#44ma1,#44ma2,#44mb1,#44mb2,#44mc1,#44mc2, " +
    "#45ma,#45mb,#45mc,#46ma,#46mb,#46mc,").hide()

$j('label[for="41ma1"],label[for="41ma2"],label[for="41mb1"],label[for="41mb2"],label[for="41mc1"],label[for="41mc2"],' +
    'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"],label[for="42mb2"],label[for="42mc1"],label[for="42mc2"],' +
    'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"],label[for="43mb2"],label[for="43mc1"],label[for="43mc2"],' +
    'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"],label[for="44mb2"],label[for="44mc1"],label[for="44mc2"],' +
    'label[for="45ma"],label[for="45mb"],label[for="45mc"],label[for="46ma"],label[for="46mb"],label[for="46mc"]').hide();


function row5FirstOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b").show()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"]').show();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2,#dose6c,#dose6d,#dose6e" +
        ",#dose6f,#dose6g,#dose6h,#dose6i,#dose6j,#dose6k").hide()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"],' +
        'label[for="42mc1"],label[for="42mc2"],label[for="dose6c"],' +
        'label[for="dose6d"],label[for="dose6e"],label[for="dose6f"],label[for="dose6g"]' +
        ',label[for="dose6h"],label[for="dose6i"],label[for="dose6j"],label[for="dose6k"]').hide();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2").hide()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"]').hide();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2").hide()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"]').hide();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc").hide()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"]').hide();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc").hide()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"]').hide();

}
function row5SecondOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b,#dose6e,#dose6f,#dose6g,#dose6h,#dose6i,#dose6j,#dose6k").hide()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"],label[for="dose6e"],label[for="dose6f"],' +
        'label[for="dose6g"],label[for="dose6h"],label[for="dose6i"],label[for="dose6j"],label[for="dose6k"]').hide();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2,#dose6c,#dose6d").show()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"],' +
        'label[for="42mc1"],label[for="42mc2"],label[for="dose6c"],label[for="dose6d"]').show();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2").hide()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"]').hide();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2").hide()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"]').hide();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc").hide()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"]').hide();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc").hide()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"]').hide();

}function row5ThirdOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b," +
        "#dose6c,#dose6d,#dose6g,#dose6h,#dose6i,#dose6j,#dose6k").hide()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"],label[for="dose6c"],' +
        'label[for="dose6d"],label[for="dose6g"],label[for="dose6h"],label[for="dose6i"],label[for="dose6j"]' +
        ',label[for="dose6k"]').hide();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2").hide()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"],' +
        'label[for="42mc1"],label[for="42mc2"]').hide();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2,#dose6e,#dose6f").show()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"],label[for="dose6e"],label[for="dose6f"]').show();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2").hide()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"]').hide();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc").hide()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"]').hide();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc").hide()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"]').hide();


}function row5FourthOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b," +
        "#dose6c,#dose6d,#dose6e,#dose6f,#dose6j,#dose6k").hide()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"],' +
        'label[for="dose6c"],label[for="dose6d"],label[for="dose6e"],label[for="dose6f"],label[for="dose6j"]' +
        ',label[for="dose6k"]').hide();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2").hide()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"]' +
        'label[for="42mc1"],label[for="42mc2"]').hide();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2").hide()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"]').hide();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2,#dose6h,#dose6i").show()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"],label[for="dose6h"],label[for="dose6i"]').show();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc").hide()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"]').hide();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc").hide()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"]').hide();

}function row5FifthOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b,#dose6c" +
        ",#dose6d,#dose6e,#dose6f,#dose6g,#dose6h,#dose6i,#dose6k").hide()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"],' +
        'label[for="dose6c"],label[for="dose6d"],label[for="dose6e"],label[for="dose6f"],label[for="dose6g"],' +
        'label[for="dose6h"],label[for="dose6i"],label[for="dose6k"]').hide();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2").hide()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"],' +
        'label[for="42mc1"],label[for="42mc2"]').hide();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2").hide()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"]').hide();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2").hide()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"]').hide();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc,#dose6j").show()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"],label[for="dose6j"]').show();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc").hide()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"]').hide();

}function row5SixthOption() {
    $j("#41,#41fa, #41fb,#41da,#41db, #41ma1,#41ma2, #41mb1,#41mb2,#41mc1,#41mc2,#dose6a,#dose6b," +
        "#dose6c,#dose6d,#dose6e,#dose6f,#dose6g,#dose6h,#dose6i,#dose6j").hide()

    $j('label[for="41"],label[for="41fa"],label[for="41fb"],label[for="41da"],label[for="41db"],' +
        'label[for="41ma1"],label[for="41ma2"],label[for="41mb1"], label[for="41mb2"],' +
        'label[for="41mc1"],label[for="41mc2"],label[for="dose6a"],label[for="dose6b"],' +
        'label[for="dose6c"],label[for="dose6d"],label[for="dose6e"],label[for="dose6f"],' +
        'label[for="dose6g"],label[for="dose6h"],label[for="dose6i"],label[for="dose6k"]').hide();


    $j("#42,#42fa, #42fb,#42da,#42db, #42ma1,#42ma2, #42mb1,#42mb2,#42mc1,#42mc2").hide()

    $j('label[for="42"],label[for="42fa"],label[for="42fb"],label[for="42da"],label[for="42db"],' +
        'label[for="42ma1"],label[for="42ma2"],label[for="42mb1"], label[for="42mb2"],' +
        'label[for="42mc1"],label[for="42mc2"]').hide();

    $j("#43,#43fa, #43fb,#43da,#43db, #43ma1,#43ma2, #43mb1,#43mb2,#43mc1,#43mc2").hide()

    $j('label[for="43"],label[for="43fa"],label[for="43fb"],label[for="43da"],label[for="43db"],' +
        'label[for="43ma1"],label[for="43ma2"],label[for="43mb1"], label[for="43mb2"],' +
        'label[for="43mc1"],label[for="43mc2"]').hide();

    $j("#44,#44fa, #44fb,#44da,#44db, #44ma1,#44ma2, #44mb1,#44mb2,#44mc1,#44mc2").hide()

    $j('label[for="44"],label[for="44fa"],label[for="44fb"],label[for="44da"],label[for="44db"],' +
        'label[for="44ma1"],label[for="44ma2"],label[for="44mb1"], label[for="44mb2"],' +
        'label[for="44mc1"],label[for="44mc2"]').hide();

    $j("#45,#45f, #45d, #45ma, #45mb,#45mc").hide()

    $j('label[for="45"],label[for="45f"],label[for="45d"], ' +
        'label[for="45ma"], label[for="45mb"], label[for="45mc"], ' +
        'label[for="45mc"]').hide();

    $j("#46,#46f, #46d, #46ma, #46mb,#46mc,#dose6k").show()

    $j('label[for="46"],label[for="46f"],label[for="46d"], ' +
        'label[for="46ma"], label[for="46mb"], label[for="46mc"], ' +
        'label[for="46mc"],label[for="dose6k"]').show();
}



/////////////



$j("#31,#32,#33,#34,#35,#36 , #31fa,#31fb, #32fa, #32fb,#33fa,#33fb, #34fa,#34fb,#35f,#36f,#dose7a,#dose7b,#dose7c" +
    ",#dose7d,#dose7e,#dose7f,#dose7g,#dose7h,#dose7i,#dose7j,#dose7k").hide();

$j('label[for="31"],label[for="32"],label[for="33"],label[for="34"],label[for="35"] ,label[for="36"],label[for="31fa"],label[for="31fb"],' +
    'label[for="32fa"],label[for="32fb"],label[for="33fa"],label[for="33fb"],label[for="34fa"],label[for="34fb"],label[for="35f"],label[for="36f"]' +
    ',label[for="dose7a"],label[for="dose7b"],label[for="dose7c"],label[for="dose7d"],label[for="dose7e"]' +
    ',label[for="dose7f"],label[for="dose7g"],label[for="dose7h"],label[for="dose7i"],label[for="dose7j"],label[for="dose7k"]').hide();


$j("#31da,#31db, #32da, #32db,#33da, #33db,#34da,#34db,#35d,#36d").hide()  ;

$j('label[for="31da"],label[for="31db"],label[for="32da"],label[for="32db"],label[for="33da"],label[for="33db"],' +
    'label[for="34da"],label[for="34db"],label[for="35d"], label[for="36d"]').hide();


$j("#31ma1,#31ma2,#31mb1,#31mb2,#31mc1,#31mc2, #32ma1,#32ma2,#32mb1,#32mb2,#32mc1,#32mc2, " +
    "#33ma1,#33ma2,#33mb1,#33mb2,#33mc1,#33mc2,#34ma1,#34ma2,#34mb1,#34mb2,#34mc1,#34mc2, " +
    "#35ma,#35mb,#35mc,#36ma,#36mb,#36mc,").hide()

$j('label[for="31ma1"],label[for="31ma2"],label[for="31mb1"],label[for="31mb2"],label[for="31mc1"],label[for="31mc2"],' +
    'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"],label[for="32mb2"],label[for="32mc1"],label[for="32mc2"],' +
    'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"],label[for="33mb2"],label[for="33mc1"],label[for="33mc2"],' +
    'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"],label[for="34mb2"],label[for="34mc1"],label[for="34mc2"],' +
    'label[for="35ma"],label[for="35mb"],label[for="35mc"],label[for="36ma"],label[for="36mb"],label[for="36mc"]').hide();


function row51FirstOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2,#dose7a,#dose7b").show()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"]').show();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2,#dose7c,#dose7d,#dose7e," +
        "#dose7f,#dose7g,#dose7h,#dose7i,#dose7j,#dose7k").hide()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"],label[for="dose7c"],label[for="dose7d"],label[for="dose7e"],' +
        'label[for="dose7f"],label[for="dose7g"],label[for="dose7h"],label[for="dose7i"],label[for="dose7j"]' +
        ',label[for="dose7k"]').hide();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2").hide()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"]').hide();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2").hide()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"]').hide();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc").hide()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"]').hide();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc").hide()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"]').hide();

}
function row51SecondOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2,#dose7a,#dose7b" +
        ",#dose7e,#dose7f,#dose7g,#dose7h,#dose7i,#dose7j,#dose7k").hide()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"]' +
        ',label[for="dose7e"],label[for="dose7f"],label[for="dose7g"],label[for="dose7h"]' +
        ',label[for="dose7i"],label[for="dose7j"],label[for="dose7k"]').hide();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2,#dose7c,#dose7d").show()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"],label[for="dose7c"],label[for="dose7d"]').show();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2").hide()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"]').hide();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2").hide()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"]').hide();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc").hide()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"]').hide();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc").hide()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"]').hide();

}function row51ThirdOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2,#dose7a,#dose7b,#dose7c" +
        ",#dose7d,#dose7g,#dose7h,#dose7i,#dose7j,#dose7k").hide()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"],label[for="dose7c"]' +
        ',label[for="dose7d"],label[for="dose7g"]' +
        ',label[for="dose7h"],label[for="dose7i"],label[for="dose7j"],label[for="dose7k"]').hide();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2").hide()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"]').hide();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2,#dose7e,#dose7f").show()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"],label[for="dose7e"],label[for="dose7f"]').show();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2").hide()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"]').hide();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc").hide()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"]').hide();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc").hide()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"]').hide();


}function row51FourthOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2," +
        "#dose7a,#dose7b,#dose7c,#dose7d,#dose7e,#dose7f,#dose7g,#dose7j,#dose7k").hide()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"],label[for="dose7c"]' +
        ',label[for="dose7d"],label[for="dose7e"],label[for="dose7f"],label[for="dose7g"]' +
        ',label[for="dose7j"],label[for="dose7k"]').hide();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2").hide()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"]').hide();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2").hide()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"]').hide();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2,#dose7h,#dose7i").show()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"],label[for="dose7h"],label[for="dose7i"]').show();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc").hide()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"]').hide();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc").hide()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"]').hide();

}function row51FifthOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2,#dose7a,#dose7b" +
        ",#dose7c,#dose7d,#dose7e,#dose7f,#dose7g,#dose7h,#dose7i,#dose7k").hide()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"],label[for="dose7c"]' +
        ',label[for="dose7d"],label[for="dose7e"],label[for="dose7f"],label[for="dose7g"]' +
        ',label[for="dose7h"],label[for="dose7i"],label[for="dose7k"]').hide();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2").hide()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"]').hide();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2").hide()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"]').hide();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2").hide()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"]').hide();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc,#dose7j").show()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"],label[for="dose7j"]').show();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc").hide()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"]').hide();

}function row51SixthOption() {
    $j("#31,#31fa, #31fb,#31da,#31db, #31ma1,#31ma2, #31mb1,#31mb2,#31mc1,#31mc2,#dose7a,#dose7b" +
        ",#dose7c,#dose7d,#dose7e,#dose7f,#dose7g,#dose7h,#dose7i,#dose7j").hide()

    $j('label[for="31"],label[for="31fa"],label[for="31fb"],label[for="31da"],label[for="31db"],' +
        'label[for="31ma1"],label[for="31ma2"],label[for="31mb1"], label[for="31mb2"],' +
        'label[for="31mc1"],label[for="31mc2"],label[for="dose7a"],label[for="dose7b"],label[for="dose7c"]' +
        ',label[for="dose7d"],label[for="dose7e"],label[for="dose7f"],label[for="dose7g"]' +
        ',label[for="dose7h"],label[for="dose7i"],label[for="dose7j"]').hide();


    $j("#32,#32fa, #32fb,#32da,#32db, #32ma1,#32ma2, #32mb1,#32mb2,#32mc1,#32mc2").hide()

    $j('label[for="32"],label[for="32fa"],label[for="32fb"],label[for="32da"],label[for="32db"],' +
        'label[for="32ma1"],label[for="32ma2"],label[for="32mb1"], label[for="32mb2"],' +
        'label[for="32mc1"],label[for="32mc2"]').hide();

    $j("#33,#33fa, #33fb,#33da,#33db, #33ma1,#33ma2, #33mb1,#33mb2,#33mc1,#33mc2").hide()

    $j('label[for="33"],label[for="33fa"],label[for="33fb"],label[for="33da"],label[for="33db"],' +
        'label[for="33ma1"],label[for="33ma2"],label[for="33mb1"], label[for="33mb2"],' +
        'label[for="33mc1"],label[for="33mc2"]').hide();

    $j("#34,#34fa, #34fb,#34da,#34db, #34ma1,#34ma2, #34mb1,#34mb2,#34mc1,#34mc2").hide()

    $j('label[for="34"],label[for="34fa"],label[for="34fb"],label[for="34da"],label[for="34db"],' +
        'label[for="34ma1"],label[for="34ma2"],label[for="34mb1"], label[for="34mb2"],' +
        'label[for="34mc1"],label[for="34mc2"]').hide();

    $j("#35,#35f, #35d, #35ma, #35mb,#35mc").hide()

    $j('label[for="35"],label[for="35f"],label[for="35d"], ' +
        'label[for="35ma"], label[for="35mb"], label[for="35mc"], ' +
        'label[for="35mc"]').hide();

    $j("#36,#36f, #36d, #36ma, #36mb,#36mc,#dose7k").show()

    $j('label[for="36"],label[for="36f"],label[for="36d"], ' +
        'label[for="36ma"], label[for="36mb"], label[for="36mc"], ' +
        'label[for="36mc"],label[for="dose7k"]').show();
}

/////////////          Efavirenz



$j("#50,#51,#52,#53,#54,#55,#56,#57 , #50f,#51f,#52f,#53fa,#53fb, #54fa,#54fb,#55fa,,#55fb,#56f,#57f" +
    ",#dose8a,#dose8b,#dose8c,#dose8d,#dose8e,#dose8f,#dose8g,#dose8h,#dose8i,#54ma3,#54mb3").hide()

$j('label[for="50"],label[for="51"],label[for="52"],label[for="53"],label[for="54"],label[for="55"],label[for="56"],' +
    'label[for="57"],label[for="53ma3"],label[for="53mb3"],label[for="54ma3"],label[for="54mb3"]' +
    'label[for="50f"],label[for="51f"],label[for="52f"],label[for="53fa"],label[for="53fb"],label[for="54fa"],' +
    'label[for="54fb"],label[for="55fa"],label[for="55fb"],label[for="56f"],label[for="57f"],label[for="dose8a"]' +
    ',label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"],label[for="dose8f"]' +
    ',label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();


$j("#50d,#51d,#52d,#53d,#54d,#55d,#56d,#57d").hide()

$j('label[for="50d"],label[for="51d"],label[for="52d"],label[for="53d"],label[for="54d"],label[for="55d"],label[for="56d"],label[for="56d"],,label[for="57d"]').hide();


$j("#50ma,#50mb,#50mc,#51ma,#51mb,#51mc,#52ma,#52mb,#52mc,  #53ma1,#53ma2,#53mb1,#53mb2,#53mc1,#53mc2, " +
    "#54ma1,#54ma2,#54mb1,#54mb2,#54mc1,#54mc2, #55ma1,#55ma2,#55mb1,#55mb2,#55mc1,#55mc2,#56ma,#56mb,#56mc, #57ma,#57mb,#57mc ").hide()

$j('label[for="50ma"],label[for="50mb"],label[for="50mc"],label[for="51ma"],label[for="51mb"],label[for="51mc"],label[for="52ma"],label[for="52mb"],label[for="52mc"], ' +
    'label[for="53ma1"],label[for="53ma2"],label[for="53mb1"],label[for="53mb2"],label[for="53mc1"],label[for="53mc2"],' +
    'label[for="54ma1"],label[for="54ma2"],label[for="54mb1"],label[for="54mb2"],label[for="54mc1"],label[for="54mc2"],' +
    'label[for="55ma1"],label[for="55ma2"],label[for="55mb1"],label[for="55mb2"],label[for="55mc1"],label[for="55mc2"],' +
    'label[for="56ma"],label[for="56mb"],label[for="56mc"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();


function row6FirstOption() {
    $j("#50,#50f, #50d,#50ma,#50mb, #50mc,#dose8a").show()

    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],' +
        'label[for="50mc"],label[for="dose8a"]').show();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc,#dose8h,#dose8b,#dose8c,#dose8d,#dose8e,#dose8f,#dose8g,#dose8i").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],' +
        'label[for="52mc"],label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"]' +
        ',label[for="dose8f"],label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();


}
function row6SecondOption() {
    $j("#50,#50f, #50d,#50ma,#50mb, #50mc,#dose8a,#dose8c,#dose8d,#dose8e,#dose8f,#dose8g,#dose8h,#dose8i").hide()

    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"]' +
        ',label[for="dose8a"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"]' +
        ',label[for="dose8f"],label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc,#dose8a").show()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],' +
        'label[for="51mc"],label[for="dose8a"]').show();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();

}function row6ThirdOption() {
    $j("#50,#50f, #50d,#50ma,#50mb, #50mc,#dose8a,#dose8b,#dose8d,#dose8e,#dose8f,#dose8g,#dose8h,#dose8i").hide()

    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],' +
        'label[for="50mc"],label[for="dose8a"],label[for="dose8b"],label[for="dose8d"],label[for="dose8e"]' +
        ',label[for="dose8f"],label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc,#dose8c").show()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]' +
        ',label[for="dose8c"]').show();

    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();

}function row6FourthOption() {
    $j("#50,#50f, #50d,#50ma,#50mb,#50mc,#dose8a,#dose8b,#dose8c,#dose8e,#dose8f,#dose8g,#dose8h,#dose8i").hide()

    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"]' +
        ',label[for="dose8a"],label[for="dose8b"],label[for="dose8c"],label[for="dose8e"],label[for="dose8f"]' +
        ',label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2,#dose8d").show()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],' +
        'label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"],label[for="dose8d"]').show();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();

}function row6FifthOption() {

    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"]' +
        ',label[for="dose8a"],label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8f"]' +
        ',label[for="dose8g"],label[for="dose8h"],label[for="dose8i"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc,#dose8a,#dose8b,#dose8c,#dose8d,#dose8f,#dose8g,#dose8h,#dose8i").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2,#dose8e").show()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],' +
        'label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"],label[for="dose8e"]').show();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()
    $j("#55ma,#55mb,#55mc").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();

}function row6SixthOption() {
    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc,#dose8a,#dose8b,#dose8c,#dose8d,#dose8e").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]' +
        ',label[for="dose8a"],label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2,#53ma3,#53mb3").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2,#54ma3,#54mb3").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2,#dose8f").show()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],' +
        'label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"],label[for="dose8f"]').show();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();
}

function row6SeventhOption() {
    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"]' +
        ',label[for="dose8a"],label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"]' +
        ',label[for="dose8f"],label[for="dose8g"],label[for="dose8i"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc,#dose8a,#dose8b,#dose8c,#dose8d,#dose8e,#dose8f,#dose8g,#dose8i").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc,#dose8h").show()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],' +
        'label[for="56mc"],label[for="dose8h"]').show();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc").hide()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"],label[for="57mc"]').hide();
}

function row6EightOption() {
    $j('label[for="50"],label[for="50f"],label[for="50d"],label[for="50ma"],label[for="50mb"],label[for="50mc"],label[for="dose8a"],label[for="dose8b"],label[for="dose8c"],label[for="dose8d"],label[for="dose8e"]' +
        ',label[for="dose8f"],label[for="dose8g"],label[for="dose8h"]').hide();

    $j("#51,#51f, #51d,#51ma,#51mb, #51mc,#dose8a,#dose8b,#dose8c,#dose8d,#dose8e,#dose8f,#dose8g,#dose8h").hide()

    $j('label[for="51"],label[for="51f"],label[for="51d"],label[for="51ma"],label[for="51mb"],label[for="51mc"]').hide();

    $j("#52,#52f, #52d,#52ma,#52mb, #52mc").hide()

    $j('label[for="52"],label[for="52f"],label[for="52d"],label[for="52ma"],label[for="52mb"],label[for="52mc"]').hide();


    $j("#53,#53fa,#53fb, #53d,#53ma1,#53mb1, #53mc1,#53ma2,#53mb2, #53mc2").hide()

    $j('label[for="53"],label[for="53fa"],label[for="53fb"],label[for="53d"],label[for="53ma1"],label[for="53mb1"],label[for="53mc1"],label[for="53ma2"],label[for="53mb2"],label[for="53mc2"]').hide();

    $j("#54,#54fa,#54fb, #54d,#54ma1,#54mb1, #54mc1,#54ma2,#54mb2, #54mc2").hide()

    $j('label[for="54"],label[for="54fa"],label[for="54fb"],label[for="54d"],label[for="54ma1"],label[for="54mb1"],label[for="54mc1"],label[for="54ma2"],label[for="54mb2"],label[for="54mc2"]').hide();

    $j("#55,#55fa,#55fb, #55d,#55ma1,#55mb1, #55mc1,#55ma2,#55mb2, #55mc2").hide()

    $j('label[for="55"],label[for="55fa"],label[for="55fb"],label[for="55d"],label[for="55ma1"],label[for="55mb1"],label[for="55mc1"],label[for="55ma2"],label[for="55mb2"],label[for="55mc2"]').hide();



    $j("#56,#56f, #56d,#56ma,#56mb, #56mc").hide()

    $j('label[for="56"],label[for="56f"],label[for="56d"],label[for="56ma"],label[for="56mb"],label[for="56mc"]').hide();
    $j("#57,#57f, #57d,#57ma,#57mb, #57mc,#dose8i").show()

    $j('label[for="57"],label[for="57f"],label[for="57d"],label[for="57ma"],label[for="57mb"]' +
        ',label[for="57mc"],label[for="dose8i"]').show();
}




//////////////////////////////////////////////////////////// 6 ZDV/3TC




$j("#61,#62,#63,#64,#65,#66 , #61f,, #62f, #63f, #64fa,#64fb,#65fa,#65fb,#66f,#dose9a,#dose9b,#dose9c,#dose9e").hide()

$j('label[for="61"],label[for="62"],label[for="63"],label[for="64"],label[for="65"] ,label[for="66"],label[for="61f"],' +
    'label[for="62f"],label[for="63f"],label[for="64fa"],label[for="64fb"],label[for="65fa"],label[for="65fb"],' +
    'label[for="66f"],label[for="dose9a"],label[for="dose9b"],label[for="dose9c"],' +
    'label[for="dose9d"],label[for="dose9e"]').hide();


$j("#61d,, #62d, #63d, #64da,#64db,#65da,#65db,#66d").hide()

$j('label[for="61d"],label[for="62d"],label[for="63d"],label[for="64da"],label[for="64db"],label[for="65da"], label[for="65db"],label[for="66d"]').hide();


$j("#61ma,#61mb,#61mc, #62ma,#62mb,#62mc, #63ma,#63mb,#63mc, #64ma,#64mb,#64mc, #64ma1,#64mb2,#64mc3, #65ma,#65mb,#65mc, #65ma1,#65mb2,#65mc3,#66ma,#66mb,#66mc,").hide()

$j('label[for="61ma"],label[for="61mb"],label[for="61mc"],label[for="62ma"],label[for="62mb"],label[for="66ma"],label[for="66mb"],label[for="66mc"],label[for="65ma1"],label[for="65mb2"],label[for="65mc3"],label[for="65ma"],label[for="65mb"],label[for="65mc"], label[for="64ma1"],label[for="64mb2"],label[for="64mc3"],label[for="62mc"],label[for="63ma"],label[for="63mb"],label[for="63mc"],label[for="64ma"],label[for="64mb"],label[for="64mc"]').hide();
function row7FirstOption() {
    $j("#61,#61f, #61d, #61ma, #61mb,#61mc,#dose9a").show()
    $j('label[for="61"],label[for="61f"],label[for="61d"],label[for="61ma"],label[for="61mb"], ' +
        'label[for="61mc"],label[for="dose9a"]').show();

    $j("#62,#62f, #62d, #62ma, #62mb,#62mc,#dose9b,#dose9c,#dose9d,#dose9e").hide()
    $j('label[for="62"],label[for="62f"],label[for="62d"],label[for="62ma"],label[for="62mb"], label[for="62mc"]' +
        ', label[for="dose9b"], label[for="dose9c"], label[for="dose9d"], label[for="dose9e"]').hide();

    $j("#63,#63f, #63d, #63ma, #63mb,#63mc").hide()
    $j('label[for="63"],label[for="63f"],label[for="63d"],label[for="63ma"],label[for="63mb"], label[for="63mc"]').hide();

    $j("#64,#64fa,#64fb, #64da,#64db, #64ma, #64mb,#64mc,#64ma1, #64mb2,#64mc3").hide()

    $j('label[for="64"],label[for="64fa"],label[for="64fb"],label[for="64da"],label[for="64db"], label[for="64ma"], label[for="64mb"], label[for="64mc"], label[for="64ma1"], label[for="64mb2"], label[for="64mc3"]').hide();

    $j("#65,#65fa,#65fb, #65da, #65db, #65ma, #65mb,#65mc,#65ma1, #65mb2,#65mc3").hide()

    $j('label[for="65"],label[for="65fa"],label[for="65fb"],label[for="65da"],label[for="65db"], label[for="65ma"], label[for="65mb"], label[for="65mc"], label[for="65ma1"], label[for="65mb2"], label[for="65mc3"]').hide();

    $j("#66,#66f, #66d, #66ma, #66mb,#66mc").hide()
    $j('label[for="66"],label[for="66f"],label[for="66d"],label[for="66ma"],label[for="66mb"], label[for="66mc"]').hide();

}
function row7SecondOption() {
    $j("#62,#62f, #62d, #62ma, #62mb,#62mc,#dose9b").show()
    $j('label[for="62"],label[for="62f"],label[for="62d"],label[for="62ma"],label[for="62mb"], label[for="62mc"]' +
     ', label[for="dose9b"]').show();


    $j("#61,#61f, #61d, #61ma, #61mb,#61mc,#dose9a,#dose9c,#dose9d,#dose9e").hide()
    $j('label[for="61"],label[for="61f"],label[for="61d"],label[for="61ma"],label[for="61mb"], label[for="61mc"]' +
        ', label[for="dose9a"], label[for="dose9c"], label[for="dose9d"], label[for="dose9e"]').hide();


    $j("#63,#63f, #63d, #63ma, #63mb,#63mc").hide()
    $j('label[for="63"],label[for="63f"],label[for="63d"],label[for="63ma"],label[for="63mb"], label[for="63mc"]').hide();

    $j("#64,#64fa,#64fb, #64da,#64db, #64ma, #64mb,#64mc,#64ma1, #64mb2,#64mc3").hide()

    $j('label[for="64"],label[for="64fa"],label[for="64fb"],label[for="64da"],label[for="64db"], label[for="64ma"], label[for="64mb"], label[for="64mc"], label[for="64ma1"], label[for="64mb2"], label[for="64mc3"]').hide();

    $j("#65,#65fa,#65fb, #65da, #65db, #65ma, #65mb,#65mc,#65ma1, #65mb2,#65mc3").hide()

    $j('label[for="65"],label[for="65fa"],label[for="65fb"],label[for="65da"],label[for="65db"], label[for="65ma"], label[for="65mb"], label[for="65mc"], label[for="65ma1"], label[for="65mb2"], label[for="65mc3"]').hide();

    $j("#66,#66f, #66d, #66ma, #66mb,#66mc").hide()
    $j('label[for="66"],label[for="66f"],label[for="66d"],label[for="66ma"],label[for="66mb"], label[for="66mc"]').hide();

}function row7ThirdOption() {
    $j("#63,#63f, #63d, #63ma, #63mb,#63mc,#dose9c").show()
    $j('label[for="63"],label[for="63f"],label[for="63d"],label[for="63ma"],label[for="63mb"], label[for="63mc"]' +
        ', label[for="dose9c"]').show();

    $j("#61,#61f, #61d, #61ma, #61mb,#61mc,#dose9a,#dose9b,#dose9d,#dose9e").hide()
    $j('label[for="61"],label[for="61f"],label[for="61d"],label[for="61ma"],label[for="61mb"], ' +
        'label[for="61mc"],label[for="dose9a"],label[for="dose9b"],label[for="dose9d"],label[for="dose9d"]').hide();


    $j("#62,#62f, #62d, #62ma, #62mb,#62mc").hide()
    $j('label[for="62"],label[for="62f"],label[for="62d"],label[for="62ma"],label[for="62mb"], label[for="62mc"]').hide();


    $j("#64,#64fa,#64fb, #64da,#64db, #64ma, #64mb,#64mc,#64ma1, #64mb2,#64mc3").hide()

    $j('label[for="64"],label[for="64fa"],label[for="64fb"],label[for="64da"],label[for="64db"], label[for="64ma"], label[for="64mb"], label[for="64mc"], label[for="64ma1"], label[for="64mb2"], label[for="64mc3"]').hide();

    $j("#65,#65fa,#65fb, #65da, #65db, #65ma, #65mb,#65mc,#65ma1, #65mb2,#65mc3").hide()

    $j('label[for="65"],label[for="65fa"],label[for="65fb"],label[for="65da"],label[for="65db"], label[for="65ma"], label[for="65mb"], label[for="65mc"], label[for="65ma1"], label[for="65mb2"], label[for="65mc3"]').hide();

    $j("#66,#66f, #66d, #66ma, #66mb,#66mc").hide()
    $j('label[for="66"],label[for="66f"],label[for="66d"],label[for="66ma"],label[for="66mb"], label[for="66mc"]').hide();


}function row7FourthOption() {
    $j("#61,#61f, #61d, #61ma, #61mb,#61mc").hide()
    $j('label[for="61"],label[for="61f"],label[for="61d"],label[for="61ma"],label[for="61mb"],' +
        ' label[for="61mc"]').hide();

    $j("#62,#62f, #62d, #62ma, #62mb,#62mc,#dose9a,#dose9b,#dose9c,#dose9e").hide()
    $j('label[for="62"],label[for="62f"],label[for="62d"],label[for="62ma"],label[for="62mb"], label[for="62mc"]' +
        ', label[for="dose9a"], label[for="dose9b"], label[for="dose9c"], label[for="dose9e"]').hide();

    $j("#63,#63f, #63d, #63ma, #63mb,#63mc").hide()
    $j('label[for="63"],label[for="63f"],label[for="63d"],label[for="63ma"],label[for="63mb"], label[for="63mc"]').hide();

    $j("#64,#64fa,#64fb, #64da,#64db, #64ma, #64mb,#64mc,#64ma1, #64mb2,#64mc3,#dose9d").show()
    $j('label[for="64"],label[for="64fa"],label[for="64fb"],label[for="64da"],label[for="64db"], label[for="64ma"],' +
        ' label[for="64mb"], label[for="64mc"], label[for="64ma1"], label[for="64mb2"],' +
        ' label[for="64mc3"],label[for="dose9d"]').show();

    $j("#65,#65fa,#65fb, #65da, #65db, #65ma, #65mb,#65mc,#65ma1, #65mb2,#65mc3").hide()

    $j('label[for="65"],label[for="65fa"],label[for="65fb"],label[for="65da"],label[for="65db"], label[for="65ma"], label[for="65mb"], label[for="65mc"], label[for="65ma1"], label[for="65mb2"], label[for="65mc3"]').hide();

    $j("#66,#66f, #66d, #66ma, #66mb,#66mc").hide()
    $j('label[for="66"],label[for="66f"],label[for="66d"],label[for="66ma"],label[for="66mb"], label[for="66mc"]').hide();


}function row7FifthOption() {
    $j("#61,#61f, #61d, #61ma, #61mb,#61mc,#dose9a,#dose9b,#dose9c,#dose9d").hide()
    $j('label[for="61"],label[for="61f"],label[for="61d"],label[for="61ma"],label[for="61mb"], label[for="61mc"]' +
        ', label[for="dose9a"], label[for="dose9b"], label[for="dose9c"], label[for="dose9d"]').hide();

    $j("#62,#62f, #62d, #62ma, #62mb,#62mc").hide()
    $j('label[for="62"],label[for="62f"],label[for="62d"],label[for="62ma"],label[for="62mb"], label[for="62mc"]').hide();

    $j("#63,#63f, #63d, #63ma, #63mb,#63mc").hide()
    $j('label[for="63"],label[for="63f"],label[for="63d"],label[for="63ma"],label[for="63mb"], label[for="63mc"]').hide();

    $j("#64,#64fa,#64fb, #64da,#64db, #64ma, #64mb,#64mc,#64ma1, #64mb2,#64mc3").hide()
    $j('label[for="64"],label[for="64fa"],label[for="64fb"],label[for="64da"],label[for="64db"], label[for="64ma"], label[for="64mb"], label[for="64mc"], label[for="64ma1"], label[for="64mb2"], label[for="64mc3"]').hide();



    $j("#65,#65fa,#65fb, #65da, #65db, #65ma, #65mb,#65mc,#65ma1, #65mb2,#65mc3,#dose9e").show()
    $j('label[for="65"],label[for="65fa"],label[for="65fb"],label[for="65da"],label[for="65db"], label[for="65ma"],' +
        ' label[for="65mb"], label[for="65mc"], label[for="65ma1"], label[for="65mb2"], ' +
        'label[for="65mc3"],label[for="dose9e"]').show();


    $j("#66,#66f, #66d, #66ma, #66mb,#66mc").hide()
    $j('label[for="66"],label[for="66f"],label[for="66d"],label[for="66ma"],label[for="66mb"], label[for="66mc"]').hide();

}
////////////////////////////// ABC /3TC



$j("#71,#72,#73,#74,#75,#76 , #71f,, #72f, #73f, #74fa,#74fb,#75fa,#75fb,#76f,#dose10a,#dose10b,#dose10c,#dose10d" +
    ",#dose10e").hide()

$j('label[for="71"],label[for="72"],label[for="73"],label[for="74"],label[for="75"] ,label[for="76"],label[for="71f"],' +
    'label[for="72f"],label[for="73f"],label[for="74fa"],label[for="74fb"],label[for="75fa"],label[for="75fb"],' +
    'label[for="76f"],label[for="dose10a"],label[for="dose10b"],label[for="dose10c"],label[for="dose10d"]' +
    ',label[for="dose10e"]').hide();


$j("#71d,, #72d, #73d, #74da,#74db,#75da,#75db,#76d").hide()

$j('label[for="71d"],label[for="72d"],label[for="73d"],label[for="74da"],label[for="74db"],label[for="75da"], label[for="75db"],label[for="76d"]').hide();


$j("#71ma,#71mb,#71mc, #72ma,#72mb,#72mc, #73ma,#73mb,#73mc, #74ma,#74mb,#74mc, #74ma1,#74mb2,#74mc3, #75ma,#75mb,#75mc, #75ma1,#75mb2,#75mc3,#76ma,#76mb,#76mc,").hide()

$j('label[for="71ma"],label[for="71mb"],label[for="71mc"],label[for="72ma"],label[for="72mb"],label[for="76ma"],label[for="76mb"],label[for="76mc"],label[for="75ma1"],label[for="75mb2"],label[for="75mc3"],label[for="75ma"],label[for="75mb"],label[for="75mc"], label[for="74ma1"],label[for="74mb2"],label[for="74mc3"],label[for="72mc"],label[for="73ma"],label[for="73mb"],label[for="73mc"],label[for="74ma"],label[for="74mb"],label[for="74mc"]').hide();
function row8FirstOption() {
    $j("#71,#71f, #71d, #71ma, #71mb,#71mc,#dose10a").show()
    $j('label[for="71"],label[for="71f"],label[for="71d"],label[for="71ma"],label[for="71mb"], ' +
        'label[for="71mc"],label[for="dose10a"]').show();

    $j("#72,#72f, #72d, #72ma, #72mb,#72mc,#dose10b,#dose10c,#dose10d,#dose10e").hide()
    $j('label[for="72"],label[for="72f"],label[for="72d"],label[for="72ma"],label[for="72mb"],' +
        ' label[for="72mc"],label[for="dose10b"],label[for="dose10c"],label[for="dose10d"],label[for="dose10e"]').hide();

    $j("#73,#73f, #73d, #73ma, #73mb,#73mc").hide()
    $j('label[for="73"],label[for="73f"],label[for="73d"],label[for="73ma"],label[for="73mb"], label[for="73mc"]').hide();

    $j("#74,#74fa,#74fb, #74da,#74db, #74ma, #74mb,#74mc,#74ma1, #74mb2,#74mc3").hide()

    $j('label[for="74"],label[for="74fa"],label[for="74fb"],label[for="74da"],label[for="74db"], label[for="74ma"], label[for="74mb"], label[for="74mc"], label[for="74ma1"], label[for="74mb2"], label[for="74mc3"]').hide();

    $j("#75,#75fa,#75fb, #75da, #75db, #75ma, #75mb,#75mc,#75ma1, #75mb2,#75mc3").hide()

    $j('label[for="75"],label[for="75fa"],label[for="75fb"],label[for="75da"],label[for="75db"], label[for="75ma"], label[for="75mb"], label[for="75mc"], label[for="75ma1"], label[for="75mb2"], label[for="75mc3"]').hide();

    $j("#76,#76f, #76d, #76ma, #76mb,#76mc").hide()
    $j('label[for="76"],label[for="76f"],label[for="76d"],label[for="76ma"],label[for="76mb"], label[for="76mc"]').hide();

}
function row8SecondOption() {
    $j("#72,#72f, #72d, #72ma, #72mb,#72mc,#dose10b").show()
    $j('label[for="72"],label[for="72f"],label[for="72d"],label[for="72ma"],label[for="72mb"],' +
        ' label[for="72mc"],label[for="dose10b"]').show();


    $j("#71,#71f, #71d, #71ma, #71mb,#71mc,#dose10a,#dose10c,#dose10d,#dose10e").hide()
    $j('label[for="71"],label[for="71f"],label[for="71d"],label[for="71ma"],label[for="71mb"], ' +
        'label[for="71mc"],label[for="dose10a"],label[for="dose10c"],label[for="dose10d"],label[for="dose10e"]').hide();


    $j("#73,#73f, #73d, #73ma, #73mb,#73mc").hide()
    $j('label[for="73"],label[for="73f"],label[for="73d"],label[for="73ma"],label[for="73mb"], label[for="73mc"]').hide();

    $j("#74,#74fa,#74fb, #74da,#74db, #74ma, #74mb,#74mc,#74ma1, #74mb2,#74mc3").hide()

    $j('label[for="74"],label[for="74fa"],label[for="74fb"],label[for="74da"],label[for="74db"], label[for="74ma"], label[for="74mb"], label[for="74mc"], label[for="74ma1"], label[for="74mb2"], label[for="74mc3"]').hide();

    $j("#75,#75fa,#75fb, #75da, #75db, #75ma, #75mb,#75mc,#75ma1, #75mb2,#75mc3").hide()

    $j('label[for="75"],label[for="75fa"],label[for="75fb"],label[for="75da"],label[for="75db"], label[for="75ma"], label[for="75mb"], label[for="75mc"], label[for="75ma1"], label[for="75mb2"], label[for="75mc3"]').hide();

    $j("#76,#76f, #76d, #76ma, #76mb,#76mc").hide()
    $j('label[for="76"],label[for="76f"],label[for="76d"],label[for="76ma"],label[for="76mb"], label[for="76mc"]').hide();

}function row8ThirdOption() {
    $j("#73,#73f, #73d, #73ma, #73mb,#73mc,#dose10c").show()
    $j('label[for="73"],label[for="73f"],label[for="73d"],label[for="73ma"],label[for="73mb"],' +
        ' label[for="73mc"],label[for="dose10c"]').show();

    $j("#71,#71f, #71d, #71ma, #71mb,#71mc,#dose10a,#dose10b,#dose10e,#dose10e").hide()
    $j('label[for="71"],label[for="71f"],label[for="71d"],label[for="71ma"],label[for="71mb"], ' +
        'label[for="71mc"],label[for="dose10a"],label[for="dose10b"],label[for="dose10d"],label[for="dose10e"]').hide();


    $j("#72,#72f, #72d, #72ma, #72mb,#72mc").hide()
    $j('label[for="72"],label[for="72f"],label[for="72d"],label[for="72ma"],label[for="72mb"], label[for="72mc"]').hide();


    $j("#74,#74fa,#74fb, #74da,#74db, #74ma, #74mb,#74mc,#74ma1, #74mb2,#74mc3").hide()

    $j('label[for="74"],label[for="74fa"],label[for="74fb"],label[for="74da"],label[for="74db"], label[for="74ma"], label[for="74mb"], label[for="74mc"], label[for="74ma1"], label[for="74mb2"], label[for="74mc3"]').hide();

    $j("#75,#75fa,#75fb, #75da, #75db, #75ma, #75mb,#75mc,#75ma1, #75mb2,#75mc3").hide()

    $j('label[for="75"],label[for="75fa"],label[for="75fb"],label[for="75da"],label[for="75db"], label[for="75ma"], label[for="75mb"], label[for="75mc"], label[for="75ma1"], label[for="75mb2"], label[for="75mc3"]').hide();

    $j("#76,#76f, #76d, #76ma, #76mb,#76mc").hide()
    $j('label[for="76"],label[for="76f"],label[for="76d"],label[for="76ma"],label[for="76mb"], label[for="76mc"]').hide();


}function row8FourthOption() {
    $j("#71,#71f, #71d, #71ma, #71mb,#71mc,#dose10a,#dose10b,#dose10c,#dose10e").hide()
    $j('label[for="71"],label[for="71f"],label[for="71d"],label[for="71ma"],label[for="71mb"], ' +
        'label[for="71mc"],label[for="dose10a"],label[for="dose10b"],label[for="dose10c"],label[for="dose10e"]').hide();

    $j("#72,#72f, #72d, #72ma, #72mb,#72mc").hide()
    $j('label[for="72"],label[for="72f"],label[for="72d"],label[for="72ma"],label[for="72mb"], label[for="72mc"]').hide();

    $j("#73,#73f, #73d, #73ma, #73mb,#73mc").hide()
    $j('label[for="73"],label[for="73f"],label[for="73d"],label[for="73ma"],label[for="73mb"], label[for="73mc"]').hide();

    $j("#74,#74fa,#74fb, #74da,#74db, #74ma, #74mb,#74mc,#74ma1, #74mb2,#74mc3,#dose10d").show()
    $j('label[for="74"],label[for="74fa"],label[for="74fb"],label[for="74da"],label[for="74db"],' +
        ' label[for="74ma"], label[for="74mb"], label[for="74mc"], label[for="74ma1"], label[for="74mb2"], ' +
        'label[for="74mc3"],label[for="dose10d"]').show();

    $j("#75,#75fa,#75fb, #75da, #75db, #75ma, #75mb,#75mc,#75ma1, #75mb2,#75mc3").hide()

    $j('label[for="75"],label[for="75fa"],label[for="75fb"],label[for="75da"],label[for="75db"], label[for="75ma"], label[for="75mb"], label[for="75mc"], label[for="75ma1"], label[for="75mb2"], label[for="75mc3"]').hide();

    $j("#76,#76f, #76d, #76ma, #76mb,#76mc").hide()
    $j('label[for="76"],label[for="76f"],label[for="76d"],label[for="76ma"],label[for="76mb"], label[for="76mc"]').hide();


}function row8FifthOption() {
    $j("#71,#71f, #71d, #71ma, #71mb,#71mc,#dose10a,#dose10b,#dose10c,#dose10d").hide()
    $j('label[for="71"],label[for="71f"],label[for="71d"],label[for="71ma"],label[for="71mb"],' +
        ' label[for="71mc"],label[for="dose10a"],label[for="dose10b"],label[for="dose10c"],label[for="dose10d"]').hide();

    $j("#72,#72f, #72d, #72ma, #72mb,#72mc").hide()
    $j('label[for="72"],label[for="72f"],label[for="72d"],label[for="72ma"],label[for="72mb"], label[for="72mc"]').hide();

    $j("#73,#73f, #73d, #73ma, #73mb,#73mc").hide()
    $j('label[for="73"],label[for="73f"],label[for="73d"],label[for="73ma"],label[for="73mb"], label[for="73mc"]').hide();

    $j("#74,#74fa,#74fb, #74da,#74db, #74ma, #74mb,#74mc,#74ma1, #74mb2,#74mc3").hide()
    $j('label[for="74"],label[for="74fa"],label[for="74fb"],label[for="74da"],label[for="74db"], label[for="74ma"], label[for="74mb"], label[for="74mc"], label[for="74ma1"], label[for="74mb2"], label[for="74mc3"]').hide();



    $j("#75,#75fa,#75fb, #75da, #75db, #75ma, #75mb,#75mc,#75ma1, #75mb2,#75mc3,#dose10e").show()
    $j('label[for="75"],label[for="75fa"],label[for="75fb"],label[for="75da"],label[for="75db"], label[for="75ma"],' +
        ' label[for="75mb"], label[for="75mc"], label[for="75ma1"], label[for="75mb2"],' +
        ' label[for="75mc3"],label[for="dose10e"]').show();


    $j("#76,#76f, #76d, #76ma, #76mb,#76mc").hide()
    $j('label[for="76"],label[for="76f"],label[for="76d"],label[for="76ma"],label[for="76mb"], label[for="76mc"]').hide();

}
////////////////////////////// ZDV /3TC /NVP



$j("#81,#82,#83,#84,#85,#86 , #81f,, #82f, #83f, #84fa,#84fb,#85fa,#85fb,#86f,#dose11a" +
    ",#dose11b,#dose11c,#dose11d,#dose11e").hide()

$j('label[for="81"],label[for="82"],label[for="83"],label[for="84"],label[for="85"] ,' +
    'label[for="86"],label[for="81f"],label[for="82f"],label[for="83f"],label[for="84fa"],' +
    'label[for="84fb"],label[for="85fa"],label[for="85fb"],label[for="86f"],label[for="dose11a"]' +
    ',label[for="dose11b"],label[for="dose11c"],label[for="dose11d"],label[for="dose11e"]').hide();


$j("#81d,, #82d, #83d, #84da,#84db,#85da,#85db,#86d").hide()

$j('label[for="81d"],label[for="82d"],label[for="83d"],label[for="84da"],label[for="84db"],label[for="85da"], label[for="85db"],label[for="86d"]').hide();


$j("#81ma,#81mb,#81mc, #82ma,#82mb,#82mc, #83ma,#83mb,#83mc, #84ma,#84mb,#84mc, #84ma1,#84mb2,#84mc3, #85ma,#85mb,#85mc, #85ma1,#85mb2,#85mc3,#86ma,#86mb,#86mc,").hide()

$j('label[for="81ma"],label[for="81mb"],label[for="81mc"],label[for="82ma"],label[for="82mb"],label[for="86ma"],label[for="86mb"],label[for="86mc"],label[for="85ma1"],label[for="85mb2"],label[for="85mc3"],label[for="85ma"],label[for="85mb"],label[for="85mc"], label[for="84ma1"],label[for="84mb2"],label[for="84mc3"],label[for="82mc"],label[for="83ma"],label[for="83mb"],label[for="83mc"],label[for="84ma"],label[for="84mb"],label[for="84mc"]').hide();
function row9FirstOption() {
    $j("#81,#81f, #81d, #81ma, #81mb,#81mc,#dose11a").show()
    $j('label[for="81"],label[for="81f"],label[for="81d"],label[for="81ma"],label[for="81mb"], ' +
        'label[for="81mc"],label[for="dose11a"]').show();

    $j("#82,#82f, #82d, #82ma, #82mb,#82mc,#dose11b,#dose11c,#dose11d,#dose11e").hide()
    $j('label[for="82"],label[for="82f"],label[for="82d"],label[for="82ma"],label[for="82mb"], ' +
        'label[for="82mc"],label[for="dose11b"],label[for="dose11c"],label[for="dose11d"],label[for="dose11e"]').hide();

    $j("#83,#83f, #83d, #83ma, #83mb,#83mc").hide()
    $j('label[for="83"],label[for="83f"],label[for="83d"],label[for="83ma"],label[for="83mb"], label[for="83mc"]').hide();

    $j("#84,#84fa,#84fb, #84da,#84db, #84ma, #84mb,#84mc,#84ma1, #84mb2,#84mc3").hide()

    $j('label[for="84"],label[for="84fa"],label[for="84fb"],label[for="84da"],label[for="84db"], label[for="84ma"], label[for="84mb"], label[for="84mc"], label[for="84ma1"], label[for="84mb2"], label[for="84mc3"]').hide();

    $j("#85,#85fa,#85fb, #85da, #85db, #85ma, #85mb,#85mc,#85ma1, #85mb2,#85mc3").hide()

    $j('label[for="85"],label[for="85fa"],label[for="85fb"],label[for="85da"],label[for="85db"], label[for="85ma"], label[for="85mb"], label[for="85mc"], label[for="85ma1"], label[for="85mb2"], label[for="85mc3"]').hide();

    $j("#86,#86f, #86d, #86ma, #86mb,#86mc").hide()
    $j('label[for="86"],label[for="86f"],label[for="86d"],label[for="86ma"],label[for="86mb"], label[for="86mc"]').hide();

}
function row9SecondOption() {
    $j("#82,#82f, #82d, #82ma, #82mb,#82mc,#dose11b").show()
    $j('label[for="82"],label[for="82f"],label[for="82d"],label[for="82ma"],label[for="82mb"],' +
        ' label[for="82mc"],label[for="dose11b"]').show();


    $j("#81,#81f, #81d, #81ma, #81mb,#81mc,#dose11a,#dose11c,#dose11d,#dose11e").hide()
    $j('label[for="81"],label[for="81f"],label[for="81d"],label[for="81ma"],label[for="81mb"], ' +
        'label[for="81mc"],label[for="dose11a"],label[for="dose11c"],label[for="dose11d"]' +
        ',label[for="dose11e"]').hide();


    $j("#83,#83f, #83d, #83ma, #83mb,#83mc").hide()
    $j('label[for="83"],label[for="83f"],label[for="83d"],label[for="83ma"],label[for="83mb"], label[for="83mc"]').hide();

    $j("#84,#84fa,#84fb, #84da,#84db, #84ma, #84mb,#84mc,#84ma1, #84mb2,#84mc3").hide()

    $j('label[for="84"],label[for="84fa"],label[for="84fb"],label[for="84da"],label[for="84db"], label[for="84ma"], label[for="84mb"], label[for="84mc"], label[for="84ma1"], label[for="84mb2"], label[for="84mc3"]').hide();

    $j("#85,#85fa,#85fb, #85da, #85db, #85ma, #85mb,#85mc,#85ma1, #85mb2,#85mc3").hide()

    $j('label[for="85"],label[for="85fa"],label[for="85fb"],label[for="85da"],label[for="85db"], label[for="85ma"], label[for="85mb"], label[for="85mc"], label[for="85ma1"], label[for="85mb2"], label[for="85mc3"]').hide();

    $j("#86,#86f, #86d, #86ma, #86mb,#86mc").hide()
    $j('label[for="86"],label[for="86f"],label[for="86d"],label[for="86ma"],label[for="86mb"], label[for="86mc"]').hide();

}function row9ThirdOption() {
    $j("#83,#83f, #83d, #83ma, #83mb,#83mc,#dose11c").show()
    $j('label[for="83"],label[for="83f"],label[for="83d"],label[for="83ma"],label[for="83mb"], ' +
        'label[for="83mc"],label[for="dose11c"]').show();

    $j("#81,#81f, #81d, #81ma, #81mb,#81mc,#dose11a,#dose11b,#dose11d,#dose11e").hide()
    $j('label[for="81"],label[for="81f"],label[for="81d"],label[for="81ma"],label[for="81mb"], ' +
        'label[for="81mc"],label[for="dose11a"],label[for="dose11b"],label[for="dose11d"],label[for="dose11e"]').hide();


    $j("#82,#82f, #82d, #82ma, #82mb,#82mc").hide()
    $j('label[for="82"],label[for="82f"],label[for="82d"],label[for="82ma"],label[for="82mb"], label[for="82mc"]').hide();


    $j("#84,#84fa,#84fb, #84da,#84db, #84ma, #84mb,#84mc,#84ma1, #84mb2,#84mc3").hide()

    $j('label[for="84"],label[for="84fa"],label[for="84fb"],label[for="84da"],label[for="84db"], label[for="84ma"], label[for="84mb"], label[for="84mc"], label[for="84ma1"], label[for="84mb2"], label[for="84mc3"]').hide();

    $j("#85,#85fa,#85fb, #85da, #85db, #85ma, #85mb,#85mc,#85ma1, #85mb2,#85mc3").hide()

    $j('label[for="85"],label[for="85fa"],label[for="85fb"],label[for="85da"],label[for="85db"], label[for="85ma"], label[for="85mb"], label[for="85mc"], label[for="85ma1"], label[for="85mb2"], label[for="85mc3"]').hide();

    $j("#86,#86f, #86d, #86ma, #86mb,#86mc").hide()
    $j('label[for="86"],label[for="86f"],label[for="86d"],label[for="86ma"],label[for="86mb"], label[for="86mc"]').hide();


}function row9FourthOption() {
    $j("#81,#81f, #81d, #81ma, #81mb,#81mc,#dose11a,#dose11b,#dose11c,#dose11e").hide()
    $j('label[for="81"],label[for="81f"],label[for="81d"],label[for="81ma"],label[for="81mb"],' +
        ' label[for="81mc"],label[for="dose11a"],label[for="dose11b"],label[for="dose11c"],label[for="dose11e"]').hide();

    $j("#82,#82f, #82d, #82ma, #82mb,#82mc").hide()
    $j('label[for="82"],label[for="82f"],label[for="82d"],label[for="82ma"],label[for="82mb"], label[for="82mc"]').hide();

    $j("#83,#83f, #83d, #83ma, #83mb,#83mc").hide()
    $j('label[for="83"],label[for="83f"],label[for="83d"],label[for="83ma"],label[for="83mb"], label[for="83mc"]').hide();

    $j("#84,#84fa,#84fb, #84da,#84db, #84ma, #84mb,#84mc,#84ma1, #84mb2,#84mc3,#dose11d").show()
    $j('label[for="84"],label[for="84fa"],label[for="84fb"],label[for="84da"],label[for="84db"], label[for="84ma"],' +
        ' label[for="84mb"], label[for="84mc"], label[for="84ma1"], label[for="84mb2"],' +
        ' label[for="84mc3"],label[for="dose11d"]').show();

    $j("#85,#85fa,#85fb, #85da, #85db, #85ma, #85mb,#85mc,#85ma1, #85mb2,#85mc3").hide()

    $j('label[for="85"],label[for="85fa"],label[for="85fb"],label[for="85da"],label[for="85db"], label[for="85ma"], label[for="85mb"], label[for="85mc"], label[for="85ma1"], label[for="85mb2"], label[for="85mc3"]').hide();

    $j("#86,#86f, #86d, #86ma, #86mb,#86mc").hide()
    $j('label[for="86"],label[for="86f"],label[for="86d"],label[for="86ma"],label[for="86mb"], label[for="86mc"]').hide();


}function row9FifthOption() {
    $j("#81,#81f, #81d, #81ma, #81mb,#81mc,#dose11a,#dose11b,#dose11c,#dose11d").hide()
    $j('label[for="81"],label[for="81f"],label[for="81d"],label[for="81ma"],label[for="81mb"], ' +
        'label[for="81mc"],label[for="dose11a"],label[for="dose11b"],label[for="dose11c"],label[for="dose11d"]').hide();

    $j("#82,#82f, #82d, #82ma, #82mb,#82mc").hide()
    $j('label[for="82"],label[for="82f"],label[for="82d"],label[for="82ma"],label[for="82mb"], label[for="82mc"]').hide();

    $j("#83,#83f, #83d, #83ma, #83mb,#83mc").hide()
    $j('label[for="83"],label[for="83f"],label[for="83d"],label[for="83ma"],label[for="83mb"], label[for="83mc"]').hide();

    $j("#84,#84fa,#84fb, #84da,#84db, #84ma, #84mb,#84mc,#84ma1, #84mb2,#84mc3").hide()
    $j('label[for="84"],label[for="84fa"],label[for="84fb"],label[for="84da"],label[for="84db"], label[for="84ma"], label[for="84mb"], label[for="84mc"], label[for="84ma1"], label[for="84mb2"], label[for="84mc3"]').hide();



    $j("#85,#85fa,#85fb, #85da, #85db, #85ma, #85mb,#85mc,#85ma1, #85mb2,#85mc3,#dose11e").show()
    $j('label[for="85"],label[for="85fa"],label[for="85fb"],label[for="85da"],label[for="85db"],' +
        ' label[for="85ma"], label[for="85mb"], label[for="85mc"], label[for="85ma1"], label[for="85mb2"],' +
        ' label[for="85mc3"],label[for="dose11e"]').show();


    $j("#86,#86f, #86d, #86ma, #86mb,#86mc").hide()
    $j('label[for="86"],label[for="86f"],label[for="86d"],label[for="86ma"],label[for="86mb"], label[for="86mc"]').hide();

}
////////////////////////////// d4T /3TC /NVP



$j("#91,#92,#93,#94,#95,#96 , #91f,, #92f, #93f, #94fa,#94fb,#95fa,#95fb,#96f,#dose12a,#dose12b,#dose12c," +
    "#dose12d,#dose12e").hide()

$j('label[for="91"],label[for="92"],label[for="93"],label[for="94"],label[for="95"] ,label[for="96"],' +
    'label[for="91f"],label[for="92f"],label[for="93f"],label[for="94fa"],label[for="94fb"],' +
    'label[for="95fa"],label[for="95fb"],label[for="96f"],label[for="dose11a"],label[for="dose11b"]' +
    ',label[for="dose11c"],label[for="dose12d"],label[for="dose12e"],label[for="dose12f"]').hide();


$j("#91d,, #92d, #93d, #94da,#94db,#95da,#95db,#96d").hide()

$j('label[for="91d"],label[for="92d"],label[for="93d"],label[for="94da"],label[for="94db"],label[for="95da"], label[for="95db"],label[for="96d"]').hide();


$j("#91ma,#91mb,#91mc, #92ma,#92mb,#92mc, #93ma,#93mb,#93mc, #94ma,#94mb,#94mc, #94ma1,#94mb2,#94mc3, #95ma,#95mb,#95mc, #95ma1,#95mb2,#95mc3,#96ma,#96mb,#96mc,").hide()

$j('label[for="91ma"],label[for="91mb"],label[for="91mc"],label[for="92ma"],label[for="92mb"],label[for="96ma"],label[for="96mb"],label[for="96mc"],label[for="95ma1"],label[for="95mb2"],label[for="95mc3"],label[for="95ma"],label[for="95mb"],label[for="95mc"], label[for="94ma1"],label[for="94mb2"],label[for="94mc3"],label[for="92mc"],label[for="93ma"],label[for="93mb"],label[for="93mc"],label[for="94ma"],label[for="94mb"],label[for="94mc"]').hide();
function row10FirstOption() {
    $j("#91,#91f, #91d, #91ma, #91mb,#91mc,#dose12a").show()
    $j('label[for="91"],label[for="91f"],label[for="91d"],label[for="91ma"],label[for="91mb"], ' +
        'label[for="91mc"],label[for="dose12a"]').show();

    $j("#92,#92f, #92d, #92ma, #92mb,#92mc,#dose12b,#dose12c,#dose12d,#dose12e").hide()
    $j('label[for="92"],label[for="92f"],label[for="92d"],label[for="92ma"],label[for="92mb"], ' +
        'label[for="92mc"],label[for="dose12b"],label[for="dose12c"],label[for="dose12d"],label[for="dose12e"]').hide();

    $j("#93,#93f, #93d, #93ma, #93mb,#93mc").hide()
    $j('label[for="93"],label[for="93f"],label[for="93d"],label[for="93ma"],label[for="93mb"], label[for="93mc"]').hide();

    $j("#94,#94fa,#94fb, #94da,#94db, #94ma, #94mb,#94mc,#94ma1, #94mb2,#94mc3").hide()

    $j('label[for="94"],label[for="94fa"],label[for="94fb"],label[for="94da"],label[for="94db"], label[for="94ma"], label[for="94mb"], label[for="94mc"], label[for="94ma1"], label[for="94mb2"], label[for="94mc3"]').hide();

    $j("#95,#95fa,#95fb, #95da, #95db, #95ma, #95mb,#95mc,#95ma1, #95mb2,#95mc3").hide()

    $j('label[for="95"],label[for="95fa"],label[for="95fb"],label[for="95da"],label[for="95db"], label[for="95ma"], label[for="95mb"], label[for="95mc"], label[for="95ma1"], label[for="95mb2"], label[for="95mc3"]').hide();

    $j("#96,#96f, #96d, #96ma, #96mb,#96mc").hide()
    $j('label[for="96"],label[for="96f"],label[for="96d"],label[for="96ma"],label[for="96mb"], label[for="96mc"]').hide();

}
function row10SecondOption() {
    $j("#92,#92f, #92d, #92ma, #92mb,#92mc,#dose12b").show()
    $j('label[for="92"],label[for="92f"],label[for="92d"],label[for="92ma"],label[for="92mb"],' +
        ' label[for="92mc"],label[for="dose12b"]').show();


    $j("#91,#91f, #91d, #91ma, #91mb,#91mc,#dose12a,#dose12c,#dose12d,#dose12e").hide()
    $j('label[for="91"],label[for="91f"],label[for="91d"],label[for="91ma"],label[for="91mb"], ' +
        'label[for="91mc"],label[for="dose12a"],label[for="dose12c"],label[for="dose12d"]' +
        ',label[for="dose12e"]').hide();


    $j("#93,#93f, #93d, #93ma, #93mb,#93mc").hide()
    $j('label[for="93"],label[for="93f"],label[for="93d"],label[for="93ma"],label[for="93mb"], label[for="93mc"]').hide();

    $j("#94,#94fa,#94fb, #94da,#94db, #94ma, #94mb,#94mc,#94ma1, #94mb2,#94mc3").hide()

    $j('label[for="94"],label[for="94fa"],label[for="94fb"],label[for="94da"],label[for="94db"], label[for="94ma"], label[for="94mb"], label[for="94mc"], label[for="94ma1"], label[for="94mb2"], label[for="94mc3"]').hide();

    $j("#95,#95fa,#95fb, #95da, #95db, #95ma, #95mb,#95mc,#95ma1, #95mb2,#95mc3").hide()

    $j('label[for="95"],label[for="95fa"],label[for="95fb"],label[for="95da"],label[for="95db"], label[for="95ma"], label[for="95mb"], label[for="95mc"], label[for="95ma1"], label[for="95mb2"], label[for="95mc3"]').hide();

    $j("#96,#96f, #96d, #96ma, #96mb,#96mc").hide()
    $j('label[for="96"],label[for="96f"],label[for="96d"],label[for="96ma"],label[for="96mb"], label[for="96mc"]').hide();

}function row10ThirdOption() {
    $j("#93,#93f, #93d, #93ma, #93mb,#93mc,#dose12c").show()
    $j('label[for="93"],label[for="93f"],label[for="93d"],label[for="93ma"],label[for="93mb"], ' +
        'label[for="93mc"],label[for="dose12c"]').show();

    $j("#91,#91f, #91d, #91ma, #91mb,#91mc,#dose12a,#dose12b,#dose12d,#dose12e").hide()
    $j('label[for="91"],label[for="91f"],label[for="91d"],label[for="91ma"],label[for="91mb"],' +
        ' label[for="91mc"],label[for="dose12a"],label[for="dose12b"],label[for="dose12d"],label[for="dose12e"]').hide();


    $j("#92,#92f, #92d, #92ma, #92mb,#92mc").hide()
    $j('label[for="92"],label[for="92f"],label[for="92d"],label[for="92ma"],label[for="92mb"], label[for="92mc"]').hide();


    $j("#94,#94fa,#94fb, #94da,#94db, #94ma, #94mb,#94mc,#94ma1, #94mb2,#94mc3").hide()

    $j('label[for="94"],label[for="94fa"],label[for="94fb"],label[for="94da"],label[for="94db"], label[for="94ma"], label[for="94mb"], label[for="94mc"], label[for="94ma1"], label[for="94mb2"], label[for="94mc3"]').hide();

    $j("#95,#95fa,#95fb, #95da, #95db, #95ma, #95mb,#95mc,#95ma1, #95mb2,#95mc3").hide()

    $j('label[for="95"],label[for="95fa"],label[for="95fb"],label[for="95da"],label[for="95db"], label[for="95ma"], label[for="95mb"], label[for="95mc"], label[for="95ma1"], label[for="95mb2"], label[for="95mc3"]').hide();

    $j("#96,#96f, #96d, #96ma, #96mb,#96mc").hide()
    $j('label[for="96"],label[for="96f"],label[for="96d"],label[for="96ma"],label[for="96mb"], label[for="96mc"]').hide();


}function row10FourthOption() {
    $j("#91,#91f, #91d, #91ma, #91mb,#91mc,#dose12a,#dose12b,#dose12c,#dose12e").hide()
    $j('label[for="91"],label[for="91f"],label[for="91d"],label[for="91ma"],label[for="91mb"], ' +
        'label[for="91mc"],label[for="dose12a"],label[for="dose12b"],label[for="dose12c"]' +
        ',label[for="dose12e"]').hide();

    $j("#92,#92f, #92d, #92ma, #92mb,#92mc").hide()
    $j('label[for="92"],label[for="92f"],label[for="92d"],label[for="92ma"],label[for="92mb"], label[for="92mc"]').hide();

    $j("#93,#93f, #93d, #93ma, #93mb,#93mc").hide()
    $j('label[for="93"],label[for="93f"],label[for="93d"],label[for="93ma"],label[for="93mb"], label[for="93mc"]').hide();

    $j("#94,#94fa,#94fb, #94da,#94db, #94ma, #94mb,#94mc,#94ma1, #94mb2,#94mc3,#dose12d").show()
    $j('label[for="94"],label[for="94fa"],label[for="94fb"],label[for="94da"],label[for="94db"], ' +
        'label[for="94ma"], label[for="94mb"], label[for="94mc"], label[for="94ma1"], label[for="94mb2"],' +
        ' label[for="94mc3"],label[for="dose12d"]').show();

    $j("#95,#95fa,#95fb, #95da, #95db, #95ma, #95mb,#95mc,#95ma1, #95mb2,#95mc3").hide()

    $j('label[for="95"],label[for="95fa"],label[for="95fb"],label[for="95da"],label[for="95db"], label[for="95ma"], label[for="95mb"], label[for="95mc"], label[for="95ma1"], label[for="95mb2"], label[for="95mc3"]').hide();

    $j("#96,#96f, #96d, #96ma, #96mb,#96mc").hide()
    $j('label[for="96"],label[for="96f"],label[for="96d"],label[for="96ma"],label[for="96mb"], label[for="96mc"]').hide();


}function row10FifthOption() {
    $j("#91,#91f, #91d, #91ma, #91mb,#91mc,#dose12a,#dose12b,,#dose12c,#dose12d").hide()
    $j('label[for="91"],label[for="91f"],label[for="91d"],label[for="91ma"],label[for="91mb"], ' +
        'label[for="91mc"],label[for="dose12a"],label[for="dose12b"],label[for="dose12c"]' +
        ',label[for="dose12d"]').hide();

    $j("#92,#92f, #92d, #92ma, #92mb,#92mc").hide()
    $j('label[for="92"],label[for="92f"],label[for="92d"],label[for="92ma"],label[for="92mb"], label[for="92mc"]').hide();

    $j("#93,#93f, #93d, #93ma, #93mb,#93mc").hide()
    $j('label[for="93"],label[for="93f"],label[for="93d"],label[for="93ma"],label[for="93mb"], label[for="93mc"]').hide();

    $j("#94,#94fa,#94fb, #94da,#94db, #94ma, #94mb,#94mc,#94ma1, #94mb2,#94mc3").hide()
    $j('label[for="94"],label[for="94fa"],label[for="94fb"],label[for="94da"],label[for="94db"], label[for="94ma"], label[for="94mb"], label[for="94mc"], label[for="94ma1"], label[for="94mb2"], label[for="94mc3"]').hide();



    $j("#95,#95fa,#95fb, #95da, #95db, #95ma, #95mb,#95mc,#95ma1, #95mb2,#95mc3,#dose12e").show()
    $j('label[for="95"],label[for="95fa"],label[for="95fb"],label[for="95da"],label[for="95db"],' +
        ' label[for="95ma"], label[for="95mb"], label[for="95mc"], label[for="95ma1"], label[for="95mb2"],' +
        ' label[for="95mc3"],label[for="dose12e"]').show();


    $j("#96,#96f, #96d, #96ma, #96mb,#96mc").hide()
    $j('label[for="96"],label[for="96f"],label[for="96d"],label[for="96ma"],label[for="96mb"], label[for="96mc"]').hide();

}



/////////////          didanosine



$j("#110,#111,#112,#113,#114,#115,#116,#117,#111f,#112f,#113fa,#113fb, #114fa,#114fb,#112f1").hide()

$j('label[for="117"],label[for="111"],label[for="112"],label[for="113"],label[for="114"],label[for="115"],label[for="116"],label[for="116"],' +
    'label[for="110f"],label[for="111f"],label[for="112f1"],label[for="112f"],label[for="113fa"],label[for="113fb"],label[for="114fb"],label[for="114fa"],label[for="114fa1"],label[for="114fb1"],' +
    'label[for="113fa1"],label[for="113fb1"],label[for="113fb2"],' +
    'label[for="115fb"],label[for="116f"],label[for="117f"]"]').hide();


$j("#110d,#111d,#112d,#113d,#114d,#115d,#116d,#117d,#dose13a,#dose13b,#dose13c,#dose13d,#dose13e" +
    ",#dose13f,#dose13g,#dose13h,#dose13i").hide()

$j('label[for="110d"],label[for="111d"],label[for="112d"],label[for="113d"],label[for="114d"],' +
    'label[for="115d"],label[for="116d"],label[for="116d"],label[for="117d"],label[for="dose13a"]' +
    ',label[for="dose13b"],label[for="dose13c"],label[for="dose13d"],label[for="dose13e"],label[for="dose13f"]' +
    ',label[for="dose13g"],label[for="dose13h"],label[for="dose13i"]').hide();


$j("#110ma,#110mb,#110mc,#111ma,#111mb,#111mc,#112ma,#112mb,#112mc,  #113ma1,#113ma2,#113mb1,#113mb2,#113mc1,#113mc2, " +
    "#114ma1,#114ma2,#114mb1,#114mb2,#114mc1,#114mc2  ,#112mc1 , #112ma11,#112mb11,#112mc11,#113ma11,#113ma21,#113ma22,#113mb11," +
    "#113mb21,#113mb22,#112ma1 ,#112mb1," +
    "#113mc11,#113mc21,#113mc22,#114ma11,#114ma21,#114mb11,#114mb21,#114mc11,#114mc21 ").hide()

$j('label[for="110ma"],label[for="110mb"],label[for="110mc"],label[for="111ma"],label[for="111mb"],label[for="111mc"],label[for="112ma"],label[for="112mb"],label[for="112mc"], ' +
    'label[for="113ma1"],label[for="113ma2"],label[for="113mb1"],label[for="113mb2"],label[for="113mc1"],label[for="113mc2"],' +
    'label[for="114ma1"],label[for="114ma2"],label[for="114mb1"],label[for="114mb2"],label[for="114mc1"],label[for="114mc2"],' +

    'label[for="112ma1"],label[for="112mb1"],label[for="112mc1"],label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],' +
    'label[for="113mc11"],label[for="113mc21"],label[for="113mc22"],label[for="114ma11"],label[for="114ma21"],label[for="114mb11"],label[for="114mb21"],label[for="114mc11"],label[for="114mc21"]').hide();


function row11SecondOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a").show()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],' +
        'label[for="111mb"],label[for="111mc"],label[for="dose13a"]').show();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc,#dose13b,#dose13c,#dose13d,#dose13e,#dose13f,#dose13h" +
        ",#dose13i").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],' +
        'label[for="112mc"],label[for="dose13b"],label[for="dose13c"],label[for="dose13d"],label[for="dose13e"]' +
        ',label[for="dose13f"],label[for="dose13h"],label[for="dose13i"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"],  label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();

}function row11ThirdOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13c,#dose13d,#dose13e,#dose13t,#dose13g,#dose13h,#dose13i").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],label[for="111mb"],' +
        'label[for="111mc"],label[for="dose13a"],label[for="dose13c"]' +
        ',label[for="dose13d"],label[for="dose13e"],label[for="dose13f"],label[for="dose13g"]' +
        ',label[for="dose13h"],label[for="dose13i"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc,#dose13b").show()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],' +
        'label[for="112mc"],label[for="dose13b"]').show();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"],  label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();


}function row11FourthOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13b,#dose13d," +
        "#dose13e,#dose13f,#dose13g,#dose13h,#dose13i").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],' +
        'label[for="111mb"],label[for="111mc"],label[for="dose13a"],label[for="dose13b"],label[for="dose13d"]' +
        ',label[for="dose13e"],label[for="dose13f"],label[for="dose13g"],label[for="dose13h"],label[for="dose13i"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],label[for="112mc"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2,#dose13c").show()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],' +
        'label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]' +
        ',label[for="dose13c"]').show();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"],  label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();



}function row11FifthOption() {

    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13b,#dose13c," +
        "#dose13e,#dose13f,#dose13g,#dose13h,#dose13i").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],label[for="111mb"],' +
        'label[for="111mc"],label[for="dose13a"],label[for="dose13b"],label[for="dose13c"]' +
        ',label[for="dose13e"],label[for="dose13f"],label[for="dose13g"]' +
        ',label[for="dose13h"],label[for="dose13i"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],label[for="112mc"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2,#dose13d").show()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],' +
        'label[for="114mb2"],label[for="114mc2"],label[for="dose13d"]').show();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"],  label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();




}function row11SixthOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13b,#dose13c,#dose13d," +
        "#dose13f,#dose13g,#dose13h,#dose13i").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],' +
        'label[for="111mb"],label[for="111mc"],label[for="dose13a"],label[for="dose13b"],label[for="dose13d"]' +
        ',label[for="dose13f"],label[for="dose13g"],label[for="dose13h"],label[for="dose13i"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],label[for="112mc"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1,#dose13e").show()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],' +
        'label[for="112mb1"],label[for="112mc1"],label[for="dose13e"]').show();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"],  label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();



}

function row11SeventhOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13b,#dose13c,#dose13d,#dose13e," +
        "#dose13h,#dose13i").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],label[for="111mb"],' +
        'label[for="111mc"],label[for="dose13a"],label[for="dose13b"],label[for="dose13d"]' +
        ',label[for="dose13e"],label[for="dose13h"],label[for="dose13i"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],label[for="112mc"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22,#113mc11," +
        "#113mc21,#113mc22,#dose13f,#dose13g").show()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"], ' +
        ' label[for="113fb2"],label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],label[for="113mc21"],' +
        'label[for="113mc22"],label[for="dose13f"],label[for="dose13g"]').show();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21").hide()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],label[for="114mb21"],label[for="114mc21"]').hide();

}

function row11EightOption() {
    $j("#111,#111f, #111d,#111ma,#111mb, #111mc,#dose13a,#dose13b,#dose13c,#dose13d,#dose13e,#dose13f," +
    "#dose13g").hide()

    $j('label[for="111"],label[for="111f"],label[for="111d"],label[for="111ma"],label[for="111mb"],' +
        'label[for="111mc"],label[for="dose13a"],label[for="dose13b"],label[for="dose13c"]' +
        ',label[for="dose13d"],label[for="dose13e"],label[for="dose13f"],label[for="dose13g"]').hide();

    $j("#112,#112f, #112d,#112ma,#112mb, #112mc").hide()

    $j('label[for="112"],label[for="112f"],label[for="112d"],label[for="112ma"],label[for="112mb"],label[for="112mc"]').hide();


    $j("#113,#113fa,#113fb, #113d,#113ma1,#113mb1, #113mc1,#113ma2,#113mb2, #113mc2").hide()

    $j('label[for="113"],label[for="113fa"],label[for="113fb"],label[for="113d"],label[for="113ma1"],label[for="113mb1"],label[for="113mc1"],label[for="113ma2"],label[for="113mb2"],label[for="113mc2"]').hide();

    $j("#114,#114fa,#114fb, #114d,#114ma1,#114mb1, #114mc1,#114ma2,#114mb2, #114mc2").hide()

    $j('label[for="114"],label[for="114fa"],label[for="114fb"],label[for="114d"],' +
        'label[for="114ma1"],label[for="114mb1"],label[for="114mc1"],label[for="114ma2"],label[for="114mb2"],label[for="114mc2"]').hide();



    $j("#115,#112f1, #115d,#112ma1,#112mb1, #112mc1").hide()


    $j('label[for="115"],label[for="112f1"],label[for="115d"],label[for="112ma1"],label[for="112mb1"],label[for="112mc1"]').hide();



    $j("#116,#116f, #116d,#113fa1,#113fb1, #113fb2,#113ma11,#113ma21,#113ma22,#113mb11,#113mb21,#113mb22," +
        "#113mc11,#113mc21,#113mc22").hide()



    $j('label[for="116"],label[for="116f"],label[for="116d"],  label[for="113fa1"], label[for="113fb1"], ' +
        ' label[for="113fb2"],    label[for="113ma11"],label[for="113ma21"],label[for="113ma22"],' +
        'label[for="113mb11"],label[for="113mb21"],label[for="113mb22"],label[for="113mc11"],' +
        'label[for="113mc21"],label[for="113mc22"]').hide();


    $j("#117,#114fa1,#114fb1, #117d, #114ma11,#114mb11, #114mc11,#114ma21,#114mb21, #114mc21,#dose13h,#dose13i").show()




    $j('label[for="117"],label[for="114fa1"],label[for="117d"],' +
        'label[for="114ma11"],label[for="114mb11"],label[for="114mc11"],label[for="114ma21"],' +
        'label[for="114mb21"],label[for="114mc21"],label[for="dose13h"],label[for="dose13i"]').show();
}






/////////////////////////// LopinVIR

$j("#120,#121,#122,#123,#124,#125,#126,#127 , #120f,#121f,#122f,#123fa,#123fb, #124fa,#124fb,#125fa" +
    ",#125fb,#126f,#127f,#dose14a,#dose14b,#dose14c,#dose14d,#dose14e,#dose14f,#dose14g,#dose14h,#dose14i,#dose14j").hide()

$j('label[for="120"],label[for="121"],label[for="122"],label[for="123"],label[for="124"],label[for="125"],' +
    'label[for="126"],label[for="127"],' +
    'label[for="120f"],label[for="121f"],label[for="122f"],label[for="123fa"],label[for="123fb"],' +
    'label[for="124fa"],label[for="124fb"],label[for="125fa"],label[for="125fb"],label[for="126f"],' +
    'label[for="127f"],label[for="dose14a"],label[for="dose14b"],label[for="dose14c"],label[for="dose14d"]' +
    ',label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14h"]' +
    ',label[for="dose14i"],label[for="dose14j"]').hide();


$j("#120d,#121d,#122d,#123d,#124d,#125d,#126d,#127d").hide()

$j('label[for="120d"],label[for="121d"],label[for="122d"],label[for="123d"],label[for="124d"],label[for="125d"],label[for="126d"],label[for="126d"],,label[for="127d"]').hide();


$j("#120ma,#120mb,#120mc,#121ma,#121mb,#121mc,#122ma,#122mb,#122mc,  #123ma1,#123ma2,#123mb1,#123mb2,#123mc1,#123mc2, " +
    "#124ma1,#124ma2,#124mb1,#124mb2,#124mc1,#124mc2, #125ma1,#125ma2,#125mb1,#125mb2,#125mc1,#125mc2,#126ma,#126mb,#126mc, #127ma,#127mb,#127mc ").hide()

$j('label[for="120ma"],label[for="120mb"],label[for="120mc"],label[for="121ma"],label[for="121mb"],label[for="121mc"],label[for="122ma"],label[for="122mb"],label[for="122mc"], ' +
    'label[for="123ma1"],label[for="123ma2"],label[for="123mb1"],label[for="123mb2"],label[for="123mc1"],label[for="123mc2"],' +
    'label[for="124ma1"],label[for="124ma2"],label[for="124mb1"],label[for="124mb2"],label[for="124mc1"],label[for="124mc2"],' +
    'label[for="125ma1"],label[for="125ma2"],label[for="125mb1"],label[for="125mb2"],label[for="125mc1"],label[for="125mc2"],' +
    'label[for="126ma"],label[for="126mb"],label[for="126mc"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();


function row12FirstOption() {
    $j("#120,#120f, #120d,#120ma,#120mb, #120mc,#dose14a").show()

    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],' +
        'label[for="120mb"],label[for="120mc"],label[for="dose14a"]').show();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc,#dose14b,#dose14c,#dose14d,#dose14e,#dose14f," +
        "#dose14g,#dose14h,#dose14i").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],' +
        'label[for="121mb"],label[for="121mc"],label[for="dose14b"],label[for="dose14c"],label[for="dose14d"],' +
        'label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14h"],label[for="dose14i"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();


}
function row12SecondOption() {
    $j("#120,#120f, #120d,#120ma,#120mb, #120mc,#dose14a,#dose14c,#dose14d,#dose14e.#dose14f," +
        "#dose14g,#dose14h,#dose14i").hide()

    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],label[for="120mb"],' +
        'label[for="120mc"],label[for="dose14a"],label[for="dose14c"],label[for="dose14d"],' +
        'label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14h"],label[for="dose14i"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc,#dose14b").show()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],' +
        'label[for="121mc"],label[for="dose14b"]').show();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();

}function row12ThirdOption() {
    $j("#120,#120f, #120d,#120ma,#120mb, #120mc,#dose14a,#dose14b,#dose14d,#dose14e," +
        "#dose14f,#dose14g,#dose14h,#dose14i").hide()

    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],label[for="120mb"],' +
        'label[for="120mc"],label[for="dose14a"],label[for="dose14b"],label[for="dose14d"],' +
        'label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14h"],' +
        'label[for="dose14i"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],label[for="121mc"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc,#dose14c").show()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],' +
        'label[for="122mb"],label[for="122mc"],,label[for="dose14c"]').show();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();

}function row12FourthOption() {
    $j("#120,#120f, #120d,#120ma,#120mb, #120mc,#dose14a,#dose14b,#dose14c," +
        "#dose14f,#dose14g,#dose14h,#dose14i").hide()

    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],label[for="120mb"],' +
        'label[for="120mc"],label[for="dose14a"],label[for="dose14b"],label[for="dose14c"],' +
        'label[for="dose14f"],label[for="dose14g"],label[for="dose14h"],' +
        'label[for="dose14i"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],label[for="121mc"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2,#dose14d,#dose14e").show()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],' +
        'label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]' +
        ',label[for="dose14d"],label[for="dose14e"]').show();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();

}function row12FifthOption() {

    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],label[for="120mb"],' +
        'label[for="120mc"],label[for="dose14a"],label[for="dose14b"],label[for="dose14c"],label[for="dose14d"]' +
        ',label[for="dose14h"],label[for="dose14i"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc,#dose14a,#dose14b,#dose14c,#dose14d,#dose14h,#dose14i").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],label[for="121mc"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2,#dose14f,#dose14g").show()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],' +
        'label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]' +
        ',label[for="dose14f"],label[for="dose14g"]').show();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();

}function row12SixthOption() {
    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],' +
        'label[for="120mb"],label[for="120mc"],label[for="dose14a"],label[for="dose14b"],label[for="dose14c"]' +
        ',label[for="dose14d"],label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14j"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc,#dose14a,#dose14b,#dose14c,#dose14d,#dose14e,#dose14f,#dose14g" +
        ",#dose14j").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],label[for="121mc"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2,#dose14h,#dose14i").show()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],' +
        'label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],' +
        'label[for="125mc2"],label[for="dose14h"],label[for="dose14i"]').show();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc").hide()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],label[for="126mc"]').hide();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();
}

function row12SeventhOption() {
    $j('label[for="120"],label[for="120f"],label[for="120d"],label[for="120ma"],label[for="120mb"],' +
        'label[for="120mc"],label[for="dose14a"],label[for="dose14b"],label[for="dose14c"],label[for="dose14d"]' +
        ',label[for="dose14e"],label[for="dose14f"],label[for="dose14g"],label[for="dose14h"],' +
        'label[for="dose14i"]').hide();

    $j("#121,#121f, #121d,#121ma,#121mb, #121mc,#dose14a,#dose14b,#dose14c,#dose14d,#dose14e,#dose14f," +
        "#dose14g,#dose14h,#dose14i").hide()

    $j('label[for="121"],label[for="121f"],label[for="121d"],label[for="121ma"],label[for="121mb"],label[for="121mc"]').hide();

    $j("#122,#122f, #122d,#122ma,#122mb, #122mc").hide()

    $j('label[for="122"],label[for="122f"],label[for="122d"],label[for="122ma"],label[for="122mb"],label[for="122mc"]').hide();


    $j("#123,#123fa,#123fb, #123d,#123ma1,#123mb1, #123mc1,#123ma2,#123mb2, #123mc2").hide()

    $j('label[for="123"],label[for="123fa"],label[for="123fb"],label[for="123d"],label[for="123ma1"],label[for="123mb1"],label[for="123mc1"],label[for="123ma2"],label[for="123mb2"],label[for="123mc2"]').hide();

    $j("#124,#124fa,#124fb, #124d,#124ma1,#124mb1, #124mc1,#124ma2,#124mb2, #124mc2").hide()

    $j('label[for="124"],label[for="124fa"],label[for="124fb"],label[for="124d"],label[for="124ma1"],label[for="124mb1"],label[for="124mc1"],label[for="124ma2"],label[for="124mb2"],label[for="124mc2"]').hide();

    $j("#125,#125fa,#125fb, #125d,#125ma1,#125mb1, #125mc1,#125ma2,#125mb2, #125mc2").hide()

    $j('label[for="125"],label[for="125fa"],label[for="125fb"],label[for="125d"],label[for="125ma1"],label[for="125mb1"],label[for="125mc1"],label[for="125ma2"],label[for="125mb2"],label[for="125mc2"]').hide();



    $j("#126,#126f, #126d,#126ma,#126mb, #126mc,#dose14j").show()

    $j('label[for="126"],label[for="126f"],label[for="126d"],label[for="126ma"],label[for="126mb"],' +
        'label[for="126mc"],label[for="dose14j"]').show();
    $j("#127,#127f, #127d,#127ma,#127mb, #127mc").hide()

    $j('label[for="127"],label[for="127f"],label[for="127d"],label[for="127ma"],label[for="127mb"],label[for="127mc"]').hide();
}

////////////////////////////////////////////////////////////        co-trimozole



$j("#201,#202,#203,#204,#205,#206 , #201fa,#201fb, #202fa, #202fb,#203fa,#203fb, #204fa,#204fb," +
    "#205f,#206f,#dose22a,#dose22b,#dose22c,#dose22d,#dose22e,#dose22f,#dose22g,#dose22h,#dose22i").hide()

$j('label[for="201"],label[for="202"],label[for="203"],label[for="204"],label[for="205"] ,' +
    'label[for="206"],label[for="201fa"],label[for="201fb"],' +
    'label[for="202fa"],label[for="202fb"],label[for="203fa"],label[for="203fb"],' +
    'label[for="204fa"],label[for="204fb"],label[for="205f"],label[for="206f"],label[for="dose22a"],' +
    'label[for="dose22b"],label[for="dose22c"],label[for="dose22d"],label[for="dose22e"]' +
    ',label[for="dose22f"],label[for="dose22g"],label[for="dose22h"],label[for="dose22i"]').hide();


$j("#201da,#201db, #202da, #202db,#203da, #203db,#204da,#204db,#205d,#206d").hide()

$j('label[for="201da"],label[for="201db"],label[for="202da"],label[for="202db"],label[for="203da"],label[for="203db"],' +
    'label[for="204da"],label[for="204db"],label[for="205d"], label[for="206d"]').hide();


$j("#201ma1,#201ma2,#201mb1,#201mb2,#201mc1,#201mc2, #202ma1,#202ma2,#202mb1,#202mb2,#202mc1,#202mc2, " +
    "#203ma1,#203ma2,#203mb1,#203mb2,#203mc1,#203mc2,#204ma1,#204ma2,#204mb1,#204mb2,#204mc1,#204mc2, " +
    "#205ma,#205mb,#205mc,#206ma,#206mb,#206mc,").hide()

$j('label[for="201ma1"],label[for="201ma2"],label[for="201mb1"],label[for="201mb2"],label[for="201mc1"],label[for="201mc2"],' +
    'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"],label[for="202mb2"],label[for="202mc1"],label[for="202mc2"],' +
    'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"],label[for="203mb2"],label[for="203mc1"],label[for="203mc2"],' +
    'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"],label[for="204mb2"],label[for="204mc1"],label[for="204mc2"],' +
    'label[for="205ma"],label[for="205mb"],label[for="205mc"],label[for="206ma"],label[for="206mb"],label[for="206mc"]').hide();


function row20FirstOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1,#201mc2,#dose22b,#dose22c").show()
    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"],label[for="dose22a"],label[for="dose22b"],label[for="dose22c"]').show();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1,#202mc2,#dose22a" +
        ",#dose22d,#dose22e,#dose22f,#dose22g,#dose22h,#dose22i").hide()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"],' +
        'label[for="202mc1"],label[for="202mc2"],label[for="dose22a"],' +
        'label[for="dose22d"],label[for="dose22e"],label[for="dose22f"],label[for="dose22g"],' +
        'label[for="dose22h"],label[for="dose22i"]').hide();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2").hide()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"]').hide();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2").hide()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"]').hide();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc").hide()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"]').hide();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").hide()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').hide();

}
function row20SecondOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1,#201mc2," +
        "#dose22a,#dose22b,#dose22c,#dose22f,#dose22g,#dose22h,#dose22i").hide()

    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"],label[for="dose22a"],label[for="dose22b"]' +
        ',label[for="dose22c"],label[for="dose22f"],label[for="dose22g"],label[for="dose22h"]' +
        ',label[for="dose22i"]').hide();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1," +
        "#202mc2,#dose22d,#dose22e").show()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"],' +
        'label[for="202mc1"],label[for="202mc2"],label[for="dose22d"],label[for="dose22e"]').show();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2").hide()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"]').hide();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2").hide()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"]').hide();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc").hide()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"]').hide();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").hide()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').hide();

}function row20ThirdOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1," +
        "#201mc2,#dose22a,#dose22b,#dose22c,#dose22d,#dose22e,#dose22h,#dose22i").hide()

    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"],label[for="dose22a"],label[for="dose22b"]' +
        ',label[for="dose22c"],label[for="dose22d"],label[for="dose22e"],label[for="dose22h"],label[for="dose22i"]').hide();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1,#202mc2").hide()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"],' +
        'label[for="202mc1"],label[for="202mc2"]').hide();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2," +
        "#dose22f,#dose22g").show()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"],label[for="dose22f"],label[for="dose22g"]').show();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2").hide()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"]').hide();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc").hide()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"]').hide();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").hide()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').hide();


}function row20FourthOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1,#201mc2,#dose22a" +
        ",#dose22b,#dose22c,#dose22d,#dose22e,#dose22f,#dose22g").hide()

    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"],label[for="dose22a"],label[for="dose22b"],label[for="dose22c"]' +
        ',label[for="dose22d"],label[for="dose22e"],label[for="dose22f"],label[for="dose22g"]').hide();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1,#202mc2").hide()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"]' +
        'label[for="202mc1"],label[for="202mc2"]').hide();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2").hide()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"]').hide();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2,#dose22h,#dose22i").show()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"],label[for="dose22h"],label[for="dose22i"]').show();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc").hide()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"]').hide();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").hide()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').hide();

}function row20FifthOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1,#201mc2,#dose22a" +
        "#dose22b,#dose22c,#dose22d,#dose22e,#dose22f,#dose22g,#dose22i,#dose22h").hide()

    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"],label[for="dose22a"],label[for="dose22b"]' +
        ',label[for="dose22c"],label[for="dose22d"],label[for="dose22e"],label[for="dose22f"],label[for="dose22g"]' +
        ',label[for="dose22i"],label[for="dose22h"]').hide();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1,#202mc2").hide()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"],' +
        'label[for="202mc1"],label[for="202mc2"]').hide();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2").hide()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"]').hide();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2").hide()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"]').hide();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc,#dose22a").show()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"],label[for="dose22a"]').show();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").hide()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').hide();

}function row20SixthOption() {
    $j("#201,#201fa, #201fb,#201da,#201db, #201ma1,#201ma2, #201mb1,#201mb2,#201mc1,#201mc2").hide()

    $j('label[for="201"],label[for="201fa"],label[for="201fb"],label[for="201da"],label[for="201db"],' +
        'label[for="201ma1"],label[for="201ma2"],label[for="201mb1"], label[for="201mb2"],' +
        'label[for="201mc1"],label[for="201mc2"]').hide();


    $j("#202,#202fa, #202fb,#202da,#202db, #202ma1,#202ma2, #202mb1,#202mb2,#202mc1,#202mc2").hide()

    $j('label[for="202"],label[for="202fa"],label[for="202fb"],label[for="202da"],label[for="202db"],' +
        'label[for="202ma1"],label[for="202ma2"],label[for="202mb1"], label[for="202mb2"],' +
        'label[for="202mc1"],label[for="202mc2"]').hide();

    $j("#203,#203fa, #203fb,#203da,#203db, #203ma1,#203ma2, #203mb1,#203mb2,#203mc1,#203mc2").hide()

    $j('label[for="203"],label[for="203fa"],label[for="203fb"],label[for="203da"],label[for="203db"],' +
        'label[for="203ma1"],label[for="203ma2"],label[for="203mb1"], label[for="203mb2"],' +
        'label[for="203mc1"],label[for="203mc2"]').hide();

    $j("#204,#204fa, #204fb,#204da,#204db, #204ma1,#204ma2, #204mb1,#204mb2,#204mc1,#204mc2").hide()

    $j('label[for="204"],label[for="204fa"],label[for="204fb"],label[for="204da"],label[for="204db"],' +
        'label[for="204ma1"],label[for="204ma2"],label[for="204mb1"], label[for="204mb2"],' +
        'label[for="204mc1"],label[for="204mc2"]').hide();

    $j("#205,#205f, #205d, #205ma, #205mb,#205mc").hide()

    $j('label[for="205"],label[for="205f"],label[for="205d"], ' +
        'label[for="205ma"], label[for="205mb"], label[for="205mc"], ' +
        'label[for="205mc"]').hide();

    $j("#206,#206f, #206d, #206ma, #206mb,#206mc").show()

    $j('label[for="206"],label[for="206f"],label[for="206d"], ' +
        'label[for="206ma"], label[for="206mb"], label[for="206mc"], ' +
        'label[for="206mc"]').show();
}


/////////////////////////// dapsone

$j("#210,#211,#212,#213,#214,#215,#216,#217 , #210f,#211f,#212f,#213fa,#213fb, " +
    "#214fa,#214fb,#215fa,,#215fb,#216f,#217f,#dose23a,#dose23b,#dose23c").hide()

$j('label[for="210"],label[for="211"],label[for="212"],label[for="213"],label[for="214"],label[for="215"],' +
    'label[for="216"],label[for="217"],' +
    'label[for="210f"],label[for="211f"],label[for="212f"],label[for="213fa"],' +
    'label[for="213fb"],label[for="214fa"],label[for="214fb"],label[for="215fa"],' +
    'label[for="215fb"],label[for="216f"],label[for="217f"],label[for="dose23a"],label[for="dose23b"],label[for="dose23c"]').hide();


$j("#210d,#211d,#212d,#213d,#214d,#215d,#216d,#217d").hide()

$j('label[for="210d"],label[for="211d"],label[for="212d"],label[for="213d"],label[for="214d"],label[for="215d"],label[for="216d"],label[for="216d"],,label[for="217d"]').hide();


$j("#210ma,#210mb,#210mc,#211ma,#211mb,#211mc,#212ma,#212mb,#212mc,  #213ma1,#213ma2,#213mb1,#213mb2,#213mc1,#213mc2, " +
    "#214ma1,#214ma2,#214mb1,#214mb2,#214mc1,#214mc2, #215ma1,#215ma2,#215mb1,#215mb2,#215mc1,#215mc2,#216ma,#216mb,#216mc, #217ma,#217mb,#217mc ").hide()

$j('label[for="210ma"],label[for="210mb"],label[for="210mc"],label[for="211ma"],label[for="211mb"],label[for="211mc"],label[for="212ma"],label[for="212mb"],label[for="212mc"], ' +
    'label[for="213ma1"],label[for="213ma2"],label[for="213mb1"],label[for="213mb2"],label[for="213mc1"],label[for="213mc2"],' +
    'label[for="214ma1"],label[for="214ma2"],label[for="214mb1"],label[for="214mb2"],label[for="214mc1"],label[for="214mc2"],' +
    'label[for="215ma1"],label[for="215ma2"],label[for="215mb1"],label[for="215mb2"],label[for="215mc1"],label[for="215mc2"],' +
    'label[for="216ma"],label[for="216mb"],label[for="216mc"],label[for="217ma"],label[for="217mb"],label[for="217mc"]').hide();


function row21FirstOption() {
    $j("#210,#210f, #210d,#210ma,#210mb, #210mc,#dose23a").show()

    $j('label[for="210"],label[for="210f"],label[for="210d"],label[for="210ma"],label[for="210mb"],' +
        'label[for="210mc"],label[for="dose23a"]').show();

    $j("#211,#211f, #211d,#211ma,#211mb, #211mc,#dose23b,#dose23c").hide()

    $j('label[for="211"],label[for="211f"],label[for="211d"],label[for="211ma"],' +
        'label[for="211mb"],label[for="211mc"],label[for="dose23b"],label[for="dose23c"]').hide();

    $j("#212,#212f, #212d,#212ma,#212mb, #212mc").hide()

    $j('label[for="212"],label[for="212f"],label[for="212d"],label[for="212ma"],label[for="212mb"],label[for="212mc"]').hide();




}
function row21SecondOption() {
    $j("#210,#210f, #210d,#210ma,#210mb, #210mc,#dose23a,#dose23c").hide()

    $j('label[for="210"],label[for="210f"],label[for="210d"],label[for="210ma"],label[for="210mb"],' +
        'label[for="210mc"],label[for="dose23a"],label[for="dose23c"]').hide();

    $j("#211,#211f, #211d,#211ma,#211mb, #211mc,#dose23b").show()

    $j('label[for="211"],label[for="211f"],label[for="211d"],label[for="211ma"],label[for="211mb"],' +
        'label[for="211mc"],label[for="dose23b"]').show();

    $j("#212,#212f, #212d,#212ma,#212mb, #212mc").hide()

    $j('label[for="212"],label[for="212f"],label[for="212d"],label[for="212ma"],label[for="212mb"],label[for="212mc"]').hide();



}function row21ThirdOption() {
    $j("#210,#210f, #210d,#210ma,#210mb, #210mc,#dose23a,#dose23b").hide()

    $j('label[for="210"],label[for="210f"],label[for="210d"],label[for="210ma"],label[for="210mb"],' +
        'label[for="210mc"],label[for="dose23a"],label[for="dose23b"]').hide();

    $j("#211,#211f, #211d,#211ma,#211mb, #211mc").hide()

    $j('label[for="211"],label[for="211f"],label[for="211d"],label[for="211ma"],label[for="211mb"],label[for="211mc"]').hide();

    $j("#212,#212f, #212d,#212ma,#212mb, #212mc,#dose23c").show()

    $j('label[for="212"],label[for="212f"],label[for="212d"],label[for="212ma"],label[for="212mb"],' +
        'label[for="212mc"],label[for="dose23c"]').show();



}






/////////////////////////// Isoniazid

$j("#220,#221,#222,#223,#224,#225,#226,#227 , #220f,#221f,#222f,#223fa,#223fb, " +
    "#224fa,#224fb,#225fa,,#225fb,#226f,#227f,#dose24a,#dose24b,#dose24c,#dose24d").hide()

$j('label[for="220"],label[for="221"],label[for="222"],label[for="223"],label[for="224"],' +
    'label[for="225"],label[for="226"],label[for="227"],' +
    'label[for="220f"],label[for="221f"],label[for="222f"],label[for="223fa"],' +
    'label[for="223fb"],label[for="224fa"],label[for="224fb"],label[for="225fa"],label[for="225fb"],' +
    'label[for="226f"],label[for="227f"],label[for="dose24a"],label[for="dose24b"],label[for="dose24c"],' +
    'label[for="dose24d"]').hide();


$j("#220d,#221d,#222d,#223d,#224d,#225d,#226d,#227d").hide()

$j('label[for="220d"],label[for="221d"],label[for="222d"],label[for="223d"],label[for="224d"],label[for="225d"],label[for="226d"],label[for="226d"],label[for="227d"]').hide();


$j("#220ma,#220mb,#220mc,#221ma,#221mb,#221mc,#222ma,#222mb,#222mc,  #223ma1,#223ma2,#223mb1,#223mb2,#223mc1,#223mc2, " +
    "#224ma1,#224ma2,#224mb1,#224mb2,#224mc1,#224mc2, #225ma1,#225ma2,#225mb1,#225mb2,#225mc1,#225mc2,#226ma,#226mb,#226mc, #227ma,#227mb,#227mc ").hide()

$j('label[for="220ma"],label[for="220mb"],label[for="220mc"],label[for="221ma"],label[for="221mb"],label[for="221mc"],label[for="222ma"],label[for="222mb"],label[for="222mc"], ' +
    'label[for="223ma1"],label[for="223ma2"],label[for="223mb1"],label[for="223mb2"],label[for="223mc1"],label[for="223mc2"],' +
    'label[for="224ma1"],label[for="224ma2"],label[for="224mb1"],label[for="224mb2"],label[for="224mc1"],label[for="224mc2"],' +
    'label[for="225ma1"],label[for="225ma2"],label[for="225mb1"],label[for="225mb2"],label[for="225mc1"],label[for="225mc2"],' +
    'label[for="226ma"],label[for="226mb"],label[for="226mc"],label[for="227ma"],label[for="227mb"],label[for="227mc"]').hide();


function row22SecondOption() {
    $j("#221,#221f, #221d,#221ma,#221mb, #221mc,#dose24a").show()

    $j('label[for="221"],label[for="221f"],label[for="221d"],' +
        'label[for="221ma"],label[for="221mb"],label[for="221mc"],label[for="dose24a"]').show();

    $j("#222,#222f, #222d,#222ma,#222mb, #222mc,#dose24b,#dose24c,#dose24d").hide()

    $j('label[for="222"],label[for="222f"],label[for="222d"],label[for="222ma"],label[for="222mb"],' +
        'label[for="222mc"],label[for="dose24b"],label[for="dose24c"],label[for="dose24d"]').hide();


    $j("#223,#223fa,#223fb, #223d,#223ma1,#223mb1, #223mc1,#223ma2,#223mb2, #223mc2").hide()

    $j('label[for="223"],label[for="223fa"],label[for="223fb"],label[for="223d"],label[for="223ma1"],label[for="223mb1"],label[for="223mc1"],label[for="223ma2"],label[for="223mb2"],label[for="223mc2"]').hide();



}function row22ThirdOption() {
    $j("#221,#221f, #221d,#221ma,#221mb, #221mc,#dose24a,#dose24c,#dose24d").hide()

    $j('label[for="221"],label[for="221f"],label[for="221d"],label[for="221ma"],label[for="221mb"],' +
        'label[for="221mc"],label[for="dose24a"],label[for="dose24c"],label[for="dose24d"]').hide();

    $j("#222,#222f, #222d,#222ma,#222mb, #222mc,#dose24b").show()

    $j('label[for="222"],label[for="222f"],label[for="222d"],label[for="222ma"],label[for="222mb"],' +
        'label[for="222mc"],label[for="dose24b"]').show();


    $j("#223,#223fa,#223fb, #223d,#223ma1,#223mb1, #223mc1,#223ma2,#223mb2, #223mc2").hide()

    $j('label[for="223"],label[for="223fa"],label[for="223fb"],label[for="223d"],label[for="223ma1"],label[for="223mb1"],label[for="223mc1"],label[for="223ma2"],label[for="223mb2"],label[for="223mc2"]').hide();




}function row22FourthOption() {

    $j("#221,#221f, #221d,#221ma,#221mb, #221mc,#dose24a,#dose24b").hide()

    $j('label[for="221"],label[for="221f"],label[for="221d"],label[for="221ma"],label[for="221mb"],' +
        'label[for="221mc"],label[for="dose24a"],label[for="dose24b"]').hide();

    $j("#222,#222f, #222d,#222ma,#222mb, #222mc").hide()

    $j('label[for="222"],label[for="222f"],label[for="222d"],label[for="222ma"],label[for="222mb"],label[for="222mc"]').hide();


    $j("#223,#223fa,#223fb, #223d,#223ma1,#223mb1, #223mc1,#223ma2,#223mb2, #223mc2,#dose24c,#dose24d").show()

    $j('label[for="223"],label[for="223fa"],label[for="223fb"],label[for="223d"],label[for="223ma1"],' +
        'label[for="223mb1"],label[for="223mc1"],label[for="223ma2"],label[for="223mb2"],' +
        'label[for="223mc2"],label[for="dose24c"],label[for="dose24d"]').show();



}
function validateHivPedsForm(){
    var num=9;
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
    if ((!$j("#arvtype1").is(':checked'))&&(!$j("#arvtype2").is(':checked'))&&(!$j("#arvtype3").is(':checked'))&&(!$j("#arvtype4").is(':checked'))&&(!$j("#arvtype5").is(':checked'))&&(!$j("#arvtype6").is(':checked')))
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
    if ($j("#drug1").is(':checked'))
    {
        if(($j("#1").is(':checked')))
        {
            if ((!$j("#1f").is(':checked')||($j("input#qnty1").val()=="") ))
            {
                num=0;
                return num;
            }
            else {
                num=1;
            }
            if($j("input#Other1").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#1ma").is(':checked'))&&(!$j("#1mb").is(':checked'))&&(!$j("#1mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else{
                num=1;
            }

        }
        else{num=0;}
        if(($j("#2").is(':checked')))
        {
            if ((!$j("#2f").is(':checked')||($j("input#qnty1").val()=="")    ))
            {
                num=0;
                return num;
            }
            else{num=1;}
            if($j("input#Other1").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#2ma").is(':checked'))&&(!$j("#2mb").is(':checked'))&&(!$j("#2mc").is(':checked'))))
            {
                num=0;
                return num;
            }else{num=1;}
        }
        if(($j("#3").is(':checked')))
        {
            if ((!$j("#3f").is(':checked')||($j("input#qnty1").val()=="")    ))
            {
                num=0;
                return num;
            }else{num=1;}
            if($j("input#Other1").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#3ma").is(':checked'))&&(!$j("#3mb").is(':checked'))&&(!$j("#3mc").is(':checked'))))
            {
                num=0;
                return num;
            }else{num=1;}
        }
        if(($j("#4").is(':checked'))){
            if($j("#4fa").is(':checked')&& !$j("#4fb").is(':checked')){
                if ((!$j("#4fa").is(':checked')||($j("input#qnty1").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other1").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#4ma").is(':checked'))&&(!$j("#4mb").is(':checked'))&&(!$j("#4mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#4fa").is(':checked')&& $j("#4fb").is(':checked')){
                if ((!$j("#4fb").is(':checked')||($j("input#qnty1").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if (((!$j("#4ma1").is(':checked'))&&(!$j("#4mb2").is(':checked'))&&(!$j("#4mc3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#5").is(':checked'))){
            if($j("#5fa").is(':checked')&& !$j("#5fb").is(':checked')){
                if ((!$j("#5fa").is(':checked')||(!$j("#5da").is(':checked'))||($j("input#qnty1").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other1").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#5ma").is(':checked'))&&(!$j("#5mb").is(':checked'))&&(!$j("#5mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#5fa").is(':checked')&& $j("#5fb").is(':checked')){
                if ((!$j("#5fb").is(':checked')||($j("input#qnty1").val()==""))){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if (((!$j("#5ma1").is(':checked'))&&(!$j("#5mb2").is(':checked'))&&(!$j("#5mc3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }

        if(($j("#6").is(':checked'))){
            if ((!$j("#6f").is(':checked')||($j("input#qnty1").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other1").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#6ma").is(':checked'))&&(!$j("#6mb").is(':checked'))&&(!$j("#6mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug2").is(':checked')) {
        if(($j("#11").is(':checked'))){
            if ((!$j("#11f").is(':checked')||($j("input#qnty2").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other2").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#11ma").is(':checked'))&&(!$j("#11mb").is(':checked'))&&(!$j("#11mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#12").is(':checked'))){
            if ((!$j("#12f").is(':checked')||($j("input#qnty2").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other2").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#12ma").is(':checked'))&&(!$j("#12mb").is(':checked'))&&(!$j("#12mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#13").is(':checked'))){
            if ((!$j("#13f").is(':checked')||($j("input#qnty2").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other2").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#13ma").is(':checked'))&&(!$j("#13mb").is(':checked'))&&(!$j("#13mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#14").is(':checked'))){
            if($j("#14fa").is(':checked')&& !$j("#14fb").is(':checked')){
                if ((!$j("#14fa").is(':checked')||($j("input#qnty2").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other2").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#14ma").is(':checked'))&&(!$j("#14mb").is(':checked'))&&(!$j("#14mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#14fa").is(':checked')&& $j("#14fb").is(':checked')){
                if ((!$j("#14fb").is(':checked')||($j("input#qnty2").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other2").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#14ma1").is(':checked'))&&(!$j("#14mb2").is(':checked'))&&(!$j("#14mc3").is(':checked'))))
                {
                    num=0;
                    return num;

                }
                else
                    num=1;
            }
        }
        if(($j("#15").is(':checked'))){
            if($j("#15fa").is(':checked')&& !$j("#15fb").is(':checked')){
                if ((!$j("#15fa").is(':checked')||(!$j("#15da").is(':checked'))||($j("input#qnty2").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other2").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#15ma").is(':checked'))&&(!$j("#15mb").is(':checked'))&&(!$j("#15mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#15fa").is(':checked')&& $j("#15fb").is(':checked')){
                if ((!$j("#15fb").is(':checked')||($j("input#qnty1").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other2").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#15ma1").is(':checked'))&&(!$j("#15mb2").is(':checked'))&&(!$j("#15mc3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }

        if(($j("#16").is(':checked'))){
            if ((!$j("#16f").is(':checked')||($j("input#qnty2").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other2").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#16ma").is(':checked'))&&(!$j("#16mb").is(':checked'))&&(!$j("#16mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug3").is(':checked')) {
        if(($j("#21").is(':checked'))){
            if ((!$j("#21f").is(':checked')||($j("input#qnty3").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other3").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#21ma").is(':checked'))&&(!$j("#21mb").is(':checked'))&&(!$j("#21mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#22").is(':checked'))){
            if ((!$j("#22f").is(':checked')||($j("input#qnty3").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other3").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#22ma").is(':checked'))&&(!$j("#22mb").is(':checked'))&&(!$j("#22mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#23").is(':checked'))){
            if ((!$j("#23f").is(':checked')||($j("input#qnty3").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            num=1;
            if($j("input#Other3").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#23ma").is(':checked'))&&(!$j("#23mb").is(':checked'))&&(!$j("#23mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#24").is(':checked'))){
            if($j("#24fa").is(':checked')&& !$j("#24fb").is(':checked')){
                if ((!$j("#24fa").is(':checked')||($j("input#qnty3").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other3").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#24ma").is(':checked'))&&(!$j("#24mb").is(':checked'))&&(!$j("#24mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;

            }
            if(!$j("#24fa").is(':checked')&& $j("#24fb").is(':checked')){
                if ((!$j("#24fb").is(':checked')||($j("input#qnty3").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                num=1;
                if($j("input#Other3").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#24ma1").is(':checked'))&&(!$j("#24mb2").is(':checked'))&&(!$j("#24mc3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#25").is(':checked'))){
            if(($j("#25").is(':checked'))){
                if ((!$j("#25fb").is(':checked')||($j("input#qnty3").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other3").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#25ma1").is(':checked'))&&(!$j("#25mb2").is(':checked'))&&(!$j("#25mc3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#26").is(':checked'))){
            if ((!$j("#26f").is(':checked')||($j("input#qnty3").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other3").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#26ma").is(':checked'))&&(!$j("#26mb").is(':checked'))&&(!$j("#26mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }

    }    if ($j("#drug4").is(':checked')) {
        if(($j("input#a1").val()!="")){
            if($j("input#Other4").val() !="") {
                num=1;
                return num;
            }
            if ((!$j("#a2").is(':checked')||($j("input#a3").val()=="")||($j("input#qnty4").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;

    }  if ($j("#drug5").is(':checked')) {
        if(($j("input#b1").val()!="")){
            if($j("input#Other5").val() !="") {
                num=1;
                return num;
            }
            if ((!$j("#b2").is(':checked')||($j("input#b3").val()=="")||($j("input#qnty5").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
        }

        else
            num=0;

    }
    if ($j("#drug6").is(':checked')) {
        if(($j("#31").is(':checked'))){
            if($j("#31fa").is(':checked')&& !$j("#31fb").is(':checked')){
                if (($j("input#qnty6").val()=="")){
                    num=0;
                    return num;
                }
                else  num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#31ma1").is(':checked') && !$j("#31ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else  num=1;

            }
            if(!$j("#31fa").is(':checked')&& $j("#31fb").is(':checked')){
                if (($j("input#qnty6").val()=="")){
                    num=0;
                    return num;
                }
                else  num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#31ma1").is(':checked') && !$j("#31ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else  num=1;
            }
        }
        else
            num=0;
        if(($j("#32").is(':checked'))){
            if($j("#32fa").is(':checked')&& !$j("#32fb").is(':checked')){
                if ((!$j("#32fa").is(':checked')||($j("input#qnty6").val()=="")   )){
                    num=0;
                    ;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#32ma1").is(':checked') && !$j("#32ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#32fa").is(':checked')&& $j("#32fb").is(':checked')){
                if ((!$j("#32fb").is(':checked')||($j("input#qnty6").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#32ma1").is(':checked') && !$j("#32ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }

        }
        if(($j("#33").is(':checked'))){
            if($j("#33fa").is(':checked')&& !$j("#33fb").is(':checked')){
                if (($j("input#qnty6").val()=="")){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#33ma1").is(':checked') && !$j("#33ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#33fa").is(':checked')&& $j("#33fb").is(':checked')){
                if (($j("input#qnty6").val()=="")){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#33ma1").is(':checked') && !$j("#33ma2").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#34").is(':checked'))){
            if($j("#34fa").is(':checked')&& !$j("#34fb").is(':checked')){
                if ((!$j("#34fa").is(':checked')||($j("input#qnty6").val()=="")   )){
                    num=0;
                    ;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#34ma1").is(':checked') && !$j("#34ma").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#34fa").is(':checked')&& $j("#34fb").is(':checked')){
                if ((!$j("#34fb").is(':checked')||($j("input#qnty6").val()=="")   )){
                    num=0;
                    return num;

                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (!$j("#34ma1").is(':checked') && !$j("#34ma").is(':checked'))
                {
                    num=0;
                    return num;
                }
                else num=1;
            }
        }
        if(($j("#35").is(':checked'))){
            if(($j("#35").is(':checked'))){
                if ((!$j("#35f").is(':checked')||($j("input#qnty6").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other6").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#35ma").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#36").is(':checked'))){
            if ((!$j("#36f").is(':checked')||($j("input#qnty6").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other6").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#36ma").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug7").is(':checked')) {
        if(($j("#41").is(':checked'))){
            if($j("#41fa").is(':checked')&& !$j("#41fb").is(':checked')){
                if ((!$j("#41fa").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#41ma1").is(':checked'))&&(!$j("#41mb1").is(':checked'))&&(!$j("#41mc1").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;            }
            if(!$j("#41fa").is(':checked')&& $j("#41fb").is(':checked')){
                if ((!$j("#41fb").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#41ma2").is(':checked'))&&(!$j("#41mb2").is(':checked'))&&(!$j("#41mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        else
            num=0;
        if(($j("#42").is(':checked'))){
            if($j("#42fa").is(':checked')&& !$j("#42fb").is(':checked')){
                if ((!$j("#42fa").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#42ma1").is(':checked'))&&(!$j("#42mb1").is(':checked'))&&(!$j("#42mc1").is(':checked'))))

                {
                    num=0;
                    return num;

                }
                else
                    num=1;
            }
            if(!$j("#42fa").is(':checked')&& $j("#42fb").is(':checked')){
                if ((!$j("#42fb").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#42ma2").is(':checked'))&&(!$j("#42mb2").is(':checked'))&&(!$j("#42mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#43").is(':checked'))){
            if($j("#43fa").is(':checked')&& !$j("#43fb").is(':checked')){
                if ((!$j("#43fa").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#43ma1").is(':checked'))&&(!$j("#43mb1").is(':checked'))&&(!$j("#43mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#43fa").is(':checked')&& $j("#43fb").is(':checked')){
                if ((!$j("#43fb").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#43ma2").is(':checked'))&&(!$j("#43mb2").is(':checked'))&&(!$j("#43mc2").is(':checked'))))
                {
                    num=0;
                    return num;

                }
                else
                    num=1;
            }
        }
        if(($j("#44").is(':checked'))){
            if($j("#44fa").is(':checked')&& !$j("#44fb").is(':checked')){
                if ((!$j("#44fa").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#44ma1").is(':checked'))&&(!$j("#44mb1").is(':checked'))&&(!$j("#44mc1").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#44fa").is(':checked')&& $j("#44fb").is(':checked')){
                if ((!$j("#44fb").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#44ma2").is(':checked'))&&(!$j("#44mb2").is(':checked'))&&(!$j("#44mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#45").is(':checked'))){
            if(($j("#45").is(':checked'))){
                if ((!$j("#45f").is(':checked')||($j("input#qnty7").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other7").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#45ma").is(':checked'))&&(!$j("#45mb").is(':checked'))&&(!$j("#45mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#46").is(':checked'))){
            if ((!$j("#46f").is(':checked')||($j("input#qnty7").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other7").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#46ma").is(':checked'))&&(!$j("#46mb").is(':checked'))&&(!$j("#46mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }

    }
    if ($j("#drug8").is(':checked')) {
        if($j("#50").is(':checked')){
            if (!$j("#50f").is(':checked') || $j("input#qnty8").val()==""){
                num=0;
                alert("50f is "+$j("#50f").is(':checked')+" qnty is "+$j("input#qnty8").val());
                return num;
            }
            else
                num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#50ma").is(':checked'))&&(!$j("#50mb").is(':checked'))&&(!$j("#50mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
       else if(($j("#51").is(':checked'))){
            if ((!$j("#51f").is(':checked') ||($j("input#qnty8").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#51ma").is(':checked'))&&(!$j("#51mb").is(':checked'))&&(!$j("#51mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else  if(($j("#52").is(':checked'))){
            if ((!$j("#52f").is(':checked')||($j("input#qnty8").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#52ma").is(':checked'))&&(!$j("#52mb").is(':checked'))&&(!$j("#52mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
       else if(($j("#53").is(':checked'))){
            if($j("#53fa").is(':checked')&& !$j("#53fb").is(':checked')){
            if (($j("input#qnty8").val()=="")){
                num=0;
                return num;
            }
            else
                num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
            if (((!$j("#53ma1").is(':checked'))&&(!$j("#53ma2").is(':checked'))&&(!$j("#53ma3").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
            if(!$j("#53fa").is(':checked')&& $j("#53fb").is(':checked')){
                if (($j("input#qnty8").val()=="")){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#53mb1").is(':checked'))&&(!$j("#53mb2").is(':checked'))&&(!$j("#53mb3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
      else if(($j("#54").is(':checked'))){
            if($j("#54fa").is(':checked')&& !$j("#54fb").is(':checked')){
                if ((!$j("#54fa").is(':checked')||($j("input#qnty8").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#54ma1").is(':checked'))&&(!$j("#54ma2").is(':checked'))&&(!$j("#54ma3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#54fa").is(':checked')&& $j("#54fb").is(':checked')){
                if ((!$j("#54fb").is(':checked')||($j("input#qnty8").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#54mb1").is(':checked'))&&(!$j("#54mb2").is(':checked'))&&(!$j("#54mb3").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
       else if(($j("#55").is(':checked'))){
            if($j("#55fa").is(':checked')&& !$j("#55fb").is(':checked')){
                if ((!$j("#55fa").is(':checked')||($j("input#qnty8").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#55ma1").is(':checked'))&&(!$j("#55mb1").is(':checked'))&&(!$j("#55mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#55fa").is(':checked')&& $j("#55fb").is(':checked')){
                if ((!$j("#55fb").is(':checked')||($j("input#qnty8").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other8").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#55ma2").is(':checked'))&&(!$j("#55mb2").is(':checked'))&&(!$j("#55mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        } if(($j("#56").is(':checked'))){
                if ((!$j("#56f").is(':checked')||($j("input#qnty8").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
                if (((!$j("#56ma").is(':checked'))&&(!$j("#56mb").is(':checked'))&&(!$j("#56mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
        }
       else if(($j("#57").is(':checked'))){
                if ((!$j("#57f").is(':checked')||($j("input#qnty8").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
                if (((!$j("#57ma").is(':checked'))&&(!$j("#57mb").is(':checked'))&&(!$j("#57mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
        }
       else if(($j("#58").is(':checked'))){
            if ((!$j("#58").is(':checked')||($j("input#qnty8").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other8").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#58ma").is(':checked'))&&(!$j("#58mb").is(':checked'))&&(!$j("#58mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug9").is(':checked')) {
        if(($j("#61").is(':checked'))){
            if ((!$j("#61f").is(':checked')||($j("input#qnty9").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other9").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#61ma").is(':checked'))&&(!$j("#61mb").is(':checked'))&&(!$j("#61mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#62").is(':checked'))){
            if ((!$j("#62f").is(':checked')||($j("input#qnty9").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other9").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#62ma").is(':checked'))&&(!$j("#62mb").is(':checked'))&&(!$j("#62mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#63").is(':checked'))){
            if ((!$j("#63f").is(':checked')||($j("input#qnty9").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other9").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#63ma").is(':checked'))&&(!$j("#63mb").is(':checked'))&&(!$j("#63mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#64").is(':checked'))){
            if(($j("#64").is(':checked'))){
                if ((!$j("#64fb").is(':checked')||($j("input#qnty9").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other9").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#64ma").is(':checked'))&&(!$j("#64mb").is(':checked'))&&(!$j("#64mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#65").is(':checked'))){
            if ((!$j("#65").is(':checked')||($j("input#qnty9").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other9").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#65ma").is(':checked'))&&(!$j("#65mb").is(':checked'))&&(!$j("#65mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug10").is(':checked')) {
        if(($j("#71").is(':checked'))){
            if ((!$j("#71f").is(':checked')||($j("input#qnty10").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other10").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#71ma").is(':checked'))&&(!$j("#71mb").is(':checked'))&&(!$j("#71mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#72").is(':checked'))){
            if ((!$j("#72f").is(':checked')||($j("input#qnty10").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other10").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#72ma").is(':checked'))&&(!$j("#72mb").is(':checked'))&&(!$j("#72mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#73").is(':checked'))){
            if ((!$j("#73f").is(':checked')||($j("input#qnty10").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other10").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#73ma").is(':checked'))&&(!$j("#73mb").is(':checked'))&&(!$j("#73mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#74").is(':checked'))){
            if(($j("#74").is(':checked'))){
                if ((!$j("#74fb").is(':checked')||($j("input#qnty10").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other10").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#74ma").is(':checked'))&&(!$j("#74mb").is(':checked'))&&(!$j("#74mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#75").is(':checked'))){
            if ((!$j("#75").is(':checked')||($j("input#qnty10").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other10").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#75ma").is(':checked'))&&(!$j("#75mb").is(':checked'))&&(!$j("#75mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug11").is(':checked')) {
        if(($j("#81").is(':checked'))){
            if ((!$j("#81f").is(':checked')||($j("input#qnty11").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other11").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#81ma").is(':checked'))&&(!$j("#81mb").is(':checked'))&&(!$j("#81mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#82").is(':checked'))){
            if ((!$j("#82f").is(':checked')||(!$j("#82d").is(':checked'))||($j("input#qnty11").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other11").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#82ma").is(':checked'))&&(!$j("#82mb").is(':checked'))&&(!$j("#82mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#83").is(':checked'))){
            if ((!$j("#83f").is(':checked')||($j("input#qnty11").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other11").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#83ma").is(':checked'))&&(!$j("#83mb").is(':checked'))&&(!$j("#83mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#84").is(':checked'))){
                if ((!$j("#84fa").is(':checked')||($j("input#qnty11").val()==""))){
                    num=0;
                    return num;
                }
                else
                    num=1;
            if($j("input#Other11").val() !="") {
                num=1;
                return num;
            }
                if (((!$j("#84ma").is(':checked'))&&(!$j("#84mb").is(':checked'))&&(!$j("#84mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
        }
        if(($j("#85").is(':checked'))){
            if ((!$j("#85").is(':checked')||($j("input#qnty11").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other11").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#85ma").is(':checked'))&&(!$j("#85mb").is(':checked'))&&(!$j("#85mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug12").is(':checked')) {
        if(($j("#91").is(':checked'))){
            if ((!$j("#91f").is(':checked')||($j("input#qnty12").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other12").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#91ma").is(':checked'))&&(!$j("#91mb").is(':checked'))&&(!$j("#91mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#92").is(':checked'))){
            if ((!$j("#92f").is(':checked')||($j("input#qnty12").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other12").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#92ma").is(':checked'))&&(!$j("#92mb").is(':checked'))&&(!$j("#92mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#93").is(':checked'))){
            if ((!$j("#93f").is(':checked')||($j("input#qnty12").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other12").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#93ma").is(':checked'))&&(!$j("#93mb").is(':checked'))&&(!$j("#93mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#94").is(':checked'))){
            if(($j("#94").is(':checked'))){
                if ((!$j("#94fb").is(':checked')||($j("input#qnty12").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other12").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#94ma").is(':checked'))&&(!$j("#94mb").is(':checked'))&&(!$j("#94mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#95").is(':checked'))){
            if ((!$j("#95").is(':checked')||($j("input#qnty12").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other12").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#95ma").is(':checked'))&&(!$j("#95mb").is(':checked'))&&(!$j("#95mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug13").is(':checked')) {
        if(($j("#111").is(':checked'))){
            if ((!$j("#111f").is(':checked')||($j("input#qnty13").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other13").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#111ma").is(':checked'))&&(!$j("#111mb").is(':checked'))&&(!$j("#111mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#112").is(':checked'))){
            if ((!$j("#112f").is(':checked')||($j("input#qnty13").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other13").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#112ma").is(':checked'))&&(!$j("#112mb").is(':checked'))&&(!$j("#112mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#113").is(':checked'))){
            if($j("#113fa").is(':checked')&& !$j("#113fb").is(':checked')){
                if ((!$j("#113fa").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#113ma1").is(':checked'))&&(!$j("#113mb1").is(':checked'))&&(!$j("#113mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#113fa").is(':checked')&& $j("#113fb").is(':checked')){
                if ((!$j("#113fb").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#113ma2").is(':checked'))&&(!$j("#113mb2").is(':checked'))&&(!$j("#113mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#114").is(':checked'))){
            if($j("#114fa").is(':checked')&& !$j("#114fb").is(':checked')){
                if ((!$j("#114fa").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#114ma1").is(':checked'))&&(!$j("#114mb1").is(':checked'))&&(!$j("#114mc1").is(':checked'))))
                {
                    num=0;
                    return num;

                }
                else
                    num=1;
            }
            if(!$j("#114fa").is(':checked')&& $j("#114fb").is(':checked')){
                if ((!$j("#114fb").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#114ma2").is(':checked'))&&(!$j("#114mb2").is(':checked'))&&(!$j("#114mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#115").is(':checked'))){
            if ((!$j("#112f1").is(':checked')||($j("input#qnty13").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other13").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#112ma1").is(':checked'))&&(!$j("#112mb1").is(':checked'))&&(!$j("#112mb1").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }


        if(($j("#116").is(':checked'))){
            if((($j("#113fa1").is(':checked'))&&($j("#113fb1").is(':checked')))&& !$j("#113fb2").is(':checked')){
                if (!(($j("#113fa1").is(':checked'))&&($j("#113fb1").is(':checked')))||(($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#113ma11").is(':checked'))&&(!$j("#113mb11").is(':checked'))&&(!$j("#113mc11").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#113ma21").is(':checked'))&&(!$j("#113mb21").is(':checked'))&&(!$j("#113mc21").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!(($j("#113fa1").is(':checked'))&&($j("#113fb1").is(':checked')))&& $j("#113fb2").is(':checked')){

                if (!$j("#113fb2").is(':checked')||(($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#113ma22").is(':checked'))&&(!$j("#113ma22").is(':checked'))&&(!$j("#113ma22").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        } if(($j("#117").is(':checked'))){
            if($j("#114fa1").is(':checked')&& !$j("#114fb1").is(':checked')){
                if ((!$j("#114fa1").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#114ma11").is(':checked'))&&(!$j("#114mb11").is(':checked'))&&(!$j("#114mc11").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#114fa1").is(':checked')&& $j("#114fb1").is(':checked')){
                if ((!$j("#114fb1").is(':checked')||($j("input#qnty13").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other13").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#114ma21").is(':checked'))&&(!$j("#114mb21").is(':checked'))&&(!$j("#114mc21").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
    }
    if ($j("#drug14").is(':checked')) {
        if(($j("#120").is(':checked'))){
            if ((!$j("#120f").is(':checked')||($j("input#qnty14").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other14").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#120ma").is(':checked'))&&(!$j("#120mb").is(':checked'))&&(!$j("#120mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;

        if(($j("#121").is(':checked'))){
            if ((!$j("#121f").is(':checked')||(!$j("#121d").is(':checked'))||($j("input#qnty14").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other14").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#121ma").is(':checked'))&&(!$j("#121mb").is(':checked'))&&(!$j("#121mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#122").is(':checked'))){
            if ((!$j("#122f").is(':checked')||($j("input#qnty14").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other14").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#122ma").is(':checked'))&&(!$j("#122mb").is(':checked'))&&(!$j("#122mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#123").is(':checked'))){
            if($j("#123fa").is(':checked')&& !$j("#123fb").is(':checked')){
                if (((!$j("#123fa").is(':checked'))||($j("input#qnty14").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#123ma1").is(':checked'))&&(!$j("#123mb1").is(':checked'))&&(!$j("#123mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#123fa").is(':checked')&& $j("#123fb").is(':checked')){
                if ((!$j("#123fb").is(':checked'))||(($j("input#qnty14").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#123ma2").is(':checked'))&&(!$j("#123mb2").is(':checked'))&&(!$j("#123mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#124").is(':checked'))){
            if($j("#124fa").is(':checked')&& !$j("#124fb").is(':checked')){
                if (((!$j("#124fa").is(':checked'))||($j("input#qnty14").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#124ma1").is(':checked'))&&(!$j("#124mb1").is(':checked'))&&(!$j("#124mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#124fa").is(':checked')&& $j("#124fb").is(':checked')){
                if ((!$j("#124fb").is(':checked'))||(($j("input#qnty14").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#124ma2").is(':checked'))&&(!$j("#124mb2").is(':checked'))&&(!$j("#124mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#125").is(':checked'))){
            if($j("#125fa").is(':checked')&& !$j("#125fb").is(':checked')){
                if (((!$j("#125fa").is(':checked'))||($j("input#qnty14").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#125ma1").is(':checked'))&&(!$j("#125mb1").is(':checked'))&&(!$j("#125mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#125fa").is(':checked')&& $j("#125fb").is(':checked')){
                if ((!$j("#125fb").is(':checked'))||(($j("input#qnty14").val()==""))){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other14").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#125ma2").is(':checked'))&&(!$j("#125mb2").is(':checked'))&&(!$j("#125mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#126").is(':checked'))){
            if ((!$j("#126f").is(':checked')||($j("input#qnty14").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other14").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#126ma").is(':checked'))&&(!$j("#126mb").is(':checked'))&&(!$j("#126mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    } if ($j("#drug15").is(':checked')) {
        if(($j("#136").is(':checked')))
            if ((!$j("#136f").is(':checked')||($j("input#qnty15").val()==""))){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other15").val() !="") {
            num=1;
            return num;
        }

        if (((!$j("#136ma").is(':checked'))&&(!$j("#136mb").is(':checked'))&&(!$j("#136mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
    }
    if ($j("#drug16").is(':checked')) {
        if(($j("#146").is(':checked'))){
            if($j("input#Other16").val() !="") {
                num=1;
                return num;
            }
            if ((!$j("#146f").is(':checked')||($j("input#qnty16").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
        if (((!$j("#146ma").is(':checked'))&&(!$j("#146mb").is(':checked'))&&(!$j("#146mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
        }

    }

    if ($j("#drug17").is(':checked')) {
        if(($j("#156").is(':checked')))
            if ((!$j("#156f").is(':checked')||($j("input#qnty17").val()==""))){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other17").val() !="") {
            num=1;
            return num;
        }
        if (((!$j("#156ma").is(':checked'))&&(!$j("#156mb").is(':checked'))&&(!$j("#156mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
    }
    if ($j("#drug18").is(':checked')) {
        if(($j("#166").is(':checked')))
            if ((!$j("#166f").is(':checked')||($j("input#qnty18").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other18").val() !="") {
            num=1;
            return num;
        }
        if (((!$j("#166ma").is(':checked'))&&(!$j("#166mb").is(':checked'))&&(!$j("#166mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;

    }
    if ($j("#drug19").is(':checked')) {
        if(($j("#176").is(':checked')))
            if ((!$j("#176f").is(':checked')||($j("input#qnty19").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other19").val() !="") {
            num=1;
            return num;
        }
        if (((!$j("#176ma").is(':checked'))&&(!$j("#176mb").is(':checked'))&&(!$j("#176mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
    }
    if ($j("#drug20").is(':checked')) {
        if(($j("#186").is(':checked')))
            if ((!$j("#186f").is(':checked')||($j("input#qnty20").val()==""))){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other20").val() !="") {
            num=1;
            return num;
        }
        if (((!$j("#186ma").is(':checked'))&&(!$j("#186mb").is(':checked'))&&(!$j("#186mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
    }

    if ($j("#drug21").is(':checked')) {
        if(($j("#196").is(':checked')))
            if ((!$j("#196f").is(':checked')||($j("input#qnty21").val()==""))){
                num=0;
                return num;
            }
            else
                num=1;
        if($j("input#Other21").val() !="") {
            num=1;
            return num;
        }
        if (((!$j("#196ma").is(':checked'))&&(!$j("#196mb").is(':checked'))&&(!$j("#196mc").is(':checked'))))
        {
            num=0;
            return num;
        }
        else
            num=1;
    }

    if ($j("#drug22").is(':checked')) {
        if(($j("#201").is(':checked'))){
            if($j("#201fa").is(':checked')&& !$j("#201fb").is(':checked')){
                if ((!$j("#201fa").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#201ma1").is(':checked'))&&(!$j("#201ma2").is(':checked'))&&(!$j("#201mb1").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#201fa").is(':checked')&& $j("#201fb").is(':checked')){
                if ((!$j("#201fb").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#201mb2").is(':checked'))&&(!$j("#201mc1").is(':checked'))&&(!$j("#201mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        else
            num=0;
        if(($j("#202").is(':checked'))){
            if($j("#202fa").is(':checked')&& !$j("#202fb").is(':checked')){
                if ((!$j("#202fa").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#202ma1").is(':checked'))&&(!$j("#202mb1").is(':checked'))&&(!$j("#202mc1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
            if(!$j("#202fa").is(':checked')&& $j("#202fb").is(':checked')){
                if ((!$j("#202fb").is(':checked')||($j("input#qnty7").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#202ma2").is(':checked'))&&(!$j("#202mb2").is(':checked'))&&(!$j("#202mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#203").is(':checked'))){
            if($j("#203fa").is(':checked')&& !$j("#203fb").is(':checked')){
                if ((!$j("#203fa").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#203ma1").is(':checked'))&&(!$j("#203ma2").is(':checked'))&&(!$j("#203mb1").is(':checked'))))
                {
                    num=0;
                    return num;

                }
                else
                    num=1;
            }
            if(!$j("#203fa").is(':checked')&& $j("#203fb").is(':checked')){
                if ((!$j("#203fb").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#203mb2").is(':checked'))&&(!$j("#203mc1").is(':checked'))&&(!$j("#203mc2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#204").is(':checked'))){
            if($j("#204fa").is(':checked')&& !$j("#204fb").is(':checked')){
                if ((!$j("#204fa").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#204ma1").is(':checked'))&&(!$j("#204ma2").is(':checked'))&&(!$j("#204mc1").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;

            }
            if(!$j("#204fa").is(':checked')&& $j("#204fb").is(':checked')){
                if ((!$j("#204fb").is(':checked')||($j("input#qnty22").val()=="")   )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#204mb1").is(':checked'))&&(!$j("#204mb2").is(':checked'))&&(!$j("#204mc2").is(':checked'))))

                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
        if(($j("#205").is(':checked'))){
            if(($j("#205").is(':checked'))){
                if ((!$j("#205f").is(':checked')||($j("input#qnt22").val()=="")    )){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other22").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#205ma").is(':checked'))&&(!$j("#205mb").is(':checked'))&&(!$j("#205mc").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
    }
    if ($j("#drug23").is(':checked')) {
        if(($j("#211").is(':checked'))){
            if ((!$j("#211f").is(':checked')||($j("input#qnty23").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other23").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#211ma").is(':checked'))&&(!$j("#211mb").is(':checked'))&&(!$j("#211mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else
            num=0;
        if(($j("#212").is(':checked'))){
            if ((!$j("#212f").is(':checked')||($j("input#qnty23").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other23").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#212ma").is(':checked'))&&(!$j("#212mb").is(':checked'))&&(!$j("#212mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        if(($j("#213").is(':checked'))){
            if ((!$j("#213f").is(':checked')||($j("input#qnty23").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other23").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#213ma").is(':checked'))&&(!$j("#213mb").is(':checked'))&&(!$j("#213mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
    }
    if ($j("#drug24").is(':checked')) {
        if(($j("#221").is(':checked'))){
            if ((!$j("#221f").is(':checked')||($j("input#qnty24").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other24").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#221ma").is(':checked'))&&(!$j("#221mb").is(':checked'))&&(!$j("#221mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else if(($j("#222").is(':checked'))){
            if ((!$j("#222f").is(':checked')||($j("input#qnty24").val()=="")    )){
                num=0;
                return num;
            }
            else
                num=1;
            if($j("input#Other24").val() !="") {
                num=1;
                return num;
            }
            if (((!$j("#222ma").is(':checked'))&&(!$j("#222mb").is(':checked'))&&(!$j("#222mc").is(':checked'))))
            {
                num=0;
                return num;
            }
            else
                num=1;
        }
        else if(($j("#223").is(':checked'))){
            if($j("#223fa").is(':checked')&& !$j("#223fb").is(':checked')){
                if (($j("input#qnty24").val()=="")){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other24").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#223ma1").is(':checked'))&&(!$j("#223mb1").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
           else if(!$j("#223fa").is(':checked')&& $j("#234fb").is(':checked')){
                if (($j("input#qnty24").val()=="")){
                    num=0;
                    return num;
                }
                else
                    num=1;
                if($j("input#Other24`").val() !="") {
                    num=1;
                    return num;
                }
                if (((!$j("#223ma2").is(':checked'))&&(!$j("#223mb2").is(':checked'))))
                {
                    num=0;
                    return num;
                }
                else
                    num=1;
            }
        }
    }
    if(num==9)
        num=2;
    return num;
}
var check1=0;
var check2=0;
var check3=0;
var check4=0;
var numbers = [
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
         numbersCounter < numbers.length ;
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
        currNumber = numbers[numbersCounter] ; // curr array
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
         numbersCounter < numbers.length ;
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
        currNumber = numbers[numbersCounter] ; // curr array
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
