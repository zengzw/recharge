/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.commoms.enums;

/**
 *
 * @author zengzw
 * @date 2016年11月29日
 */
public enum EnumSeatType {
    
    BUSINESS_CLASS_SEAT(0,"商务座"),
    
    SUPERIOR__SEaT(1,"特等座"),
    
    FIRST_CLASS_SEAT(2,"一等座"),
    
    SECOND_CLASS_SEAT(3,"二等座"),
    
    LUXURY_SOFT_SLEEPER(4,"高级软卧"),
    
    SOFT_SLEEPER(5,"软卧"),
    
    HARD_SLEEPER(6,"硬卧"),
    
    SOFT_SEAT(7,"软座"),
    
    HARD_SEAT(8,"硬座"),
    
    STAND(9,"无座"),
    
    OTHER(10,"无座");
    
    
    
    private int type;

    private String name;

    /**
     * 
     */
     EnumSeatType(int setType,String name) {
        this.type = setType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    
    public static EnumSeatType getEnume(Integer code) {
    	if(null == code){
    		return HARD_SEAT;
    	}
        for (EnumSeatType item : values ()) {
            if (item.getType() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
