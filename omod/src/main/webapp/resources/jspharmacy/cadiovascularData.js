$j("#datePicker").datepicker();
$j("#datePicker").datepicker().datepicker('setDate',new Date());
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
            if(this.id=="drug1"){
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
}) ;
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
                        alert("Ensure batch and quantity are set for this drug");
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
}) ;
$j('#drug16').live('click',function(){
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
        }
    })
}) ;
$j('#drug17').live('click',function(){
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
        }
    })
}) ;
$j('#drug18').live('click',function(){
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
        }
    })
}) ;
$j('#drug19').live('click',function(){
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
        }
    })
}) ;
$j('#drug30').blur( function(){
    var values = $j("input[title='drug']").map(
        function () {
            if(this.id=="drug30"){
                return $j(this).val().substring($j(this).val().indexOf('|')+1);
            }

        }).get();
    var values2 =$j("input[title='drug']").map(
        function () {
            if(this.id=="drug30"){
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
            document.getElementById("quantityInStore30").value=result;
        }
    })
}) ;
var a = {
    dosage: [{
        "dosageName": "Select",
        "dosageID": "0"
    }, {
        "dosageName": "1OD",
        "dosageID": "1"
    }, {
        "dosageName": "1BD",
        "dosageID": "2"
    }, {
        "dosageName": "1TDS",
        "dosageID": "3"
    },
        {
            "dosageName": "2OD",
            "dosageID": "4"
        },
        {
            "dosageName": "2BD",
            "dosageID": "5"
        },
        {
            "dosageName": "2TDS",
            "dosageID": "6"
        },
        {
            "dosageName": "3OD",
            "dosageID": "7"
        },
        {
            "dosageName": "3BD",
            "dosageID": "8"
        },
        {
            "dosageName": "3TDS",
            "dosageID": "9"
        }]
};

$j.each(a.dosage, function (key, value) {
    for(var i=0; i<=30; i++){
        $j("#dose"+i).append('<option value="'+value.dosageID+'">'+value.dosageName+'</option>');
    }
});

$j("#address, #city, #country").bind("change", function() {
    //do something
});
$j("input[title='gnty1']").blur( function () {
    document.getElementById("tamount1").value  =parseInt($j("input[title='gnty1']").val())* 2;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty2']").blur( function () {

    document.getElementById("tamount2").value  =parseInt($j("input[title='gnty2']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty3']").blur( function () {
    document.getElementById("tamount3").value  =parseInt($j("input[title='gnty3']").val())* 4;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty4']").blur( function () {
    document.getElementById("tamount4").value  =parseInt($j("input[title='gnty4']").val())* 4;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty5']").blur( function () {
    document.getElementById("tamount5").value  =parseInt($j("input[title='gnty5']").val())* 8;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty6']").blur( function () {
    document.getElementById("tamount6").value  =parseInt($j("input[title='gnty6']").val())* 6;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty7']").blur( function () {
    document.getElementById("tamount7").value  =parseInt($j("input[title='gnty7']").val())* 6;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty8']").blur( function () {
    document.getElementById("tamount8").value  =parseInt($j("input[title='gnty8']").val())* 4;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty9']").blur( function () {
    document.getElementById("tamount9").value  =parseInt($j("input[title='gnty9']").val())* 4;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty10']").blur( function () {

    document.getElementById("tamount10").value  =parseInt($j("input[title='gnty10']").val())* 4;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty11']").blur( function () {

    document.getElementById("tamount11").value  =parseInt($j("input[title='gnty11']").val())* 20;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty12']").blur( function () {

    document.getElementById("tamount12").value  =parseInt($j("input[title='gnty12']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty13']").blur( function () {

    document.getElementById("tamount13").value  =parseInt($j("input[title='gnty13']").val())* 2;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty14']").blur( function () {

    document.getElementById("tamount14").value  =parseInt($j("input[title='gnty14']").val())* 20;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty15']").blur( function () {

    document.getElementById("tamount15").value  =parseInt($j("input[title='gnty15']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty16']").blur( function () {

    document.getElementById("tamount16").value  =parseInt($j("input[title='gnty16']").val())* 30;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty17']").blur( function () {

    document.getElementById("tamount17").value  =parseInt($j("input[title='gnty17']").val())* 1;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});
$j("input[title='gnty30']").blur( function () {

    document.getElementById("tamount30").value  =parseInt($j("input[title='gnty30']").val())* 25;
    var sum = 0;
    $j('.expectedAmount').each(function() {
        sum += Number($j(this).val());
    });
    document.getElementById("expectedtotal").value=Number(sum);
});

$j("input[id='pamount1'],input[id='pamount2'],input[id='pamount3'],input[id='pamount4'],input[id='pamount5']" +
    "input[id='pamount6'],input[id='pamount7'],input[id='pamount8'],input[id='pamount9'],input[id='pamount10']" +
    "input[id='pamount11'],input[id='pamount12'],input[id='pamount13'],input[id='pamount14'],input[id='pamount15']").change( function () {
        var totalAmount=0;
        for(var i=0; i<30; i++){
            if ($j("#drug"+i+"").is(':checked') && $j("#pamount"+i+"]").val() !="") {
                totalAmount=totalAmount+ parseInt($j("#pamount"+i+"]").val());

            }

        }

        document.getElementById("totalamount").value  = totalAmount;
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
function validateCadiovascularForm(){
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
                if ($j("#pack"+t+"a").val()=="" || $j("#qnty"+t).val()=="" || $j("#pamount"+t).val()=="") {
                    value=0;
                    return value;
                }
                else
                    value=1;
                if (parseInt($j("#dose"+t).val())==0) {
                    value=0;
                    return value;
                }
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
        message+="No Next visit date "+"<br/>";    }

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
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });


});
