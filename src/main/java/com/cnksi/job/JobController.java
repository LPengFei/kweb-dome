package com.cnksi.job;

import com.cnksi.utils.JobKit;
import com.google.common.base.Preconditions;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;

import java.util.HashMap;

/**
 * 任务调度controller，可动态启用，禁用指定任务
 * Created by xyl on 2017/8/14, 014.
 */
@Clear
public class JobController extends Controller {

    /**
     * 启用定时任务，请求参数：action exp name
     */
    public void enable() {
        String action = getPara("action"); //执行具体业务逻辑action url
        String jobExp = getPara("exp"); //时间表达式
        String jobName = getPara("name"); //任务名称

        try {
            Preconditions.checkArgument(StrKit.notNull(action, jobExp, jobName), "请求参数action, exp, name不能为空!");
            //拼接localhost前缀，如果action中不含有http://前缀，默认设置为http://localhost:{port}/ess
            if (!action.startsWith("http://"))
                action = "http://localhost:" + getRequest().getServerPort() + getRequest().getContextPath() + action;

            JobKit.resetJob(jobName, jobExp, action);
            bjuiAjax(200);
        } catch (RuntimeException e) {
            bjuiAjax(300, e.getMessage());
        } catch (Exception e) {
            LogKit.error("创建定时任务失败，任务名称：" + jobName, e);
            bjuiAjax(300);
        }

    }

    /**
     * 禁用/移除定时任务,请求参数: 任务名称 /{jobName}
     */
    public void disable() {
        try {
            JobKit.deleteJob(getPara());
            bjuiAjax(200);
        } catch (Exception e) {
            e.printStackTrace();
            bjuiAjax(300);
        }
    }


    private void bjuiAjax(int statusCode) {
        bjuiAjax(statusCode, null);
    }

    private void bjuiAjax(int statusCode, String errMsg) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
       // resultMap.put("message", "操作成功");
        resultMap.put("tabid", getPara("tabid"));
        resultMap.put("statusCode", statusCode);
        resultMap.put("message", statusCode == 200 ? "操作成功" : StrKit.isBlank(errMsg) ? "操作失败" : errMsg);
        renderJson(resultMap);
    }


}
