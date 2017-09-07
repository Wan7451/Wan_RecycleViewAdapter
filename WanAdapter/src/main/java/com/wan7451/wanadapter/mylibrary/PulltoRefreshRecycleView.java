package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by Hello on 2015/6/30.
 */
public class PulltoRefreshRecycleView extends PullToRefreshBase<RecyclerView> {

    public PulltoRefreshRecycleView(Context context) {
        super(context);
    }

    public PulltoRefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PulltoRefreshRecycleView(Context context, Mode mode) {
        super(context, mode);
    }

    public PulltoRefreshRecycleView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView view = new RecyclerView(context, attrs);
        view.setId(R.id.recycleView);
        return view;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(getRefreshableView().getChildAt(getRefreshableView().getChildCount() -1));
        if (lastVisiblePosition >= getRefreshableView().getAdapter().getItemCount()-1) {
            return getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1).getBottom() <= getRefreshableView().getBottom();
        }
        return false;
    }

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
