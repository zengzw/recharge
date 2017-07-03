package com.tsh.phone.recharge.flpay.parse;

import com.dtds.platform.commons.utility.DateUtil;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.recharge.flpay.vo.supplier.response.CreateOrderResponse;
import com.tsh.phone.recharge.flpay.vo.supplier.response.QueryMobileResponse;
import com.tsh.phone.recharge.flpay.vo.supplier.response.QueryOrderResponse;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/12 012.
 */
public class FuluBeanParse {

    /**
     * 把供应商返回数据转换为bean
     * @param orderno
     * @param response
     * @return
     */
    public static OrderInfoVo parseOrder(String orderno, QueryOrderResponse response){
        if(response == null){
            return null;
        }
        OrderInfoVo orderInfo = new OrderInfoVo();
        orderInfo.setOrderNo(orderno);
        orderInfo.setSpOrderNo(response.getOrderId());
        orderInfo.setFinishMoney(response.getPurchasePrice());
        orderInfo.setOrderStatus(getQueryOrderStatus(response.getStatus()));

        return orderInfo;
    }

    /**
     *
     * @param mobile
     * @return
     */
    public static PhoneLocationVo parsePhoneLocation(QueryMobileResponse mobile){
        PhoneLocationVo phoneLocationVo = new PhoneLocationVo();
        phoneLocationVo.setProvinceName(mobile.getProvince());
        phoneLocationVo.setType(mobile.getSp());
        phoneLocationVo.setCityName(mobile.getCity());

        return phoneLocationVo;
    }

    /**
     *
     * @param orderno
     * @param result
     * @return
     */
    public static RechargeVo parseRechargeInfo(String orderno, CreateOrderResponse result){
        if(result == null){
            return null;
        }
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(orderno);
        rechargeVo.setSpOrderNo(result.getOrderId());
        rechargeVo.setOrderStauts(getOrderStatus(result.getStatus()));
        rechargeVo.setSellerPrice(Double.valueOf(result.getPurchasePrice()));
        rechargeVo.setSuccessTime(result.getCreateTime());
        return rechargeVo;

    }

    /**
     *
     * @param orderno
     * @param orderInfoVo
     * @return
     */
    public static RechargeVo parseRechargeInfoSecond(String orderno, OrderInfoVo orderInfoVo){
        if(orderInfoVo == null){
            return null;
        }
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(orderno);
        rechargeVo.setSpOrderNo(orderInfoVo.getSpOrderNo());
        rechargeVo.setOrderStauts(Configurations.OrderStatus.SUCCESS);
        rechargeVo.setSellerPrice(Double.valueOf(orderInfoVo.getFinishMoney()));
        rechargeVo.setSuccessTime(DateUtil.date2String(new Date()));
        return rechargeVo;

    }


    /**
     * 下单返回结果状态处理
     *
     * @param orderStatus
     * @return
     */
    private static String getOrderStatus(String orderStatus){
        //未处理,处理中,成功,失败,可疑
        if(orderStatus.equals("未处理")){
            return Configurations.OrderStatus.SUCCESS;
        }

        return Configurations.OrderStatus.FAILED;
    }

    /**
     * 订单查询结果状态处理
     *
     * @param orderStatus
     * @return
     */
    private static String getQueryOrderStatus(String orderStatus){
        //未处理,处理中,成功,失败,可疑
        if(orderStatus.equals("未处理")){
            return Configurations.OrderStatus.FAILED;
        }
        if(orderStatus.equals("处理中")){
            return Configurations.OrderStatus.NOT_CONFIRM;
        }
        if(orderStatus.equals("成功")){
            return Configurations.OrderStatus.SUCCESS;
        }
        if(orderStatus.equals("失败")){
            return Configurations.OrderStatus.FAILED;
        }
        if(orderStatus.equals("可疑")){
            return Configurations.OrderStatus.NOT_CONFIRM;
        }
        return Configurations.OrderStatus.FAILED;
    }

    /**
     * 获取订单失败结果
     * @param orderNo
     * @return
     */
    public static RechargeVo getFailOrder(String orderNo){
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(orderNo);
        rechargeVo.setOrderStauts(Configurations.OrderStatus.FAILED);
        return rechargeVo;
    }

    /**
     *
     * @param orderNo
     * @return
     */
    public static RechargeVo getSuccessOrder(String orderNo){
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setOrderNo(orderNo);
        rechargeVo.setOrderStauts(Configurations.OrderStatus.SUCCESS);
        return rechargeVo;
    }
}
