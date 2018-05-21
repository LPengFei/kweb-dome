package com.cnksi.interceptor;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by zf on 2017/8/4.
 */
public class LoginInterceptor implements Interceptor {


    @Override
    public void intercept(Invocation invocation) {
        HttpSession session = invocation.getController().getSession();

        //默认访问根路径
        String contextPath = invocation.getController().getRequest().getContextPath();
        contextPath = StringUtils.defaultIfBlank(contextPath, "/");

        if(session == null){
            invocation.getController().renderHtml("<script>window.location='" + contextPath + "'</script>");
        }
        else{
            User user = (User)session.getAttribute(KConfig.SESSION_USER_KEY);
            if(user != null) {
                invocation.invoke();
            }
            else {
                invocation.getController().renderHtml("<script>window.location='" + contextPath + "'</script>");
            }
        }
    }
}
