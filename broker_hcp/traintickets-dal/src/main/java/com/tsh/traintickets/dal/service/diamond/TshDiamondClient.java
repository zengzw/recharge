package com.tsh.traintickets.dal.service.diamond;

import com.dtds.platform.diamond.TshPropertyPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化配置信息
 *
 * @author zhoujc
 */
public class TshDiamondClient extends TshPropertyPlaceholderConfigurer {

    private static TshDiamondClient tshDiamondClient = new TshDiamondClient ();

    public static TshDiamondClient getInstance() {
        return tshDiamondClient;
    }

    private String groupName = "group";
	private String dataIdName = "dataId";
	private String commonName = "common";

    public void init() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map1 = new HashMap();
		map1.put(groupName, "broker");
		map1.put(dataIdName, "broker-jdbc");
		list.add(map1);

		Map<String,String> map2 = new HashMap();
		map2.put(groupName, commonName);
		map2.put(dataIdName, "zookeeper");
		list.add(map2);

		Map<String,String> map3 = new HashMap();
		map3.put(groupName, commonName);
		map3.put(dataIdName, "redis");
		list.add(map3);

		Map<String,String> map4 = new HashMap();
		map4.put(groupName, commonName);
		map4.put(dataIdName, "oss");
		list.add(map4);

		Map<String,String> map5 = new HashMap();
		map5.put(groupName, commonName);
		map5.put(dataIdName, "metaq");
		list.add(map5);

        Map<String,String> map6 = new HashMap();
        map6.put(groupName, commonName);
        map6.put(dataIdName, "lts");
        list.add(map6);

        Map<String, String> map7 = new HashMap();
        map7.put(groupName, commonName);
        map7.put(dataIdName, "sms");
        list.add(map7);

        Map<String,String> map10 = new HashMap();
        map10.put(groupName, "hcp");
        map10.put(dataIdName, "kuyou");
        list.add(map10);

		this.loadMultConfigFromDiamond(list);
    }
}
