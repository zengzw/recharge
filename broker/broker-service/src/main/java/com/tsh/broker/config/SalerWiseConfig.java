package com.tsh.broker.config;

import com.tsh.broker.diamond.TshDiamondClient;

/**
 * SalerWiseConfig
 *
 * @author dengjd
 * @date 2016/9/29
 */
public class SalerWiseConfig  extends BaseConfig{
/*    //用户名
    private String userName ;
    //签名秘钥
    private String signKey;*/
    //查询缴费单位接口Url地址
    private String  queryPayUnitUrl;
    //公共事业缴费充值Url地址
    private String  rechargeUrl;
    //查询充值结果Url地址
    private String queryRechargeResultUrl;
    //查询缴费账单Url地址
    private String queryPaymentBillUrl;
    //充值超时时间（秒），时间区间 (300 秒-7200 秒)，默认600秒
    private int rechargeTimeout = 600;
    //多账单查询接口url地址
    private String queryPaymentBillMoreUrl;

    @Override
    public String getUserName() {
        return TshDiamondClient.getInstance().getConfig("sdm.salerwise.userName");
    }

    @Override
    public String getSignKey() {
        return TshDiamondClient.getInstance().getConfig("sdm.salerwise.signKey");
    }

    @Override
    public String getBusinessNo() {
        return TshDiamondClient.getInstance().getConfig("sdm.salerwise.businessNo");
    }

    @Override
    public String getBusinessKey() {
        return TshDiamondClient.getInstance().getConfig("sdm.salerwise.businessKey");
    }

    @Override
    public String getResultNotifyUrl() {
        return TshDiamondClient.getInstance().getConfig("sdm.salerwise.resultNotifyUrl");
    }



    public String getQueryPayUnitUrl() {
        return queryPayUnitUrl;
    }

    public void setQueryPayUnitUrl(String queryPayUnitUrl) {
        this.queryPayUnitUrl = queryPayUnitUrl;
    }

    public String getRechargeUrl() {
        return rechargeUrl;
    }

    public void setRechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl;
    }

    public String getQueryRechargeResultUrl() {
        return queryRechargeResultUrl;
    }

    public void setQueryRechargeResultUrl(String queryRechargeResultUrl) {
        this.queryRechargeResultUrl = queryRechargeResultUrl;
    }
    public String getQueryPaymentBillUrl() {
        return queryPaymentBillUrl;
    }

    public void setQueryPaymentBillUrl(String queryPaymentBillUrl) {
        this.queryPaymentBillUrl = queryPaymentBillUrl;
    }

    public int getRechargeTimeout() {
        return rechargeTimeout;
    }

    public void setRechargeTimeout(int rechargeTimeout) {
        this.rechargeTimeout = rechargeTimeout;
    }

	public String getQueryPaymentBillMoreUrl() {
		return queryPaymentBillMoreUrl;
	}

	public void setQueryPaymentBillMoreUrl(String queryPaymentBillMoreUrl) {
		this.queryPaymentBillMoreUrl = queryPaymentBillMoreUrl;
	}
    
}
