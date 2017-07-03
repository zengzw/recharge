/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo.sign;

import java.util.LinkedHashMap;

import com.tsh.phone.util.SignUtils;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 * 话费请求签名
 * 
 * @author zengzw
 * @date 2017年2月16日
 */
public class PhoneRequestSingUtils {
    
    
    /**
     *  获取订单信息签名
     *  
     * @param orderInfo
     * @param businessKey
     * @return
     */
    public static String signQueryOrderInfo(RequestOrderInfoVo orderInfo,String businessKey){
        LinkedHashMap<String,Object> queryPayParams = new LinkedHashMap<String,Object>();
        queryPayParams.put("orderId", orderInfo.getOrderId());

        return SignUtils.sign(queryPayParams,businessKey);
    }
    
    
    
    /**
     *   手机归属地、运营商 签名
     *  
     * @param params
     * @param businessKey
     * @return
     */
    public static String signQueryPhoneType(RequestQueryPhoneTypeVo params,String businessKey){
        LinkedHashMap<String,Object> queryPayParams = new LinkedHashMap<String,Object>();
        queryPayParams.put("mobileNum", params.getMobileNum());
        
        return SignUtils.sign(queryPayParams,businessKey);
    }

    /**
     *   手机归属地、运营商 签名
     *
     * @param params
     * @param businessKey
     * @return
     */
    public static String signFLQueryPhoneType(RequestQueryPhoneTypeVo params, String businessKey){
        LinkedHashMap<String,Object> queryPayParams = new LinkedHashMap<String,Object>();
        queryPayParams.put("mobileNum", params.getMobileNum());
        queryPayParams.put("money", params.getMoney());

        return SignUtils.sign(queryPayParams,businessKey);
    }

    
    /**
     *   手机归属地、运营商 签名
     *  
     * @param params
     * @param businessKey
     * @return
     */
    public static String signRecharge(RequestRechargeVo params,String businessKey){
        LinkedHashMap<String,Object> queryPayParams = new LinkedHashMap<String,Object>();
        queryPayParams.put("mobileNum", params.getMobileNum());
        queryPayParams.put("price", params.getPrice());
        
        return SignUtils.sign(queryPayParams,businessKey);
    }
    
    
    public static String signReverse(RequestReverseVo params,String businessKey){
        LinkedHashMap<String,Object> queryPayParams = new LinkedHashMap<String,Object>();
        queryPayParams.put("mobileNum", params.getMobileNum());
        queryPayParams.put("price", params.getPrice());
        queryPayParams.put("orderNo", params.getOrderNo());
        
        return SignUtils.sign(queryPayParams,businessKey);
    }

}
