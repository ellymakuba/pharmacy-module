<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.openmrs.web.WebConstants" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Pharmacy" otherwise="/login.htm" redirect="/module/pharmacy/home.form"/>
<html>
<head>
<style type="text/css">
    .newRowClass{
        border:1px solid black;
    }
    .queueFormDIVClass table tr td{
        border:1px solid white;
        background-color:#EBEBFF;
    }
</style>
<openmrs:htmlInclude file="/openmrs.js" />
<openmrs:htmlInclude file="/scripts/openmrsmessages.js"  appendLocale="true" />
<openmrs:htmlInclude file="/style.css" />
<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRAlertService.js" />
<c:if test="${empty DO_NOT_INCLUDE_JQUERY}">
    <openmrs:htmlInclude file="/scripts/jquery/jquery.min.js"/>
    <openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js"/>
    <openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js"/>
    <link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css"
          type="text/css" rel="stylesheet"/>
</c:if>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRPersonService.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<c:choose>
    <c:when test="${!empty pageTitle}">
        <title>${pageTitle}</title>
    </c:when>
    <c:otherwise>
        <title><spring:message code="openmrs.title" /></title>
    </c:otherwise>
</c:choose>
<openmrs:extensionPoint pointId="org.openmrs.headerMinimalIncludeExt"   type="html"
                        requiredClass="org.openmrs.module.web.extension.HeaderIncludeExt">
    <c:forEach var="file" items="${extension.headerFiles}">
        <openmrs:htmlInclude file="${file}" />
    </c:forEach>
</openmrs:extensionPoint>
<link  href="${pageContext.request.contextPath}/moduleResources/pharmacy/css/pharmacy_openmrs.css" type="text/css" rel="stylesheet"/>
<link  href="${pageContext.request.contextPath}/moduleResources/pharmacy/css/datatables.css" type="text/css" rel="stylesheet"/>

<%--Css for pharamcy module--%>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/moduleResources/pharmacy/css/style.css"/>


<script type="text/javascript"  src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.jeditable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.dataTables.rowGrouping.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.layout-latest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.dform-0.1.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.dataTables.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.easy-confirm-dialog.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/jspdf/jspdf.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/jspdf/jspdf.plugin.standard_fonts_metrics.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/jspdf/jspdf.plugin.split_text_to_size.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/jspdf/jspdf.plugin.from_html.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery.easy-confirm-dialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/jquery-jForm-plugin.js">  </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/Serialize.js">  </script>

<script type="text/javascript">
<c:if test="${empty DO_NOT_INCLUDE_JQUERY}">
    var $j = jQuery.noConflict();
</c:if>
/* variable used in js to know the context path */

$j("#patient").show();
var openmrsContextPath = '${pageContext.request.contextPath}';
var dwrLoadingMessage = '<spring:message code="general.loading" />';
var jsDateFormat = '<openmrs:datePattern localize="false"/>';
var jsLocale = '<%= org.openmrs.api.context.Context.getLocale() %>';

/* prevents users getting false dwr errors msgs when leaving pages */
var pageIsExiting = false;
if (jQuery)
    jQuery(window).bind('beforeunload', function () { pageIsExiting = true; } );

var handler = function(msg, ex) {
    if (!pageIsExiting) {
        var div = document.getElementById("openmrs_dwr_error");
        div.style.display = ""; // show the error div
        var msgDiv = document.getElementById("openmrs_dwr_error_msg");
        msgDiv.innerHTML = '<spring:message code="error.dwr"/>' + " <b>" + msg + "</b>";
    }
};
dwr.engine.setErrorHandler(handler);
dwr.engine.setWarningHandler(handler);

var lastSearch;
$j.validator.addMethod(
        "selectNone",
        function (value, element) {
            if (element.value == "-1") {
                return false;
            }
            else return true;
        }
);
var path = ${pageContext.request.contextPath}/;
function selectedChwProvider(userId, provider) {
    if (provider != null) {
        var prov = provider.userId;
    }
    $j("#requisitiono").val(prov);
    $j("#requisitionp").val(prov);
    $j("#requisition").val(prov)
}
function stopRKey(evt) {
    var evt = (evt) ? evt : ((event) ? event : null);
    var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
    if ((evt.keyCode == 13) && (node.type == "text")) {
        return false;
    }
}
document.onkeypress = stopRKey;
function selectedChwProvider1(userId, provider) {
    if (provider != null) {
        var prov = provider.userId;
    }
    $j("#issuedo").val(prov);
    $j("#issuedp").val(prov);
    $j("#issued").val(prov);
}
function selectedChwProvider2(userId, provider) {
    if (provider != null) {
        var prov = provider.userId;
    }
    $j("#authorized").val(prov);
    $j("#authorizedo").val(prov);
    $j("#authorizedp").val(prov);
}
jQuery.Page = {
    context:path
};


</script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/pharmacy/scripts/themeswitchertool.js"></script>
<script type="text/javascript">

var oCache = {
    iCacheLower:-1
};
function fnSetKey(aoData, sKey, mValue) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            aoData[i].value = mValue;
        }
    }
}

function fnGetKey(aoData, sKey) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            return aoData[i].value;
        }
    }
    return null;
}

