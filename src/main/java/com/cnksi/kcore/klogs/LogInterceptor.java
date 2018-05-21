package com.cnksi.kcore.klogs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnksi.kcore.jfinal.IResource;
import com.cnksi.kcore.klogs.api.KLog;
import com.cnksi.kcore.klogs.model.KLogInfo;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 系统日志过滤
 * 
 * <pre>
 * 	日志记录
 * 		1、方法上有@KLog注解的，优先记录
 * 		2、无@KLog注解，则根据actionKey从Resource权限中获取对应的业务名称
 * </pre>
 * 
 * @author joe
 *
 */
public class LogInterceptor implements Interceptor {

	Map<String, IResource> resMap = null;
	Map<String, String> operatetypeMap = null;
	{
		operatetypeMap = new HashMap<>();
		operatetypeMap.put("index", "查询");
		operatetypeMap.put("save", "保存");
		operatetypeMap.put("edit", "修改");
		operatetypeMap.put("delete", "删除");
	}

	public LogInterceptor() {
		resMap = new HashMap<>();
	}

	/**
	 * 初始化时，加载所有的业务资源信息，减少数据库查询
	 * 
	 * @param resList
	 */
	public LogInterceptor(List<?> resList) {
		resMap = new HashMap<>();
		for (Object obj : resList) {
			if (obj instanceof IResource) {
				IResource res = (IResource) obj;
				resMap.put(res.getUrl(), res);
			}
		}
	}

	@Override
	public void intercept(Invocation inv) {
		// 只过滤save和delete方法
		if (inv.getMethodName().contains("save") || inv.getMethodName().contains("delete")) {
			// 方法上有KLog这个注解时，优先处理KLog注解
			KLog logAnn = inv.getMethod().getAnnotation(KLog.class);
			KLogInfo info = new KLogInfo();
			IResource res = resMap.get(inv.getActionKey());

			if (logAnn == null && res == null) {
				inv.invoke();
				return;
			}

			if (logAnn != null) {
				info.set("operatetype", logAnn.operaName()); // 操作类型
				info.set("businessName", logAnn.businessName()); // 业务名称
			} else {
				info.set("operatetype", operatetypeMap.get(inv.getMethodName()) != null ? operatetypeMap.get(inv.getMethodName()) : inv.getMethodName());
				info.set("businessName", res.getName());
			}
			info.set("visitURL", inv.getActionKey());
			info.set("pkValue", inv.getController().getPara());
			info.set("operator", "");
			info.set("operatetime", new Date());
			info.save();
		}
		inv.invoke();
	}
}
