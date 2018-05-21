package com.cnksi.kconf.controller;

import com.cnksi.kconf.controller.vo.LookupQuery;
import com.cnksi.kconf.controller.vo.LookupTypeQuery;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.LookupType;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.utils.IConstans;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量配置
 */
public class LookupController extends KController {

	public LookupController() {
		super(Lookup.class);
	}

	public void index() {

		KModel m = KModel.me.findByTableName("k_lookup");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		LookupQuery queryParam = Injector.injectBean(LookupQuery.class, null, getRequest(), false);

		if(queryParam!=null) {
			setAttr("page", Lookup.me.paginate(queryParam));
			setAttr("query", queryParam);
		}
		render("lookup.jsp");
	}

	public void edit() {
		KModel m = KModel.me.findByTableName("k_lookup");
		if (m != null) {
			setAttr("model", m);
			List<KModelField> fields = m.getField();
			fields.forEach(f -> f.set("settings", f.getSettings()));
			setAttr("fields", fields);

		}

		String idValue = getPara("id", null);

		Lookup record = null;
		if (idValue != null) {
			record = Lookup.me.findById(idValue);
		} else {
			record = new Lookup();
			record.set("ltid", getPara("ltid"));
		}

		setAttr("record", record);
		render("lookup_form.jsp");
	}

	public void save() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Lookup record = getModel(Lookup.class, "record");

		if (record.get("lkid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");

			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lookup-list");
		} else {
			// record.set(record.getPkName(), UUID.randomUUID().toString());
			record.set(record.getPkName(), -100);
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lookup-list");
		}

		renderJson(resultMap);
	}

	public void delete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Lookup record = Lookup.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "删除成功");

			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			resultMap.put("message", "删除失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}

	/**
	 * 获取typeid下的kv
	 */
	public void json() {
		String typeid = getPara("typeid");
		List<Record> lookups = Lookup.me.findByTypeidData(typeid);

		renderJson(lookups);
	}

	/**
	 * 获取所有Lookup类型
	 */
	public void types() {

		KModel m = KModel.me.findByTableName("k_lookup_type");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		LookupTypeQuery queryParam = Injector.injectBean(LookupTypeQuery.class, null, getRequest(), false);

		if(queryParam!=null) {
			Integer pageSize = getParaToInt("pageSize");
			if (pageSize == null) {
				queryParam.setPageSize(150);
			}
			Page<LookupType> page = LookupType.me.paginate(queryParam);

			List<Record> records = new ArrayList<>();
			page.getList().forEach(lookupType -> {
				List<Record> remark = Db.find("SELECT GROUP_CONCAT(CONCAT(m.`mname`,'\\(',m.mid,'\\)')) AS mfname,mf.fname remark" +
						" FROM k_model m INNER JOIN (SELECT f.mid,GROUP_CONCAT(f.`field_name`) fname FROM k_model_field f WHERE f.`enabled` = 0 " +
						"AND f.`form_data_source` IN (SELECT d.`dataurl` FROM k_datasource d WHERE d.`dsname` = ? AND d.enabled = 0) GROUP BY f.`mid`) mf " +
						"ON mf.mid = m.`mid` WHERE m.`enabled` = 0 GROUP BY mf.fname ", lookupType.getStr("tname"));
				Record record = new Record();
				record.setColumns(lookupType);
				if (null != remark && remark.size() > 0) {
					record.set("model_filed", remark);
				}
				Long count = Db.queryLong("SELECT COUNT(1) FROM k_lookup WHERE ltid = ? and enabled = 0",lookupType.getStr("ltid"));
				record.set("count",count);

				records.add(record);

			});
			Page<Record> recordPage = new Page<>(records,page.getPageNumber(),page.getPageSize(),page.getTotalPage(),page.getTotalRow());
			setAttr("page", recordPage);
			setAttr("query", queryParam);
		}
		render("lookuptype.jsp");
	}

	/**
	 * 编辑新增类型
	 */
	public void typeEdit() {
		KModel m = KModel.me.findByTableName("k_lookup_type");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		String idValue = getPara("id", null);

		LookupType record = null;
		if (idValue != null) {
			record = LookupType.me.findById(idValue);
		} else {
			record = new LookupType();
		}

		setAttr("record", record);
		render("lookuptype_form.jsp");
	}

	/**
	 * 保存、更新类型
	 */
	public void typeSave() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		LookupType record = getModel(LookupType.class, "record");
		if (record.getStr("ltid") != null) {
			LookupType _type = LookupType.me.findById(record.getStr("ltid"));
			if (_type != null) {
				record.update();
			} else {

				Datasource ds = new Datasource();
				ds.set("dsid", -100);// 设置为-100数据库自增
				ds.set("dsname", record.getStr("tname"));
				ds.set("dataurl", "/kconf/lookup/json?typeid=" + record.getStr("ltid"));
				ds.save();
				record.save();
			}
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");

			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lktype-list");
		} else {
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lktype-list");
		}

		renderJson(resultMap);
	}

	/**
	 * 删除类型
	 */
	public void typeDelete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		LookupType record = LookupType.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "删除成功");

			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			resultMap.put("message", "删除失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}

	/**
	 * 类型JSON数据
	 */
	public void typeJson() {
		List<Record> types = LookupType.me.findJson();
		renderJson(types);
	}



	/**
	 *	模板
	 */
	public void export() throws IOException {

		List<Lookup> lookups = Lookup.me.find(" SELECT t.`tname`,l.*,t.`type`,t.`remark` AS type_remark FROM k_lookup l " +
				"LEFT JOIN k_lookup_type t ON t.`ltid` = l.`ltid` WHERE l.`enabled` = 0 AND t.`enabled` = 0 ");
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, lookups);
	}

	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "lookup");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					LookupType lookupType = new LookupType();
					lookupType._setAttrs(map);
					lookupType.set("remark",map.get("type_remark"));
					LookupType lookupType1 = LookupType.me.findById(lookupType.get("ltid"));
					if(null==lookupType1){
						lookupType.save();
					}

					Lookup lookup = new Lookup();
					lookup._setAttrs(map);
					if(!"私有常量".equals(lookupType.getStr("type"))){

						Lookup record = Lookup.me.findFirst("select lkid from k_lookup where ltid = ? and lkey = ? and enabled = 0",lookup.getStr("ltid"),lookup.getStr("lkey"));

						if(null!=record){
							lookup.set("lkid",record.getLong("lkid"));
							lookup.update();
						}else if(null==record){
							lookup.set("lkid",-100);
							lookup.save();
						}
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
