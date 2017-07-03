package com.tsh.vas.sdm.service.charge;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.data.metaq.Producer;
import com.dtds.platform.util.HttpUtils;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tsh.broker.enumType.PayResultType;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.utils.SdmRequestSignUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.GenerateOrderNoRequest;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;
import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.sfund.api.TicketBizType;
import com.tsh.sfund.api.TicketCreateParam;
import com.tsh.sfund.api.TicketDTO;
import com.tsh.sfund.api.TicketService;
import com.tsh.sfund.dto.FundsAccept;
import com.tsh.sfund.dto.orderpay.BizOrderPayParam;
import com.tsh.sfund.dto.orderrefundV1.OrderRefundParamV1;
import com.tsh.sfund.dto.settle.GoodsDetail;
import com.tsh.sfund.dto.settle.OrderSettleParam;
import com.tsh.sfund.dto.settleV1.GoodsDetailV1;
import com.tsh.sfund.dto.settleV1.OrderSettleParamV1;
import com.tsh.sfund.orderpay.BizOrderPayService;
import com.tsh.sfund.orderrefund.OrderRefundService;
import com.tsh.sfund.settle.OrderSettleService;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.BusinessStoreShareDao;
import com.tsh.vas.dao.ChargeInfoDao;
import com.tsh.vas.dao.ChargeInfoErrLogDao;
import com.tsh.vas.dao.ChargePayHttpLogDao;
import com.tsh.vas.dao.ChargePaymentInfoDao;
import com.tsh.vas.dao.ChargeRefundDao;
import com.tsh.vas.dao.SupplierAreaBusinessDao;
import com.tsh.vas.dao.SupplierBusinessDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.BusinessCodeConvert;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.BusinessStoreShare;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.ChargeInfoErrLog;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.ChargePaymentInfo;
import com.tsh.vas.model.ChargeRefund;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.service.CommomService;
import com.tsh.vas.service.SmsService;

import com.tsh.vas.utils.GenerateOrderNumber;
import com.tsh.vas.vo.charge.BillInfoVo;
import com.tsh.vas.vo.charge.ChargeInfoVo;
import com.tsh.vas.vo.charge.ChargeOrgVo;
import com.tsh.vas.vo.charge.MemberVo;
import com.tsh.vas.vo.charge.PrintChargeVo;
import com.tsh.vas.vo.charge.RechargeParams;


/**
 * Created by Iritchie.ren on 2016/11/1.
 */
@Service
@SuppressWarnings(value="all")
public class PayChargeService {


    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private ChargeInfoDao chargeInfoDao;

    @Autowired
    private SupplierInfoDao supplierInfoDao;

    @Autowired
    private BusinessInfoDao businessInfoDao;

    @Autowired
    private ChargePaymentInfoDao chargePaymentInfoDao;

    @Autowired
    private ShopApi shopApi;

    @Autowired
    private TicketService ticketService;

    /**
     * 订单支付接口
     */
    @Autowired
    private BizOrderPayService bizOrderPayService;

    /**
     * 订单结算接口
     */
    @Autowired
    private OrderSettleService orderSettleService;

    /**
     * 订单退款+退货Dubbo接口
     */
    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private ChargePayHttpLogDao chargePayHttpLogDao;

    @Autowired
    private SupplierBusinessDao supplierBusinessDao;

    @Autowired
    private BusinessStoreShareDao businessStoreShareDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SupplierAreaBusinessDao supplierAreaBusinessDao;

    @Autowired
    private ChargeRefundDao chargeRefundDao;

    @Autowired
    private ChargeInfoErrLogDao chargeInfoErrLogDao;
    
    /**
     * 公共服务类
     */
    @Autowired(required=false)
    private CommomService commomService;


    /**
     * 确认结算MQ主题
     */
    private final static String MQ_CONFIRM_SETTLE = "FINA-ComfirmOrderSettleTopic";



