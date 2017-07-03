package com.tsh.broker.controller.valiator;

import com.tsh.broker.config.BaseConfig;
import com.tsh.broker.config.SalerWiseConfig;
import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.utils.SdmRequestSignUtils;
import com.tsh.broker.utils.SignUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.request.*;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate
 *
 * @author dengjd
 * @date 2016/10/9
 */
@SuppressWarnings("all")
public class SdmValidator {


    /**
     * 查询缴费单位请求参数校验
     */
    public ResponseWrapper validateQueryPayUnit(PayUnitRequest payUnitRequest,BaseConfig baseConfig){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        
        if(!baseConfig.getBusinessNo().equals(payUnitRequest.getBusinessId())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("供应商编号不正确");
            return responseWrapper;
        }
        if(payUnitRequest.getPayType() != 1
                && payUnitRequest.getPayType() != 2
                && payUnitRequest.getPayType() != 3){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("缴费类型不支持");
            return responseWrapper;
        }
        if(StringUtils.isBlank(payUnitRequest.getProvince())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("省份/直辖市不能为空");
            return responseWrapper;
        }
        if(StringUtils.isBlank(payUnitRequest.getCity())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("城市/区不能为空");
            return responseWrapper;
        }
        if(!validateTimestampFormat(payUnitRequest.getTimestamp())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        String signed = SdmRequestSignUtils.signQueryPayUnit(payUnitRequest,baseConfig.getBusinessKey());
        if(!signed.equals(payUnitRequest.getSigned())){
            responseWrapper.setStatus(10003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }


    public ResponseWrapper validateRecharge(RechargeRequest rechargeRequest,BaseConfig baseConfig) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(!baseConfig.getBusinessNo().equals(rechargeRequest.getBusinessId())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("供应商编号不正确");
            return responseWrapper;
        }
        if(StringUtils.isBlank(rechargeRequest.getOutOrderNo())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("外部订单编号不能为空");
            return responseWrapper;
        }
        if(StringUtils.isBlank(rechargeRequest.getUnitId())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("充值单位编号不能为空");
            return responseWrapper;
        }
        if(StringUtils.isBlank(rechargeRequest.getAccount())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("账户不为空");
            return responseWrapper;
        }
        if(rechargeRequest.getPayAmount().compareTo(new BigDecimal(0)) <= 0){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("充值金额不正确");
            return responseWrapper;
        }
        if(!validateTimestampFormat(rechargeRequest.getTimestamp())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        String signed = SdmRequestSignUtils.signRecharge(rechargeRequest, baseConfig.getBusinessKey());
        if(!signed.equals(rechargeRequest.getSigned())){
            responseWrapper.setStatus(20003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateQueryRechargeResult(RechargeResultRequest rechargeResultRequest,BaseConfig baseConfig) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        if(!baseConfig.getBusinessNo().equals(rechargeResultRequest.getBusinessId())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("供应商编号不正确");
            return responseWrapper;
        }
        if(StringUtils.isBlank(rechargeResultRequest.getOutOrderNo())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("外部订单编号不能为空");
            return responseWrapper;
        }
        if(!validateTimestampFormat(rechargeResultRequest.getTimestamp())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        String signed = SdmRequestSignUtils.signQueryRechargeResult(rechargeResultRequest, baseConfig.getBusinessKey());
        if(!signed.equals(rechargeResultRequest.getSigned())){
            responseWrapper.setStatus(30003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateQueryPaymentBill(PaymentBillRequest paymentBillRequest,BaseConfig baseConfig) {
    	System.out.println(baseConfig.getBusinessKey());
    	System.out.println(baseConfig.getBusinessNo());
    	
    	
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(!baseConfig.getBusinessNo().equals(paymentBillRequest.getBusinessId())){
            responseWrapper.setStatus(50001);
            responseWrapper.setMessage("供应商编号不正确");
            return responseWrapper;
        }
        if(StringUtils.isBlank(paymentBillRequest.getAccount())){
            responseWrapper.setStatus(50001);
            responseWrapper.setMessage("账户不为空");
            return responseWrapper;
        }
        if(!validateTimestampFormat(paymentBillRequest.getTimestamp())){
            responseWrapper.setStatus(50001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        String signed = SdmRequestSignUtils.signPaymentBill(paymentBillRequest, baseConfig.getBusinessKey());
        if(!signed.equals(paymentBillRequest.getSigned())){
            responseWrapper.setStatus(50003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateGenerateOrderNo(GenerateOrderNoRequest generateOrderNoRequest,BaseConfig baseConfig) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(!baseConfig.getBusinessNo().equals(generateOrderNoRequest.getBusinessId())){
            responseWrapper.setStatus(60001);
            responseWrapper.setMessage("供应商编号不正确");
            return responseWrapper;
        }
        if(!validateTimestampFormat(generateOrderNoRequest.getTimestamp())){
            responseWrapper.setStatus(60001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, baseConfig.getBusinessKey());
        if(!signed.equals(generateOrderNoRequest.getSigned())){
            responseWrapper.setStatus(60003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateResultNotify(ResultNotifyRequest resultNotifyRequest,BaseConfig baseConfig) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(!baseConfig.getUserName().equals(resultNotifyRequest.getBusinessId())){
            responseWrapper.setStatus(40001);
            responseWrapper.setMessage("用户名不正确");
            return responseWrapper;
        }
        if(StringUtils.isBlank(resultNotifyRequest.getOutOrderNo())){
            responseWrapper.setStatus(40001);
            responseWrapper.setMessage("外部订单编号不能为空");
            return responseWrapper;
        }
        //没有时间戳参数
        /*if(!validateTimestampFormat(resultNotifyRequest.getTimestamp())){
            responseWrapper.setStatus(40001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }*/

        String signed = SdmRequestSignUtils.signResultNotify(resultNotifyRequest, baseConfig.getSignKey());
        if(!signed.equals(resultNotifyRequest.getSigned())){
            responseWrapper.setStatus(40003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    private  boolean  validateTimestampFormat(String timestamp){
        if(StringUtils.isBlank(timestamp))
            return false;
        String pattern = "^\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}$";
        return  timestamp.matches(pattern);
    }
}
