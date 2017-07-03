package com.tsh.openpf.controller;

import org.apache.commons.lang.StringUtils;

import com.tsh.openpf.config.GlobalConfig;
import com.tsh.openpf.utils.RegexValidateUtils;
import com.tsh.openpf.utils.ServRegisterSignUtils;
import com.tsh.openpf.vo.common.ResponseWrapper;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.openpf.vo.request.RegisterBizzServRequest;

/**
 * ServRegisterValidator
 *
 * @author dengjd
 * @date 2016/10/12
 */
@SuppressWarnings("all")
public class ServRegisterValidator {

    public ResponseWrapper validateCreateBizzService(BizzServiceRequest bizzServiceRequest, GlobalConfig globalConfig){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(StringUtils.isBlank(bizzServiceRequest.getBusinessId())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("供应商编号不能为空");
            return responseWrapper;
        }
        if(!RegexValidateUtils.isValidTimestampFormat(bizzServiceRequest.getTimestamp())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }
        String signed = ServRegisterSignUtils.signCreateBusinessService(bizzServiceRequest, globalConfig.getSignKey());
        if(!signed.equals(bizzServiceRequest.getSigned())){
            responseWrapper.setStatus(10001);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateQueryBizzService(BizzServiceRequest bizzServiceRequest, GlobalConfig globalConfig) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
       /* if(StringUtils.isBlank(bizzServiceRequest.getBusinessId())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("供应商编号不能为空");
            return responseWrapper;
        }
        
        if(StringUtils.isBlank(bizzServiceRequest.getBusinessCode())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("供应商业务编码为空");
            return responseWrapper;
        }*/
        
        
        
        if(!RegexValidateUtils.isValidTimestampFormat(bizzServiceRequest.getTimestamp())){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }
        String signed = ServRegisterSignUtils.signQueryBizzService(bizzServiceRequest, globalConfig.getSignKey());
        if(!signed.equals(bizzServiceRequest.getSigned())){
            responseWrapper.setStatus(20003);
            responseWrapper.setMessage("签名不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }

    public ResponseWrapper validateRegisterBizzService(RegisterBizzServRequest registerBizzServRequest) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if(StringUtils.isBlank(registerBizzServRequest.getBusinessId())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("供应商编号不能为空");
            return responseWrapper;
        }
        if(StringUtils.isBlank(registerBizzServRequest.getServiceAddr())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("服务地址不能为空");
            return responseWrapper;
        }
        if(!RegexValidateUtils.isValidUrl(registerBizzServRequest.getServiceAddr())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("服务地址不合法");
            return responseWrapper;
        }

        if(!RegexValidateUtils.isValidTimestampFormat(registerBizzServRequest.getTimestamp())){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("时间戳格式不正确");
            return responseWrapper;
        }

        return responseWrapper;
    }
}
