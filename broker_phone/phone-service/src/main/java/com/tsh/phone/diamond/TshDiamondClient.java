package com.tsh.phone.diamond;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtds.platform.diamond.TshPropertyPlaceholderConfigurer;

/**
 * 初始化配置信息
 * 
 * @author cwli
 * 
 */
public class TshDiamondClient extends TshPropertyPlaceholderConfigurer {

    private static TshDiamondClient tshDiamondClient = new TshDiamondClient();

    public static TshDiamondClient getInstance() {
        return tshDiamondClient;
    }


    public void init() {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("group", "broker");
        map1.put("dataId", "broker-jdbc");
        list.add(map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("group", "common");
        map2.put("dataId", "zookeeper");
        list.add(map2);

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("group", "common");
        map3.put("dataId", "redis");
        list.add(map3);

        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("group", "common");
        map4.put("dataId", "sms");
        list.add(map4);

        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("group", "common");
        map5.put("dataId", "metaq");
        list.add(map5);

        // solr
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("group", "common");
        map6.put("dataId", "solr");
        list.add(map6);

        // lts
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("group", "common");
        map7.put("dataId", "lts");
        list.add(map7);

        // 图片上传
        Map<String, String> map9 = new HashMap<String, String>();
        map9.put("group", "common");
        map9.put("dataId", "oss");
        list.add(map9);


        //话费充值 of 配置信息
        Map<String, String> map11 = new HashMap<String, String>();
        map11.put("group", "broker");
        map11.put("dataId", "ofpay-phone");
        list.add(map11);

        //话费充值 gy 配置信息
        Map<String, String> map12 = new HashMap<String, String>();
        map12.put("group", "broker");
        map12.put("dataId", "gypay-phone");
        list.add(map12);

        //话费充值 fl 配置信息
        Map<String, String> map13 = new HashMap<String, String>();
        map13.put("group", "broker");
        map13.put("dataId", "flpay-phone");
        list.add(map13);
        
        
        //话费充值 瑞通（rt) 配置信息
        Map<String, String> map14 = new HashMap<String, String>();
        map14.put("group", "broker");
        map14.put("dataId", "rtpay-phone");
        list.add(map14);

        this.loadMultConfigFromDiamond(list);
    }


}
