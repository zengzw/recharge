package com.tsh.vas.trainticket.mq;

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
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.share.metaq.vo.taskstatus.TaskResult;
import com.tsh.share.metaq.vo.taskstatus.TaskStatusType;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.service.SmsService;

import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.constants.SMSMessageTemplateConstants;
import com.tsh.vas.trainticket.service.APPAPIService;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;


/**
 * 账户支付回调
 *
 * @author zengzw
 * @date 2016年12月5日
 */
@SuppressWarnings("rawtypes")
@Component
public class HCPPayChargeCallbackMessageListener extends DefaultMessageListener {

    private Logger logger = LoggerFactory.getLogger(HCPPayChargeCallbackMessageListener.class);

    @Autowired
    private APPAPIService apiService;

    @Autowired
    HcpOrderInfoService orderInfoService;

    @Autowired
    SmsService smsService;
    
    
    @Override
    public void onReceiveMessages(MetaqMessage msg) {
        logger.info ("------------------------支付完后回调：{}",JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("------------------------支付完后回调：{}",JSONObject.toJSONString (taskResult));
        
        if(taskResult == null){
            logger.info("----------------支付回调，接收到的消息位空");
            return;
        }
        
        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.apiService.addLog (result, mqBizOrderPay.getBizOrderNo (), "PayChargeCallbackMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户支付完后回调");
        } catch (Exception e) {
            logger.error ("===>记录账户支付完后回调失败",e);
            return;
        }

        //判断订单状态是否正常的转换
        String orderNo = mqBizOrderPay.getBizOrderNo();
        HcpOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, orderNo).getData();
        if(orderInfo.getPayStatus().intValue() != EnumOrderInfoPayStatus.PAIDING.getCode().intValue()){
            logger.info("====================订单号：{}，的状态为:{},不合理的状态转换.",orderInfo.getHcpOrderCode(),orderInfo.getPayStatus());
            return;
        }

        String debugger = TshDiamondClient.getInstance().getConfig("hcpDebugger");
        logger.info("====================hcpDebugger：{}", debugger);

        if("1".equals(debugger)){
            // debugger 什么都不判断，直接返回成功，屏蔽支付失败结果
            logger.info("====================屏蔽支付失败结果");
           
            taskResult.setTaskStatus(TaskStatusType.Success.getCode ());

        } else {
            //返回失败,订单状态，交易关闭 Doing(1, "处理中"),Success(2, "交易成功"),Fail(3, "交易失败");
            if (taskResult.getTaskStatus() == TaskStatusType.Fail.getCode()) {
                try {
                    logger.info("===>支付完成后,返回失败,订单状态【6】.{}", taskResult.getTaskStatus());
                    this.apiService.updateOrderStatus(result, orderNo, EnumOrderInfoPayStatus.PAY_FAIL.getCode());

                    //短信提示
                    String message = String.format(SMSMessageTemplateConstants.PAY_FAIL, orderInfo.getMemberMobile(), orderInfo.getHcpOrderCode());
                    smsService.sendSms(orderInfo.getMemberMobile(), message);
                } catch (Exception e) {
                    logger.error("===>支付完成后,修改订单状态失败", e);
                    return;
                }
                return;
            }

            if (taskResult.getTaskStatus() == TaskStatusType.Doing.getCode()) {
                logger.warn("===>订单：{}支付还在进行中,{}", orderNo, taskResult.getTaskStatus());
                return;
            }
        }

        //返回成功
        if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
            try {
                //订单状态更改为扣款成功
                logger.info ("===>支付完成后,订单状态更改为扣款成功。状态位:【3】");
                this.apiService.updateOrderStatus (result,orderNo, EnumOrderInfoPayStatus.PAY_SUCCESS.getCode ());
            } catch (Exception e) {
                logger.error ("===>支付完成后,修改订单状态失败",e);
                return;
            }

            // 账号支付成功后回调，
            //1新增支付信息，2,结算记录生成  3:购票
            try {
                logger.info("===>进行记录支付信息，结算记录生成，购票接口......");
                this.apiService.paySuccessCallBack(result, mqBizOrderPay);
            } catch (Exception e) {
                logger.error ("XXXXXXXXXXXXXXX支付完成后消息回调处理失败",e);
            }
        }
    }

    public static void main(String[] args) {
        
    }
}
