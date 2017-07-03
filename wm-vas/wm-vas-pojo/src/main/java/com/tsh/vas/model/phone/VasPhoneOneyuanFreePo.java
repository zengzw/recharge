package com.tsh.vas.model.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "vas_phone_oneyuan_free")
public class VasPhoneOneyuanFreePo implements Serializable{


    /**  主键ID*/
    private Integer id;
    /**  订单编号*/
    private String orderCode;
    /**
     * 充值手机号码
     */
    private String chargeMobile;

    /**
     * 充值金额
     */
    private BigDecimal chargeAmount;

    private String lotteryType;

    private String cashCoupon;

    /**  活动金额*/
    private Integer activityAmount;

    /**  下单时间*/
    private Date createTime;

    /**  审核状态,（0:待审核、1:通过、2:不通过，默认为”待审核”）*/
    private Integer checkStatus;

    /**  审核时间*/
    private Date checkTime;

    /**  开奖状态(0:待开奖、1:中奖、2:未中奖，默认为”待开奖”）*/
    private Integer lotteryStatus;
    /**  开奖时间*/
    private Date lotteryTime;
    /**  开奖人名称*/
    private String lotteryman;
    /**  幸运号*/
    private String luckyNumber;
    /**  业务类型(MPCZ：话费，HCP：火车票，DJDF：缴电费等）)*/
    private String bizType;
    /**  下单县域ID*/
    private Integer areaId;
    /**  下单县域名称*/
    private String areaName;
    /**  下单网点ID*/
    private Integer storeId;
    /**  下单网点名称*/
    private String storeName;
    /**  批次ID*/
    private Integer batchId;
    /**  中奖金额*/
    private String winningAmount;

    @Column(name = "activity_amount")
    public Integer getActivityAmount() {
        return activityAmount;
    }
    @Column(name = "area_id")
    public Integer getAreaId() {
        return areaId;
    }
    @Column(name = "area_name")
    public String getAreaName() {
        return areaName;
    }
    @Column(name = "biz_type")
    public String getBizType() {
        return bizType;
    }

    @Column(name="charge_amount")
    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    @Column(name = "charge_mobile")
    public String getChargeMobile() {
        return chargeMobile;
    }
    @Column(name = "check_status")
    public Integer getCheckStatus() {
        return checkStatus;
    }

    @Column(name = "check_time")
    public Date getCheckTime() {
        return checkTime;
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
    @Column(name = "lotteryman")
    public String getLotteryman() {
        return lotteryman;
    }

    @Column(name = "lottery_status")
    public Integer getLotteryStatus() {
        return lotteryStatus;
    }
    @Column(name = "lottery_time")
    public Date getLotteryTime() {
        return lotteryTime;
    }

    @Column(name = "lucky_number")
    public String getLuckyNumber() {
        return luckyNumber;
    }
    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    @Column(name = "store_id")
    public Integer getStoreId() {
        return storeId;
    }
    @Column(name = "store_name")
    public String getStoreName() {
        return storeName;
    }
    @Column(name = "batch_id")
    public Integer getBatchId(){
        return batchId;
    }
    @Column(name = "winning_amount")
    public String getWinningAmount(){ return winningAmount; }
    @Column(name = "lottery_type")
    public String getLotteryType(){ return lotteryType; }
    @Column(name = "cash_coupon")
    public String getCashCoupon(){ return cashCoupon; }

    public void setActivityAmount(Integer activityAmount) {
        this.activityAmount =activityAmount;
    }
    public void setAreaId(Integer areaId) {
        this.areaId =areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName =areaName;
    }
    public void setBizType(String bizType) {
        this.bizType =bizType;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
    public void setChargeMobile(String chargeMobile) {
        this.chargeMobile = chargeMobile;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus =checkStatus;
    }
    public void setCheckTime(Date checkTime) {
        this.checkTime =checkTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    public void setId(Integer id) {
        this.id =id;
    }

    public void setLotteryman(String lotteryman) {
        this.lotteryman =lotteryman;
    }
    public void setLotteryStatus(Integer lotteryStatus) {
        this.lotteryStatus =lotteryStatus;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime =lotteryTime;
    }
    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber =luckyNumber;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode =orderCode;
    }
    public void setStoreId(Integer storeId) {
        this.storeId =storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName =storeName;
    }
    public void setBatchId(Integer batchId){
        this.batchId = batchId;
    }

    public void setWinningAmount(String winningAmount){ this.winningAmount = winningAmount; }
    public void setLotteryType(String lotteryType) { this.lotteryType = lotteryType; }
    public void setCashCoupon(String cashCoupon) { this.cashCoupon = cashCoupon; }
}
