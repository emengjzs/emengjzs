<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
   <div id="courseinfo-in" class="well well-sm">
	<c:if test="${auth.canAddAssignment}">
    <button class="btn btn-primary btn-lg" data-toggle="modal" 
   data-target="#addassignment">
   添加新作业
    </button>
    <br/>
    </c:if>
   	<c:forEach items="${assignments}" var="as">
    <!-- -->
    
        <div id="${as.aid }" >

        <div class="panel panel-default">
        <div class="panel panel-body">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#${as.aid}info" data-toggle="tab">基本信息</a></li>
            
            <c:if test="${auth.canSeeOwnHomework}">
            <li><a href="#${as.aid}profile" data-toggle="tab">作业情况</a></li>
            </c:if>
            <c:if test="${auth.canSeeAllHomework}">
            <li><a class="recordtabletab" aid="${as.aid}" score="${as.score}" href="#${as.aid}records" data-toggle="tab">学生作业管理</a></li>
            </c:if>
            <li><a href="#${as.aid}comment" data-toggle="tab">作业总评</a></li>
            <li><a class="scommenttab" load='0' aid="${as.aid}" href="#${as.aid}scomment" data-toggle="tab">评论</a></li>
            <li class="pull-right"><h4 class="text-primary">作业-${as.name }</h4></li>
            <li class="pull-right" style="margin-top: 8px;"><span class="assignmentstatus label label-info">
                            ${as.statusEnum.info}
                            </span> </li>
        </ul>
        <div class="tab-content">
            
            <!-- 作业基本信息 -->
            <div class="tab-pane fade active in" id="${as.aid}info">
                <form>
        
                      <div class="form-group">                     
                            <label class="col-lg-2 control-label info-field">发布日期</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.publishDateString }
                            </div>
                            </div>               
                            </div>
                      
                      </div>
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">作业概要</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.name }  
                            </div>
                            </div>               
                            </div>
                      </div>
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">详细描述</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.description }  
                            </div>
                            </div>               
                            </div>
                      </div>
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">提交截止日期</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.deadlineString }
                            </div>
                            </div>               
                            </div>
                      </div>
                      <div class="form-group">
                            <label  class="col-lg-2 control-label info-field">批改截止日期</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.checkDeadlineString }
                            </div>
                            </div>               
                            </div>
                      </div>
                      <div class="form-group">
                            <label  class="col-lg-2 control-label">作业难度</label>
                            <div class="col-lg-10">

                            <div class="progress">
                                <div class="progress-bar progress-bar-warning" style="width: ${as.difficulty}0%; font-size: 14px;">${as.difficulty}
                               </div>
                            </div>               
                            </div>
                      </div>
                      <div class="row form-group" style="margin-left: -1px;">
                            <label  class="col-lg-2 control-label">总分</label>
                            <div class="col-lg-10">
                            <span class="label label-info" style="font-size: 14px; ">
                            ${as.score }
                            </span>               
                            </div>              
                      </div>
                      <div class="row form-group" style="margin-left:-1px;">
                            <div class="col-lg-2">
                            <label class="control-label">格式</label>
                            </div>
                            <div class="col-lg-10">
                            <span class="label label-info" style="font-size: 14px; ">
                            .${as.fileType }
                            </span>               
                            </div>              
                      </div>
                </form>
            </div>
            <!-- 作业基本信息 -->
			<c:if test="${auth.canSeeOwnHomework}">
            <!-- 作业情况 -->
            <div class="tab-pane fade" id="${as.aid}profile">
            
                      <br>
                      
                      <div class="row form-group" style="margin-left:-1px;">
                            <div class="col-lg-2">
                            <label class="control-label">状态</label>
                            </div>
                            <div class="col-lg-10">
                            <c:if test="${as.submitStatus==1}">
                            <span id="${as.aid}status" class="label label-danger" style="font-size: 14px;">                           
                            未提交
                            </span> 
                            </c:if>     
                            <c:if test="${as.submitStatus==0}">
                            <span id="${as.aid}status" class="label label-success" style="font-size: 14px;">                           
                            已提交
                            </span> 
                            </c:if>          
                            </div>
                      </div>
                      
                      <c:if test="${as.submitStatus==0}">
                      <div class="form-group">
                            <label  class="col-lg-2 control-label info-field">提交时间</label>
                            <div class="col-lg-10">
                            <div class="panel panel-default">
                            <div class="panel-body">
                            ${as.submitDateString}
                            </div>
                            </div>               
                            </div>
                      </div>
                      <c:if test="${as.statusEnum.info=='成绩发布'}">
                      <div class="form-group">
                            <div class="col-lg-2">
                            <label class="control-label">成绩</label>
                            </div>
                            <div class="col-lg-10">
                            <div class="progress">
                                <div class="progress-bar progress-bar-warning" style="width: ${as.submitScore/as.score * 100}%; font-size: 14px;">${as.submitScore}</div>
                            </div>                
                            </div>
                      </div>
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">作业评语</label>
                            <div class="col-lg-10">
                            <blockquote>
                                <p>${as.review}</p>
                                <small>${as.tname}</small>
                            </blockquote>               
                            </div>
                      </div>
                      </c:if>
                      </c:if>
                      <c:if test="${as.canSubmitHomework}">
                      <form id="homework${as.aid}form" enctype="multipart/form-data">
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">提交作业</label>
                            <div class="col-lg-10  info-field">
                            
                            <input class="homeworkinput" aid=${as.aid} id="homework${as.aid}" name="UpLoadFile" type="file" /> 
                            
							<input class="btn-homework btn btn-info" name="submitHomework${as.aid}" value="上传" aid="${as.aid}" id="submitHomework${as.aid}"/>
                      		</div>
                      </div>
                      </form>
                      </c:if>
            </div>
            <!-- 作业情况 -->
            </c:if>
            
            <c:if test="${auth.canSeeAllHomework}">
            <div class="tab-pane fade" id="${as.aid}records">
            <div class="recordtable" id="${as.aid}recordtable" aid="${as.aid}" canReview="${auth.canReviewHomework && as.canReviewHomework}"></div>
            <br/>
            
            <c:if test="${as.status==2 && auth.isOwnTeacher}">
            <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-4">
            <button aid="${as.aid}" r="1" class="publish-score btn btn-success btn-block" type="button">确认成绩</button>
            </div>
            <div class="col-md-4">
            <button aid="${as.aid}" r="0" class="publish-score btn btn-danger btn-block" type="button">通知重改</button>
            </div>
            <div class="col-md-2"></div>
            </div>
            </c:if>
            
            <c:if test="${auth.canReviewHomework && as.canReviewHomework}">
            <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
            <button aid="${as.aid}" r="1" class="prepare-review btn btn-success btn-block" type="button">提交审核</button>
            </div>
            <div class="col-md-4"></div>
            </div>
            </c:if>
            
            
            </div>
			</c:if>
            

            <div class="tab-pane fade" id="${as.aid}comment">
                  <br>
                  <div class="form-group">
                        <label  class="col-lg-2 control-label info-field">总评</label>
                        <div class="col-lg-10">
                        <div class="panel panel-default">
                        <div class="panel-body">
						<div aid="${as.aid}" id="${as.aid}summaryeditor"></div> 
                        <div aid="${as.aid}" id="${as.aid}summaryeditor-in"> 
                        ${as.summary}
                        </div>
                        
                        </div>
                        </div>               
                        </div>
                  </div>

				  <c:if test="${auth.canAddAssignment}">
                  <div class="row">
                  <div class="col-lg-2"></div>
                  <div class="col-lg-10">
                        <button class="btn btn-success summary-btn" aid="${as.aid}" type="button">修改总评</button>               
                  </div>
                  </div>
                  
                  <div>
                  <div class="col-lg-2"></div>
                  <div class="col-lg-10">
                        <button aid=${as.aid} id="updatereview${as.aid}" class="btn btn-success update-summary-btn" aid="${as.aid}" type="button">确认修改</button>               
                  </div> 
                  </div>
                  
                  <form id="homeworksample${as.aid}form" enctype="multipart/form-data">
                      <div class="form-group">
                            <label class="col-lg-2 control-label info-field">提交作业样例</label>
                            <div class="col-lg-10  info-field">
                            
                            <input class="homeworksample" aid=${as.aid} id="homework${as.aid}" name="UpLoadFile" type="file" /> 
                            
							<input class="btn-homeworksample btn btn-info" name="submitsample${as.aid}" value="上传" aid="${as.aid}" id="submitsample${as.aid}"/>
                      		</div>
                      </div>
                  </form>
                  
                  
                  
                  
                  </c:if>

                  <div class="form-group">
                        <label  class="col-lg-2 control-label info-field">下载答案样例</label>
                        <div class="col-lg-3  info-field">
                        <c:choose>
                        <c:when test="${as.file != null}">
                        <div class="btn btn-success btn-block" onclick="window.location.href='${as.file}'" type="button">下载</div>               
                        </c:when>
                        <c:otherwise>
                        	<div class="btn btn-success btn-block disabled">暂未上传作业样例</div>
                        </c:otherwise>
                        </c:choose>
                        </div>
                        
                  </div>
            </div>
            <div class="tab-pane fade" id="${as.aid}scomment">
            <div id="commentin${as.aid}"></div>
            <div class="col-lg-12"> 
                 <textarea class="scomment" aid="${as.aid}" id="scomment${as.aid}" rows="3"></textarea>

            </div>
            <div class="col-lg-10">
                <button aid="${as.aid}" id="commitcomment${as.aid}" class="btn btn-success commitcommentbtn" aid="${as.aid}" type="button">发表</button>               
            </div>
            </div>

        </div>
        </div>
        </div>
        </div>
        </c:forEach>
	
		<c:if test="${auth.canAddAssignment}">
		<jsp:include page="lesson.addAssignment.jsp"/>
		</c:if>

