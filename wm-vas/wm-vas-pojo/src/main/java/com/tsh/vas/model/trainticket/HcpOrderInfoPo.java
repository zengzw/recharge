package com.tsh.vas.model.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 订单PO
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Entity
@Table(name = "hcp_order_info")
public class HcpOrderInfoPo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8673064974132977202L;


    /**  火车票订单ID*/
    private Integer id;
    /**  火车票订单编号*/
    private String hcpOrderCode;
    /**  开发平台订单编号*/
    private String openPlatformNo;
    /**  供应商编号*/
    private String supplierCode;
    /**  供应商名称*/
    private String supplierName;
    /**  父级服务业务编号*/
    private String businessCode;
    /**  父级服务业务名称*/
    private String businessName;
    /**  子分类编号*/
    private String subBusinessCode;
    /**  子分类名称*/
    private String subBusinessName;
    /**  市*/
    private String city;
    /**  省*/
    private String province;
    /**  县*/
    private String country;
    /**  县运营中心编号ID*/
    private String countryCode;
    /**  县运营中心名称*/
    private String countryName;
    /**  支付会员用户电话*/
    private String mobile;
    /**  支付会员用户姓名*/
    private String payUserName;
    /**  支付会员用户id*/
    private Integer payUserId;
    /**  付费用户biz_id*/
    private Integer bizId;
    /**  付费用户账户角色类型（2：平台管理，3：县域，4：网点，5：供应商，9：会员）*/
    private Integer bizType;
    /**  网点编号*/
    private String storeCode;
    /**  网点名称*/
    private String storeName;
    /**  成本价*/
    private BigDecimal costingAmount;
    /**  应付金额*/
    private BigDecimal originalAmount;
    /**  实付金额*/
    private BigDecimal realAmount;
    /**  创建时间*/
    private Date createTime;
    /**  支付状态（1：待支付，2：支付中，3：网点支付完成，4：交易成功，5：交易失败，6：网点支付异常*/
    private Integer payStatus;
    /**  网点其他信息*/
    private String storeInfo;
    /**  提点系数*/
    private  Double liftCoefficient;
    /**  平台和县域，平台分成比*/
    private  Double platformDividedRatio;
    /**  平台和县域，县域分成比*/
    private Double  areaDividedRatio;
    /**  县域和网点，县域佣金比*/
    private Double areaCommissionRatio;
    /**  县域和网点，网点佣金比*/
    private Double storeCommissionRatio;
    /**  缴费账单日期*/
    private  String billYearMonth;
    /**  供应商产品id*/
    private String productId;
    /**  会员手机号码*/
    private String memberMobile;
    /**  会员姓名*/
    private String memberName;
    /**  乘车日期*/
    private String travelTime;
    /**  火车开车时间*/
    private String stationStartTime;
    /**  始发站*/
    private String fromStation;
    /**  火车到达时间*/
    private String stationArriveTime;
    /**  终点站*/
    private String arriveStation;
    /**  是否购买保险*/
    private Integer bx;
    /**  证件类型*/
    private Integer idType;
    /**  证件号码*/
    private String idCardId;
    /**  乘客姓名*/
    private String userName;
    /**  座位类型*/
    private Integer seatType;
    /**  车次*/
    private String trainCode;
    /**  服务费用*/
    private  Double servicePrice;
    /**  联系人姓名*/
    private String linkName;
    /**  联系人手机号*/
    private String linkPhone;
    
    /**
     * 车票类型
     */
    private String ticketType;
    
    /**
     * 车厢号
     */
    private String trainBox;
    
    /**
     * 座位号
     */
    private String seatNo;
    
    /**
     * 出票时间
     */
    private String outTicktetTime;
    
    
    /**
     * 供应商订单ID
     */
    private String supplierOrderId;

    /**
     * 订单失败描述（供应商返回）
     */
    private String failReason;

    /**
     * 途径时长（分钟）
     */
    private String costTime;

    @Column(name="supplier_order_id")
    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    @Column(name="out_ticktet_time")
    public String getOutTicktetTime() {
        return outTicktetTime;
    }

    public void setOutTicktetTime(String outTicktetTime) {
        this.outTicktetTime = outTicktetTime;
    }

    @Column(name="train_box")
    public String getTrainBox() {
        return trainBox;
    }

    public void setTrainBox(String trainBox) {
        this.trainBox = trainBox;
    }

    @Column(name="seat_no")
    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Column(name="ticket_type")
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    @Column(name = "hcp_order_code")
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }
    @Column(name = "open_platform_no")
    public String getOpenPlatformNo() {
        return openPlatformNo;
    }

    public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo =openPlatformNo;
    }
    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode =supplierCode;
    }
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName =supplierName;
    }
    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }
    @Column(name = "business_name")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName =businessName;
    }
    @Column(name = "sub_business_code")
    public String getSubBusinessCode() {
        return subBusinessCode;
    }

    public void setSubBusinessCode(String subBusinessCode) {
        this.subBusinessCode =subBusinessCode;
    }
    @Column(name = "sub_business_name")
    public String getSubBusinessName() {
        return subBusinessName;
    }

    public void setSubBusinessName(String subBusinessName) {
        this.subBusinessName =subBusinessName;
    }
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city =city;
    }
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province =province;
    }
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country =country;
    }
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode =countryCode;
    }
    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName =countryName;
    }
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile =mobile;
    }
    @Column(name = "pay_user_name")
    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName =payUserName;
    }
    @Column(name = "pay_user_id")
    public Integer getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(Integer payUserId) {
        this.payUserId =payUserId;
    }
    @Column(name = "biz_id")
    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId =bizId;
    }
    @Column(name = "biz_type")
    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType =bizType;
    }
    @Column(name = "store_code")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode =storeCode;
    }
    @Column(name = "store_name")
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName =storeName;
    }
    @Column(name = "costing_amount")
    public BigDecimal getCostingAmount() {
        return costingAmount;
    }

    public void setCostingAmount(BigDecimal costingAmount) {
        this.costingAmount =costingAmount;
    }
    @Column(name = "original_amount")
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount =originalAmount;
    }
    @Column(name = "real_amount")
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    @Column(name = "pay_status")
    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus =payStatus;
    }
    @Column(name = "store_info")
    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo =storeInfo;
    }
    @Column(name = "lift_coefficient")
    public Double getLiftCoefficient() {
        return liftCoefficient;
    }

    public void setLiftCoefficient(Double liftCoefficient) {
        this.liftCoefficient =liftCoefficient;
    }
    @Column(name = "platform_divided_ratio")
    public Double getPlatformDividedRatio() {
        return platformDividedRatio;
    }

    public void setPlatformDividedRatio( Double platformDividedRatio) {
        this.platformDividedRatio =platformDividedRatio;
    }
    @Column(name = "area_divided_ratio")
    public Double getAreaDividedRatio() {
        return areaDividedRatio;
    }

    public void setAreaDividedRatio(Double areaDividedRatio) {
        this.areaDividedRatio =areaDividedRatio;
    }
    @Column(name = "area_commission_ratio")
    public Double getAreaCommissionRatio() {
        return areaCommissionRatio;
    }

    public void setAreaCommissionRatio(Double areaCommissionRatio) {
        this.areaCommissionRatio =areaCommissionRatio;
    }
    @Column(name = "store_commission_ratio")
    public Double getStoreCommissionRatio() {
        return storeCommissionRatio;
    }

    public void setStoreCommissionRatio( Double storeCommissionRatio) {
        this.storeCommissionRatio =storeCommissionRatio;
    }
    @Column(name = "bill_year_month")
    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth =billYearMonth;
    }
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId =productId;
    }
    @Column(name = "member_mobile")
    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile =memberMobile;
    }
    @Column(name = "member_name")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName =memberName;
    }
    @Column(name = "travel_time")
    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime =travelTime;
    }
    @Column(name = "station_start_time")
    public String getStationStartTime() {
        return stationStartTime;
    }

    public void setStationStartTime(String stationStartTime) {
        this.stationStartTime =stationStartTime;
    }
    @Column(name = "from_station")
    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation =fromStation;
    }
    @Column(name = "station_arrive_time")
    public String getStationArriveTime() {
        return stationArriveTime;
    }

    public void setStationArriveTime(String stationArriveTime) {
        this.stationArriveTime =stationArriveTime;
    }
    @Column(name = "arrive_station")
    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation =arriveStation;
    }
    @Column(name = "bx")
    public Integer getBx() {
        return bx;
    }

    public void setBx(Integer bx) {
        this.bx =bx;
    }
    @Column(name = "id_type")
    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType =idType;
    }
    @Column(name = "id_card_id")
    public String getIdCardId() {
        return idCardId;
    }

    public void setIdCardId(String idCardId) {
        this.idCardId =idCardId;
    }
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName =userName;
    }
    @Column(name = "seat_type")
    public Integer getSeatType() {
        return seatType;
    }

    public void setSeatType(Integer seatType) {
        this.seatType =seatType;
    }

    @Column(name = "train_code")
    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode =trainCode;
    }
    @Column(name = "service_price")
    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice =servicePrice;
    }
    @Column(name = "link_name")
    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName =linkName;
    }
    @Column(name = "link_phone")
    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    @Column(name = "fail_reason")
    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Column(name = "costtime")
    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }
}
