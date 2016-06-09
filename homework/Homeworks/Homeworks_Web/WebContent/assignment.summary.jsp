<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="assignment-summary-in">  
    
<div id="assignmentsummary">
</div>
<h4 id="a-assscore"></h4>
<h4 id="a-avgscore"></h4>
<h4 id="a-maxscore"></h4>
<h4 id="a-num"></h4>
<h4 id="a-avgrate"></h4>
</div>
<script type="text/javascript">
    
$(function () {




var loadasscharts = function(data){
    $('#assignmentsummary').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '作业成绩统计'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '比例',
            data: [
                ['优',   data['perfect']],
                ['良',      data['greate']],
                ['中',    data['good']],
                ['不合格',     data['bad']],
                ['未交',     data['miss']]
            ]
        }]
    });

}


$.ajax({
        type: 'GET',
        url:  'summary/assignment',
        dataType: 'json',
        data:{aid: ${aid}},
        success: function(data) {
            console.log(data);
            
            $('#a-assscore').text('作业总分：' + data['aiisgnmentscore']);
            $('#a-maxscore').text('最高分：' + data['maxscore']);
            $('#a-avgcore').text('平均分：' + data['avgscore']);
            $('#a-avgrate').text('平均得分率：' + data['avgrate']);
            $('#a-num').text('学生人数：' + data['stunum']);
            loadasscharts(data);
        },
});


});     


</script>