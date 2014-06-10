var oCache = {
    iCacheLower:-1
};
var ooTable;
var editTr;
var link;
var aData;
$j("#fmapform").validate(); //
$j("#datef").datepicker();
$j("#datet").datepicker();

$j("form#fmapform").submit(function () {

    if ($j("#fmapform").valid()) {
        ooTable = $j('#fmaptable').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bServerSide:true,
            bAutoWidth:false,
            bProcessing:true,
            sAjaxSource: "fmapReport.form?" + "datef=" + $j('#datef').val() + "&datet=" + $j('#datet').val(),
            "fnFooterCallback": function (nRow, aaData, iStart, iEnd, aiDisplay) {
                for (var i = 0; i < aaData.length; i++) {
                   aaData[0];
                   aaData[1];
                   aaData[2];
                }

            },
            //sAjaxSource: "revolveAdult.form",
            "fnServerData":fnDataTablesPipeline
        });

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
$j("#export").click(function () {
    expTable(document.getElementById("fmaptable"));
});

function write_headers_to_excel()
{
    str="";

    var myTableHead = document.getElementById('headers');
    var rowCount = myTableHead.rows.length;
    var colCount = myTableHead.getElementsByTagName("tr")[0].getElementsByTagName("th").length;

    var ExcelApp = new ActiveXObject("Excel.Application");
    var ExcelSheet = new ActiveXObject("Excel.Sheet");
    ExcelSheet.Application.Visible = true;

    for(var i=0; i<rowCount; i++)
    {
        for(var j=0; j<colCount; j++)
        {
            str= myTableHead.getElementsByTagName("tr")[i].getElementsByTagName("th")[j].innerHTML;
            ExcelSheet.ActiveSheet.Cells(i+1,j+1).Value = str;
        }
    }

}