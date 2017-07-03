package com.tsh.openpf.vo.request;

import com.tsh.openpf.vo.common.BaseRequest;

/**
 * CreateBizzServiceRequest
 *
 * @author dengjd
 * @date 2016/10/11
 */
public class BizzServiceRequest extends BaseRequest {
   
    /**
     * 业务编码
     */
    private String businessCode;

   
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    
    
}
