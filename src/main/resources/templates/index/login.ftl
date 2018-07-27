<!DOCTYPE html>
<html>
<head>
    <#include "../common/head.ftl">
    <link href="/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
    <title>登录_任务调度管理系统</title>
</head>
<body>
<div class="container">
    <div class="form row">
        <div class="form-horizontal col-md-offset-3" id="login_form">
            <h3 class="form-title">LOGIN</h3>
            <div class="col-md-9">
                <!--has-success or has-error glyphicon-ok or glyphicon-remove-->
                <div class="form-group has-feedback" id="username_from">
                    <i class="fa fa-user fa-lg"></i>
                    <input type="text" class="form-control" id="userName" placeholder="Username" autofocus="autofocus" onkeypress="if (event.keyCode === 13) login._login();" maxlength="20" aria-describedby="inputSuccess1Status">
                    <span id="username_icon" class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" style="padding-top: 20px;padding-left: 20px;display: none"></span>
                    <span id="inputSuccess1Status" class="sr-only"></span>
                </div>
                <div class="form-group has-feedback" id="pwd_form">
                    <i class="fa fa-lock fa-lg"></i>
                    <input type="password" class="form-control" id="password" placeholder="Password" autofocus="autofocus" onkeypress="if (event.keyCode === 13) login._login();" maxlength="20" aria-describedby="inputSuccess2Status">
                    <span id="pwd_icon" class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" style="padding-top: 20px;padding-left: 20px;display: none"></span>
                    <span id="inputSuccess2Status" class="sr-only"></span>
                </div>
                <div class="form-group col-md-9">
                    <span class="login-warning"></span>
                </div>
                    <div class="form-group col-md-offset-9">
                    <button type="submit" onclick="login._login();" class="btn btn-success pull-right" name="submit">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../common/footer.ftl">
<script type="text/javascript" src="/js/jobmanager-login.js"></script>
</body>
</html>
