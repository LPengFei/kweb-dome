package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;
/**
 * 系统模型链接查询VO
 * 
 * @author rocher
 *
 */

@KQuery(select = "SELECT * ", from = "FROM  k_model_link ",orderBy = "ord")
public class ConfModelLinkQuery extends KWebQueryVO {
	
	/**
	 * 模型id
	 */
	@KQueryParam(colName = "mid", op = "=")
	private String mid;

	/**
	 * 字段名称
	 */
	@KQueryParam(colName = "label", op = "like")
	private String label;
	
	public ConfModelLinkQuery() {
		addFilter("enabled", "=", "0");
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
	
}
