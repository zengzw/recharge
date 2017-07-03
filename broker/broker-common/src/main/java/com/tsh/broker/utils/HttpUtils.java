package com.tsh.broker.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


/**
 * HTTP 请求工具类
 * 
 *
 * @author zengzw
 * @date 2017年2月16日
 */
public class HttpUtils {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static final String charset_utf8 = "UTF-8";	
    public static final String charset_gbk = "GBK";
    public static final String charset_gb2312 = "GB2312";
    
    public static final String CONTENTTYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENTTYPE_JSON = "application/json";
    
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    
    
    
    

    public static ThreadLocal<String> ipLocal = new ThreadLocal<String>();


    public static String post(String path, String method, String params,String contentType,String charset) {

        HttpURLConnection conn = null; 
        DataOutputStream outStream = null;
        InputStream in = null;
        InputStreamReader reader = null;

        try {
            logger.info(String.format("request-->Path[%s], RequestMethod[%s],RequestBody[%s]", path, method,
                    URLDecoder.decode((StringUtils.isEmpty(params)? "":params), charset_utf8)));
            
            boolean isPost = true;
            if (method.equals("GET")) {
                isPost = false;
                if (params != null && !params.equals(""))
                    path += "?" + params;
            }

            URL url = new URL(path);

            logger.info("path:"+path);

            InetAddress address = InetAddress.getByName(url.getHost());
            String ip = address.getHostAddress();
            ipLocal.set(ip);
            conn = (HttpURLConnection) url.openConnection();

            // TODO 连接超时的设置
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestMethod(method);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", charset);

            if (isPost) {
                byte[] data = params.getBytes(charset);
                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                conn.setRequestProperty("Content-Type", contentType);
                outStream = new DataOutputStream(conn.getOutputStream());
                outStream.write(data);
                outStream.flush();
            }

            in = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            reader = new InputStreamReader(in, charset);

            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) > 0) {
                sb.append(buff, 0, len);
            }

            logger.info(String.format("response-->Path[%s], requestParma[%s],ResponseCode[%s], ResponseBody[%s]", path,
                    URLDecoder.decode((StringUtils.isEmpty(params)? "":params), charset), conn.getResponseCode(), sb.toString().replace("\n", "")));			
            if (conn.getResponseCode() == 200) {
                if (!sb.toString().equals("")) {
                    return sb.toString();
                } else {
                    logger.error("Response empty string.");
                }
            } else {
                logger.warn("Response not 200 - " + conn.getResponseCode() + ", " + conn.getResponseMessage());
            }
        } catch (IOException ioe) {

            ioe.printStackTrace();
            logger.error("**********" + ioe);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(String.format("Path[%s], Error[%s]", path, e.getMessage()));
        }finally {
            try {
                if(conn != null){
                    conn.disconnect();
                }
                if(outStream != null){

                    outStream.close();
                }
                if(in != null){
                    in.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    
    public static String doPost(String path, Map<String, Object> params,String charset) {
        return post(path, METHOD_POST, parse(params,charset),CONTENTTYPE_FORM,charset);
    }
    
    
    public static String doPost(String path, Map<String, Object> params) {
        return post(path,METHOD_POST, parse(params,charset_utf8),CONTENTTYPE_FORM,charset_utf8);
    }

    
    public static String doGet(String path, Map<String, Object> params,String charset) {
        return post(path, METHOD_GET, parse(params,charset),CONTENTTYPE_FORM,charset);
    }
    
    
    public static String doGet(String path, Map<String, Object> params) {
        return post(path,METHOD_GET, parse(params,charset_utf8),CONTENTTYPE_FORM,charset_utf8);
    }
    
    
    public static String doGetJson(String path, Object object) {
        if(object == null){
            object = "";
        }
        return post(path,METHOD_GET,JSON.toJSONString(object),CONTENTTYPE_JSON,charset_utf8);
    }
    
    
    /**
     * 
     * @param path
     * @param object
     * @return
     */
    public static String doPostJson(String path, Object object) {
        if(object == null){
            object = "";
        }
        return post(path, METHOD_POST,JSON.toJSONString(object),CONTENTTYPE_JSON,charset_utf8);
    }

    
    public static String doPostJson(String path, String jsonStr) {
        if(jsonStr == null){
            jsonStr = "";
        }
        return post(path, METHOD_POST,jsonStr,CONTENTTYPE_JSON,charset_utf8);
    }
    

    private static String parse(Map<String, Object> params,String charset) {
        if (params == null){
            return "";
        }

        if(charset == null || charset.equals("")){
            charset = charset_utf8;
        }

        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet()) {
            try {
                builder.append(key + "=" + URLEncoder.encode(String.valueOf(params.get(key)), charset) + "&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(builder.length() > 0){
            return builder.substring(0, builder.length() -1);
        }

        return null;
    }



}
