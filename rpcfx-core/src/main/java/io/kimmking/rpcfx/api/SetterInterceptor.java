package io.kimmking.rpcfx.api;

import net.bytebuddy.implementation.bind.annotation.FieldProxy;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class SetterInterceptor {

    interface FieldGetter {
        Object getValue();
    }

    interface FieldSetter {
        void setValue(Object value);
    }

    @RuntimeType
    public Object intercept(@FieldProxy("stringVal") FieldGetter accessor) {
        Object value = accessor.getValue();
        System.out.println("Invoked method with: " + value);
        return value;
    }
}
