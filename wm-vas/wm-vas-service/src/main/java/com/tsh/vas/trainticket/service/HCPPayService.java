/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.metaq.Producer;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tsh.sfund.api.TicketBizType;
import com.tsh.sfund.api.TicketCreateParam;
import com.tsh.sfund.api.TicketDTO;
import com.tsh.sfund.api.TicketService;
import com.tsh.sfund.dto.FundsAccept;
import com.tsh.sfund.dto.orderpay.BizOrderPayParam;
import com.tsh.sfund.dto.orderrefundV1.OrderRefundParamV1;
import com.tsh.sfund.dto.settleV1.GoodsDetailV1;
import com.tsh.sfund.dto.settleV1.OrderSettleParamV1;
import com.tsh.sfund.orderpay.BizOrderPayService;
import com.tsh.sfund.orderrefund.OrderRefundService;
import com.tsh.sfund.settle.OrderSettleService;
import com.tsh.vas.commoms.constants.FundURLConstants;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.ChargePayHttpLogDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.dao.trainticket.HcpOrderInfoDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundType;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.MQTopicConstants;
import com.tsh.vas.trainticket.constants.UrlConstants;
import com.tsh.vas.trainticket.vo.MemberResultVo;
import com.tsh.vas.vo.charge.MemberVo;

/**
 *  火车票 支付业务服务
 *
 * @author zengzw
 * @date 2016年11月28日
 */

@Service
public class HCPPayService {

    private static final Logger logger = LoggerFactory.getLogger(HCPPayService.class);

    @Autowired
    private TicketService ticketService;


    @Autowired
    private ChargePayHttpLogDao chargePayHttpLogDao;

    @Autowired
    private HcpOrderInfoDao orderInfoDao;

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

    /**
     * 订单结算接口
     */
    @Autowired
    private OrderSettleService orderSettleService;


    /**
     * 支付接口
     * 
     * @param result 
     * @param chargeInfo 订单信息对象
     * @throws Exception
     */
    public  void pay(int size) throws Exception {
        if(size >2){
            throw new FunctionException(new Result(), "测试支付");
        }
    }

