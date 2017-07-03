/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constroller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.vas.commoms.exception.CouponRuntimeException;
import com.tsh.vas.commoms.queue.DBLogQueue;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.validate.APIPhoneReqeustParamsValidate;
import com.tsh.vas.phone.vo.ReqQueryActivityOrderListParams;
import com.tsh.vas.phone.vo.ReqQueryOrderListParams;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;


/**
 * 话费 APP api 接口
 *
 * @author zengzw
 * @date 2017年3月7日
 */
@Controller
@RequestMapping(value = "/app/vas/phone")
public class APPPhoneAPIController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(APPPhoneAPIController.class);

    /**
     * api service
     */
    @Autowired
    private APIAppPhoneService apiService;

    @RequestMapping("/test")
    public void test() throws InterruptedException{
        System.out.println("--------------"+this);
        DBLogQueue<ChargePayHttpLog> queue = new DBLogQueue<>();

        ChargePayHttpLog chargePayHttpLog = new ChargePayHttpLog ();
        //日志编号

        //业务编号
        chargePayHttpLog.setChargeCode ("2222222");
        //发送请求时间
        chargePayHttpLog.setSendTime (new Date());
        //收到消息时间
        chargePayHttpLog.setReceiveTime (new Date());
        //发送的本地方法
        chargePayHttpLog.setSendMothed ("payChargeInfo");
        //请求对像的方法，如资金服务中心
        chargePayHttpLog.setReceiveMothed ("ddd");
        //发送内容
        chargePayHttpLog.setSendData ("DDDDD");
        //收到消息内容
        chargePayHttpLog.setReceiveData ("D");
        //业务类型描述，如下单、退款调用
        chargePayHttpLog.setRemark ("RE");  
        queue.off(chargePayHttpLog);



    }


    /**
     * 查询机构信息
     * @param businessCode
     * @return
     */
    @RequestMapping(value = "/organization/info/{businessCode}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO getOrg(@PathVariable String businessCode) {
        logger.info ("#url:/organization/info/{businessCode} -- req params:" + businessCode);
        Result result = this.getResult ();
        UserInfo userInfo  = result.getUserInfo();
        ReturnDTO validate = combineValidate(businessCode, userInfo);
        if(validate != null){
            return validate;
        }

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
     * 获取 限制号码段
     * @return
     */
    @RequestMapping(value = "/limit/segment", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO queryLimit(){
        logger.info("#url:/limit/segment-- 获取限制号码段");

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();

        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        try {
            apiService.queryLimitPhoneSegment(result);
            return responseSuccess("获取成功", result.getData());

        } catch (Exception e) {
            logger.error(">>>> 获取当前城市接口失败",e);
            return responseFail("获取失败");
        }

    }


    /**
     * 获取默认的充值面值
     * @return
     */
    @RequestMapping(value = "/value", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO queryPhoneValue(){
        logger.info("#url:/value --> 获取默认充值面值");

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();

        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        apiService.queryListDefaultPhoneValue(result);
        return responseSuccess("获取成功", result.getData());

    }


    /**
     * 根据手机号码 充值面值
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/value/{mobile}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO mobile(@PathVariable String mobile){
        logger.info("#url:/value/{mobile}-- req params:" + mobile);

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();
        ReturnDTO validate = combineValidate(mobile, userInfo);
        if(validate != null){
            return validate;
        }

        apiService.queryCurrentMobilePhoneValue(result, mobile);
        return responseSuccess("获取成功", result.getData());

    }

    /**
     * 根据手机号码 获取号码段
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/segment/{mobile}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO segment(@PathVariable String mobile){
        logger.info("#url:/segment/{mobile}-- req params:{}",mobile);

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();
        ReturnDTO validate = combineValidate(mobile, userInfo);
        if(validate != null){
            return validate;
        }

        apiService.queryPhoneLocation(result,mobile);
        return responseSuccess("获取成功", result.getData());

    }


    /**
     * 支付
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnDTO pay(String params){
        logger.info("#req--->method[post],url[pay],params:{}",params);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validateResult = combineValidate(params, userInfo);
        if(validateResult != null){
            return validateResult;
        }

        PhoneOrderInfoVo orderInfo = JSON.parseObject(params,PhoneOrderInfoVo.class);
        //参数校验
        APIPhoneReqeustParamsValidate.validateOrderInfoVO(result, orderInfo);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }


        try {

            apiService.payOdrerInfo(result, orderInfo);

        }catch(CouponRuntimeException e){
            logger.error("-->tsh绑定优惠券失败了:msg:" + e.getMessage()+",code:"+e.getErrCode(), e);
            return responseSuccess(e.getErrMsg(),Integer.parseInt(e.getErrCode()));
        }catch (Exception e) {
            logger.error("-->订单支付失败:" + e.getMessage(), e);
            return responseFail(getErrorMessage(e));
        }

        return result.DTO();
    }

    /**
     * 故乡获取我的订单列表
     * @param mobile
     * @param type
     * @param page
     * @param openId
     * @return
     */
    @RequestMapping(value = "/gx/myOrderList", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO myOrderList(String mobile, String type, int page, String openId){
        logger.info("#req--->method[get],url[gx/myOrderList]:mobile:"+mobile+",type:"+type+",page:"+page);
        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        if(StringUtils.isBlank(openId)){
            return responseFail("openId为空");
        }

        apiService.queryMyOrderList(result, userInfo, mobile, page, 5, type, openId);

        return responsePageSuccess("获取成功", result.getData(), result.getCode());
    }

    /**
     * 故乡支付接口
     * 
     * @param params
     * @return
     */
    @RequestMapping(value = "/gx/pay", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnDTO gxYay(String params){
        logger.info("#req--->method[post],url[gx/pay],params:{}",params);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();

        //校验用户是否登录
        ReturnDTO validateResult = combineValidate(params, userInfo);
        if(validateResult != null){
            return validateResult;
        }

        PhoneOrderInfoVo orderInfo = JSON.parseObject(params,PhoneOrderInfoVo.class);
        orderInfo.setToken(userInfo.getSessionId());

        //参数校验
        APIPhoneReqeustParamsValidate.validateGXOrderInfoVO(result, orderInfo);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }


        try {
            
            apiService.payGXOdrerInfo(result, orderInfo); 
            
        }catch(CouponRuntimeException e){
            logger.error("-->tsh绑定优惠券失败了:msg:" + e.getMessage()+",code:"+e.getErrCode(), e);
            return responseSuccess(e.getErrMsg(),Integer.parseInt(e.getErrCode()));
        }
        catch (Exception e) {
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
    @RequestMapping(value = "/order/list", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnDTO list(String params){
        logger.info("#req-->/order/list.req params:"+params);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();
        ReturnDTO validateResult = combineValidate(params, userInfo);
        if(validateResult != null){
            return validateResult;
        }

        ReqQueryOrderListParams queryOrderParam = JSON.parseObject(params, new TypeReference<ReqQueryOrderListParams>(){});
        if(null == queryOrderParam){
            queryOrderParam = new ReqQueryOrderListParams();
        }

        APIPhoneReqeustParamsValidate.validateRequestQueryOrderListParam(result, queryOrderParam);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }

        apiService.queryOrderList(result, queryOrderParam);

        return result.DTO();
    }

    //    /**
    //     * 查询优惠券列表
    //     *
    //     * @param params
    //     * @return
    //     */
    //    @RequestMapping(value = "/coupon/list", method = {RequestMethod.GET})
    //    @ResponseBody
    //    public ReturnDTO couponlist(String params){
    //        logger.info("#req-->/coupon/list.req params:"+params);
    //
    //        Result result = this.getResult();
    //        UserInfo userInfo = result.getUserInfo();
    //        ReturnDTO validateResult = combineValidate(params, userInfo);
    //        if(validateResult != null){
    //            return validateResult;
    //        }
    //
    //        QueryCouponListParams queryCouponListParam = JSON.parseObject(params, new TypeReference<QueryCouponListParams>(){});
    //        if(null == queryCouponListParam){
    //            queryCouponListParam = new QueryCouponListParams();
    //        }
    //
    //        APIPhoneReqeustParamsValidate.validateRequestQueryCouponListParam(result, queryCouponListParam);
    //        if(result.getStatus() != HttpResponseConstants.SUCCESS){
    //            return responseFail(result.getMsg());
    //        }
    //
    //        apiService.queryCouponList(result, queryCouponListParam);
    //
    //        return result.DTO();
    //    }

    /**
     * 查询订单详情
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/order/{orderCode}", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnDTO detail(@PathVariable String orderCode){
        logger.info("#req-->/order/"+orderCode);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();
        //校验用户是否登录
        ReturnDTO validateResult = combineValidate(orderCode, userInfo);
        if(validateResult != null){
            return validateResult;
        }

        apiService.queryOrderDetail(result, orderCode);

        return result.DTO();
    }


    /**
     * 打印票据
     * @return
     */
    @RequestMapping(value = "/print/info")
    @ResponseBody
    public ReturnDTO print() {
        logger.info("#req-->/print/info");

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
     * 查询活动【一元免单】订单列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/order/activity/list", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnDTO activityOrderlist(String params){
        logger.info("#req-->/order/activity/list.req params:"+params);

        Result result = this.getResult();
        UserInfo userInfo = result.getUserInfo();
        ReturnDTO validateResult = combineValidate(params, userInfo);
        if(validateResult != null){
            return validateResult;
        }

        //请求参数转换
        ReqQueryActivityOrderListParams queryOrderParam = JSON.parseObject(params, new TypeReference<ReqQueryActivityOrderListParams>(){});
        if(null == queryOrderParam){
            queryOrderParam = new ReqQueryActivityOrderListParams();
        }


        //参数校验
        APIPhoneReqeustParamsValidate.validateRequestQueryOrderListParam(result, queryOrderParam);
        if(result.getStatus() != HttpResponseConstants.SUCCESS){
            return responseFail(result.getMsg());
        }

        //查询结果集
        apiService.queryActivityOrderList(result, queryOrderParam);

        return result.DTO();
    }


    /**
     * 根据手机号码 获取号码段
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO queryListCoupon(String mobile,String price){
        logger.info("#url:/coupon/list-- req params mobile:{},price:{}",mobile,price);

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();
        ReturnDTO validate = combineValidate(userInfo,mobile,price);
        if(validate != null){
            return validate;
        }

        //获取优惠券列表，并且安装到期时间升序
        List<CouponVO> lstCoupon = apiService.queryListCoupons(result, mobile, price).getData();
        return responseSuccess("获取成功",lstCoupon);

    }



    /**
     * @param params
     * @param userInfo
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

    /**
     * @param params
     * @param userInfo
     */
    private ReturnDTO combineValidate(UserInfo userInfo,String... params) {
        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        for(String param : params){
            validate = super.validateRequestParams(param);
            if(validate != null){
                return validate;
            }
        }

        return null;
    }




}
