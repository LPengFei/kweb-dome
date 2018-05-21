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
import com.cnksi.app.model.Log; 
/**
 * 
 */
 @KRequiresPermissions(name = "系统操作日志",model=Log.class) 
 public class LogController extends KController {

 	public LogController(){
		super(Log.class);
	} 

	@KRequiresPermissions(name = "系统操作日志列表")
	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) setAttr("page", Log.me.paginate(queryParam));

		render(listJsp);
	}



	@KRequiresPermissions(name = "系统操作日志编辑")
	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		Log record = null;
		if (idValue != null) {
			record = Log.me.findById(idValue);
		}else{
			record = new Log();

		}
		setAttr("record", record);

		render(formJsp);
	}


	@KRequiresPermissions(name = "系统操作日志保存")
	public void save() {
		Log record = getModel(Log.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	@KRequiresPermissions(name = "系统操作日志详情查看")
	public void view() {

		super.doEdit();

		String idValue = getPara(IConstans.ID, getPara());
		Log record = null;
		if (idValue != null) {
			record = Log.me.findById(idValue);
			setAttr(IConstans.RECORD, record);
			render(detailJsp);
		}else{
			bjuiAjax(300, IDataSource.NOT_DATA, false);
		}
	}



	@KRequiresPermissions(name = "系统操作日志删除")
	public void delete() {
		Log record = Log.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Log> p = Log.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Log record = new Log();
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
			setAttr("page", Log.me.paginate(queryParam));

		setAttr("modelName","log");
		render(lookupJsp);
	}


}
