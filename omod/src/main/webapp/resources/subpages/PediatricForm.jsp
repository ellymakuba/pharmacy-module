<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Pediatric ARV Prescription form</title>
    <style type="text/css">
        .pediatricFormDivClass table tr td{
            border:1px solid white;
            background-color:#EBEBFF;
        }
    </style>
</head>
<body>
<DIV id="pediatricFormDiv" class="pediatricFormDivClass">
<h1><a>Pediatric ARV Prescription form</a></h1>
<form id="pediatricForm" action="#">
<fieldset>
<table   id="patientSection">
    <tr class="display">
        <td>Patient ID</td><td><input name="patientId" id="patientId" class="required"  style="width:100px;"/></td>
        <td colspan="2">Patient name</td><td><input type="text" name="patientName" id="patientName" style="width:200px;"  readonly/></td>
        <td>Current Regimen</td><td><input type="text" name="currentRegimen" id="currentRegimen" style="width:150px;"  readonly/></td>
    </tr>
</table>
<table id="dataSection">
<tr class="display">
    <td>Encounter date</td><td><input id="datePicker" name="Enc4*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
    <td>No of days </td><td><input id="noofmonths" name="Enc6*noOfMonths|6#6" type="text"  value="" style="width:100px;"/></td>
    <td> Next visit date</td><td><input id="nextvisit" name="Enc5*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
</tr>
<tr class="display">
    <td>Weight</td><td><input id="weight" name="weight" type="text"  value="" style="width:100px;"/></td></td>
</tr>
<tr class="display">
    <td>Patient type(Compulsory) </td>
    <td> <input type="radio" id="patienttype1" name="Obs*PATIENT TYPE|1724#10" value="PMTCT CHILD BORN|1360" > PMTCT</td>
    <td><input type="radio" id="patienttype2" name="Obs*PATIENT TYPE|1724#10" value="ROUTINE|432" > ROUTINE </td>
    <td colspan="2"><input type="radio" id="patienttype3" name="Obs*PATIENT TYPE|1724#10" value="POST EXPOSURE PROPHYLAXIS|2090"> POST EXPOSURE PROPHYLAXI</td>
</tr>
<tr class="display">
    <td>Regimen Line </td>
    <td colspan="2"> <input type="radio" name="regimen_line" value="Obs*Check|1705#10" > 1ST LINE ARV REGIMEN REFILL</td>
    <td colspan="2"><input type="radio" name="regimen_line" value="Obs*Check|1705#10" >2ND LINE ARV REGIMEN REFILL </td>
</tr>
<tr class="display">
    <td>ARV types </td>
    <td> <input type="radio" id="arvtype1" name="Obs*CHECK|1705#10" value="INITIATION|1703" >INITIATION OF ARVs</td>
    <td><input type="radio" id="arvtype2" name="Obs*CHECK|1705#10" value="In patient|7625" >In patient refill</td>
    <td><input type="radio" id="arvtype3" name="Obs*CHECK|1705#10" value="OI DRUG REFILL|7626">OI  REFILL</td>
    <td colspan="2"><input type="radio" id="arvtype4" name="Obs*CHECK|1705#10" value="Initiation of OI prophylaxis|7627">Initiation of OI prophylaxis</td>
    <td><input type="radio" id="arvtype5" name="Obs*CHECK|1705#10" value="CONTINUE REGIMEN|1257">CONTINUE REGIMEN</td>
</tr>
<tr class="display">
    <td>Regimen change</td><td><input type="radio" id="regimenchange" name="regimenchange" value="OREGIMEN FAILURE (TREATMENT FAILURE)|843" />Regimen Change</td>
    <td>Reason for regimen Change</td>
    <td><input type="radio" id="regimenchange1" name="Obs*CHANGE REGIMEN|1252#10" value="SIDE EFFECTS FROM TAKING MEDICATIONS|1664" >SIDE EFFECTS</td>
    <td><input type="radio" id="regimenchange2" name="Obs*CHANGE REGIMEN|1252#10" value="REGIMEN FAILURE (TREATMENT FAILURE)|843" >REGIMEN FAILURE (TREATMENT FAILURE)</td>
    <td><input type="radio" id="regimenchange3" name="Obs*CHANGE REGIMEN|1252#10" value="PREGNANCY|44" >PREGNANCY</td>
</tr>
<tr>
    <th>Medication</th><th>Weight Range</th><th>Form</th><th>Dose</th><th style="width:25%;">Quantity Prescribed</th><th>Other</th><th>Pill Count</th><th>Quantity Dispensed</th>
