<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<div id="courseinfo-in">
<c:if test="${canAdd}">    
<button class="btn btn-primary" data-toggle="modal" 
   data-target="#addcoursemodal">
   添加
</button>
<br/>
</c:if>



<div id="lessonusertable">
</div>
<c:if test="${canAdd}">
<jsp:include page="lesson.user.addModel.jsp" flush="true"/>
</c:if>
</div>


<script type="text/javascript">
var luser = "${luser}";
var lid = "${lid}";
    init_add_table({
    id: idMaps[luser],
    jq: "#lessonusertable",
    url: "lesson/" + luser + "?lid=" + lid,
    selectbox: false,
    columns: get_${luser}_columns(),
});
    


</script>
