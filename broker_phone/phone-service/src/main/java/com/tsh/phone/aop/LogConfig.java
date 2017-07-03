package com.tsh.phone.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 日志注解
 *
 * @author zengzw
 * @date 2017年2月21日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
@Component
public @interface LogConfig { 
    
    String description()  default "";   
    
    
    /**
     * 默认Get 请求
     * @return
     */
    String httpType() default "GET";
    
    
    /**
     * 默认Http请求
     * @return
     */
    int logType() default 1;
    
    
    /**
     * 供应商类型
     * 
     * @return
     */
    String supplierType() default "";
    
}  