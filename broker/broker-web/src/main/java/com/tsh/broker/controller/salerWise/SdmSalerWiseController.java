package com.tsh.broker.controller.salerWise;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.config.SalerWiseConfig;
import com.tsh.broker.controller.valiator.SdmValidator;
import com.tsh.broker.execute.sdm.SdmExecute;
import com.tsh.broker.utils.ExceptionHandler;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.request.GenerateOrderNoRequest;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;

/**
 * 易赛水电煤Controller
 *
 * @author dengjd
 * @date 2016/9/28
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/sdm/salerwise")
public class SdmSalerWiseController {

    private static Logger logger = LogManager.getLogger (SdmSalerWiseController.class);

    @Resource(name = "sdmSalerWiseExecute")
    private SdmExecute sdmExecute;
    @Resource(name = "salerWiseConfig")
    private SalerWiseConfig salerWiseConfig;
    private SdmValidator requestValidator = new SdmValidator();

    /**
     * 缴费单位信息查询接口(查本地)
     * @Date 2016年11月9日<br>
     * @param payUnitRequest
     * @return
     */
    @RequestMapping(value = "/queryPayUnit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryPayUnit(@ModelAttribute PayUnitRequest payUnitRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryPayUnit (payUnitRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            responseWrapper = sdmExecute.queryPayUnit (payUnitRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (10002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询充值单位异常", ex);
        }
        return responseWrapper;
    }
    /**
     * 充值接口
     * @Date 2016年11月9日<br>
     * @param rechargeRequest
     * @return
     */
    @RequestMapping(value = "/recharge.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper recharge(@ModelAttribute RechargeRequest rechargeRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateRecharge (rechargeRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }
        try {
            responseWrapper = sdmExecute.recharge (rechargeRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (20002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("充值请求异常", ex);
        }

        return responseWrapper;
    }

    /**
     * 充值结果查询接口
     * @Date 2016年11月9日<br>
     * @param rechargeResultRequest
     * @return
     */
    @RequestMapping(value = "/queryRechargeResult.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryRechargeResult(@ModelAttribute RechargeResultRequest rechargeResultRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryRechargeResult (rechargeResultRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            responseWrapper = sdmExecute.queryRechargeResult (rechargeResultRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (30002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询充值结果请求异常", ex);
        }

        return responseWrapper;
    }
    /**
     * 充值结果异步通知接口
     * @Date 2016年11月9日<br>
     * @param request
     * @param response
     */
    @RequestMapping(value = "/resultNotify.do")
    public void resultNotify(HttpServletRequest request, HttpServletResponse response) {
        //获取请求参数
        ResultNotifyRequest resultNotifyRequest = new ResultNotifyRequest ();
        resultNotifyRequest.setBusinessId (request.getParameter ("UserNumber")); //接口商编号
        resultNotifyRequest.setOutOrderNo (request.getParameter ("InOrderNumber"));	//易赛方面生成的订单号
        resultNotifyRequest.setInOrderNo (request.getParameter ("OutOrderNumber"));	//接口商方面生成的订单号
        resultNotifyRequest.setPayResult (Integer.valueOf (request.getParameter ("PayResult"))); //订单结果
        resultNotifyRequest.setSigned (request.getParameter ("RecordKey")); //签名
        
        //订单类型
        String orderType = request.getParameter("OrderType");
        logger.info("易赛异步回调结果=======：" + resultNotifyRequest);
        
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateResultNotify (resultNotifyRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return;
        }

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

    /**
     * 查询缴费账单接口
     * @Date 2016年11月9日<br>
     * @param paymentBillRequest
     * @return
     */
    @RequestMapping(value = "/queryPaymentBill.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryPaymentBill(@ModelAttribute PaymentBillRequest paymentBillRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryPaymentBill (paymentBillRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            responseWrapper = sdmExecute.queryPaymentBill (paymentBillRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (50002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询缴费账单请求异常", ex);
        }

        return responseWrapper;
    }
    
    /**
     * 多账单查询缴费账单接口
     * @Date 2016年11月9日<br>
     * @param paymentBillRequest
     * @return
     */
    @RequestMapping(value = "/queryPaymentBillMore.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryPaymentBillMore(@ModelAttribute PaymentBillRequest paymentBillRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateQueryPaymentBill (paymentBillRequest, salerWiseConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString (responseWrapper));
            return responseWrapper;
        }

        try {
            responseWrapper = sdmExecute.queryPaymentBillMore(paymentBillRequest);
        } catch (Exception ex) {
            responseWrapper.setStatus (50002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage (ex));
            logger.error ("查询缴费账单请求异常", ex);
        }

        return responseWrapper;
    }
    
    /**
     * 生成订单编号接口
     * @Date 2016年11月9日<br>
     * @param generateOrderNoRequest
     * @return
     */
    @RequestMapping(value = "/generateOrderNo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper generateOrderNo(@ModelAttribute GenerateOrderNoRequest generateOrderNoRequest){
        //请求参数校验
        ResponseWrapper responseWrapper = requestValidator.validateGenerateOrderNo (generateOrderNoRequest, salerWiseConfig);
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
