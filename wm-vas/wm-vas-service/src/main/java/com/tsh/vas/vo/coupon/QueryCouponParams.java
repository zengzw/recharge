/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo.coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 *
 * @author zengzw
 * @date 2017年6月22日
 */
public class QueryCouponParams {
    
    
    /**
     * 订单号
     */
    private Double money;
    
    
    /**
     * 订单号
     */
    private String orderNo;
    
    
    /**
     * 网点ID,
     */
    private long shopId;
    
    /**
     *   使用范围类型，1：默认使用范围（商品类目等） 2：话费充值。支持多类型查询。
     */
    private Integer[] useScopeTypes;

    /** 
     *  优惠券Id 列表
     */
    private Integer[] couponIds;


    /**
     * 会员ID
     */
    private int  userId;

    public Integer[] getCouponIds() {
        return couponIds;
    }

    public Double getMoney() {
        return money;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public long getShopId() {
        return shopId;
    }

    public int getUserId() {
        return userId;
    }

    public Integer[] getUseScopeTypes() {
        return useScopeTypes;
    }

    public void setCouponIds(Integer[] couponIds) {
        this.couponIds = couponIds;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
   
    
    public void setUseScopeTypes(Integer[] useScopeTypes) {
        this.useScopeTypes = useScopeTypes;
    }

    public static void main(String[] args) {
        List<QueryCouponParams> list = new ArrayList<>();
        QueryCouponParams params = new QueryCouponParams();
        params.setMoney(222D);
        params.setOrderNo("orerId");
        params.setShopId(11);
        params.setUserId(22);
        list.add(params);
        
        QueryCouponParams params1 = new QueryCouponParams();
        params1.setMoney(223D);
        params1.setOrderNo("orerId2");
        params1.setShopId(113);
        params1.setUserId(223);
        list.add(params1);
        
        Map<String, Object> map = new HashMap<>();
        map.put(params.getOrderNo(), list);
        
        
        String jsonResult = JSON.toJSONString(map);
        System.out.println(jsonResult);
        
        
        Map<String, List<QueryCouponParams>> parseMap = JSON.parseObject(jsonResult,new TypeReference<Map<String, List<QueryCouponParams>>>(){});
        
        System.out.println(parseMap);
        
    }
}
