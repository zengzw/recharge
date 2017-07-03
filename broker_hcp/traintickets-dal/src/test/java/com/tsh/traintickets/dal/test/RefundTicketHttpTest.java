package com.tsh.traintickets.dal.test;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.UUIDUtil;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class RefundTicketHttpTest extends BaseHttpTest{
    @Override
    public String getType() {
        return "refundTicket";
    }

    @Test
    public void test() throws Exception {
        super.init();

        String comment="测试退票功能老了";
        String merchant_order_id = "1234567890asdfghjk833afe";
        String order_id = "EXHC161128113719112";
        String refund_result_url = "http://brokerhcp.testtsh365.cn:15433/traintickets/callback/dealRefund.do";
        String refund_type = "all";
        String request_id = UUIDUtil.getUUID();

        Map<String, Object> jsonParams = new LinkedHashMap<String, Object>();
        jsonParams.put("comment", comment);
        jsonParams.put("merchant_order_id", merchant_order_id );
        jsonParams.put("order_id", order_id);
        jsonParams.put("refund_picture_url", "");
        jsonParams.put("refund_result_url", refund_result_url);
        jsonParams.put("refund_type", refund_type);
        jsonParams.put("refundinfo", Collections.emptyList());
        jsonParams.put("request_id", request_id);

        String jsonParamsString = StringEscapeUtils.unescapeJava(JSON.toJSONString(jsonParams));

        paramsMap.put("json_param", jsonParamsString);

        String paramsAll = headerParams + jsonParamsString + hmac;
        System.out.println("paramsAll:" + paramsAll);
        String encrypt = Md5Digest.encryptMD5(paramsAll);

        paramsMap.put("hmac", encrypt);

        System.out.println("request:" + paramsMap);
        String result = HttpXmlClient.post(url, paramsMap);
        System.out.println("result1:" + result);
        byte[] b = result.getBytes("ISO-8859-1");
        result = new String(b,"UTF-8");
        System.out.println("result2:" + result);
    }

}
