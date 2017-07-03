/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.google.common.collect.Lists;
import com.tsh.sfund.api.TicketBizType;
import com.tsh.sfund.api.TicketCreateParam;
import com.tsh.sfund.api.TicketDTO;
import com.tsh.sfund.api.TicketService;
import com.tsh.sfund.dto.FundsAccept;
import com.tsh.sfund.dto.UseCoupons;
import com.tsh.sfund.dto.orderpay.BizOrderPayParam;
import com.tsh.sfund.dto.orderrefundV1.OrderRefundParamV1;
import com.tsh.sfund.dto.settleV1.GoodsDetailV1;
import com.tsh.sfund.orderpay.BizOrderPayService;
import com.tsh.sfund.orderrefund.OrderRefundService;
import com.tsh.vas.commoms.enums.EnumActivityStatus;
import com.tsh.vas.commoms.queue.DBLogQueue;
import com.tsh.vas.commoms.utils.PayLogBuilder;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.iservice.IPayRefundService;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.phone.constants.FundBuilder;
import com.tsh.vas.phone.constants.MQPhoneTopicConstants;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.trainticket.constants.MQTopicConstants;

/**
 * 退款服务实现
 * 
 * 
 * @author zengzw
 * @date 2017年5月31日
 */
@Service
public class PhonePayRefundMoneyImpl implements IPayRefundService{


    private static final Logger logger = LoggerFactory.getLogger(PhonePayRefundMoneyImpl.class);


    @Autowired
    private TicketService ticketService;


    /**
     * 订单退款+退货Dubbo接口
     */
    @Autowired
    private OrderRefundService orderRefundService;

    /**
     * 订单支付接口
     */
    @Autowired
    private BizOrderPayService bizOrderPayService;


    @Autowired
    private SupplierInfoDao supplierInfoDao;


    @Autowired
    private BusinessInfoDao businessInfoDao;


    private DBLogQueue<ChargePayHttpLog> longQueue = new DBLogQueue<>();

    @Override
    public void refundMoney(Result result, PhoneOrderInfoPo orderInfo, PhoneRefundAmountPo chargeRefund)
            throws Exception {

        logger.info(">>>>>>orderCode:{}开始退款操作,orderInfo:{},chargeRefund:{}",chargeRefund.getRefundAmountCode(),JSON.toJSONString(chargeRefund));

        //1----获取ticket
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeRefund.getRefundAmountCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationBackOrderSettle.getKey ());
        ticketCreateParam.setUserId (orderInfo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，解锁异常：" + JSONObject.toJSONString (ticketCreateParam) + "异常信息：" + ex);
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }

        //----2.调用退款接口
        BizOrderPayParam bizOrderPayParam = new BizOrderPayParam ();
        //令牌id.
        bizOrderPayParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        bizOrderPayParam.setOrderId (chargeRefund.getId ());
        //业务单编号（业务的唯一编号）
        bizOrderPayParam.setBizOrderNo (chargeRefund.getRefundAmountCode());
        // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
        bizOrderPayParam.setAddTag (2);
        // 待支付/待退回总额（不包括优惠金额,单位是分）. //已供应商实际扣款返回金额位准
        BigDecimal returnMoney = chargeRefund.getRealAmount();
        bizOrderPayParam.setTotalMoney (returnMoney.multiply (new BigDecimal (100)).longValue ());
        //请求操作用户Id.
        bizOrderPayParam.setOperateUserId (orderInfo.getPayUserId ());

        //使用优惠券
        List<UseCoupons> lstUseCoupons = FundBuilder.buildListCoupons(orderInfo,  orderInfo.getOrderCoupon());
        bizOrderPayParam.setUseCoupons (lstUseCoupons);

        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (chargeRefund.getPhoneOrderCode());
        // 业务详情.
        bizOrderPayParam.setBizDetails ("增值服务购票失败订单退款");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("增值服务购票失败订单退款");
        // PC-业务详情穿透url.订单详情的url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url")+  PhoneConstants.ORDER_DETAIL + "?orderCode=" + orderInfo.getPhoneOrderCode();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic (MQPhoneTopicConstants.REFUND_NOTIFY);
        // 所属账户的商业ID.支付账户
        bizOrderPayParam.setBizId (orderInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        bizOrderPayParam.setBizType (orderInfo.getBizType ());
        Date sendTime1 = new Date ();
        Date receiveTime1;
        FundsAccept fundsAccept = null;
        try {
            logger.info (">>>>>>orderCode:{}开始请求退款bizOrderPayService.requestBizOrderRefund():{}",chargeRefund.getRefundAmountCode (),JSONObject.toJSONString (bizOrderPayParam));
            fundsAccept = this.bizOrderPayService.requestBizOrderRefund (bizOrderPayParam);
            logger.info (">>>>>>orderCode:{}请求退款结束bizOrderPayService.requestBizOrderRefund():{}", chargeRefund.getRefundAmountCode (),JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请请求账户系统退款")
                    );
        } catch (Exception e) {
            logger.error ("XXXXXXXXXXXX调用退款接口失败",e);
            receiveTime1 = new Date ();
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "调用退款接口失败")
                    );

