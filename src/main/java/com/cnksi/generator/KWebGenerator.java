package com.cnksi.generator;

import com.cnksi.generator.tpl.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * GeneratorDemo
 */
public class KWebGenerator {

	// public static String[] genTables = new String[] { "e_illegal", "e_illegal300", "e_jdk", "e_jdk_content_ques", "e_jdk_item", "e_jdk_item_content", "e_kcode", "e_message", "e_pad_jdk", "e_pad_plan", "e_plan", "e_plan_user", "e_plan_work_img", "e_question", "e_user_qualification""e_illegal", "e_illegal300", "e_jdk", "e_jdk_content_ques", "e_jdk_item", "e_jdk_item_content", "e_kcode", "e_message", "e_pad_jdk", "e_pad_plan", "e_plan", "e_plan_user", "e_plan_work_img", "e_question", "e_user_qualification"};
	public static String[] genTables = new String[] { "y_ylog" , "y_project","y_deploy","y_training"};
	static String[] removedTableNamePrefixes = new String[] { "k_", "e_zx_", "e_", "y_" };
	static String prePackage = "com.cnksi.yw";

	static ActiveRecordPlugin arp = null;

	public static DataSource getDataSource() {
		Prop p = PropKit.use("dbconfig.txt");
		System.err.println(p.get("db.jdbcUrl"));
		DruidPlugin c3p0Plugin = new DruidPlugin(p.get("db.jdbcUrl"), p.get("db.user"), p.get("db.password"));
		c3p0Plugin.start();

		// _MappingKit.mapping(arp);

		return c3p0Plugin.getDataSource();
	}

	public static void main(String[] args) {

		MetaBuilder metaBuilder = new MetaBuilder(getDataSource());
		metaBuilder.addincludedTable(genTables);
		metaBuilder.setRemovedTableNamePrefixes(removedTableNamePrefixes);

		// base model 所使用的包名
		// String baseModelPackageName = prePackage + ".model";
		// String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + baseModelPackageName.replaceAll("[.]", "/") + "/base";// com/cnksi/kweb/model/base";

		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = prePackage + ".model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + modelPackageName.replaceAll("[.]", "/");

		// 创建生成器
		// Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		Generator gernerator = new Generator(getDataSource(), null, new KWebModelGenerator(modelPackageName, modelPackageName, modelOutputDir));

		gernerator.setMetaBuilder(metaBuilder);

		// 设置数据库方言
		gernerator.setDialect(new MysqlDialect());
		// 添加不需要生成的表名
		gernerator.addExcludedTable("adv");
		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(true);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		gernerator.setRemovedTableNamePrefixes(removedTableNamePrefixes);
		// 生成
		gernerator.generate();

		// 生成查询QueryVO
//		 String queryvoPackageName = prePackage + ".controller.vo";
//		 String queryvoOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + queryvoPackageName.replaceAll("[.]", "//");
//		 KWebQueryVOGenerator queryvoGen = new KWebQueryVOGenerator(queryvoPackageName, queryvoOutputDir);
//		 queryvoGen.generate(metaBuilder.build());

		// 生成Controller
		String controllerPackageName = prePackage + ".controller";
		String controllerOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + controllerPackageName.replaceAll("[.]", "/");

		KWebControllerGenerator controllerGen = new KWebControllerGenerator(controllerPackageName, controllerOutputDir);
		controllerGen.generate(metaBuilder.build(true));

		// 生成Route配置文件
		RouteKitGenerator routeKitGenerator = new RouteKitGenerator(controllerPackageName, controllerOutputDir);
		routeKitGenerator.generate(metaBuilder.build());

		// 生成JSP页面
//		 String jspOutputDir = PathKit.getWebRootPath() + "/src/main/webapp/WEB-INF/jsp";
//		 KWebListJspGenerator listGenerator = new KWebListJspGenerator(jspOutputDir);
//		 listGenerator.generate(metaBuilder.build());
//
//		 KWebFormJspGenerator formGenerator = new KWebFormJspGenerator(jspOutputDir);
//		 formGenerator.generate(metaBuilder.build());
	}

}
