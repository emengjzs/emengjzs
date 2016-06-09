<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="courseinfo-in">
        <div class="form-group">                     
            <label class="col-xs-2 control-label info-field">课程名</label>
            <div class="col-xs-10">
            <div class="panel panel-default">
            <div class="panel-body">
            ${ lesson.name}
            </div>
            </div>               
            </div>  
        </div>
        <div class="form-group">                     
            <label class="col-xs-2 control-label info-field">课程号</label>
            <div class="col-xs-10">
            <div class="panel panel-default">
            <div class="panel-body">
            ${ lesson.cid}
            </div>
            </div>               
            </div>  
        </div>
        <div class="form-group">                     
            <label class="col-xs-2 control-label info-field">院系</label>
            <div class="col-xs-10">
            <div class="panel panel-default">
            <div class="panel-body">
            ${ lesson.institute}
            </div>
            </div>               
            </div>  
        </div>
        <div class="row form-group" style="margin-left:-1px;">                   
            <label class="col-xs-2 control-label info-field">教师</label>
            <div class="col-xs-10 info-field">
            <c:forEach items="${lesson.teacherAssigns}" var="teacher">
            <span class="label label-info" style="font-size: 14px;font-weight: normal">${teacher.name }</span>  
                       
            </c:forEach>
            </div>  
        </div> 
        <div class="row form-group" style="margin-left:-1px;">                 
            <label class="col-xs-2 control-label info-field">助教</label>
            <div class="col-xs-10 info-field" >
            <c:forEach items="${lesson.tuitorAssigns}" var="student">
            <span class="label label-info" style="font-size: 14px;font-weight: normal">${student.name }</span>  
                       
            </c:forEach>
            </div>  

        </div>       

</div>