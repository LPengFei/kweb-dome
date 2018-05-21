package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 统计图配置
 */
@SuppressWarnings("serial")
public class KChart extends BaseModel<KChart> {

	public static final KChart me = new KChart();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}


}
