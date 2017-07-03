package com.tsh.vas.phone.facade;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.redis.lock.IRedisLock;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.bis.api.SupplierApi;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.dubbo.bis.vo.SupplierVO;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;
import com.tsh.share.vo.AreaVo;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.commoms.utils.PhoneUtils;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.iservice.IMemberService;
import com.tsh.vas.iservice.IPayRefundService;
import com.tsh.vas.iservice.IPaySettleService;
import com.tsh.vas.model.BusinessStoreShare;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.ModuleAuthorityPo;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.model.phone.PhoneGoodsPo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhonePaymentRecordPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;
import com.tsh.vas.model.phone.PhoneValuePo;
import com.tsh.vas.model.phone.VasPhoneLotteryRecordPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.constants.SMSPhoneMessageTemplateConstants;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.enums.EnumActivetyRewardStatus;
import com.tsh.vas.phone.enums.EnumLotteryType;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumPhoneRefundOrderStatus;
import com.tsh.vas.phone.enums.EnumPhoneRefundType;
import com.tsh.vas.phone.enums.EnumRequestOrderType;
import com.tsh.vas.phone.enums.EnumServiceProviceType;
import com.tsh.vas.phone.manager.CouponComparetor;
import com.tsh.vas.phone.manager.GXOrderInfoMangerService;
import com.tsh.vas.phone.manager.OrderInfoMangerStrategy;
import com.tsh.vas.phone.manager.TSHOrderInfoManagerService;
import com.tsh.vas.phone.service.APIPhoneBrokerService;
import com.tsh.vas.phone.service.ModuleAuthorityService;
import com.tsh.vas.phone.service.PhoneGoodsService;
import com.tsh.vas.phone.service.PhoneLotteryRecordService;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.phone.service.PhonePaymentRecordService;
import com.tsh.vas.phone.service.PhoneRefundAmountService;
import com.tsh.vas.phone.service.PhoneUseCouponRecordService;
import com.tsh.vas.phone.service.PhoneValueService;
import com.tsh.vas.phone.service.VasPhoneOneyuanFreeService;
import com.tsh.vas.phone.vo.APIPhoneSegmentVo;
import com.tsh.vas.phone.vo.ReqQueryActivityOrderListParams;
import com.tsh.vas.phone.vo.ReqQueryOrderListParams;
import com.tsh.vas.sdm.service.common.BusinessStoreShareService;
import com.tsh.vas.sdm.service.supplier.SupplierService;
import com.tsh.vas.service.ChargePayHttpLogService;
import com.tsh.vas.service.CommomService;
import com.tsh.vas.service.CouponService;
import com.tsh.vas.service.DubboAddressService;
import com.tsh.vas.service.ShopApiService;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.vo.charge.PrintChargeVo;
import com.tsh.vas.vo.coupon.QueryCouponParams;
import com.tsh.vas.vo.phone.MemberVo;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;
import com.tsh.vas.vo.phone.api.APIPhoneValue;




/**
 * 话费充值 API service类
 * 
 *
 * @author zengzw
 * @date 2017年3月2日
 */

@Service
public class APIAppPhoneService {

    private final static Logger logger = LoggerFactory.getLogger(APIAppPhoneService.class);

    /**
     * 好慢段限制 key
     */
    private final static String LIMIT_SEGMENT = "limit_phone_segment";


    /**
     * 全部网点
     */
    private final static String allStore = "1";



    /**
     * broker 服务类
     *
     */
    @Autowired
    private APIPhoneBrokerService brokerService;

    /**
     * 话费订单服务类
     *
     */
    @Autowired
    private PhoneOrderInfoService orderInfoService;

    @Autowired
    CommomService commomService;

    @Autowired
    PhonePaymentRecordService paymentRecordService;




    @Autowired
    SupplierService supplierService;

    @Autowired
    ShopApiService shopApiService;


    @Autowired
    private BusinessStoreShareService businessStoreShareService;

    @Autowired
    private ChargePayHttpLogService chargePayHttpLogService;

    @Autowired
    private SupplierApi supplierApi;


    /**
     * 退款接口
     */
    @Autowired
    private IPayRefundService refundService;


    /**
     * 结算接口
     */
    @Autowired
    private IPaySettleService settleService;

    /**
     * 面值Dao
     */
    @Autowired PhoneValueService phoneValueService;

    @Autowired DubboAddressService addressService;

    @Autowired PhoneGoodsService phoneGoodsService;


    @Autowired
    QueueRegisterSupplier queueRegisterSupplier;

    /**
     * 退款dao
     */
    @Autowired
    PhoneRefundAmountService refundAmountService;


    @Autowired
    private SmsService smsService;


    /**
     * 活动服务
     *
     */
    @Autowired
    private VasPhoneOneyuanFreeService activityService;

    /**
     * 活动权限设置
     */
    @Autowired
    private ModuleAuthorityService authorityService;


    @Autowired
    TSHOrderInfoManagerService tshOrderInfoService;

    @Autowired
    GXOrderInfoMangerService gxOrderInfoService;


    @Autowired
    private PhoneLotteryRecordService lotteryRecordService;



    @Autowired
    CouponService couponService;

    @Autowired
    PhoneUseCouponRecordService orderCouponService;

    /**
     * 分布式锁
     */
    @Autowired
    private IRedisLock lock;
    
    
    @Autowired
    IMemberService memberService;

    /**
     * 锁等待时间,ms
     */
    private final static long LOCK_TIME = 50 * 1000;

    /**
     * 锁，前缀
     */
    private  static String LOCK_PREFIX ="vas_lock_";





    /**
     * 查看手机归属地
     *
     * @param mobileNum 手机号码
     * @return  Result
     */
    public Result queryPhoneLocation(Result result,String mobileNum){
        brokerService.queryPhoneLocation(result, mobileNum);

        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            //根据归属地，转换位我们的内部地址库ID
            PhoneLocationVo phoneLocationVo = result.getData();
            //获取营运商编号
            String providerCode = PhoneUtils.getServiceProviderCode(phoneLocationVo.getType());
            phoneLocationVo.setSubBusinessCode(providerCode);

            result.setData(phoneLocationVo);
            return result;
        }

