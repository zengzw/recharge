package com.tsh.traintickets.po;


import javax.persistence.*;

import com.traintickets.common.BaseSerializable;

import java.io.Serializable;

/**
 * TicketsTest
 *
 * @author zhoujc
 */
@Entity
@Table(name = "hcp_processing_record_log")
public class HcpProcessingRecordLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private  Long id;

	@Column(name = "msgId")
	private  String msgId;			 	//消息ID,调用唯一性标准，UUID值

	@Column(name = "supplierType")
	private  String supplierType;	    //供应商类型 1：酷游

	@Column(name = "http_type")
	private String httpType;			// request,response

	@Column(name = "methodName")
	private  String methodName;			// 调用方法名

	@Column(name = "logType")
	private  Integer logType;		 	//日志类型：1.http日志 2.dubbo日志 3.mq日志,4.应用日志

	@Column(name = "logContent")
	private  String logContent;     	//日志内容

	@Column(name = "createTime")
	private  Long createTime;		 	//创建时间

	@Column(name = "cost")
	private Long cost;					// 消耗时间

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

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getHttpType() {
		return httpType;
	}

	public void setHttpType(String httpType) {
		this.httpType = httpType;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}
}
