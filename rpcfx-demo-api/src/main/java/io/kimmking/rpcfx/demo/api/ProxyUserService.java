package io.kimmking.rpcfx.demo.api;

public class ProxyUserService implements UserService{

    @AdviceMethod(url = "http://localhost:8080/")/*最好读取服务注册中心的地址*/
    @Override
    public User findById(int id) {
        System.out.println("proxyUserService start"+id);
        return new User();
    }
}