    /**
     * 查询缴费机构信息
     *
     * @param result
     * @param businessCode
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result getOrganizationInfo(Result result, String businessCode) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        if(null == userInfo){
            result.setData ("");
            result.setStatus (Result.STATUS_OK);
            result.setMsg("无法获取用户信息");
            return result;
        }
        Date timestamp = new Date ();
        //根据用户id获取网点信息，用户的网点bizId就是网点id
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = this.shopApi.getShop (result, storeCode).getData ();
        logger.info("获取网点信息是：" + shopVO);

        //供应商编号，对应增值服务的supplierCode;后台根据权重判断获取获取
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findByBusinessCode (result, String.valueOf (shopVO.getAreaId ()), businessCode).getData ();
        if(null == supplierAreaBusinessList){
            result.setData ("");
            result.setStatus (Result.STATUS_OK);
            result.setMsg("没有可用的供应商1");
            return result;
        }
        int count = supplierAreaBusinessList.size();
        logger.info("supplierAreaBusinessDao.findByBusinessCode 返回的list是：" + supplierAreaBusinessList);

        ChargeOrgVo chargeOrgVo = new ChargeOrgVo ();

        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<ChargeOrgVo.UnitInfo> unitInfos = Lists.newArrayList ();
        String supplierCode = "";
        ServiceRegisterVo serviceRegisterVo = new ServiceRegisterVo();
        String businessKey = "";
        //没有供应商
        if(count <= 0){
            /*chargeOrgVo.setSupplierCode (supplierCode);
            chargeOrgVo.setSupplierToken (businessKey);
            chargeOrgVo.setServerAddr ("");
            chargeOrgVo.setUnitInfos (unitInfos);*/
            result.setData ("");
            result.setStatus (Result.STATUS_OK);
            result.setMsg("没有可用的供应商2");
            return result;
        }
        //有供应商
        for(SupplierAreaBusiness supplierAreaBusiness : supplierAreaBusinessList){
            supplierCode = supplierAreaBusiness.getSupplierCode ();

            //获取开放平台供应商信息
            serviceRegisterVo = getServiceRegisterVo (result, supplierCode);
            businessKey = serviceRegisterVo.getSignKey ();
            String url = serviceRegisterVo.getServiceAddr () + "queryPayUnit.do";

            //获取供应商信息
            PayUnitRequest payUnitRequest = new PayUnitRequest ();
            //网点联调
            /*payUnitRequest.setProvince("湖北省");
            payUnitRequest.setCity("天门市");*/
            payUnitRequest.setProvince (shopVO.getContactProvince ());
            payUnitRequest.setCity (shopVO.getContactCity ());
            payUnitRequest.setCounty (shopVO.getContactArea ());
            payUnitRequest.setTimestamp (DateUtil.date2String (timestamp));
            Integer payType = BusinessCodeConvert.valueOf (businessCode).getCode ();
            payUnitRequest.setPayType (payType);
            //供应商编号，对应增值服务的supplierCode;
            payUnitRequest.setBusinessId (supplierAreaBusiness.getSupplierCode ());
            String signed = SdmRequestSignUtils.signQueryPayUnit (payUnitRequest, businessKey);
            payUnitRequest.setSigned (signed);
            logger.info ("请求，开放平台，获取机构信息url：" + url);
            logger.info ("请求，开放平台，获取机构信息：" + JSONObject.toJSONString (payUnitRequest));
            String response = HttpUtils.postContent (url, JSONObject.parseObject (JSONObject.toJSONString (payUnitRequest)));

            logger.info("调用 queryPayUnit 返回的信息是：" + response);


            responseWrapper = JSONObject.toJavaObject (JSONObject.parseObject (response), ResponseWrapper.class);

            logger.info ("请求，开放平台，获取机构信息结果1：" + responseWrapper);
            //如果是最后一个供应商
            if(count - 1 == supplierAreaBusinessList.indexOf(supplierAreaBusiness)){
                if (CollectionUtils.isEmpty (supplierAreaBusinessList)) {
                    result.setData ("");
                    result.setStatus (Result.STATUS_OK);
                    logger.info ("该区域没有支持的供应商1," + shopVO.getAreaName ());
                    return result;
                }
                if (StringUtils.isEmpty (response)) {
                    logger.error ("获取缴费机构，获取开放平台秘返回空");
                    result.setData ("");
                    result.setStatus (Result.STATUS_ERROR);
                    logger.info ("获取缴费机构，获取开放平台秘返回空," + shopVO.getAreaName ());
                    return result;
                    //throw new FunctionException (result, "获取缴费机构，获取开放平台秘返回空");
                }
                logger.info ("请求，开放平台，获取机构信息结果2：" + response);
                responseWrapper = JSONObject.toJavaObject (JSONObject.parseObject (response), ResponseWrapper.class);


                if (responseWrapper.getStatus () != 0) {
                    logger.error ("获取缴费机构," + responseWrapper.getMessage ());
                    result.setData ("");
                    result.setStatus (Result.STATUS_ERROR);
                    logger.info ("该区域没有支持的供应商2," + shopVO.getAreaName ());
                    return result;
                    //throw new FunctionException (result, responseWrapper.getMessage () + ",请联系管理员！");
                }
            }else{
                if (CollectionUtils.isEmpty (supplierAreaBusinessList)) {
                    logger.info ("该区域没有支持的供应商3," + shopVO.getAreaName ());
                    continue;
                }
                if (StringUtils.isEmpty (response)) {
                    logger.error ("获取缴费机构，获取开放平台秘返回空");
                    continue;
                }
                if (responseWrapper.getStatus () != 0) {
                    logger.error ("获取缴费机构," + responseWrapper.getMessage ());
                    continue;
                }
            }
            List<PayUnit> payUnitList = JSONArray.parseArray (responseWrapper.getData ().toString (), PayUnit.class);
            if (payUnitList.size() <= 0) {
                //如果是最后一个供应商
                if(count - 1 == supplierAreaBusinessList.indexOf(supplierAreaBusiness)){

                    //没有收费单位
                    result.setData ("");
                    result.setStatus (Result.STATUS_OK);
                    logger.info ("该区域没有支持的供应商," + shopVO.getAreaName ());
                    return result;
                }else{
                    //非最后一个供应商
                    continue;
                }

            }else {
                for (PayUnit item : payUnitList) {
                    ChargeOrgVo.UnitInfo unitInfo = new ChargeOrgVo.UnitInfo ();
                    unitInfo.setChargeOrgName (item.getUnitName ());
                    unitInfo.setChargeOrgCode (item.getUnitId ());
                    unitInfo.setAccountType (item.getAccountType ());
                    unitInfo.setPayModel (item.getPayModel ());
                    unitInfo.setStatus (item.getUnitStatus ());
                    unitInfo.setProvince (item.getProvince ());
                    unitInfo.setCity (item.getCity ());
                    unitInfo.setCounty (item.getCounty ());
                    unitInfo.setPayType (item.getPayType ());
                    unitInfo.setIsNeedMonth (item.getIsNeedMonth ());
                    unitInfo.setUnitStatus (item.getUnitStatus ());
                    unitInfo.setProductId (item.getProductId ());
                    unitInfos.add (unitInfo);
                }
                chargeOrgVo.setSupplierCode (supplierCode);
                chargeOrgVo.setSupplierToken (businessKey);
                chargeOrgVo.setServerAddr (serviceRegisterVo.getServiceAddr ());
                chargeOrgVo.setUnitInfos (unitInfos);
                logger.info("getOrganizationInfo 返回的结果是：" + chargeOrgVo);
                result.setData (chargeOrgVo);
                result.setStatus (Result.STATUS_OK);
                return result;
            }
        }
        
        result.setData ("");
        result.setStatus (Result.STATUS_OK);
        return result;
    }

    /*
     * 获取服务商
     */
    private ServiceRegisterVo getServiceRegisterVo(Result result, String supplierCode) throws Exception {
        return commomService.getServiceRegisterVo(result, supplierCode, EnumBusinessCode.DJDF);

    }



    /**
     * 获取用户账单信息
     *
     * @param result
     * @param rechargeParams
     * @return
     * @author iritchie.ren
     */
    public Result getBillInfo(Result result, RechargeParams<BillInfoVo> rechargeParams) throws Exception {
        PaymentBillRequest paymentBillRequest = new PaymentBillRequest ();
        paymentBillRequest.setBusinessId (rechargeParams.getSupplierCode ());
        paymentBillRequest.setUnitId (rechargeParams.getData ().getChargeOrgCode ());
        paymentBillRequest.setUnitName (rechargeParams.getData ().getChargeOrgName ());
        paymentBillRequest.setAccount (rechargeParams.getData ().getAccount ());
        paymentBillRequest.setYearmonth ("");
        paymentBillRequest.setProductId (rechargeParams.getData ().getProductId ());
        paymentBillRequest.setPayType (Integer.valueOf (rechargeParams.getData ().getPayType ()));
        paymentBillRequest.setAccountType (Integer.valueOf (rechargeParams.getData ().getAccountType ()));
        paymentBillRequest.setProvince (rechargeParams.getData ().getProvince ());
        paymentBillRequest.setCity (rechargeParams.getData ().getCity ());
        paymentBillRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));

        String paymentBillSigned = SdmRequestSignUtils.signPaymentBill (paymentBillRequest, rechargeParams.getSupplierToken ());
        paymentBillRequest.setSigned (paymentBillSigned);

        String url = rechargeParams.getServerAddr () + "queryPaymentBill.do";
        JSONObject param = JSONObject.parseObject (JSONObject.toJSONString (paymentBillRequest));
        logger.info ("请求，开放平台，获取机构信息url：" + url);
        logger.info ("请求，开放平台，获取机构信息：" + JSONObject.toJSONString (paymentBillRequest));
        String response = HttpUtils.postContent (url, param);
        logger.info ("请求，开放平台，获取机构信息：" + response);

        logger.info("查询账单返回response is：" + response);

        if(null == response){
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData ("查询账单失败!");
            return result;
        }

        ResponseWrapper responseWrapper = JSONObject.toJavaObject (JSONObject.parseObject (response), ResponseWrapper.class);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("从开放平台获取用户账单失败");
            throw new FunctionException (result, responseWrapper.getMessage ());
        }
        if (responseWrapper.getData () == null) {
            logger.error ("从开放平台获取用户账单数据为空");
            throw new FunctionException (result, responseWrapper.getMessage ());
        }

        List<PaymentBill> paymentBills = JSONArray.parseArray (responseWrapper.getData ().toString (), PaymentBill.class);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (paymentBills);
        return result;
    }

    /**
     * 新增订单接口
     *
     * @param result
     * @param chargeInfoVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result payChargeInfo(Result result, ChargeInfoVo chargeInfoVo) throws Exception {

        //生成订单,保存订单信息，订单状态为待支付
        ChargeInfo chargeInfo = this.addChargeInfo (result, chargeInfoVo);
        //调用支付接口
        this.pay (result, chargeInfo);
        //订单状态为支付中，后面有个支付消息中间件回调继续下面的步骤
        this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), ChargePayStatus.PAIDING.getCode ());
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfo);
        return result;
    }


    /**
     * 支付接口
     * 
     * @param result 
     * @param chargeInfo 订单信息对象
     * @throws Exception
     */
    private void pay(Result result, ChargeInfo chargeInfo) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (chargeInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeInfo.getChargeCode ());
        ticketCreateParam.setBizType (TicketBizType.AppreciationOrderPay.getKey ());
        ticketCreateParam.setUserId (userInfo.getUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
            logger.info("获取访问令牌信息为：" + ticketDTO);

        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，已经解锁过了" + JSONObject.toJSONString (ticketCreateParam));
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }
        //----2.调用支付
        BizOrderPayParam bizOrderPayParam = new BizOrderPayParam ();
        //令牌id.
        bizOrderPayParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        bizOrderPayParam.setOrderId (chargeInfo.getId ());
        //业务单编号（业务的唯一编号）
        bizOrderPayParam.setBizOrderNo (chargeInfo.getChargeCode ());
        // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
        bizOrderPayParam.setAddTag (1);
        // 待支付/待退回总额（不包括优惠金额,单位是分）.
        bizOrderPayParam.setTotalMoney (chargeInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ());
        //请求操作用户Id.
        bizOrderPayParam.setOperateUserId (userInfo.getUserId ());
        // 账户使用优惠券信息.
        bizOrderPayParam.setUseCoupons (null);
        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (null);

        //账户使用活动信息. modify by zengzw.2016-11-17。电费没用到活动信息，为空
        bizOrderPayParam.setOrderPayActivitys(null);

        // 业务详情.
        bizOrderPayParam.setBizDetails ("增值服务订单支付");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("增值服务");
        // PC-业务详情穿透url.订单详情的url.
        JSONObject bizParam = new JSONObject ();
        bizParam.put ("chargeCode", chargeInfo.getChargeCode ());
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + "/views/bills/detail.html?chargeCode=" + chargeInfo.getChargeCode ();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic ("vasPayChargeTopic");
        // 所属账户的商业ID.支付账户
        bizOrderPayParam.setBizId (chargeInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        bizOrderPayParam.setBizType (chargeInfo.getBizType ());
        Date sendTime1 = new Date ();
        Date receiveTime1;
        FundsAccept fundsAccept = null;
        try {
            logger.info ("调用账户支付bizOrderPayService.requestBizOrderPay():" + JSONObject.toJSONString (bizOrderPayParam));
            fundsAccept = this.bizOrderPayService.requestBizOrderPay (bizOrderPayParam);
            logger.info ("调用账户支付bizOrderPayService.requestBizOrderPay()结果:" + fundsAccept);
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "bizOrderPayService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统支付");
        } catch (Exception ex) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "bizOrderPayService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付调用失败");
            logger.error ("请求账户支付调用失败");
            throw new FunctionException (result, "请求账户支付调用失败");
        }
        assert fundsAccept != null;
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "bizOrderPayService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付调用返回失败状态");
            logger.error ("请求账户支付调用返回失败状态");
            throw new FunctionException (result, "请求账户支付调用返回失败状态");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "bizOrderPayService.requestBizOrderPay()", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户支付重复调用");
            logger.error ("请求账户支付重复调用");
            throw new FunctionException (result, "请求账户支付重复调用");
        }
    }

    private ChargeInfo addChargeInfo(Result result, ChargeInfoVo chargeInfoVo) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        ShopVO shopVO = this.shopApi.getShop (result, userInfo.getBizId ()).getData ();
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, chargeInfoVo.getSupplierCode ()).getData ();
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, chargeInfoVo.getBusinessCode ()).getData ();
        SupplierBusiness supplierBusiness = this.supplierBusinessDao.queryBySupplierCodeAndBusinessCode (result, chargeInfoVo.getSupplierCode (), chargeInfoVo.getBusinessCode ()).getData ();
        BusinessStoreShare businessStoreShare = this.businessStoreShareDao.queryByCountryCodeAndBusinessCode (result, String.valueOf (shopVO.getAreaId ()), chargeInfoVo.getBusinessCode ()).getData ();
        Double areaCommissionRatio;
        Double storeCommissionRatio;
        if (businessStoreShare != null) {
            areaCommissionRatio = (businessStoreShare.getCountryShareRatio ());
            storeCommissionRatio = (businessStoreShare.getStoreShareRatio ());
        } else {
            areaCommissionRatio = 0.00;
            storeCommissionRatio = 100.00; //modify by zengzw.当没设置分润的时候，默认全部给网点#2016-11-23
        }
        BusinessInfo businessInfoParent = this.businessInfoDao.getBusinessInfoByParent (result, chargeInfoVo.getBusinessCode ()).getData ();
        String parentBusinessCode;
        String parentBusinessName;
        if (businessInfoParent != null) {
            parentBusinessCode = businessInfoParent.getBusinessCode ();
            parentBusinessName = businessInfoParent.getBusinessName ();
        } else {
            parentBusinessCode = businessInfo.getBusinessCode ();
            parentBusinessName = businessInfo.getBusinessName ();
        }
        //生成开放平台的订单编号
        String openPlatformNo = this.getOpenPlatformNo (result, chargeInfoVo);

        //获取会员信息
        Map<String, Object> mbrParam = Maps.newHashMap ();
        mbrParam.put ("reqSource", "b2c");
        mbrParam.put ("sysType", 2);
        mbrParam.put ("token", result.getUserInfo ().getSessionId ());
        mbrParam.put ("mobile", chargeInfoVo.getMobile ());
        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + "/member/app/getMemberUserInfo.do";
        String mbrResponse = HttpUtils.postContent (mbrUrl, mbrParam);

        logger.info("获取会员的信息：" + mbrResponse);

        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }
        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);

        Long bizId;
        Integer bizType;
        String payUserName;
        Long payUserId;
        String mobile;
        if (chargeInfoVo.getBizType ().equals (RoleType.SHOP.getCode ())) {
            //网点密码验证
            Map<String, Object> storeParam = Maps.newHashMap ();
            storeParam.put ("payPwd", chargeInfoVo.getPayPassword ());
            storeParam.put ("userId", userInfo.getUserId ());
            String url = TshDiamondClient.getInstance ().getConfig ("acc-url") + "/accpwd/validateOutPayPwd.do";
            logger.info ("网点密码验证url:" + url);
            logger.info ("网点密码验证参数：" + JSONObject.toJSONString (storeParam));
            String response = HttpUtils.postContent (url, storeParam);
            logger.info ("网点密码验证参数：" + response);

            if (StringUtils.isEmpty (response)) {
                logger.error ("网点密码验证失败");
                throw new FunctionException (result, "密码验证失败");
            }
            Result storeResult = JSONObject.parseObject (response, Result.class);
            if (storeResult.getData ()) {
                logger.info ("密码验证成功-------------");
            } else {
                logger.error ("密码错误");
                throw new FunctionException (result, "密码错误");
            }

            // 网点
            // 所属账户的商业ID.支付账户
            bizId = userInfo.getBizId ();
            // 所属账户的商业类型.支付账户，网点，会员，找邓松
            bizType = RoleType.SHOP.getCode ();
            payUserId = shopVO.getUserId ();
            //??获取网点的信息
            payUserName = shopVO.getContact ();
            mobile = shopVO.getContactTel ();
        } else if (chargeInfoVo.getBizType ().equals (RoleType.MEMBER.getCode ())) {

            //会员密码验证：http://172.16.3.56:8160/member/app/validateMemberCard.do?mobile=15921578824&memberId=196750&payPassword=123456
            String url = TshDiamondClient.getInstance ().getConfig ("mbr-url") + "/member/app/validateMemberCard.do";
            Map<String, Object> param = Maps.newHashMap ();
            param.put ("mobile", chargeInfoVo.getMobile ());
            param.put ("memberId", memberVo.getId ());
            param.put ("payPassword", chargeInfoVo.getPayPassword ());
            logger.info ("网点密码验证url:" + url);
            logger.info ("网点密码验证参数：" + JSONObject.toJSONString (param));
            String response = HttpUtils.postContent (url, param);
            logger.info ("网点密码验证结果:" + response);

            if (StringUtils.isEmpty (response)) {
                logger.error ("密码验证错误");
                throw new FunctionException (result, "密码验证错误");
            }
            JSONObject resultJson = JSONObject.parseObject (response);
            int resultFlag = resultJson.getIntValue ("status");
            String message = resultJson.getString ("msg");
            if (resultFlag == ReturnStatusEnum.OK.getValue ()) {
                logger.info ("密码验证成功---------------------");
            } else {
                logger.error ("密码验证错误：" + message);
                throw new FunctionException (result, message);
            }

            // 会员
            // 所属账户的商业ID.支付账户
            bizId = memberVo.getId ();
            payUserId = memberVo.getUserID ();
            // 所属账户的商业类型.支付账户，网点，会员，找邓松
            bizType = RoleType.MEMBER.getCode ();
            payUserName = memberVo.getMemberName ();
            mobile = chargeInfoVo.getMobile ();
        } else {
            logger.error ("账户类型不存在");
            throw new FunctionException (result, "账户类型不存在");
        }
        //订单编号=业务编号+时间码
        String chargeCode = chargeInfoVo.getBusinessCode () + GenerateOrderNumber.getOrderNumber ();
        JSONObject storeInfoJson = new JSONObject ();
        storeInfoJson.put ("contact", shopVO.getContact ());
        storeInfoJson.put ("contactTel", shopVO.getContactTel ());
        BigDecimal costingAmount;
        if (chargeInfoVo.getBusinessCode ().equals ("DJDF")) {
            //电费没有成本,成本==实付
            costingAmount = chargeInfoVo.getRealAmount ();
        } else {
            costingAmount = chargeInfoVo.getCostingAmount ();
        }

        ChargeInfo chargeInfo = new ChargeInfo ();
        chargeInfo.setChargeCode (chargeCode);
        chargeInfo.setOpenPlatformNo (openPlatformNo);
        chargeInfo.setSupplierCode (chargeInfoVo.getSupplierCode ());
        chargeInfo.setSupplierName (supplierInfo.getCompany ());  //修改为供应商名称
        chargeInfo.setStoreCode (String.valueOf (userInfo.getBizId ()));
        chargeInfo.setStoreName (shopVO.getShopName ());
        chargeInfo.setStoreInfo (storeInfoJson.toJSONString ());
        chargeInfo.setProvince (shopVO.getContactProvince ());
        chargeInfo.setCity (shopVO.getContactCity ());
        chargeInfo.setCountry (shopVO.getContactArea ());
        chargeInfo.setCountryCode (String.valueOf (shopVO.getAreaId ()));
        chargeInfo.setCountryName (shopVO.getAreaName ());
        chargeInfo.setRechargeUserCode (chargeInfoVo.getRechargeUserCode ());
        chargeInfo.setRechargeUserName (chargeInfoVo.getRechargeUserName ());
        chargeInfo.setChargeOrgCode (chargeInfoVo.getChargeOrgCode ());
        chargeInfo.setChargeOrgName (chargeInfoVo.getChargeOrgName ());
        chargeInfo.setBizId (bizId);
        chargeInfo.setBizType (bizType);
        chargeInfo.setPayUserId (payUserId);
        chargeInfo.setRechargeUserAddr (shopVO.getContactAddress ());
        chargeInfo.setMobile (mobile);
        chargeInfo.setChargeOrgCode (chargeInfoVo.getChargeOrgCode ());
        chargeInfo.setBusinessCode (parentBusinessCode);
        chargeInfo.setBusinessName (parentBusinessName);
        chargeInfo.setSubBusinessCode (chargeInfoVo.getBusinessCode ());
        chargeInfo.setSubBusinessName (businessInfo.getBusinessName ());
        chargeInfo.setLiftCoefficient (supplierBusiness.getTotalShareRatio ());
        chargeInfo.setPlatformDividedRatio (supplierBusiness.getPlatformShareRatio ());
        chargeInfo.setAreaDividedRatio (supplierBusiness.getAreaShareRatio ());
        chargeInfo.setAreaCommissionRatio (areaCommissionRatio);
        chargeInfo.setStoreCommissionRatio (storeCommissionRatio);
        chargeInfo.setBillYearMonth (chargeInfoVo.getBillYearMonth ());
        chargeInfo.setRealAmount (chargeInfoVo.getRealAmount ());
        chargeInfo.setChargeOrgName (chargeInfoVo.getChargeOrgName ());
        chargeInfo.setCreateTime (new Date ());
        chargeInfo.setPayStatus (ChargePayStatus.NON_PAYMENT.getCode ());
        chargeInfo.setRefundStatus (ChargeRefundStatus.NORMAL_REFUND.getCode ());
        chargeInfo.setCostingAmount (costingAmount);
        chargeInfo.setOriginalAmount (chargeInfoVo.getOriginalAmount ());
        chargeInfo.setPayUserName (payUserName);
        chargeInfo.setRechargeUserType (chargeInfoVo.getRechargeUserType ());
        chargeInfo.setProductId (chargeInfoVo.getProductId ());
        chargeInfo.setMemberMobile (chargeInfoVo.getMobile ());
        chargeInfo.setMemberName (memberVo.getMemberName ());
        chargeInfo.setExtContent (chargeInfoVo.getExtContent () == null ? "" : chargeInfoVo.getExtContent ());
        this.chargeInfoDao.insert (result, chargeInfo).getData ();
        return chargeInfo;
    }

    private String getOpenPlatformNo(Result result, ChargeInfoVo chargeInfoVo) throws Exception {
        GenerateOrderNoRequest getKFPOrderNoGenerateOrderNoRequest = new GenerateOrderNoRequest ();
        getKFPOrderNoGenerateOrderNoRequest.setBusinessId (chargeInfoVo.getSupplierCode ());
        getKFPOrderNoGenerateOrderNoRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        String getKFPOrderNoSigned = SdmRequestSignUtils.signGenerateOrderNo (getKFPOrderNoGenerateOrderNoRequest, chargeInfoVo.getSupplierToken ());
        getKFPOrderNoGenerateOrderNoRequest.setSigned (getKFPOrderNoSigned);
        Map<String, String> getKFPOrderNoParam = ObjectMapUtils.toStringMap (getKFPOrderNoGenerateOrderNoRequest);
        String getKFPOrderNoUrl = chargeInfoVo.getServerAddr () + "/generateOrderNo.do";
        String getKFPOrderNoResponse = HttpXmlClient.post (getKFPOrderNoUrl, getKFPOrderNoParam);

        logger.info("生成开放平台的订单编号：" + getKFPOrderNoResponse);

        if (StringUtils.isEmpty (getKFPOrderNoResponse)) {
            logger.error ("生成开放平台的订单编号失败");
            throw new FunctionException (result, "生成开放平台的订单编号失败");
        }
        ResponseWrapper responseWrapperKey = JSONObject.parseObject (getKFPOrderNoResponse, ResponseWrapper.class);
        if (responseWrapperKey.getStatus () != 0) {
            logger.error ("生成开放平台的订单编号失败，状态码返回不成功");
            throw new FunctionException (result, responseWrapperKey.getMessage ());
        }
        String openPlatformNo = responseWrapperKey.getData ().toString ();
        if (StringUtils.isEmpty (openPlatformNo)) {
            logger.error ("获取开放平台的订openPlatformNo，状态码返回不成功");
            throw new FunctionException (result, "生成开放平台的订单编号失败");
        }
        return openPlatformNo;
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
    public Result payBack(Result result, MQBizOrderPay mqBizOrderPay) throws Exception {
        ChargePaymentInfo chargePaymentInfo = new ChargePaymentInfo ();
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByChargeCode (result, mqBizOrderPay.getBizOrderNo ()).getData ();
        //订单支付成功后，又返回失败的情况。严格判断状态。
        if (!chargeInfo.getPayStatus ().equals (ChargePayStatus.PAY_SUCCESS.getCode ())) {
            result = this.insertErrorStatus (result, chargeInfo, "支付回调，不是合理状态的单，状态转变");

            result.setStatus (500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (chargeInfo);
            return result;
        }

        chargePaymentInfo.setChargeCode (mqBizOrderPay.getBizOrderNo ());
        chargePaymentInfo.setPayWay (chargeInfo.getBizType ());
        chargePaymentInfo.setCreateTime (new Date ());
        chargePaymentInfo = this.chargePaymentInfoDao.insert (result, chargePaymentInfo).getData ();
        //获取开放平台供应商的信息
        ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo (result, chargeInfo.getSupplierCode ());


        //根据订单创建时间，判断走新老支付流程
        boolean isOldBusiness = isOldBusiness(chargeInfo.getCreateTime());
        if(!isOldBusiness){
            //@change 添加结算生成接口.add by zengzw#2016-11-18
            this.paySettleV1(result, chargeInfo.getOpenPlatformNo());
        }

        //请求开放平台充值
        this.chargePlatform (result, chargeInfo, serviceRegisterVo);


        //订单状态为缴费中
        logger.info ("订单状态为缴费中");
        this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), ChargePayStatus.CHARGING.getCode ());

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargePaymentInfo);
        return result;
    }

    private void chargePlatform(Result result, ChargeInfo chargeInfo, ServiceRegisterVo serviceRegisterVo) throws Exception {
        logger.info("#--> 开始调用外部平台充值接口进行实际充值..."+JSON.toJSONString(chargeInfo));

        String businessKey = serviceRegisterVo.getSignKey ();

        RechargeRequest rechargeRequest = new RechargeRequest ();
        rechargeRequest.setBusinessId (chargeInfo.getSupplierCode ());
        rechargeRequest.setInOrderNo (chargeInfo.getChargeCode ());
        rechargeRequest.setOutOrderNo (chargeInfo.getOpenPlatformNo ());
        rechargeRequest.setUnitId (chargeInfo.getChargeOrgCode ());
        rechargeRequest.setAccount (chargeInfo.getRechargeUserCode ());
        rechargeRequest.setPayAmount (chargeInfo.getRealAmount ());
        rechargeRequest.setYearmonth (chargeInfo.getBillYearMonth ());
        rechargeRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        rechargeRequest.setExtContent (chargeInfo.getExtContent ());
        //产品ID
        rechargeRequest.setProductId (chargeInfo.getProductId ());
        //缴费类型	1.水费2.电费3.煤气
        rechargeRequest.setPayType (BusinessCodeConvert.valueOf (chargeInfo.getBusinessCode ()).getCode ());
        //账户类型  1.户号 2.条形码
        rechargeRequest.setAccountType (chargeInfo.getRechargeUserType ());
        //省份/直辖市
        rechargeRequest.setProvince (chargeInfo.getProvince ());
        //城市/区
        rechargeRequest.setCity (chargeInfo.getCity ());
        //县/区
        rechargeRequest.setCounty (chargeInfo.getCountry ());
        String rechargeSigned = SdmRequestSignUtils.signRecharge (rechargeRequest, businessKey);
        rechargeRequest.setSigned (rechargeSigned);
        Map<String, String> rechargeRequestMap = ObjectMapUtils.toStringMap (rechargeRequest);
        String urlCharge = serviceRegisterVo.getServiceAddr () + "/recharge.do";
        Date sentTime = new Date ();
        logger.info ("调用充值接口url：" + urlCharge);
        logger.info ("调用充值接口：" + JSONObject.toJSONString (rechargeRequestMap));
        String rechargeResponse = HttpXmlClient.post (urlCharge, rechargeRequestMap);
        logger.info ("调用充值接口结果：" + rechargeResponse);
        Date receiveTime = new Date ();
        this.insertHttpLog (result, chargeInfo.getChargeCode (), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "请求开放平台充值");
        //不进行回滚，以消息回调为准
        if (StringUtils.isEmpty (rechargeResponse)) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "开放平台充值失败");
            logger.error ("开放平台充值失败");
        }
        ResponseWrapper responseWrapperCharge = JSONObject.parseObject (rechargeResponse, ResponseWrapper.class);
        if (responseWrapperCharge.getStatus () != 0) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "开放平台充值返回失败状态");
            logger.error ("开放平台充值返回失败状态");
        }

        logger.info("#--> 完成调用外部平台充值接口进行实际充值..."+rechargeResponse);

    }

    /**
     * 开放平台充值完后回调，进行结算
     *
     * @param result
     * @return
     * @author iritchie.ren
     */
    public Result rechargeBack(Result result, ResultNotifyRequest resultNotifyRequest) throws Exception {
        logger.info("resultNotifyRequest====" + resultNotifyRequest);

        Integer payStatus;
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByOpenPlatformNo (result, resultNotifyRequest.getOutOrderNo ()).getData ();

        logger.info("chargeInfo===========" + chargeInfo);

        //订单充值成功后，又返回失败的情况。严格判断状态。
        if (!(chargeInfo.getPayStatus ().equals (ChargePayStatus.CHARGING.getCode ()) || chargeInfo.getPayStatus ().equals (ChargePayStatus.TRAD_SUCCESS.getCode ())) || !chargeInfo.getRefundStatus ().equals (ChargeRefundStatus.NORMAL_REFUND.getCode ())) {
            result = this.insertErrorStatus (result, chargeInfo, "充值回调，不合理的状态转变");
            logger.info("1===================================");
            result.setStatus (500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (chargeInfo);
            return result;
        }

        Boolean flag = false;
        if (resultNotifyRequest.getPayResult ().equals (PayResultType.CHARGING_SUCCESS.getCode ())) {
            logger.info("2===================================");
            //回调返回成功，订单状态改成交易成功
            payStatus = ChargePayStatus.TRAD_SUCCESS.getCode ();
            //订单更改状态
            this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), payStatus);
            //等待结果（缴费中）”转变为“已成功”的短信提醒
            String msg = "亲爱的m" + chargeInfo.getMemberMobile () + "，您的缴电费订单("+chargeInfo.getChargeCode()+")缴费已成功，缴费金额"+chargeInfo.getRealAmount()+"元，欢迎您下次光临。";
            this.smsService.sendSms (chargeInfo.getMemberMobile (),msg );
        } else if (resultNotifyRequest.getPayResult ().equals (PayResultType.CHARGING.getCode ())) {
            logger.info("3===================================");
            result.setMsg (PayResultType.getEnume (resultNotifyRequest.getPayResult ()).getDesc ());
            flag = true;
        } else if (resultNotifyRequest.getPayResult ().equals (PayResultType.CHARGING_FAIL.getCode ())) {
            logger.info("4===================================");
            this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), ChargePayStatus.TRAD_FAIL.getCode ());
            //“等待结果（缴费中）”转变为“已失败”的短信提醒：
            this.smsService.sendSms (chargeInfo.getMemberMobile (), "亲爱的m" + chargeInfo.getMemberMobile () + "，很抱歉，您的缴电费订单缴费失败了，支付金额会在七天内退回支付账户。");

            //---通知运营蔡璐玲---通知测试杨珊珊---通知工程师黄振盛
            /*	 String notificationStr = TshDiamondClient.getInstance ().getConfig ("notification").trim ();
			 if (notificationStr != "none") {
				 logger.info("5===================================");
				 String[] notificationArray = StringUtils.split (notificationStr, ";");
				 for (String item : notificationArray) {
					 //判断是欧飞还是易赛
					 if(chargeInfo.getOpenPlatformNo().startsWith("IWEC")){
						 //易赛
						 this.smsService.sendSms (item.trim (), "【淘实惠工程师】亲爱的m" + chargeInfo.getMemberMobile () + "，很抱歉，缴电费易赛订单号" + chargeInfo.getOpenPlatformNo () + ";平台订单号" + chargeInfo.getChargeCode () + "缴费失败了，请留意！");
					 }else if(chargeInfo.getOpenPlatformNo().startsWith("SP")){
						 //欧飞
						 this.smsService.sendSms (item.trim (), "【淘实惠工程师】亲爱的m" + chargeInfo.getMemberMobile () + "，很抱歉，缴电费欧飞订单号" + chargeInfo.getOpenPlatformNo () + ";平台订单号" + chargeInfo.getChargeCode () + "缴费失败了，请留意！");
					 }else {
						 this.smsService.sendSms (item.trim (), "【淘实惠工程师】亲爱的m" + chargeInfo.getMemberMobile () + "，很抱歉，缴电费订单号" + chargeInfo.getOpenPlatformNo () + ";平台订单号" + chargeInfo.getChargeCode () + "缴费失败了，请留意！");
					 }

				 }
			 }*/
            logger.info("6===================================");
            result.setMsg (PayResultType.getEnume (resultNotifyRequest.getPayResult ()).getDesc ());
            flag = true;
        } else if (resultNotifyRequest.getPayResult ().equals (PayResultType.CHARGING_UNDEFINED.getCode ())) {

            result.setMsg (PayResultType.getEnume (resultNotifyRequest.getPayResult ()).getDesc ());
            flag = true;
        } else {
            logger.info("7===================================");
            result.setMsg ("充值完后，回调参数状态无效");
            flag = true;
        }
        if (flag) {
            logger.info("8===================================");
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (500);
            return result;
        }

        result.setStatus (200);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfo);
        return result;
    }

    private Result insertErrorStatus(Result result, ChargeInfo chargeInfo, String remark) throws Exception {
        ChargeInfoErrLog chargeInfoErrLog = new ChargeInfoErrLog ();
        chargeInfoErrLog.setPayStatus (chargeInfo.getPayStatus ());
        chargeInfoErrLog.setChargeCode (chargeInfo.getChargeCode ());
        chargeInfoErrLog.setCreateTime (new Date ());
        chargeInfoErrLog.setOpenPlatformNo (chargeInfo.getOpenPlatformNo ());
        chargeInfoErrLog.setRefundStatus (chargeInfo.getRefundStatus ());
        chargeInfoErrLog.setRemark (remark);
        result = this.chargeInfoErrLogDao.addLog (result, chargeInfoErrLog);
        return result;
    }


    /**
     * 结算
     * 
     * @param result
     * @param openPlatformNo
     * @return
     * @throws Exception
     */
    public Result paySettle(Result result, String openPlatformNo) throws Exception {
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (chargeInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeInfo.getChargeCode ());
        ticketCreateParam.setBizType (TicketBizType.AppreciationPeriodSettle.getKey ());
        ticketCreateParam.setUserId (chargeInfo.getPayUserId ());
        TicketDTO ticketDTO;
        try {
            ticketDTO = ticketService.builderTicket (ticketCreateParam);
            logger.info("获取访问令牌信息：" + ticketDTO);
        } catch (Exception ex) {
            logger.warn ("获取ticketId异常，已经解锁过了" + JSONObject.toJSONString (ticketCreateParam));
            throw new FunctionException (result, "获取ticketId异常，已经解锁过了");
        }
        //----2.调用结算接口
        OrderSettleParam orderSettleParam = new OrderSettleParam ();
        //请求操作用户Id.
        orderSettleParam.setOperateUserId (chargeInfo.getPayUserId ());
        //令牌id.
        orderSettleParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderSettleParam.setOrderId (chargeInfo.getId ());

        //业务单编号（业务的唯一编号）.
        orderSettleParam.setBizOrderNo (chargeInfo.getChargeCode ());
        //业务简述.
        orderSettleParam.setBizIntro ("增值服务结算记录");
        //业务详情.
        orderSettleParam.setBizDetails ("增值服务结算");
        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + "/views/bills/detail.html?chargeCode=" + chargeInfo.getChargeCode ();
        orderSettleParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空(消息标识未由账号定义是考虑：外部传递是解决多个订阅者能够灵活进行消息隔离).
        orderSettleParam.setMsgTopic ("");
        //关联原锁定金额的业务单编号.
        orderSettleParam.setOffsetBizOrderNo (chargeInfo.getChargeCode ());
        //订单类型,	1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderSettleParam.setOrderType (12);
        //订单应付金额
        orderSettleParam.setOrderMoney (chargeInfo.getOriginalAmount ());
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, chargeInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderSettleParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderSettleParam.setProxySupplierId (null);
        //供应商账期
        orderSettleParam.setSupplierPaymentDays (7);
        //分润账期
        orderSettleParam.setProfitPaymentDays (7);
        //物流费
        orderSettleParam.setLogisticsMoney (new BigDecimal (0));
        //支付者用户ID
        orderSettleParam.setPayerUserId (chargeInfo.getPayUserId ());
        //销售县域Id
        orderSettleParam.setSaleAreaId (Long.parseLong (chargeInfo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderSettleParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderSettleParam.setSaleStoreId (Long.valueOf (chargeInfo.getStoreCode ()));
        //结算商品明细
        List<GoodsDetail> goodsDetails = Lists.newArrayList ();
        GoodsDetail goodsDetail = new GoodsDetail ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (chargeInfo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, chargeInfo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (chargeInfo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (chargeInfo.getBusinessName ());

        //商品价格（销售价/批发价）
        goodsDetail.setGoodsPrice (chargeInfo.getRealAmount ());

        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (chargeInfo.getCostingAmount ());
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
        goodsDetail.setCommissionRatio (new BigDecimal (chargeInfo.getLiftCoefficient ()));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (new BigDecimal (0));
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));
        //销售县域佣金比例
        goodsDetail.setSaleAreaCommissionRatio (new BigDecimal (chargeInfo.getAreaCommissionRatio ()));
        //销售网点提点比例 （县域零售、全国零售时提供）
        goodsDetail.setSaleStoreCommissionRatio (new BigDecimal (chargeInfo.getStoreCommissionRatio ()));
        //质量原因退货时间
        goodsDetail.setQualityReturnDays (7);
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (7);
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
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算");
        } catch (Exception e) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败");
            logger.error ("请求账户系统结算失败");
            throw new FunctionException (result, "请求账户系统结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求结算调用失败");
            throw new FunctionException (result, "请求结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求结算重复调用");
            throw new FunctionException (result, "请求结算重复调用");
        }
        return result;
    }


    /**
     * 新 结算接口V1
     * 
     * @param result
     * @param openPlatformNo
     * @return
     * @throws Exception
     */
    public Result paySettleV1(Result result, String openPlatformNo) throws Exception {
        logger.info("--> 开始生产结算记录......openPlatformNo:"+openPlatformNo);

        ChargeInfo chargeInfo = this.chargeInfoDao.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (chargeInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeInfo.getChargeCode ());
        ticketCreateParam.setBizType (TicketBizType.AppreciationPeriodSettle.getKey ());
        ticketCreateParam.setUserId (chargeInfo.getPayUserId ());
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
        orderSettleParam.setOperateUserId (chargeInfo.getPayUserId ());
        //令牌id.
        orderSettleParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderSettleParam.setOrderId (chargeInfo.getId ());

        //总订单号
        orderSettleParam.setTotalOrderNo(chargeInfo.getChargeCode());

        //业务单编号（业务的唯一编号）.
        orderSettleParam.setBizOrderNo (chargeInfo.getChargeCode ());
        //业务简述.
        orderSettleParam.setBizIntro ("增值服务结算记录");
        //业务详情.
        orderSettleParam.setBizDetails ("增值服务结算");
        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + "/views/bills/detail.html?chargeCode=" + chargeInfo.getChargeCode ();
        orderSettleParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空(消息标识未由账号定义是考虑：外部传递是解决多个订阅者能够灵活进行消息隔离).
        orderSettleParam.setMsgTopic ("");
        //关联原锁定金额的业务单编号.
        orderSettleParam.setOffsetBizOrderNo (chargeInfo.getChargeCode ());
        //订单类型,	1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderSettleParam.setOrderType (12);
        //订单应付金额
        orderSettleParam.setOrderMoney (chargeInfo.getOriginalAmount ());
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, chargeInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderSettleParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderSettleParam.setProxySupplierId (null);
        //供应商账期
        orderSettleParam.setSupplierPaymentDays (7);
        //分润账期
        orderSettleParam.setProfitPaymentDays (7);
        //物流费
        orderSettleParam.setLogisticsMoney (new BigDecimal (0));
        //支付者用户ID
        orderSettleParam.setPayerUserId (chargeInfo.getPayUserId ());
        //销售县域Id
        orderSettleParam.setSaleAreaId (Long.parseLong (chargeInfo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderSettleParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderSettleParam.setSaleStoreId (Long.valueOf (chargeInfo.getStoreCode ()));


        //结算商品明细
        List<GoodsDetailV1> goodsDetails = Lists.newArrayList ();
        GoodsDetailV1 goodsDetail = new GoodsDetailV1 ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (chargeInfo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, chargeInfo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (chargeInfo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (chargeInfo.getBusinessName ());

        //### add by zengzw #2016-11-17
        //商品销售价格（秒杀/限时折扣后的价格）
        goodsDetail.setSaleGoodsPrice(chargeInfo.getRealAmount());
        //原销售价(秒杀/限时折扣前的价格)，如果没有秒杀/限时折扣活动
        goodsDetail.setOriginalSaleGoodsPrice(chargeInfo.getRealAmount());
        //商品所使用的县域优惠券金额
        goodsDetail.setAreaCouponMoney(new BigDecimal (0));

        //商品价格（销售价/批发价）
        //goodsDetail.setGoodsPrice (chargeInfo.getRealAmount ());

        //##### modify end.....


        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (chargeInfo.getCostingAmount ());
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
        goodsDetail.setCommissionRatio (new BigDecimal (chargeInfo.getLiftCoefficient ()));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (new BigDecimal (0));
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));
        //销售县域佣金比例
        goodsDetail.setSaleAreaCommissionRatio (new BigDecimal (chargeInfo.getAreaCommissionRatio ()));
        //销售网点提点比例 （县域零售、全国零售时提供）
        goodsDetail.setSaleStoreCommissionRatio (new BigDecimal (chargeInfo.getStoreCommissionRatio ()));
        //质量原因退货时间
        goodsDetail.setQualityReturnDays (7);
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (7);
        goodsDetails.add (goodsDetail);

        orderSettleParam.setGoodsDetails (goodsDetails);
        lstOrderSettleParam.add(orderSettleParam);

        FundsAccept fundsAccept = null;
        Date sendTime1 = new Date ();
        Date receiveTime1;
        try {
            logger.info ("调用结算接口orderSettleService.orderSettle()：" + JSONObject.toJSONString (orderSettleParam));
            fundsAccept = this.orderSettleService.orderSettleV1(result, lstOrderSettleParam);
            logger.info ("调用结算接口orderSettleService.orderSettle()结果：" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算");
        } catch (Exception e) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败");
            logger.error ("请求账户系统结算失败",e);
            throw new FunctionException (result, "请求账户系统结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求结算调用失败");
            throw new FunctionException (result, "请求结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderSettleParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求结算重复调用");
            throw new FunctionException (result, "请求结算重复调用");
        }

        logger.info("-->完成生产结算记录......openPlatformNo:"+openPlatformNo);
        return result;
    }

    /**
     * 增值服务退款
     *
     * @param result
     * @param chargeCode
     * @return
     * @throws Exception
     */
    public Result refundCharge(Result result, String chargeCode,boolean oldBusiness) throws Exception {
        logger.info(">>>refundCharge 退款操作。chargeCode:"+chargeCode+" oldBusiness:"+oldBusiness);

        ChargeInfo chargeInfo = this.chargeInfoDao.queryByChargeCode (result, chargeCode).getData ();
        //订单退款，又返回失败的情况。严格判断状态。
        if (!chargeInfo.getPayStatus ().equals (ChargePayStatus.TRAD_FAIL.getCode ())) {
            result = this.insertErrorStatus (result, chargeInfo, "退款，不是合理状态的单，状态转变");

            result.setStatus (500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (chargeInfo);
            return result;
        }

        //退款前和第三方进行对账
        RechargeResultRequest rechargeResultRequest = new RechargeResultRequest ();
        rechargeResultRequest.setBusinessId (chargeInfo.getSupplierCode ());
        rechargeResultRequest.setInOrderNo (chargeInfo.getChargeCode ());
        rechargeResultRequest.setOutOrderNo (chargeInfo.getOpenPlatformNo ());
        rechargeResultRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        //获取开放平台供应商的信息
        ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo (result, chargeInfo.getSupplierCode ());
        rechargeResultRequest.setSigned (SdmRequestSignUtils.signQueryRechargeResult (rechargeResultRequest, serviceRegisterVo.getSignKey ()));

        Map<String, String> map = ObjectMapUtils.toStringMap (rechargeResultRequest);
        String url = serviceRegisterVo.getServiceAddr () + "queryRechargeResult.do";
        String response = HttpXmlClient.post (url, map);
        if (StringUtils.isEmpty (response)) {
            logger.error ("生成开放平台的订单编号失败");
            throw new FunctionException (result, "生成开放平台的订单编号失败");
        }
        ResponseWrapper responseWrapper = JSONObject.parseObject (response, ResponseWrapper.class);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("生成开放平台的订单编号失败，状态码返回不成功");
            throw new FunctionException (result, responseWrapper.getMessage ());
        }

        Integer orderStatus = (Integer) responseWrapper.getData ();
        if (PayResultType.CHARGING_SUCCESS.getCode ().equals (orderStatus)) {
            //将订单同步成成功
            this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), ChargePayStatus.TRAD_SUCCESS.getCode ());
            return result;
        } else if (!PayResultType.CHARGING_FAIL.getCode ().equals (orderStatus)) {
            //订单同步为缴费异常
            this.chargeInfoDao.updateStatus (result, chargeInfo.getChargeCode (), ChargePayStatus.CHARGE_FAIL.getCode ());
            return result;
        }

        ChargeRefund chargeRefund = getChargeRefund (chargeInfo);
        chargeRefund = this.chargeRefundDao.insert (result, chargeRefund).getData ();

        if (chargeRefund.getId () == null) {
            logger.error ("生成退款记录失败");
            throw new FunctionException (result, "生成退款记录失败");
        }

        //调用，调用退款结算接口
        if(oldBusiness){
            logger.info(">>> 调用老接口退款操作.......refundCharge-chargeCode:"+chargeCode);
            this.refundMoney (result, chargeInfo, chargeRefund);
        }else{
            logger.info(">>> 调用新接口退款操作.......refundCharge-chargeCode:"+chargeCode);
            //调用，退款，拿回分润接口。add by zengzw.#2016-11-18
            this.refundMoneyV1(result, chargeInfo, chargeRefund);
        }
        //订单退款状态退款中
        this.chargeInfoDao.updateRefundStatus (result, chargeCode, ChargeRefundStatus.WAIT_REFUND.getCode ());

        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }



    private void refundMoney(Result result, ChargeInfo chargeInfo, ChargeRefund chargeRefund) throws Exception {
        //1----获取ticket
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (chargeInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeRefund.getRefundCode ());
        ticketCreateParam.setBizType (TicketBizType.AppreciationBackOrderSettle.getKey ());
        ticketCreateParam.setUserId (chargeInfo.getPayUserId ());
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
        bizOrderPayParam.setBizOrderNo (chargeRefund.getRefundCode ());
        // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
        bizOrderPayParam.setAddTag (2);
        // 待支付/待退回总额（不包括优惠金额,单位是分）.
        bizOrderPayParam.setTotalMoney (chargeInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ());
        //请求操作用户Id.
        bizOrderPayParam.setOperateUserId (chargeInfo.getPayUserId ());
        // 账户使用优惠券信息.
        bizOrderPayParam.setUseCoupons (null);
        //关联原锁定金额的业务单编号.
        bizOrderPayParam.setOffsetBizOrderNo (chargeRefund.getChargeCode ());
        // 业务详情.
        bizOrderPayParam.setBizDetails ("增值服务订单退款");
        // 业务简述.
        bizOrderPayParam.setBizIntro ("增值服务退款");
        // PC-业务详情穿透url.订单详情的url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + "/views/bills/detail.html?chargeCode=" + chargeInfo.getChargeCode ();
        bizOrderPayParam.setBizPenetrationUrl (bizPenetrationUrl);
        //消息回调标识,不需要回调则为空.接收消息的主题
        bizOrderPayParam.setMsgTopic ("vasRefundChargeTopic");
        // 所属账户的商业ID.支付账户
        bizOrderPayParam.setBizId (chargeInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        bizOrderPayParam.setBizType (chargeInfo.getBizType ());
        Date sendTime1 = new Date ();
        Date receiveTime1;
        FundsAccept fundsAccept = null;
        try {
            logger.info ("请求退款bizOrderPayService.requestBizOrderRefund():" + JSONObject.toJSONString (bizOrderPayParam));
            fundsAccept = this.bizOrderPayService.requestBizOrderRefund (bizOrderPayParam);
            logger.info ("请求退款bizOrderPayService.requestBizOrderRefund():" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统退款");
        } catch (Exception e) {
            logger.error ("调用退款接口失败",e);
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "调用退款接口失败");
            throw new FunctionException (result, "请求账户退款调用失败,请求失败");
        }
        assert fundsAccept != null;
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款调用失败，返回错误状态");
            logger.error ("请求账户退款调用失败，返回错误状态");
            throw new FunctionException (result, "请求账户退款调用失败，返回错误状态");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (bizOrderPayParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户退款重复调用");
            logger.error ("请求账户退款重复调用");
            throw new FunctionException (result, "请求账户退款重复调用");
        }
    }

    /**
     * 退款 + 退货
     * 
     * @param result
     * @param chargeInfo
     * @param chargeRefund
     * @throws Exception
     */
    private void refundMoneyV1(Result result, ChargeInfo chargeInfo, ChargeRefund chargeRefund) throws Exception {
        //1----获取ticket
        TicketCreateParam ticketCreateParam = new TicketCreateParam ();
        ticketCreateParam.setOrderId (chargeInfo.getId ());
        ticketCreateParam.setBizOrderNo (chargeRefund.getRefundCode ());
        ticketCreateParam.setBizType (TicketBizType.AppreciationBackOrderSettle.getKey ());
        ticketCreateParam.setUserId (chargeInfo.getPayUserId ());
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
        orderRefundParam.setOperateUserId (chargeInfo.getPayUserId ());
        //令牌id.
        orderRefundParam.setTicketId (ticketDTO.getTicketId ());
        //订单ID
        orderRefundParam.setOrderId (chargeRefund.getId ());

        //业务单编号（业务的唯一编号）.
        orderRefundParam.setBizOrderNo (chargeRefund.getRefundCode());
        //业务简述.
        orderRefundParam.setBizIntro ("增值服务行退款和收回分润结算");
        //业务详情.
        orderRefundParam.setBizDetails ("增值服务退款，收回分润结算");

        //关联原锁定金额的业务单编号（用于退款，订单支付是按总订单号支付的，所以此处需要传总订单号）
        orderRefundParam.setOffsetBizOrderNo(chargeInfo.getChargeCode ());
        //子订单号（用于退分润，订单结算是按子订单进行结算的，所以此处要传子订单编号）
        orderRefundParam.setOffsetOrderNo(chargeInfo.getChargeCode ());

        //消息回调标识,不需要回调则为空.接收消息的主题
        orderRefundParam.setMsgTopic ("vasRefundChargeTopic");
        // 所属账户的商业ID.支付账户
        orderRefundParam.setBizId (chargeInfo.getBizId ());
        // 所属账户的商业类型.支付账户，网点，会员，找邓松;
        orderRefundParam.setBizType (chargeInfo.getBizType ());



        //PC-业务详情穿透url.
        String bizPenetrationUrl = TshDiamondClient.getInstance ().getConfig ("vas-n-url") + "/views/bills/detail.html?chargeCode=" + chargeInfo.getChargeCode ();
        orderRefundParam.setBizPenetrationUrl (bizPenetrationUrl);

        //关联原锁定金额的业务单编号.
        orderRefundParam.setOffsetBizOrderNo (chargeInfo.getChargeCode ());
        //订单类型, 1：全国零售订单 2：县域批发订单 3：县域零售订单 4：代销 6:虚拟订单 8:农资订单 11:增值服务-差价 12:增值服务-提点
        orderRefundParam.setOrderType (12);
        //待支付/待退回总额（不包括优惠金额,单位是分）.
        orderRefundParam.setTotalMoney(chargeInfo.getRealAmount ().multiply (new BigDecimal (100)).longValue ());

        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, chargeInfo.getSupplierCode ()).getData ();
        //供应商ID.是商家那边的供应商id
        orderRefundParam.setSupplierId (supplierInfo.getShopSupplierId ());
        //代理供应商ID（代销订单时提供)，不给
        orderRefundParam.setProxySupplierId (null);
        //物流费
//        orderRefundParam.setLogisticsMoney (new BigDecimal (0));
        orderRefundParam.setLogisticsMoneyDetails(null);//20170518

        //支付者用户ID
        orderRefundParam.setPayerUserId (chargeInfo.getPayUserId ());
        //销售县域Id
        orderRefundParam.setSaleAreaId (Long.parseLong (chargeInfo.getCountryCode ()));
        //供货县域Id,(全国零售跨县域销售、代销跨县域销售时提供),不给
        orderRefundParam.setSoureAreaId (null);
        //销售网点Id,(县域零售、全国零售时提供)
        orderRefundParam.setSaleStoreId (Long.valueOf (chargeInfo.getStoreCode ()));

        /*
         * 结算商品明细
         */
        List<GoodsDetailV1> goodsDetails = Lists.newArrayList ();
        GoodsDetailV1 goodsDetail = new GoodsDetailV1 ();
        //订单明细ID
        goodsDetail.setOrderDetailsId (chargeInfo.getId ());
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, chargeInfo.getBusinessCode ()).getData ();
        //商品Id
        goodsDetail.setGoodsId (businessInfo.getId ());
        //商品编码
        goodsDetail.setGoodsNo (chargeInfo.getBusinessCode ());
        //商品名称
        goodsDetail.setGoodsName (chargeInfo.getBusinessName ());

        //### add by zengzw #2016-11-17
        //商品销售价格（秒杀/限时折扣后的价格）
        goodsDetail.setSaleGoodsPrice(chargeInfo.getRealAmount());
        //原销售价(秒杀/限时折扣前的价格)，如果没有秒杀/限时折扣活动
        goodsDetail.setOriginalSaleGoodsPrice(chargeInfo.getRealAmount());
        //商品所使用的县域优惠券金额
        goodsDetail.setAreaCouponMoney(new BigDecimal (0));
        //##### modify end.....

        //商品供货价（代销订单时必须要传）,成本价可能=实付
        goodsDetail.setSupplierGoodsPrice (chargeInfo.getCostingAmount ());
        //订单商品数量（取下单时此商品的数量）
        goodsDetail.setBuyGoodsCount(1);
        //实际退款金额 @change
        goodsDetail.setRefundMoney (chargeInfo.getRealAmount());


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
        goodsDetail.setCommissionRatio (new BigDecimal (chargeInfo.getLiftCoefficient ()));
        //平台提点比例
        goodsDetail.setPlatformTidianRatio (new BigDecimal (0));
        //销售县域提点比例
        goodsDetail.setSaleAreaTidianRatio (new BigDecimal (0));
        //供货县域提点比例 (全国零售跨县域销售、代销跨县域销售时提供)
        goodsDetail.setSoureAreaTidianRatio (new BigDecimal (0));
        //销售县域佣金比例
        goodsDetail.setSaleAreaCommissionRatio (new BigDecimal (chargeInfo.getAreaCommissionRatio ()));
        //销售网点提点比例 （县域零售、全国零售时提供）
        goodsDetail.setSaleStoreCommissionRatio (new BigDecimal (chargeInfo.getStoreCommissionRatio ()));
        //质量原因退货时间
        goodsDetail.setQualityReturnDays (7);
        //无理由退货时间
        goodsDetail.setNoReasonReturnDays (7);
        goodsDetails.add (goodsDetail);

        //商品活动明细（有活动的情况下要传过来）
        goodsDetail.setGoodsActivityInfos(null);


        orderRefundParam.setGoodsDetails (goodsDetails);

        FundsAccept fundsAccept = null;
        Date sendTime1 = new Date ();
        Date receiveTime1;
        try {
            logger.info ("调用结算接口orderSettleService.orderSettle()：" + JSONObject.toJSONString (orderRefundParam));
            fundsAccept = orderRefundService.requestOrderRefundV1(result, orderRefundParam);
            logger.info ("调用结算接口orderSettleService.orderSettle()结果：" + JSONObject.toJSONString (fundsAccept));
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算");
        } catch (Exception e) {
            receiveTime1 = new Date ();
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求账户系统结算失败");
            logger.error ("请求账户系统退款和收回分润结算失败",e);
            throw new FunctionException (result, "请求账户系统退款和收回分润结算失败");
        }
        if (fundsAccept.getStatus () != 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求退款和收回分润结算失败");
            throw new FunctionException (result, "请求退款和收回分润结算调用失败");
        }
        if (fundsAccept.getIsRepetition () == 1) {
            this.insertHttpLog (result, chargeInfo.getChargeCode (), "orderSettleService.orderSettle", JSONObject.toJSONString (orderRefundParam), JSONObject.toJSONString (fundsAccept), sendTime1, receiveTime1, "请求结算调用失败");
            logger.error ("请求退款和收回分润结算重复调用");
            throw new FunctionException (result, "请求退款和收回分润结算重复调用");
        }

    }



    private ChargeRefund getChargeRefund(ChargeInfo chargeInfo) {
        String refundCode = "TD-" + chargeInfo.getChargeCode ();
        ChargeRefund chargeRefund = new ChargeRefund ();
        chargeRefund.setChargeCode (chargeInfo.getChargeCode ());
        chargeRefund.setRefundAmount (chargeInfo.getRealAmount ());
        chargeRefund.setRefundCode (refundCode);
        chargeRefund.setRefundTime (new Date());
        chargeRefund.setUserCode ("");
        chargeRefund.setUserMobile ("");
        chargeRefund.setUserName ("自动退款");
        return chargeRefund;
    }

    /**
     * 记录调用日志
     *
     * @param result
     * @param chargeCode
     * @param receiveMethod
     * @param sendData
     * @param receiveData
     * @param sentTime
     * @param receiveTime
     * @param remark
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result addLog(Result result, String chargeCode, String receiveMethod, String sendData, String receiveData, Date sentTime, Date receiveTime, String remark) throws Exception {
        this.insertHttpLog (result, chargeCode, receiveMethod, sendData, receiveData, sentTime, receiveTime, remark);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData ("成功");
        return result;
    }

    private void insertHttpLog(Result result, String chargeCode, String receiveMethod, String sendData, String receiveData, Date sentTime, Date receiveTime, String remark) throws Exception {
        //---------记录请求日志
        ChargePayHttpLog chargePayHttpLog = new ChargePayHttpLog ();
        //日志编号
        if (result.getUserInfo () != null) {
            chargePayHttpLog.setLogCode (result.getUserInfo ().getSessionId ());
        }
        //业务编号
        chargePayHttpLog.setChargeCode (chargeCode);
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

    public Result updateChargeStatus(Result result, String chargeCode, Integer payStatus) throws Exception {
        this.chargeInfoDao.updateStatus (result, chargeCode, payStatus);
        return result;
    }

    public Result updateChargeRefundStatus(Result result, String chargeCode, Integer refundStatus) throws Exception {
        this.chargeInfoDao.updateRefundStatus (result, chargeCode, refundStatus);
        return result;
    }

    public Result findByPayStatusAndTime(Result result, Integer chargeStatus, Integer beforeDay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        String nowYearMonthDayStr = sdf.format (new Date ());
        Date nowYearMonthDay = sdf.parse (nowYearMonthDayStr);
        Calendar rightNow = Calendar.getInstance ();
        rightNow.setTime (nowYearMonthDay);
        rightNow.add (Calendar.DAY_OF_YEAR, -beforeDay);
        Date beforeDate = rightNow.getTime ();
        List<ChargeInfo> chargeInfoList = this.chargeInfoDao.findByPayStatusAndTime (result, chargeStatus, beforeDate).getData ();
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfoList);
        return result;
    }

    public Result queryByChargeCode(Result result, String chargeCode) throws Exception {
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByChargeCode (result, chargeCode).getData ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfo);
        return result;
    }

    public Result findByPayStatusAndTimeAndRefundStatus(Result result, Integer payStatus, Integer refundStatus, Integer beforeDay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        String nowYearMonthDayStr = sdf.format (new Date ());
        Date nowYearMonthDay = sdf.parse (nowYearMonthDayStr);
        Calendar rightNow = Calendar.getInstance ();
        rightNow.setTime (nowYearMonthDay);
        rightNow.add (Calendar.DAY_OF_YEAR, -beforeDay);
        Date beforeDate = rightNow.getTime ();
        List<ChargeInfo> chargeInfoList = this.chargeInfoDao.findByPayStatusAndTimeAndRefundStatus (result, payStatus, refundStatus, beforeDate).getData ();
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfoList);
        return result;
    }

    /**
     * 打印缴费单的接口
     *
     * @param result
     * @param chargeCode
     * @return
     * @throws Exception
     */
    public Result printChargeInfo(Result result, String chargeCode) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = this.shopApi.getShop (result, storeCode).getData ();
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByChargeCode (result, chargeCode).getData ();
        String memberCard = null;
        String memberName = null;
        BigDecimal memberBalance = null;
        if (chargeInfo.getBizType ().equals (RoleType.MEMBER.getCode ())) {
            Map<String, Object> mbrParam = Maps.newHashMap ();
            mbrParam.put ("reqSource", "b2c");
            mbrParam.put ("sysType", 2);
            mbrParam.put ("token", result.getUserInfo ().getSessionId ());
            mbrParam.put ("mobile", chargeInfo.getMobile ());
            String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + "/member/app/getMemberUserInfo.do";
            String mbrResponse = HttpUtils.postContent (mbrUrl, mbrParam);

            logger.info("获取会员信息：" + mbrResponse);

            if (StringUtils.isEmpty (mbrResponse)) {
                logger.error ("获取会员信息异常");
                throw new FunctionException (result, "获取会员信息异常");
            }
            ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
            MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);
            memberCard = memberVo.getIdCard ();
            memberName = memberVo.getMemberName ();

        }
        PrintChargeVo printChargeVo = new PrintChargeVo ();

        // 订单编号
        printChargeVo.setChargeCode (chargeCode);
        // 订单类型
        printChargeVo.setChargeTypeStr ("增值服务订单");
        // 消费热线
        printChargeVo.setConsumerHotline ("400-833-2882");
        // 日期
        printChargeVo.setCreateTime (chargeInfo.getCreateTime ());
        // 优惠金额
        printChargeVo.setDiscountAmount (new BigDecimal (0));
        // 加盟店
        printChargeVo.setFranchisedStore (shopVO.getShopName ());
        // 会员卡号
        printChargeVo.setMemberCard (memberCard);
        // 会员
        printChargeVo.setMemberName (memberName);

        printChargeVo.setMemberBalance (memberBalance);
        //实收金额
        printChargeVo.setRealAmount (chargeInfo.getRealAmount ());
        //门店地址
        printChargeVo.setStoreAddr (shopVO.getContactAddress ());
        // 门店电话
        printChargeVo.setMobile (shopVO.getContactTel ());
        // 请保管
        printChargeVo.setTips ("此小票为业务支付凭据，请妥善保管！");
        // 合计
        printChargeVo.setTotalAmount (chargeInfo.getRealAmount ());
        // 谢谢惠顾，欢迎下次光临
        printChargeVo.setWelcomes ("谢谢惠顾，欢迎下次光临");
        // 产品信息
        List<PrintChargeVo.ProductInfo> productInfos = Lists.newArrayList ();
        PrintChargeVo.ProductInfo productInfo = new PrintChargeVo.ProductInfo ();
        productInfo.setAmount (chargeInfo.getRealAmount ());
        productInfo.setPrice (chargeInfo.getRealAmount ());
        productInfo.setProductName (chargeInfo.getBusinessName ());
        productInfo.setQuantity (1);
        productInfos.add (productInfo);
        printChargeVo.setProductInfos (productInfos);

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (printChargeVo);
        return result;
    }

    public Result queryRefundByChargeCode(Result result, String refundCode) throws Exception {
        ChargeRefund chargeRefund = this.chargeRefundDao.queryByRefundCode (result, refundCode).getData ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeRefund);
        return result;
    }


    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) throws Exception {
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfo);
        return result;
    }


    /**
     *  确认资金结算
     *  
     *  TODO 发送MQ失败的时候，得定时器扫描去继续通知账户中心。状态位：交易成功。(4)
     * @param result
     * @param openPlatformNo 开放平台外部订单号
     * @param immediatelySettle  缴费成功：false、失败:true
     * @return
     * @throws Exception
     */
    public Result confirmSettle(Result result, String openPlatformNo,boolean immediatelySettle) throws Exception {
        logger.info(">> PayChargeService.confirmSettle()确认结算接口请求.openPlatformNo:"+openPlatformNo);

        ChargeInfo chargeInfo = this.chargeInfoDao.queryByOpenPlatformNo (result, openPlatformNo).getData ();
        if(chargeInfo == null){
            logger.info("--->找不到订单对象,订单号:"+openPlatformNo);
            result.setStatus(500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId",chargeInfo.getId());
        paramsMap.put("orderNo",chargeInfo.getChargeCode());
        paramsMap.put("immediatelySettle",immediatelySettle);

        if(chargeInfo == null){
            logger.info(">>>> PayChargeService.confirmSettle() 订单信息为空");
            result.setStatus(500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }

        //判断状态。只有缴费成功或者失败才确认结算
        if(chargeInfo.getPayStatus().intValue() != ChargePayStatus.TRAD_SUCCESS.getCode().intValue()
                && chargeInfo.getPayStatus().intValue() != ChargePayStatus.TRAD_FAIL.getCode().intValue()
                && chargeInfo.getPayStatus().intValue() != ChargePayStatus.CHARGING.getCode().intValue()){
            logger.info(">>>> PayChargeService.confirmSettle()当前订单状态不是”交易成功“，不做处理！status:"+ChargePayStatus.getEnume(chargeInfo.getPayStatus()).getDesc());
            result.setStatus(500);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            return result;
        }

        Date begin = new Date();
        String message = JSON.toJSONString(paramsMap);
        logger.info(">>>>confirmSettle 通知确认结算MQ消息请求参数："+message);

        //发送MQ消息
        boolean flag = Producer.getInstance().producerRun(MQ_CONFIRM_SETTLE,message, null);

        logger.info(">>>>confirmSettle 通知确认结算MQ消息请求结果："+flag);
        this.insertHttpLog (result, chargeInfo.getChargeCode (), MQ_CONFIRM_SETTLE,message, flag+"", begin, new Date(), "确认结算接口");

        result.setStatus((flag?200:500));
        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }

    /**
     * 判断是否是走老业务。通过上线时间点判断
     * 
     * @param createTime 订单创建时间
     * @return
     */
    public boolean isOldBusiness(Date createTime){
        boolean  oldBusiness = true;
        //版本上线时间。如果创建时间在版本上线时间，按照老的逻辑进行退款操作。否则，走新逻辑。
        String date = TshDiamondClient.getInstance ().getConfig ("new-version-date");
        if(StringUtils.isEmpty(date)){
            logger.info("-->new-version-date 没有配置，不执行操作！");
            return oldBusiness;
        }

        Date newVersionDate = DateUtil.str2Date(date);
        oldBusiness = DateUtil.before(createTime, newVersionDate);
        logger.info("====>call payment logic control:createTime:"+createTime+" newversiondate:"+newVersionDate+" isBefor:"+oldBusiness);
        return oldBusiness;
    }


}