function fnDataTablesPipeline(sSource, aoData, fnCallback) {
    var iPipe = 5;
    /* Ajust the pipe size */

    var bNeedServer = false;
    var sEcho = fnGetKey(aoData, "sEcho");
    var iRequestStart = fnGetKey(aoData, "iDisplayStart");
    var iRequestLength = fnGetKey(aoData, "iDisplayLength");
    var iRequestEnd = iRequestStart + iRequestLength;
    oCache.iDisplayStart = iRequestStart;

    /* outside pipeline? */
    if (oCache.iCacheLower < 0 || iRequestStart < oCache.iCacheLower || iRequestEnd > oCache.iCacheUpper) {
        bNeedServer = true;
    }

    /* sorting etc changed? */
    if (oCache.lastRequest && !bNeedServer) {
        for (var i = 0, iLen = aoData.length; i < iLen; i++) {
            if (aoData[i].name != "iDisplayStart" && aoData[i].name != "iDisplayLength" && aoData[i].name != "sEcho") {
                if (aoData[i].value != oCache.lastRequest[i].value) {
                    bNeedServer = true;
                    break;
                }
            }
        }
    }

    /* Store the request for checking next time around */
    oCache.lastRequest = aoData.slice();

    if (bNeedServer) {
        if (iRequestStart < oCache.iCacheLower) {
            iRequestStart = iRequestStart - (iRequestLength * (iPipe - 1));
            if (iRequestStart < 0) {
                iRequestStart = 0;
            }
        }

        oCache.iCacheLower = iRequestStart;
        oCache.iCacheUpper = iRequestStart + (iRequestLength * iPipe);
        oCache.iDisplayLength = fnGetKey(aoData, "iDisplayLength");
        fnSetKey(aoData, "iDisplayStart", iRequestStart);
        fnSetKey(aoData, "iDisplayLength", iRequestLength * iPipe);

        $j.getJSON(sSource, aoData, function (json) {
            /* Callback processing */
            oCache.lastJson = jQuery.extend(true, {}, json);

            if (oCache.iCacheLower != oCache.iDisplayStart) {
                json.aaData.splice(0, oCache.iDisplayStart - oCache.iCacheLower);
            }
            json.aaData.splice(oCache.iDisplayLength, json.aaData.length);

            fnCallback(json)
        });
    }
    else {
        json = jQuery.extend(true, {}, oCache.lastJson);
        json.sEcho = sEcho;
        /* Update the echo for each response */
        json.aaData.splice(0, iRequestStart - oCache.iCacheLower);
        json.aaData.splice(iRequestLength, json.aaData.length);
        fnCallback(json);
        return;
    }
}

$j("#themeSwitcher").themeswitcher(
        {
            "loadTheme":"Flick"
        });

$j(document).ajaxError(function(e, xhr, settings, exception) {
    $j("#errorDialog").empty();
    $j('<dl><dt></dt><dd >' + "Error: " +  'error in:' + settings.url + '-' + exception + '</dd></dl> ').appendTo('#errorDialog');
    $j("#errorDialog").dialog("open");
});


function hideInactiveDivElements(){
    $j("#psychiatrysec").hide();
    $j("#locationError").hide();
    //$j("#outgoing").tabs();
    return false;
}
$j(document).ready(
        function() {
//            Variables used in the module
            var pageToDisplay;
            var patientID;
            var patientName;
            var url;
            var dateGiven;
            var quantity;
            var price;
            var valgiven;
            var method;
            var priceOne;
            var valgivenOne;
            var methodOne;
            new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectionHandler,
                    [	{fieldName:"identifier", header:omsgs.identifier},
                        {fieldName:"givenName", header:omsgs.givenName},
                        {fieldName:"middleName", header:omsgs.middleName},
                        {fieldName:"familyName", header:omsgs.familyName},
                        {fieldName:"age", header:omsgs.age},
                        {fieldName:"gender", header:omsgs.gender},
                        {fieldName:"birthdateString", header:omsgs.birthdate},
                    ],
                    {
                        searchLabel: '<spring:message code="Patient.searchBox" javaScriptEscape="true"/>'
                                <c:if test="${not empty param.phrase}">
                    , searchPhrase: '<spring:message text="${ param.phrase }" javaScriptEscape="true"/>'
            </c:if>
        });


$j("#errorDiv").hide();
$j('#west_panel_content').load('resources/subpages/rfpDispenseForm.form',function(){});
/*Generic datatable r;efresh function*/
function RefreshTable(tableId) {
    table = $j(tableId).dataTable();
    table.fnDraw();
}
/*Fucntion to be called when a patient is selected*/
function doSelectionHandler(index, data) {
    patientID = data.patientId;
    $j.getJSON("dispense.form?Pid=" + patientID,function (result) { $j.each(result,function (index, value) {
        $j("#patientInfo").show("slow");//
        $j('#patientInfo .locc').replaceWith("<div id='red'>" + "Patient Name:" + value + "</div>");
    });
    });
    url = data.patientId;
    $j("#patient").show();
    $j("#corn").hide();
    var oFormObject = document.forms['cornform'];
    oFormObject.reset();
    var path = ${pageContext.request.contextPath}/;
    jQuery.Pid = {
        id:url
    };

    jQuery.Page = {
        context:path
    };
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/dispensePage.jsp #disp', function () {

        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/dispensePage.js", function () {
        });


    });

    $j("#newEncounter").show();
    $j("#parent_div_2").hide();
}


