package com.cnksi.kcore.klogs.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 系统日志详情记录
 * 
 * 针对每条记录都有哪些字段被修改了
 * 
 */
public class KLogDetail extends BaseModel<KLogDetail> {

	private static final long serialVersionUID = 1L;
	public static final KLogDetail me = new KLogDetail();

	public KLogDetail() {
		super();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Class getCls() {
		return this.getClass();
	}
}
