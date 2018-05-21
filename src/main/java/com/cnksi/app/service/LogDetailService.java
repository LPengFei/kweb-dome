package com.cnksi.app.service;


import com.cnksi.app.model.LogDetail;
import com.cnksi.utils.IConstans;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by zf on 2017/8/10.
 */
public class LogDetailService {

    public static LogDetailService service = new LogDetailService();

	/**
	 * 比较两个model哪些字段做了变更，并做记录
	 * @param old_record
	 * @param new_record
	 */
	public void tosaveLogDetail(Record old_record, Record new_record,Long log_id)  {

		Map<String,Object> new_map = new_record.getColumns();
		Map<String,Object> old_map = old_record.getColumns();

		for(Map.Entry<String,Object> entry:old_map.entrySet()){
		    //比较时间
		    if("start_time".equals(entry.getKey()) || "end_time".equals(entry.getKey())){
                Date old_date;
				Date new_date;
                try {
					String n_date = new_map.get(entry.getKey()).toString();
					String o_date = entry.getValue().toString();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					new_date = sf.parse(n_date);
					old_date = sf.parse(o_date);
				}catch (Exception e){
					old_date = (Date) entry.getValue();
					new_date = (Date) new_map.get(entry.getKey());
				}
                if(old_date.getTime()!=new_date.getTime()){
                    saveLogDetail(entry.getKey(),entry.getValue(),new_map.get(entry.getKey()),log_id);
                }
            }
		    else if(entry.getValue()!=null && StrKit.notBlank(entry.getValue().toString()) && !entry.getValue().equals(new_map.get(entry.getKey()))){
				//记录做了变更字段
				saveLogDetail(entry.getKey(),entry.getValue(),new_map.get(entry.getKey()),log_id);
			}else if (entry.getValue()==null && new_map.get(entry.getKey())!=null && StrKit.notBlank(new_map.get(entry.getKey()).toString())){
				saveLogDetail(entry.getKey(),null,new_map.get(entry.getKey()),log_id);
			}
		}
	}

	/**
	 * 保存字段变更记录
	 * @param field_name
	 * @param old_value
	 * @param new_value
	 */
	public void saveLogDetail(String field_name,Object old_value,Object new_value,Long log_id){
		LogDetail logDetail = new LogDetail();
		logDetail.set("log_id",log_id);
		logDetail.set("field_name",field_name);
		logDetail.set("old_value",old_value);
		logDetail.set("new_value",new_value);
		logDetail.save();
	}

}
