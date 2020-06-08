package com.leo.hook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>Date:2020/6/4.5:08 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class AMSMock implements InvocationHandler {
    private Object mBase;

    public AMSMock(Object obj) {
        mBase = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            System.out.println("HOOK startActivity SUCCESS");
            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }
}