//searchHandler for the Search widget
function doPatientSearch(text, resultHandler, getMatchCount, opts) {
    lastSearch = text;
    DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
}
$j('a.ui-dialog-titlebar-close').remove();
$j("#patient").show();
$j("#newEncounter").hide();
$j("#dialog").dialog({ position:[350, 100],
    modal:false,
    draggable:false,
    width:400
});

$j("#dialog1").dialog({
    position:[900, 100],
    modal:false,
    draggable:false,
    width:400
});
function test() {
    $j("#dialog-confirm").dialog('open');
}
function CloseDialog() {
    $j("#dialog").dialog('close');
    $j("#dialog1").dialog('close');

}
function CloseAll() {
    $j("#Approved").dialog('close');
    $j("#Requests").dialog('close');
    $j("#Pending").dialog('close');
}
$j("#dashboard").click(function () {

});
hideInactiveDivElements();
//form validation
$j("#formulationvoid").validate();
$j("#drugs").validate({
    rules:{
        drugsformulation:{
            selectNone:true
        },
        drugsstrength:{
            selectNone:true
        },
        drugsunits:{
            selectNone:true
        }
    }
});
$j("#doses").validate({
    rules:{
        dosesdrug:{
            selectNone:true
        },
        dosesfrequency:{
            selectNone:true
        },
        dosequantity:{
            required:true,
            digits:true
        }
    }
});

$j("#binvoid").validate();
$j("#bincard").validate({
    rules:{
        bindrug:{
            selectNone:true
        },
        binquantityin:{
            required:true,
            digits:true
        },
        binmin:{

            digits:true
        },
        binmax:{

            digits:true
        }
    }
});
/*Layout */
myLayout = $j("body") .layout( { west__size:300 });

// ACCORDION - in the West pane
$j("#accordion1").accordion({
//    heightStyle: "fill" ,
    autoHeight: false
//    navigation: true,
//    heightStyle: "content"
//    fillSpace:true, active:1 // set active accordion-panel
});

$j("#ts").click(function () {
    // load about page on click

    //$j("#myform").buildForm("m/sample.json");
    $j("#store").show("slow");
    $j("#stock").hide();

});
$j("#retireInventoryBatches").click(function() {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/retireInventoryBatches.form' , function() {

    });
    hideInactiveDivElements();
});
/*shows drug categories view*/
$j("#DCategories").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugCategories.jsp #dnames', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugCategories.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#DrugCategory").show();
});
/*show Dispense Batch settings view*/
$j("#DBatch").click(function () {
    $j("#parent_div_2").hide();
    $j("#parent_div_1").hide();
    $j("#spinner").show();
    /*Check if location has been set */
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/dispenseSettings.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/dispenseSettings.js", function () {
                });
            });
            hideInactiveDivElements();
            $j("#Batchsetter").show();

        }
    });
});
$j("#dSetUp").click(function () {
    $j("#parent_div_2").hide();
    $j("#parent_div_1").hide();
    $j("#spinner").show();
    /*Check if location has been set */
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugAdministration.JSP #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugAdministration.js", function () {
                });
            });
            hideInactiveDivElements();
            $j("#drugSetUp").show();
        }
    });
});
/*show barcode generation page*/
$j("#DBarcode").click(function () {
    $j("#parent_div_2").hide();
    $j("#parent_div_1").hide();
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/barcode.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/barcode.js", function () {
                });
            });
            hideInactiveDivElements();
            $j("#Barcode").show();
        }
    });
});

$j("#tbFormLink").click(function () {
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/tbForm.jsp', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/tbForm.js", function () {

                });
            });
            hideInactiveDivElements();
            $j("#tbFormContainer").show();
        }
    })
});
$j("#pediatric").click(function () {
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/PediatricForm.form', function () {
    });
});
$j("#adultHiv").click(function () {
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/HivForm.form', function () {
    });
});
$j("#adultOI").click(function () {
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/adultOIForm.jsp', function () {
                // $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/PediatricForm.js", function () {

                // });
            });
            hideInactiveDivElements();
        }
    })
});
/*show Bincard Totals view*/
$j("#bincardtotaldrugs").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/bincardTotal.jsp #dtotal', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugbincardtotal.js", function () {

        });
    });
});
/*show stock take interface*/
$j("#dstock").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/bincardStock.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugbincardStock2.js", function () {

                });

            });
            hideInactiveDivElements();
            $j("#stock").show("slow");
        }
    });

});
$j("#stockTakeForm").click(function () {
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/stockTakeForm.form', function () {
    });
    hideInactiveDivElements();
});
$j("#stockTakeApprovalForm").click(function () {
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/stockTakeApprovalForm.form', function () {
    });
    hideInactiveDivElements();
});
$j("#stockTransferApprovalForm").click(function () {
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/stockTransferApprovalForm.form', function () {
    });
    hideInactiveDivElements();
});
$j("#dispenseRFPForm").click(function(){
$j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/rfpDispenseForm.form', function () {
    });
    hideInactiveDivElements();
});
/*show bincard view*/
$j("#dstore").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {

            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/bincard.jsp #dtab_1', function () {


                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugbincard.js", function () {

                });

            });
            var data = '<strong>Drug management: Location1-' + result + '</strong>';
            $j('#instorelocation').empty();
            $j(data).appendTo(' #instorelocation');
            hideInactiveDivElements();
            $j("#store").show("slow");
        }
    });
});
/*low stock view*/
$j("#dlow").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/lowbincard.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/druglowbincard.js", function () {

                });

            });

            var data = '<strong>Drug Low levels: Location2-' + result + '</strong>';
            hideInactiveDivElements();
            $j(data).appendTo(' #inlowlocation');
            $j("#druglow").show("slow");
        }
    });
});
/*Short to expire stock*/
$j("#dhigh").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();

            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/highbincard.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drughighbincard.js", function () {

                });

            });
            var data = '<strong>Drug high levels: Location3-' + result + '</strong>';
            hideInactiveDivElements();
            $j(data).appendTo(' #inhighlocation');
            $j("#drughigh").show("slow");
        }
    });
});

