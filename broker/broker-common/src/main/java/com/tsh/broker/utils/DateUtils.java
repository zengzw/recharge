package com.tsh.broker.utils;



import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtils
 *
 * @author dengjd
 * @date 2016/6/16
 */
public class DateUtils {
    private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN_TRAIN_START = "yyyy-MM-dd HH:mm";

    public static String  format(Date date,String  pattern){
        if(date == null)
            throw new IllegalArgumentException("date argument is null");
        if(StringUtils.isEmpty(pattern))
            throw new IllegalArgumentException("pattern argument is empty");

        return DateFormatUtils.format(date, pattern);
    }

    public static Date parse(String date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  format(Date date){
        if(date == null)
            throw new IllegalArgumentException("date argument is null");

        return DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
    }


}
