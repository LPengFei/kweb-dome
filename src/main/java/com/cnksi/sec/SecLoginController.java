package com.cnksi.sec;

import com.cnksi.interceptor.LoginInterceptor;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.controller.LoginController;
import com.cnksi.kconf.model.Logb;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.exception.KException;
import com.cnksi.kcore.utils.DateUtil;
import com.cnksi.kcore.utils.EncodeUtils;
import com.cnksi.kcore.utils.MD5Util;
import com.cnksi.utils.NetUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 用户登录Controller
 *
 * @author joe
 */
@Clear
public class SecLoginController extends Controller {
    private static final Boolean REMEMBER_ME = PropKit.getBoolean("rememberme", false);
    private static final Boolean captcha = PropKit.getBoolean("captcha", true);

	private Log log = Log.getLog(LoginController.class);

    protected String username, password;
    protected boolean success = false;

    class CaptchaException extends KException {

        public CaptchaException(String message) {
            super(message);
        }

        public CaptchaException(String message, Exception e) {
            super(message, e);
        }
    }

	/**
	 * 主页默认显示：指挥大厅地图
	 */
	public void index() throws UnsupportedEncodingException {
		// redirect("/map/riskmap.html");
//        getResponse().setHeader( "Set-Cookie", "HttpOnly");
        redirect("/login");
	}

	public void login() {
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");

        setAttr("captcha", captcha); //验证码
        setAttr("rememberme", REMEMBER_ME); //验证码
		render("login.jsp");

		if (getRequest().getMethod().equalsIgnoreCase("POST")) {

            try {
                //验证请求参数是否篡改 _jfinal_token, username, password, captcha
                List<String> paraNames = Collections.list(getParaNames());
                if (!paraNames.contains("username") || !paraNames.contains("password") || !paraNames.contains("captcha")){
                    throw new CaptchaException("登录失败，口令被篡改，请重新登录");
                }

                // rsa 私钥解密
                decryptUsernamePassword();

                //登录前校验
                doLoginValidate();

                //执行登录操作
                doLogin(username, password);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                String msg = StringUtils.defaultString(e.getMessage(), "登录错误");
                msg = XssEncode.hasChinese(msg) ? msg : "登陆错误";

                if (StrKit.notBlank(username)){
                    username = XssEncode.encode(username);
                    if (username.contains("-"))
                        username = StringUtils.substringAfter(username, "-");
                }

                if (e instanceof RSAUtils.RSAException) {
                    msg = "登录错误，数据被篡改";
                } else if (e instanceof CaptchaException) {
                    msg = e.getMessage();
                } else {
                    Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), msg, "登录失败");
                }

                this.redirect("/login?msg=" + EncodeUtils.urlEncode(msg));
                return;
            }

		}else {
            RSAUtils.genRsa(getRequest()); // 生成公私钥
            createToken();
        }

