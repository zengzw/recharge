package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "business_info")
public class BusinessInfo implements Serializable {

    /**
     * 服务业务信息id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 服务业务编号
     */
    @Basic
    @Column(name = "business_code")
    private String businessCode = "";
    /**
     * 服务业务名称
     */
    @Basic
    @Column(name = "business_name")
    private String businessName = "";
    /**
     * 父级业务编号
     */
    @Basic
    @Column(name = "parent_code")
    private String parentCode = "";
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 服务状态；1：可服务，2：停止服务
     */
    @Basic
    @Column(name = "business_status")
    private Integer businessStatus = 0;
    /**
     * 分成规则：1.全额，2差额
     */
    @Basic
    @Column(name = "divide_rule")
    private Integer divideRule = 0;

    /**
     * 返回相应的时间格式
     *
     * @return
     */
    public String getCreateTimeStr() {
        return DateUtil.date2String (createTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Integer getDivideRule() {
        return divideRule;
    }

    public void setDivideRule(Integer divideRule) {
        this.divideRule = divideRule;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
