package com.cnksi.kconf.service;


import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.utils.IConstans;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class DepartmentService {

	public static DepartmentService service = new DepartmentService();


	public Department setparam(Department record){
		if(StrKit.notNull(record.getLong(IConstans.PID))){
			Department dept = Department.me.findById(record.getLong(IConstans.PID));
			record.put("dept_pid",dept==null?"":dept.getName());
		}
		if(StrKit.notNull(record.getLong(IConstans.JCDWID))){
			Department dept = Department.me.findById(record.getLong(IConstans.JCDWID));
			record.put("dept_jcdwid",dept==null?"":dept.getName());
		}
		if(StrKit.notBlank(record.getStr(IConstans.TYPE))){
			Lookup lookup = Lookup.me.findByTypeidAndKey("department_type",record.getStr(IConstans.TYPE));
			if(lookup!=null){
				record.set("type",lookup.getValue());
			}
		}
		if(StrKit.notNull(record.getStr(IConstans.NATURE))){
			Lookup lookup = Lookup.me.findByTypeidAndKey("department_nature",record.getStr(IConstans.NATURE));
			if(lookup!=null){
				record.set(IConstans.NATURE,lookup.getValue());
			}
		}
     return  record;
	}
}
