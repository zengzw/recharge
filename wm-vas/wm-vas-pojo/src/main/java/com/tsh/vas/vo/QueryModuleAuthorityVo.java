package com.tsh.vas.vo;

import java.io.Serializable;
import java.util.Date;

import com.dtds.platform.commons.utility.DateUtil;


public class QueryModuleAuthorityVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7213821149427450927L;
    /**
     * 活动状态。true:开启，false，关闭
     */
    private boolean activityStatus;
    /**  县域ID*/
    private Integer areaId;

    /**
     * 县域名称
     */
    private String  areaName;

    /**
     * 活动开始时间
     */
    private Date beginTime;

    /**  业务模块编码*/
    private String businessCode;
    /**
     * 活动查看结束时间
     */
    private Date checkEndTime;
    
    /**
     * 活动结束时间
     */
    private Date endTime;


    /**  */
    private Integer id;

    /**  项目编码*/
    private String projectCode;

    /**
     * 查询状态
     */
    private  boolean queryStatus;


    /**
     * 描述
     */
    private  String remark;

    /**
     * 活动状态 -1:全部，0：未开始，1：进行中，2：已结束
     */
    private Integer selectStatus;


    /**
     * 开启状态 启用状态。ON:开启，OFF：关闭
     */
    private String  status;

    public Integer getAreaId() {
        return areaId;
    }


    public String getAreaName() {
        return areaName;
    }

    public Date getBeginTime() {
        return beginTime;
    }
    
    public String getBeginTimeStr(){
        if(this.beginTime != null){
            return DateUtil.date2String(beginTime);
        }
        
        return "";
    }
    
    
    public String getBusinessCode() {
        return businessCode;
    }


    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public String getCheckEndTimeStr(){
        if(checkEndTime != null){
            return DateUtil.date2String(checkEndTime);
        }
        
        return "";
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimeStr(){
        if(this.endTime != null){
            return DateUtil.date2String(endTime);
        }
        
        return "";
    }

    public Integer getId() {
        return id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getRemark() {
        return remark;
    }

 

    public Integer getSelectStatus() {
        return selectStatus;
    }

 

    public String getStatus() {
        return status;
    }


    public boolean isActivityStatus() {
        return activityStatus;
    }


    public boolean isQueryStatus() {
        return queryStatus;
    }

    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public void setAreaId(Integer areaId) {
        this.areaId =areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }
    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public void setId(Integer id) {
        this.id =id;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode =projectCode;
    }

    public void setQueryStatus(boolean queryStatus) {
        this.queryStatus = queryStatus;
    }
    
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSelectStatus(Integer selectStatus) {
        this.selectStatus = selectStatus;
    }
    
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
