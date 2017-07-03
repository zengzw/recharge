package com.tsh.vas.sdm.autotask;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.sdm.service.charge.PayChargeService;

/**
 * Created by Iritchie.ren on 2016/10/22.
 */
@Component
public class RefundChargeInfoQuartz {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private PayChargeService payChargeService;

    public void autoRun() {
        //处理过期支付中的单，将单改成支付异常
        this.dealWithPaiding ();

        //处理过期缴费中的单，将单改成缴费异常
        this.dealWithCharging ();

        //处理交易失败的单，需要退款
        this.dealWithTradeFail ();
    }

    //处理卡在了支付中的单，将单改成支付异常
    private void dealWithPaiding() {//查询支付中的，3天还在支付中的单
        Result result = new Result ();
        //退款时间为3天
        Integer beforeTime = 3;
        List<ChargeInfo> chargeInfoPaidingList;
        try {
            chargeInfoPaidingList = this.payChargeService.findByPayStatusAndTime (result, ChargePayStatus.PAIDING.getCode (), beforeTime).getData ();
        } catch (Exception e) {
            logger.error ("自动任务将单改成支付异常失败：",e);
            return;
        }

        if (chargeInfoPaidingList == null) {
            logger.info ("++++++++++没有自动任务将单改成支付异常++++++++++++++");
            return;
        }

        for (ChargeInfo item : chargeInfoPaidingList) {
            try {
                this.payChargeService.updateChargeStatus (result, item.getChargeCode (), ChargePayStatus.PAY_FAIL.getCode ());
            } catch (Exception e) {
                logger.error ("自动任务将单改成支付异常失败：" + item.getChargeCode () + "；异常为：" + e.getMessage (),e);
            }
        }
    }

    //缴费中的单变成缴费异常
    private void dealWithCharging() {
        Result result = new Result ();
        Integer beforeTime = 3;
        List<ChargeInfo> chargeInfoChargingList;
        try {
            chargeInfoChargingList = this.payChargeService.findByPayStatusAndTime (result, ChargePayStatus.CHARGING.getCode (), beforeTime).getData ();
        } catch (Exception e) {
            logger.error ("自动任务查询充值中的订单失败：",e);
            return;
        }

        if (chargeInfoChargingList == null) {
            logger.info ("+++++++++++++++没有自动任务查询充值中的订单++++++++++++++++");
            return;
        }

        for (ChargeInfo item : chargeInfoChargingList) {
            try {
                logger.info("--->缴费异常单，进行确认结算操作platformNO:"+item.getOpenPlatformNo());

                //如果是新支付流程，进行确认分润操作
                boolean isOldBusiness = this.payChargeService.isOldBusiness(item.getCreateTime());
                if(!isOldBusiness){
                    result =  this.payChargeService.confirmSettle(result, item.getOpenPlatformNo(), true);
                }

                this.payChargeService.updateChargeStatus (result, item.getChargeCode (), ChargePayStatus.CHARGE_FAIL.getCode ());

            } catch (Exception e) {
                logger.error ("自动任务处理缴费中的订单失败：" + item.getChargeCode () + "；异常为：" + e.getMessage ());
            }
        }
    }

    //处理交易失败的单，需要退款!!!
    private void dealWithTradeFail() {
        //处理多少天的单，单位：天
        Result result = new Result ();
        //退款时间为3天
        Integer beforeTime = 3;
        List<ChargeInfo> chargeInfoTradeFailList;
        //查询所有符合自动退款的订单,查询交易失败
        try {
            chargeInfoTradeFailList = this.payChargeService.findByPayStatusAndTimeAndRefundStatus (result, ChargePayStatus.TRAD_FAIL.getCode (), ChargeRefundStatus.NORMAL_REFUND.getCode (), beforeTime).getData ();
        } catch (Exception e) {
            logger.error ("自动任务查询退款失败：",e);
            return;
        }

        if (chargeInfoTradeFailList == null) {
            logger.info ("+++++++++++++++没有自动任务查询退款的单++++++++++");
            return;
        }

        //订单退款
        for (ChargeInfo item : chargeInfoTradeFailList) {
            try {

                //版本上线时间。如果创建时间在版本上线时间，按照老的逻辑进行退款操作。否则，走新逻辑。
                boolean oldBusiness = this.payChargeService.isOldBusiness(item.getCreateTime());
                if(!oldBusiness){
                    logger.info("---交易失败，确认退款操作..........openplatformNo:"+item.getOpenPlatformNo());
                    //确认结算。
                    result =  this.payChargeService.confirmSettle(result, item.getOpenPlatformNo(), true);
                }

                //退款，收回分润
                this.payChargeService.refundCharge (result, item.getChargeCode (),oldBusiness);

            } catch (Exception e) {
                logger.error ("自动任务退款失败：" + item.getChargeCode () + "；异常为：" + e.getMessage (),e);
            }
        }
    }

    public static void main(String[] args) {
        Date date = DateUtil.str2Date("2016-11-22 15:35:59");
        Date date2 = DateUtil.str2Date("2016-11-20 23:59:59");

        System.out.println(DateUtil.before(date,date2));
    }
}
