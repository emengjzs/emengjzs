<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lesson"   type="entity.LessonPO" scope="request"/>
<jsp:useBean id="auth"   type="authority.LessonAuthority" scope="request"/>
<div id="contents-in">

     <br/>   
    <div>
    <div style="text-align: center; margin-top:-6px"><h3 class="text-primary">课程 ${lesson.name}</h3></div>
    <ul class="nav nav-pills">
        <li class="active"><a for="lesson/info.html?lid=${lesson.lid}" href="javascript: void(0)">课程信息</a></li>
        <c:choose>
        <c:when test="${auth.canSeeOwnHomework}">
        <li><a id="refreshAssign" for="lesson/assignments/forstudents.html?lid=${lesson.lid}" href="javascript: void(0)">作业管理</a></li>
        </c:when>
        <c:otherwise>
        <li><a id="refreshAssign" for="lesson/assignments.html?lid=${lesson.lid}" href="javascript: void(0)">作业管理</a></li>
        </c:otherwise>
        </c:choose>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">课程管理 <span class="caret"></span></a>
            <ul class="dropdown-menu">
            
            <c:choose>
            <c:when test="${auth.canAddTeacher}">
                <li><a for="lesson/teachers.html?lid=${lesson.lid}" href="javascript: void(0)">教师管理</a></li>
            </c:when>
            <c:otherwise>
            	<c:if test="${auth.canSeeTeacherAndTuitor}">    
                <li><a for="lesson/teachers.html?lid=${lesson.lid}" href="javascript: void(0)">教师查看</a></li>
                </c:if> 
            </c:otherwise>            
            </c:choose>
            
            <c:choose>
            <c:when test="${auth.canAddStudentAndTuitors}">    
                <li><a for="lesson/tuitors.html?lid=${lesson.lid}" href="javascript: void(0)">助教管理</a></li>
            </c:when> 
            <c:otherwise>
            	<c:if test="${auth.canSeeTeacherAndTuitor}">    
                <li><a for="lesson/tuitors.html?lid=${lesson.lid}" href="javascript: void(0)">助教查看</a></li>
                </c:if> 
            </c:otherwise>
            </c:choose>
            
            <c:choose>
            <c:when test="${auth.canAddStudentAndTuitors}">  
                <li><a for="lesson/students.html?lid=${lesson.lid}" href="javascript: void(0)">学生管理</a></li>
            </c:when> 
            <c:otherwise>
            	<c:if test="${auth.canSeeStudent}">  
                <li><a for="lesson/students.html?lid=${lesson.lid}" href="javascript: void(0)">学生查看</a></li>
                </c:if> 
            </c:otherwise>
            </c:choose>   
            </ul>   
           
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">成绩管理 <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="homework/scores?lid=${lesson.lid}" class="disabled">课程成绩汇总</a></li>
                <c:if test="${auth.canSeeOwnHomework}">
                <li><a for="lesson/score.html?lid=${lesson.lid}" href="javascript: void(0)" class="disabled">作业成绩统计</a></li>
                </c:if>
                <c:if test="${auth.canSeeAllHomework}">
                <li><a for="lesson/students/score.html?lid=${lesson.lid}" href="javascript: void(0)" class="disabled">课程成绩管理</a></li>
                </c:if>
            </ul>
        </li>
    </ul>
    </div>
	<br>

    <div>
    <!-- Tab -->
    <div class="panel panel-default">
    <div class="panel panel-body">

    <!-- 替换内容-->
    <div id="courseinfo-out">
    <div id="courseinfo-in">
        <jsp:include page="lesson.info.jsp"/>
    </div>
    </div>
    <!-- 替换内容-->
    
    </div>
    </div>
    <!--- Tab -->
    </div>


</div>

<script type="text/javascript">
addClickListener('#contents-in a[for]', '#courseinfo-in','#courseinfo-out');
</script>