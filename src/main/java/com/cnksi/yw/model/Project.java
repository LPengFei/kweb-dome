package com.cnksi.yw.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Project extends BaseModel<Project> {

	public static final Project me = new Project();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}


	public List<Record> findJson() {
		String sql = "select name,id from y_project where enabled=0 order by sort desc";
		return Db.find(sql);
	}
}
