package com.tsh.vas.model.phone;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "phone_value")
public class PhoneValuePo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -4716286779654187821L;


    /**  订单支付ID*/
    private Long id;
    
    /**  面额*/
    private Integer value;
    
    /**  创建时间*/
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    @Column(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value =value;
    }
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
}
