<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>
<DIV id="dnames">
    <DIV id="incss">
        <form id="locationvoid" method="POST" action="">
            <fieldset>
                <legend>Name Void reason</legend>
                <label for="engine">Reason</label> <input type="text" name="locationreason" id="locationreason" class="required"/>
                <input type="hidden" name="locationuuidvoid" id="locationuuidvoid"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="locationform" action="#">
            <fieldset>
                <legend>Name</legend>
                <label for="engine">Location name</label>
                <input type="text" name="locationname" id="locationname" class="required"/> <br/>
                <label for="engine">description</label>
                <input type="text" name="description" id="description"/> <br/>
                <label for="engine">Allow batch settings</label><select name="batch_settings">
                    <option value="0">No</option>
                    <option value="1">Yes</option>
                </select> <br/>
                <label for="engine">Cashier Present</label><select name="cashier_present">
                     <option value="0">No</option>
                     <option value="1">Yes</option>
                </select> <br/>
                <input type="hidden" name="locationeedit" id="locationedit" value="false"/>
                <input type="hidden" name="locationuuid" id="locationuuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tlocation">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>Location name</th>
            <th>Description</th>
            <th>Batch Setting</th>
            <th>Cashier Present</th>
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
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</DIV>
</body>
</html>