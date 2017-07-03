package com.tsh.vas.trainticket.commoms.enums;

/**
 *  退票状态 
 *   
 */
public enum EnumReturnTicketStatus {
    /**
     * 待票中
     */
    NON_REFUND ("待票中", 1),
    
    /**
     * 退票中
     */
    RETURNING ("退票中", 2),
    
    /**
     * 退票失败
     */
    REFUND_FAIL ("退票失败 ", 5),
    
    /**
     * 退票成功
     */
    RETURN_SUCCESS ("退票成功", 13),
    
    /**
     * 关闭
     */
    CLOSE("关闭", 14);

    private String desc;
    private Integer code;
    //    private String clientDesc;

    EnumReturnTicketStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }


    public static EnumReturnTicketStatus getEnume(Integer code) {
        for (EnumReturnTicketStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
