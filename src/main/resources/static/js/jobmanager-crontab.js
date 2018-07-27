// 定时任务相关js
$(function () {
    //初始化消息提示位置
    toastr.options = {
        //是否显示关闭按钮（提示框右上角关闭按钮）
        closeButton: false,
        //是否为调试
        debug: false,
        //是否显示进度条（设置关闭的超时时间进度条）
        progressBar: false,
        positionClass: "toast-bottom-right",
        //点击消息框自定义事件
        onclick: null,
        //显示动作时间
        showDuration: "300",
        //隐藏动作时间
        hideDuration: "1000",
        //自动关闭超时时间
        timeOut: "2000",
        extendedTimeOut: "1000",
        showEasing: "swing",
        hideEasing: "linear",
        //显示的方式，和jquery相同
        showMethod: "fadeIn",
        //隐藏的方式，和jquery相同
        hideMethod: "fadeOut"
    };
    // 初始化
    crontab._query();

    $("#add_jobUrl").blur(function () {
        var jobUrl = $("#add_jobUrl").val().trim();
        var check = crontab._checkJobUrl(jobUrl);
        if(check){
            $('#add_btn_submit').prop('disabled',false);
        }else{
            $('#add_btn_submit').prop('disabled',"disabled");
        }
    });
    $("#edit_jobUrl").change(function () {
        var jobUrl = $("#edit_jobUrl").val().trim();
        var jobUrlOld = $('#edit_jobUrl_old').val();
        if(jobUrl != jobUrlOld){
            var check = crontab._checkJobUrl(jobUrl);
            if(check){
                $('#edit_btn_submit').prop('disabled',false);
            }else{
                $('#edit_btn_submit').prop('disabled',"disabled");
            }
        }
    });
});

function clickPage(uri) {
    $("#panelBody").load(uri);
}

