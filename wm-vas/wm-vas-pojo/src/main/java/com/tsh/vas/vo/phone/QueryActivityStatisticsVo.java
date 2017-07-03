/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo.phone;


/**
 *
 * @author zengzw
 * @date 2017年5月10日
 */
public class QueryActivityStatisticsVo {
    
    private Integer areaId;
    
    private String beginTime;
    
    private String endTime;
    
    private Integer page;
    
    private Integer rows;

    private Integer storeId;

    public Integer getAreaId() {
        return areaId;
    }

   
    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }


    public String getBeginTime() {
        return beginTime;
    }


    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    
    
}
