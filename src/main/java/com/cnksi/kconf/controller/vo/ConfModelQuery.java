package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 系统模型查询VO
 * 
 * @author joe
 *
 */
@KQuery(select = "SELECT * ", from = " FROM k_model ",orderBy=" mtable,mname desc ")
public class ConfModelQuery extends KWebQueryVO {

	/**
	 * 模型名称
	 */
	@KQueryParam(colName = "mname", op = "like")
	private String mname;

	/**
	 * 表名称
	 */
	@KQueryParam(colName = "mtable", op = "like")
	private String mtable;

	public ConfModelQuery() {
		addFilter("enabled", "=", 0);
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMtable() {
		return mtable;
	}

	public void setMtable(String mtable) {
		this.mtable = mtable;
	}
}
