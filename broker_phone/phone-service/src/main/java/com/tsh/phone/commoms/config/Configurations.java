/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.config;


/**
 *  公共配置文件
 *  
 * @author zengzw
 * @date 2016年7月22日
 */
public class Configurations {


    private Configurations(){};


    public static final String SP_TYPE_GY = "GY";

    public static final String SP_TYPE_OF = "OF";

    public static final String SP_TYPE_FL = "FL";
    
    
    public static final String SP_TYPE_RT = "RT";

    /**
     * 订单号前缀
     *
     * @author zengzw
     * @date 2016年7月29日
     */
    public interface OrderNoType{

        /** 高阳平台 订单好前缀。没要求 **/

        String GY = "GY";

        /** 欧飞平台规则要求 要以IP开头 **/
        String OF = "IP";

        /** 欧飞平台规则要求 要以FL开头 **/
        String FL = "FL";
        
        /** 瑞通平台规则要求 要以FL开头 **/
        String RT = SP_TYPE_RT;
    }


    /**
     *  环境变量
     */
    public interface ENVInfo{
        String DEV = "dev";

        String TEST = "test";

        String UAT = "uat";

        String PROD = "product";
    }


    /**
     * 手机类型
     *
     * @author zengzw
     * @date 2016年7月29日
     */
    public interface PhoneType{
        /** 固定电话 **/
        String  FIXED_PHONE = "固定电话";

        /** 移动电话 **/
        String  MOBILE_PHONE = "移动电话";

        /** 小灵通 **/
        String  PHS = "小灵通";
    }
    
    

    /**
     * 通用 订单状态。
     * 
     *
     * @author zengzw
     * @date 2016年7月27日
     */
    public interface OrderStatus{

        /** 初始状态 **/
        String INITIAL = "-1";

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

        /** 不确定 **/
        String NOT_CONFIRM = "5";
    }


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
     * 取消 充值状态
     *
     * @author zengzw
     * @date 2016年7月27日
     */
    public interface ReversalStatus{
        /**失败**/
        String ERROR = "0"; 
    
        
        /**成功**/
        String SUCCESS = "1";
        
        
        /** 未知异常 **/
        String UNKNOW_ERROR = "3";
        
        
    }

}
