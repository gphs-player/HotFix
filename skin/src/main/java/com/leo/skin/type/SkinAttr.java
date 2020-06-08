package com.leo.skin.type;

import android.view.View;

/**
 * <p>Date:2020-04-04.11:09</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinAttr {


    private String mResName;
    private SkinAttrType mType;

    public SkinAttr(String resName, SkinAttrType type) {
        this.mResName = resName;
        this.mType = type;
    }

    public String getmResName() {
        return mResName;
    }

    public void setmResName(String mResName) {
        this.mResName = mResName;
    }

    public SkinAttrType getmType() {
        return mType;
    }

    public void setmType(SkinAttrType mType) {
        this.mType = mType;
    }

    public void apply(View view) {
        mType.apply(view,mResName);
    }
}
