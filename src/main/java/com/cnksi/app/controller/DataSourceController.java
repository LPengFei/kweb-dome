package com.cnksi.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.User;
import com.cnksi.kconf.service.UserService;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

/**
 * Created by Joey on 2017/2/15. 数据源Controller
 *
 */
public class DataSourceController extends Controller {

	public void findModel(){
        StringBuilder builder = new StringBuilder("select mid id,mname name from k_model where enabled = 0");
        renderJson(Db.find(builder.toString()));
	}

	/**
	 * 计划类型树 数据源 {name,pId,id,open}
	 */
	public void planType() {
		String sql = "select lkey id , pid pId , lvalue name from k_lookup where ltid = 'plan_type' and enabled=0 ";
		renderJson(Lookup.me.find(sql));
	}
	
	/**
	 * 动态计划类型树 数据源 {name,pId,id,open}
	 */
	public void planTypeDifelition() {
		//User loginUser = getLoginUser();
		Department loginDept = getLoginDept();
		String plan_type = loginDept.get("plan_type");
		if (StringUtils.isNotBlank(plan_type)) {
			String[] split = plan_type.split(",");
			String str="";
			for (int i = 0; i < split.length; i++) {
				str= str+"'"+split[i]+"',";
			}
			str = str.substring(0, str.length()-1);
		String sql = "select lkey id , pid pId , lvalue name from k_lookup where ltid = 'plan_type' and lkey in("+str+") and enabled=0 ";
		renderJson(Lookup.me.find(sql));
		}else {
			
		String sql = "select lkey id , pid pId , lvalue name from k_lookup where ltid = 'plan_type' and enabled=0 ";
		renderJson(Lookup.me.find(sql));
		}
	}

	/**
	 * 变电站或线路控件数据源
	 */
	public void bdzOrLine() {
		Department dept = getLoginDept();
		String type = getPara("type");
		StringBuilder sql = new StringBuilder("SELECT bl.name as id,bl.name as name,bl.type ,d.jcdwid from bdz_or_line bl LEFT JOIN k_department d on d.id = bl.dept_id where 1=1 ");
		List<Object> params = new ArrayList<>();
		if (StrKit.notBlank(type)) {
			sql.append(" and bl.type  =? ");
			params.add(type);
		}
		// 部门的筛选 当前登录人员的部门基层单位下的变电站或线路
		if (dept.get("jcdwid") != null) {// 如果基层单位为空为乐山市公司人员
			sql.append(" and bl.dept_id = ? ");
			params.add(dept.get("jcdwid"));
		}
		// TODO 获取前30条线路 需采用ajax获取
		sql.append(" order by bl.type,bl.name limit 30");
		renderJson(Db.find(sql.toString(), params.toArray()));
	}

	/**
	 * 三种人资质 筛选控件数据源 type 资质类型
	 */
	public void qualificationUser() {
		renderJson(User.me.findGzfzrByDept(getLoginDept().getLong("id")));
	}

	/**
	 * <pre>
	 * 1、 如果当前登录人员是班组，则上报计划时审核人员选择当前所在基层单位下、调度中心下的人员； 
	 * 2、 当前登录人员是基层单位的调度中心，则审核人员选择本部中运检部审核； 
	 * 3、 如果当前登录人员是本部的运检部，则审核人员选择本部调度中心下的人员；
	 * </pre>
	 */
	public void verifyUser() {

		// 审核类型(人员具有的审核类型字符串)
		String verifyType = getPara("type", "audit_plan_month");

		// 获取当前登录用户部门
		Department dept = getLoginUser().getDepartment();

		Objects.requireNonNull(dept, "当前登录用户部门不能为空");

		List<User> users = UserService.service.getVerifyUser(dept, verifyType);
		renderJson(users);

		// // 基层单位ID
		// Long jcdwId = dept.getJCDWID() == null ? dept.getLong("id") : dept.getJCDWID();
		//
		// // 部门名称
		// String dname = dept.get("dname");
		// // 部门类型
		// String type = dept.get("type");
		//
		// String lastThreedName = dname.substring(dname.length() - 3, dname.length());
		// String lastFourName = dname.substring(dname.length() - 4, dname.length());
		// // 1.当前登录人员是本部的运检部
		// if ("运检部".equals(lastThreedName) && "1".equals(type)) {
		// renderJson(User.me.find("select id,uname as name from k_user where enabled = 0 and dept_id in " + "(select id from k_department where enabled = 0 and type = 1 and dname like '%调度中心')"));
		// }
		// // 2.当前登录用户是基层单位的调度中心
		// else if ("2".equals(type) && "调度中心".equals(lastFourName)) {
		// renderJson(User.me.find("select id,uname as name from k_user where enabled = 0 and dept_id in " + "(select id from k_department where enabled = 0 and type = 1 and dname like '%运检部')"));
		//
		// } else {
		// renderJson(User.me.find("select id,uname as name from k_user where enabled = 0 and dept_id in " + "(select id from k_department where enabled = 0 and type = 2 and dname like '%调度中心' and jcdwid =?)", jcdwId));
		// }

	}

	/**
	 * <pre>
	 * 1、 如果当前登录人员是班组，则上报计划时审核人员选择当前所在基层单位下、调度中心下的人员； 
	 * 2、 当前登录人员是基层单位的调度中心，则审核人员选择本部中运检部审核； 
	 * 3、 如果当前登录人员是本部的运检部，则审核人员选择本部调度中心下的人员；
	 * </pre>
	 */
	public void verifyWeekUser() {

		// 审核类型(人员具有的审核类型字符串)
		String verifyType = getPara("type", "audit_plan_week");

		// 获取当前登录用户部门
		Department dept = getLoginUser().getDepartment();

		Objects.requireNonNull(dept, "当前登录用户部门不能为空");

		List<User> users = UserService.service.getVerifyWeekUser(dept, verifyType);
		renderJson(users);

	}

	/**
	 * todo 到岗到位人员
	 */
	public void dgdwUser() {

	}

	/**
	 * todo 到岗到位监督人员/巡查人员
	 */
	public void dgdwJdUser() {

	}

	private User getLoginUser() {
		return getSessionAttr(KConfig.SESSION_USER_KEY);
	}

	private Department getLoginDept() {
		return getSessionAttr(KConfig.SESSION_DEPT_KEY);
	}

}
