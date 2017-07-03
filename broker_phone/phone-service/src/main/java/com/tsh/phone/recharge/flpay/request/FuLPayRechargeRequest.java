package com.tsh.phone.recharge.flpay.request;

import com.dtds.platform.commons.utility.DateUtil;
import com.github.ltsopensource.core.json.JSON;
import com.tsh.phone.aop.LogConfig;
import com.tsh.phone.commoms.BaseRechargeRquest;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.config.FLRechargeConfig;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.properties.BasicProperties;
import com.tsh.phone.commoms.properties.FLRechargeProperties;
import com.tsh.phone.commoms.utils.Md5Digest;
import com.tsh.phone.recharge.flpay.common.FlConstantsEnums;
import com.tsh.phone.util.HttpUtils;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/12 012.
 */
@Component
public class FuLPayRechargeRequest extends BaseRechargeRquest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuLPayRechargeRequest.class);

    /**
     *
     * @param bizVo
     * @return
     * @throws Exception
     */
    @LogConfig(supplierType="FL")
    public ResponseMessage queryMobile(RequestQueryPhoneTypeVo bizVo) throws Exception{

        Map<String, Object> params = new HashMap<>();
        params.put("method", FlConstantsEnums.QUERY_MOBILE.getValue());
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", FlConstantsEnums.VERSION14.getValue());
        params.put("format", FlConstantsEnums.FORMAT.getValue());

        params.put("customerid", FLRechargeConfig.getCustomerId());
        params.put("chargephone", bizVo.getMobileNum());
        params.put("chargeparvalue", bizVo.getMoney());

        StringBuilder signParamsBuilder = new StringBuilder().append("chargeparvalue="+bizVo.getMoney())
                .append("&chargephone="+bizVo.getMobileNum())
                .append("&customerid="+FLRechargeConfig.getCustomerId())
                .append("&format="+FlConstantsEnums.FORMAT.getValue())
                .append("&method="+FlConstantsEnums.QUERY_MOBILE.getValue())
                .append("&timestamp="+timestamp)
                .append("&v="+FlConstantsEnums.VERSION14.getValue());

        String signValue = StringEscapeUtils.unescapeJava(signParamsBuilder.toString());
        params.put("sign", Md5Digest.encryptMD5(signValue + FLRechargeConfig.getPrivateKey()));

        LOGGER.info("fl querymobile params:" + params);
        String result = null;
        try{
            result =  HttpUtils.doPost(FLRechargeConfig.getBaseUrl(), params, HttpUtils.charset_utf8);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),JSON.toJSONString(params), e.getMessage());
        }

        return getSuccessMessage(result,JSON.toJSONString(params), result);
    }

    /**
     *
     * @param bizVo
     * @return
     * @throws Exception
     */
    @LogConfig(supplierType="FL")
    public ResponseMessage queryOrder(RequestOrderInfoVo bizVo) throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("method", FlConstantsEnums.QUERY_ORDER.getValue());
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", FlConstantsEnums.VERSION10.getValue());
        params.put("format", FlConstantsEnums.FORMAT.getValue());

        params.put("customerid", FLRechargeConfig.getCustomerId());
        params.put("customerorderno", bizVo.getOrderId());


        StringBuilder signValueBuilder = new StringBuilder().append("customerid="+FLRechargeConfig.getCustomerId())
                .append("&customerorderno="+bizVo.getOrderId())
                .append("&format="+FlConstantsEnums.FORMAT.getValue())
                .append("&method="+FlConstantsEnums.QUERY_ORDER.getValue())
                .append("&timestamp="+timestamp)
                .append("&v="+FlConstantsEnums.VERSION10.getValue());

        String signValue = StringEscapeUtils.unescapeJava(signValueBuilder.toString());

        params.put("sign", Md5Digest.encryptMD5(signValue + FLRechargeConfig.getPrivateKey()));
        LOGGER.info("fl queryOrder params:" + params);
        String result = null;
        try{
            result =  HttpUtils.doPost(FLRechargeConfig.getBaseUrl(), params, HttpUtils.charset_utf8);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),JSON.toJSONString(params), e.getMessage());
        }

        return getSuccessMessage(result, JSON.toJSONString(params), result);

    }

    /**
     *
     * @param orderno
     * @param bizVo
     * @return
     * @throws Exception
     */
    @LogConfig(supplierType="FL")
    public ResponseMessage charge(String orderno, RequestRechargeVo bizVo) throws Exception{

        Map<String, Object> params = new HashMap<>();
        params.put("method", FlConstantsEnums.CHARGE.getValue());
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", FlConstantsEnums.VERSION10.getValue());
        params.put("format", FlConstantsEnums.FORMAT.getValue());

        params.put("customerid", FLRechargeConfig.getCustomerId());
        params.put("customerorderno", orderno);
        params.put("chargephone", bizVo.getMobileNum());
        params.put("chargeparvalue", bizVo.getPrice());
        params.put("notifyurl", FLRechargeConfig.getBackUrl());


        StringBuilder signValueBuilder = new StringBuilder().append("chargeparvalue="+bizVo.getPrice())
                .append("&chargephone="+bizVo.getMobileNum())
                .append("&customerid="+FLRechargeConfig.getCustomerId())
                .append("&customerorderno="+orderno)
                .append("&format="+FlConstantsEnums.FORMAT.getValue())
                .append("&method="+FlConstantsEnums.CHARGE.getValue())
                .append("&notifyurl="+FLRechargeConfig.getBackUrl())
                .append("&timestamp="+timestamp)
                .append("&v="+FlConstantsEnums.VERSION10.getValue());

        String signValue = StringEscapeUtils.unescapeJava(signValueBuilder.toString());

        params.put("sign", Md5Digest.encryptMD5(signValue + FLRechargeConfig.getPrivateKey()));

        LOGGER.info("fl charge params:" + params);
        String result = null;
        try{
            result =  HttpUtils.doPost(FLRechargeConfig.getBaseUrl(), params, HttpUtils.charset_utf8);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return getErrorMessage(String.valueOf(SystemCodes.ERROR_CODE),JSON.toJSONString(params), e.getMessage());
        }

        return getSuccessMessage(result, JSON.toJSONString(params), result);

    }

    /**
     *
     * @param reqeust
     */
    @LogConfig(supplierType="FL")
    public String callBackToVas(RequestCallbackVo reqeust){

        Map<String, Object> params = new LinkedHashMap();
        params.put("orderId", reqeust.getCustomerOrderNo());

        if(FlConstantsEnums.CHARGE_CALLBACK_SUCCESS.getValue().equals(reqeust.getStatusStr())){
            params.put("status", Configurations.RechargeCallBackStatus.SUCCESS);
        } else {
            params.put("status", Configurations.RechargeCallBackStatus.FAILED);
        }
        params.put("message", reqeust.getMessage());

        return HttpUtils.doPost(FLRechargeConfig.getVasCallBackUrl(), params, HttpUtils.charset_utf8);
    }


    @Override
    public BasicProperties getProperteis() {
        return new FLRechargeProperties();
    }
}
