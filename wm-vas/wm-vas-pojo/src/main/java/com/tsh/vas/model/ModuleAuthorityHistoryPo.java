package com.tsh.vas.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "module_authority_history")
public class ModuleAuthorityHistoryPo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8602208634925767188L;

    /**  */
    private Integer id;
    
    private Date startTime;

    private Date endTime;

    private Date showEndTime;

    private Integer areaId;

    private String areaName;
    
    
    /**
     * 创建时间
     */
    private  Date createTime;

    @Column(name = "area_id")
    public Integer getAreaId() {
        return areaId;
    }

    @Column(name = "area_name")
    public String getAreaName() {
        return areaName;
    }

    @Column(name="create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "show_end_time")
    public Date getShowEndTime() {
        return showEndTime;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
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

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShowEndTime(Date showEndTime) {
        this.showEndTime = showEndTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
