package com.tsh.vas.model.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/21 021.
 */
@Entity
@Table(name = "phone_use_coupon_record")
public class PhoneUseCouponRecordPo implements Serializable {

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
    @Column(name = "coupon_money_type")
    public String getCouponMoneyType() {
        return couponMoneyType;
    }
    @Column(name = "coupon_status")
    public Integer getCouponStatus() {
        return couponStatus;
    }

    @Column(name = "coupon_type")
    public String getCouponType() {
        return couponType;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "merchant_user_id")
    public String getMerchantUserId() {
        return merchantUserId;
    }

    @Column(name = "merchant_user_type")
    public String getMerchantUserType() {
        return merchantUserType;
    }

    @Column(name = "money")
    public String getMoney() {
        return money;
    }

    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    @Column(name = "record_id")
    public String getRecordId() {
        return recordId;
    }

    @Column(name = "rule_create_id")
    public String getRuleCreateId() {
        return ruleCreateId;
    }

    @Column(name="rule_id")
    public String getRuleId() {
        return ruleId;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Column(name = "use_begin_time")
    public String getUseBeginTime() {
        return useBeginTime;
    }

    @Column(name = "use_end_time")
    public String getUseEndTime() {
        return useEndTime;
    }

    @Column(name = "use_limit")
    public String getUseLimit() {
        return useLimit;
    }

    @Column(name = "use_scope")
    public String getUseScope() {
        return useScope;
    }

    public void setCouponMoneyType(String couponMoneyType) {
        this.couponMoneyType = couponMoneyType;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public void setMerchantUserType(String merchantUserType) {
        this.merchantUserType = merchantUserType;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setRuleCreateId(String ruleCreateId) {
        this.ruleCreateId = ruleCreateId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUseBeginTime(String useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

    public void setUseEndTime(String useEndTime) {
        this.useEndTime = useEndTime;
    }

    public void setUseLimit(String useLimit) {
        this.useLimit = useLimit;
    }

    public void setUseScope(String useScope) {
        this.useScope = useScope;
    }
}
