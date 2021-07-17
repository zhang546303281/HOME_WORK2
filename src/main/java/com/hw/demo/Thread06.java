package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Vector;

public class Thread06 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread06.class);

    private static Vector<String> VECTOR = new Vector<>();

    /**
     * 使用VECTOR集合只要保证子线程处理完主线程也可以获取对应的修改数据
     * 注：此方式不能保证绝对的数据一致性和有序性(如果子线程先行,此时子线程与主线程获取到VECTOR[0]值是不同的,而子线程是否先行也是无法保证与控制的)
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread06 thread = new Thread06();
            new Thread(thread).start();

            Thread.sleep(100);

            Assert.isTrue("1".equals(VECTOR.get(0)), "pls check result!");

            Thread mainThread = Thread.currentThread();
            LOGGER.debug("main get param:" + VECTOR);
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    @Override
    public void run() {
        VECTOR.add("1");
        LOGGER.debug("child thread process end,param:" + VECTOR);
    }
}