/*View to choose location*/
$j("#locationsetterid").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/locationSetter.jsp #dlocation', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/locationSetter.js", function () {

        });
    });
    hideInactiveDivElements();
    $j("#locationsetter").show("slow");
});
/*View to choose location*/
$j("#requestsum").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/patientWaivers.form', function () {

    });
    hideInactiveDivElements();
    $j("#requestsumpage").show();hideInactiveDivElements();
});
$j("#patientDiscountsReport").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/patientDiscounts.form', function () {

    });
    hideInactiveDivElements();
    $j("#requestsumpage").show();hideInactiveDivElements();
});

$j("#s11").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/s11_reports.jsp #reportS11', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/s11_reports.js", function () {

        });
    });
    hideInactiveDivElements();
});
$j("#FCDRR").click(function () {
   $j('#west_panel_content').empty();
           $j('#west_panel_content').load('resources/subpages/F-CDRR.form' , function() {

           });
           hideInactiveDivElements();
});
$j("#standAloneFCDRR").click(function () {
   $j('#west_panel_content').empty();
           $j('#west_panel_content').load('resources/subpages/F-CDRRStandAlone.form' , function() {

           });
           hideInactiveDivElements();
});
$j("#DCDRR").click(function () {
   $j('#west_panel_content').empty();
           $j('#west_panel_content').load('resources/subpages/D-CDRR.form' , function() {

           });
           hideInactiveDivElements();
});
$j("#rfpreport").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/rfpGeneralReport.form', function () {

    });
    hideInactiveDivElements();
});
$j("#comprehensiveRFPReport").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/comprehensiveRFPReport.form', function () {

    });
    hideInactiveDivElements();
});
$j("#inventoryMetaData").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/inventoryMetaData.form', function () {
    });
    hideInactiveDivElements();
});
$j("#supplier").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/supplier.form', function () {
    });
    hideInactiveDivElements();
});
$j("#newS11").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/s11Form.form', function () {
    });
    hideInactiveDivElements();
});
$j("#receiveDeliveryNote").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/deliveryNote.form', function () {
    });
    hideInactiveDivElements();
});
$j("#issueDeliveredGoods").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/issueDeliveredGoods.form', function () {
    });
    hideInactiveDivElements();
});
$j("#regimenAgeGenderBreakDown").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/regimenAgeGenderBreakDown.form', function () {
    });
    hideInactiveDivElements();
});
$j("#dailyDispensedPrescriptionsPerUser").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/dailyDispensedPrescriptionsPerUser.form', function () {
    });
    hideInactiveDivElements();
});
$j("#rfpCashierReport").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/rfpCashierSalesReport.jsp', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/rfpCashierReport.js", function () {

        });
    });
    hideInactiveDivElements();
});
/*View to choose location*/
$j("#fmapreport").click(function () {
    $j('#west_panel_content').empty();
        $j('#west_panel_content').load('resources/subpages/FmapReport.form' , function() {

        });
        hideInactiveDivElements();
});

$j("#dsdr").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/dsdr.jsp #report', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/dsdr.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#dsdrpage").show();
});

/*View to choose location*/
$j("#drugusage").click(function () {
    $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/locationSetter.jsp #dlocation', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/locationSetter.js", function () {

        });
    });
    hideInactiveDivElements();
    $j("#drugusagepage").show();
});

