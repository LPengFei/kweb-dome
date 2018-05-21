package com.cnksi.shiro;

import com.google.common.collect.Maps;
import com.jfinal.aop.Invocation;
import com.jfinal.ext.plugin.shiro.AuthzHandler;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroKit;
import com.jfinal.kit.StrKit;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

import java.util.HashMap;
import java.util.Map;

public class KShiroInterceptor extends ShiroInterceptor {

    private Logger logger = Logger.getLogger(KShiroInterceptor.class);


	@Override
	public void intercept(Invocation ai) {
		AuthzHandler ah = ShiroKit.getAuthzHandler(ai.getActionKey());
		// 存在访问控制处理器。
		if (ah != null) {
			try {
				// 执行权限检查。
				ah.assertAuthorized();
			} catch (UnauthenticatedException lae) {
				lae.printStackTrace();
				// RequiresGuest，RequiresAuthentication，RequiresUser，未满足时，抛出未经授权的异常。
				// 如果没有进行身份验证，返回HTTP401状态码,或者跳转到默认登录页面
				if (StrKit.notBlank(ShiroKit.getLoginUrl())) {
					// 保存登录前的页面信息,只保存GET请求。其他请求不处理。
					if (ai.getController().getRequest().getMethod().equalsIgnoreCase("GET")) {
						ai.getController().setSessionAttr(ShiroKit.getSavedRequestKey(), ai.getActionKey());
					}
					ai.getController().redirect(ShiroKit.getLoginUrl());
				} else {
				}
				return;
			} catch (AuthorizationException ae) {
				// RequiresRoles，RequiresPermissions授权异常
				// 如果没有权限访问对应的资源，返回HTTP状态码403，或者调转到为授权页面
				// if (StrKit.notBlank(ShiroKit.getUnauthorizedUrl())) {
				// ai.getController().redirect(ShiroKit.getUnauthorizedUrl());
				// } else {
				// ai.getController().renderError(403);
				// }
				ae.printStackTrace();

				ai.getController().renderJson(unAuthorizationMsg());
				return;
			}
		}
		// 执行正常逻辑
        try {
            ai.invoke();
        } catch (Exception e) {
		    logger.error(e.getMessage(), e);
            renderException(ai, e);
        }
    }

    /**
     * 返回异常json信息
     * @param ai
     * @param e
     */
    private void renderException(Invocation ai, Exception e){
	    Map<String, Object> map = Maps.newHashMap();
        map.put("statusCode", 300);
        map.put("message", e.getMessage());
        map.put("closeCurrent", true);
        map.put("tabid", "");
        ai.getController().renderJson(map);
    }

	private Map<String, Object> unAuthorizationMsg() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statusCode", 300);
		resultMap.put("message", "你无权访问该资源,请与管理员联系");
		resultMap.put("closeCurrent", true);
		resultMap.put("tabid", "");
		return resultMap;
	}
}
