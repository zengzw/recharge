package com.tsh.vas.vo.trainticket;

/**
 * Created by Administrator on 2016/12/8 008.
 */
public class SuccessOrderInfoVo {

    private String hcpOrderCode;// 订单编号

    private String username;//  乘客姓名

    private String mobile;// 支付手机号

    private String servicePrice;//	服务费用
    private String costingAmount;//	成本价
    private String realAmount;//	实付金额
    private String createTime;// 订单创建时间
    
    private Integer payStatus;// 0:失败，1：成功


    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode = hcpOrderCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getCostingAmount() {
        return costingAmount;
    }

    public void setCostingAmount(String costingAmount) {
        this.costingAmount = costingAmount;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
