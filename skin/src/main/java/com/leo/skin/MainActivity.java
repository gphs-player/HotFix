package com.leo.skin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseSkinActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }

        FrameLayout content = findViewById(R.id.container);
        final DrawerLayout drawerLayout = findViewById(R.id.main);

        ListView listview = findViewById(R.id.listView);
        List<String> data = new ArrayList<>();
        initData(data);
        SkinAdapter adapter = new SkinAdapter(this, data);
        listview.setAdapter(adapter);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,new SideFragment()).commit();
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                View content = drawerLayout.getChildAt(0);
                View menu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                if (drawerView.getTag().equals("LEFT")){
                    float leftScale = 1 -0.3f * scale;
                    ViewHelper.setScaleX(menu,leftScale);
                    ViewHelper.setScaleY(menu,leftScale);
                    ViewHelper.setAlpha(menu,0.6f + 0.4f * (1-leftScale) );
                    ViewHelper.setTranslationX(content,menu.getMeasuredWidth() * (1-scale));
                    ViewHelper.setPivotX(content,0);
                    ViewHelper.setPivotX(content,content.getMeasuredHeight()/2);
                    content.invalidate();
                    ViewHelper.setScaleX(content,rightScale);
                    ViewHelper.setScaleY(content,rightScale);
                }
            }
        });
    }

    private void initData(List<String> data) {
        data.add("设计模式");
        data.add("数据结构");
        data.add("算法");
        data.add("多线程");
        data.add("虚拟机");
        data.add("Activity");
        data.add("Service");
        data.add("Fragment");
    }


}
