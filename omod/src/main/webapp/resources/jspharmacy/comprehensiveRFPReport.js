$j("#startDate").datepicker();
$j("#endDate").datepicker();
resultsTable = $j("#resultsTable").dataTable({
			sScrollX: "100%",
			sScrollXInner: "110%",
			sDom: '<"H"Clf>rt<"F"ip>',
			bJQueryUI: true,
			bAutoWidth: false,
            lengthMenu: [[100, 250, 500, -1], [100, 250, 500, "All"]],
            iDisplayLength : 50,
            sPagination: "full_numbers",
            paging: false,
			bScrollCollapse: true,
			"oTableTools": {
				sSwfPath: "/swf/copy_csv_xls_pdf.swf"
			}
		});
    function submitToFetchRecords(){
   		 $j('#west_panel_content').empty();
    	 $j('#west_panel_content').load("resources/subpages/comprehensiveRFPReport.form?startDate="+$j("#startDate").val()+"&endDate="+$j("#endDate").val(), function () {});
    	}
 var tableToExcel = (function() {
        			var uri = 'data:application/vnd.ms-excel;base64,'
        					, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        					, base64 = function(s) {
        				return window.btoa(unescape(encodeURIComponent(s)))
        			}
        			, format = function(s, c) {
        				return s.replace(/{(\w+)}/g, function(m, p) {
        					return c[p];
        				})
        			}
        			return function(table, name) {
        				if (!table.nodeType)
        					table = document.getElementById(table)
        				var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        				window.location.href = uri + base64(format(template, ctx))
        			}
        		}()
);