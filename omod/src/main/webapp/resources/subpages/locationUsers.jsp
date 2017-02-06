<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%@ page import="org.openmrs.api.context.Context" %>
<%@ page import="org.openmrs.module.pharmacy.model.*" %>
<%@ page import="org.openmrs.module.pharmacy.service.PharmacyService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.openmrs.Drug" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        .newRowClass{
            border:1px solid black;
        }
        table.visibleBorder td{
            border:1px solid black;
            background-color:#EBEBFF;
        }
        table.tdbordered td{
                    cell-padding:5px;
                    border:1px solid white;
                }
           #pharmacyLocationUserDIV{
             margin:10px;
             font-weight:bold;
             border:5px #AAAAAA;
           }
           #pharmacyLocationUserDIV table{
             background-color:#EBEBFF;
           }
           h2.centered{
            text-align:center;
           }
    </style>
<script type="text/javascript">
function saveForm(){
    var json = [];
    $j('#pharmacyLocationUserTable').find('tr').each(function(){
        var obj = {}
        var  td = $j(this).find('input,select,hidden');
        var key = td.attr('name');
        var val = td.val();
        obj[key] = val;
        json.push(obj);
    });
    $j.ajax({
        type:"POST",
        url:"locationUsers.form",
        data:{values:JSON.stringify(json) },
        success:function (result) {
        }
    });
}
var oCache = {
    iCacheLower:-1
};

var usersTable;
var userEditTr;
var userLink;
var uuid;
var useraData;
$j("#locationformusers").validate();
function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

$j("#username").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },
    source:function (request, response) {
        dataString = "user=" + request.term;
        $j.getJSON("displayPharmacyLocationUsers.form?" + dataString, function (result) {
            $j("#username").removeClass('working');
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
    change:function (event, ui) {
        if (!ui.item) {
            $j("#username").val("");
        }
    }
});
$j('#locationname').live('keydown.autocomplete', function () {
$j(this).autocomplete({
    search:function () {
        $j(this).addClass('working');
    },
    source:function (request, response) {
        dataString = "locations=" + request.term;
        $j.getJSON("displayPharmacyLocationUsers.form?" + dataString, function (result) {
            $j("#locationname").removeClass('working');
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
    change:function (event, ui) {
        if (!ui.item) {
            $j("#locationname").val("");
        }
    }
});
});
function AutoReload() {
    RefreshTable('#tlocationusers', 'displayPharmacyLocationUsers.form');

}
function fnFormatDetails(nTr) {
    document.getElementById("username").value=useraData[3];
    document.getElementById("locationname").value=useraData[4];
}
usersTable = $j('#tlocationusers').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    sAjaxSource:'displayPharmacyLocationUsers.form',
    "fnServerData":fnDataTablesPipeline,
    fnRowCallback:function (nRow, useraData, iDisplayIndex) {
        var htm = '<ul class="popLocationUsers">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popLocationUsers" id=' + "popLocationUsers" + useraData[2] + '>';
        if (useraData[0] == "edit") {
            htm += '<li> <a href=""  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';
        }
        if (useraData[5] == "void") {
            htm += '<li> <a href="" id="deleteUser" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }
        htm += '<li> <a href="" id="cancelUser"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';
        htm += '</ul></li></ul>';
        $j('td:eq(0)', nRow).html(htm);
        return nRow;
    },
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[0, 2, 5 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:3,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });
$j('#tlocationusers tbody tr').live('click', function () {
    var data = usersTable.fnGetData(this);
    uuid = data[2];
});
$j('#edit').live('click', function () {
    $j("ul .popLocationUsers").hide();
    fnFormatDetails(userEditTr);
    return false;
});
$j('#cancelUser').live('click', function () {
    $j("ul .popLocationUsers").hide();
    return false;
});
$j('#tlocationusers tbody td ul').live('click', function () {
    userEditTr = this.parentNode.parentNode;
    useraData = usersTable.fnGetData(userEditTr);
    userLink = "#popLocationUsers" + useraData[2];
    $j(userLink).show();
});

</script>
</head>
<body>
<div id="pharmacyLocationUserDIV">
<form id="pharmacyLocationUserForm"/>
<h2 class="centered">Pharmacy Location User</h2>
<table style="width:100%;" id="pharmacyLocationUserTable" class="tdbordered">
    <tr><td>User Name</td><td><input type="text" name="username" id="username" value="" /></td></tr>
    <tr><td>pharmacy Location</td><td><input type="text" name="locationname" id="locationname" value="" /></td></tr>
    <tr><td></td><td><input type="button" onclick="saveForm()" value="Save Data"/></td></tr>
</table>
</form>
</div>
 <table cellpadding="0" cellspacing="0" border="0" class="display" id="tlocationusers">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>User name</th>
            <th>Location</th>
            <th>Void</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</body>