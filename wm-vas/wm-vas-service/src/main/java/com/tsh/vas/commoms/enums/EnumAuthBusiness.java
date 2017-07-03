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
public enum EnumAuthBusiness {

    /**
     * 招工，优蓝
     */
    YOULAN("job","YL"),
    
    
    /**
     * 话费，一元免单
     */
    YYMD("VAS","YYMD"),
    
    
    /**
     * 信息展示
     */
    XXZS("VAS","XXZS");
    
    
    
    private  EnumAuthBusiness(String projectCode,String businessCode){
        this.projectCode = projectCode;
        this.businessCode = businessCode;
    }
    
 
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    private String projectCode;
    
    private String businessCode;
    
}
