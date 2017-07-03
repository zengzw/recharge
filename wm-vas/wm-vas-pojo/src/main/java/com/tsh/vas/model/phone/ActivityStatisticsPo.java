package com.tsh.vas.model.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 活动统计实体
 *
 * @author zengzw
 * @date 2017年5月10日
 */
@Entity
public class ActivityStatisticsPo implements Serializable{


    /**
     * 网点下单数
     */
    private Integer areaCount;
    
    /**  下单县域名称*/
    private String areaName;

    private int id;


    /**
     * 县域下单数
     */
    private Integer storeCount;


    /**  下单网点名称*/
    private String storeName;


    @Column(name="area_count")
    public Integer getAreaCount() {
        return areaCount;
    }


    @Column(name="area_name")
    public String getAreaName() {
        return areaName;
    }


    @Id
    public int getId() {
        return id;
    }

    @Column(name="store_count")
    public Integer getStoreCount() {
        return storeCount;
    }


    @Column(name="store_name")
    public String getStoreName() {
        return storeName;
    }

    public void setAreaCount(Integer areaCount) {
        this.areaCount = areaCount;
    }


    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }


    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


}
