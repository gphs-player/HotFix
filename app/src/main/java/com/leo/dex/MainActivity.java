package com.leo.dex;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


public class MainActivity extends Activity {

    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        version = findViewById(R.id.version);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }

        IShow content = new Content();
        version.setText(content.getShowValue());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getDex(){
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test.dex");
        if (!file.exists()) {
            //补丁不存在，返回
            System.err.println("补丁不存在，返回");
            return;
        }
        String dexPath = file.getAbsolutePath();
        String dexOutput = getApplicationInfo().dataDir;
        String nativeLibraryDir = getApplicationInfo().nativeLibraryDir;
        DexClassLoader dexClassLoader = new DexClassLoader(
                dexPath,
                dexOutput
                , nativeLibraryDir
                , this.getClass().getClassLoader());
        try {
            Class<?> aClass = dexClassLoader.loadClass("com.leo.dex.Test");
            //Version 1
//            Method getVersion = aClass.getDeclaredMethod("getVersion", new Class[]{});
//            Object invoke = getVersion.invoke(aClass.newInstance(), new Object[]{});
//            version.setText(String.valueOf(invoke));

            //Version 2
            IShow instance = (IShow) aClass.newInstance();
            version.setText(instance.getShowValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNext(View view) {
//            startActivity(new Intent(this, AspectActivity.class));
//            getDex();
    }
}
