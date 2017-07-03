package com.tsh.vas.vo.phone;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/23 023.
 */
public class CashCouponVo implements Serializable {

    private Integer id;//代金券ID
    private String name;//活动名称
    private String range;//适用范围
    private String area;//优惠区域
    private String amount;//面额
    private String dateRange;//使用时间范围
    private Integer count;//有效张数

    private String orderCode;
    private Integer activityId;// 增值服务活动ID
    private Long storeId;
    private Long userId;
    private String cashCoupon;// 代金券


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCashCoupon() {
        return cashCoupon;
    }

    public void setCashCoupon(String cashCoupon) {
        this.cashCoupon = cashCoupon;
    }
}