var submitFlag = true;
var crontab = {
    URL:{
        _query:function () {
          return "/job/query";
        },
        _checkUrl:function () {
          return "/job/check";
        },
        _addCrontab:function () {
            return "/job/add";
        },
        _updCrontab:function () {
            return "/job/modify";
        },
        _reverseCrontab:function () {
            return "/job/reverse";
        },
        _delCrontab:function () {
            return "/job/delete"
        }
    },

    _initAddCrontab:function () {
        $('#add_jobUrl').val('');
        $('#add_cronExp').val('');
        $('#add_descript').val('');
        $('#add_btn_submit').prop('disabled','disabled');
        $('#add_status').prop("checked",false);
        $("#add_crontab_Modal").modal();
    },
    _initUpdCrontab:function (obj,jobId) {
        var $curBtn = $(obj);
        var $curTr = $curBtn.parents('tr#curTr');
        var url = $curTr.find('td#url').html();
        var cronExp = $curTr.find('td#cronExp').html();
        var descript = $curTr.find('td#descript').html();
        $('#edit_jobId').val(jobId);
        $('#edit_jobUrl').val(url);
        $('#edit_jobUrl_old').val(url);
        $('#edit_cronExp').val(cronExp);
        $('#edit_descript').val(descript);
        $("#edit_crontab_Modal").modal();
    },
    _query:function () {
        $("#panelBody").load(crontab.URL._query() + "?key=" + encodeURIComponent($('#key').val()));
    },
    // 新建任务
    _addCrontab:function () {
        if(!submitFlag){
            toastr.warn("请勿重复提交.");
            return;
        }
        var jobUrl = $('#add_jobUrl').val().trim();
        var cronExp = $('#add_cronExp').val().trim();
        var descript = $('#add_descript').val().trim();
        var check = crontab._checkParams(cronExp,descript);
        if(check){
            var startJob = $('#add_status').prop("checked");
            var status = 1;
            if(startJob){
                status = 0;
            }
            submitFlag = false;
            $.ajax({
                url:crontab.URL._addCrontab(),
                type:'post',
                data:{
                    jobUrl:jobUrl,
                    cronExp:cronExp,
                    status:status,
                    descript:descript
                },
                dataType:'json',
                success:function (result) {
                    submitFlag = true;
                    if(result.code === 1){
                        //成功
                        crontab._query();
                        toastr.success("新建成功");
                    }else{
                        toastr.error(result.message);
                    }
                },
                error:function () {
                    submitFlag = true;
                    toastr.error("网络错误");
                }
            })
        }
    },
    // 更新任务
    _updCrontab:function () {
        if(!submitFlag){
            toastr.warn("请勿重复提交.");
            return;
        }
        var jobId = $('#edit_jobId').val();
        var jobUrl = $('#edit_jobUrl').val().trim();
        var cronExp = $('#edit_cronExp').val().trim();
        var descript = $('#edit_descript').val().trim();
        var check = crontab._checkParams(cronExp,descript);
        if(check){
            submitFlag = false;
            $.ajax({
                url:crontab.URL._updCrontab(),
                type:'post',
                data:{
                    jobId:jobId,
                    jobUrl:jobUrl,
                    cronExp:cronExp,
                    descript:descript
                },
                dataType:'json',
                success:function (result) {
                    submitFlag = true;
                    if(result.code === 1){
                        // 更新成功
                        crontab._query();
                        toastr.success("更新成功");
                    }else{
                        toastr.error(result.message);
                    }
                },
                error:function () {
                    submitFlag = true;
                    toastr.error("网络错误");
                }
            })
        }
    },
    _delCrontab:function (obj,jobId) {
        // var $curBtn = $(obj);
        Ewin.confirm({message: "您确认要删除该定时任务吗？"}).on(function (e) {
            if (e) {
                $.ajax({
                    url:crontab.URL._delCrontab(),
                    type:'post',
                    data:{jobId:jobId},
                    dataType:'json',
                    success:function (result) {
                        if(result.code === 1){
                            // 删除成功
                            // $curBtn.parents('tr#curTr').hide();
                            crontab._query();
                            toastr.success("删除成功");
                        }else{
                            toastr.error(result.message);
                        }
                    },
                    error:function () {
                        toastr.error("网络错误");
                    }
                });
            }
        });
    },
    // 启用/停止任务
    _reverseCrontab:function (obj,jobId) {
        var str = "启用";
        var $curBtn = $(obj);
        var status = $curBtn.parents('tr#curTr').find('td#status').attr('data-value');
        var flag = false;
        if(status == 0){
            str = "停止";
            flag = true;
        }
        Ewin.confirm({message: "您确认要" + str + "该定时任务吗？"}).on(function (e) {
            if (e) {
                $.ajax({
                    url:crontab.URL._reverseCrontab(),
                    type:'get',
                    data:{jobId:jobId,isStop:flag},
                    dataType:'json',
                    success:function (result) {
                        if(result.code === 1){
                            // 成功
                            if(flag){
                                $curBtn.html('启 用');
                                $curBtn.removeClass('btn-warning').addClass('btn-success');
                                $curBtn.parents('tr#curTr').removeClass('success');
                                $curBtn.parents('tr#curTr').find('td#status').html('已停止');
                                $curBtn.parents('tr#curTr').find('td#status').attr('data-value',1);
                            }else{
                                $curBtn.html('停 止');
                                $curBtn.removeClass('btn-success').addClass('btn-warning');
                                $curBtn.parents('tr#curTr').addClass('success');
                                $curBtn.parents('tr#curTr').find('td#status').html('启用中');
                                $curBtn.parents('tr#curTr').find('td#status').attr('data-value',0);
                            }
                            toastr.success("操作成功");
                        }else{
                            toastr.error(result.message);
                        }
                    },
                    error:function () {
                        toastr.error("网络错误");
                    }
                });
            }
        });
    },
    _checkJobUrl:function (jobUrl) {
        var checkResult = false;
        if(jobUrl === "" || jobUrl.match(/^\s+$/g)){
            toastr.warning("路径Url不能为空");
        }else{
            $.ajax({
                url:crontab.URL._checkUrl(),
                type:'get',
                data:{jobUrl:jobUrl},
                dataType:'json',
                async:false,
                success:function (result) {
                    if(result.code == 1){
                        // jobUrl可用
                        checkResult = true;
                    }else{
                        toastr.error(result.message);
                    }
                },
                error:function () {
                    toastr.error("网络错误");
                }
            });
        }
        return checkResult;
    },
    _checkParams:function (cronExp,descript) {
        var regext = /^\s+$/g;
        if(cronExp === "" || cronExp.match(regext)){
            toastr.warning("执行规则不能为空");
            return false;
        }
        if(descript === "" || descript.match(regext)){
            toastr.warning("任务描述不能为空");
            return false;
        }
        if(descript.length > 180){
            toastr.warning("任务描述不能超过180个字符");
            return false;
        }
        return true;
    }
};