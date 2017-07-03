package com.tsh.broker.utils;

import org.apache.commons.lang.StringUtils;

/**
 * EmptyStringUtils
 *
 * @author dengjd
 * @date 2016/10/9
 */
public class EmptyStringUtils {

    public static String emptyToNone(String org){
        return StringUtils.isNotBlank(org) ? org  : "none";
    }
}
