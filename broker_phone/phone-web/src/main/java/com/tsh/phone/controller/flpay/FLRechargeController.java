package com.tsh.phone.controller.flpay;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.config.FLRechargeConfig;
import com.tsh.phone.controller.validate.PhoneRequestValidates;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/11 011.
 */
@Controller
@RequestMapping("/rechargephone/fl")
public class FLRechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FLRechargeController.class);

    @Resource(name="flRecharge")
    IPhoneRechargeService rechargeService;

    @RequestMapping(value="/orderinfo")
    @ResponseBody
    public ResponseResult queryOrderInfo(RequestOrderInfoVo reqParmas){
        LOGGER.info("--- 福禄 查询订单信息请求参数:{}", JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validateQueryOrderInfoVo(reqParmas, new FLRechargeConfig());
        if(result.hasError()){
            return result;
        }

        return rechargeService.queryOrderById(reqParmas);
    }

    /**
     * 查询手机号码类型
     *
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/phonetype")
    @ResponseBody
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo reqParmas){
        LOGGER.info("--- 福禄 查询 手机号码段、归属地接口 请求参数:{}",JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validateFLPhoneTypeVo(reqParmas, new FLRechargeConfig());
        if(result.hasError()){
            return result;
        }

        return rechargeService.queryPhoneType(reqParmas);
    }

    /**
     * 充值接口
     *
     * @param reqParmas
     * @return
     */
    @RequestMapping(value="/recharge")
    @ResponseBody
    public ResponseResult recharge(RequestRechargeVo reqParmas){
        LOGGER.info("--- 福禄 充值  请求参数:{}",JSON.toJSONString(reqParmas));

        ResponseResult result =  PhoneRequestValidates.validateRechargeVo(reqParmas, new FLRechargeConfig());
        if(result.hasError()){
            return result;
        }

        return rechargeService.recharge(reqParmas);
    }


    /**
     * 福禄 充值结果回调接口
     *
     * 代理商商城系统在接收到充值结果后将状态值（status）返回，返回其他信息均表示未收到充值结果
     *5分钟之后重新发送充值结果，直到代理商商城系统返回正确的充值结果：状态值（status）,或者重发的次数到达6次
     * @param request
     * @param response
     */
    @RequestMapping(value="/callback")
    public void gaoyRechargeCallback(HttpServletRequest request, HttpServletResponse response){

        LOGGER.info("------福禄 充值回调：" + JSON.toJSONString(request.getParameterMap()));

        try {
            RequestCallbackVo callbackVo = new RequestCallbackVo();
            String status = request.getParameter("Status"); //true充值成功 false充值失败
            String orderNo = request.getParameter("OrderNo");//福禄订单号
            String customerOrderNo = request.getParameter("CustomerOrderNo"); //合作商家订单号
            String chargeTime = request.getParameter("ChargeTime"); //交易完成时间
            String reMark = request.getParameter("ReMark");


            LOGGER.info("#FL--------->flPay asyn callback parameters# status:{},orderNo:{},"
                            + "customerOrderNo:{},chargeTime:{},reMark:{}",
                    new Object[]{status,orderNo,customerOrderNo,chargeTime,reMark});

            if(StringUtils.isEmpty(orderNo)|| StringUtils.isEmpty(status)){
                LOGGER.info("#FL----------> flPay asyn callback 参数为空");
                callbackVo.setMessage("供应商返回消息为空");
                callbackVo.setStatus(Configurations.RechargeCallBackStatus.OTHER);
            }else{
                callbackVo.setStatusStr(status);
                callbackVo.setOrderId(orderNo);
                callbackVo.setCustomerOrderNo(customerOrderNo);
                callbackVo.setMessage(reMark);
            }

            rechargeService.callback(callbackVo);

            // 部分成功情况下。未充值上部分，如果你不想给用户退款，可以再次给他们充值未成功部分，需要重新下单
            //  如果不再冲了那就退款给用户

        } catch (Exception e) {
            LOGGER.error("flPay asyn recharge callback error:"+e);
        }finally{
            //不管成功失败，必须把状态返回给服务商。免得服务商多次重试调用
            try {
                response.getWriter().write("<status>True</status>");
            } catch (IOException e) {
                LOGGER.error("flPay asyn recharge callback printwrite error:",e);
            }
        }
    }
}
