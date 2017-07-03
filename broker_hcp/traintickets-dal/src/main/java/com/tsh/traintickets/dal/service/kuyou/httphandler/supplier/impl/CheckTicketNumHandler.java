package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumSupplierRequest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/18 018.
 */
@Service("checkTicketNumHandler")
public class CheckTicketNumHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(CheckTicketNumHandler.class);

    @Override
    public String doHandler(Object request){
        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = init(timestamp);
        CheckTicketNumSupplierRequest supplierRequest = (CheckTicketNumSupplierRequest) request;

        // 加密字符串body
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(getHeaderParamsString(timestamp)).
                append(supplierRequest.getTrain_code()).
                append(supplierRequest.getTravel_time()).
                append(supplierRequest.getFrom_station()).
                append(supplierRequest.getArrive_station());

        // 设置业务参数
        paramsMap.put("train_code", supplierRequest.getTrain_code());
        paramsMap.put("travel_time", supplierRequest.getTravel_time());
        paramsMap.put("from_station", supplierRequest.getFrom_station());
        paramsMap.put("arrive_station", supplierRequest.getArrive_station());

        // 设置加密内容
        paramsMap.put("hmac", getEncrypt(paramsBuilder.toString()));

        String result = null;
        logger.info("------------------> checkTicketNum request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> checkTicketNum error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.QUERY_TICKETS_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> checkTicketNum response:" + result);

        return result;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.checkTicketNum.toString();
    }
}
