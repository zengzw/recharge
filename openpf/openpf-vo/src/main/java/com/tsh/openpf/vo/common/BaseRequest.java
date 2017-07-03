package com.tsh.openpf.vo.common;

/**
 * RequestBase
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class BaseRequest {

    private String  businessId;  //供应商编号
    private String  timestamp;   //时间戳
    private String  signed;      //签名

    private String messageId;    //请求消息ID
    private String version;      //接口版本号

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
