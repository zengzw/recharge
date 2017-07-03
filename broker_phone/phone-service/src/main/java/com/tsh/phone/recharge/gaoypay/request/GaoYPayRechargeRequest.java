/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.gaoypay.request;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.aop.LogConfig;
import com.tsh.phone.commoms.BaseRechargeRquest;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.GYRechargeConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.properties.BasicProperties;
import com.tsh.phone.commoms.properties.GYPropertiesKey;
import com.tsh.phone.commoms.properties.GYPropertiesValue;
import com.tsh.phone.commoms.properties.GyRechargeProperties;
import com.tsh.phone.recharge.gaoypay.parse.GaoYXmlToBean;
import com.tsh.phone.recharge.gaoypay.xmlbean.Accsegment;
import com.tsh.phone.recharge.gaoypay.xmlbean.AllProducts;
import com.tsh.phone.recharge.gaoypay.xmlbean.Mobile;
import com.tsh.phone.recharge.gaoypay.xmlbean.PostResult;
import com.tsh.phone.recharge.gaoypay.xmlbean.PostResultElement;
import com.tsh.phone.recharge.gaoypay.xmlbean.Product;
import com.tsh.phone.util.HttpUtils;
import com.tsh.phone.util.JaxbUtil;
import com.tsh.phone.util.StringUtils;

/**
 *  高阳 接口http 请求
 *  
 *  由于供应商接口中，如果错误的话，直接返回字符串错误码。因此这种情况下我们要封装成自己的对象返回。
 *  
 * @author zengzw
 * @date 2016年7月22日
 */
@Component
public class GaoYPayRechargeRequest extends BaseRechargeRquest{

    private static final Logger LOGGER = LoggerFactory.getLogger(GaoYPayRechargeRequest.class);

    
    
    /**
     * 高阳配置信息
     */
    @Autowired
    GYRechargeConfig config;
    
    
    
    
    
    
    /**
     * 产品查询接口
     * 
     *@return  product list
     * 
     */
    
    @LogConfig(supplierType="GY")
    public ResponseMessage queryProduct(){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("agentid", config.getAgentId());
        params.put("source", config.getSource()); //来源。固定值
        String verifyString = config.requestSign(params);
        params.put("verifystring",verifyString);
        LOGGER.info("--> 加密结果："+verifyString);

        String inParamter = JSON.toJSONString(params);
        String result =  HttpUtils.doGet(config.getProductQueryUrl(), params, HttpUtils.charset_utf8);
        LOGGER.info("-->result:" +result);


        if(!JaxbUtil.validate(result,AllProducts.class)){
            LOGGER.info("--> numberSegment 返回结果xml解析出错!。result:{}",result);
            String code = StringUtils.stringContentIsInt(result) ? (result): String.valueOf(SystemCodes.ERROR_CODE);
            
            //由于高阳返回的错误码，没有全局统一。所以这边得根据业务做个性化处理。
            if(code.equals(GYPropertiesValue.KEY_1000))
                    code= GYPropertiesKey.QUERY_PRODUCT;
            
            return getErrorMessage(code,inParamter,result);
        }

        try {
            List<Product> lstProduct = GaoYXmlToBean.parseProduct(result);
            return getSuccessMessage(JSON.toJSONString(lstProduct),inParamter,result);

        } catch (UnsupportedEncodingException e) {
            LOGGER.info("xml parse object error",e);
        }

        return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),inParamter,result);

    }
    
    
    
    


    /**
     * * 订单查询
     * 单查询接口有频率限制，1秒最多只能查1笔订单
     * 
     * @param orderId 订单好Id
     * @return String 
     * 
     */
    
    @LogConfig(supplierType="GY")
    public ResponseMessage queryOrderByOrderNo(String orderId){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("agentid", config.getAgentId());
        params.put("backurl", config.getCallbackUrl());
        params.put("returntype", 2);    //返回类型。2：xml,1:调用callback url。 这里写2
        params.put("orderid", orderId); //订单号 注意：不能重复，保证唯一性
        params.put("source", config.getSource()); //来源。固定值

        String verifyString = config.requestSign(params);
        params.put("verifystring",verifyString);
        LOGGER.info("--> 加密结果："+verifyString);
        
        String inParamters = JSON.toJSONString(params);
        
        String result =  HttpUtils.doGet(config.getOrderQueryUrl(), params, HttpUtils.charset_utf8);
        LOGGER.info("-->result:" +result);

        if(!JaxbUtil.validate(result,PostResultElement.class)){
            LOGGER.info("解析xml出错.code:{}",result);
            String code = StringUtils.stringContentIsInt(result) ? (result): String.valueOf(SystemCodes.ERROR_CODE);
            if(code.equals(GYPropertiesValue.KEY_1000)) 
                code = GYPropertiesKey.QUERY_ORDER;
            return getErrorMessage(code,inParamters,result);
        }

        PostResult postResult =  GaoYXmlToBean.parseOrder(result);
        return getSuccessMessage(JSON.toJSONString(postResult),inParamters,result);
    }

    
    
    
    
    /**
     * 查询归属地、运营商信息接口
     * 
     * @param mobileNum 手机号码
     * @return  Mobile
     * 
     */
    @LogConfig(supplierType="GY")
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
    
    

    /**
     * 充值接口
     * 
     * 下单返回1006 1011 无响应、超时时是否会调订单查询接口判断状态，而不是直接置为失败
     * 
     * @param orderNo 订单号，自己生成
     * @param prodId  产品Id，通过producQuery() 产品查询接口得到。
     * @param mobileNum  充值号码
     * @return  Json String  {@link PostResult}
     *           
     */
    
    @LogConfig(supplierType="GY")
    public ResponseMessage recharge(String orderNo,String prodId,String mobileNum){
        Map<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("prodid", prodId); //产品id
        params.put("agentid", config.getAgentId());
        params.put("backurl", "");
        params.put("returntype", 2); //返回类型 1:backUrl,2:xml。值为2的时候 backurl 可以为空。
        params.put("orderid", orderNo); //订单号 注意：不能重复，保证唯一性
        params.put("mobilenum", mobileNum);
        params.put("source", config.getSource()); //来源。固定值
        params.put("mark",orderNo); //预留字段，原值返回

        String verifyString = config.requestSign(params);
        params.put("verifystring",verifyString);
        LOGGER.info("--> 加密结果："+verifyString);

        String inParameters = JSON.toJSONString(params);

        String result =  HttpUtils.doGet(config.getRechargeUrl(), params, HttpUtils.charset_utf8);
        if(!JaxbUtil.validate(result,PostResultElement.class)){
            LOGGER.info("-----> 充值接口，返回result xml 格式化出错");
            
            String code = StringUtils.stringContentIsInt(result) ? (result): String.valueOf(SystemCodes.ERROR_CODE);
            return  getErrorMessage(code,inParameters,result);
        }

        PostResult postResult =  GaoYXmlToBean.parseOrder(result);
        if(postResult.getResultNo().equals("0000")){
            return getSuccessMessage(JSON.toJSONString(postResult),inParameters,result);
        }

        return getErrorMessage(postResult.getResultNo(),inParameters,result);
    }



    /**
     * 消息回调给vas
     * 
     * @param orderId
     * @param status
     */
    @LogConfig(supplierType="GY")
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
        return new GyRechargeProperties();
    }
    

}


