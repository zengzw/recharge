package com.tsh.vas.vo.supplier;

import com.tsh.vas.model.SupplierInfo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/29.
 */
public class SupplierInfoVo extends SupplierInfo {

    /**
     * 业务名称类表字符串
     */
    private String businesseNamesStr;
    /**
     * 业务列表
     */
    private List<String> businessCodes;

    public String getBusinesseNamesStr() {
        return businesseNamesStr;
    }

    public void setBusinesseNamesStr(String businesseNamesStr) {
        this.businesseNamesStr = businesseNamesStr;
    }

    public List<String> getBusinessCodes() {
        return businessCodes;
    }

    public void setBusinessCodes(List<String> businessCodes) {
        this.businessCodes = businessCodes;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
