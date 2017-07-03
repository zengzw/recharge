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
public class CreateOrderHttpTest extends BaseHttpTest{

    @Override
    public String getType() {
        return "createOrder";
    }

    @Test
    public void createOrder() throws Exception {
        super.init();

        String merchant_order_id = UUIDUtil.getUUID();
        String order_level = "0";
        String order_result_url = "http://www.baidu.com";
//        String book_result_url = "";
        String arrive_station = "长沙";
        String arrive_time = "06:10";

        String bx = "0";
        String ids_type = "2";
        String ticket_type = "0";
        String user_ids = "430524198802083253";
        String user_name = "周建超";

        String bx_invoice = "0";
        String from_station = "深圳";
        String from_time = "20:09";
//        String link_address = "";
//        String link_mail = "";
//        String link_name = "";
//        String link_phone = "";
//        String order_pro1 = "";
//        String order_pro2 = "";
        String seat_type = "8";
        String sms_notify = "0";
        String sum_amount = "146.0";
        String ticket_price = "146.0";
        String train_code = "K9018";
        String travel_time = "2016-11-28";
        String wz_ext = "0";

        Map<String, Object> jsonParams = new LinkedHashMap<String, Object>();

        jsonParams.put("merchant_order_id", merchant_order_id);
        jsonParams.put("order_level", order_level);
        jsonParams.put("order_result_url", order_result_url);
        jsonParams.put("arrive_station", arrive_station);
        jsonParams.put("arrive_time", arrive_time);
        List<Map<String, String>> passengers = new ArrayList<Map<String, String>>();
        Map<String,String> onePassenger = new HashMap<String, String>();
        onePassenger.put("bx", bx);
        onePassenger.put("ids_type", ids_type);
        onePassenger.put("ticket_type", ticket_type);
        onePassenger.put("user_ids", user_ids);
        onePassenger.put("user_name", user_name);
        passengers.add(onePassenger);
        jsonParams.put("book_detail_list", passengers);
        jsonParams.put("bx_invoice", bx_invoice);
        jsonParams.put("from_station", from_station);
        jsonParams.put("from_time", from_time);

        jsonParams.put("seat_type", seat_type);
        jsonParams.put("sms_notify", sms_notify);
        jsonParams.put("sum_amount", sum_amount);
        jsonParams.put("ticket_price", ticket_price);
        jsonParams.put("train_code", train_code);
        jsonParams.put("travel_time", travel_time);
        jsonParams.put("wz_ext", wz_ext);

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


    public static void main(String args[]){
        Map<String, String> jsonParams = new LinkedHashMap<String, String>();
        jsonParams.put("seat_type", "seat_type");
        jsonParams.put("sms_notify", "sms_notify");

        System.out.println(JSON.toJSONString(jsonParams));
    }
}
