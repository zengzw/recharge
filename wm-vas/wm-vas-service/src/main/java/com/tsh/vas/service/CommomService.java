/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.openpf.utils.ServRegisterSignUtils;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.phone.util.HttpUtils;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.diamond.TshDiamondClient;

/**
 *   公共服务网类
 * 
 * @author zengzw
 * @date 2017年3月3日
 */
@Service
public class CommomService {


    private final static Logger LOGGER = LoggerFactory.getLogger(CommomService.class);


    /**
     * 获取单个服务商
     * 
     * 
     * @param result
     * @param supplierCode 供应商编码
     * @param businessCode 公司编号
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public ServiceRegisterVo getServiceRegisterVo(Result result, String supplierCode,EnumBusinessCode businessCode) throws Exception{
        if(businessCode == null){
            throw new IllegalArgumentException("businessCode 参数为空");
        }


        BizzServiceRequest bizzServiceRequest = new BizzServiceRequest ();
        bizzServiceRequest.setBusinessId (supplierCode);
        bizzServiceRequest.setBusinessCode(businessCode.name());
        bizzServiceRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        String signedKey = ServRegisterSignUtils.signQueryBizzService (bizzServiceRequest, TshDiamondClient.getInstance ().getConfig ("openpf.signKey"));
        bizzServiceRequest.setSigned (signedKey);

        Map<String, Object> map = ObjectMapUtils.toObjectMap (bizzServiceRequest);
        String url = TshDiamondClient.getInstance ().getConfig ("openpf-web") + "/openpf/queryBizzService.do";
        String responseKey = HttpUtils.doPost(url, map);
        if (StringUtils.isEmpty (responseKey)) {
            LOGGER.error ("调用开放平台的秘钥服务错误：" + responseKey);
            throw new BusinessRuntimeException("", "调用开放平台的秘钥服务错误");
        }

        ResponseWrapper responseWrapperKey = JSONObject.parseObject (responseKey, ResponseWrapper.class);
        if (responseWrapperKey.getStatus () != 0) {
            LOGGER.error ("调用开放平台的秘钥服务返回错误状态" + responseWrapperKey.getMessage ());
            throw new BusinessRuntimeException("", responseWrapperKey.getMessage ());
        }

        if(responseWrapperKey.getData() == null){
            LOGGER.error ("调用开放平台的秘钥服务返回data数据null：" + responseKey);
            throw new BusinessRuntimeException("", "调用开放平台的秘钥服务返回data数据null");
        }

        ServiceRegisterVo serviceRegister = JSON.parseObject(responseWrapperKey.getData().toString(),ServiceRegisterVo.class);
        if(serviceRegister == null){
            LOGGER.error ("调用开放平台的秘钥服务返回错误数据null：" + responseKey);
            throw new BusinessRuntimeException("", "调用开放平台的秘钥服务返回错误数据null");
        }

        return serviceRegister;
    }



    /**
     * 获取服务商列表
     * 
     * 
     * @param result
     * @param businessCode 供应商编码不能为空
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes"})
    public List<ServiceRegisterVo> getListServiceRegisterVo(Result result,EnumBusinessCode businessCode) throws Exception {
        if(businessCode == null){
            throw new IllegalArgumentException("businessCode 参数为空");
        }

        BizzServiceRequest bizzServiceRequest = new BizzServiceRequest ();
        bizzServiceRequest.setBusinessCode(businessCode.name());
        bizzServiceRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        String signedKey = ServRegisterSignUtils.signQueryBizzService (bizzServiceRequest, TshDiamondClient.getInstance ().getConfig ("openpf.signKey"));
        bizzServiceRequest.setSigned (signedKey);
        Map<String, Object> map = ObjectMapUtils.toObjectMap(bizzServiceRequest);

        String url = TshDiamondClient.getInstance ().getConfig ("openpf-web") + "/openpf/queryListBizzService.do"; 
        String responseKey = HttpUtils.doPost(url, map);
        if (StringUtils.isEmpty (responseKey)) {
            LOGGER.error ("调用开放平台的秘钥服务错误：" + result);
            throw new FunctionException(result, "调用开放平台的秘钥服务错误");
        }

        ResponseWrapper responseWrapperKey = JSONObject.parseObject (responseKey, ResponseWrapper.class);
        if (responseWrapperKey.getStatus () != 0) {
            LOGGER.error ("调用开放平台的秘钥服务返回错误状态" + responseWrapperKey.getMessage ());
            throw new FunctionException (result, responseWrapperKey.getMessage ());
        }

        if(responseWrapperKey.getData() == null){
            LOGGER.error ("调用开放平台的秘钥服务返回错误数据null：" + result);
            throw new FunctionException(result, "调用开放平台的秘钥服务返回错误数据null");
        }


        List<ServiceRegisterVo> lstServiceRegister = JSON.parseObject(responseWrapperKey.getData().toString(), new TypeReference<List<ServiceRegisterVo>>(){});
        if(CollectionUtils.isEmpty(lstServiceRegister)){
            LOGGER.error ("调用开放平台的秘钥服务返回错误数据null：" + result);
            throw new FunctionException(result, "调用开放平台的秘钥服务返回错误数据null");
        }

        return lstServiceRegister;
    }
    
    
    public void test() throws FunctionException{
//     throw new BusinessRuntimeException("", "测试异常");    
     throw new FunctionException(new Result(), "测试异常");    
    }

}
