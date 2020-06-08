package com.leo.skin.type;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;

import com.leo.skin.ResourceManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2020-04-04.11:18</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinManager {

    private static SkinManager mInstance;

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    private Context mCtx;
    private ResourceManager resourceManager;

    private List<ISkinChangedListener> mListenters = new ArrayList<>();
    private Map<ISkinChangedListener, List<SkinView>> mMap = new HashMap<>();

    public void init(Context context) {
        mCtx = context;
    }

    public void registListener(ISkinChangedListener listener) {
        mListenters.add(listener);
    }

    public void unRegistListener(ISkinChangedListener listener) {
        mListenters.remove(listener);
        mMap.remove(listener);
    }


    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void loadPlugin(String path, String pkName) {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            AssetManager manager = AssetManager.class.newInstance();
            Method addAssetPath = manager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(manager, path);

            Resources superResource = mCtx.getResources();
            Resources resources = new Resources(manager, superResource.getDisplayMetrics(), superResource.getConfiguration());
            resourceManager = new ResourceManager(resources, pkName);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<SkinView> getSkin(ISkinChangedListener key) {
        return mMap.get(key);
    }

    public void add(ISkinChangedListener key, List<SkinView> list) {
        mMap.put(key, list);
    }


    public void changeSkin(final String path, final String pluginApkName, ISkinChangeCallback iSkinChangeCallback) {
        if (iSkinChangeCallback == null) {
            iSkinChangeCallback = ISkinChangeCallback.DEFAULT;
        }
        final ISkinChangeCallback callback = iSkinChangeCallback;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    loadPlugin(path, pluginApkName);
                } catch (Exception e) {
                    callback.onError(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                notifyChangeSkin();
                callback.onComplete();
            }
        }.execute();
    }

    private void notifyChangeSkin() {
        for (ISkinChangedListener listener : mListenters) {
            changeSkinReal(listener);
            listener.onSkinChanged();
        }
    }

    private void changeSkinReal(ISkinChangedListener listener) {
        List<SkinView> skinViews = mMap.get(listener);
        if (skinViews == null || skinViews.isEmpty()) return;
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }
}
