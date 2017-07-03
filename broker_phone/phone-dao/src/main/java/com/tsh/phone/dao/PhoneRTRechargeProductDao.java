/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.dao;

import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.tsh.phone.po.PhoneRTRechargeProduct;

/**
 * 瑞通 话费充值供货价
 * 
 * @author zengzw
 * @date 2016年7月29日
 */

@Repository
public class PhoneRTRechargeProductDao extends HibernateDao<PhoneRTRechargeProduct,Integer> {

    /**
     * 根据条件匹配相关产品
     * 
     * @param provinceId 省份名称
     * @param prodIspType 卡类型（移动、灵通）
     * @param prodContent 面值
     * 
     * @return
     */
    public PhoneRTRechargeProduct queryProduct(String provinceId,String prodIspType,Integer price){
        String hql = "from PhoneRTRechargeProduct where prodProvinceid = ?"
                + " and prodIsptype=?  and prodContent=? and status='ON'";
        PhoneRTRechargeProduct phoneRechargeProduct = this.findUnique(hql,provinceId,prodIspType,price);

        return phoneRechargeProduct;
    }


}
