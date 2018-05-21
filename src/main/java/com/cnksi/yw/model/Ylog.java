package com.cnksi.yw.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

@SuppressWarnings("serial")
public class Ylog extends BaseModel<Ylog> {

	public static final Ylog me = new Ylog();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<Record> getYlogList(String  ids ){
		String sql = "SELECT u.email, p.full_name AS project_name, l.content AS job_content, l.used_time AS job_duration, l.start_time AS jb_date, l.work_type AS type_name FROM y_ylog l LEFT JOIN k_user u ON l.user_id = u.id LEFT JOIN y_project p ON l.project_id = p.id where l.enabled = 0 AND l.id IN  (" + ids + ")";
		return Db.find(sql);
	}
}
