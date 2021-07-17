package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thread07 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread07.class);

    private volatile static int STATUS = 0;

    /**
     * 定义线程共享成员变量,阻塞主线程轮询
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread07 thread = new Thread07();
            Thread t = new Thread(thread);
            t.start();

            while (true) {
                if (STATUS == 1)
                    break;
            }

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + STATUS);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    @Override
    public void run() {
        STATUS = 1;
        LOGGER.debug("child thread process end,param:" + STATUS);
    }
}