        return result;

    }




    /**
     * 根据订单号，查询订单状态
     *
     * @param  orderNo  外部订单号
     * @return  Result
     */
    //    @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
    public Result queryRechargeOrderInfo(Result result,String orderCode){
        //根据订单号，查询当前所属的供应商编码号
        PhoneOrderInfoPo orderInfoPo = orderInfoService.queryByOrderCode(result, orderCode).getData();
        if(orderInfoPo == null){
            logger.info("#--recharge 充值订单对象为空。订单号：{}",orderCode);
            throw new BusinessRuntimeException(HttpResponseConstants.ERROR+"","获取订单对象为空");
        }

        String supplierCode = orderInfoPo.getSupplierCode();
        try {
            ServiceRegisterVo serviceRegisterVo =  queueRegisterSupplier.getServiceRegisterVo(result, supplierCode);
            brokerService.queryOrderInfo(result,orderInfoPo.getOpenPlatformNo(),serviceRegisterVo);
            if(result.getStatus() == HttpResponseConstants.SUCCESS){
                result.setData(JSON.parseObject(result.getData().toString(),OrderInfoVo.class));
                return result;
            }


        } catch (Exception e) {
            logger.error("#-- 获取注册服务商出错",e);
        }

        result.setData(null);
        result.setStatus(HttpResponseConstants.ERROR);
        return result;

    }

    /**
     * 订单支付
     *
     * @param result
     * @param orderInfoVo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=FunctionException.class)
    public Result payGXOdrerInfo(Result result, PhoneOrderInfoVo orderInfoVo) throws Exception {
        logger.info("#GX-->【1】检查参数合法性");
        //校验当前商品是否可用，或者销售价是否一致。（页面长时间的停留，会导致某些问题）
        checkAvailableItem(result, orderInfoVo);

        //根据策略完成下单，支付、结果构建
        OrderInfoMangerStrategy strategy = new OrderInfoMangerStrategy(gxOrderInfoService);
        strategy.operateion(result, orderInfoVo);

        return result;

    }

    /**
     * 订单支付
     *
     * @param result
     * @param orderInfoVo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=FunctionException.class)
    public Result payOdrerInfo(Result result, PhoneOrderInfoVo orderInfoVo) throws Exception {
        logger.info("#-->【1】检查参数合法性");
        //校验当前商品是否可用，或者销售价是否一致。（页面长时间的停留，会导致某些问题）
        checkAvailableItem(result, orderInfoVo);

        OrderInfoMangerStrategy strategy = new OrderInfoMangerStrategy(tshOrderInfoService);
        strategy.operateion(result, orderInfoVo);

        return result;

    }




    /*
     * 添加 参与订活动订单
     */
    public void addActivityOrder(Result result,PhoneOrderInfoPo phoneOrderInfoPo) throws FunctionException{
        //判断活动是否到期
        ModuleAuthorityPo isExpire =  authorityService.queryPhoneActivityByAreaId(result, result.getUserInfo().getBizId()).getData();
        if(isExpire == null){
            throw new FunctionException(result, "对不起，话费充值活动已经结束");
        }

        //保存活动信息
        VasPhoneOneyuanFreeVo activety = new VasPhoneOneyuanFreeVo();
        activety.setOrderCode(phoneOrderInfoPo.getPhoneOrderCode());
        activety.setChargeAmount(phoneOrderInfoPo.getSaleAmount());
        activety.setChargeMobile(phoneOrderInfoPo.getRechargePhone());
        activety.setActivityAmount(phoneOrderInfoPo.getActivetyMoeny());
        activety.setCreateTime(new Date());
        activety.setCheckStatus(EnumActivetyAuditStatus.DEFAULT.getType());
        activety.setLotteryStatus(EnumActivetyRewardStatus.DEFAULT.getType());
        activety.setBizType(EnumBusinessCode.MPCZ.getBusinessName());
        activety.setAreaId(Integer.parseInt(phoneOrderInfoPo.getCountryCode()));
        activety.setAreaName(phoneOrderInfoPo.getCountryName());
        activety.setStoreId(Integer.parseInt(phoneOrderInfoPo.getStoreCode()));
        activety.setStoreName(phoneOrderInfoPo.getStoreName());

        VasPhoneOneyuanFreePo vasPhone =  activityService.addVasPhoneOneyuanFree(result, activety).getData();

        logger.info("-> 添加参与活动对象：{}",JSON.toJSONString(activety));

        //修改幸运号
        activityService.updateLuckNumById(result,vasPhone.getId(),PhoneUtils.generateLuckNumber(vasPhone.getId()));

    }



    /**
     *
     * @param result
     * @param orderInfoVo
     * @throws Exception
     */
    private void checkAvailableItem(Result result, PhoneOrderInfoVo orderInfoVo) throws Exception {
        //校验当前商品是否已经下架，或者价格是否已经调整。
        PhoneGoodsPo phoneGoodsPo = phoneGoodsService.getAvailablePhoneGoodsById(result,Long.parseLong(orderInfoVo.getGoodsId())).getData();
        if(phoneGoodsPo == null){
            throw new BusinessRuntimeException("","当前商品已经下架！");
        }

        if(phoneGoodsPo.getSaleAmount().compareTo(orderInfoVo.getOriginalAmount()) != 0){
            throw new BusinessRuntimeException("", "销售价格已经发生变更！");
        }
    }





    /**
     * 账号支付成功后回调，新增支付信息，继续充值接口
     *
     * @param result
     * @param mqBizOrderPay
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result paySuccessCallBack(Result result, MQBizOrderPay mqBizOrderPay){
        PhoneOrderInfoPo orderInfoPo = this.orderInfoService.queryByOrderCode(result, mqBizOrderPay.getBizOrderNo ()).getData ();
        //订单支付成功后，又返回失败的情况。严格判断状态。
        if (!orderInfoPo.getPayStatus ().equals (EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode ())) {
            logger.warn("#---------Phone支付回调，订单:{} 不是合理状态的单，状态转变,{}",mqBizOrderPay.getBizOrderNo (),orderInfoPo.getPayStatus());
            result.setStatus (HttpResponseConstants.ERROR);
            result.setMsg("当前订单状态不能进行充值");
            result.setData (orderInfoPo);
            return result;
        }

        //添加日志
        this.addPaymentLog(result, mqBizOrderPay, orderInfoPo);
        logger.info("-->【11】订单号：{}，添加支付日志完成.....",mqBizOrderPay.getBizOrderNo ());

        //根据当前网点，获取可用的注册服务商，缓存到队列中。
        String key = PhoneUtils.getStoreRedisKey(orderInfoPo.getStoreCode(),orderInfoPo.getRechargePhone());
        try {
            queueRegisterSupplier.setAvaliableSupplier(key, Long.parseLong(orderInfoPo.getStoreCode()));
        } catch (Exception e) {
            logger.error("-->设置供应商优先级出错,订单号："+mqBizOrderPay.getBizOrderNo (),e);
        }

        logger.info("-->【12】订单号：{},查找设置可用 注册服务商.....",mqBizOrderPay.getBizOrderNo());


        //请求开放平台充值
        this.recharge(result, orderInfoPo.getPhoneOrderCode());
        logger.info ("---->【18】订单号：{}，充值接口下单完成。成功订单状态为 充值中【4】，否则为【7】",mqBizOrderPay.getBizOrderNo ());

        result.setCode (HttpResponseConstants.SUCCESS);
        return result;
    }






    /**
     * 开放平台  充值 完成后回调
     *
     * 如果充值成功时：修改当前订单状态；清除队列。（不清除队列也是可以的，每次设置的时候会先清除）
     * 如果充值失败：
     *         1：不修改订单状态
     *         2：不创建退款单
     *         3:执行轮询机制。如果有供应商，继续充值。如果没有，修改订单状态，创建退款单。
     *
     * 注意：因为这边失败会重试，所以充值失败时得做特殊处理。
     *
     *
     * @param result
     * @return
     */
    public void orderCallBack(Result result, RequestCallbackVo param){
        logger.info("#orderback------->充值回调参数：params:{}",JSON.toJSONString(param));
        if(param == null){
            throw new BusinessRuntimeException("", "RequestCallbackVo 请求参数为空");
        }

        String merchantOrderId =  param.getOrderId();

        if(StringUtils.isEmpty(merchantOrderId)){
            throw new BusinessRuntimeException("", "callback orderId 为空");
        }

        PhoneOrderInfoPo orderInfo = this.orderInfoService.queryByOpenPlatformNo(result, merchantOrderId).getData();
        logger.info("#【19】orderback------->外部订单号:{},获取得到订单信息:{}",merchantOrderId, JSON.toJSONString(orderInfo));

        if(orderInfo == null){
            // 针对于 ”瑞通“ 充值回调的时间差问题。
            //因为是异步，我们充值完后面一系列的动作进行操作（可能耗时在1、2秒之间），测试第三方回调在秒级的话就会存在订单不存在的问题。
            //这边做了5秒的休眠，再查询订单，如果不存在，抛出异常。
            if(merchantOrderId.startsWith("RT")){
                try {
                    Thread.sleep(5000);
                    logger.info("#--Rt callback 订单信息为空,休眠5秒后查询订单信息");
                } catch (InterruptedException e) {
                    logger.error("#==回调sleep异常",e);
                }

                orderInfo = this.orderInfoService.queryByOpenPlatformNo(result, merchantOrderId).getData();
                logger.info("#--Rt callback 订单信息为空,休眠5秒后查询订单信息为：{}",JSON.toJSONString(orderInfo));
            }

            if(orderInfo == null){
                throw new BusinessRuntimeException("", "订单信息为空。"+merchantOrderId);
            }
        }

        logger.info("#【20】orderback------->订单编号:{},回调结果:{}",orderInfo.getPhoneOrderCode(), JSON.toJSONString(param));

        //订单充值成功后，又返回失败的情况。严格判断状态。
        if (!orderInfo.getPayStatus ().equals (EnumPhoneOrderInfoPayStatus.TRADING.getCode())
                || orderInfo.getPayStatus ().equals (EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode ())) {

            logger.info("#orderback------->不合理的状态转换,状态为:{}, orderCode:{}",orderInfo.getPayStatus(), orderInfo.getPhoneOrderCode());

            return ;
        }

        Boolean flag = false;
        //回调返回成功，订单状态改成交易成功
        if (param.getStatus().intValue() == PhoneConstants.RechargeCallBackStatus.SUCCESS) {
            logger.info("#【21】orderback------->orderId:{} 充值成功！",merchantOrderId);
            flag = rechargeSuccessWithBusiness(result,orderInfo);

        }
        else if (param.getStatus().intValue() == PhoneConstants.RechargeCallBackStatus.FAILED
                || param.getStatus().intValue() == PhoneConstants.RechargeCallBackStatus.OTHER) {

            logger.info("#orderback------->orderId:{}充值失败,原因:{}",merchantOrderId,param.getMessage());
            flag = true;
        }
        else if(param.getStatus().intValue() == PhoneConstants.RechargeCallBackStatus.PARTSUCCESS) {
            logger.info("#orderback------->orderId:{}充值回调 部分成功",merchantOrderId);
            flag = true;
        }



        if (flag) {
            logger.info("#【23】orderback---> orderNo:{}, merchantOrderId:{}, callback充值失败，进行轮询重试机制", orderInfo.getPhoneOrderCode(), merchantOrderId);
            //进行轮询重试
            this.recharge(result, orderInfo.getPhoneOrderCode());
        }

    }




    /**
     *  修改 订单状态、清楚供应商队列、结算
     *
     * @param result
     * @param merchantOrderId
     * @param orderInfo
     * @param flag
     * @return
     */
    public Boolean rechargeSuccessWithBusiness(Result result, PhoneOrderInfoPo orderInfo){
        boolean flag = false;
        //订单更改状态,已经成功时间
        this.orderInfoService.updateStatusAndSuccessTime(result, orderInfo.getPhoneOrderCode(),
                EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode (),new Date());

        //修改参加活动状态 ###
        this.updateActivityOrderStatus(orderInfo.getPhoneOrderCode(), EnumActivetyAuditStatus.PASS.getType());

        //清除队列消息
        String key = PhoneUtils.getStoreRedisKey(orderInfo.getStoreCode(), orderInfo.getRechargePhone());
        this.clearQueue(key);

        //调用结算
        try {

            settleService.paySettle(result, orderInfo.getPhoneOrderCode());
            
        } catch (Exception e) {
            flag = true;
            logger.error("#orderback---->orderId:"+orderInfo.getOpenPlatformNo()+" 结算失败",e);
        }finally{
            //中间内部发送错误了，也要调用修改优惠券状态接口（远程），否则无法释放。
            this.updateCouponStatus(result, orderInfo.getPhoneOrderCode(), CouponStatusEnum.USED);
        }

        return flag;
    }


    /**
     *  充值失败
     *
     *  1，修改订单状态
     *  2，创建退款单
     *  3，修改参与活动状态
     *
     * @param result
     * @param merchantOrderId
     * @param orderInfo
     * @param flag
     * @return
     */
    public void rechargeErrorWithBusiness(Result result, PhoneOrderInfoPo orderInfo){
        try{
            orderInfoService.updateStatus(result, orderInfo.getPhoneOrderCode(), EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode());
            logger.info("#--recharge 没有可用供应商,充值失败。订单号：{},修改状态为:【7】",orderInfo.getPhoneOrderCode());

            //生产退款单
            this.createRefundOrder(result, orderInfo.getPhoneOrderCode(), orderInfo.getRealAmount(), EnumPhoneRefundType.RECHARGE_ERROR.getType(),"【充值失败】没有可用供应商");

            //发送短信
            String message = String.format(SMSPhoneMessageTemplateConstants.TRAIN_FAIL,
                    orderInfo.getRechargePhone(),orderInfo.getPhoneOrderCode(),orderInfo.getRealAmount());
            smsService.sendSms(orderInfo.getRechargePhone(), message);

            //修改活动状态
            this.updateActivityOrderStatus(orderInfo.getPhoneOrderCode(), EnumActivetyAuditStatus.NOT_PASS.getType());
        }
        finally{
            //中间内部发送错误了，也要调用修改优惠券状态接口（远程），否则无法释放。
            this.updateCouponStatus(result, orderInfo.getPhoneOrderCode(), CouponStatusEnum.NOT_USE);
        }
    }






    /**
     * @param result
     * @param mqBizOrderPay
     * @param orderInfoPo
     */
    private void addPaymentLog(Result result, MQBizOrderPay mqBizOrderPay, PhoneOrderInfoPo orderInfoPo) {
        try{
            //新增支付日志 
            PhonePaymentRecordPo paymentRecordPo = new PhonePaymentRecordPo();
            paymentRecordPo.setPhoneOrderCode(mqBizOrderPay.getBizOrderNo ());
            paymentRecordPo.setPayWay (orderInfoPo.getBizType ());
            paymentRecordPo.setCreateTime (new Date ());
            paymentRecordPo.setPayAmount(orderInfoPo.getRealAmount());
            paymentRecordPo.setPayType(1);//订单支付

            paymentRecordService.addPhonePaymentRecord(result, paymentRecordPo).getData ();

        }catch(Exception e){
            logger.error("-添加日志错误",e);
        }
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
        this.chargePayHttpLogService.insert (result, chargePayHttpLog);
    }



    /**
     * <h1>手机充值。失败进行轮询</h1>
     *
     *
     * 失败有两个地方：（这里的失败指  ”wmvas <-> 第三方供应商“ 之间的接口调用）
     * <p>
     *  .下单失败 （同步返回），调用轮询 <br>
     *  .充值失败 （异步返回），调用轮询 <br>
     * 
     * <p>
     * 分布式锁：避免并发出现数据异常问题。<br>
     *   注意：为了充值效率，这里不能用全局锁，应该只锁住当前的充值对象。 所以分布式锁的key，不能全局唯一. <br>
     *
     *   key rules : {LOCK_PREFIX} + 网点Id + 充值手机号码。
     *
     * 
     * @param result 放回结果对象
     * @param orderCode  内部订单号
     *
     */
    public synchronized void recharge(Result result,String orderCode){
        //根据订单号，查询当前充值的信息
        PhoneOrderInfoPo orderInfoPo = orderInfoService.queryByOrderCode(result, orderCode).getData();
        if(orderInfoPo == null){
            logger.info("#--recharge 充值订单对象为空。订单号：{}",orderCode);
            throw new BusinessRuntimeException(HttpResponseConstants.ERROR+"","获取订单对象为空");
        }

        String key = PhoneUtils.getStoreRedisKey(orderInfoPo.getStoreCode(),orderInfoPo.getRechargePhone());  //redis可用供应商队列key
        String lockKey = LOCK_PREFIX + key; //锁的key值
        ServiceRegisterVo serviceRegisterVo = null;

        try{
            //分布式阻塞锁
            boolean getLock =  lock.tryLock(lockKey, LOCK_TIME);
            if(getLock){
                logger.info("#-->orderCode:{},key:{},拿到锁，可以进行操作。",orderCode,lockKey);
                //如果当前状态不是充值中，或者是支付成功的，不能进行充值
                if(orderInfoPo.getPayStatus().intValue() != EnumPhoneOrderInfoPayStatus.TRADING.getCode().intValue()
                        && orderInfoPo.getPayStatus().intValue() != EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode().intValue()){
                    logger.info("#--recharge 充值订单状态不是支付成功【3】或充值中【4】，不能进行充值。订单号：{}",orderCode);
                    throw new BusinessRuntimeException(String.valueOf(HttpResponseConstants.ERROR),"订单状态不是支付成功【3】");
                }


                logger.info("#-----【14】recharge 当前充值订单对象数据：{}",JSON.toJSONString(orderInfoPo));

                //从redis 队列中，获取最高优先级的供应商进行充值
                serviceRegisterVo = queueRegisterSupplier.getAvaliableSupplier(key);
                if(serviceRegisterVo == null){
                    this.rechargeErrorWithBusiness(result, orderInfoPo); //修改状态、创建退款单
                    return;
                }


                logger.info("#------【15】recharge 获取可充值供应商为：{}",JSON.toJSONString(serviceRegisterVo));

                //从订单表中，取充值相关信息设置到rechargeVo中, 进行充值调用
                RequestRechargeVo rechargeVo = buildRequestRechargeVo(orderInfoPo);
                Result execResult =  brokerService.recharge(result, rechargeVo,serviceRegisterVo);
                if(execResult.getStatus() == HttpResponseConstants.SUCCESS){
                    RechargeVo rechargeVO = execResult.getData();
                    logger.info("#---下单成功返回结果：{}",JSON.toJSONString(rechargeVO));

                    //下单成功,进行状态的修改
                    this.updateOrderInfoAfterSyncRchargeSuccess(result,orderCode,serviceRegisterVo.getBusinessId(),rechargeVO);

                    logger.info("#--【16】订单号：{}，下单成功。修改订单对象数据。订单状态位：【4】供应商Id：{}",orderCode,serviceRegisterVo.getBusinessId());

                    return;
                }
            }else{
                logger.info("#---> key:{}，拿不到锁不做操作。，订单号:{}",lockKey,orderCode);
                return;
            }

        }finally{
            //操作最后一定要释放锁，否则一直赌赛
            lock.unLock(lockKey);
            logger.info("#-->orderCode:{},key:{}，操作完，释放锁。",orderCode,lockKey);
        }

        
        //下单失败
        logger.info("#--【17】订单号：{}充值下单失败，进行轮询重试。失败供应商对象为：{}",orderCode,JSON.toJSONString(serviceRegisterVo));

        //继续轮询下一个供应商充值，直到最后一个成功或者失败，结束流程
        recharge(result,orderCode);


    }




    /**
     * 取消充值
     *
     *
     * @param  orderNo  内部订单号
     *
     */
    public void reversal(Result result,String orderCode){
        //根据订单号，查询当前充值的信息
        PhoneOrderInfoPo orderInfoPo = orderInfoService.queryByOrderCode(result, orderCode).getData();
        if(orderInfoPo == null){
            logger.info("#--reversal 充值订单对象为空。订单号：{}",orderCode);
            throw new BusinessRuntimeException(HttpResponseConstants.ERROR+"","获取订单对象为空");
        }


        //如果当前状态不是充值中，或者是支付成功的，不能进行充值
        if(orderInfoPo.getPayStatus().intValue() != EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()){
            logger.info("#--reversal 充值订单状态不是充值成功【5】不能进行取消充值。订单号：{}",orderCode);
            throw new BusinessRuntimeException(HttpResponseConstants.ERROR+"","订单状态不是充值成功【5】");
        }


        logger.info("#--reversal 当前取消的充值订单对象数据：{}",JSON.toJSONString(orderInfoPo));


        String supplierCode = orderInfoPo.getSupplierCode();
        ServiceRegisterVo serviceRegisterVo;
        try {
            serviceRegisterVo = queueRegisterSupplier.getServiceRegisterVo(result, supplierCode);

            logger.info("#--reversal 获取可充值供应商为：{}",JSON.toJSONString(serviceRegisterVo));

            //从订单表中，取充值相关信息设置到rechargeVo中, 进行充值调用
            RequestReverseVo rechargeVo = buildRequestReversalVo(orderInfoPo);
            Result execResult =  brokerService.reversal(result, rechargeVo,serviceRegisterVo);
            if(execResult.getStatus() == HttpResponseConstants.SUCCESS){
                //取消订单成功
                logger.info("#--订单号：{}取消充值成功。返回对象：{}",orderCode,JSON.toJSONString(execResult.getData()));
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setMsg("取消订单成功");

            }else{
                //取消订单失败
                logger.info("#--订单号：{}取消充值失败，返回对象：{}",orderCode,JSON.toJSONString(execResult.getData()));
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg(execResult.getMsg());
            }


        } catch (Exception e) {
            logger.error("#------->取消充值失败",e);
        }

    }



    /**
     * @param result
     * @param orderInfoPo
     * @return
     */
    private RequestRechargeVo buildRequestRechargeVo(PhoneOrderInfoPo orderInfoPo) {
        Result result = new Result();
        RequestRechargeVo rechargeVo = new RequestRechargeVo();
        rechargeVo.setMobileNum(orderInfoPo.getRechargePhone());
        rechargeVo.setPrice(orderInfoPo.getSaleAmount().intValue());
        //获取供归属地，这里只针对瑞通供应商。如果为空切恰好是瑞通供应商，逻辑继续走。（放弃瑞通，调用别的供应商）
        PhoneLocationVo phoneLocationVo =  queryPhoneLocation(result, orderInfoPo.getRechargePhone()).getData();
        if(phoneLocationVo != null){
            rechargeVo.setProvinceName(phoneLocationVo.getProvinceName());
            rechargeVo.setSupplierType(phoneLocationVo.getType());
        }

        return rechargeVo;
    }


    /**
     * @param result
     * @param orderInfoPo
     * @return
     */
    private RequestReverseVo buildRequestReversalVo(PhoneOrderInfoPo orderInfoPo) {
        Result result = new Result();
        RequestReverseVo rechargeVo = new RequestReverseVo();
        rechargeVo.setMobileNum(orderInfoPo.getRechargePhone());
        rechargeVo.setPrice(orderInfoPo.getSaleAmount().intValue());
        rechargeVo.setOrderNo(orderInfoPo.getPhoneOrderCode());
        //获取供归属地，这里只针对瑞通供应商。如果为空切恰好是瑞通供应商，逻辑继续走。（放弃瑞通，调用别的供应商）
        PhoneLocationVo phoneLocationVo =  queryPhoneLocation(result, orderInfoPo.getRechargePhone()).getData();
        if(phoneLocationVo != null){
            rechargeVo.setProvinceName(phoneLocationVo.getProvinceName());
            rechargeVo.setSupplierType(phoneLocationVo.getType());
        }

        return rechargeVo;
    }




    /**
     * 创建退款单单
     *
     * @param result
     * @param orderCode 订单编号
     * @param realAmount 退款金额
     * @param type 退款类型
     * @param remark 描述
     */
    public void createRefundOrder(Result result,String orderCode,BigDecimal realAmount,int type,String remark){
        logger.info("#---------> orderId:{}创建退款单",orderCode);

        PhoneRefundAmountPo chargeInfo = refundAmountService.queryByOrderCode(result, orderCode).getData();
        if(null  == chargeInfo){
            PhoneRefundAmountPo phoneRefund =  getChargeRefund(orderCode,realAmount,type,remark);
            phoneRefund = this.refundAmountService.addPhoneRefundAmount(result, phoneRefund).getData ();
            if (phoneRefund.getId () == null) {
                logger.error ("#---------> orderId:{}生成退款记录失败",orderCode);
            }

            logger.info("#--------->orderId:{}创建退款单成功:{}",orderCode,JSON.toJSONString(phoneRefund));
        } else {

            logger.info("#--------->orderId:{} 退款单已经存在！", orderCode);
        }

    }




    /**
     * 增值服务退款（针对支付异常）
     *
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result refundChargeMoney(Result result, String orderCode) {
        logger.info("#refundChargeMoney---->orderCode:{} 进行退款操作。",orderCode);

        PhoneOrderInfoPo orderInfo = this.orderInfoService.queryByOrderCode(result, orderCode).getData ();

        //订单退款,充值失败【7】，支付异常【16】
        if (!orderInfo.getPayStatus ().equals (EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode())
                && !orderInfo.getPayStatus().equals(EnumPhoneOrderInfoPayStatus.PAY_EXCEPTION.getCode()))
        {
            logger.error("#refundChargeMoney---->orderCode:{}当前的状态:{}不允许的退款操作！",orderCode,orderInfo.getPayStatus ());
            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfo);
            return result;
        }


        PhoneRefundAmountPo refundInfo = refundAmountService.queryByOrderCode(result, orderCode).getData();
        //进行退款
        try {
            //设置优惠券信息
            PhoneUseCouponRecordPo orderCoupon = orderCouponService.queryByOrderCode(result, orderCode).getData();
            orderInfo.setOrderCoupon(orderCoupon);
            
            //判断是否使用了优惠券
            VasPhoneOneyuanFreePo activityVo =  activityService.queryByOrderNo(orderInfo.getPhoneOrderCode());
            if(activityVo != null){
                orderInfo.setJoinStatus(PhoneConstants.JOIN_STATUS);
            }
            
            refundService.refundMoney(result, orderInfo, refundInfo);

        } catch (Exception e) {
            logger.error("#refundChargeMoney---->orderCode:{}退款失败了",orderCode);
            logger.error("#refundChargeMoney error:",e);

            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfo);
            return result;
        }

        result.setStatus(HttpResponseConstants.SUCCESS);
        return result;
    }




    public void updateRefundStatusAndTime(Result result,String refundCode,int status,Date refundTime){
        //订单退款状态退款中,更新退款时间
        this.refundAmountService.updateRefundStatusAndTime(result, refundCode, status,new Date());

    }






    /**
     * 创建退款单
     *
     *
     * @param orderNo
     * @param realAmount
     * @param type
     * @param remark
     * @return
     */
    private PhoneRefundAmountPo getChargeRefund(String orderNo,BigDecimal realAmount,int type,String remark){
        String refundCode = "TD-" + orderNo;
        PhoneRefundAmountPo chargeRefund = new PhoneRefundAmountPo();
        chargeRefund.setPhoneOrderCode(orderNo);
        chargeRefund.setRealAmount(realAmount);
        chargeRefund.setRefundAmountCode(refundCode);
        chargeRefund.setRefundType(type);//购票退款
        chargeRefund.setCreateTime(new Date());
        chargeRefund.setStatus(EnumRefundOrderStatus.NON_REFUND.getCode());
        chargeRefund.setRefundTimes(0);
        chargeRefund.setRemark(remark);
        return chargeRefund;
    }




    /**
     * 修改订单状态信息
     *
     * @param result
     * @param orderCode 订单号
     * @param supplierCode 供应商编号
     * @param rechargeVO
     * @throws Exception
     */
    private void updateOrderInfoAfterSyncRchargeSuccess(Result result,String orderCode,String supplierCode, RechargeVo rechargeVO){
        try{
            PhoneOrderInfoPo orderInfo =  orderInfoService.queryByOrderCode(result, orderCode).getData();
            if(null != rechargeVO){
                orderInfo.setOpenPlatformNo(rechargeVO.getOrderNo());
                if(StringUtils.isNotBlank(rechargeVO.getSpOrderNo())){
                    orderInfo.setSupplierOrderId(rechargeVO.getSpOrderNo());
                }
                if(null != rechargeVO.getSellerPrice()){
                    orderInfo.setCostingAmount(BigDecimal.valueOf(rechargeVO.getSellerPrice()));
                }

                //判断是是否参与活动，如果参与活动，供货价  = 供货价 + 活动价
                VasPhoneOneyuanFreePo phoneOneyuanFreePo =  activityService.queryByOrderNo(orderInfo.getPhoneOrderCode());
                if(phoneOneyuanFreePo != null){
                    logger.info("#--> 下单成功，订单号：{}参与活动，当前供货价加1",orderInfo.getPhoneOrderCode());
                    BigDecimal sellerPrice = orderInfo.getCostingAmount().add(BigDecimal.valueOf(phoneOneyuanFreePo.getActivityAmount()));
                    orderInfo.setCostingAmount(sellerPrice);
                }

            }

            //根据供应商编码获取供应商信息
            SupplierInfo  supplierInfo = supplierService.queryBySupplierCode(result, supplierCode).getData();
            orderInfo.setSupplierName(supplierInfo.getCompany());

            // 获取供应商信息Dubbo
            SupplierVO supplierVO = supplierApi.getSupplier(result, supplierInfo.getShopSupplierId()).getData();
            orderInfo.setBillYearMonth(String.valueOf(supplierVO.getRetailPeriod())); //添加账期


            //************ 只有在充值 下单才能确认 分润信息 ***************

            setRatio(result,orderInfo,supplierCode);

            //****************************************

            orderInfo.setSupplierCode(supplierCode);
            orderInfo.setPayStatus(EnumPhoneOrderInfoPayStatus.TRADING.getCode()); //充值中
            orderInfoService.updateOrderInfo(result, orderInfo);

            logger.info("#--下单成功，修改订单数据结果对象为:{}",JSON.toJSONString(orderInfo));

        }catch(Exception e){
            logger.error("修改供应商对象失败",e);
        }
    }


    /**
     * 设置分润
     *
     * @param result
     * @param orderInfo
     * @param supplierCode
     * @throws Exception
     */
    private void setRatio(Result result,PhoneOrderInfoPo orderInfo,String supplierCode) throws Exception{

        /*
         *  屏端app 分润根据后台设置来设置
         */
        if(orderInfo.getSources().equals(EnumRequestOrderType.TSH.name())){
            //根据县域Id获取县域、网点分润信息
            BusinessStoreShare businessStoreShare = this.businessStoreShareService.queryByCountryCodeAndBusinessCode (result, orderInfo.getCountryCode(), orderInfo.getBusinessCode ()).getData ();

            //  根据供应商编码，业务编码获取供应商业务信息（具体平台、县域润润比率）
            SupplierBusiness supplierBusiness = this.supplierService.queryBySupplierCodeAndBusinessCode (result, supplierCode, orderInfo.getBusinessCode ()).getData ();

            //****************** 话费：平台、县域 没有分润.此地方可以不用设置 ***************************
            Double areaCommissionRatio;
            Double storeCommissionRatio;
            if (businessStoreShare != null) {
                areaCommissionRatio = (businessStoreShare.getCountryShareRatio ());
                storeCommissionRatio = (businessStoreShare.getStoreShareRatio ());
            } else {
                //如果网点没有设置分润，网点分润位100%（所有金额都分给网点）
                areaCommissionRatio = 0.00;
                storeCommissionRatio = 100.00;
            }

            orderInfo.setPlatformDividedRatio (supplierBusiness.getPlatformShareRatio ()); //平台分成比率
            orderInfo.setAreaDividedRatio (supplierBusiness.getAreaShareRatio ()); //平台与县域分成比率
            orderInfo.setAreaCommissionRatio (areaCommissionRatio); //县域佣金
            orderInfo.setStoreCommissionRatio (storeCommissionRatio); //网点佣金
        }
        else{
            /*
             * 故乡的分润全部给平台 
             */
            orderInfo.setPlatformDividedRatio(100.00); //平台分成比率
            orderInfo.setAreaDividedRatio(0.00); //平台与县域分成比率
            orderInfo.setAreaCommissionRatio(0.00); //县域佣金
            orderInfo.setStoreCommissionRatio(0.00); //网点佣金
        }
    }



    /**
     * 查询限制号码段
     *
     * @param result
     * @return
     */
    public Result queryLimitPhoneSegment(Result result){
        String strSegment = TshDiamondClient.getInstance().getConfig(LIMIT_SEGMENT);

        List<String> lstResult = new ArrayList<>();
        if(StringUtils.isNotBlank(strSegment)){
            String[] arraySgement = strSegment.split(";");
            for(String segment : arraySgement){
                lstResult.add(segment);
            }
        }

        result.setData(lstResult);
        return result;
    }



    /**
     * 查询进入充值首页，默认显示所有充值面值
     *
     * 默认面值接口：销售价 跟 面值一直
     *
     * @param result
     * @return
     */
    public Result queryListDefaultPhoneValue(Result result){
        //获取所有列表
        List<PhoneValuePo> lstPhoneValues = phoneValueService.getListPhoneValue(result).getData();

        //对象的转换。成为屏端需要的VO
        List<APIPhoneValue> lstResultVo = new ArrayList<>();
        for(PhoneValuePo phone : lstPhoneValues){
            APIPhoneValue apiPhoneValue = new APIPhoneValue();
            apiPhoneValue.setId(phone.getId());
            apiPhoneValue.setValue(phone.getValue());
            apiPhoneValue.setSellPrice(Double.parseDouble(phone.getValue().toString())); //销售价跟面额一直

            lstResultVo.add(apiPhoneValue);
        }

        result.setData(lstResultVo);
        return result;
    }



    /**
     * 查询 当前手机号码所在服务器支持的充值面额
     *
     *
     * @param result
     * @param mobile
     * @return
     */
    public Result queryCurrentMobilePhoneValue(Result result,String mobileNum){
        APIPhoneSegmentVo apiPhoneSegmentVo = new APIPhoneSegmentVo();
        //根据手机号，获取归属地
        brokerService.queryPhoneLocation(result, mobileNum);

        List<APIPhoneValue> lstResult = new ArrayList<>();
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            //根据归属地，转换成我们的内部地址库ID
            PhoneLocationVo phoneLocationVo = result.getData();
            if(null == phoneLocationVo || StringUtils.isEmpty(phoneLocationVo.getProvinceName())
                    || StringUtils.isEmpty(phoneLocationVo.getType())){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("获取归属地数据不完整");
                result.setData("");
                return result;
            }

            //转换成内部地址
            String provinceName = phoneLocationVo.getProvinceName();
            AreaVo areaVo  = addressService.getAreaByTelAreaName(result, provinceName);
            if(areaVo == null){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("获取内部地址库出错");
                result.setData("");
                return result;
            }

            //获取营运商编号
            String providerCode = PhoneUtils.getServiceProviderCode(phoneLocationVo.getType());
            if(StringUtils.isEmpty(providerCode)){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("找不到充值服务商编码");
                result.setData("");
                return result;
            }
            phoneLocationVo.setSubBusinessCode(providerCode);


            Integer areaId = areaVo.getId();
            //根据地址库ID,运营商编号  查询所有面额
            List<PhoneGoodsPo> lstPhoneGoods = phoneGoodsService.queryListGoodsByAreaIdAndProviceCode(result, areaId, providerCode).getData();
            if(CollectionUtils.isNotEmpty(lstPhoneGoods)){
                for(PhoneGoodsPo phoneGoods : lstPhoneGoods){
                    APIPhoneValue apiPhoneValue = new APIPhoneValue();
                    apiPhoneValue.setGoodId(phoneGoods.getId());
                    apiPhoneValue.setValue(phoneGoods.getPhoneValue());
                    apiPhoneValue.setSellPrice(phoneGoods.getSaleAmount().doubleValue());
                    apiPhoneValue.setSubBussinessCode(phoneGoods.getSupplierCode());
                    lstResult.add(apiPhoneValue);
                }
            }

            apiPhoneSegmentVo.setLocationVo(phoneLocationVo);
            apiPhoneSegmentVo.setLstPhoneValue(lstResult);

        }else{
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("获取归属地地址失败");
            result.setData("");
            return result;
        }

        result.setData(HttpResponseConstants.SUCCESS);
        result.setData(apiPhoneSegmentVo);
        return result;
    }




    /**
     * 查询 是否当前网点是否支持 当前业务
     *
     * @param result
     * @param businessCode
     * @return
     * @throws Exception
     */
    public Result getOrganizationInfo(Result result, String businessCode) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        //根据用户id获取网点信息，用户的网点bizId就是网点id
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = shopApiService.getShop (result, storeCode);
        logger.info("获取网点信息是：" + shopVO);

        //供应商编号，对应增值服务的supplierCode;后台根据权重判断获取获取
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierService.findByBusinessCode (result, String.valueOf (shopVO.getAreaId ()), businessCode).getData ();
        logger.info("supplierAreaBusinessDao.findByBusinessCode 返回的list是：" + supplierAreaBusinessList);


        if(CollectionUtils.isEmpty(supplierAreaBusinessList)){
            result.setData("");
            result.setStatus (Result.STATUS_OK);
            result.setMsg("没有可用的供应商");
            return result;
        }

        SupplierAreaBusiness supplierAreaBusiness = supplierAreaBusinessList.get(0);
        result.setData (supplierAreaBusiness);
        result.setStatus (Result.STATUS_OK);
        return result;
    }




    /**
     * 打印支付单接口
     *
     * @param result
     * @return
     * @throws Exception
     */
    public Result printChargeInfo(Result result) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = this.shopApiService.getShop (result, storeCode);
        String memberCard = null;
        String memberName = null;
        BigDecimal memberBalance = null;

        PrintChargeVo printChargeVo = new PrintChargeVo ();

        // 订单类型
        printChargeVo.setChargeTypeStr ("话费充值");
        // 消费热线
        printChargeVo.setConsumerHotline ("400-833-2882");
        // 优惠金额
        printChargeVo.setDiscountAmount (new BigDecimal (0));
        // 加盟店
        printChargeVo.setFranchisedStore (shopVO.getShopName ());
        // 会员卡号
        printChargeVo.setMemberCard (memberCard);
        // 会员
        printChargeVo.setMemberName (memberName);

        printChargeVo.setMemberBalance (memberBalance);
        //门店地址
        printChargeVo.setStoreAddr (shopVO.getContactAddress ());
        // 门店电话
        printChargeVo.setMobile (shopVO.getContactTel ());
        // 请保管
        printChargeVo.setTips ("此小票为业务支付凭据，请妥善保管！");
        // 谢谢惠顾，欢迎下次光临
        printChargeVo.setWelcomes ("谢谢惠顾，欢迎下次光临");

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (printChargeVo);
        return result;
    }





    /**
     * 记录调用日志
     *
     */
    public Result addLog(Result result, String chargeCode, String receiveMethod, String sendData, String receiveData, Date sentTime, Date receiveTime, String remark) throws Exception {
        this.insertHttpLog (result, chargeCode, receiveMethod, sendData, receiveData, sentTime, receiveTime, remark);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData ("成功");
        return result;
    }



    /**
     * 修改订单状态
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateOrderStatus(Result result, String orderCode, Integer payStatus){
        orderInfoService.updateStatus (result, orderCode, payStatus);
        return result;
    }



    /**
     * 清楚队列
     *
     * @param key
     */
    public void clearQueue(String key){
        queueRegisterSupplier.cleanQueue(key);
    }


    /**
     * 获取订单列表
     *
     * @param result
     * @param queryOrderParam 订单参数
     *
     * @return List PhoneOrderInfoVo
     */
    public Result queryOrderList(Result result, ReqQueryOrderListParams queryOrderParam){
        //组装条件
        UserInfo userInfo = result.getUserInfo();
        PhoneOrderInfoVo params = new PhoneOrderInfoVo();
        params.setStoreCode(userInfo.getBizId().toString());
        params.setPage((queryOrderParam.getPage().intValue() - 1 ) * queryOrderParam.getRows().intValue());
        params.setRows(queryOrderParam.getRows().intValue());
        params.setSearchInfo(queryOrderParam.getSearchInfo());
        String statusIn = buildStatusCondition(queryOrderParam.getType());
        params.setStatusIn(statusIn);
        params.setSources(EnumRequestOrderType.TSH.toString());

        //根据网点信息，已经查询条件查询订单列表
        List<PhoneOrderInfoPo> lstPhoneOrders = orderInfoService.queryOrderInfoListPage(result, params).getData();
        List<PhoneOrderInfoVo> lstResult = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(lstPhoneOrders)){
            for(PhoneOrderInfoPo orderInfo : lstPhoneOrders){
                PhoneOrderInfoVo pov = new PhoneOrderInfoVo();
                BeanUtils.copyProperties(orderInfo,pov);

                //设置状态
                buildAppStatusInfo(orderInfo,pov);

                lstResult.add(pov);
            }
        }

        result.setData(lstResult);
        return result;
    }

