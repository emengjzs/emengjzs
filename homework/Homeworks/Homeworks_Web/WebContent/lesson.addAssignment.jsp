<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
    <!-- model-->
       
<!-- 模态框（Modal） -->
<div class="modal fade" id="addassignment" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" style="width: 65%">
<div class="modal-content">
     <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               发布新作业
            </h4>
     </div>
     <div class="modal-body">
     <div class="row">
         <div class="col-sm-1"></div>
         <div class="col-sm-10">
         
            
            <form id="assignment" class="form-horizontal">
            <fieldset>
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">作业标题</label>
                    <div class="col-lg-9">
                        <input type="text" class="form-control" id="inputTitle" placeholder="作业标题">                   
                    </div>
              </div>
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">作业描述</label>
                    <div class="col-lg-9">
                        <textarea id="editor"></textarea>                  
                    </div>
              </div> 
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">作业难度</label>
                    <div class="col-lg-9">
                        <div id="level"></div>         
                    </div>
              </div> 
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">作业总分</label>
                    <div class="col-lg-9">
                        <div id="score"></div>         
                    </div>
              </div> 
               <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">提交格式</label>
                    <div class="col-lg-9">
                        <div id="fileformat"></div>         
                    </div>
              </div> 
              <div class="col-lg-12"><h5>近期作业截止日期</h5></div>
              <c:forEach items="${recent}" var="s">
              <div class="col-lg-12"><p>${s.deadlineString}</p></div>

              </c:forEach>
              <div class="col-lg-12"><p>2015-06-18 00:00:00</p></div>
              <div class="col-lg-12"><p>2015-06-20 00:00:00</p></div>
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">提交截止日期</label>
                    <div class="col-lg-9">
                        <div class="calendar" id="deadline"></div>      
                    </div>
              </div>    
              <div class="form-group">
                    <label for="inputTitle" class="col-lg-2 control-label">批改截止日期</label>
                    <div class="col-lg-9">
                         <div class="calendar" id="reviewDeadline"></div>           
                    </div>
              </div>  
            </fieldset>
            </form>
          </div> 
          <div class="col-sm-1"></div>
        </div>
        </div>
         <div class="modal-footer" style="margin-top: -5px">
         <div class="row">      
            <div class="col-sm-6"></div>
            <div class="col-sm-3">
            <button id="addAssignmentButton" type="submit" class=" btn btn-block btn-success" onclick='return false;'>添加</button>                      
            </div>
            <div class="col-sm-3">
            <button class="btn btn-block btn-warning ">撤销</button>
            </div>                      
         </div>
         </div>
</div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
<!-- model-->

<!-- -->
<script type="text/javascript">
$('#addassignment').on('shown.bs.modal', function() {
            $('#editor').jqxEditor({
                theme: "bootstrap",
                height:"500px",
                width: '100%',
                stylesheets:["bootstrap/css/pastel-stream.css"],
      
            }); 
            $('.calendar').jqxDateTimeInput({
                theme: "bootstrap",
                width: '90%',
                height: 35,
                formatString: "yyyy/MM/dd HH:mm:ss",
            });       
           $('#fileformat').jqxDropDownList({
                theme: "bootstrap",
                width: '90%',
                height: 35,
                source: ['zip', 'rar', 'doc', 'pdf', 'txt', 'ppt']
            });       
           $('#score').jqxNumberInput({
                theme: "bootstrap",
                decimal: 100,
                width: '90%',
                height: 35,
                min: 1,
                max: 100,
                spinButtons: true,
                inputMode:'simple',
                decimalDigits: 0,
                textAlign: "left",
            });       



           $('#level').jqxSlider({height: 35, width: '90%', min: 0, max: 10, theme: "bootstrap",mode: "fixed", ticksFrequency: 1, value: 1, step:1});
});
$('#addAssignmentButton').click(function() {
    var input = {
      lid: ${lid},
      title: $('#inputTitle').val(),
      description : $('#editor').val(),
      level : $('#level').jqxSlider('value'),
      score:  $('#score').jqxNumberInput('getDecimal'),
      fileformat: $('#fileformat').val(),
      deadline: $('#deadline').val(),
      reviewDeadline: $('#reviewDeadline').val(),
    };

    
    console.log(input);
    var refresh = false;
    $.ajax({
            type: 'POST',
            url:  "assignment",
            dataType: 'json',
            data: input,
            success: function(data) { 
                shownotify(data);
                if(data.success) {
                   refresh = true;
                   $('#addassignment').modal('hide'); 
                }
            },
    });

    $('#addassignment').on('hidden.bs.modal', function() {
        if(refresh) {
          changepage('#courseinfo-in', '#courseinfo-out', 'lesson/assignments.html?lid=${lid}');
        }
    });

});
</script>