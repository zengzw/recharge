package com.tsh.vas.vo;

/**
 * Created by Administrator on 2017/4/14 014.
 */
public class ChangeOrderInfo {

    private String orderType;
    private String orderTypeName;
    private String orderNo;
    private String payBalance;
    private String orderStatus;
    private String orderStatusName;
    private String refundStatus;
    private String refundStatusName;
    private String createTimeStr;

    private String supplierOrderStatus;
    private String supplierOrderStatusName;

    private String changeExists = "0";

    private String refundExists = "0";

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(String payBalance) {
        this.payBalance = payBalance;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusName() {
        return refundStatusName;
    }

    public void setRefundStatusName(String refundStatusName) {
        this.refundStatusName = refundStatusName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getChangeExists() {
        return changeExists;
    }

    public void setChangeExists(String changeExists) {
        this.changeExists = changeExists;
    }

    public String getRefundExists() {
        return refundExists;
    }

    public void setRefundExists(String refundExists) {
        this.refundExists = refundExists;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getSupplierOrderStatus() {
        return supplierOrderStatus;
    }

    public void setSupplierOrderStatus(String supplierOrderStatus) {
        this.supplierOrderStatus = supplierOrderStatus;
    }

    public String getSupplierOrderStatusName() {
        return supplierOrderStatusName;
    }

    public void setSupplierOrderStatusName(String supplierOrderStatusName) {
        this.supplierOrderStatusName = supplierOrderStatusName;
    }
}
