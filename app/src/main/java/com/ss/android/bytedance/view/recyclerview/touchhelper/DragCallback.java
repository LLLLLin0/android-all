package com.ss.android.bytedance.view.recyclerview.touchhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ss.android.bytedance.util.Logger;
import com.ss.android.bytedance.util.Util;
import com.ss.android.bytedance.view.recyclerview.adapter.StringAdapter;

import java.util.List;

/**
 * Created on 2019-09-19.
 * linzhipeng.1996@bytedance.com
 */
public class DragCallback extends ItemTouchHelper.Callback {
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder fromViewHolder,
                          @NonNull RecyclerView.ViewHolder toViewHolder) {
        StringAdapter adapter = (StringAdapter) recyclerView.getAdapter();

        int fromPosition = fromViewHolder.getAdapterPosition();
        int toPosition = toViewHolder.getAdapterPosition();

        List<String> data = adapter.getData();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Util.swapListValue(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Util.swapListValue(data, i, i - 1);
            }
        }

        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Logger.i(Logger.PATH1, "view position:" + viewHolder.getLayoutPosition() + " i:" + i);
    }
}
