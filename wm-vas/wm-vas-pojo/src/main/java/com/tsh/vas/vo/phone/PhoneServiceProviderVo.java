package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.util.Date;


public class PhoneServiceProviderVo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -8668207333566938756L;
    
    
    /**  订单支付ID*/
    private Long id;
    
    
    /**  支付类型(1：订单支付，2：退票支付)*/
    private String providerCode;

    
    /**  火车票订单编号*/
    private String providerName;
    

    /**  创建时间*/
    private Date createTime;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode =providerCode;
    }
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName =providerName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
}