/*View incoming requests*/
$j("#dincoming").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/incoming.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugincoming.js", function () {
                    $j('#date').datepicker({changeMonth:true,changeYear:true,yearRange: "2015:2025"});
                    $j(".myDate").live('focus', function(){
                        var $jthis = $j(this);
                         if(!$jthis.data('datepicker')) {
                            $jthis.removeClass("hasDatepicker");
                            $jthis.datepicker({changeMonth:true,changeYear:true,yearRange: "2015:2025"});
                            $jthis.datepicker("show");
                        }
                    });
                });
            });
            var data = '<strong>Drug  requests: Location4-' + result + '</strong>';
            $j('#inincominglocation').empty();
            hideInactiveDivElements();
            CloseAll();
            $j(data).appendTo(' #inincominglocation');
            $j("#incomingData").show("slow");
        }
    });
});
$j("#dincomingnew").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/incomingnew.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugincomingnew.js", function () {
                });
            });
            var data = '<strong>Drug  requests: Location5-' + result + '</strong>';
            $j('#inincominglocation').empty();
            hideInactiveDivElements();
            CloseAll();
            $j(data).appendTo(' #inincominglocation');
            $j("#incomingDatanew").show("slow");
        }
    });
});
$j("#dapproved").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/approved.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugapproved.js", function () {
                });
            });
            var data = '<strong>Drug approved requests: Location6-' + result + '</strong>';
            $j('#approvedlocation').empty();
            $j(data).appendTo(' #approvedlocation');
            hideInactiveDivElements();
            CloseAll();
            $j("#approvedData").show();
            //		CloseDialog();
        }
    });

});
/*outgoing requests view*/
$j("#doutgoing").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/outgoing.jsp #dtab_1', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugoutgoing.js", function () {
                });
            });
            var data = '<strong>Drug requests from other sites: Location7-' + result + '</strong>';
            $j('#inoutgoinglocation').empty();
            $j(data).appendTo(' #inoutgoinglocation');
            hideInactiveDivElements();
            CloseAll();
            $j("#outgoing").show("slow");
        }
    });

});
/*view transaction logs*/
$j("#dtransactions").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugtransactions.jsp #dtransactionsdiv', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugtransactions.js", function () {
                });
            });
            var data = '<strong>Drug transactions: Location8-' + result + '</strong>';
            $j('#intransactionslocation').empty();
            $j(data).appendTo(' #intransactionslocation');
            hideInactiveDivElements();
            CloseAll();
            $j("#transactionsLogs").show("slow");
        }
    });
});
$j("#stockTransfer").click(function () {
 $j("#spinner").show();
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/stockTransfer.form', function () {
    });
    hideInactiveDivElements();
});
$j("#dmanager").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/doseForm.jsp', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/doseForm.js", function () {
                });
            });
            var data = '<strong>Drug transactions: Location9-' + result + '</strong>';
            $j('#intransactionslocation').empty();
            $j(data).appendTo(' #intransactionslocation');
            hideInactiveDivElements();
            CloseAll();
            $j("#doseManagement").show();
        }
    });
});
$j("#systemStockQuantitiesForm").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/systemStockQuantities.jsp', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/systemStockQuantities.js", function () {
                });
            });
            var data = '<strong>Drug transactions: Location9-' + result + '</strong>';
            $j('#intransactionslocation').empty();
            $j(data).appendTo(' #intransactionslocation');
            hideInactiveDivElements();
            CloseAll();
            $j("#systemStock").show();
        }
    });
});
$j("#patient").click(function () {
    $j("#corn").show();
});
$j("#edit_encounter").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/editPrescription.jsp #edit_prescription', function () {});
});

$j("#cstrength").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugstrength.jsp #dstrength', function () {

        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugstrength.js", function () {

        });

    });
});

$j("#cdnames").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugnames.jsp #dnames', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugnames.js", function () {

        });
    });

});

$j("#cunits").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugunits.jsp #dunits', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugunit.js", function () {

        });
    });
});
$j("#rnames").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/regimenname.jsp #rnamediv', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/regimennames.js", function () {

        });
    });
});
$j("#combinationnames").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/regimencombinations.jsp #rcombidiv', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugregimen.js", function () {

        });
    });
});
$j("#cnames").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugdetails.jsp #details', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugdetails.js", function () {

        });
    });
});
$j("#cdoses").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugdoses.jsp #ddoses', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugdoses.js", function () {

        });
    });
});
$j("#cfrequency").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugfrequency.jsp #dfrequency', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugfrequency.js", function () {
        });
    });
});
/*Drug settings*/
$j("#dsettings").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugformulation.jsp #dformulation', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugformulation.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#settings").show("slow");
});

/*View suppliers*/
$j("#Asupplier").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/suppliernames.jsp #dnames', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/suppliers.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#supplierSettings").show("slow");
});
$j("#tt").click(function () {
    $j("#parent_div_2").hide();
    $j("#parent_div_1").hide();
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/transactiontypenames.jsp #dnames', function () {
                $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/transactiontypes.js", function () {
                });
            });
            hideInactiveDivElements();
            $j("#transactionsSetting").show("slow");
        }
    });
});
/*View locations names*/
$j("#locationName").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/locationNames.jsp #dnames', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/locationNames.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#locationNames").show("slow");
});
/*Tie users to specificlocations*/
$j("#locationUser").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('resources/subpages/locationUsers.form', function () {});
    hideInactiveDivElements();
});

