package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class LinkAuthority extends BaseModel<LinkAuthority> {

	public static final LinkAuthority me = new LinkAuthority();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
