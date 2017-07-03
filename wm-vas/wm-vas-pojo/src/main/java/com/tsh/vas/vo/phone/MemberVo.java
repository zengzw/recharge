package com.tsh.vas.vo.phone;

import java.util.Date;
/**
 * 优惠券会员PO
 */
public class MemberVo {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userID;
	/**
	 * 
	 */
	private Long storeID;
	
	/**
	 * 县域
	 */
	private Long areaID;
	
	private Integer sex;
	
	/**
	 * 会员姓名.
	 */
	private String memberName;
	/**
	@Column(name="sex")
	private Long sex;

	/**
	 * 会员手机号.
	 */
	private String mobileNumber;
	
	/**
	 * 电话.
	 */
	private String telphone;
	
	/**
	 * 
	 */
	private Date birthday;
	/**
	 * 
	 */
	private String idCard;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String qq;
	/**
	 * 
	 */
	private String province;
	/**
	 * 
	 */
	private String city;
	/**
	 * 
	 */
	private String area;
	/**
	 * 
	 */
	private String towns;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Integer integral;
	/**
	 * 
	 */
	private Integer growth;
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
	 * 	
	 */
	private String postalCode;
	/**
	 * 微信公众号用户openId
	 */
	private String openId;
	/**
	 * 会员登录密码.
	 */
	private String loginPassword;
	/**	
	 * 
	 */
	private String inviterMobileNumber;
	/**
	 * 
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

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getGrowth() {
		return growth;
	}

	public void setGrowth(Integer growth) {
		this.growth = growth;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getInviterMobileNumber() {
		return inviterMobileNumber;
	}

	public void setInviterMobileNumber(String inviterMobileNumber) {
		this.inviterMobileNumber = inviterMobileNumber;
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
}
