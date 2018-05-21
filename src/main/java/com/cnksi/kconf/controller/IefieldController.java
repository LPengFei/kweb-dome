package com.cnksi.kconf.controller;


import com.cnksi.kconf.model.Iefield;
import com.cnksi.kconf.model.Iexport;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel导入管理
 */
public class IefieldController extends KController {
	public IefieldController() {
		super(Iexport.class);
	}

	/**
	 *	模板
	 */
	public void export() throws IOException {
        Long ieid = getParaToLong("ieid");
        Iexport iexport = Iexport.me.findById(ieid);
		List<Iefield> iefields = Iefield.me.find("select * from k_iefield where enabled = 0 and ieid = ?",ieid);
		String xlsid = getPara("xlsid", "-1");
		Map<String, String> titleDynicParams = new HashMap<>();
		titleDynicParams.put("name",iexport.get("iename"));
		super.export(xlsid, iefields,null,titleDynicParams);
	}

	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "iefield");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				Iexport iexport=null;
				for (Map<String, Object> map : result.getList()) {
					Iefield record = new Iefield();
					record._setAttrs(map);

					if(null==iexport){
						iexport = Iexport.me.findById(record.getLong("ieid"));
					}
					//查询相应得字段是否存在，如果不存在则创建
					StringBuilder sb = new StringBuilder("select iefid from k_iefield where enabled = 0 and ieid = ? and field_name = ?");
					if(null!=iexport && "导出".equals(iexport.getStr("ietype"))
							&& StrKit.notBlank(record.getStr("field_alias"))){
						sb.append(" and field_alias = "+record.getStr("field_alias"));
					}

					Iefield iefield = Iefield.me.findFirst(sb.toString(),record.get("ieid"),record.getStr("field_name"));

					if(null!=iefield){
						record.set("iefid",iefield.getLong("iefid"));
						record.update();
					}else {
						record.set("iefid",-100);
						record.save();
					}
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

		if (Integer.parseInt(resultMap.get("statusCode").toString()) == 200)
			resultMap.put("message", "导入数据成功！");

		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}
}
