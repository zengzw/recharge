package com.tsh.vas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "module_authority")
public class ModuleAuthorityPo implements Serializable{


    /**  */
    private Integer id;
    
    /**  项目编码*/
    private String projectCode;
    
    /**  业务模块编码*/
    private String businessCode;
    
    /**  县域ID*/
    private Integer areaId;

    /**
     * 县域名称
     */
    private String  areaName;

    /**
     * 启用状态。ON:开启，OFF：关闭
     */
    private String status;


    /**
     * 活动开始时间
     */
    private Date beginTime;



    /**
     * 活动结束时间
     */
    private Date endTime;


    /**
     * 活动查看结束时间
     */
    private Date checkEndTime;


    /**
     * 描述
     */
    private  String remark;


    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="area_name")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name="begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name="end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name="check_end_time")
    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    @Column(name = "area_id")
    public Integer getAreaId() {
        return areaId;
    }

    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "project_code")
    public String getProjectCode() {
        return projectCode;
    }
    @Column(name="status")
    public String getStatus() {
        return status;
    }

    public void setAreaId(Integer areaId) {
        this.areaId =areaId;
    }
    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode =projectCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
