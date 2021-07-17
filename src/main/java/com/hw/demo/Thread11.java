package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Thread11 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread11.class);

    private volatile static AtomicLong SCORE = new AtomicLong(0);

    private volatile static LongAdder SCORE_LONG_ADDER = new LongAdder();

    /**
     * 使用原子操作类
     * @param args
     */
    public static void main(String[] args) {
        try {
            int count = Thread.activeCount();
            Thread11 thread = new Thread11();
            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(thread);
                t.start();
            }

            while (Thread.activeCount() > count) {
                Thread.yield();
            }

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param SCORE:" + SCORE.get());
            LOGGER.debug("main get param SCORE_LONG_ADDER:" + SCORE_LONG_ADDER.sum());
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            SCORE.getAndIncrement();
            SCORE_LONG_ADDER.increment();
        }
    }
}
