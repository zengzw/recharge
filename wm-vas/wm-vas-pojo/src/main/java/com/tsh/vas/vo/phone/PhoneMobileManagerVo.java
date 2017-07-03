package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/2 002.
 */
public class PhoneMobileManagerVo implements Serializable{
    private Long id;
    private String mobileShort;//手机号前3位,如138、139'
    private String mobileNum;//号段前7位,如1300000',
    private String mobileProvince;//'归属省,如"广东"',
    private String mobileCity;//'归属市,如"深圳"',
    private String mobileSupplier;//'归属运营商,如"中国联通"',
    private Integer citycode;//'区号,如"0755"',
    private Integer postcode;//'邮编号,如"53001840"',
    private Date createtime;//'创建时间',
    private String createTimeStr;
    private String createby;//'创建人',
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileShort() {
        return mobileShort;
    }

    public void setMobileShort(String mobileShort) {
        this.mobileShort = mobileShort;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getMobileProvince() {
        return mobileProvince;
    }

    public void setMobileProvince(String mobileProvince) {
        this.mobileProvince = mobileProvince;
    }

    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    public String getMobileSupplier() {
        return mobileSupplier;
    }

    public void setMobileSupplier(String mobileSupplier) {
        this.mobileSupplier = mobileSupplier;
    }

    public Integer getCitycode() {
        return citycode;
    }

    public void setCitycode(Integer citycode) {
        this.citycode = citycode;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
