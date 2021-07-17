package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class Thread03 implements Runnable{

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread03.class);

    private static final Object OBJECT = new Object();

    public Thread03(int score){
        this.score = score;
    }

    /**
     * 按obj头锁住主线程等子线程处理完通知主线程再处理
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread03 thread = new Thread03(0);
            new Thread(thread).start();

            synchronized (OBJECT) {
                OBJECT.wait();
            }

            afterAssert(thread);

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + thread.getScore());
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    private static void afterAssert(Thread03 thread) {
        Assert.isTrue((thread.getScore() == 1), "pls check result!");
    }

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void run() {
        synchronized (OBJECT){
            try {
                setScore(1);
                LOGGER.debug("child thread process end,param:" + getScore());
                OBJECT.notify();
//                Thread.sleep(1000);
            } catch (Exception e) {
                LOGGER.error("run error:", e);
            }
        }
    }
}
