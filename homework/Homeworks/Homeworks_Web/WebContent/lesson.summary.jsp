<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="lessonsummary">
</div>
<h4 id="l-avgscore"></h4>
<h4 id="l-maxscore"></h4>
<h4 id="l-num"></h4>
<h4 id="l-badrate"></h4>

<script type="text/javascript">
    
$(function () {




var loadcharts = function(data){
    $('#lessonsummary').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '课程成绩统计'
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
                ['良',      data['great']],
                ['中',    data['good']],
                ['不合格',     data['bad']],
            ]
        }]
    });

}


$.ajax({
        type: 'GET',
        url:  'summary/lesson',
        dataType: 'json',
        data:{lid: ${lid}},
        success: function(data) {
            console.log(data);
            loadcharts(data);
            $('#l-maxscore').text('最高分：' + data['maxscore']);
            $('#l-avgcore').text('平均分：' + data['avgscore']);
            $('#l-num').text('学生人数：' + data['stunum']);
            $('#l-badrate').text('挂科率：' + (data['badrate'] *100)+ "%");
        },
});


});     


</script>