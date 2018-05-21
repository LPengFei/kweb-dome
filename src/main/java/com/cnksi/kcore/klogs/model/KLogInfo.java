package com.cnksi.kcore.klogs.model;

import java.util.Date;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 系统日志记录
 * 
 * 谁在什么时候做了什么事儿
 * 
 */
public class KLogInfo extends BaseModel<KLogInfo> {

	private static final long serialVersionUID = 1L;
	public static final KLogInfo me = new KLogInfo();

	public KLogInfo() {
		super();
	}

	/**
	 * 系统日志
	 * 
	 * @param tableName数据库表名称
	 * @param businessName业务名称
	 * @param visitURL访问地址
	 * @param pkValue主键值
	 * @param operator操作人员
	 * @param operatorType操作类型
	 */
	public KLogInfo(String tableName, String businessName, String visitURL, String pkValue, String operator, String operatorType) {
		set("tablename", tableName);
		set("businessName", businessName);
		set("visitURL", visitURL);
		set("pkValue", pkValue);
		set("operator", operator);
		set("operatetype", operatorType);
		set("operatetime", new Date());
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Class getCls() {
		return this.getClass();
	}
}
