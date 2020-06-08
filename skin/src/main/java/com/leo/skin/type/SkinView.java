package com.leo.skin.type;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2020-04-04.11:05</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinView {

    private View mView;
    private List<SkinAttr> mAttrs;


    public SkinView(View view, List<SkinAttr> attrs) {
        this.mView = view;
        this.mAttrs = attrs;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public List<SkinAttr> getmAttrs() {
        return mAttrs;
    }

    public void setmAttrs(List<SkinAttr> mAttrs) {
        this.mAttrs = mAttrs;
    }

    public void apply(){
        for (SkinAttr mAttr : mAttrs) {

        mAttr.apply(mView);
        }
    }
}
