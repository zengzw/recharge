package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dtds.platform.commons.utility.DateUtil;


public class VasPhoneOneyuanFreeVo implements Serializable{
    private static final long serialVersionUID = 1033462384246849814L;

    public final static String FORMAT_STRING = "yyyy-MM-dd";

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

    /**  活动金额*/
    private Integer activityAmount;

    /**  下单时间*/
    private Date createTime;

    private String startCreateTime;

    private String endCreateTime;

    private String createTimeStr;

    private String createTimeString;

    /**  审核状态,（0:待审核、1:通过、2:不通过，默认为”待审核”）*/
    private Integer checkStatus;

    /**  审核时间*/
    private Date checkTime;

    /**  开奖状态(0:待开奖、1:中奖、2:未中奖，默认为”待开奖”）*/
    private Integer lotteryStatus;

    private String lotteryStatusStr;

    /**  开奖时间*/
    private Date lotteryTime;

    private String lotteryTimeStr;

    private String lotteryTimeString;

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

    private String count;

    private String winningAmount;

    private String lotteryType;

    private String lotteryTypeName;

    private String cashCoupon;
    
    /**
     * 代金券名称
     */
    private String couponName;

    private String useDateRange;

    /**
     * 开奖开始时间
     */
    private String lotteryStartTime;
    
    /**
     * 开奖结束时间
     */
    private String lotteryEndTime;
    
    
    public Integer getActivityAmount() {
        return activityAmount;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getBizType() {
        return bizType;
    }

    public String getCashCoupon() {
        return cashCoupon;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public String getChargeMobile() {
        return chargeMobile;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }




    public Date getCheckTime() {
        return checkTime;
    }

    public String getCount() {
        return count;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        if(createTime != null)
            return DateUtil.date2String(createTime,FORMAT_STRING);
        
        return "";
    }
    public String getCreateTimeString() {
        return createTimeString;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }
    public Integer getId() {
        return id;
    }

    public String getLotteryEndTime() {
        return lotteryEndTime;
    }
    public String getLotteryman() {
        return lotteryman;
    }

    public String getLotteryStartTime() {
        return lotteryStartTime;
    }
    public Integer getLotteryStatus() {
        return lotteryStatus;
    }

    public String getLotteryStatusStr() {
        return lotteryStatusStr;
    }
    public Date getLotteryTime() {
        return lotteryTime;
    }

    public String getLotteryTimeStr() {
        if(lotteryTime != null){
            return DateUtil.date2String(lotteryTime,FORMAT_STRING);
        }
        return "";
    }

    public String getLotteryTimeString() {
        return lotteryTimeString;
    }

    public String getLotteryType() {
        return lotteryType;
    }
    public String getLotteryTypeName() {
        return lotteryTypeName;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public String getStartCreateTime() {
        return startCreateTime;
    }
    public Integer getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }
    public String getWinningAmount() {
        return winningAmount;
    }

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

    public void setCashCoupon(String cashCoupon) {
        this.cashCoupon = cashCoupon;
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

    public void setCount(String count) {
        this.count = count;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public void setId(Integer id) {
        this.id =id;
    }

    public void setLotteryEndTime(String lotteryEndTime) {
        this.lotteryEndTime = lotteryEndTime;
    }

    public void setLotteryman(String lotteryman) {
        this.lotteryman =lotteryman;
    }

    public void setLotteryStartTime(String lotteryStartTime) {
        this.lotteryStartTime = lotteryStartTime;
    }

    public void setLotteryStatus(Integer lotteryStatus) {
        this.lotteryStatus =lotteryStatus;
    }

    public void setLotteryStatusStr(String lotteryStatusStr) {
        this.lotteryStatusStr = lotteryStatusStr;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime =lotteryTime;
    }

    public void setLotteryTimeStr(String lotteryTimeStr) {
        this.lotteryTimeStr = lotteryTimeStr;
    }

    public void setLotteryTimeString(String lotteryTimeString) {
        this.lotteryTimeString = lotteryTimeString;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public void setLotteryTypeName(String lotteryTypeName) {
        this.lotteryTypeName = lotteryTypeName;
    }


    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber =luckyNumber;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode =orderCode;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public void setStoreId(Integer storeId) {
        this.storeId =storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName =storeName;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getUseDateRange() {
        return useDateRange;
    }

    public void setUseDateRange(String useDateRange) {
        this.useDateRange = useDateRange;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
}
