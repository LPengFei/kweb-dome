package com.cnksi.app;

import com.cnksi.app.controller.*;
import com.cnksi.app.model.*;
import com.cnksi.app.policy.UpFileRenamePolicy;
import com.cnksi.generator.KWebGenerator;
import com.cnksi.job.JobController;
import com.cnksi.kconf.KConfig;
import com.cnksi.yw.controller.DeployController;
import com.cnksi.yw.controller.ProjectController;
import com.cnksi.yw.controller.TrainingController;
import com.cnksi.yw.controller.YlogController;
import com.cnksi.yw.model.Deploy;
import com.cnksi.yw.model.Project;
import com.cnksi.yw.model.Training;
import com.cnksi.yw.model.Ylog;
import com.jfinal.config.Constants;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.upload.OreillyCos;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * API引导式配置
 */
public class AppConfig extends KConfig {

	Logger logger = Logger.getLogger(AppConfig.class);

    /**
     * 保存上传文件的文件夹
     */
	public enum  UploadFolder{
	    upload, pictures, download, video, icon, document
    }

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		super.configConstant(me);
		// 设置上传文件默认路径
		me.setBaseUploadPath(PropKit.use("dbconfig.txt").get("baseUploadPath", "upload"));
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		super.configRoute(me);



		me.add("/app/datasource", DataSourceController.class);
		me.add("/app/attachment", AttachMentController.class, "/attachment");

		//2017/8/14 任务调度
        me.add("/app/job", JobController.class, "/job");

		//2017.08.09 日志
		me.add("/app/log",  LogController.class,"/log");
		me.add("/app/logdetail",  LogDetailController.class,"/logdetail");

		//发布管理
		me.add("/app/deploy",  DeployController.class,"/deploy");
		//项目管理
		me.add("/app/project",  ProjectController.class,"/project");
		//培训管理
		me.add("/app/training",  TrainingController.class,"/training");
		//运维日志
		me.add("/app/ylog",  YlogController.class,"/ylog");


		_RouteKit.mapping(me);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		super.configPlugin(me);

		//2017.08.09
		arp.addMapping("k_log", "id", Log.class);
		arp.addMapping("k_log_detail", "id", LogDetail.class);


		arp.addMapping("y_deploy", "id", Deploy.class);
		arp.addMapping("y_project", "id", Project.class);
		arp.addMapping("y_training", "id", Training.class);
		arp.addMapping("y_ylog", "id", Ylog.class);

		_MappingKit.mapping(arp);

        //添加定时任务插件
//        QuartzPlugin jobPlugin = new QuartzPlugin();
//        me.add(jobPlugin);
//        JobKit.plugin = jobPlugin;

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		super.configInterceptor(me);
	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();

		if (PropKit.getBoolean("devMode")) {
			// 向ConfModel中插入数据
			MetaBuilder metaBuilder = new MetaBuilder(DbKit.getConfig().getDataSource());
			metaBuilder.addincludedTable(KWebGenerator.genTables);
			List<TableMeta> tableMeta = metaBuilder.build(true);
			insertToModel(tableMeta, KWebGenerator.genTables);

			// 插入数据到IExport
			insertToIExport(tableMeta, KWebGenerator.genTables);
		}

		// 设置文件上传重命名策略
		OreillyCos.setFileRenamePolicy(new UpFileRenamePolicy());
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}
}
