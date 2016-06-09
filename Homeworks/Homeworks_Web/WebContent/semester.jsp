<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lessons"   type="java.util.List" scope="request"/>
<jsp:useBean id="semester"   type="logic.Semester" scope="request"/>
<jsp:useBean id="lastSemester"   type="java.lang.String" scope="request"/>
<jsp:useBean id="nextSemester"   type="java.lang.String" scope="request"/>
<jsp:useBean id="role"   type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<div id="contents-in">
<div class="col-sm-12">
    <div class="row">
    <ul class="pager">
        <div class="col-xs-3">
        <c:choose>
        <c:when test="${lastSemester == '-1'}">
        	<li class="previous disabled"><a>&larr; 上学期</a></li>
        </c:when>
        <c:otherwise>
        	<li class="previous"><a href="javascript:void(0)" for="semester/lessons.html?tid=${lastSemester}&role=${role}">&larr; 上学期</a></li>
        </c:otherwise>
        </c:choose>
        </div>
        <div class="col-xs-6">
        <li>
        <h3 class="text-primary" style="margin-top: 0">${semester.string}</h3>
        </li>
        </div>
        <div class="col-xs-3">
        <c:choose>
        <c:when test="${nextSemester == '-1'}">
        	<li class="next disabled"><a>下学期 &rarr;</a></li>
        </c:when>
        <c:otherwise>
        	<li class="next"><a href="javascript:void(0)" for="semester/lessons.html?tid=${nextSemester}&role=${role}">下学期 &rarr;</a></li>
        </c:otherwise>
        </c:choose>
        
        </div>
    </ul>
    </div>
    <div class="row">
    <div class="col-xs-3"></div>
    <div class="col-xs-6">
    	<center>${semester.term.startDateString} 至 ${semester.term.endDateString}  </center>
    </div>
    <div class="col-xs-3"></div>
  	<!-- semester.term.startDateString 
    <div class="col-xs-12">
            <button type="button" class="btn btn-sm btn-success">制定新学期</button>
            <button type="button" class="btn btn-sm btn-warning disabled">修改此学期</button>
    </div>
    -->
    </div>


    <div class="row">
    <div class="col-xs-12">

        <table class="lessontable table table-striped table-hover">
            <thead>
                <tr>
                    <th style="width=30%;">课程号</th>
                    <th>课程名称</th>
                    <th>院系</th>
                    <th>任课教师</th>
                    <th>助教</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lessons}" var="lesson">
                <tr>
                	<td>${lesson.cid }</td>
                    <td><a href="javascript:void(0)" for="lesson.html?lid=${lesson.lid}">${lesson.name }</a></td>
                    <td>${lesson.institute }</td>
                    <td>${lesson.teachersNameString }</td>
                    <td>${lesson.tuitorsNameString }</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
    </div>
    <script type="text/javascript">
        $('#contents-in a[for]').each(function(i) {
            if($(this).attr('for') != "") {
                $(this).click(function() { 
                    $.ajax({
                        type: 'GET',
                        url:  $(this).attr('for'),
                        dataType: 'html',
                        
                        success: function(data) { 
                            $("#contents-in").remove();
                            $(data).appendTo('#contents-out');    
                        },
                    });
                });
            }
        });

    </script>
</div>
</div>
