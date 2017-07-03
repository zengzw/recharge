package com.tsh.traintickets.dal.test;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.JsonUtils;
import com.traintickets.common.utils.Md5Digest;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketsBO;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class QueryTicketsHttpTest extends BaseHttpTest{

    @Override
    public String getType() {
        return "queryLeftTicket";
    }

    @Test
    public void testQueryTicket() throws Exception {
        super.init();

        String travel_time = "2016-11-28";
        String from_station = "深圳";
        String arrive_station = "长沙";

        // 加密字符串body
        String bodyParams = travel_time + from_station + arrive_station;

        String paramsAll = headerParams + bodyParams + hmac;

        System.out.println("params:" + paramsAll);

        String encrypt = Md5Digest.encryptMD5(paramsAll);
        System.out.println("params:" + encrypt);

        paramsMap.put("hmac", encrypt);

        paramsMap.put("travel_time", travel_time);
        paramsMap.put("from_station", from_station);
        paramsMap.put("arrive_station", arrive_station);

        System.out.println("request:" + paramsMap);
        String result = HttpXmlClient.post(url, paramsMap);
        System.out.println("result1:" + result);
        byte[] b = result.getBytes("ISO-8859-1");
        result = new String(b,"UTF-8");
        System.out.println("result2:" + result);

        long start1 = System.nanoTime();
        QueryTicketsBO queryTicketsBO1 = JSON.parseObject(result, QueryTicketsBO.class);
//        System.out.println("json:" + JSON.toJSONString(queryTicketsBO1));
        String a = JSON.toJSONString(queryTicketsBO1);
        System.out.println("fastjson json2string cost:" + (System.nanoTime()-start1) / 1000000);

        long start2 = System.nanoTime();
        QueryTicketsBO queryTicketsBO2 = JsonUtils.convert2Object(result, QueryTicketsBO.class);
//        System.out.println("json:" + JSON.toJSONString(queryTicketsBO2));
        System.out.println("google string2json cost:" + (System.nanoTime()-start2) / 1000000);

        long start3 = System.nanoTime();
//        System.out.println(JSON.toJSONString(queryTicketsBO2));
        String c = JSON.toJSONString(queryTicketsBO2);
        System.out.println("fastjson json2string cost:" + (System.nanoTime()-start3) / 1000000);

        long start4 = System.nanoTime();
//        System.out.println(JsonUtils.getInstance().toJson(queryTicketsBO2));
        QueryTicketsBO cc = JsonUtils.convert2Object(result, QueryTicketsBO.class);
        System.out.println("gson string2json cost:" + (System.nanoTime()-start4) / 1000000);
    }


}
