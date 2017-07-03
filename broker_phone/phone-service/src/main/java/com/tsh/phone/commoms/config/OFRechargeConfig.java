/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.config;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.tsh.phone.commoms.exceptions.BusinessRuntimeException;
import com.tsh.phone.util.MD5Util;

/**
 * 欧飞 配置文件
 * 
 * @author zengzw
 * @date 2017年2月16日
 */
@Component
public class OFRechargeConfig extends BaseConfig{


    /**
     * 服务商签名
     * @return
     */
    public String getBusinessKey() {
        return  getTshDiamondClient().getConfig("of.businessKey");
    }


    /**
     * 版本
     * @return
     */
    public String getVersion(){
        return  getTshDiamondClient().getConfig("of.version");
    }


    /**
     * 用户ID
     * @return
     */
    public String getUserId() {
        return  getTshDiamondClient().getConfig("of.userId");
    }

    /**
     * 密码
     * @return
     */
    public String getPassword() {
        return  MD5Util.encode(getTshDiamondClient().getConfig("of.password"));
    }


    /**
     * 请求签名字符串
     * @return
     */
    public String getSignKey(){
        return getTshDiamondClient().getConfig("of.signKey");
    }


    /**
     * 
     * @return
     */
    public String getDomainURL(){
        return getTshDiamondClient().getConfig("of.baseRequest");
    }

    

    /**
     * 回调地址
     * @return
     */
    public String getCallbackUrl() {
        return getTshDiamondClient().getConfig("of.callbackUrl");
    }


    /**
     * 查询号码归属地
     * @return
     */
    public String getNumberSegmentUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("of.numberSegmentUrl");
    }


    /**
     * 订单查询
     * 
     * @return
     */
    public String getOrderQueryUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("of.orderQueryUrl");
    }


    /**
     * 充值号码
     * @return
     */
    public String getRechargeUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("of.rechargeUrl");
    }


    /**
     * 查询 是否可充值Url
     * 
     * @return
     */
    public String getAvailableUrl(){
        return getDomainURL() + getTshDiamondClient().getConfig("of.availableUrl"); 
    }
    
    
    /**
     * 获取充值类型
     * 
     * @return
     */
    public String getRechargeType(){
       return "140101"; //快充 
    }


    
    @Override
    public String requestSign(Map<String, Object> params) {
        if(CollectionUtils.isEmpty(params)){
            throw new BusinessRuntimeException("", "确实参数");
        }
        
        StringBuffer buffer = new StringBuffer();
        for (String key : params.keySet()) {
            buffer.append(params.get(key));
        }
        buffer.append(getSignKey());

        String md5Str = MD5Util.encode(buffer.toString());
        return md5Str.toUpperCase();
    }


}
