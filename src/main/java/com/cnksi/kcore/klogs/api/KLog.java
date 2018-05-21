package com.cnksi.kcore.klogs.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志
 * 
 * @author joe
 *
 */
@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KLog {

	/**
	 * 业务名称
	 * 
	 * @return
	 */
	public String businessName() default "";

	/**
	 * 操作类型{登录、注销、新增用户}等自定义类型
	 * 
	 * @return
	 */
	public String operaName() default "";
}
