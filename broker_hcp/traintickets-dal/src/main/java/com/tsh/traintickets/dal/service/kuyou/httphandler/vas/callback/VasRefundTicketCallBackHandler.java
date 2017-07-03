package com.tsh.traintickets.dal.service.kuyou.httphandler.vas.callback;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.SignUtils;
import com.tsh.traintickets.bo.kuyou.refundcallback.RefundTicketCallBackBO;
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
@Service("refundTicketCallBackHandler")
public class VasRefundTicketCallBackHandler implements TicketHttpHandler{

    private static Logger logger = LogManager.getLogger(VasRefundTicketCallBackHandler.class);

    @Override
    public String doHandler(Object request) {
        Map<String, String> params = new LinkedHashMap();
        if(null == request){
            params.put("status", TicketsConstants.FAILURE);
            params.put("failReason", "供应商返回结果为空");
            logger.info("------------------> call vas refundTicketCallBack request: supplier return null" );
        } else {
            RefundTicketCallBackBO callBackBO = (RefundTicketCallBackBO) request;
            params = this.setParams(params, callBackBO);
            String signValue = SignUtils.buildParams(params);
            params.put("signKey", SignUtils.doKuyouSign(signValue,  DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey)));
            logger.info("------------------> call vas refundTicketCallBack request:" + JSON.toJSONString(callBackBO));
        }

        String reponse = null;
        String vasRefundTicketCallBackUrl = DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_vasRefundTicketCallBackUrl);
        if(StringUtils.isNotEmpty(vasRefundTicketCallBackUrl) &&
                !"null".equals(vasRefundTicketCallBackUrl)){

            reponse = HttpXmlClient.post(vasRefundTicketCallBackUrl, params);
        }
        logger.info("------------------> call vas refundTicketCallBack request:" + reponse);
        return reponse;
    }

    private Map<String, String> setParams(Map<String, String> params, RefundTicketCallBackBO callBackBO){
        params.put("requestId", callBackBO.getRequestId());//	合作商户退款流水号
        params.put("tripNo", callBackBO.getTripNo());//	19旅行退款流水号
        params.put("merchantOrderId", callBackBO.getMerchantOrderId());//	商户订单Id（协议参数）
        params.put("status", callBackBO.getStatus());//	处理结果
        params.put("refundTotalAmount", callBackBO.getRefundTotalAmount());//	退款金额
        params.put("refundType", callBackBO.getRefundType());//	退款类型
        params.put("orderId", callBackBO.getOrderId());//	19旅行订单Id

        params.put("idType", callBackBO.getIdType());//	证件类型
        params.put("ticketType", callBackBO.getTicketType());//	车票类型
        params.put("userId", callBackBO.getUserId());//	证件号码
        params.put("userName", callBackBO.getUserName());//	姓名
        params.put("failReason", callBackBO.getFailReason());//	失败原因

        // 测试需要
        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.refundTicketFailure))){
            params.put("status", TicketsConstants.FAILURE);//处理结果
            params.put("failReason", "broker主动退票失败");
        }

        return params;
    }
}
