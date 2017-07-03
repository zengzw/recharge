/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.controller.rtpay;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.config.RTRechargeConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.controller.validate.PhoneRequestValidates;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/** 
 * 瑞通 手机充值回调接口
 * 
 * @author zengzw
 * @date 2017年5月9日
 */
@Controller
@RequestMapping("/rechargephone/rt")
public class RTRechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RTRechargeController.class);


    @Resource(name="rtRecharge")
    IPhoneRechargeService rechargeService;


    @Autowired
    RTRechargeConfig config;



    /**
     * 查询订单信息
     * 
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/orderinfo")
    @ResponseBody
    public ResponseResult queryOrderInfo(RequestOrderInfoVo reqParmas){
        LOGGER.info("--- 查询订单信息请求参数:{}",JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validateQueryOrderInfoVo(reqParmas, config);
        if(result.hasError()){
            return result;
        }

        return rechargeService.queryOrderById(reqParmas);
    }


    /**
     * 查询手机号码类型 
     * 
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/phonetype")
    @ResponseBody
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo reqParmas){
        LOGGER.info("--- 查询 手机号码段、归属地接口 请求参数:{}",JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validatePhoneTypeVo(reqParmas, config);
        if(result.hasError()){
            return result;
        }

        return rechargeService.queryPhoneType(reqParmas);
    }



    /**
     * 充值
     * 
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/recharge")
    @ResponseBody
    public ResponseResult recharge(RequestRechargeVo reqParmas){
        LOGGER.info("---充值  请求参数:{}",JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validateRechargeVo(reqParmas,config);
        if(result.hasError()){
            return result;
        }
        
        //特殊验证
        if(StringUtils.isEmpty(reqParmas.getProvinceName())||
                StringUtils.isEmpty(reqParmas.getSupplierType())){
            return new ResponseResult(SystemCodes.ERROR_CODE, "provinceName或者supplierType为空");
        }

        ResponseResult responseResult =  rechargeService.recharge(reqParmas);
        return responseResult;
        
    }
    
    /**
     * 充值
     * 
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/reversal")
    @ResponseBody
    public ResponseResult reversal(RequestReverseVo reqParmas){
        LOGGER.info("--reversal  请求参数:{}",JSON.toJSONString(reqParmas));
        
        ResponseResult result =  PhoneRequestValidates.validateReverseVo(reqParmas,config);
        if(result.hasError()){
            return result;
        }
        
        //特殊验证
        if(StringUtils.isEmpty(reqParmas.getProvinceName())||
                StringUtils.isEmpty(reqParmas.getSupplierType())){
            return new ResponseResult(SystemCodes.ERROR_CODE, "provinceName或者supplierType为空");
        }
        
        return rechargeService.reversal(reqParmas);
    }



    /**
     *瑞通  充值结果回调接口
     * 
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value="/callback")
    public void rechargeCallback(HttpServletRequest request,HttpServletResponse response){
        String agtPhone = request.getParameter("agtPhone"); // //代理号
        String orderNo = request.getParameter("reqStreamId"); //外部订单号
        String state = request.getParameter("state"); //状态。0：成功，1：失败
        String sign = request.getParameter("sign");  //32位 md5小写 （ agtPhone+reqStreamId+state+appKey）
        RequestCallbackVo callbackVo = new RequestCallbackVo();

        LOGGER.info("#RT---------->rtPay asyn callback parameters# agtPhone:{},orderNo:{},state:{},sign:{}",
                new Object[]{agtPhone,orderNo,state,sign});


        if(StringUtils.isEmpty(orderNo)|| StringUtils.isEmpty(state)){
            LOGGER.info("#RT---------> rtPay asyn callback 参数为空");
            callbackVo.setMessage("供应商返回消息为空");
            callbackVo.setStatus(Configurations.RechargeCallBackStatus.OTHER);
        }else{
            callbackVo.setStatus(Integer.parseInt(state));
            callbackVo.setOrderId(orderNo);
        }

        LOGGER.info("#RT----------->rtrPay asyn callback:orderNo:{},{}",orderNo,
                (Configurations.OrderStatus.RECHARGEING.equals(state)? "充值成功":"充值失败"));


        rechargeService.callback(callbackVo);
        
        try{
            response.getWriter().write("success");
        }catch(Exception e){
           LOGGER.error("#RT--->回调异常",e);
        }

    }

}
