package com.leo.hook.proxy;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.leo.hook.reflect.RefInvoke;

/**
 * <p>Date:2020/6/4.4:47 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class Evilinstrumentation extends Instrumentation {
    private Instrumentation mProxy;

    public Evilinstrumentation(Instrumentation mProxy) {
        this.mProxy = mProxy;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        System.out.println("*******HOOK START*******");
        //反射调用非公开方法
        Class[] p1 = {Context.class, IBinder.class, IBinder.class,
                Activity.class, Intent.class, int.class, Bundle.class};
        Object[] v1 = {who,contextThread,token,target,intent,requestCode,options};
        return (ActivityResult) RefInvoke.invokeInstanceMethod(mProxy, "execStartActivity", p1, v1);
    }


}
