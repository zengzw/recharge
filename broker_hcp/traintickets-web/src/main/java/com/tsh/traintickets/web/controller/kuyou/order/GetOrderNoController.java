package com.tsh.traintickets.web.controller.kuyou.order;

import com.traintickets.common.utils.DateUtils;
import com.traintickets.common.utils.RandomUtils;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseRequest;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.response.GetOrderNoResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/25 025.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class GetOrderNoController extends BaseController {

    @RequestMapping(value = "/getOrderNo.do", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getOrderNoMethod(BaseRequest baseRequest){
        this.validateParams(baseRequest);

        GetOrderNoResponse response = new GetOrderNoResponse();
        String outOrderNo = "SPHCPKUYOU" +  DateUtils.format(new Date(), "yyyyMMddHHmmss")
                + RandomUtils.generateRandomNo(4);
        response.setOrderNo(outOrderNo);

        return ResponseBuilder.buildSuccess(outOrderNo);
    }

    private void validateParams(BaseRequest baseRequest) {
        if ("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(baseRequest.getSignKey());

            String sign = KuyouRequestSign.signGetOrderNo(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if (!baseRequest.getSignKey().equals(sign)) {
                super.throwParamException("signKey validate error");
            }
        }
    }
}
