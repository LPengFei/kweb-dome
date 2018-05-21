package com.cnksi.kconf.controller;

import java.io.IOException;

import com.cnksi.kconf.controller.vo.DatasourceQuery;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.core.Injector;
import com.jfinal.plugin.activerecord.Page;

/**
 * 数据源配置
 */
public class DatasourceController extends KController {

	public DatasourceController() {
		super(Datasource.class);
	}

	public void index() {
		Integer pageSize = getParaToInt("pageSize");
		DatasourceQuery queryParam = Injector.injectBean(DatasourceQuery.class, null, getRequest(), false);
		if (queryParam != null) {

			if(pageSize==null){
				queryParam.setPageSize(150);
			}
			setAttr("page", Datasource.me.paginate(queryParam));
		}
		render(modelName + ".jsp");
	}

	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		Datasource record = null;
		if (idValue != null) {
			record = Datasource.me.findById(idValue);
		} else {
			record = new Datasource();

		}
		setAttr("record", record);

		render(modelName + "_form.jsp");
	}

	public void save() {
		Datasource record = getModel(Datasource.class, "record");
		if (record.get("dsid") != null) {
			record.update();
		} else {
			record.set("dsid", -100);
			record.save();
		}

		bjuiAjax(200, true);
	}

	public void delete() {
		Datasource record = Datasource.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}

	public void export() throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<Datasource> p = Datasource.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}
}
