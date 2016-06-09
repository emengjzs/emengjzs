<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%-- 
<jsp:useBean id="user" type="entity.User" scope="session"/>
--%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homeworks | 课程作业管理系统</title>
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <link rel="stylesheet" href="jqwidgets/styles/jqx.bootstrap.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="bootstrap/css/pastel-stream.css" />
    <link rel="stylesheet" type="text/css" href="css/self.css" />  
    <link rel="stylesheet" type="text/css" href="css/jquery.toastmessage.css"/>   
</head>
<body>
    <div id="messagenotify"></div>
    <!-- 顶部导航栏-->
    <div class="navbar navbar-default navbar-fixed-top">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Homeworks</a>
        </div>
        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">
                <li><a href="javascript:void(0);">任务进度
                <span id="notify"></span></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                
                <li>&nbsp</li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">用户： ${user.name}<b class="caret "></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#" for="user" one="账户管理" two="账户信息">账户信息</a></li>
                        <li class="divider"></li>
                        <li><a href="logout">注销</a></li>
                    </ul>
                </li>
                <li>&nbsp&nbsp&nbsp&nbsp</li>
            </ul>
        </div>
    </div>
    <!-- 顶部导航栏结束-->
    
    
    <div class="container-fluid">
        <div class="row">
            <!-- 左侧导航栏 id = main-nav -->
            <div class="col-sm-3 ">
                <ul id="main-nav" class="nav nav-tabs nav-stacked">
                    <li class="active">                      
                        <a href="javascript:void(0)"> 
                            <i class="glyphicon glyphicon-th-large"> 
                             功能菜单
                            </i>                           
                        </a>

                    </li>
                      
                    <c:if test="${user.firstRole.str == 'admin' }">
                    
                    <li>
                        <a href="#s1" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-user">       
                            </span>     
                            <span class="left-menu-first">用户管理</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s1" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="student.html" one="管理任务" two="学生管理">
                                 <span class="left-menu-second glyphicon glyphicon-user"></span>
                                 <span class="left-menu-first" > 学生管理 </span></a>
                            </li>
                            <li><a href="javascript:void(0)" for="teacher.html" one="管理任务" two="教师管理">
                            	<span class="left-menu-second glyphicon glyphicon-user" ></span>
                            	<span class="left-menu-first"> 教师管理</span>
                            	</a>
                            </li>
                            
                        </ul>
                    </li>
                    
                     
                    
                    <li>
                        <a href="#s2" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-user">       
                            </span>     
                            <span class="left-menu-first">课程管理</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s2" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="course.html" one="管理任务" two="课程管理">
                            <span class="left-menu-second glyphicon glyphicon-tasks"></span>
                            <span class="left-menu-first"> 课程信息管理 </span>
                            </a></li>
                            <li><a href="javascript:void(0)" for="semester/lessons.html" one="管理任务" two="学期管理">
                            <span class="left-menu-second glyphicon glyphicon-tasks"></span>
                            <span class="left-menu-first"> 学期课程管理 </span>
                            </a></li>
                            <li><a href="javascript:void(0)" for="plan.html">
                            <span class="left-menu-second glyphicon glyphicon-tasks" one="管理任务" two="教学计划管理"></span>
                            <span class="left-menu-first"> 新建教学计划 </span>
                            </a></li>
                            
                        </ul>
                    </li>                   
                  
                    </c:if>
                    
                    <c:if test="${user.firstRole.str == 'teacher' }">
                    <li>
  						<a href="#s3" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-tasks">       
                            </span>     
                            <span class="left-menu-first">课程管理</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s3" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="semester/lessons.html?role=TEACHER" one="教师任务" two="我的课程">
                            <span class="left-menu-second glyphicon glyphicon-list-alt"></span>
                            <span class="left-menu-first"> 我的课程 </span>
                            </a></li>
                            <li><a href="javascript:void(0)" for="semester/lessons.html" one="教师任务" two="课程查看">
                            <span class="left-menu-second glyphicon glyphicon-search"></span>
                            <span class="left-menu-first"> 全校课程查看 </span>
                            </a></li>
                            
                        </ul>       
                    </li>           	
                    </c:if>

                    <c:if test="${user.firstRole.str == 'student' }">
                    <li>
  						<a href="#s5" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-tasks">       
                            </span>     
                            <span class="left-menu-first">课程管理</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s5" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="semester/lessons.html?role=STUDENT" one="学生任务" two="我的课程">
                            <span class="left-menu-second glyphicon glyphicon-list-alt"></span>
                            <span class="left-menu-first"> 我的课程 </span>
                            </a></li>
                            <li><a href="javascript:void(0)" for="score/student.html?sid=${user.sid}" one="学生任务" two="我的成绩">
                            <span class="left-menu-second glyphicon glyphicon-list-alt"></span>
                            <span class="left-menu-first"> 我的成绩</span>
                            </a></li>
                            <li><a href="javascript:void(0)" for="semester/lessons.html"  one="学生任务" two="课程查看">
                            <span class="left-menu-second glyphicon glyphicon-search"></span>
                            <span class="left-menu-first"> 全校课程查看 </span>
                            </a></li>
                            
                        </ul>       
                    </li>           	
                    </c:if>
					
					<c:if test="${user.secondRole.str == 'tuitor' }">
                    <li>
  						<a href="#s4" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-tasks">       
                            </span>     
                            <span class="left-menu-first">助教任务</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s4" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="semester/lessons.html?role=TUITOR"  one="助教任务" two="课程管理">
                            <span class="left-menu-second glyphicon glyphicon-list-alt"></span>
                            <span class="left-menu-first"> 助教课程 </span>
                            </a></li>                         
                        </ul>       
                    </li>           	
                    </c:if>
					
                    <c:if test="${user.secondRole.str == 'charge' }">
					<li>
                        <a href="#s6" class="nav-header collapsed" data-toggle="collapse">
                            <span class="glyphicon glyphicon-tasks">       
                            </span>     
                            <span class="left-menu-first">教学负责人任务</span>   
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="s6" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="javascript:void(0)" for="statis" one="教学管理" two="每月统计">
                            <span class="left-menu-second glyphicon glyphicon-calendar"></span>
                            <span class="left-menu-first"> 每月统计 </span>
                            </a></li>   
                            <li><a href="javascript:void(0)" for="lessons/summary.html" one="教学管理" two="教学质量统计">
                            <span class="left-menu-second glyphicon glyphicon-calendar"></span>
                            <span class="left-menu-first"> 教学质量统计 </span>
                            </a></li>       
                                        
                        </ul>       
                    </li>
                    </c:if>

                    <c:if test="${user.firstRole.str == 'prime' }">
                        <li><a href="javascript:void(0)" for="institutes/summary.html" one="教学管理" two="概况查看">
                            <span class="left-menu-second glyphicon glyphicon-calendar"></span>
                            <span class="left-menu-first"> 概况查看 </span>
                        </a></li> 
                    </c:if>

                    <li><a href="javascript:void(0)" for="goal" one="教学管理" two="战略查看">
                            <span class="left-menu-second glyphicon glyphicon-calendar"></span>
                            <span class="left-menu-first"> 战略查看 </span>
                    </a></li> 

                </ul>
            </div>
            <!-- 左侧导航栏结束-->

            <!-- 正文-->
            <div class="col-sm-9" id="contents-out">
            
                <!-- 位置条 -->
                <ul class="breadcrumb" style="margin-bottom: 5px;">
                    <li>主页</li>
                    <li id="one">用户管理</li>
                    <li id="two" class="active"></li>
                </ul>
                <!-- 位置条结束 -->
                <div id="contents-in">
                <div class="col-lg-8">
                </div>
                </div>
            </div>
            <!-- 正文-->
        </div>
    </div>
        <!-- END -->

    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.edit.js"></script>  
    <script type="text/javascript" src="jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.filter.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.grouping.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.aggregates.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdatatable.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxeditor.js"></script>
	<script type="text/javascript" src="jqwidgets/jqxslider.js"></script>
	<script type="text/javascript" src="jqwidgets/jqxdropdownbutton.js"></script>
	<script type="text/javascript" src="jqwidgets/jqxcolorpicker.js"></script>
	<script type="text/javascript" src="jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxnotification.js"></script>
    <script type="text/javascript" src="jqwidgets/globalization/globalize.js"></script>
    <script type="text/javascript" src="js/jquery.toastmessage.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxfileupload.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="chart/highcharts.js"></script>
    <script type="text/javascript" src="js/myjs.js"></script>
    <script type="text/javascript">
        $('a[for]').each(function(i) {
            if($(this).attr('for') != "") {
                $(this).click(function() { 
                    $.ajax({
                        type: 'GET',
                        url:  $(this).attr('for'),
                        dataType: 'html',
                        
                        success: function(data) { 
                            $("#contents-in").remove();
                            $(data).appendTo('#contents-out'); 
                            
                        },
                    });
                    $('#one').text($(this).attr('one'));
                    $('#two').text($(this).attr('two'));
                });
            }
        });
        initnotify();
    </script>
    <script type="text/javascript">

      refreshprogress(true);

    </script>


</body>
</html>