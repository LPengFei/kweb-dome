package com.cnksi.kconf.excel;

import com.cnksi.kconf.model.Iefield;
import com.cnksi.kcore.exception.KException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.jeecgframework.poi.handler.inter.IExcelDataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入导出数据处理
 * 
 * @author joe
 *
 */
public class KExcelDataHandler implements IExcelDataHandler<Object> {

	String[] needHandlerFields = new String[] {};

	public Map<Object, Iefield> exportConfMap = null;

	public Map<Object, Iefield> importConfMap = null;

	// 动态参数
	public Map<String, Object> dynicParams = null;

	/**
	 * 添加导入时动态参数;动态参数在导入配置时的SQL中配置
	 * 
	 * @param key
	 * @param value
	 */
	public Map<String, Object> addDynicParam(String key, Object value) {
		dynicParams.put(key, value);
		return dynicParams;
	}

	// 数据缓存
	public Map<String, Object> cacheMap = new HashMap<>();

	public KExcelDataHandler() {
		dynicParams = new HashMap<>();
	}

	public void setFields(List<Iefield> fields) {

		this.exportConfMap = Iefield.me.toMap(fields, "field_name");
		this.importConfMap = Iefield.me.toMap(fields, "field_alias");

		// 设定需要做数据处理的字段
		List<String> needHandlerFieldsList = new ArrayList<String>();
		fields.forEach(record -> {
			if (StrKit.notBlank(record.getStr("list_sql"))) {
				needHandlerFieldsList.add(record.getStr("field_name"));
				needHandlerFieldsList.add(record.getStr("field_alias"));
			}
		});
		needHandlerFields = needHandlerFieldsList.toArray(needHandlerFields);

		System.out.println(needHandlerFields.length);
	}

	@Override
	public Object exportHandler(Object obj, String name, Object value) {
		Object _value = value;

		Iefield fieldConf = exportConfMap.get(name);
		if (fieldConf != null && StrKit.notBlank(fieldConf.getStr("list_sql"))) {
			Object _val = cacheMap.get(name + "-" + value);
			if (StrKit.notNull(_val)) {
				_value = _val;
			} else {
				List<Object> datas = Db.query(fieldConf.getStr("list_sql"), value);
				if (datas != null && !datas.isEmpty()) {
					_value = datas.get(0);
					cacheMap.put(name + "-" + value, _value);
				}
			}
		}
		return _value;
	}

	@Override
	public String[] getNeedHandlerFields() {
		return needHandlerFields;
	}

	@Override
	public Object importHandler(Object obj, String name, Object value) {
		if (value != null && value instanceof String){
			value = StringUtils.trim((String) value);
		}
		Object _value = value;

		// list_sql中有且仅有一个
		List<Object> values = new ArrayList<>();

		Iefield fieldConf = importConfMap.get(name);
		if (fieldConf != null && StrKit.notBlank(fieldConf.getStr("list_sql"))) {
			Object _val = cacheMap.get(name + "-" + value);
			if (StrKit.notNull(_val)) {
				_value = _val;
			} else {
				values.add(_value);
				List<Object> datas = Db.query(dealListSQL(fieldConf.getStr("list_sql"), values), values.toArray());
				if (datas != null && !datas.isEmpty()) {
					_value = datas.get(0);
					cacheMap.put(name + "-" + value, _value);
				} else {
					_value = importNotFondRelateData(name, value, dealListSQL(fieldConf.getStr("list_sql"), values));
				}
			}
		}

		return _value;
	}

	/**
	 * 查询关联数据时，未查询到数据调用此方法，重写此方法可以处理向其他数据库表插入数据并返回真实值；
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Object importNotFondRelateData(String name, Object value, String sql) {
        throw new KException(name + (StrKit.notNull(value) ? "(" + value + ")" : "") + "不存在");
    }

	@Override
	public void setNeedHandlerFields(String[] fields) {
		this.needHandlerFields = fields;
	}

	@Override
	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		// riginKey为excel的表头,通过表头汉字查询出对应的field_name
		Iefield field = importConfMap.get(originKey);
		if (field != null) {
			map.put(field.getStr("field_name"), value);
		} else {
			map.put(originKey, value);
		}
	}

	@Override
	public Hyperlink getHyperlink(CreationHelper creationHelper, Object obj, String name, Object value) {
		return null;
	}

	/**
	 * 处理SQL中： select dname from k_department where dept_id=? and jcdwid=#jcdwid#
	 * 
	 * @param list_sql
	 * @return
	 */
	private String dealListSQL(String list_sql, List<Object> values) {

		List<Object> dynicList = new ArrayList<Object>();
		Pattern p = Pattern.compile("#(.+?)#");
		Matcher m = p.matcher(list_sql);
		while (m.find()) {
			String dynic = m.group(0); // 查找动态参数#userid#
			String key = dynic.replaceAll("#", "");
			if (dynicParams.containsKey(key)) {
				dynicList.add(dynicParams.get(key));
				list_sql = list_sql.replace(dynic, "?");
				values.add(dynicParams.get(key));
			} else {
				System.err.println("SQL: " + list_sql + "\r\n 动态参数：" + key + " 未传值！！");
			}
		}
		return list_sql;

	}
}
