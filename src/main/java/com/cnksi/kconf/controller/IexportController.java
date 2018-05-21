package com.cnksi.kconf.controller;

import com.cnksi.kconf.controller.vo.ConfIefieldQuery;
import com.cnksi.kconf.controller.vo.ConfIexportQuery;
import com.cnksi.kconf.model.*;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.utils.IConstans;
import com.jfinal.aop.Before;
import com.jfinal.core.Injector;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel导入管理
 */
public class IexportController extends KController {
	public IexportController() {
		super(Iexport.class);
	}

	public void index() {
		// KModel m = KModel.me.findByTableName("k_iexport");
		// if (m != null) {
		// setAttr("model", m);
		// setAttr("fields", m.getField());
		// }
		Integer pageSize = getParaToInt("pageSize");
		ConfIexportQuery queryParam = Injector.injectBean(ConfIexportQuery.class, null, getRequest(), false);

		if(queryParam!=null) {
			if (pageSize == null) {
				queryParam.setPageSize(150);
			}
			setAttr("page", Iexport.me.paginate(queryParam));
			setAttr("query", queryParam);
		}
		render("iexport.jsp");
	}

	public void edit() {
		// KModel m = KModel.me.findByTableName("k_iexport");
		// if (m != null) {
		// setAttr("model", m);
		// setAttr("fields", m.getField());
		// }
		String idValue = getPara("id", null);

		Iexport record = null;
		if (idValue != null) {
			record = Iexport.me.findById(idValue);
		} else {
			record = new Iexport();
		}

		List<KModel> modelList = KModel.me.findAll();
		setAttr("models", modelList);
		setAttr("record", record);
		render("iexport_form.jsp");
	}

	public void save() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iexport record = getModel(Iexport.class, "record");
		if (record.get("ieid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iexport-list");
		} else {
			record.set("ieid", -100);
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iexport-list");
		}

