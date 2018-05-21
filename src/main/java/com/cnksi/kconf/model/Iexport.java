package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 导入导出模型
 */
@SuppressWarnings("serial")
public class Iexport extends BaseModel<Iexport> {

	public static final Iexport me = new Iexport();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	public List<Iexport> findByModelId(String type, Long modelid) {
		String sql = " select * from %s where ietype =? and modelid = ? and enabled = 0 ";

		return find(String.format(sql, tableName), type, modelid);
	}
}
