package io.kimmking.rpcfx.demo.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AdviceMethod {
    String url() default "";
}
