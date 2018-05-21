package com.cnksi.kconf.service;

import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/1/11.
 */
public class UserService {

	public static UserService service = new UserService();

	public List<User> comboboxData() {
		String sql = " select id , uname name  from %s where enabled = 0 order by uname_pinyin asc limit 0,100";
		return User.me.find(String.format(sql, User.me.getTableName()));
	}

	public List<User> principalUserData() {
		String sql = "select id , CONCAT(uname,' ',ifnull(tel,'')) name from %s where enabled =0  order by uname_pinyin asc ";
		return User.me.find(String.format(sql, User.me.getTableName()));
	}

	/**
	 * 根据当前人员所在部门和计划类型 获取下一节点审核人员候选项
	 * 
	 * @param dept=当前登录人员所在部门
	 * @param verifyType={audit_plan_month/audit_plan_week}
	 * @return
	 */
	public List<User> getVerifyUser(Department dept, String verifyType) {
		List<User> users = new ArrayList<>();

		// 月计划审核流程（科室上报-->县调审核-->运检部审核-->地调审核）
		if ("audit_plan_month".equals(verifyType)) {
			if ("keshi".equalsIgnoreCase(dept.getType())) {
				// M1、如果当前操作人员是(非调度)科室成员,则查询当前科室所在基层单位下归口部门为调度，且具有月计划审核权限的人员
				if (!"调度".equalsIgnoreCase(dept.getStr("relevant"))) {
					users = User.me.findUserByJcwdAndRelevant(dept.getJCDWID(), "调度", verifyType);
				} else {
					// M2、如果当前操作人员是基层人员(县调),则查询本部的归口部门为运检，且具有月计划审核权限的人员
					users = User.me.findUserByJcwdAndRelevant(null, "运检", verifyType);
				}
			} else if ("chejian".equalsIgnoreCase(dept.getType())) {// 市运检部和市调度都属于本部单位
				// M3、如果当前操作人员所在部门是车间(变电运维、输电运检、配电运检、变电检修),则查询本部的归口部门为运检，且具有月计划审核权限的人员
				users = User.me.findUserByJcwdAndRelevant(null, "运检", verifyType);

			} else if ("benbu".equalsIgnoreCase(dept.getType()) && "运检".equalsIgnoreCase(dept.getStr("relevant"))) {// 市运检部和市调度都属于本部单位
				// M3、如果当前操作人员所在部门是本部(运检部),则查询本部的归口部门为调度，且具有月计划审核权限的人员
				users = User.me.findUserByJcwdAndRelevant(null, "调度", verifyType);

			} else {
				// 其他角色不能选中审核人员
			}
		}

		return users;
	}

	/**
	 * 查询周计划相关审核人员
	 * 
	 * @param dept
	 * @param verifyType
	 * @return
	 */
	public List<User> getVerifyWeekUser(Department dept, String verifyType) {

		List<User> users = new ArrayList<>();

		// W1、如果当前操作人员是班组人员,则查询当前班组所在科室，且具有周计划审核权限的人员
		if ("banzu".equalsIgnoreCase(dept.getType())) {
			Long keshiId = dept.getLong("pid"); // TODO 修改为查询上级科室
			users = User.me.findWeekAuditPersonByDept(keshiId, verifyType);
		} else if ("keshi".equalsIgnoreCase(dept.getType())) {
			// W1、如果当前操作人员是(非调度)科室成员,则查询当前科室所在基层单位下归口部门为调度，且具有月计划审核权限的人员
			if (!"调度".equalsIgnoreCase(dept.getStr("relevant"))) {
				users = User.me.findUserByJcwdAndRelevant(dept.getJCDWID(), "调度", verifyType);
			}
			// else {
			// // W2、如果当前操作人员是基层人员(县调),则查询本部的归口部门为运检，且具有月计划审核权限的人员
			// users = User.me.findUserByJcwdAndRelevant(null, "运检", verifyType);
			// }
		} else if ("chejian".equalsIgnoreCase(dept.getType())) {// 市运检部和市调度都属于本部单位
			// M3、如果当前操作人员所在部门是车间(变电运维、输电运检、配电运检、变电检修),则查询本部的归口部门为运检，且具有月计划审核权限的人员
			users = User.me.findUserByJcwdAndRelevant(null, "运检", verifyType);

		} else if ("benbu".equalsIgnoreCase(dept.getType()) && "运检".equalsIgnoreCase(dept.getStr("relevant"))) {// 市运检部和市调度都属于本部单位
			// M3、如果当前操作人员所在部门是本部(运检部),则查询本部的归口部门为调度，且具有月计划审核权限的人员
			users = User.me.findUserByJcwdAndRelevant(null, "调度", verifyType);

		} else {
			// 其他角色不能选中审核人员
		}

		return users;
	}
	public  String  formatPrincipal(String id){
		User user = User.me.findById(id);
		if(user!=null){
			return user.get("uname");
		}else{
			return "";
		}

	}
}
