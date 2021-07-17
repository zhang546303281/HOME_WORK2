package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class Thread02 {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread02.class);

    private int score = 0;

    /**
     * join阻塞获取到结果后再执行外面的主线程
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread02 thread = new Thread02();
            thread.execute();

            afterAssert(thread);

            LOGGER.debug("main get param:" + thread.getScore());
            Thread mainThread = Thread.currentThread();
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    private static void afterAssert(Thread02 thread) {
        Assert.isTrue((thread.getScore() == 1), "pls check result!");
    }

    private void execute() throws InterruptedException {
        Thread childThread = new Thread(() -> {
            setScore(1);
            LOGGER.debug("child thread process end,param:" + getScore());
        });
        childThread.start();
        childThread.join();
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}
