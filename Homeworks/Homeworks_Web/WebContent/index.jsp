<%@ page contentType="text/html;charset=utf-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homeworks | 课程作业管理系统</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/pastel-stream.css" />
    <link rel="stylesheet" type="text/css" href="css/self.css" />    
</head>
<body>
    <div class="container">
    <div class="row">
        <div class="col-xs-3 col-lg-4"></div>
        <div class="col-xs-6 col-lg-4">
            <!-- 登陆框-->
            <div class="well">
            <form class="form-horizontal login-form">
            <fieldset>
                <legend><center>登录</center></legend> 

                <div class="form-group">
                    <label for="select" class="col-sm-2 control-label">身份</label>
                    <div class="col-sm-9">
                        <select class="form-control" id="select">
                            <option>学生</option>
                            <option>教师</option>
                            <option>管理员</option>
                            <option>总负责人</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" name="inputId">
                    <label for="inputId" class="label-no col-sm-2 control-label">工号</label>
                    <div class="col-sm-9">
                        <input type="text" data-toggle="popover" data-content="账户不存在" class="form-control" id="inputId" placeholder="请输入工号">
                    </div>
                </div>
                <div class="form-group" name="inputPassword">
                    <label for="inputPassword" class="label-password col-sm-2 control-label" >密码</label>
                    <div class="col-sm-9">
                        <input type="password" data-toggle="popover" data-content="密码错误" class="form-control" id="inputPassword" placeholder="请输入密码">
                        <!--  
                        <div class="checkbox">
                            <label>
                                <input class="check-password" type="checkbox">
                                记住密码
                            </label>
                        </div>
                        -->
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-6">
                        <button id="login" type="submit" class="btn btn-block btn-primary" onclick='return false;'>登录</button>                      
                    </div>
                    <div class="col-sm-3">
                        <button class="btn btn-block btn-default ">撤销</button>      
                    </div> 
                    <div class="col-sm-2"></div>    
                </div>

            </fieldset>
            </form>
            </div>
        </div>
        <div class="col-xs-3 col-lg-4">
                 
        </div>
    </div>
    </div>
    
    

    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript">

    var user = [];
    var type_index = 0;
    var type = ['学生', '教师', '管理员', '总负责人'];
    $('#login').click(
        function() {
            $(".login-form :input").slice(0, 3).each(function(i) {
                    user[i] = $(this).val();
                }
            );
            var j;
            for(j = 0; j < 4; j++) {                
                if(user[type_index] == type[j]) {
                    user[type_index] = j;                    
                    break;
                }
            }
            console.log(user);
            $.ajax({
                type: 'POST',
                url:  'login',
                dataType: 'json',
                data: {
                    'type': user[0],
                    'id'  : user[1],
                    'password': user[2],
                },
                success: function(data) {                   
                    if(data.result == "PASS") {
                        window.location.href = "home.html";
                    }
                    else if(data.result == "PASSWORD_ERROR") {
                      
                        $('.form-group[name="inputPassword"]').addClass("has-error");
                        $('#inputPassword').popover({placement: 'right',
                        	trigger:'manual',
                            }).popover('show');
                        //$('.form-group[name="inputPassword"]').popover({contents:'密码错误',
                                      //                      	trigger: 'manual'});
                        // $('.form-group[name="inputPassword"]').popover('show');
                    }
                    else if(data.result == "NOT_EXISTS") {                       
                        $('.form-group[name="inputId"]').addClass("has-warning");
                        $('#inputId').popover({placement: 'right',
                            trigger:'manual',
                        }).popover('show');
                    }
                },
            });
            return false;
        }
    );
    
    $('#inputPassword').click(function() {
         $('.form-group[name="inputPassword"]').removeClass("has-error");
         $('#inputPassword').popover('destroy');
     }
    );

    $('#inputId').click(function() {
         $('.form-group[name="inputId"]').removeClass("has-warning");
         $('#inputId').popover('destroy');
     }
    );

    </script>

</body>
</html>