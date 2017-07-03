package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "supplier_business")
public class SupplierBusiness implements Serializable {

    /**
     * 供应商服务业务
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 供应商编号
     */
    @Basic
    @Column(name = "supplier_code")
    private String supplierCode = "";
    /**
     * 服务业务编码
     */
    @Basic
    @Column(name = "business_code")
    private String businessCode = "";
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 利润分成方式 1.不可分成 2.全额分成 ，3.差额分成
     */
    @Basic
    @Column(name = "share_way")
    private Integer shareWay = 0;
    /**
     * 总利润分成百分比率
     */
    @Basic
    @Column(name = "total_share_ratio")
    private Double totalShareRatio = 0.00;
    /**
     * 平台分成百分比率
     */
    @Basic
    @Column(name = "platform_share_ratio")
    private Double platformShareRatio = 0.00;
    /**
     * 县域分成百分比率
     */
    @Basic
    @Column(name = "area_share_ratio")
    private Double areaShareRatio = 0.00;

    /**
     * 转换相应的日期格式
     *
     * @return
     */
    public String getCreateTimeStr() {
        return DateUtil.date2String (createTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShareWay() {
        return shareWay;
    }

    public void setShareWay(Integer shareWay) {
        this.shareWay = shareWay;
    }

    public Double getTotalShareRatio() {
        return totalShareRatio;
    }

    public void setTotalShareRatio(Double totalShareRatio) {
        this.totalShareRatio = totalShareRatio;
    }

    public Double getPlatformShareRatio() {
        return platformShareRatio;
    }

    public void setPlatformShareRatio(Double platformShareRatio) {
        this.platformShareRatio = platformShareRatio;
    }

    public Double getAreaShareRatio() {
        return areaShareRatio;
    }

    public void setAreaShareRatio(Double areaShareRatio) {
        this.areaShareRatio = areaShareRatio;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
