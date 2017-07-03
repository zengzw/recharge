/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.controller.gypay;

import java.io.IOException;

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
import com.tsh.phone.commoms.config.GYRechargeConfig;
import com.tsh.phone.controller.validate.PhoneRequestValidates;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;

/** 
 * 手机充值回调接口
 * 
 * @author zengzw
 * @date 2016年7月29日
 */
@Controller
@RequestMapping("/rechargephone/gy")
public class GYRechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GYRechargeController.class);


    @Resource(name="gyRecharge")
    IPhoneRechargeService rechargeService;


    @Autowired
    GYRechargeConfig config;
    
    private final static String SUCCESS_STATUS = "2";
    
    private final static String PART_STATUS = "3";
    
    




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
     * 充值回调
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

        return rechargeService.recharge(reqParmas);
    }





    /**
     * 高阳 充值结果回调接口
     * 
     * 代理商商城系统在接收到充值结果后将状态值（status）返回，返回其他信息均表示未收到充值结果
     *5分钟之后重新发送充值结果，直到代理商商城系统返回正确的充值结果：状态值（status）,或者重发的次数到达6次
     * @param request
     * @param response
     */
    @RequestMapping(value="/callback")
    public void gaoyRechargeCallback(HttpServletRequest request,HttpServletResponse response){
        String status = request.getParameter("status"); //订单金额  2充值成功  3部分成功4充值失败
        try {
            RequestCallbackVo callbackVo = new RequestCallbackVo();
            String orderNo = request.getParameter("orderid"); 
            String orderMoney = request.getParameter("ordermoney"); //订单金额
            String mobileBalance = request.getParameter("mobileBalance"); //手机余额
            String verifystring = request.getParameter("verifystring");


            LOGGER.info("#GY--------->gyPay asyn callback parameters# status:{},orderNo:{},"
                    + "orderMoney:{},mobileBalance:{},verifystring:{}",
                    new Object[]{status,orderNo,orderMoney,mobileBalance,verifystring});

            if(StringUtils.isEmpty(orderNo)|| StringUtils.isEmpty(status)){
                LOGGER.info("#GY----------> gyPay asyn callback 参数为空");
                callbackVo.setMessage("供应商返回消息为空");
                callbackVo.setStatus(Configurations.RechargeCallBackStatus.OTHER);
            }else{
                callbackVo.setStatus(Integer.parseInt(status));
                callbackVo.setOrderId(orderNo);
            }

            LOGGER.info("#GY----------> gyPay asyn callback:{},orderNo:{},orderMoeny:{}",
                    new Object[]{(SUCCESS_STATUS.equals(status)? "手机充值成功!" : PART_STATUS.equals(status)? "手机充值部分成功!" : "充值失败" ),orderNo,orderMoney});
            
            
            rechargeService.callback(callbackVo);

            // 部分成功情况下。未充值上部分，如果你不想给用户退款，可以再次给他们充值未成功部分，需要重新下单
            //  如果不再冲了那就退款给用户

        } catch (Exception e) {
            LOGGER.error("gyPay asyn recharge callback error:"+e);
        }finally{
            //不管成功失败，必须把状态返回给服务商。免得服务商多次重试调用
            try {
                response.getWriter().write(status);
            } catch (IOException e) {
                LOGGER.error("gyPay asyn recharge callback printwrite error:",e);
            }
        }
    }



}
