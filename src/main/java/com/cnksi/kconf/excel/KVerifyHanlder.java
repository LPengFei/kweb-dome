package com.cnksi.kconf.excel;

import com.cnksi.kconf.model.Iefield;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;
import org.jeecgframework.poi.exception.excel.enums.ExcelImportEnum;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KVerifyHanlder implements IExcelVerifyHandler<Object> {

	// String[] needHandlerFields = new String[] {};

	Map<Object, Iefield> confMap = null;

	// 数据缓存
	Map<String, Object> cacheMap = new HashMap<>();

	public void setFields(List<Iefield> fields) {
		//
		this.confMap = Iefield.me.toMap(fields, "field_name");
		//
		// // 设定需要做数据处理的字段
		// List<String> needHandlerFieldsList = new ArrayList<String>();
		// fields.forEach(record -> {
		// if (StrKit.notBlank(record.getStr("list_sql"))) {
		// needHandlerFieldsList.add(record.getStr("field_name"));
		// }
		// });
		// needHandlerFields = needHandlerFieldsList.toArray(needHandlerFields);
		//
		// // System.out.println(needHandlerFields.length);
	}

	// 动态参数
	public Map<String, Object> dynicParams = new HashMap<>();

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

	@Override
	public ExcelVerifyHanlderResult verifyHandler(Object obj) {

		ExcelVerifyHanlderResult result = new ExcelVerifyHanlderResult(true);
		boolean success = true;
		StringBuilder sb = new StringBuilder(256);
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> _objMap = (Map<String, Object>) obj;
			for (Entry<String, Object> entry : _objMap.entrySet()) {
				Iefield field = confMap.get(entry.getKey());
				if (field == null)
					continue;
				
				System.err.println(entry);
				if (entry.getKey().equals("kcode")) {
				}

				// 非空验证
				if (1 == field.getInt("allow_blank")) {
					if (entry.getValue() == null || StrKit.isBlank(String.valueOf(entry.getValue()))) {
						success = false;
						sb.append(field.getStr("field_alias") + " 列不能为空，请填写").append("    ");
					}
				}

				// 日期格式验证
				String format = field.getStr("format");
				if (StrKit.notBlank(format)) {
                    try {
                    	if(entry.getValue()!=null && StrKit.notBlank(String.valueOf(entry.getValue()))){
							Date date = toDateCellValue(entry.getValue() + "", format);
							entry.setValue(date);
						}
                    } catch (Exception e) {
                        success = false;
                        sb.append(field.getStr("field_alias") + " 格式不正确，请按照" + DateFormatUtils.format(new Date(), format) + " 格式填写").append("    ");
                        e.printStackTrace();
                    }
				}


				CacheKit.remove("record_list","importxlsed");
				CacheKit.put("record_list", "importxlsed", _objMap);
				// 其他第三方业务验证
				boolean bisuccess = businessVerifyHandler(entry.getKey(), entry.getValue(), sb);
				//默认验证或业务验证有一个不通过，设置结果为false
				if (!success || !bisuccess) result.setSuccess(false);
				/*if (success && bisuccess) {
					success = true;
				} else {
					success = false;
				}*/

                /*if (success == false){
                    System.out.println("-------------------"+success+"--------------------------");
                    System.out.println(entry);
                    System.out.println(sb.toString());
                }*/
            }
		}
		result.setMsg(sb.toString());
		return result;
	}

    /**
     * 转换日期格式数据
     * @param value
     * @param formatStr
     */
    public Date toDateCellValue(String value, String formatStr){
        Date date = null;
        try {
            if (StringUtils.isNotBlank(formatStr)) {
                date = DateUtils.parseDate(value, formatStr);
            }
        } catch (ParseException e) {
            throw new ExcelImportException("日期格式转换失败", ExcelImportEnum.GET_VALUE_ERROR);
        }

        return date;

    }

	/**
	 * 程序扩展其他验证
	 * 
	 * @param sb
	 * @return
	 */
	protected boolean businessVerifyHandler(String colName, Object value, StringBuilder sb) {
		return true;
	}

}
