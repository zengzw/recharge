package com.tsh.broker.execute.sdm;


import java.util.List;

import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.GenerateOrderNoRequest;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;

/**
 * SdmBrokerExecute
 *
 * @author dengjd
 * @date 2016/9/28
 */
@SuppressWarnings("all")
public interface SdmExecute {

    /**
     * 缴费单位信息查询接口
     * @param payUnitRequest
     * @return
     */
    public ResponseWrapper<List<PayUnit>> queryPayUnit(PayUnitRequest payUnitRequest) throws Exception;

    /**
     * 充值接口
     * @param rechargeRequest
     * @return
     */
    public ResponseWrapper recharge(RechargeRequest rechargeRequest) throws Exception;


    /**
     * 充值结果查询接口
     * @param rechargeResultRequest
     * @return
     */
    public ResponseWrapper<Integer> queryRechargeResult(RechargeResultRequest rechargeResultRequest) throws Exception;


    /**
     * 充值结果通知接口
     * @param resultNotifyRequest
     * @return
     */
    public ResponseWrapper resultNotify(ResultNotifyRequest resultNotifyRequest) throws Exception;


    /**
     * 查询缴费账单接口
     * @param paymentBillRequest
     * @return
     */
    public ResponseWrapper<List<PaymentBill>> queryPaymentBill(PaymentBillRequest paymentBillRequest) throws Exception;
    /**
     * 多账单查询接口
     * @author Administrator <br>
     * @Date 2016年11月14日<br>
     * @param paymentBillRequest
     * @return
     * @throws Exception
     */
    public ResponseWrapper<List<PaymentBill>> queryPaymentBillMore(PaymentBillRequest paymentBillRequest) throws Exception;
    /**
     * 生成订单编号接口
     * @param generateOrderNoRequest
     * @return
     */
    public ResponseWrapper<String> generateOrderNo(GenerateOrderNoRequest generateOrderNoRequest);

}
