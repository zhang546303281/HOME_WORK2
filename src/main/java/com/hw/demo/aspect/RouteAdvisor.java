package com.hw.demo.aspect;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

public class RouteAdvisor {
    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        if (null != method.getAnnotation(RouteLog.class)) {
            System.out.println("进入方法" + method.getName() + "-args:" + Arrays.toString(arguments));
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return(readOnly = false, typing = DYNAMIC) Object ret) {
        if (null != method.getAnnotation(RouteLog.class)) {
            System.out.println("方法出栈" + method.getName() + "-args:" + Arrays.toString(arguments) + "-return:" + ret);
        }
    }
}