</div>

<c:if test="${auth.isOwnTeacher}">
<script type="text/javascript">
$('.update-summary-btn').hide().click(function(){
	var aid = $(this).attr('aid');
	var summary = $('#' +  aid + 'summaryeditor').jqxEditor('val');
	$.ajax({
		type: 'POST',
        url:  "assignment/summary",
        dataType: 'json',
        data: {summary: summary, aid: aid},
        success: function(data) {
        	shownotify(data);
        },
	});
	
});




$('.publish-score').click(function() {
	var aid = $(this).attr('aid');
	var r = $(this).attr('r') == '1' ? true : false;
	$.ajax({
		type: 'POST',
		url:  'assignment/status',
		dataType: 'json',
		data:{aid: aid, pass: r},
		success: function(data) {
			shownotify(data);
			$(".publish-score[aid="+ aid + "]").hide();
		},
	});
});

$('.summary-btn').click(function(){
      $(this).hide();
     
      var aid = $(this).attr('aid');
      $('#updatereview' + aid).show();
      var rhtml = $('#' +  aid + 'summaryeditor-in').html();
      $('#' +  aid + 'summaryeditor-in').html('');
      $('#' +  aid + 'summaryeditor').jqxEditor({
                theme: "bootstrap",
                height:"300px",
                width: "100%",
                stylesheets:["bootstrap/css/pastel-stream.css"],
      
      });
       $('#' +  aid + 'summaryeditor').val(rhtml);
});  
$('.btn-homeworksample').click(function() {
    var aid =  $(this).attr('aid');
    console.log(aid);
    $('#homeworksample'+ aid + 'form').ajaxSubmit({
      type: 'post',
      url: 'homework/sample',
      beforeSubmit: function(a, b,c) {
        a.push({name: 'aid',value: aid});
        return true;
      },
      dataType: 'json',
      success: function(data, status) {
      	shownotify(data);
          console.log(data);
      },
    });
});


