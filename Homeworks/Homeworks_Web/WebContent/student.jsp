<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<div id="contents-in">
    <div class="row" style="margin-top: 8px; margin-bottom: -8px;">
    <div class="col-md-5">
    <div class="input-group">
    <input id="registerinput" class="form-control" type="text" placeholder="输入数字作为新学号"></input>
        <span class="input-group-btn">
        <button id="registerStudent" class="btn btn-success" type="button">添加学生</button>
        </span>
    </div>
    </div>
    <div class="col-md-7"></div>
    </div>
    </br>
<div class='default'>
    <div id='jqxWidget'>
        <div id="jqxgrid"></div>
        <div>
            <div id="cellbegineditevent"></div>
            <div style="margin-top: 10px;" id="cellendeditevent"></div>
       </div>
    </div>
    
</div>
</div>
  
    <script type="text/javascript">
        $(document).ready(function () {
                       
            // prepare the data
            

            var source =
            {   url: "student",
                datatype: "json",
                updaterow: function (rowid, rowdata, commit) {
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failder.
                    var result;
                    //console.log(rowdata);
                    $.ajax({
                        type: 'POST',
                        url:  'student',
                        dataType: 'json',
                        data: {
                            'sid': rowdata.sid,
                            'password': rowdata.password,
                            'year'    : rowdata.year.getFullYear(),
                            'institute': rowdata.institute,
                            'name': rowdata.name,
                            'tuitor': rowdata.tuitor
                        },
                        success: function(data) {                   
                            shownotify(data);                
                            result = data.success;                        },
                    });
                    commit(result);
                },
                 addrow: function (rowid, rowdata, position, commit) {
                    //var result;
                    //console.log(rowdata);
                    ///
                    
                    
                    
                    commit(true);     
                },

                datafields:
                [
                    { name: 'sid',          type: 'string'  },
                    { name: 'name',         type: 'string'  },
                    { name: 'institute',    type: 'string'  },               
                    { name: 'year',         type: 'date' , format: "yyyy"   },
                    { name: 'tuitor',       type: 'bool'    },
                    { name: 'password',     type: 'string'  },
                ]
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                theme:          "bootstrap",
                rowsheight:     35,
                 columnsheight: 30,
                width: "100%",
                source:         dataAdapter,
                sortable:       true,
                editable:       true,
                enabletooltips: true,
                showfilterrow: true,
                filterable: true,
                editmode: 'selectedrow',
                pageable: true,
                pagesize: 15,
                selectionmode: 'singlecell',
                columns: [
                  { text: '学号', columntype: 'textbox', datafield: 'sid',   align: 'center', cellsalign: 'center', editable: false },
                  { text: '姓名', columntype: 'textbox', datafield: 'name', align: 'center', cellsalign: 'center' },
                  { text: '院系', columntype: 'dropdownlist', datafield: 'institute',  align: 'center' , cellsalign: 'center',
                       filtertype: 'checkedlist',
                    initeditor : function(row, cellvalue, editor) {
                        editor.jqxDropDownList('selectItem', cellvalue);
                    },
                    createeditor: function (row, cellvalue, editor) {
                        
                         var config = get_institute_config();          
                        editor.jqxDropDownList(
                            config
                        );
                    }
                  },             
                  {
                      text: '入学年份', datafield: 'year', columntype: 'datetimeinput', align: 'center', cellsalign: 'center',  cellsformat: 'yyyy',
                      validation: function (cell, value) {
                          if (value == "")
                             return {result: false, message: "入学年份不能为空"};
                          return true;
                           
                        }
                  },
                   { text: '是否助教', datafield: 'tuitor', columntype: 'checkbox', align: 'center' , cellsalign: 'center'},
                   { text: '密码', columntype: 'textbox', datafield: 'password',  align: 'center', cellsalign: 'center' },
                      
                ]
            });
        });
        
</script>
<script type="text/javascript">
    $('#registerStudent').click(function() {
        var id = $('#registerinput').val();
        var result = false;
        $.ajax({
                        type: 'POST',
                        url:  'add/student',
                        dataType: 'json',
                        data: {'sid': id}, 
                        success: function(data) {                   
                            console.log(data);
                            result = data.success;
                            if(result) { 
                            	var date = new Date();
                                $('#jqxgrid').jqxGrid('addrow', id, {sid: id, year: date, password: id});
                                $('#registerinput').val('');
                                
                            } 
                            shownotify(data);
                        },          
        });      
        
    });


</script>