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
$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
$j('.amountWaived').blur(function () {
    var sumwaived = 0;
    $j('.amountWaived').each(function() {
        sumwaived += Number($j(this).val());
    });
    document.getElementById("totalwaived").value=Number(sumwaived);
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
}) ;
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
});
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
});
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
});
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
});
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
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
});
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
});
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
});
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
});
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
});
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
                            alert("Ensure batch and quantity are set for this drug");
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
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
                            alert("Ensure batch and quantity are set for this drug");
                        }
                    }
                })
            }
        })
    }
});
$j('#drug24').live('click',function(){
    if($j('#drug24').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug24"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug24"){
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
                document.getElementById("quantityInStore24").value=result;
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
$j('#drug25').live('click',function(){
    if($j('#drug25').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug25"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug25"){
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
                document.getElementById("quantityInStore25").value=result;
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
$j('#drug26').live('click',function(){
    if($j('#drug26').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug26"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug26"){
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
                document.getElementById("quantityInStore26").value=result;
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
$j('#drug27').live('click',function(){
    if($j('#drug27').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug27"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug27"){
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
                document.getElementById("quantityInStore27").value=result;
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
$j('#drug28').live('click',function(){
    if($j('#drug28').attr('checked')==true){
        var values = $j("input[title='drug']").map(
            function () {
                if(this.id=="drug28"){
                    return $j(this).val().substring($j(this).val().indexOf('|')+1);
                }

            }).get();
        var values2 =$j("input[title='drug']").map(
            function () {
                if(this.id=="drug28"){
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
                document.getElementById("quantityInStore28").value=result;
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
$j('.amountPaid').blur(function () {
    var sumpaid = 0;
    $j('.amountPaid').each(function() {
        sumpaid += Number($j(this).val());
    });
    document.getElementById("totalamount").value=Number(sumpaid);
});
$j("#address, #city, #country").bind("change", function() {
    //do something
});
$j("input[title='dailydose1'],input[title='days1']").change( function () {
    document.getElementById("qnty1").value  =parseInt($j("input[title='dailydose1']").val()) * parseInt($j("input[title='days1']").val())*3;
    document.getElementById("expectedamount1").value  =parseInt($j("input[title='gnty1']").val())* 2;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose2'],input[title='days2']").change( function () {
    var sum = 0;
    document.getElementById("qnty2").value  =parseInt($j("input[title='days2']").val() * $j("input[title='dailydose2']").val())*3;
    document.getElementById("expectedamount2").value  =parseInt($j("input[title='gnty2']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose3'],input[title='days3']").change( function () {
    var sum = 0;
    document.getElementById("qnty3").value  =parseInt($j("input[title='days3']").val() * $j("input[title='dailydose3']").val())*3;
    document.getElementById("expectedamount3").value  =parseInt($j("input[title='gnty3']").val())* 4;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose4'],input[title='days4']").change( function () {
    var sum = 0;
    document.getElementById("qnty4").value  =parseInt($j("input[title='days4']").val() * $j("input[title='dailydose4']").val())*3;
    document.getElementById("expectedamount4").value  =parseInt($j("input[title='gnty4']").val())* 4;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose5'],input[title='days5']").change( function () {
    var sum = 0;
    document.getElementById("qnty5").value  =parseInt($j("input[title='days5']").val() * $j("input[title='dailydose5']").val())*3;
    document.getElementById("expectedamount5").value  =parseInt($j("input[title='gnty5']").val())* 8;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose6'],input[title='days6']").change( function () {
    var sum = 0;
    document.getElementById("qnty6").value  =parseInt($j("input[title='days6']").val() * $j("input[title='dailydose6']").val())*2;
    document.getElementById("expectedamount6").value  =parseInt($j("input[title='gnty6']").val())* 6;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose7'],input[title='days7']").change( function () {
    var sum = 0;
    document.getElementById("qnty7").value  =parseInt($j("input[title='days7']").val() * $j("input[title='dailydose7']").val())*2;
    document.getElementById("expectedamount7").value  =parseInt($j("input[title='gnty7']").val())* 6;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose8'],input[title='days8']").change( function () {
    var sum = 0;
    document.getElementById("qnty8").value  =parseInt($j("input[title='days8']").val() * $j("input[title='dailydose8']").val())*2;
    document.getElementById("expectedamount8").value  =parseInt($j("input[title='gnty8']").val())* 4;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose9'],input[title='days9']").change( function () {
    var sum = 0;
    document.getElementById("qnty9").value  =parseInt($j("input[title='days9']").val() * $j("input[title='dailydose9']").val())*2;
    document.getElementById("expectedamount9").value  =parseInt($j("input[title='gnty9']").val())* 4;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose10'],input[title='days10']").change( function () {
    var sum = 0;
    document.getElementById("qnty10").value  =parseInt($j("input[title='days10']").val() * $j("input[title='dailydose10']").val())*2;
    document.getElementById("expectedamount10").value  =parseInt($j("input[title='gnty10']").val())* 4;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose11'],input[title='days11']").change( function () {
    var sum = 0;
    document.getElementById("qnty11").value  =parseInt($j("input[title='days11']").val() * $j("input[title='dailydose11']").val())*2;
    document.getElementById("expectedamount11").value  =parseInt($j("input[title='gnty11']").val())* 20;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose12'],input[title='days12']").change( function () {
    var sum = 0;
    document.getElementById("qnty12").value  =parseInt($j("input[title='days12']").val() * $j("input[title='dailydose12']").val())*2;
    document.getElementById("expectedamount12").value  =parseInt($j("input[title='gnty12']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose13'],input[title='days13']").change( function () {
    var sum = 0;
    document.getElementById("qnty13").value  =parseInt($j("input[title='days13']").val() * $j("input[title='dailydose13']").val())*2;
    document.getElementById("expectedamount13").value  =parseInt($j("input[title='gnty13']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose14'],input[title='days14']").change( function () {
    var sum = 0;
    document.getElementById("qnty14").value  =parseInt($j("input[title='days14']").val() * $j("input[title='dailydose14']").val());
    document.getElementById("expectedamount14").value  =parseInt($j("input[title='gnty14']").val())* 20;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose15'],input[title='days15']").change( function () {
    var sum = 0;
    document.getElementById("qnty15").value  =parseInt($j("input[title='days15']").val() * $j("input[title='dailydose15']").val())*3;
    document.getElementById("expectedamount15").value  =parseInt($j("input[title='gnty15']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose16'],input[title='days16']").change( function () {
    var sum = 0;
    document.getElementById("qnty16").value  =parseInt($j("input[title='days16']").val() * $j("input[title='dailydose16']").val());
    document.getElementById("expectedamount16").value  =parseInt($j("input[title='gnty16']").val())* 30;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose17'],input[title='days17']").change( function () {
    var sum = 0;
    document.getElementById("qnty17").value  =parseInt($j("input[title='days17']").val() * $j("input[title='dailydose17']").val());
    document.getElementById("expectedamount17").value  =parseInt($j("input[title='gnty17']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose18'],input[title='days18']").change( function () {
    var sum = 0;
    document.getElementById("qnty18").value  =parseInt($j("input[title='days18']").val() * $j("input[title='dailydose18']").val());
    document.getElementById("expectedamount18").value  =parseInt($j("input[title='gnty18']").val())* 25;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose19'],input[title='days19']").change( function () {
    var sum = 0;
    document.getElementById("qnty19").value  =parseInt($j("input[title='days19']").val() * $j("input[title='dailydose19']").val())*2;
    document.getElementById("expectedamount19").value  =parseInt($j("input[title='gnty19']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose20'],input[title='days20']").change( function () {
    var sum = 0;
    document.getElementById("qnty20").value  =parseInt($j("input[title='days20']").val() * $j("input[title='dailydose20']").val());
    document.getElementById("expectedamount20").value  =parseInt($j("input[title='gnty20']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose21'],input[title='days21']").change( function () {
    var sum = 0;
    document.getElementById("qnty21").value  =parseInt($j("input[title='days21']").val() * $j("input[title='dailydose21']").val());
    document.getElementById("expectedamount21").value  =parseInt($j("input[title='gnty21']").val())* 5;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose22'],input[title='days22']").change( function () {
    var sum = 0;
    document.getElementById("qnty22").value  =parseInt($j("input[title='days22']").val() * $j("input[title='dailydose22']").val());
    document.getElementById("expectedamount22").value  =parseInt($j("input[title='gnty22']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose23'],input[title='days23']").change( function () {
    var sum = 0;
    document.getElementById("qnty23").value  =parseInt($j("input[title='days23']").val() * $j("input[title='dailydose23']").val())*3;
    document.getElementById("expectedamount23").value  =parseInt($j("input[title='gnty23']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose24'],input[title='days24']").change( function () {
    var sum = 0;
    document.getElementById("qnty24").value  =parseInt($j("input[title='days17']").val() * $j("input[title='dailydose24']").val());
    document.getElementById("expectedamount24").value  =parseInt($j("input[title='gnty24']").val())* 3;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose25'],input[title='days25']").change( function () {
    var sum = 0;
    document.getElementById("qnty25").value  =parseInt($j("input[title='days25']").val() * $j("input[title='dailydose25']").val());
    document.getElementById("expectedamount25").value  =parseInt($j("input[title='gnty25']").val())* 3;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose26'],input[title='days26']").change( function () {
    var sum = 0;
    document.getElementById("qnty26").value  =parseInt($j("input[title='days26']").val() * $j("input[title='dailydose26']").val())*3;
    document.getElementById("expectedamount26").value  =parseInt($j("input[title='gnty26']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose27'],input[title='days27']").change( function () {
    var sum = 0;
    document.getElementById("qnty27").value  =parseInt($j("input[title='days27']").val() * $j("input[title='dailydose27']").val());
    document.getElementById("expectedamount27").value  =parseInt($j("input[title='gnty27']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose28'],input[title='days28']").change( function () {
    var sum = 0;
    document.getElementById("qnty28").value  =parseInt($j("input[title='days28']").val() * $j("input[title='dailydose28']").val());
    document.getElementById("expectedamount28").value  =parseInt($j("input[title='gnty28']").val())* 1;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose29'],input[title='days29']").change( function () {
    var sum = 0;
    document.getElementById("qnty29").value  =parseInt($j("input[title='days29']").val() * $j("input[title='dailydose29']").val());
    document.getElementById("expectedamount29").value  =parseInt($j("input[title='gnty29']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='dailydose30'],input[title='days30']").change( function () {
    var sum = 0;
    document.getElementById("qnty30").value  =parseInt($j("input[title='days30']").val() * $j("input[title='dailydose30']").val());
    //document.getElementById("expectedamount30").value  =parseInt($j("input[title='gnty30']").val())* 2;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});






$j("input[title='gnty']").focus( function () {

    document.getElementById("expectedamount1").value  =parseInt($j("input[title='gnty1']").val())* 2;

});
$j("input[title='gnty2']").change( function () {

    document.getElementById("expectedamount2").value  =parseInt($j("input[title='gnty2']").val())* 1;

});
$j("input[title='gnty3']").change( function () {

    document.getElementById("expectedamount3").value  =parseInt($j("input[title='gnty3']").val())* 4;

});
$j("input[title='gnty4']").change( function () {

    document.getElementById("expectedamount4").value  =parseInt($j("input[title='gnty4']").val())* 4;

});
$j("input[title='gnty5']").change( function () {

    document.getElementById("expectedamount5").value  =parseInt($j("input[title='gnty5']").val())* 8;

});
$j("input[title='gnty6']").change( function () {

    document.getElementById("expectedamount6").value  =parseInt($j("input[title='gnty6']").val())* 6;

});
$j("input[title='gnty7']").change( function () {

    document.getElementById("expectedamount7").value  =parseInt($j("input[title='gnty7']").val())* 6;

});
$j("input[title='gnty8']").change( function () {

    document.getElementById("expectedamount8").value  =parseInt($j("input[title='gnty8']").val())* 4;

});
$j("input[title='gnty9']").change( function () {

    document.getElementById("expectedamount9").value  =parseInt($j("input[title='gnty9']").val())* 4;

});
$j("input[title='gnty10']").change( function () {

    document.getElementById("expectedamount10").value  =parseInt($j("input[title='gnty10']").val())* 4;

});
$j("input[title='gnty11']").change( function () {

    document.getElementById("expectedamount11").value  =parseInt($j("input[title='gnty11']").val())* 20;

});
$j("input[title='gnty12']").change( function () {

    document.getElementById("expectedamount12").value  =parseInt($j("input[title='gnty12']").val())* 1;

});
$j("input[title='gnty13']").change( function () {

    document.getElementById("expectedamount13").value  =parseInt($j("input[title='gnty13']").val())* 2;

});
$j("input[title='gnty14']").change( function () {

    document.getElementById("expectedamount14").value  =parseInt($j("input[title='gnty14']").val())* 20;

});
$j("input[title='gnty15']").change( function () {

    document.getElementById("expectedamount15").value  =parseInt($j("input[title='gnty15']").val())* 1;

});
$j("input[title='gnty16']").change( function () {

    document.getElementById("expectedamount16").value  =parseInt($j("input[title='gnty16']").val())* 130;

});
$j("input[title='gnty17']").change( function () {

    document.getElementById("expectedamount17").value  =parseInt($j("input[title='gnty17']").val())* 1;

});
$j("input[title='gnty18']").change( function () {

    document.getElementById("expectedamount18").value  =parseInt($j("input[title='gnty18']").val())* 25;

});
$j('#drug19').live('click',function(){
    document.getElementById("expectedamount19").value  =parseInt(24)* 1;
});
$j('#drug20').live('click',function(){
    document.getElementById("expectedamount20").value  =parseInt(164)* 2;

});
$j("input[title='gnty21']").change( function () {

    document.getElementById("expectedamount21").value  =parseInt($j("input[title='gnty21']").val())* 5;

});
$j("input[title='gnty22']").change( function () {

    document.getElementById("expectedamount22").value  =parseInt($j("input[title='gnty22']").val())* 2;

});
$j("input[title='gnty23']").change( function () {

    document.getElementById("expectedamount23").value  =parseInt($j("input[title='gnty23']").val())* 2;

});
$j('#drug24').live('click',function(){

    document.getElementById("expectedamount24").value  =parseInt(6)* 3;

});
$j("input[title='gnty25']").change( function () {

    document.getElementById("expectedamount25").value  =parseInt($j("input[title='gnty25']").val())* 3;

});
$j("input[title='gnty26']").change( function () {

    document.getElementById("expectedamount26").value  =parseInt($j("input[title='gnty26']").val())* 1;

});
$j("input[title='gnty27']").change( function () {

    document.getElementById("expectedamount27").value  =parseInt($j("input[title='gnty27']").val())* 1;

});
$j("input[title='gnty28']").change( function () {

    document.getElementById("expectedamount28").value  =parseInt($j("input[title='gnty28']").val())* 1;

});
$j("input[title='gnty29']").change( function () {

    document.getElementById("expectedamount29").value  =parseInt($j("input[title='gnty29']").val())* 2;

});


$j("#nextvisit").datepicker();

var pRegimen;

var cRegimen;

var id = jQuery.Pid.id;


var formDose;

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





function validateRevolvingAdultForm(){


    message="";

    var value;
    var oneChecked=0;






    if ($j("#rno").val()=="") {

        value=0;

        message+="Receipt no missing";
        return value;
    }



    for(var t=1;t<=31;t++){
        if(t>=29){

            if ($j("#drug"+t+"").val()!="") {
                oneChecked=1;

                if (($j("input#days"+t+"").val()!="")&&($j("input#dose"+t+"").val()!="")&&($j("input#qnty"+t+"").val()!="")){

                    value=1;

                }
                else  {
                    value=0;

                }
                if ($j("#pack"+t+"a").val()=="") {

                    value=0;
                }
                else
                    value=1;
            }




        }
        else{


            if ($j("#drug"+t+"").is(':checked')) {
                oneChecked=1;

                if ($j("#pack"+t+"a").val()=="") {

                    value=0;
                    return value;
                }
                else
                    value=1;




                continue;

            }





        }

    }


    if (oneChecked==0) {

        message+="Atleast one drug must be selected "+"<br/>";
    }

    if ($j("#datePicker").val()=="") {

        message+="Give Encounter date "+"<br/>";
    }

    if ($j("#nextvisit").val()=="") {
        message+="No Next visit date "+"<br/>";

    }

    if ($j("#prescriber").val()=="") {
        message+="Enter the  prescriber "+"<br/>";

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
