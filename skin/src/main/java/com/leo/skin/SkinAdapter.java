package com.leo.skin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * <p>Date:2020-04-03.14:31</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinAdapter extends BaseAdapter {
    Context ctx;
    List<String> mData;

    public SkinAdapter(Context context, List<String> list) {
        ctx = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        SkinHolder holder;
        if (convertView == null) {
            holder = new SkinHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_text, viewGroup, false);
            convertView.setTag(holder);
            holder.root = (TextView) convertView;
        } else {
            holder = (SkinHolder) convertView.getTag();
        }
        holder.root.setText(String.valueOf(getItem(i)));
        return convertView;
    }


    static class SkinHolder {
        TextView root;
    }

}
