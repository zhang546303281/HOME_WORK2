package io.kimmking.rpcfx.api;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.AdviceMethod;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class CreateBeanAdvisor {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.AllArguments Object[] arguments, @Advice.Origin Method method) {
        return;
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.AllArguments Object[] arguments, @Advice.Origin Method method, @Advice.Origin Class<?> cls) {
        if (null == method.getAnnotation(AdviceMethod.class)) {
            return;
        }

        try {
            Class<?> inPutClass = Class.forName(cls.getSuperclass().getName());
            Rpcfx.RpcfxInvocationHandler handler = new Rpcfx.RpcfxInvocationHandler(inPutClass, method.getAnnotation(AdviceMethod.class).url(), null);
            handler.invoke(null, method, arguments);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
