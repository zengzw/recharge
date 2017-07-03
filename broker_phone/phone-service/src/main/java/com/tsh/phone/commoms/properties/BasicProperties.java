/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.properties;

import java.util.Properties;

/**
 *  不同业务文件业务配置读取 抽象类
 *  
 *   用于把第三方返回的错误码转换成为 具体文字说明。
 *  
 * @author zengzw
 * @date 2017年2月20日
 */
public abstract class BasicProperties {

    
   public abstract Properties load();


   public abstract String getValue(String key);

}
