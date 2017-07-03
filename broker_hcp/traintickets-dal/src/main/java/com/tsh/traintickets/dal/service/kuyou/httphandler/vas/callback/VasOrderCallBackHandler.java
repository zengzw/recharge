package com.tsh.traintickets.dal.service.kuyou.httphandler.vas.callback;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.SignUtils;
import com.tsh.traintickets.bo.kuyou.ordercallback.OrderCallBackBO;
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24 024.
 */
@Service("orderCallBackHandler")
public class VasOrderCallBackHandler implements TicketHttpHandler{

    private static Logger logger = LogManager.getLogger(VasOrderCallBackHandler.class);

    @Override
    public String doHandler(Object request) {

        Map<String, String> params = new LinkedHashMap();
        if(null == request){
            params.put("status", TicketsConstants.FAILURE);
            params.put("failReason", "供应商返回结果为空");
            logger.info("------------------> call vas orderCallBack request: supplier return null" );

        } else {
            OrderCallBackBO callBackBO = (OrderCallBackBO) request;
            params = this.setParams(params, callBackBO);
            String signValue = SignUtils.buildParams(params);
            params.put("signKey", SignUtils.doKuyouSign(signValue, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey)));
            logger.info("------------------> call vas orderCallBack request:" + JSON.toJSONString(callBackBO));
        }

        String reponse = null;
        String vasDealOrderCallBackUrl = DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_vasDealOrderCallBackUrl);
        if(StringUtils.isNotEmpty(vasDealOrderCallBackUrl) &&
                !"null".equals(vasDealOrderCallBackUrl)){

            reponse = HttpXmlClient.post(vasDealOrderCallBackUrl, params);
        }
        logger.info("------------------> call vas orderCallBack response: " + reponse);
        return reponse;
    }

    private Map<String, String> setParams(Map<String, String> params, OrderCallBackBO callBackBO){
        params.put("merchantOrderId", callBackBO.getMerchantOrderId());//商户订单Id
        params.put("tradeNo", callBackBO.getTradeNo());//支付流水号
        params.put("status", callBackBO.getStatus());//处理结果
        params.put("failReason", callBackBO.getFailReason());//失败原因
        params.put("amount", callBackBO.getAmount());//支付金额
        params.put("refundAmount", callBackBO.getRefundAmount());//退款金额
        params.put("refundType", callBackBO.getRefundType());//退款类型
        params.put("orderId", callBackBO.getOrderId());//19旅行订单Id
        params.put("outTicketBillno", callBackBO.getOutTicketBillno());//12306订单Id
        params.put("outTicketTime", callBackBO.getOutTicketTime());//出票成功时间
        params.put("bxPayMoney", callBackBO.getBxPayMoney());//保险金额

        params.put("idType", callBackBO.getIdType());//	证件类型
        params.put("ticketType", callBackBO.getTicketType());//	车票类型
        params.put("userId", callBackBO.getUserId());//	证件号码
        params.put("userName", callBackBO.getUserName());//	姓名
        params.put("trainBox", callBackBO.getTrainBox());//	车厢号
        params.put("seatNo", callBackBO.getSeatNo());//	座位号
        params.put("seatType", callBackBO.getSeatType());//	座位类型

        // 测试需要
        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.createOrderFailure))){
            params.put("status", TicketsConstants.FAILURE);//处理结果
            params.put("failReason", "broker主动出票失败");//失败原因
        }
        return params;
    }
}
