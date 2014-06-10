
$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j('#regimenchange1').live("click",function(){
    $j('#regimenc').empty();
    $j('<textarea rows="5" cols="15"> </textarea>').appendTo('#regimenc');
})

$j("#nextvisit").datepicker();

var pRegimen;

var cRegimen;

var id = jQuery.Pid.id;
var formDose;

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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
$j('#drug7').live('click',function(){
    if($j('#drug7').attr('checked')==true){
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
}) ;
$j('#drug13').live('click',function(){
    if($j('#drug12').attr('checked')==true){
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
$j('#drug15').blur( function(){
    if($j('#drug12').attr('checked')==true){
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
}) ;
$j('#drug16').blur( function(){
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
}) ;
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
$j.getJSON("dispense.form?encounter=enc&Pen=" + id+"&formtype="+formType, function(result) {
    if(jQuery.isEmptyObject(result)){
        $j("#infodiv").append("Not given");
        //document.getElementById('pregimen').style.color = 'blue';
        $j("#infodiv").css("color","blue");
    }
    else{
        $j.each(result, function(key, val) {
            var strv= val.toString();
            var numbersArray = strv.split(",");
            pRegimen="";
            for (var i=0;i<numbersArray.length;i++)
            {
                i= i+1;
                if(numbersArray[i]!=null)
                pRegimen+=numbersArray[i]+",";
            }
            $j("#infodiv").html(strv);
            $j("#infodiv").css("color","blue");
        });
    }
});
function checkHivForm(val){
    cRegimen=val;
    if(cRegimen!=pRegimen)
    {
        if($j("#regimenchange").is(':checked') || $j("#arvtype3").is(':checked') || $j("#arvtype4").is(':checked'))  {
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

function validateHivForm(){
    var value;
    message="";
    //drug1 row1s row1m  row1d
    var dose,dispense,otherd,other;
    var regimenChangeChecked;
    for(var t=1;t<=19;t++){
        if(t>=20){
            if ($j("#drug"+t+"").val()!="") {
                neednoregimen=1;
            }
        }
        else{
            if ($j("#drug"+t+"").is(':checked'))
            {
                dose= parseInt($j("input:radio[name=Month"+t+"]:checked").val());
                dispense=  parseInt( $j("input:radio[name=Dispensed"+t+"]:checked").val());
                otherd=parseInt( $j("#other"+t+"d").val());
                other=parseInt( $j("#row"+t+"o").val());

                if((dose < dispense)){
                    message+="Dispensed drugs cannot exceed monthly supply  "+"<br/>";

                }

                else if (($j("input:radio[name=Month"+t+"]:checked").length > 0 || $j("#row"+t+"0").val() !="") && ($j("input:radio[name=Dispensed"+t+"]:checked").length > 0 || $j("#other"+t+"d").val() !="")){

                    value=1;
                }
                else{
                    value=0;
                }

            }
        }



    }


    if (!$j("#patienttype1").is(':checked')&&!$j("#patienttype2").is(':checked')&&!$j("#patienttype3").is(':checked')&&!$j("#patienttype4").is(':checked'))
    {
        message+="Atleast one patient type must be selected  "+"<br/>";
    }

    if ((!$j("#arvtype1").is(':checked'))&&(!$j("#arvtype2").is(':checked'))&&(!$j("#arvtype3").is(':checked'))&&(!$j("#arvtype4").is(':checked')) && ($j("#regimenchange").attr('checked')==false))
    {
        message+="Atleast one ARV type must be selected or choose regimen change"+"<br/>";
    }

    if ($j("#regimenchange").is(':checked')) {
        if (($j("#arvtype1").is(':checked')) || ($j("#arvtype2").is(':checked')) || ($j("#arvtype3").is(':checked')) || ($j("#arvtype4").is(':checked')))
        {
            message+="If it's regimen change you cannot choose ARV type(s) "+"<br/>";
        }
        if ((!$j("#regimenchange1").is(':checked'))&&(!$j("#regimenchange2").is(':checked'))&&(!$j("#regimenchange3").is(':checked')))
        {
            message+="Atleast one regimen change reason must be selected "+"<br/>";
        }
    }
    if ($j("#arvtype4").is(':checked') && (!$j("#arvtype1").is(':checked')) && (!$j("#arvtype2").is(':checked')) && (!$j("#arvtype3").is(':checked'))) {
        message+="Existing regimen option must be filled with either ARV refill or OI refill or both"+"<br/>";
    }
    if($j("#arvtype3").is(':checked') && $j("#arvtype4").is(':checked')){
        message+="Cannot dispense ARV initiation with Existing regimen at the same time "+"<br/>";
    }
    if($j("#arvtype1").is(':checked') && $j("#arvtype2").is(':checked') && $j("#arvtype3").is(':checked')){
        message+="Cannot dispense ARV refill,OI refill and ARV initiation at the same time "+"<br/>";
    }
    if($j("#arvtype1").is(':checked') && $j("#arvtype2").is(':checked') && $j("#arvtype3").is(':checked') && $j("#arvtype4").is(':checked')){
        message+="Cannot dispense ARV refill,OI refill,ARV initiation and Existing regimen at the same time "+"<br/>";
    }
    if ($j("#datePicker").val()=="") {

        message+="Give Encounter date "+"<br/>";
    }

    if ($j("#nextvisit").val()=="") {
        message+="No Next visit date "+"<br/>";

    }

    return value;

}

var data=["1 tablets|1888","2 tablets|1888","3 tablets|1888"];


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

$j("input[name=ObsDrug1*1900#10]").live("focus", function () {


    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },

        source:data,
        minLength:1,
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
    [1400]
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
    splicedRegimen=val.slice(0);
    var septrinPosition=regimen.indexOf('916');
    var dapsonePosition=regimen.indexOf('92');
    var isoniazidPosition=regimen.indexOf('656');
    var positionOfEquity = -1 ;
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
            regimenCode='AF1A';
        }
    }
    else if(positionOfEquity < 5) {
      regimenName='TDF/3TC/EFV';
      regimenCode='AF2B';
    }
    else if(positionOfEquity < 7) {
        regimenName='d4T/3TC/NVP';
        regimenCode='AF3A';
    }
    else if(positionOfEquity ==7) {
        if($j("#patienttype1").is(':checked')) {
        regimenName='AZT/3TC/EFV';
        regimenCode='PM4';
        }
        else{
            regimenName='AZT/3TC/EFV';
            regimenCode='AF1B';
        }
    }
    else if(positionOfEquity ==8) {
        regimenName='AZT/3TC/ABC';
        regimenCode='AF1C';
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
        regimenCode='AS1B';
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
          regimenCode='PA2B';
      }
        else{
          regimenName='d4T/3TC/LPV/r';
          regimenCode='AS4A';
      }

    }
    else if(positionOfEquity==18 || positionOfEquity==19){
        regimenName='AZT/3TC';
        regimenCode='PA1A';
    }
    else if (positionOfEquity==20 || positionOfEquity==21){
        regimenName='D4T/3TC';
        regimenCode='PA2A';
    }
    else if(positionOfEquity==22 || positionOfEquity==23){
        regimenName='TDF/3TC';
        regimenCode='PA3A'
    }
    return [regimenCode,regimenName];
 }
