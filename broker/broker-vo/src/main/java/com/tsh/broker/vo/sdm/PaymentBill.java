package com.tsh.broker.vo.sdm;

import java.math.BigDecimal;

/**
 * 支付账单信息 PaymentBill
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class PaymentBill {

    private String unitId;        //缴费单位编号
    private String account;        //账户编号
    private String yearmonth;    //账单月份
    private String username;    //用户真实姓名
    private BigDecimal bills;       //账单金额(欠单金额)
    private BigDecimal balance;     //账户余额
    private Integer payModel;       //	缴费模式	1.预付 2.后付,如果没有通过查询账单判断
    private String extContent;      //充值扩展字段，Map<String,String> 对象json字符串格式

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getBills() {
        return bills;
    }

    public void setBills(BigDecimal bills) {
        this.bills = bills;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }

}
