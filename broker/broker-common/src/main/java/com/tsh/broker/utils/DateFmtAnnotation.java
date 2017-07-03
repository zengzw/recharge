package com.tsh.broker.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 日期格式化注解
 * <li>@author Leejean
 * <li>@create 2014-6-24 上午11:48:40
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFmtAnnotation {
	/**
	 * 格式化模板
	 * yyyy-MM-dd HH:mm:ss
	 * <br>更多模板参考Data类相关API
	 * @author Leejean
	 * @create 2014-7-3下午04:04:05
	 * @return 模板字符串
	 */
	public String fmtPattern();
}
