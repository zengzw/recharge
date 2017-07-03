package com.tsh.traintickets.web.controller.kuyou.callback;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.DealOrderCallBackRequest;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 订单处理回调接口
 * Created by Administrator on 2016/11/21 021.
 */
@Controller
@RequestMapping("/traintickets/kuyou/callback")
public class DealOrderCallbackController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/dealOrder.do", method = RequestMethod.POST )
    public void dealOrderCallBack() {
        Result result = super.getResult();
        try {
            logger.info("----------------------------------");
            logger.info("---------order callback-----------");
            logger.info("----------------------------------");

            String timestamp = request.getParameter(TicketsConstants.BACK_PARAM_TIMESTAMP);
            String jsonparams = request.getParameter(TicketsConstants.BACK_PARAM_JSON_PARAM);
            String merchantId = request.getParameter(TicketsConstants.BACK_PARAM_MERCHANT_ID);

            if(StringUtils.isNotEmpty(timestamp) && StringUtils.isNotEmpty(jsonparams) &&
                    StringUtils.isNotEmpty(merchantId) &&
                    DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_merchantId).equals(merchantId)){

                DealOrderCallBackRequest callbackRequest = JsonUtils.convert2Object(jsonparams, DealOrderCallBackRequest.class);
                logger.info("-------> orderCallbackRequest: " + JSON.toJSONString(callbackRequest));
                kuyouService.dealOrderCallBack(ConvertRequest2BO.convertDealOrderCallBack(callbackRequest));
            }
        } catch (BizException be) {
            logger.error(be.getMessage(), be);
            result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
            this.error(result, be);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.error(result, e);
        } finally {
            this.send(result);
        }

        super.writeCallBackMsg(true);

    }
}
