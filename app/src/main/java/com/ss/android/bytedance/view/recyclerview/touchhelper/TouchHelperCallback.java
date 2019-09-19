package com.ss.android.bytedance.view.recyclerview.touchhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created on 2019-09-19.
 * linzhipeng.1996@bytedance.com
 */
public class TouchHelperCallback extends ItemTouchHelper.Callback {

    public TouchHelperCallback() {

    }

    private int function = 0;

    /**
     * 1.用于确定拖动与滑动支持的方向
     * 2.makeMoveMentFlags的第一个参数用于标识拖动方向，可以支持上下左右，使其向对应方向拖动后出发onMove回调
     * 3.第二个参数用于表示滑动方向，一般用于侧滑操作，支持左右方向，对应拖动触发onSwiped回调
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        if ((function & 1) > 0) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        if ((function & 2) > 0) {
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 1.启动拖拽后，每次与另一个item重叠触发回调
     * 2.拖拽排序：
     *  <1>从fromPosition到toPosition，相邻item交互数据，达到fromItem移到toItem，中间的item对应移动一格
     *  <2>交换数据后调用notifyItemMove()实现交换item动画
     *  <3>拖拽结束后需要在clear里刷新一次数据，否则数据交换一些item对应的监听会乱
     * @param recyclerView
     * @param fromViewHolder 拖动的ViewHolder
     * @param toViewHolder 被覆盖的ViewHolder
     * @return 是否完成了本次操作
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder fromViewHolder,
                          @NonNull RecyclerView.ViewHolder toViewHolder) {

        int fromPosition = fromViewHolder.getAdapterPosition();
        int toPosition = toViewHolder.getAdapterPosition();

        /*
        拖拽到空格移动到末位实现
        1.判断toPosition是否是空格，如果是触发逻辑
        2.找到最后一个非空格的item的lastPosition，如果它不是fromPosition，即用fromPosition和lastPosition做交换算法
        List<String> appIdList = ((DragAppAdapter) recyclerView.getAdapter()).getData();
        String toPositionValue = appIdList.get(toPosition);
        if (TextUtils.equals(toPositionValue, DragAppAdapter.EMPTY)) {
            int last = 11;
            for (int i = last; i >= 0; i--) {
                String appId = appIdList.get(i);
                if (!TextUtils.equals(appId, UIConstants.EMPTY)) {
                    last = i;
                    break;
                }
            }
            if (fromPosition < last) {
                for (int i = fromPosition; i < last; i++) {
                    swapStringListIndexValue(appIdList, i, i + 1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition, last);
            return true;
        }*/

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                //swap(i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                //swap(i, i-1_;
            }
        }

        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 1.侧滑时回调
     * 2.侧滑删除：
     *  <1>在数据源删除对应的item，然后notify
     * @param viewHolder
     * @param flags 方向，4左8右
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int flags) {

    }

    /**
     * 1.可以用于改变拖动时item的背景
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //selected
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 1.松手时触发，用于刷新数据
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //onUnselected()
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 1.是否启动长按item进入拖拽
     * 2.可以关闭，然后手动使用startDrag()实现设置条件拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }
}