//        setSessionAttr("synchronizingServerPath", PropKit.get("synchronizingServerPath"));
//        setSessionAttr("tokenName", Const.DEFAULT_TOKEN_NAME);
	}

    private void decryptUsernamePassword() {
        RSAPrivateKey privateKey = RSAUtils.getPrivateKeyCached(getPara("publicKeyExponent"));
        username = getPara("username");
        password = getPara("password");
        Preconditions.checkArgument(StrKit.notBlank(username), "用户名为空");
        Preconditions.checkArgument(StrKit.notBlank(password), "密码为空");

        System.out.println(username);
        System.out.println(password);
//        password = new StringBuilder(password).replace(2,3, "12").toString();
        username = RSAUtils.decryptByPrivateKey(username, privateKey);
        password = RSAUtils.decryptByPrivateKey(password, privateKey);
        System.out.println(username.concat(" : ").concat(password));

        //验证用户名、密码md5完整性
        try {
            List<String> templist = Lists.newArrayList(username, password);
            for (String item : templist) {
                String[] arr = item.split("-");
                Preconditions.checkArgument(arr.length == 2 && arr[0].equals(MD5Util.getMd5(arr[1])), "数据被篡改");
            }
        } catch (Exception e) {
            throw new RSAUtils.RSAException(e);
        }

        username = username.split("-")[1];
        username = XssEncode.encode(username);
        password = password.split("-")[1];


        System.out.println(username);
        System.out.println(password);

    }

    private void doLoginValidate() throws Exception {
        if (!validateToken()) {
            throw new Exception("token错误，请返回并刷新页面");
        }

        //验证是否在允许登录时间范围
        checkAllowLoginTime();

        //操作登录人数，不允许登录
        if (checkMaxLoginUserNum() == false) {
            throw new Exception("登录失败，在线人数超过限制，请联系管理员");
        }

        // 验证码校验
        if (captcha && validateCaptcha("captcha") == false) {
            throw new CaptchaException("验证码错误，请重新输入");
        }

    }

    private boolean doLogin(String username, String password) throws Exception {

        String msg = null;
        User loginUserRecord = null;

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);

        try {
            // 校验密码（8-20位，包含字母、数字）
            try {
                SecUserService.me.validPassword(password, null);
            } catch (Exception e) {
                User user = User.me.findByUsername(username);
                if (user != null && SecUserService.me.isLoginLocked(user)) {
                    throw new LockedAccountException("该用户已被锁定");
                }else{
                    throw e;
                }
            }

            // 加密密码
            token = new UsernamePasswordToken(username, SecUserService.me.encodePassword(password));

            Subject userSub = SecurityUtils.getSubject();

            //获取session数据
            userSub.login(token);
            loginUserRecord = (User) userSub.getPrincipal();

            //如果3个月未登录,不允许登录
            if (loginUserRecord != null && loginUserRecord.get("login_time") != null) {
                Date month3Date = DateUtil.addMonths(new Date(), -SecurityProp.lockUnloginUserTime);
                if (loginUserRecord.getDate("login_time").before(month3Date)) {
                    throw new AuthenticationException("你已经" + SecurityProp.lockUnloginUserTime + "个月未登录，请联系管理员");
                }
            }

            if (lockUser(loginUserRecord)){
                throw new LockedAccountException("该用户已被锁定，请联系管理员");
            }

            //只允许单用户登录，注销其他地方登陆的用户
//            keepOneLoginUser(username);

            // 记录用户日志
//            Logb.me.saveLogb(this, "登录系统成功", "login");
            Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), loginUserRecord.getStr("uname") + "登录系统成功", "登录成功");
            this.redirect("/index");

            //记录登录状态
            keepLoginUserStatus(loginUserRecord);

            // 登录成功，清除错误次数和锁定时间
            loginUserRecord.set("login_time", new Date());
            SecUserService.me.unlock(loginUserRecord);

            loginUserRecord.put("upwd2", password);

            //添加cookie username
