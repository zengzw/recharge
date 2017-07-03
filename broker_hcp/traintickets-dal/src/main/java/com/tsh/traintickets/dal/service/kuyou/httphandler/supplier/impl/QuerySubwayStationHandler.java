package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationSupplierRequest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/19 019.
 */
@Service("querySubwayStationHandler")
public class QuerySubwayStationHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(QuerySubwayStationHandler.class);

    @Override
    public String doHandler(Object request){

        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = super.init(timestamp);
        QuerySubwayStationSupplierRequest supplierRequest = (QuerySubwayStationSupplierRequest) request;

        // 加密字符串body
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(super.getHeaderParamsString(timestamp)).
                append(supplierRequest.getTrain_code());

        // 设置业务参数
        paramsMap.put("train_code", supplierRequest.getTrain_code());

        // 设置加密内容
        paramsMap.put("hmac", getEncrypt(paramsBuilder.toString()));


        String result = null;
        logger.info("------------------> querySubwayStation request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> querySubwayStation error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.QUERY_SUBWAY_STATION_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> querySubwayStation response:" + result);

        return result;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.querySubwayStation.toString();
    }
}
