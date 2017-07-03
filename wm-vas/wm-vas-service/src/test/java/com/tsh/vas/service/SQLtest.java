package com.tsh.vas.service;

/**
 * Created by Administrator on 2017/4/21 021.
 */
public class SQLtest {
    public static void main(String[] args) {
        int size = 430;

        int start = 117;
        for(int i = 0;i<size;i++){
            String sql = "INSERT INTO `vas_phone_oneyuan_free` VALUES ("+(start+i)+
                    ", '30', '15820784340', " +
                    "'MPCZ20170421111947047993833', " +
                    "1, '2017-4-21 11:19:47', 1, NULL, 0, '2017-4-21 17:25:55', '', '000104', '话费', 19181, '武江区龙归镇奇石村小卖铺', 19181, '武江区龙归镇奇石村小卖铺', null);";

            System.out.println(sql);
        }
    }
}
