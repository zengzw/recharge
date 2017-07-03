/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.enums;

/**
 *  权限枚举类
 *      
 *  
 * @author zengzw
 * @date 2017年5月9日
 */
public enum EnumActivityStatus {

    /**
     * 招工，优蓝
     */
    NOT_START(1,"未开始"),
    
    
    /**
     * 话费，一元免单
     */
    STARTING(2,"进行中"),
    
    
    /**
     * 信息展示
     */
    ENDED(3,"已结束");
    
    
    
    private  EnumActivityStatus(int status,String desc){
        this.status = status;
        this.desc = desc;
    }
    
 
  
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    private int status;
    
    private String desc;
    
}
