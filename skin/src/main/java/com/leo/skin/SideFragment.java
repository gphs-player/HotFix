package com.leo.skin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.leo.skin.type.Const;
import com.leo.skin.type.ISkinChangeCallback;
import com.leo.skin.type.SkinManager;

import java.io.File;
import java.lang.reflect.Method;

/**
 * <p>Date:2020-04-03.14:42</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SideFragment extends Fragment implements View.OnClickListener {
    private View mMianLeft;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_side, container, false);
        mMianLeft = view.findViewById(R.id.leftMain);
        View appChangeOne = view.findViewById(R.id.appChangeOne);
        View appChangeTwo = view.findViewById(R.id.appChangeTwo);
        View patch = view.findViewById(R.id.patch);
        View reset = view.findViewById(R.id.reset);
        appChangeOne.setOnClickListener(this);
        appChangeTwo.setOnClickListener(this);
        patch.setOnClickListener(this);
        reset.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.appChangeOne:
                changeBg();
                break;
            case R.id.appChangeTwo:
                break;
            case R.id.patch:
                break;
            case R.id.reset:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeBg() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "plugin.apk";
        SkinManager.getInstance().changeSkin(path, Const.PLUGIN_APK_NAME, new ISkinChangeCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                System.out.println("换肤失败");
//                Toast.makeText(getActivity(), "换肤失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(getActivity(), "换肤成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
