package com.leo.assets;

import android.content.Context;

/**
 * <p>Date:2020/6/8.2:30 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class Dynamic implements IDynamic{
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.plugin_str_hello);
    }
}
