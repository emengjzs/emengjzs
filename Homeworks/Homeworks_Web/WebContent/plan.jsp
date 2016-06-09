<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<div id="contents-in">

<div class="row" style="margin-top: 12px; margin-bottom: 8px;">  
<div class="col-sm-2" style="margin-right: -60px;
margin-top: 4px;">从</div>
<div class="col-sm-4">
<div id="startdate"></div>
</div>  
<div class="col-sm-2" style="margin-right: -60px;
margin-top: 4px;">到</div>
<div class="col-sm-4">
<div id="enddate"></div></div>  
<div class="col-sm-4"> 
<button id="newterm" type="button" class="btn btn-sm btn-success">制定新学期</button> 
</div>     
</div>
<!-- terminfo 
<div id="terminfo">  
  <div class="row">  
  <div class="col-sm-2"></div>  
    <div class="col-sm-4"><p>从</p> 
    
    </div>
  <div class="col-sm-4"><p>到</p>
  
  </div>
  <div class="col-sm-2"></div>
  </div>
</div>
terminfo -->

<div class="row">

    <div class="col-sm-6">
    <div class="panel  panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">待选课程列表</h3>
        </div>
        <div class="panel-body">
            <div id="courseTable"></div>
        </div>
    </div>
    </div>
     
    <div class="col-sm-6">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">教学计划</h3>
        </div>
        <div class="panel-body">
            <div id="planTable"></div>
        </div>        
    </div>



    </div>
