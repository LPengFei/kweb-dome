package com.cnksi.kconf.controller;

import com.cnksi.kconf.model.KChart;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.io.IOException;
import java.util.Objects;

/**
 * 统计图配置
 */
 public class ChartController extends KController {

 	public ChartController(){
		super(KChart.class);
	}

	/**
	 * 获取图表统计数据
	 */
	public void chartData() {
		Objects.requireNonNull(getPara("id"), "id is null");

		KChart chart = KChart.me.findById(getPara("id"));
		if(Objects.isNull(chart)){
			renderNull();
			return;
		}

		chart.put("data", Db.find(chart.getStr("sql")));
		renderJson(chart);
	}

	public void list() {

		KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) setAttr("page", KChart.me.paginate(queryParam));

		render(listJsp);
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		KChart record = null;
		if (idValue != null) {
			record = KChart.me.findById(idValue);
		}else{
			record = new KChart();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		KChart record = getModel(KChart.class, "record");
		if (record.get("chartid") != null) {
			record.update();
		} else {
			record.save();
		}

		bjuiAjax(200, true);
	}



	public void delete() {
		KChart record = KChart.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}

	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<KChart> p = KChart.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}

}
