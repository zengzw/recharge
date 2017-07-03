package com.tsh.broker.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数字精度注解
 * <li>@author Leejean
 * <li>@create 2014-6-24 上午11:48:40
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberFmtAnnotation {
	/**
	 * 小数位数
	 * <br>"0","1","2"
	 * @author Leejean
	 * @date 2014-7-3下午04:05:25
	 * @return 小数点位数
	 */
	public int scale();
}
