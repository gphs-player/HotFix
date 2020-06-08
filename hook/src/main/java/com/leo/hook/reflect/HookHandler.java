package com.leo.hook.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>Date:2020/6/4.4:15 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
class HookHandler implements InvocationHandler {
    private Object mBase;
    public HookHandler(Object mInstance) {
        mBase = mInstance;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println(method.getName() + "Hooked !");
        return method.invoke(mBase,objects);
    }
}
