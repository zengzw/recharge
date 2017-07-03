package com.tsh.traintickets.web.controller.kuyou.query;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.QuerySubwayStationRequest;
import com.tsh.traintickets.vo.response.QuerySubwayStationModel;
import com.tsh.traintickets.vo.response.QuerySubwayStationResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 查询车次途径站点列表
 * Created by Administrator on 2016/11/19 019.
 */

@Controller
@RequestMapping("/traintickets/kuyou")
public class QuerySubwayStationController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/querySubwayStation.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse querySubwayStation(QuerySubwayStationRequest request) {
        Result result = super.getResult ();
        try{
            this.validateParam(request);

            QuerySubwayStationBO queryResult = kuyouService.querySubwayStation(ConvertRequest2BO.convertQuerySubwayStation(request));
            QuerySubwayStationResponse response = new QuerySubwayStationResponse();
            if(null != queryResult && TicketsConstants.SUCCESS_CODE.equals(queryResult.getReturn_code())){
                Map<String, Object> resultMap = Convert2VO.convertQuerySubwayStation(request, queryResult);
                response.setDataList((List<QuerySubwayStationModel>) resultMap.get("list"));
                response.setArriveDays((String) resultMap.get("arriveDays"));
                result.setData(ResponseBuilder.buildSuccess(response));
            } else {
                response.setDataList(Collections.<QuerySubwayStationModel>emptyList());
                result.setData(ResponseBuilder.buildSuccess(response));
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

        logger.info("-------------> querySubwayStationReturn：" + JSON.toJSONString(result.getData()));
        return result.getData();
    }

    private void validateParam(QuerySubwayStationRequest request) {
        if(StringUtils.isEmpty(request.getTrainCode())){
            super.throwParamException("trainCode is null");
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signQuerySubwayStation(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }

    }
}
