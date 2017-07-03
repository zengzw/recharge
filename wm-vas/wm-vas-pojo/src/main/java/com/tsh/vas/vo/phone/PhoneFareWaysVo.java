package com.tsh.vas.vo.phone;

import java.io.Serializable;

public class PhoneFareWaysVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String company;
	
	//单位元
	private Integer faceValue;
	//销售价
	private String salePrice;
	//销售区域
	private String saleRegion;
	//发布状态
	private int publicStatus;
	//删除状态 1,已删除;2正常
	private int delStatus;
	/**
	 * 发布状态名称
	 */
	private String publicStatusName; 
	
	
	
	
	public String getPublicStatusName() {
		return publicStatusName;
	}
	public void setPublicStatusName(String publicStatusName) {
		this.publicStatusName = publicStatusName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public Integer getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(Integer faceValue) {
		this.faceValue = faceValue;
	}
	
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getSaleRegion() {
		return saleRegion;
	}
	public void setSaleRegion(String saleRegion) {
		this.saleRegion = saleRegion;
	}
	public int getPublicStatus() {
		return publicStatus;
	}
	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}
	
}
