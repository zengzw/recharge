/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.parse;

import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.recharge.gaoypay.xmlbean.Mobile;
import com.tsh.phone.recharge.gaoypay.xmlbean.Product;
import com.tsh.phone.recharge.rtpay.parse.resp.RespOrderInfo;
import com.tsh.phone.recharge.rtpay.parse.resp.RespRechargeInfo;
import com.tsh.phone.recharge.rtpay.parse.resp.RespReversalInfo;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.PhoneInfoVo;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.RespReversalInfoVo;

/**
 *  封装接口对象为统一返回对象
 *  
 * @author zengzw
 * @date 2016年7月27日
 */
public class RTBeanParse {


    /**
     * 订单信息转换
     * 
     * @param postResult
     * @return
     */
    public static OrderInfoVo parseOrder(RespOrderInfo postResult){
        if(postResult == null){
            return null;
        }
        OrderInfoVo orderInfo = new OrderInfoVo();
        orderInfo.setOrderNo(postResult.getData().getReqStreamId());
        orderInfo.setSpOrderNo(postResult.getData().getOrderNo());
        orderInfo.setOrderStatus(getOrderStatus(postResult.getStatus()));

        return orderInfo;
    }


    public static PhoneInfoVo parsePhoneInfo(Product product){
        if(product == null){
            return null;
        }
        PhoneInfoVo phoneInfoVo = new PhoneInfoVo();
        phoneInfoVo.setSpType(product.getProdIsptype());
        phoneInfoVo.setProvinceName(product.getProdProvinceid());
        phoneInfoVo.setPrice(product.getProdContent());
        phoneInfoVo.setSellerPrice(product.getProdContent());
        phoneInfoVo.setPhoneType(product.getProdType());

        return phoneInfoVo;

    }


    /**
     * 充值信息转换
     * 
     * @param result
     * @return
     */
    public static RechargeVo parseRechargeInfo(RespRechargeInfo result){
        if(result == null){
            return null;
        }
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(result.getData().getReqStreamId());
        rechargeVo.setSpOrderNo(result.getData().getOrderNo());
        rechargeVo.setCurrentStatus(result.getStatus());
        rechargeVo.setOrderStauts(getRechargeStatus(result.getStatus()));
        return rechargeVo;

    }

    /**
     * 充值信息转换
     * 
     * @param result
     * @return
     */
    public static RespReversalInfoVo parseRespReversalInfo(RespReversalInfo result){
        if(result == null){
            return null;
        }
        RespReversalInfoVo rechargeVo = new RespReversalInfoVo();
        rechargeVo.setOrderNo(result.getData().getReqStreamId());
        rechargeVo.setSpOrderNO(result.getData().getOrderNo());
        rechargeVo.setReversalTime(result.getData().getReversalTime());
        rechargeVo.setStatus(getRespReversalStatus(result.getStatus()));
        return rechargeVo;
        
    }
    



    public static PhoneLocationVo parsePhoneLocation(Mobile mobile){
        PhoneLocationVo phoneLocationVo = new PhoneLocationVo();
        if(mobile == null){
            return phoneLocationVo;
        }
        
        phoneLocationVo.setProvinceName(mobile.getProvinceName());
        phoneLocationVo.setType(mobile.getSpType());

        return phoneLocationVo;
    }


    /**
     * 最终订单状态
     * 
     * @param orderStatus
     * @return
     */
    private static String getOrderStatus(String orderStatus){
        if(orderStatus.equals("1011")
                || orderStatus.equals("1013")
                || orderStatus.equals("1003")
                || orderStatus.equals("1017")
                || orderStatus.equals("1019")){
            return Configurations.OrderStatus.RECHARGEING;
        }
        else if(orderStatus.equals("1000")){
            return Configurations.OrderStatus.SUCCESS;
        }
        else if(orderStatus.equals("1001")){
            return Configurations.OrderStatus.FAILED;
        }
        else if(orderStatus.equals("1015")){
            return Configurations.OrderStatus.NOT_EXISTS;
        }

        return Configurations.OrderStatus.FAILED;
    }


    /*
     * 充值状态 
     */
    private static String getRechargeStatus(String orderStatus){
        if(orderStatus.equals("1000") 
                || orderStatus.equals("1003")
                || orderStatus.equals("1011")
                || orderStatus.equals("1012")
                || orderStatus.equals("1014")){

            return Configurations.RechargeStatus.SUCCESS;
            
        }else if(orderStatus.equals("1001")){
            return Configurations.RechargeStatus.ERROR;
        }



        return Configurations.RechargeStatus.ERROR;
    }
    
    /*
     * 取消状态
     *  
     */
    private static String getRespReversalStatus(String orderStatus){
        if(orderStatus.equals("2000") ){
            
            return Configurations.RechargeStatus.SUCCESS;
            
        }else if(orderStatus.equals("2001")){
            
            return Configurations.RechargeStatus.ERROR;
        }
        
        
        
        return Configurations.RechargeStatus.ERROR;
    }
}
