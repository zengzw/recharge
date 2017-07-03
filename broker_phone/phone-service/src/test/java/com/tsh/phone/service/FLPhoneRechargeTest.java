package com.tsh.phone.service;

import com.dtds.platform.commons.utility.DateUtil;
import com.github.ltsopensource.core.json.JSON;
import com.tsh.phone.commoms.utils.JsonUtils;
import com.tsh.phone.commoms.utils.Md5Digest;
import com.tsh.phone.recharge.flpay.vo.supplier.response.QueryMobileResponse;
import com.tsh.phone.util.HttpUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 011.
 */

public class FLPhoneRechargeTest {

    private String key = "017DF0B8B2ADECF7A55C1E848D2B6046";
    private String customerid = "803263";
    private String url = "http://ccapi.kabaling.com/Interface/Method";
    private String mobile = "15820784340";
    @Test
    public void charge() throws Exception{

        String methodName = "kamenwang.phoneorder.add";
        String version = "1.0";
        String orderno = "tsh"+System.currentTimeMillis();
        String money = "1";
        String format = "json";

        Map<String, Object> params = new HashMap<>();
        params.put("method", methodName);
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", version);
        params.put("format", format);

        params.put("customerid", customerid);
        params.put("customerorderno", orderno);
        params.put("chargephone", mobile);
        params.put("chargeparvalue", money);

//        params.put("notifyurl", "http://callback.com");


        String signValue = "chargeparvalue="+money+
                "&chargephone="+mobile+
                "&customerid="+customerid+
                "&customerorderno="+orderno+
                "&format="+format+
                "&method="+methodName+
                "&timestamp="+timestamp+
                "&v="+version;

        signValue = StringEscapeUtils.unescapeJava(signValue);

        System.out.println("sign:" + signValue+key);
        params.put("sign", Md5Digest.encryptMD5(signValue+key));

        System.out.println("params:" + params);
        String result =  HttpUtils.doPost(url, params, HttpUtils.charset_utf8);

        System.out.println("result:" + result);
    }

    @Test
    public void queryMobile() throws Exception{
        String methodName = "kamenwang.phonegoods.checkandgetprice";
        String version = "1.4";
        String format = "json";
        String money = "100";

        Map<String, Object> params = new HashMap<>();
        params.put("method", methodName);
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", version);
        params.put("format", format);

        params.put("customerid", customerid);
        params.put("chargephone", mobile);
        params.put("chargeparvalue", money);

        String signValue = "chargeparvalue="+money+
                "&chargephone="+mobile+
                "&customerid="+customerid+
                "&format="+format+
                "&method="+methodName+
                "&timestamp="+timestamp+
                "&v="+version;

        signValue = StringEscapeUtils.unescapeJava(signValue);

        System.out.println("sign:" + signValue+key);
        params.put("sign", Md5Digest.encryptMD5(signValue+key));

        System.out.println("params:" + params);
        String result =  HttpUtils.doPost(url, params, HttpUtils.charset_utf8);
        System.out.println("result1:" + result);
        QueryMobileResponse response = JsonUtils.convert2Object(result, QueryMobileResponse.class);
        System.out.println("result json:" + JSON.toJSONString(response));
    }

    @Test
    public void queryOrder() throws Exception{
        String methodName = "kamenwang.order.get";
        String version = "1.0";
        String format = "json";

        String orderno = "tsh1491977492517";


        Map<String, Object> params = new HashMap<>();
        params.put("method", methodName);
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", version);
        params.put("format", format);

        params.put("customerid", customerid);
        params.put("customerorderno", orderno);


        String signValue = "customerid="+customerid+
                "&customerorderno="+orderno+
                "&format="+format+
                "&method="+methodName+
                "&timestamp="+timestamp+
                "&v="+version;

        signValue = StringEscapeUtils.unescapeJava(signValue);

        System.out.println("sign:" + signValue+key);
        params.put("sign", Md5Digest.encryptMD5(signValue+key));

        System.out.println("params:" + params);
        String result =  HttpUtils.doPost(url, params, HttpUtils.charset_utf8);
        System.out.println("result1:" + result);

    }

    @Test
    public void queryOrderList() throws Exception{
        String url = "http://orders.ccapi.kamenwang.com/api/Interface/method";

        String methodName = "kamenwang.customerorder.get";
        String version = "1.0";
        String format = "json";

        String starttime = "2017-04-01 12:00:00";
        String endtime = "2017-04-28 2:00:00";

        Map<String, Object> params = new HashMap<>();
        params.put("method", methodName);
        String timestamp = DateUtil.date2String(new Date());
        params.put("timestamp", timestamp);
        params.put("v", version);
        params.put("format", format);

        params.put("customerid", customerid);
        params.put("starttime", starttime);
        params.put("endtime", endtime);

        String signValue = "customerid="+customerid+
                "&endtime="+endtime+
                "&format="+format+
                "&method="+methodName+
                "&starttime="+starttime+
                "&timestamp="+timestamp+
                "&v="+version;

        signValue = StringEscapeUtils.unescapeJava(signValue);

        System.out.println("sign:" + signValue+key);
        params.put("sign", Md5Digest.encryptMD5(signValue+key));

        System.out.println("params:" + params);
        String result =  HttpUtils.doPost(url, params, HttpUtils.charset_utf8);
        System.out.println("result1:" + result);



    }
}
