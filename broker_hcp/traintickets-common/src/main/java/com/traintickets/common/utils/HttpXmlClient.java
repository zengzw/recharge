package com.traintickets.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpXmlClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpXmlClient.class);

    public static String post(String url, Map<String,String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  60000 );
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  60000 );

        logger.info("-------------> create httppost:" + url);
        HttpPost post = postForm(url, params);

        String body = invoke(httpclient, post);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    public static String post2(String url, Map<String, Object> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        logger.info("create httppost:" + url);
        HttpPost post = postForm2(url, params);

        body = invoke(httpclient, post);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    public static String get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        logger.info("create httppost:" + url);
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    public static String get(String url, Map<String,String> params) {
        String  getUrl = url.lastIndexOf("?") == -1 ? (url + "?") : url;
        StringBuffer stringBuffer = new StringBuffer();
        for(Map.Entry keyValue  : params.entrySet()){
            stringBuffer.append(keyValue.getKey());
            stringBuffer.append("=");
            stringBuffer.append(keyValue.getValue());
            stringBuffer.append("&");
        }

        stringBuffer =  stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        getUrl += stringBuffer.toString();

        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        logger.info("create httppost:" + getUrl);
        HttpGet get = new HttpGet(getUrl);
        body = invoke(httpclient, get);

        httpclient.getConnectionManager().shutdown();

        return body;
    }


    private static String invoke(DefaultHttpClient httpclient,
                                 HttpUriRequest httpost) {

        HttpResponse response = sendRequest(httpclient, httpost);
        if (null == response) {
            return null;
        }
        return paseResponse(response);
    }

    private static String paseResponse(HttpResponse response) {
        logger.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        logger.info("response status: " + response.getStatusLine());
        String charset = EntityUtils.getContentCharSet(entity);
        logger.info(charset);

        String body = null;
        try {
            body = EntityUtils.toString(entity);
            logger.info(body);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
                                            HttpUriRequest httpost) {
        logger.info("execute post...");
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params){

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();

        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            logger.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    private static HttpPost postForm2(String url, Map<String, Object> params){

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();


        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, JSON.toJSONString(params.get(key))));
        }

        try {
            logger.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }
}
