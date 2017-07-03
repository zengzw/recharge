package com.tsh.phone.commoms.config;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/12 012.
 */
public class FLRechargeConfig extends BaseConfig{

    /**
     * customerid
     * @return
     */
    public static String getCustomerId(){
        return getStaticTshDiamondClient().getConfig("fl.customerid");
    }

    /**
     * businessKey
     * @return
     */
    public String getBusinessKey(){
        return getStaticTshDiamondClient().getConfig("fl.businessKey");
    }

    /**
     *  privatekey
     * @return
     */
    public static String getPrivateKey() {
        return getStaticTshDiamondClient().getConfig("fl.privatekey");
    }

    /**
     *  backurl
     * @return
     */
    public static String getBackUrl() {
        return getStaticTshDiamondClient().getConfig("fl.backurl");
    }

    /**
     * baseurl
     * @return
     */
    public static String getBaseUrl(){
        return getStaticTshDiamondClient().getConfig("fl.baseurl");
    }

    /**
     * vascallback
     * @return
     */
    public static String getVasCallBackUrl(){
        return getStaticTshDiamondClient().getConfig("fl.vascallback");
    }

    @Override
    public String requestSign(Map<String, Object> params) {
        return null;
    }



}
