package xyz.sallai.r1.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {
    private static  ExecutorService pool;
    private MyThreadPool() {
    }
    public static ExecutorService getThreadPool(){
        if(pool == null) {
            synchronized (MyThreadPool.class) {
                if (pool == null) {
                    pool = new ThreadPoolExecutor(5,
                            5,
                            15,
                            TimeUnit.MICROSECONDS,
                            new ArrayBlockingQueue<>(10),
                            Executors.defaultThreadFactory(),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return pool;
    }

}
