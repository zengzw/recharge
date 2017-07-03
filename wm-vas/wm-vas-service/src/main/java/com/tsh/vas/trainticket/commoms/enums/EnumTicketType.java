package com.tsh.vas.trainticket.commoms.enums;
/**
 * 车票类型枚举
 * @version 1.0.0 2016年12月9日<br>
 * @see 
 * @since JDK 1.7.0
 */
public enum EnumTicketType {
	CRP(0,"成人票"),
	RTP(1,"儿童票");
	
	private int type;

    private String name;
    
    /**
     * 
     */
    EnumTicketType(int setType,String name) {
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
    
    public static EnumTicketType getEnume(Integer code) {
    	if(null == code){
    		return CRP;
    	}
        for (EnumTicketType item : values ()) {
            if (item.getType() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}

