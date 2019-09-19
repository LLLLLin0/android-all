package com.ss.android.bytedance.view.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss.android.bytedance.R;
import com.ss.android.bytedance.R2;
import com.ss.android.bytedance.view.recyclerview.adapter.StringAdapter;
import com.ss.android.bytedance.view.recyclerview.base.BaseAdapter;
import com.ss.android.bytedance.view.recyclerview.base.BaseViewHolder;
import com.ss.android.bytedance.view.recyclerview.touchhelper.DragCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2019-09-19.
 * linzhipeng.1996@bytedance.com
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R2.id.recycler) RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recycler_drag_activity);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        StringAdapter stringAdapter = new StringAdapter(list);
        mRecyclerView.setAdapter(stringAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }



}
