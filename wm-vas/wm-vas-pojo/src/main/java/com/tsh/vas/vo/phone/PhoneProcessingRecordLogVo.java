package com.tsh.vas.vo.phone;

import java.io.Serializable;


public class PhoneProcessingRecordLogVo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -161135028137966171L;
    
    /**  序列ID*/
    private Long id;
    /**  消息ID*/
    private String msgid;

    /**  供应商类型：kuyou:酷游 vas:增值服务*/
    private String suppliertype;

    /**  http请求类型（request,response）*/
    private String httpType;

    /**  调用方法名*/
    private String methodname;

    /**  日志类型：1.http日志 2.dubbo日志 3.mq日志,4.应用日志*/
    private Integer logtype;

    /**  请求内容*/
    private String logcontent;

    /**  创建时间*/
    private Long createtime;

    /**  消耗时间*/
    private Integer cost;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid =msgid;
    }
    public String getSuppliertype() {
        return suppliertype;
    }

    public void setSuppliertype(String suppliertype) {
        this.suppliertype =suppliertype;
    }
    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType =httpType;
    }
    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname =methodname;
    }
    public Integer getLogtype() {
        return logtype;
    }

    public void setLogtype(Integer logtype) {
        this.logtype =logtype;
    }
    public String  getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent =logcontent;
    }
    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime =createtime;
    }
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost =cost;
    }
}
