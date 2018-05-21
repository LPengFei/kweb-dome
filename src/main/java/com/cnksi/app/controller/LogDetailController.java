package com.cnksi.app.controller;

import java.io.File; 
import java.io.IOException; 
import java.util.Map; 
import com.cnksi.kcore.jfinal.model.KModel; 
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.jfinal.ext.plugin.annotation.KRequiresPermissions; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.LogDetail; 
/**
 * 
 */
 @KRequiresPermissions(name = "日志详细内容",model=LogDetail.class) 
 public class LogDetailController extends KController {

 	public LogDetailController(){
		super(LogDetail.class);
	} 

	@KRequiresPermissions(name = "日志详细内容列表")
	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) setAttr("page", LogDetail.me.paginate(queryParam));

		render(listJsp);
	}



	@KRequiresPermissions(name = "日志详细内容编辑")
	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		LogDetail record = null;
		if (idValue != null) {
			record = LogDetail.me.findById(idValue);
		}else{
			record = new LogDetail();

		}
		setAttr("record", record);

		render(formJsp);
	}


	@KRequiresPermissions(name = "日志详细内容保存")
	public void save() {
		LogDetail record = getModel(LogDetail.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
		    record.set(IConstans.ID,-100);
			record.save();
		} 

		bjuiAjax(200, true);
	}



	@KRequiresPermissions(name = "日志详细内容详情查看")
	public void view() {

		super.doEdit();

		String idValue = getPara(IConstans.ID, getPara());
		LogDetail record = null;
		if (idValue != null) {
			record = LogDetail.me.findById(idValue);
			setAttr(IConstans.RECORD, record);
			render(detailJsp);
		}else{
			bjuiAjax(300, IDataSource.NOT_DATA, false);
		}
	}



	@KRequiresPermissions(name = "日志详细内容删除")
	public void delete() {
		LogDetail record = LogDetail.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<LogDetail> p = LogDetail.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					LogDetail record = new LogDetail();
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

		if(Integer.parseInt(resultMap.get("statusCode").toString())==200)
			resultMap.put("message","导入数据成功！");

		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}



	public void lookup() {
		keepPara();

		String kmodelid = getPara("modelId", getPara());
		KWebQueryVO queryParam;

		if(kmodelid!=null){
			KModel kModel = getKModel(kmodelid);
			queryParam = super.doIndex(kModel, null);
		}else
			queryParam = super.doIndex();

		if(queryParam!=null)
			setAttr("page", LogDetail.me.paginate(queryParam));

		setAttr("modelName","logdetail");
		render(lookupJsp);
	}


}
