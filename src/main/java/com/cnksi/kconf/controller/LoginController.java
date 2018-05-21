package com.cnksi.kconf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.exception.KExceptionInterceptor;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.utils.EncodeUtils;
import com.cnksi.sec.EncryptUtil;
import com.cnksi.sec.RSAUtils;
import com.cnksi.sec.SecCaptchaRender;
import com.cnksi.sec.SecLoginController;
import com.cnksi.sec.filter.XssHttpServletRequestWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.kit.*;
import com.jfinal.render.RedirectRender;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录Controller
 *
 * @author joe
 */
@Clear
@Before(KExceptionInterceptor.class)
public class LoginController extends SecLoginController {

    /**
     * 主页默认显示：指挥大厅地图
     */
    public void index() throws UnsupportedEncodingException {
//        redirect("/map/riskmap.html");
       //redirect("/login");

		//是否需要对接到调控
		Lookup lookup = Lookup.me.findByTypeidAndKey("dk_to_ess","is_link");
		if(null!=lookup && "1".equals(lookup.getValue())){
			User user = getSessionAttr(KConfig.SESSION_USER_KEY);
			//如果登录直接跳转到index，否则需要跳转到登录页面
			if (user != null){
				setAttr("login_or_index", "index");
			}else{
				setAttr("login_or_index", "login");
			}

            String lscc_server_login = getLsccServerUrlLookup();
            if(lscc_server_login != null){
				setAttr("lscc_server_login", lscc_server_login);  // 地县一体化平台登录地址
			}

			setAttr("serverUrl", getCtx(getRequest()));

			render("ls_index.jsp");
		}else{
			redirect("/login");
		}
    }

    private String getLsccServerUrlLookup() {
        Lookup lookup = Lookup.me.findByPropertity(new String[]{"enabled", "ltid", "lkey"}, new Object[]{0, "dk_to_ess", "lscc_server_login"}, BaseModel.Logical.AND);
        if (lookup != null) {
            return lookup.getValue();
        }
        return null;
    }

    private String getCtx(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String contextPath = StringUtils.defaultIfBlank(request.getContextPath(), "/");
		return url + contextPath;
	}

	public void login() {
        String fromUrl = getOrgRequest(getRequest()).getParameter("from");
        setAttr("from", fromUrl);
		super.login();
		if (success) {
		    //跳转到指定来源
			if (StrKit.notBlank(fromUrl)){
				redirect(fromUrl);
				return;
			}

			String tab_id = getPara("tab_id");
			if (StrKit.notBlank(tab_id))
				this.redirect("/index?tab_id=" + tab_id);
			else
				this.redirect("/index");
		}else{
            //错误跳转链接后增加from url
			if (getRender() instanceof RedirectRender && StrKit.notBlank(fromUrl)) {
				String url = ((RedirectRender) getRender()).buildFinalUrl();
                String contxtPath = getContxtPath();
                if (StrKit.notBlank(contxtPath)) url = StringUtils.substringAfter(url, contxtPath);
                if (!url.contains("?")) url += "?";
                else url += "&";
                url += "from=" + EncodeUtils.urlEncode(fromUrl);
                redirect(url);
            }
        }
	}

    String getContxtPath() {
        String cp = JFinal.me().getContextPath();
        return ("".equals(cp) || "/".equals(cp)) ? null : cp;
    }

	/*public void login() throws UnsupportedEncodingException {

		setAttr("captcha", PropKit.getBoolean("captcha"));

		render("login.jsp");
		setSessionAttr("synchronizingServerPath", PropKit.get("synchronizingServerPath"));
		if (getRequest().getMethod().equalsIgnoreCase("POST")) {


			Subject user = SecurityUtils.getSubject();
			String username = getPara("username");
			String password = getPara("password");
			if(StringUtils.isBlank(username)  || StringUtils.isBlank(password)){
				Cookie cookie_username = getCookieByName(getRequest(),"username");
				if(StringUtils.isBlank(username)  && cookie_username != null){
					username = cookie_username.getValue();
				}

				Cookie cookie_password = getCookieByName(getRequest(),"password");
				if(StringUtils.isBlank(password) && cookie_password != null){
					password = cookie_password.getValue();
				}
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			try {
				user.login(token);
				// 记录用户日志
				Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), "登录系统成功", "login");

				// 设置Cookie,用户和密码
				Cookie cookie_username = getCookieByName(getRequest(),"username");
				if(cookie_username == null){
					cookie_username = new Cookie("username",username);
				}else{
					cookie_username.setValue(username);
				}
				Cookie cookie_password = getCookieByName(getRequest(),"password");
				if(cookie_password == null){
					cookie_password = new Cookie("password",password);
				}else{
					cookie_password.setValue(password);
				}
				cookie_username.setPath("/");
				cookie_password.setPath("/");
				getResponse().addCookie(cookie_username);
				getResponse().addCookie(cookie_password);


				// 添加用户当前部门信息的cookie
				Department dept = getSessionAttr(KConfig.SESSION_DEPT_KEY);
				if(dept!=null) {
					Long jcdwid = dept.getJCDWID() == null ? dept.get("id") : dept.getJCDWID();
					getResponse().addCookie(new Cookie("jcdwid", jcdwid + ""));
				}
				//跳转到系统主页
				String tab_id = getPara("tab_id");
				if(StrKit.notBlank(tab_id))
				    this.redirect("/index?tab_id="+tab_id);
				else
					this.redirect("/index");
			} catch (UnknownAccountException uae) {
				uae.printStackTrace();
				Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), "登录系统失败，用户账号不存在", "error");
				this.redirect("/login?msg=" + URLEncoder.encode("用户账号不存在", "UTF-8"));
			} catch (IncorrectCredentialsException ice) {
				ice.printStackTrace();
				Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), "登录系统失败，账号密码不正确", "error");
				this.redirect("/login?msg=" + URLEncoder.encode("账号密码不正确", "UTF-8"));
			} catch (LockedAccountException lae) {
				lae.printStackTrace();
				Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), "登录系统失败，账号被锁定", "error");
				this.redirect("/login?msg=" + URLEncoder.encode("账号被锁定", "UTF-8"));
			} catch (AuthenticationException e) {
				e.printStackTrace();
				Logb.me.saveLogb(username, NetUtil.getIp(getRequest()), "登录系统失败，其他错误" + e.getMessage(), "error");
				this.redirect("/login?msg=" + URLEncoder.encode("其他错误" + e.getMessage(), "UTF-8"));
				token.clear();
			}

		}

	}*/

