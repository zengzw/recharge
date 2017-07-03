package com.tsh.broker.controller.ofpay;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.config.OfPayConfig;
import com.tsh.broker.controller.valiator.SdmValidator;
import com.tsh.broker.execute.sdm.SdmExecute;
import com.tsh.broker.utils.ExceptionHandler;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.request.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * SdmOfPayMockController
 * 本Controller屏端测试使用
 * @author dengjd
 * @date 2016/10/22
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/sdm/ofpay/mock")
public class SdmOfPayMockController {

    private static Logger logger = LogManager.getLogger(SdmOfPayController.class);

    @Resource(name = "sdmOfPayExecute")
    private SdmExecute sdmExecute;
    @Resource(name = "ofPayConfig")
    private OfPayConfig ofPayConfig;
    private SdmValidator requestValidator = new SdmValidator();

    @RequestMapping(value = "/queryPayUnit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryPayUnit(@ModelAttribute PayUnitRequest payUnitRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryPayUnit (payUnitRequest, ofPayConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString(responseWrapper));
            return responseWrapper;
        }

        try {
            String  json  = "{\"status\":0,\"message\":\"\",\"data\":[{\"unitId\":\"v85002\",\"unitName\":\"国网浙江省电力公司\",\"province\":\"浙江\",\"city\":\"杭州\",\"county\":null,\"payType\":2,\"payModel\":null,\"accountType\":1,\"isNeedMonth\":null,\"unitStatus\":null,\"productId\":\"64156301\"}]}";
            return JSONObject.parseObject(json, ResponseWrapper.class);
        } catch (Exception ex) {
            responseWrapper.setStatus (10002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage(ex));
            logger.error ("查询充值单位异常", ex);
        }

        return responseWrapper;
    }

    @RequestMapping(value = "/recharge.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper recharge(@ModelAttribute RechargeRequest rechargeRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateRecharge (rechargeRequest, ofPayConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }
        try {
            String json = "{\"status\":0,\"message\":\"\",\"data\":70001}\n";
            return JSONObject.parseObject(json, ResponseWrapper.class);
        } catch (Exception ex) {
            responseWrapper.setStatus (20002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("充值请求异常", ex);
        }

        return responseWrapper;
    }


    @RequestMapping(value = "/queryRechargeResult.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryRechargeResult(@ModelAttribute RechargeResultRequest rechargeResultRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryRechargeResult (rechargeResultRequest, ofPayConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            String json = "{\"status\":0,\"message\":\"\",\"data\":70003}";
            return JSONObject.parseObject(json, ResponseWrapper.class);
        } catch (Exception ex) {
            responseWrapper.setStatus (30002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询充值结果请求异常", ex);
        }

        return responseWrapper;
    }

    @RequestMapping(value = "/resultNotify.do", method = RequestMethod.POST)
    public void resultNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("欧飞话水电煤订单通知结果通知信息," + JSONObject.toJSONString(request.getParameterMap()));

        ResponseWrapper responseWrapper = new ResponseWrapper();
        ResultNotifyRequest resultNotifyRequest = new ResultNotifyRequest ();
        resultNotifyRequest.setOutOrderNo (request.getParameter ("sporder_id"));
        resultNotifyRequest.setPayResult(Integer.valueOf(request.getParameter("ret_code")));
        resultNotifyRequest.setResultMsg(request.getParameter("err_msg"));
        try {
            responseWrapper = sdmExecute.resultNotify (resultNotifyRequest);

        } catch (Exception ex) {
            responseWrapper.setStatus (40002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("充值结果通知异常", ex);
        }
        //返回通知成功
        if (responseWrapper.getStatus () == 0) {
            writeNotifySuccess (response);
        }
    }


    @RequestMapping(value = "/queryPaymentBill.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryPaymentBill(@ModelAttribute PaymentBillRequest paymentBillRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryPaymentBill (paymentBillRequest, ofPayConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }
        try {
            String json = "{\"status\":0,\"message\":\"\",\"data\":[{\"unitId\":\"v85002\",\"account\":\"9033841019\",\"yearmonth\":\"201308\",\"username\":\"苏*司\",\"bills\":2,\"balance\":0,\"payModel\":2},{\"unitId\":\"v85002\",\"account\":\"9033841019\",\"yearmonth\":\"201309\",\"username\":\"苏*司\",\"bills\":0.04,\"balance\":0,\"payModel\":1}]}";
            responseWrapper = JSONObject.parseObject(json, ResponseWrapper.class);
            return responseWrapper;
        } catch (Exception ex) {
            responseWrapper.setStatus (50002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询缴费账单请求异常", ex);
        }

        return responseWrapper;
    }

    @RequestMapping(value = "/generateOrderNo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper generateOrderNo(@ModelAttribute GenerateOrderNoRequest generateOrderNoRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateGenerateOrderNo (generateOrderNoRequest, ofPayConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            responseWrapper = sdmExecute.generateOrderNo (generateOrderNoRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (60002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("生成订单编号请求异常", ex);
        }

        return responseWrapper;
    }


    private void writeNotifySuccess(HttpServletResponse response) {
        try {
            response.setContentType ("text/html;charser=utf-8");
            response.setCharacterEncoding ("UTF-8");
            response.setHeader ("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter ();
            out.write ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<Esaipay>\n" +
                    "<Result>Success</Result>\n" +
                    "<Message>接收成功</Message>\n" +
                    "</Esaipay>");
            out.flush ();
            out.close ();
        } catch (IOException e) {
            logger.error ("返回通知结果异常", e);
        }
    }

}
