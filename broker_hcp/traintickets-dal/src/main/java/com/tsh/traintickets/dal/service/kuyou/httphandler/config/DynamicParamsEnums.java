package com.tsh.traintickets.dal.service.kuyou.httphandler.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Administrator on 2016/12/15 015.
 */
public enum DynamicParamsEnums {
    kuyou_url,
    kuyou_terminal,
    kuyou_merchantId,
    kuyou_version,
    kuyou_hmac,
    kuyou_orderCallbackUrl,
    kuyou_refundCallbackUrl,
    kuyou_signKey,
    kuyou_vasDealOrderCallBackUrl,
    kuyou_vasRefundTicketCallBackUrl,
    isValidateSign,
    createOrderFailure,//出票失败
    refundTicketFailure //退票失败

}
