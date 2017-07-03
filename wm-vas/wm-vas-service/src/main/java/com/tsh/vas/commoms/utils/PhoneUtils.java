/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.utils;

import com.tsh.vas.phone.enums.EnumServiceProviceType;

/**
 *
 * @author zengzw
 * @date 2017年3月6日
 */
public class PhoneUtils {

    private final static String PREFIX = "vas-phone-";

    private final static String SEPARATOR = "-";

    /**
     * 生产 存储redis key
     * 
     *  key: prefix + 网点编号+充值手机号码
     * @param orderInfoPo
     * @return
     */
    public static String getStoreRedisKey(String storeCode,String mobile){
        String key = PREFIX + storeCode + SEPARATOR + mobile;
        return key;
    }

    
    /**
     *  根据营运商 获取运营商编号
     *  这里模糊匹配
     *  
     * @param name 营运商名称
     * @return
     */
    public static String getServiceProviderCode(String name){
        if(name.indexOf(EnumServiceProviceType.LTCZ.getTypeName()) != -1){
           return EnumServiceProviceType.LTCZ.name(); 
        }
        
        if(name.indexOf(EnumServiceProviceType.YDCZ.getTypeName()) != -1){
            return EnumServiceProviceType.YDCZ.name(); 
        }
        
        if(name.indexOf(EnumServiceProviceType.DXCZ.getTypeName()) != -1){
            return EnumServiceProviceType.DXCZ.name(); 
        }
        
        return "";
    }

    
    /**
     * 生产幸运号. 长度6位。当前号码 不够六位，前面补0.
     * 
     * @param num 当前号码
     * @return String 幸运号
     */
    public static String generateLuckNumber(Integer num){
        String numStr = String.valueOf(num);
        int maxLength = 6; //最大位数
        String fillNo = ""; //填充号码 
        
        for(int i = numStr.length(); i <maxLength; i++  ){
            fillNo += "0";
        }
        
        return fillNo + numStr;
    }

}
