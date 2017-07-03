/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.ofpay.serviceimpl;




import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.exceptions.BusinessException;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.recharge.ofpay.parse.OfPayBeanParse;
import com.tsh.phone.recharge.ofpay.request.OfPayRechargeRequest;
import com.tsh.phone.recharge.ofpay.xmlbean.CardInfoElement;
import com.tsh.phone.recharge.ofpay.xmlbean.OrderInfoElement;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 *  欧飞 充值实现类
 * @author zengzw
 * @date 2016年7月22日
 */
@Service("ofRecharge")
public class OfPayPhoneRechargeServiveImpl implements IPhoneRechargeService{

    private static final Logger LOG = LoggerFactory.getLogger(OfPayPhoneRechargeServiveImpl.class);

    @Autowired
    private OfPayRechargeRequest request;


    //针对于三个字的省份
    private static List<String> lstProvince = new ArrayList<>();
    static{
        lstProvince.add("内蒙古");
        lstProvince.add("黑龙江");
    }

    /**
     * 根据订单Id 查询订单状态
     * 
     * @param orderId 
     * @return
     */
    public ResponseResult queryOrderById(RequestOrderInfoVo reqParams){
        ResponseResult result = new ResponseResult();
        ResponseMessage respMesage = request.queryByOrderNo(reqParams.getOrderId());
        if(respMesage.hasError()){
            LOG.info("#OF----->获取订单出错了:code:{},orderId:{}",respMesage.getCode(),reqParams.getOrderId());
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setCode(respMesage.getCode());
            result.setMessage(respMesage.getMessage());
            return result;
        }

        OrderInfoElement orderInfo = respMesage.parseValueToObject(OrderInfoElement.class);

        result.setData(OfPayBeanParse.parseOrder(orderInfo));
        return result;
    }



    /**
     * 检查手机 当前手机、充值面额是否支持
     * 
     * @param mobileNum 手机号码 
     * @param price 价格
     * @return
     * @throws BusinessException
     */
    private CardInfoElement checkPhoneAvailable(String mobileNum, int price)  throws BusinessException{
        ResponseMessage respMesage = request.checkPhoneAvailable(mobileNum, price);
        if(respMesage.hasError()){
            LOG.info("#OF------>获取手机归属地信息出错:code:{},message:{}",respMesage.getCode(),respMesage.getMessage());
            throw new BusinessException(respMesage.getCode()+"", respMesage.getMessage());
        }

        return respMesage.parseValueToObject(CardInfoElement.class);
    }




    /**
     * 充值
     */

    @Override
    public ResponseResult recharge(RequestRechargeVo reqParams){
        String orderNo = StringUtils.getOrderNo(Configurations.OrderNoType.OF);
        LOG.info("#OF----------> 欧飞充值接口请求参数：{},orderNO:{}",JSON.toJSONString(reqParams),orderNo);

        ResponseResult result = new ResponseResult();
        //检查手机状态是否可以充值
        try {
            /* CardInfoElement cardInfo =*/ checkPhoneAvailable(reqParams.getMobileNum(), reqParams.getPrice());
        } catch (BusinessException e) {
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setCode(e.getErrCode());
            result.setMessage(e.getErrMsg());
            return result;
        }


        //进行接口调用
        ResponseMessage respMessage = request.recharge(orderNo, reqParams.getMobileNum(), reqParams.getPrice());
        if(respMessage.hasError()){
            result.setStatus(SystemCodes.ERROR_CODE);
            result.setCode(respMessage.getCode());
            result.setMessage(respMessage.getMessage());
            return result;
        }

        //转换成公共的对象返回
        OrderInfoElement orderInfo = respMessage.parseValueToObject(OrderInfoElement.class);
        RechargeVo rechargeVo = OfPayBeanParse.parseRechargeInfo(orderInfo);

        //如果下单成功，显示充值；否则，充值失败。下单成功，不代表充值成功。
        LOG.info("#OF--------------> OFpay 充值【同步返回】成功 。responseParams:Mobile:{},price:{},status:{}",
                new Object[]{reqParams.getMobileNum(),reqParams.getPrice(),rechargeVo.getOrderStauts()});


        result.setData(rechargeVo);
        return result;
    }



    /**
     * 查询手机归属地、运营商
     * 
     * response格式：
     * 13333333|广东|移动
     * 13333333|内蒙古|移动
     */
    @Override
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo reqParams) {
        LOG.info("#OF----->查询手机类型，请求参数:{}",JSON.toJSONString(reqParams));

        ResponseResult result = new ResponseResult();
        String response = request.queryPhoneType(reqParams.getMobileNum());
        if(response != null){
            String[] splitResult = response.split("\\|");
            if(splitResult.length > 1 ){
                PhoneLocationVo phoneLocationVo = new PhoneLocationVo();
                String[] areas = getSpecialArea(splitResult[1]);
                phoneLocationVo.setProvinceName(areas[0]);
                phoneLocationVo.setCityName(areas[1]);
                phoneLocationVo.setType(splitResult[2]);
                result.setData(phoneLocationVo);
            }else{
                result.setStatus(SystemCodes.ERROR_CODE);
                result.setMessage(response);
            }

            return result;
        }

        result.setStatus(SystemCodes.ERROR_CODE);
        result.setMessage("查询返回位空");
        return result;
    }




    /**
     * 消息回调给VAS
     */
    @Override
    public void callback(RequestCallbackVo params) {
        LOG.info("#OF------->内部callback通知vas请求参数:{}",JSON.toJSONString(params));

        request.callback(params.getOrderId(),parseStatus(params.getStatus()),params.getMessage());
    }




    /*
     *  状态转换
     */
    private int parseStatus(int status){
        if(status == 1){
            return Configurations.RechargeCallBackStatus.SUCCESS;
        }else if(status ==  9){
            return Configurations.RechargeCallBackStatus.FAILED;
        }

        return Configurations.RechargeCallBackStatus.OTHER;
    }


    /**
     * 截取到省份、城市
     * 
     * content:
     *      广东|移动
     *      内蒙古|移动
     * 
     * @param content
     * @return
     */
    private static String[] getSpecialArea(String content){
        String[] results = new String[2];
        for(String s: lstProvince){
            if(content.startsWith(s)){
                results[0] = content.substring(0,3);
                results[1] = content.substring(3,5);
                return results;
            }
        }

        results[0] = content.substring(0,2);
        results[1] = content.substring(2,4);
        return results;
    }



    @Override
    public ResponseResult reversal(RequestReverseVo reqParams) {
        return null;
    }

}
