/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.utils;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargePayHttpLog;

/**
 * 
 * @author zengzw
 * @date 2017年5月26日
 */
public class PayLogBuilder {
    
    private PayLogBuilder(){};
    

    /** 
     *  构建http日志记录
     *  
     *  
     * @param result
     * @param orderCode
     * @param receiveMethod
     * @param sendData
     * @param receiveData
     * @param sentTime
     * @param receiveTime
     * @param remark
     * @return
     * @throws Exception
     */
    public static  ChargePayHttpLog buildPayLog(Result result, String orderCode, 
            String receiveMethod, String sendData, String receiveData, 
            Date sentTime, Date receiveTime, String remark){
        //---------记录请求日志
        ChargePayHttpLog chargePayHttpLog = new ChargePayHttpLog ();
        //日志编号
        if (result.getUserInfo () != null) {
            chargePayHttpLog.setLogCode (result.getUserInfo ().getSessionId ());
        }
        //业务编号
        chargePayHttpLog.setChargeCode (orderCode);
        //发送请求时间
        chargePayHttpLog.setSendTime (sentTime);
        //收到消息时间
        chargePayHttpLog.setReceiveTime (receiveTime);
        //发送的本地方法
        chargePayHttpLog.setSendMothed ("pay");
        //请求对像的方法，如资金服务中心
        chargePayHttpLog.setReceiveMothed (receiveMethod);
        //发送内容
        chargePayHttpLog.setSendData (JSONObject.toJSONString (sendData));
        //收到消息内容
        chargePayHttpLog.setReceiveData (receiveData);
        //业务类型描述，如下单、退款调用
        chargePayHttpLog.setRemark (remark);
        
        return chargePayHttpLog;
    }
    

}
