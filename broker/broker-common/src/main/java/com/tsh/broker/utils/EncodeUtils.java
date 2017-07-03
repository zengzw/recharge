package com.tsh.broker.utils;

import java.io.UnsupportedEncodingException;

/**
 * EncodeUtils
 *
 * @author dengjd
 * @date 2016/10/23
 */
public class EncodeUtils {

    public static String setCharacterEncoding(String org, String fromEncode, String toEncode){
        String ret = "";
        try {
            byte[] b = org.getBytes(fromEncode);
            ret  = new String(b,toEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
