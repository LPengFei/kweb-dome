package com.cnksi.kconf.controller;

import com.cnksi.kconf.controller.vo.ConfModelQuery;
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
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 系统模型管理Controller
 * 
 * @author admin
 *
 */
public class ModelController extends KController {

	public ModelController() {
		super(KModel.class);
	}

	/**
	 * list页面
	 */
	public void index() {
		Integer pageSize = getParaToInt("pageSize");
		ConfModelQuery queryParam = Injector.injectBean(ConfModelQuery.class, null, getRequest(), false);

		if(queryParam!=null) {
			if (pageSize == null) {
				queryParam.setPageSize(150);
			}
			Page<KModel> page = KModel.me.paginate(queryParam);
			List<Record> records = new ArrayList<>();
			page.getList().forEach(kModel -> {
				 String remark = Db.queryStr("SELECT GROUP_CONCAT(u.`mname`) AS mname  FROM k_menu u WHERE u.enabled = 0 " +
						 "and u.`murl` NOT LIKE '%http%' and ( u.`murl` LIKE '%mid="+kModel.getLong("mid")+"' or  u.`murl` like '%mid="+kModel.getLong("mid")+"&%')");
				 Record record = new Record();
				 record.setColumns(kModel);
				 record.set("menu_name",remark);
				records.add(record);
			});

			Page<Record> recordPage = new Page<>(records,page.getPageNumber(),page.getPageSize(),page.getTotalPage(),page.getTotalRow());
			setAttr("page", recordPage);
		}
		keepPara();
		render("kmodel.jsp");
	}

	/**
	 * 新增修改页面
	 */
	public void edit() {
		String idValue = getPara("mid", "");
		KModel model = null;
		if (StrKit.notBlank(idValue)) {
			model = KModel.me.findById(idValue);
		} else {
			model = new KModel();
		}
		setAttr("record", model);

		render("kmodel_form.jsp");
	}

	/**
	 * 保存/更新
	 */
	public void save() {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		KModel model = getModel(KModel.class, "record");
		if (model.get("mid") != null) {
			model.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "model-list");
		} else {
			model.set(model.getPkName(), -100);
			model.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "model-list");
		}
		renderJson(resultMap);

	}

	/**
	 * 删除
	 */
	@Before(Tx.class)
	public void delete() {
		KModel model = KModel.me.findById(getPara("mid"));
		if (model != null) {
			model.set("enabled", 1).update();
			// 删除关联字段配置
			// Db.deleteById(KModelField.me.getTableName(), model.getPkName(), model.getPkVal().toString());

			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 200);
			apiJson.put("message", "删除成功");
			apiJson.put("closeCurrent", false);
			renderJson(apiJson);
		} else {
			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 300);
			apiJson.put("message", "删除失败");
			apiJson.put("closeCurrent", false);
			apiJson.put("tabid", "");
			renderJson(apiJson);
		}
	}

	/**
	 * 复制一个model
	 */
	public void copy() {
		String id = getPara();
		Objects.requireNonNull("id", "id is null");

		boolean result = KModel.me.copyKModelAndFields(id);
		if (result)
			bjuiAjax(200);
		else
			bjuiAjax(300);
	}

	/**
	 * 清除业务表
	 */
	public void clearBusiness(){

		//违章
		Db.update("delete from e_illegal");

		//违章/问题人员
		Db.update("delete from e_pad_bug_person");

		//问题和违章文件
		Db.update("delete from e_pad_datum");

		//到岗到位人员
		Db.update("delete from e_pad_jd_person");

		//监督卡
		Db.update("delete from e_pad_jdk");

		//巡查
		Db.update("delete from e_pad_plan");

		//日管控
		Db.update("delete from e_plan_day");

		//月计划
		Db.update("delete from e_plan_month");

		//审核流程
		Db.update("delete from e_plan_verify");

		//周计划
		Db.update("delete from e_plan_week");

		//年计划
		Db.update("delete from e_plan_year");

		//问题
		Db.update("delete from e_question");

		//短信记录
		Db.update("delete from e_sms_record");

		//变更日志
		Db.update("delete from k_log");

		//日志详情
		Db.update("delete from k_log_detail");

		//日志
		Db.update("delete from k_logb");

        bjuiAjax(200,"成功业务数据成功",false);

	}


	/**
	 *	模板
	 */
	public void export() throws IOException {
		List<KModel> models = KModel.me.findAll();
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, models);
	}

	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "model");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					KModel record = new KModel();
					record._setAttrs(map);

					//查询相应得字段是否存在，如果不存在则创建
					KModel model = KModel.me.findById(record.getLong(IConstans.MID));

					if(null!=model){
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

	public void toexchange(){
		keepPara();
		List<KModel> modelList = KModel.me.findAll();
		setAttr("models", modelList);
		render("kmodel_dataexchange_dialog.jsp");
	}

	@Before(Tx.class)
	public void exchange(){

		String mid = getPara("mid");
		String modelid = getPara("modelid");
		String tabid = getPara("tabid");

		if(StrKit.notBlank(mid,modelid)){

			List<KModelField> modelFields = KModelField.me.findByMid(modelid);
			if(modelFields.size()>0){
				//清除当前模型的数据
				Db.update("update k_model_field set enabled = 1 where mid = ?",mid);
				//设置模型ID
				modelFields.forEach(record -> {record.set("mid",mid);record.set("mfid",null);});
				Db.batchSave(modelFields,modelFields.size());
				bjuiAjax(200,"模型字段覆盖成功！",true,tabid);
			}else{
				bjuiAjax(300,"模型A的字段为空，不能参与字段覆盖！",false);
			}
		}else{

			bjuiAjax(300,"无法获取到相应模型",false);
		}
	}
}
