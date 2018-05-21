
package com.cnksi.kcore.web.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询参数配置
 */
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KQueryParam {
	/**
	 * 当前查询参数对应的字段名，如果在QueryInfo中配置有表别名应加上别名前缀 如果未设置本参数，则字段名与当前属性名相同
	 */
	public String colName() default "";

	/**
	 * 配置字段对应的操作符，默认为相等操作
	 */
	public String op() default "=";

}
