/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.properties;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.tsh.phone.util.StringUtils;

/**
 * 瑞通 配置
 *
 * @author zengzw
 * @date 2017年2月20日
 */
public class RTRechargePropertries extends BasicProperties{

    private static Properties properties = null;



    public  String getValue(String key){
        String result = load().getProperty(key);
        if(StringUtils.isEmpty(result)){
            return result;
        }

        try {
            result = new String(result.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



    public Properties load(){
        if(properties != null){
            return properties;
        }

        try {
            Resource resource = new ClassPathResource("rtpay-phone.properties");
            properties =  PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
