package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Thread04 {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread04.class);

    private static String SCORE = "0";

    /**
     * future子线程执行，主线程get阻塞获取同步结果
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            CallableBean callableBean = new CallableBean();
            FutureTask<String> futureTask = new FutureTask<>(callableBean);
            Thread thread = new Thread(futureTask);
            thread.start();

            String result = futureTask.get();

            Assert.isTrue(result.equals(SCORE), "pls check result!");

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + result);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    static final class CallableBean implements Callable<String> {
        @Override
        public String call() throws Exception {
            SCORE = "1";
            LOGGER.debug("child thread process end,param:" + SCORE);
            return SCORE;
        }
    }
}
