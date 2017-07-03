/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.phone.util.HttpUtils;
import com.tsh.phone.util.StringUtils;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.RechargeVo;
import com.tsh.phone.vo.RespReversalInfoVo;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;
import com.tsh.phone.vo.sign.PhoneRequestSingUtils;
import com.tsh.vas.commoms.config.PhoneUrlConfig;
import com.tsh.vas.commoms.utils.PhoneCommon;
import com.tsh.vas.phone.cache.CachePhoneProvider;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.vo.phone.PhoneMobileManagerVo;

/**
 *  调用broker api 业务逻辑类
 *  
 *  
 * @author zengzw
 * @date 2017年3月6日
 */

@Service
public class APIPhoneBrokerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(APIPhoneBrokerService.class);


    /**
     * 可用供应商缓存 服务类
     */
    @Autowired
    private CachePhoneProvider cachePhoneProvider;


    /**
     * 号码归属地管理服务
     */
    @Autowired
    private VasMobileManagerService mobileManagerService;

//    private final byte[] Lock = new byte[0];



    /**
     * 查看手机归属地
     * 
     * @param mobileNum 手机号码
     * @return  Result
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Result queryPhoneLocation(Result result,String mobileNum){

        try{
            //首先查询本地号码库
            PhoneMobileManagerVo phoneMobileManagerVo = mobileManagerService.queryByMobilenum(result, mobileNum).getData();
            if(phoneMobileManagerVo != null){
                LOGGER.info("#queryPhoneLocation-->mobile:{}从本地获取 归属地",mobileNum);

                PhoneLocationVo phoneLocationVo = new PhoneLocationVo();
                phoneLocationVo.setProvinceName(phoneMobileManagerVo.getMobileProvince());
                phoneLocationVo.setCityName(phoneMobileManagerVo.getMobileCity());
                if(StringUtils.isNotEmpty(phoneMobileManagerVo.getMobileSupplier())){
                    phoneLocationVo.setType(phoneMobileManagerVo.getMobileSupplier().substring(2,4));
                }
                result.setData(phoneLocationVo);
            }else{
                LOGGER.info("#queryPhoneLocation-->mobile:{}从API获取 归属地",mobileNum);
                //根据api查询归属地
                PhoneLocationVo phoneLocationVo = queryPhoneSegmentBySupplierAPI(result, mobileNum);

                //完善本地号码库(新增）
                if(phoneLocationVo != null){
                    LOGGER.info("#-->新增到本地库,api获取对象:{}",JSON.toJSONString(phoneLocationVo));
                    result.setData(phoneLocationVo);
                    this.addMobileSegment(mobileNum,phoneLocationVo);
                }
            }

        }catch(Exception e){
            LOGGER.error("#queryPhoneLocation-->获取手机类型出错",e);
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("内部错误");
        }

        return result;
    }



    /**
     *   添加号段库
     *   
     * @param phoneLocationVo
     */
    private void addMobileSegment(String mobile,PhoneLocationVo phoneLocationVo) {
        try{
            String mobileNum = mobile.substring(0,7);
            Result result = new Result();

            // 判断段号是否存在
            PhoneMobileManagerVo phoneMobile = mobileManagerService.queryByMobilenum(result, mobileNum).getData();
            if(null != phoneMobile){
                LOGGER.info("#addMobileSegment-->添加号码段库已经存在：{}", JSON.toJSON(phoneMobile));
                return ;
            }

            String mobileShort = mobile.substring(0,3);
            String supplierCountry = "中国";

            PhoneMobileManagerVo phoneMobileManagerVo = new PhoneMobileManagerVo();
            phoneMobileManagerVo.setMobileShort(mobileShort);
            phoneMobileManagerVo.setMobileNum(mobileNum);
            phoneMobileManagerVo.setMobileProvince(phoneLocationVo.getProvinceName());
            phoneMobileManagerVo.setMobileCity(phoneLocationVo.getCityName());
            if(phoneLocationVo.getType().length() > 2){
                phoneMobileManagerVo.setMobileSupplier(phoneLocationVo.getType());
            }else{
                phoneMobileManagerVo.setMobileSupplier(supplierCountry+phoneLocationVo.getType());
            }


            mobileManagerService.addMobileSegmentNo(result, phoneMobileManagerVo);

        }catch(Exception e){
            LOGGER.info("#addMobileSegment-->添加号码段库失败",e);
        }
    }



    /**
     * 根据接口查询归属地
     * 
     * @param result
     * @param mobileNum 号码查询
     * @throws Exception
     */
    private PhoneLocationVo queryPhoneSegmentBySupplierAPI(Result result, String mobileNum) throws Exception {
        //获取所有注册的服务商
        List<ServiceRegisterVo> lstRegister = cachePhoneProvider.queryCacheServiceRegister();
        if(CollectionUtils.isEmpty(lstRegister)){
            LOGGER.warn("#-->mobile:{},查询号段时没有可用供应商。",mobileNum);
            return null;
        }

        //循环调用服务商，如果有 一个成功，立马返回。否则，轮询下一个
        boolean success = false;
        Object data = null;


        for(int i = 0; i < lstRegister.size(); i++){
            //参数签名
            ServiceRegisterVo serviceRegisterVo = lstRegister.get(i);
            RequestQueryPhoneTypeVo requestVo = new RequestQueryPhoneTypeVo();
            requestVo.setMobileNum(mobileNum);
            requestVo.setMoney("50"); //为了兼容某些供应商查询,特意做处理
            String sign = PhoneRequestSingUtils.signQueryPhoneType(requestVo, serviceRegisterVo.getSignKey());
            requestVo.setSign(sign);

            //map 转换
            Map<String, Object> reqParams = ObjectMapUtils.toObjectMap(requestVo);
            String strResult = HttpUtils.doGet(serviceRegisterVo.getServiceAddr()+ PhoneUrlConfig.QUERY_PHONE_TYPE, reqParams);

            LOGGER.info("#apiPhoneBroker-- mobile:{},queryPhoneLocation：轮询调用供应商编号为:{}",mobileNum,serviceRegisterVo.getBusinessId());

            //结果转换
            ResponseResult responseResult = JSON.parseObject(strResult, ResponseResult.class);
            if(responseResult != null && !responseResult.hasError()){
                data = responseResult.getData();
                success = true;
                break;
            }else{
                success = false;
                lstRegister.remove(i);
                i--;
            }
        }

        if(success){
            PhoneLocationVo phoneLocationVo = JSON.parseObject(data.toString(),PhoneLocationVo.class);
            return phoneLocationVo;
        }

        return null;

    }



    /**
     * 查看手机归属地
     * 
     * @param  orderNo  外部订单号
     * @return  Result
     */
    public Result queryOrderInfo(Result result,String orderNo,ServiceRegisterVo serviceRegisterVo){
        try{
            //参数签名
            RequestOrderInfoVo requestVo = new RequestOrderInfoVo();
            requestVo.setOrderId(orderNo);
            String sign = PhoneRequestSingUtils.signQueryOrderInfo(requestVo, serviceRegisterVo.getSignKey());
            requestVo.setSign(sign);

            //map 转换
            Map<String, Object> reqParams = ObjectMapUtils.toObjectMap(requestVo);
            String strResult = HttpUtils.doGet(serviceRegisterVo.getServiceAddr() + PhoneUrlConfig.QUERY_ORDER_INFO, reqParams);

            //结果转换
            ResponseResult responseResult = JSON.parseObject(strResult, ResponseResult.class);
            if(responseResult != null && !responseResult.hasError()){
                result.setStatus(HttpResponseConstants.SUCCESS);
                result.setData(responseResult.getData());
            }else{
                result.setStatus(HttpResponseConstants.ERROR);
                if(responseResult != null)
                    result.setMsg(responseResult.getMessage());
            }

        }catch(Exception e){
            LOGGER.error("#APIPhoneService--> 获取手机类型出错",e);
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("内部错误");
        }

        return result;
    }


    /**
     * 充值
     * 
     * @param  RequestRechargeVo  充值请求对象
     * @return  Result
     */
    public Result recharge(Result result,RequestRechargeVo rechargeVo,ServiceRegisterVo serviceRegister){
        try{
            //参数签名
            String sign = PhoneRequestSingUtils.signRecharge(rechargeVo, serviceRegister.getSignKey());
            rechargeVo.setSign(sign);

            //map 转换
            Map<String, Object> reqParams = ObjectMapUtils.toObjectMap(rechargeVo);
            String strResult = HttpUtils.doPost(serviceRegister.getServiceAddr() + PhoneUrlConfig.RECHARGE, reqParams);

            //结果转换
            ResponseResult responseResult = JSON.parseObject(strResult, ResponseResult.class);
            if(responseResult != null && !responseResult.hasError()){
                //返回不会空，再判断对象里面的充值状态是否下单成功
                RechargeVo resultRecharge = JSON.parseObject(responseResult.getData().toString(),RechargeVo.class);
                if(resultRecharge.getOrderStauts().equals(PhoneCommon.RechargeStatus.SUCCESS)){
                    result.setStatus(HttpResponseConstants.SUCCESS);
                    result.setData(resultRecharge);

                    LOGGER.info("#--手机号码：{}，调用充值接口返回成功.data:{}",rechargeVo.getMobileNum(),JSON.toJSONString(resultRecharge));
                }else{
                    result.setStatus(HttpResponseConstants.ERROR);
                    result.setMsg("充值失败！");

                    LOGGER.info("#--手机号码：{}，调用充值接口返回失败",rechargeVo.getMobileNum());
                }
                return result;
            }else{
                result.setStatus(HttpResponseConstants.ERROR);
                if(responseResult != null)
                    result.setMsg(responseResult.getMessage());

                LOGGER.info("#--手机号码：{}，调用充值接口返回失败。msg:{}",rechargeVo.getMobileNum(),(responseResult==null ? "" : responseResult.getMessage()));
                return result;
            }

        }catch(Exception e){
            LOGGER.error("#APIPhoneService--> 充值出错,手机号码:"+rechargeVo.getMobileNum(),e);
            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setMsg("内部错误");
            return result;
        }


    }


    /**
     * 取消充值
     * 
     * @param  RequestRechargeVo  充值请求对象
     * @return  Result
     */
    public Result reversal(Result result,RequestReverseVo rechargeVo,ServiceRegisterVo serviceRegister){
        try{
            //参数签名
            String sign = PhoneRequestSingUtils.signReverse(rechargeVo, serviceRegister.getSignKey());
            rechargeVo.setSign(sign);

            //map 转换
            Map<String, Object> reqParams = ObjectMapUtils.toObjectMap(rechargeVo);
            String strResult = HttpUtils.doPost(serviceRegister.getServiceAddr() + PhoneUrlConfig.REVERSAL, reqParams);

            //结果转换
            ResponseResult responseResult = JSON.parseObject(strResult, ResponseResult.class);
            if(responseResult != null && !responseResult.hasError()){
                //返回不会空，再判断对象里面的充值状态是否下单成功
                RespReversalInfoVo resultRecharge = JSON.parseObject(responseResult.getData().toString(),RespReversalInfoVo.class);
                if(resultRecharge.getStatus().equals(PhoneCommon.ReversalStatus.SUCCESS)){
                    result.setStatus(HttpResponseConstants.SUCCESS);
                    result.setData(resultRecharge);

                    LOGGER.info("#--手机号码：{}，调用取消充值接口返回成功.data:{}",rechargeVo.getMobileNum(),JSON.toJSONString(resultRecharge));
                }else{
                    result.setStatus(HttpResponseConstants.ERROR);
                    result.setMsg("充值失败！");

                    LOGGER.info("#--手机号码：{}，调用取消充值接口返回失败",rechargeVo.getMobileNum());
                }
                return result;
            }else{
                result.setStatus(HttpResponseConstants.ERROR);
                if(responseResult != null)
                    result.setMsg(responseResult.getMessage());

                LOGGER.info("#--手机号码：{}，调用取消充值接口返回失败。msg:{}",rechargeVo.getMobileNum(),(responseResult==null ? "" : responseResult.getMessage()));
                return result;
            }

        }catch(Exception e){
            LOGGER.error("#APIPhoneService--> 取消充值出错,手机号码:"+rechargeVo.getMobileNum(),e);
            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setMsg("内部错误");
            return result;
        }


    }



}
