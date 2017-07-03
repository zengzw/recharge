package com.tsh.broker.convertor;

/**
 * PayResultConvetor
 *
 * @author dengjd
 * @date 2016/10/9
 */
public class PayResultConvetor {

    /**
     * 易赛结果转换器
     * @param payResult
     * 0、1、2 充值中
     * 4 充值成功
     * 5 充值失败
     * @return
     */
    public static Integer convertNotifyStatus(Integer payResult){
        Integer payStatus;
        if(payResult == 0 || payResult == 1 || payResult == 2){
            payStatus = 70001;
        }else if(payResult == 4){
            payStatus = 70002;
        }else if(payResult == 5){
            payStatus = 70003;
        }else {
            payStatus = 70004;
        }

        return payStatus;
    }


    /**
     * 欧飞通知结果转换器
     * 欧飞结果状态码： 1充值成功，0充值中，9充值失败，-1找不到此订单
     * @param status
     * @return
     */
    public static Integer convertPayResult(Integer status){
        Integer payResult;
        if(0 == status){
            payResult = 70001;
        }else if(1  == status){
            payResult = 70002;
        }else if (9 == status){
            payResult = 70003;
        }else {
            payResult = 70004;
        }

        return payResult;
    }
}
