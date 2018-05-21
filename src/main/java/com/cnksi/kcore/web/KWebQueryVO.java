package com.cnksi.kcore.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 网页查询VO
 * 
 * @author joe
 *
 */
public class KWebQueryVO {

	Map<String, Object> filterMap = new HashMap<String, Object>();

	private int pageNumber = 1;

	private int pageSize = 60;

	// 排序字段
	private String orderField = "";

	// 升序or降序
	private String orderDirection = " DESC ";

	// 是否是导出(true,则设定每页大小为10000000)
	private Boolean export = false;

	/**
	 * 数据库表名称
	 */
	private String tableName;

	/**
	 * 添加自定义查询条件
	 * 
	 * @return
	 * @throws Exception
	 */

	public void addFilter(String colName, String op, Object value) {
		if (value != null) {
			filterMap.put(colName + op, value);
		} else {
			System.err.println(" value 为null,请使用 addFilter(colName is null) ");
		}
	}

	/**
	 * 添加单查询条件{xx is null , xx is not null }
	 * 
	 * @param colName
	 * @param op
	 */
	public void addFilter(String condition) {
		filterMap.put(condition, null);
	}

	/**
	 * 获取自定义查询条件
	 * 
	 * @return
	 */
	public Map<String, Object> getFilter() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	/**
	 * 获取自定义排序条件
	 * 
	 * @return
	 */
	public String getOrderBy() {
		if (orderField != null && orderDirection != null && orderField.trim().length() > 0 && orderDirection.trim().length() > 0) {
			return orderField + " " + orderDirection;
		} else {
			return null;
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

    /**
     * 如果是导出设置为第一页
     * @return
     */
    public int getPageNumber() {
        return export ? 1 : pageNumber;
    }

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	//如果导出，则根据条件全部导出
	public int getPageSize() {
		return export ? Integer.MAX_VALUE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean export) {
		this.export = export;
	}

	public Map<String, Object> getMap() {
		 
		return null;
	}
}
