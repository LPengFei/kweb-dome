package com.cnksi.utils;

import com.cnksi.kconf.model.Lookup;
import com.jfinal.kit.StrKit;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 时间处理类
 * 
 * @author joe
 *
 */
public class KDateUtils {
	private static final Logger logger = Logger.getLogger(KDateUtils.class);
	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

	public static String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT));
	}

	public static String getCurrentDay() {
		return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
	}

	/**
	 * 获取到期时间按每月30天计算
	 * 
	 * @param expirationTime
	 * @return
	 */
	public static String getExpirationTime(Date expirationTime, List<Lookup> status) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		long day;
		String endStauts = "";
		try {
			Date date = sf.parse(sf.format(new Date()));
			Date toTime = sf.parse(sf.format(expirationTime));
			day = (toTime.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
			for (Lookup lookup : status) {
				endStauts = findStatus(day, lookup);
				if (StrKit.notBlank(endStauts))
					break;
				else
					continue;
			}

		} catch (ParseException e) {
			logger.error("到期时间格式不正确或者常量备注类型不正确!", e);
		}

		return endStauts;
	}

	private static String findStatus(Long day, Lookup lookup) {
		Long beginDay;
		Long endDay;
		try {
			beginDay = Long.parseLong(lookup.getStr(IConstans.REMARK));

		} catch (Exception e) {
			beginDay = -9223372036854775808L;
		}

		try {
			endDay = Long.parseLong(lookup.getStr(IConstans.LKEY));
		} catch (Exception e2) {
			endDay = 9223372036854775807L;
		}

		if (day >= beginDay && day <= endDay)
			return lookup.getStr(IConstans.LVALUE);
		else
			return "";
	}
}
