package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread08 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread08.class);

    private volatile static int SCORE = 0;

    private static Lock LOCK = new ReentrantLock();

    /**
     * 使用非公平可重入锁，轮询到当前存活线程=初始线程（这里是1）再进入主线程逻辑
     * @param args
     */
    public static void main(String[] args) {
        try {
            int count = Thread.activeCount();
            Thread08 thread = new Thread08();
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
        LOCK.lock();
        for (int i = 0; i < 100000; i++) {
            SCORE++;
        }
        LOCK.unlock();
    }
}
