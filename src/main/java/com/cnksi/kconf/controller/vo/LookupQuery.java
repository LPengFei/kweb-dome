package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

@KQuery(select = "SELECT * ", from = " from k_lookup ", orderBy = " sort asc ")
public class LookupQuery extends KWebQueryVO {

	public LookupQuery() {
		addFilter(" enabled=0 ");
	}

	@KQueryParam(colName = "lkey", op = "like")
	private String k;

	@KQueryParam(colName = "ltid", op = " = ")
	private String type;

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
