package com.traintickets.common.utils;



import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils
 *
 * @author dengjd
 * @date 2016/6/16
 */
public class DateUtils {
    public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN_1 = "yyyyMMddHHmmss";
    public static String DATE_PATTERN_2 = "HHmm";
    public static String DATE_PATTERN_3 = "yyyy-MM-dd";
    public static String DATE_PATTERN_4 = "yyyy-MM-ddHH:mm";

    public static String  format(Date date,String  pattern){
        if(date == null)
            throw new IllegalArgumentException("date argument is null");
        if(StringUtils.isEmpty(pattern))
            throw new IllegalArgumentException("pattern argument is empty");

        return DateFormatUtils.format(date, pattern);
    }

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_3);
        return sdf.format(new Date());
    }

    public static Date parse(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_4);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {}
        return null;
    }

    public static String  format(Date date){
        if(date == null)
            throw new IllegalArgumentException("date argument is null");

        return DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
    }

    public static Date addHour(Date date, int hour){
        Calendar ca=  Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY,hour);
        return ca.getTime();
    }

    public static void main(String args[]){
        System.out.println(parse("2016-12-1920:09").getTime());
    }
}
