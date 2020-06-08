package com.leo.dex;

/**
 * <p>Date:2020-04-02.20:48</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class Content implements IShow{
    @Override
    public String getShowValue() {
        return BuildConfig.VERSION_NAME;
    }
}
