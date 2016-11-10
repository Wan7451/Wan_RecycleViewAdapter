package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 可以刷新的RecycleView
 * Created by Hello on 2015/6/30.
 */
public class WanRecycleView extends PullToRefreshBase<RecyclerView> {

    public WanRecycleView(Context context) {
        super(context);
    }

    public WanRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WanRecycleView(Context context, Mode mode) {
        super(context, mode);
    }

    public WanRecycleView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    //重写4个方法
    //1 滑动方向
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    //重写4个方法
    //2  滑动的View
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView view = new RecyclerView(context, attrs);
        return view;
    }

    //重写4个方法
    //3 是否滑动到底部
    @Override
    protected boolean isReadyForPullEnd() {
        int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(getRefreshableView().getChildAt(getRefreshableView().getChildCount() -1));
        if (lastVisiblePosition >= getRefreshableView().getAdapter().getItemCount()-1) {
            return getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1).getBottom() <= getRefreshableView().getBottom();
        }
        return false;
    }

    //重写4个方法
    //4 是否滑动到顶部
    @Override
    protected boolean isReadyForPullStart() {
        if (getRefreshableView().getChildCount() <= 0)
            return true;
        int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(getRefreshableView().getChildAt(0));
        if (firstVisiblePosition == 0)
            return getRefreshableView().getChildAt(0).getTop() == getRefreshableView().getPaddingTop();
        else
            return false;
    }


}
