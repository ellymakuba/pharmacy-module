
$j("#Pending").dialog({ position:[350, 200]
});

$j("#Requests").dialog({ position:[850, 200]
});


$j("#Approved").dialog({ position:[350, 400]
});

$j("#Approved").dialog('close');
$j("#Requests").dialog('close');
$j("#Pending").dialog('close');

$j("#spinner").hide();

$j.getJSON("locationSetter.form", function (result) {
    if (result == "none") {
        $j("#errorDiv").show();
        $j("#errorDiv").delay(5000).hide("slow");
        $j("#spinner").delay(5000).hide("slow");
    }
    else {
        $j("#spinner").hide();
$j.ajax({
    type:"POST",
    url:"home.form?requests=requests",
    success:function (data) {

        $j("#Requests").empty();

        $j(data).appendTo('#Requests');


        $j("#Requests").dialog("open");
    }
});

$j.ajax({
    type:"POST",
    url:"home.form?pending=pending",
    success:function (data) {
        $j("#Pending").empty();

        $j(data).appendTo('#Pending');


        $j("#Pending").dialog("open");

    }
});

$j.ajax({
    type:"POST",
    url:"home.form?approved=approved",
    success:function (data) {

        $j("#Approved").empty();

        $j(data).appendTo('#Approved');


        $j("#Approved").dialog("open");
    }
});
    }
});

