package com.leo.hook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.leo.hook.R;
import com.leo.hook.proxy.Evilinstrumentation;
import com.leo.hook.reflect.AMSHookHelper;
import com.leo.hook.reflect.RefInvoke;

/**
 * HOOK 当前Activity的 mInstrumentation 字段，startActivity方法
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //2.hook ActivityManager
        AMSHookHelper.hookActivityManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.hook Instrumentation
        Instrumentation mInstrumentation = (Instrumentation) RefInvoke.getFieldObject(Activity.class, this, "mInstrumentation");
        Instrumentation proxy = new Evilinstrumentation(mInstrumentation);
        RefInvoke.setFieldObject(Activity.class, this, "mInstrumentation", proxy);
    }

    public void start(View view) {
        Intent intent = new Intent(this, TargetActivity.class);
        startActivity(intent);
    }
}