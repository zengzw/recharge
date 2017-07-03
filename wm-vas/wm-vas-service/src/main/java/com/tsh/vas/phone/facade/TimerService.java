/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.facade;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.vas.commoms.exception.BusinessException;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumPhoneRefundOrderStatus;
import com.tsh.vas.phone.enums.EnumRequestOrderType;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.phone.service.PhoneRefundAmountService;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;

/**
 * 定时器 服务类
 * 
 * 
 * @author zengzw
 * @date 2017年3月13日
 */

@Service
public class TimerService {

    private static final Logger logger = LoggerFactory.getLogger(TimerService.class);

    @Autowired
    private PhoneOrderInfoService orderInfoService;


    @Autowired
    private APIAppPhoneService apiService;

    @Autowired
    private PhoneRefundAmountService refundAmountService;


    /**
     * 检查订单状态
     * 
     * @param depositPo
     * @return
     * @throws BusinessException
     */
    private void checkStatusAndretryRecharing(PhoneOrderInfoPo orderinfo){
        logger.info("#callbacktimer--->处理当前的订单号：{}",orderinfo.getPhoneOrderCode());
        //
        Result result = new Result();
        OrderInfoVo orderInfoVo = null;
        try {
            orderInfoVo = apiService.queryRechargeOrderInfo(result, orderinfo.getPhoneOrderCode()).getData();

        } catch (Exception e) {
            logger.info("#--处理回调消息出错",e);
        }

        if(orderInfoVo == null){
            logger.info("#callbacktimer--> 查询订单返回为空。openOrderCode:{}",orderinfo.getOpenPlatformNo());
            return;
        }
        String orderStatus = orderInfoVo.getOrderStatus();

        // 如果返回“不确定”状态，直接跳过，下次轮继续处理
        if(orderStatus.equals(PhoneConstants.OrderStatus.NOT_CONFIRM)){
            logger.info("#callbacktimer--> 不确定订单。openOrderCode:{}",orderinfo.getOpenPlatformNo());
            return;
        }

        //订单不存在
        if(orderStatus.equals(PhoneConstants.OrderStatus.NOT_EXISTS)){
            logger.info("#callbacktimer--> 订单不存在。openOrderCode:{}",orderinfo.getOpenPlatformNo());

            apiService.rechargeErrorWithBusiness(result, orderinfo);
            return;
        }


        //如果订单成功的话，修改订单状态
        if(orderStatus.equals(PhoneConstants.OrderStatus.SUCCESS)){
            logger.info("#callbacktimer-->订单号:{},充值成功，修改订单状态",orderinfo.getOpenPlatformNo());

            apiService.rechargeSuccessWithBusiness(result,orderinfo);
        }
        //充值失败
        else if(orderStatus.equals(PhoneConstants.OrderStatus.FAILED)){
            logger.info("#callbacktimer-->订单号:{},充值失败，执行轮询操作",orderinfo.getOpenPlatformNo());
            apiService.recharge(result, orderinfo.getPhoneOrderCode());

        }
        //部分成功
        else if(orderStatus.equals(PhoneConstants.OrderStatus.PARTSUCCESS)){
            logger.info("#callbacktimer-->订单号:{},部分成功",orderinfo.getOpenPlatformNo());
        }
        //正在充值
        else if(orderStatus.equals(PhoneConstants.OrderStatus.RECHARGEING)){
            logger.info("#callbacktimer-->订单号:{},正在充值中",orderinfo.getOpenPlatformNo());

        }
        //其他状态
        else{
            logger.info("#callbacktimer-->订单号:{},其他状态",orderinfo.getOpenPlatformNo());
        }


    }





    /**
     *  处理没回调的消息
     */
    public void dealWithNoCallBackOrderInfo(){
        Result result = new Result();
        int time = 10;

        List<PhoneOrderInfoPo> lstPhones =  orderInfoService.queryAllOrderInfoRechargeing(result, time).getData();
        if(CollectionUtils.isEmpty(lstPhones)){
            logger.info("#callbacktimer--> 没有找到充值没回调的订单");
            return;
        }

        logger.info("#callbacktimer----->找到需要处理的数据条数:[{}]",lstPhones.size());
        for(PhoneOrderInfoPo orderInfo : lstPhones){

            checkStatusAndretryRecharing(orderInfo);
        }

    }


    /**
     * 处理支付中,超过30分钟的订单,将其改为支付失败
     */
    public void dealWithPadingOrderInfo(){
        Result result = new Result();
        int time = 30;

        List<PhoneOrderInfoPo> lstPhones =  orderInfoService.queryAllOrderInfoPaiding(result, time).getData();
        if(CollectionUtils.isEmpty(lstPhones)){
            logger.info("#callbacktimer--> 没有【支付中】的订单。");
            return;
        }

        logger.info("#callbacktimer----->【支付中】订单数：[{}]",lstPhones.size());
        for(PhoneOrderInfoPo orderInfo : lstPhones){
            if(EnumRequestOrderType.GX.name().equals(orderInfo.getSources())){
                orderInfoService.updateStatus(result,orderInfo.getPhoneOrderCode(),EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode(),"故乡支付超时，订单关闭");
            }
        }

    }



    /**
     *  
     * 针对未退款的单进行退款操作。
     *      
     */
    public void detalWithByUnRefundOrder(){
        Result result = new Result ();
        //处理时间2小时
        Integer beforeMinuteTime = -60 * 2;
        Date date = DateUtil.addTime(new Date(), beforeMinuteTime);
        List<PhoneRefundAmountPo> lstRefundOrderInfo = refundAmountService.findAllUnRefundOrder(result, date).getData();

        if(CollectionUtils.isEmpty(lstRefundOrderInfo)){
            logger.info ("没有找到要【待退款】的退款记录");
        }else{
            logger.info ("查找到【待退款】的条数有：{}",lstRefundOrderInfo.size());
            for(PhoneRefundAmountPo returnOrder:lstRefundOrderInfo){
                try {
                    //进行退款操作
                    apiService.refundChargeMoney(result, returnOrder.getPhoneOrderCode());

                    logger.info("------------> 退款返回结果！"+JSON.toJSONString(result));

                    if(result.getStatus() == HttpResponseConstants.SUCCESS){
                        //订单退款状态退款中,更新退款时间
                        apiService.updateRefundStatusAndTime(result, returnOrder.getRefundAmountCode(), 
                                EnumPhoneRefundOrderStatus.REFUNDING.getCode (),new Date());

                        logger.info("----->退款成功，修改订单号状态,12 退款中");

                    }else{
                        //订单退款状态退款中
                        apiService.updateRefundStatusAndTime(result, returnOrder.getRefundAmountCode(), 
                                EnumPhoneRefundOrderStatus.REFUND_FAIL.getCode (),new Date());

                        logger.info("----->退款失败，修改订单号状态,15 失败");

                    }

                } catch (Exception e) {

                    logger.error ("退款失败",e);
                }
            }
        }

    }

}
