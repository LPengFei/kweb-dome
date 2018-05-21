package com.cnksi.kconf.controller;

import com.cnksi.app.EMVerifyStatus;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Logb;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.User;
import com.cnksi.kconf.service.MenuService;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.utils.DateUtil;
import com.cnksi.utils.IDataSource;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统主页
 * 
 * @author joe
 *
 */

public class IndexController extends Controller {

	public void index() throws UnsupportedEncodingException {
		User user = getSessionAttr(KConfig.SESSION_USER_KEY);
		if (user == null) {
			this.redirect("/?msg=" + URLEncoder.encode("用户未登陆或页面超时", "UTF-8"));
		} else {
			//menu菜单
			setAttr("menus", MenuService.service.findMenuAndChidrenByRole(user.get("role_id")));
			setAttr("tab_id",getPara("tab_id"));
            Lookup lookup = Lookup.me.findByPropertity(new String[] {"enabled","ltid","lkey"}, new Object[] {0,"dk_to_ess","lscc_server_login"}, BaseModel.Logical.AND);
            if(lookup != null){
                setAttr("lscc_server_login", lookup.getValue());  // 地县一体化平台登录地址
            }

			Lookup renameLookup = Lookup.me.findByPropertity(new String[] {"enabled","ltid","lkey"}, new Object[] {0,"dk_to_ess","rename"}, BaseModel.Logical.AND);
			if(renameLookup != null){
				setAttr("dk_rename", renameLookup.getValue());  // 地县一体化平台登录地址
			}
			Department dept = getSessionAttr(KConfig.SESSION_DEPT_KEY);
            setAttr("jcdwid", dept.getJCDWID() == null ? dept.get("id") : dept.getJCDWID());
			render("index.jsp");
		}
	}

	/**
	 * 主页中间部分页面
	 */
	public void main() {
		User user = getSessionAttr(KConfig.SESSION_USER_KEY);

		Logb lastLoginInfo = Logb.me.findUserLastLoginInfo(user.getStr("uaccount"));
		setAttr("lastLoginInfo", lastLoginInfo);
		setAttr("role",user.getRole());


		//动态配置消息中心
			List<Record> messageCenters = Db.find("select * from k_lookup where ltid  = 'message_center' and enabled =0 order by sort asc");
			Lookup lookup = Lookup.me.findByTypeidAndKey("plan_week_custom","sui_ning");
			/*for (Record record:messageCenters) {
				if ("planmonthverfy-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(月)");
					//周计划审核
				} else if ("weekplan-list".equals(record.getStr("lkey"))) {
					if(null!=lookup && "1".equals(lookup.getValue())){
						record.set("meg_remark","待审(日)");
					}else{

						record.set("meg_remark","待审(周)");
					}
					//临时计划审核
				} else if ("temp_verfying_plan-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(临时)");
					//抢修计划审核
				} else if ("qiangxiu_verfying_plan-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(抢修)");
					//日计划审核
				} else if ("verify_day_plan-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(日)");
				}else if ("planweek_change_verifying-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(周变更)");
				}else if ("linshi_change_verifying-list".equals(record.getStr("lkey"))) {
					record.set("meg_remark","待审(临时变更)");
				}
				//判断当前用户是否有审核资格
				Long count = 0L;
				if(user.getStr("audit_type")!=null) {
					//月计划审核
					if ("planmonthverfy-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_MONTH)) {

						count = PlanMonthService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString());
						//周计划审核
					} else if ("weekplan-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_WEEK)) {

						count = PlanWeekService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString(), IDataSource.ZHOU_PLAN_TYPE);
						//临时计划审核
					} else if ("temp_verfying_plan-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_NORMAL)) {

						count = PlanWeekService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString(), IDataSource.LINSHI_PLAN_TYPE);
						//抢修计划审核
					} else if ("qiangxiu_verfying_plan-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_REPAIR)) {
						count = PlanWeekService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString(), IDataSource.QIANGXIU_PLAN_TYPE);
						//日计划审核
					} else if ("verify_day_plan-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains("audit_plan_day")) {

						count = PlanDayService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString());
					}else if ("planweek_change_verifying-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_WEEK_CHANGE)) {
						count = PlanWeekChangeService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString(), IDataSource.ZHOU_CHANGE_PLAN_TYPE);
					}
					else if ("linshi_change_verifying-list".equals(record.getStr("lkey"))
							&& (user.getStr("audit_type")).contains(IDataSource.AUDIT_PLAN_NORMAL_CHANGE)) {
						count = PlanWeekChangeService.service.count(user.getDepartment().getPkVal(), EMVerifyStatus.verifying.toString(), IDataSource.LINSHI_CHANGE_PLAN_TYPE);
					}
				}
				record.set("count",count);
	}*/



		/*//添加计划上报时间
		String report_start_time = PlanWeekService.service.setReportTime("report_start_time");
		String report_end_time =PlanWeekService.service.setReportTime("report_end_time");

		if(report_start_time!=null&&report_end_time!=null){
			setAttr("report_start_time",report_start_time);
			setAttr("report_end_time",report_end_time);
		}

		setAttr("messageCenters",messageCenters);

		Map<String,String> date = DateUtil.getWeekDate(0);
		Map<String,String> date2 = DateUtil.getMonthDate(null, null);
		setAttr("weekStart",date.get("start"));
		setAttr("weekEnd",date.get("end"));
		setAttr("monthStart",date2.get("start"));
		setAttr("monthEnd",date2.get("end"));*/



		//查询首页中间链接
		List<Lookup> sys_home = Lookup.me.find("select * from k_lookup where ltid  = 'sys_center_home' and enabled = 0 order by sort");
		setAttr("sys_home",sys_home);

		//统计默认开始时间和结束时间
		Date start_time = DateUtil.getWeekStartDate();
		Date end_time = DateUtil.getWeekEndDate();

		setAttr("start_time",start_time);
		setAttr("end_time",end_time);
		render("index_main.jsp");
	}
	/**
	 * 详情界面
	 */
	public void detail() {
		render("detail.jsp");
	}


	/**
	 * 工作人员视图
	 *
	 */
	public void employeeView() {
		String rname = getPara("rname");
		setAttr("rname",rname);
		render("index_main_new.jsp");
	}



	
	
}
