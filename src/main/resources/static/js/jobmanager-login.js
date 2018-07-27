$(function () {
    $("#userName").blur(function () {
        var userName = $("#userName").val().trim();
        if(userName === "" || userName.match(/^\s+$/g)){
            $('#username_from').removeClass('has-success').addClass('has-error');
            $('#username_icon').removeClass('glyphicon-ok').addClass('glyphicon-remove').show();
            $('#userName').attr('placeholder','Username is empty !');
        }else{
            $('#username_from').removeClass('has-error').addClass('has-success');
            $('#username_icon').removeClass('glyphicon-remove').addClass('glyphicon-ok').show();
        }

    });

    $("#password").blur(function () {
        var password = $("#password").val().trim();
        if(password === "" || password.match(/^\s+$/g)){
            $('#pwd_form').removeClass('has-success').addClass('has-error');
            $('#pwd_icon').removeClass('glyphicon-ok').addClass('glyphicon-remove').show();
            $('#password').attr('placeholder','Password is empty !');
        }else{
            $('#pwd_form').removeClass('has-error').addClass('has-success');
            $('#pwd_icon').removeClass('glyphicon-remove').addClass('glyphicon-ok').show();
        }
    });

});

var login = {
    URL:{
        _login:function () {
            return "/login/login"
        }
    },
    _login:function () {
        var canLogin = true;
        var userName = $("#userName").val().trim();
        var password = $("#password").val().trim();
        if(userName === "" || userName.match(/^\s+$/g)){
            $('#username_from').removeClass('has-success').addClass('has-error');
            $('#username_icon').removeClass('glyphicon-ok').addClass('glyphicon-remove').show();
            $('#userName').attr('placeholder','Username is empty !');
            canLogin = false;
        }
        if(password === "" || password.match(/^\s+$/g)){
            $('#pwd_form').removeClass('has-success').addClass('has-error');
            $('#pwd_icon').removeClass('glyphicon-ok').addClass('glyphicon-remove').show();
            $('#password').attr('placeholder','Password is empty !');
            canLogin = false;
        }
        if(canLogin){
            $.ajax({
                url:login.URL._login(),
                type:'post',
                data:{userName:userName,password:password},
                dataType:'json',
                async:false,
                success:function (result) {
                    console.log(result);
                    if(result.code == 1){
                        // 登录成功
                        window.location = "/";
                    }else{
                        $('.login-warning').html(result.message).fadeIn(100);
                    }
                }
            });
        }
    }
};