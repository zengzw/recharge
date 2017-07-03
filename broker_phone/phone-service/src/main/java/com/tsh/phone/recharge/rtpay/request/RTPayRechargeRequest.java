/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.request;

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
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.config.RTRechargeConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.properties.BasicProperties;
import com.tsh.phone.commoms.properties.RTRechargePropertries;
import com.tsh.phone.recharge.gaoypay.xmlbean.PostResult;
import com.tsh.phone.recharge.rtpay.parse.resp.RespReversalInfo;
import com.tsh.phone.recharge.rtpay.parse.resp.RespRechargeInfo;
import com.tsh.phone.util.HttpUtils;
import com.tsh.phone.util.StringUtils;

/**
 *  瑞通 接口http 请求
 *  
 *  
 * @author zengzw
 * @date 2016年7月22日
 */
@Component
public class RTPayRechargeRequest extends BaseRechargeRquest{

    private static final Logger LOGGER = LoggerFactory.getLogger(RTPayRechargeRequest.class);


    /**
     * 高阳配置信息
     */
    @Autowired
    RTRechargeConfig config;




    /**
     * * 订单查询
     * 单查询接口有频率限制，1秒最多只能查1笔订单
     * 
     * @param orderId 订单好Id
     * @return String 
     * 
     */

    @LogConfig(supplierType = Configurations.SP_TYPE_RT)
    public ResponseMessage queryOrderByOrderNo(String orderId){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("reqStreamId", orderId);
        params.put("agtPhone", config.getAgtPhone());
        
        LOGGER.info("#--->orderId:{},before sing params:{}",orderId,JSON.toJSONString(params));
        
        String sign = config.requestSign(params);
        
        LOGGER.info("#--->orderId:{},sing result:{}",orderId,sign);
        
        params.clear();
        params.put("appId", config.getAppId());
        params.put("param", sign);

        //请求
        String inParamters = JSON.toJSONString(params);
        String result =  HttpUtils.doPostJson(config.getOrderQueryUrl(), inParamters);
        LOGGER.info("#-->orderId:{},query result:{}", orderId,result);

        if(StringUtils.isEmpty(result)){
            LOGGER.info("#-->orderId:{},订单查询返回为空",orderId);
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParamters,result);
        }

