package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.util.Date;


public class PhoneOrderTransformHistoryVo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -5196440148532713053L;
    
    /**  订单支付ID*/
    private Long id;
    
    /**  支付类型(1：订单支付，2：退票支付)*/
    private String phoneOrderCode;
    

    /**  操作人Id*/
    private Integer createId;
    

    /**  操作人*/
    private String creater;
    

    /**  实付金额*/
    private Date updateTime;
    

    /**  调整前状态*/
    private Integer beforeStatus;
    

    /**  创建时间*/
    private Integer afterStatus;
    

    /**  支付流水*/
    private String remark;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public String getPhoneOrderCode() {
        return phoneOrderCode;
    }

    public void setPhoneOrderCode(String phoneOrderCode) {
        this.phoneOrderCode =phoneOrderCode;
    }
    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId =createId;
    }
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater =creater;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =updateTime;
    }
    public Integer getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(Integer beforeStatus) {
        this.beforeStatus =beforeStatus;
    }
    public Integer getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(Integer afterStatus) {
        this.afterStatus =afterStatus;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
