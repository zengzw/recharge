package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.queryorder.QueryOrderSupplierRequest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21 021.
 */
@Service("queryOrderInfoHandler")
public class QueryOrderInfoHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(QueryOrderInfoHandler.class);

    @Override
    public String doHandler(Object request) {
        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = super.init(timestamp);
        QueryOrderSupplierRequest supplierRequest = (QueryOrderSupplierRequest) request;

        // 加密字符串body
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(super.getHeaderParamsString(timestamp)).
                append(supplierRequest.getOrder_id()).
                append(supplierRequest.getMerchant_order_id());

        // 设置业务参数
        paramsMap.put("order_id", supplierRequest.getOrder_id());
        paramsMap.put("merchant_order_id", supplierRequest.getMerchant_order_id());

        // 设置加密内容
        paramsMap.put("hmac", getEncrypt(paramsBuilder.toString()));

        String result = null;
        logger.info("------------------> queryOrderInfo request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> queryOrderInfo error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.QUERY_CHECKT_NUM_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> queryOrderInfo response:" + result);


        return result;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.queryOrderInfo.toString();
    }
}
