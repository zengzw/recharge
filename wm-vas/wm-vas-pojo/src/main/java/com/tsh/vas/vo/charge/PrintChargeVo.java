package com.tsh.vas.vo.charge;

import com.dtds.platform.commons.utility.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/10/27.
 */
public class PrintChargeVo {

    /**
     * 订单编号
     */
    private String chargeCode;
    /**
     * 订单类型名称
     */
    private String chargeTypeStr;
    /**
     * 消费热线
     */
    private String consumerHotline;
    /**
     * 日期
     */
    private Date createTime;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 加盟店,名称
     */
    private String franchisedStore;
    /**
     * 会员卡号
     */
    private String memberCard;
    /**
     * 会员
     */
    private String memberName;

    /**
     实收金额
     */
    ;
    private BigDecimal realAmount;
    /**
     门店地址
     */
    ;
    private String storeAddr;
    /**
     * 门店电话
     */
    private String mobile;
    /**
     * 请保管
     */
    private String tips;
    /**
     * 合计
     */
    private BigDecimal totalAmount;
    /**
     * 谢谢惠顾，欢迎下次光临
     */
    private String welcomes;

    /**
     * 产品信息
     */
    private List<ProductInfo> productInfos;
    /**
     * 账户余额
     */
    private BigDecimal memberBalance;

    /**
     * 日期格式化
     */
    public String getCreatTimeStr() {
        return DateUtil.date2String (createTime);
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getChargeTypeStr() {
        return chargeTypeStr;
    }

    public void setChargeTypeStr(String chargeTypeStr) {
        this.chargeTypeStr = chargeTypeStr;
    }

    public String getConsumerHotline() {
        return consumerHotline;
    }

    public void setConsumerHotline(String consumerHotline) {
        this.consumerHotline = consumerHotline;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFranchisedStore() {
        return franchisedStore;
    }

    public void setFranchisedStore(String franchisedStore) {
        this.franchisedStore = franchisedStore;
    }

    public String getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getWelcomes() {
        return welcomes;
    }

    public void setWelcomes(String welcomes) {
        this.welcomes = welcomes;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    public static class ProductInfo {

        /**
         * 金额
         */
        private BigDecimal amount;
        /**
         * 单价
         */
        private BigDecimal price;
        /**
         * 商品
         */
        private String productName;
        /**
         * 数量
         */
        private Integer quantity;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
