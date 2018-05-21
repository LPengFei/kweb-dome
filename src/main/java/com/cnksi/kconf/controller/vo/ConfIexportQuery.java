package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 导入导出查询
 */
@KQuery(select = "SELECT ie.*,mname ", from = "from k_iexport ie left join k_model m on ie.modelid=m.mid  ",orderBy = "ie.enabled,ie.ietable")
public class ConfIexportQuery extends KWebQueryVO {

	@KQueryParam(colName = "iename", op = "like")
	private String iename;

	@KQueryParam(colName = "ietype", op = "like")
	private String ietype;

	public ConfIexportQuery() {
		 
	}

	public String getIename() {
		return iename;
	}

	public void setIename(String iename) {
		this.iename = iename;
	}

	public String getIetype() {
		return ietype;
	}

	public void setIetype(String ietype) {
		this.ietype = ietype;
	}

}
