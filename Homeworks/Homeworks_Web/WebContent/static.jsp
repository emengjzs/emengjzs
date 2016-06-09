<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<div id="contents-in">
<div class="col-lg-2"></div>
<div class="col-lg-4">
<div id="monthdate"></div>
</div>
<div class="col-lg-4">
<button id="searchbutton" class="btn btn-success">查看</button>
</div>
<div class="col-lg-2"></div>


<div class="col-lg-12">

    <center><h1>${month}统计报告</h1></center> 
</div>


<div class="col-lg-12">
<div class="page-header">   
    <h2>课程统计</h2> 
</div>
</div>
<div class="col-lg-12">
<h3>本月共有${lessons}门课程进行中</h3>
</div>

<div class="col-lg-12">
<div id="assignmentcontainer"></div>
</div>

<div class="col-lg-12">
<div class="page-header">   
    <h2>作业统计</h2> 
</div>
</div>
<div class="col-lg-12">
    
<div id="container"></div>

</div>

<div class="col-lg-12">
    
<div id="container"></div>
<div id="unsubmitcontainer"></div>
</div>





<div class="col-lg-12">
<div class="page-header">   
    <h2>学生统计</h2> 
</div>
<div class="col-lg-12">
    <div id="scorecontainer"></div>
</div>


</div>

<div class="col-lg-12">
<div class="page-header">   
    <h2>助教统计</h2>
</div>
<div class="col-lg-12">
    <div id="tuitorcontainer"></div>
</div>
</div>

<div class="col-lg-12">
<div class="page-header">   
    <h2>教师统计</h2> 
</div>
<div class="col-lg-12">
    <div id="teachercontainer"></div>
</div>
</div>



</div>
<script type="text/javascript">
$('#monthdate').jqxDateTimeInput({
    theme: "bootstrap",
    width: '90%',
    height: 35,
    value: ${jsmonth},
    formatString: "yyyy/MM",
}); 
$('#searchbutton').click(function() {
	var d = $('#monthdate').val();
	$.ajax({
		url:  "statis",
        dataType: 'html',
        data: {d: d},
        success: function(data) {
        	 $("#contents-in").remove();
             $(data).appendTo("#contents-out"); 
        }
	});
});
</script>
<script type="text/javascript">
// 作业提交统计
$(function () {
	
	var submitArray = ${submitArray};
	
	
    $('#container').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '作业提交统计'
        },
        xAxis: {
            categories: getcolarray(submitArray, 0),
            labels: {
               // rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            },
            title: {text: '天数'}
        },
        yAxis: {
            min: 0,
            title: {
                text: '提前提交作业的人数'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: ' <b>天前提交作业有{point.y}人</b>',
        },
        series: [{
            name: 'Population',
            data: getcolarray(submitArray, 1),
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
});

</script>
<script type="text/javascript">
// 作业、分数统计
    $(function () {
        var homeworkArray = ${homeworkArray};
    $('#assignmentcontainer').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: '课程布布数及分数统计图'
        },
        xAxis: [{
            categories: getcolarray(homeworkArray, 1),
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}分',
                style: {
                    color: '#89A54E'
                },
                allowDecimals: false,
            },
            title: {
                text: '平均设置总分',
                style: {
                    color: '#89A54E'
                }
            },
            min: 0,
            max: 100,
        }, { // Secondary yAxis
            title: {
                text: '作业数',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value}次作业',
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            //layout: 'vertical',
            align: 'center',
            //x: 120,
            verticalAlign: 'bottom',
            //y: -40,
            floating: false,
            backgroundColor: '#FFFFFF'
        },
        series: [{
            name: '作业数',
            color: '#4572A7',
            type: 'column',
            yAxis: 1,
            data: getcolarray(homeworkArray, 2),
            tooltip: {
                valueSuffix: '次'
            }

        }, {
            name: '平均设置总分',
            color: '#89A54E',
            type: 'spline',
            data: getcolarray(homeworkArray, 3),
            tooltip: {
                valueSuffix: '分'
            }
        }]
    });
});



