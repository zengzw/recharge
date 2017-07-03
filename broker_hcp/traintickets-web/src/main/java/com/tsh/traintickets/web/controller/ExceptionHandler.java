package com.tsh.traintickets.web.controller;

import com.traintickets.common.ResponseCode;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.web.controller.kuyou.query.QuerySubwayStationController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 *
 * Created by Administrator on 2016/12/1 001.
 */

@Controller
public class ExceptionHandler implements HandlerExceptionResolver {

    private static Logger logger = LogManager.getLogger(QuerySubwayStationController.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("=======ExceptionHandler catch", ex);
        if(ex instanceof HttpRequestMethodNotSupportedException){
            return new ModelAndView("redirect:/errorHandler.do?code=" + ResponseCode.NOT_SUPPORT_ERROR.getCode());
        }

        return new ModelAndView("redirect:/errorHandler.do?code=" + ResponseCode.SYSTEM_ERROR.getCode());
    }

    @RequestMapping(value = "/errorHandler.do")
    @ResponseBody
    public BaseResponse errorHandler(String code) {
        logger.info("=======ExceptionHandler code:"+ code);
        if(StringUtils.isNotEmpty(code)){
            return ResponseBuilder.buildError(ResponseCode.get(Integer.valueOf(code)));
        }

        return ResponseBuilder.buildError(ResponseCode.SYSTEM_ERROR);
    }
}
