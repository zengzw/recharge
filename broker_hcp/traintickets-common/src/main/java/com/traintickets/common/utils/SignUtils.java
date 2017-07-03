package com.traintickets.common.utils;

import java.util.Map;

/**
 * 签名工具类（此类不要轻易修改，VAS会引用）
 * Created by Administrator on 2016/11/28 028.
 */
public class SignUtils {

    public static String doKuyouSign(String value, String signKey){
        try {
            return Md5Digest.encryptMD5(value + signKey).toUpperCase();
        } catch (Exception e) {}
        return null;
    }

    public static String buildParams(Map<String, String> paramsMap){
        StringBuilder sb = new StringBuilder();
        if(null != paramsMap && paramsMap.size() > 0){
            for(String value : paramsMap.values()){
                sb.append(value);
            }
        }
        return sb.toString();
    }
}
