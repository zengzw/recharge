/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.sms.sendconfig.SmsResult;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.broker.utils.DateUtil;
import com.tsh.vas.commoms.exception.BusinessException;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.TrainTicketUtils;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.SMSMessageTemplateConstants;
import com.tsh.vas.trainticket.service.APPAPIService;
import com.tsh.vas.trainticket.validate.APIReqeustParamsValidate;
import com.tsh.vas.trainticket.vo.req.QueryOrderParam;
import com.tsh.vas.trainticket.vo.req.QueryServiceFeeParam;
import com.tsh.vas.trainticket.vo.req.RequestQuerySubwayStationParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketNumParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketParam;
import com.tsh.vas.trainticket.vo.req.RequestValidateUserInfoParam;
import com.tsh.vas.vo.trainticket.HcpOrderInfoVo;


/**
 * 火车票 APP api 接口
 *
 * @author zengzw
 * @date 2016年11月21日
 */
@Controller
@RequestMapping(value = "/app/vas/train")
public class APPAPIController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(APPAPIController.class);


    /**
     * api service
     */
    @Autowired
    private APPAPIService apiService;

    @Autowired
    private SmsService smsService;


    /**
     * 查询机构信息
     * @param params
     * @return
     */
    @RequestMapping(value = "organization/info", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getOrganizationInfo(String params) {
        logger.info ("==>通过开放平台获取服务商信息:{}", params);
        Result result = this.getResult ();
        UserInfo userInfo  = result.getUserInfo();
        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        JSONObject json = JSONObject.parseObject (params);
        String businessCode = json.getString("businessCode");
        try {
            result = this.apiService.getOrganizationInfo (result, businessCode);
        } catch (Exception ex) {
            logger.error ("通过开放平台获取服务商信息失败" + result.getMsg (),ex);
            return responseFail("获取失败");
        } 

        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));
        return result.DTO() ;
    }


    /**
     * 获取城市名称
     * @return
     */
    @RequestMapping(value = "/mylocation", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getCityByUserToken(){
        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();

        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        try {
            String resultData = apiService.getUserCityByUser(result, userInfo).getData();
            return responseSuccess("获取成功", resultData == null ? "":resultData);

        } catch (Exception e) {
            logger.error(">>>> 获取当前城市接口失败",e);
            return responseFail("获取失败");
        }

    }

    /**
     * 获取热门城市
     * @return
     */
    @RequestMapping(value = "/getHotCities", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getHotCities(){
        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();

        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        String hotCities = TshDiamondClient.getInstance().getConfig("hotCities");
        if(StringUtils.isNotEmpty(hotCities)){
            result.setData(hotCities.split(","));
        }

        return result.DTO();
    }

    /**
     * 查询车次列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object queryTrainNumberList(String params){
        logger.info("-->获取车次列表请求参数:{}",params);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();
        ReturnDTO validate = combineValidate(params, userInfo);
        if(validate != null){
            return validate;
        }

        //本地参数校验
        RequestQueryTicketParam queryTicketParam = JSON.parseObject(params,RequestQueryTicketParam.class);
        APIReqeustParamsValidate.validateRequestQueryTicketParam(result, queryTicketParam);
        if(result.getStatus() == HttpResponseConstants.ERROR){
            return responseFail(result.getMsg());
        }

        //调用远程接口获取数据
        apiService.queryTrainList(result, queryTicketParam);
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            return result.DTO ();
        } else {
            return responseFail("获取失败");
        }

    }

    /**
     * 查询车次途径站点列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/pathway/station", method = RequestMethod.GET)
    @ResponseBody
    public Object querySubwayStation(String params){
        logger.info("-->获取车次途径站点列表请求参数:{}",params);
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();


        RequestQuerySubwayStationParam queryTicketParam = JSON.parseObject(params, RequestQuerySubwayStationParam.class);
        ReturnDTO validate = combineValidate(params, userInfo);
        if(validate != null){
            return validate;
        }

        apiService.querySubwayStation(result, queryTicketParam);
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            return result.DTO();
        }else{
            return responseFail("获取失败");
        }

    }


    /**
     * 校验乘客信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/validate/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO validatePassengers(String params){
        logger.info("-->获取火车票列表请求参数:{}",params);
        Result result =  this.getResult();
        UserInfo userInfo = result.getUserInfo();

        ReturnDTO validate = combineValidate(params, userInfo);
        if(validate != null){
            return validate;
        }

        //校验参的合法性
        RequestValidateUserInfoParam userInfoParam = JSON.parseObject(params, RequestValidateUserInfoParam.class);
        APIReqeustParamsValidate.validateUserInfo(result, userInfoParam.getUserModels());
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }
        
        

        //调用openpf验证用户信息
        apiService.verifyUsers(result,userInfoParam);
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            return result.DTO();
        }else{
            return responseFail("获取失败");
        }

    }

    /**
     * 查询车次余票数量
     * @param params
     * @return
     */
    @RequestMapping(value = "/query/ticket/number", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getTicketNum(String params){
        logger.info("-->查询车次余票数量请求参数:{}", params);
        Result result =  this.getResult();
        UserInfo userInfo = result.getUserInfo();

        ReturnDTO validate = combineValidate(params, userInfo);
        if(validate != null){
            return validate;
        }

        //校验参的合法性
        RequestQueryTicketNumParam model = JSON.parseObject(params, new TypeReference<RequestQueryTicketNumParam>(){});
        APIReqeustParamsValidate.validateQueryTicketNumber(result, model);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }

        //调用openpf验证用户信息
        apiService.queryTicketNum(result, model);
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            return result.DTO();
        }else{
            return responseFail("获取失败");
        }

    }


    /**
     * 获取服务费
     * @param params
     * @return
     */
    @RequestMapping(value = "/service/fee", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getServiceFee(String params){
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(userInfo);
        if(validateResult != null){
            return validateResult;
        }
        QueryServiceFeeParam serviceFeeParam = com.traintickets.common.utils.JsonUtils.convert2Object(params, QueryServiceFeeParam.class);
        APIReqeustParamsValidate.validateQueryServiceFee(result, serviceFeeParam);

        //获取服务费信息 。
        try {
            this.apiService.getServiceFee(result, serviceFeeParam);
        } catch (BusinessException e) {
            logger.error("-->获取服务费失败",e);
            return responseFail(getErrorMessage(e));
        }

        logger.info("-->获取到的服务费为:"+result.getData());
        return responseSuccess("获取成功", result.getData());
    }


    /**
     * 支付
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ReturnDTO pay(String params){
        logger.info("---pay 请求参数:{}"+params);
        
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(userInfo);
        if(validateResult != null){
            return validateResult;
        }

        //请求参数校验
        HcpOrderInfoVo orderInfoVo = JSON.parseObject(params,HcpOrderInfoVo.class);
        APIReqeustParamsValidate.validateOrderInfoVO(result, orderInfoVo);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }


        try {
            apiService.payOdrerInfo(result, orderInfoVo);
        } catch (Exception e) {
            logger.error("-->订单支付失败:" + e.getMessage(), e);
            return responseFail(getErrorMessage(e));
        }

        return result.DTO();
    }


    /**
     * 查询订单列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/order/list", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ReturnDTO queryBills(String params){
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();
        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(userInfo);
        if(validateResult != null){
            return validateResult;
        }

        QueryOrderParam queryOrderParam = JSON.parseObject(params, new TypeReference<QueryOrderParam>(){});
        if(null == queryOrderParam){
            queryOrderParam = new QueryOrderParam();
        }

        apiService.queryOrderList(result, queryOrderParam);

        return result.DTO();
    }


    /**
     * 查询订单详情
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/order/detail", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ReturnDTO queryBillDetail(String orderCode){
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(userInfo);
        if(validateResult != null){
            return validateResult;
        }

        apiService.queryOrderDetail(result, orderCode);

        return result.DTO();
    }


    /**
     * 退票，手机号码发送
     * @param mobilePhone
     * @return
     */
    @RequestMapping(value = "/sms/send", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO beforReturnSendSMS(String mobilePhone){
        logger.info("====>beforReturnSendSMS requsst params:{}",mobilePhone);

        Result result = this.getResult();
        ReturnDTO returnDTO = combineValidate(mobilePhone,result.getUserInfo());
        if(returnDTO != null){
            return returnDTO;
        }

        String code = TrainTicketUtils.generatePhoneCode(6);
        String message = String.format(SMSMessageTemplateConstants.RETURN_TICKET,mobilePhone,code);
        logger.info("-->smscode request:mobile:{},code:{}",mobilePhone,code);

        try {
            //发送手机验证码
            SmsResult smsResult = smsService.sendVerificationCode(mobilePhone, message);
            //成功了，根据手机号码存储验证码code
            if(smsResult.isSuccess()){  
                int time = 5 * 60;
                RedisSlave.getInstance().set(mobilePhone, code, time);
            }else{
                logger.info("==>mobile:{}发送验证码失败",mobilePhone);
                return this.responseFail("发送失败");
            }

        } catch (Exception e) {
            logger.error("==>mobile:{}发送验证码异常。",mobilePhone,e);
            return this.responseFail("发送异常");
        }

        return this.responseSuccess("发送成功");

    }


    /**
     * 退票接口 短信验证，通过进行退票
     * @param params
     * @return
     */
    @RequestMapping(value="/validate/refund", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ReturnDTO returnTicket(String params){
        logger.info("==========>退票请求参数:{}",params);

        Result result = this.getResult();
        ReturnDTO returnDTO = combineValidate(params, result.getUserInfo());
        if(returnDTO != null){
            return returnDTO;
        }

        //参数校验
        RequestReturnTicketParam request = JSON.parseObject(params, RequestReturnTicketParam.class);
        APIReqeustParamsValidate.validateReturnTicketParams(result, request);
        if(result.getStatus() == HttpResponseConstants.ERROR){
            return responseFail(result.getMsg());
        }


        try {
            //校验手机验证码,成功，不删除验证code。提交信息的时候会重新校验Code
            smsService.checkValidateCode(result, request.getMobile(), request.getCode());
            if(result.getStatus() == 200){
                //进行退票操作
                apiService.returnTicket(result, request);
                return result.DTO();
            } else {
                logger.info("mobile:{}校验失败",request.getMobile());
                return this.responseFail(result.getMsg());
            }

        } catch (Exception e) {
            logger.error("退票失败",e);
            return this.responseFail(getErrorMessage(e));
        }

    }


    /**
     * 获取当前日期
     * @return
     */
    @RequestMapping(value = "/get/current/date", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getCurrentDate(){
        Result result = this.getResult();
        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(result.getUserInfo());
        if(validateResult != null){
            return validateResult;
        }

        String dateString = DateUtil.datetoStr(new Date(), "yyyy-MM-dd");

        return responseSuccess("成功", dateString);
    }


    /**
     * 获取全国省，市，县的json字符串
     * @return
     */
    @RequestMapping(value = "/get/cities/json", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getCityListJson(){
        Result result = this.getResult();
        //校验用户是否登录
        ReturnDTO validateResult = super.validateUserInfo(result.getUserInfo());
        if(validateResult != null){
            return validateResult;
        }

        apiService.queryHcpCityList(result);

        return result.DTO();
    }


    /**
     * 票据打印
     * @return
     */
    @RequestMapping(value = "/print/info")
    @ResponseBody
    public ReturnDTO printChargeInfo() {
        Result result = getResult ();

        try {
            result = this.apiService.printChargeInfo (result);
        } catch (Exception ex) {
            logger.error ("打印订单失败" + result.getMsg (),ex);
            return responseFail(ex.getMessage());
        }
        
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));
        return result.DTO();
    }


    /**
     *
     * @param params
     * @param userInfo
     * @return
     */
    private ReturnDTO combineValidate(String params, UserInfo userInfo) {
        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        validate = super.validateRequestParams(params);
        if(validate != null){
            return validate;
        }

        return null;
    }




}
