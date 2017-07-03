package com.tsh.vas.trainticket.vo.req;

/**
 * Created by Administrator on 2016/12/2 002.
 */
public class QueryOrderParam {
    private String status;//订单状态
    
    private String pageSize;//每页数量
    
    private String pageNo;//当前页数
    
    private String type;//1：等待结果，2：已成功，3：已失败，4：已退票
    
    private String searchInfo;//查询信息

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
}
