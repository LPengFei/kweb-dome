package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class UserRole extends BaseModel<UserRole> {

	public static final UserRole me = new UserRole();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 根据用户id查询角色
	 * 
	 * @param roleid
	 * @return
	 */
	public UserRole findByUserId(String propertity, Object userid) {
		String sql = String.format("select * from %s where %s = ?", tableName, propertity);

		return findFirst(sql, userid);
	}
}
