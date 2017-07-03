package com.tsh.phone.commoms.enums;

/**
 * 
 * 日志类型枚举
 * 
 *
 * @author zengzw
 * @date 2017年2月20日
 */
public enum EnumLogType {

    HTTP(1, "http日志"),
    
    DUBBO(2, "dubbo日志"),
    
    MQ(3, "mq日志"),
    
    APPLICATION(4, "应用日志");

    private Integer code;
    private String msg;

    private EnumLogType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    
    public static EnumLogType getByCode(int code ){
        for(EnumLogType supplierType : EnumLogType.values()){
            if(supplierType.getCode().intValue() == code){
                return supplierType;
            }
        }
        
        return null;
    }
  
}
