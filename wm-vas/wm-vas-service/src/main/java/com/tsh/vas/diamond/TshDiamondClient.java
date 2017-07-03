package com.tsh.vas.diamond;

import com.dtds.platform.diamond.TshPropertyPlaceholderConfigurer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iritchie.ren on 2016/9/19.
 */
public class TshDiamondClient extends TshPropertyPlaceholderConfigurer {

    private static TshDiamondClient tshDiamondClient = new TshDiamondClient ();

    public static TshDiamondClient getInstance() {
        return tshDiamondClient;
    }

    public void init() {
        List<Map<String, String>> list = Lists.newArrayList ();

        Map<String, String> map1 = Maps.newHashMap ();
        map1.put ("group", "common");
        map1.put ("dataId", "zookeeper");
        list.add (map1);

        Map<String, String> map2 = Maps.newHashMap ();
        map2.put ("group", "common");
        map2.put ("dataId", "redis");
        list.add (map2);

        Map<String, String> map3 = Maps.newHashMap ();
        map3.put ("group", "common");
        map3.put ("dataId", "oss");
        list.add (map3);

        Map<String, String> map4 = Maps.newHashMap ();
        map4.put ("group", "common");
        map4.put ("dataId", "sms");
        list.add (map4);

        Map<String, String> map5 = Maps.newHashMap ();
        map5.put ("group", "common");
        map5.put ("dataId", "metaq");
        list.add (map5);

        Map<String, String> map6 = Maps.newHashMap ();
        map6.put ("group", "common");
        map6.put ("dataId", "lts");
        list.add (map6);

        Map<String, String> map7 = Maps.newHashMap ();
        map7.put ("group", "vas-new");
        map7.put ("dataId", "vas-jdbc");
        list.add (map7);

        Map<String, String> map8 = Maps.newHashMap ();
        map8.put ("group", "vas-new");
        map8.put ("dataId", "url");
        list.add (map8);

        Map<String, String> map9 = Maps.newHashMap ();
        map9.put ("group", "vas-new");
        map9.put ("dataId", "config");
        list.add (map9);

        Map<String, String> map10 = Maps.newHashMap ();
        map10.put ("group", "vas-new");
        map10.put ("dataId", "hcp");
        list.add (map10);
        
        Map<String, String> map11 = Maps.newHashMap ();
        map11.put ("group", "vas-new");
        map11.put ("dataId", "phone");
        list.add (map11);

        Map<String,String> map = new HashMap<String,String>();
        map.put("group", "common");
        map.put("dataId", "newsms");
        list.add(map);

        this.loadMultConfigFromDiamond (list);
    }
}