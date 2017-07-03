package com.traintickets.common.utils;


import java.security.MessageDigest;


public class Md5Digest {

    /**
     * md5加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String key) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes("utf-8"));
        String result = "";
        byte[] temp = md5.digest();
        for (int i = 0; i < temp.length; i++) {
            result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
        }
        return result;
    }

    public static void main(String args[]){
        try {
            System.out.println(encryptMD5("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
