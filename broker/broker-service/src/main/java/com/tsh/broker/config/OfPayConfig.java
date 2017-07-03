package com.tsh.broker.config;

import com.tsh.broker.diamond.TshDiamondClient;

/**
 * OfPayConfig
 *
 * @author dengjd
 * @date 2016/10/15
 */
public class OfPayConfig extends BaseConfig {

    //充值签名秘钥
    private String rechargeSignKey;
    //4.公共事业缴费单位查询接口(getPayUnitList.do)
    private String getPayUnitListURL;
    //5.公共事业缴费方式查询接口（getPayModeList.do）
    private String getPayModeListURL;
    //6.公共事业商品信息查询接口(queryClassId.do)
    private String queryClassIdURL;
    //7.公共事业账单查询接口(queryBalance.do)
    private String queryBalanceURL;
    //8.公共事业充值(utilityOrder.do)
    private String utilityOrderURL;
    //易赛充值结果回调地址
    private String callbackUrl;
    //充值订单查询接口
    public String queryOrderResultURL;

    @Override
    public String getUserName() {
        return TshDiamondClient.getInstance().getConfig("sdm.ofpay.userName");
    }

    @Override
    public String getSignKey() {
        return TshDiamondClient.getInstance().getConfig("sdm.ofpay.signKey");
    }

    @Override
    public String getBusinessNo() {
        return TshDiamondClient.getInstance().getConfig("sdm.ofpay.businessNo");
    }

    @Override
    public String getBusinessKey() {
        return TshDiamondClient.getInstance().getConfig("sdm.ofpay.businessKey");
    }

    @Override
    public String getResultNotifyUrl() {
        return TshDiamondClient.getInstance().getConfig("sdm.ofpay.resultNotifyUrl");
    }


    public String getRechargeSignKey() {
        return rechargeSignKey;
    }

    public void setRechargeSignKey(String rechargeSignKey) {
        this.rechargeSignKey = rechargeSignKey;
    }

    public String getGetPayUnitListURL() {
        return getPayUnitListURL;
    }

    public void setGetPayUnitListURL(String getPayUnitListURL) {
        this.getPayUnitListURL = getPayUnitListURL;
    }

    public String getGetPayModeListURL() {
        return getPayModeListURL;
    }

    public void setGetPayModeListURL(String getPayModeListURL) {
        this.getPayModeListURL = getPayModeListURL;
    }

    public String getQueryClassIdURL() {
        return queryClassIdURL;
    }

    public void setQueryClassIdURL(String queryClassIdURL) {
        this.queryClassIdURL = queryClassIdURL;
    }

    public String getQueryBalanceURL() {
        return queryBalanceURL;
    }

    public void setQueryBalanceURL(String queryBalanceURL) {
        this.queryBalanceURL = queryBalanceURL;
    }

    public String getUtilityOrderURL() {
        return utilityOrderURL;
    }

    public void setUtilityOrderURL(String utilityOrderURL) {
        this.utilityOrderURL = utilityOrderURL;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getQueryOrderResultURL() {
        return queryOrderResultURL;
    }

    public void setQueryOrderResultURL(String queryOrderResultURL) {
        this.queryOrderResultURL = queryOrderResultURL;
    }



}
