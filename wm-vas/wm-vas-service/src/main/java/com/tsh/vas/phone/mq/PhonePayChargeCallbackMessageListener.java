package com.tsh.vas.phone.mq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Result;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.tsh.dubbo.hlq.api.CouponApi;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.share.metaq.vo.taskstatus.TaskResult;
import com.tsh.share.metaq.vo.taskstatus.TaskStatusType;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;
import com.tsh.vas.phone.constants.SMSPhoneMessageTemplateConstants;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.phone.service.PhoneUseCouponRecordService;
import com.tsh.vas.service.CouponService;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;


/**
 * 账户支付回调
 *
 * @author zengzw
 * @date 2017年3月15日
 */
@SuppressWarnings("rawtypes")
@Component
public class PhonePayChargeCallbackMessageListener extends DefaultMessageListener {

    private Logger logger = LoggerFactory.getLogger(PhonePayChargeCallbackMessageListener.class);

    @Autowired
    private APIAppPhoneService apiService;

    @Autowired
    PhoneOrderInfoService orderInfoService;
    
    @Autowired
    SmsService smsService;


    @Override
    public void onReceiveMessages(MetaqMessage msg) {
        logger.info ("#----------->【7】支付完后回调：{}",JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("#----------->【8】支付完后回调：{}",JSONObject.toJSONString (taskResult));

        if(taskResult == null){
            logger.info("#---------->支付回调，接收到的消息位空");
            return;
        }

        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.apiService.addLog (result, mqBizOrderPay.getBizOrderNo (), "PhonePayChargeCallbackMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户支付完后回调");
        } catch (Exception e) {
            logger.error ("#----->记录账户支付完后回调失败",e);
            return;
        }

        //判断订单状态是否正常的转换
        String orderNo = mqBizOrderPay.getBizOrderNo();
        PhoneOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, orderNo).getData();
        if(orderInfo.getPayStatus().intValue() != EnumOrderInfoPayStatus.PAIDING.getCode().intValue()){
            logger.info("#------->订单号：{}，的状态为:{},不合理的状态转换.",orderInfo.getPhoneOrderCode(),orderInfo.getPayStatus());
            return;
        }
        
        

        //返回失败,订单状态，交易关闭 Doing(1, "处理中"),Success(2, "交易成功"),Fail(3, "交易失败");
        if (taskResult.getTaskStatus() == TaskStatusType.Fail.getCode()) {
            try {
                logger.info("#------>【9】支付完成后,返回失败,订单编号:{},订单状态为：【6】.getTaskStatus：{}", orderInfo.getPhoneOrderCode(), taskResult.getTaskStatus());
                this.apiService.updateOrderStatus(result, orderNo, EnumOrderInfoPayStatus.PAY_FAIL.getCode());


                //修改参与活动数据状态
                this.apiService.updateActivityOrderStatus(orderNo, EnumActivetyAuditStatus.NOT_PASS.getType());

                //短信提示 
                String message = String.format(SMSPhoneMessageTemplateConstants.PAY_FAIL, orderInfo.getRechargePhone(), orderInfo.getPhoneOrderCode());
                smsService.sendSms(orderInfo.getRechargePhone(), message);
                
            } catch (Exception e) {
                logger.error("#----->支付完成后,修改订单状态失败", e);
                return;
            }

            return;
        }

        if (taskResult.getTaskStatus() == TaskStatusType.Doing.getCode()) {
            logger.warn("#----->订单：{}支付还在进行中,{}", orderNo, taskResult.getTaskStatus());
            return;
        }


        //返回成功
        if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
            //订单状态更改为扣款成功
            logger.info ("#----->【10】支付完成后,订单状态更改为扣款成功。状态位:【3】,orderCode:{}", orderInfo.getPhoneOrderCode());
            this.apiService.updateOrderStatus (result,orderNo, EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode ());


            // 账号支付成功后回调，
            //1记录支付信息，2：获取可用注册服务商，3:手机充值（轮询）
            try {
                logger.info("#------>进行记录支付信息，充值接口......");
                this.apiService.paySuccessCallBack(result, mqBizOrderPay);

            } catch (Exception e) {
                logger.error ("#----->支付完成后消息回调处理失败",e);
            }
        }
    }


}
