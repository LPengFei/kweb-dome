package com.cnksi.yw.controller;

import java.io.File; 
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.yw.model.Deploy;
import com.cnksi.yw.model.Training;
import com.jfinal.plugin.activerecord.Record;
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
import com.cnksi.yw.model.Project; 
/**
 * 
 */
 @KRequiresPermissions(name = "",model=Project.class) 
 public class ProjectController extends KController {

 	public ProjectController(){
		super(Project.class);
	} 

	@KRequiresPermissions(name = "列表")
	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) {
			queryParam.addFilter("enabled = 0");
			setAttr("page", Project.me.paginate(queryParam));
		}

		render(listJsp);
	}



	@KRequiresPermissions(name = "编辑")
	public void edit() {
		keepPara();
		super.doEdit();
		String tab = getPara(IConstans.TABID);

		if(StrKit.notBlank(tab)){
			tabid = tab;
		}
		setAttr("tabid", tabid);

		String idValue = getPara("id", getPara());
		Project record = null;

		if (idValue != null) {
			record = Project.me.findById(idValue);
		}else{
			record = new Project();

		}
		setAttr("record", record);

		render(formJsp);
	}


	@KRequiresPermissions(name = "保存")
	public void save() {
		Project record = getModel(Project.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.set("create_time", new Date());
			record.set("creator_id", getLoginUserId());
			record.set("creator_name", getLoginUserName());
			record.save();
		}
		bjuiAjax(200, true,getPara("tabid"));
	}



	@KRequiresPermissions(name = "详情查看")
	public void view() {

		super.doEdit();

		String idValue = getPara(IConstans.ID, getPara());
		Project record = null;
		if (idValue != null) {
			record = Project.me.findById(idValue);
			setAttr(IConstans.RECORD, record);
			setAttr("deployList", Deploy.me.getDeployList(record.getStr("id")));
			setAttr("trainingList", Training.me.getDeployList(record.getStr("id")));
			render("/WEB-INF/jsp/project/project_view.jsp");
		}else{
			bjuiAjax(300, IDataSource.NOT_DATA, false);
		}
	}



	@KRequiresPermissions(name = "删除")
	public void delete() {
		Project record = Project.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Project> p = Project.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Project record = new Project();
					record.put(map);
					record.set("create_time", new Date());
					record.set("creator_id", getLoginUserId());
					record.set("creator_name", getLoginUserName());
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
			setAttr("page", Project.me.paginate(queryParam));

		setAttr("modelName","project");
		render("/WEB-INF/jsp/project/city_lookup.jsp");
	}
	/**
	 * JSON
	 *
	 * @return
	 */
	public void json() {
 		List<Record> lookups = Project.me.findJson();
		renderJson(lookups);
	}

}
