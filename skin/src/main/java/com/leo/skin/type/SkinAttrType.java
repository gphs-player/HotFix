package com.leo.skin.type;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.skin.ResourceManager;

/**
 * <p>Date:2020-04-04.11:10</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public enum  SkinAttrType {
    BACKGROUND("background"){
        @Override
        public void apply(View view, String resName) {
            Drawable drawableFromAPK = getResManager().getDrawableFromAPK(resName);
            if (drawableFromAPK != null) {
                (view).setBackgroundDrawable(drawableFromAPK);
            }else {
                Log.e(TAG,"background load error");
            }
        }
    }
    ,SRC("src") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawableFromAPK = getResManager().getDrawableFromAPK(resName);
            if (view instanceof ImageView && drawableFromAPK != null){

                ((ImageView) view).setImageDrawable(drawableFromAPK);
            }else {
                Log.e(TAG,"src load error");
            }
        }
    },TEXT_COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof TextView){
                ColorStateList colorFromAPK = getResManager().getColorFromAPK(resName);
                if (colorFromAPK != null) {
                    ((TextView) view).setTextColor(colorFromAPK);
                }
            }
        }
    };


    private static final String TAG = "SkinAttrType";
    private String mType;

    public String getType() {
        return mType;
    }

    SkinAttrType(String name) {
        mType = name;
    }

    public abstract void apply(View view, String resName);


    public ResourceManager getResManager(){
        ResourceManager resourceManager = SkinManager.getInstance().getResourceManager();
        return resourceManager;
    }
}
