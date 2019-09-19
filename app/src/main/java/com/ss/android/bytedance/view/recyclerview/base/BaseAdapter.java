package com.ss.android.bytedance.view.recyclerview.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2019-08-11.
 * linzhipeng.1996@bytedance.com
 * 1.简化设置view与子view监听
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder>
        extends RecyclerView.Adapter<H> {

    protected List<T> mData;
    private BaseClickListener mListener;
    private HashMap<String, Boolean> mCheckMap;

    public BaseAdapter(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        mData = list;
        initListener();
        initCheckMap();
    }

    protected abstract H createVH(ViewGroup parent, int viewType);

    protected abstract void convert(H holder, int position);

    protected void initListener() {
        //mListenenr = new FunctionClickListenenrAdapter(){...}
    }

    private void initCheckMap() {
        mCheckMap = new HashMap<>();
        updateCheckMap();
    }

    protected void updateCheckMap() {

    }

    public void refresh(List<T> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public T safeGet(int position) {
        if (mData != null && 0 <= position && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    protected void setOnItemClick(View view, int position) {
        if (mListener != null) {
            view.setOnClickListener(v -> mListener.onClick(this, v, position));
        }
    }

    protected void setOnChildClick(View childView, int position) {
        if (mListener != null) {
            childView.setOnClickListener(v -> mListener.onChildClick(this, childView, position));
        }
    }

    protected void setOnLongClick(View view, int position, BaseViewHolder viewHolder) {
        if (mListener != null) {
            view.setOnLongClickListener(v -> mListener.onLongClick(
                    this, view, position, viewHolder));
        }
    }

    public void setListener(BaseClickListener listener) {
        mListener = listener;
    }

    public List<T> getData() {
        return mData;
    }

    public HashMap<String, Boolean> getCheckMap() {
        return mCheckMap;
    }

    public void setCheckMap(HashMap<String, Boolean> checkMap) {
        mCheckMap = checkMap;
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
