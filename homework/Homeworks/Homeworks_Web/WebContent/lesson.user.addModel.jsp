<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="luser"   type="java.lang.String" scope="request"/>
<!-- 模态框（Modal） -->
<div class="modal fade" id="addcoursemodal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            
            <h4 class="modal-title" id="myModalLabel">
               添加${luser}
            </h4>
            
         </div>
         <div class="modal-body">
         <div class="row">
         <div class="col-sm-1"></div>
         <div class="col-sm-10">
         <div id="lessonaddusertableout">
         <div id="lessonaddusertable" user="${luser}" lid="${lid}"></div>
         </div>
         </div>
         <div class="col-sm-1"></div>
         </div>
         </div>
         <div class="modal-footer" style="margin-top: -5px">
         <div class="row">      
            <div class="col-sm-6"></div>
            <div class="col-sm-3">
            <button id="lesson-addUser" type="submit" class=" btn btn-block btn-success" onclick='return false;'>添加</button>                      
            </div>
            <div class="col-sm-3">
            <button class="btn btn-block btn-warning ">撤销</button>
            </div>                      
         </div>
         </div>
      </div><!-- /.modal-content -->
    </div>
</div><!-- /.modal -->
<script type="text/javascript">

$('#addcoursemodal').unbind('show.bs.modal', modelfunction)
$('#lesson-addUser').unbind('click', addcallback);

	var addcallback = function() {
	    var addUserlist = get_selected_id('#lessonaddusertable');
	    var luser = $('#lessonaddusertable').attr('user');
	    var lid =  $('#lessonaddusertable').attr('lid');
	    console.log(addUserlist);
	    $.ajax({
	            type: 'POST',
	            url:  'lesson/' + luser,
	            dataType: 'json',
	            data: {ids: addUserlist,
	                   lid: lid},
	            success: function(data) {   
                  shownotify(data);
                  if(data.success) {
                     $('#addcoursemodal').modal('hide');
                     
                  }                
	                console.log(data);
	            },
	    });
	    return false;
	};
	$('#lesson-addUser').click(addcallback);




var modelfunction = function () {
	
	var luser = $('#lessonaddusertable').attr('user');
	console.log(luser);
    var lid =  $('#lessonaddusertable').attr('lid');
	init_add_table({
	    id: idMaps[luser],
	    jq: "#lessonaddusertable",
	    url: "lesson/except/" + luser + "?lid=" + lid,
	    selectbox: true,
	    columns: get_${luser}_columns(),
	});
};


    
$('#addcoursemodal').on('show.bs.modal', modelfunction);
$('#addcoursemodal').on('hidden.bs.modal', function () {
	$('#lessonaddusertable').jqxGrid('destroy');
	$('#lessonaddusertableout').append('<div id="lessonaddusertable" user="${luser}" lid="${lid}"></div>');
   $('#lessonusertable').jqxGrid('updatebounddata');
});



</script>