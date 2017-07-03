/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.config;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.exceptions.BusinessRuntimeException;
import com.tsh.phone.util.DESUtils;
import com.tsh.phone.util.MD5Util;
import com.tsh.phone.util.StringUtils;

/**
 * 瑞通 配置文件
 * 
 * @author zengzw
 * @date 2017年2月16日
 */
@Component
public class RTRechargeConfig extends BaseConfig{


    /**
     * 服务商签名
     * @return
     */
    public String getBusinessKey() {
        return  getTshDiamondClient().getConfig("rt.businessKey");
    }



    /**
     * appId
     * 
     * @return
     */
    public String getAppId() {
        return getTshDiamondClient().getConfig("rt.appId");
    }

    /**
     * appKey
     * 
     * @return
     */
    public String getAppKey() {
        return getTshDiamondClient().getConfig("rt.appKey");
        
    }
    
    
    /**
     * 代理商号码
     * 
     * @return
     */
    public String getAgtPhone(){
        return getTshDiamondClient().getConfig("rt.agtPhone");
    }


    /**
     * 交易密码
     * @return
     */
    public String getPassword() {
        return  MD5Util.encode(getTshDiamondClient().getConfig("rt.password"));
    }


    /**
     *  域名地址
     *  
     * @return
     */
    public String getDomainURL(){
        return getTshDiamondClient().getConfig("rt.baseRequest");
    }



    /**
     * 回调地址
     * @return
     */
    public String getCallbackUrl() {
        return getTshDiamondClient().getConfig("rt.callbackUrl");
    }



    /**
     * 订单查询
     * 
     * @return
     */
    public String getOrderQueryUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("rt.orderQueryUrl");
    }


    /**
     * 充值url
     * @return
     */
    public String getRechargeUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("rt.rechargeUrl");
    }
    
    /**
     * 充值url
     * @return
     */
    public String getReversalUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("rt.reversalUrl");
    }



    /**
     * 参数，转换成json，再根据appKey 进行DES加密
     */
    @Override
    public String requestSign(Map<String, Object> params) {
        if(CollectionUtils.isEmpty(params)){
            throw new BusinessRuntimeException("", "确实参数");
        }

        String jsonParams = JSON.toJSONString(params);
        try {
            return DESUtils.encrypt(jsonParams, getAppKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtils.DEFAULT_STRING;
    }


}
