<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="courseinfo-in">
<div class="col-lg-3">
总成绩：${record.score}
</div>
<div class="col-lg-3">
作业成绩： ${record.homeworkScore}
</div>
<div class="col-lg-3">
考试成绩： ${record.score-record.homeworkScore}
</div>
<div class="col-lg-12">
计算方法： ${lesson.scoreDescription}
</div>
<div class="col-lg-12">
排名： ${record.rank}
</div>
<div class="col-lg-12">
<div id="lessonscoretable">

</div>

<div class="col-lg-6">
<h2>课程总体成绩报表</h2>

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
</div>
<script type="text/javascript">
(function load() {
    var lid = ${lid};

    var data_source = 
        {   
            url: 'assignments/forstudents?lid=' + lid,
            datatype: "json",
            datafields: [
                {name: 'name' , type: 'string'},
                {name: 'submitscore', type: 'float', map: 'ar>score'},
                {name: 'fullscore', type: 'float',map: 'as>score'},
                {name: 'aid', type: 'int'},
                {name: 'rank', type: 'int', map: 'ar>rank'},
                {name: 'status', type: 'int', map: 'ar>status'},
                {name: 'reviewer', type: 'string', map:'ar>tname'},
            ],
            id: 'aid',
        };  

    var table_columns = [
     { text: '编号', dataField: 'aid', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '作业', dataField: 'name', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '成绩', dataField: 'submitscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '满分', dataField: 'fullscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '排名', dataField: 'rank', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['min', 'max'],
        },
        { text: '状态', dataField: 'status', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false',
        cellsrenderer: function (row, columnfield, value, defaulthtml, columnproperties) {
                if (value == 1) {
                    return defaulthtml.replace(/1<\/div>/, "未交</div>");
                }
                else {
                    return defaulthtml.replace(/0<\/div>/, "已交</div>");
                }
            }  
         },
        { text: '批改人', dataField: 'reviewer', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        
    ];

    var dataAdapter = new $.jqx.dataAdapter(data_source);

    $('#lessonscoretable').jqxGrid(
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
            statusbarheight: 100,
            rowsheight: 35,
            columns:table_columns,
            showfilterrow: true,
            filterable: true,
            groupable:true,
            showaggregates: true,
        });  
    

        $('#lessonscoretable').on('rowselect', function (event) 
    {
        var args = event.args;
        var rowBoundIndex = args.rowindex;
        var rowData = args.row;
        $.ajax({
            type: 'GET',
            url:  "summary/assignment.html",
            dataType: 'html',
            data: {aid: rowData['aid']},
            success: function(data) { 
                $("#assignment-summary-in").remove();
                //$('<div id="assignments-summary-in"></div>').appendTo('#assignments-summary-out');
                $(data).appendTo('#assignment-summary-out'); 
            },
        });    
    });

})();

</script>