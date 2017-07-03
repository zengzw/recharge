package com.tsh.traintickets.dal.test.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.traintickets.common.utils.HttpXmlClient;
import com.tsh.traintickets.vo.request.VerifyUserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24 024.
 */
public class VerifyUsersTest {

    public static void main(String args[]){
        String url = "http://172.16.1.206:8330/traintickets/verifyUsers.do";
        String url2 = "http://localhost:8086/traintickets/verifyUsers.do";
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        List<VerifyUserModel> list = new ArrayList<VerifyUserModel>();

        VerifyUserModel user1 = new VerifyUserModel();
        user1.setIdType("2");
        user1.setUserId("1121221211");
        user1.setUserName("111");
        VerifyUserModel user2 = new VerifyUserModel();
        user2.setIdType("2");
        user2.setUserId("323232323");
        user2.setUserName("3333");

        list.add(user1);
        list.add(user2);

        String json = JSON.toJSONString(list);
        System.out.println(json);
        paramsMap.put("userList", json);

        String result = HttpXmlClient.post2(url2, paramsMap);
        System.out.println("result:" + result);
    }
}