</script>
</c:if>

<script type="text/javascript">
/*
$('.btn-homework').jqxFileUpload(
    { width: 300, uploadUrl: 'homework', }
  );
*/

  $('.btn-homework').click(function() {
      var aid =  $(this).attr('aid');
      console.log(aid);
      $('#homework'+ aid + 'form').ajaxSubmit({
        type: 'post',
        url: 'homework',
        beforeSubmit: function(a, b,c) {
          a.push({name: 'aid',value: aid});
          return true;
        },
        dataType: 'json',
        success: function(data, status) {
        	shownotify(data);
            if(data.success) {
                $('#' + aid + 'status').text("已提交");
                refreshprogress(false);
            }
            console.log(data);
        },
      });
  });
$('.prepare-review').click(function() {
	var aid = $(this).attr('aid');
	//var r = $(this).attr('r') == '1' ? true : false;
	$.ajax({
		type: 'POST',
		url:  'assignment/prepare',
		dataType: 'json',
		data:{aid: aid},
		success: function(data) {
			shownotify(data);
			$(".prepare-review[aid="+ aid + "]").hide();
		},
	});
});
$('.commitcommentbtn').click(function() {
  var aid = $(this).attr('aid');
  $.ajax({
    type: 'POST',
    url:  'comment',
    dataType: 'json',
    data:{aid: aid, content: $("#scomment" + aid).val(), to : 12, toName: "abc"},
    success: function(data) {
      shownotify(data);
    },
  });
});

