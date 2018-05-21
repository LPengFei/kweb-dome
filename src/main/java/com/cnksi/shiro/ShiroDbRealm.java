package com.cnksi.shiro;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Authority;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Role;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.utils.DateUtil;
import com.cnksi.sec.SecurityProp;
import com.cnksi.utils.IConstans;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

	public static ShiroDbRealm instance;

	@Override
	protected void onInit() {
		if (instance == null) {
			instance = this;
		}
		super.onInit();
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		User user = (User) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (null == user) {
			return info;
		}

		Role role = user.getRole();
		if (null != role) {
			info.addRole(role.getStr("rname"));
			addResourceOfRole(role, info);
		}

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

		User user = User.me.findByUsername(authcToken.getUsername());
		if (null == user) {
			throw new AuthenticationException("用户名或者密码错误");
		}
		if (user.getInt("enabled") == 1) {
			throw new LockedAccountException("该用户已被锁定");
		}

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(true);
		// TODO 设定Session过期时间毫秒,设置为负数则永不过期
		session.setTimeout(1000 * 60 * SecurityProp.sessionTime); // 设定超时时间
		if(StrKit.notBlank(user.getStr("headpic")) && StrKit.notBlank(user.getStr("headpic").split(",")[0])){
			String bgpic = PropKit.get(IConstans.BASEUPLOADPATH).concat(File.separator).concat("hr").concat(File.separator).concat(user.getStr("headpic").split(",")[0]);
			File file = new File(bgpic);
			if(!file.exists()){
				user.remove("headpic");
			}else{
				user.set("headpic",user.getStr("headpic").split(",")[0]);
			}
		}
		session.setAttribute(KConfig.SESSION_USER_KEY, user);

		session.setAttribute(KConfig.SESSION_DEPT_KEY, user.getDepartment());
		//添加系统级别变量到session
		addSystemConstants(session);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.get("upwd"), getName());

		return authenticationInfo;

	}

    /**
     * 添加系统级别变量到session，主要用于url中定义动态变量本周，本月，当前基层单位等
     * @param session
     */
    private void addSystemConstants(Session session) {
        User user = (User) session.getAttribute(KConfig.SESSION_USER_KEY);
        Department department = (Department) session.getAttribute(KConfig.SESSION_DEPT_KEY);

        //登录人员基层单位和部门id
        session.setAttribute(KConfig.SESSION_SYS_DEPT_ID, department.getPkVal());

		// 非本部单位设置基层单位到session
		if(department.isBenbu() || 1==department.getLong("id")) {
			session.setAttribute(KConfig.SESSION_SYS_JCDW_ID, null);
		} else {
			session.setAttribute(KConfig.SESSION_SYS_JCDW_ID, department.getJCDWID());
		}

        //年初、年尾、月初、月尾、周一、周日对应日期
        LocalDate dateNow = LocalDate.now();

        session.setAttribute(KConfig.SESSION_SYS_YEAR, dateNow.getYear()); //本年
        session.setAttribute(KConfig.SESSION_SYS_MONTH, dateNow.getMonthValue()); //本月
		session.setAttribute(KConfig.SESSION_SYS_DAY,dateNow.getDayOfMonth());//本日
        session.setAttribute(KConfig.SESSION_SYS_TODAY, dateNow.toString()); //今日
        session.setAttribute(KConfig.SESSION_SYS_YEAR_START, dateNow.getYear() + "-01-01"); //年初
        session.setAttribute(KConfig.SESSION_SYS_YEAR_END, dateNow.getYear()+"-12-31"); //年末
        session.setAttribute(KConfig.SESSION_SYS_MONTH_START, dateNow.with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ISO_DATE)); //月初
        session.setAttribute(KConfig.SESSION_SYS_MONTH_END, dateNow.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ISO_DATE)); //月末
        session.setAttribute(KConfig.SESSION_SYS_WEEK_START, DateUtil.getWeekStart()); //周一
        session.setAttribute(KConfig.SESSION_SYS_WEEK_END, DateUtil.getWeekEnd()); //周日

    }

    private void addResourceOfRole(Role role, SimpleAuthorizationInfo info) {

		// TODO 权限控制
		List<Authority> resources = Authority.me.findByRoleid(role.get("id"));
		if (resources != null && !resources.isEmpty()) {
			for (Authority resource : resources) {
				// 资源代码就是权限值，类似user：list
				info.addStringPermission(resource.getStr("aurl"));
			}
		}

	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

}
