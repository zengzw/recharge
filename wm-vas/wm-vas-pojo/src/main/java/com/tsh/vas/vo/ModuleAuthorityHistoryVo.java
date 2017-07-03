package com.tsh.vas.vo;

import java.io.Serializable;
import java.util.Date;


public class ModuleAuthorityHistoryVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7213821149427450927L;

    private String startTimeStr;

    private String endTimeStr;

    private String showEndTimeStr;
    
    private String createTimeStr;

    private Date startTime;

    private Date endTime;

    private Date showEndTime;
    
    private Integer areaId;

    private String areaName;
    
    private Date createTime;
    
    public Integer getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
       return createTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public Date getShowEndTime() {
        return showEndTime;
    }

    public String getShowEndTimeStr() {
        return showEndTimeStr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public void setShowEndTime(Date showEndTime) {
        this.showEndTime = showEndTime;
    }

    public void setShowEndTimeStr(String showEndTimeStr) {
        this.showEndTimeStr = showEndTimeStr;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
}
