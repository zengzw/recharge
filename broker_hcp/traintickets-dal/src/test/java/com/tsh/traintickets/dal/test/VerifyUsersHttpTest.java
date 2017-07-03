package com.tsh.traintickets.dal.test;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.utils.HttpXmlClient;
import com.traintickets.common.utils.Md5Digest;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2016/11/16 016.
 */
public class VerifyUsersHttpTest extends BaseHttpTest{
    @Override
    public String getType() {
        return "verifyUsers";
    }

    @Test
    public void test() throws Exception {
        super.init();

        String ids_type = "2";
        String user_ids = "430524198802083253";
        String user_name = "周建超";




        Map<String, String> user1 = new LinkedHashMap<String, String>();
        user1.put("ids_type", ids_type);
        user1.put("user_ids", user_ids);
        user1.put("user_name", user_name);

        Map<String, String> user2 = new LinkedHashMap<String, String>();
        user2.put("ids_type", ids_type);
        user2.put("user_ids", "430524198802");
        user2.put("user_name", "周涵");


        List<Map<String, String>> users = new ArrayList<Map<String, String>>();
        users.add(user1);
        users.add(user2);

        Map<String, List<Map<String, String>>> detailList = new HashMap<String, List<Map<String, String>>>();
        detailList.put("detail_list", users);

        paramsMap.put("json_param", JSON.toJSONString(detailList));

        // 加密字符串body
        String bodyParams = ids_type + user_ids + user_name;

        String paramsAll = headerParams + JSON.toJSONString(detailList) + hmac;

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
