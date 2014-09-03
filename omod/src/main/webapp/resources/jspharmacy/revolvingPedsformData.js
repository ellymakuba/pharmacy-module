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
}) ;
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
    if($j('#drug1').attr('checked')==true){
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
$j('#drug6').live('click',function(){
    if($j('#drug6').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug6"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug6"){
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
                document.getElementById("quantityInStore6").value=result;
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
$j('#drug7').live('click',function(){
    if($j('#drug2').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug7"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug7"){
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
                document.getElementById("quantityInStore7").value=result;
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
$j('#drug8').live('click',function(){
    if($j('#drug8').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug8"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug8"){
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
                document.getElementById("quantityInStore8").value=result;
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
$j('#drug9').live('click',function(){
    if($j('#drug9').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug9"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug9"){
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
                document.getElementById("quantityInStore9").value=result;
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
$j('#drug10').live('click',function(){
    if($j('#drug10').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug10"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug10"){
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
                document.getElementById("quantityInStore10").value=result;
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
$j('#drug11').live('click',function(){
    if($j('#drug11').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug11"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug11"){
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
                document.getElementById("quantityInStore11").value=result;
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
$j('#drug12').live('click',function(){
    if($j('#drug12').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug12"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug12"){
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
                document.getElementById("quantityInStore12").value=result;
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
$j('#drug13').live('click',function(){
    if($j('#drug13').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug13"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug13"){
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
                document.getElementById("quantityInStore13").value=result;
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
$j('#drug14').live('click',function(){
    if($j('#drug14').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug14"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug14"){
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
                document.getElementById("quantityInStore14").value=result;
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
$j('#drug15').live('click',function(){
    if($j('#drug15').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug15"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug15"){
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
                document.getElementById("quantityInStore15").value=result;
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
$j('#drug16').live('click',function(){
    if($j('#drug16').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug16"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug16"){
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
                document.getElementById("quantityInStore16").value=result;
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
$j('#drug17').live('click',function(){
    if($j('#drug17').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug17"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug17"){
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
                document.getElementById("quantityInStore17").value=result;
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
$j('#drug18').live('click',function(){
    if($j('#drug18').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug18"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug18"){
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
                document.getElementById("quantityInStore18").value=result;
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
$j('#drug19').live('click',function(){
    if($j('#drug19').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug19"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug19"){
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
                document.getElementById("quantityInStore19").value=result;
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
$j('#drug20').live('click',function(){
    if($j('#drug20').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug20"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug20"){
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
                document.getElementById("quantityInStore20").value=result;
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
$j('#drug21').live('click',function(){
    if($j('#drug21').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug21"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug21"){
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
                document.getElementById("quantityInStore21").value=result;
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
$j('#drug22').live('click',function(){
    if($j('#drug22').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug22"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug22"){
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
                document.getElementById("quantityInStore22").value=result;
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
$j('#drug23').live('click',function(){
    if($j('#drug23').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug23"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug23"){
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
                document.getElementById("quantityInStore23").value=result;
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
$j('.amountWaived').blur(function () {
    var sumwaived = 0;
    $j('.amountWaived').each(function() {
        sumwaived += Number($j(this).val());
    });
    document.getElementById("totalwaived").value=Number(sumwaived);
});
$j('.amountPaid').blur(function () {
    var sumpaid = 0;
    $j('.amountPaid').each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("totalamount").value=Number(sumpaid);
});
$j("input[title='gnty1']").blur( function () {
    document.getElementById("expectedamount1").value  =parseInt($j("input[title='gnty1']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty2']").blur( function () {
    document.getElementById("expectedamount2").value  =parseInt($j("input[title='gnty2']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty3']").blur( function () {
    document.getElementById("expectedamount3").value  =parseInt($j("input[title='gnty3']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty4']").blur( function () {
    document.getElementById("expectedamount4").value  =parseInt($j("input[title='gnty4']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty5']").blur( function () {
    document.getElementById("expectedamount5").value  =parseInt($j("input[title='gnty5']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty6']").blur( function () {
    document.getElementById("expectedamount6").value  =parseInt($j("input[title='gnty6']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty7']").blur( function () {
    document.getElementById("expectedamount7").value  =parseInt($j("input[title='gnty7']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty8']").blur( function () {
    document.getElementById("expectedamount8").value  =parseInt($j("input[title='gnty8']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty9']").blur( function () {
    document.getElementById("expectedamount9").value  =parseInt($j("input[title='gnty9']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty10']").blur( function () {
    document.getElementById("expectedamount10").value  =parseInt($j("input[title='gnty10']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty11']").blur( function () {
    document.getElementById("expectedamount11").value  =parseInt($j("input[title='gnty11']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty12']").blur( function () {
    document.getElementById("expectedamount12").value  =parseInt($j("input[title='gnty12']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty13']").blur( function () {
    document.getElementById("expectedamount13").value  =parseInt($j("input[title='gnty13']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty14']").blur( function () {
    document.getElementById("expectedamount14").value  =parseInt($j("input[title='gnty14']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty15']").blur( function () {
    document.getElementById("expectedamount15").value  =parseInt($j("input[title='gnty15']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty16']").blur( function () {
    document.getElementById("expectedamount16").value  =parseInt($j("input[title='gnty16']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty17']").blur( function () {
    document.getElementById("expectedamount17").value  =parseInt($j("input[title='gnty17']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty18']").blur( function () {
    document.getElementById("expectedamount18").value  =parseInt($j("input[title='gnty18']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty19']").blur( function () {
    document.getElementById("expectedamount19").value  =parseInt($j("input[title='gnty19']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty19']").blur( function () {
    document.getElementById("expectedamount19").value  =parseInt($j("input[title='gnty19']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty20']").blur( function () {
    document.getElementById("expectedamount20").value  =parseInt($j("input[title='gnty20']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty21']").blur( function () {
    document.getElementById("expectedamount21").value  =parseInt($j("input[title='gnty21']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty22']").blur( function () {
    document.getElementById("expectedamount22").value  =parseInt($j("input[title='gnty22']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty23']").blur( function () {
    document.getElementById("expectedamount23").value  =parseInt($j("input[title='gnty23']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty24']").blur( function () {
    document.getElementById("expectedamount24").value  =parseInt($j("input[title='gnty24']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
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
$j.getJSON("dispense.form?encounter=enc&Pen=" + id, function(result) {
    if(jQuery.isEmptyObject(result)){
        $j("#infodiv").append("Not given");
        //document.getElementById('pregimen').style.color = 'blue';
        $j("#infodiv").css("color","blue");
    }
    else{
        $j.each(result, function(key, val) {

            var strv= val.toString();

            var numbersArray = strv.split(",");

//            var value=numbersArray[0]+"/"+numbersArray[1]+"/"+numbersArray[2]+"/"+numbersArray[3]+"/"+numbersArray[4]+"/"+numbersArray[5]+"/"+numbersArray[6]+"|"+numbersArray[7];
            pRegimen="";

            for (var i=0;i<numbersArray.length;i++)
            {

                if(numbersArray[i+1]!=null)
                    pRegimen+=numbersArray[i+1]+",";
            }
            $j("#infodiv").html(strv);

            $j("#infodiv").css("color","blue");
        });
    }
});
function validateRevolvingPedsForm(){
    var value;
    message="";
    var oneChecked=0;
    if ($j("#rno").val()=="") {
        value=0;
        message+="Receipt no missing";
        return value;
    }
    for(var t=1;t<=23;t++){
        if ($j("#drug"+t+"").is(':checked')) {
            oneChecked=1;
            if ($j("#qnty"+t+"").val()=="") {
                value=0;
                return value;
            }
            else
                value=1;
            continue;
        }
    }
    if (oneChecked==0) {
        message+="Atleast one drug must be selected "+"<br/>";
    }
    if ($j("#Weight").val()=="") {
        value=0;
    }
    else
        value=1;
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
$j("input[name=ObsDrug*MEDICATION ADDED|1895#10]").live("focus", function () {
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
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });

});
