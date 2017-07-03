/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsh.phone.recharge.rtpay.commons.RTCommons;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.recharge.RequestCallbackVo;

/**
 * 线程  - 模拟供应商回调
 * 
 *  主要针对瑞通供应商。下单成功就达标充值成功了。
 *  
 * 
 * @author zengzw
 * @date 2017年5月5日
 */
public class ThreadCallbackService implements Runnable{

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadCallbackService.class);
    
    private RechargeVo rechargeInfoVo;
    
    private IPhoneRechargeService  rechargeService;
    
    private final byte[] lock = new byte[0];
    
    /**
     * 
     */
    public ThreadCallbackService(IPhoneRechargeService rechargeService,RechargeVo rechargeInfo) {
       this.rechargeInfoVo = rechargeInfo;
       this.rechargeService = rechargeService;
    }
    
    
    @Override
    public void run() {
        LOGGER.info("--------------回调线程开始执行-----------");
        synchronized (lock) {
            try {
                if(rechargeInfoVo == null){
                    LOGGER.info("#--- 充值对象为空");
                    return;
                }
                
                if(rechargeService == null){
                    LOGGER.info("#--- 服务类为空");
                    return;
                }
                
                LOGGER.info("#--- 回调线程，休息20秒");
                Thread.sleep(20 * 1000);
                
                RequestCallbackVo callBack = new RequestCallbackVo();
                //充值成功
                if(rechargeInfoVo.getCurrentStatus().equals("1000")){
                    LOGGER.info("#--- 订单{}【充值成功】，系统主动回调。",rechargeInfoVo.getOrderNo());
                    
                    callBack.setOrderId(rechargeInfoVo.getOrderNo());
                    callBack.setMessage("充值成功");
                    callBack.setStatus(RTCommons.RechargeCallbackStatus.SUCCESS);//状态。0：成功，1：失败
                    rechargeService.callback(callBack);
                    
                    return;
                }
                //api 文档明确说明，以下集中状态单做是‘充值中’处理
                else if(rechargeInfoVo.getCurrentStatus().equals("1003") //服务器内部 服务器内部 错误
                        || rechargeInfoVo.getCurrentStatus().equals("1011") //订单 正在处理中 正在处理中 正在处理中
                        || rechargeInfoVo.getCurrentStatus().equals("1012") //其他 存疑 错误
                        || rechargeInfoVo.getCurrentStatus().equals("1014")){ //充值 超时
                    
                    LOGGER.info("#--- 订单{}【正在充值中】，系统不做回调。等待供应商回调。",rechargeInfoVo.getOrderNo());
                    return;
                }
                else{
                    //失败处理
                    LOGGER.info("#--- 订单{}【充值失败】，系统主动回调。",rechargeInfoVo.getOrderNo());
                    
                    callBack.setOrderId(rechargeInfoVo.getOrderNo());
                    callBack.setMessage("充值失败");
                    callBack.setStatus(RTCommons.RechargeCallbackStatus.FAIL);//状态。0：成功，1：失败
                    rechargeService.callback(callBack);
                }
                
                
            } catch (InterruptedException e) {
                LOGGER.error("#===> 线程回调callback出错了",e);
            }
        }
        
    }

}
