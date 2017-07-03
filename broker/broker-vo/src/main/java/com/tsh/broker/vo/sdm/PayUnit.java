package com.tsh.broker.vo.sdm;

/**
 *
 *  缴费单位信息 PayUnit
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class PayUnit {

    private  String    unitId;    		//	缴费单位ID ,chargeOrgCode
    private  String    unitName;    	//	单位名称,chargeName
    private  String    province;    	//	省份/直辖市
    private  String    city;    		//	城市/区
    private  String    county;    		//	县/区
    private  Integer    payType;    	//	缴费类型	1.水费2.电费3.煤气
    private  Integer    payModel;    	//	缴费模式	1.预付 2.后付,如果没有通过查询账单判断
    private  Integer   accountType;   // 支持账户类型 0.默认值（任意账户类型） 1.户号 2.条形码 3.都支持(户号和条形码)
    private  Integer    isNeedMonth;    //	是否需要账单月份	Int	是	1.不需要 2.需要
    private  Integer    unitStatus;    	//	单位状态	1.关闭 2.开启
    private String  productId;          //产品ID


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }


    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getIsNeedMonth() {
        return isNeedMonth;
    }

    public void setIsNeedMonth(Integer isNeedMonth) {
        this.isNeedMonth = isNeedMonth;
    }

    public Integer getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(Integer unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


}
