package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 角色查询
 */
@KQuery(select = "SELECT * ", from = " from k_role ", orderBy = " level desc ")
public class RoleQuery extends KWebQueryVO {

	/**
	 * 角色名称
	 */
	@KQueryParam(colName = "rname", op = "like")
	private String rname;

	public RoleQuery() {
		addFilter(" enabled=0 ");
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

}
