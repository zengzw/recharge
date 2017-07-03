package com.tsh.openpf.vo;

import java.io.Serializable;

/**
 * ServiceRegisterVo
 *
 * @author dengjd
 * @date 2016/10/11
 */
public class ServiceRegisterVo implements Serializable {
    private Long id;
    
    private String businessId;    //供应商编号
    
    private String serviceAddr;    //访问服务地址
    
    private String signKey;    //签名秘钥
    
    private String remark;    //备注信息
    
    private Long createTime;    //创建时间
    
    private Long updateTime;    //更新时间
    
    
    private String businessCode; //业务编码

    public String getBusinessCode() {
        return businessCode;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Long getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public String getSignKey() {
        return signKey;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

}
