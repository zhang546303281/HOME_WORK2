package com.hw.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;

public class Thread13 implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Thread13.class);

    private final static BlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();

    /**
     * 放共享队列中等达到预期size作为一个批次进行处理
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Thread13 thread = new Thread13();
            Thread t = new Thread(thread);
            t.start();

            while (true) {
                if (QUEUE.size() == 10) {
                    break;
                }
            }

            Thread mainThread = Thread.currentThread();

            LongAdder longAdder = new LongAdder();
            while (true){
                if(QUEUE.isEmpty()){
                    break;
                }else{
                    String item = QUEUE.take();
                    longAdder.increment();
                    LOGGER.debug("main get param,index:{},param:{}", longAdder.sum(), item);
                }
            }
            mainThread.interrupt();

        } catch (Exception e) {
            LOGGER.error("err:", e);
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 10; i < 20; i++) {
                QUEUE.put("MyCard:" + i);
            }
        } catch (InterruptedException e) {
            LOGGER.error("run err:", e);
        }
    }
}
