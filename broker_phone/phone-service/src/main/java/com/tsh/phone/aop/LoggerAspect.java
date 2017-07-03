/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.commoms.ResponseMessage;
import com.tsh.phone.service.IProcessingRecordLogService;

/**
 * 日志切面
 * 
 * 用于记录与第三方请求的日志信息
 * 
 * 
 * @author zengzw
 * @date 2017年2月20日
 */

@Component
@Aspect
public class LoggerAspect {


    private final static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     * 日志记录服务类
     * 
     */
    @Autowired
    private IProcessingRecordLogService loggerService;


    /*   
     * 
     * 添加业务逻辑方法切入点 
     *  @Pointcut("@annotation(com.tsh.phone.apo.LogConfig)")    
     */
    /* @Pointcut("execution(* com.tsh.phone.service..*(..))")  
    public void beforeCall() { }  
     */



    /**
     *  aop around增强，用于日志记录
     *  
     * ("execution(* com.tsh.phone.recharge..*.*(..)) && @annotation(config)")   更具体的限制
     *  
     *  
     * @param pjp  链接点
     * @param config 自定义注解对象r
     * @return 具体返回执行后的结果
     */
   @Around("@annotation(config)")  
    public Object arroundCallCalls(ProceedingJoinPoint pjp,LogConfig config){  
        long startTime = System.currentTimeMillis();
        Object[] argsValue = pjp.getArgs(); // 获取被切函数 参数值
        String methodName = pjp.getSignature().getName(); //获取到请求方法名称 

        //获取到请求参数名称
        CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
        String[] argNames = codeSignature.getParameterNames();

        String reqeustParamsters = generateParamsString(argsValue,argNames);
        System.out.println("reqeustParams:"+reqeustParamsters);

        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable e) {
            logger.error("***********日志记录报错*************",e);
        } 

        long endTime = System.currentTimeMillis();

        if(object != null){
            String reqeustMessage = "";
            String responseMessage = "";
            if(object instanceof ResponseMessage){
                ResponseMessage resp  = (ResponseMessage)object;
                reqeustMessage = resp.getInParametes();
                responseMessage = resp.getOutParmeters();
            }else{
                reqeustMessage =   reqeustParamsters;
                responseMessage = JSON.toJSONString(object);
            }

            loggerService.saveLog(reqeustMessage,responseMessage, config.supplierType(),config.httpType(),config.logType(), methodName,(endTime-startTime));
            
        }else{
            
            loggerService.saveLog(reqeustParamsters,"", config.supplierType(),config.httpType(),config.logType(), methodName,(endTime-startTime));
        }

        return object;
    }


    
    
    /*
     * 将请求参数、参数值转换成json
     */
    public String generateParamsString(Object[] argsValue,Object[] args){
        Map<Object, Object> params = new HashMap<>();
        for(int i = 0; i<args.length; i++){
            params.put(args[i], argsValue[i]);
        }

        return JSON.toJSONString(params);
    }


}
