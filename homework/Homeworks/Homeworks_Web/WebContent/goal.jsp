<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="contents-in">
<h2>战略计划</h2>
<c:forEach items="${goals}" var="c">
    
    <div class="col-lg-12">
    <div class="panel panel-default">
    <div class="panel-heading">${c.yearStr}战略计划</div>
    <div class="panel-body">
        ${c.content}
    </div>
    </div>
    </div>
</c:forEach>

<c:if test="${user.firstRole.str == 'prime' }">
<form  class="form-horizontal">
            <div class="form-group">
                    <label for="inputTitle" class="col-lg-1 control-label">战略</label>
                    <div class="col-lg-11">
                        <textarea id="editor"></textarea>                  
                    </div>
            </div> 
            <button id="addgoalButton" type="submit" class=" btn btn-success" onclick='return false;'>发布</button> 
</form>
</c:if>

</div>
<script type="text/javascript">
(function() {
     $('#editor').jqxEditor({
                    theme: "bootstrap",
                    height:"500px",
                    width: '100%',
                    stylesheets:["bootstrap/css/pastel-stream.css"],
          
                }); 


        $('#addgoalButton').click(function() {
            var input = {
              content : $('#editor').val(),          
            };

    
            console.log(input);
            $.ajax({
                    type: 'POST',
                    url:  "goal",
                    dataType: 'json',
                    data: input,
                    success: function(data) { 
                        shownotify(data);
                        if(data.success) {
                          
                        }
                    },
            });
        });

})();
</script>