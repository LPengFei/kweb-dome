package com.cnksi.kconf.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import com.cnksi.kconf.controller.vo.LogbQuery;
import com.cnksi.kconf.model.Logb;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 业务日志管理
 */
public class LogbController extends KController {

	public LogbController() {
		super(Logb.class);
	}

	public void index() {

		KWebQueryVO queryParam = super.doIndex(LogbQuery.class);
        Integer pageSize = getParaToInt("pageSize");

		if (queryParam != null){
			if(pageSize==null){
				queryParam.setPageSize(150);
			}
			setAttr("page", Logb.me.paginate(queryParam));
		}

		render(listJsp);
	}

	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		Logb record = null;
		if (idValue != null) {
			record = Logb.me.findById(idValue);
		} else {
			record = new Logb();

		}
		setAttr("record", record);

		render(formJsp);
	}

	public void save() {
		Logb record = getModel(Logb.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		}

		bjuiAjax(200, true);
	}

	public void delete() {
		Logb record = Logb.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}

	public void export() throws IOException {
		KWebQueryVO queryParam = super.doIndex(LogbQuery.class);
		Page<Logb> p = Logb.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Logb record = new Logb();
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
				File moveFile = new File(PathKit.getWebRootPath() + File.separator + "error",
						result.getSaveFile().getName());
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