/*View max and min level setters*/
$j("#Dmaxmin").click(function () {
    $j('#west_panel_content').empty();
    $j('#west_panel_content').load('${pageContext.request.contextPath}/moduleResources/pharmacy/subpages/drugMaxMin.jsp #dnames', function () {
        $j.getScript("${pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugMaxMin.js", function () {
        });
    });
    hideInactiveDivElements();
    $j("#maxmin").show("slow");
});
/*get form data just a test*/
function getData() {
    $j.getJSON("jsonforms.form?datajson=cf7f7bd6-8407-47bb-8846-06af87557323",
            function (result) {
                $j("#psychiatryform").buildForm(JSON.parse(result));
            });

}
/*Generic error dialog */
$j("#errorDialog").dialog({
    autoOpen:false,
    height:250,
    width:600,
    cache:false,
    modal:true,
    buttons:{

        Ok:function () {
            $j(this).dialog('close');
        }
    }
});
$j("#patientSummaryDialog").dialog({
    autoOpen:false,
    height:350,
    width:450,
    cache:false,
    modal:true,
    buttons:{
        Ok:function () {
            $j(this).dialog('close');
        }
    }
});
$j("#successDialog").dialog({
    autoOpen:false,
    height:150,
    width:300,
    cache:false,
    modal:true
});
$j("#psychiatry").click(function () {
    $j("#spinner").show();
    $j.getJSON("locationSetter.form", function (result) {
        if (result == "none") {
            $j("#errorDiv").show();
            $j("#errorDiv").delay(5000).hide("slow");
            $j("#spinner").delay(5000).hide("slow");
        }
        else {
            $j('#west_panel_content').empty();
            $j("#psychiatrysec").show();
        }
    });
});
/*Generic form when a user submits from it */
$j("form#generalform").submit(function () {
    if ($j("#medication").val() != "") {
        var values = $j("input[id='medication']")
                .map( function () {
                    return $j(this).val().substring($j(this).val().indexOf("#") + 1);
                }).get();
        var values2 = $j("input[id='month1']")
                .map(
                function () {
                    return $j(this).val();
                }).get();
        var vals = values.toString().split(",");
        var vals2 = values2.toString().split(",");
        var size = vals.length;
        var json = {};
        for (i = 0; i < size; i++) {
            if (json.hasOwnProperty(vals[i])) {
                var tot = parseInt(json[vals[i]]) + parseInt(vals2[i]);
                json[vals[i]] = tot;
            }
            else
                json[vals[i]] = vals2[i];
        }
        $j.ajax({
            type:"GET",
            url:"dispense.form",
            data:{drugCheck:JSON.stringify(json) },
            dataType:"json",
            success:function (result) {
                if (result.toString() == 'true') {
                    var fields = $j("#generalform").serializeArray();
                    $j.ajax({
                        type:"POST",
                        url:"hiv.form",
                        data:{values:JSON.stringify(fields) },
                        dataType:"json",
                        beforeSend:function (x) {
                            if (x && x.overrideMimeType) {
                                x.overrideMimeType("application/j-son;charset=UTF-8");
                            }
                        },
                        success:function () {
                            clear_form_elements("#generalform");
                        }
                    });
                }
                else {
                    $j("#info").text("Cannot dispense now you have not set batch no !!!!!");
                    $j("#info").css("color", "red");
                    setTimeout(function () {
                        $j("#info").css("color", "white");
                    }, 10000);
                }
            }
        });
    }
    else {
        $j("#info").text("You must add drugs ");

        $j("#info").css("color", "red");
        setTimeout(function () {
            $j("#info").css("color", "white");
        }, 5000);
    }
    return false;
});
/*Function to clear form elements*/
function clear_form_elements(ele) {
    $j(ele).find(':input').each(function () {
        switch (this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'hidden':
            case 'textarea':
                $j(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });
}
CloseDialog();
});

</script>
</head>
<body>
<%

    String userLocation=(String)session.getAttribute("location");
%>
<DIV id="center" class="ui-layout-center">
    <div class="header-footer ui-state-default ui-corner-all">
        <div id="location_session">
            <table>
                <tr>
                    <%
                        if(userLocation ==null){
                    %>
                    <td>Please set location before you start using the system</td>
                    <%
                    } else {
                    %>
                    <td>Current Location:</td>
                    <td>
                        <%
                            out.print(userLocation);
                        %>
                    </td>
                    <%} %>
                </tr>
            </table>

        </div>
    </div>
    <div id="successDialog" title="Success"></div>
    <div id="errorDialog" title="Error"></div>
    <div id="patientSummaryDialog" title="patient Summary"></div>
    <DIV id="psychiatrysec">
        <DIV class="ui-layout-content ui-widget-content ui-corner-bottom" style="border-top: 0; padding-bottom: 1em;">
            <fieldset id="parent_field">
                <A href="#" class="top" id="patient"> Search patient</A>
            </fieldset>
            <div id="corn">
                <form id="cornform">
                    <fieldset>
                        <legend>
                            <c:if test="${empty patient}">Enter and select the patient:</c:if>
                            <c:if test="${not empty patient}">Profile</c:if>
                        </legend>
                        <input id="hiddenfield" type="hidden" name="fieldCount"/>
                        <div>
                            <div>
                                <c:choose>
                                    <c:when test="${empty patient}">
                                        <style>
                                            #openmrsSearchTable_wrapper {
                                                /* Removes the empty space between the widget and the Create New Patient section if the table is short */
                                                /* Over ride the value set by datatables */
                                                min-height: 0px;
                                                height: auto !important;
                                            }
                                        </style>
                                        <script type="text/javascript">
                                        </script>
                                        <div>
                                            <div class="searchWidgetContainer" id="findPatients"></div>
                                        </div>

                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${not empty patient}">
                                            <table border="0" style="background-color: #B4D1B6;">
                                                <tr>
                                                    <td><b>Name: &nbsp;</b> <c:if
                                                            test="${not empty patient.givenName}">${patient.givenName}
                                                        &nbsp;</c:if>
                                                        <c:if test="${not empty patient.middleName}">${patient.middleName}
                                                            &nbsp;</c:if>
                                                        <c:if test="${not empty patient.familyName}">${patient.familyName}</c:if>
                                                    </td>
                                                    <td><b>DOB (Age): &nbsp;</b> <c:if
                                                            test="${not empty patient.birthdate}">${patient.birthdate}
                                                        &nbsp;(${patient.age} )</c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><b>Identifiers : &nbsp;</b> <c:if
                                                            test="${not empty patient.identifiers}">${patient.identifiers} </c:if>
                                                    </td>
                                                    <td><b>Gender : &nbsp;</b> <c:if
                                                            test="${not empty patient.gender}">${patient.gender} </c:if>
                                                    </td>
                                                </tr>


                                            </table>

                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                    </fieldset>
                </form>
            </div>
            <div id="dispenseform" title="Dispense to:">
                <form id="psychiatryform"></form>
                <form id="generalform"></form>
                <div id="pregimen"><form id="hivform"></form></div>
                <form id="hivAdultOiForm"></form>
                <form id="hivpedsform"></form>
                <form id="revolvingPedsform"></form>
                <form id="revolvingAdultform"></form>
                <form id="cadiovascularform"></form>
            </div>
            <div id="encountform" title="Information"></div>
            <DIV id="tab_1psychiatry">
            </DIV>
        </DIV>
    </DIV>
    <div id="west_panel_content"></div>
