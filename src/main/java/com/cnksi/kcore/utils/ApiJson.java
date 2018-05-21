package com.cnksi.kcore.utils;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Joey on 2016/9/19.
 * 接口json格式返回
 */
@SuppressWarnings("serial")
public class ApiJson extends Record {


/*
     ApiJson 返回格式如下
    {
            statusCode:
            message:
            closeCurrent:
            tabid:
            dialogid:
            data:
    }
*/

    public ApiJson(Integer statusCode, String message, boolean closeCurrent, String tabId, String dialogId, Object data) {
        setStatusCode(statusCode);
        setMessage(message);
        setCloseCurrent(closeCurrent);
        if (StrKit.notBlank(tabId))
            setTabid(tabId);
        if (StrKit.notBlank(dialogId))
            setDialogid(dialogId);
        if (data != null)
            setData(data);
    }

    public static ApiJson result(boolean result) {
        return new ApiJson(result ? 200 : 300, result ? "操作成功" : "操作失败", result, null, null, null);
    }

    public static ApiJson success(boolean closeCurrent, String tabId, String dialogId) {
        return new ApiJson(200, "操作成功", closeCurrent, tabId, dialogId, null);
    }

    public static ApiJson error(boolean closeCurrent, String tabId, String dialogId) {
        return new ApiJson(300, "操作失败", closeCurrent, tabId, dialogId, null);

    }

    public void setStatusCode(Integer statusCode) {
        set("statusCode", statusCode);
    }

    public void setMessage(String message) {
        set("message", message);
    }

    public void setCloseCurrent(boolean closeCurrent) {
        set("closeCurrent", closeCurrent);
    }

    public void setTabid(String tabId) {
        set("tabid", tabId);
    }

    public void setDialogid(String dialogId) {
        set("dialogid", dialogId);
    }

    public void setData(Object data) {
        set("data", data);
    }
}
