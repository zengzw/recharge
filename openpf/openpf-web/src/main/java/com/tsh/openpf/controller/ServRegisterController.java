package com.tsh.openpf.controller;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.broker.utils.ExceptionHandler;
import com.tsh.openpf.config.GlobalConfig;
import com.tsh.openpf.service.ServRegisterService;
import com.tsh.openpf.utils.ServRegisterSignUtils;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.openpf.vo.common.ResponseWrapper;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.openpf.vo.request.RegisterBizzServRequest;

/**
 * ServiceRegisterController
 *
 * @author dengjd
 * @date 2016/10/11
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/openpf")
public class ServRegisterController extends BaseController {
    private static Logger logger = LogManager.getLogger(ServRegisterController.class);

    @Resource(name = "servRegisterService")
    private ServRegisterService servRegisterService;
    @Resource(name = "globalConfig")
    private GlobalConfig globalConfig;
    private ServRegisterValidator servRegisterValidator = new ServRegisterValidator();

    /**
     * 注册供应商服务
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param bizzServiceRequest
     * @return
     */
    @RequestMapping(value = "/createBizzService.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper createBusinessService(BizzServiceRequest bizzServiceRequest){
        ResponseWrapper responseWrapper = servRegisterValidator.validateCreateBizzService(bizzServiceRequest, globalConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString(responseWrapper));
            return responseWrapper;
        }
        try {
            String remark = "";
            String bizzId = bizzServiceRequest.getBusinessId();
            servRegisterService.addBusinessService(bizzId,remark);
        } catch (Exception ex) {
            responseWrapper.setStatus (10002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage(ex));
            logger.error ("创建供应商注册服务异常", ex);
        }

        return responseWrapper;
    }
    
    
    
    
    /**
     * 查询供应商注册的服务
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param bizzServiceRequest
     * @return
     */
    @RequestMapping(value = "/queryBizzService.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryBizzService(BizzServiceRequest bizzServiceRequest){
        ResponseWrapper responseWrapper = servRegisterValidator.validateQueryBizzService(bizzServiceRequest, globalConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString(responseWrapper));
            return responseWrapper;
        }

        try {
            String bizzId = bizzServiceRequest.getBusinessId();
            String bizzCode = bizzServiceRequest.getBusinessCode();
            
            Result result = servRegisterService.queryBusinessService(bizzId,bizzCode);
            responseWrapper.setData(result.getData());
        } catch (Exception ex) {
            responseWrapper.setStatus (20002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage(ex));
            logger.error ("查询供应商注册服务异常", ex);
        }

        return responseWrapper;
    }
    
    
    
    /**
     * 查询供应商注册的服务
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param bizzServiceRequest
     * @return
     */
    @RequestMapping(value = "/queryListBizzService.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper queryListBizzService(BizzServiceRequest bizzServiceRequest){
        ResponseWrapper responseWrapper = servRegisterValidator.validateQueryBizzService(bizzServiceRequest, globalConfig);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString(responseWrapper));
            return responseWrapper;
        }
        
        try {
            String bizzCode = bizzServiceRequest.getBusinessCode();
            Result result = servRegisterService.queryListBusinessService(bizzCode);
            responseWrapper.setData(result.getData());
        } catch (Exception ex) {
            responseWrapper.setStatus (20002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage(ex));
            logger.error ("查询供应商注册服务异常", ex);
        }
        
        return responseWrapper;
    }
    
    
    
    
    @RequestMapping(value = "/registerBizzService.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper registerBizzService(RegisterBizzServRequest registerBizzServRequest)  {
        ResponseWrapper responseWrapper = servRegisterValidator.validateRegisterBizzService(registerBizzServRequest);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("校验参数错误," + JSONObject.toJSONString(responseWrapper));
            return responseWrapper;
        }
        try {
            String bizzId = registerBizzServRequest.getBusinessId();
            Result result  = servRegisterService.queryBusinessService(bizzId);
            ServiceRegisterVo serviceRegisterVo = result.getData();

            if(serviceRegisterVo == null){
                responseWrapper.setStatus(30001);
                responseWrapper.setMessage("供应商信息错误");
                return responseWrapper;
            }

            String signed = ServRegisterSignUtils.signRegisterBizzService(registerBizzServRequest, serviceRegisterVo.getSignKey());
            if(!signed.equals(registerBizzServRequest.getSigned())){
                responseWrapper.setStatus(30003);
                responseWrapper.setMessage("签名不正确");
                return responseWrapper;
            }

            String serviceAddr = registerBizzServRequest.getServiceAddr();
            servRegisterService.registerServiceAddr(bizzId, serviceAddr);
        } catch (Exception ex) {
            responseWrapper.setStatus (30002);
            responseWrapper.setMessage (ExceptionHandler.getErrorMessage(ex));
            logger.error ("注册供应商服务地址异常", ex);
        }

        return responseWrapper;
    }

}
