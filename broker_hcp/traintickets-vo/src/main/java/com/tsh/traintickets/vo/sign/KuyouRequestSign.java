package com.tsh.traintickets.vo.sign;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.utils.SignUtils;
import com.tsh.traintickets.vo.request.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求参数签名（此类不要轻易修改，VAS会引用）
 * Created by Administrator on 2016/11/25 025.
 *
 */
public class KuyouRequestSign {

    private static Logger logger = LogManager.getLogger(KuyouRequestSign.class);

    public static String signQueryTickets(QueryTicketsRequest request, String signKey){

        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("travelTime", request.getTravelTime());
        paramsMap.put("fromStation", request.getFromStation());
        paramsMap.put("arriveStation", request.getArriveStation());

        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }

    public static String signVerifyUsers(VerifyUsersRequest request, String signKey){
        return SignUtils.doKuyouSign(request.getUserList(), signKey);
    }

    public static String signQueryTicketNum(CheckTicketNumRequest request, String signKey){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("trainCode", request.getTrainCode());
        paramsMap.put("travelTime", request.getTravelTime());
        paramsMap.put("fromStation", request.getFromStation());
        paramsMap.put("arriveStation", request.getArriveStation());

        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }

    public static String signQuerySubwayStation(QuerySubwayStationRequest request, String signKey){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("trainCode", request.getTrainCode());
        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }

    public static String signQueryOrderInfo(QueryOrderInfoRequest request, String signKey){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("orderId", request.getOrderId());
        paramsMap.put("merchantOrderId", request.getMerchantOrderId());

        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }

    public static String signGetOrderNo(String signKey){
        return SignUtils.doKuyouSign("", signKey);
    }

    public static String signRefundTicket(RefundTicketRequest request, String signKey){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("comment", request.getComment());
        paramsMap.put("merchantOrderId", request.getMerchantOrderId());
        paramsMap.put("orderId", request.getOrderId());
        paramsMap.put("refundType", request.getRefundType());
        paramsMap.put("requestId", request.getRequestId());

        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }

    public static String signCreateOrder(CreateOrderRequest request, String signKey){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("merchantOrderId", request.getMerchantOrderId());
        paramsMap.put("orderLevel", request.getOrderLevel());
        paramsMap.put("arriveStation", request.getArriveStation());
        paramsMap.put("arriveTime", request.getArriveTime());
        paramsMap.put("userDetailList", request.getUserDetailList());
        if(StringUtils.isNotEmpty(request.getBxInvoice())){
            paramsMap.put("bxInvoice", request.getBxInvoice());
        }
        paramsMap.put("fromStation", request.getFromStation());
        paramsMap.put("fromTime", request.getFromTime());

//        if(StringUtils.isNotEmpty(request.getLinkAddress())){
//            paramsMap.put("linkAddress", request.getLinkAddress());
//        }
//        if(StringUtils.isNotEmpty(request.getLinkMail())){
//            paramsMap.put("linkMail", request.getLinkMail());
//        }
//        if(StringUtils.isNotEmpty(request.getLinkName())){
//            paramsMap.put("linkName", request.getLinkName());
//        }
//        if(StringUtils.isNotEmpty(request.getLinkPhone())){
//            paramsMap.put("linkPhone", request.getLinkPhone());
//        }
        paramsMap.put("seatType", request.getSeatType());
        paramsMap.put("smsNotify", request.getSmsNotify());
        paramsMap.put("sumAmount", request.getSumAmount());
        paramsMap.put("ticketPrice", request.getTicketPrice());
        paramsMap.put("trainCode", request.getTrainCode());
        paramsMap.put("travelTime", request.getTravelTime());
        if(StringUtils.isNotEmpty(request.getWzExt())){
            paramsMap.put("wzExt", request.getWzExt());
        }

        String paramsString = SignUtils.buildParams(paramsMap);
        logger.info("-----------> createOrder paramas:" + JSON.toJSONString(paramsMap));
        return SignUtils.doKuyouSign(paramsString, signKey);
    }


    public static String signChargingRefund(ChargingRefundRequest request, String signKey) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("trainPrice", request.getTrainPrice());
        paramsMap.put("ticketTime", request.getTicketTime());

        String paramsString = SignUtils.buildParams(paramsMap);
        return SignUtils.doKuyouSign(paramsString, signKey);
    }
}
