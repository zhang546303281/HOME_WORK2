package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Thread05 {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread05.class);

    private static String SCORE = "0";

    /**
     * countDownLatch计数阻塞，给两个线程数
     * @param args
     */
    public static void main(String[] args) {
        try {

            final CountDownLatch countDownLatch = new CountDownLatch(2);
            Thread thread = new Thread(() -> {
                SCORE = "1";
                LOGGER.debug("child thread process end,param:" + SCORE);
            });
            thread.start();
            countDownLatch.countDown();

            countDownLatch.await(100, TimeUnit.MILLISECONDS);//等待100毫秒
            Thread mainThread = Thread.currentThread();

            Assert.isTrue("1".equals(SCORE), "pls check result!");

            LOGGER.debug("main get param:" + SCORE);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }
}
