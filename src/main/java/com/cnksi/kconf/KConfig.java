package com.cnksi.kconf;

import com.cnksi.interceptor.LoginInterceptor;
import com.cnksi.kconf.controller.*;
import com.cnksi.kconf.model.*;
import com.cnksi.kcore.exception.KExceptionInterceptor;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.sec.PwdCheckController;
import com.cnksi.sec.filter.IExportInterceptor;
import com.cnksi.shiro.KShiroInterceptor;
import com.cnksi.shiro.ShiroPlugin;
import com.cnksi.utils.KCacheKit;
import com.jfinal.config.*;
import com.jfinal.json.IJsonFactory;
import com.jfinal.json.JFinalJson;
import com.jfinal.json.Json;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.ModelRecordElResolver;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

import java.util.Arrays;
import java.util.List;

/**
 * BaseConfig
 * 
 * @author joe
 *
 */
public class KConfig extends JFinalConfig {
	public final static String SESSION_USER_KEY = "_login_user_key";
	public final static String SESSION_DEPT_KEY = "_login_dept_key";
	public final static String SESSION_ROLE_KEY = "_login_role_key";
    public final static String SESSION_SYS_YEAR = "sys_year";
    public final static String SESSION_SYS_MONTH = "sys_month";
    public final static String SESSION_SYS_DAY ="sys_day";
    public final static String SESSION_SYS_YEAR_START = "sys_year_start";
    public final static String SESSION_SYS_YEAR_END = "sys_year_end";
    public final static String SESSION_SYS_MONTH_START = "sys_month_start";
	public final static String SESSION_SYS_MONTH_END = "sys_month_end";
	public final static String SESSION_SYS_WEEK_START = "sys_week_start";
	public final static String SESSION_SYS_WEEK_END = "sys_week_end";
	public final static String SESSION_SYS_TODAY = "sys_today";
	public final static String SESSION_SYS_JCDW_ID = "sys_jcdw_id"; //当前登录人员所在基层单位id
	public final static String SESSION_SYS_DEPT_ID = "sys_dept_id"; //当前登录人员所在部门id

	public final static String appid = "app";

	protected ActiveRecordPlugin arp = null;
	protected DruidPlugin druidPlugin = null;

	/**
	 * 供Shiro插件使用。
	 */
	Routes routes;

	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("dbconfig.txt");
		// 因devMode与框架配置有关联，这里直接设置的为jfinal开关为true
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
		me.setBaseViewPath("/WEB-INF/jsp");
		// me.setError500View("/WEB-INF/jsp/error/500.jsp");
		// me.setError401View("/WEB-INF/jsp/error/401.jsp");
		// me.setError404View("/WEB-INF/jsp/error/404.jsp");
		me.setUrlParaSeparator("--");
		// 但前端js处理long型精度不足,所以得将long json化为string处理
		me.setJsonFactory(new IJsonFactory() {
			@Override
			public Json getJson() {
				return new JFinalJson() {
					@Override
					protected String toJson(Object value, int depth) {
						if (value == null) {
							return "null";
						}
						if (value instanceof Long) {
							return "\"" + value + "\"";
						}
						return super.toJson(value, depth);
					}
				};
			}
		});

