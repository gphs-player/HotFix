package com.leo.skin;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArrayMap;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.fragment.app.FragmentActivity;

import com.leo.skin.type.ISkinChangedListener;
import com.leo.skin.type.SkinAttr;
import com.leo.skin.type.SkinAttrSupport;
import com.leo.skin.type.SkinManager;
import com.leo.skin.type.SkinView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2020-04-04.10:30</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {
    private static final String TAG = MainActivity.class.getSimpleName();


    private static final Class<?>[] sConstructorSignature = new Class<?>[]{
            Context.class, AttributeSet.class};

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();

    private final Object[] mConstructorArgs = new Object[2];
    private final Class[] mDelegateMethodArgs = new Class[]{View.class, String.class, Context.class, AttributeSet.class};
    private final Object[] mDelegateMethodParams = new Object[4];


    private Method mCreateViewMthod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinManager.getInstance().registListener(this);
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //1.完成系统先做的事情
                AppCompatDelegate delegate = getDelegate();
                View result = null;
                List<SkinAttr> skinAttrs;
                mDelegateMethodParams[0] = parent;
                mDelegateMethodParams[1] = name;
                mDelegateMethodParams[2] = context;
                mDelegateMethodParams[3] = attrs;
                try {
                    if (mCreateViewMthod == null) {
                        mCreateViewMthod = delegate.getClass().getMethod("createView", mDelegateMethodArgs);
                    }
                    result = (View) mCreateViewMthod.invoke(delegate, mDelegateMethodParams);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
                if (skinAttrs.isEmpty()){
                    return null;
                }

                if (result == null) {
                    result = createViewFromTag(context, name, attrs);
                }

                if (result != null) {
                    //准备换肤
                    injectSkin(result,skinAttrs);
                }

                return result;
            }
        });
        super.onCreate(savedInstanceState);

    }

    private void injectSkin(View view, List<SkinAttr> skinAttrs) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkin(this);
        if (skinViews == null){
            skinViews = new ArrayList<>();
            SkinManager.getInstance().add(this,skinViews);
        }
        skinViews.add(new SkinView(view,skinAttrs));
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createViewByPrefix(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createViewByPrefix(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix)
            throws InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = Class.forName(
                        prefix != null ? (prefix + name) : name,
                        false,
                        context.getClassLoader()).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegistListener(this);
    }

    @Override
    public void onSkinChanged() {

    }
}
