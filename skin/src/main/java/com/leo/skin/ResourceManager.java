package com.leo.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.leo.skin.type.Const;

/**
 * <p>Date:2020-04-03.18:19</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class ResourceManager {

    private Resources mResources;
    private String pkgName;
    public ResourceManager(Resources resources,String pkName) {
        this.mResources = resources;
        pkgName = pkName;
    }

    public Drawable getDrawableFromAPK(String name){
        return mResources.getDrawable(mResources.getIdentifier(name,"drawable", pkgName));
    }


    public ColorStateList getColorFromAPK(String name){
        return mResources.getColorStateList(mResources.getIdentifier(name,"color",pkgName));
    }



}
