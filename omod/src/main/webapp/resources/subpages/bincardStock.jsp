<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">


    <form id="stockfilter" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend> Category Filter</legend>

            <label>Drug stock</label>

            <select id="filterstock" name="filterstock">


            </select>


        </fieldset>
        <hr color=#1aad9b width="100%">

    </form>


    <DIV id="incss">
        <form id="bincardform" action="#">
            <fieldset>

                <legend>Name</legend>

                 <input type="hidden"  name="drug_id" id="drug_id" /><br/>
                <input type="hidden"  name="uuid" id="uuid" /><br/>
                <label for="engine">Drug Name</label> <input type="text" name="drugname" id="drugname" readonly=""/><br/>
                <label for="engine">Quantity</label> <input type="text"  name="cQuantity" id="cQuantity" readonly=""/><br/>
                <label for="engine">New Quantity</label> <input type="text" name="aStcock" id="aStcock" /><br/>
                <label for="engine">Unit Price</label> <input type="text" name="unitPrice" id="unitPrice" /><br/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tstockcard">
        <thead>
        <tr>

            <th>Edit</th>
            <th>Action</th>
            <th>Drug</th>
            <th>Quantity</th>
            <th>Expiry Date</th>
            <th>Batch No</th>
            <th>Drug ID</th>
            <th>Unit Price</th>
            <th>Void</th>

        </tr>
        </thead>
        <tbody>
        <tr>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>

        </tr>
        </tbody>

    </table>

</DIV>

</body>
</html>