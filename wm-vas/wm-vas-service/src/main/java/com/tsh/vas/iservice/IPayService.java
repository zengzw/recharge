/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.iservice;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;

/**
 *  支付接口 
 * 
 * @author zengzw
 * @date 2017年5月25日
 */
public interface IPayService {
    
    /**
     * 支付接口 
     * 
     * 
     * @param result
     * @param orderInfo
     * @throws Exception
     */
    public Result pay(Result result, PhoneOrderInfoPo orderInfo) throws Exception;

}
