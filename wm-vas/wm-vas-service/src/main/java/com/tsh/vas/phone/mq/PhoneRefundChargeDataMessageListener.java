package com.tsh.vas.phone.mq;

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
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.phone.constants.SMSPhoneMessageTemplateConstants;
import com.tsh.vas.phone.enums.EnumPhoneRefundOrderStatus;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.phone.service.PhoneRefundAmountService;
import com.tsh.vas.service.SmsService;

/**
 * 退款消息监听
 * 
 *
 * @author zengzw
 * @date 2017年3月14日
 */
public class PhoneRefundChargeDataMessageListener extends DefaultMessageListener<String> {

    private Logger logger = LoggerFactory.getLogger(PhoneRefundChargeDataMessageListener.class);

    @Autowired
    private APIAppPhoneService appService;

    @Autowired
    private PhoneOrderInfoService orderInfoService;

    @Autowired
    private PhoneRefundAmountService refundService;

    @Autowired
    SmsService smsService;


    @Override
    public void onReceiveMessages(MetaqMessage<String> msg) {
        logger.info ("#PhoneRefund=======退款成功后消息回调：" + JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("#PhoneRefund=======退款成功后消息回调：" + JSONObject.toJSONString (taskResult));
        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.appService.addLog (result, mqBizOrderPay.getBizOrderNo (), "PhoneRefundChargeDataMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户退款后回调");
        } catch (Exception e) {
            logger.error ("#PhoneRefund-----记录账户退款后回调失败",e);
            return;
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Doing.getCode ()) {
            logger.error ("#PhoneRefund------订单号：{}，退款状态退款中：" , mqBizOrderPay.getBizOrderNo ());
            return;
        }

        try {

            PhoneRefundAmountPo refundInfo = refundService.queryByReturnOrderCode(result, mqBizOrderPay.getBizOrderNo()).getData();
            if(refundInfo == null){
                logger.info("#PhoneRefund-------没有找到退款单！");
                return;
            }

            //位了避免重复通知的情况或者延迟（主动查单，状态改变之后）的情况状态处理。
            if(refundInfo.getStatus().intValue() == EnumPhoneRefundOrderStatus.REFUND_FAIL.getCode().intValue()
                    || refundInfo.getStatus().intValue()  == EnumPhoneRefundOrderStatus.REFUND_SUCCESS.getCode().intValue() ){
                logger.info("#PhoneRefund-------退款单当前的状态为：{},不合理的状态改变！",refundInfo.getStatus());
                return;
            }


            PhoneOrderInfoPo orderInfo = orderInfoService.queryByOrderCode(result, refundInfo.getPhoneOrderCode()).getData();
            if(orderInfo ==null){
                logger.info("#PhoneRefund-----没有找到订单,orderId:{}",refundInfo.getPhoneOrderCode());
                return;
            }

            //退款失败，修改退款单状态，增加次数
            if (taskResult.getTaskStatus () == TaskStatusType.Fail.getCode ()) {
                logger.info("#PhoneRefund-------单号：{}退款失败【4】",refundInfo.getPhoneOrderCode());
                //退款状态为，退款失败【4】
                refundService.updateRefundStatus(result, refundInfo.getRefundAmountCode(), EnumPhoneRefundOrderStatus.REFUND_FAIL.getCode());

                //短信提示
                String message = String.format(SMSPhoneMessageTemplateConstants.RETURN_AMONUT_FAIL,
                        orderInfo.getMemberMobile(),orderInfo.getPhoneOrderCode(),refundInfo.getRealAmount());
                smsService.sendSms(orderInfo.getMemberMobile(), message);

            }else if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
                logger.info("#PhoneRefund--------单号：{}，退款成功【13】",refundInfo.getPhoneOrderCode());
                //订单退款状态，退款成功[13]
                refundService.updateRefundStatus(result, refundInfo.getRefundAmountCode(), EnumPhoneRefundOrderStatus.REFUND_SUCCESS.getCode());
            }

        } catch (Exception e) {
            logger.error ("#PhoneRefund------修改退款状态退款中,订单不存在：" + mqBizOrderPay.getBizOrderNo (),e);
        }


    }
}
