/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.gaoypay.serviceimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.commoms.config.Configurations;
import com.tsh.phone.commoms.exceptions.BusinessException;
import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.po.PhoneRechargeProduct;
import com.tsh.phone.recharge.gaoypay.parse.GaoYBeanParse;
import com.tsh.phone.recharge.gaoypay.request.GaoYPayRechargeRequest;
import com.tsh.phone.recharge.gaoypay.xmlbean.Mobile;
import com.tsh.phone.recharge.gaoypay.xmlbean.PostResult;
import com.tsh.phone.recharge.gaoypay.xmlbean.Product;
import com.tsh.phone.service.IPhoneRechargeService;
import com.tsh.phone.service.impl.PhoneProductService;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;

/**
 *  高阳 充值接口 实现类
 *  
 * @author zengzw
 * @date 2017年2月22日
 */
@Service("gyRecharge")
public class GaoYPhoneRechargeServiveImpl implements IPhoneRechargeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(GaoYPhoneRechargeServiveImpl.class);


    @Autowired
    private GaoYPayRechargeRequest request;


    @Autowired
    private PhoneProductService productService;



    /**
     * 查询订单信息
     */
    public ResponseResult queryOrderById(RequestOrderInfoVo reqParams){
        //请求
        ResponseMessage message = request.queryOrderByOrderNo(reqParams.getOrderId());
        if(message.hasError()){
            LOGGER.info("#GY----> 获取订单信息出错。code:{} ",message.getCode());
            return new ResponseResult(SystemCodes.ERROR_CODE, message.getMessage(),message.getCode());
        }

        //返回值转换
        PostResult postResult = message.parseValueToObject(PostResult.class);

        //转换成具体统一对象
        OrderInfoVo orderInfoVo =  GaoYBeanParse.parseOrder(postResult);
        return new ResponseResult(orderInfoVo);

    }


    /**
     * 检查手机号码状态是否用
     * 
     * @param mobileNum 手机号码
     * @param price
     * @return
     * @throws BusinessException
     */
    private Product checkPhoneAvailable(String mobileNum, int price) throws BusinessException{
        //查询归属地、运营商信息
        ResponseMessage resultMessage = request.numberSegment(mobileNum);
        
        if(!resultMessage.hasError()){
            Mobile mobile = resultMessage.parseValueToObject(Mobile.class);
            LOGGER.info("#GY----高阳查询号码段信息接口返回参数:{}",JSON.toJSONString(mobile));
            
            //查数据库
            PhoneRechargeProduct rechargeProduct = productService.getProductOfMobilePhone(mobile.getProvinceName(), mobile.getSpType(), price);
            if(rechargeProduct != null){
                Product product = new Product();
                BeanUtils.copyProperties(rechargeProduct, product);
                return product;
            }else{
                throw new BusinessException(SystemCodes.ERROR_CODE+"", "没有找到相关产品");
            }

        }else{
            LOGGER.info("#GY----mobile:{}获取不到号码段。错误码:{}",mobileNum,resultMessage.getCode());

            throw new BusinessException(resultMessage.getCode(), resultMessage.getMessage());
        }
    }


    /**
     * 充值接口
     * @param reqParams
     * @return
     */
    @Override 
    public ResponseResult recharge(RequestRechargeVo reqParams){
        //检查手机状态是否可以用
        String orderNo = StringUtils.getOrderNo(Configurations.SP_TYPE_GY);
        LOGGER.info("#GY---------> 调用高阳充值接口，请求参数:{}",JSON.toJSONString(reqParams));

        ResponseResult responseResult =  new ResponseResult();
        Product product = null;
        try {
            product = checkPhoneAvailable(reqParams.getMobileNum(), reqParams.getPrice());
        } catch (BusinessException e) {
            LOGGER.error("#GY----查找相关商品信息出错",e);
            responseResult.setCode(e.getErrCode());
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setMessage(e.getErrMsg());
            return responseResult;
        }

        if(product == null){
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setMessage("没有找到相关充值商品");
            return responseResult;
        }


        //进行充值
        ResponseMessage reqeustResult =  request.recharge(orderNo, product.getProdId(), reqParams.getMobileNum());
        if(reqeustResult.hasError()){
            LOGGER.info("#GY------ 调用充值出错了,订单号：{}，code:{},message:{}",new Object[]{orderNo,reqeustResult.getCode(),reqeustResult.getMessage()});
            responseResult.setStatus(SystemCodes.ERROR_CODE);
            responseResult.setCode(reqeustResult.getCode());
            responseResult.setMessage(reqeustResult.getMessage());
            return responseResult;
        }

        //对象的转换
        PostResult postResult =  reqeustResult.parseValueToObject(PostResult.class);
        RechargeVo rechargeVo =  GaoYBeanParse.parseRechargeInfo(postResult);
        rechargeVo.setSellerPrice(product.getProdPrice()); //供货价

        //如果下单成功，显示充值中。；否则，充值失败。下单成功，不代表充值成功，成功以callback为准。
        LOGGER.info("#GY-------------> 高阳手机充值【同步返回】成功。订单号：{},mobile:{},price:{},status:{}",
                    new Object[]{orderNo,reqParams.getMobileNum(),reqParams.getPrice(),rechargeVo.getOrderStauts()});

        responseResult.setData(rechargeVo);
        return responseResult;
    }




    /**
     * 查询手机归属地、运营商
     */
    @Override
    public ResponseResult queryPhoneType(RequestQueryPhoneTypeVo params){
        ResponseMessage responseMessage = request.numberSegment(params.getMobileNum());
        if(responseMessage.hasError()){
            LOGGER.info("#GY-----> 获取手机号码归属地出错。code:{}，msg:{} ",responseMessage.getCode(),responseMessage.getMessage());
            return new ResponseResult(SystemCodes.ERROR_CODE, responseMessage.getMessage(),responseMessage.getCode());
        }

        Mobile mobile = responseMessage.parseValueToObject(Mobile.class);
        PhoneLocationVo phoneLocationVo =  GaoYBeanParse.parsePhoneLocation(mobile);

        return new ResponseResult(phoneLocationVo);
    }


    
    /**
     * 消息回调给VAS
     */
    @Override
    public void callback(RequestCallbackVo params) {
        LOGGER.info("#GY-----内部通知请求参数:{}",JSON.toJSONString(params));
        
        request.callback(params.getOrderId(),parseStatus(params.getStatus()),params.getMessage());
    }

    

    private int parseStatus(int status){
        if(status == 2){
            return Configurations.RechargeCallBackStatus.SUCCESS;
        }else if(status == 3){
            return Configurations.RechargeCallBackStatus.PARTSUCCESS;
        }else if(status ==  4){
            return Configurations.RechargeCallBackStatus.FAILED;
        }

        return Configurations.RechargeCallBackStatus.OTHER;
    }


    @Override
    public ResponseResult reversal(RequestReverseVo reqParams) {
        // TODO Auto-generated method stub
        return null;
    }



}
