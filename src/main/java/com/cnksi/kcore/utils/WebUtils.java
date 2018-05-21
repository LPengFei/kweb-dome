package com.cnksi.kcore.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public final class WebUtils
{
	/**
	 * 取得带相同前缀的Request Parameters
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 * 
	 * @param request
	 * 
	 * @param prefix
	 * 
	 * @return
	 */
	public static Map<String, String> getParametersStartingWith2(ServletRequest request, String prefix, boolean xls){
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		if (prefix == null)
		{
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements())
		{
			String paramName = (String) paramNames.nextElement();
			if (paramName.startsWith(prefix))
			{
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
				} else {
					params.put(paramName.replace(prefix+".", ""), iso2utf(values[0], xls));
				}
				request.setAttribute(paramName.replace(".", ""), iso2utf(values[0], xls));
			}
		}

		return params;
	}
	
	public static String iso2utf(String str, boolean xls){
		String result = str != null ? str.trim() : "";
		if (xls){
			try{
				result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getClientIp(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
