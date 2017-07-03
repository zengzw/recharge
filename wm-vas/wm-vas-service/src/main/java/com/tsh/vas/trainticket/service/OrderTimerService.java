/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundType;
import com.tsh.vas.trainticket.commoms.enums.EnumSeatType;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.SMSMessageTemplateConstants;
import com.tsh.vas.trainticket.constants.UrlConstants;
import com.tsh.vas.trainticket.vo.QueryAccOrderStatusVO;
import com.tsh.vas.trainticket.vo.resp.QueryAccOrderInfoStatusResult;

/**
 * 定时器 Service
 * 
 * @author zengzw
 * @date 2016年12月2日
 */
@Service
public class OrderTimerService {

    private static final Logger logger = LoggerFactory.getLogger(OrderTimerService.class);

    @Autowired
    HcpOrderInfoService orderInfoService;

    @Autowired
    HcpRefundAmountService refundService;

    @Autowired
    APPAPIService apiService;

    @Autowired
    HCPPayService payService;

    @Autowired
    SmsService smsService;

    /**
     * 发车短信提醒
     * @author Administrator <br>
     * @Date 2016年12月6日<br>
     */
    public void startToRemind(){
        //查询需要发送短信的乘车人
        List<HcpOrderInfoPo> hcpOrderInfoPos = this.orderInfoService.getStartToRemindOrder();
        for(HcpOrderInfoPo hcpOrderInfoPo : hcpOrderInfoPos){
            //模板，火车开车时间，起始站，终点站，车次、车厢号、座位号、座位类型
            //如果无座的话，没有座位号，空处理。
            EnumSeatType enumSeatType = EnumSeatType.getEnume(hcpOrderInfoPo.getSeatType());
            String seatType = enumSeatType.getType() == EnumSeatType.STAND.getType()? "":enumSeatType.getName();
            String message = String.format(SMSMessageTemplateConstants.REMIND,
                    hcpOrderInfoPo.getTravelTime(),
                    hcpOrderInfoPo.getStationStartTime(),
                    hcpOrderInfoPo.getFromStation(),
                    hcpOrderInfoPo.getArriveStation(),
                    hcpOrderInfoPo.getTrainCode(),
                    hcpOrderInfoPo.getTrainBox(),
                    hcpOrderInfoPo.getSeatNo(),
                    hcpOrderInfoPo.getSeatNo(),
                    seatType);
            this.smsService.sendSms (hcpOrderInfoPo.getMemberMobile (),message);
        }
    }

    /**
     * 查找支付中的订单，查询账户系统当前状态。如果成功，进行退款，如果失败不处理。、
     * 
     * 查询账户系统的当前支付单状态
     *  成功：
     *      修改订单状态为“支付异常”16
     *      生成退款单
     *      短信提醒
     *      
     *   失败：修改订单状态位“支付失败”6.
     * 
     * 
     */
    public void detalWithPayding(){
        Result result = new Result ();
        //处理时间2小时
        Integer beforeTime = 60*2;

        Date date = DateUtil.addTime(new Date(), -beforeTime);
        List<HcpOrderInfoPo> orderInfoNoPayList = this.orderInfoService.findByPayStatusAndTime (result, EnumOrderInfoPayStatus.PAIDING.getCode (), date).getData ();

        if(CollectionUtils.isEmpty(orderInfoNoPayList)){
            logger.info ("没有【支付中未回调】的订单");
        }else{
            logger.info ("查找到【支付中未回调】的订单条数位：{}",orderInfoNoPayList.size());

            for(HcpOrderInfoPo order:orderInfoNoPayList){
                try {
                    //调用账户系统进行查询订单
                    QueryAccOrderStatusVO queryResult =  queryOrderStatus(order.getHcpOrderCode());
                    if(queryResult == null){
                        continue;
                    }

                    if(queryResult.getStatus() == 1){
                        logger.info("---->当前订单:{}支付状态还在处理中.....",order.getHcpOrderCode());
                        continue;
                    }
                    //成功
                    if(queryResult.getStatus() == 2){
                        logger.info("--->当前订单：{}，支付成功。订单状态变为：【16】支付异常，进行退款操作",order.getHcpOrderCode());
                        //修改订单状态为支付异常（16）
                        orderInfoService.updateStatus(result, order.getHcpOrderCode(),EnumOrderInfoPayStatus.PAY_EXCEPTION.getCode());

                        logger.info("---> 当前订单：{}，生成退款单",order.getHcpOrderCode());
                        //生成退款单
                        apiService.createRefundOrder(result, order.getHcpOrderCode(),order.getRealAmount(),EnumRefundType.PAY_EXCEPTION.getType(),EnumRefundType.PAY_EXCEPTION.getName());

                        //短信提醒
                        String message =String.format(SMSMessageTemplateConstants.TRAIN_FAIL,order.getMemberMobile(),order.getHcpOrderCode());
                        smsService.sendSms(order.getMemberMobile(), message);
                    }
                    else{
                        logger.info("--->当前订单：{}，支付失败，修改状态位：【6】(支付失败）不做操作",order.getHcpOrderCode());
                        //失败
                        orderInfoService.updateStatus(result, order.getHcpOrderCode(),EnumOrderInfoPayStatus.PAY_FAIL.getCode());
                    }
                } catch (Exception e) {
                    logger.error ("自动任务将单改成交易关闭【14】：",e);
                }
            }
        }



    }