		// 给token添加TokenCache
		me.setTokenCache(KCacheKit.getTokenCache());
	}

	@Override
	public void configRoute(Routes me) {

		me.add("/", LoginController.class, "/");
		me.add("/index", IndexController.class, "/");
		me.add("/kconf/model", ModelController.class, "/kconf");
		me.add("/kconf/field", ModelFieldController.class, "/kconf");
		me.add("/kconf/iexport", IexportController.class, "/kconf");// 导入导出配置
		me.add("/kconf/iefield",IefieldController.class,"/kconf");
		me.add("/kconf/department", DepartmentController.class, "/kconf");
		me.add("/kconf/lookup", LookupController.class, "/kconf");
		me.add("/kconf/role", RoleController.class, "/kconf");
		me.add("/kconf/user", UserController.class, "/kconf");
		me.add("/kconf/datasource", DatasourceController.class, "/kconf");
		me.add("/kconf/menu", MenuController.class, "/kconf");
		me.add("/kconf/modellink", ModelLinkController.class, "/kconf");
		me.add("/kconf/chart", ChartController.class, "/kconf");
		me.add("/app/user", UserController.class, "/kconf");
		me.add("/app/logb", LogbController.class, "/logb"); // 业务日志

		me.add("/kconf/file", FileManageController.class, "/"); // 文件上传

		me.add("/app/pwdcheck", PwdCheckController.class, "/sec");

		me.add("/kconf/linkauthority",  LinkAuthorityController.class,"/kconf/authority");

		routes = me;
	}

	@Override
	public void configPlugin(Plugins me) {
		//添加缓存
		me.add(new EhCachePlugin());

		druidPlugin = new DruidPlugin(PropKit.get("db.jdbcUrl"), PropKit.get("db.user", "root"),
				PropKit.get("db.password", "root").trim());
		druidPlugin.setInitialSize(PropKit.getInt("db.initialSize", 2));
		druidPlugin.setMaxActive(PropKit.getInt("db.maxActive", 3));
		druidPlugin.setMinIdle(PropKit.getInt("db.minIdle", 1));
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(PropKit.getBoolean("db.showSql", true));

		// 数据模型配置，动态模型
		arp.addMapping("k_model", "mid", KModel.class);
		arp.addMapping("k_model_field", "mfid", KModelField.class);
		arp.addMapping("k_iefield", "iefid", Iefield.class);
		arp.addMapping("k_iexport", "ieid", Iexport.class);

		arp.addMapping("k_authority", "aid", Authority.class);
		arp.addMapping("k_department", "id", Department.class);
		arp.addMapping("k_lookup", "lkid", Lookup.class);
		arp.addMapping("k_lookup_type", "ltid", LookupType.class);
		arp.addMapping("k_menu", "menuid", Menu.class);
		arp.addMapping("k_role", "id", Role.class);
		arp.addMapping("k_role_authority", "raid", RoleAuthority.class);
		arp.addMapping("k_role_menu", "id", RoleMenu.class);
		arp.addMapping("k_user", "id", User.class);
		arp.addMapping("k_user_role", "id", UserRole.class);
		arp.addMapping("k_datasource", "dsid", Datasource.class);

		arp.addMapping("k_model_link", "id", ModelLink.class);
		arp.addMapping("k_model_field_role", "id", ModelFieldRole.class);
		arp.addMapping("k_chart", "chartid", KChart.class);

		arp.addMapping("k_logb", "id", Logb.class);// 业务日志


		//权限控制
		arp.addMapping("k_model_link_authority", "id", LinkAuthority.class);
		me.add(arp);

		// 加载Shiro插件
		ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
		shiroPlugin.setLoginUrl("/");
		shiroPlugin.setSuccessUrl("/index");
		shiroPlugin.setUnauthorizedUrl("/error/401.jsp");
		me.add(shiroPlugin);


	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 是否开启KRequiresPermissions权限过滤
		if (PropKit.getBoolean("shiro.open")) {
			me.add(new KShiroInterceptor());
		}
		//登录拦截器
		me.add(new LoginInterceptor());

		//导入、导出功能需要二次验证密码后才可以提交
		me.add(new IExportInterceptor());

		//异常统一拦截处理,放最后
		me.add(new KExceptionInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// me.add(new ContextPathHandler("BASE_PATH"));
	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		ModelRecordElResolver.setResolveBeanAsModel(true);
	}

	// 向ConfModel中插入数据
	protected void insertToModel(List<TableMeta> build, String[] genTables) {

		for (TableMeta tableMeta : build) {
			if (!StrKit.isBlank(tableMeta.primaryKey)) {

				// 只生成配置的数据库表
				if (!Arrays.asList(genTables).contains(tableMeta.name)) {
					continue;
				}

				KModel model = KModel.me.findByPropertity("mtable", tableMeta.name);
				if (model == null) {
					model = new KModel();
					model.set("mid", -100);
					model.set("mtable", tableMeta.name);
					model.set("mname", tableMeta.remarks);
					model.save();
				}

				for (ColumnMeta colMeta : tableMeta.columnMetas) {
					KModelField field = KModelField.me.findByPropertity(new String[] { "mid", "field_name" },
							new Object[] { model.getLong("mid"), colMeta.name }, Logical.AND);
					if (field == null) {
						field = new KModelField();
						field.set("mid", model.getLong("mid"));
						field.set("field_alias", colMeta.remarks);
						field.set("field_name", colMeta.name);
						field.set("remark", colMeta.remarks);
						field.set("type", colMeta.type);

						field.set("is_system", "true");
						field.set("form_required", colMeta.isNullable == "NO" ? "true" : "false");

						if ("enabled,last_modify_time,create_time".contains(colMeta.name)) {
							continue;
						} else {
							field.set("list_view", "false");
							field.set("list_sort", 300);
							field.set("form_view", "false");
							field.set("form_sort", 300);
						}

						field.save();
					}
				}
			}
		}
	}

	protected void insertToIExport(List<TableMeta> build, String[] genTables) {
		for (TableMeta tableMeta : build) {

			if (!Arrays.asList(genTables).contains(tableMeta.name)) {
				continue;
			}

			Iexport iemport = Iexport.me.findByPropertity(new String[] { "ietable", "ietype" },
					new String[] { tableMeta.name, "导入" }, Logical.AND);
			if (iemport == null) {
				iemport = new Iexport();
				iemport.set(iemport.getPkName(), -100);
				iemport.set("ietable", tableMeta.name);
				iemport.set("ietype", "导入");
				iemport.set("iename", tableMeta.remarks);
				iemport.save();
			}

			Iexport exmport = Iexport.me.findByPropertity(new String[] { "ietable", "ietype" },
					new String[] { tableMeta.name, "导出" }, Logical.AND);
			if (exmport == null) {
				exmport = new Iexport();
				exmport.set(iemport.getPkName(), -100);
				exmport.set("ietable", tableMeta.name);
				exmport.set("iename", tableMeta.remarks);
				exmport.set("ietype", "导出");
				exmport.save();
			}

			for (ColumnMeta colMeta : tableMeta.columnMetas) {
				if ("enabled,last_modify_time,create_time".contains(colMeta.name)) {
					continue;
				}

				Iefield importField = Iefield.me.findByPropertity(new String[] { "ieid", "field_name" },
						new Object[] { iemport.get(iemport.getPkName()), colMeta.name }, Logical.AND);
				if (importField == null) {
					importField = new Iefield();
					importField.set(importField.getPkName(), -100);
					importField.set("ieid", iemport.get(iemport.getPkName()));
					importField.set("field_alias", colMeta.remarks);
					importField.set("field_name", colMeta.name);
					importField.set("width", "20");
					importField.set("sort", 300);
					importField.set("type", 1);
					importField.set("allow_blank", 0);
					importField.save();
				}

				Iefield exportField = Iefield.me.findByPropertity(new String[] { "ieid", "field_name" },
						new Object[] { exmport.get(exmport.getPkName()), colMeta.name }, Logical.AND);
				if (exportField == null) {
					exportField = new Iefield();
					exportField.set(exportField.getPkName(), -100);
					exportField.set("ieid", exmport.get(exmport.getPkName()));
					exportField.set("field_alias", colMeta.remarks);
					exportField.set("field_name", colMeta.name);
					exportField.set("width", "20");
					exportField.set("sort", 300);
					exportField.set("type", 1);
					exportField.set("allow_blank", 0);
					exportField.save();
				}
			}
		}
	}
}
