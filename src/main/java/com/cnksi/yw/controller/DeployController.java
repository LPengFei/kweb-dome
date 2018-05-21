package com.cnksi.yw.controller;

import java.io.File; 
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.yw.model.Project;
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
import com.cnksi.yw.model.Deploy; 
/**
 * 发布管理
 */
 @KRequiresPermissions(name = "发布管理",model=Deploy.class)
 public class DeployController extends KController {

 	public DeployController(){
		super(Deploy.class);
	} 

	@KRequiresPermissions(name = "列表")
	public void index() {
		keepPara();
		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) {
			queryParam.addFilter("enabled = 0");
			setAttr("page", Deploy.me.paginate(queryParam));
		}

		render("/WEB-INF/jsp/deploy/deploy_list.jsp");
	}



	@KRequiresPermissions(name = "编辑")
	public void edit() {
        keepPara();
		super.doEdit();

		String idValue = getPara("id", getPara());
		Deploy record = null;
		if (idValue != null) {
			record = Deploy.me.findById(idValue);
		}else{
			record = new Deploy();

		}
		setAttr("record", record);

		render(formJsp);
	}


	@KRequiresPermissions(name = "保存")
	public void save() {
		String tab = getPara(IConstans.TABID);
		String modelId = getPara(IConstans.MID);
		if(StrKit.notBlank(modelId)){
			getKModel(modelId);
		}
		if(StrKit.notBlank(tab)){
			tabid = tabid+","+tab;
		}
		Deploy record = getModel(Deploy.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.set("create_time", new Date());
			record.set("create_time", new Date());
			record.set("creator_id", getLoginUserId());
			record.set("creator_name", getLoginUserName());
			Project p =  Project.me.findById(record.getLong("project_id"));
			record.set("city",p.getStr("city"));
			record.save();
		}

		bjuiAjax(200, true,tabid);
	}



	@KRequiresPermissions(name = "详情查看")
	public void view() {

		super.doEdit();

		String idValue = getPara(IConstans.ID, getPara());
		Deploy record = null;

		if (idValue != null) {
			record = Deploy.me.findById(idValue);
			setAttr(IConstans.RECORD, record);
			setAttr("deployList",Deploy.me.getDeployList(record.getStr("project_id")));
			render("/WEB-INF/jsp/deploy/deploy_view.jsp");
		}else{
			bjuiAjax(300, IDataSource.NOT_DATA, false);
		}
	}



	@KRequiresPermissions(name = "删除")
	public void delete() {
		Deploy record = Deploy.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Deploy> p = Deploy.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Deploy record = new Deploy();
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
			setAttr("page", Deploy.me.paginate(queryParam));

		setAttr("modelName","deploy");
		render(lookupJsp);
	}


}
