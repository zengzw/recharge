package com.tsh.openpf.diamond;

import com.dtds.platform.diamond.TshPropertyPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化配置信息
 *
 * @author dengjd
 */
public class TshDiamondClient extends TshPropertyPlaceholderConfigurer {

    private static TshDiamondClient tshDiamondClient = new TshDiamondClient ();

    public static TshDiamondClient getInstance() {
        return tshDiamondClient;
    }

    public void init() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("group", "openpf");
		map1.put("dataId", "jdbc");
		list.add(map1);
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("group", "common");
		map2.put("dataId", "zookeeper");
		list.add(map2);
		
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("group", "common");
		map3.put("dataId", "redis");
		list.add(map3);
		
		Map<String,String> map4 = new HashMap<String,String>();
		map4.put("group", "common");
		map4.put("dataId", "oss");
		list.add(map4);
		
		Map<String,String> map5 = new HashMap<String,String>();
		map5.put("group", "common");
		map5.put("dataId", "metaq");
		list.add(map5);

        Map<String,String> map6 = new HashMap<String,String>();
        map6.put("group", "common");
        map6.put("dataId", "lts");
        list.add(map6);

        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("group", "common");
        map7.put("dataId", "sms");
        list.add(map7);

        Map<String, String> map8 = new HashMap<String, String>();
        map8.put("group", "openpf");
        map8.put("dataId", "config");
        list.add(map8);

		this.loadMultConfigFromDiamond(list);
    }
}
