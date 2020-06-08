package com.leo.skin;

import android.app.Application;

import com.leo.skin.type.SkinManager;

/**
 * <p>Date:2020-04-04.12:56</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(getApplicationContext());
    }
}
