/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.config;

import java.util.Map;

import com.tsh.phone.diamond.TshDiamondClient;

/**
 *  供应商基本配置父类
 *  
 *  包括 账号、请求地址等这些信息
 *  
 * @author zengzw
 * @date 2017年2月20日
 */
public abstract class BaseConfig {
    
    private static TshDiamondClient client = TshDiamondClient.getInstance();
    
    private String  businessKey;
  

    public String getBusinessKey() {
        return  businessKey;
    }

    /**
     * 本地回调。（wmvas地址 ）
     * @return
     */
    public String getLocalCallBackUrl() {
        return client.getConfig("localCallBackUrl");
    }
    
    public TshDiamondClient getTshDiamondClient(){
        return client;
    }

    public static TshDiamondClient getStaticTshDiamondClient(){
        return client;
    }
    
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
    
    
    /**
     * 客户端请求签名
     * 
     * @param params
     * @return
     */
    public abstract String requestSign(Map<String, Object> params);
    
 

}
