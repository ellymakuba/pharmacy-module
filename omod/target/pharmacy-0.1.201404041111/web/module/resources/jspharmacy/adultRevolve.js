var oCache = {
    iCacheLower:-1
};

var ooTable;
var editTr;
var link;
var aData;
$j("#dispensedform").validate(); //
$j("#datefrom").datepicker();
$j("#dateto").datepicker();



$j("form#dispensedform").submit(function () {
    if ($j("#dispensedform").valid()) {

            ooTable = $j('#tdispensed').dataTable({
                bJQueryUI:true,
                bRetrieve:true,
                bServerSide:true,
                bAutoWidth:false,
                bProcessing:true,
                "fnFooterCallback": function (nRow, aaData, iStart, iEnd, aiDisplay) {
                    var iTotal =[0,0];
                    for (var i = 0; i < aaData.length; i++) {
                        iTotal[0] += Number(aaData[i][3]);
                        iTotal[1] += Number(aaData[i][4]);
                    }

                    /* Modify the footer row to match what we want */
                    var nCells = nRow.getElementsByTagName('th');
                    nCells[0].innerHTML="Totals";
                    nCells[3].innerHTML = iTotal[0];
                    nCells[4].innerHTML = iTotal[1];
                },
                sAjaxSource: "revolveAdult.form?" + "datefrom=" + $j('#datefrom').val() + "&dateto=" + $j('#dateto').val(),
                //sAjaxSource: "revolveAdult.form",
                "fnServerData":fnDataTablesPipeline

            });
        var oFormObject = document.forms['dispensedform'];

        oFormObject.elements["datefrom"].value = "";
        oFormObject.elements["dateto"].value = "";
       return false;
}
});
(function(){
    var cache = {};

    this.tmpl = function tmpl(str, data){
        // Figure out if we're getting a template, or if we need to
        // load the template - and be sure to cache the result.
        var fn = !/\W/.test(str) ?
            cache[str] = cache[str] ||
                tmpl(document.getElementById(str).innerHTML) :

            // Generate a reusable function that will serve as a template
            // generator (and which will be cached).
            new Function("obj",
                "var p=[],print=function(){p.push.apply(p,arguments);};" +

                    // Introduce the data as local variables using with(){}
                    "with(obj){p.push('" +

                    // Convert the template into pure JavaScript
                    str.replace(/[\r\t\n]/g, " ")
                        .split("{{").join("\t")
                        .replace(/((^|}})[^\t]*)'/g, "$1\r")
                        .replace(/\t=(.*?)}}/g, "',$1,'")
                        .split("\t").join("');")
                        .split("}}").join("p.push('")
                        .split("\r").join("\\'")
                    + "');}return p.join('');");

        // Provide some basic currying to the user
        return data ? fn( data ) : fn;
    };
})();

var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{{=worksheet}}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>{{for(var i=0; i<tables.length;i++){ }}<table>{{=tables[i]}}</table>{{ } }}</body></html>',
        base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
        format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
    return function(tableList, name) {
        if (!tableList.length>0 && !tableList[0].nodeType) table = document.getElementById(table)
        var tables = [];
        for(var i=0; i<tableList.length; i++){
            tables.push(tableList[i].innerHTML);
        }
        var ctx = {worksheet: name || 'Worksheet', tables: tables};
        window.location.href = uri + base64(tmpl(template, ctx))
    }
})();
function expTable(tbl)
{
    var tables = [];
    tables.push(tbl);
    tableToExcel(tables,"one");
}
$j("#export_dispensed").click(function () {
    expTable(document.getElementById("tdispensed"));
});