package com.tsh.vas.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringEscapeUtils;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Created by Administrator on 2016/12/19 019.
 */
public class CreateOrderTest1 {

    public static final String URL = "http://wmvas.tsh.com/app/vas/train/pay";
    public static final String KUYOU_URL = "http://brokerhcp.tsh.com/traintickets/kuyou/";

    Logger logger = LoggerFactory.getLogger(CreateOrderTest1.class);


    /**
     * test
     */
    @Test
    @PerfTest(invocations = 100, threads = 7)
    @Required(max = 1200, average = 250, totalTime = 60000)
    public void start() {

        try {

            Map<String, Object> paramsMap = new LinkedHashMap();
            paramsMap.put("arriveStation", "长沙");
            paramsMap.put("bizType", "4");
            paramsMap.put("businessCode", "HCP");
            paramsMap.put("businessName", "火车票");
            paramsMap.put("bx", "0");
            paramsMap.put("costingAmount", "146");
            paramsMap.put("fromStation", "深圳");
            paramsMap.put("userName", "测试姓名" + new Random().nextInt(999999999));
            paramsMap.put("payPassword", "123456");
            paramsMap.put("mobile", "18666998350");
            paramsMap.put("originalAmount", "151");
            paramsMap.put("realAmount", "151");
            paramsMap.put("seatType", "8");
            paramsMap.put("ticketType", "0");
            paramsMap.put("serverAddr", KUYOU_URL);
            paramsMap.put("servicePrice", "5");
            paramsMap.put("stationArriveTime", "06:10");
            paramsMap.put("stationStartTime", "20:09");
            paramsMap.put("supplierName", "北京酷游航空服务有限公司");
            paramsMap.put("supplierCode", "ZZFW20161212141903632918123");
            paramsMap.put("supplierToken", "539a4423e7b070c4");
            paramsMap.put("trainCode", "K9018");
            paramsMap.put("travelTime", "2017-01-19");
            paramsMap.put("partSubmit", "0");

            List<Map<String, String>> userList = new ArrayList();
            Map<String, String> user1 = new HashMap<>();
            user1.put("idType", "2");
            user1.put("userId", "430524187728322121");
            user1.put("userName", "楚王13-" + new Random().nextInt(999999999));
            user1.put("ticketType", "0");
            userList.add(user1);
            paramsMap.put("userDetailList", userList);

            Map<String, Object> postParamsMap = new HashMap<>();
            postParamsMap.put("params", StringEscapeUtils.unescapeJava(JSON.toJSONString(paramsMap)));
            postParamsMap.put("token", "auc_jsession_66a75c4a086f2bc80509190a5b9866a12d90862c");
            logger.info("params:" + JSON.toJSON(postParamsMap));
            String msg = com.dtds.platform.util.HttpUtils.postContent(URL, postParamsMap);
            logger.info("msg:" + msg);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

}





















