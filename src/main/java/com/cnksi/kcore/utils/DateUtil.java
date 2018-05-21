package com.cnksi.kcore.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016/12/10.
 */
public class DateUtil extends DateUtils {

	public static final String YMD = "yyyy-MM-dd";
	public static final String YMDHMS = "yyyy-MM-dd HH:mm";

	/**
	 * 获取月份开始及结束的日期 为空则返回当月或当年
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Map<String, String> getMonthDate(Integer year, Integer month) {
		Map<String, String> date = new HashMap<>();
		Calendar cal = getCalInstance();
		if (year != null) {
			cal.set(Calendar.YEAR, year);
		}
		if (month != null) {
			cal.set(Calendar.MONTH, month - 1);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String start = date2String(cal.getTime(), "yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String end = date2String(cal.getTime(), "yyyy-MM-dd");
		date.put("start", start);
		date.put("end", end);
		return date;
	}

	public static Date getMonthStartDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Date getMonthEndDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	public static Date getYearStartDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	public static Date getYearEndDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		return cal.getTime();
	}
    /**
     *
     * @param diff WEEK_OF_YEAR
     * @return
     */
	public static Map<String, String> getWeekDate(Integer diff) {
		Map<String, String> date = new HashMap<>();
		Calendar cal = getCalInstance();
		if (diff != null && diff != 0) {
			int seq = cal.get(Calendar.WEEK_OF_YEAR);
			cal.set(Calendar.WEEK_OF_YEAR, seq + diff);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String start = date2String(cal.getTime(), "yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String end = date2String(cal.getTime(), "yyyy-MM-dd");
		date.put("start", start);
		date.put("end", end);
		return date;
	}

	/**
	 * 获取当天时间 yyyy-MM-dd 00:00:00 到 yyyy-MM-dd 23:59:59
	 * 
	 * @return
	 */
	public static Map<String, String> getCurrentDate() {
		Map<String, String> date = new HashMap<>();
		Calendar cal = getCalInstance();
		String start = date2String(cal.getTime(), "yyyy-MM-dd");
		String end = date2String(cal.getTime(), "yyyy-MM-dd");
		date.put("start", start);
		date.put("end", end);
		return date;
	}

	/**
	 * 获取今天0时的时间戳
	 * @return
	 */
	public static Date getDayStartDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某日0时的时间戳
	 * @param date
	 * @return
	 */
	public static Date getDayStartDate(Date date) {
		Calendar cal = getCalInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取今天23时59分59秒的时间戳
	 * @return
	 */
	public static Date getDayEndDate() {
		Calendar cal = getCalInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取某日23时59分59秒的时间戳
	 * @param date
	 * @return
	 */
	public static  Date getDayEndDate(Date date) {
		Calendar cal = getCalInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 格式化日期格式：
	 *
	 * @param date
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			str = sdf.format(date);
		}
		return str;
	}

	/**
	 * 根据给定的格式，格式化字符串
	 *
	 * @param stringDate
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String stringDate, String pattern) {
		SimpleDateFormat dd = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = dd.parse(stringDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	/**
	 * 线程安全的Calender实例获取
	 *
	 * @return
	 */
	public static Calendar getCalInstance() {
		ThreadLocal<Calendar> cal = new ThreadLocal<Calendar>();
		Calendar tmp = Calendar.getInstance();
		tmp.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(tmp);
		return cal.get();
	}

	public static Integer getCurrentYear() {
		return getCalInstance().get(Calendar.YEAR);
	}

	public static Integer getCurrentMonth() {
		return getCalInstance().get(Calendar.MONTH) + 1;
	}

	public static Integer getCurrentWeek(){
		return  getCalInstance().get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 获取年日期起始
	 * 
	 * @param year
	 * @return
	 */
	public static Map<String, String> getYearDate(Integer year) {
		Calendar cal = getCalInstance();
		if (year != null) {
			cal.set(Calendar.YEAR, year);
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);
		String start = date2String(cal.getTime(), YMD);
		int i = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, i);
		String end = date2String(cal.getTime(), YMD);
		Map<String, String> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		return map;
	}

	/**
	 * 获取某年的第一天
	 * @param year
	 * @return
	 */
	public static Date getYearStart(int year) {
		Calendar cal = getCalInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 获取某年的最后一天
	 * @param year
	 * @return
	 */
	public static Date getYearEnd(int year) {
		Calendar cal = getCalInstance();
		cal.set(Calendar.YEAR, year);
		int i = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, i);
		return cal.getTime();
	}

    /**
     * 本周一 , yyyy-MM-dd
     * @return
     */
    public static String getWeekStart(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(getWeekStartDate());
    }

	/**
	 * 本周一, yyyy-MM-dd
	 * @return Date
	 */
	public static Date getWeekStartDate() {
		Calendar cal = Calendar.getInstance();
		//设置每周第一天为星期一 (默认是星期日)
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

    /**
     * 本周日
     * @return
     */
    public static String getWeekEnd(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(getWeekEndDate());
    }
	/**
	 * 上周一
	 * @return
	 */

    public  static String  getPreWeekStart(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		String lastBeginDate = sdf.format(calendar1.getTime());
		return lastBeginDate ;
	}
	/**
	 * 上周日
	 * @return
	 */
	public  static String  getPreWeekEnd(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset2 = 7 - dayOfWeek;
		calendar2.add(Calendar.DATE, offset2 - 7);
		String lastEndDate = sdf.format(calendar2.getTime());
		return lastEndDate;
	}

	/**
	 * 本周日
	 * @return Date
	 */
	public static Date getWeekEndDate() {
		Calendar cal = Calendar.getInstance();
		//设置每周第一天为星期一 (默认是星期日)
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
    }
	/**
	 * 本月第一天
	 * @return Date
	 */
	public static String getMonthStart(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getMonthStartDate());
	}
	/**
	 * 本月最后一天
	 * @return Date
	 */
	public static String getMonthEnd(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getMonthEndDate());
	}
	/**
	 * 本年第一天
	 * @return Date
	 */
	public static String getYearStart(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getYearStartDate());
	}
	/**
	 * 本月最后一天
	 * @return Date
	 */
	public static String getYearEnd(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getYearEndDate());
	}


    public static String getWeekDay(int weekDay){
    	weekDay++;
    	if(weekDay==8){
            weekDay = 1;
		}

		Calendar cal = Calendar.getInstance();
		//设置每周第一天为星期一 (默认是星期日)
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, weekDay);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(cal.getTime());
	}


}
