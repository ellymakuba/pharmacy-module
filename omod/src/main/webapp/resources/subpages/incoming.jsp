<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
<DIV id="dtab_1">
    <div id="detailsformin" title="Information"> </div>
    <div id="detailsformValin" title="Are you sure ?"></div>
    <div id="encountformDet" title="Information"></div>
    <div id="detailsformValinT" title="Are you sure ?"></div>
    <DIV id="incss">
        <form id="incomingextrain" action="#">
            <hr color=#1aad9b width="100%">
            <fieldset>
                <legend>Extra information</legend>
                <br/>
                <label>Supplier</label>
                <select id="supplier" name="supplier" class="required"></select> <br/>
                <label>Quantity in</label>
                <input type="text" name="incomingnumber" id="incomingnumber" class="required"/> <br/> <br/>
                <label>Batch No</label>
                <input type="text" name="incomingbatch" id="incomingbatch" class="required"/> <br/> <br/>
                <label for="incomingexpire">Expire date</label>
                <input type="text" name="incomingexpire" id="incomingexpire" class="required"/> <br/> <br/>
                <label>Delivery No</label>
                <input type="text" name="delivery" id="delivery" class="required"/> <br/></select><br/>
                <input type="hidden" name="incomingdrugg" id="incomingdrugg" class="required"/>
                <input type="hidden" name="incominguuidextra" id="incominguuidextra" class="required"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
            <hr color=#1aad9b width="100%">
        </form>
        </form>
        <form id="s11">
            <fieldset>
                <table>
                    <tr>
                        <td>S11 No. </td>
                        <td><input type="text" name="incomings11" id="incomings11" /> </td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="dateCreated" id="dateCreated" /> </td>
                    </tr>
                    <tr>
                        <td><label>Transactions Type</label> </td>
                        <td><select id="transactions" name="transactions" > </select> </td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label>Requesting from</label></td>
                        <td><select id="location" name="location" class="required"> </select></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
                <table id="tableincoming">
                    <tr>
                        <th>Drug</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th class="hidable">Expiry date</th>
                        <th class="hidable">Batch NO</th>
                        <th class="hidable">Buying Price</th>
                        <th class="hidable">Selling Price</th>
                        <th></th>
                    </tr>
                    <tr><td><input type="text" name="incomingdrug" id="incomingdrug" class="required"/> </td>
                        <td><select id="incomingcategory" class="datavalues" name="incomingcategory" style="width:100px;" class="required"> </select></td>
                        <td><input type="text" class="datavalues" name="incomingquantityin" id="incomingquantityin" style="width:100px;" class="required"/> </td>
                        <td><input type="text"  name="date" id="date" style="width:100px;" class="myDate" readonly="true"/></td>
                        <td><input type="text" name="batchNo" id="batchNo" style="width:100px;" class="hidable"/> </td>
                        <td><input type="text" name="buyingPrice" id="buyingPrice" style="width:100px;" class="hidable" value=""/> </td>
                        <td><input type="text" name="unitPrice" id="unitPrice" style="width:100px;" class="hidable" value=""/> </td>
                        <td><a href="#">Remove</a></td>
                    </tr>
                </table>
                <input type="button"  value="Add Drug" onclick="addRow('tableincoming')"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="incoming">
            <fieldset>
                <label>S11 No.</label>
                <input type="text" name="incomings11" id="incomings111" class="required"/> <br/>
                <label>Transactions Type</label>
                <select id="transactions1" name="transactions" class="required"> </select> <br/>
                <label>Requesting from</label>
                <select id="location1" name="location" class="required"></select> <br/>
                <label>Drug </label>
                <input type="text" name="incomingdrug2" id="incomingdrug2" class="required"/> <br/>
                <label>category </label>
                <select id="incomingcategory1" class="datavalues" name="incomingcategory" class="required"> </select> <br/>
                <label>Quantity </label>
                <input type="text" name="incomingquantity" id="incomingquantity" class="required"/> <br/>
                <input type="hidden" name="incomingedit" id="incomingedit" value="" class="required"/> <br/>
                <input type="hidden" name="incominguuid" id="incominguuid" value="" class="required"/> <br/>
                <input class="submit" type="submit" value="Submit"/><br/>
            </fieldset>
        </form>
        <form id="incomingvoid" action="#">
            <hr color=#1aad9b width="100%">
            <fieldset>
                <legend> Void reason</legend>
                <label>Reason</label> <input type="text" name="incomingreason" id="incomingreason" class="required"/>
                <input type="hidden" name="incominguuidvoid"  id="incominguuidvoid"/>
                <input class="submit  type="submit" value="Submit"/>
            </fieldset>
            <hr color=#1aad9b width="100%">
        </form>
    </DIV>
</DIV>
</body>
</html>