var oCache = {
    iCacheLower:-1
};

$j("#locationForm").validate();
getDataLocation();
$j("#hidelocation").hide();
$j("#showlocation").click(function () {
    $j("#hidelocation").show();
    $j("#showlocation").hide();
});
$j("#locationForm").show();
$j("#hidelocation").click(function () {
    $j("#hidelocation").hide();
    $j("#showlocation").show();
    $j("#locationForm").hide();
});
$j("#locationForm").submit(function () {
        var e = document.getElementById("locationsVal");
        var selectedLocation = e.options[e.selectedIndex].value;
            $j.ajax({
                type:"POST",
                url:"locationSetter.form?selectedLocation="+selectedLocation,
                success:function (data) {
                }
            });
    return true;
});
function getDataLocation() {
    $j.getJSON("getLocations.form",function (result) {
        if (result == "") {
            $j("#spinner").hide();
            $j("#errorDialog").empty();
            $j('<dl><dt></dt><dd >' + "Info: " + "No locations set for you, contact your admin" + '</dd></dl> ').appendTo('#errorDialog');
            $j("#errorDialog").dialog("open");
        }
        else {
            $j("#locationsVal").get(0).options.length = 0;
            $j("#locationsVal").get(0).options[0] = new Option("Select","-1");
            $j.each( result,function (index, value) {
                $j("#locationsVal").get(0).options[$j(
                    "#locationsVal").get(0).options.length] = new Option( value, value);
                $j("#spinner").hide();
            });
        }
    });
}