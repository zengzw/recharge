package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketSupplierRequest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21 021.
 */
@Service("refundTicketHandler")
public class RefundTicketHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(RefundTicketHandler.class);

    @Override
    public String doHandler(Object request) {
        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = super.init(timestamp);
        RefundTicketSupplierRequest supplierRequest = (RefundTicketSupplierRequest) request;

        Map<String, Object> jsonParams = this.getJsonParamMap(supplierRequest);

        // 去掉转义符号
        String jsonParamsString = StringEscapeUtils.unescapeJava(JSON.toJSONString(jsonParams));

        // 加密字符串body
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(getHeaderParamsString(timestamp)).
                append(jsonParamsString);

        // 设置加密内容
        paramsMap.put("hmac", getEncrypt(paramsBuilder.toString()));
        paramsMap.put("json_param", jsonParamsString);

        String result = null;
        logger.info("------------------> refundTicket request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> refundTicket error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.REFUND_TICKET_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> refundTicket response:" + result);

        return result;
    }

    private Map<String, Object> getJsonParamMap(RefundTicketSupplierRequest supplierRequest) {
        Map<String, Object> jsonParams = new HashMap<String, Object>();
        jsonParams.put("comment", supplierRequest.getComment());
        jsonParams.put("merchant_order_id", supplierRequest.getMerchant_order_id());
        jsonParams.put("order_id", supplierRequest.getOrder_id());
        jsonParams.put("refund_picture_url", "");
        jsonParams.put("refund_result_url", supplierRequest.getRefund_result_url());
        jsonParams.put("refund_type", supplierRequest.getRefund_type());
        jsonParams.put("request_id", supplierRequest.getRequest_id());
        jsonParams.put("refundinfo", Collections.emptyList());
        return jsonParams;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.refundTicket.toString();
    }
}
