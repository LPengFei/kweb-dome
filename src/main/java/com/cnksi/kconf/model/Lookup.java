package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 系统常量Lookup
 */
@SuppressWarnings("serial")
public class Lookup extends BaseModel<Lookup> {

	public static final Lookup me = new Lookup();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 获取typeid下的key/value
	 *
	 * @param typeid
	 * @return
	 */
	public List<Record> findByTypeidData(String typeid) {
		String sql = "select lkey as id ,lvalue as name from " + tableName + " where ltid= ? and enabled=0 order by sort asc ";
		return Db.find(sql, typeid);
	}

	public List<Lookup> findByTypeId(String ltid) {
		String sql = "select  * from %s where ltid=  ? and enabled= 0 order by sort ";
		return find(String.format(sql, tableName), ltid);
	}

	public Lookup findByTypeidAndKey(String ltid, String key) {
		String sql = "select * from %s where ltid = ? and lkey = ?  and enabled = 0";
		return findFirst(String.format(sql, getTableName()), ltid, key);
	}

	public Lookup findbyTypeids(List<String> lkeys,String ltid){
		StringBuffer buffer = new StringBuffer("SELECT GROUP_CONCAT(lvalue) AS lvalue FROM k_lookup WHERE ltid=? AND lkey IN (");
		buffer.append("'"+ lkeys.get(0) +"'");
		for(String lkey:lkeys){
			buffer.append(",'"+lkey+"'");
		}
		buffer.append(") and enabled = 0");
		return  findFirst(buffer.toString(),new Object[]{ltid});
	}
	public String getValue() {
		return get("lvalue");
	}

	public List<Record> findTree(String ltid) {
		String sql = "select lkid as id, pid as pId, lkey as name from k_lookup where enabled=0 and ltid = ? order by sort asc ";
		return Db.find(sql, ltid);
	}
}
