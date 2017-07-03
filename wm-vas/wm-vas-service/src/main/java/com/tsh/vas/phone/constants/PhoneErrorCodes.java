/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constants;

import com.tsh.vas.commoms.constants.BusinessCodes;

/**
 *
 * @author zengzw
 * @date 2017年6月26日
 */
public class PhoneErrorCodes {
    
    /**
     * 100 ~ 119
     * 
     *  dubbo接口错误代码
     *
     * @author zengzw
     * @date 2017年6月26日
     */
    public interface DubboAPI{
        
        /**
         * 优惠券其他异常
         */
        String COUPON_OTHER_ERROR = BusinessCodes.PHONE + "100";
        
        /**
         * 优惠券绑定失败
         */
        String COUPON_BIND_ERROR = BusinessCodes.PHONE + "101";
        
        
        /**
         * 修改优惠券状态失败
         */
        String COUPON_UPDATE_ERROR = BusinessCodes.PHONE + "102";
        
    }
    
    
    
    
    /**
     * 120 ~ 139
     * 
     * 资金账户错误代码
     *
     * @author zengzw
     * @date 2017年6月26日
     */
    public interface FundAPI{
        
        
    }

}
