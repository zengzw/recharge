/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.controller.validate;

import com.tsh.phone.commoms.config.BaseConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;
import com.tsh.phone.vo.sign.PhoneRequestSingUtils;

/**
 *  请求参数校验
 *  
 * 
 * @author zengzw
 * @date 2017年2月21日
 */
public class PhoneRequestValidates {

    /**
     * 查询缴费单位请求参数校验
     */
    public static ResponseResult validateQueryOrderInfoVo(RequestOrderInfoVo reqOrderInfo,BaseConfig baseConfig){
        ResponseResult result = new ResponseResult();
        
        if(StringUtils.isEmpty(reqOrderInfo.getOrderId())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "orderId参数为空");
            return result;
        }

        String signed = PhoneRequestSingUtils.signQueryOrderInfo(reqOrderInfo,baseConfig.getBusinessKey());
        if(!signed.equals(reqOrderInfo.getSign())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage("签名不正确");
            return result;
        }

        return result;
    }
    
    
    /**
     * 查找手机号码归属地、运营商 校验
     */
    public static ResponseResult validatePhoneTypeVo(RequestQueryPhoneTypeVo reqPhoneType,BaseConfig baseConfig){
        ResponseResult result = new ResponseResult();
        
        if(StringUtils.isEmpty(reqPhoneType.getMobileNum())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "mobileNum参数为空");
            return result;
        }
        
        String signed = PhoneRequestSingUtils.signQueryPhoneType(reqPhoneType,baseConfig.getBusinessKey());
        if(!signed.equals(reqPhoneType.getSign())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage("签名不正确");
            return result;
        }
        
        return result;
    }


    /**
     * 查找手机号码归属地、运营商 校验
     */
    public static ResponseResult validateFLPhoneTypeVo(RequestQueryPhoneTypeVo reqPhoneType, BaseConfig baseConfig){
        ResponseResult result = new ResponseResult();

        if(StringUtils.isEmpty(reqPhoneType.getMobileNum())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "mobileNum参数为空");
            return result;
        }


        if(StringUtils.isEmpty(reqPhoneType.getMoney())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "money参数为空");
            return result;
        }


        String signed = PhoneRequestSingUtils.signFLQueryPhoneType(reqPhoneType,baseConfig.getBusinessKey());
        if(!signed.equals(reqPhoneType.getSign())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage("签名不正确");
            return result;
        }

        return result;
    }
    
    
    /**
     * 充值参数校验
     */
    public static ResponseResult validateRechargeVo(RequestRechargeVo reqRecharge,BaseConfig baseConfig){
        ResponseResult result = new ResponseResult();
        
        if(StringUtils.isEmpty(reqRecharge.getMobileNum())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "mobileNum参数为空");
            return result;
        }
        
        if(reqRecharge.getPrice() == 0){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "充值金额不对！");
            return result;
        }
        
        String signed = PhoneRequestSingUtils.signRecharge(reqRecharge,baseConfig.getBusinessKey());
        if(!signed.equals(reqRecharge.getSign())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage("签名不正确");
            return result;
        }
        
        return result;
    }
    
    
    /**
     * 取消充值参数校验
     */
    public static ResponseResult validateReverseVo(RequestReverseVo reqRecharge,BaseConfig baseConfig){
        ResponseResult result = new ResponseResult();
        
        if(StringUtils.isEmpty(reqRecharge.getMobileNum())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "mobileNum参数为空");
            return result;
        }
        
        if(reqRecharge.getPrice() == 0){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "充值金额不对！");
            return result;
        }
        
        if(StringUtils.isEmpty(reqRecharge.getOrderNo())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage( "订单号为空！");
            return result;
        }
        
        
        String signed = PhoneRequestSingUtils.signReverse(reqRecharge,baseConfig.getBusinessKey());
        if(!signed.equals(reqRecharge.getSign())){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setMessage("签名不正确");
            return result;
        }
        
        return result;
    }

}
