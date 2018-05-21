package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Logb extends BaseModel<Logb> {

	public static final Logb me = new Logb();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	public Logb() {
	}

	public Logb(String who, String where, String what, String type) {
		setEnabled(true);
		set("lwho", who);
		set("lwhere", where);
		set("lwhat", what);
		set("lwhen", new Date());
		set("type", type);
	}

	/**
	 * 保存业务日志
	 * 
	 * @param who
	 * @param where
	 * @param what
	 */
	public void saveLogb(String who, String where, String what, String type) {
		Logb record = new Logb();
		record.setEnabled(true);
		record.set("lwho", who);
		record.set("lwhere", where);
		record.set("lwhat", what);
		record.set("lwhen", new Date());
		record.set("type", type);
		record.save();
	}

	/*
	 * 查询用户最近一次登录系统信息
	 */
	public Logb findUserLastLoginInfo(String uaccount) {
		String sql = "select * from " + tableName + " where lwho=? and type=? order by lwhen desc limit 2";
		List<Logb> logbList = find(sql, new Object[] { uaccount, "登录成功" });
		if (logbList.size() == 2) {
			return logbList.get(1);
		} else if (logbList.size() == 1) {
			return logbList.get(0);
		} else {
			return null;
		}

	}

}