        return getSuccessMessage(result,inParamters,result);
    }



    /*

     *//**
     * 查询归属地、运营商信息接口
     * 
     * @param mobileNum 手机号码
     * @return  Mobile
     * 
     *//*
    @LogConfig(supplierType = Configurations.SP_TYPE_RT)
    public  ResponseMessage numberSegment(String mobileNum){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("agentid", config.getAgentId());
        params.put("source", config.getSource()); //来源。固定值
        params.put("mobilenum",mobileNum); //手机号码
        String verifyString = config.requestSign(params);
        params.put("verifystring",verifyString);
        LOGGER.info("--> 加密结果："+verifyString);

        String inParams = JSON.toJSONString(params);
        String result =  HttpUtils.doGet(config.getNumberSegmentUrl(), params, HttpUtils.charset_utf8);
        LOGGER.info("-->result:" +result);

        if(StringUtils.isEmpty(result)){
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParams,result);
        }

        if(!JaxbUtil.validate(result,Accsegment.class)){
            LOGGER.info("--> numberSegment 返回结果xml解析出错!:result:"+result);
            String code = StringUtils.stringContentIsInt(result) ? (result): String.valueOf(SystemCodes.ERROR_CODE);
            if(code.equals(GYPropertiesValue.KEY_1000))
                code = GYPropertiesKey.SEGMENT;

            return getErrorMessage(code,inParams,result);
        }

        try {
            Mobile mobile = GaoYXmlToBean.parseSegment(result);
            return getSuccessMessage(JSON.toJSONString(mobile),inParams,result);

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("解析出错.",e);
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParams,result);
        }

    }
*/


    /**
      * 充值接口
      * 
      * 下单返回1006 1011 无响应、超时时是否会调订单查询接口判断状态，而不是直接置为失败
      * 
      * @param orderNo 订单号，自己生成
      * @param prodId  产品Id，通过producQuery() 产品查询接口得到。
      * @param mobileNum  充值号码
      * @param money  充值金额
      * @return  Json String  {@link PostResult}
      *           
      */

    @LogConfig(supplierType = Configurations.SP_TYPE_RT)
    public ResponseMessage recharge(String orderNo,String businessCode,String mobileNum,int money){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("reqStreamId", orderNo); //订单号
        params.put("agtPhone", config.getAgtPhone()); //代理商号码 
        params.put("businessCode",businessCode); //业务代码
        params.put("chargeAddr", mobileNum); //充值手机号码 
        params.put("data", ""); 
        params.put("chargeMoney", money);//充值金额
        params.put("tradePwd", config.getPassword());  //交易密码 
        params.put("nofityUrl", config.getCallbackUrl()); //回调地址

        LOGGER.info("#-->充值接口 加密前参数：{}",JSON.toJSONString(params));
        
        String sign = config.requestSign(params);
        
        LOGGER.info("#-->充值接口 加密结果：{}",sign);
        
        params.clear();
        params.put("appId",config.getAppId());
        params.put("param", sign);

        String inParameters = JSON.toJSONString(params);
        String result =  HttpUtils.doPostJson(config.getRechargeUrl(), params);
        if(StringUtils.isEmpty(result)){
            LOGGER.info("----->充值号码：{}， 充值返回result 为null",mobileNum);
            return  getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParameters,result);    
        }

        RespRechargeInfo rechargeInfo = JSON.parseObject(result,RespRechargeInfo.class);
        String status = rechargeInfo.getStatus();
        if(status.equals("1000") 
                || status.equals("1003")
                || status.equals("1011")
                || status.equals("1012")
                || status.equals("1014")){
            return getSuccessMessage(result,inParameters,result);
        }

        return getErrorMessage(rechargeInfo.getStatus(),inParameters,result);
    }


    /**
     * 冲正接口
     * 
     * @param orderNo
     * @param businessCode
     * @param mobileNum
     * @param money
     * @return
     */
    @LogConfig(supplierType = Configurations.SP_TYPE_RT)
    public ResponseMessage reversal(String orderNo,String businessCode,String mobileNum,int money){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("reqStreamId", orderNo); //订单号
        params.put("agtPhone", config.getAgtPhone()); //代理商号码 
        params.put("businessCode",businessCode); //业务代码
        params.put("chargeAddr", mobileNum); //充值手机号码 
        params.put("chargeMoney", money);//充值金额
        params.put("tradePwd", config.getPassword());  //交易密码 

        LOGGER.info("#-->充正接口 加密前参数：{}",JSON.toJSONString(params));
        
        String sign = config.requestSign(params);
        
        LOGGER.info("#-->充正接口 加密结果：{}",sign);
        
        params.clear();
        params.put("appId",config.getAppId());
        params.put("param", sign);

        String inParameters = JSON.toJSONString(params);
        String result =  HttpUtils.doPostJson(config.getReversalUrl(), params);
        if(StringUtils.isEmpty(result)){
            LOGGER.info("----->充正号码：{}， 充值返回result 为null",mobileNum);
            return  getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParameters,result);    
        }

        RespReversalInfo rechargeInfo = JSON.parseObject(result,RespReversalInfo.class);
        String status = rechargeInfo.getStatus();
        if(status.equals("2000")){
            return getSuccessMessage(result,inParameters,result);
        }

        return getErrorMessage(rechargeInfo.getStatus(),inParameters,result);
    }


      /**
       * 消息回调给vas
       * 
       * @param orderId
       * @param status
       */
    @LogConfig(supplierType = Configurations.SP_TYPE_RT)
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
        return new RTRechargePropertries();
    }
}


