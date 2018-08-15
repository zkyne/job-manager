<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <#include "../common/head.ftl">
    <title>定时任务调度管理系统</title>
</head>
<body style="background-image:url('/images/bodybg.png');background-position: left top;background-size: auto;background-repeat: repeat;background-attachment: scroll">
<div class="container">
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="javascript:void(0);" style="width: auto">
                <img alt="" style="width: auto;height: 20px" src="/images/timg.gif"/></a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="javascript:void(0);" style="cursor:default"><span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;定时任务调度管理系统<span class="sr-only"></span></a>
                </li>
            </ul>
            <form action="/job/query" class="navbar-form navbar-left" onsubmit="return false">
                <div class="form-group">
                    <input type="text" class="form-control" name="key" style="width: 250px" id="key" placeholder="任 务 描 述 . . ." onkeypress="if (event.keyCode === 13) crontab._query();">
                </div>
                <button type="button" onclick="crontab._query()" class="btn btn-default">查 询</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top: 10px"><img alt="" src="/images/person.png" style="width: 30px;height: 30px;"/></li>
                <li class="dropdown">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${sys_user_session_info.userName}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0);"><span class="glyphicon glyphicon-question-sign"></span>&nbsp;&nbsp;Help</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/login/logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;Exit</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading text-left">
                    <div class="row">
                        <div class="col-xs-4 col-xs-offset-8 text-right">
                            <button type="button" onclick="crontab._initAddCrontab()" class="btn btn-info">新建任务</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body" id="panelBody">
                    <#include "queryResult.ftl"/>
                </div>
                <div class="panel-footer" style="text-align: center">版权所有: @ www.zkyne.com</div>
            </div>
        </div>
    </div>
</div>

<!-- edit模态框 start -->
<div class="modal fade" id="edit_crontab_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="margin-top: 100px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="editModalLabel">更新定时任务</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="edit_jobId" name="edit_jobId" value="">
                <input type="hidden" id="edit_jobUrl_old" name="edit_jobUrl_old" value="">
                <div class="form-group">
                    <label for="edit_jobUrl">路径URL:</label>
                    <input type="text" id="edit_jobUrl" name="edit_jobUrl" class="form-control" value="">
                </div>
                <div class="form-group">
                    <label for="edit_cronExp">执行规则:</label>
                    <input type="text" id="edit_cronExp" name="edit_cronExp" class="form-control" value="">
                </div>
                <div class="form-group">
                    <label for="edit_descript">任务描述:</label>
                    <input type="text" id="edit_descript" name="edit_descript" class="form-control" value="">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                </button>
                <button type="button" id="edit_btn_submit" onclick="crontab._updCrontab()" class="btn btn-primary" data-dismiss="modal">
                    <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                </button>
            </div>
        </div>
    </div>
</div><!-- edit模态框 end -->

<!-- add模态框 start -->
<div class="modal fade" id="add_crontab_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="margin-top: 100px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="addModalLabel">新建定时任务</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="add_jobUrl">路径URL:</label>
                    <input type="text" id="add_jobUrl" name="add_jobUrl" class="form-control" value="">
                </div>
                <div class="form-group">
                    <label for="add_cronExp">执行规则:</label>
                    <input type="text" id="add_cronExp" name="add_cronExp" class="form-control" value="">
                </div>
                <div class="form-group">
                    <label for="add_descript">任务描述:</label>
                    <input type="text" id="add_descript" name="add_descript" class="form-control" value="">
                </div>
                <div class="form-group">
                    <label for="add_status">状态:</label>&nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                    <input type="checkbox" id="add_status" name="add_status" value="0"> 立即启用</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                </button>
                <button type="button" id="add_btn_submit" onclick="crontab._addCrontab();" class="btn btn-primary" data-dismiss="modal">
                    <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>添加
                </button>
            </div>
        </div>
    </div>
</div><!-- edit模态框 end -->

<#include "../common/footer.ftl">
<script type="text/javascript" src="/js/jobmanager-crontab.js"></script>
</body>
</html>
