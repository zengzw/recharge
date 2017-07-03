package com.tsh.traintickets.web.controller.kuyou.query;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.request.CheckTicketNumRequest;
import com.tsh.traintickets.vo.response.CheckTicketNumResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.jam.JamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查询车次你余票
 * Created by Administrator on 2016/11/19 019.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class QueryTicketNumController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/queryTicketNum.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse checkTicketNum(CheckTicketNumRequest request) {
        Result result = super.getResult ();
        try{
            this.validateParam(request);

            CheckTicketNumBO queryTicketResult = kuyouService.queryTicketNum(ConvertRequest2BO.convertCheckTicketNum(request));
            CheckTicketNumResponse response = new CheckTicketNumResponse();
            // 判断是否为空
            if(null != queryTicketResult && TicketsConstants.SUCCESS_CODE.equals(queryTicketResult.getReturn_code())) {
                response.setData(Convert2VO.convertCheckTicket(queryTicketResult));
                result.setData(ResponseBuilder.buildSuccess(response));
            } else {
                result.setData(ResponseBuilder.buildSuccess(""));
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
        logger.info("-------------> queryTicketNumReturn：" + JSON.toJSONString(result.getData()));
        return result.getData();
    }


    private void validateParam(CheckTicketNumRequest request) {
        if(StringUtils.isEmpty(request.getArriveStation())){
            super.throwParamException("arriveStation is null");
        }
        if(StringUtils.isEmpty(request.getFromStation())){
            super.throwParamException("fromStation is null");
        }
        if(StringUtils.isEmpty(request.getTravelTime())){
            super.throwParamException("travelTime is null");
        }
        if(StringUtils.isEmpty(request.getTrainCode())){
            super.throwParamException("trainCode is null");
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signQueryTicketNum(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }
    }
}
