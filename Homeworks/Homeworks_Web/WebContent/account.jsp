<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="contents-in">
<div>
<div class="panel panel-default">
        <div class="panel panel-body">
            <div class="form-group">
                <label class="col-lg-2 control-label info-field">工号</label>
                <div class="col-lg-10">
                <div class="panel panel-default">
                <div class="panel-body">
                 ${user.ID}
                </div>
                </div>               
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label info-field">名称</label>
                <div class="col-lg-10">
                <div class="panel panel-default">
                <div class="panel-body">
                 ${user.name}
                </div>
                </div>               
                </div>
            </div>
            <c:if test="${user.firstRole.str=='student' || user.firstRole.str == 'teacher'}">
             <div class="form-group">
                <label class="col-lg-2 control-label info-field">院系</label>
                <div class="col-lg-10">
                <div class="panel panel-default">
                <div class="panel-body">
                 ${user.institute}
                </div>
                </div>               
                </div>
            </div>
            </c:if>
            
             <div class="form-group">
                <label class="col-lg-2 control-label info-field">角色</label>
                <div class="col-lg-10">
                <div class="panel panel-default">
                <div class="panel-body">
                 ${user.firstRole.info}
                 <c:if test="${user.secondRole.str !='default'}">
                 ${user.secondRole.info}
                 </c:if>
                </div>
                </div>               
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label info-field">修改密码</label>
                <div class="col-lg-10">
                        <div class="input-group">
                        <input id="passwordinput" class="form-control" type="password" placeholder="输入新密码"></input>
                            <span class="input-group-btn">
                            <button id="changepassword" class="btn btn-warning" type="button">修改密码</button>
                            </span>
                        </div>        
                </div>
            </div>
        </div>
</div>
</div>
</div>
 <script type="text/javascript">
$('#changepassword').click(function() {
    var password = $('#passwordinput').val();
            $.ajax({
                        type: 'POST',
                        url:  'user/password',
                        dataType: 'json',
                        data: {password: password}, 
                        success: function(data) {                               
                            shownotify(data);
                        },          
            }); 
});

 </script>