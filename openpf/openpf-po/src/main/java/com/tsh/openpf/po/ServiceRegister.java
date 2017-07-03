package com.tsh.openpf.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ServiceRegister
 *
 * @author dengjd
 * @date 2016/10/11
 */
@Entity
@Table(name = "service_register")
public class ServiceRegister  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "business_id")
    private String businessId;    //供应商编号
    
    @Column(name = "service_addr")
    private String serviceAddr;    //访问服务地址
    
    @Column(name = "sign_key")
    private String signKey;    //签名秘钥
    
    @Column(name = "remark")
    private String remark;    //备注信息
    
    @Column(name = "create_time")
    private Long createTime;    //创建时间
    
    @Column(name = "update_time")
    private Long updateTime;    //更新时间
    
    
    @Column(name="business_code")
    private String businessCode; //业务编码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

}
