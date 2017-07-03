package com.tsh.traintickets.dal.test;

import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class CheckTicketNumHttpTest extends BaseHttpTest{

    @Override
    public String getType() {
        return "checkTicketNum";
    }

    @Test
    public void test() throws Exception {
        super.init();

        String train_code = "K9018";
        String travel_time = "2016-11-23";
        String from_station = "深圳";
        String arrive_station = "长沙";

        paramsMap.put("train_code", train_code);
        paramsMap.put("travel_time", travel_time);
        paramsMap.put("from_station", from_station);
        paramsMap.put("arrive_station", arrive_station);

        // 加密字符串body
        String bodyParams = train_code + travel_time + from_station + arrive_station;

        String paramsAll = headerParams + bodyParams + hmac;

        String encrypt = Md5Digest.encryptMD5(paramsAll);

        paramsMap.put("hmac", encrypt);

        System.out.println("request:" + paramsMap);
        long start = System.nanoTime();
        String result = HttpXmlClient.post(url, paramsMap);
        System.out.println("cost:" + (System.nanoTime()-start) / 1000000);
        System.out.println("result1:" + result);
        byte[] b = result.getBytes("ISO-8859-1");
        result = new String(b,"UTF-8");
        System.out.println("result2:" + result);

    }
}
