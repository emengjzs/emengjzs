<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<div id="contents-in">

    <div class="row" style="margin-top: 8px; margin-bottom: -8px;">
    <div class="col-md-5">
    <div class="input-group">
    <input id="registerinput" class="form-control" type="text" placeholder="输入数字作为新工号"></input>
        <span class="input-group-btn">
        <button id="registerTeacher" class="btn btn-success" type="button">添加教师</button>
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
            {   url: "teacher",
                datatype: "json",
                updaterow: function (rowid, rowdata, commit) {
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failder.
                    var result;
                    //console.log(rowdata);
                    $.ajax({
                        type: 'POST',
                        url:  'teacher',
                        dataType: 'json',
                        data: {
                            'tid': rowdata.tid,
                            'password': rowdata.password,
                            'institute': rowdata.institute,
                            'name': rowdata.name,
                            'manager': rowdata.manager
                        },
                        success: function(data) {                   
                            shownotify(data);                
                            result = data.success;                     },
                    });
                    commit(result);
                },
                datafields:
                [
                    { name: 'tid',          type: 'string'  },
                    { name: 'name',         type: 'string'  },
                    { name: 'institute',    type: 'string'  },               
                   
                    { name: 'manager',       type: 'bool'    },
                    { name: 'password',     type: 'string'  },
                ]
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                theme:          "bootstrap",
                rowsheight:     35,
                width: "100%",
                source:         dataAdapter,
                sortable:       true,
                editable:       true,
                filterable: true,
                showfilterrow: true,
                enabletooltips: true,
                editmode: 'selectedrow',
                pageable: true,
                pagesize: 15,
                selectionmode: 'multiplecellsadvanced',
                columns: [
                  { text: '工号', columntype: 'textbox', datafield: 'tid',   align: 'center', cellsalign: 'center', editable: false },
                  { text: '姓名', columntype: 'textbox', datafield: 'name',  align: 'center', cellsalign: 'center' },
                  { text: '院系', columntype: 'dropdownlist', datafield: 'institute', align: 'center' , cellsalign: 'center',
                	  filtertype: 'checkedlist',initeditor : function(row, cellvalue, editor) {
                        editor.jqxDropDownList('selectItem', cellvalue);
                    },
                    createeditor: function (row, cellvalue, editor) { 
                        var config = get_institute_config();          
                        editor.jqxDropDownList(
                            config
                        );
                    
                    }
                  },             
                  
                   { text: '教学负责人', datafield: 'manager', columntype: 'checkbox', align: 'center' , cellsalign: 'center'},
                   { text: '密码', columntype: 'textbox', datafield: 'password',   align: 'center', cellsalign: 'center' },
                      
                ]
            });
        });
        
    </script>
<script type="text/javascript">
    $('#registerTeacher').click(function() {
        var id = $('#registerinput').val();
        $.ajax({
            type: 'POST',
            url:  'add/teacher',
            dataType: 'json',
            data: {tid: id}, 
            success: function(data) {                  
                console.log(data);
                if(data.success){ 
                    $('#jqxgrid').jqxGrid('addrow', id, {tid: id, password: id});
                    $('#registerinput').val('');
                } 
                shownotify(data);
            },          
        });
    });


</script>