</script>



<script type="text/javascript">
// 未交作业统计
$(function () {
    
    var unsubmitArray = ${unsubmitArray};
    
    
    $('#unsubmitcontainer').highcharts({
        chart: {

           // type: 'spline',
            inverted: true,
        //},
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '未提交作业统计'
        },
        xAxis: {
            categories: getcolarray(unsubmitArray, 2),
            labels: {
               // rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            },
            title: {text: '次数'}
        },
        yAxis: {
            min: 0,
            title: {
                text: '未交作业次数'
            },
            allowDecimals: false,
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: ' <b>{point.y}次未交作业</b>',
        },
        series: [{
            name: 'Population',
            data: getcolarray(unsubmitArray, 1),
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
});
</script>

<script type="text/javascript">
    // 分数统计
$(function () {
    var scoreArray = ${scoreArray};
   
    $('#scorecontainer').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '作业得分率统计'
        },
        xAxis: {
            categories: getcolarray(scoreArray, 0),
            labels: {
               // rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            },
            title: {text: '得分率'}
        },
        yAxis: {
            min: 0,
            title: {
                text: '人数'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: ' <b>有{point.y}人</b>',
        },
        series: [{
            name: 'Population',
            data: getcolarray(scoreArray, 1),
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            },
            tooltip: {
                valueSuffix: '分'
            }
        }]
    });
});

</script>
<script type="text/javascript">
// 助教批改分数统计
    $(function () {
        var tuitorArray = ${tuitorArray};
    $('#tuitorcontainer').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: '助教批改分数统计图'
        },
        xAxis: [{
            categories: getcolarray(tuitorArray, 0),
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                style: {
                    color: '#89A54E'
                },
                allowDecimals: false,
                formatter: function() {
                    return this.value+"%";
                },
            },
            title: {
                text: '平均批改得分率',
                style: {
                    color: '#89A54E'
                }
            },
            min: 0,
            max: 100,
        }, { // Secondary yAxis
            title: {
                text: '批改数',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value}次作业',

                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            //layout: 'vertical',
            align: 'center',
            //x: 120,
            verticalAlign: 'bottom',
            //y: -40,
            floating: false,
            backgroundColor: '#FFFFFF'
        },
        series: [{
            name: '批改数',
            color: '#4572A7',
            type: 'column',
            yAxis: 1,
            data: getcolarray(tuitorArray, 1),
            tooltip: {
                valueSuffix: '次'
            }

        }, {
            name: '平均批改得分',
            color: '#89A54E',
            type: 'spline',
            data: getcolarray(tuitorArray, 2),
            tooltip: {
                valueSuffix: '%'
            }
        }]
    });
});

</script>
<script type="text/javascript">
// 作业、分数统计
    $(function () {
        var homeworkArray = ${teacherArray};
    $('#teachercontainer').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: '教师发布作业及分数统计图'
        },
        xAxis: [{
            categories: getcolarray(homeworkArray, 1),
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}分',
                style: {
                    color: '#89A54E'
                },
                allowDecimals: false,
            },
            title: {
                text: '平均学生得分',
                style: {
                    color: '#89A54E'
                }
            },
            min: 0,
            max: 100,
        }, { // Secondary yAxis
            title: {
                text: '作业数',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value}次作业',
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            //layout: 'vertical',
            align: 'center',
            //x: 120,
            verticalAlign: 'bottom',
            //y: -40,
            floating: false,
            backgroundColor: '#FFFFFF'
        },
        series: [{
            name: '作业数',
            color: '#4572A7',
            type: 'column',
            yAxis: 1,
            data: getcolarray(homeworkArray, 2),
            tooltip: {
                valueSuffix: '次'
            }

        }, {
            name: '学生平均得分',
            color: '#89A54E',
            type: 'spline',
            data: getcolarray(homeworkArray, 3),
            tooltip: {
                valueSuffix: '分'
            }
        }]
    });
});



</script>

</body>
</html>

