<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><klookup:kv key="title" type="system_settings"/></title>
    <link rel="stylesheet" href="${ctx }/BJUI/themes/css/login1_style.css"/>
    <link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/reveal.css"/>
    <link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/theme/black.css"/>
    <script src="${ctx }/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx }/BJUI/js/jquery.cookie.js"></script>
    <script src="${ctx }/js/RSA.js"></script>
    <style type="text/css">
        .msg-wrap {
            min-height: 10px;
        }

        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
        }

        .close-button {
            position: fixed;
            right: 10px;
            top: 10px;
            width: 40px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            border-radius: 100%;
            background: rgba(0, 0, 0, 0.2);
            color: white;
            font-size: 14px;
            cursor: pointer;
            z-index: 33;
        }

        .login-bg {
            position: absolute;
            right:0;
            top: 0;
            z-index: 32;
        }

        .reveal .slides section {
            color: white;
        }

        .login-echart-wrap {
            position: relative;
            width: 1000px;
            height: 500px;
            margin: 0 auto;

        }

        .login-echart {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
        }
        .loadding {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
            background: url("./images/big_load.gif") no-repeat center #fff;
            z-index: 100;
        }
        .reveal .slides > section{
            padding: 0px;
        }
    </style>
</head>
<body style="background: none">
<div class="close-button" style="display: none;">打开</div>
<div class="login-bg">
    <img src="${ctx }/images/login/bg_1_.png" id="bg_img">
    <form action="${ctx}/app/user/changePwd" method="post" id="loginForm" onsubmit="return false;">
        ${token}
        <input type="hidden" id="publicKeyExponent" value="${publicKeyExponent}">
        <input type="hidden" id="publicKeyModulus" value="${publicKeyModulus}">

        <div class="login-wrap">
            <div class="login-title">修改用户密码<span>×</span></div>
            <div class="login-line divPassword">
                <div class="userImg"><img src="${ctx }/images/login/pwd1.png"></div>
                <input type="password" onfocus="this.type='password'" id="password1" value="" placeholder="请输入密码"/>
            </div>
            <div class="login-line divPassword">
                <div class="userImg"><img src="${ctx }/images/login/pwd1.png"></div>
                <input type="password" onfocus="this.type='password'" name="password" id="password2" value="" placeholder="请输入密码"/>
            </div>
            <input type="hidden" id="upwd" name="upwd">
            <%--<input type="hidden" id="old_upwd" name="old_upwd">--%>
            <div id='errorMsg' style="color:red;margin:5px 20px;margin-top: 10px; text-align: left;font-size:14px;">${error}</div>
            <div class="login-btn" onclick="loginsubmit();">提交</div>
        </div>
    </form>
</div>
<!-- 展示的容器 -->

</body>
<script type="text/javascript">

    $(function () {

        $('#bg_img').css('height', function () {
            return $(window).height()
        });
        $('.login-wrap').css('margin-top', function () {
            return -parseInt($('.login-wrap').css('height')) / 2 - 20 + 'px'
        });


        $("input").keydown(function (e) {
            if(e.which=='13'){
                loginsubmit();
            }
        });

    });



    function loginsubmit() {
        var issubmit = true;

        //验证密码
        $("[type=password]").each(function () {
            var val =  $(this).val();
            if(!val) {
                $("#errorMsg").text("密码为空");
                issubmit = false;
            }
        });
        if (!issubmit) {
            return false;
        }

        //两次密码输入匹配
        if($("#password1").val() != $("#password2").val()){
            $("#errorMsg").text("两次输入密码不相等");
            return false;
        }

        if (checkPassword($("#password2").val()) == false) {
            $("#errorMsg").html("密码中只能包含字母、数字、以及如下英文符号：+ / $ & * = # ! % @");
            return false;
        }



        $("#errorMsg").text('');

        //加密密码
        $("#upwd").val($("#password2").val());
        rsaEncodePwd($("#upwd"));

        //正在提交...
        $(".login-btn").text('正在提交...').attr("disabled", true);

        var tokenInput = $("input:hidden:first");

        $.post('${ctx}/app/user/changePwd',{'record.upwd': $("#upwd").val(), _jfinal_token: tokenInput.val()}, function (json) {
            console.debug(json);
            if(!json) return false;
            if(json.statusCode == 200){
                $("#errorMsg").text("修改密码成功，请使用新密码重新登录");
                setTimeout(function () {
                    location.href = 'logout';
                }, 1000);
            }else{
                $("#errorMsg").text(json.message || json);
                $(".login-btn").text('提交').attr("disabled", false);
                setTimeout(function () {
                    window.location.reload()
                }, 1200);
            }
        });

    }

    function checkPassword(newpwd) {
        var spchar = "+,/,$,&,*,=,#,!,%,@".split(",");
        var newpwdChar = newpwd.replace(/[0-9a-zA-Z]+/g, '').split('');
        if(!newpwdChar || newpwdChar.length == 0) return true;

        for (var nc in newpwdChar) {
            var found = false;
            for (var sc in spchar) {
                if (newpwdChar[nc] == spchar[sc]) {
                    found = true;
                }
            }
            if (found == false) {
                return false;
            }
        }
        return true;
    }

    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }

</script>
</html>