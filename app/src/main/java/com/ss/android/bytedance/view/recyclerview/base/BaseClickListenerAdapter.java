package com.ss.android.bytedance.view.recyclerview.base;

import android.view.View;

/**
 * Created on 2019-08-11.
 * linzhipeng.1996@bytedance.com
 */
public class BaseClickListenerAdapter<T extends BaseAdapter> implements BaseClickListener<T> {

    @Override
    public void onClick(T adapter, View view, int position) {

    }

    @Override
    public void onChildClick(T adapter, View child, int position) {

    }

    @Override
    public boolean onLongClick(T adapter, View child, int position, BaseViewHolder viewHolder) {
        return false;
    }
}
