package com.tsh.traintickets.web.controller.kuyou.query;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.utils.DateUtils;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.request.ChargingRefundRequest;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.math.BigDecimal;

/**
 * 计算火车票可退金额
 *
 * Created by Administrator on 2016/12/2 002.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class ChargingRefundController extends BaseController {

    @RequestMapping(value = "/chargingRefund.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse queryTickets(ChargingRefundRequest request) {
        Result result = super.getResult ();
        try{
            this.validateParam(request);
            result.setData(ResponseBuilder.buildSuccess(this.doCharging(request)));

        } catch (BizException be) {
            logger.error(be.getMessage(), be);
            result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
            this.error(result, be);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setData(ResponseBuilder.buildError(ResponseCode.SYSTEM_ERROR));
            this.error(result, e);
        } finally {
            this.send(result);
        }
        logger.info("-------------> chargingRefundReturn：" + JSON.toJSONString(result.getData()));
        return result.getData();
    }

    private String doCharging(ChargingRefundRequest request){
        long nowTime = System.currentTimeMillis();
        long startTime = DateUtils.parse(request.getTicketTime()).getTime();
        long temp = startTime - nowTime;
        long days15 = (long)3600*24*15*1000;
        long days2 = (long)3600*24*2*1000;
        long days1 = (long)3600*24*1*1000;
        long hour1 = (long)3600*1*1000;
        BigDecimal money = new BigDecimal(request.getTrainPrice());
        BigDecimal fee = null;
        if(temp > days15){
            logger.info("---------------------> calculate: 15天外");
            //开车时间360小时（15天）之前原价退票
            fee = new BigDecimal(0);
        }
        if(temp > days2 && temp <= days15){
            logger.info("---------------------> calculate: 2~15天");
            //开车时间前48小时至360（包含）小时内5%退票费
            fee = money.multiply(BigDecimal.valueOf(0.05));
        }
        if(temp > days1 && temp <= days2){
            logger.info("---------------------> calculate: 1~2天");
            //开车时间前24小时至48（包含）小时内10%退票费
            fee = money.multiply(BigDecimal.valueOf(0.1));
        }
        if(temp > hour1 && temp <= days1){
            logger.info("---------------------> calculate: 1小时~1天");
            //开车时间前24小时以内20%退票费
            fee = money.multiply(BigDecimal.valueOf(0.2));
        }
        if(temp <= hour1){
            //控制开车前1小时不可退票
            fee = money;
        }
        logger.info("---------------------> fee: " + fee.toString());
        fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
        money = money.subtract(fee);

        String doubleValue = String.valueOf(money.doubleValue());
        if(StringUtils.isNotEmpty(doubleValue)){
            Float miniData = 0.00f;
            int index = doubleValue.indexOf('.');
            if(index > 0){
                String mini = doubleValue.substring(index + 1);
                if(StringUtils.isNotEmpty(mini)){
                    Integer miniValue = Integer.valueOf(mini);
                    if(miniValue < 25){
                        miniData = 0.0f;
                    }
                    if(miniValue >= 25 && miniValue < 75){
                        miniData = 0.5f;
                    }
                    if(miniValue > 75){
                        miniData = 1.00f;
                    }
                }
            }

            money = BigDecimal.valueOf(money.intValue());
            money = money.add(BigDecimal.valueOf(miniData));
        }


        // 服务费最低2元   FIXME 这里判断要做调整
        if(money.floatValue() <= 2.00f){
            money = BigDecimal.valueOf(2.00);
        }

        request.setTrainPrice(String.valueOf(money));
        return request.getTrainPrice();
    }

    private void validateParam(ChargingRefundRequest request) {
        if(StringUtils.isEmpty(request.getTrainPrice())){
            super.throwParamException("trainPrice is null");
        }

        if(StringUtils.isEmpty(request.getTicketTime())){
            super.throwParamException("ticketTime is null");
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signChargingRefund(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }

    }

}
