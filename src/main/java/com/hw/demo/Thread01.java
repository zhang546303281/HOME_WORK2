package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class Thread01 {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread01.class);

    private int score = 0;

    /**
     * 最搓的方法主线程sleep
     * 只要保证sleep时间内子线程能处理完也能实现..
     * 注：此方式不能保证绝对的数据一致性和有序性
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread01 thread = new Thread01();
            thread.execute();

            Thread.sleep(100);

            afterAssert(thread);

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + thread.getScore());
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    private static void afterAssert(Thread01 thread) {
        Assert.isTrue((thread.getScore() == 1), "pls check result!");
    }

    private void execute(){
        Thread childThread = new Thread(() -> {
            setScore(1);
            LOGGER.debug("child thread process end,param:" + getScore());
        });
        childThread.start();
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}
