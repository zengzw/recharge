/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.metaq.Producer;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.google.common.collect.Lists;
import com.tsh.sfund.api.TicketBizType;
import com.tsh.sfund.api.TicketCreateParam;
import com.tsh.sfund.api.TicketDTO;
import com.tsh.sfund.api.TicketService;
import com.tsh.sfund.dto.FundsAccept;
import com.tsh.sfund.dto.settle.GoodsDetail;
import com.tsh.sfund.dto.settle.OrderSettleParam;
import com.tsh.sfund.dto.settleV1.GoodsDetailV1;
import com.tsh.sfund.dto.settleV1.OrderSettleParamV1;
import com.tsh.sfund.settle.OrderSettleService;
import com.tsh.vas.commoms.queue.DBLogQueue;
import com.tsh.vas.commoms.utils.PayLogBuilder;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.iservice.IPaySettleService;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.MQTopicConstants;

/**
 * 
 * 结算服务实现类
 * 
 * 
 * @author zengzw
 * @date 2017年5月31日
 */
@Service
public class PhonePaySettleServiceImpl implements IPaySettleService{

    private static final Logger logger = LoggerFactory.getLogger(PhonePaySettleServiceImpl.class);

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PhoneOrderInfoService orderInfoService;

    @Autowired
    private SupplierInfoDao supplierInfoDao;


    @Autowired
    private BusinessInfoDao businessInfoDao;


    /**
     * 订单结算接口
     */
    @Autowired
    private OrderSettleService orderSettleService;
    
    


