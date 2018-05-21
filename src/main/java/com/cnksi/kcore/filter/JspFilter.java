package com.cnksi.kcore.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cnksi.kcore.exception.KException;

/**
 * 控制不允许直接通过浏览器访问的文件，通过文件后缀限定{如:jsp,jspx} 不允许直接访问
 * 
 * @author joe
 *
 */
public class JspFilter implements Filter {

	// 允许访问的后缀集合
	private List<String> suffixList = null;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String reqUri = request.getRequestURI();
		if (reqUri != null) {
			int lastDot = reqUri.lastIndexOf(".");
			if (lastDot != -1) {
				String _ext = reqUri.substring(lastDot + 1);
				if (suffixList.contains(_ext)) {
					throw new KException(_ext + "后缀结尾的请求不允许被访问");
				}
			}
		}
		chain.doFilter(request, resp);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		suffixList = new ArrayList<String>();
		String _suffix = fc.getInitParameter("suffix");
		if (_suffix != null) {
			suffixList.addAll(Arrays.asList(_suffix.split(",")));
		}
	}

}
