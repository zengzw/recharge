package com.tsh.vas.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dtds.platform.commons.utility.DateUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsh.vas.utils.DateJsonDeserializer;
import com.tsh.vas.utils.DateJsonSerializer;


public class ModuleAuthorityVo implements Serializable{

    
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
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using=DateJsonSerializer.class)  
    @JsonDeserialize(using=DateJsonDeserializer.class)  
    private Date beginTime;
    

    /**  业务模块编码*/
    private String businessCode;

    /**
     * 活动查看结束时间
     */
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using=DateJsonSerializer.class)  
    @JsonDeserialize(using=DateJsonDeserializer.class)  
    private Date checkEndTime;

    /**
     * 活动结束时间
     */
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using=DateJsonSerializer.class)  
    @JsonDeserialize(using=DateJsonDeserializer.class)  
    private Date endTime;
    /**  */
    private Integer id;

    private List<ReceiveShopVo> lstReceiveShop;


    /**  项目编码*/
    private String projectCode;

    /**
     * 查询状态
     */
    private  boolean queryStatus;

    /**
     * 开通范围
     */
    private String receiveScore;


    /**
     * 描述
     */
    private  String remark;


    /**
     * 开启状态 启用状态。ON:开启，OFF：关闭
     */
    private String  status;


    /**
     * 当前状态
     */
    private String strActivityStatus;

    /**
     * 批量修改id ,隔开
     */
    private String updateIds;


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


    public List<ReceiveShopVo> getLstReceiveShop() {
        return lstReceiveShop;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getReceiveScore() {
        return receiveScore;
    }

    public String getRemark() {
        return remark;
    }

    public String getStatus() {
        return status;
    }

    public String getStrActivityStatus() {
        return strActivityStatus;
    }

    public String getUpdateIds() {
        return updateIds;
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

    public void setLstReceiveShop(List<ReceiveShopVo> lstReceiveShop) {
        this.lstReceiveShop = lstReceiveShop;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode =projectCode;
    }

    public void setQueryStatus(boolean queryStatus) {
        this.queryStatus = queryStatus;
    }

    public void setReceiveScore(String receiveScore) {
        this.receiveScore = receiveScore;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setStrActivityStatus(String strActivityStatus) {
        this.strActivityStatus = strActivityStatus;
    }


   

    public void setUpdateIds(String updateIds) {
        this.updateIds = updateIds;
    }
    
   
}
