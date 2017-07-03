/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.constants;

/**
 * 花车票URL常量
 * 
 * @author zengzw
 * @date 2016年11月22日
 */
public class UrlConstants {

    private UrlConstants(){}

    /**
     * 查询车票URL
     */
    public static final String LIST_TRAING_TICKETS = "queryTickets.do";


    /**
     * 下单操作
     */
    public static final String CREATE_ORDER = "createOrder.do";


    /**
     * 订单查询
     */
    public static final String QUERY_ORDER = "queryOrder.do";


    /**
     * 查询途经站点
     */
    public static final String QUERY_SUBWAY_STATION = "querySubwayStation.do";



    /**
     * 查询余票
     */
    public static final String QUERY_TICKET_NUM = "queryTicketNum.do";


    /**
     * 计算可退金额
     */
    public static final String CHARGINT_REFUND_AMOUNT = "chargingRefund.do";

    /**
     * 验证用户信息
     */
    public static final String VALIDATE_USER_INFO = "verifyUsers.do";


    /**
     * 生成订单号
     */
    public static final String GENERATE_ORDER_NO = "getOrderNo.do";


    /**
     * 退票
     */
    public static final String RETURN_TICKEET = "refundTicket.do";


    /**
     * 查询订单状态
     */
    public static final String ACC_QUERY_ORDER_STATUS= "api/orderPay/queryOrderStatus.do";
    
        
    
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = "/views/hcp/detail.html";
    
    
    

}
