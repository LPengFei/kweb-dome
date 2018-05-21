package com.cnksi.yw.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

@SuppressWarnings("serial")
public class Training extends BaseModel<Training> {

	public static final Training me = new Training();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<Record> getDeployList(String  projectId ){
		String sql = "select * from y_training where enabled=0 and project_id=? order by end_time desc";
		return Db.find(sql,projectId);
	}

}
