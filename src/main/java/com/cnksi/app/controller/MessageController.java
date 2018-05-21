package com.cnksi.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import com.cnksi.app.model.Message;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 */
public class MessageController extends KController {

	public MessageController() {
		super(Message.class);
	}

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null)
			setAttr("page", Message.me.paginate(queryParam));

		render(listJsp);
	}

	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		Message record = null;
		if (idValue != null) {
			record = Message.me.findById(idValue);
		} else {
			record = new Message();

		}
		setAttr("record", record);

		render(formJsp);
	}

	public void save() {
		Message record = getModel(Message.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		}

		bjuiAjax(200, true);
	}

	public void delete() {
		Message record = Message.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}

	public void export() throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Message> p = Message.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Message record = new Message();
					record.put(map);
					record.save();
				}
			} else {
				msg = "导入错误：数据校验失败，请查看校验结果文件！";
				errorFile = result.getSaveFile().getName();
				File errorFolder = new File(PathKit.getWebRootPath(), "error");
				if (!errorFolder.isDirectory()) {
					errorFolder.mkdirs();
				}
				File moveFile = new File(PathKit.getWebRootPath() + File.separator + "error", result.getSaveFile().getName());
				result.getSaveFile().renameTo(moveFile);
			}
		} catch (ExcelImportException e) {
			e.printStackTrace();
			msg = "导入错误：" + e.getMessage();
		}
		Map<String, Object> resultMap = bjuiAjaxBackMap(StrKit.notBlank(msg) ? 300 : 200, msg, false);
        if(Integer.parseInt(resultMap.get("statusCode").toString())==200){
            resultMap.put("message","导入数据成功！");
        }
		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}

}
