package com.tsh.vas.trainticket.mq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Result;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.share.metaq.vo.taskstatus.TaskResult;
import com.tsh.share.metaq.vo.taskstatus.TaskStatusType;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.service.SmsService;

import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.constants.SMSMessageTemplateConstants;
import com.tsh.vas.trainticket.service.APPAPIService;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;
import com.tsh.vas.trainticket.service.HcpRefundAmountService;

/**
 * 退款消息监听
 * 
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public class HCPRefundChargeDataMessageListener extends DefaultMessageListener<String> {

    private Logger logger = LoggerFactory.getLogger(HCPRefundChargeDataMessageListener.class);

    @Autowired
    private APPAPIService appService;

    @Autowired
    private HcpOrderInfoService orderInfoService;

    @Autowired
    private HcpRefundAmountService refundService;
    
    @Autowired
    SmsService smsService;
    

    @Override
    public void onReceiveMessages(MetaqMessage<String> msg) {
        logger.info ("======================退款成功后消息回调：" + JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("======================退款成功后消息回调：" + JSONObject.toJSONString (taskResult));
        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.appService.addLog (result, mqBizOrderPay.getBizOrderNo (), "HCPRefundChargeDataMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户退款后回调");
        } catch (Exception e) {
            logger.error ("--------------记录账户退款后回调失败",e);
            return;
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Doing.getCode ()) {
            logger.error ("--------------订单号：{}，退款状态退款中：" , mqBizOrderPay.getBizOrderNo ());
            return;
        }

        try {

            HcpRefundAmountPo refundInfo = refundService.queryByReturnOrderCode(result, mqBizOrderPay.getBizOrderNo()).getData();
            if(refundInfo == null){
                logger.info("--------------没有找到退款单！");
                return;
            }

            //位了避免重复通知的情况或者延迟（主动查单，状态改变之后）的情况状态处理。
            if(refundInfo.getStatus().intValue() == EnumRefundOrderStatus.REFUND_FAIL.getCode().intValue()
                    || refundInfo.getStatus().intValue()  == EnumRefundOrderStatus.REFUND_SUCCESS.getCode().intValue() ){
                logger.info("--------------退款单当前的状态为：{},不合理的状态改变！",refundInfo.getStatus());
                return;
            }
            
            
            HcpOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, refundInfo.getHcpOrderCode()).getData();
            if(orderInfo ==null){
                logger.info("--------------没有找到订单,orderId:{}",refundInfo.getHcpOrderCode());
                return;
            }

            //退款失败，修改退款单状态，增加次数
            if (taskResult.getTaskStatus () == TaskStatusType.Fail.getCode ()) {
                logger.info("--------------单号：{}退款失败【4】",refundInfo.getHcpOrderCode());
                //退款状态为，退款失败【4】
                refundService.updateRefundStatus(result, refundInfo.getRefundAmountCode(), EnumRefundOrderStatus.REFUND_FAIL.getCode());

                //短信提示
                String message = String.format(SMSMessageTemplateConstants.RETURN_AMONUT_FAIL,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode());
                smsService.sendSms(orderInfo.getMemberMobile(), message);
            
            }else if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
                logger.info("--------------单号：{}，退款成功【13】",refundInfo.getHcpOrderCode());
                //订单退款状态，退款成功[13]
                refundService.updateRefundStatus(result, refundInfo.getRefundAmountCode(), EnumRefundOrderStatus.REFUND_SUCCESS.getCode());
                
                String message = String.format(SMSMessageTemplateConstants.RETURN_AMONUT_SUCCESS,orderInfo.getMemberMobile(),orderInfo.getHcpOrderCode(),refundInfo.getRealAmount());
                smsService.sendSms(orderInfo.getMemberMobile(), message);
            }

        } catch (Exception e) {
            logger.error ("--------------修改退款状态退款中,订单不存在：" + mqBizOrderPay.getBizOrderNo (),e);
        }


    }
}
