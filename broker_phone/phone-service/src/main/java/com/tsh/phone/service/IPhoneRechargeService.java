/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.service;

import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 *
 * 话费充值接口
 * 
 * @author zengzw
 * @date 2016年7月22日
 */
public interface IPhoneRechargeService {

    
    /**
     * 根据订单Id 查询订单状态
     * 
     * @param reqParams
     * @return
     */
   public ResponseResult queryOrderById(RequestOrderInfoVo reqParams);
    
    
    /**
     * 根据手机号码 查询具体运营商 
     * 
     * @param reqParams
     * @return
     */
   public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo reqParams);
    
    
    /**
     * 具体充值接口
     * 
     * @param reqParams
     * @return
     */
    public ResponseResult recharge(RequestRechargeVo reqParams);
    
    
    
    /**
     * 消息回调
     * 
     * @param reqeust
     * @return
     */
    public void callback(RequestCallbackVo reqeust);
    
    
    /**
     * 取消充值
     * 
     * @param reqParams
     */
    public ResponseResult reversal(RequestReverseVo reqParams);
    
}
