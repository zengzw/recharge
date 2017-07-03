/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.commons;

/**
 *
 * @author zengzw
 * @date 2017年5月8日
 */
public class RTCommons {

    private RTCommons(){};

    
    /**
     * 充值回调状态
     *
     * @author zengzw
     * @date 2017年5月8日
     */
    public interface RechargeCallbackStatus{

        /**
         * 成功
         */
        int SUCCESS = 0;

        /**
         * 失败
         */
        int FAIL = 1;
    }
    
    
    
    /**
     * 下单充值状态
     *
     * @author zengzw
     * @date 2017年5月9日
     */
    public  interface RechargeStatus{
       /* 
        int success = 1000; //充值成功
        int fail = 1001; //充值失败 
        int no_set_supplier = 1002;//未设置供应商 
        int server_error = 1003;
                1004=交易密码错误
                1005=代理商账号已被冻结
                1006=未开通该业务类型
                1007=无效号码
                1008=未上架该产品
                1009=非法号段
                1010=余额不足
                1011=订单正在处理中
                1012=其他
                1013=代理号不存在
                1014=充值超时
                1016=相同流水号不允许重复提交*/
    }

}
