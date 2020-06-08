package com.leo.hook.reflect;

import com.leo.hook.proxy.AMSMock;

import java.lang.reflect.Proxy;

/**
 * <p>Date:2020/6/4.5:09 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class AMSHookHelper {
    public static void hookActivityManager() {
        Object singleton = RefInvoke.getStaticFieldObject("android.app.ActivityTaskManager", "IActivityTaskManagerSingleton");
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", singleton, "mInstance");
        try {
            Class<?> aClass = Class.forName("android.app.IActivityTaskManager");

            Object proxyInstance = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader()
                    , new Class[]{aClass},
                    new AMSMock(mInstance));

            RefInvoke.setFieldObject("android.util.Singleton", singleton, "mInstance", proxyInstance);
        } catch (Exception e) {
            throw new RuntimeException("Hook AMS Failed");
        }
    }
}
