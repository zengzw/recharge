/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.controller.ofpay;

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
import com.tsh.phone.commoms.config.OFRechargeConfig;
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
@RequestMapping("/rechargephone/of")
public class OFRechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OFRechargeController.class);


    @Resource(name="ofRecharge")
    IPhoneRechargeService rechargeService;


    @Autowired
    OFRechargeConfig config;





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

        return rechargeService.recharge(reqParmas);
    }



    /**
     * 欧飞 充值结果回调接口
     * 
     * 代理商商城系统在接收到充值结果后将状态值（status）返回，返回其他信息均表示未收到充值结果
     *5分钟之后重新发送充值结果，直到代理商商城系统返回正确的充值结果：状态值（status）,或者重发的次数到达6次
     * @param request
     * @param response
     */
    @RequestMapping(value="/callback")
    public void rechargeCallback(HttpServletRequest request,HttpServletResponse response){
        String retCode = request.getParameter("ret_code"); //充值后状态，1代表成功，9代表撤消
        String orderNo = request.getParameter("sporder_id"); //SP订单号
        String postTime = request.getParameter("ordersuccesstime"); //处理时间
        String errMsg = request.getParameter("err_msg");  //失败原因(ret_code为1时，该值为空)
        RequestCallbackVo callbackVo = new RequestCallbackVo();

        LOGGER.info("#OF---------->ofPay asyn callback parameters# retCode:{},orderNo:{},postTime:{},errMsg:{}",
                new Object[]{retCode,orderNo,postTime,errMsg});


        if(StringUtils.isEmpty(orderNo)|| StringUtils.isEmpty(retCode)){
            LOGGER.info("#OF---------> ofPay asyn callback 参数为空");
            callbackVo.setMessage("供应商返回消息为空");
            callbackVo.setStatus(Configurations.RechargeCallBackStatus.OTHER);
        }else{
            callbackVo.setStatus(Integer.parseInt(retCode));
            callbackVo.setOrderId(orderNo);
        }

        LOGGER.info("#OF-----------> ofPay asyn callback:orderNo:{},{}",orderNo,
                (Configurations.OrderStatus.SUCCESS.equals(retCode)? "充值成功":"充值失败"));


        rechargeService.callback(callbackVo);
        
        
        try{
            response.getWriter().write("success");
        }catch(Exception e){
           LOGGER.error("--->回调异常",e);
        }


    }

}
