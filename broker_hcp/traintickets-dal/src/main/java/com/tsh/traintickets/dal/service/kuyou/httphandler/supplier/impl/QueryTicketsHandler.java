package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketSupplierRequest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Map;


/**
 * Created by Administrator on 2016/11/16 016.
 */
@Service("queryTicketsHandler")
public class QueryTicketsHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(QueryTicketsHandler.class);

    @Override
    public String doHandler(Object request){
        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = super.init(timestamp);
        QueryTicketSupplierRequest supplierRequest = (QueryTicketSupplierRequest) request;

        // 加密字符串body
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(super.getHeaderParamsString(timestamp)).
                append(supplierRequest.getTravel_time()).
                append(supplierRequest.getFrom_station()).
                append(supplierRequest.getArrive_station());

        // 设置业务参数
        paramsMap.put("travel_time", supplierRequest.getTravel_time());
        paramsMap.put("from_station", supplierRequest.getFrom_station());
        paramsMap.put("arrive_station", supplierRequest.getArrive_station());

        // 设置加密内容
        paramsMap.put("hmac", getEncrypt(paramsBuilder.toString()));

        String result = null;
        logger.info("------------------> queryLeftTicket request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> queryLeftTicket error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.QUERY_CHECKT_NUM_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> queryLeftTicket response:" + result);

        return result;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.queryLeftTicket.toString();
    }
}
