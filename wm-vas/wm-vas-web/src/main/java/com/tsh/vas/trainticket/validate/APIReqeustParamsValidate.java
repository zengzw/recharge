/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.validate;

import java.util.List;

import com.tsh.vas.trainticket.vo.req.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.vo.VerifyUserModel;
import com.tsh.vas.vo.trainticket.HcpOrderInfoVo;
import com.tsh.vas.vo.trainticket.TrainUserInfoVo;

/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class APIReqeustParamsValidate extends BaseController{


    /**
     * 验证请求参数
     * @param result
     * @param params
     */
    public static void validateRequestQueryTicketParam(Result result,RequestQueryTicketParam params){
        if(params == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return;
        }


        if(StringUtils.isEmpty(params.getTravelTime())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("乘车时间为空");
            return;
        }

        if(StringUtils.isEmpty(params.getFromStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("始发站为空");
            return;
        }

        if(StringUtils.isEmpty(params.getArriveStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("终点站为空");
            return;
        }

        result.setStatus(HttpResponseConstants.SUCCESS);
    }

    /**
     * 验证查询服务费参数
     * @param result
     * @param param
     */
    public static void validateQueryServiceFee(Result result, QueryServiceFeeParam param){
        if(null == param){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getBusinessCode())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("服务代码为空");
            return;
        }

        if(StringUtils.isEmpty(param.getSupplierCode())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("供应商编号为空");
            return;
        }
    }

    /**
     * 验证查询车次余票参数
     * @param result
     * @param param
     */
    public static void validateQueryTicketNumber(Result result, RequestQueryTicketNumParam param){
        if(null == param){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getArriveStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("终点站为空");
            return;
        }

        if(StringUtils.isEmpty(param.getFromStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("始发站为空");
            return;
        }

        if(StringUtils.isEmpty(param.getTrainCode())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("车次编号为空");
            return;
        }

        if(StringUtils.isEmpty(param.getTravelTime())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("乘车时间为空");
            return;
        }

    }

    /**
     * 校验用户信息
     * @param result
     * @param lstModel
     */
    public static void  validateUserInfo(Result result,List<VerifyUserModel> lstModel){
        if(CollectionUtils.isEmpty(lstModel)){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return ;
        }

        boolean validate = true;
        for(VerifyUserModel model:lstModel){
            if(StringUtils.isEmpty(model.getIdType())){
                validate = false;
                break;
            }

            if(StringUtils.isEmpty(model.getUserId())){
                validate = false;
                break;
            }

            if(StringUtils.isEmpty(model.getUserName())){
                validate = false;
                break;
            }
        }

        if(!validate){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
        }else{
            result.setStatus(HttpResponseConstants.SUCCESS);
        }
    }



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
     * 退票参数验证
     * 
     * @param result
     * @param params
     */
    public static void validateReturnTicketParams(Result result,RequestReturnTicketParam params){
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

        if(StringUtils.isEmpty(params.getOrderNo())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("订单编码为空");
            return ;
        }

    }


    /**
     * 校验下单请求参数
     * 
     * @param result
     * @param orderInfo
     */
    public static void validateOrderInfoVO(Result result,HcpOrderInfoVo orderInfo){

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

        if(StringUtils.isEmpty(orderInfo.getPayPassword())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("支付用户名为空");
            return ;
        }

        if(orderInfo.getBizType() == null || orderInfo.getBizType() == 0){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("用户类型为空");
            return ;
        }

        if(orderInfo.getPartSubmit() == null){
            result.setMsg("partSubmit参数位空！");
            return ;
        }
        
        if(StringUtils.isEmpty(orderInfo.getArriveStation())){
            result.setMsg("到达车站为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getStationArriveTime())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("到达时间为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getFromStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("触发车站为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getArriveStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("到达车站为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getFromStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("出发时间为空");
            return ;
        }
        if(orderInfo.getSeatType() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("座位类型为空");
            return ;
        }
        if(orderInfo.getRealAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("实际支付价格为空");
            return ;
        }
        if(orderInfo.getCostingAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("成本价格为空");
            return ;
        }

        if(orderInfo.getOriginalAmount() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("应付价格为空");
            return ;
        }
        if(orderInfo.getServicePrice() == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("服务费为空");
            return ;
        }

        if(StringUtils.isEmpty(orderInfo.getArriveStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("到达车站为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getTrainCode())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("车次为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getArriveStation())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("到达车站为空");
            return ;
        }
        if(StringUtils.isEmpty(orderInfo.getTravelTime())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("乘车日期为空");
            return ;
        }


        if(CollectionUtils.isEmpty(orderInfo.getUserDetailList())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("乘车用户为空");
            return ;
        }

       
        for(TrainUserInfoVo userInfo : orderInfo.getUserDetailList()){
            if(StringUtils.isEmpty(userInfo.getUserName())){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("乘车用户名为空");
                break;
            }
            if(StringUtils.isEmpty(userInfo.getUserId())){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("乘车证件为空");
                break;
            }
            if(StringUtils.isEmpty(userInfo.getTicketType())){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("乘车用户票据类型为空");
                break;
            }
            if(userInfo.getIdType() == null
                    || userInfo.getIdType() == 0){
                result.setStatus(HttpResponseConstants.ERROR);
                result.setMsg("证件类型为空");
                break;
            }

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
            
        }
        if(StringUtils.isEmpty(param.getMerchantOrderId())){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("外部订单号为空");
        }
    }
    

}
