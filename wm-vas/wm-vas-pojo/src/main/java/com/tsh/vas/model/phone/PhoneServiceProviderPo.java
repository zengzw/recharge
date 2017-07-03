package com.tsh.vas.model.phone;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "phone_service_provider")
public class PhoneServiceProviderPo implements Serializable{
	
	
		/**
     * 
     */
    private static final Long serialVersionUID = 6798750386509154593L;
    
    
        /**  订单支付ID*/
	private Long id;
	
		/**  支付类型(1：订单支付，2：退票支付)*/
	private String providerCode;
	
		/**  火车票订单编号*/
	private String providerName;
	
	
		/**  创建时间*/
	private Date createTime;
		
			@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id =id;
	}
				@Column(name = "provider_code")
	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode =providerCode;
	}
				@Column(name = "provider_name")
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName =providerName;
	}
				@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =createTime;
	}
		}
