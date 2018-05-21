<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>  
<%@ include file="../taglib.jsp" %>
<script src="${ctx }/js/md5.js"></script>
<script src="${ctx }/js/RSA.js"></script>
<style>
	.change-pwd-form ul>li{
		width: 100%;
	}

</style>
	<div class="bjui-pageContent">
	<div class="ess-form change-pwd-form">
		<form action="${ctx}/kconf/user/changePwd" data-toggle="ajaxform" data-reload-navtab="true" method="post" id="change_pwd_form">
			<input type="hidden" id="publicKeyExponent" value="${publicKeyExponent}">
			<input type="hidden" id="publicKeyModulus" value="${publicKeyModulus}">
			<%--<input type="hidden" name="record.uid" value="${record.uid }">--%>
			<ul>
				<%--<li class="input_">
					<label>用户名</label>
					<input type="text" value="${record.uaccount}"  class="form-control">
				</li>--%>
				<li class="input_">
					<label>新密码</label>
					<input type="password" id="newpwd" name="newpwd" value="" data-rule="新密码:required" class="form-control">
				</li>
				<li class="input_">
					<label>确认密码</label>
					<input type="password" value="" id="newpwd2" name="record.upwd"  data-rule="确认密码:required;match(newpwd);" class="form-control">
				</li>
			</ul>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;">
				<button type="button" class="btn" style="background: #14CAB4; color: white;" onclick="change_pwd_submit();">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>
		</form>
		
	</div>
</div>

<script src="${ctx }/BJUI/js/sha256.js"></script>
<script type="text/javascript">

    function change_pwd_submit() {

        var newpwd = currentDialog("#newpwd").val(), newpwd2 = currentDialog("#newpwd2").val();

        if(currentDialog("[aria-invalid=true]").length > 0 ){
            return false;
        }
        if(!newpwd || !newpwd2) {
            $(this).alertmsg("error", "密码不能为空");
            return false;
        }
        if(newpwd != newpwd2) {
            $(this).alertmsg("error", "新密码与确认密码不一致");
            return false;
        }


        if (checkPassword(newpwd) == false) {
            $(this).alertmsg("error", "密码中只能包含字母、数字，以及如下英文符号：<br/> + / $ & * = # ! % @");
            return false;
        }

//		$("#newpwd, #newpwd2", this).val(function (index, val) {
//			return SHA256_hash(val);
//		});

        //加密用户名，密码
        encodeUserNameAndPwd();

        currentDialog("#change_pwd_form").submit();
        currentDialog("[type=password]").val("");
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

    /**
     * 加密用户名，密码
     */
    function encodeUserNameAndPwd(){
        var publicExponent = $("#publicKeyExponent").val();
        var publicModulus = $("#publicKeyModulus").val();
        RSAUtils.setMaxDigits(200);
        var rsaKey = new RSAUtils.getKeyPair(publicExponent, "", publicModulus);

        //加密密码
        $("[type=password]").val(function (i, val) {
//            val = md5(val) + "-" + val;
            return RSAUtils.encryptedString(rsaKey, val.split("").reverse().join(""));
        });
    }

</script>
