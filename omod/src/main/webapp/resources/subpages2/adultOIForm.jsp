<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Adult OI Prescription form</title>
    <style type="text/css">
        .adultOIFormDivClass table tr td{
            border:1px solid white;
            background-color:#EBEBFF;
        }
    </style>
</head>
<body>
<DIV id="AdultOIFormDiv" class="adultOIFormDivClass">
    <h1><a>Adult OI Prescription form</a></h1>
    <form id="adultOIForm" action="#">
        <fieldset>
            <table   id="patientSection">
                <tr class="display">
                    <td>Patient ID</td><td><input name="patientId" id="patientId" class="required"  style="width:100px;"/></td>
                    <td colspan="2">Patient name</td><td><input type="text" name="patientName" id="patientName" style="width:200px;"  readonly/></td>
                </tr>
                <tr class="display">
                    <td>Encounter date</td><td><input id="datePicker" name="Enc4*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
                    <td>No of days </td><td><input id="noofmonths" name="Enc6*noOfMonths|6#6" type="text"  value="" style="width:100px;"/></td>
                    <td> Next visit date</td><td><input id="nextvisit" name="Enc5*EncounterDate#3" type="text"  value="" style="width:100px;"/></td>
                </tr>
            </table>
            <table id="dataSection" style="width:100%;">
                <tr>
                    <th>Medication</th><th style="width:30%;">Indication</th><th style="width:25%;">Sig(Qty per pack)</th><th style="width:100px;">Quantity</th>
                </tr>
                <tr class="hideRow" id="row1">
                    <td><input type="radio" name="drug1" title="drug" value="732|63"/>Acyclovir 200mg</td>
                    <td><input type="radio" name="indication1" title="indication" value="Genital/Oral HSV initial or recurrent" /> Genital/Oral HSV initial or recurrent</br>
                        <input type="radio" name="indication1" title="indication" value="Herpes zoster" />Herpes zoster</br>
                        <input  type="radio" name="indication1" title="indication" value="Other" />Other
                    </td>
                    <td>2 TDS x 7 days(42)<input type="hidden" name="sig1" value="2 TDS x 7 days(42)" style="width:50px;"/></br>
                        4 QIDx 10 days<input type="hidden" name="sig1" value="4 QIDx 10 days" style="width:50px;"/>
                    </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="hideRow" id="row2">
                    <td><input type="radio" name="drug2" title="drug" value="747|64"/>Fluconazole 200 mg</td>
                    <td><input type="radio" name="indication2" title="indication" value="Initial treatment phase for cryptococcal meningitis" /> Initial treatment phase for cryptococcal meningitis</br>
                        <input type="radio" name="indication2" title="indication" value="continuation Phase for cryptococcal meningitis" />continuation Phase for cryptococcal meningitis</br>
                        <input  type="radio" name="indication2" title="indication" value="Esophageal candidiasis" />Esophageal candidiasis
                        <input  type="radio" name="indication2" title="indication" value="Initiation" />Initiation
                        <input  type="radio" name="indication2" title="indication" value="Retreatment" />Retreatment
                    </td>
                    <td>4 OD X 2 weeks (56)</br>
                        2 QD X 28 days(56)</br>
                        1 QD X 14 days(14)
                    </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="hideRow" id="row3">
                    <td><input type="radio" name="drug3" title="drug" value="916|65"/>SMX/TMP SS</td>
                    <td><input type="radio" name="indication3" title="indication" value="PCP Treatment(252 SS)" /> PCP Treatment(252 SS)</br>
                        <input type="radio" name="indication3" title="indication" value="skin Inf(56 SS tab)" />skin Inf(56 SS tab)</br>
                        <input  type="radio" name="indication3" title="indication" value="2 nd line Toxo(224 SS)" />2 nd line Toxo(224 SS)
                    </td>
                    <td>4 SS TDS X 21 days </br>
                        4 SS TDS X 7 days </br>
                        4 SS TDS X 28 days
                    </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="hideRow" id="row4">
                    <td><input type="radio" name="drug4" title="drug" value="916|61"/>SMX/TMP DS</td>
                    <td><input type="radio" name="indication4" title="indication" value="PCP Treatment(126 DS)" />PCP Treatment(126 DS)</br>
                        <input type="radio" name="indication4" title="indication" value="skin Inf(28 DS tab)" />skin Inf(28 DS tab)</br>
                        <input  type="radio" name="indication4" title="indication" value="2 nd line Toxo(112 DS)" />2 nd line Toxo(112 DS)
                    </td>
                    <td>2 DS TDS X 21 days </br>
                        2 DS TDS X 7 days</br>
                        2 DS TDS X 28 days
                    </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="hideRow" id="row5">
                    <td><input type="radio" name="drug5" title="drug" value="766|66"/>Pyridoxine 50 mg</td>
                    <td><input type="radio" name="indication5" title="indication" value="INH Peripheral neuropathy treatment or preventions" />INH Peripheral neuropathy treatment or preventions
                    </td>
                    <td>1 OD X 30 days</td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="otherDrug" id="row6">
                    <td><input type="text" name="dispenseForm_medication" id=d_6  style="width:300px;"/></td>
                    <td><input type="text" name="indication6" value="" style="width:250px;"/></td>
                    <td><input type="text" name="sig6" value="" style="width:200px;"/> </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
                </tr>
                <tr class="otherDrug" id="row7">
                    <td><input type="text" name="dispenseForm_medication" id=d_7  style="width:300px;"/></td>
                    <td><input type="text" name="indication7" value="" style="width:250px;"/></td>
                    <td><input type="text" name="sig7" value="" style="width:200px;"/> </td>
                    <td><input type="text" name="Quantity" value="" title="qnty" style="width:90%;"/> </td>
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