//            setCookie(KConfig.USERNAME_MD5, EncryptUtil.DES.encrypt(loginUserRecord.get("id").toString()), -1, true);

            //登录成功后验证用户是否未修改初始化密码，是否三个月未修改密码
            if (needChangePwd(loginUserRecord)) {
                redirect("/changepwd");
                return false;
            }
            success = true;
            return true;
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            token.clear();

            if (loginUserRecord == null) {
                loginUserRecord = User.me.findByUsername(username);
            }

            if (e instanceof IncorrectCredentialsException){
                msg = "登录系统失败, 账号或密码不正确";
                saveLoginError(loginUserRecord);
            }else if (StrKit.notBlank(e.getMessage()) && XssEncode.hasChinese(e.getMessage())){
                msg = "登录系统失败, " + e.getMessage();
            }else{
                msg = "登录失败, 其他错误";
            }

            throw new Exception(msg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            token.clear();
            throw new Exception("登录失败, 其他错误");
        }

    }

    /**
     * 判断用户是否锁定，如果锁定中，返回错误信息
     */
    private boolean lockUser(User user) {
        //单纯被锁定，不是因为登录失败锁定
        if (StringUtils.equals(user.get("status"), "lock") && user.get("err_login_time") == null){
            return true;
        }

        boolean loginLocked = SecUserService.me.isLoginLocked(user);
//        if (loginLocked == false){
////            user.set("err_login_count", 0).set("err_login_time", null).update();
//            user.unlock();
//        }
        return loginLocked;

    }

    /**
     * 移除之前登录的用户，保持单用户登录
     * @param username
     */
    private void keepOneLoginUser(String username) {
        setSessionAttr("ip", NetUtil.getIp(getRequest()));

        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// 获取当前已登录的用户session列表

        Serializable currentSessionId = SecurityUtils.getSubject().getSession().getId();
        String ip = NetUtil.getIp(getRequest());
        sessions.stream()
                .filter(session -> {
                    User _user = (User) session.getAttribute(KConfig.SESSION_USER_KEY);
                    boolean sameUserName = _user != null && Objects.equals(username, _user.getStr("uaccount"))
                            && !Objects.equals(currentSessionId, session.getId());
                    boolean sameIp = Objects.equals(session.getAttribute("ip"), ip) && !Objects.equals(currentSessionId, session.getId());
                    //session不相同，用户名或ip相同，需要移除此session，保留最新的一个session
                    return sameUserName || sameIp;
                }).forEach(session -> sessionManager.getSessionDAO().delete(session));
    }

    /**
     * 验证最大登录人数
     * @return
     */
    private boolean checkMaxLoginUserNum(){
        return true;
//        return LoginUserManager.getLoginUsers().size() < SecurityProp.maxLoginUserNum;
    }

    /**
     * 检查是否在允许登录的时间段
     * @return
     */
    public void checkAllowLoginTime(){
        String loginTime = SecurityProp.prop.get("login.time");
        if (StrKit.isBlank(loginTime)) return;

        String[] timeArr = loginTime.trim().split("-");
        //验证时间格式：08:00
        Preconditions.checkArgument(StrKit.notBlank(timeArr[0]) && timeArr[0].trim().matches("\\d{1,2}:\\d{1,2}(:\\d{1,2})?"), "登录时间段配置错误，请联系管理员");
        Preconditions.checkArgument(StrKit.notBlank(timeArr[1]) && timeArr[1].trim().matches("\\d{1,2}:\\d{1,2}(:\\d{1,2})?"), "登录时间段配置错误，请联系管理员");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime starttime = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(timeArr[0].trim()));
        LocalDateTime endtime = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(timeArr[1].trim()));

        if (!(starttime.isBefore(now) && now.isBefore(endtime))){
            throw new RuntimeException("登录失败，系统只能在时间段 " + loginTime + " 内登录");
        }

    }

    /**
     * 保持登录状态，将登陆人员缓存到application
     * @param loginUserRecord
     */
    private synchronized void keepLoginUserStatus(User loginUserRecord) {
//        Ret loginUsers = (Ret) getSession().getServletContext().getAttribute(KConfig.APPLICATION_LOGIN_USERS);
//        if (loginUsers == null) loginUsers = Ret.create();

        //判断是否操作最大登录人数
//        LoginUserManager.set(SecurityUtils.getSubject().getSession().getId().toString(), loginUserRecord);
//        System.err.println("LoginController.keepLoginUserStatus: " + LoginUserManager.getLoginUsers().getData().size());
//        getSession().getServletContext().setAttribute(KConfig.APPLICATION_LOGIN_USERS, loginUsers);
    }



    /**
     * 登录成功后验证用户是否未修改初始化密码，是否三个月未修改密码, session 中使用changepwd=true来标记，必须修改密码
     *
     * @param user
     */
    private boolean needChangePwd(User user) {
        //不起用密码修改校验
        if (SecurityProp.changePwdTimeEnabled == false) return false;

        boolean needChangePwd = user.get("change_pwd_time") == null || SecUserService.me.isDefaultPassword(user);
        String changepwdMsg = "";
        if (needChangePwd == false) {
            Date changePwdTime = user.getDate("change_pwd_time");
            needChangePwd = changePwdTime == null || DateUtil.addMonths(changePwdTime, SecurityProp.changePwdTime).compareTo(new Date()) <= 0;
            if (needChangePwd) {
                changepwdMsg = "已经" + SecurityProp.changePwdTime + "个月未修改密码了，请立即修改";
            }
        } else if (user.get("login_time") == null){
            changepwdMsg = "请修改初始密码";
        } else{
            changepwdMsg = "请修改初始密码";
        }

        if (needChangePwd) {
            getSession().setAttribute("changepwd", true);
            getSession().setAttribute("changepwdMsg", changepwdMsg);
        }
        return needChangePwd;
    }

	/**
	 * 登录错误，记录错误次数，3次后锁定用户
	 *
	 * @param user
	 */
	private void saveLoginError(User user) {
		// 未开启用户错误登录锁定功能
		if (SecurityProp.errorLoginEnable == false)
			return;

        if (user == null) {
            user = User.me.findByUsername(username);
        }

        if (user == null) return;

		Integer errorLoginCount = user.getInt("err_login_count");
		Date errorLoginTime = user.getDate("err_login_time");
		if (errorLoginCount == null) errorLoginCount = 0;

        user = new User().setPkVal(user.getPkVal()).set("err_login_count", errorLoginCount + 1).set("err_login_time", new Date());
        if (user.getInt("err_login_count") >= SecurityProp.errLoginNumber){
            user.set("status", "lock");
        }
        user.update();

	}

	/**
	 * 退出系统并跳转到登录界面
	 */
	//@ActionKey("/logout")
	public void logout() {
        //移除用户登录状态
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        String uname = "";
//        if (user != null){
//            uname = user.getName();
//            LoginUserManager.remove(user);
//            Logb.me.saveLogb(this, uname+"退出系统", "退出系统");
//        }
//        System.out.println("LoginController.logout: " + LoginUserManager.getLoginUsers().size());

		removeSessionAttr(KConfig.SESSION_USER_KEY);
		removeSessionAttr(KConfig.SESSION_DEPT_KEY);
		SecurityUtils.getSubject().logout();


		redirect("/login");
	}

	/**
	 * 验证码
	 */
	public void captcha() {
		renderCaptcha();
	}

	/**
	 * 跳转到错误页面
	 */
	public void errorPage() {
		setAttr("errors", getPara("msg", ""));
		render("errors/500.jsp");
	}

    /**
     * 跳转到修改密码界面
     */
    @Before({LoginInterceptor.class})
	public void changepwd(){
        createToken();
        RSAUtils.genRsa(getRequest());
        setAttr("error", getPara("msg", getSessionAttr("changepwdMsg")));
	    render("changepwd.jsp");
    }

    @Override
    public void renderCaptcha() {
        render(new SecCaptchaRender());
    }

    @Override
    public boolean validateCaptcha(String paraName) {
        return SecCaptchaRender.validate(this, getPara(paraName));
    }
    /**
     * 携带token跳转到指定url
     */
    public void redirectWithToken() {
        String url = getPara("url");
        createToken("_token");
        if (url.contains("?")) url += "&";
        else url += "?";

        String token = getAttrForStr("_token");
        User user = getSessionAttr(KConfig.SESSION_USER_KEY);
        String uaccount = user.get("uaccount");
        url += "_token=" + EncryptUtil.DES.encrypt(token + "=" + uaccount);
        redirect(url.replaceAll("／","/"));
    }

    /**
     * 验证token返回user
     */
    @Clear
    public void validateTokenUser() {
        if (validateToken("_token")) {
            String res = "ok";
            renderText(EncryptUtil.DES.encrypt(res));
        } else {
            renderNull();
        }
    }

    /**
     * 根据token获取用户信息，参数：
     * utoken: 由redirectWithToken中生成的token+uaccount加密字符串
     * _token: 从utoken中解密，解析出的真实token
     */
    public void getUserByToken() {
        if (validateToken("_token")) {
            String utoken = EncryptUtil.DES.decrypt(getPara("_utoken")); //token+username
            String username = StringUtils.substringAfter(utoken, "=");
            User user = User.me.findByUsername(username);
            String jsonStr = DES3Util.encode(JsonKit.toJson(user));
            renderText(jsonStr);
        } else {
            renderText("error");
        }
    }

}
