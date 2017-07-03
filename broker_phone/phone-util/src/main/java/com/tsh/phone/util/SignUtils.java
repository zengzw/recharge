package com.tsh.phone.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 签名工具类
 * 
 *
 * @author zengzw
 * @date 2017年2月16日
 */
public class SignUtils {

    private static String build(Map<String,Object> params) {
        if(params == null)
            throw  new  IllegalArgumentException("params argument is null");
        
        StringBuffer  buffer = new StringBuffer();
        for(Object value :  params.values()){
            buffer.append(value);
        }
        return buffer.toString();
    }
    
    
    
    public  static String sign(LinkedHashMap<String, Object> params, String signKey){
        if(params == null)
            throw new IllegalArgumentException("params argument is null");
        
        if(StringUtils.isEmpty(signKey))
            throw new IllegalArgumentException("signKey argument is blank");

        return MD5Util.encode(build(params) + signKey);
    }

}