</tr>
<tr class="hideRow" id="drug1row1">
    <td>Lamivudine (3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="first_drug_first_line" title="drug" value="628|137" />Syrup</td>
    <td>3ml BD<input type="hidden" value="3ml BD" /></td>
    <td style="width: 200px"><input type="radio" name="first_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="first_first_line_requested" title="Requested" value="2" />2
        <input  type="radio" name="first_first_line_requested" title="Requested" value="1" /> 3
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row2">
    <td>Lamivudine (3TC)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="first_drug_second_line" title="drug" value="628|137" />Syrup</td>
    <td>4ml BD<input type="hidden" name="dose" value="4ml BD" /></td>
    <td><input type="radio" name="first_second_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="first_second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="first_second_line_requested" title="Requested" value="1" /> 3
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row3">
    <td>Lamivudine (3TC)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="first_drug_third_line" title="drug" value="628|137" />Syrup</td>
    <td>6ml BD<input type="hidden" name="dose" value="6ml BD" /></td>
    <td><input type="radio" name="first_third_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="first_third_line_requested" title="Requested" value="3" />3
        <input type="radio" name="first_third_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row4">
    <td>Lamivudine (3TC)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="first_drug_fourth_line" title="drug" value="628|137"/>Syrup</br>
        <input type="radio"  name="first_drug_fourth_line" title="drug" value="628|23"/>150mg tab
    </td>
    <td><input type="radio" value="7ml BD" name="first_fourth_line_dose"/>7ml BD</br>
        <input type="radio" value="1/2 tab BD" name="first_fourth_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="first_fourth_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="first_fourth_line_requested" title="Requested" value="4" />4
        <input type="radio" name="first_fourth_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="first_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="first_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="first_fourth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row5">
    <td>Lamivudine (3TC)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="first_drug_fifth_line" title="drug" value="628|137"/>Syrup</br>
        <input type="radio"  name="first_drug_fifth_line" title="drug" value="628|23"/>150mg tab
    </td>
    <td><input type="radio" value="9ml BD" name="first_fifth_line_dose"/>9ml BD</br>
        <input type="radio" value="1 tab am 1/2 tab pm" name="first_fifth_line_dose"/>1 tab am 1/2 tab pm
    </td>
    <td><input type="radio" name="first_fifth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="first_fifth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="first_fifth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="first_fifth_line_requested" title="Requested" value="45" />45
        <input type="radio" name="first_fifth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="first_fifth_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug1row6">
    <td>Lamivudine (3TC)</td>
    <td>>25kg</td>
    <td><input type="radio" name="first_drug_sixth_line" title="drug" value="628|23" />150mg tab</td>
    <td>1 tab BD<input type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="first_sixth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="first_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="first_sixth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--second drug begins-->

<tr class="hideRow" id="drug2row1">
    <td>Abacavir (ABC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="second_drug_first_line" title="drug" value="814|138" />Syrup</td>
    <td>3ml BD<input type="hidden" value="3ml BD" /></td>
    <td><input type="radio" name="second_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="second_first_line_requested" title="Requested" value="1" />1
        <input type="radio" name="second_first_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row2">
    <td>Abacavir</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="second_drug_second_line" title="drug" value="814|138" />Syrup</td>
    <td>4ml BD<input type="hidden" value="4ml BD" /></td>
    <td><input type="radio" name="second_second_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="second_second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="second_second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row3">
    <td>Abacavir (ABC)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="second_drug_third_line" title="drug" value="814|138" />Syrup</td>
    <td>6ml BD<input type="hidden" value="6ml BD" /></td>
    <td><input type="radio" name="second_third_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="second_third_line_requested" title="Requested" value="3" />3
        <input type="radio" name="second_third_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row4">
    <td>Abacavir (ABC)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="second_drug_fourth_line" title="drug" value="814|138"/>Syrup</br>
        <input type="radio"  name="second_drug_fourth_line" title="drug" value="814|28"/>300mg tab
    </td>
    <td><input type="radio" value="7ml BD" name="second_fourth_line_dose"/>7ml BD</br>
        <input type="radio" value="1/2 tab BD" name="second_fourth_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="second_fourth_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="second_fourth_line_requested" title="Requested" value="4" />4
        <input type="radio" name="second_fourth_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="second_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="second_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="second_fourth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row5">
    <td>Abacavir (ABC)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="second_drug_fifth_line" title="drug" value="814|138"/>Syrup</br>
        <input type="radio"  name="second_drug_fifth_line" title="drug" value="814|28"/>300mg tab
    </td>
    <td><input type="radio" value="9ml BD" name="second_fifth_line_dose"/>9ml BD</br>
        <input type="radio" value="1 tab am 1/2 tab pm" name="second_fifth_line_dose"/>1 tab am 1/2 tab pm
    </td>
    <td><input type="radio" name="second_fifth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="second_fifth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="second_fifth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="second_fifth_line_requested" title="Requested" value="45" />45
        <input type="radio" name="second_fifth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="second_fifth_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug2row6">
    <td>Abacavir (ABC)</td>
    <td>>25kg</td>
    <td><input type="radio" name="second_drug_sixth_line" title="drug" value="814|28" />300mg tab</td>
    <td>1 tab BD<input type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="second_sixth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="second_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="second_sixth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--third drug begins-->
<tr class="hideRow" id="drug3row1">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="third_drug_first_line" title="drug" value="797|139" />Syrup</td>
    <td>3ml BD<input type="hidden" value="3ml BD" /></td>
    <td><input type="radio" name="third_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="third_first_line_requested" title="Requested" value="1" />1
        <input type="radio" name="third_first_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row2">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="third_drug_second_line" title="drug" value="797|139" />Syrup</td>
    <td>9ml BD<input type="hidden" value="9ml BD" /></td>
    <td><input type="radio" name="third_second_line_requested" title="Requested" value="3" /> 3
        <input type="radio" name="third_second_line_requested" title="Requested" value="6" />6
        <input type="radio" name="third_second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row3">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="third_drug_third_line" title="drug" value="797|139" />Syrup</td>
    <td>6ml BD<input type="hidden" value="12ml BD" /></td>
    <td><input type="radio" name="third_third_line_requested" title="Requested" value="3" />3
        <input type="radio" name="third_third_line_requested" title="Requested" value="6" />6
        <input type="radio" name="third_third_line_requested" title="Requested" value="2" />2
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row4">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="third_drug_fourth_line" title="drug" value="797|139"/>Syrup</br>
        <input type="radio"  name="third_drug_fourth_line" title="drug" value="797|78"/>300mg tab
    </td>
    <td><input type="radio" value="15ml BD" name="third_fourth_line_dose"/>15ml BD</br>
        <input type="radio" value="1/2 tab BD" name="third_fourth_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="third_fourth_line_requested" title="Requested" value="5" /> 5
        <input type="radio" name="third_fourth_line_requested" title="Requested" value="10" />10
        <input type="radio" name="third_fourth_line_requested" title="Requested" value="3" /> 3 </br>
        <input type="radio" name="third_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="third_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="third_fourth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row5">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="third_drug_fifth_line" title="drug" value="797|78"/>300mg tab</td>
    <td><input type="radio" value="1 tab am 1/2 tab pm" name="third_fifth_line_dose"/>1 tab am 1/2 tab pm</td>
    <td>
        <input type="radio" name="third_fifth_line_requested" title="Requested" value="45" />45
        <input type="radio" name="third_fifth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="third_fifth_line_requested" title="Requested" value="25" />25
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug3row6">
    <td>Zidovudine (ZDV or AZT)</td>
    <td>>25kg</td>
    <td><input type="radio" name="third_drug_sixth_line" title="drug" value="797|78" />300mg tab</td>
    <td>1 tab BD<input type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="third_sixth_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="third_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="third_sixth_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fourth drug begins-->
<tr class="hideRow" id="drug4row1">
    <td>ZDV for PMTC</td>
    <td><input type="text" name="patient_weight" value="" style="width: 50px"></td>
    <td><input type="radio" name="fourth_drug_sixth_line" title="drug" value="631|140" />Syrup</td>
    <td><input type="radio" name="fourth_sixth_line_requested" title="Requested" value="10mg/ml" /> 10mg/ml</td>
    <td>200 ml bottle for 42 days<input type="hidden" value="200 ml bottle for 42 days" /></td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fifth drug begins-->
<tr class="hideRow" id="drug5row1">
    <td>Nevirapine (NVP)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="fifth_drug_first_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_first_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="2ml OD" name="fifth_first_line_dose"/>2ml OD</br>
        <input type="radio" value="1/2 tab BD" name="fifth_first_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="fifth_first_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="fifth_first_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row2">
    <td>Nevirapine (NVP)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="fifth_drug_second_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_second_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="4ml OD" name="fifth_second_line_dose"/>4ml OD</br>
        <input type="radio" value="1 tab BD" name="fifth_second_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="fifth_second_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="fifth_second_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row3">
    <td>Nevirapine (NVP)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="fifth_drug_third_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="fifth_drug_third_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="5ml OD" name="fifth_third_line_dose"/>5ml OD</br>
        <input type="radio" value="1 tab BD" name="fifth_third_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="fifth_third_line_requested" title="Requested" value="1" /> 1 </br>
        <input type="radio" name="fifth_third_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row4">
    <td>Nevirapine (NVP)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="fifth_drug_fourth_line" title="drug" value="631|140"/>Syrup</br>
        <input type="radio"  name="fifth_drug_fourth_line" title="drug" value="631|22"/>50mg tab
    </td>
    <td><input type="radio" value="6ml BD" name="fifth_fourth_line_dose"/>6ml BD</br>
        <input type="radio" value="11/2 tab BD" name="fifth_fourth_line_dose"/>11/2 tab BD
    </td>
    <td><input type="radio" name="fifth_fourth_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="fifth_fourth_line_requested" title="Requested" value="22" />22
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row5">
    <td>Nevirapine (NVP)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="fifth_drug_fifth_line" title="drug" value="631|22"/>50mg tab</td>
    <td><input type="radio" value="2 tab OD" name="fifth_fifth_line_dose"/>2 tab OD</td>
    <td><input type="radio" name="fifth_fifth_line_requested" title="Requested" value="30" />30</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug5row6">
    <td>Nevirapine (NVP)</td>
    <td>>25kg</td>
    <td><input type="radio" name="fifth_drug_sixth_line" title="drug" value="631|22" />200mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 tab OD" /></td>
    <td><input type="radio" name="fifth_sixth_line_requested" title="Requested" value="15" />15</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>

<!--sixth drug begins-->
<tr class="hideRow" id="drug6row1">
    <td>Nevirapine (NVP)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="sixth_drug_first_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_first_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="2ml OD" name="sixth_first_line_dose"/>2ml OD</br>
        <input type="radio" value="1/2 tab BD" name="sixth_first_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="sixth_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="sixth_first_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="sixth_first_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row2">
    <td>Nevirapine (NVP)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="sixth_drug_second_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_second_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="4ml OD" name="sixth_second_line_dose"/>4ml OD</br>
        <input type="radio" value="1 tab BD" name="sixth_second_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="sixth_second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="sixth_second_line_requested" title="Requested" value="4" /> 4
        <input type="radio" name="sixth_second_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_second_line_requested" title="Requested" value="90" />90
        <input type="radio" name="sixth_second_line_requested" title="Requested" value="180" />180
        <input type="radio" name="sixth_second_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row3">
    <td>Nevirapine (NVP)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="sixth_drug_third_line" title="drug" value="631|140" />Syrup</br>
        <input type="radio" name="sixth_drug_third_line" title="drug" value="631|22" />50mg
    </td>
    <td><input type="radio" value="5ml OD" name="sixth_third_line_dose"/>5ml OD</br>
        <input type="radio" value="1 tab BD" name="sixth_third_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="sixth_third_line_requested" title="Requested" value="3" />1
        <input type="radio" name="sixth_third_line_requested" title="Requested" value="5" />5
        <input type="radio" name="sixth_third_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_third_line_requested" title="Requested" value="150" />150
        <input type="radio" name="sixth_third_line_requested" title="Requested" value="300" />300
        <input type="radio" name="sixth_third_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row4">
    <td>Nevirapine (NVP)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="sixth_drug_fourth_line" title="drug" value="631|140"/>Syrup</br>
        <input type="radio"  name="sixth_drug_fourth_line" title="drug" value="631|22"/>50mg tab
    </td>
    <td><input type="radio" value="6ml BD" name="sixth_fourth_line_dose"/>6ml BD</br>
        <input type="radio" value="11/2 tab BD" name="sixth_fourth_line_dose"/>11/2 tab BD
    </td>
    <td><input type="radio" name="sixth_fourth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="sixth_fourth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="sixth_fourth_line_requested" title="Requested" value="1" /> 1</br>
        <input type="radio" name="sixth_fourth_line_requested" title="Requested" value="150" />150
        <input type="radio" name="sixth_fourth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="sixth_fourth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row5">
    <td>Nevirapine (NVP)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="sixth_drug_fifth_line" title="drug" value="631|22"/>50mg tab</td>
    <td><input type="radio" value="2 tab OD" name="sixth_fifth_line_dose"/>2 tab OD</td>
    <td><input type="radio" name="sixth_fifth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="sixth_fifth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="sixth_fifth_line_requested" title="Requested" value="45" />45</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug6row6">
    <td>Nevirapine (NVP)</td>
    <td>>25kg</td>
    <td><input type="radio" name="sixth_drug_sixth_line" title="drug" value="631|22" />200mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 tab OD" /></td>
    <td><input type="radio" name="sixth_sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixth_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixth_sixth_line_requested" title="Requested" value="2" />2</td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--seventh drug begins-->

<tr class="hideRow" id="drug7row1">
    <td>Efavirenz (EFV)</td>
    <td><5kg</td>
    <td><input type="radio" name="seventh_drug_first_line" title="drug" value="633|142" />50mg cap</td>
    <td>1 cap OD<input type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_first_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_first_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row2">
    <td>Efavirenz (EFV)</td>
    <td>5-9.9kg</td>
    <td><input type="radio" name="second_drug_second_line" title="drug" value="633|143" />200mg cap</td>
    <td>1 cap OD<input type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_second_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_second_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row3">
    <td>Efavirenz (EFV)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="seventh_drug_third_line" title="drug" value="633|143" />200mg cap</td>
    <td>1 cap OD<input type="hidden" value="1 cap OD" /></td>
    <td><input type="radio" name="seventh_third_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_third_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row4">
    <td>Efavirenz (EFV)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="seventh_drug_fourth_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_fourth_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="50mg OD" name="second_fourth_line_dose"/>50mg OD</br>
        <input type="radio" value="200mg OD" name="second_fourth_line_dose"/>200 mg OD
    </td>
    <td><input type="radio" name="seventh_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_fourth_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_fourth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row5">
    <td>Efavirenz (EFV)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="seventh_drug_fifth_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_fifth_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="100mg OD" name="seventh_fifth_line_dose"/>100mg OD</br>
        <input type="radio" value="200mg OD" name="seventh_fifth_line_dose"/>200mg OD
    </td>
    <td><input type="radio" name="seventh_fifth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_fifth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_fifth_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_fifth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_fifth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventh_fifth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row6">
    <td>Efavirenz (EFV)</td>
    <td>25-29.9kg</td>
    <td><input type="radio"  name="seventh_drug_sixth_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="seventh_drug_sixth_line" title="drug" value="633|143"/>200mg tab
    </td>
    <td><input type="radio" value="100mg OD" name="seventh_sixth_line_dose"/>100mg OD</br>
        <input type="radio" value="200mg OD" name="seventh_sixth_line_dose"/>200mg OD
    </td>
    <td><input type="radio" name="seventh_sixth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_sixth_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="seventh_sixth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="seventh_sixth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="seventh_sixth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row7">
    <td>Efavirenz (EFV)</td>
    <td>30-39.9kg</td>
    <td><input type="radio"  name="seventh_drug_seventh_line" title="drug" value="633|143"/>200mg tab</td>
    <td><input type="radio" value="2 caps OD" name="seventh_seventh_line_dose"/>2 caps OD</td>
    <td><input type="radio" name="seventh_seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_seventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventh_seventh_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug7row8">
    <td>Efavirenz (EFV)</td>
    <td> >40kg</td>
    <td><input type="radio"  name="seventh_drug_eighth_line" title="drug" value="633|143"/>600mg tab</td>
    <td><input type="radio" value="1 tab OD" name="seventh_eighth_line_dose"/>1 tab OD</td>
    <td><input type="radio" name="seventh_eighth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_eighth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventh_eighth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eighth drug begins-->
<tr class="hideRow" id="drug8row1">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="eighth_drug_first_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>1tab BD<input type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="eighth_first_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="eighth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eighth_first_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row2">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="eighth_drug_second_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>1.5 tab BD<input type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="eighth_second_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="eighth_second_line_requested" title="Requested" value="180" />180
        <input type="radio" name="eighth_second_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row3">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="eighth_drug_third_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>2 tabs BD<input type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="eighth_third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eighth_third_line_requested" title="Requested" value="240" />240
        <input type="radio" name="eighth_third_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row4">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="eighth_drug_fourth_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>2.5 tabs BD<input type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="eighth_fourth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="eighth_fourth_line_requested" title="Requested" value="60" />300
        <input type="radio" name="eighth_fourth_line_requested" title="Requested" value="15" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug8row5">
    <td>Zidovudine/Lamivudine(ZDV/3TC)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="eighth_drug_fifth_line" title="drug" value="630|157" />60/30mg tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="eighth_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="eighth_fifth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="eighth_fifth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="eighth_fifth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--ninth drug begins-->

<tr class="hideRow" id="drug9row1">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="ninth_drug_first_line" title="drug" value="6679|158" />combo tab</td>
    <td>1tab BD<input type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="ninth_first_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="ninth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninth_first_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row2">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="ninth_drug_second_line" title="drug" value="6679|158" />combo tab</td>
    <td>1.5 tab BD<input type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="ninth_second_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="ninth_second_line_requested" title="Requested" value="180" />180
        <input type="radio" name="ninth_second_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row3">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="ninth_drug_third_line" title="drug" value="6679|158" />combo tab</td>
    <td>2 tabs BD<input type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="ninth_third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninth_third_line_requested" title="Requested" value="240" />240
        <input type="radio" name="ninth_third_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row4">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="ninth_drug_fourth_line" title="drug" value="6679|158" />combo tab</td>
    <td>2.5 tabs BD<input type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="ninth_fourth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="ninth_fourth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="ninth_fourth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug9row5">
    <td>Abacavir/Lamivudine(ABC/3TC)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="tenth_drug_fifth_line" title="drug" value="6679|158" />combo tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="tenth_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--tenth drug begins-->

<tr class="hideRow" id="drug10row1">
    <td>ZDV/3TC/NVP</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="tenth_drug_first_line" title="drug" value="6467|159" />combo tab</td>
    <td>1tab BD<input type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="tenth_first_line_requested" title="Requested" value="60" /> 60
        <input type="radio" name="tenth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="tenth_first_line_requested" title="Requested" value="30" /> 30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row2">
    <td>ZDV/3TC/NVP</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="tenth_drug_second_line" title="drug" value="6467|159" />combo tab</td>
    <td>1.5 tab BD<input type="hidden" value="1.5 tab BD" /></td>
    <td><input type="radio" name="tenth_second_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="tenth_second_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_second_line_requested" title="Requested" value="45" /> 45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row3">
    <td>ZDV/3TC/NVP</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="tenth_drug_third_line" title="drug" value="6467|159" />combo tab</td>
    <td>2 tabs BD<input type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="tenth_third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="tenth_third_line_requested" title="Requested" value="240" />240
        <input type="radio" name="tenth_third_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row4">
    <td>ZDV/3TC/NVP</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="tenth_drug_fourth_line" title="drug" value="6467|159" />combo tab</td>
    <td>2.5 tabs BD<input type="hidden" value="2.5 tabs BD" /></td>
    <td>
        <input type="radio" name="tenth_fourth_line_requested" title="Requested" value="30" />150
        <input type="radio" name="tenth_fourth_line_requested" title="Requested" value="300" />300
        <input type="radio" name="tenth_fourth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug10row5">
    <td>ZDV/3TC/NVP</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="tenth_drug_fifth_line" title="drug" value="6467|159" />combo tab</td>
    <td>3 tabs BD<input type="hidden" value="3 tabs BD" name="eighth_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="360" />360
        <input type="radio" name="tenth_fifth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eleventh drug begins-->
<tr class="hideRow" id="drug11row1">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="eleventh_drug_first_line" title="drug" value="792|160" />combo tab</td>
    <td>1tab BD<input type="hidden" value="1tab BD" /></td>
    <td><input type="radio" name="eleventh_first_line_requested" title="Requested" value="30" />30
        <input type="radio" name="eleventh_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eleventh_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row2">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="eleventh_drug_second_line" title="drug" value="792|160" />combo tab</td>
    <td>1tab am 0.5 tab pm<input type="hidden" value="1tab am 0.5 tab pm" /></td>
    <td><input type="radio" name="eleventh_second_line_requested" title="Requested" value="45" />45
        <input type="radio" name="eleventh_second_line_requested" title="Requested" value="90" />90
        <input type="radio" name="eleventh_second_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row3">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="eleventh_drug_third_line" title="drug" value="792|160" />combo tab</td>
    <td>1 tab BD<input type="hidden" value="1 tab BD" /></td>
    <td><input type="radio" name="eleventh_third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eleventh_third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eleventh_third_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row4">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="eleventh_drug_fourth_line" title="drug" value="792|160" />combo tab</td>
    <td>1.5 tab am 1 tab pm<input type="hidden" value="1.5 tab am 1 tab pm" /></td>
    <td>
        <input type="radio" name="eleventh_fourth_line_requested" title="Requested" value="75" />75
        <input type="radio" name="eleventh_fourth_line_requested" title="Requested" value="150" />150
        <input type="radio" name="eleventh_fourth_line_requested" title="Requested" value="75" />75
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug11row5">
    <td>D4T/3TC/NVP(peds Triomune Jr)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="eleventh_drug_fifth_line" title="drug" value="792|160" />combo tab</td>
    <td>1.5 tab BD<input type="hidden" value="1.5 tab BD" name="eighth_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="eleventh_fifth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eleventh_fifth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="eleventh_fifth_line_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twelfth drug begins-->
<tr class="hideRow" id="drug12row1">
    <td>Didanosine (DDI)</td>
    <td><5-9.9kg</td>
    <td><input type="radio" name="twelfth_drug_first_line" title="drug" value="796|149" />25mg tab</td>
    <td>50mg tab BD<input type="hidden" value="50mg tab BD" /></td>
    <td><input type="radio" name="twelfth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row2">
    <td>Didanosine(DDI)</td>
    <td>10-10.9kg</td>
    <td><input type="radio" name="twelfth_drug_second_line" title="drug" value="796|149" />200mg cap</td>
    <td>50mg tab BD<input type="hidden" value="50mg tab BD" /></td>
    <td><input type="radio" name="twelfth_second_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_second_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_second_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row3">
    <td>Didanosine (DDI)</td>
    <td>11-13.9kg</td>
    <td><input type="radio"  name="twelfth_drug_third_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="twelfth_drug_third_line" title="drug" value="633|143"/>25mg tab
    </td>
    <td><input type="radio" value="50mg BD" name="twelfth_third_line_dose"/>50mg BD</br>
        <input type="radio" value="25mg BD" name="twelfth_third_line_dose"/>25mg BD
    </td>
    <td><input type="radio" name="twelfth_third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_third_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_third_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row4">
    <td>Didanosine (DDI)</td>
    <td>14-16.9kg</td>
    <td><input type="radio"  name="twelfth_drug_fourth_line" title="drug" value="633|142"/>50mg tab</br>
        <input type="radio"  name="twelfth_drug_fourth_line" title="drug" value="633|143"/>25mg tab
    </td>
    <td><input type="radio" value="50mg BD" name="twelfth_fourth_line_dose"/>50mg BD</br>
        <input type="radio" value="25mg BD" name="twelfth_fourth_line_dose"/>25mg BD
    </td>
    <td><input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="15" />15 </br>
        <input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="twelfth_fourth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row5">
    <td>Didanosine (DDI)</td>
    <td>17-19.9kg</td>
    <td><input type="radio"  name="twelfth_drug_fifth_line" title="drug" value="633|142"/>50mg tab</td>
    <td>100mg (2tabs) BD<input type="radio" value="100mg (2tabs) BD" name="seventh_fifth_line_dose"/></td>
    <td>
        <input type="radio" name="twelfth_fifth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_fifth_line_requested" title="Requested" value="240" />240
        <input type="radio" name="twelfth_fifth_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row6">
    <td>Didanosine (DDI)</td>
    <td>20-60kg</td>
    <td><input type="radio"  name="twelfth_drug_sixth_line" title="drug" value="633|142"/>100mg tab</br>
        <input type="radio"  name="twelfth_drug_sixth_line" title="drug" value="633|143"/>25mg tab</br>
        <input type="radio"  name="twelfth_drug_sixth_line" title="drug" value="633|143"/>250mg EC cap
    </td>
    <td><input type="radio" value="100mg BD" name="seventh_sixth_line_dose"/>100mg BD</br>
        <input type="radio" value="25mg BD" name="seventh_sixth_line_dose"/>25mg BD</br>
        <input type="radio" value="1 tab OD" name="seventh_sixth_line_dose"/>1 tab OD
    </td>
    <td><input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_sixth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug12row7">
    <td>Didanosine (DDI)</td>
    <td>>60</td>
    <td><input type="radio"  name="twelfth_drug_seventh_line" title="drug" value="633|143"/>200mg tab </br>
        <input type="radio"  name="twelfth_drug_seventh_line" title="drug" value="633|143"/>400mg EC cap
    </td>
    <td>
        <input type="radio" value="1 tab BD" name="seventh_seventh_line_dose"/>1 tab BD</br>
        <input type="radio" value="400mg EC cap OD" name="seventh_seventh_line_dose"/>400mg EC cap OD
    </td>
    <td><input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="30" />30</br>
        <input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twelfth_seventh_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--thirteenth drug begins-->
<tr class="hideRow" id="drug13row1">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>3-5.9kg</td>
    <td><input type="radio" name="thirteenth_drug_first_line" title="drug" value="794|151" />Syrup</td>
    <td>1ml BD<input type="hidden" value="1ml BD" /></td>
    <td><input type="radio" name="thirteenth_first_line_requested" title="Requested" value="2" /> 2
        <input type="radio" name="thirteenth_first_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_first_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow"  id="drug13row2">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>6-9.9kg</td>
    <td><input type="radio" name="thirteenth_drug_second_line" title="drug" value="794|151" />Syrup</td>
    <td>1.5ml BD<input type="hidden" value="1.5ml BD" /></td>
    <td><input type="radio" name="thirteenth_second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="thirteenth_second_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_second_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row3">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>10-13.9kg</td>
    <td><input type="radio" name="thirteenth_drug_third_line" title="drug" value="794|151" />Syrup</td>
    <td>2ml BD<input type="hidden" value="2ml BD" /></td>
    <td><input type="radio" name="thirteenth_third_line_requested" title="Requested" value="2" />2
        <input type="radio" name="thirteenth_third_line_requested" title="Requested" value="4" />4
        <input type="radio" name="thirteenth_third_line_requested" title="Requested" value="1" /> 1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row4">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>14-19.9kg</td>
    <td><input type="radio"  name="thirteenth_drug_fourth_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_fourth_line" title="drug" value="794|150"/>200mg
    </td>
    <td><input type="radio" value="2.5ml BD" name="second_fourth_line_dose"/>2.5ml BD</br>
        <input type="radio" value="1 tab BD" name="second_fourth_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="5" />5
        <input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="2" />2 </br>
        <input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="thirteenth_fourth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row5">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>20-24.9kg</td>
    <td><input type="radio"  name="thirteenth_drug_fifth_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_fifth_line" title="drug" value="794|150"/>200mg tab
    </td>
    <td><input type="radio" value="3ml BD" name="thirteenth_fifth_line_dose"/>3ml BD</br>
        <input type="radio" value="1 tab BD" name="thirteenth_fifth_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="thirteenth_fifth_line_requested" title="Requested" value="3" />3
        <input type="radio" name="thirteenth_fifth_line_requested" title="Requested" value="6" />6
        <input type="radio" name="thirteenth_fifth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="thirteenth_dfifth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="thirteenth_fifth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="thirteenth_fifth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row6">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>25-29.9</td>
    <td><input type="radio"  name="thirteenth_drug_sixth_line" title="drug" value="794|151"/>Syrup</br>
        <input type="radio"  name="thirteenth_drug_sixth_line" title="drug" value="794|150"/>200mg tab
    </td>
    <td><input type="radio" value="3.5ml BD" name="thirteenth_sixth_line_dose"/>3.5ml BD</br>
        <input type="radio" value="2 tabs am 1 tab pm" name="thirteenth_sixth_line_dose"/>2 tabs am 1 tab pm
    </td>
    <td><input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="4" />4
        <input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="7" />7
        <input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="2" /> 2 </br>
        <input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="90" /> 90
        <input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="180" />180
        <input type="radio" name="thirteenth_sixth_line_requested" title="Requested" value="45" />45
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug13row7">
    <td>Lopinavir/rit (LPN/r)</td>
    <td>>=30</td>
    <td><input type="radio" name="thirteenth_drug_seventh_line" title="drug" value="794|150" />200mg tab</td>
    <td>2 tabs BD<input type="hidden" value="2 tabs BD" /></td>
    <td><input type="radio" name="thirteenth_seventh_line_requested" title="Requested" value="120" /> 120
        <input type="radio" name="thirteenth_seventh_line_requested" title="Requested" value="240" />240
        <input type="radio" name="thirteenth_seventh_line_requested" title="Requested" value="60" /> 60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fourteenth drug begins-->

<tr class="hideRow" id="drug14row1">
    <td>TDF/3TC(Tenofovir/Lamivudine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="fourteenth_drug_first_line" title="drug" value="1400|30" />300mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="fourteenth_first_line_requested" title="Requested" value="30" />30
        <input type="radio" name="fourteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="fourteenth_first_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--fifteenth drug begins-->

<tr class="hideRow" id="drug15row1">
    <td>TDF/3TC/EFV<br>(Tenofovir/Lamivudine/Efavirenz)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="fifteenth_drug_first_line" title="drug" value="6964|38" />combo tab</td>
    <td>1 tab OD(Nocte)<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="fifteenth_first_line_requested" title="Requested" value="30" />30
        <input type="radio" name="fifteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="fifteenth_first_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--sixteenth drug begins-->

<tr class="hideRow" id="drug16row1">
    <td>ZDV/3TC(Zidovudine/Lamivudine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="sixteenth_drug_first_line" title="drug" value="630|26" />combo tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="sixteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="sixteenth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="sixteenth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--seventeenth drug begins-->

<tr class="hideRow" id="drug17row1">
    <td>ZDV/3TC/NVP<br>(Zidovudine/Lamivudine/Nevirapine)</td>
    <td>>=25kg</td>
    <td><input type="radio" name="seventeenth_drug_first_line" title="drug" value="6467|18" />combo tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="seveteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="seventeenth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="seventeenth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--eiteenth drug begins-->

<tr class="hideRow" id="drug18row1">
    <td>D4T/3TC/NVP<br>(Stavudine/Lamivudine/Nevirapine) Triomune 30</td>
    <td>>=25kg</td>
    <td><input type="radio" name="eigteenth_drug_first_line" title="drug" value="792|21" />combo tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="eigteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="eigteenth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="eigteenth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--ninteenth drug begins-->

<tr class="hideRow" id="drug19row1">
    <td>D4T/3TC<br>(Stavudine/Lamivudine)30mg/150mg</td>
    <td>>=25kg</td>
    <td><input type="radio" name="ninteenth_drug_first_line" title="drug" value="6965|39" />30/150mg tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="ninteenth_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="niteenth_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="ninteenth_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentythird drug begins-->

<tr class="hideRow" id="drug23row1">
    <td>Ritonovir</td>
    <td>>10kg</td>
    <td><input type="radio" name="twentyThird_drug_first_line" title="drug" value="795|130" />100mg tab</td>
    <td>1 tab BD<input type="hidden" value="1 Tab BD" /></td>
    <td><input type="radio" name="twentyThird_first_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentyThird_first_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twentyThird_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr>
    <td colspan=3><h3>OI PROPHYLAXIS</h3></td>
</tr>
<!--Twentienth drug begins-->

<tr class="hideRow" id="drug20row1">
    <td>Co-trimaxazole</td>
    <td><5kg</td>
    <td><input type="radio" name="twentieth_drug_first_line" title="drug" value="916|110"/>Syrup
    <td>2.5ml BD<input type="hidden" value="2.5ml BD" /></td>
    <td><input type="radio" name="twentieth_first_line_requested" title="Requested" value="1" /> 1
        <input type="radio" name="twentieth_first_line_requested" title="Requested" value="2" />2
        <input type="radio" name="twentieth_first_line_requested" title="Requested" value="1" />1
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row2">
    <td>Co-trimaxazole</td>
    <td>5-9kg</td>
    <td><input type="radio" name="twentieth_drug_second_line" title="drug" value="916|110" />Syrup</br>
        <input type="radio" name="twentieth_drug_second_line" title="drug" value="916|65" />SS Tab
    </td>
    <td><input type="radio" value="5ml BD" name="twentieth_econd_line_dose"/>5ml BD</br>
        <input type="radio" value="1/2 tab BD" name="twentieth_second_line_dose"/>1/2 tab BD
    </td>
    <td><input type="radio" name="twentieth_second_line_requested" title="Requested" value="2" />2
        <input type="radio" name="twentieth_second_line_requested" title="Requested" value="3" />3
        <input type="radio" name="twentieth_second_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="twentieth_second_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentieth_second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_second_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row3">
    <td>Co-trimaxazole</td>
    <td>10-15kg</td>
    <td><input type="radio" name="twentieth_drug_third_line" title="drug" value="916|110" />Syrup</br>
        <input type="radio" name="twentieth_drug_third_line" title="drug" value="916|65" />SS  tab
    </td>
    <td><input type="radio" value="10ml BD" name="twentieth_third_line_dose"/>10ml BD</br>
        <input type="radio" value="1 tab BD" name="twentieth_third_line_dose"/>1 tab BD
    </td>
    <td><input type="radio" name="twentieth_third_line_requested" title="Requested" value="3" />3
        <input type="radio" name="twentieth_third_line_requested" title="Requested" value="6" />6
        <input type="radio" name="twentieth_third_line_requested" title="Requested" value="1" />1</br>
        <input type="radio" name="twentieth_third_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_third_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row4">
    <td>Co-trimaxazole</td>
    <td>15-30kg</td>
    <td><input type="radio"  name="twentieth_drug_fourth_line" title="drug" value="916|61"/>DS Tab</br>
        <input type="radio"  name="twentieth_drug_fourth_line" title="drug" value="916|65"/>SS Tab
    </td>
    <td><input type="radio" value="1/2 DS Tab OD" name="twentieth_fourth_line_dose"/>1/2 DS Tab OD</br>
        <input type="radio" value="1 SS tab OD" name="twentieth_fourth_line_dose"/>1 SS tab OD
    </td>
    <td><input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="8" />8</br>
        <input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_fourth_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug20row5">
    <td>Co-trimaxazole</td>
    <td>>30kg</td>
    <td><input type="radio"  name="twentieth_drug_fifth_line" title="drug" value="916|61"/>DS Tab</br>
        <input type="radio"  name="twentieth_drug_fifth_line" title="drug" value="916|65"/>SS Tab
    </td>
    <td><input type="radio" value="1 DS Tab OD" name="twentieth_fifth_line_dose"/>1 DS Tab OD</br>
        <input type="radio" value="2 SS tab OD" name="twentieth_fifth_line_dose"/>2 SS tab OD
    </td>
    <td><input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="15" />15</br>
        <input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="120" />120
        <input type="radio" name="twentieth_fifth_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentyfirst drug begins-->

<tr class="hideRow" id="drug21row1">
    <td>Dapsone</td>
    <td>8-14</td>
    <td><input type="radio" name="twentyfirst_drug_first_line" title="drug" value="92|62" />100mg tab</td>
    <td>1/2 tab once weekly<input type="hidden" value="1/2 tab once weekly" /></td>
    <td><input type="radio" name="twentyfirst_first_line_requested" title="Requested" value="5" />5
        <input type="radio" name="twentyfirst_first_line_requested" title="Requested" value="10" />10
        <input type="radio" name="twentyfirst_first_line_requested" title="Requested" value="2" />2
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug21row2">
    <td>Dapsone</td>
    <td>>15-30kg</td>
    <td><input type="radio" name="twentyfirst_drug_second_line" title="drug" value="92|62" />100mg tab</td>
    <td>1/2 tab OD<input type="hidden" value="1/2 Tab OD" /></td>
    <td><input type="radio" name="twentyfirst_second_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentyfirst_second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentyfirst_second_line_requested" title="Requested" value="8" />8
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug21row3">
    <td>Dapsone</td>
    <td>>30kg</td>
    <td><input type="radio" name="twentyfirst_drug_third_line" title="drug" value="92|62" />100mg tab</td>
    <td>1 tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="twentyfirst_third_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentyfirst_third_line_requested" title="Requested" value="60" />60
        <input type="radio" name="twentyfirst_third_line_requested" title="Requested" value="15" />15
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<!--twentysecond drug begins -->

<tr class="hideRow" id="drug22row1">
    <td>Isoniazid</td>
    <td>5-10</td>
    <td><input type="radio" name="twentysecond_drug_first_line" title="drug" value="656|59" />100mg tab</td>
    <td>1 Tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="twentysecond_first_line_requested" title="Requested" value="15" />15
        <input type="radio" name="twentysecond_first_line_requested" title="Requested" value="30" />30
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug22row2">
    <td>Isoniazid</td>
    <td>>10.1-30</td>
    <td><input type="radio" name="twentysecond_drug_second_line" title="drug" value="656|59" />100mg tab</td>
    <td>1/2 tab OD<input type="hidden" value="1/2 Tab OD" /></td>
    <td><input type="radio" name="twentysecond_second_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentysecond_second_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="hideRow" id="drug22row3">
    <td>Isoniazid</td>
    <td>>20kg</td>
    <td><input type="radio" name="twentysecond_drug_third_line" title="drug" value="656|59" />100mg tab </br>
        <input type="radio" name="twentysecond_drug_third_line" title="drug" value="656|60" />300mg tab
    </td>
    <td><input type="radio" value="1 Tab OD" name="twentieth_third_line_dose"/>1 Tab OD</br>
        <input type="radio" value="3 tab OD" name="twentieth_third_line_dose"/>3 tab OD
    </td>
    <td><input type="radio" name="twentysecond_third_line_requested" title="Requested" value="90" />90
        <input type="radio" name="twentysecond_third_line_requested" title="Requested" value="180" />180</br>
        <input type="radio" name="twentysecond_third_line_requested" title="Requested" value="30" />30
        <input type="radio" name="twentysecond_third_line_requested" title="Requested" value="60" />60
    </td>
    <td><input type="text" name="other" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="otherDrug" id="drug24row1">
    <td><input type="text" name="dispenseForm_medication" id=d_24  style="width:150px;"/></td>
    <td><input type="hidden" name="twentyfourth_drug_first_line" id=h_24 value="" /></td>
    <td></td>
    <td><select id="pediatricFormDose1"  name="dose"></select></td>
    <td><input type="text" name="twentyfourth_first_line_requested" title="Requested" value="" style="width:100px;"/></td>
    <td></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="otherDrug" id="drug25row1">
    <td><input type="text" name="dispenseForm_medication" id=d_25  style="width:150px;"/></td>
    <td><input type="hidden" name="twentyfifth_drug_first_line" id=h_25 value="" /></td>
    <td></td>
    <td><select id="pediatricFormDose2"  name="dose"></select></td>
    <td><input type="text" name="twentyfifth_first_line_requested" title="Requested" value="" style="width:100px;"/></td>
    <td></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="display">
    <td>Prescribed By</td><td><input type="text" id="prescriber" name="Prescriber" style="width:100px;" value=""/></td><td><input type="button" value="Dispense" onclick="processForm()"></td>
</tr>
</div>
</table>
</fieldset>
</form>
</div>
</body>
</html>