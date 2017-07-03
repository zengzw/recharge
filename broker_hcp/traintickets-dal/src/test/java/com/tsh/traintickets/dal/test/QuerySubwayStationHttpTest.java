package com.tsh.traintickets.dal.test;

import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class QuerySubwayStationHttpTest extends BaseHttpTest{
    @Override
    public String getType() {
        return "querySubwayStation";
    }

    @Test
    public void test() throws Exception {
        super.init();

        String train_code = "K9018";

        String paramsAll = headerParams + train_code + hmac;

        String encrypt = Md5Digest.encryptMD5(paramsAll);

        paramsMap.put("hmac", encrypt);

        paramsMap.put("train_code", train_code);

        System.out.println("request:" + paramsMap);
        String result = HttpXmlClient.post(url, paramsMap);
        System.out.println("result1:" + result);
        byte[] b = result.getBytes("ISO-8859-1");
        result = new String(b,"UTF-8");
        System.out.println("result2:" + result);

    }
}
