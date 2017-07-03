/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.timer;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;

/**
 * 订单定时器
 * 
 * 针对T+3的任务
 * 
 * 
 * @author zengzw
 * @date 2016年11月30日
 */
public class OrderInfoT3Timer {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);



    @Autowired
    HcpOrderInfoService orderInfoService;
    
    

    public void autoRun() {
        
        //处理过期支付中的单，将单改成支付失败
      //  this.dealWithPaiding ();
        

        //处理未支付的订单，改位交易关闭
       // this.detalWithNoPayment ();
    }
    
    
    
    /**
     * 三条未支付的，设置位交易关闭(status:14)
     */
    private void detalWithNoPayment(){
        Result result = new Result ();
        //处理时间3天
        Integer beforeTime = -3;
        try {
            Date date = DateUtil.addDate(new Date(), beforeTime);
            List<HcpOrderInfoPo> orderInfoNoPayList = this.orderInfoService.findByPayStatusAndTime (result, EnumOrderInfoPayStatus.NON_PAYMENT.getCode (), date).getData ();

            if(CollectionUtils.isEmpty(orderInfoNoPayList)){
                logger.info ("=======没有自动任务将单改成关闭状态:status:14");
            }else{
                logger.info ("=======查找到自动任务将单改成关闭状态:status:14条数位：{}",orderInfoNoPayList.size());
                for(HcpOrderInfoPo order:orderInfoNoPayList){
                    this.orderInfoService.updateStatus(result, order.getHcpOrderCode(),EnumOrderInfoPayStatus.ORDER_CLOSE.getCode());
                }
            }

        } catch (Exception e) {
            logger.error ("自动任务将单改成交易关闭(14)：",e);
        }

    }
    
    

    /**
     * 处理卡在了支付中的单，将单改成支付失败（状态：6）
     */
    private void dealWithPaiding() {
        Result result = new Result ();
        //处理时间3天
        Integer beforeTime = -3;
        try {
            Date date = DateUtil.addDate(new Date(), beforeTime);
            List<HcpOrderInfoPo> orderInfoPaidingList = this.orderInfoService.findByPayStatusAndTime (result, EnumOrderInfoPayStatus.PAIDING.getCode (), date).getData ();

            if(CollectionUtils.isEmpty(orderInfoPaidingList)){
                logger.info ("=======没有自动任务将单改成支付失败（status:6)");
            }else{
                logger.info ("=======自动任务将单改成支付失败（status:6)查找条数:{}",orderInfoPaidingList.size());
                for(HcpOrderInfoPo order:orderInfoPaidingList){
                    this.orderInfoService.updateStatus(result, order.getHcpOrderCode(),EnumOrderInfoPayStatus.PAY_FAIL.getCode());
                }
            }

        } catch (Exception e) {
            logger.error ("自动任务将单改成支付失败(6)：",e);
        }

    }


}
