package com.tsh.vas.vo.charge;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author leejean <br>
 * @version 1.0.0 2016年2月23日下午5:27:03<br>
 * @see
 * @since JDK 1.7.0
 */
public class MemberVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3783973040545401038L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userID;
    /**
     * 网点ID
     */
    private Long storeID;
    /**
     * 县域
     */
    private Long areaID;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 会员姓名.
     */
    private String memberName;
    /**
     * 会员手机号.
     */
    private String mobileNumber;

    /**
     * 电话.
     */
    private String telphone;

    /**
     * 生日
     */
    private Date birthday;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 镇
     */
    private String towns;
    /**
     * 镇
     */
    private String address;
    /**
     * 1.正常，0，关闭
     */
    private Integer status;

    /**
     *
     */
    private Long createdUserID;
    /**
     *
     */
    private Date createdDate;
    /**
     *
     */
    private Long updatedUserID;
    /**
     *
     */
    private Date updatedDate;
    /**
     * 邮政编号
     */
    private String postalCode;
    /**
     * 会员头像
     */
    private String imgUrl;
    /**
     * 是否开卡  0 未  1开
     */
    private Integer vipCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getStoreID() {
        return storeID;
    }

    public void setStoreID(Long storeID) {
        this.storeID = storeID;
    }

    public Long getAreaID() {
        return areaID;
    }

    public void setAreaID(Long areaID) {
        this.areaID = areaID;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTowns() {
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = towns;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Long createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(Long updatedUserID) {
        this.updatedUserID = updatedUserID;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getVipCard() {
        return vipCard;
    }

    public void setVipCard(Integer vipCard) {
        this.vipCard = vipCard;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
