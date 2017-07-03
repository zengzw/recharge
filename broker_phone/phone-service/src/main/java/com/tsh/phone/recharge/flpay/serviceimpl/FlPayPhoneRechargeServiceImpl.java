package com.tsh.phone.recharge.flpay.serviceimpl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ltsopensource.core.json.JSON;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.utils.JsonUtils;
import com.tsh.phone.diamond.TshDiamondClient;
import com.tsh.phone.recharge.flpay.common.FlCheckErrorCode;
import com.tsh.phone.recharge.flpay.common.FlConstantsEnums;
import com.tsh.phone.recharge.flpay.parse.FuluBeanParse;
import com.tsh.phone.recharge.flpay.request.FuLPayRechargeRequest;
import com.tsh.phone.recharge.flpay.vo.supplier.response.CreateOrderResponse;
import com.tsh.phone.recharge.flpay.vo.supplier.response.QueryMobileResponse;
import com.tsh.phone.recharge.flpay.vo.supplier.response.QueryOrderResponse;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 * Created by Administrator on 2017/4/12 012.
 */
@Service("flRecharge")
public class FlPayPhoneRechargeServiceImpl implements IPhoneRechargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlPayPhoneRechargeServiceImpl.class);

    @Autowired
    FuLPayRechargeRequest rechargeRequest;

    @Override
    public ResponseResult queryOrderById(RequestOrderInfoVo reqParams) {
        ResponseMessage responseMessage = null;
        try {
            responseMessage = rechargeRequest.queryOrder(reqParams);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseResult(SystemCodes.ERROR_CODE, e.getMessage(), String.valueOf(SystemCodes.ERROR_CODE));
        }

        if(null != responseMessage && responseMessage.hasError()){
            LOGGER.info("#FL-----> 获取订单信息出错。code:{}，msg:{} ",responseMessage.getCode(),responseMessage.getMessage());
            return new ResponseResult(SystemCodes.ERROR_CODE, responseMessage.getMessage(),responseMessage.getCode());
        }

        QueryOrderResponse response = JsonUtils.convert2Object(responseMessage.getValue(), QueryOrderResponse.class);
        if(null != response){
            if(FlConstantsEnums.QUERY_ORDER_SUCCESS_CODE.getValue().equals(response.getStatus())){
                return new ResponseResult(FuluBeanParse.parseOrder(reqParams.getOrderId(), response));
            } else {
                return new ResponseResult(SystemCodes.ERROR_CODE,
                        response.getMessageInfo(),
                        String.valueOf(SystemCodes.ERROR_CODE));
            }

        } else {
            return new ResponseResult(SystemCodes.ERROR_CODE,
                    "系统异常",
                    String.valueOf(SystemCodes.SYSTEM_ERROR));
        }
    }

    @Override
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo reqParams) {
        ResponseMessage responseMessage = null;
        try {
            responseMessage = rechargeRequest.queryMobile(reqParams);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseResult(SystemCodes.ERROR_CODE, e.getMessage(), String.valueOf(SystemCodes.ERROR_CODE));
        }

        if(null != responseMessage && responseMessage.hasError()){
            LOGGER.info("#FL-----> 获取手机号码归属地出错。code:{}，msg:{} ",responseMessage.getCode(),responseMessage.getMessage());
            return new ResponseResult(SystemCodes.ERROR_CODE, responseMessage.getMessage(),responseMessage.getCode());
        }

        QueryMobileResponse response = JsonUtils.convert2Object(responseMessage.getValue(), QueryMobileResponse.class);
        if(null != response){
            if(FlConstantsEnums.QUERY_MOBILE_SUCCESS_CODE.getValue().equals(response.getMessageCode())){
                return new ResponseResult(FuluBeanParse.parsePhoneLocation(response));
            } else {
                return new ResponseResult(SystemCodes.ERROR_CODE, response.getMessageInfo(), String.valueOf(SystemCodes.ERROR_CODE));
            }

        } else {
            return new ResponseResult(SystemCodes.ERROR_CODE, "系统异常", String.valueOf(SystemCodes.SYSTEM_ERROR));
        }

    }

    @Override
    public ResponseResult recharge(RequestRechargeVo reqParams) {
        String orderNo = com.tsh.phone.util.StringUtils.getOrderNo(Configurations.SP_TYPE_FL);

        String flag = TshDiamondClient.getInstance().getConfig("fl.test");
        LOGGER.info("#FL-----> 测试标识:{} ", flag);
        if("1".equals(flag)){
            RechargeVo rechargeVo = new RechargeVo();
            rechargeVo.setOrderNo(orderNo);
            rechargeVo.setOrderStauts(Configurations.OrderStatus.SUCCESS);
            LOGGER.info("#FL-----> 返回测试结果:{} ", rechargeVo);
            return new ResponseResult(rechargeVo);
        }


        // 检测充值手机号和充值金额
        ResponseResult responseResult = checkPhoneAvailable(reqParams);
        if(null != responseResult && responseResult.getStatus() == SystemCodes.SUCCESS_CODE){

        } else {
            LOGGER.info("---------#FL-----> recharge check error:" + JSON.toJSONString(responseResult));
            return responseResult;
        }


        ResponseMessage responseMessage = null;
        try {
            responseMessage = rechargeRequest.charge(orderNo, reqParams);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        if(null != responseMessage && responseMessage.hasError()){
            LOGGER.info("#FL-----> 充值出错:{} ", JSON.toJSONString(responseMessage));
        }

        CreateOrderResponse response = JsonUtils.convert2Object(responseMessage.getValue(), CreateOrderResponse.class);
        if(null != response){
            if(FlConstantsEnums.CHARGE_SUCCESS_STATUS.getValue().equals(response.getStatus()) &&
                    FlConstantsEnums.CHARGE_SUCCESS_STATUS_MSG.getValue().equals(response.getStatusMsg()) ){
                // 充值成功，直接返回
                return new ResponseResult(FuluBeanParse.parseRechargeInfo(orderNo, response));
            }

            // 判断是否返回需要复查的错误，是则返回成功结果
            if(FlCheckErrorCode.QUERY_ERROR.getCode().equals(response.getMessageCode()) ||
                    FlCheckErrorCode.ORDER_ERROR.getCode().equals(response.getMessageCode()) ||
                    FlCheckErrorCode.TIME_OUT.getCode().equals(response.getMessageCode())){

                RechargeVo rechargeVo = new RechargeVo();
                rechargeVo.setOrderNo(orderNo);
                rechargeVo.setOrderStauts(Configurations.OrderStatus.SUCCESS);
                return new ResponseResult(rechargeVo);
            }

            // 其他直接返回失败结果
            return new ResponseResult(FuluBeanParse.getFailOrder(orderNo));
        }

        // 其他直接返回失败结果
        return new ResponseResult(FuluBeanParse.getFailOrder(orderNo));

    }


    /**
     * 充值前检测
     * @param reqParams
     * @return
     */
    private ResponseResult checkPhoneAvailable(RequestRechargeVo reqParams) {
        RequestQueryPhoneTypeVo queryPhoneTypeVo = new RequestQueryPhoneTypeVo();
        queryPhoneTypeVo.setMobileNum(reqParams.getMobileNum());
        queryPhoneTypeVo.setMoney(String.valueOf(reqParams.getPrice()));

        return this.queryPhoneType(queryPhoneTypeVo);
    }


    @Override
    public void callback(RequestCallbackVo reqeust) {

        if(StringUtils.isNotBlank(reqeust.getStatusStr()) &&
                StringUtils.isNotBlank(reqeust.getOrderId()) &&
                StringUtils.isNotBlank(reqeust.getCustomerOrderNo())){

            rechargeRequest.callBackToVas(reqeust);
        }
    }

    @Override
    public ResponseResult reversal(RequestReverseVo reqParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
