/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.iservice;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;

/**
 * 退款接口
 *
 * @author zengzw
 * @date 2017年5月25日
 */
public interface IPayRefundService {
    
    /**
     * 退款接口
     * 
     * @param result
     *             参数传递
     * @param orderInfo
     *              订单信息
     * @param chargeRefund
     *              退款单信息
     * @throws Exception
     */
    public void refundMoney(Result result,PhoneOrderInfoPo orderInfo, PhoneRefundAmountPo chargeRefund) throws Exception;
    
    
    
    /**
     * 退款几口 V1
     * 
     *  @param result
     *             参数传递
     * @param orderInfo
     *              订单信息
     * @param chargeRefund
     *              退款单信息
     * @throws Exception
     */
    public void refundMoneyV1(Result result, PhoneOrderInfoPo orderInfo, PhoneRefundAmountPo chargeRefund) throws Exception;
    
    
}
