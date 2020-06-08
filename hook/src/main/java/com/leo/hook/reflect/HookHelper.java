package com.leo.hook.reflect;


import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Proxy;

/**
 * <p>Date:2020/6/4.4:09 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
class HookHelper {
    public static void hookActivityManager() {
        Object singleton = RefInvoke.getStaticFieldObject("android.app.ActivityManager", "IActivityManagerSingleton");
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", singleton, "mInstance");
        try {
            Class<?> aClass = Class.forName("android.app.IActivityManager");

            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , new Class[]{aClass}, new HookHandler(mInstance));

            RefInvoke.setFieldObject("android.util.Singleton", singleton, "mInstance", proxyInstance);
        } catch (Exception e) {
            throw new RuntimeException("Hook AMS Failed");
        }
    }

    public static void hookPackageManager(Context context) {
        try {
            Object currentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
            Object sPackageManager = RefInvoke.getFieldObject("android.app.ActivityThread", currentActivityThread, "sPackageManager");
            Class<?> iPmsInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , new Class[]{iPmsInterface}, new HookHandler(sPackageManager));
            //替换ActivityThread的sPackageManager
            RefInvoke.setFieldObject(sPackageManager, "sPackageManager", proxyInstance);
            //替换ApplicationPackageManager的mPM
            PackageManager pm = context.getPackageManager();
            RefInvoke.setFieldObject(pm, "mPM", proxyInstance);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Hook Failed");
        }

    }
}