$('.scommenttab').on('shown.bs.tab', function (e) {
  var aid=$(this).attr('aid');
  console.log('#commentin'+aid);
  var load = $(this).attr('load');
  if(load == '0') {
      $.ajax({
        type: 'GET',
        url:  'comment',
        dataType: 'html',
        data:{aid: aid},
        success: function(data) {
          $(this).attr('load', '1');
          console.log(data);
          console.log('#commentin'+aid);
          var newc = $('<div>' + data + '</div>').html();
          $('#commentin'+aid).append(newc);
          //$(data).appendTo("#commentin"+aid); //shownotify(data);
        },
      });
   }
});
</script>


<c:if test="${auth.canSeeAllHomework}">
<script type="text/javascript">
$('.recordtabletab[data-toggle="tab"]').on('shown.bs.tab', function (e) {
   var aid = $(this).attr('aid');
   var score = parseInt($(this).attr('score'));
   var datafields = [ 
   {name: 'sid', type: 'number', map:'student>sid'},
   {name: 'name', type: 'string', map:'student>name'},
   {name: 'srid', type: 'number', map:'assignSubmitRecord>srid'},
   {name: 'score', type: 'number', map:'assignSubmitRecord>score'},
   {name: 'review', type: 'string', map:'assignSubmitRecord>review'},
   {name: 'fileUrl', type: 'string', map:'assignSubmitRecord>fileUrl'},
  
    ];

   var columns = [
  { text: '学号', dataField: 'sid', cellsAlign: 'center', width: '15%', align: 'center'  , editable: false},
{ text: '姓名', dataField: 'name', cellsAlign: 'center', align: 'center'  , editable: false, width: '15%'},
{ text: '评分', dataField: 'score', cellsAlign: 'center', align: 'center'  , editable: true,
	columntype: 'numberinput', 
	validation: function(score) {
		return function (cell, value) {
			        if (value < 0 || value > score) {
			            return { result: false, message: "成绩在0~" + score + "之间" };
			        }
			        return true;
			    }
	}(score),
    createeditor: function (row, cellvalue, editor) {
        editor.jqxNumberInput({ decimalDigits: 0});
    }
    },
{ text: '评语', dataField: 'review', cellsAlign: 'center', align: 'center'  , editable: true, width: '35%'},
{
  text: '作业', cellsalign: 'center', align: "center",  width: '15%', columnType: 'none', editable: false, sortable: false, dataField: null, filterable: false, cellsrenderer: function (row, columnfield, value, defaulthtml, config, data) {
      // render custom column.
      if(data['fileUrl'] && data['fileUrl'] != '')
      	return defaulthtml.replace(/<\/div>/, "<a href='" + data['fileUrl'] + "'>下载</a></div>");
      else
        return defaulthtml.replace(/<\/div>/, "未交</div>");
  }
  }

  ];
   var record_table_source = 
  {   
        url: "assignment/records",
        datatype: "json",
        data: {'aid': aid},
        datafields: datafields,
        id: 'srid',
        updaterow: function (rowid, rowdata, commit) {
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failder.
                    var result;
                    //console.log(rowdata);
                    $.ajax({
                        type: 'POST',
                        url:  'assignment/records/review',
                        dataType: 'json',
                        data: {
                            'srid': rowdata.srid,
                            'score': rowdata.score,
                            'review': rowdata.review,
                        },
                        success: function(data) {  
                        	shownotify(data);
                        console.log(data);              
                            result = data.result;                        },
                    });

                    //$('#course-list').jqxGrid('clearselection');
                    commit(result);
                },
    };   

  var recorddataAdapter = new $.jqx.dataAdapter(record_table_source);
  
  var canReview = $('#' + aid + 'recordtable').attr('canReview') ==  'true';
   $('#' + aid + 'recordtable').jqxGrid(
    {   theme: "bootstrap",
                source: recorddataAdapter,
                autoheight: true,
                rowsheight: 35,
                columnsheight: 30,
                showfilterrow: true,
                sortable: true,
                pageable: true,
              
                
                editable: canReview,
                editmode: 'selectedrow',
               
                //selectionMode: 'custom',
                enablebrowserselection: false,
                pagesize: 20,
                altRows: false,
                width:"100%",
                columns:columns,
                filterable: true,
            });
});


</script>
</c:if>