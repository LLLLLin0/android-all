package com.ss.android.bytedance.view.recyclerview.base;

import android.view.View;

/**
 * Created on 2019-08-11.
 * linzhipeng.1996@bytedance.com
 */
public interface BaseClickListener<T extends BaseAdapter> {
    void onClick(T adapter, View view, int position);

    void onChildClick(T adapter, View child, int position);

    boolean onLongClick(T adapter, View child, int position,
                        BaseViewHolder viewHolder);
}
