<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="contents-in">

<h2>院系综合统计</h2>
<div id="lessons-summary-out">
    <div id="institutes">
    </div>
</div>
<h2>全校课程信息报表</h2>
<div id="lessons-summary-out">
    <div id="lessons-summary-in">
    </div>
</div>
<br/>
<div id="assignments-summary-out">

    <div id="assignments-summary-in">
    </div>
</div>
</div>

<script type="text/javascript">
(function load() {


    var data_source = 
        {   
            url: 'summary/lessons',
            datatype: "json",
            datafields: [
                {name: 'lessonname' , type: 'string'},
                {name: 'institute', type: 'string'},
                {name: 'term' , type: 'string'},
                {name: 'perfect', type: 'int'},
                {name: 'great', type: 'int'},
                {name: 'good', type: 'int'},
                {name: 'bad', type: 'int'},
                {name: 'avgscore', type: 'float'},
                {name: 'maxscore', type: 'float'},
                {name: 'badrate', type: 'float'},
                {name: 'stddev', type: 'float'},
                {name: 'lid', type: 'int'},
                {name: 'stunum', type: 'int'},
            ],
            id: 'lid',
        };  

    var table_columns = [
    { text: '院系', dataField: 'institute',  sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false',filtertype: 'checkedlist'},
       { text: '编号', dataField: 'lid', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
       { text: '学期', dataField: 'term', sortable: 'true', filterable: 'true', filtertype: 'checkedlist',align: 'center', cellsalign: 'center', editable: 'false',hidden: 'true', hideable:'true'},
        { text: '课程', width:"15%", dataField: 'lessonname', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '平均分', dataField: 'avgscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '最高分', dataField: 'maxscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'min', 'max'],},
        { text: '标准差', dataField: 'stddev', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '学生人数', dataField: 'stunum', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '优秀人数', dataField: 'perfect', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '良好人数', dataField: 'great', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '中等人数', dataField: 'good', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '不合格人数', dataField: 'bad', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '挂科率', dataField: 'badrate', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['min', 'max','avg'],
        },
        
        
    ];

    var dataAdapter = new $.jqx.dataAdapter(data_source);

    $('#lessons-summary-in').jqxGrid(
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
    $('#lessons-summary-in').jqxGrid('addgroup', 'institute');
    $('#lessons-summary-in').on('rowselect', function (event) 
    {
        var args = event.args;
        var rowBoundIndex = args.rowindex;
        var rowData = args.row;
       $.ajax({
        type: 'GET',
        url:  "lesson/students/score.html",
        dataType: 'html',
        data: {lid: rowData['lid']},
        success: function(data) { 
            $("#assignments-summary-in").remove();
            $('<div id="assignments-summary-in"></div>').appendTo('#assignments-summary-out');
            $(data).appendTo('#assignments-summary-in'); 
        },
    });



    });

    


})();


(function load() {


    var data_source = 
        {   
            url: 'institutes/summary',
            datatype: "json",
            datafields: [
                //{name: 'lessonname' , type: 'string'},
                {name: 'institute', type: 'string'},
                {name: 'index', type: 'int'},
                                {name: 'term' , type: 'string'},
                {name: 'perfect', type: 'int'},
                {name: 'great', type: 'int'},
                {name: 'good', type: 'int'},
                {name: 'bad', type: 'int'},
                {name: 'avgscore', type: 'float'},
                {name: 'maxscore', type: 'float'},
                {name: 'avgrate', type: 'float'},
                //{name: 'stddev', type: 'float'},
                //{name: 'lid', type: 'int'},
                {name: 'count', type: 'int'},
            ],
        };  

    var table_columns = [
    { text: '院系', dataField: 'institute',width: '15%', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
       { text: '学期', dataField: 'term', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
    
        { text: '平均分', dataField: 'avgscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '发展指数', dataField: 'index', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '最高分', dataField: 'maxscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'min', 'max'],},
        { text: '学生人数', dataField: 'count', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '优秀人数', dataField: 'perfect', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '良好人数', dataField: 'great', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '中等人数', dataField: 'good', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '不合格人数', dataField: 'bad', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '挂科率', dataField: 'avgrate', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['min', 'max','avg'],
        },
        
        
    ];

    var dataAdapter = new $.jqx.dataAdapter(data_source);

    $('#institutes').jqxGrid(
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
    $('#institutes').jqxGrid('addgroup', 'term');
    

})();
</script>