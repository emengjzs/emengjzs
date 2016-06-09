<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="assignmentscoretable">

</div>

<script type="text/javascript">
(function load() {
    var lid = ${lid};

    var data_source = 
        {   
            url: 'summary/assignments?lid=' + lid,
            datatype: "json",
            datafields: [
                {name: 'name' , type: 'string'},
                {name: 'avgrate', type: 'float', map: 'avgrate'},
                {name: 'fullscore', type: 'float',map: 'aiisgnmentscore'},
                {name: 'aid', type: 'int'},
                {name: 'avgscore', type: 'float', map: 'avgscore'},
            ],
            id: 'aid',
        };  

    var table_columns = [
       { text: '编号', dataField: 'aid', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '作业', dataField: 'name', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '平均分', dataField: 'avgscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '满分', dataField: 'fullscore', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'count', 'min', 'max'],},
        { text: '平均得分率', dataField: 'avgrate', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['min', 'max'],
        },
        
        
    ];

    var dataAdapter = new $.jqx.dataAdapter(data_source);

    $('#assignmentscoretable').jqxGrid(
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
    
    $('#assignmentscoretable').on('rowselect', function (event) 
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