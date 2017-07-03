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
 *  高阳话费充值配置文件
 *  
 * @author zengzw
 * @date 2017年2月16日
 */

@Component
public class GYRechargeConfig extends BaseConfig{

    /**
     * 注册供应商签名
     */
    public String getBusinessKey() {
        return  getTshDiamondClient().getConfig("gy.businessKey");
    }

    /**
     *  代理人Id
     * @return
     */
    public String getAgentId() {
        return  getTshDiamondClient().getConfig("gy.agentId");
    }


    /**
     * 回调地址
     * @return
     */
    public String getCallbackUrl() {
        return getTshDiamondClient().getConfig("gy.callbackUrl");
    }

    /**
     *  merchantKey 
     * @return
     */
    public String getMerchantKey() {
        return getTshDiamondClient().getConfig("gy.merchantKey");
    }


    /**
     * 
     * @return
     */
    public String getDomainURL(){
        return getTshDiamondClient().getConfig("gy.baseRequest");
    }

    /**
     * 号码段查询Url
     * 
     * @return
     */
    public String getNumberSegmentUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("gy.numberSegmentUrl");
    }



    /**
     * 订单查询Url
     * @return
     */
    public String getOrderQueryUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("gy.orderQueryUrl");
    }

    /**
     * 产品查询Url
     * 
     * @return
     */
    public String getProductQueryUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("gy.productQueryUrl");
    }


    /**
     * 充值查询URL
     * @return
     */
    public String getRechargeUrl() {
        return getDomainURL() + getTshDiamondClient().getConfig("gy.rechargeUrl");
    }


    /**
     * 请求来源
     * @return
     */
    public String getSource(){
        return getTshDiamondClient().getConfig("gy.source");
    }
    
    


    /**
     * 请求签名
     */
    @Override
    public String requestSign(Map<String, Object> params) {
        if(CollectionUtils.isEmpty(params)){
            throw new BusinessRuntimeException("", "确实参数");
        }

        return generateRquestVerify(params);
    }



    /*
     * 
     *  生产签名key
     *  
     * @param params
     * @return
     */
    private String generateRquestVerify(Map<String, Object> params){
        StringBuffer buffer = new StringBuffer();
        for (String key : params.keySet()) {
            buffer.append(key+"="+params.get(key)).append("&");
        }
        buffer.append("merchantKey="+getMerchantKey());

        return MD5Util.encode(buffer.toString());
    }
}