//    /**
//     *
//     * 查询优惠券列表
//     *
//     * @param result
//     * @param queryCouponListParam
//     * @return
//     */
//    public Result queryCouponList(Result result, QueryCouponListParams queryCouponListParam){
//        List<PhoneCouponVo> couponList = new ArrayList<>();
//
//        PhoneCouponVo couponVo1 = new PhoneCouponVo();
//        couponVo1.setRecordID(1);
//        couponVo1.setMoney("5");
//        couponVo1.setUseBeginTime("2017-07-01");
//        couponVo1.setUseEndTime("2017-08-01");
//        couponVo1.setUseScope("全场使用");
//        couponList.add(couponVo1);
//
//        PhoneCouponVo couponVo2 = new PhoneCouponVo();
//        couponVo2.setRecordID(2);
//        couponVo2.setMoney("3");
//        couponVo2.setUseBeginTime("2017-06-28");
//        couponVo2.setUseEndTime("2017-08-28");
//        couponVo2.setUseScope("全场使用");
//        couponList.add(couponVo2);
//
//
//        result.setData(couponList);
//        return result;
//    }


    /**
     *  根据查找类型 构建查询状态in
     *
     * @param queryOrderParam
     */
    private String buildStatusCondition(String type) {
        List<Integer> statusList = new ArrayList<Integer>();
        if(StringUtils.isNotEmpty(type)){
            switch (type){
            case "1"://等待结果列表
                statusList.add(EnumPhoneOrderInfoPayStatus.NON_PAYMENT.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.PAIDING.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.TRADING.getCode());
                break;
            case "2"://已成功列表
                statusList.add(EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode());
                break;
            case "3"://已失败列表
                statusList.add(EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode());
                break;
            }
        }

        return com.tsh.phone.util.StringUtils.collectionToDelimitedString(statusList, ",");
    }

    /**
     *
     * @param type
     * @return
     */
    private String buildStatusConditionForGX(String type) {
        List<Integer> statusList = new ArrayList<Integer>();
        if(StringUtils.isNotEmpty(type)){
            switch (type){
            case "1"://等待结果列表
                statusList.add(EnumPhoneOrderInfoPayStatus.NON_PAYMENT.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.TRADING.getCode());
                break;
            case "2"://已成功列表
                statusList.add(EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode());
                break;
            case "3"://已失败列表
                statusList.add(EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode());
                statusList.add(EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode());
                break;
            }
        }

        return com.tsh.phone.util.StringUtils.collectionToDelimitedString(statusList, ",");
    }


    /**
     *  构造屏端 状态显示信息
     *
     * @param orderInfo
     * @return
     */
    private void buildAppStatusInfo(PhoneOrderInfoPo orderInfo,PhoneOrderInfoVo orderSet){
        Result result = new Result();
        //支付状态
        String payStatusStr = EnumPhoneOrderInfoPayStatus.getEnume(orderInfo.getPayStatus()).getClientDesc();
        orderSet.setPayStatusStr(payStatusStr);

        //只有充值失败的时候，才去退款表中查询数据.退款状态
        if(orderInfo.getPayStatus().intValue() == EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue()){
            PhoneRefundAmountPo refund = refundAmountService.queryByOrderCode(result, orderInfo.getPhoneOrderCode()).getData();
            if(refund != null){
                String refundStatusMsg = EnumPhoneRefundOrderStatus.getEnume(refund.getStatus()).getDesc();
                orderSet.setRefundStatusStr(refundStatusMsg);
            }
        }

    }


    /**
     * 获取订单列表
     *
     * @param result
     * @param queryOrderParam 订单参数
     *
     * @return List PhoneOrderInfoVo
     */
    public Result queryOrderDetail(Result result, String orderCode){
        //根据网点信息，已经查询条件查询订单列表
        PhoneOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, orderCode).getData();
        if(orderInfo != null){
            PhoneOrderInfoVo pov = new PhoneOrderInfoVo();
            BeanUtils.copyProperties(orderInfo,pov);

            //设置运营商名称
            pov.setSpName(EnumServiceProviceType.getEnume(orderInfo.getSubBusinessCode()).getFullTypeName());

            //设置状态
            buildAppStatusInfo(orderInfo,pov);

            //计算佣金
            countFc(pov);

            // 计算优惠金额 2017-06-22
            BigDecimal discountAmount = orderInfo.getOriginalAmount().subtract(orderInfo.getRealAmount());
            pov.setDiscountAmount(discountAmount.setScale(2));


            VasPhoneOneyuanFreePo oneyuanFreePo = activityService.queryByOrderNo(orderInfo.getPhoneOrderCode());
            if(null != oneyuanFreePo){
                pov.setJoinStatus(1);
                pov.setJoinMoney(oneyuanFreePo.getActivityAmount());
            } else {
                pov.setJoinStatus(0);
                pov.setJoinMoney(0);
            }

            result.setData(pov);
        }

        return result;
    }


    //分成金额计算+提点金额 价格加工
    private void countFc(PhoneOrderInfoVo phoneReportVo){
        //计算平台+县域+网点佣金
        BigDecimal am = new BigDecimal(100);
        //计算分成金额 = 支付金额 - 票价
        BigDecimal fcAmount = BigDecimal.valueOf(0);
        if(null != phoneReportVo.getOriginalAmount() && null != phoneReportVo.getCostingAmount()){
            fcAmount = phoneReportVo.getOriginalAmount().subtract(phoneReportVo.getCostingAmount());
        }


        //平台，县域，网点，分成比例
        BigDecimal platFormRatio = BigDecimal.valueOf(phoneReportVo.getPlatformDividedRatio()).divide(am);
        BigDecimal areaFormRatio =BigDecimal.valueOf(phoneReportVo.getAreaCommissionRatio()).divide(am);
        BigDecimal stormFormRatio = BigDecimal.valueOf(phoneReportVo.getStoreCommissionRatio()).divide(am);

        //平台，县域，网点分成金额
        BigDecimal platformRatioYuan = platFormRatio.multiply(fcAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal areaFormRatioYuan = areaFormRatio.multiply(fcAmount.subtract(platformRatioYuan)).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal stormFormRatioYuan = BigDecimal.valueOf(0);

        if(areaFormRatioYuan.compareTo(new BigDecimal(0)) == 0){
            stormFormRatioYuan = fcAmount.subtract(platformRatioYuan);
        }else {
            stormFormRatioYuan = fcAmount.subtract(platformRatioYuan).subtract(areaFormRatioYuan);
        }

        phoneReportVo.setStoreCommissionRatioStr(stormFormRatioYuan.toString());
    }


    /**
     * 修改活动状态
     *
     * @param orderCode
     * @param status
     */
    public void updateActivityOrderStatus(String orderCode,Integer status){
        VasPhoneOneyuanFreePo phoneOneyuanFreePo = activityService.queryByOrderNo(orderCode);
        if(phoneOneyuanFreePo == null){
            logger.info("#--> 订单号：{}，没有参与活动，不进行任何修改操作！");
            return;
        }

        logger.info("#--> 订单号：{}，参与活动，修改状态位：{}",orderCode,status);

        Result result = new Result();
        Date auditTime = new Date();
        activityService.updateAuditStatus(result,orderCode,auditTime,status);

    }


    /**
     * 获取活动列表
     *
     * @param result
     * @param queryOrderParam 订单参数
     *
     * @return List PhoneOrderInfoVo
     */
    public Result queryActivityOrderList(Result result, ReqQueryActivityOrderListParams queryOrderParam){
        //组装条件
        UserInfo userInfo = result.getUserInfo();
        VasPhoneOneyuanFreeVo params = new VasPhoneOneyuanFreeVo();
        if(queryOrderParam.getType().equals(allStore)){
            //如果不是全部,按照网点查询
            params.setStoreId(userInfo.getBizId().intValue());
        }
        params.setChargeMobile(queryOrderParam.getSearchInfo());
        params.setLotteryStatus(EnumActivetyRewardStatus.PASS.getType()); //中奖状态
        Page page =  new Page<>(queryOrderParam.getPage(),queryOrderParam.getRows());


        Pagination pagination  = activityService.queryVasPhoneOneyuanFreeListByOrder(result, page, params).getData();
        List<VasPhoneOneyuanFreeVo> lstResult = new ArrayList<>();
        if(pagination != null && CollectionUtils.isNotEmpty(pagination.getRows())){
            List<VasPhoneOneyuanFreePo> lstPo= (List<VasPhoneOneyuanFreePo>) pagination.getRows();

            // 对象转换 PO -> VO
            for(VasPhoneOneyuanFreePo order:lstPo){
                VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo = new VasPhoneOneyuanFreeVo();
                BeanUtils.copyProperties(order, vasPhoneOneyuanFreeVo);

                //业务名称
                EnumBusinessCode businessCode = EnumBusinessCode.getBusinessCode(vasPhoneOneyuanFreeVo.getBizType());
                if(businessCode != null){
                    vasPhoneOneyuanFreeVo.setBizType(businessCode.getBusinessName());
                }

                // 开奖类型
                if(StringUtils.isNotBlank(vasPhoneOneyuanFreeVo.getLotteryType())){
                    vasPhoneOneyuanFreeVo.setLotteryTypeName(EnumLotteryType.getEnume(vasPhoneOneyuanFreeVo.getLotteryType()).getName());
                }

                //设置优惠券名称
                if(StringUtils.isNotBlank(order.getLotteryType()) && order.getLotteryType().equals(EnumLotteryType.D.toString())){
                    VasPhoneLotteryRecordPo lotteryRecordPo = lotteryRecordService.queryLotterRecordByOrderNo(result, order.getOrderCode()).getData();
                    if(null != lotteryRecordPo){
                        vasPhoneOneyuanFreeVo.setUseDateRange(lotteryRecordPo.getDateRange());
                        vasPhoneOneyuanFreeVo.setCouponName(lotteryRecordPo.getName());
                    }
                }

                // 手机号处理
                if(StringUtils.isNotBlank(vasPhoneOneyuanFreeVo.getChargeMobile())){
                    vasPhoneOneyuanFreeVo.setChargeMobile(vasPhoneOneyuanFreeVo.getChargeMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
                }
                lstResult.add(vasPhoneOneyuanFreeVo);
            }
        }

        result.setData(lstResult);

        return result;
    }

    /**
     * 查询我的订单列表，只对故乡
     * @param result
     * @param userInfo
     * @param mobile
     * @param page
     * @param pageSize
     * @param type
     * @param openId
     * @return
     */
    public Result queryMyOrderList(Result result, UserInfo userInfo, String mobile, int page, int pageSize, String type, String openId) {
        //组装条件
        PhoneOrderInfoVo params = new PhoneOrderInfoVo();
        //        params.setPayUserId(userInfo.getUserId());
        params.setOpenUserId(openId);
        params.setSources(EnumRequestOrderType.GX.toString());
        if(StringUtils.isNotBlank(mobile)){
            params.setRechargePhone(mobile);
        }
        if(pageSize == 0){
            pageSize = 5;
        }
        if(page == 0){
            page = 1;
        }
        params.setRows(pageSize);
        params.setPage((page-1)*pageSize);
        String statusIn = buildStatusConditionForGX(type);
        params.setStatusIn(statusIn);
        params.setRemark("故乡支付超时，订单关闭");//过滤掉没有支付的订单

        List<PhoneOrderInfoPo> lstPhoneOrders = orderInfoService.queryOrderInfoListPage(result, params).getData();
        List<PhoneOrderInfoVo> lstResult = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(lstPhoneOrders)){
            for(PhoneOrderInfoPo orderInfo : lstPhoneOrders){
                PhoneOrderInfoVo pov = new PhoneOrderInfoVo();
                BeanUtils.copyProperties(orderInfo,pov);

                //设置状态
                buildAppStatusInfo(orderInfo,pov);

                lstResult.add(pov);
            }
        }

        result.setData(lstResult);
        // 返回当前页数
        result.setCode(page);
        return result;
    }


    /**
     * 调用dubbo接口，修改优惠券状态
     * 修改本地优惠券状态
     * 
     * @param result
     * @param orderCode
     * @param status
     * @return
     */
    public Result updateCouponStatus(Result result,String orderCode,CouponStatusEnum status){
        PhoneUseCouponRecordPo orderCoupon = orderCouponService.queryByOrderCode(result, orderCode).getData();
        if(orderCoupon != null){
            try {
                if(StringUtils.isEmpty(orderCoupon.getRecordId())){
                    logger.info("订单号：{}，优惠券Id为空，不做修改",orderCode);
                    return result;
                }
                
                logger.info("#-->修改优惠券信息。优惠券Id:{},状态:{}",orderCoupon.getRecordId(),status.getStatus());
                //修改dubbo第三方优惠券状态
                couponService.updateCouponStatusById(result, Long.parseLong(orderCoupon.getRecordId()), status);

                //修改本地优惠券状态
            } catch (Exception e) {
                logger.error("调用dubbo修改优惠券状态接口失败,orderCode:{}"+orderCode,e);
            }

        }else{
            logger.info("#---> 订单号：{}，没有使用优惠券",orderCode);
        }

        return result;
    }

    
    /**
     * 获取优惠券列表  
     * 
     * 
     * @param result
     * @param mobile 手机号码
     * @param price 销售价
     * @return
     */
    public Result queryListCoupons(Result result,String mobile,String price){
        UserInfo userInfo = result.getUserInfo();
        String orderNo = mobile + "_" + price;
        QueryCouponParams queryParams = new QueryCouponParams();
        queryParams.setMoney(Double.parseDouble(price));
        queryParams.setOrderNo(orderNo);
        queryParams.setShopId(Integer.parseInt(userInfo.getBizId().toString()));
        
        //根据手机号码，获取会员信息，如果存在，则新建。
        try {
            MemberVo memberVo =  memberService.getMemberInfoForApp(mobile, mobile, userInfo.getToken());
            queryParams.setUserId(Integer.parseInt(memberVo.getId().toString()));
        } catch (FunctionException e) {
            logger.error("#--> 获取会员Id出错了",e);
            result.setData("");
            result.setData("获取会员信息出错");
            result.setStatus(HttpResponseConstants.ERROR);
            return result;
        }
        
        //按优惠券到期时间进行升序（快到期的越靠前）
        List<CouponVO> listResult = couponService.getListCoupon(result, queryParams);
        if(CollectionUtils.isNotEmpty(listResult)){
            Collections.sort(listResult,new CouponComparetor());
            for(CouponVO couponVO : listResult){
                couponVO.setUseScope("话费充值");
            }
        }

        result.setData(listResult);
        return result;
    }

}