    private DBLogQueue<ChargePayHttpLog> logQueue = new DBLogQueue<>();

    
    
    


    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @Override
    public Result paySettle(Result result, String orderCode) throws Exception {
        logger.info("#-->订单号：{} 开始结算",orderCode);

        PhoneOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, orderCode).getData();
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfo.getId ());
        ticketCreateParam.setBizOrderNo (orderInfo.getPhoneOrderCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationPeriodSettle.getKey ());
        ticketCreateParam.setUserId (orderInfo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            logger.info("获取访问令牌信息：{}",JSONObject.toJSONString (ticketCreateParam));

            ticketDTO = ticketService.builderTicket (ticketCreateParam);

            logger.info("获取访问令牌信息：" + ticketDTO);
        } catch (Exception ex) {
            logger.error ("获取ticketId异常",ex);
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }
        //----2.调用结算接口
        OrderSettleParam orderSettleParam = new OrderSettleParam ();
        //请求操作用户Id.
        orderSettleParam.setOperateUserId (orderInfo.getPayUserId ());
        //令牌id.
        orderSettleParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderSettleParam.setOrderId (orderInfo.getId ());

        //业务单编号（业务的唯一编号）.
        orderSettleParam.setBizOrderNo (orderInfo.getPhoneOrderCode());
        //总单号
        orderSettleParam.setTotalOrderNo(orderInfo.getPhoneOrderCode());
        //业务简述.
        orderSettleParam.setBizIntro ("增值服务结算记录");
        //业务详情.
        orderSettleParam.setBizDetails ("增值服务结算");
        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + PhoneConstants.ORDER_DETAIL+"?orderCode=" + orderInfo.getPhoneOrderCode();
        orderSettleParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空(消息标识未由账号定义是考虑：外部传递是解决多个订阅者能够灵活进行消息隔离).
        orderSettleParam.setMsgTopic ("");
        //关联原锁定金额的业务单编号.
        orderSettleParam.setOffsetBizOrderNo (orderInfo.getPhoneOrderCode());
        //订单类型, 1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderSettleParam.setOrderType (6);
        //订单应付金额
        orderSettleParam.setOrderMoney (orderInfo.getOriginalAmount ());
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, orderInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderSettleParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderSettleParam.setProxySupplierId (null);
        //供应商账期
        orderSettleParam.setSupplierPaymentDays (Integer.parseInt(orderInfo.getBillYearMonth()));
        //分润账期
        orderSettleParam.setProfitPaymentDays (Integer.parseInt(orderInfo.getBillYearMonth()));


        //物流费
        orderSettleParam.setLogisticsMoney (new BigDecimal (0));
        //支付者用户ID
        orderSettleParam.setPayerUserId (orderInfo.getPayUserId ());
        //销售县域Id
        orderSettleParam.setSaleAreaId (Long.parseLong (orderInfo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderSettleParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderSettleParam.setSaleStoreId (Long.valueOf (orderInfo.getStoreCode ()));
        //结算商品明细
        List<GoodsDetail> goodsDetails = Lists.newArrayList ();
        GoodsDetail goodsDetail = new GoodsDetail ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (orderInfo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, orderInfo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (businessInfo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (businessInfo.getBusinessName ());

        //商品价格（销售价/批发价）
        goodsDetail.setGoodsPrice (orderInfo.getOriginalAmount());

        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (orderInfo.getCostingAmount ());
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
        goodsDetail.setCommissionRatio (new BigDecimal (0));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (new BigDecimal(orderInfo.getPlatformDividedRatio()));
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));

        BigDecimal oneHundred = new BigDecimal(100);
        //销售县域佣金比例(平台比率 * 县域比率）  TODO
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
        
        //质量原因退货时间
        goodsDetail.setQualityReturnDays (Integer.parseInt(orderInfo.getBillYearMonth()));
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (Integer.parseInt(orderInfo.getBillYearMonth()));
        goodsDetails.add (goodsDetail);
        orderSettleParam.setGoodsDetails (goodsDetails);

        FundsAccept fundsAccept = null;
        Date sendTime1 = new Date ();
        Date receiveTime1;
        try {
            logger.info ("调用结算接口orderSettleService.orderSettle()：" + JSONObject.toJSONString (orderSettleParam));
            fundsAccept = this.orderSettleService.orderSettle (result, orderSettleParam);
            logger.info ("调用结算接口orderSettleService.orderSettle()结果：" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算")
                    );

            
        } catch (Exception e) {
            receiveTime1 = new Date ();
           
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算失败")
                    );
            
            logger.error ("请求账户系统结算失败");
            throw new FunctionException (result, "请求账户系统结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算失败")
                    );
            
            
            logger.error ("请求结算调用失败");
            throw new FunctionException (result, "请求结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算失败")
                    );
            
            
            logger.error ("请求结算重复调用");
            throw new FunctionException (result, "请求结算重复调用");
        }
        return result;
    }



    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @Override
    public Result paySettleV1(Result result, PhoneOrderInfoPo orderInfoPo) throws Exception {
        logger.info("#paySettleV1--> 开始生产结算记录......openPlatformNo:"+JSON.toJSONString(orderInfoPo));

        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (orderInfoPo.getId ());
        ticketCreateParam.setBizOrderNo (orderInfoPo.getPhoneOrderCode());
        ticketCreateParam.setBizType (TicketBizType.AppreciationPeriodSettle.getKey ());
        ticketCreateParam.setUserId (orderInfoPo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
            logger.info("#phone --> 获取访问令牌信息：" + ticketDTO);
        } catch (Exception ex) {
            logger.warn ("#phone -->获取ticketId异常，已经解锁过了" + JSONObject.toJSONString (ticketCreateParam),ex);
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
        orderSettleParam.setTotalOrderNo(orderInfoPo.getPhoneOrderCode());

        //业务单编号（业务的唯一编号）.
        orderSettleParam.setBizOrderNo (orderInfoPo.getPhoneOrderCode());
        //业务简述.
        orderSettleParam.setBizIntro ("增值服话费结算记录");
        //业务详情.
        orderSettleParam.setBizDetails ("增值服务话费结算");
        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + PhoneConstants.ORDER_DETAIL +"?orderCode=" + orderInfoPo.getPhoneOrderCode();
        orderSettleParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空(消息标识未由账号定义是考虑：外部传递是解决多个订阅者能够灵活进行消息隔离).
        orderSettleParam.setMsgTopic ("");
        //关联原锁定金额的业务单编号.
        orderSettleParam.setOffsetBizOrderNo (orderInfoPo.getPhoneOrderCode());
        //订单类型,    1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点   TODO
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
        goodsDetail.setSaleGoodsPrice(orderInfoPo.getOriginalAmount());
        //原销售价(秒杀/限时折扣前的价格)，如果没有秒杀/限时折扣活动
        goodsDetail.setOriginalSaleGoodsPrice(orderInfoPo.getOriginalAmount());
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
        goodsDetail.setCommissionRatio (new BigDecimal (0));
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
            logger.info ("#settle1---->Phone调用结算接口orderSettleService.orderSettle()：" + JSONObject.toJSONString (orderSettleParam));
            fundsAccept = this.orderSettleService.orderSettleV1(result, lstOrderSettleParam);
            logger.info ("#settle1---->Phone调用结算接口orderSettleService.orderSettle()结果：" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();

            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfoPo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算")
                    );

        } catch (Exception e) {
            receiveTime1 = new Date ();
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfoPo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求账户系统结算失败")
                    );

            logger.error ("#settle1----->Phone请求账户系统结算失败",e);
            throw new FunctionException (result, "请求账户系统结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfoPo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求结算调用失败")
                    );
            
            logger.error ("#settle1---->Phone请求结算调用失败");
            throw new FunctionException (result, "请求结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            logQueue.off(
                    PayLogBuilder.buildPayLog(result, orderInfoPo.getPhoneOrderCode(), 
                    "PhonePaySettleServiceImpl.orderSettle", 
                    JSONObject.toJSONString (orderSettleParam), 
                    JSONObject.toJSONString (fundsAccept),
                    sendTime1, 
                    receiveTime1, 
                    "请求结算调用失败")
                    );

            logger.error ("#settle1----->Phone请求结算重复调用");
            throw new FunctionException (result, "请求结算重复调用");
        }

        logger.info("#settle1----->Phone完成生产结算记录......openPlatformNo:"+JSON.toJSONString(orderInfoPo));
        return result;
    }



    @Override
    public Result confirmSettle(Result result, String openPlatformNo, boolean immediatelySettle) throws Exception {

        logger.info("#confirmSettle----->Phone PhonePaySettleServiceImpl.confirmSettle()确认结算接口请求.openPlatformNo:"+openPlatformNo);

        PhoneOrderInfoPo orderInfoPo = this.orderInfoService.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        if(orderInfoPo == null){
            logger.info("#confirmSettle---->Phone PhonePaySettleServiceImpl.confirmSettle() 订单信息为空");
            result.setStatus(HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }


        //判断状态。只有交易成功，购票失败，出票失败才确认结算
        int pageStatus = orderInfoPo.getPayStatus().intValue();
        if(pageStatus!= EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()
                && pageStatus != EnumOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue()
                && pageStatus != EnumOrderInfoPayStatus.TICKET_FAIL.getCode().intValue()){
            logger.info("#confirmSettle------>orderCode:{}当前订单状态不是”交易成功“，不做处理！status:{}",orderInfoPo.getPhoneOrderCode(),pageStatus);
            result.setStatus(HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }


        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId",orderInfoPo.getId());
        paramsMap.put("orderNo",orderInfoPo.getPhoneOrderCode());
        paramsMap.put("immediatelySettle",immediatelySettle);
        Date begin = new Date();
        String message = JSON.toJSONString(paramsMap);
        logger.info("#confirmSettle----->confirmSettle 通知确认结算MQ消息请求参数："+message);

        //发送MQ消息
        boolean flag = Producer.getInstance().producerRun(MQTopicConstants.MQ_CONFIRM_SETTLE,message, null);

        logger.info("#confirmSettle------>confirmSettle 通知确认结算MQ消息请求结果：{}",   flag);

        logQueue.off(
                PayLogBuilder.buildPayLog(result, orderInfoPo.getPhoneOrderCode(), 
                        MQTopicConstants.MQ_CONFIRM_SETTLE, 
                        message, 
                        flag+"", 
                        begin, 
                        new Date(), 
                        "确认结算接口")
                );

        result.setStatus((flag?HttpResponseConstants.SUCCESS:HttpResponseConstants.ERROR));
        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }

}
