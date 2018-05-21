<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>

<link rel="stylesheet" href="${ctx }/BJUI/themes/css/login1_style.css"/>
<link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/reveal.css"/>
<link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/theme/black.css"/>
<%--<script src="${ctx }/BJUI/js/jquery-1.7.2.min.js"></script>
<script src="${ctx }/BJUI/js/jquery.cookie.js"></script>
<script src="${ctx }/BJUI/js/sha256.js"></script>
<script src="${ctx }/BJUI/plugins/revealjs/js/reveal.js"></script>--%>
<script src="${ctx }/js/md5.js"></script>
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
		bottom: 5px;
		z-index: 32;
		background-color: white;
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

<div class="login-bg">
	<form action="${ctx}/app/pwdcheck/verifyPwd" id="loginForm" data-toggle="validate" data-alertmsg="false" data-callback="callback">
		${token}
		<input type="hidden" id="publicKeyExponent" value="${publicKeyExponent}">
		<input type="hidden" id="publicKeyModulus" value="${publicKeyModulus}">
		<input type="hidden" id="url" value="${url}">

		<div class="login-wrap" style="margin-top: -65px;">
			<div class="login-line divPassword">
				<div class="userImg"><img src="${ctx }/images/login/pwd1.png"></div>
				<input type="text" onfocus="this.type='password'" id="password1" value="" placeholder="请输入密码"/>
				<input type="hidden" name="password" id="password">
			</div>
			<div id="errorMsg" style="color:red;margin:5px 20px; text-align: left;font-size:12px;">${error } </div>
			<div class="login-btn" onclick="loginsubmit();">提交</div>
		</div>
	</form>
</div>

<script type="text/javascript">
    $(function () {
        //console.log(window.monthData)
        $('#loginForm').keydown(function (e) {
            if(e.which=='13'){
                loginsubmit();
                return false;
            }
        });
        //获取链接中的错误信息
        try {
            var url  = decodeURI(location.href);
            if (url.indexOf('msg=') >= 0){
                var errorMsg = url.substring(url.indexOf('msg=')+'msg='.length);
                errorMsg = decodeURIComponent(errorMsg);
                if (errorMsg){
                    errorMsg = errorMsg.replace(/\+/g, " ");
                }
                $("#errorMsg").text(errorMsg);
            }
        } catch (e) {
        }

    });

    function loginsubmit() {

        $('#url',$.CurrentDialog).val($('#url',$.CurrentDialog).val()+window.monthData+"&");
        var issubmit = true;

        if ($.trim($("#password1").val()).length == 0) {
            $(".divPassword1").css('border', '1px #ff0000 solid');
            $("#password1").focus();
            issubmit = false;
            return;
        }

        if (!issubmit) {
            return;
        }
        encodeUserNameAndPwd();
        $("#loginForm").submit();

    }

    /**
     * 加密用户名，密码
     */
    function encodeUserNameAndPwd(){
        var publicExponent = $("#publicKeyExponent").val();
        var publicModulus = $("#publicKeyModulus").val();
        RSAUtils.setMaxDigits(200);
        var rsaKey = new RSAUtils.getKeyPair(publicExponent, "", publicModulus);

        //加密密码
        $("[name=password]").val(function () {
            var val = $("#password1").val();
            val = md5(val) + "-" + val;
            return RSAUtils.encryptedString(rsaKey, val.split("").reverse().join(""));
        });
    }
    function callback(json){
        if("200"==json.statusCode){
//            $(this).dialog("closeCurrent", true);
			if ("导入" == "${ie }") {
				$(this).dialog({id:'dialog-normal', url:'${ctx}' + $("#url").val() + 'verified=yes', title:'${ie }', width:500, height:200});
            } else {
                $(this).dialog("closeCurrent", true);
                $(this).bjuiajax('doExport', {url:'${ctx}' + $("#url").val() + 'verified=yes'});
            }
        }else{
            $(this).bjuiajax('ajaxDone', json).dialog('closeCurrent',false); // 为指定的tabid设置刷新标记
        }
    }
</script>