package com.tsh.traintickets.dal.test;

import com.traintickets.common.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public abstract class BaseHttpTest {

    String url = "http://114.247.40.69:38055/externalInterface.do";

    String terminal = "PC";
    String merchant_id = "szzhihuicity";
    String timestamp = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_1);
    String version = "1.0.0";
    String hmac = "531c6154b45d59c745b94f647c572478";

    public abstract String getType();


    // 加密字符串header
    String headerParams = terminal + merchant_id + timestamp + this.getType() + version;

    Map<String,String> paramsMap = new LinkedHashMap<String, String>();

    public void init(){
        paramsMap.put("terminal", terminal);
        paramsMap.put("merchant_id", merchant_id);
        paramsMap.put("timestamp", timestamp);
        paramsMap.put("type", getType());
        paramsMap.put("version", version);
    }


}