    /**
     * 支付接口
     * 
     * @param result 
     * @param chargeInfo 订单信息对象
     * @throws Exception
     */
    public  void pay(Result result, HcpOrderInfoPo orderInfo) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfo.getId ());
        ticketCreateParam.setBizOrderNo (orderInfo.getHcpOrderCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationOrderPay.getKey ());
        ticketCreateParam.setUserId (userInfo.getUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
            logger.info("获取访问令牌信息为：{}",ticketDTO);

        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，已经解锁过了.{}",JSONObject.toJSONString (ticketCreateParam));
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }
        //----2.调用支付
        BizOrderPayParam bizOrderPayParam = new BizOrderPayParam ();
        //令牌id.
        bizOrderPayParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        bizOrderPayParam.setOrderId (orderInfo.getId ());
        //业务单编号（业务的唯一编号）
        bizOrderPayParam.setBizOrderNo (orderInfo.getHcpOrderCode());
        // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
        bizOrderPayParam.setAddTag (1);
        // 待支付/待退回总额（不包括优惠金额,单位是分）.
        bizOrderPayParam.setTotalMoney (orderInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ());
        //请求操作用户Id.
        bizOrderPayParam.setOperateUserId (userInfo.getUserId ());
        // 账户使用优惠券信息.
        bizOrderPayParam.setUseCoupons (null);
        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (null);

        //账户使用活动信息. modify by zengzw.2016-11-17。没用到活动信息，为空
        bizOrderPayParam.setOrderPayActivitys(null);

        // 业务详情.
        bizOrderPayParam.setBizDetails ("火车票增值服务订单支付");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("火车票增值服务");
        // PC-业务详情穿透url.订单详情的url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") +UrlConstants.ORDER_DETAIL+"?orderCode=" + orderInfo.getHcpOrderCode();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic (MQTopicConstants.PAY_NOTIFY);
        // 所属账户的商业ID.支付账户
        bizOrderPayParam.setBizId (orderInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        bizOrderPayParam.setBizType (orderInfo.getBizType ());
        Date sendTime1 = new Date ();
        Date receiveTime1;
        FundsAccept fundsAccept = null;
        try {
            logger.info ("调用账户支付APPAPIService.requestBizOrderPay():{}",JSONObject.toJSONString (bizOrderPayParam));
            fundsAccept = this.bizOrderPayService.requestBizOrderPay (bizOrderPayParam);
            logger.info ("调用账户支付APPAPIService.requestBizOrderPay()结果:{}",fundsAccept);
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "APPAPIService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统支付");
        } catch (Exception ex) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "APPAPIService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付调用失败");
            logger.error ("请求账户支付调用失败");
            //throw new FunctionException (result, "请求账户支付调用失败");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("请求账户支付调用失败");
            return;
        }

        if(fundsAccept == null){
            logger.error ("请求账户支付调用失败,返回对象为空");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("请求账户支付调用失败");
            return;
        }

        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "APPAPIService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付调用返回失败状态");
            logger.error ("请求账户支付调用返回失败状态");
            //            throw new FunctionException (result, "请求账户支付调用返回失败状态");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("请求账户支付调用返回失败状态");
            return;
        }

        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "APPAPIService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付重复调用");
            logger.error ("请求账户支付重复调用");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("请求账户支付重复调用");
            return;
            //            throw new FunctionException (result, "请求账户支付重复调用");
        }
    }

    /**
     * 新 结算接口V1
     * 
     * @param result
     * @param openPlatformNo
     * @return
     * @throws Exception
     */
    public Result paySettleV1(Result result, HcpOrderInfoPo orderInfoPo) throws Exception {
        logger.info("--> 开始生产结算记录......openPlatformNo:"+JSON.toJSONString(orderInfoPo));

        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfoPo.getId ());
        ticketCreateParam.setBizOrderNo (orderInfoPo.getHcpOrderCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationPeriodSettle.getKey ());
        ticketCreateParam.setUserId (orderInfoPo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
            logger.info("获取访问令牌信息：" + ticketDTO);
        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，已经解锁过了" + JSONObject.toJSONString (ticketCreateParam),ex);
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }

        List<OrderSettleParamV1> lstOrderSettleParam = new ArrayList<>();
        //----2.调用结算接口
        OrderSettleParamV1 orderSettleParam = new OrderSettleParamV1 ();
        //请求操作用户Id.
        orderSettleParam.setOperateUserId (orderInfoPo.getPayUserId ());
        //令牌id.
        orderSettleParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderSettleParam.setOrderId (orderInfoPo.getId ());
        
        //总订单号  
        orderSettleParam.setTotalOrderNo(orderInfoPo.getHcpOrderCode());

        //业务单编号（业务的唯一编号）.
        orderSettleParam.setBizOrderNo (orderInfoPo.getHcpOrderCode());
        //业务简述.
        orderSettleParam.setBizIntro ("增值服火车票务结算记录");
        //业务详情.
        orderSettleParam.setBizDetails ("增值服务火车票结算");
        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + UrlConstants.ORDER_DETAIL+"?orderCode=" + orderInfoPo.getHcpOrderCode();
        orderSettleParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空(消息标识未由账号定义是考虑：外部传递是解决多个订阅者能够灵活进行消息隔离).
        orderSettleParam.setMsgTopic ("");
        //关联原锁定金额的业务单编号.
        orderSettleParam.setOffsetBizOrderNo (orderInfoPo.getHcpOrderCode());
        //订单类型,    1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderSettleParam.setOrderType (11);
        //订单应付金额
        orderSettleParam.setOrderMoney (orderInfoPo.getOriginalAmount ());

        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, orderInfoPo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderSettleParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderSettleParam.setProxySupplierId (null);
        //供应商账期
        orderSettleParam.setSupplierPaymentDays (Integer.parseInt(orderInfoPo.getBillYearMonth()));
        //分润账期
        orderSettleParam.setProfitPaymentDays (Integer.parseInt(orderInfoPo.getBillYearMonth()));
        //物流费
        orderSettleParam.setLogisticsMoney (new BigDecimal (0));
        //支付者用户ID
        orderSettleParam.setPayerUserId (Long.parseLong(orderInfoPo.getPayUserId ()+""));
        //销售县域Id
        orderSettleParam.setSaleAreaId (Long.parseLong (orderInfoPo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderSettleParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderSettleParam.setSaleStoreId (Long.valueOf (orderInfoPo.getStoreCode ()));


        //结算商品明细
        List<GoodsDetailV1> goodsDetails = Lists.newArrayList ();
        GoodsDetailV1 goodsDetail = new GoodsDetailV1 ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (orderInfoPo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, orderInfoPo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (orderInfoPo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (orderInfoPo.getBusinessName ());

        //### add by zengzw #2016-11-17
        //商品销售价格（秒杀/限时折扣后的价格）
        goodsDetail.setSaleGoodsPrice(orderInfoPo.getRealAmount());
        //原销售价(秒杀/限时折扣前的价格)，如果没有秒杀/限时折扣活动
        goodsDetail.setOriginalSaleGoodsPrice(orderInfoPo.getRealAmount());
        //商品所使用的县域优惠券金额
        goodsDetail.setAreaCouponMoney(new BigDecimal (0));
        //##### modify end.....


        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (orderInfoPo.getCostingAmount ());
        //实际退款金额
        goodsDetail.setRefundMoney (new BigDecimal (0));
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
        //佣金比例
        goodsDetail.setCommissionRatio (new BigDecimal (orderInfoPo.getLiftCoefficient ()));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (new BigDecimal (orderInfoPo.getPlatformDividedRatio()));
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));

        BigDecimal oneHundred = new BigDecimal(100);
        //销售县域佣金比例(平台比率 * 县域比率）
        BigDecimal areaCommissionRatio = new BigDecimal(orderInfoPo.getAreaCommissionRatio ())
                .multiply(new BigDecimal (orderInfoPo.getAreaDividedRatio()))
                .divide(oneHundred)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        goodsDetail.setSaleAreaCommissionRatio(areaCommissionRatio);

        //销售网点提点比例 （县域零售、全国零售时提供）（平台比率*网点比率）
        BigDecimal storeCommissionRatio = new BigDecimal(orderInfoPo.getStoreCommissionRatio ())
                .multiply(new BigDecimal (orderInfoPo.getAreaDividedRatio()))
                .divide(oneHundred)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        goodsDetail.setSaleStoreCommissionRatio (storeCommissionRatio); 

        //质量原因退货时间
        goodsDetail.setQualityReturnDays (Integer.parseInt(orderInfoPo.getBillYearMonth()));
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (Integer.parseInt(orderInfoPo.getBillYearMonth()));
        goodsDetails.add (goodsDetail);

        orderSettleParam.setGoodsDetails (goodsDetails);
        lstOrderSettleParam.add(orderSettleParam);

        FundsAccept fundsAccept = null;
        Date sendTime1 = new Date ();
        Date receiveTime1;
        try {
            logger.info ("---->HCP调用结算接口orderSettleService.orderSettle()：" + JSONObject.toJSONString (orderSettleParam));
            fundsAccept = this.orderSettleService.orderSettleV1(result, lstOrderSettleParam);
            logger.info ("---->HCP调用结算接口orderSettleService.orderSettle()结果：" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfoPo.getHcpOrderCode(), "HCP-orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算");
        } catch (Exception e) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result,orderInfoPo.getHcpOrderCode(), "HCP-orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败");
            logger.error ("----->HCP请求账户系统结算失败",e);
            throw new FunctionException (result, "请求账户系统结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, orderInfoPo.getHcpOrderCode(), "HCP-orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("---->HCP请求结算调用失败");
            throw new FunctionException (result, "请求结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, orderInfoPo.getHcpOrderCode(), "HCP-orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("----->HCP请求结算重复调用");
            throw new FunctionException (result, "请求结算重复调用");
        }

        logger.info("----->HCP完成生产结算记录......openPlatformNo:"+JSON.toJSONString(orderInfoPo));
        return result;
    }

    /**
     *  确认资金结算
     *  
     *  
     * @param result
     * @param openPlatformNo 开放平台外部订单号
     * @param immediatelySettle  缴费成功：false、失败:true
     * @return
     * @throws Exception
     */
    public Result confirmSettle(Result result, String openPlatformNo,boolean immediatelySettle) throws Exception {
        logger.info("----->HCP PayService.confirmSettle()确认结算接口请求.openPlatformNo:"+openPlatformNo);

        HcpOrderInfoPo orderInfoPo = this.orderInfoDao.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        if(orderInfoPo == null){
            logger.info("---->HCP PayService.confirmSettle() 订单信息为空");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }


        //判断状态。只有交易成功，购票失败，出票失败才确认结算
        int pageStatus = orderInfoPo.getPayStatus().intValue();
        if(pageStatus!= EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()
                && pageStatus != EnumOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue()
                && pageStatus != EnumOrderInfoPayStatus.TICKET_FAIL.getCode().intValue()){
            logger.info("------>orderCode:{}当前订单状态不是”交易成功“，不做处理！status:{}",orderInfoPo.getHcpOrderCode(),pageStatus);
            result.setStatus(HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }


        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId",orderInfoPo.getId());
        paramsMap.put("orderNo",orderInfoPo.getHcpOrderCode());
        paramsMap.put("immediatelySettle",immediatelySettle);
        Date begin = new Date();
        String message = JSON.toJSONString(paramsMap);
        logger.info("----->confirmSettle 通知确认结算MQ消息请求参数："+message);

        //发送MQ消息
        boolean flag = Producer.getInstance().producerRun(MQTopicConstants.MQ_CONFIRM_SETTLE,message, null);

        logger.info("------>confirmSettle 通知确认结算MQ消息请求结果：{}",   flag);
        this.insertHttpLog (result, orderInfoPo.getHcpOrderCode(), MQTopicConstants.MQ_CONFIRM_SETTLE,message, flag+"", begin, new Date(), "确认结算接口");

        result.setStatus((flag?HttpResponseConstants.SUCCESS:HttpResponseConstants.ERROR));
        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }


    /**
     * 退款。针对购票成功的情况，用户手动退票退款
     * 
     * @param result
     * @param chargeInfo
     * @param chargeRefund
     * @throws Exception
     */
    public void refundMoney(Result result,HcpOrderInfoPo orderInfo, HcpRefundAmountPo chargeRefund) throws Exception {
        logger.info(">>>>>>orderCode:{}开始火车票退款操作,orderInfo:{},chargeRefund:{}",orderInfo.getHcpOrderCode(),JSON.toJSONString(chargeRefund));

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
        // 账户使用优惠券信息.
        bizOrderPayParam.setUseCoupons (null);
        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (chargeRefund.getHcpOrderCode());
        // 业务详情.
        bizOrderPayParam.setBizDetails ("增值服务购票失败订单退款");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("增值服务购票失败订单退款");
        // PC-业务详情穿透url.订单详情的url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url")+  UrlConstants.ORDER_DETAIL + "?orderCode=" + orderInfo.getHcpOrderCode();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic (MQTopicConstants.REFUND_NOTIFY);
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
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "HcpPayService.refundMoney", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统退款");
        } catch (Exception e) {
            logger.error ("XXXXXXXXXXXX调用退款接口失败",e);
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HcpPayService.refundMoney", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "调用退款接口失败");
            throw new FunctionException (result, "请求账户退款调用失败,请求失败");
        }
        assert fundsAccept != null;
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HcpPayService.refundMoney", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款调用失败，返回错误状态");
            logger.error ("XXXXXXXXXXXX请求账户退款调用失败，返回错误状态");
            throw new FunctionException (result, "请求账户退款调用失败，返回错误状态");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HcpPayService.refundMoney", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款重复调用");
            logger.error ("XXXXXXXXXXXX请求账户退款重复调用");
            throw new FunctionException (result, "请求账户退款重复调用");
        }
    }

    /**
     * 退款 + 推分润
     * 
     * @param result
     * @param chargeInfo
     * @param chargeRefund
     * @throws Exception
     */
    public void refundMoneyV1(Result result, HcpOrderInfoPo orderInfo, HcpRefundAmountPo chargeRefund) throws Exception {
        logger.info("======> orderCode:{}火车票【退款退分润】操作,chargeRefund:{}",orderInfo.getHcpOrderCode(),JSON.toJSONString(chargeRefund));
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
        orderRefundParam.setOffsetBizOrderNo(orderInfo.getHcpOrderCode());
        //子订单号（用于退分润，订单结算是按子订单进行结算的，所以此处要传子订单编号）
        orderRefundParam.setOffsetOrderNo(orderInfo.getHcpOrderCode ());

        //消息回调标识,不需要回调则为空.接收消息的主题
        orderRefundParam.setMsgTopic (MQTopicConstants.REFUND_NOTIFY);
        // 所属账户的商业ID.支付账户
        orderRefundParam.setBizId (orderInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        orderRefundParam.setBizType (orderInfo.getBizType ());



        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + UrlConstants.ORDER_DETAIL+ "?orderCode=" + orderInfo.getHcpOrderCode();
        orderRefundParam.setBizPenetrationUrl (bizPenetrationUrl);

        //关联原锁定金额的业务单编号.
        orderRefundParam.setOffsetBizOrderNo (orderInfo.getHcpOrderCode());
        //订单类型, 1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderRefundParam.setOrderType (11);

        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, orderInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderRefundParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderRefundParam.setProxySupplierId (null);
        //物流费
//        orderRefundParam.setLogisticsMoney (new BigDecimal (0));
        orderRefundParam.setLogisticsMoneyDetails(null);//20170518

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
        if(chargeRefund.getRefundType() == EnumRefundType.BUY_TICKET_ERROR.getType()
                || chargeRefund.getRefundType() == EnumRefundType.PAY_EXCEPTION.getType()){
            //佣金比例
            goodsDetail.setCommissionRatio (new BigDecimal (orderInfo.getLiftCoefficient ()));
            //平台提点比例
            goodsDetail.setPlatformTidianRatio (new BigDecimal (orderInfo.getPlatformDividedRatio())); 
            //销售县域提点比例
            goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
            //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
            goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));

            BigDecimal oneHundred = new BigDecimal(100);
            //销售县域佣金比例(平台比率 * 县域比率）
            BigDecimal areaCommissionRatio = new BigDecimal(orderInfo.getAreaCommissionRatio ())
                    .multiply(new BigDecimal (orderInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            goodsDetail.setSaleAreaCommissionRatio(areaCommissionRatio);

            //销售网点提点比例 （县域零售、全国零售时提供）（平台比率*网点比率）
            BigDecimal storeCommissionRatio = new BigDecimal(orderInfo.getStoreCommissionRatio ())
                    .multiply(new BigDecimal (orderInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            goodsDetail.setSaleStoreCommissionRatio (storeCommissionRatio); 
            totalMoney = orderInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ();
            //退票退款，按照实际退的金额为准
            goodsDetail.setRefundMoney (orderInfo.getRealAmount());
            
        }else{
            //退票退款，按照实际退的金额为准
            goodsDetail.setRefundMoney (chargeRefund.getRealAmount());
            //佣金比例
            goodsDetail.setCommissionRatio (new BigDecimal (0));
            //平台提点比例
            goodsDetail.setPlatformTidianRatio (new BigDecimal (0));
            //销售县域提点比例
            goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
            //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
            goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));
            //销售县域佣金比例
            goodsDetail.setSaleAreaCommissionRatio (new BigDecimal (0)); 
            //销售网点提点比例 （县域零售、全国零售时提供） 
            goodsDetail.setSaleStoreCommissionRatio (new BigDecimal (0));

            //退款总金额
            totalMoney = chargeRefund.getRealAmount().multiply (new BigDecimal (100)).longValue ();
        }


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
            logger.info (">>>>>>>>>>>>>>>HCP-调用结算接口orderCode:{},refundMoneyV1()：{}",orderInfo.getHcpOrderCode(),JSONObject.toJSONString (orderRefundParam));
            fundsAccept = orderRefundService.requestOrderRefundV1(result, orderRefundParam);
            logger.info (">>>>>>>>>>>>>>>>HCP-调用结算接口orderCode:{},refundMoneyV1()：{}",orderInfo.getHcpOrderCode(),JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfo.getHcpOrderCode(), "HCPPayService.refundMoneyV1()", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算");
        } catch (Exception e) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HCPPayService.refundMoneyV1()", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败");
            logger.error ("XXXXXXXXXXXXXXXXHCP-请求账户系统退款和收回分润结算失败",e);
            throw new FunctionException (result, "请求账户系统退款和收回分润结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HCPPayService.refundMoneyV1()", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("XXXXXXXXXXXXXXXXHCP-请求退款和收回分润结算失败");
            throw new FunctionException (result, "请求退款和收回分润结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, orderInfo.getHcpOrderCode (), "HCPPayService.refundMoneyV1()", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("XXXXXXXXXXXXXXXXXHCP-请求退款和收回分润结算重复调用");
            throw new FunctionException (result, "请求退款和收回分润结算重复调用");
        }

    }



    /**
     * 校验会员信息
     * 
     * @param mobile 支付手机号码
     * @param bizType 登录账户类型
     * @param password 支付密码
     * @param userId   登录ID
     * @return
     * @throws FunctionException 
     */
    public MemberResultVo  memberVerification(Result result,String mobile,Integer bizType,String password) throws FunctionException{

        MemberResultVo memberResultVo = new MemberResultVo();

        UserInfo userInfo = result.getUserInfo();
        //获取会员信息
        Map<String, Object> mbrParam = Maps.newHashMap ();
        mbrParam.put ("reqSource", "b2c");
        mbrParam.put ("sysType", 2);
        mbrParam.put ("token", userInfo.getSessionId ());
        mbrParam.put ("mobile", mobile);
        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + FundURLConstants.ACC_MEMEBER_INFO;
        String mbrResponse = HttpUtils.doPost(mbrUrl, mbrParam);

        logger.info("----->获取会员的信息：{}", mbrResponse);

        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }
        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        if (200 != returnDTO.getStatus()) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }

        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);

        if (bizType.equals (RoleType.SHOP.getCode ())) {
            logger.info("----->网点支付密码校验");
            //网点密码验证
            Map<String, Object> storeParam = Maps.newHashMap ();
            storeParam.put ("payPwd",password);
            storeParam.put ("userId", userInfo.getUserId ());
            String url = TshDiamondClient.getInstance ().getConfig ("acc-url") + FundURLConstants.ACC_VALIDATE_OUTPAY_PASSWORD;
            String response = HttpUtils.doPost(url, storeParam);

            if (StringUtils.isEmpty (response)) {
                logger.error ("------>网点密码验证失败");
                throw new FunctionException (result, "密码验证失败");
            }
            Result storeResult = JSONObject.parseObject (response, Result.class);
            if(null == storeResult || null == storeResult.getData()){
                logger.error ("---------->验证密码返回结果为空:" + JSON.toJSONString(response));
                throw new FunctionException (result, "验证密码返回结果为空");
            }
            if (storeResult.getData ()) {
                logger.info ("------>密码验证成功");
            } else {
                logger.error ("---------->密码错误");
                throw new FunctionException (result, "密码错误");
            }

            memberResultVo.setBizId(userInfo.getBizId().intValue());
            memberResultVo.setBizType(RoleType.SHOP.getCode ());
            memberResultVo.setMemberName(memberVo.getMemberName());

        } else if (bizType.equals (RoleType.MEMBER.getCode ())) {
            logger.info("-----> 会员密码校验.");

            String url = TshDiamondClient.getInstance ().getConfig ("mbr-url") +FundURLConstants.ACC_VALIDATE_MEMBERCARD;
            Map<String, Object> param = Maps.newHashMap ();
            param.put ("mobile",mobile);
            param.put ("memberId", memberVo.getId ());
            param.put ("payPassword", password);
            String response = HttpUtils.doPost(url, param);
            if (StringUtils.isEmpty (response)) {
                logger.error ("------->会员密码验证错误");
                throw new FunctionException (result, "密码验证错误");
            }
            JSONObject resultJson = JSONObject.parseObject (response);
            int resultFlag = resultJson.getIntValue ("status");
            String message = resultJson.getString ("msg");
            if (resultFlag == ReturnStatusEnum.OK.getValue ()) {
                logger.info ("------>密码验证成功");
            } else {
                logger.error ("---->密码验证错误：" + message);
                throw new FunctionException (result, message);
            }

            memberResultVo.setBizId(memberVo.getId().intValue());
            memberResultVo.setUserId(memberVo.getUserID().intValue());
            memberResultVo.setBizType(RoleType.MEMBER.getCode ());
            memberResultVo.setUserName(memberVo.getMemberName());
            memberResultVo.setMobiel(mobile);
            memberResultVo.setMemberName(memberVo.getMemberName());
        } else {
            logger.error ("==>账户类型不存在");
            throw new FunctionException (result, "账户类型不存在");
        }

        return memberResultVo;
    }



    private void insertHttpLog(Result result, String orderCode, String receiveMethod, String sendData, String receiveData, Date sentTime, Date receiveTime, String remark) throws Exception {
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
        chargePayHttpLog.setSendMothed ("payChargeInfo");
        //请求对像的方法，如资金服务中心
        chargePayHttpLog.setReceiveMothed (receiveMethod);
        //发送内容
        chargePayHttpLog.setSendData (JSONObject.toJSONString (sendData));
        //收到消息内容
        chargePayHttpLog.setReceiveData (receiveData);
        //业务类型描述，如下单、退款调用
        chargePayHttpLog.setRemark (remark);
        logger.info ("插入日志信息：" + JSONObject.toJSONString (chargePayHttpLog));
        this.chargePayHttpLogDao.insert (result, chargePayHttpLog);
    }

  
}
