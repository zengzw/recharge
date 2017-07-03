package com.tsh.traintickets.dal.service.kuyou.httphandler.supplier.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderSupplierRequest;
import com.tsh.traintickets.bo.kuyou.queryorder.BookDetail;
import com.tsh.traintickets.dal.service.kuyou.httphandler.AbstractKuyouHttpHandler;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by Administrator on 2016/11/21 021.
 */
@Service("createOrderHandler")
public class CreateOrderHandler extends AbstractKuyouHttpHandler implements TicketHttpHandler {

    private static Logger logger = LogManager.getLogger(CreateOrderHandler.class);

    @Override
    public String doHandler(Object request){
        String timestamp = super.getTimestamp();

        Map<String, String> paramsMap = super.init(timestamp);
        CreateOrderSupplierRequest supplierRequest = (CreateOrderSupplierRequest) request;

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
        logger.info("------------------> createOrder request:" + JSON.toJSONString(paramsMap));
        try{
            result = HttpXmlClient.post(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_url), paramsMap);
        } catch (Exception e){
            logger.error("------------------> createOrder error:"+e.getMessage(), e);
            throw new BizException(ResponseCode.CREATE_ORDER_ERROR);
        }
        result = super.getUtf8(result);
        logger.info("------------------> createOrder response:" + result);

        return result;
    }

    @Override
    protected String getMethodName() {
        return KuyouSupplierMethod.createOrder.toString();
    }

    // 设置输入参数
    private Map<String, Object> getJsonParamMap(CreateOrderSupplierRequest supplierRequest){
        Map<String, Object> jsonParams = new LinkedHashMap<String, Object>();
        jsonParams.put("merchant_order_id", supplierRequest.getMerchant_order_id());
        jsonParams.put("order_level", supplierRequest.getOrder_level());
        jsonParams.put("order_result_url", supplierRequest.getOrder_result_url());
        jsonParams.put("arrive_station", supplierRequest.getArrive_station());
        jsonParams.put("arrive_time", supplierRequest.getArrive_time());
        jsonParams.put("bx_invoice", supplierRequest.getBx_invoice());
        jsonParams.put("from_station", supplierRequest.getFrom_station());
        jsonParams.put("from_time", supplierRequest.getFrom_time());
        jsonParams.put("seat_type", supplierRequest.getSeat_type());
        jsonParams.put("sms_notify", supplierRequest.getSms_notify());
        jsonParams.put("sum_amount", supplierRequest.getSum_amount());
        jsonParams.put("ticket_price", supplierRequest.getTicket_price());
        jsonParams.put("train_code", supplierRequest.getTrain_code());
        jsonParams.put("travel_time", supplierRequest.getTravel_time());
        jsonParams.put("wz_ext", supplierRequest.getWz_ext());
        List<Map<String, String>> passengers = new ArrayList<Map<String, String>>();
        if(null != supplierRequest.getBook_detail_list()
                && !supplierRequest.getBook_detail_list().isEmpty()){
            for(BookDetail bookDetail : supplierRequest.getBook_detail_list()){
                Map<String,String> onePassenger = new HashMap<String, String>();
                onePassenger.put("bx", bookDetail.getBx());
                onePassenger.put("ids_type", bookDetail.getIds_type());
                onePassenger.put("ticket_type", bookDetail.getTicket_type());
                onePassenger.put("user_ids", bookDetail.getUser_ids());
                onePassenger.put("user_name", bookDetail.getUser_name());
                passengers.add(onePassenger);
            }
            jsonParams.put("book_detail_list", passengers);
        }
        return jsonParams;
    }
}
