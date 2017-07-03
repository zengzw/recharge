package com.tsh.broker.utils;

/**
 * AmoutUtils
 *
 * @author dengjd
 * @date 2016/10/9
 */
public class AmountUtils {

    /**
     *  金额减少n位精度
     * @param amount
     * @param n
     * @return
     */
    public static long  reduceAccuracy(long amount,int n){
        if(amount < 0)
            throw  new IllegalArgumentException("amount argument not less than zero");
        if(n <= 0)
            throw  new IllegalArgumentException("n argument not less than equals zero");

        return  amount / (int)Math.pow(10,n);
    }

    /**
     * 金额增加n位精度
     * @param amount
     * @param n
     * @return
     */
    public static long  addAccuracy(long amount,int n){
        if(amount < 0)
            throw  new IllegalArgumentException("amount argument not less than zero");
        if(n <= 0)
            throw  new IllegalArgumentException("n argument not less than equals zero");

        return  amount *  (int)Math.pow(10,n);
    }

}