    /**
     * 查询订单状态
     * 
     * @param order
     */
    private QueryAccOrderStatusVO queryOrderStatus(String orderNo) {
        String url =TshDiamondClient.getInstance ().getConfig ("acc-url") + UrlConstants.ACC_QUERY_ORDER_STATUS;
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo",orderNo);
        String responseMessage = HttpUtils.doGet(url,params);

        if(StringUtils.isEmpty(responseMessage)){
            logger.info("--->查询账户系统订单状态异常,reponseMessage为空");
            return null;
        }
        try{
            QueryAccOrderInfoStatusResult result =  JSON.parseObject(responseMessage,QueryAccOrderInfoStatusResult.class);
            if(result.getStatus() != HttpResponseConstants.SUCCESS){
                logger.info("---> 获取订单状态失败{}",responseMessage);
                return null;
            }else{
                return result.getData();
            }
        }catch(Exception e){
            logger.error("获取订单状态信息异常",e);
        }

        return null;
    }



    /**
     * 查询1个小时后还在退款中的订单
     * 
     * 1.主动去查询账户系统当前退款的状态
     * 2.修改退款单状态
     *  成功：-
     *  失败：退款进行人工干预
     * 3.短信提醒用户
     */
    public void detalWithRefunding(){
        Result result = new Result ();
        //处理时间1小时
        Integer beforeMinuteTime = -60;

        Date date = DateUtil.addTime(new Date(), beforeMinuteTime);
        List<HcpRefundAmountPo> lstRefundOrderInfo = this.refundService.findByStatusAndRefundTime(result,date,EnumRefundOrderStatus.REFUNDING.getCode()).getData();

        if(CollectionUtils.isEmpty(lstRefundOrderInfo)){
            logger.info ("没有找到要【退款中】的退款记录。");
        }else{
            logger.info ("查找到【退款中】的条数有：{}",lstRefundOrderInfo.size());
            for(HcpRefundAmountPo returnOrder:lstRefundOrderInfo){
                try {
                    //到账户系统查询当前状态
                    QueryAccOrderStatusVO queryResult = queryOrderStatus(returnOrder.getRefundAmountCode());
                    if(queryResult == null){
                        continue;
                    }

                    //如果退款中，不做操作。继续下一轮调用
                    if(queryResult.getStatus() == 1){
                        logger.info("---当前退款单：{}，还在退款中。",returnOrder.getRefundAmountCode());
                        continue;
                    }

                    HcpOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, returnOrder.getHcpOrderCode()).getData();
                    if(queryResult.getStatus() == 2){
                        logger.info("---->退款单：{}，退款成功:【13】",returnOrder.getRefundAmountCode());
                        //如果成功修改位成功
                        refundService.updateRefundStatus(result, returnOrder.getRefundAmountCode(),EnumRefundOrderStatus.REFUND_SUCCESS.getCode());

                        //短信提示
                        String message = String.format(SMSMessageTemplateConstants.RETURN_AMONUT_SUCCESS,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode(),returnOrder.getRealAmount().toString());
                        smsService.sendSms(orderInfo.getMemberMobile(), message);
                    }
                    else{
                        logger.info("---->退款单：{}，退款失败:【15】",returnOrder.getRefundAmountCode());
                        refundService.updateRefundStatus(result, returnOrder.getRefundAmountCode(),EnumRefundOrderStatus.REFUND_FAIL.getCode());

                        String message = String.format(SMSMessageTemplateConstants.RETURN_AMONUT_FAIL,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode());
                        smsService.sendSms(orderInfo.getMemberMobile(), message);
                    }

                } catch (Exception e) {

                    logger.error ("【退款中】处理失败",e);
                }
            }
        }

    }



    /**
     * 查找购票失败、出票失败的退款单进行退款操作
     * 
     *  确认结算
     *  进行退款、退分润
     */
    public void detalWithByErrorAndOutTicketError(){
        Result result = new Result ();
        //处理时间1小时
        Integer beforeMinuteTime = -60;
        Date date = DateUtil.addTime(new Date(), beforeMinuteTime);
        List<HcpRefundAmountPo> lstRefundOrderInfo = this.refundService.findByStatusWaitOrError(result,date,EnumRefundOrderStatus.NON_REFUND.getCode(),EnumRefundType.BUY_TICKET_ERROR.getType()).getData();

        if(CollectionUtils.isEmpty(lstRefundOrderInfo)){
            logger.info ("没有找到要【购票失败、出票失败】的退款记录。");
        }else{
            logger.info ("查找到【购票失败、出票失败】的条数有：{}",lstRefundOrderInfo.size());
            for(HcpRefundAmountPo returnOrder:lstRefundOrderInfo){
                try {

                    HcpOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, returnOrder.getHcpOrderCode()).getData();
                    logger.info("--1.订单号：{},openCode:{}购票退款，进行确认结算",orderInfo.getHcpOrderCode(),orderInfo.getOpenPlatformNo());
                    //确认结算
                    payService.confirmSettle(result, orderInfo.getOpenPlatformNo(), true);


                    logger.info("--2.订单号：{},openCode:{}购票退款，进行退款操作",orderInfo.getHcpOrderCode(),orderInfo.getOpenPlatformNo());
                    //进行退操作
                    apiService.refundCharge(result, returnOrder.getHcpOrderCode());

                } catch (Exception e) {
                    if(e instanceof FunctionException){
                        logger.info("------->Exception:退款失败，修改订单状态，与失败数");
                        refundService.updateRefundStatus(result, returnOrder.getRefundAmountCode(), EnumRefundOrderStatus.REFUND_FAIL.getCode());
                    }

                    logger.error ("[购票失败、出票失败]退款失败",e);
                }
            }
        }

    }


    /**
     * 查找购支付异常的退款单进行【退款】操作
     * 
     *  进行退款操作
     *      （分润单只有成功时才会生成，所以支付异常单并没有生成,故只做退款）
     *      
     */
    public void detalWithByPayError(){
        Result result = new Result ();
        //处理时间1小时
        Integer beforeMinuteTime = -60;
        Date date = DateUtil.addTime(new Date(), beforeMinuteTime);
        List<HcpRefundAmountPo> lstRefundOrderInfo = this.refundService.findByStatusWaitOrError(result,date,EnumRefundOrderStatus.NON_REFUND.getCode(),EnumRefundType.PAY_EXCEPTION.getType()).getData();

        if(CollectionUtils.isEmpty(lstRefundOrderInfo)){
            logger.info ("没有找到要【支付异常】的退款记录");
        }else{
            logger.info ("查找到【支付异常】的条数有：{}",lstRefundOrderInfo.size());
            for(HcpRefundAmountPo returnOrder:lstRefundOrderInfo){
                try {
                    //进行退款操作，不推分润
                    apiService.refundChargeMoney(result, returnOrder.getHcpOrderCode());
                } catch (Exception e) {

                    logger.error ("【支付异常】退款失败",e);
                }
            }
        }

    }



    /**
     * 查找退票退款的退款单进行 【退款操作 不退分润】
     * 
     * 确认结算
     * 进行退款
     *  分润都为0
     * 
     */
    public void detalWithReturnTicket(){
        Result result = new Result ();
        //处理时间6个小时
        Integer beforeMinuteTime = 60 * 6;
        Date date = DateUtil.addTime(new Date(), -beforeMinuteTime);
        List<HcpRefundAmountPo> lstRefundOrderInfo = this.refundService.findByStatusWaitOrError(result,date,EnumRefundOrderStatus.NON_REFUND.getCode(), EnumRefundType.RETURN_TICKET.getType()).getData();

        if(CollectionUtils.isEmpty(lstRefundOrderInfo)){
            logger.info ("没有找到要【用户退款】的退款记录。");
        }else{
            logger.info ("查找到【用户退款】的条数有：{}",lstRefundOrderInfo.size());
            for(HcpRefundAmountPo returnOrder:lstRefundOrderInfo){
                try {
                    HcpOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, returnOrder.getHcpOrderCode()).getData();

                    logger.info("-->1.订单号：{}用户手动退票退款，开始确认结算",orderInfo.getHcpOrderCode());
                    //确认结算
                    payService.confirmSettle(result, orderInfo.getOpenPlatformNo(), true);


                    logger.info("-->2.订单号：{}用户手动退票退款，开始进行退款推[不退分润]",orderInfo.getHcpOrderCode());
                    //进行退操作
                    apiService.refundCharge(result, returnOrder.getHcpOrderCode());
                } catch (Exception e) {
                    if(e instanceof FunctionException){
                        logger.info("------>Exception:退款失败，修改订单状态，与失败数");
                        refundService.updateRefundStatus(result, returnOrder.getRefundAmountCode(), EnumRefundOrderStatus.REFUND_FAIL.getCode());
                    }

                    logger.error ("用户退票退款失败：",e);
                }
            }
        }
    }


}
