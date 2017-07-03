/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.data.redis.lock.IRedisLock;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.service.BaseCaseTest;

/**
 *
 * @author zengzw
 * @date 2017年6月7日
 */
public class RedisTest extends BaseCaseTest{


    private static String key = "redis_key";
    private static String value = "redis_value";
    private static int expireTime =  5;

    @Autowired
    IRedisLock redisLock;
    
    
    private static Map<String, Integer> cache = new HashMap<>();

    @Test
    public void testDisLock(){

        for(int i = 0; i<3; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        testL();
                    }catch(Exception e){
                        System.out.println("异常");
                        e.printStackTrace();
                    }
                }
            },"thread-"+i).start();



        }

        while(true){}
    }


    @Test
    public void testUnLock(){
        redisLock.unLock("redis_lok");
    }

    
    

    @Test
    public void testSetNX(){

        for(int i = 0; i<3; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean result = RedisSlave.getInstance().setNX(key, value, expireTime);
                    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Thread.currentThread().getName()+":"+result);

                }
            },"thread-"+i).start();

        }
    }



    public void testL(){
        boolean getLock = redisLock.tryLock("redis_lok",10000);
        if(getLock){
            System.out.println(Thread.currentThread().getName()+"----->获取到锁");
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟异常
                if(Thread.currentThread().getName().equals("thread-2")){
                    throw new BusinessRuntimeException("", "runtime exception");
                }


                //模拟成功
                if(Thread.currentThread().getName().equals("thread-0")){
                    return;
                }
                
                if(cache.containsKey(Thread.currentThread().getName())){
                    int value =  cache.get(Thread.currentThread().getName());
                    if(value > 3){
                        System.out.println("---重试大于三，结束。。。。。。");
                        return;
                    }
                    cache.put(Thread.currentThread().getName(), value+1);
                }else{
                    cache.put(Thread.currentThread().getName(), 1);
                }
               


            }finally{
                System.out.println(Thread.currentThread().getName()+"----->释放锁");
                redisLock.unLock("redis_lok");
            }

        }

        System.out.println("finally after，retrying.......");

        testL();

    }
}
