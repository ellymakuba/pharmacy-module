<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Adult HIV Prescription form</title>
    <style type="text/css">
        .adultHIVFormDivClass table tr td{
            border:1px solid white;
            background-color:#EBEBFF;
        }
    </style>
</head>
<body>
<DIV id="adultHIVFormDiv" class="adultHIVFormDivClass">
<h1><a>Adult HIV Prescription form</a></h1>
<form id="adultHIVForm" action="#">
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
    <td>Patient type(Compulsory) </td>
    <td> <input type="radio" id="patienttype1" name="Obs*PATIENT TYPE|1724#10" value="PMTCT CHILD BORN|1360" > PMTCT</td>
    <td><input type="radio" id="patienttype2" name="Obs*PATIENT TYPE|1724#10" value="ROUTINE|432" > ROUTINE </td>
    <td colspan="2"><input type="radio" id="patienttype3" name="Obs*PATIENT TYPE|1724#10" value="POST EXPOSURE PROPHYLAXIS|2090"> POST EXPOSURE PROPHYLAXI</td>
</tr>
<tr class="display">
    <td>ARV types </td>
    <td> <input type="checkbox" id="arvtype1" name="Obs*CHECK|1705#10" value="INITIATION|1703" >INITIATION OF ARVs</td>
    <td><input type="checkbox" id="arvtype2" name="Obs*CHECK|1705#10" value="In patient|7625" >ARV refill</td>
    <td><input type="checkbox" id="arvtype3" name="Obs*CHECK|1705#10" value="OI DRUG REFILL|7626">OI REFILL</td>
    <td colspan="2"><input type="checkbox" id="arvtype4" name="Obs*CHECK|1705#10" value="Initiation of OI prophylaxis|7627">Initiation of OI prophylaxis</td>
    <td><input type="checkbox" id="arvtype5" name="Obs*CHECK|1705#10" value="CONTINUE REGIMEN|1257">CONTINUE REGIMEN</td>
</tr>
<tr class="display">
    <td>Regimen change</td><td><input type="radio" id="regimenchange" name="regimenchange" value="OREGIMEN FAILURE (TREATMENT FAILURE)|843" />Regimen Change</td>
    <td>Reason for regimen Change</td>
    <td><input type="radio" id="regimenchange1" name="Obs*CHANGE REGIMEN|1252#10" value="SIDE EFFECTS FROM TAKING MEDICATIONS|1664" >SIDE EFFECTS</td>
    <td><input type="radio" id="regimenchange2" name="Obs*CHANGE REGIMEN|1252#10" value="REGIMEN FAILURE (TREATMENT FAILURE)|843" >REGIMEN FAILURE (TREATMENT FAILURE)</td>
    <td><input type="radio" id="regimenchange3" name="Obs*CHANGE REGIMEN|1252#10" value="PREGNANCY|44" >PREGNANCY</td>
</tr>
<tr>
    <th>Medication</th><th>Dose</th><th style="width:20%;">Quantity Prescribed</th><th>Other</th><th>Pill Count</th><th style="width:20%;">Quantity Dispensed</th><th>Other</th>
