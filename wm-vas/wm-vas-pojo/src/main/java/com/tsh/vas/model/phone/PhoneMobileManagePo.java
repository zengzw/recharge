package com.tsh.vas.model.phone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/2 002.
 */
@Entity
@Table(name = "phone_mobile_manage")
public class PhoneMobileManagePo implements Serializable {

    private Long id;
    private String mobileShort;//手机号前3位,如138、139'
    private String mobileNum;//号段前7位,如1300000',
    private String mobileProvince;//'归属省,如"广东"',
    private String mobileCity;//'归属市,如"深圳"',
    private String mobileSupplier;//'归属运营商,如"中国联通"',
    private Integer citycode;//'区号,如"0755"',
    private Integer postcode;//'邮编号,如"53001840"',
    private Date createtime;//'创建时间',
    private String createby;//'创建人',

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "mobile_short")
    public String getMobileShort() {
        return mobileShort;
    }

    public void setMobileShort(String mobileShort) {
        this.mobileShort = mobileShort;
    }

    @Column(name = "mobile_num")
    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    @Column(name = "mobile_province")
    public String getMobileProvince() {
        return mobileProvince;
    }

    public void setMobileProvince(String mobileProvince) {
        this.mobileProvince = mobileProvince;
    }

    @Column(name = "mobile_city")
    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    @Column(name = "mobile_supplier")
    public String getMobileSupplier() {
        return mobileSupplier;
    }

    public void setMobileSupplier(String mobileSupplier) {
        this.mobileSupplier = mobileSupplier;
    }

    @Column(name = "citycode")
    public Integer getCitycode() {
        return citycode;
    }

    public void setCitycode(Integer citycode) {
        this.citycode = citycode;
    }

    @Column(name = "postcode")
    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "createby")
    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }
}
