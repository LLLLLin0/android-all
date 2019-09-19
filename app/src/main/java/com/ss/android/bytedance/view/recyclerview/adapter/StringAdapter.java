package com.ss.android.bytedance.view.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss.android.bytedance.R;
import com.ss.android.bytedance.view.recyclerview.base.BaseAdapter;
import com.ss.android.bytedance.view.recyclerview.base.BaseViewHolder;

import java.util.List;

/**
 * Created on 2019-09-20.
 * linzhipeng.1996@bytedance.com
 */
public class StringAdapter extends BaseAdapter<String, BaseViewHolder> {

    public StringAdapter(List<String> list) {
        super(list);
    }

    @Override
    protected BaseViewHolder createVH(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_common_text_2, parent, false));
    }

    @Override
    protected void convert(BaseViewHolder holder, int position) {
        TextView item = holder.getView(R.id.text);
        item.setText(safeGet(position));
    }
}