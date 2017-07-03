/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.validate;

import com.tsh.vas.phone.vo.QueryCouponListParams;
import org.apache.commons.lang.StringUtils;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.phone.vo.ReqQueryActivityOrderListParams;
import com.tsh.vas.phone.vo.ReqQueryOrderListParams;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.vo.req.RequestQueryOrderParam;
import com.tsh.vas.trainticket.vo.req.RequestValidateSmsCodeParam;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;

/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class APIPhoneReqeustParamsValidate extends BaseController{



    /**
     *  校验手机验证码请求参数
     *  
     * @param result
     * @param params
     */
    public static void validateSmsCode(Result result,RequestValidateSmsCodeParam params){
        if(params == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return ;
        }


        if(StringUtils.isEmpty(params.getMobile())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("手机号码为空!");
            return ;
        }

        if(StringUtils.isEmpty(params.getCode())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("验证码为空!");
            return ;
        }

        result.setStatus(HttpResponseConstants.SUCCESS);

    }


    /**
     * 校验下单请求参数
     * 
     * @param result
     * @param orderInfo
     */
    public static void validateOrderInfoVO(Result result,PhoneOrderInfoVo orderInfo){

        if(StringUtils.isEmpty(orderInfo.getMobile())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("支付电话为空");
            return ;
        }

        if(StringUtils.isEmpty(orderInfo.getPayPassword())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("支付密码为空");
            return ;
        }

        if(StringUtils.isEmpty(orderInfo.getPayUserName())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("支付用户名为空");
            return ;
        }

        //角色类型 2:平台管理 3:县域 4:网点，5：供应商 9：会员
        if(orderInfo.getBizType() == null || orderInfo.getBizType() == 0){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("用户类型为空");
            return ;
        }


        if (StringUtils.isEmpty (orderInfo.getBusinessCode ())) {
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg ("父业务编码不能为空");
            return;
        }



        if (StringUtils.isEmpty (orderInfo.getSubBusinessCode())) {
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg ("子业务编码不能为空");
            return;
        }




        if(StringUtils.isEmpty (orderInfo.getRechargePhone())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("充值手机号码为空");
            return ;
        }


        if(StringUtils.isEmpty (orderInfo.getGoodsId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("商品ID不能为空");
            return ;
        }


        if(orderInfo.getSaleAmount() == null ){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("销售价为空");
            return ;
        }


        if(orderInfo.getRealAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("实际支付价格为空");
            return ;
        }


        if(orderInfo.getOriginalAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("应付价格为空");
            return ;
        }

    }
    
    
    /**
     * 校验下故乡单请求参数
     * 
     * @param result
     * @param orderInfo
     */
    public static void validateGXOrderInfoVO(Result result,PhoneOrderInfoVo orderInfo){
        if (StringUtils.isEmpty (orderInfo.getBusinessCode ())) {
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg ("父业务编码不能为空");
            return;
        }
        
        
        
        if (StringUtils.isEmpty (orderInfo.getSubBusinessCode())) {
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg ("子业务编码不能为空");
            return;
        }
        
        
        
        
        if(StringUtils.isEmpty (orderInfo.getRechargePhone())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("充值手机号码为空");
            return ;
        }
        
        
        if(StringUtils.isEmpty (orderInfo.getGoodsId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("商品ID不能为空");
            return ;
        }
        
        
        if(orderInfo.getSaleAmount() == null ){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("销售价为空");
            return ;
        }
        
        
        if(orderInfo.getRealAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("实际支付价格为空");
            return ;
        }
        
        
        if(orderInfo.getOriginalAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("应付价格为空");
            return ;
        }
        
        
        if(StringUtils.isEmpty(orderInfo.getOpenUserId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("openId 为空");
            return ;
        }
    }


    /**
     * 校验订单查询接口
     * 
     * @param result
     * @param param
     */
    public static void validateRequestQueryOrderParam(Result result,RequestQueryOrderParam param){
        if(StringUtils.isEmpty(param.getOrderId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("供应商订单号为空");
            return;
        }

        if(StringUtils.isEmpty(param.getMerchantOrderId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("外部订单号为空");
            return;
        }
    }

    /**
     * 校验订单查询接口
     * 
     * @param result
     * @param param
     */
    public static void validateRequestQueryOrderListParam(Result result,ReqQueryOrderListParams param){
        if(param.getPage() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数 page为空");
            return;
        }

        if(param.getRows() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数rows为空");
            return;
        }
    }

    /**
     * 校验查询优惠券接口
     *
     * @param result
     * @param queryCouponListParam
     */
    public static void validateRequestQueryCouponListParam(Result result,  QueryCouponListParams queryCouponListParam){
        if(StringUtils.isBlank(queryCouponListParam.getMobile())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数 手机号为空为空");
            return;
        }

    }

    /**
     * 校验订单查询接口
     * 
     * @param result
     * @param param
     */
    public static void validateRequestQueryOrderListParam(Result result,ReqQueryActivityOrderListParams param){
        if(param.getPage() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数 page为空");
            return;
        }
        
        if(param.getRows() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数rows为空");
            return;
        }
        
        if(StringUtils.isEmpty(param.getType())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数type为空");
            return;
        }
    }
    

}
