package com.hw.demo.services.impl;

import com.hw.demo.aspect.FilterLog;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {

    @FilterLog(operationType = "OP-001")
    public void nettyServicerHandler(){
        System.out.println("netty服务主体业务逻辑方法执行");
    }
}
