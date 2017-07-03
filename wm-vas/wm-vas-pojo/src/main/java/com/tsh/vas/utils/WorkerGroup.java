package com.tsh.vas.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步调用工具
 * 处理工作组，伪异步方式
 * Created by Iritchie.ren on 2016/10/19.
 */
public class WorkerGroup {

    private static ExecutorService worker = Executors.newFixedThreadPool (5);

    /**
     * 提交异步任务
     */
    public static void submit(Runnable task) {
        worker.submit (task);
    }
}
