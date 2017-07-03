/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.dubbo.bis.api.AreaApi;
import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.api.SupplierApi;
import com.tsh.dubbo.bis.vo.AreaInfoVO;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.dubbo.bis.vo.SupplierVO;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.traintickets.vo.request.ChargingRefundRequest;
import com.tsh.traintickets.vo.request.CheckTicketNumRequest;
import com.tsh.traintickets.vo.request.CreateOrderRequest;
import com.tsh.traintickets.vo.request.QueryOrderInfoRequest;
import com.tsh.traintickets.vo.request.QuerySubwayStationRequest;
import com.tsh.traintickets.vo.request.QueryTicketsRequest;
import com.tsh.traintickets.vo.request.RefundTicketRequest;
import com.tsh.traintickets.vo.request.VerifyUsersRequest;
import com.tsh.traintickets.vo.response.CheckTicketNumModel;
import com.tsh.traintickets.vo.response.CheckTicketNumResponse;
import com.tsh.traintickets.vo.response.VefifyUsersResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.exception.BusinessException;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.BusinessStoreShareDao;
import com.tsh.vas.dao.ChargePayHttpLogDao;
import com.tsh.vas.dao.SupplierAreaBusinessDao;
import com.tsh.vas.dao.SupplierBusinessDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.dao.trainticket.HcpCityDao;
import com.tsh.vas.dao.trainticket.HcpOrderInfoDao;
import com.tsh.vas.dao.trainticket.HcpPaymentRecordDao;
import com.tsh.vas.dao.trainticket.HcpRefundAmountDao;
import com.tsh.vas.dao.trainticket.HcpRefundTicketDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.BusinessStoreShare;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.model.trainticket.HcpCityInfo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpPaymentRecordPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.model.trainticket.HcpRefundTicketPo;
import com.tsh.vas.service.CommomService;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumPayWay;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundType;
import com.tsh.vas.trainticket.commoms.enums.EnumReturnTicketStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumSeatType;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.SMSMessageTemplateConstants;
import com.tsh.vas.trainticket.constants.UrlConstants;
import com.tsh.vas.trainticket.vo.BaseResponse;
import com.tsh.vas.trainticket.vo.MemberResultVo;
import com.tsh.vas.trainticket.vo.QueryTicketModel;
import com.tsh.vas.trainticket.vo.VerifyUserModel;
import com.tsh.vas.trainticket.vo.req.QueryOrderParam;
import com.tsh.vas.trainticket.vo.req.QueryServiceFeeParam;
import com.tsh.vas.trainticket.vo.req.RequestOrderBackParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryOrderParam;
import com.tsh.vas.trainticket.vo.req.RequestQuerySubwayStationParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketNumParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketBackParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketParam;
import com.tsh.vas.trainticket.vo.req.RequestValidateUserInfoParam;
import com.tsh.vas.trainticket.vo.resp.QueryOrderInfoResult;
import com.tsh.vas.trainticket.vo.resp.QueryTicketsResponse;
import com.tsh.vas.utils.GenerateOrderNumber;
import com.tsh.vas.vo.charge.ChargeOrgVo;
import com.tsh.vas.vo.charge.PrintChargeVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoVo;
import com.tsh.vas.vo.trainticket.SuccessOrderInfoVo;
import com.tsh.vas.vo.trainticket.TrainUserInfoVo;
import com.tsh.vas.vo.trainticket.locationbean.CityBean;
import com.tsh.vas.vo.trainticket.locationbean.DistrictBean;
import com.tsh.vas.vo.trainticket.locationbean.ProvinceBean;
import com.tsh.vas.vo.trainticket.screen.HcpOrderDetail;


/**
 * 火车票对app api service
 * 
 * @author zengzw
 * @date 2016年11月22日
 */

@Service
public class APPAPIService {

    private static final Logger logger = LoggerFactory.getLogger(APPAPIService.class);


    @Autowired
    HCPPayService payService;

    /**
     * Dubbo 城市服务]接口
     */
    @Autowired
    private AreaApi dubboAreaApi;

    @Autowired
    private HcpOrderInfoDao orderInfoDao;

    @Autowired
    private SupplierInfoDao supplierInfoDao;

    @Autowired
    private BusinessInfoDao businessInfoDao;

    @Autowired
    private HcpCityDao hcpCityDao;

    @Autowired
    private ShopApi shopApi;


    @Autowired
    private ChargePayHttpLogDao chargePayHttpLogDao;

    @Autowired
    private SupplierBusinessDao supplierBusinessDao;

    @Autowired
    private HcpPaymentRecordDao payMentRecord;

    @Autowired
    private BusinessStoreShareDao businessStoreShareDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SupplierAreaBusinessDao supplierAreaBusinessDao;


    @Autowired
    private HcpRefundAmountDao refundAmountDao;


    @Autowired
    private HcpRefundTicketDao refundTicketDao;


    @Autowired
    private SupplierApi supplierApi;


    @Autowired(required=false)
    private CommomService commomService;


    public final static int PART_SUBMIT = 1; //部分提交


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
        //根据用户id获取网点信息，用户的网点bizId就是网点id
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = this.shopApi.getShop (result, storeCode).getData ();
        logger.info("获取网点信息是：" + shopVO);

        //供应商编号，对应增值服务的supplierCode;后台根据权重判断获取获取
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findByBusinessCode (result, String.valueOf (shopVO.getAreaId ()), businessCode).getData ();
        logger.info("supplierAreaBusinessDao.findByBusinessCode 返回的list是：" + supplierAreaBusinessList);


        if(CollectionUtils.isEmpty(supplierAreaBusinessList)){
            result.setData ("");
            result.setStatus (Result.STATUS_OK);
            result.setMsg("没有可用的供应商");
            return result;
        }

        SupplierAreaBusiness supplierAreaBusiness = supplierAreaBusinessList.get(0);
        //获取开放平台供应商信息
        ServiceRegisterVo serviceRegisterVo = getServiceRegisterVo (result, supplierAreaBusiness.getSupplierCode());
        ChargeOrgVo chargeOrgVo = new ChargeOrgVo ();
        chargeOrgVo.setSupplierCode (supplierAreaBusiness.getSupplierCode());
        chargeOrgVo.setSupplierToken (serviceRegisterVo.getSignKey());
        chargeOrgVo.setServerAddr (serviceRegisterVo.getServiceAddr ());

