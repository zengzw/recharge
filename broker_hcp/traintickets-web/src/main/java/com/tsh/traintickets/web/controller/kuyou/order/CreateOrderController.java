package com.tsh.traintickets.web.controller.kuyou.order;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.google.gson.reflect.TypeToken;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.CreateOrderRequest;
import com.tsh.traintickets.vo.request.UserModel;
import com.tsh.traintickets.vo.response.CreateOrderResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * 下单并出票接口
 * @author zhoujc
 *
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class CreateOrderController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/createOrder.do", method = RequestMethod.POST )
    @ResponseBody
    public BaseResponse createOrder(CreateOrderRequest request) {
        Result result = super.getResult();
        try {
            this.validateParam(request);

            CreateOrderBO createResult = kuyouService.createOrder(ConvertRequest2BO.convertCreateOrder(request));
            CreateOrderResponse response = new CreateOrderResponse();
            // 判断是否为空
            if(null != createResult && TicketsConstants.SUCCESS_CODE.equals(createResult.getReturn_code())){
                response.setData(Convert2VO.convertCreateOrder(createResult));
                result.setData(ResponseBuilder.buildSuccess(response));
            } else {
                result.setData(ResponseBuilder.buildError(ResponseCode.QUERY_TICKETS_ERROR));
            }
        } catch (BizException be) {
            logger.error(be.getMessage(), be);
            result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
            this.error(result, be);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setData(ResponseBuilder.buildError(ResponseCode.CREATE_ORDER_ERROR));
            this.error(result, e);
        } finally {
            this.send(result);
        }

        logger.info("-------------> createOrderReturn：" + JSON.toJSONString(result.getData()));
        return result.getData();
    }

    private void validateParam(CreateOrderRequest request) {
        this.valiateParmsNull(request);

        List<UserModel> userList = JsonUtils.convert2List(request.getUserDetailList(), new TypeToken<List<UserModel>>(){}.getType());
        if(null == userList || userList.isEmpty()){
            super.throwParamException("userDetailList is null");
        }
        for(UserModel userModel : userList){
            if(StringUtils.isEmpty(userModel.getBx())){
                super.throwParamException("bx is null");
            }
            if(StringUtils.isEmpty(userModel.getIdType())){
                super.throwParamException("idType is null");
            }
            if(StringUtils.isEmpty(userModel.getTicketType())){
                super.throwParamException("ticketType is null");
            }
            if(StringUtils.isEmpty(userModel.getUserId())){
                super.throwParamException("userId is null");
            }
            if(StringUtils.isEmpty(userModel.getUserName())){
                super.throwParamException("userName is null");
            }
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signCreateOrder(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }
    }


    private void valiateParmsNull(CreateOrderRequest request){
        if(StringUtils.isEmpty(request.getMerchantOrderId())){
            super.throwParamException("merchantOrderId is null");
        }
        if(StringUtils.isEmpty(request.getOrderLevel())){
            super.throwParamException("orderLevel is null");
        }
        if(StringUtils.isEmpty(request.getArriveStation())){
            super.throwParamException("arriveStation is null");
        }
        if(StringUtils.isEmpty(request.getArriveTime())){
            super.throwParamException("arriveTime is null");
        }
        if(StringUtils.isEmpty(request.getFromStation())){
            super.throwParamException("fromStation is null");
        }

        this.valiate(request);
    }

    private void valiate(CreateOrderRequest request){
        if(StringUtils.isEmpty(request.getFromTime())){
            super.throwParamException("fromTime is null");
        }
        if(StringUtils.isEmpty(request.getSeatType())){
            super.throwParamException("seatType is null");
        }
        if(StringUtils.isEmpty(request.getSmsNotify())){
            super.throwParamException("smsNotify is null");
        }
        if(StringUtils.isEmpty(request.getSumAmount())){
            super.throwParamException("sumAmount is null");
        }
        if(StringUtils.isEmpty(request.getTicketPrice())){
            super.throwParamException("ticketPrice is null");
        }
        if(StringUtils.isEmpty(request.getTrainCode())){
            super.throwParamException("trainCode is null");
        }
        if(StringUtils.isEmpty(request.getTravelTime())){
            super.throwParamException("travelTime is null");
        }

    }
}
