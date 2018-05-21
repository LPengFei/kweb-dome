package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 用户
 * 
 * @author joe
 *
 */
public class User extends BaseModel<User> {

	private static final long serialVersionUID = 1L;
	public static final User me = new User();

	/**
	 * 获取当前用户所在部门
	 * 
	 * @return Department
	 */
	public Department getDepartment() {
		return Department.me.findById(getLong("dept_id"));
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 获取当前用户的角色
	 * 
	 * @return
	 */
	public Role getRole() {
		return Role.me.findById(getLong("role_id"));
	}

	/**
	 * 根据用户名查询用户
	 *
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		String sql = String.format("select * from %s where  enabled = 0 and (uaccount = ? or tel = ?)", tableName);
		return findFirst(sql, username, username);
	}

	public String getName() {
		return getStr("uname");
	}

	public String getTel() {
		return get("tel");
	}

	/**
	 * 查询审核用户 示例：select id,uname as name from k_user where dept_id in (select id from k_department where jcdwid=35 and relevant='调度') and find_in_set(audit_type,'audit_plan_month')>0
	 * 
	 * @param jcdwid=基层单位ID
	 * @param relevant={归口部门:调度、运检、安监、其他}
	 * @param audit_plan_type={audit_plan_month,audit_plan_week}
	 * @return
	 */
	public List<User> findUserByJcwdAndRelevant(Long jcdwid, String relevant, String audit_plan_type) {
		if (jcdwid != null) {
			String sql = String.format("select id,uname as name from %s where dept_id in (select id from k_department where jcdwid=? and relevant=?) and find_in_set(?,audit_type)>0", tableName);
			return find(sql, jcdwid, relevant, audit_plan_type);
		} else { // 无基层单位，则查询本部(根据部门type查询)
			String sql = String.format("select id,uname as name from %s where dept_id in (select id from k_department where type=? and relevant=?) and find_in_set(?,audit_type)>0", tableName);
			return find(sql, "benbu", relevant, audit_plan_type);
		}
	}

	/**
	 * 查询某个部门下的工作负责人
	 * 
	 * @param deptid
	 * @return
	 */
	public List<User> findGzfzrByDept(Long deptid) {
		// 查询当前及当前部门一下的所有部门
		String childIdLst = Department.me.getChildLst(deptid);
		StringBuilder sb = new StringBuilder("SELECT id,CONCAT(uname , ' ' ,IFNULL(tel,'')) name from k_user WHERE enabled = 0 and qualification like ? and dept_id in (" + childIdLst + ")");
		return find(sb.toString(), "%工作负责人%");
	}

	/**
	 * 查询keshiId部门下，具有周计划审核权限的人员
	 * 
	 * @param keshiId
	 * @return
	 */
	public List<User> findWeekAuditPersonByDept(Long keshiId, String audit_plan_type) {
		StringBuilder sb = new StringBuilder("SELECT id,uname name from k_user WHERE enabled = 0 and find_in_set(?,audit_type)>0 and dept_id  = ? ");
		return find(sb.toString(), audit_plan_type, keshiId);
	}
}