	/**
	 * 退出系统并调整到登录界面
	 */
	@ActionKey("/logout")
	public void logout() {
		removeSessionAttr(KConfig.SESSION_USER_KEY);
		removeSessionAttr(KConfig.SESSION_DEPT_KEY);
		String isRegister = getPara("isRegister");
		if(StrKit.isBlank(isRegister) || !"1".equals(isRegister)){
			removeCookie("username");
			removeCookie("password");
		}
		SecurityUtils.getSubject().logout();

		Lookup lookup = Lookup.me.findByTypeidAndKey("dk_to_ess","is_link");
		if(null!=lookup && "1".equals(lookup.getValue())){
			redirect("/");
		}else{
			redirect("/login");
		}
	}

	/**
	 * 验证码
	 */
	public void captcha() {
		renderCaptcha();
	}

	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	private Cookie getCookieByName(HttpServletRequest request, String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie = (Cookie)cookieMap.get(name);
			return cookie;
		}else{
			return null;
		}
	}



	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for(Cookie cookie : cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 用于远程调用获取验证码和token，只允许127.0.0.1访问此接口
	 */
	public void getCaptchaAndToken() {
//		Preconditions.checkArgument(Objects.equals(NetUtil.getIp(getRequest()), "127.0.0.1"), "没有访问权限");

		createToken();
		String tokenId = getAttrForStr(Const.DEFAULT_TOKEN_NAME);
		SecCaptchaRender.create(getRequest(), getResponse()).getBufferedImage();
		String captcha = getAttrForStr("_jfinal_captcha");

		RSAUtils.KRSA rsa = RSAUtils.genRsa();

		Ret ret = Ret.create("captcha", captcha).put("token", tokenId)
				.put("publicKeyExponent", rsa.getPublicExponent())
				.put("publicKeyModulus", rsa.getPublicModulus());

		//rsa公钥加密
		String username = getPara("u");
		if (StrKit.notBlank(username)) {
			username = EncryptUtil.DES.decrypt(username);
			username = HashKit.md5(username) + "-" + username;
			ret.put("u", RSAUtils.encryptByPublicKey(username, rsa.getPublicKey()));
		}
		String password = getPara("p");
		if (StrKit.notBlank(password)) {
			password = EncryptUtil.DES.decrypt(password);
			password = HashKit.md5(password) + "-" + password;
			ret.put("p", RSAUtils.encryptByPublicKey(password, rsa.getPublicKey()));
		}

		renderJson(ret.getData());
	}

	/**
	 * 跳转外部调度系统登录
	 */
	public void tolscc() {
		HttpServletRequest orgRequest = getOrgRequest(getRequest());
        String action = (String) orgRequest.getParameter("action");
        Preconditions.checkNotNull(action, "参数action为空");
        setAttr("action", action);

		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//未登录，跳转到登录页面并携带跳转链接
		if (user == null) {
			redirect("/login?from=" + EncodeUtils.urlEncode(getCtx(getRequest()) + "/tolscc?action=" + action));
			return;
		}

		//传递用户名密码，在调控端进行rsa加密
		String username = user.get("uaccount"), password = user.get("upwd2");
		Map<String, String> map = Maps.newHashMap();
		map.put("u", EncryptUtil.DES.encrypt(username));
		map.put("p", EncryptUtil.DES.encrypt(password));

		//获取调度token和验证码
		String httpResult = HttpKit.get(getLsccServerUrlLookup() + "/getCaptchaAndToken", map);
		JSONObject httpResultJson = JSON.parseObject(httpResult);

		//获取加密后的字符串作为登录信息
		username = httpResultJson.getString("u");
		password = httpResultJson.getString("p");

		Ret hiddens = Ret.create().put("username", username).put("password", password)
				.put("captcha", httpResultJson.getString("captcha"))
				.put(Const.DEFAULT_TOKEN_NAME, httpResultJson.getString("token"))
				.put("publicKeyExponent", httpResultJson.getString("publicKeyExponent"))
				.put("publicKeyModulus", httpResultJson.getString("publicKeyModulus"));

		setAttr("hiddens", hiddens.getData());

		render("login_external.jsp");
	}

	/**
	 * 获取最原始的request（没有进过xss包装）
	 * @param request
	 * @return
	 */
	private HttpServletRequest getOrgRequest(HttpServletRequest request){
		if (request instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) request).getOrgRequest();
		} else {
			try {
				Method getRequest = request.getClass().getSuperclass().getMethod("getRequest");
				return getOrgRequest((HttpServletRequest) getRequest.invoke(request));
			} catch (Exception e) {
				LogKit.error(e.getMessage(), e);
			}
		}
		return request;
	}

}