        logger.info("getOrganizationInfo 返回的结果是：" + chargeOrgVo);
        result.setData (chargeOrgVo);
        result.setStatus (Result.STATUS_OK);
        return result;
    }


    /*
     * 获取服务商
     */
    private ServiceRegisterVo getServiceRegisterVo(Result result, String supplierCode) throws Exception {
        return commomService.getServiceRegisterVo(result, supplierCode, EnumBusinessCode.HCP);

    }


    /**
     * 获取用户城市名称
     * 
     * @param result
     * @param userInfo 用户Id
     * @return AreaInfoVO
     */
    public Result getUserCityByUser(Result result,UserInfo userInfo)throws Exception{
        //根据县域ID获取城市名称
        AreaInfoVO areaInfoVO =  dubboAreaApi.getAreaInfo(result, userInfo.getBelongId()).getData();
        if(null == areaInfoVO){
            result.setData("");
        } else {
            result.setData(handleSpecialCity(areaInfoVO.getCity()));
        }

        return result;
    }

    /**
     * 处理特殊城市
     * 
     * 格式：原始城市:显示城市;XX:XX;......
     * 
     * @param city
     * @return
     */
    private  String handleSpecialCity(String city){
        //小于3，直接返回
        if(city.length() <3){
            return city;

        }

        String dealResult = city;
        String result = TshDiamondClient.getInstance().getConfig("special_city");
        logger.info("获取的城市地区为:{}",result);

        if(StringUtils.isEmpty(result)){
            return dealResult;
        }


        String[] arrayCity = result.split(";");
        for(String fCity:arrayCity){
            if(fCity.indexOf(":")<0){
                break;
            }

            String[] replaceValue = fCity.split(":");
            if(replaceValue[0].equals(city)){
                dealResult = replaceValue[1];
                break;
            }

        }

        return dealResult;
    }





    /**
     * 获取车次列表
     * @param result
     * @param param
     * @return
     */
    public Result queryTrainList(Result result,RequestQueryTicketParam param) {
        //获取开放平台供应商的信息
        ServiceRegisterVo serviceRegisterVo;
        try {
            serviceRegisterVo = this.getServiceRegisterVo(result, param.getSupplierCode());
            //生存
            QueryTicketsRequest request = new QueryTicketsRequest();
            BeanUtils.copyProperties(param, request);
            String signKey = KuyouRequestSign.signQueryTickets(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            //转换位Map参数
            Map<String, Object> params = ObjectMapUtils.toObjectMap(request);

            //调用接口
            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.LIST_TRAING_TICKETS;
            String responeMsg =  HttpUtils.doPost(url, params, HttpUtils.charset_utf8);
            BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
            if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(response.getData());
            } else {
                result.setStatus(HttpResponseConstants.ERROR);
            }
        } catch (Exception e) {
            logger.error("查询出错");
            result.setStatus(HttpResponseConstants.ERROR);
        }


        return result;

    }


    /**
     * 查询购票订单的详情（open PF)
     * 
     * @param result
     * @param param
     * @return
     */
    public Result queryOrderInfoForRefund(Result result, RequestQueryOrderParam param){
        try {
            // 默认能退票
            result.setData("1");

            ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, param.getSupplierCode());
            //生存
            QueryOrderInfoRequest request = new QueryOrderInfoRequest();
            request.setOrderId(param.getOrderId());
            request.setMerchantOrderId(param.getMerchantOrderId());
            String signKey = KuyouRequestSign.signQueryOrderInfo(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            //转换位Map参数
            Map<String, Object> params = ObjectMapUtils.toObjectMap(request);
            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.QUERY_ORDER;
            String responeMsg =  HttpUtils.doPost(url, params);
            if(responeMsg != null){
                QueryOrderInfoResult queryResult =  JSON.parseObject(responeMsg,QueryOrderInfoResult.class);
                if(queryResult.getStatus() == HttpResponseConstants.SUCCESS){
                    result.setStatus(HttpResponseConstants.SUCCESS);
                    if(null != queryResult.getData() && null != queryResult.getData().getOrderInfo()){
                        // 如果供应商返回“REFUSE_REFUND”（拒绝退款），不能再退票
                        if("REFUSE_REFUND".equals(queryResult.getData().getOrderInfo().getRefundStatus())){
                            result.setData("0");
                        }
                    }
                } else {
                    result.setStatus(HttpResponseConstants.ERROR);
                    logger.info("==>获取订单详情出错.status:{},queryParam:{}",HttpResponseConstants.SUCCESS,JSON.toJSONString(params));
                }
            }else{
                result.setStatus(HttpResponseConstants.ERROR);
            }

        } catch (Exception e){
            logger.error("查询失败供应商失败",e);
            result.setStatus(HttpResponseConstants.ERROR);
        }

        return result;

    }

    /**
     * 计算可退金额
     * @param result
     * @param hcpOrderInfoPo
     * @return
     * @throws Exception
     */
    private Result getCanRefundAmount(Result result, HcpOrderInfoPo hcpOrderInfoPo) throws Exception{
        ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, hcpOrderInfoPo.getSupplierCode());
        ChargingRefundRequest request = new ChargingRefundRequest();
        request.setTrainPrice(String.valueOf(hcpOrderInfoPo.getCostingAmount()));
        String ticketTime = hcpOrderInfoPo.getTravelTime() + hcpOrderInfoPo.getStationStartTime();
        request.setTicketTime(ticketTime);
        request.setSignKey(KuyouRequestSign.signChargingRefund(request, serviceRegisterVo.getSignKey()));

        //转换位Map参数
        Map<String, Object> params = ObjectMapUtils.toObjectMap(request);
        String url = serviceRegisterVo.getServiceAddr() + UrlConstants.CHARGINT_REFUND_AMOUNT;
        String responeMsg =  HttpUtils.doPost(url, params);
        BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
        if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setData(response.getData());
        }
        return result;
    }

    /**
     * 查询车次途经点
     * @param result
     * @param queryTicketParam
     * @return
     */
    public Result querySubwayStation(Result result, RequestQuerySubwayStationParam queryTicketParam){
        try {
            //获取开放平台供应商的信息
            ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, queryTicketParam.getSupplierCode());
            //生存
            QuerySubwayStationRequest request = new QuerySubwayStationRequest();
            BeanUtils.copyProperties(queryTicketParam, request);
            String signKey = KuyouRequestSign.signQuerySubwayStation(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.QUERY_SUBWAY_STATION;
            Map<String, Object> params = ObjectMapUtils.toObjectMap(request);
            String responeMsg =  HttpUtils.doPost(url, params);
            BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
            if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(response.getData());
            } else {
                result.setStatus(HttpResponseConstants.ERROR);
            }
        }catch(Exception e){
            logger.error("获取查询车次的途径所有站点列表信息",e);
            result.setStatus(HttpResponseConstants.ERROR);
        }

        return result;

    }


    /**
     * 查询车次当前所有座位的余票信息
     * @param result
     * @param params
     * @return
     */
    public Result queryTicketNum(Result result,RequestQueryTicketNumParam params){
        try {
            //获取开放平台供应商的信息
            ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, params.getSupplierCode());

            //签名校验
            CheckTicketNumRequest request = new CheckTicketNumRequest();
            BeanUtils.copyProperties(params, request);
            String signKey = KuyouRequestSign.signQueryTicketNum(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.QUERY_TICKET_NUM;
            Map<String, Object> mapParams = ObjectMapUtils.toObjectMap(request);
            String responeMsg =  HttpUtils.doPost(url, mapParams);
            BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
            if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(response.getData());
            }else{
                result.setStatus(HttpResponseConstants.ERROR);
            }
        }catch(Exception e){
            logger.error("queryTicketNum出错啦.",e);
            result.setStatus(HttpResponseConstants.ERROR);
        }

        return result;

    }



    /**
     * 校验 同个人同一天购买同个车次的
     * 
     * @param result
     * @param params
     * @return
     */
    private  List<String> validateSameTrainCode(Result result,RequestValidateUserInfoParam params){
        String trainCode = params.getTrainCode();
        String trainTime = params.getTrainTime();


        List<String> lstResult = new ArrayList<>();
        List<VerifyUserModel> lstUser = params.getUserModels();
        for(VerifyUserModel user:lstUser){
            //判断订单表是否已经存在相同记录
            List<HcpOrderInfoPo> orderInfoList =  orderInfoDao.queryCurrentDaySameTrainNum(result, trainCode, trainTime, user.getUserId(), EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode()).getData();


            if(orderInfoList != null && !orderInfoList.isEmpty()){
                for(HcpOrderInfoPo hcpOrderInfoPo : orderInfoList){
                    HcpRefundTicketPo refundTicketPo = refundTicketDao.queryByOrderCode(result, hcpOrderInfoPo.getHcpOrderCode()).getData();
                    //如果存在，判断退票是否已经成功。退票成功可以继续购票
                    if(null == refundTicketPo || EnumReturnTicketStatus.RETURN_SUCCESS.getCode().intValue() != refundTicketPo.getRefundStatus().intValue()){
                        lstResult.add(user.getUserName());
                        break;
                    }
                }
            }
        }

        return lstResult;
    }

    /**
     * 校验用户信息
     * @param result
     * @param params
     * @return
     */
    public Result verifyUsers(Result result,RequestValidateUserInfoParam params){

        try {

            //校验相同车次的
            List<String> lstResult = validateSameTrainCode(result,params);
            if(CollectionUtils.isNotEmpty(lstResult)){
                result.setStatus(HttpResponseConstants.SUCCESS);
                VefifyUsersResponse response = new VefifyUsersResponse();
                response.setValidateFailList(lstResult);
                response.setFailUserNameList(Collections.<String>emptyList());
                result.setData(response);
                result.setMsg("一天内不能购买相同的车次");

                return result;
            }



            //获取开放平台供应商的信息
            ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, params.getSupplierCode());

            //签名校验
            VerifyUsersRequest request = new VerifyUsersRequest();
            request.setUserList(JSON.toJSONString(params.getUserModels()));
            String signKey = KuyouRequestSign.signVerifyUsers(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.VALIDATE_USER_INFO;
            Map<String, Object> reqParams = ObjectMapUtils.toObjectMap(request);
            String responeMsg =  HttpUtils.doPost(url, reqParams);
            BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
            if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(response.getData());
            } else {
                result.setStatus(HttpResponseConstants.ERROR);
            }

        }catch(Exception e){
            logger.error("校验用户出错啦",e);
            result.setStatus(HttpResponseConstants.ERROR);
        }
        return result;

    }


    /**
     * 获取平台服务费
     * @param result
     * @param serviceFeeParam
     * @return
     * @throws BusinessException
     */
    public Result getServiceFee(Result result, QueryServiceFeeParam serviceFeeParam) throws BusinessException{
        try {
            supplierBusinessDao.queryBySupplierCodeAndBusinessCode(result, serviceFeeParam.getSupplierCode(), serviceFeeParam.getBusinessCode());
        } catch (Exception e) {
            logger.error("数据库查询服务费异常", e);
        }
        SupplierBusiness supplierBusiness = result.getData();
        if(null != supplierBusiness){
            result.setData(supplierBusiness.getTotalShareRatio());
        } else {
            result.setData(0);
        }

        return result;
    }


    /**
     * 订单支付
     * @param result
     * @param orderInfoVo
     * @return
     * @throws Exception
     */
    public Result payOdrerInfo(Result result, HcpOrderInfoVo orderInfoVo) throws Exception {

        //校验单张表的总金额(realAmount=单张票用户支付价格)
        boolean flag = validateHcpPrice(result, orderInfoVo);
        if(!flag){
            //价格不对
            result.setCode (ResponseCode.REQUEST_EXCEPTION_PRICE.getCode());
            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setMsg("单票总金额有误");

            return result;
        }

        //校验是否支持 余票不足 部分提交
        boolean paratSubmit = isParatSubmit(result,orderInfoVo);
        if(!paratSubmit){
            //价格不对
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode());
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("余票不足");
            return result;
        }

        //生成订单,保存订单信息，订单状态为待支付
        List<HcpOrderInfoPo> lstOrderInfoPo = this.addOrderInfo (result, orderInfoVo);
        List<SuccessOrderInfoVo> successOrderInfoVoList = new ArrayList<>();
        for(HcpOrderInfoPo orderInfo:lstOrderInfoPo){

            //调用支付接口
            payService.pay (result, orderInfo);

            Integer payStatus = 0; //支付成功，失败标示
            if(result.getStatus() == HttpResponseConstants.SUCCESS){
                payStatus = 1;
                //订单状态为支付中，后面有个支付消息中间件回调继续下面的步骤
                this.orderInfoDao.updateStatus (result, orderInfo.getHcpOrderCode(), EnumOrderInfoPayStatus.PAIDING.getCode ());
                logger.info("----订单：{}支付同步接口成功，订单状态位：支付中【2】",orderInfo.getHcpOrderCode());
            }else{
                this.orderInfoDao.updateStatus (result, orderInfo.getHcpOrderCode(), EnumOrderInfoPayStatus.PAY_FAIL.getCode ());
                logger.info("----订单：{}支付同步接口成功，订单状态位：支付异常【6】",orderInfo.getHcpOrderCode());
            }

            //设置返回信息
            SuccessOrderInfoVo sInfoVo = new SuccessOrderInfoVo();
            sInfoVo.setHcpOrderCode(orderInfo.getHcpOrderCode());
            sInfoVo.setUsername(orderInfo.getUserName());
            sInfoVo.setMobile(orderInfo.getMobile());
            sInfoVo.setServicePrice(String.valueOf(orderInfo.getServicePrice()));
            sInfoVo.setCostingAmount(String.valueOf(orderInfo.getCostingAmount()));
            sInfoVo.setRealAmount(String.valueOf(orderInfo.getRealAmount()));
            sInfoVo.setPayStatus(payStatus);
            sInfoVo.setCreateTime(DateUtils.format(orderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            successOrderInfoVoList.add(sInfoVo);
        }

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData(successOrderInfoVoList);
        return result;
    }


    /**
     * 判断是否支持部分提交
     * @param result
     * @param orderInfo
     * @return
     */
    private boolean isParatSubmit(Result result,HcpOrderInfoVo orderInfo){
        boolean isSubmit = false;
        if(orderInfo.getPartSubmit() == PART_SUBMIT){
            return !isSubmit;
        }

        RequestQueryTicketNumParam params = new RequestQueryTicketNumParam();
        params.setFromStation(orderInfo.getFromStation());
        params.setArriveStation(orderInfo.getArriveStation());
        params.setTrainCode(orderInfo.getTrainCode());
        params.setTravelTime(orderInfo.getTravelTime());
        params.setSupplierCode(orderInfo.getSupplierCode());

        Object data = this.queryTicketNum(result, params).getData();
        logger.info("---查询余票返回的结果:{},data:{}",result.getStatus(),result.getData());

        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            throw new BusinessRuntimeException("", "查询接口余票接口出错！");
        }


        String checkTicketJsonStr =  JSON.toJSONString(data);
        CheckTicketNumResponse queryTicketsResponse = JsonUtils.convert2Object(checkTicketJsonStr, CheckTicketNumResponse.class);
        if(queryTicketsResponse == null){
            throw new BusinessRuntimeException("", "查询余票出错!");
        }

        CheckTicketNumModel checkTicket = queryTicketsResponse.getData();
        Integer seatType = orderInfo.getSeatType();
        String number = "0"; //默认值为0，不支持部分提交。
        if(null != seatType){
            switch (seatType) {
            case 0://商务座
                number = checkTicket.getSwzNum();
                break;
            case 1: //特等座
                number = checkTicket.getTdzNum();
                break;
            case 2: //一等座
                number = checkTicket.getRz1Num();
                break;
            case 3: //二等座
                number = checkTicket.getRz2Num();
                break;
            case 4: //高级软卧
                number = checkTicket.getGwNum();
                break;
            case 5: //软卧
                number = checkTicket.getRwNum();
                break;
            case 6: //硬卧
                number = checkTicket.getYwNum();
                break;
            case 7: //软座
                number = checkTicket.getRzNum();
                break;
            case 8: //硬座
                number = checkTicket.getYzNum();
                break;
            case 9: //无座
                number = checkTicket.getWzNum();
                break;
            default: //其他
                logger.info("非法座位类型：" + seatType);
                break;
            }
        }

        //接口原因，特殊处理。没有票的时候显示 “-”。
        if(number.equals("-")){
            return isSubmit;
        }

        int size = orderInfo.getUserDetailList().size(); //用户购票数
        int ticketCount = Integer.parseInt(number); //余票数
        return (ticketCount>=size);

    }


    /**
     * 验证火车票价格
     * @param result
     * @param orderInfoVo
     * @return 
     * @throws Exception 
     */
    private boolean validateHcpPrice(Result result,HcpOrderInfoVo orderInfoVo) throws Exception {
        logger.info("下单请求参数：" + orderInfoVo);

        boolean validateResult = false;
        String travelTime = orderInfoVo.getTravelTime(); //乘车日期
        String from = orderInfoVo.getFromStation(); //开始站
        String to = orderInfoVo.getArriveStation(); //到站
        String trainCode = orderInfoVo.getTrainCode();
        RequestQueryTicketParam param = new RequestQueryTicketParam();
        param.setTravelTime(travelTime);
        param.setFromStation(from);
        param.setArriveStation(to);
        param.setSupplierCode(orderInfoVo.getSupplierCode());
        //通过http查询第三方车票价格信息
        Object obj = queryTrainList(result,param).getData();
        String json = JSON.toJSONString(obj);
        QueryTicketsResponse queryTicketsResponse = null;
        try{
            queryTicketsResponse = JsonUtils.convert2Object(json, QueryTicketsResponse.class);
        } catch (Exception e){
            logger.error("查询车次列表转json异常:" + json);
            logger.error(e.getMessage(), e);
        }
        if(null == queryTicketsResponse){
            result.setData("已过可购票时间了,请重新选择");//错误信息屏端显示
            return validateResult;
        }
        logger.info("获取车次列表返回的结果：" + JSON.toJSONString(queryTicketsResponse));

        List<QueryTicketModel> dataList = queryTicketsResponse.getDataList();

        logger.info("获取车次列表返回dataList：" + JSON.toJSONString(dataList));

        QueryTicketModel queryTicketModel = null;
        for(QueryTicketModel ticketModel : dataList){
            if(trainCode.equals(ticketModel.getTrainCode())){
                queryTicketModel = ticketModel;
                break;
            }
        }
        logger.info("获取车次列表返回queryTicketModel：" + JSON.toJSONString(queryTicketModel));
        if(null == queryTicketModel){
            result.setData("已过可购票时间了,请重新选择");//错误信息屏端显示
            return validateResult;
        }

        Integer seatType = orderInfoVo.getSeatType();
        String price = "0";
        if(null != seatType){
            switch (seatType) {
            case 0://商务座
                price = queryTicketModel.getSwz();
                break;
            case 1: //特等座
                price = queryTicketModel.getTdz();
                break;
            case 2: //一等座
                price = queryTicketModel.getRz1();
                break;
            case 3: //二等座
                price = queryTicketModel.getRz2();
                break;
            case 4: //高级软卧
                logger.info("非法座位类型：" + seatType);
                break;
            case 5: //软卧
                logger.info("非法座位类型：" + seatType);
                break;
            case 6: //硬卧
                logger.info("非法座位类型：" + seatType);
                break;
            case 7: //软座
                price = queryTicketModel.getRz();
                break;
            case 8: //硬座
                price = queryTicketModel.getYz();
                break;
            case 9: //无座
                price = queryTicketModel.getWz();
                break;
            default: //其他
                logger.info("非法座位类型：" + seatType);
                break;
            }
        }

        SupplierBusiness supplierBusiness = supplierBusinessDao.queryBySupplierCodeAndBusinessCode(result, orderInfoVo.getSupplierCode(), orderInfoVo.getBusinessCode()).getData();
        logger.info("服务费对象为：" + supplierBusiness);
        //获取服务费
        BigDecimal fwf = new BigDecimal(supplierBusiness.getTotalShareRatio());
        BigDecimal dz = new BigDecimal(price).add(fwf);
        logger.info("单张票传过来的的总价格为：" + orderInfoVo.getRealAmount());
        logger.info("单张票计算的总价格为：" + dz);
        if(null != orderInfoVo.getRealAmount() && orderInfoVo.getRealAmount().compareTo(dz) == 0){
            validateResult = true;
        }else {
            result.setData("单张票总价格错误");
        }
        return validateResult;
    }


    /**
     * 退票接口
     * 
     * 同步接口 返回异常 直接回滚数据
     * 
     * @param result
     * @param params
     * @return
     */
    public Result returnTicket(Result result,RequestReturnTicketParam params){
        try {
            //获取订单信息
            HcpOrderInfoPo orderInfo = orderInfoDao.queryByOrderCode(result, params.getOrderNo()).getData();
            if(orderInfo.getPayStatus().intValue() != EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("不合理的操作");
                return result;
            }

            //获取开放平台供应商的信息
            ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, orderInfo.getSupplierCode());


            //生成退票单
            HcpRefundTicketPo returnTicket = this.createReturnTicketModel(result,orderInfo);


            //生存signKey
            RefundTicketRequest request = new RefundTicketRequest();
            request.setComment("用户主动退票");
            request.setMerchantOrderId(orderInfo.getOpenPlatformNo());//当前用户下单的订单ID
            request.setOrderId(orderInfo.getSupplierOrderId());//供应商返回的订单ID
            request.setRefundType("all");
            request.setRequestId(returnTicket.getRefundCode());//退票流水号
            String signKey = KuyouRequestSign.signRefundTicket(request, serviceRegisterVo.getSignKey());
            request.setSignKey(signKey);

            //转换位Map参数，请求Broken进行退票
            Map<String, Object> mapPrams = ObjectMapUtils.toObjectMap(request);
            String url = serviceRegisterVo.getServiceAddr() + UrlConstants.RETURN_TICKEET;
            String responeMsg =  HttpUtils.doPost(url, mapPrams);

            BaseResponse response = JsonUtils.convert2Object(responeMsg, BaseResponse.class);
            if(response != null && response.getStatus() == HttpResponseConstants.SUCCESS){
                logger.info("-->退票请求成功，单号：{}，退票单号：{},正在退票中【2】",orderInfo.getOpenPlatformNo(),returnTicket.getRefundCode());
                insertHttpLog(result,orderInfo.getHcpOrderCode(),"APPAPIService.returnTicket",JSON.toJSONString(request),responeMsg,null,null,"用户退票成功");

                //退票成功，修改位【退票中：2】
                refundTicketDao.updateStatusByOrderCode(result, returnTicket.getHcpOrderCode(), EnumReturnTicketStatus.RETURNING.getCode());

                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(response.getData());
            }else{
                logger.info("-->退票请求失败，单号：{}，退票单号：{},退票失败【5】",orderInfo.getOpenPlatformNo(),returnTicket.getRefundCode());
                /*  insertHttpLog(result,orderInfo.getHcpOrderCode(),"APPAPIService.returnTicket",JSON.toJSONString(request),responeMsg,null,null,"用户退票失败");

                //退票失败，修改为【退票失败：5】
                refundTicketDao.updateStatusByOrderCode(result, returnTicket.getHcpOrderCode(), EnumReturnTicketStatus.REFUND_FAIL.getCode());

                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("退票失败");*/

                throw new BusinessRuntimeException("", "退票失败");
            }
        } catch (Exception e) {
            logger.error("退票查询出错",e);
            throw new BusinessRuntimeException("", "退票失败");
            /* result.setMsg("退票请求异常");
            result.setStatus(HttpResponseConstants.ERROR);*/
        }

        return result;
    }


    /*
     * 
     * 生成退票单
     * 
     * @param orderinfo
     */
    private  HcpRefundTicketPo createReturnTicketModel(Result result,HcpOrderInfoPo orderinfo){
        //存在的话直接返回,且修改当前状态位‘待退票’，创建时间位当前时间。FIXME 由于原来的设计存在缺陷订单-退票关系为1-1二不是1-n，还有第三方线下退票没有通知到系统。
        HcpRefundTicketPo refundTicket =  refundTicketDao.queryByOrderCode(result, orderinfo.getHcpOrderCode()).getData();
        if(refundTicket != null){
            logger.info("-已存在退票单，修改状态，创建时间，返回对象。单号:{},退票单号：{}",refundTicket.getHcpOrderCode(),refundTicket.getRefundCode());
            refundTicket.setCreateTime(new Date());
            refundTicket.setRefundStatus(EnumReturnTicketStatus.NON_REFUND.getCode());
            refundTicketDao.update(refundTicket);

            return refundTicket;
        }

        //重新生成退票单
        refundTicket = new HcpRefundTicketPo();
        refundTicket.setCreateTime(new Date());
        refundTicket.setHcpOrderCode(orderinfo.getHcpOrderCode());
        refundTicket.setRealAmount(orderinfo.getRealAmount());
        refundTicket.setRefundCode("TP-"+orderinfo.getHcpOrderCode());
        refundTicket.setRefundStatus(EnumReturnTicketStatus.NON_REFUND.getCode());
        refundTicket.setRemark("用户退票");
        refundTicketDao.addHcpRefundTicket(result,refundTicket);

        return refundTicket;
    }



    /**
     * 退票接口 异步回调
     * @param result
     * @param callbackParam
     * @return
     */
    public Result returnTicketCallBack(Result result,RequestReturnTicketBackParam callbackParam){
        Assert.notNull(callbackParam, "callbackParma 参数为空");

        HcpRefundTicketPo ticketPo =  refundTicketDao.queryByReturnTicketCode(result, callbackParam.getRequestId()).getData();
        if(ticketPo.getRefundStatus().intValue() != EnumReturnTicketStatus.RETURNING.getCode().intValue()){
            logger.info("---->退票单不合理的状态转换，当前状态为：{}",ticketPo.getRefundStatus());

            result.setStatus(HttpResponseConstants.ERROR);
            return result;
        }

        HcpOrderInfoPo orderInfo = orderInfoDao.queryByOrderCode(result, ticketPo.getHcpOrderCode()).getData();

        //全部成功
        if(callbackParam.getStatus().equals("SUCCESS")){
            logger.info("----->退票成功，开始进行退款,退票单号：{}",callbackParam.getRequestId());

            BigDecimal actualMoney = new BigDecimal(callbackParam.getRefundTotalAmount());
            //修改实际退款金额，供应商流水，状态
            ticketPo.setRefundAmount(actualMoney); //修改退款金额
            ticketPo.setSupplierRecord(callbackParam.getTripNo()); //供应商流水
            ticketPo.setRefundStatus(EnumReturnTicketStatus.RETURN_SUCCESS.getCode());
            refundTicketDao.update(ticketPo);

            //创建退款单,退票退款单
            createRefundOrder(result, ticketPo.getHcpOrderCode(),actualMoney, EnumRefundType.RETURN_TICKET.getType(), EnumRefundType.RETURN_TICKET.getName());


            //短信提示
            String message = String.format(SMSMessageTemplateConstants.RETURN_TICKET_SUCCESS,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode(),ticketPo.getRefundAmount());
            smsService.sendSms(orderInfo.getMemberMobile(), message);

            result.setStatus(HttpResponseConstants.SUCCESS);
        }
        else if(callbackParam.getStatus().equals("FAILURE")){//退票失败
            logger.info("------>退票失败");
            //修改退票状态、失败理由
            ticketPo.setFailReason(callbackParam.getFailReason());
            ticketPo.setRefundStatus(EnumReturnTicketStatus.REFUND_FAIL.getCode());
            refundTicketDao.update(ticketPo);

            result.setMsg(callbackParam.getFailReason());
            result.setStatus(HttpResponseConstants.ERROR);

            //短信提示
            String message = String.format(SMSMessageTemplateConstants.RETURN_TICKET_FAIL,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode());
            smsService.sendSms(orderInfo.getMemberMobile(), message);
        }
        else if(callbackParam.getStatus().equals("PART")){
            //部分成功
            logger.info("----> 退票部分成功");

            result.setMsg("部分成功，当前业务不支持");
            result.setStatus(HttpResponseConstants.ERROR);
        }


        return result;
    }



    /**
     * 获取订单列表
     * @param result
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Result queryOrderList(Result result, QueryOrderParam queryOrderParam){
        //根据网点信息获取网点购买的车票列表
        UserInfo userInfo = result.getUserInfo();

        HcpOrderInfoPo hcpOrderInfoPo = new HcpOrderInfoPo();
        hcpOrderInfoPo.setStoreCode(userInfo.getBizId().toString());

        // 判断分页信息
        Page page = new Page();
        if(StringUtils.isEmpty(queryOrderParam.getPageNo())){
            page.setPageNo(1);
        } else {
            page.setPageNo(Integer.valueOf(queryOrderParam.getPageNo()));
        }
        if(StringUtils.isEmpty(queryOrderParam.getPageSize())){
            page.setPageSize(10);
        } else {
            page.setPageSize(Integer.valueOf(queryOrderParam.getPageSize()));
        }

        if(StringUtils.isNotEmpty(queryOrderParam.getSearchInfo())){
            hcpOrderInfoPo.setHcpOrderCode(queryOrderParam.getSearchInfo());
            hcpOrderInfoPo.setMobile(queryOrderParam.getSearchInfo());
        }

        List<Integer> statusList = new ArrayList();
        List<String> orderCodeList = new ArrayList();
        if(StringUtils.isNotEmpty(queryOrderParam.getType())){
            switch (queryOrderParam.getType()){

            case "1"://等待结果列表
                statusList.add(EnumOrderInfoPayStatus.NON_PAYMENT.getCode());
                statusList.add(EnumOrderInfoPayStatus.PAIDING.getCode());
                statusList.add(EnumOrderInfoPayStatus.PAY_SUCCESS.getCode());
                statusList.add(EnumOrderInfoPayStatus.TRADING.getCode());
                break;
            case "2"://已成功列表
                statusList.add(EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode());
                break;
            case "3"://已失败列表
                statusList.add(EnumOrderInfoPayStatus.PAY_FAIL.getCode());
                statusList.add(EnumOrderInfoPayStatus.TRAIN_FAIL.getCode());
                statusList.add(EnumOrderInfoPayStatus.TICKET_FAIL.getCode());
                break;
            case "4"://已退票列表，先从退票表查询退票成功的记录，再查订单表
                HcpRefundTicketPo refundTicketPo = new HcpRefundTicketPo();
                refundTicketPo.setRefundStatus(EnumReturnTicketStatus.RETURN_SUCCESS.getCode());
                refundTicketDao.queryList(result, page, refundTicketPo);
                if(null != result.getData()){
                    Pagination pagination = result.getData();
                    if(null != pagination.getRows() && !pagination.getRows().isEmpty()){
                        List<HcpRefundTicketPo> refundList = (List) pagination.getRows();
                        if(null != refundList && !refundList.isEmpty()){
                            for(HcpRefundTicketPo refundtPo : refundList){
                                orderCodeList.add(refundtPo.getHcpOrderCode());
                            }
                        }
                    }
                    if(orderCodeList.isEmpty()){
                        orderCodeList.add("empty");
                    }
                }
                break;
            }
        }
        orderInfoDao.queryHcpOrderInfoListForScreen(result, page, hcpOrderInfoPo, statusList, orderCodeList, queryOrderParam.getType());
        List<HcpOrderInfoPo> orderInfoPoList = result.getData();
        List<HcpOrderDetail> orderList = new ArrayList();
        if(null != orderInfoPoList && !orderInfoPoList.isEmpty()){
            for(HcpOrderInfoPo orderInfoPo : orderInfoPoList){
                HcpOrderDetail detail = this.convertOrderInfoPo2Vo(orderInfoPo);
                // 查询已成功列表，需要查询退票状态
                if("2".equals(queryOrderParam.getType())){
                    detail = this.queryRefundTicketStatus(result, detail, orderInfoPo.getHcpOrderCode());
                }
                // 查询已退票列表，需要查询退款状态
                if("4".equals(queryOrderParam.getType())){
                    detail.setRefundTicketStatus(String.valueOf(EnumReturnTicketStatus.RETURN_SUCCESS.getCode()));
                    detail.setRefundTicketStatusName(EnumReturnTicketStatus.RETURN_SUCCESS.getDesc());
                    detail.setIsRefundTicket("1");
                    detail = this.queryRefundAmountStatus(result, detail, orderInfoPo.getHcpOrderCode());
                }
                if(StringUtils.isEmpty(queryOrderParam.getType())){
                    detail = this.queryRefundTicketStatus(result, detail, orderInfoPo.getHcpOrderCode());
                    detail = this.queryRefundAmountStatus(result, detail, orderInfoPo.getHcpOrderCode());
                }
                orderList.add(detail);
            }
        }
        result.setData(orderList);
        return result;

    }

    private HcpOrderDetail queryRefundTicketStatus(Result result, HcpOrderDetail detail, String orderCode){
        refundTicketDao.queryByOrderCode(result, orderCode);
        HcpRefundTicketPo refundTicketPo = result.getData();
        if(null != refundTicketPo){
            detail.setRefundTicketStatus(String.valueOf(refundTicketPo.getRefundStatus()));
            detail.setRefundTicketStatusName(EnumReturnTicketStatus.getEnume(refundTicketPo.getRefundStatus()).getDesc());
            if(EnumReturnTicketStatus.RETURN_SUCCESS.getCode().toString().equals(refundTicketPo.getRefundStatus().toString()) ){
                detail.setIsRefundTicket("1");
            } else {
                detail.setIsRefundTicket("0");
            }
        }
        return detail;
    }

    private HcpOrderDetail queryRefundAmountStatus(Result result, HcpOrderDetail detail, String orderCode){
        refundAmountDao.queryByOrderCode(result, orderCode);
        HcpRefundAmountPo refundAmountPo = result.getData();
        if(null != refundAmountPo){
            detail.setRefundStatus(String.valueOf(refundAmountPo.getStatus()));
            detail.setRefundStatusName(EnumRefundOrderStatus.getEnume(refundAmountPo.getStatus()).getClientDesc());
        }
        return detail;
    }

    /**
     * @param hcpOrderInfoPo
     * @return
     */
    private HcpOrderDetail convertOrderInfoPo2Vo(HcpOrderInfoPo hcpOrderInfoPo){
        HcpOrderDetail hcpOrderDetail = null;
        if(null != hcpOrderInfoPo) {
            hcpOrderDetail = new HcpOrderDetail();
            hcpOrderDetail.setChargeCode(hcpOrderInfoPo.getHcpOrderCode());
            hcpOrderDetail.setPayStatus(String.valueOf(hcpOrderInfoPo.getPayStatus()));
            hcpOrderDetail.setPayStatusName(EnumOrderInfoPayStatus.getEnume(Integer.valueOf(hcpOrderInfoPo.getPayStatus())).getClientDesc());
            hcpOrderDetail.setRechargeUserCode(hcpOrderInfoPo.getUserName() + "  " + hcpOrderInfoPo.getMobile());
            hcpOrderDetail.setCreateTime(DateUtils.format(hcpOrderInfoPo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            hcpOrderDetail.setCreateTimeStr(DateUtils.format(hcpOrderInfoPo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            hcpOrderDetail.setTrainCode(hcpOrderInfoPo.getTrainCode());
            hcpOrderDetail.setRealAmount(String.valueOf(hcpOrderInfoPo.getRealAmount()));
            hcpOrderDetail.setSeatType(String.valueOf(hcpOrderInfoPo.getSeatType()));
            hcpOrderDetail.setSeatTypeName(EnumSeatType.getEnume(hcpOrderInfoPo.getSeatType()).getName());
            hcpOrderDetail.setFromStation(hcpOrderInfoPo.getFromStation());
            hcpOrderDetail.setArriveStation(hcpOrderInfoPo.getArriveStation());
            hcpOrderDetail.setStationStartTime(hcpOrderInfoPo.getTravelTime() + "  " + hcpOrderInfoPo.getStationStartTime());
            hcpOrderDetail.setStationArriveTime(this.calArriveTime(hcpOrderInfoPo.getTravelTime(), hcpOrderInfoPo.getStationStartTime(), hcpOrderInfoPo.getCostTime()) + "  " + hcpOrderInfoPo.getStationArriveTime());
            hcpOrderDetail.setOriginalAmount(String.valueOf(hcpOrderInfoPo.getOriginalAmount()));
            hcpOrderDetail.setCostingAmount(String.valueOf(hcpOrderInfoPo.getCostingAmount()));
            hcpOrderDetail.setServicePrice(String.valueOf(hcpOrderInfoPo.getServicePrice()));
            hcpOrderDetail.setMobile(hcpOrderInfoPo.getLinkPhone());
            hcpOrderDetail.setPayUserName(hcpOrderInfoPo.getLinkName());
            hcpOrderDetail.setPayWay(String.valueOf(hcpOrderInfoPo.getBizType()));
            hcpOrderDetail.setPayWayName(EnumPayWay.getEnume(hcpOrderInfoPo.getBizType()).getMsg());
            hcpOrderDetail.setUserName(hcpOrderInfoPo.getUserName());
            hcpOrderDetail.setMerchantOrderId(hcpOrderInfoPo.getOpenPlatformNo());
            hcpOrderDetail.setOrderId(hcpOrderInfoPo.getSupplierOrderId());
            hcpOrderDetail.setCostTime(hcpOrderInfoPo.getCostTime());
        }
        return hcpOrderDetail;
    }

    private String calArriveTime(String travelDate, String stationStartTime, String costTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date date = sdf.parse(travelDate + " " + stationStartTime);
            return DateUtils.format(new Date(date.getTime() + Long.valueOf(costTime)*60*1000l), "yyyy-MM-dd");
        } catch(Exception e){}
        return null;
    }

    /**
     * 查看订单详情
     * 
     * @param result
     * @param orderCode
     * @return
     */
    public  Result queryOrderDetail(Result result,String orderCode){
        orderInfoDao.queryByOrderCode(result, orderCode);
        HcpOrderInfoPo hcpOrderInfoPo = result.getData();
        if(null != hcpOrderInfoPo){
            HcpOrderDetail hcpOrderDetail = this.convertOrderInfoPo2Vo(hcpOrderInfoPo);
            // 获取退票状态
            refundTicketDao.queryByOrderCode(result, orderCode);
            HcpRefundTicketPo refundTicketPo = result.getData();
            if(null != refundTicketPo){
                hcpOrderDetail.setRefundTicketStatus(String.valueOf(refundTicketPo.getRefundStatus()));
                hcpOrderDetail.setRefundTicketStatusName(EnumReturnTicketStatus.getEnume(refundTicketPo.getRefundStatus()).getDesc());
                if(EnumReturnTicketStatus.RETURN_SUCCESS.getCode().equals(refundTicketPo.getRefundStatus())){
                    hcpOrderDetail.setIsRefundTicket("1");
                } else {
                    hcpOrderDetail.setIsRefundTicket("0");
                }
            }

            // 获取退款状态
            refundAmountDao.queryByOrderCode(result, orderCode);
            HcpRefundAmountPo refundAmountPo = result.getData();
            if(null != refundAmountPo){
                hcpOrderDetail.setRefundStatus(String.valueOf(refundAmountPo.getStatus()));
                hcpOrderDetail.setRefundStatusName(EnumRefundOrderStatus.getEnume(Integer.valueOf(refundAmountPo.getStatus())).getClientDesc());
            }


            // 查询供应商订单信息，判断是否可以退票
            // CanRefund,1是可退票，0是不能退票
            if(StringUtils.isNotEmpty(hcpOrderInfoPo.getSupplierOrderId())) {
                RequestQueryOrderParam queryOrderParam = new RequestQueryOrderParam();
                queryOrderParam.setOrderId(hcpOrderInfoPo.getSupplierOrderId());
                queryOrderParam.setMerchantOrderId(hcpOrderInfoPo.getOpenPlatformNo());
                queryOrderParam.setSupplierCode(hcpOrderInfoPo.getSupplierCode());
                this.queryOrderInfoForRefund(result, queryOrderParam);
                hcpOrderDetail.setCanRefund(String.valueOf(result.getData()));

                logger.info("-----> query order detail:" + JSON.toJSONString(hcpOrderDetail));

                // 如果退票记录为空，可以退票
                // 如果退票记录不为空，退票状态是“退票失败”，可以退票
                if(null == refundTicketPo || EnumReturnTicketStatus.REFUND_FAIL.getCode().equals(refundTicketPo.getRefundStatus())){

                } else {
                    hcpOrderDetail.setCanRefund("0");
                }

                // 如果可以退票，计算可退票金额
                if("1".equals(hcpOrderDetail.getCanRefund())){
                    // 获取可退金额
                    try{
                        this.getCanRefundAmount(result, hcpOrderInfoPo);
                        hcpOrderDetail.setCanRefundAmount(String.valueOf(result.getData()));
                    } catch (Exception e){
                        logger.error("计算可退金额报错", e);
                    }
                }
            } else {
                hcpOrderDetail.setCanRefund("0");
            }

            result.setData(hcpOrderDetail);
        }
        return result;
    }

    /**
     * 查询省，市，县的json字符串
     * @param result
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public Result queryHcpCityList(Result result){
        List<HcpCityInfo> cityList = hcpCityDao.queryListOrderByLevel();
        if(null != cityList && !cityList.isEmpty()){

            Map<String, String> level0Map = new HashMap();//记录level=0的id,name
            Map<String, String> level1Map = new HashMap();//记录level=1的id,parentId
            Map<String, String> level2Map = new HashMap();//记录level=1的id,name

            List<ProvinceBean> provinceBeanList = new ArrayList();
            List<String> totalCityList = new ArrayList();

            Map<String ,ProvinceBean> provinceBeanMap = new HashMap();
            Map<String ,CityBean> cityBeanMap = new HashMap();



            for(HcpCityInfo city : cityList){
                String id = String.valueOf(city.getId());
                String parentId = String.valueOf(city.getParent_id());
                String level = String.valueOf(city.getLevel());
                String name = city.getShort_name();

                if("0".equals(level)){
                    level0Map.put(id, name);

                    ProvinceBean provinceBean = new ProvinceBean();
                    provinceBean.setName(name);
                    provinceBean.setCity(new ArrayList());

                    provinceBeanList.add(provinceBean);
                    provinceBeanMap.put(name, provinceBean);
                }
                if("1".equals(level)){
                    String shengName = level0Map.get(parentId);
                    level1Map.put(id, parentId);
                    level2Map.put(id, name);

                    CityBean cityBean = new CityBean();
                    cityBean.setName(name);

                    List<CityBean> cityBeanList = provinceBeanMap.get(shengName).getCity();
                    if(null == cityBeanList){
                        cityBeanList = new ArrayList();
                    }
                    cityBeanList.add(cityBean);

                    cityBeanMap.put(shengName+name, cityBean);

                    if(!totalCityList.contains(city.getShort_name())){
                        totalCityList.add(city.getShort_name());
                    }
                }

                if("2".equals(level)){
                    String shengId = level1Map.get(parentId);
                    String shengName = level0Map.get(shengId);
                    String shiName = level2Map.get(parentId);

                    DistrictBean districtBean = new DistrictBean();
                    districtBean.setName(name);

                    List<DistrictBean> districtBeanList = cityBeanMap.get(shengName+shiName).getDistrict();
                    if(null == districtBeanList){
                        districtBeanList = new ArrayList();
                        cityBeanMap.get(shengName+shiName).setDistrict(districtBeanList);
                    }
                    districtBeanList.add(districtBean);
                }

            }

            Map<String, Object> resultMap = new HashedMap();
            resultMap.put("province", provinceBeanList);
            resultMap.put("cityList", totalCityList);
            result.setData(StringEscapeUtils.unescapeJava(JSON.toJSONString(resultMap).replaceAll("\uFEFF", "").replaceAll(" ", "")));
            result.setStatus(HttpResponseConstants.SUCCESS);

            level0Map.clear();
            level1Map.clear();
            level2Map.clear();

            provinceBeanList.clear();
            totalCityList.clear();
            provinceBeanMap.clear();
            cityBeanMap.clear();
        }

        return result;
    }





    /*
     * 验证账号信息，组装订单
     * 
     * 多个用户的情况下，安装用户数进行拆单
     * 
     * @param result
     * @param orderInfoVo
     * @return List<HcpOrderInfoPo> 订单列表
     * @throws Exception
     */
    private  List<HcpOrderInfoPo> addOrderInfo(Result result, HcpOrderInfoVo orderInfoVo) throws Exception {
        //得到用户登录信息
        UserInfo userInfo = result.getUserInfo ();
        //根据用户所属县域ID获取所属城市
        ShopVO shopVO = this.shopApi.getShop (result, userInfo.getBizId ()).getData ();
        //根据供应商编码获取供应商信息
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, orderInfoVo.getSupplierCode ()).getData ();
        //根据业务编码获取业务信息
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, orderInfoVo.getBusinessCode ()).getData ();
        //根据供应商编码，业务编码获取供应商业务信息（具体平台、县域润润比率）
        SupplierBusiness supplierBusiness = this.supplierBusinessDao.queryBySupplierCodeAndBusinessCode (result, orderInfoVo.getSupplierCode (), orderInfoVo.getBusinessCode ()).getData ();
        //根据县域Id获取县域、网点分润信息
        BusinessStoreShare businessStoreShare = this.businessStoreShareDao.queryByCountryCodeAndBusinessCode (result, String.valueOf (shopVO.getAreaId ()), orderInfoVo.getBusinessCode ()).getData ();
        //获取供应商信息Dubbo
        SupplierVO supplierVO = supplierApi.getSupplier(result, supplierInfo.getShopSupplierId()).getData();

        Double areaCommissionRatio;
        Double storeCommissionRatio;
        if (businessStoreShare != null) {
            areaCommissionRatio = (businessStoreShare.getCountryShareRatio ());
            storeCommissionRatio = (businessStoreShare.getStoreShareRatio ());
        } else {//如果网点没有设置分润，网点分润位100%（所有金额都分给网点）
            areaCommissionRatio = 0.00;
            storeCommissionRatio = 100.00; 
        }
        BusinessInfo businessInfoParent = this.businessInfoDao.getBusinessInfoByParent (result, orderInfoVo.getBusinessCode ()).getData ();
        String parentBusinessCode;
        String parentBusinessName;
        if (businessInfoParent != null) {
            parentBusinessCode = businessInfoParent.getBusinessCode ();
            parentBusinessName = businessInfoParent.getBusinessName ();
        } else {
            parentBusinessCode = businessInfo.getBusinessCode ();
            parentBusinessName = businessInfo.getBusinessName ();
        }

        //获取会员信息,校验密码
        MemberResultVo memberResult = payService.memberVerification(result, orderInfoVo.getMobile(), orderInfoVo.getBizType(), orderInfoVo.getPayPassword());
        if (orderInfoVo.getBizType ().equals (RoleType.SHOP.getCode ())) {
            memberResult.setUserId(Integer.parseInt(shopVO.getUserId()+""));
            memberResult.setUserName(shopVO.getContact ());
            memberResult.setMobiel(shopVO.getContactTel ());
        }


        JSONObject storeInfoJson = new JSONObject ();
        storeInfoJson.put ("contact", shopVO.getContact ());
        storeInfoJson.put ("contactTel", shopVO.getContactTel ());
        BigDecimal costingAmount = orderInfoVo.getCostingAmount (); 
        if(costingAmount == null){ //如果成本价为空，取车票价格。
            costingAmount = new BigDecimal(orderInfoVo.getTicketPrice());
        }


        List<HcpOrderInfoPo> lstOrderInfo = new ArrayList<>();
        //多个乘客，按照拆分成多个订单
        List<TrainUserInfoVo> lstUserInfo = orderInfoVo.getUserDetailList();


        logger.info("---------->购买用户数：{}",lstUserInfo.size());

        for(TrainUserInfoVo trainUserInfo:lstUserInfo){
            //生成开放平台的订单编号
            String openPlatformNo = this.getOpenPlatformNo (result, orderInfoVo);
            //订单编号=业务编号+时间码
            String orderCode = orderInfoVo.getBusinessCode () + GenerateOrderNumber.getOrderNumber ();

            HcpOrderInfoPo orderInfoPo = new HcpOrderInfoPo();
            orderInfoPo.setHcpOrderCode(orderCode);
            orderInfoPo.setOpenPlatformNo (openPlatformNo);
            orderInfoPo.setSupplierCode (orderInfoVo.getSupplierCode ());
            orderInfoPo.setSupplierName (supplierInfo.getCompany ());  //修改为供应商名称
            orderInfoPo.setStoreCode (String.valueOf (userInfo.getBizId ()));
            orderInfoPo.setStoreName (shopVO.getShopName ());
            orderInfoPo.setStoreInfo (storeInfoJson.toJSONString ());
            orderInfoPo.setProvince (shopVO.getContactProvince ());
            orderInfoPo.setCity (shopVO.getContactCity ());
            orderInfoPo.setCountry (shopVO.getContactArea ());
            orderInfoPo.setCountryCode (String.valueOf (shopVO.getAreaId ()));
            orderInfoPo.setCountryName (shopVO.getAreaName ());
            orderInfoPo.setBizId (memberResult.getBizId());
            orderInfoPo.setBizType (memberResult.getBizType());
            orderInfoPo.setPayUserId (memberResult.getUserId());
            orderInfoPo.setMobile (memberResult.getMobiel());
            orderInfoPo.setBusinessCode (parentBusinessCode);
            orderInfoPo.setBusinessName (parentBusinessName);
            orderInfoPo.setSubBusinessCode (orderInfoVo.getBusinessCode ());
            orderInfoPo.setSubBusinessName (businessInfo.getBusinessName ());
            orderInfoPo.setBillYearMonth(supplierVO.getRetailPeriod()+""); //添加账期

            orderInfoPo.setLiftCoefficient (supplierBusiness.getTotalShareRatio ()); //提点系数（火车票没有提点）
            orderInfoPo.setPlatformDividedRatio (supplierBusiness.getPlatformShareRatio ()); //平台分成比率
            orderInfoPo.setAreaDividedRatio (supplierBusiness.getAreaShareRatio ()); //平台与县域分成比率
            orderInfoPo.setAreaCommissionRatio (areaCommissionRatio); //县域佣金
            orderInfoPo.setStoreCommissionRatio (storeCommissionRatio); //网点佣金

            orderInfoPo.setRealAmount (orderInfoVo.getRealAmount ());//实际支付价格
            orderInfoPo.setCostingAmount (costingAmount);        //成本价
            if(orderInfoVo.getOriginalAmount() == null){ 
                orderInfoPo.setOriginalAmount (orderInfoVo.getRealAmount ()); //应付金额为空，等于实付金额
            }else{
                orderInfoPo.setOriginalAmount (orderInfoVo.getOriginalAmount ()); //应付金额
            }


            orderInfoPo.setCreateTime (new Date ());
            orderInfoPo.setPayStatus (EnumOrderInfoPayStatus.NON_PAYMENT.getCode ());
            orderInfoPo.setPayUserName (memberResult.getUserName());
            orderInfoPo.setMemberMobile (orderInfoVo.getMobile ());
            orderInfoPo.setMemberName (memberResult.getMemberName());
            orderInfoPo.setLinkName(orderInfoVo.getUserName());
            orderInfoPo.setLinkPhone(orderInfoVo.getMobile ());


            orderInfoPo.setServicePrice(orderInfoVo.getServicePrice());
            orderInfoPo.setTrainCode(orderInfoVo.getTrainCode());
            orderInfoPo.setTravelTime(orderInfoVo.getTravelTime()); 
            orderInfoPo.setFromStation(orderInfoVo.getFromStation());
            orderInfoPo.setArriveStation(orderInfoVo.getArriveStation());
            orderInfoPo.setStationStartTime(orderInfoVo.getStationStartTime());
            orderInfoPo.setStationArriveTime(orderInfoVo.getStationArriveTime());
            orderInfoPo.setBx(0);       // 是否购买保险
            orderInfoPo.setSeatType(orderInfoVo.getSeatType());
            orderInfoPo.setIdType(trainUserInfo.getIdType());
            orderInfoPo.setIdCardId(trainUserInfo.getUserId());
            orderInfoPo.setTicketType(trainUserInfo.getTicketType());
            orderInfoPo.setUserName(trainUserInfo.getUserName());
            orderInfoPo.setServicePrice(orderInfoVo.getServicePrice()); //服务费
            orderInfoPo.setCostTime(orderInfoVo.getCostTime());

            this.orderInfoDao.addHcpOrderInfo(result, orderInfoPo);
            logger.info("===> 新增订单成功"+JSON.toJSONString(orderInfoPo));

            lstOrderInfo.add(orderInfoPo);
        }

        return lstOrderInfo;

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


    /**
     * 修改订单状态
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateOrderStatus(Result result, String orderCode, Integer payStatus) throws Exception {
        orderInfoDao.updateStatus (result, orderCode, payStatus);
        return result;
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
    public Result paySuccessCallBack(Result result, MQBizOrderPay mqBizOrderPay) throws Exception {
        HcpOrderInfoPo orderInfoPo = this.orderInfoDao.queryByOrderCode(result, mqBizOrderPay.getBizOrderNo ()).getData ();
        //订单支付成功后，又返回失败的情况。严格判断状态。
        if (!orderInfoPo.getPayStatus ().equals (EnumOrderInfoPayStatus.PAY_SUCCESS.getCode ())) {
            logger.warn("---HCP支付回调，不是合理状态的单，状态转变");
            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfoPo);
            return result;
        }


        //添加日志
        addPaymentLog(result, mqBizOrderPay, orderInfoPo);
        logger.info("-->1,添加支付日志完成.....");

        //结算
        payService.paySettleV1(result, orderInfoPo);
        logger.info("--->2,调用结算完成.......");

        //获取开放平台供应商的信息
        ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo (result, orderInfoPo.getSupplierCode ());

        //请求开放平台购票
        this.buyTicket (result, orderInfoPo, serviceRegisterVo);

        logger.info ("---->3,购票完成，订单状态为出票中");
        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }


    /**
     * @param result
     * @param mqBizOrderPay
     * @param orderInfoPo
     */
    private void addPaymentLog(Result result, MQBizOrderPay mqBizOrderPay, HcpOrderInfoPo orderInfoPo) {
        try{
            //新增支付日志 
            HcpPaymentRecordPo paymentRecordPo = new HcpPaymentRecordPo();
            paymentRecordPo.setHcpOrderCode(mqBizOrderPay.getBizOrderNo ());
            paymentRecordPo.setPayWay (orderInfoPo.getBizType ());
            paymentRecordPo.setCreateTime (new Date ());
            paymentRecordPo.setPayAmount(orderInfoPo.getRealAmount());
            paymentRecordPo.setPayType(1);//订单支付
            payMentRecord.addHcpPaymentRecord(result, paymentRecordPo).getData ();
        }catch(Exception e){
            logger.error("-添加日志错误",e);
        }
    }



    /*

     * 调用第三方购票
     * @param result
     * @param order
     * @param serviceRegisterVo
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private void buyTicket(Result result,HcpOrderInfoPo order,ServiceRegisterVo serviceRegisterVo) throws Exception{

        logger.info("#--> HCP开始调用外部购票接口进行实际充值..."+JSON.toJSONString(order));

        String businessKey = serviceRegisterVo.getSignKey ();
        CreateOrderRequest request = new CreateOrderRequest();
        request.setMerchantOrderId(order.getOpenPlatformNo());
        request.setOrderLevel("0"); //普通票，1:vip票
        request.setFromStation(order.getFromStation()); //出发车站
        request.setArriveStation(order.getArriveStation());//到达车站
        request.setArriveTime(order.getStationArriveTime()); //到达时间
        request.setFromTime(order.getStationStartTime());//出发时间
        request.setBxInvoice("0");  //是否需要保险发票.0:无，1：购买
        request.setSeatType(order.getSeatType()+"");//到达时间
        request.setSmsNotify("0"); //0，否，1：是。是的话，联系人、手机号码必填
        request.setSumAmount(order.getCostingAmount().toString());
        request.setTicketPrice(order.getCostingAmount().toString());
        request.setTrainCode(order.getTrainCode());
        request.setTravelTime(order.getTravelTime());
        request.setWzExt("0"); //备选无座

        //乘车用户信息
        List<TrainUserInfoVo> listTrainUserInfo = new ArrayList<>();
        TrainUserInfoVo userInfoVo = new TrainUserInfoVo();
        userInfoVo.setIdType(order.getIdType());
        userInfoVo.setUserId(order.getIdCardId());
        userInfoVo.setUserName(order.getUserName());
        userInfoVo.setTicketType(order.getTicketType()); //0：成人票  1：儿童票
        userInfoVo.setBx("0");
        listTrainUserInfo.add(userInfoVo);
        request.setUserDetailList(JSON.toJSONString(listTrainUserInfo));

        logger.info("----------------> createOrder params:" + JSON.toJSONString(request));
        logger.info("----------------> createOrder businessKey:" + businessKey);
        //签名
        String rechargeSigned = KuyouRequestSign.signCreateOrder(request, businessKey);
        request.setSignKey(rechargeSigned);

        //进行购票
        Map<String, Object> rechargeRequestMap = ObjectMapUtils.toObjectMap(request);
        String urlCharge = serviceRegisterVo.getServiceAddr () + UrlConstants.CREATE_ORDER;
        Date sentTime = new Date ();
        String rechargeResponse = HttpUtils.doPost(urlCharge, rechargeRequestMap);
        Date receiveTime = new Date ();
        this.insertHttpLog (result, order.getHcpOrderCode(), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "请求开放平台购票");

        //不进行回滚，以消息回调为准
        if (StringUtils.isEmpty (rechargeResponse)) {
            this.insertHttpLog (result, order.getHcpOrderCode(), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "开放平台购票失败");
            logger.error("===========购票失败，response为null");
            return;
        }

        ResponseWrapper responseWrapperCharge = JSONObject.parseObject (rechargeResponse, ResponseWrapper.class);
        if (responseWrapperCharge.getStatus () != 200) {
            //购票失败
            updateOrderStatus(result, order.getHcpOrderCode(), EnumOrderInfoPayStatus.TRAIN_FAIL.getCode()); 

            //创建退款单
            createRefundOrder(result, order.getHcpOrderCode(),order.getRealAmount(),EnumRefundType.BUY_TICKET_ERROR.getType(),"购票失败");

            //新增日志
            this.insertHttpLog (result, order.getHcpOrderCode(), urlCharge, JSONObject.toJSONString (rechargeRequestMap), rechargeResponse, sentTime, receiveTime, "开放平台购票返回失败状态");
            logger.error ("开放平台充值返回失败状态，订单状态位购票失败【7】");


            //短信通知购买用户
            String message = String.format(SMSMessageTemplateConstants.TRAIN_FAIL,order.getMemberMobile(),order.getHcpOrderCode());
            this.smsService.sendSms (order.getMemberMobile (),message);

        }else{
            //出票中
            updateOrderStatus(result, order.getHcpOrderCode(), EnumOrderInfoPayStatus.TRADING.getCode()); 
            logger.info("#--> 完成调用外部购票接口，订单状态位出票中【4】..."+rechargeResponse);
        }

    }


    /**
     * 开放平台 购票 完成后回调
     *
     * @param result
     * @return
     */
    public Result orderCallBack(Result result, RequestOrderBackParam param){
        logger.info("=====>购票回调参数：params:{}",JSON.toJSONString(param));

        String merchantOrderId =  param.getMerchantOrderId();
        HcpOrderInfoPo orderInfo = this.orderInfoDao.queryByOpenPlatformNo(result, merchantOrderId).getData();

        logger.info("=====>获取得到订单信息:" + JSON.toJSONString(orderInfo));

        //订单充值成功后，又返回失败的情况。严格判断状态。
        if (!(orderInfo.getPayStatus ().equals (EnumOrderInfoPayStatus.TRADING.getCode()) 
                || orderInfo.getPayStatus ().equals (EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode ()))) {
            logger.info("=====>合理的状态转换,状态为:{}",orderInfo.getPayStatus());
            result.setStatus (HttpResponseConstants.SUCCESS);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfo);
            return result;
        }

        Boolean flag = false;
        //回调返回成功，订单状态改成交易成功
        if (param.getStatus().equals("SUCCESS")) { 
            logger.info("====>orderId:{}出票成功！",merchantOrderId);
            //订单更改状态
            this.orderInfoDao.updateStatus(result, orderInfo.getHcpOrderCode(),  EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode ());
            orderInfo.setPayStatus(EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode ());

            //修改订单信息
            updateOrderInfo(param, orderInfo);

            try{
                //如果无座的话，没有座位号，空处理。
                EnumSeatType enumSeatType = EnumSeatType.getEnume(orderInfo.getSeatType());
                String seatType = enumSeatType.getType() == EnumSeatType.STAND.getType()? "":enumSeatType.getName();

                //等待结果（缴费中）”转变为“已成功”的短信提醒  
                String message = String.format(SMSMessageTemplateConstants.TRAIN_SUCCESS,
                        orderInfo.getMemberMobile(),
                        orderInfo.getHcpOrderCode(),
                        orderInfo.getTravelTime(),
                        orderInfo.getStationStartTime(),
                        orderInfo.getFromStation(),
                        orderInfo.getArriveStation(),
                        orderInfo.getTrainCode(),
                        orderInfo.getTrainBox(),
                        orderInfo.getSeatNo(),
                        seatType,
                        param.getOutTicketBillno());
                this.smsService.sendSms (orderInfo.getMemberMobile (),message);
            }catch(Exception e){
                logger.error("购票成功，发短信失败",e);
            }

        } 
        else if (param.getStatus().equals ("FAILURE")) {
            logger.info("=====>orderId:{}出票失败,原因:{}",merchantOrderId,param.getFailReason());
            this.orderInfoDao.updateStatusAndFailReason (result, orderInfo.getHcpOrderCode(), EnumOrderInfoPayStatus.TICKET_FAIL.getCode () ,param.getFailReason());

            //生成退款单
            createRefundOrder(result, orderInfo.getHcpOrderCode(),orderInfo.getRealAmount(),EnumRefundType.BUY_TICKET_ERROR.getType(),"出票失败");

            //短信通知购买用户
            String message = String.format(SMSMessageTemplateConstants.TRAIN_FAIL,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode());
            this.smsService.sendSms (orderInfo.getMemberMobile (),message);

            //---通知运营蔡璐玲---通知测试杨珊珊---通知工程师黄振盛  TODO 是否开启
            intenalSMSNotify(orderInfo);

            result.setMsg (param.getFailReason());
            flag = true;
        } 
        else {
            logger.info("====>orderId:{}购票回调参数无效",merchantOrderId);
            result.setMsg ("回调参数状态无效");
            flag = true;
        }

        if (flag) {
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (HttpResponseConstants.ERROR);
            return result;
        }

        result.setStatus (HttpResponseConstants.SUCCESS);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (orderInfo);
        return result;
    }


    /**
     * 创建退款单单
     * @param result
     * @param orderCode
     * @param realAmount
     * @param type
     * @param remark
     */
    public void createRefundOrder(Result result,String orderCode,BigDecimal realAmount,int type,String remark){
        logger.info("---------> orderId:{}创建退款单",orderCode);

        HcpRefundAmountPo chargeRefund = getChargeRefund(orderCode,realAmount,type,remark);

        chargeRefund = this.refundAmountDao.addHcpRefundAmount(result, chargeRefund).getData ();
        if (chargeRefund.getId () == null) {
            logger.error ("---------> orderId:{}生成退款记录失败",orderCode);
        }

        logger.info("--------->orderId:{}创建退款单成功:{}",orderCode,JSON.toJSONString(chargeRefund));
    }



    /*
     * @param orderInfo
     */
    private void intenalSMSNotify(HcpOrderInfoPo orderInfo) {
        String notificationStr = TshDiamondClient.getInstance ().getConfig ("notification").trim ();
        if (StringUtils.isNotBlank(notificationStr)) {
            String[] notificationArray = StringUtils.split (notificationStr, ";");
            for (String item : notificationArray) {
                this.smsService.sendSms (item.trim (), "【淘实惠工程师】亲爱的m" + orderInfo.getMemberMobile () 
                + "，很抱歉，购票订单号" + orderInfo.getOpenPlatformNo () 
                + ";平台订单号" + orderInfo.getHcpOrderCode() + "购票失败了，请留意！");
            }
        }
    }



    /*
     * 更新订单
     * 
     * @param param
     * @param orderInfo
     */
    private void updateOrderInfo(RequestOrderBackParam param, HcpOrderInfoPo orderInfo) {
        //更新订单
        orderInfo.setSeatNo(param.getSeatNo()); //座位号
        orderInfo.setOutTicktetTime(param.getOutTicketTime());//出票时间、
        orderInfo.setTrainBox(param.getTrainBox()); //车厢号
        orderInfo.setSupplierOrderId(param.getOrderId()); //供应商编号
        this.orderInfoDao.update(orderInfo);
    }


    /**
     * 增值服务退款（针对支付异常）
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result refundChargeMoney(Result result, String orderCode) throws Exception {
        logger.info(">>>>>>orderCode:{} 进行退款操作。",orderCode);

        HcpOrderInfoPo orderInfo = this.orderInfoDao.queryByOrderCode(result, orderCode).getData ();
        //订单退款,购票失败（16）
        if (!orderInfo.getPayStatus ().equals (EnumOrderInfoPayStatus.PAY_EXCEPTION.getCode()))
        {
            logger.error(">>>>>>>>>orderCode:{}当前的状态:{}不允许的退款退分润操作！",orderCode,orderInfo.getPayStatus ());
            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfo);
            return result;
        }

        HcpRefundAmountPo refundInfo = refundAmountDao.queryByOrderCode(result, orderCode).getData();

        //3，支付成功（超时），进行退款，不退分润
        payService.refundMoney(result, orderInfo, refundInfo);

        //订单退款状态退款中,更新退款时间
        this.refundAmountDao.updateRefundStatusAndTime(result, refundInfo.getRefundAmountCode(), 
                EnumRefundOrderStatus.REFUNDING.getCode (),new Date());

        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }


    /**
     * 增值服务退款,推分润
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result refundCharge(Result result, String orderCode) throws Exception {
        logger.info(">>>>>>orderCode:{} 进行退款操作。",orderCode);

        HcpOrderInfoPo orderInfo = this.orderInfoDao.queryByOrderCode(result, orderCode).getData ();

        //订单退款,购票失败（7）或者出票失败（8）,支付成功[5](退票)
        if (!orderInfo.getPayStatus ().equals (EnumOrderInfoPayStatus.TICKET_FAIL.getCode())
                && !orderInfo.getPayStatus().equals(EnumOrderInfoPayStatus.TRAIN_FAIL.getCode())
                && !orderInfo.getPayStatus().equals(EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode())){
            logger.error(">>>>>>>>>orderCode:{}当前的状态不允许的退款退分润操作！",orderCode);
            result.setStatus (HttpResponseConstants.ERROR);
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (orderInfo);
            return result;
        }

        HcpRefundAmountPo refundInfo = refundAmountDao.queryByOrderCode(result, orderCode).getData();

        //1，购票退款（购票失败，出票失败，进行退款推分润）；2，退票退款不进行推分润；3，支付成功（超时）
        payService.refundMoneyV1(result, orderInfo, refundInfo);

        //订单退款状态退款中,退款时间
        this.refundAmountDao.updateRefundStatusAndTime(result, refundInfo.getRefundAmountCode(), 
                EnumRefundOrderStatus.REFUNDING.getCode (),new Date());

        result.setCode (ResponseCode.OK_CODE.getCode ());
        return result;
    }



    /**
     * 创建退票单
     * 
     * 
     * @param orderNo
     * @param realAmount
     * @param type
     * @param remark
     * @return
     */
    private HcpRefundAmountPo getChargeRefund(String orderNo,BigDecimal realAmount,int type,String remark){
        String refundCode = "TD-" + orderNo;
        HcpRefundAmountPo chargeRefund = new HcpRefundAmountPo ();
        chargeRefund.setHcpOrderCode(orderNo);
        chargeRefund.setRealAmount(realAmount);
        chargeRefund.setRefundAmountCode(refundCode);
        //        chargeRefund.setRefundTime (new Date ());
        chargeRefund.setRefundType(type);//购票退款
        chargeRefund.setCreateTime(new Date());
        chargeRefund.setStatus(EnumRefundOrderStatus.NON_REFUND.getCode());
        chargeRefund.setUserCode ("系统");
        chargeRefund.setUserMobile ("");
        chargeRefund.setUserName ("自动退款");
        chargeRefund.setRefundTimes(0);
        chargeRefund.setRemark(remark);
        return chargeRefund;
    }


    /*
     * 获取开放平台生成 openPlatformNo
     * 
     * @param result
     * @param orderInfoVo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private String getOpenPlatformNo(Result result, HcpOrderInfoVo orderInfoVo) throws Exception {
        ServiceRegisterVo serviceRegisterVo = this.getServiceRegisterVo(result, orderInfoVo.getSupplierCode());

        Map<String, String> getOrderNoParam = new HashMap<>();
        getOrderNoParam.put("signKey", KuyouRequestSign.signGetOrderNo(serviceRegisterVo.getSignKey()));
        String getKFPOrderNoUrl = orderInfoVo.getServerAddr () + UrlConstants.GENERATE_ORDER_NO;
        String getKFPOrderNoResponse = HttpXmlClient.post (getKFPOrderNoUrl, getOrderNoParam);

        logger.info("火车票-->生成开放平台的订单编号：" + getKFPOrderNoResponse);

        if (StringUtils.isEmpty (getKFPOrderNoResponse)) {
            logger.error ("火车票-->生成开放平台的订单编号失败");
            throw new FunctionException (result, "火车票-->生成开放平台的订单编号失败");
        }
        ResponseWrapper responseWrapperKey = JSONObject.parseObject (getKFPOrderNoResponse, ResponseWrapper.class);
        if (responseWrapperKey.getStatus () != HttpResponseConstants.SUCCESS) {
            logger.error ("火车票-->生成开放平台的订单编号失败，状态码返回不成功");
            throw new FunctionException (result, responseWrapperKey.getMessage ());
        }
        String openPlatformNo = responseWrapperKey.getData ().toString ();
        if (StringUtils.isEmpty (openPlatformNo)) {
            logger.error ("火车票-->获取开放平台的订openPlatformNo，状态码返回不成功");
            throw new FunctionException (result, "生成开放平台的订单编号失败");
        }
        return openPlatformNo;
    }

    /**
     * 打印缴费单的接口
     * @param result
     * @return
     * @throws Exception
     */
    public Result printChargeInfo(Result result) throws Exception {
        UserInfo userInfo = result.getUserInfo ();
        Long storeCode = userInfo.getBizId ();
        //根据网点id获取省市县信息
        ShopVO shopVO = this.shopApi.getShop (result, storeCode).getData ();
        String memberCard = null;
        String memberName = null;
        BigDecimal memberBalance = null;

        PrintChargeVo printChargeVo = new PrintChargeVo ();

        // 订单类型
        printChargeVo.setChargeTypeStr ("火车票订单");
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

}