</tr>
<tr id="row1">
    <td><input type="radio" name="drug1" title="drug" value="6467|18"/>NVP200/3TC150/ZDV300</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="first_requested" title="Requested" value="60" /> 60
        <input type="radio" name="first_requested" title="Requested" value="120" />120
        <input  type="radio" name="first_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="first_dispensed" title="dispensed" value="60" /> 60
        <input type="radio" name="first_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="first_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr  id="row2">
    <td><input type="radio" name="drug2" title="drug" value="6964|38"/>TDF300mg/3TC300mg/EFV600mg</td>
    <td>1 tablet OD<input type="hidden" value="1 tablet OD" /></td>
    <td><input type="radio" name="second_requested" title="Requested" value="30" /> 30
        <input type="radio" name="second_requested" title="Requested" value="60" />60
        <input  type="radio" name="second_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="second_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="second_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="second_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr  id="row3">
    <td><input type="radio" name="drug3" title="drug" value="792|21"/>NVP200/D4T30/3TC150</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="third_requested" title="Requested" value="60" />60
        <input type="radio" name="third_requested" title="Requested" value="120" />120
        <input  type="radio" name="third_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="third_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="third_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="third_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row4">
    <td><input type="radio" name="drug4" title="drug" value="630|26"/>3TC 150mg/ZDV300mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="fourth_requested" title="Requested" value="60" /> 60
        <input type="radio" name="fourth_requested" title="Requested" value="120" />120
        <input  type="radio" name="fourth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="fourth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="fourth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="fourth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row5">
    <td><input type="radio" name="drug5" title="drug" value="1400|30"/>3TC300mg/TDF300mg</td>
    <td>1 tablet OD<input type="hidden" value="1 tablet OD" /></td>
    <td><input type="radio" name="fifth_requested" title="Requested" value="30" />30
        <input type="radio" name="fifth_requested" title="Requested" value="60" />60
        <input  type="radio" name="fifth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="fifth_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="fifth_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="fifth_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row6">
    <td><input type="radio" name="drug6" title="drug" value="6965|39"/>3TC150mg/D4T30mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="sixth_requested" title="Requested" value="60" /> 60
        <input type="radio" name="sixth_requested" title="Requested" value="120" />120
        <input  type="radio" name="sixth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="sixth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="sixth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="sixth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row7">
    <td><input type="radio" name="drug7" title="drug" value="633|27"/>Efavirenz 600mg</td>
    <td>1 tablet nocte<input type="hidden" value="1 tablet nocte" /></td>
    <td><input type="radio" name="seventh_requested" title="Requested" value="30" />30
        <input type="radio" name="seventh_requested" title="Requested" value="60" />60
        <input  type="radio" name="seventh_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="seventh_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="seventh_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="seventh_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row8">
    <td><input type="radio" name="drug8" title="drug" value="631|22"/>Nevirapine200mg</td>
    <td><input type="radio" value="1 tab OD" />1 tab OD</br>
        <input type="radio" value="1 tab BD" />1 tab BD
    </td>
    <td><input type="radio" name="eigth_requested" title="Requested" value="14" />14</br>
        <input type="radio" name="eigth_requested" title="Requested" value="60" />60
        <input type="radio" name="eigth_requested" title="Requested" value="120" />120
        <input type="radio" name="eigth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td> <input type="radio" name="eigth_dispensed" title="dispensed" value="14" />14</br>
        <input type="radio" name="eigth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="eigth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="eigth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row9">
    <td><input type="radio" name="drug9" title="drug" value="814|28"/>Abacavir300mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="ninth_requested" title="Requested" value="60" /> 60
        <input type="radio" name="ninth_requested" title="Requested" value="120" />120
        <input  type="radio" name="ninth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="ninth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="ninth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="ninth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr  id="row10">
    <td><input type="radio" name="drug10" title="drug" value="628|23"/>Lamivudine150mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="tenth_requested" title="Requested" value="60" /> 60
        <input type="radio" name="tenth_requested" title="Requested" value="120" />120
        <input  type="radio" name="tenth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="tenth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="tenth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="tenth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row11">
    <td><input type="radio" name="drug11" title="drug" value="797|24"/>Zidovudine300mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="eleventh_requested" title="Requested" value="60" /> 60
        <input type="radio" name="eleventh_requested" title="Requested" value="120" />120
        <input  type="radio" name="eleventh_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="eleventh_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="eleventh_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="eleventh_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row12">
    <td><input type="radio" name="drug12" title="drug" value="794|150"/>Aluvia 200mg</td>
    <td><input type="radio" name="twelfth_dose" value="2 tablets BD" />2 tablets BD</br>
        <input type="radio" name="twelfth_dose" value="3 tablets BD" />3 tablets BD
    </td>
    <td><input type="radio" name="twelfth_requested" title="Requested" value="120" />120
        <input type="radio" name="twelfth_requested" title="Requested" value="240" />240
        <input  type="radio" name="twelfth_requested" title="Requested" value="360" />360</br>
        <input type="radio" name="twelfth_requested" title="Requested" value="240" /> 240
        <input type="radio" name="twelfth_requested" title="Requested" value="480" />480
        <input  type="radio" name="twelfth_requested" title="Requested" value="600" />600
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="twelfth_dispensed" title="dispensed" value="120" />120
        <input type="radio" name="twelfth_dispensed" title="dispensed" value="240" />240
        <input  type="radio" name="twelfth_dispensed" title="dispensed" value="360" />360</br>
        <input type="radio" name="twelfth_dispensed" title="dispensed" value="240" /> 240
        <input type="radio" name="twelfth_dispensed" title="dispensed" value="480" />480
        <input  type="radio" name="twelfth_dispensed" title="dispensed" value="600" />600
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row13">
    <td><input type="radio" name="drug13" title="drug" value="6156|36"/>Raltergravir 400mg</td>
    <td>1 tablet BD<input type="hidden" value="1 tablet BD" /></td>
    <td><input type="radio" name="thirteenth_requested" title="Requested" value="60" /> 60
        <input type="radio" name="thirteenth_requested" title="Requested" value="120" />120
        <input  type="radio" name="thirteenth_requested" title="Requested" value="180" />180
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="thirteenth_dispensed" title="dispensed" value="60" />60
        <input type="radio" name="thirteenth_dispensed" title="dispensed" value="120" />120
        <input  type="radio" name="thirteenth_dispensed" title="dispensed" value="180" />180
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row14">
    <td><input type="radio" name="drug14" title="drug" value="6180|31"/>Truvada</td>
    <td>1 tab OD<input type="hidden" value="1 tab OD" /></td>
    <td><input type="radio" name="fourteenth_requested" title="Requested" value="30" />30
        <input type="radio" name="fourteenth_requested" title="Requested" value="60" />60
        <input  type="radio" name="fourteenth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="fourteenth_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="fourteenth_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="fourteenth_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr>
    <td colspan=3><h3>OI PROPHYLAXIS</h3></td>
