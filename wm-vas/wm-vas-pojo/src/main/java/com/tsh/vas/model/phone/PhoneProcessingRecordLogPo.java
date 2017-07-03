package com.tsh.vas.model.phone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "phone_processing_record_log")
public class PhoneProcessingRecordLogPo implements Serializable{


    /**
     * 
     */
    private static final Long serialVersionUID = 5416430773244288448L;
    
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
    private String  logcontent;
    
    /**  创建时间*/
    private Long createtime;
    
    /**  消耗时间*/
    private Integer cost;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    @Column(name = "msgId")
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid =msgid;
    }
    @Column(name = "supplierType")
    public String getSuppliertype() {
        return suppliertype;
    }

    public void setSuppliertype(String suppliertype) {
        this.suppliertype =suppliertype;
    }
    @Column(name = "http_type")
    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType =httpType;
    }
    @Column(name = "methodName")
    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname =methodname;
    }
    @Column(name = "logType")
    public Integer getLogtype() {
        return logtype;
    }

    public void setLogtype(Integer logtype) {
        this.logtype =logtype;
    }
    @Column(name = "logContent")
    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent =logcontent;
    }
    @Column(name = "createTime")
    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime =createtime;
    }
    @Column(name = "cost")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost =cost;
    }
}
