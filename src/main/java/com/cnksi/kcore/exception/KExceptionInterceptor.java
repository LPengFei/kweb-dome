package com.cnksi.kcore.exception;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.StrKit;


/**
 * @title: 异常处理拦截器
 * @className: KExceptionInterceptor
 * @version: 1.0
 */
public class KExceptionInterceptor implements Interceptor {

	private static final Logger log = Logger.getLogger(KExceptionInterceptor.class);

	@Override
	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		// HttpServletRequest request = controller.getRequest();

		try {
			ai.invoke();
		} catch (Exception e) {
			// 判断是否ajax请求
			// String header = request.getHeader("X-Requested-With");
			// boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
			// String msg = formatException(e);
			// if (isAjax) {
			// msg = new StringBuilder().append("{\"status\":\"0\",\"msg\":\"").append(msg).append("\"}").toString();
			// controller.renderJson(msg);
			// } else {
			// String redirctUrl = request.getHeader("referer");
			// if (StrKit.isBlank(redirctUrl)) {
			// redirctUrl = request.getRequestURI();
			// }
			// controller.setAttr("message", msg);
			// controller.setAttr("redirctUrl", redirctUrl);
			controller.render("/WEB-INF/jsp/errors/500.jsp");
			// }

			// 记录错误日志
			doLog(ai, e);
			// doSaveDbLog(ai, e);
		}
	}

	/**
	 * 记录错误日志
	 * 
	 * @methodName: doLog
	 * @param ai
	 * @param e
	 */
	private void doLog(Invocation ai, Exception e) {
		// 开发模式
		if (JFinal.me().getConstants().getDevMode()) {
			e.printStackTrace();
		} else {
			// Log4j记录日志
			StringBuilder sb = new StringBuilder("\n---Exception Log Begin---\n");
			sb.append("Controller:").append(ai.getController().getClass().getName()).append("\n");
			sb.append("Method:").append(ai.getMethodName()).append("\n");
			sb.append("Exception Type:").append(e.getClass().getName()).append("\n");
			sb.append("Exception Details:");
			log.error(sb.toString(), e);

		}
	}

	/**
	 * 格式化异常信息，用于友好响应用户
	 * 
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String formatException(Exception e) {
		String message = null;
		Throwable ourCause = e;
		while ((ourCause = e.getCause()) != null) {
			e = (Exception) ourCause;
		}
		String eClassName = e.getClass().getName();
		// 一些常见异常提示
		if ("java.lang.NumberFormatException".equals(eClassName)) {
			message = "请输入正确的数字";
		} else if (e instanceof KException || e instanceof RuntimeException) {
			message = e.getMessage();
			if (StrKit.isBlank(message))
				message = e.toString();
		}

		// 获取默认异常提示
		if (StrKit.isBlank(message)) {
			message = "系统繁忙,请稍后再试";
		}
		// 替换特殊字符
		if(message!=null)
		message = message.replaceAll("\"", "'");
		return message;
	}

	// private void doSaveDbLog(Invocation ai, Exception e) {
	// // 保存数据库日志
	// KLog logAnn = ai.getMethod().getAnnotation(KLog.class);
	// KLogInfo info = new KLogInfo();
	// IResource res = resMap.get(ai.getActionKey());
	// if (logAnn != null) {
	// info.set("operatetype", logAnn.operaName()); // 操作类型
	// info.set("businessName", logAnn.businessName()); // 业务名称
	// } else {
	// info.set("operatetype", operatetypeMap.get(ai.getMethodName()) != null ? operatetypeMap.get(ai.getMethodName()) : ai.getMethodName());
	// info.set("businessName", res.getName());
	// }
	// info.set("visitURL", ai.getActionKey());
	// info.set("pkValue", e.getMessage());
	// info.set("operator", ShiroUtils.getFullname());
	// info.set("operatetime", new Date());
	// info.save();
	// }
}