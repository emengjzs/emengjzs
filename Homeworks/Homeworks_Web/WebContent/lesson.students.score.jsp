<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="courseinfo-in">
<h2>${lesson.name}课程成绩表</h2>
<div class="col-lg-12">
成绩计算方法： ${lesson.scoreDescription}
</div>
</br>
<div class="col-lg-12">

<c:if test="${auth.isOwnTeacher}">
<form id="uploadscoreform" enctype="multipart/form-data">
<div class="form-group">
<input id="uploadscore" class="score" lid="${lid}" name="UpLoadFile" type="file" />                        
<input class="btn btn-info" name="submitscore" value="上传成绩Excel表" lid="${lid}" id="submitscore"/>
</div>
</form>
</c:if>

</div>
</br>
<div id="termsummary">
</div>

<div class="col-lg-12">
<h2>作业成绩概况报表</h2>
<jsp:include page="lesson.assignments.summary.jsp"/>
</div>

<div class="col-lg-6">
<h2>课程总体成绩概况统计图</h2>

<jsp:include page="lesson.summary.jsp"/>
</div>
<div class="col-lg-6">
<h2>作业成绩概况统计图</h2>
<div id="assignment-summary-out">
    <div id="assignment-summary-in">  
    </div>
</div>

</div>
</div>
<script type="text/javascript">
(function load() {
    
    var data_source = 
        {   
            url: "score/lesson?lid=${lid}",
            datatype: "json",
            datafields: [
                {name: 'studentname' , type: 'string'},
                {name: 'term', type: 'term'},
                {name: 'score', type: 'float'},
                {name: 'homeworkScore', type: 'float'},
                {name: 'examScore', type: 'float'},
                {name: 'rank', type: 'int'},
                {name: 'institute', type: 'string'},
                {name: 'coursename', type: 'string'},
               {name: 'GPA', type: 'float'},
            ],
            id: 'rid',
        };  

    var table_columns = [
        { text: '学生名', dataField: 'studentname', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '作业成绩', dataField: 'homeworkScore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'stdev','count', 'min', 'max',],
        },
        { text: '考试成绩', dataField: 'examScore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'stdev','count', 'min', 'max',],
        },

        { text: '课程成绩', dataField: 'score', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'stdev','count', 'min', 'max',
                    { '<b>不合格人数</b>':
                            function (aggregatedValue, currentValue, column, record) {
                                var s = 0;
                                if(record['score'] < 60) {s= 1;}
                                return aggregatedValue + s;
                            }
                      },
                                      


            ],},
        { text: '排名', dataField: 'rank', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['min', 'max'],
        },
        {
                text: '学分绩', editable: false, datafield: 'GPA', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg'],
                
        },
   
    
    ];

    var dataAdapter = new $.jqx.dataAdapter(data_source);

    $('#termsummary').jqxGrid(
        {   theme: "bootstrap",
            source: dataAdapter,
            altRows: true,
            sortable: true,
            scrollmode: 'default',
            enablehover: false,
            selectionmode: 'singlerow',
            width:"100%",
            height: 500,
            columnsheight: 38,
            showstatusbar: true,
            statusbarheight: 150,
            rowsheight: 35,
            columns:table_columns,
            showfilterrow: true,
            filterable: true,
            groupable:true,
            showaggregates: true,
        });  
    //$('#termsummary').jqxGrid('addgroup', 'term');



    $('#submitscore').click(function() {
        var lid =  $(this).attr('lid');
        console.log(lid);
        $('#uploadscoreform').ajaxSubmit({
          type: 'post',
          url: 'homework/scores',
          beforeSubmit: function(a, b,c) {
            a.push({name: 'lid',value: lid});
            return true;
          },
          dataType: 'json',
          success: function(data, status) {
            shownotify(data);
              console.log(data);
          },
        });
    });


})();
</script>