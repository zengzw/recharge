/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.phone.util.StringUtils;
import com.tsh.vas.commoms.enums.EnumOrderType;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.phone.commons.PhoneRefundAmountEnum;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.phone.service.PhoneRefundAmountService;
import com.tsh.vas.sdm.service.charge.ChargeService;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;
import com.tsh.vas.trainticket.service.HcpRefundAmountService;
import com.tsh.vas.vo.QueryOrderChangeVO;

/**
 *  订单查询
 *  
 * @author zengzw
 * @date 2017年4月14日
 */
@Service
public class ChangeMulticOrderService {

    @Autowired
    PhoneOrderInfoService phoneOrderInfoService;

    @Autowired
    PhoneRefundAmountService phoneRefundAmountService;

    @Autowired
    HcpOrderInfoService hcpOrderInfoService;

    @Autowired
    HcpRefundAmountService hcpRefundAmountService;

    @Autowired
    ChargeService chargeService;


    
    /**
     * 对外查询接口
     * 
     * @param orderCode
     * @param status
     * @return
     */
    public QueryOrderChangeVO queryByOrderCode(String orderCode,Integer status){
        if(StringUtils.isEmpty(orderCode) || status == null){
            throw new BusinessRuntimeException("", "确实参数");
        }

        if(status.intValue() == EnumOrderType.DJDF.getCode()){
            return new PhoneChageOrder().getOrderByOrderCode(orderCode);
        }
        else if(status.intValue() == EnumOrderType.HCP.getCode()){
            return new HCPChageOrder().getOrderByOrderCode(orderCode);
        }
        else if(status.intValue() == EnumOrderType.MPCZ.getCode()){
            return new PhoneChageOrder().getOrderByOrderCode(orderCode);
        }

        return null;
    }


    abstract class ChangePhoneOrder <T>{

        public abstract QueryOrderChangeVO getOrderByOrderCode(String orderCode);

        public  abstract QueryOrderChangeVO parse(T object);
    }


    /**
     * 话费订单查询
     *
     * @author zengzw
     * @date 2017年4月14日
     */
    class PhoneChageOrder extends ChangePhoneOrder<PhoneOrderInfoPo>{

        @Override
        public  QueryOrderChangeVO getOrderByOrderCode(String orderCode) {
            Result result = new Result();
            PhoneOrderInfoPo phoneOrderInfoPo = phoneOrderInfoService.queryByOrderCode(result, orderCode).getData();
            QueryOrderChangeVO queryOrderChangeVO =  this.parse(phoneOrderInfoPo);
            return queryOrderChangeVO;
        }

        @Override
        public  QueryOrderChangeVO parse(PhoneOrderInfoPo object) {
            QueryOrderChangeVO result = new QueryOrderChangeVO();
            if(object == null){
                return result;
            }

            result.setOrderId(object.getId().toString());
            result.setOrderNo(object.getPhoneOrderCode());
            result.setOrderStatus(EnumOrderInfoPayStatus.getEnume(object.getPayStatus()).getDesc());
            result.setOrderType("花费充值");
            result.setPayBalance(object.getRealAmount().toString());

            //设置退款状态
            if(object.getPayStatus().intValue() == EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue()){
                Result selectdResult = new Result();
                PhoneRefundAmountPo phoneRefundAmountPo = phoneRefundAmountService.queryByOrderCode(selectdResult, object.getPhoneOrderCode()).getData();
                if(phoneRefundAmountPo != null){
                    result.setRefundStatus(PhoneRefundAmountEnum.getEnume(phoneRefundAmountPo.getStatus()).getClientDesc());
                }
            }
            return result;

        }

    }



    /**
     * 水电煤订单查询
     *
     * @author zengzw
     * @date 2017年4月14日
     */
    class SdmChageOrder extends ChangePhoneOrder<ChargeInfo>{

        @Override
        public  QueryOrderChangeVO getOrderByOrderCode(String orderCode) {
            Result result = new Result();
            ChargeInfo chargeInfo = chargeService.queryByChargeCode(result, orderCode).getData();
            QueryOrderChangeVO queryOrderChangeVO =  this.parse(chargeInfo);
            return queryOrderChangeVO;
        }

        @Override
        public  QueryOrderChangeVO parse(ChargeInfo object) {
            QueryOrderChangeVO result = new QueryOrderChangeVO();
            if(object == null){
                return result;
            }

            result.setOrderId(object.getId().toString());
            result.setOrderNo(object.getChargeCode());
            result.setOrderStatus(EnumOrderInfoPayStatus.getEnume(object.getPayStatus()).getDesc());
            result.setOrderType("交电费");
            result.setPayBalance(object.getRealAmount().toString());
            result.setRefundStatus(ChargeRefundStatus.getEnume (object.getRefundStatus ()).getClientDesc ());
            return result;

        }

    }
    
    /**
     * 火车票订单查询
     *
     * @author zengzw
     * @date 2017年4月14日
     */
    class HCPChageOrder extends ChangePhoneOrder<HcpOrderInfoPo>{

        @Override
        public  QueryOrderChangeVO getOrderByOrderCode(String orderCode) {
            Result result = new Result();
            HcpOrderInfoPo orderInfoPo = hcpOrderInfoService.queryByOrderCode(result, orderCode).getData();
            QueryOrderChangeVO queryOrderChangeVO =  this.parse(orderInfoPo);
            return queryOrderChangeVO;
        }

        @Override
        public  QueryOrderChangeVO parse(HcpOrderInfoPo object) {
            QueryOrderChangeVO result = new QueryOrderChangeVO();
            if(object == null){
                return result;
            }

            result.setOrderId(object.getId().toString());
            result.setOrderNo(object.getHcpOrderCode());
            result.setOrderStatus(EnumOrderInfoPayStatus.getEnume(object.getPayStatus()).getDesc());
            result.setOrderType("火车票");
            result.setPayBalance(object.getRealAmount().toString());

            //设置退款状态
            if(object.getPayStatus().intValue() == EnumOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue()
                    || object.getPayStatus().intValue() == EnumOrderInfoPayStatus.TICKET_FAIL.getCode().intValue()
                    || object.getPayStatus().intValue() == EnumOrderInfoPayStatus.PAY_EXCEPTION.getCode().intValue()){
                Result selectdResult = new Result();
                HcpRefundAmountPo refundAmountPo = hcpRefundAmountService.queryByOrderCode(selectdResult, object.getHcpOrderCode()).getData();
                if(refundAmountPo != null){
                    result.setRefundStatus(EnumRefundOrderStatus.getEnume(refundAmountPo.getStatus()).getClientDesc());
                }
            }

            return result;

        }

    }
}
