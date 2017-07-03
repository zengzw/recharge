package com.tsh.vas.sdm.metaq;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Result;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.share.metaq.vo.taskstatus.TaskResult;
import com.tsh.share.metaq.vo.taskstatus.TaskStatusType;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.sdm.service.charge.PayChargeService;

/**
 * Created by Iritchie.ren on 2016/10/20.
 */
@SuppressWarnings("rawtypes")
@Component
public class PayChargeDataMessageListener extends DefaultMessageListener {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private PayChargeService payChargeService;

    @Override
    public void onReceiveMessages(MetaqMessage msg) {
        logger.info ("------------------------支付完后回调：" + JSONObject.toJSONString (msg));
        TaskResult taskResult = (TaskResult) SerializeUtil.unserialize (msg.getData ());
        logger.info ("------------------------支付完后回调：" + JSONObject.toJSONString (taskResult));
        JSONObject jsonObject = (JSONObject) taskResult.getData ();
        MQBizOrderPay mqBizOrderPay = JSONObject.toJavaObject (jsonObject, MQBizOrderPay.class);
        Result result = new Result ();

        try {
            this.payChargeService.addLog (result, mqBizOrderPay.getBizOrderNo (), "PayChargeDataMessageListener.onReceiveMessages", "", JSONObject.toJSONString (taskResult), new Date (), new Date (), "账户支付完后回调");
        } catch (Exception e) {
            logger.error ("记录账户支付完后回调失败",e);
            return;
        }

        //返回失败,订单状态，交易关闭 Doing(1, "处理中"),Success(2, "交易成功"),Fail(3, "交易失败");
        if (taskResult.getTaskStatus () == TaskStatusType.Fail.getCode ()) {
            try {
                logger.info ("支付完成后,返回失败,订单状态");
                this.payChargeService.updateChargeStatus (result, mqBizOrderPay.getBizOrderNo (), ChargePayStatus.TRAD_CLOSE.getCode ());
            } catch (Exception e) {
                logger.error ("支付完成后,修改订单状态失败",e);
                return;
            }
            return;
        }

        if (taskResult.getTaskStatus () == TaskStatusType.Doing.getCode ()) {
            logger.warn ("订单支付还在进行中");
            return;
        }

        //返回成功
        if (taskResult.getTaskStatus () == TaskStatusType.Success.getCode ()) {
            try {
                //订单状态更改为扣款成功
                logger.info ("支付完成后,订单状态更改为扣款成功");
                this.payChargeService.updateChargeStatus (result, mqBizOrderPay.getBizOrderNo (), ChargePayStatus.PAY_SUCCESS.getCode ());
            } catch (Exception e) {
                logger.error ("支付完成后,修改订单状态失败",e);
                return;
            }
            
            // 账号支付成功后回调，
            //1新增支付信息，2,结算记录生成  3:继续充值接口
            try {
                this.payChargeService.payBack (result, mqBizOrderPay);
            } catch (Exception e) {
                logger.error ("支付完成后消息回调处理失败",e);
            }
        }
    }
    
    public static void main(String[] args) {
     
        System.out.println(DateUtil.addDate(new Date(),-1).getTime());
    }
}
