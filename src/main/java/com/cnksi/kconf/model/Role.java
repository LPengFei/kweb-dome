package com.cnksi.kconf.model;

import java.util.ArrayList;
import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Role extends BaseModel<Role> {

	public static final Role me = new Role();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 根据当前用户的角色查询其子角色JSON
	 * 
	 * @return
	 */
	public List<Record> findJson(Object role_id) {

		if (role_id != null) {
			String sql = "select rname as name ,id from " + tableName + " where  enabled=0 and level<=(select level from k_role where id=? limit 1) order by level desc";
			return Db.find(sql, role_id);
		} else {
			return new ArrayList<>();
		}
	}

	public String getName() {
		return getStr("rname");
	}
}
