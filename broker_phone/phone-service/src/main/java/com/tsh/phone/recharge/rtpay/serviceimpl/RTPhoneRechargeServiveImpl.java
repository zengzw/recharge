/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.serviceimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.po.PhoneRTBusinessPo;
import com.tsh.phone.po.PhoneRTRechargeProduct;
import com.tsh.phone.recharge.rtpay.parse.RTBeanParse;
import com.tsh.phone.recharge.rtpay.parse.resp.RespOrderInfo;
import com.tsh.phone.recharge.rtpay.parse.resp.RespRechargeInfo;
import com.tsh.phone.recharge.rtpay.parse.resp.RespReversalInfo;
import com.tsh.phone.recharge.rtpay.request.RTPayRechargeRequest;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.service.impl.PhoneRTBusinessService;
import com.tsh.phone.service.impl.PhoneRTProductService;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.RespReversalInfoVo;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 *  高阳 充值接口 实现类
 *  
 * @author zengzw
 * @date 2017年2月22日
 */
@Service("rtRecharge")
public class RTPhoneRechargeServiveImpl implements IPhoneRechargeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RTPhoneRechargeServiveImpl.class);


    @Autowired
    private RTPayRechargeRequest request;

    /**
     * 产品编码服务
     */
    @Autowired
    PhoneRTBusinessService businessService;
    
    /**
     * 供货价服务
     */
    @Autowired
    PhoneRTProductService productService;

    /**
     * 查询订单信息
     */
    public ResponseResult queryOrderById(RequestOrderInfoVo reqParams){
        //请求
        ResponseMessage message = request.queryOrderByOrderNo(reqParams.getOrderId());
        if(message.hasError()){
            LOGGER.info("#RT----> 获取订单信息出错。code:{} ",message.getCode());
            return new ResponseResult(SystemCodes.ERROR_CODE, message.getMessage(),message.getCode());
        }

        //返回值转换
        RespOrderInfo postResult = message.parseValueToObject(RespOrderInfo.class);

        //转换成具体统一对象
        OrderInfoVo orderInfoVo =  RTBeanParse.parseOrder(postResult);
        return new ResponseResult(orderInfoVo);

    }



    /**
     * 充值接口
     * @param reqParams
     * @return
     */
    @Override 
    public ResponseResult recharge(RequestRechargeVo reqParams){
        //检查手机状态是否可以用
        String orderNo = StringUtils.getOrderNo(Configurations.SP_TYPE_RT);
        LOGGER.info("#RT---------> 调用瑞通充值接口，请求参数:{}",JSON.toJSONString(reqParams));

        ResponseResult responseResult = new ResponseResult();
       
        //查找供货价 
        PhoneRTRechargeProduct phoneProduct =  productService.getProductOfMobilePhone(reqParams.getProvinceName(),reqParams.getSupplierType(),reqParams.getPrice());
       if(phoneProduct == null){
            LOGGER.info("#RT------ 调用充值出错了,订单号：{}，没有找到供货价放弃充值",orderNo);
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setMessage("不支持当前面前的充值");
            return responseResult;
        }

      
        //查找businessCode
        PhoneRTBusinessPo phoneRTBusinessPo = businessService.queryBusinessCode(reqParams.getProvinceName(), reqParams.getSupplierType());
        if(phoneRTBusinessPo == null){
            LOGGER.info("#RT------ 调用充值出错了,订单号：{}，没有找到BusinessCode放弃充值",orderNo);
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setMessage("没有找到BusinessCode");
            return responseResult;
        }

        
        //进行充值
        ResponseMessage reqeustResult =  request.recharge(orderNo,phoneRTBusinessPo.getBusinessCode(), reqParams.getMobileNum(),reqParams.getPrice());
        if(reqeustResult.hasError()){
            LOGGER.info("#RT------ 调用充值出错了,订单号：{}，code:{},message:{}",new Object[]{orderNo,reqeustResult.getCode(),reqeustResult.getMessage()});
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setCode(reqeustResult.getCode());
            responseResult.setMessage(reqeustResult.getMessage());
            return responseResult;
        }

        //对象的转换
        RespRechargeInfo postResult =  reqeustResult.parseValueToObject(RespRechargeInfo.class);
        RechargeVo rechargeVo =  RTBeanParse.parseRechargeInfo(postResult);
        rechargeVo.setSellerPrice(phoneProduct.getProdPrice()); //供货价 

        //如果下单成功，显示充值中。；否则，充值失败。下单成功，不代表充值成功，成功以callback为准。
        LOGGER.info("#RT-------------> 瑞通手机充值【同步返回】成功。订单号：{},mobile:{},price:{},status:{}",
                new Object[]{orderNo,reqParams.getMobileNum(),reqParams.getPrice(),rechargeVo.getOrderStauts()});
        responseResult.setData(rechargeVo);
        

        //如果状态成功或者失败，模拟供应商回调。
        new Thread(new ThreadCallbackService(this, rechargeVo)).start();
        
        return responseResult;
    }
    
    /**
     * 取消充值 接口
     * @param reqParams
     * 
     * @return
     */
    public ResponseResult reversal(RequestReverseVo reqParams){
        //检查手机状态是否可以用
        String orderNo = reqParams.getOrderNo();
        LOGGER.info("#RT---------> 调用瑞通取消充值接口，请求参数:{}",JSON.toJSONString(reqParams));
        
        ResponseResult responseResult = new ResponseResult();
        
        //查找businessCode
        PhoneRTBusinessPo phoneRTBusinessPo = businessService.queryBusinessCode(reqParams.getProvinceName(), reqParams.getSupplierType());
        if(phoneRTBusinessPo == null){
            LOGGER.info("#RT------ 订单号：{} 调用取消充值充值出错了,，没有找到BusinessCode放弃充值",orderNo);
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setMessage("没有找到BusinessCode");
            return responseResult;
        }
        
        
        //进行冲正
        ResponseMessage reqeustResult =  request.reversal(orderNo,phoneRTBusinessPo.getBusinessCode(), 
                reqParams.getMobileNum(),reqParams.getPrice());
        if(reqeustResult.hasError()){
            LOGGER.info("#RT------ ,订单号：{} 调用取消充值充值出错了，code:{},message:{}",new Object[]{orderNo,reqeustResult.getCode(),reqeustResult.getMessage()});
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setCode(reqeustResult.getCode());
            responseResult.setMessage(reqeustResult.getMessage());
            return responseResult;
        }
        
        //对象的转换
        RespReversalInfo postResult =  reqeustResult.parseValueToObject(RespReversalInfo.class);
        RespReversalInfoVo reversalInfo =  RTBeanParse.parseRespReversalInfo(postResult);
        
        LOGGER.info("#RT-------------> 订单号：{},瑞通取消充值充值成功。,mobile:{},price:{},status:{}",
                new Object[]{orderNo,reqParams.getMobileNum(),reqParams.getPrice(),reversalInfo.getStatus()});
        
        responseResult.setData(reversalInfo);
        return responseResult;
    }




    /**
     * 查询手机归属地、运营商
     * 
     * 对方没提供供应商，直接返回查询错误
     */
    @Override
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo params){
        LOGGER.info("#RT-----> 获取手机号码归属地出错。没有相关接口，直接返回空对象");

        return new ResponseResult(SystemCodes.ERROR_CODE, "供应商没提供查询接口",String.valueOf(SystemCodes.ERROR_CODE));
    }



    /**
     * 消息回调给VAS
     */
    @Override
    public void callback(RequestCallbackVo params) {
        LOGGER.info("#RT-----内部通知请求参数:{}",JSON.toJSONString(params));

        request.callback(params.getOrderId(),parseStatus(params.getStatus()),params.getMessage());
    }



    private int parseStatus(int status){
        if(status == 0){
            return Configurations.RechargeCallBackStatus.SUCCESS;
        }else if(status == 1){
            return Configurations.RechargeCallBackStatus.FAILED;
        }

        return Configurations.RechargeCallBackStatus.OTHER;
    }



}
