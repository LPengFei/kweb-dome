package com.cnksi.kconf.controller;

import com.cnksi.kconf.controller.vo.ConfModelFieldQuery;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kconf.model.ModelFieldRole;
import com.cnksi.kconf.model.Role;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.utils.IConstans;
import com.jfinal.aop.Before;
import com.jfinal.core.Injector;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统模型字段管理Controller
 * 
 * @author admin
 *
 */
public class ModelFieldController extends KController {
	public ModelFieldController() {
		super(KModelField.class);
	}

	/**
	 * list页面字段配置(按list_sort排序)
	 */
	public void list() {
		ConfModelFieldQuery queryParam = Injector.injectBean(ConfModelFieldQuery.class, null, getRequest(), false);
		queryParam.setPageSize(150);
		if (StrKit.isBlank(queryParam.getOrderField())) {
			queryParam.setOrderField("list_sort");
			queryParam.setOrderDirection("asc");
		}
		setAttr("sort_type","list");
		setAttr("page", KModelField.me.paginate(queryParam));
		keepPara();
		render("kmodelfield.jsp");
	}
	
	/**
	 * form页面字段配置，按form_sort排序
	 */
	public void form() {
		ConfModelFieldQuery queryParam = Injector.injectBean(ConfModelFieldQuery.class, null, getRequest(), false);
		queryParam.setPageSize(150);
		if (StrKit.isBlank(queryParam.getOrderField())) {
			queryParam.setOrderField("form_sort");
			queryParam.setOrderDirection("asc");
		}
		setAttr("sort_type","form");
		setAttr("page", KModelField.me.paginate(queryParam));
		keepPara();
		render("kmodelfield.jsp");
	}

	/**
	 * 新增修改页面
	 */
	public void edit() {
		String idValue = getPara("mfid", "");
		String mid = getPara("mid", "");
		KModelField model = null;
		if (StrKit.notBlank(idValue)) {
			model = KModelField.me.findById(idValue);
		} else {
			model = new KModelField();
			model.set("mid", mid);
		}

		if (StrKit.isBlank(model.getStr("settings")))
			model.set("settings", "null");

		// 数据源配置
		List<Datasource> dsList = Datasource.me.findListByPropertity("enabled", "0");
		setAttr("dsList", dsList);

		setAttr("record", model);

		render("kmodelfield_form.jsp");
	}

	public void reset(){
		String mid = getPara("mid", "");
		String sort_type = getPara("sort_type","");
		if("list".equals(sort_type) && StrKit.notBlank(mid)){
             Db.update("update k_model_field set list_view = 'false',list_sort = 300 where mid = ? and enabled = 0",mid);
			bjuiAjax(200,"重置成功！",false,"fields-list-"+mid);
		}else if("form".equals(sort_type) && StrKit.notBlank(mid)){
			Db.update("update k_model_field set form_view = 'false',form_sort = 300,form_required = 'false' where mid = ? and enabled = 0 and is_lock <> '1'",mid);
			bjuiAjax(200,"重置成功！",false,"fields-form-"+mid);
		}else{
			bjuiAjax(300,"重置失败！",false);
		}



	}

