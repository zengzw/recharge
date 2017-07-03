/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.vo;

/**
 * 订单列表查询请求参数
 * 
 * @author zengzw
 * @date 2017年4月20日
 */
public class ReqQueryActivityOrderListParams {
    
    private Integer rows;//每页数量

    private Integer page;//当前页数

    private String type;//-1:全部；1：当前网点

    private String searchInfo;//查询信息。重置手机号码，订单号，联系人

    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

   
    public String getType() {
        return type;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public void setType(String type) {
        this.type = type;
    }
}
