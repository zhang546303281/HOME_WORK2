package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class Thread10 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread10.class);

    private volatile static int SCORE = 0;
    private Semaphore semaphore = new Semaphore(1);

    /**
     * 使用Semaphore限制并行数量为1,子线程run方法i++操作衍变成原子操作
     * @param args
     */
    public static void main(String[] args) {
        try {
            int count = Thread.activeCount();
            Thread10 thread = new Thread10();
            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(thread);
                t.start();
            }

            while (Thread.activeCount() > count) {
                Thread.yield();
            }

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + SCORE);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            for (int i = 0; i < 100000; i++) {
                SCORE++;
            }
            semaphore.release();
        } catch (Exception e) {
            LOGGER.error("run err:", e);
        }
    }
}