	/**
	 * 保存/更新
	 */
	public void save() {

		//Map<String, Object> resultMap = new HashMap<String, Object>();

		KModelField model = getModel(KModelField.class, "f");
		if(StrKit.isBlank(model.getStr("field_name"))){
             model.set("form_view","false");
		}

		// 列表界面中修改不会修改setting，用nosetting标记
//		if (getParaToBoolean("nosetting", false) == false) {
//			Map<String, String> listMap = WebUtils.getParametersStartingWith2(getRequest(), "list", false);
//			Map<String, String> formMap = WebUtils.getParametersStartingWith2(getRequest(), "form", false);
//			Map<String, String> dataMap = WebUtils.getParametersStartingWith2(getRequest(), "data", false);
//			model.set("settings", "{\"formview\":" + JsonKit.toJson(formMap) + ",\"listview\":" + JsonKit.toJson(listMap) + ",\"dataconfig\":" + JsonKit.toJson(dataMap) + "}");
//		}

		if (model.get("mfid") != null) {
			model.update();
//			resultMap.put("statusCode", "200");
//			resultMap.put("message", "更新成功");
//			resultMap.put("closeCurrent", true);
//			resultMap.put("tabid", "fields-list");
			bjuiAjax(statusCode200, true, "fields-list-"+model.get("mid")+","+"fields-form-"+model.get("mid"));
		} else {
			if(StrKit.isBlank(model.getStr("list_sort"))){
				model.set("list_sort",300);
			}
			if(StrKit.isBlank(model.getStr("form_sort"))){
				model.set("form_sort",300);
			}
			if(StrKit.notBlank(model.getStr("is_lock")) && "1".equals(model.getStr("is_lock"))){
				model.set("form_sort",400);
			}
			model.set(model.getPkName(), -100);
			model.save();
			// 新增字段并实时刷新model
			if (StrKit.notBlank(model.getStr("type")) && StrKit.notBlank(model.getStr("field_name")) && StrKit.notBlank(model.getStr("field_alias"))) {
				KModel m = KModel.me.findById(model.getLong("mid"));
				String alterSql = String.format(" alter table %s add column %s %s comment '%s' ", m.getStr("mtable"), model.getStr("field_name"), model.getStr("type"), model.getStr("field_alias"));
				Db.update(alterSql);
				TableMapping.me().refreshTable(m.getStr("mtable"));
			}
//			resultMap.put("statusCode", "200");
//			resultMap.put("message", "保存成功");
//			resultMap.put("closeCurrent", true);
//			resultMap.put("tabid", "fields-list");

			bjuiAjax(statusCode200, true, "fields-list-"+model.get("mid")+","+"fields-form-"+model.get("mid"));
		}
//		renderJson(resultMap);

	}

