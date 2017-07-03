/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  解决Ajax 跨越的问题
 *
 * @author zengzw
 * @date 2017年5月23日
 */
@WebFilter(filterName="corsFilter",urlPatterns="/app/vas/phone/*")
public class CORSFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("#------CORSFilter Filter----");

        //Access-Control-Allow-Credentials: true | false // 控制是否开启与Ajax的Cookie提交方式
        
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*"); // 授权的源控制
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"); // 允许请求的HTTP Method
        resp.setHeader("Access-Control-Max-Age", "3600");  // 授权的时间
        resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); // 控制哪些header能发送真正的请求    
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
