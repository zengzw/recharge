package com.tsh.vas.vo.charge;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/9.
 */
public class ChargeSearchVo {

    /**
     * 订单状态：1：等待结果。2：已成功。3：已失败。4：已撤销
     */
    private Integer payStatus;
    /**
     * 搜索信息
     */
    private String searchInfo;
    /**
     * 网点编号
     */
    private String storeCode;


    /**
     * 缴费业务编号
     */
    private String businessCode;

    /**
     * 页码
     */
    private Integer page;
    /**
     * 页大小
     */
    private Integer rows;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
