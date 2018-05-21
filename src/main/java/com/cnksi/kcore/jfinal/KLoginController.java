package com.cnksi.kcore.jfinal;

import com.jfinal.core.Controller;

/**
 * 用户登录Controller
 * 
 * @author joe
 *
 */
public class KLoginController extends Controller {
	/**
	 * 跳转到登录界面
	 */
	public void index() {
		render("login.jsp");
	}

	/**
	 * 验证码
	 */
	public void captcha() {
		renderCaptcha();
	}

	/**
	 * 退出系统
	 */
	public void logout() {
		redirect("/sec/main");
	}

	public void main() {

	}
}
