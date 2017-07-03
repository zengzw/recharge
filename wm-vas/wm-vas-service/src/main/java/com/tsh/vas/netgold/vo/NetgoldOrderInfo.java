package com.tsh.vas.netgold.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/10 010.
 */
public class NetgoldOrderInfo implements Serializable{
    // 订单ID
    private Long orderId;
    // 订单编号
    private String bizOrderNo;
    // 加减标识：1,扣减金额-订单支付 ; 2，增加金额-订单退款.
    private Integer addTag = 1;
    // 待支付/待退回总额（不包括优惠金额,单位是分）.
    private BigDecimal totalMoney;
    //请求操作用户Id.
    private Long operateUserId;
    // 账户使用优惠券信息.
    private String useCoupons;
    //关联原锁定金额的业务单编号.
    private String offsetBizOrderNo;

    private String orderPayActivitys;

    private String bizDetails;
    // 业务简述.
    private String bizIntro;
    // PC-业务详情穿透url.订单详情的url.
    private String bizPenetrationUrl ;
    //消息回调标识,不需要回调则为空.接收消息的主题
    private String msgTopic;
    // 所属账户的商业ID.支付账户
    private Long bizId;
    // 所属账户的商业类型.支付账户，网点，会员，找邓松;
    private Integer bizType;
    // 县域ID
    private String countryCode;
    // 县域名称
    private String countryName;
    // 订单支付状态
    private Integer payStatus;
    // 创建时间
    private String createTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public Integer getAddTag() {
        return addTag;
    }

    public void setAddTag(Integer addTag) {
        this.addTag = addTag;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getUseCoupons() {
        return useCoupons;
    }

    public void setUseCoupons(String useCoupons) {
        this.useCoupons = useCoupons;
    }

    public String getOffsetBizOrderNo() {
        return offsetBizOrderNo;
    }

    public void setOffsetBizOrderNo(String offsetBizOrderNo) {
        this.offsetBizOrderNo = offsetBizOrderNo;
    }

    public String getOrderPayActivitys() {
        return orderPayActivitys;
    }

    public void setOrderPayActivitys(String orderPayActivitys) {
        this.orderPayActivitys = orderPayActivitys;
    }

    public String getBizDetails() {
        return bizDetails;
    }

    public void setBizDetails(String bizDetails) {
        this.bizDetails = bizDetails;
    }

    public String getBizIntro() {
        return bizIntro;
    }

    public void setBizIntro(String bizIntro) {
        this.bizIntro = bizIntro;
    }

    public String getBizPenetrationUrl() {
        return bizPenetrationUrl;
    }

    public void setBizPenetrationUrl(String bizPenetrationUrl) {
        this.bizPenetrationUrl = bizPenetrationUrl;
    }

    public String getMsgTopic() {
        return msgTopic;
    }

    public void setMsgTopic(String msgTopic) {
        this.msgTopic = msgTopic;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