		renderJson(resultMap);
	}

	/**
	 * 对导入导出配置启用和禁用
	 */
	public void delete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iexport record = Iexport.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", getPara("enabled")).update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "操作成功");

			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			resultMap.put("message", "操作失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}

	public void fields() {
		KModel m = KModel.me.findByTableName("k_iefield");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}

		ConfIefieldQuery queryParam = Injector.injectBean(ConfIefieldQuery.class, null, getRequest(), false);
		queryParam.setOrderField("enabled,sort");
		queryParam.setOrderDirection("asc");
		queryParam.setPageSize(1000);

		setAttr("iexport", Iexport.me.findById(queryParam.getIeid()));
		setAttr("page", Iefield.me.paginate(queryParam));
		setAttr("query", queryParam);
		render("iefield.jsp");
	}

	public void fieldEdit() {
		KModel m = KModel.me.findByTableName("k_iefield");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		String idValue = getPara("id", null);

		Iefield record = null;
		if (idValue != null) {
			record = Iefield.me.findById(idValue);
		} else {
			record = new Iefield();
			record.set("ieid", getParaToLong("ieid"));
		}
		List<LookupType> lookupTypes = new ArrayList<>();
		if(null!=record.getLong("ieid")){
			Iexport iexport = Iexport.me.findById(record.getLong("ieid"));
			if(iexport!=null && StrKit.notNull(iexport.getLong("modelid"))){
				lookupTypes = LookupType.me.find("SELECT * FROM k_lookup_type WHERE tname IN(" +
						"SELECT dsname FROM k_datasource WHERE dataurl IN (" +
						"SELECT form_data_source FROM k_model_field WHERE mid = ? AND  form_data_source IS NOT NULL AND enabled = 0) AND enabled = 0) AND enabled = 0",iexport.getLong("modelid"));
			}

		}
		// 数据源配置
		List<LookupType> dsList = LookupType.me.find("select * from k_lookup_type where (type != '系统常量' or type is null) and enabled = 0");
		dsList.removeAll(lookupTypes);
		lookupTypes.addAll(dsList);
		setAttr("dsList", lookupTypes);

		setAttr("iexport", Iexport.me.findById(record.getLong("ieid")));
		setAttr("record", record);
		render("iefield_form.jsp");
	}

	public void fieldSave() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iefield record = getModel(Iefield.class, "record");
		if (record.get("iefid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iefield-list");
		} else {
			record.set("iefid", -100);
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iefield-list");
		}

		renderJson(resultMap);
	}

	public void fieldDelete() {
		Iefield record = Iefield.me.findById(getPara("id"));
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
				if (null==record.getInt(updatecolumn) || record.getInt(updatecolumn) == 0) {
					if("enabled".equals(updatecolumn)){
						record.set("sort",300);
						record.set("required",1);
						record.set("allow_blank",0);
					}
					record.set(updatecolumn, 1).update();
				} else {
					if("enabled".equals(updatecolumn)){
						Integer sort = Db.queryInt("SELECT MAX(sort) FROM k_iefield WHERE ieid = ? AND enabled = 0",record.getLong("ieid"));
						if(!StrKit.notNull(sort)) sort = 0;
						record.set("sort",sort+5);
					}
					record.set(updatecolumn, 0).update();
				}
			}
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}


	/**
	 *	模板
	 */
	public void export() throws IOException {
		List<Iexport> iexports = Iexport.me.find("select * from k_iexport where enabled = 0");
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, iexports);
	}

	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "iexport");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Iexport record = new Iexport();
					record._setAttrs(map);

					//查询相应得字段是否存在，如果不存在则创建
					Iexport iexport = Iexport.me.findById(record.getLong("ieid"));

					if(null!=iexport){
						record.update();
					}else {
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

	/**
	 * 重置字段
	 */
	public void reset(){

		String ieid = getPara("ieid");

		String tabid = getPara("tabid");
		//重置数据
		Db.update("UPDATE k_iefield SET sort = 300,required=0,allow_blank=0,enabled=1 WHERE ieid =?",ieid);

		bjuiAjax(200,"重置成功！",false,tabid);

	}

	//将表单顺序重置到导入列表中
	public void resetform(){

		String mid = getPara(IConstans.MID);

		String ieid = getPara("ieid");

		String tabid = getPara("tabid");

		//重置数据
		Db.update("UPDATE k_iefield SET sort = 300,required=0,allow_blank=0,enabled=1 WHERE ieid =?",ieid);

		List<KModelField> modelFields = KModelField.me.find("SELECT mid,field_name,field_alias,form_required,list_format,form_sort,form_data_source " +
				"FROM k_model_field WHERE mid = ? AND form_view_type <> 'hidden' AND form_view = 'true' and enabled = 0 GROUP BY field_name,MID",mid);

		//查询当前导入所有导入字段
		List<Iefield> iefields = Iefield.me.find("SELECT * FROM k_iefield where ieid = ? group by field_name",ieid);
		Map<String,Iefield> iefieldMap = iefields.stream().collect(Collectors.toMap(iefield->iefield.getStr("field_name"),iefield->iefield));

		List<Iefield> iefieldList = new ArrayList<>();
		for(KModelField modelField:modelFields){
			Iefield iefield = new Iefield();

			//如果存在则更新数据
			if(null!=iefieldMap && iefieldMap.get(modelField.getStr("field_name"))!=null){

                 iefield = iefieldMap.get(modelField.getStr("field_name"));

				iefield=setIefield(iefield,modelField);
                iefield.update();
			}else{
				//不存在则添加导入数据
				iefield.set("ieid",ieid);
				iefield.set("field_name",modelField.getStr("field_name"));

				iefield=setIefield(iefield,modelField);
				iefieldList.add(iefield);
			}

		}
		Db.batchSave(iefieldList,iefieldList.size());

		bjuiAjax(200,"数据导入成功",false,tabid);
	}

	private Iefield setIefield(Iefield iefield,KModelField modelField){
		//更新是否导入
		iefield.set("enabled",0);
		//更新字段名称
		iefield.set("field_alias",modelField.getStr("field_alias"));
		//更新必包含列
		if("true".equals(modelField.getStr("form_required"))){
			iefield.set("required",1);
			iefield.set("allow_blank",1);
		}else{
			iefield.set("required",0);
			iefield.set("allow_blank",0);
		}
		//设置日期格式
		iefield.set("format",modelField.getStr("list_format"));
		iefield.set("sort",modelField.getStr("form_sort"));
		//设置常量
		if(StrKit.notBlank(modelField.getStr("form_data_source"))){
			String lookup = Db.queryStr("SELECT ltid FROM k_lookup_type WHERE tname IN(SELECT dsname FROM k_datasource WHERE dataurl IN  " +
					"(SELECT form_data_source FROM k_model_field WHERE MID = ? AND  form_data_source = ? AND enabled = 0) " +
					" AND enabled = 0) AND enabled = 0",modelField.get(IConstans.MID),modelField.getStr("form_data_source"));
			iefield.set("data_lookup",lookup);
		}
		return iefield;
	}


	public void toexchange(){
		keepPara();
		List<Iexport> iexports = Iexport.me.find("SELECT * FROM k_iexport WHERE enabled = 0 AND modelid IS NOT NULL AND ietype = '导入'");
		setAttr("iexports", iexports);
		render("iefield_dataexchange_dialog.jsp");
	}

	@Before(Tx.class)
	public void exchange(){

		String ieid = getPara("ieid");
		String p_ieid = getPara("p_ieid");
		String tabid = getPara("tabid");

		if(StrKit.notBlank(ieid,p_ieid)){
            //重置数据
			Db.update("UPDATE k_iefield SET sort = 300,required=0,allow_blank=0,enabled=1 WHERE ieid =?",ieid);
			List<Iefield> iefields = Iefield.me.find("select * from k_iefield where ieid = ? group by field_name",ieid);
			Map<String,Iefield> iefieldMap = iefields.stream().collect(Collectors.toMap(iefield->iefield.getStr("field_name"),iefield->iefield));

			List<Iefield> p_Iefields = Iefield.me.findByIeid(p_ieid);

			List<Iefield> records = new ArrayList<>();
			for(Iefield iefield:p_Iefields){
				Iefield record = new Iefield();
				if(StrKit.notBlank(iefield.getStr("field_name")) && iefieldMap.get(iefield.getStr("field_name"))!=null){

					record=iefieldMap.get(iefield.getStr("field_name"));
					record.set("field_alias",iefield.getStr("field_alias"));
					record.set("sort",iefield.getStr("sort"));
					record.set("enabled",0);
                    record.update();
				}else{
					record._setAttrs(iefield);
					record.set("ieid",ieid);
					record.set("required",0);
					record.set("iefid",null);
					records.add(record);
				}
			}
			Db.batchSave(records,records.size());
			bjuiAjax(200,"模型字段覆盖成功！",true,tabid);
		}else{

			bjuiAjax(300,"无法获取到相应模型",false);
		}
	}
}
