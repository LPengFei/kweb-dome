package com.cnksi.kcore.web.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KQuery {

	/**
	 * 要查询的sql语句
	 * 
	 * @return
	 */
	public String select();

	public String from();

	public String join() default "";

	/**
	 * 分组 子句
	 * 
	 * @return
	 */
	public String groupBy() default "";

	/**
	 * 排序字句
	 * 
	 * @return
	 */
	public String orderBy() default "";

	/**
	 * 从指定的方法获取orderBy字符串
	 * 
	 * @return
	 */
	//public String orderByMethod() default "";

}