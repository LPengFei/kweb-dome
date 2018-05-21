package com.cnksi.sec.filter;

import com.jfinal.kit.StrKit;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 过滤特殊字符串，防止跨站脚本攻击，sql注入等
 * Created by xyl on 2017/9/6, 006.
 */
public class XssFilter implements Filter {
    private Logger logger = Logger.getLogger(XssFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //只能嵌入同源的iframe
        resp.setHeader("X-Frame-Options", "SAMEORIGIN");
//        resp.setHeader( "Set-Cookie", "HttpOnly");

        //cookie设置HttpOnly
//        WebUtils.setCookieHttpOnly(req);

        //非get，post请求为非法请求
        String reqMethod = req.getMethod();
        if ("get".equalsIgnoreCase(reqMethod) && "post".equalsIgnoreCase(reqMethod)){
            forward(req, resp, "非法请求");
            return;
        }

        if (req.getRequestURI().toLowerCase().contains("sessionid")){
            resp.sendRedirect("/logout");
            return;
        }

        //模型配置不进行 xss 编码
        String url = req.getRequestURI().toLowerCase();
        if (url.contains("save") && (url.contains("model") || url.contains("kconf") || url.contains("iexport"))) {
            chain.doFilter(request, response);
            return;
        }

        try {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(xssRequest, response);

            //对于编辑操作，将token添加到record
            /*if (req.getRequestURI().endsWith("edit")) {
                Object record = request.getAttribute("record");
                if (record != null && record instanceof Model) {
                    Model model = (Model) record;
                    model.set(Const.DEFAULT_TOKEN_NAME, request.getAttribute(Const.DEFAULT_TOKEN_NAME));
//                    request.setAttribute("record", model);
                }
            }*/
        } catch (Exception e) {
            if(e instanceof RuntimeException) logger.error(e.getMessage());
            logger.error(e);
            if (StrKit.notBlank(e.getMessage())){
                redirectError(resp, e.getMessage());
            }
        }

    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String errorMsg){
        request.setAttribute("errors", errorMsg);
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/errors/500.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void redirectError(HttpServletResponse response, String msg){
        try {
            response.sendRedirect("errorPage?msg="+ URLEncoder.encode(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }
}

