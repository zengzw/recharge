package com.tsh.broker.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:13:19
 */
public class DateUtil {
	private static String GLOBAL_DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
	/**
	 * 字符串转日期
	 * @param str 被格式化字符串
	 * @param pattern 格式化字符,不传入pattern时,本方法采用GLOBAL_DATE_PATTERN格式化
	 * @return Date
	 */
	public static Date formatStringToDate(String str,String...pattern) {
		Date date = null;
		if(str==null||str.trim().equals("")){
			return date;
		}
		if(pattern.length==0){			
			pattern=new String[1];
			pattern[0]=GLOBAL_DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern[0]);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 日期转字符串
	 * @param date 被格式化日期
	 * @param pattern 格式化字符,不传入pattern时,本方法采用GLOBAL_DATE_PATTERN格式化
	 * @return String
	 */
	public static String formatDateToString(Date date, String... pattern) {
		try {
			if(date==null){
				return "";
			}
			if(pattern.length==0){			
				pattern=new String[1];
				pattern[0]=GLOBAL_DATE_PATTERN;
			}			
			SimpleDateFormat sdf = new SimpleDateFormat(pattern[0]);
			return sdf.format(date).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 取得现在的日期及时间
	 * @param formatLayout 格式化 如：yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static  String getCurrentDate(String formatLayout) {
		SimpleDateFormat sdf;
		if (formatLayout == null || formatLayout.length() <= 0)
			sdf = new SimpleDateFormat(GLOBAL_DATE_PATTERN);
		else
			sdf = new SimpleDateFormat(formatLayout);

		return (sdf.format(new Date()));
	}
	/**
	 * 根据传过来的Str和格式,格式化str到Date
	 * @param str 日期
	 * @param formatLayout 转换格式 如：yyyy-MM-dd HH:mm:ss
	 * @return Date
	 * @throws java.text.ParseException
	 */
	public static Date strToDate(String str,String formatLayout){
		if (str == null || str.trim().length() < 1)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(formatLayout);
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	/**
	 * 根据转过来的date和时间格式，把date转化为指点格式的字符串
	 * @param dateTime
	 * @return string
	 */
	public static String datetoStr(Date date,String dateStr){
		if(date==null){
			return  null;
		}else{
			return new SimpleDateFormat(dateStr).format(date);
		}
	}
	 /**
	 * 将dateFormat格式的time转成toDateFormat格式的时间，返回
	 * @author luther.zhang
	 * @param time           时间
	 * @param dateFormat     time 格式 yyyy-MM-dd
	 * @param toDateFormat   期望转成的格式 yyyyMMdd
	 * @return
	 */
	public static String formatStrDate(String time,String dateFormat,String toDateFormat){
		if(null!=time&&!"".equals(time.trim())){
			time = time.trim();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				Date date = sdf.parse(time);
				sdf = new SimpleDateFormat(toDateFormat);
				time = sdf.format(date);
			} catch (Exception e) {
				if(time.length()>10){
					time=time.substring(0,10);
				}
			}
		}else{
			time = "";
		}
		return time;
			
	}


    public static long getCurrentDayTime() throws ParseException {
        String todayStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        Date today = org.apache.commons.lang.time.DateUtils.parseDate(todayStr, new String[]{"yyyy-MM-dd"});

        return today.getTime();
    }

}
