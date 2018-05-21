package com.cnksi.kcore.jfinal;

/**
 * 资源权限接口
 */
public interface IResource {

	/**
	 * 获取权限URL
	 * 
	 * @return
	 */
	public String getUrl();

	/**
	 * 获取对应的业务名称
	 * 
	 * @return
	 */
	public String getName();
}
