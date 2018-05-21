package com.cnksi.sec;


import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.utils.KStrKit;
import com.cnksi.kcore.utils.MD5Util;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 用户登录Controller
 *
 * @author joe
 */
public class PwdCheckController extends SecLoginController {

    private String username, password;

    public void verifyPwd() throws UnsupportedEncodingException {
        try {
            //验证请求参数是否篡改 _jfinal_token, password
            List<String> paraNames = Collections.list(getParaNames());
            if (!paraNames.contains("password")){
                throw new CaptchaException("口令被篡改");
            }

            // rsa 私钥解密
            decryptPassword();

            //登录前校验
            doLoginValidate2();

            //执行密码校验
            doVerifyPwd(password);

        } catch (Exception e) {
            String msg = StringUtils.defaultString(e.getMessage(), "密码校验失败");
            msg = KStrKit.hasChinese(msg) ? msg : "密码校验失败";

            if (e instanceof RSAUtils.RSAException) {
                msg = "数据被篡改";
            } else if (e instanceof CaptchaException) {
                msg = e.getMessage();
            }
            bjuiAjax(300, msg, false, null);
        }
    }
    /**
     * 解密密码
     */
    private void decryptPassword() {
        RSAPrivateKey privateKey = getSessionAttr(RSAUtils.SESSION_RSA_PRIVATE_KEY);
        password = getPara("password");
        Preconditions.checkArgument(StrKit.notBlank(password), "密码为空");

        System.out.println(password);
        password = RSAUtils.decryptByPrivateKey(password, privateKey);
        System.out.println(password);

        //验证密码md5完整性
        List<String> templist = Lists.newArrayList(password);
        for (String item : templist) {
            String[] arr = item.split("-");
            Preconditions.checkArgument(arr.length == 2 && arr[0].equals(MD5Util.getMd5(arr[1])), "数据被篡改");
        }

        password = password.split("-")[1];

        System.out.println(password);

    }

    private void doLoginValidate2() throws Exception {
        if (!validateToken()) {
            throw new Exception("token错误，请返回并刷新页面");
        }

        //验证是否在允许登录时间范围
//        checkAllowLoginTime();
    }

    private void doVerifyPwd(String password) throws Exception {
        String msg = null;
        try {
            // 校验密码（8-20位，包含字母、数字）
            try {
                SecUserService.me.validPassword(password, null);
            } catch (Exception e) {
                //密码不符合校验规则
                throw e;
            }

            // 验证密码
            password = SecUserService.me.encodePassword(password);
            String password1 = getLoginUser().get("upwd");
            if (password.equals(password1)) {
                bjuiAjax(200, "密码校验成功！", true, null);
            } else {
                bjuiAjax(300, "密码校验失败！", true, null);
            }
        } catch (AuthenticationException e) {
            if (e instanceof IncorrectCredentialsException){
                msg = "密码不正确";
            }else if (StrKit.notBlank(e.getMessage()) && KStrKit.hasChinese(e.getMessage())){
                msg = "密码校验失败, " + e.getMessage();
            }else{
                msg = "密码校验失败, 其他错误";
            }
            throw new Exception(msg);
        } catch (Exception e) {
            throw new Exception("密码校验失败, 其他错误");
        }

    }

    /**
     * 当前登录用户
     *
     * @return
     */
    protected User getLoginUser() {
        return getSessionAttr(KConfig.SESSION_USER_KEY);
    }


    /**
     * <pre>
     * statusCode=200,300
     * msg={msg}
     * closeCurrent = {closeCurrent}
     * tabid= {tabid}
     * </pre>
     *
     * @return
     */
    protected void bjuiAjax(int statusCode, String msg, boolean closeCurrent, String tabid) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", statusCode);
        resultMap.put("message", msg);
        resultMap.put("closeCurrent", closeCurrent);
        resultMap.put("tabid", tabid);
        renderJson(resultMap);
    }
}
