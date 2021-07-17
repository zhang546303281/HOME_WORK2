package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread12 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread12.class);

    private volatile static int SCORE = 0;

    final static Lock LOCK = new ReentrantLock();

    final static Condition CONDITION = LOCK.newCondition();

    /**
     * 使用Condition让主线程等待，等待子线程的信号量通知再继续执行
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread12 thread = new Thread12();
            Thread t = new Thread(thread);
            t.start();

            try {
                LOCK.lock();
                CONDITION.await();
            } finally {
                LOCK.unlock();
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
        try {
            SCORE = 1;
            LOGGER.debug("child thread process end,param:" + SCORE);
            CONDITION.signal();
        } catch (Exception e) {
            LOGGER.error("run err:", e);
        } finally {
            LOCK.unlock();
        }
    }
}
