package com.tsh.vas.vo.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/21 021.
 */
@Entity
@Table(name = "phone_use_coupon_record")
public class PhoneUseCouponRecordVo implements Serializable {

    private Integer id;// '主键ID',
    private String orderCode;// '订单编号',
    private String couponMoneyType;// '优惠卷金额类型',
    private Integer couponStatus;// '优惠券状态（0：正常 1：已领完  2：未使用 3：已经使用 4：已过期 5：未可用 6：已关联）',
    private Date updateTime;// '修改时间',
    private Date createTime;// '创建时间',
    private String couponType;// '优惠券类型',
    private String merchantUserId;// '结算商家ID',
    private String merchantUserType;// '结算商家类型（1:平台,2:县域,3:供应商）',
    private String money;// '优惠券面额',
    private String recordId;// '优惠券ID',
    
    /**
     * 
     */
    private String ruleId; //优惠券规制Id
    
    private String ruleCreateId;// '优惠券创建人ID',

    private String useBeginTime;// '优惠券使用开始时间',

    private String useEndTime;// '优惠券使用结束时间',
    private String useLimit;// '使用限制说明',
    private String useScope;// '使用范围',
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getCouponMoneyType() {
        return couponMoneyType;
    }
    public void setCouponMoneyType(String couponMoneyType) {
        this.couponMoneyType = couponMoneyType;
    }
    public Integer getCouponStatus() {
        return couponStatus;
    }
    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getMerchantUserId() {
        return merchantUserId;
    }
    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }
    public String getMerchantUserType() {
        return merchantUserType;
    }
    public void setMerchantUserType(String merchantUserType) {
        this.merchantUserType = merchantUserType;
    }
    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getRuleId() {
        return ruleId;
    }
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
    public String getRuleCreateId() {
        return ruleCreateId;
    }
    public void setRuleCreateId(String ruleCreateId) {
        this.ruleCreateId = ruleCreateId;
    }
    public String getUseBeginTime() {
        return useBeginTime;
    }
    public void setUseBeginTime(String useBeginTime) {
        this.useBeginTime = useBeginTime;
    }
    public String getUseEndTime() {
        return useEndTime;
    }
    public void setUseEndTime(String useEndTime) {
        this.useEndTime = useEndTime;
    }
    public String getUseLimit() {
        return useLimit;
    }
    public void setUseLimit(String useLimit) {
        this.useLimit = useLimit;
    }
    public String getUseScope() {
        return useScope;
    }
    public void setUseScope(String useScope) {
        this.useScope = useScope;
    }
    
}
