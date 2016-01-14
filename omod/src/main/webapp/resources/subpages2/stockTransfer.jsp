<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Stock Transfer Form</title></head>
<body>
<DIV id="dtab_1">
        <form id="stockTransferForm">
            <fieldset>
                <table>
                    <tr>
                        <td><label>Tranfering To</label></td>
                        <td><select id="location" name="location" class="required"> </select></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
                <table id="stockTransferTable">
                    <tr>
                        <th>Drug</th>
                        <th>Quantity In Stock</th>
                        <th class="hidable">Expiry date</th>
                        <th class="hidable">Batch NO</th>
                        <th class="hidable">Buying Price</th>
                        <th class="hidable">Selling Price</th>
                        <th>Quantity To transfer</th>
                        <th></th>
                    </tr>
                    <tr><td><input type="text" name="outgoingDrug" id="outgoingDrug"/> </td>
                        <td><input type="text" id="quantityInStock" name="quantityInStock" style="width:100px;" readonly=''> </td>
                        <td><input type="text"  name="expiryDate" id="expiryDate" style="width:100px;" readonly='' /></td>
                        <td><input type="text" name="batchNo" id="batchNo" style="width:100px;" readonly=''/> </td>
                        <td><input type="text" name="buyingPrice" id="buyingPrice" style="width:100px;" value="" readonly=''/> </td>
                        <td><input type="text" name="unitPrice" id="unitPrice" style="width:100px;"  value="" readonly=''/> </td>
                        <td><input type="text" name="outgoingQuantity" id="outgoingQuantity" style="width:100px;"/> </td>
                        <td><a href="#">Remove</a></td>
                    </tr>
                </table>
                <input type="button"  value="Add Drug" onclick="addRow('stockTransferTable')"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
</DIV>
</body>
</html>