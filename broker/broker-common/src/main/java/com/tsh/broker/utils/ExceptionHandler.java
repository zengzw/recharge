package com.tsh.broker.utils;


/**
 * ExceptionHelper
 *
 * @author dengjd
 * @date 2016/7/11
 */
public class ExceptionHandler {

    public static  String getErrorMessage(Throwable ex) {
        if(ex == null) return "";

        Throwable next = ex;
        while (next.getCause() != null) {
            next = next.getCause();
        }

        return next.getMessage();
    }

}