</div>
</div>


    <script type="text/javascript">
	
    $("#startdate").jqxDateTimeInput({
        theme: 'bootstrap', 
        formatString: "yyyy/MM/dd",
        value: ${s.JSStartDateString},
        min: ${s.JSStartDateString},
      });
    $("#enddate").jqxDateTimeInput({
        theme: 'bootstrap', 
        formatString: "yyyy/MM/dd",
        value: ${s.JSEndDateString}
    });
    
    
    
    var init_table = function(tid) {
            /*初始化课程列表*/
            var course_source =
            {   url: "course/except",
                datatype: "json",
                data:{'tid': tid},
                id: 'cid' ,
                datafields:
                [
                    { name: 'cid',          type: 'string'  },
                    { name: 'name',         type: 'string'  },
                    { name: 'institute',    type: 'string'  },
                ]
            };
           var coursedataAdapter = new $.jqx.dataAdapter(course_source);

           var columns = [
                  { text: '课程号', dataField: 'cid', cellsAlign: 'center', width: '30%', align: 'center'                       
                 
                  },
                  {
                      text: '课程名', dataField: 'name',  cellsAlign: 'center', align: 'center'
                  },
                  { text: '院系', dataField: 'institute', cellsAlign: 'center', align: 'center'},
                  {
                      text: '移入', cellsalign: 'center', align: "center",  width: '15%', columnType: 'none', editable: false, sortable: false, dataField: null, filterable: false, cellsrenderer: function (row, columnfield, value, defaulthtml, config, data) {
                          // render custom column.
                          return defaulthtml.replace(/<\/div>/, "<button data-row='" + data['cid'] + "' class='addButton btn btn-success btn-sm'>加入</button></div>");
                                                  
                      }
                  }
                ];



           /*初始化计划列表*/
           var plan_source =
            {   url: "semester/lesson",
                datatype: "json",
                data:{'tid': tid},
                id: 'cid' ,
                datafields:
                [   
                    { name: 'cid',          type: 'string'},
                    { name: 'name',         type: 'string'},
                    { name: 'institute',    type: 'string'},
                ],
                
                 addrow: function (rowid, rowdata, position, commit) {
                    var result;
                    //console.log(rowdata);
                    ///
                    
                    $.ajax({
                        type: 'POST',
                        url:  'semester/lesson',
                        dataType: 'json',
                        data: {
                            'cid': rowdata.cid,
                            'tid': tid,
                        },
                        success: function(data) {  
                          console.log(data['result']);                 
                          result = data['result'];  
                                            
                        },
                    });
                    
                    commit(result);     
                },
                deleterow: function (rowid, commit) {
                    $.ajax({
                        type: 'POST',
                        url:  'semester/lesson/delete',
                        dataType: 'json',
                        data: {
                            'cid': rowid,
                            'tid': tid,
                        },
                        success: function(data) {                 
                        },
                    });
                    commit(true);
                },             
            };
 

             var planColumns = [
                  { text: '课程号', dataField: 'cid', cellsAlign: 'center', width: '30%', align: 'center'                       
                 
                  },
                  {
                      text: '课程名', dataField: 'name',  cellsAlign: 'center', align: 'center'
                  },
                  { text: '院系', dataField: 'institute', cellsAlign: 'center', align: 'center'},
                  {
                      text: '移出', cellsAlign: 'center', align: "center",  width: '15%', columnType: 'none', filterable: false,editable: false, sortable: false, dataField: null, cellsRenderer: function (row, column, value) {
                          // render custom column.
                          return "<button data-row='" + row + "' class='removeButton btn btn-warning btn-sm'>移除</button>";
                      }
                  }
                ];
            
            var plandataAdapter = new $.jqx.dataAdapter(plan_source);

            var clickButtonEvent = function(event) {
                var rowIndex = parseInt(event.target.getAttribute('data-row'));
                console.log(rowIndex);
                if (isNaN(rowIndex)) {
                    return;
                }
                var row = $('#courseTable').jqxGrid('getrowdatabyid', rowIndex);                                              
                $('#planTable').jqxDataTable('addRow', row['cid'],  row);
                $("#courseTable").jqxGrid('deleteRow', row['cid']);
            }
         
            

            $("#courseTable").jqxGrid(
            {   theme: "bootstrap",
                source: coursedataAdapter,
                pageable: false,
                altRows: false,
                //autorowheight: true,
                selectionMode: 'custom',
                width:"100%",
                //pagerButtonsCount: 8,
                columns:columns,
                showfilterrow: true,
                rowsheight: 35,
                height: "620px",
                filterable: true,
                rendered: function () {
                    console.log("rendered");
                    $('.addButton').click(clickButtonEvent);

                }

            });

             $("#courseTable").on("filter", function(event) {
                 $('.addButton').click(clickButtonEvent);
             });
            
          
             $("#planTable").jqxDataTable(
            {   theme: "bootstrap",
                source: plandataAdapter,
                pageable: true,
                altRows: false,
                selectionMode: 'custom',
                width:"100%",
                pagerButtonsCount: 8,
                columns:planColumns,

                rendered: function () {

                    $('.removeButton').click(function(event) {
                        
                        var rowIndex = parseInt(event.target.getAttribute('data-row'));
                        if (isNaN(rowIndex)) {
                            return;
                        }
                        var row = {};
                        var field = ['cid', 'name', 'institute'];
                        for(var i in field) {
                             row[field[i]] = $("#planTable").jqxDataTable('getCellValue', rowIndex, field[i]);
                        }
                       
                        $('#courseTable').jqxGrid('addRow', row['cid'],  row);
                        $("#planTable").jqxDataTable('deleteRow', rowIndex);                       
                    });

                }

            });
    }
    </script>
    <script type="text/javascript">
    //$('#terminfo').hide();
    $('#newterm').click(function() {
                    var startd = $("#startdate").val();
                    var endd = $("#enddate").val();
                    //if(startd.getYearendd >0) {
                    //  shownotify({success: false, info: "请检查日期！"});
                    //}
                    $.ajax({
                        type: 'POST',
                        url:  'semester',
                        dataType: 'json',
                        data: {start: startd ,
                               end:  endd},                                               
                      
                        success: function(data) { 
                            shownotify(data);
                              if(! data.success) {
                                  
                                  return;
                              }

                              $("#startdate").jqxDateTimeInput({
                                  theme: 'bootstrap', 
                                  formatString: "yyyy/MM/dd",
                                  value: new Date(
                                      data['term']['startDateString']
                                        .replace(/-/g,   "/"))
                                });
                              $("#enddate").jqxDateTimeInput({
                                  theme: 'bootstrap', 
                                  formatString: "yyyy/MM/dd",
                                  value: new Date(
                                      data['term']['endDateString']
                                        .replace(/-/g,   "/"))
                                });
                              //$("#terminfo").show();
                              init_table(data['term']['id']);
                        },
                    });        
    });
    </script>