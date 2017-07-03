package com.traintickets.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * RandomUtils
 *
 * @author dengjd
 * @date 2016/9/30
 */
public class RandomUtils {

    private static Random random = new Random(System.nanoTime());
    /**
     * 根据位数获取随机数
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param n
     * @return
     */
    public static String  generateRandomNo(int n){
        char[] holder = new char[n];
        for(int i = 0; i < n; i++){
            holder[i] = '0';
        }
        char[] maxNum  = String.valueOf(random.nextInt((int)Math.pow(10,n)) - 1).toCharArray();
        int startPosition = n - maxNum.length;
        for(int i = 0; i < maxNum.length; i++){
            holder[startPosition + i] = maxNum[i];
        }

        return String.valueOf(holder);
    }

    public static String  generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args){
        /*for(int i = 0; i < 100;i++ )
            System.out.println(RandomUtils.generateRandomNo(4));*/
            System.out.println(generateUUID());
    }


}
