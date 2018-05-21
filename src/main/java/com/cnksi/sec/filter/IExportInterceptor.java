package com.cnksi.sec.filter;

import com.cnksi.sec.RSAUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;
import java.util.Objects;

/**
 * 导入、导出功能需要二次验证密码后才可以提交
 * Created by van on 2017/12/19.
 */
public class IExportInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        String actionKey = invocation.getActionKey();
//        Optional<String> actionKey1 = Optional.ofNullable(actionKey);^((?!iexport).)+(export|importxls).*
        if (StringUtils.isNotBlank(actionKey) && (actionKey.endsWith("/export") || actionKey.endsWith("/export_re")|| actionKey.endsWith("/log_export") || actionKey.endsWith("/export_outer"))
                && !Objects.equals("yes", controller.getPara("verified"))) {
//            invocation.invoke();
            RSAUtils.genRsa(controller.getRequest());
            controller.createToken();
            if (StringUtils.containsIgnoreCase(actionKey, "export")) {
                controller.setAttr("ie", "导出");
            } else {
                controller.setAttr("ie", "导入");
            }
            StringBuffer buffer = new StringBuffer(actionKey);
            buffer.append("?");
            Enumeration<String> paraNames = controller.getParaNames();
            while (paraNames.hasMoreElements()) {
                String s =  paraNames.nextElement();
                buffer.append(s + "=" + controller.getPara(s) + "&");
            }
            controller.setAttr("url", buffer.toString());
            controller.render("/WEB-INF/jsp/sec/verifypwd.jsp");
        } else {
            invocation.invoke();
        }


    }
}
