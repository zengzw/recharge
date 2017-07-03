/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constants;

/**
 *
 * @author zengzw
 * @date 2017年3月9日
 */
public class PhoneConstants {
    
    
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = "/views/phonefare/detail.html";
    
    
    /**
     * 参加活动状态
     */
    public  final static int JOIN_STATUS = 1;
    

    /**
     *  充值回调状态
     *
     * @author zengzw
     * @date 2017年2月22日
     */
    public  interface RechargeCallBackStatus{

        /**充值成功 **/
         int SUCCESS = 1;  

        /**部分成功 **/
         int PARTSUCCESS = 2; 

        /** 充值失败 **/
         int FAILED = 3; 

        /** 找不到此订单 **/
         int NOT_EXISTS = 4;
         
         /**
          * 其他异常
          */
         int OTHER = 5;
    }
    
    
    
    /**
     * 通用 订单状态。
     * 
     *
     * @author zengzw
     * @date 2016年7月27日
     */
    public interface OrderStatus{

        /**正在充值 **/
        String RECHARGEING = "0"; 

        /**充值成功 **/
        String SUCCESS = "1";  

        /**部分成功 **/
        String PARTSUCCESS = "2"; 

        /** 充值失败 **/
        String FAILED = "3"; 

        /** 找不到此订单 **/
        String NOT_EXISTS = "4";

        /** 不确定状态 **/
        String NOT_CONFIRM = "5";
    }
    
    

}
