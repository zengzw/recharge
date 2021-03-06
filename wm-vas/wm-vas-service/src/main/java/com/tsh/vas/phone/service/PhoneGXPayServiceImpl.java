/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.sfund.dto.FundsAccept;
import com.tsh.sfund.dto.UseCoupons;
import com.tsh.sfund.dto.orderpay.WeiXinBizOrderPayParam;
import com.tsh.sfund.orderpay.BizOrderPayService;
import com.tsh.vas.commoms.queue.DBLogQueue;
import com.tsh.vas.commoms.utils.PayLogBuilder;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.iservice.IPayService;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.constants.FundBuilder;
import com.tsh.vas.phone.constants.MQPhoneTopicConstants;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;

/**
 * 故乡支付接口实现类
 * 
 * 
 * @author zengzw
 * @date 2017年5月25日
 */
@Service("gxPayServie")
public class PhoneGXPayServiceImpl implements IPayService{


    private static final Logger logger = LoggerFactory.getLogger(PhoneGXPayServiceImpl.class);


    /**
     * 订单支付接口
     */
    @Autowired
    private BizOrderPayService bizOrderPayService;

    private DBLogQueue<ChargePayHttpLog> longQueue = new DBLogQueue<>();


    @Override
    public Result pay(Result result, PhoneOrderInfoPo orderInfo) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        //----2.调用支付
        WeiXinBizOrderPayParam bizOrderPayParam = new WeiXinBizOrderPayParam ();
        //订单ID
        bizOrderPayParam.setOrderId (orderInfo.getId ());
        //业务单编号（业务的唯一编号）
        bizOrderPayParam.setBizOrderNo (orderInfo.getPhoneOrderCode());
        // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
        bizOrderPayParam.setAddTag (1);

        long totalMoney = orderInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ();
        // 待支付/待退回总额（不包括优惠金额,单位是分）.
        bizOrderPayParam.setTotalMoney (totalMoney);
        //请求操作用户Id.
        bizOrderPayParam.setOperateUserId (userInfo.getUserId ());

        //使用优惠券
        List<UseCoupons> lstUseCoupons = FundBuilder.buildListCoupons(orderInfo,  orderInfo.getOrderCoupon());
        bizOrderPayParam.setUseCoupons (lstUseCoupons);
        
        
        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (null);

        //话费订单支付完成之后需要立即发送扣款短信。 话费专有字段。
        bizOrderPayParam.setExtensions("{\"sendOrderPayMsgImmediately\":\"true\"}");

        //账户使用活动信息. 
        bizOrderPayParam.setOrderPayActivitys(null);

        // 业务详情.
        bizOrderPayParam.setBizDetails ("话费-增值服务订单支付");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("话费-增值服务");
        // PC-业务详情穿透url.订单详情的url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") +PhoneConstants.ORDER_DETAIL+"?orderCode=" + orderInfo.getPhoneOrderCode();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic (MQPhoneTopicConstants.PAY_NOTIFY);

        bizOrderPayParam.setOpenId(orderInfo.getOpenUserId());
        bizOrderPayParam.setProductName("增值服务-话费充值");
        bizOrderPayParam.setOrderAmount(totalMoney);


        Date sendTime1 = new Date ();
        Date receiveTime1;
        FundsAccept fundsAccept = null;
        try {
            logger.info ("调用账户支付PhoneGXPayServiceImpl.requestWXDeskTopUrl():{}",JSONObject.toJSONString (bizOrderPayParam));
            fundsAccept = this.bizOrderPayService.requestWXDeskTopUrl (bizOrderPayParam);
            logger.info ("调用账户支付PhoneGXPayServiceImpl.requestWXDeskTopUrl()结果:{}",JSONObject.toJSONString (fundsAccept));

            receiveTime1 = new Date ();
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(),
                            "PhoneGXPayServiceImpl.requestWXDeskTopUrl()",
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "GX请求账户系统支付")
                    );

        } catch (Exception ex) {
            receiveTime1 = new Date ();
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "PhoneGXPayServiceImpl.requestWXDeskTopUrl()", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "GX请求账户支付调用失败")
                    );

            logger.error ("GX请求账户支付调用失败",ex);
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("GX请求账户支付调用失败");
            return result;
        }

        if(fundsAccept == null){
            logger.error ("GX请求账户支付调用失败,返回对象为空");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("GX请求账户支付调用失败");
            return result;
        }

        //判断是否成功
        if (fundsAccept.getStatus () != 1) {
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "PhoneGXPayServiceImpl.requestWXDeskTopUrl()", 
                            JSONObject.toJSONString (bizOrderPayParam),
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "GX请求账户支付调用返回失败状态")
                    );

            logger.error ("GX请求账户支付调用返回失败状态");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("GX请求账户支付调用返回失败状态");
            return result;
        }

        //判断是否重复提交
        if (fundsAccept.getIsRepetition () == 1) {
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "PhoneGXPayServiceImpl.requestWXDeskTopUrl()", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "GX请求账户支付重复调用")
                    );

            logger.error ("GX请求账户支付重复调用");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("GX请求账户支付重复调用");
            return result;
        }

        result.setData(fundsAccept.getObj());
        result.setStatus(HttpResponseConstants.SUCCESS);
        return result;
    }

}