</DIV>
<DIV class="ui-layout-north">
    <div id="pageBodyP">
        <div id="userBar">
            <openmrs:authentication>
                <c:if test="${authenticatedUser != null}">
					<span id="userLoggedInAs" class="firstChild"> <spring:message
                            code="header.logged.in"/> ${authenticatedUser.personName} </span>
					<span id="userLogout"> <a
                            href='${pageContext.request.contextPath}/logout'><spring:message
                            code="header.logout"/>
                    </a> </span>
					<span> <a
                            href="${pageContext.request.contextPath}/options.form"><spring:message
                            code="Navigation.options"/>
                    </a> </span>
                </c:if>
                <c:if test="${authenticatedUser == null}">
					<span id="userLoggedOut" class="firstChild"> <spring:message
                            code="header.logged.out"/> </span>
					<span id="userLogIn"> <a
                            href='${pageContext.request.contextPath}/login.htm'><spring:message
                            code="header.login"/>
                    </a> </span>
                </c:if>
            </openmrs:authentication>

			<span id="userHelp"> <a
                    href='<%= request.getContextPath() %>/help.htm'><spring:message
                    code="header.help"/>
            </a> </span>
        </div>
        <%@ include file="/WEB-INF/template/banner.jsp" %>
    </div>
</DIV>
<DIV class="ui-layout-west">
    <div id="accordion1" class="basic">
        <h3>
            <a href="#">Location </a>
        </h3>
        <div class="ui-layout-content">
            <ul>
                <li><A href="#" id="locationsetterid">Select location</A></li>
                <% if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin")){ %>
                <LI><A href="#" id="locationUser">Location Users</A></LI>
                <LI><A href="#" id="locationName">Location names</A></LI>
                <% } %>
            </ul>
        </div><%
        if(userLocation !=null){
            if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy dispense") || org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin ")) {
    %>
        <h3>
            <div id="ui1">
                <a href="#">RFP Dispensing Form</a>
            </div>

        </h3>
        <div class="ui-layout-content">
            <div id="ui18">
                <UL>
                    <LI><A href="#" id="dispenseRFPForm">RFP Prescription Form</A>
                    </LI>
                </UL>
            </div>
        </div>
        <% }
            if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy inventory")){
        %>
        <h3>
            <div id="ui2">
                <a href="#">Main Pharmacy Forms</a>
            </div>
        </h3>
        <div class="ui-layout-content">
            <div id="ui28">
                <!--<LI><A href="#" id="psychiatry">Start Dispensing</A></LI>-->
                <LI><A href="#" id="pediatric">Pediatric ARV Form</A></LI>
                <LI><A href="#" id="adultHiv">Adult HIV Form</A></LI>
                <LI><A href="#" id="tbFormLink">TB Drugs Dispensing Form</A></LI>
            </div>
        </div>
        <% } %>
        <h3>
            <a href="#">Reports </a>
        </h3>
        <div class="ui-layout-content">
            <UL>
                <%  if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy dispense") || org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin ")){
                %>
                <LI><A href="#" id="rfpreport">General RFP Report</A></LI>
                <LI><A href="#" id="comprehensiveRFPReport">Comprehensive RFP Report</A></LI>
                <LI><A href="#" id="rfpCashierReport">RFP Cashier Report</A></LI>
                <% }
                 if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy inventory")){
                %>
                <LI><A href="#" id="fmapreport">F-map Report</A></LI>
                <LI><A href="#" id="requestsum">Patient Waivers Report</A></LI>
                <LI><A href="#" id="patientDiscountsReport">Patient Discounts Report</A></LI>
                <LI><A href="#" id="s11">View S11 Reports</A></LI>
                <LI><A href="#" id="FCDRR">FCDRR For Satellite Sites</A></LI>
                <LI><A href="#" id="standAloneFCDRR">FCDRR For Stand Alone Site</A></LI>
                <LI><A href="#" id="DCDRR">DCDRR For Central Sites</A></LI>
                <LI><A href="#" id="regimenAgeGenderBreakDown">Regimen Breakdown Report</A></LI>
                <LI><A href="#" id="dailyDispensedPrescriptionsPerUser">Daily Dispensed Prescriptions Per User</A></LI>
                <% } %>
            </UL>
        </div>

        <%
            if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy dispense") || org.openmrs.api.context.Context.hasPrivilege("Pharmacy inventory")) {
        %>
        <h3>
            <div id="ui">
                <a href="#">Inventory</a>
            </div>
        </h3>
        <div class="ui-layout-content">
            <div id="ui8">
                <UL>
                    <LI><A href="#" id="newS11">Receive New S11</A> </LI>
                    <LI><A href="#" id="stockTakeForm">Stock Take Form</A></LI>
                    <LI><A href="#" id="stockTransfer">Stock Transfer Between Sites</A></LI>
                    <LI><A href="#" id="dtransactions">View Transactions Logs</A></LI>
                </UL>
            </div>
        </div>
        <%
            }
            if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin") || org.openmrs.api.context.Context.hasPrivilege("Pharmacy dispense")) {
        %>
        <h3>
            <div id="ui3">
                <a href="#">Admin functions</a>
            </div>
        </h3>
        <div>
            <div class="ui-layout-content">
                <div id="ui38">
                    <%  if (org.openmrs.api.context.Context.hasPrivilege("Pharmacy Admin")){ %>
                    <LI><A href="#" id="receiveDeliveryNote">Receive Delivery Note</A> </LI>
                    <LI><A href="#" id="issueDeliveredGoods">Issue Delivered Goods</A> </LI>
                    <LI><A href="#" id="stockTakeApprovalForm">Stock Take Approval</A></LI>
                    <LI><A href="#" id="inventoryMetaData">Inventory MetaData</A></LI>
                    <LI><A href="#" id="stockTransferApprovalForm">Stock Transfer Approval</A></LI>
                    <LI><A href="#" id="retireInventoryBatches">Retire Inventory Batches</A></LI>
                    <LI><A href="#" id="tt">Transaction Types</A></LI>
                    <LI><A href="#" id="dmanager">Dose Management</A></LI>
                    <LI><A href="#" id="DCategories">Drug Categories</A> </LI>
                    <LI><A href="#" id="supplier">Supplier Management</A> </LI>
                    <% } %>
                    <LI><A href="#" id="DBatch">Dispense Batch settings</A></LI>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</DIV>
