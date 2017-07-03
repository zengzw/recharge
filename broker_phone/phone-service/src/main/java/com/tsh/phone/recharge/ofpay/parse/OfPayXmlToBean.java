/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.ofpay.parse;

import com.tsh.phone.recharge.ofpay.xmlbean.OrderInfoElement;
import com.tsh.phone.util.JaxbUtil;

/**
 *
 * @author zengzw
 * @date 2016年7月22日
 */
public class OfPayXmlToBean {

    /**
     * 格式化充值返回内容
     * 
     * @param result
     * @return
     */
    public static OrderInfoElement parseOrderInfo(String result){
        OrderInfoElement orderInfo = JaxbUtil.converyToJavaBean(result, OrderInfoElement.class);
        return orderInfo;
    }

}
