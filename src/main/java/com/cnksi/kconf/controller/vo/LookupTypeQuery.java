package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 枚举类型管理
 */
@KQuery(select = "SELECT * ", from = " from k_lookup_type  ",orderBy = "type desc")
public class LookupTypeQuery extends KWebQueryVO {

	/**
	 * 类型名称
	 */
	@KQueryParam(colName = "tname", op = "like")
	private String tname;

	public LookupTypeQuery() {
		addFilter(" enabled=0 ");
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

}