	/**
	 * 更新列表排序
	 */
	public void list_sort_update() {
		String id = getPara("mfid");
		String list_sort = getPara("value");
		if (StrKit.notBlank(id)) {
			KModelField confModelField = KModelField.me.findById(id);
			confModelField.set("list_sort", list_sort);
			confModelField.update();
			confModelField.put("mname", KModel.me.findById(confModelField.get("mid").toString()).get("mname"));
			renderJson(confModelField);
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		KModelField model = KModelField.me.findById(getPara("mfid"));
		if (model != null && (StrKit.isBlank(model.getStr("is_lock")) || "0".equals(model.getStr("is_lock")))) {
			model.set("enabled", 1).update();
			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 200);
			apiJson.put("message", "删除成功");
			apiJson.put("closeCurrent", false);
			renderJson(apiJson);
		} else {
			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 200);
			apiJson.put("message", "删除失败");
			apiJson.put("closeCurrent", false);
			apiJson.put("tabid", "");
			renderJson(apiJson);
		}
	}

	/**
	 * 字段状态改变
	 */
	public void fieldChange() {
		KModelField record = KModelField.me.findById(getPara("id"));
		String updatecolumn = getPara("updatecolumn"); // 需更新字段名
		String value = getPara("val");
		String type = getPara("type");
		if (record != null && !StrKit.isBlank(updatecolumn)) {
			if (!StrKit.isBlank(value)) {
				if (type.equals("plus")) { // 加字段顺序值
					record.set(updatecolumn, Integer.parseInt(value) + 5).update();
				} else if (type.equals("minus")) {// 减字段顺序值
					if (Integer.parseInt(value) == 0) {
						bjuiAjax(300, "已经是最小值，不能在减了！");
						return;
					} else {
						record.set(updatecolumn, Integer.parseInt(value) - 1).update();
					}
				}
			} else {
				if ("false".equals(record.getStr(updatecolumn))) {
					if("list_view".equals(updatecolumn)){
                         Long listSortMax = Db.queryLong("SELECT MAX(list_sort) FROM  `k_model_field` WHERE mid = ? AND list_view = 'true' AND enabled = 0 ",record.getLong(IConstans.MID));
                         if(!StrKit.notNull(listSortMax)) listSortMax = 0L;
                         record.set("list_sort",listSortMax+5);
						record.set(updatecolumn, "true");
						record.update();
					}else if("form_view".equals(updatecolumn)){
						Integer formSortMax = Db.queryInt("SELECT MAX(form_sort) FROM  `k_model_field` WHERE mid = ? AND form_view = 'true' AND enabled = 0 AND form_view_type <> 'hidden'",record.getLong(IConstans.MID));
						if(!StrKit.notNull(formSortMax)) formSortMax = 0;
						record.set("form_sort",formSortMax+5);
						record.set(updatecolumn, "true");
						record.update();
					}
					else{
						record.set(updatecolumn, "true").update();
					}
				} else {

					if("form_view".equals(updatecolumn)){
						if(null!=record.getStr("is_lock") && "0".equals(record.getStr("is_lock"))){
							record.set("form_sort",300);
							record.set(updatecolumn, "false");
						}else{
							record.set("form_sort",400);
						}
						record.update();
					}else if("list_view".equals(updatecolumn)){
						record.set("list_sort",300);
						record.set(updatecolumn, "false").update();
					}else{
						record.set(updatecolumn, "false").update();
					}


				}
			}
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}
	}

	/***
	 * 配置各个角色显示的字段
	 */
	public void role() {
		String mid = getPara("mid");
		List<Role> roles = Role.me.findListByPropertity("enabled", "0");
		for (Role role : roles) {
			String roleHasFields = ModelFieldRole.me.getFieldidByRole(role.getPkVal(),mid);
			role.put("fields", roleHasFields);
		}

		//只显示列表中展示的字段
		List<KModelField> fields =KModelField.me.find("select * from  k_model_field where mid =  ? and enabled = 0 and (list_view = ? or length(list_search_op)>0)",new Object[]{mid,"true"});
		setAttr("fields", fields);
		setAttr("roles", roles);
		keepPara("mid");
		renderJsp("kmodelfield_role.jsp");
	}

	@Before(Tx.class)
	public void saveRole() {
		String mid = getPara("mid");
		ModelFieldRole.me.deleteByMid(mid);

		List<Role> roles = Role.me.findListByPropertity("enabled", "0");
		for (Role role : roles) {
			Long[] mfids = getParaValuesToLong("role-" + role.getLong("id"));
			if (mfids != null) {
				for (Long mfid : mfids) {
					KModelField field = KModelField.me.findById(mfid);
					if (field == null)
						continue;
					ModelFieldRole mfr = new ModelFieldRole();
					mfr.set(mfr.getPkName(), -100);
					mfr.set("rid", role.getPkVal());
					mfr.set("mfid", mfid);
					mfr.set("mid", mid);
					mfr.set("list_view", field.get("list_view"));
					mfr.set("list_sort", field.get("list_sort"));
					mfr.set("form_view", field.get("form_view"));
					mfr.set("form_edit", "true");
					mfr.set("form_sort", field.get("form_sort"));

					mfr.set("export", "true");
					mfr.set("export_sort", field.get("list_sort"));
					mfr.set("export_width", "30");
					mfr.set("export_type", "StringType");
					mfr.save();
				}
			}
		}

		bjuiAjax(200);
	}

	/**
	 * 表单中指定字段换行显示
	 */
	public void formNewLine(){
		String id = getPara("id");
		boolean newline = getParaToBoolean("newline");

		KModelField record = new KModelField().setPkVal(id);
		//TODO 如果添加其他表单样式，此处需要修改
		if(newline == true){
			record.set("form_style", "clear:left");
		}else {
			record.set("form_style", "");
		}

		if(record.update()){
			bjuiAjax(statusCode200);
		}else{
			bjuiAjax(statusCode300);
		}

	}


	/**
	 *	模板
	 */
	public void export() throws IOException {

		String mid = getPara(IConstans.MID);
		if (StrKit.notBlank(mid)) {
			List<KModelField> modelFields = KModelField.me.findByMid(mid);
			KModel kModel = getKModel(mid);
			String xlsid = getPara("xlsid", "-1");
			Map<String, String> titleDynicParams = new HashMap<>();
			titleDynicParams.put("name", kModel.get("mname"));
			super.export(xlsid, modelFields, null, titleDynicParams);
		}
	}

	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "field");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					KModelField record = new KModelField();
					record._setAttrs(map);

					//查询相应得字段是否存在，如果不存在则创建
					KModelField modelField = KModelField.me.findFirst("select * from k_model_field where mid = ? and field_name = ? " +
							"and (field_alias = ? or field_alias is  null) and enabled = 0",record.get("mid"),record.get("field_name"),record.get("field_alias"));

					if(null!=modelField){
						record.set("mfid",modelField.getLong("mfid"));
						record.update();
					}else {
						record.set("mfid",-100);
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
