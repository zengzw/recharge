/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.gaoypay.parse;

import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.recharge.gaoypay.xmlbean.Mobile;
import com.tsh.phone.recharge.gaoypay.xmlbean.PostResult;
import com.tsh.phone.recharge.gaoypay.xmlbean.Product;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.PhoneInfoVo;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;

/**
 *  封装接口对象为统一返回对象
 *  
 * @author zengzw
 * @date 2016年7月27日
 */
public class GaoYBeanParse {

    public static OrderInfoVo parseOrder(PostResult postResult){
        if(postResult == null){
            return null;
        }
        OrderInfoVo orderInfo = new OrderInfoVo();
        orderInfo.setOrderNo(postResult.getOrderId());
        orderInfo.setSpOrderNo(postResult.getTranid());
        orderInfo.setFinishMoney(postResult.getFinishMoney());
        orderInfo.setOrderStatus(getOrderStatus(postResult.getResultNo()));

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


    public static RechargeVo parseRechargeInfo(PostResult result){
        if(result == null){
            return null;
        }
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(result.getOrderId());
        rechargeVo.setSpOrderNo(result.getTranid());
        rechargeVo.setOrderStauts(getRechargeStatus(result.getResultNo()));
        return rechargeVo;

    }




    public static PhoneLocationVo parsePhoneLocation(Mobile mobile){
        PhoneLocationVo phoneLocationVo = new PhoneLocationVo();
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
        if(orderStatus.equals("1")
               || orderStatus.equals("1018")
               || orderStatus.equals("1006")
               || orderStatus.equals("1000")){
            return Configurations.OrderStatus.RECHARGEING;
        }
        else if(orderStatus.equals("2")){
            return Configurations.OrderStatus.SUCCESS;
        }
        else  if(orderStatus.equals("3")){
            return Configurations.OrderStatus.PARTSUCCESS;
        }
        else if(orderStatus.equals("4")){
            return Configurations.OrderStatus.FAILED;
        }


        return Configurations.OrderStatus.FAILED;
    }


    /*
     * 充值状态 
     */
    private static String getRechargeStatus(String orderStatus){
        if(orderStatus.equals("0000")){
            return Configurations.RechargeStatus.SUCCESS;
        }
        else if(orderStatus.equals("0001")){
            return Configurations.RechargeStatus.PAY_ERROR;
        }
        else  if(orderStatus.equals("1000")){
            return Configurations.RechargeStatus.ERROR;
        }
        else if(orderStatus.equals("0002")){
            return Configurations.RechargeStatus.unknow_error;
        }


        return Configurations.RechargeStatus.ERROR;
    }
}
