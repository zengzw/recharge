package com.tsh.vas.model;

import java.io.Serializable;
import java.util.List;

/**
 * BillDetailInfo
 *
 * @author dengjd
 * @date 2016/11/3
 */
public class BillDetailInfo implements Serializable {


    public static class CommissionInfo {



        /**
         * 销售价格
         */
        private String salePrice;
        /**
         * 成本价
         */
        private String costingAmount;

        /**
         * 佣金比例
         */
        private String commissionRatio;
        
        
        /**
         * 差价
         */
        private String differential;
        
        
        /**
         * 平台提成比率
         */
        private String  platformRatio;
        
        /**
         * 平台分成
         */
        private String platformCommission;
        

        /**
         * 县域比例
         */
        private String countyRatio;
        /**
         * 县域分成比
         */
        private String areaDividedRatio;

        /**
         * 县域佣金
         */
        private String countyCommission;

        /**
         * 网点比例
         */
        private String shopRatio;

        /**
         * 网点佣金
         */
        private String shopCommission;

        public String getCommissionRatio() {
            return commissionRatio;
        }

        public String getCountyCommission() {
            return countyCommission;
        }

        public String getCountyRatio() {
            return countyRatio;
        }

        public String getDifferential() {
            return differential;
        }

        public String getPlatformCommission() {
            return platformCommission;
        }

        public String getPlatformRatio() {
            return platformRatio;
        }


        public String getSalePrice() {
            return salePrice;
        }

        public String getShopCommission() {
            return shopCommission;
        }


        public String getShopRatio() {
            return shopRatio;
        }

        public void setCommissionRatio(String commissionRatio) {
            this.commissionRatio = commissionRatio;
        }

        public void setCountyCommission(String countyCommission) {
            this.countyCommission = countyCommission;
        }

        public void setCountyRatio(String countyRatio) {
            this.countyRatio = countyRatio;
        }

        public void setDifferential(String differential) {
            this.differential = differential;
        }

        public void setPlatformCommission(String platformCommission) {
            this.platformCommission = platformCommission;
        }

        public void setPlatformRatio(String platformRatio) {
            this.platformRatio = platformRatio;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public void setShopCommission(String shopCommission) {
            this.shopCommission = shopCommission;
        }

        public void setShopRatio(String shopRatio) {
            this.shopRatio = shopRatio;
        }

		public String getCostingAmount() {
			return costingAmount;
		}

		public void setCostingAmount(String costingAmount) {
			this.costingAmount = costingAmount;
		}

		public String getAreaDividedRatio() {
			return areaDividedRatio;
		}

		public void setAreaDividedRatio(String areaDividedRatio) {
			this.areaDividedRatio = areaDividedRatio;
		}
        
        
    }

    /**
     * 缴费订单编号
     */
    private String chargeCode;

    /**
     * 业务类型名
     */
    private String businessCodeName;

    /**
     * 支付金额
     */
    private String paymentAmount;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 销售县域
     */
    private String saleCountyName;

    /**
     * 网点
     */
    private String shopName;

    /**
     * 佣金信息
     */
    private List<CommissionInfo> commissionInfoList;

    public String getBusinessCodeName() {
        return businessCodeName;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public List<CommissionInfo> getCommissionInfoList() {
        return commissionInfoList;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getSaleCountyName() {
        return saleCountyName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setBusinessCodeName(String businessCodeName) {
        this.businessCodeName = businessCodeName;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public void setCommissionInfoList(List<CommissionInfo> commissionInfoList) {
        this.commissionInfoList = commissionInfoList;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setSaleCountyName(String saleCountyName) {
        this.saleCountyName = saleCountyName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}