</tr>
<tr id="row15">
    <td><input type="radio" name="drug15" title="drug" value="656|60"/>Isoniazid 300mg</td>
    <td>300mg Tab OD<input type="hidden" value="300mg Tab OD" /></td>
    <td><input type="radio" name="fifteenth_requested" title="Requested" value="30" />30
        <input type="radio" name="fifteenth_requested" title="Requested" value="60" />60
        <input  type="radio" name="fifteenth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="fifteenth_requested" title="dispensed" value="30" />30
        <input type="radio" name="fifteenth_requested" title="dispensed" value="60" />60
        <input  type="radio" name="fifteenth_requested" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr id="row16">
    <td><input type="radio" name="drug16" title="drug" value="656|61"/>Septrin DS</td>
    <td>1 Tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="sixteenth_requested" title="Requested" value="30" />30
        <input type="radio" name="sixteenth_requested" title="Requested" value="60" />60
        <input  type="radio" name="sixteenth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="sixteenth_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="sixteenth_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="sixteenth_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr  id="row18">
    <td><input type="radio" name="drug18" title="drug" value="92|62"/>Dapsone 100mg</td>
    <td>1 Tab OD<input type="hidden" value="1 Tab OD" /></td>
    <td><input type="radio" name="eigteenth_requested" title="Requested" value="30" />30
        <input type="radio" name="eigteenth_requested" title="Requested" value="60" />60
        <input  type="radio" name="eigteenth_requested" title="Requested" value="90" />90
    </td>
    <td><input type="text" name="otherR" value="" style="width:50px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="radio" name="eigteenth_dispensed" title="dispensed" value="30" />30
        <input type="radio" name="eigteenth_dispensed" title="dispensed" value="60" />60
        <input  type="radio" name="eigteenth_dispensed" title="dispensed" value="90" />90
    </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
</tr>
<tr class="otherDrug" id="row19">
    <td><input type="text" name="dispenseForm_medication" id=d_19  style="width:150px;"/></td>
    <td><input type="hidden" name="nineteenth_drug_line" id=h_19 value="" /></td>
    <td><select id="adultHIVFormDose1"  name="dose"></select></td>
    <td><input type="text" name="nineteenth_requested" title="Requested" value="" style="width:100px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
    <td></td>
</tr>
<tr class="otherDrug" id="row20">
    <td><input type="text" name="dispenseForm_medication" id=d_20  style="width:150px;"/></td>
    <td><input type="hidden" name="twentienth_drug_line" id=h_20 value="" /></td>
    <td><select id="adultHIVFormDose2"  name="dose"></select></td>
    <td><input type="text" name="twentienth_requested" title="Requested" value="" style="width:100px;"/></td>
    <td><input type="text" name="PillCount" value="" style="width:50px;"/> </td>
    <td><input type="text" name="Quantity" value="" title="qnty" style="width:50px;"/> </td>
    <td></td>
</tr>
<tr class="display">
    <td>Prescribed By</td><td><input type="text" id="prescriber" name="Prescriber" style="width:100px;" value=""/></td><td><input type="button" value="Dispense" onclick="processForm()"></td>
</tr>
</table>
</fieldset>
</form>
</div>
</body>
</html>