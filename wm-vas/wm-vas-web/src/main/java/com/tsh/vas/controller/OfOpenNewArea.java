package com.tsh.vas.controller;

import java.util.HashMap;
import java.util.Map;

import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.apache.log4j.Logger;

/**
 * 欧飞缴费区域V码查询
 */
public class OfOpenNewArea {

    public static final Logger LOGGER = Logger.getLogger(OfOpenNewArea.class);

    private OfOpenNewArea(){
        super();
    }



    /**
     * 启动
     * @param args
     */
    public static void main(String[] args) {
        try{
            Map<String, String> params = new HashMap();
            params.put("userid", "A1359194");
            params.put("userpws", Md5Digest.encryptMD5("taoshihui@abc123").toLowerCase());
            params.put("provId", "v1904");
            params.put("version", "6.0");
            String result = HttpXmlClient.get("http://AXXXX.api2.ofpay.com/getCityList.do", params);
            result = new String(result.getBytes(), "UTF-8");
            LOGGER.info("result:" + result);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

    }
}
