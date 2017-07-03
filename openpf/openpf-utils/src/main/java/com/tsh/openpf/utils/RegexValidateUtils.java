package com.tsh.openpf.utils;

import org.apache.commons.lang.StringUtils;


/**
 * RegexUtils
 *
 * @author dengjd
 * @date 2016/10/11
 */
public class RegexValidateUtils {
	/**
	 * 验证url
	 * @author Administrator <br>
	 * @Date 2016年11月9日<br>
	 * @param url
	 * @return
	 */
    public static boolean isValidUrl(String url){
        if(StringUtils.isBlank(url))
            return false;
        String regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
       return  url.matches(regex);
    }

    /**
     * 验证时间戳
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param timestamp
     * @return
     */
    public  static boolean  isValidTimestampFormat(String timestamp){
        if(StringUtils.isBlank(timestamp))
            return false;
        String pattern = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}$";
        return  timestamp.matches(pattern);
    }
}
