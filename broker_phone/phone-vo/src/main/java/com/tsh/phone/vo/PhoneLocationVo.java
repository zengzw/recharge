/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo;

import java.io.Serializable;

/**
 * 手机号码归属地对象
 * 
 * 内容格式： {"provinceName":"广东","type":"移动"}
 * 
 * 
 * @author zengzw
 * @date 2016年8月10日
 */
public class PhoneLocationVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3024352650660739623L;
    

    /**
     * 归属地,省
     */
    private String provinceName;
    
    /**
     * 城市
     */
    private  String cityName;
    
    
    
    /**
     * 手机类型
     * 
     */      
    private String type;


    /**
     * 子编码
     */
    private String subBusinessCode;


    public String getCityName() {
        return cityName;
    }

    
    public String getProvinceName() {
        return provinceName;
    }

    
    
    public String getSubBusinessCode() {
        return subBusinessCode;
    }


    public String getType() {
        return type;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public void setSubBusinessCode(String subBusinessCode) {
        this.subBusinessCode = subBusinessCode;
    }


    public void setType(String type) {
        this.type = type;
    }
    
    
}
