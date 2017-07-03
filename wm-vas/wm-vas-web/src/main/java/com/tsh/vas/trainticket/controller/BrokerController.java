/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.trainticket.service.APPAPIService;
import com.tsh.vas.trainticket.service.HCPPayService;
import com.tsh.vas.trainticket.vo.req.RequestOrderBackParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketBackParam;

/**
 *
 * broken 回调controler
 * 
 * @author zengzw
 * @date 2016年11月23日
 */
@Controller
@RequestMapping("/vas/train/broker/")
public class BrokerController extends BaseController{

    @Autowired
    private APPAPIService apiService;
    
    @Autowired
    private HCPPayService payService;


    /**
     * 火车票购票回调
     * @param param
     * @return
     */
    @RequestMapping(value = "order/back", method = {RequestMethod.POST,RequestMethod.GET})
    public void orderBack(RequestOrderBackParam param) {
        logger.info (">>>>>>开发平台购票回调:" + JSONObject.toJSONString (param));
        Result result = getResult ();
        try {
            result = this.apiService.orderCallBack(result, param);
        } catch (Exception ex) {
            logger.error ("开发平台购票回调失败:" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        }


        //调用，调用结算接口
        if (result.getStatus () == 200) {
            try {
                //调用确认结算接口 
                result = this.payService.confirmSettle(result, param.getMerchantOrderId(), false);
                result.setStatus (200);

            } catch (Exception ex) {
                logger.error ("开发平台购票回调，回调失败:" + result.getMsg (),ex);
                result.setStatus (200);
                result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
                result.setMsg (ex.getMessage ());
            } finally {
                send (result);
            }
        } else {
            logger.error ("开发平台购票回调，回调失败:" + result.getMsg ());
            result.setStatus (200);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg ("开发平台购票回调错误，无法结算");
        }

        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));

        PrintWriter write;
        try {
            write = response.getWriter();
            write.println("SUCCESS");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }




    /**
     * 退票回调
     * 
     * 最后要通知回调方是否成功收到。否则会一直不断调用我们的系统。
     *
     * @param resultNotifyRequest
     * @return
     * @author
     */
    @RequestMapping(value = "returnticket/back", method = {RequestMethod.POST,RequestMethod.GET})
    public void returnBack(RequestReturnTicketBackParam resultNotifyRequest) {
        logger.info ("------------------>开发平台退票回调：" + JSONObject.toJSONString (resultNotifyRequest));
        Result result = getResult ();
        try {
            result = this.apiService.returnTicketCallBack(result, resultNotifyRequest);
        } catch (Exception ex) {
            logger.error ("------------------->开发平台退票，回调失败:" + result.getMsg (),ex);
        } finally {
            PrintWriter write;
            try {
                write = response.getWriter();
                write.println("SUCCESS");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
