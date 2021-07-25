package com.hw.demo.services.impl;

import com.hw.demo.aspect.FilterLog;
import com.hw.demo.aspect.RouteLog;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {

    @FilterLog(operationType = "FILTER-001")
    public void nettyServerHandler() {
        System.out.println("netty服务主体业务逻辑方法执行");
    }

    @RouteLog(operationType = "ROUTE-001")
    public void route(String time) {
        System.out.println("进入route方法time:" + time);
    }
}
