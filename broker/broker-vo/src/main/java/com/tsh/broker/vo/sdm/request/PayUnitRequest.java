package com.tsh.broker.vo.sdm.request;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.tsh.broker.vo.common.BaseRequest;

/**
 * PayUnitQuery
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class PayUnitRequest extends BaseRequest {

    private Integer payType;  //缴费类型 1．水费 2.电费 3.煤气
    private String province;  //省份/直辖市
    private String city;      //城市/区
    private String county;    //县/区

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
