package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;

@SuppressWarnings("serial")
public class ModelFieldRole extends BaseModel<ModelFieldRole> {

	public static final ModelFieldRole me = new ModelFieldRole();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 查找当前角色配置有那些字段id
	 * 
	 * @param rid
	 * @return
	 */
	public String getFieldidByRole(Long rid,String mid) {
		String sql = "select group_concat(mfid)  from k_model_field_role where rid = ? and mid=? and enabled = 0";
		return Db.queryStr(sql, rid,mid);
	}

	/**
	 * 根据模型ID删除数据
	 * 
	 * @param mid
	 */
	public void deleteByMid(String mid) {
//		String sql = "delete from " + tableName + " where mid = ? ";
		String sql = "update " + tableName + " set enabled = 1 where mid = ? ";
		Db.update(sql, mid);
	}
}
