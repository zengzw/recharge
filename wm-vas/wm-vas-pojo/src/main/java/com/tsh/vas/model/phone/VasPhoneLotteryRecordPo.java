package com.tsh.vas.model.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/24 024.
 */
@Entity
@Table(name = "vas_phone_lottery_record")
public class VasPhoneLotteryRecordPo implements Serializable {

    private Integer id;//'主键',
    private String orderCode;// '订单编号',
    private String cashCoupon;// '代金券码',
    private String createTime;
    private String name;// '优惠券活动名称',
    private String range;// '适用范围',
    private String region;// '优惠区域',
    private String amount;// '面额',
    private String dateRange;// '使用时间范围',
    private Integer count;// '有效张数',


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "cash_coupon")
    public String getCashCoupon() {
        return cashCoupon;
    }

    public void setCashCoupon(String cashCoupon) {
        this.cashCoupon = cashCoupon;
    }

    @Column(name = "activity_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "use_range")
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Column(name = "date_range")
    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
