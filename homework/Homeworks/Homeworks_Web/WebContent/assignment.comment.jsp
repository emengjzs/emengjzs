<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
<div class="row">
<c:forEach items="${comments}" var="c">
    
    <div class="col-lg-10">
    <div class="panel panel-default">
    <div class="panel-heading">
         ${c.fromName} --- ${c.time}      
    </div>
    <div class="panel-body">
    <c:if test="${c.toName}">
        @${c.toName}:
     </c:if>
     ${c.conntent}
    </div>
    </div>
    </div>
    <div class="col-lg-2"></div>
</c:forEach>
</div>
</div>