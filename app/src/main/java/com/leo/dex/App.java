package com.leo.dex;

import android.content.Context;

import androidx.multidex.MultiDexApplication;


/**
 * <p>Date:2020-04-02.19:49</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class App extends MultiDexApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        HotFixManager.installPatchDex(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
