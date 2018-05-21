package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;


/**
 * 用户附加功能
 * 
 * @author zf
 *
 */
@SuppressWarnings("serial")
public class UserDefine extends BaseModel<UserDefine> {

	public static final UserDefine me = new UserDefine();

	public UserDefine getUserDefine(Long id){
		String sql = String.format("select * from %s where %s = ?",tableName,pkName);

		return findFirst(sql,id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}


}
