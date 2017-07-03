package com.tsh.broker.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BaseConfig
 *
 * @author dengjd
 * @date 2016/10/17
 */
public class BaseConfig {
    //用户名
    private String userName;
    //签名秘钥
    private String signKey;
    //供应商编号
    private String businessNo;
    //供应商秘钥
    private String businessKey;
    //充值结果通知请求地址,调用内部服务地址
    private String resultNotifyUrl;

    //供应商编号
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getResultNotifyUrl() {
        return resultNotifyUrl;
    }

    public void setResultNotifyUrl(String resultNotifyUrl) {
        this.resultNotifyUrl = resultNotifyUrl;
    }
    
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
