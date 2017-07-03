/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.utils;

/**
 *
 * @author zengzw
 * @date 2017年3月6日
 */
public class PhoneCommon {

    /**
     * 通用的 充值状态
     *
     * @author zengzw
     * @date 2016年7月27日
     */
    public interface RechargeStatus{

        /**下单成功**/
        String SUCCESS = "1";

        /**下单失败**/
        String ERROR = "0"; 

        /**支付失败 **/
        String PAY_ERROR = "2";

        /** 未知异常 **/
        String unknow_error = "3";


    }
    
    /**
     * 通用的  取消充值状态状态
     *
     * @author zengzw
     * @date 2016年7月27日
     */
    public interface ReversalStatus{
        
        /**成功**/
        String SUCCESS = "1";
        
        /**失败**/
        String ERROR = "0"; 
        
        
        /** 未知异常 **/
        String unknow_error = "3";
        
        
    }
}
