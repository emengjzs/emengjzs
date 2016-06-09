<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="contents-in">
<div id="termsummary">
</div>
</div>

<script type="text/javascript">
(function load() {
    
    var data_source = 
        {   
            url: "${url}",
            datatype: "json",
            datafields: [
                {name: 'studentname' , type: 'string'},
                {name: 'term', type: 'term'},
                {name: 'score', type: 'float'},
                {name: 'rank', type: 'int'},
                {name: 'institute', type: 'string'},
                {name: 'coursename', type: 'string'},
               {name: 'GPA', type: 'float'},
            ],
            id: 'rid',
        };  

    var table_columns = [
        { text: '课程名', dataField: 'coursename', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false'},
        { text: '成绩', dataField: 'score', sortable: 'true', filterable: 'true', align: 'center', cellsalign: 'center', editable: 'false', aggregates: ['avg', 'stdev','count', 'min', 'max',
                    { '<b>不合格科目数</b>':
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
        { text: '学期', dataField: 'term', sortable: 'true', filterable: 'true',filtertype: 'checkedlist'
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
    $('#termsummary').jqxGrid('addgroup', 'term');



})();
</script>