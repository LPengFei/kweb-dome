package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;
/**
 * 导出/导入 字段配置
 */
@SuppressWarnings("serial")
public class Iefield extends BaseModel<Iefield> {

	public static final Iefield me = new Iefield();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
	
	
	/**
	 * 根据ieid查询所有字段
	 * @param ieid
	 * @return
	 */
	public List<Iefield> findByIeid(String ieid){
		String sql = String.format("SELECT * FROM %s where ieid = ? and enabled=0 ORDER BY sort", tableName);
		
		return find(sql, ieid);
	}
}
