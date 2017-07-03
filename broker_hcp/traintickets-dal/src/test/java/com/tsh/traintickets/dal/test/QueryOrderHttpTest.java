package com.tsh.traintickets.dal.test;

import com.dtds.platform.util.UUIDUtil;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class QueryOrderHttpTest extends BaseHttpTest{

    @Override
    public String getType() {
        return "queryOrderInfo";
    }

    @Test
    public void test() throws Exception {
        super.init();

        String order_id = "EXHC161121150112718";
        String merchant_order_id = "953422e1c0a84e678fc780d15058f5a5";

        // 加密字符串body
        String bodyParams = order_id + merchant_order_id;

        String paramsAll = headerParams + bodyParams + hmac;

        System.out.println("params:" + paramsAll);

        String encrypt = Md5Digest.encryptMD5(paramsAll);
        System.out.println("params:" + encrypt);

        paramsMap.put("hmac", encrypt);

        paramsMap.put("order_id", order_id);
        paramsMap.put("merchant_order_id", merchant_order_id);

        System.out.println("request:" + paramsMap);
        String result = HttpXmlClient.post(url, paramsMap);
        System.out.println("result1:" + result);
        byte[] b = result.getBytes("ISO-8859-1");
        result = new String(b,"UTF-8");
        System.out.println("result2:" + result);

    }
}