<DIV class="ui-layout-south">
    <div id="footer">
        <div id="footerInner">
			<span id="localeOptions">
				<% //removes last instance of lang= from querystring and encodes the url to avoid xml problems
                    String qs = org.apache.commons.lang.StringEscapeUtils.escapeXml(request.getQueryString());
                    if (qs == null)
                        qs = "";
                    int i = qs.lastIndexOf("&amp;lang=");
                    if (i == -1)
                        i = qs.length();
                    pageContext.setAttribute("qs", qs.substring(0, i));
                    pageContext.setAttribute("locales", org.openmrs.api.context.Context.getAdministrationService().getPresentationLocales());
                    pageContext.setAttribute("openmrsVersion", org.openmrs.util.OpenmrsConstants.OPENMRS_VERSION);
                    pageContext.setAttribute("locale", org.openmrs.api.context.Context.getLocale());
                %>

				<c:forEach items="${locales}" var="loc" varStatus="status">
                    <%
                        java.util.Locale locTmp = (java.util.Locale) pageContext.getAttribute("loc");
                        pageContext.setAttribute("locDisplayName", locTmp.getDisplayName(locTmp));
                    %>
                    <c:if test="${status.index != 0}">| </c:if>
                    <c:if test="${fn:toLowerCase(locale) == fn:toLowerCase(loc)}">${locDisplayName}</c:if>
                    <c:if test="${fn:toLowerCase(locale) != fn:toLowerCase(loc)}"><a
                            href="?${qs}&amp;lang=${loc}">${locDisplayName}</a></c:if>
                </c:forEach>
			</span>

            <span id="buildDate"><spring:message
                    code="footer.lastBuild"/>: <%= org.openmrs.web.WebConstants.BUILD_TIMESTAMP %></span>

            <span id="codeVersion"><spring:message code="footer.version"/>: ${openmrsVersion}</span>

            <span id="poweredBy"><a href="http://openmrs.org"><spring:message code="footer.poweredBy"/> <img border="0"
                                                                                                             align="top"
                                                                                                             src="<%= request.getContextPath() %>/images/openmrs_logo_tiny.png"/></a></span>
        </div>
    </div>
</DIV>
</body>
<script type="text/javascript">
    $j('.datePickerClass').datepicker();
</script>
</html>