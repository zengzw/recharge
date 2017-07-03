package com.tsh.vas.sdm.metaq;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Result;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.share.metaq.vo.taskstatus.TaskResult;
import com.tsh.share.metaq.vo.taskstatus.TaskStatusType;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.ChargeRefund;
import com.tsh.vas.sdm.service.charge.PayChargeService;

/**
 * Created by Iritchie.ren on 2016/10/20.
 */
public class RefundChargeDataMessageListener extends DefaultMessageListener<String> {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private PayChargeService payChargeService;

    @Override
    public void onReceiveMessages(MetaqMessage<String> msg) {
        logger.info ("------------------------退款成功后消息回调：" + JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("------------------------退款成功后消息回调：" + JSONObject.toJSONString (taskResult));
        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.payChargeService.addLog (result, mqBizOrderPay.getBizOrderNo (), "RefundChargeDataMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户退款后回调");
        } catch (Exception e) {
            logger.error ("记录账户退款后回调失败",e);
            return;
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Doing.getCode ()) {
            logger.error ("修改退款状态退款中：" + mqBizOrderPay.getBizOrderNo ());
            return;
        }

        ChargeInfo chargeInfo;
        try {
            ChargeRefund chargeRefund = this.payChargeService.queryRefundByChargeCode (result, mqBizOrderPay.getBizOrderNo ()).getData ();
            chargeInfo = this.payChargeService.queryByChargeCode (result, chargeRefund.getChargeCode ()).getData ();
        } catch (Exception e) {
            logger.error ("修改退款状态退款中,订单不存在：" + mqBizOrderPay.getBizOrderNo (),e);
            return;
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Fail.getCode ()) {
            try {
                this.payChargeService.updateChargeRefundStatus (result, chargeInfo.getChargeCode (), ChargeRefundStatus.REFUND_FAIL.getCode ());
            } catch (Exception e) {
                logger.error ("修改退款状态为退款失败，异常",e);
                return;
            }
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
            try {
                //订单退款状态，已退款
                this.payChargeService.updateChargeRefundStatus (result, chargeInfo.getChargeCode (), ChargeRefundStatus.REFUND.getCode ());
            } catch (Exception e) {
                logger.error ("修改退款状态为退款成功,异常",e);
                return;
            }
        }
    }
}
