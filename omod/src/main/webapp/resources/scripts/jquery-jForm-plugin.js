(function($){
    $.fn.extend({

        jForm: function(form) {
            return this.each(function() {
                var obj = $(this);

                var html="";


                for(var x in form)
                {

                    if(x.substring(3)=="hasTable")


                    {

                        html+="<table width='100%'>";
                        $.each(form[x].table, function(key,value) {
                            // create a row
                            if(key.substring(3)=="row")
                            {
                                html+='<tr class="bottomline">';
                                $.each(value, function(key1,value1) {
                                    // create a td for the table
                                    if(key1.substring(3)=="td")
                                    {
                                        html+="<td>";
                                        //add elements to that td created above
                                        $.each(value1, function(key2,value2) {
                                            if(key2.substring(3)=="text")
                                            {

                                                html+='<p><input type="'+key2.substring(3)+'" title="'+value2.title+'" name="'+value2.name+'"  id="'+value2.id+'" value="" size="10"/>';

                                            }
                                            else if(key2.substring(3)=="text2")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'" value=""   size="10"/>'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="textPeds")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'"  value="'+value2.value+'"   size="10"/>'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="text1")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'" title="'+value2.title+'" class="expectedAmount"  readonly="" size="10" />'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="textStock")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'" title="'+value2.title+'"  readonly="" size="10" />'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="textwaived")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'" title="'+value2.title+'" class="amountWaived" value="0" size="10"/>'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="textpaid")
                                            {

                                                html+='<input type="text" name="'+value2.name+'"  id="'+value2.id+'" title="'+value2.title+'" class="amountPaid" value="0" size="10"/>'+value2.label+'';

                                            }
                                            else if(key2.substring(3)=="radio")
                                            {


                                                html+='</'+value2.line+'><input id="'+value2.id+'" class="radios" type="'+key2.substring(3)+'" title="'+value2.title+'" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="select")
                                            {


                                                html+='<p><select  name="'+value2.name+'"  id="'+value2.id+'" value=""  ></select>';
                                                /*alert("input type is "+key2.substring(3)+" ID is  "+value2.id);*/
                                            }
                                            else if(key2.substring(3)=="radio1")
                                            {


                                                html+='</'+value2.line+'><p><input  id="'+value2.id+'" class="radios" title="'+value2.title+'" type="radio" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="radio2")
                                            {


                                                html+='</'+value2.line+'></br><input id="'+value2.id+'" type="radio" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }

                                            else if(key2.substring(3)=="submit")
                                            {
                                                html+='<div id="submitDiv" data-role="fieldcontain"> <input type="'+key2.substring(3)+'" value="'+value2.value+'"  data-inline="true"/></div>';

                                            }
                                            else if(key2.substring(3)=="checkbox")
                                            {


                                                html+='</'+value2.line+'><p><input id="'+value2.id+'" type="'+key2.substring(3)+'" title="'+value2.title+'" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="checkbox2")
                                            {


                                                html+='</'+value2.line+'></br><input id="'+value2.id+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="checkbox1")
                                            {


                                                html+='</'+value2.line+'><input  id="'+value2.id+'" title="'+value2.title+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="label")
                                            {

                                                html+=' <label>'+value2.label+'</label></br>';

                                            }
                                            else if(key2.substring(3)=="div")
                                            {

                                                html+=' <div id='+value2.id+'></div>';

                                            }else if(key2.substring(3)=="hidden")
                                            {

                                                html+='<input type="'+key2.substring(3)+'" title="'+value2.title+'" name="'+value2.name+'"  id="'+value2.id+'" value="'+value2.value+'"   />';

                                            }
                                            else if(key2.substring(3)=="checkboxsinglemonth")
                                            {


                                                html+='</'+value2.line+'><input  id="'+value2.id+'" class="month1"  title="'+value2.title+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="checkboxsinglemonth1")
                                            {


                                                html+='</'+value2.line+'><p><input  id="'+value2.id+'" class="month1"  title="'+value2.title+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="checkboxsingledispense")
                                            {


                                                html+='</'+value2.line+'><input  id="'+value2.id+'" class="dispensed1"  title="'+value2.title+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }
                                            else if(key2.substring(3)=="checkboxsingldispense1")
                                            {


                                                html+='</'+value2.line+'><p><input  id="'+value2.id+'" class="dispensed1"  title="'+value2.title+'" type="checkbox" name="'+value2.name+'" value="'+value2.value+'"><label for="'+value2.id+'">'+value2.label+'</label>';

                                            }

                                        });

                                        html+="</td>";


                                    }


                                    else if(key1.substring(3)=="th")
                                    {
                                        html+="<th>";
                                        //add elements to that td created above
                                        $.each(value1, function(key2,value2) {
                                            if(key2.substring(3)=="label")
                                            {

                                                html+=' <label>'+value2.label+'</label>';

                                            }




                                        });

                                        html+="</th>";
                                    }

                                });

                                html+="</tr>";

                            }



                        });

                        html+="</table>";


                        obj.append(html).trigger("create");
                        break;
                    }
                    else if(x.substring(3)=="noTable")
                    {
                        html="";

                        $.each(form[x].content, function(key,value) {


                            if(key.substring(3)=="text")
                            {

                                html+=' <label for="'+value.id+'">'+value.label+':</label><div id="'+value.id+'" data-role="fieldcontain"><input type="'+key.substring(3)+'" name="'+value.name+'" id="'+value.id+'" value="" size="'+value.size+'"  /></div>';

                            }
                            else 	if(x.substring(3)=="radio")
                            {
                                html+='<fieldset data-role="controlgroup" data-mini="true"><legend>'+form[x].label+':</legend><input type="radio" name="radio-mini" id="radio-mini-1" value="choice-1" checked="checked" /><label for="radio-mini-1">Female</label><input type="radio" name="radio-mini" id="radio-mini-2" value="choice-2"  /><label for="radio-mini-2">Male</label></fieldset>';


                            }

                            else if(x.substring(3)=="button")
                            {
                                html+='<div id="submitDiv" data-role="fieldcontain"> <input type="submit" value="Submit" data-inline="true"/></div>';

                            }



                        });

                        obj.append(html).trigger("create");
                        break;
                    }



                };






            });
        }
    });
})(jQuery);
