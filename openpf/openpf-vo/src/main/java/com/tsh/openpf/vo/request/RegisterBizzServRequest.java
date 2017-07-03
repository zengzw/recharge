package com.tsh.openpf.vo.request;

import com.tsh.openpf.vo.common.BaseRequest;

/**
 * RegisterBizzServRequest
 *
 * @author dengjd
 * @date 2016/10/11
 */
public class RegisterBizzServRequest  extends BaseRequest {

    private String serviceAddr;

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }
}
