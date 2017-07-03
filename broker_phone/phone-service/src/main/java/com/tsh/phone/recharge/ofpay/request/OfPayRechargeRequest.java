/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.ofpay.request;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.aop.LogConfig;
import com.tsh.phone.commoms.BaseRechargeRquest;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.OFRechargeConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.properties.BasicProperties;
import com.tsh.phone.commoms.properties.OfRechargePropertries;
import com.tsh.phone.recharge.ofpay.xmlbean.CardInfoElement;
import com.tsh.phone.recharge.ofpay.xmlbean.OrderInfoElement;
import com.tsh.phone.util.DateUtils;
import com.tsh.phone.util.HttpUtils;
import com.tsh.phone.util.JaxbUtil;

/**
 *  欧飞，接口请求
 *  
 * @author zengzw
 * @date 2016年7月22日
 */

@Component
public class OfPayRechargeRequest extends BaseRechargeRquest{

    private static final Logger LOGGER = LoggerFactory.getLogger(OfPayRechargeRequest.class);


    @Autowired
    private OFRechargeConfig config;

    /**
     * 根据手机号码 价格 查询是否充值可用
     * 
     * @param mobileNum  手机号码
     * @param price       价格
     * @return
     */
    @LogConfig(supplierType="OF")
    public ResponseMessage checkPhoneAvailable(String mobileNum,int price){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("userid", config.getUserId());
        params.put("userpws",config.getPassword());
        params.put("phoneno",mobileNum);
        params.put("pervalue", price);
        params.put("version", config.getVersion());

        String inParamters = JSON.toJSONString(params);
        String result = HttpUtils.doGet(config.getAvailableUrl(), params, HttpUtils.charset_gbk);
        LOGGER.info("调用结果:" + result);

        if(result == null){
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParamters,result);

        }

        CardInfoElement cardInfo = JaxbUtil.converyToJavaBean(result, CardInfoElement.class);
        if(cardInfo.getRetCode().equals("1")){
            return getSuccessMessage(JSON.toJSONString(cardInfo),inParamters,result);
        }

        return getErrorMessage(cardInfo.getRetCode(),inParamters,result);
    }




    /**
     * 根据手机号码 查询运营商类型
     *
     */
    @LogConfig(supplierType="OF")
    public  String queryPhoneType(String mobileNum){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("mobilenum",mobileNum);

        String result = HttpUtils.doGet(config.getNumberSegmentUrl(), params, HttpUtils.charset_gbk);
        LOGGER.info("调用结果:" + result);

        return result;
    }



    /**
     *根据订单号 查询当前充值的
     * 返回实际金额
     * 
     * 1充值成功，0充值中，9充值失败，-1找不到此订单。如果返回-1
     * 
     * 
     */
    @LogConfig(supplierType="OF")
    public ResponseMessage queryByOrderNo(String orderNo){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("userid",config.getUserId());
        params.put("userpws",config.getPassword());
        params.put("sporder_id", orderNo);

        String md5Str = config.requestSign(params);
        params.put("md5_str",md5Str);
        params.put("version",config.getVersion());

        String inParameter = JSON.toJSONString(params);
        String result = HttpUtils.doPost(config.getOrderQueryUrl(), params, HttpUtils.charset_gbk);
        LOGGER.info("调用结果:" + result);

        if(result == null){
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParameter,result);
        }


        if(!JaxbUtil.validate(result, OrderInfoElement.class)){
            return  getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),result,inParameter);
        }

        OrderInfoElement orderInfo = JaxbUtil.converyToJavaBean(result, OrderInfoElement.class);
        if(orderInfo.getRetCode().intValue() == 1){
            return getSuccessMessage(JSON.toJSONString(orderInfo),inParameter,result);
        }


        return getErrorMessage(String.valueOf(orderInfo.getRetCode()), inParameter,result);

    }




    /**
     * 在线充值接口
     */
    @LogConfig(supplierType="OF")
    public ResponseMessage recharge(String orderNo,String phoneNo,int price){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("userid", config.getUserId());
        params.put("userpws",config.getPassword());
        params.put("cardid", "140101"); //快充 140101; 慢充：170101 
        params.put("cardnum",price); //面额
        params.put("sporder_id",orderNo); //订单号 注意：不能重复，保证唯一性
        params.put("sporder_time", DateUtils.dateToString(new Date(),"yyyyMMddHHmmss"));
        params.put("game_userid",phoneNo);

        String md5Str = config.requestSign(params);
        params.put("md5_str",md5Str);
        params.put("ret_url",config.getCallbackUrl());
        params.put("version",config.getVersion());

        String inParameter = JSON.toJSONString(params);
        String result =  HttpUtils.doGet(config.getRechargeUrl(), params, HttpUtils.charset_gbk);
        LOGGER.info("result:" +result);

        if(result == null){
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParameter,result);
        }

        if(!JaxbUtil.validate(result, OrderInfoElement.class)){
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParameter,result);
        }


        //如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
        OrderInfoElement orderInfo = JaxbUtil.converyToJavaBean(result, OrderInfoElement.class);
        if(orderInfo.getRetCode()==1
                && (orderInfo.getGameState() != null 
                || orderInfo.getGameState().equals("1")
                || orderInfo.getGameState().equals("0"))){

            return  getSuccessMessage(JSON.toJSONString(orderInfo),inParameter,result);
        }

        return getErrorMessage(String.valueOf(orderInfo.getRetCode()), inParameter,result);
    }


    
    /**
     * 消息回调给vas
     * 
     * @param orderId 订单ID
     * @param status 状态
     * @param msg  消息
     */
    @LogConfig(supplierType="OF")
    public String callback(String orderId,int status,String msg){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("orderId", orderId);
        params.put("status", status);
        params.put("message", msg);
        
        String result = HttpUtils.doGet(config.getLocalCallBackUrl(), params, HttpUtils.charset_utf8);
        
        return result;
    }
    
    
    
    
    
    @Override
    public BasicProperties getProperteis() {
        return new OfRechargePropertries();
    }


}
