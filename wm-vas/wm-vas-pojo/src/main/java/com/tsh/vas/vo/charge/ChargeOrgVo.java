package com.tsh.vas.vo.charge;

import java.util.List;

/**
 * Created by Iritchie.ren on 2016/10/19.
 */
public class ChargeOrgVo {

    /**
     * 供应商编号
     */
    private String supplierCode;
    /**
     * 增值服务与开放平台通讯的供应商key
     */
    private String supplierToken;
    /**
     * 供应商的服务器地址
     */
    private String serverAddr;
    /**
     * 单位列表
     */
    private List<UnitInfo> unitInfos;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierToken() {
        return supplierToken;
    }

    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public List<UnitInfo> getUnitInfos() {
        return unitInfos;
    }

    public void setUnitInfos(List<UnitInfo> unitInfos) {
        this.unitInfos = unitInfos;
    }

    public static class UnitInfo {

        /**
         * 收费机构名称
         */
        private String ChargeOrgName;
        /**
         * 收费机构爱编号
         */
        private String ChargeOrgCode;
        /**
         * 缴费模式	1.预付,可预交 2.后付，不可预交
         */
        private Integer payModel;
        /**
         * 账户号码类型	1.用户号2.条形码
         */
        private Integer accountType;
        private  String   province;    	//	省份/直辖市
        private  String   city;    		//	城市/区
        private  String   county;    		//	县/区
        private  Integer  payType;    	//	缴费类型	1.水费2.电费3.煤气
        private  Integer  isNeedMonth;    //	是否需要账单月份	Int	是	1.不需要 2.需要
        private  Integer  unitStatus;    	//	单位状态	1.关闭 2.开启
        private  String  productId;          // 产品ID

        /**
         * 单位状态	1.关闭 2.开启
         */
        private Integer status;


        public String getChargeOrgName() {
            return ChargeOrgName;
        }

        public void setChargeOrgName(String chargeOrgName) {
            ChargeOrgName = chargeOrgName;
        }

        public String getChargeOrgCode() {
            return ChargeOrgCode;
        }

        public void setChargeOrgCode(String chargeOrgCode) {
            ChargeOrgCode = chargeOrgCode;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public Integer getPayType() {
            return payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
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

}
