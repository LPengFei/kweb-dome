package com.cnksi.kconf.controller;

import java.util.HashMap;
import java.util.Map;

import com.cnksi.kconf.controller.vo.ConfModelLinkQuery;
import com.cnksi.kconf.model.ModelLink;
import com.cnksi.kcore.jfinal.model.KModel;
import com.jfinal.core.Injector;

/**
 * 系统模型链接Controller
 */
public class ModelLinkController extends KController {

	public ModelLinkController() {
		super(ModelLink.class);
	}

	public void index() {
		ConfModelLinkQuery queryParam = Injector.injectBean(ConfModelLinkQuery.class, null, getRequest(), false);
		setAttr("page", ModelLink.me.paginate(queryParam));
		keepPara();
		render("kmodellink.jsp");
	}

	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		String mid = getPara("mid");
		ModelLink record = null;
		if (idValue != null) {
			record = ModelLink.me.findById(idValue);
		} else {
			record = new ModelLink();
			record.set("mid", mid);

		}
		KModel model = KModel.me.findById(mid);

		setAttr("record", record);
		setAttr("mname", model.get("mname"));
		render("kmodellink_form.jsp");
	}

	public void save() {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ModelLink record = getModel(ModelLink.class, "r");
		if (record.get("id") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "modellinks-list");
		} else {
			record.set(record.getPkName(), -100);
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "modellinks-list");
		}

		renderJson(resultMap);
	}

	public void delete() {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ModelLink model = ModelLink.me.findById(getPara("id"));
		if (model != null) {
			model.set("enabled", 1).update();
			resultMap.put("statusCode", 200);
			resultMap.put("message", "删除成功");
			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", 200);
			resultMap.put("message", "删除失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}
}
