package com.tsh.phone.po;


import javax.persistence.*;


import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 话费日志记录
 * 
 * @author zengzw
 * @date 2017年2月20日
 */


@Entity
@Table(name = "phone_processing_record_log")
public class PhoneProcessingRecordLog implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 378742157352825970L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private  Long id;

	@Column(name = "msg_id")
	private  String msgId;			 	//消息ID,调用唯一性标准，UUID值

	@Column(name = "supplier_type")
	private  String supplierType;	    //供应商类型 1：酷游

	@Column(name = "http_type")
	private String httpType;			// request,response

	@Column(name = "method_name")
	private  String methodName;			// 调用方法名

	@Column(name = "log_type")
	private  Integer logType;		 	//日志类型：1.http日志 2.dubbo日志 3.mq日志,4.应用日志

	@Column(name = "request_content")
	private  String reqeustContent;     	//日志内容
	
	@Column(name = "response_content")
	private  String responseContent;     	//日志内容

    @Column(name = "create_time")
	private  Date createTime;		 	//创建时间

    @Column(name = "cost")
	private Long cost;					// 消耗时间

    public Long getCost() {
		return cost;
	}

    public Date getCreateTime() {
		return createTime;
	}

	public String getHttpType() {
		return httpType;
	}

	public Long getId() {
		return id;
	}

	
	public Integer getLogType() {
		return logType;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getReqeustContent() {
        return reqeustContent;
    }

	public String getResponseContent() {
        return responseContent;
    }

	public String getSupplierType() {
		return supplierType;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setHttpType(String httpType) {
		this.httpType = httpType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setReqeustContent(String reqeustContent) {
        this.reqeustContent = reqeustContent;
    }

	public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
}
