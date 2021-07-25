package com.hw.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {

    /**
     * 匹配以Impl结尾的类下的所有方法
     */
    @Pointcut("execution(* com.hw.demo..*Impl.*(..))")
    private void aspectMethod() {
    }

    @Pointcut("@annotation(com.hw.demo.aspect.FilterLog)")
    public void aspectMethodByAnnotation() {
    }

//    /**
//     * 方法执行后执行
//     * @param joinPoint
//     */
//    @AfterReturning(value = "aspectMethodByAnnotation()")
//    public void afterLogger(JoinPoint joinPoint) {
//
//    }

    @Around(value = "@annotation(com.hw.demo.aspect.FilterLog)")
    public void aroundLogger(ProceedingJoinPoint point) throws Throwable {
        System.out.println("方法执行前前置拦截处理input handler");//拦截器路由方法执行具体方法省略
        point.proceed();
        System.out.println("方法执行后后置拦截处理output handler");//拦截器路由方法执行具体方法省略
    }

    /**
     * 方法抛出异常时执行
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "aspectMethod()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        e.printStackTrace();
    }
}
