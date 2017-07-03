package com.tsh.broker.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * SwPayUnit
 *
 * @author dengjd
 * @date 2016/9/30
 */
@Entity
@Table(name = "sw_pay_unit")
public class SwPayUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long  id;
    @Column(name = "payUnitId")
    private  String  payUnitId;	//缴费单位编号
    @Column(name = "province")
    private  String  province;  //省
    @Column(name = "city")
    private  String  city; //市
    @Column(name = "payUnitName")
    private  String  payUnitName;	//缴费单位名称
    @Column(name = "rechargeType")
    private  Integer  rechargeType; //充值类型 1.水费 2.电费 3.燃气费
    @Column(name = "unitStatus")
    private  Integer  unitStatus;	//单位状态: 1.正常 2.维护
    @Column(name = "statementType")
    private  Integer  statementType;	//结算类想 1.预付费 2.后付费

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayUnitId() {
        return payUnitId;
    }

    public void setPayUnitId(String payUnitId) {
        this.payUnitId = payUnitId;
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

    public String getPayUnitName() {
        return payUnitName;
    }

    public void setPayUnitName(String payUnitName) {
        this.payUnitName = payUnitName;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public Integer getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(Integer unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Integer getStatementType() {
        return statementType;
    }

    public void setStatementType(Integer statementType) {
        this.statementType = statementType;
    }

	@Override
	public String toString() {
		return "SwPayUnit [id=" + id + ", payUnitId=" + payUnitId
				+ ", province=" + province + ", city=" + city
				+ ", payUnitName=" + payUnitName + ", rechargeType="
				+ rechargeType + ", unitStatus=" + unitStatus
				+ ", statementType=" + statementType + "]";
	}
    
}
