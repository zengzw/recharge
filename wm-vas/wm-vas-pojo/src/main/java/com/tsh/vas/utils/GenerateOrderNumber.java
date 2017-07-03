package com.tsh.vas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateOrderNumber {

    /**
     * 获取订单号
     *
     * @return
     */
    public static String getOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
        String orderNumber = sdf.format (new Date ());

        for (int i = 0; i < 6; i++) {
            orderNumber += (int) (Math.random () * 10);
        }
        return orderNumber;
    }

//    /**
//     * 获取订单号
//     *
//     * @return
//     */
//    public static String getOrderNumber(int num) {
//        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
//        String orderNumber = sdf.format (new Date ());
//
//        for (int i = 0; i < num; i++) {
//            orderNumber += (int) (Math.random () * 10);
//        }
//        return orderNumber;
//    }
    public static void main(String[] args) {
        String orderNumber = GenerateOrderNumber.getOrderNumber ();
        System.out.println (orderNumber);
    }
}
