/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constroller;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;

/**
 *
 * broken 回调controler
 * 
 * @author zengzw
 * @date 2017年3月9日
 */
@Controller
@RequestMapping("/vas/phone/")
public class PhoneBrokerController extends BaseController{

    @Autowired
    private APIAppPhoneService apiService;


    /**
     * 火车票购票回调
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "order/back", method = {RequestMethod.POST,RequestMethod.GET})
    public void orderBack(RequestCallbackVo param) {
        logger.info ("#phone-orderCallBack---->开发平台充值回调:" + JSONObject.toJSONString (param));
        Result result = getResult ();
        String resultMsg = "SUCCESS";
        try {

            if(param == null){
                logger.info("#phone-orderCallBack---->回调参数为空");
                resultMsg ="ERROR";
                return;
            }

            if(StringUtils.isEmpty(param.getOrderId())){
                logger.info("#phone-orderCallBack---->订单Id为空");
                resultMsg ="ERROR";
                return;
            }


            this.apiService.orderCallBack(result, param);


        } catch (Exception ex) {
            logger.error ("开发平台充值回调失败:" + result.getMsg (),ex);
            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        }finally{
            PrintWriter write;
            try {
                write = response.getWriter();
                write.println(resultMsg);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }
    /**
     * 火车票购票回调
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "order/reversal", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object reversal(String orderNo) {
        logger.info ("#取消充值订单号："+orderNo);
        Result result = this.getResult();

        if(com.tsh.phone.util.StringUtils.isEmpty(orderNo)){
            result.setMsg("订单号为空");
            return result;
        }

        try{
            
            apiService.reversal(result, orderNo);
            
        }catch(Exception e){
            result.setMsg(getErrorMessage(e));
        }

        return result;
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
    }
}
