package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 菜单查询VO
 */
@KQuery(select = "SELECT * ", from = " from k_menu  ")
public class MenuQuery extends KWebQueryVO {

	@KQueryParam(colName = "mname", op = "like")
	private String mname;
	
	@KQueryParam(colName = "pmenuid", op = "=")
	private String pmenuid;

	public MenuQuery() {
		addFilter(" enabled=0 ");
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getPmenuid() {
		return pmenuid;
	}

	public void setPmenuid(String pmenuid) {
		this.pmenuid = pmenuid;
	}

}
