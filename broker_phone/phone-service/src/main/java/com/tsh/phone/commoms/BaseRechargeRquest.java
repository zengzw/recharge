/*
 * Use, Copy is subject to authorized license.
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 */
package com.tsh.phone.commoms;


import com.tsh.phone.commoms.exceptions.SystemCodes;
import com.tsh.phone.commoms.properties.BasicProperties;

/**
 * 基础请求
 * 
 * @author zengzw
 * @date 2016年7月26日
 */
public abstract class BaseRechargeRquest {

    /**
     * 请求类型
     */
    public abstract BasicProperties getProperteis(); 

   
    /**
     * 获取错误对象
     * 
     * @param code
     * @param value
     * @return
     */
    public  ResponseMessage getErrorMessage(String code,String inParameters){
        //根据Code 获取message
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(code);
        resultMessage.setMessage(getPropertiesMessage(code));
        resultMessage.setInParametes(inParameters);
        
        return resultMessage;
    }

    /**
     * 获取错误对象
     * 
     * @param code
     * @param value
     * @return
     */
    public  ResponseMessage getErrorMessage(String code,String inParameters,String outParameters){
        //根据Code 获取message
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(code);
        resultMessage.setMessage(getPropertiesMessage(code));
        resultMessage.setInParametes(inParameters);
        resultMessage.setOutParmeters(outParameters);

        return resultMessage;
    }

    /**
     * 读取资源文件
     * 
     * @param type
     * @param code
     * @return
     */
    private  String getPropertiesMessage(String code){
        BasicProperties properties = getProperteis();
        return properties.getValue(code);
    }




    /**
     * 获取错误对象
     * 
     * @param code
     * @param value
     * @return
     */
    public  ResponseMessage getErrorMessage(String code){
        //根据Code 获取message
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(code);
        resultMessage.setMessage("");
        return resultMessage;
    }


    /**
     * 获取成功对象
     * @param value
     * @return
     */
    public  ResponseMessage getSuccessMessage(String value){
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(String.valueOf(SystemCodes.SUCCESS_CODE));
        resultMessage.setValue(value);

        return resultMessage;
    }



    /**
     * 获取成功对象
     * 
     * @param value
     * @return
     */
    public  ResponseMessage getSuccessMessage(String value,String inParametes){
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(String.valueOf(SystemCodes.SUCCESS_CODE));
        resultMessage.setValue(value);
        resultMessage.setInParametes(inParametes);

        return resultMessage;
    }
    
    /**
     * 获取成功对象
     * 
     * @param value
     * @return
     */
    public  ResponseMessage getSuccessMessage(String value,String inParametes,String outParamters){
        ResponseMessage resultMessage = new ResponseMessage();
        resultMessage.setCode(String.valueOf(SystemCodes.SUCCESS_CODE));
        resultMessage.setValue(value);
        resultMessage.setInParametes(inParametes);
        resultMessage.setOutParmeters(outParamters);
        
        return resultMessage;
    }
}
