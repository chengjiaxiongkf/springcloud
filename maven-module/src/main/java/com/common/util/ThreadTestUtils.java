package com.common.util;

import java.util.concurrent.CountDownLatch;

/**
 * 并发测试工具类
 * @Author chengjiaxiong
 * @Date 2020/8/8 21:52
 */
public class ThreadTestUtils {
    /**
     * 高并发测试：
     * 创建threadNum个线程；
     * 执行任务task
     * @param threadNum 线程数量
     * @param task 任务
     */
    public static void parallelTesk(int threadNum, Runnable task){

        // 1. 定义闭锁来拦截线程
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate  = new CountDownLatch(threadNum);

        // 2. 创建指定数量的线程
        for (int i = 0; i <threadNum; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            t.start();
        }

        // 3. 线程统一放行，并记录时间！
        long start =  System.nanoTime();

        startGate.countDown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println("cost times :" +(end - start));
    }
}
