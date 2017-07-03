/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsh.phone.dao.PhoneRTRechargeProductDao;
import com.tsh.phone.po.PhoneRTRechargeProduct;

/**
 *
 * 瑞通 供货价查询
 * 
 * @author zengzw
 * @date 2016年7月29日
 */

@Service
public class PhoneRTProductService{

    @Autowired
    private PhoneRTRechargeProductDao phoneRechargeDao;


    /**
     * 获取移动电话的产品
     * 
     * （城市 + 服务商类型 + 面值 +  服务类型 ） 确认一个产品
     * 
     * @param provinceId 省份
     * @param prodIspType 运营商类型
     * @param prodContent 充值金额
     */
    public PhoneRTRechargeProduct getProductOfMobilePhone(String provinceId,String supplierType,Integer price){

        return phoneRechargeDao.queryProduct(provinceId, supplierType, price);
    }

}
