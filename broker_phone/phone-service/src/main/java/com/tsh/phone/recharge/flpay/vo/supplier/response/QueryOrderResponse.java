package com.tsh.phone.recharge.flpay.vo.supplier.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/12 012.
 */
public class QueryOrderResponse implements Serializable{

    private String OrderId;
    private String ProductId;
    private Integer BuyNum;
    private String Account;// 手机号
    private String Area;
    private String Server;
    private String Type;
    private String CreateTime;
    private String Status;
    private String FinishTime;
    private String StatusMsg;
    private String PurchasePrice;
    private String MessageCode;
    private String MessageInfo;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public Integer getBuyNum() {
        return BuyNum;
    }

    public void setBuyNum(Integer buyNum) {
        BuyNum = buyNum;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        FinishTime = finishTime;
    }

    public String getStatusMsg() {
        return StatusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        StatusMsg = statusMsg;
    }

    public String getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public String getMessageCode() {
        return MessageCode;
    }

    public void setMessageCode(String messageCode) {
        MessageCode = messageCode;
    }

    public String getMessageInfo() {
        return MessageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        MessageInfo = messageInfo;
    }
}
