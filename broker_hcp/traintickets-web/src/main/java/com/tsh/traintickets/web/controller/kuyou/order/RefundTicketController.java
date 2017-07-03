package com.tsh.traintickets.web.controller.kuyou.order;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.RefundTicketRequest;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 退票退款接口
 * Created by Administrator on 2016/11/21 021.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class RefundTicketController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/refundTicket.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse refundTicket(RefundTicketRequest request) {
        Result result = super.getResult();
        try {
            this.validateParam(request);

            RefundTicketBO refundResult = kuyouService.refundTicket(ConvertRequest2BO.convertRefundTicket(request));
            // 判断是否为空
            if(null != refundResult){
                if(TicketsConstants.SUCCESS_CODE.equals(refundResult.getReturn_code()) &&
                        TicketsConstants.SUCCESS.equals(refundResult.getStatus()) &&
                        StringUtils.isEmpty(refundResult.getFail_reason())){
                    result.setData(ResponseBuilder.buildSuccess(Convert2VO.convertRefundTicket(refundResult)));
                } else {
                    result.setData(ResponseBuilder.buildError(ResponseCode.CANNOT_REFUND, Convert2VO.convertRefundTicket(refundResult)));
                }
            } else {
                result.setData(ResponseBuilder.buildError(ResponseCode.REFUND_TICKET_ERROR));
            }

        } catch (BizException be) {
            logger.error(be.getMessage(), be);
            result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
            this.error(result, be);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setData(ResponseBuilder.buildError(ResponseCode.REFUND_TICKET_ERROR));
            this.error(result, e);
        } finally {
            this.send(result);
        }

        logger.info("-------------> refundTicketReturn：" + JSON.toJSONString(result.getData()));

        return result.getData();
    }

    private void validateParam(RefundTicketRequest request) {
        if(StringUtils.isEmpty(request.getMerchantOrderId())){
            super.throwParamException("merchantOrderId is null");
        }
        if(StringUtils.isEmpty(request.getOrderId())){
            super.throwParamException("orderId is null");
        }
        if(StringUtils.isEmpty(request.getComment())){
            super.throwParamException("comment is null");
        }
        if(StringUtils.isEmpty(request.getRefundType())){
            super.throwParamException("refundType is null");
        }
        if(StringUtils.isEmpty(request.getRequestId())){
            super.throwParamException("requestId is null");
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signRefundTicket(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }
    }
}
