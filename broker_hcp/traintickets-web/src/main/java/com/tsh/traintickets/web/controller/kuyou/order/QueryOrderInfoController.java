package com.tsh.traintickets.web.controller.kuyou.order;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.tsh.traintickets.bo.kuyou.queryorder.QueryOrderBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.QueryOrderInfoRequest;
import com.tsh.traintickets.vo.response.QueryOrderInfoResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查询订单信息接口
 * Created by Administrator on 2016/11/21 021.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class QueryOrderInfoController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/queryOrder.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse checkTicketNum(QueryOrderInfoRequest request) {
        Result result = super.getResult();
        try {
            this.validateParam(request);

            QueryOrderBO queryResult = kuyouService.queryOrderInfo(ConvertRequest2BO.convertQueryOrderInfo(request));
            QueryOrderInfoResponse response = new QueryOrderInfoResponse();
            // 判断是否为空
            if(null != queryResult && TicketsConstants.SUCCESS_CODE.equals(queryResult.getReturn_code())){
                response.setOrderId(queryResult.getOrder_id());
                response.setMerchantOrderId(queryResult.getMerchant_order_id());
                response.setOrderInfo(Convert2VO.convertQueryOrderInfo(queryResult.getTicket_list()));
                result.setData(ResponseBuilder.buildSuccess(response));
            } else {
                result.setData(ResponseBuilder.buildError(ResponseCode.QUERY_ORDER_ERROR));
            }

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

        logger.info("-------------> queryOrderInfoReturn：" + JSON.toJSONString(result.getData()));
        return result.getData();
    }

    private void validateParam(QueryOrderInfoRequest request) {
        if (StringUtils.isEmpty(request.getOrderId())){
            super.throwParamException("orderId is null");
        }
        if (StringUtils.isEmpty(request.getMerchantOrderId())){
            super.throwParamException("merchantOrderId is null");
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signQueryOrderInfo(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }
    }
}
