/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author zengzw
 * @date 2017年5月25日
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DBLogQueue<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBLogQueue.class);

    private static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();


    public void clean(){
        queue.clear();
    }


    public boolean isEmpty(){
        return queue.isEmpty();
    }


    public void off(T t){
        LOGGER.info("#--添加日志信息到队列中，等待消费");

        queue.offer(t);
    }



    public T pool(){
        LOGGER.info("#--从队列中，获取数据....准备消费");

        return (T)queue.poll();
    }

    
    
    public int size(){
        return queue.size();
    }

    public static int i = 1;

    public static void main(String[] args) {
        DBLogQueue dbLogQueue = new DBLogQueue<>();
        dbLogQueue.off("dd");
        dbLogQueue.off("dd");
        dbLogQueue.off("dd");
        
        System.out.println(dbLogQueue.pool());
        System.out.println(dbLogQueue.pool());
        System.out.println(dbLogQueue.pool());
        
       /* new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    System.out.println("00000000000:"+i);
                    i++;
                    if(i>6){
                        break;
                    }
                }
            

            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while(i > 1){
                    System.out.println("--------");
                }

            }
        }).start();*/
    }

}