            throw new FunctionException (result, "请求账户退款调用失败,请求失败");
        }

        assert fundsAccept != null;
        if (fundsAccept.getStatus () != 1) {
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款调用失败，返回错误状态")
                    );

            logger.error ("XXXXXXXXXXXX请求账户退款调用失败，返回错误状态");
            throw new FunctionException (result, "请求账户退款调用失败，返回错误状态");
        }

        if (fundsAccept.getIsRepetition () == 1) {

            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney", 
                            JSONObject.toJSONString (bizOrderPayParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款重复调用")
                    );

            logger.error ("XXXXXXXXXXXX请求账户退款重复调用");
            throw new FunctionException (result, "请求账户退款重复调用");
        }
    }


    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(12);
        BigDecimal b = BigDecimal.valueOf(11);

        System.out.println(a.compareTo(b));

    }

    @Override
    public void refundMoneyV1(Result result, PhoneOrderInfoPo orderInfo, PhoneRefundAmountPo chargeRefund)
            throws Exception {

        logger.info("======> orderCode:{}火车票【退款退分润】操作,chargeRefund:{}",orderInfo.getPhoneOrderCode(),JSON.toJSONString(chargeRefund));
        //1----获取ticket
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeRefund.getRefundAmountCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationBackOrderSettle.getKey ());
        ticketCreateParam.setUserId (orderInfo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，解锁异常：" + JSONObject.toJSONString (ticketCreateParam) + "异常信息：" + ex);
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }

        OrderRefundParamV1 orderRefundParam = new OrderRefundParamV1();
        //----2.调用结算接口
        //请求操作用户Id.
        orderRefundParam.setOperateUserId (orderInfo.getPayUserId ());
        //令牌id.
        orderRefundParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderRefundParam.setOrderId (chargeRefund.getId ());

        //业务单编号（业务的唯一编号）.
        orderRefundParam.setBizOrderNo (chargeRefund.getRefundAmountCode());
        //业务简述.
        orderRefundParam.setBizIntro ("增值服务火车票退票退款和收回分润结算");
        //业务详情.
        orderRefundParam.setBizDetails ("增值服务火车票退票退款收回分润结算");

        //关联原锁定金额的业务单编号（用于退款，订单支付是按总订单号支付的，所以此处需要传总订单号）
        orderRefundParam.setOffsetBizOrderNo(orderInfo.getPhoneOrderCode());
        //子订单号（用于退分润，订单结算是按子订单进行结算的，所以此处要传子订单编号）
        orderRefundParam.setOffsetOrderNo(orderInfo.getPhoneOrderCode ());

        //消息回调标识,不需要回调则为空.接收消息的主题
        orderRefundParam.setMsgTopic (MQTopicConstants.REFUND_NOTIFY);
        // 所属账户的商业ID.支付账户
        orderRefundParam.setBizId (orderInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        orderRefundParam.setBizType (orderInfo.getBizType ());



        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") +  PhoneConstants.ORDER_DETAIL + "?orderCode=" + orderInfo.getPhoneOrderCode();
        orderRefundParam.setBizPenetrationUrl (bizPenetrationUrl);

        //关联原锁定金额的业务单编号.
        orderRefundParam.setOffsetBizOrderNo (orderInfo.getPhoneOrderCode());
        //订单类型, 1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderRefundParam.setOrderType (11);

        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, orderInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderRefundParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderRefundParam.setProxySupplierId (null);
        //物流费
        //        orderRefundParam.setLogisticsMoney (new BigDecimal (0));
        orderRefundParam.setLogisticsMoneyDetails(null);// 20170518

        //支付者用户ID
        orderRefundParam.setPayerUserId (Long.parseLong(orderInfo.getPayUserId ()+""));
        //销售县域Id
        orderRefundParam.setSaleAreaId (Long.parseLong (orderInfo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderRefundParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderRefundParam.setSaleStoreId (Long.valueOf (orderInfo.getStoreCode ()));

        /*
         * 结算商品明细
         */
        List<GoodsDetailV1> goodsDetails = Lists.newArrayList ();
        GoodsDetailV1 goodsDetail = new GoodsDetailV1 ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (orderInfo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, orderInfo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (orderInfo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (orderInfo.getBusinessName ());

        //### add by zengzw #2016-11-17
        //商品销售价格（秒杀/限时折扣后的价格）
        goodsDetail.setSaleGoodsPrice(orderInfo.getRealAmount());
        //原销售价(秒杀/限时折扣前的价格)，如果没有秒杀/限时折扣活动
        goodsDetail.setOriginalSaleGoodsPrice(orderInfo.getRealAmount());
        //商品所使用的县域优惠券金额
        goodsDetail.setAreaCouponMoney(new BigDecimal (0));
        //##### modify end.....

        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (orderInfo.getCostingAmount ());
        //订单商品数量（取下单时此商品的数量）
        goodsDetail.setBuyGoodsCount(1);

        //供应商优惠券金额
        goodsDetail.setSupplierCouponMoney (new BigDecimal (0));
        //平台优惠券金额
        goodsDetail.setPlatformCouponMoney (new BigDecimal (0));
        //商品数量;订单结算：下单时候的商品数量;退货结算：商品退货的数量
        goodsDetail.setGoodsCount (1);
        //商品下单时的数量（退货的时候要传过来）
        goodsDetail.setBuyGoodsCount (1);
        //供应商系数
        goodsDetail.setSupplierXishu (new BigDecimal (0));
        //县域零售提点比例\县域批发提点比例\全国零售提点比例\代销提点比例
        goodsDetail.setTidianRatio (new BigDecimal (0));

        Long totalMoney = 0L; //总金额
        //退款、推分润(购票失败，支付异常的情况下）

        //佣金比例 
        goodsDetail.setCommissionRatio (BigDecimal.valueOf(0));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (BigDecimal.valueOf(orderInfo.getPlatformDividedRatio())); 
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (BigDecimal.valueOf(0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (BigDecimal.valueOf(0));

        BigDecimal oneHundred = new BigDecimal(100);
        //销售县域佣金比例(平台比率 * 县域比率）
        BigDecimal areaCommissionRatio = BigDecimal.valueOf(orderInfo.getAreaCommissionRatio ())
                .multiply(BigDecimal.valueOf(orderInfo.getAreaDividedRatio()))
                .divide(oneHundred)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        goodsDetail.setSaleAreaCommissionRatio(areaCommissionRatio);

        //销售网点提点比例 （县域零售、全国零售时提供）（平台比率*网点比率）
        BigDecimal storeCommissionRatio = BigDecimal.valueOf(orderInfo.getStoreCommissionRatio ())
                .multiply(BigDecimal.valueOf(orderInfo.getAreaDividedRatio()))
                .divide(oneHundred)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        goodsDetail.setSaleStoreCommissionRatio (storeCommissionRatio); 
        totalMoney = orderInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ();
        //退票退款，按照实际退的金额为准
        goodsDetail.setRefundMoney (orderInfo.getRealAmount());



        //待支付/待退回总额（不包括优惠金额,单位是分）.
        orderRefundParam.setTotalMoney(totalMoney);

        //质量原因退货时间
        goodsDetail.setQualityReturnDays (Integer.parseInt(orderInfo.getBillYearMonth()));
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (Integer.parseInt(orderInfo.getBillYearMonth()));
        goodsDetails.add (goodsDetail);

        //商品活动明细（有活动的情况下要传过来）
        goodsDetail.setGoodsActivityInfos(null);


        orderRefundParam.setGoodsDetails (goodsDetails);

        FundsAccept fundsAccept = null;
        Date sendTime1 = new Date ();
        Date receiveTime1;
        try {
            logger.info (">>>>>>>>>>>>>>>Phone-调用结算接口orderCode:{},refundMoneyV1()：{}",orderInfo.getPhoneOrderCode(),JSONObject.toJSONString (orderRefundParam));
            fundsAccept = orderRefundService.requestOrderRefundV1(result, orderRefundParam);
            logger.info (">>>>>>>>>>>>>>>>Phone-调用结算接口orderCode:{},refundMoneyV1()：{}",orderInfo.getPhoneOrderCode(),JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();

            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney1", 
                            JSONObject.toJSONString (orderRefundParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算")
                    );

        } catch (Exception e) {
            receiveTime1 = new Date ();
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney1", 
                            JSONObject.toJSONString (orderRefundParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败")
                    );

            logger.error ("XXXXXXXXXXXXXXXXPhone-请求账户系统退款和收回分润结算失败",e);
            throw new FunctionException (result, "请求账户系统退款和收回分润结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney1", 
                            JSONObject.toJSONString (orderRefundParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败")
                    );

            logger.error ("XXXXXXXXXXXXXXXXPhone-请求退款和收回分润结算失败");
            throw new FunctionException (result, "请求退款和收回分润结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            longQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                            "RefundMoneyImpl.refundMoney1", 
                            JSONObject.toJSONString (orderRefundParam), 
                            JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款调用失败，返回错误状态")
                    );

            logger.error ("XXXXXXXXXXXXXXXXXPhone-请求退款和收回分润结算重复调用");
            throw new FunctionException (result, "请求退款和收回分润结算重复调用");
        }

    }

}
