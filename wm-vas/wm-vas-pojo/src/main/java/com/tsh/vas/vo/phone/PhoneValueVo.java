package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.util.Date;


public class PhoneValueVo implements Serializable{
    /**
     * 
     */
    private static final Long serialVersionUID = -8792149590889807920L;


    /**  订单支付ID*/
    private Long id;
    
    /**  面额*/
    private Integer value;
    

    /**  创建时间*/
    private Date createTime;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value =value;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
}
