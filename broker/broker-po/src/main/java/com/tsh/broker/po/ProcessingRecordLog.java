package com.tsh.broker.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ProcessingRecordLog
 *
 * @author dengjd
 * @date 2016/10/19
 */
@Entity
@Table(name = "processing_record_log")
public class ProcessingRecordLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column(name = "msgId")
    private  String msgId;			 //消息ID,调用唯一性标准，UUID值
    @Column(name = "logSubject")
    private  String logSubject;	    //日志主体对象，主体包括记录的日志方法等
    @Column(name = "logType")
    private  Integer logType;		 //日志类型：1.http日志 2.dubbo日志 3.mq日志,4.应用日志
    @Column(name = "logContent")
    private  String logContent;     //日志内容
    @Column(name = "createTime")
    private  Long createTime;		 //创建时间


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getLogSubject() {
        return logSubject;
    }

    public void setLogSubject(String logSubject) {
        this.logSubject = logSubject;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
