/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.iservice;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;

/**
 *  结算接口
 *
 * @author zengzw
 * @date 2017年5月25日
 */
public interface IPaySettleService {

    
    /**
     * 结算接口
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result paySettle(Result result, String orderCode) throws Exception ;
    
    
    
    /**
     * 新结算接口
     * 
     * @param result
     * @param orderInfoPo
     * @return
     * @throws Exception
     */
    public Result paySettleV1(Result result, PhoneOrderInfoPo orderInfoPo) throws Exception;
    
    
    /**
     *  确认资金结算
     *  
     *  
     * @param result
     * @param openPlatformNo 开放平台外部订单号
     * @param immediatelySettle  缴费成功：false、失败:true
     * @return Result
     * @throws Exception
     */
    public Result confirmSettle(Result result, String openPlatformNo,boolean immediatelySettle) throws Exception;
    
    
}
