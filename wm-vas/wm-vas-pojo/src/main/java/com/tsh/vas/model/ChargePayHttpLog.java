package com.tsh.vas.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/10/19.
 */
@Entity
@Table(name = "charge_pay_http_log")
public class ChargePayHttpLog implements Serializable {

    /**
     * 请求日志记录，主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    /**
     * 日志编号
     */
    @Basic
    @Column(name = "log_code")
    private String logCode;
    /**
     * 业务编号
     */
    @Basic
    @Column(name = "charge_code")
    private String chargeCode;
    /**
     * 发送请求时间
     */
    @Basic
    @Column(name = "send_time")
    private Date sendTime;
    /**
     * 收到消息时间
     */
    @Basic
    @Column(name = "receive_time")
    private Date receiveTime;
    /**
     * 发送的本地方法
     */
    @Basic
    @Column(name = "send_mothed")
    private String sendMothed;
    /**
     * 请求对像的方法，如资金服务中心
     */
    @Basic
    @Column(name = "receive_mothed")
    private String receiveMothed;
    /**
     * 业务类型描述，如下单、退款调用
     */
    @Basic
    @Column(name = "remark")
    private String remark;
    /**
     * 发送内容
     */
    @Basic
    @Column(name = "send_data")
    private String sendData;
    /**
     * 收到消息内容
     */
    @Basic
    @Column(name = "receive_data")
    private String receiveData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getSendMothed() {
        return sendMothed;
    }

    public void setSendMothed(String sendMothed) {
        this.sendMothed = sendMothed;
    }

    public String getReceiveMothed() {
        return receiveMothed;
    }

    public void setReceiveMothed(String receiveMothed) {
        this.receiveMothed = receiveMothed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getReceiveData() {
        return receiveData;
    }

    public void setReceiveData(String receiveData) {
        this.receiveData = receiveData;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
