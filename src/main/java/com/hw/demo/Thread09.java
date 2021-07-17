package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Thread09 {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread09.class);

    private volatile static int SCORE = 0;

    /**
     * 主线程释放信号量使子线程释放lock从而赋值1,主线程轮询到结果1后再执行
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread t = new Thread(() -> {
                LOGGER.debug("child ready to park,param:" + SCORE);
                LockSupport.park();
                SCORE = 1;
                LOGGER.debug("child unpark end,param:" + SCORE);
            });
            t.start();

            LockSupport.unpark(t);

            while (true) {
                if (SCORE == 1)
                    break;
            }

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + SCORE);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }
}
