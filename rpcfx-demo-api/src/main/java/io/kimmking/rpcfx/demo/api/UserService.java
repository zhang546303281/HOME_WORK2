package io.kimmking.rpcfx.demo.api;

@RpcInterface
public interface UserService {
    @AdviceMethod
    User findById(int id);
}
