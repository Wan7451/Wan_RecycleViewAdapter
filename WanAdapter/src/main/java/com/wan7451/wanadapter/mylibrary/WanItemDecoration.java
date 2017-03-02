package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by WangGang on 2015/6/27.
 */
public class WanItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private final Drawable mDivider;

    private int mOrientation;

    private int leftMargin = 0;
    private int rightMargit = 0;


    private boolean isShowFirstItemDecoration = true;
    private boolean isShowSecondItemDecoration = true;


    public boolean isShowFirstItemDecoration() {
        return isShowFirstItemDecoration;
    }

    public void setIsShowFirstItemDecoration(boolean isShowFirstItemDecoration) {
        this.isShowFirstItemDecoration = isShowFirstItemDecoration;
    }

    public boolean isShowSecondItemDecoration() {
        return isShowSecondItemDecoration;
    }

    public void setIsShowSecondItemDecoration(boolean isShowSecondItemDecoration) {
        this.isShowSecondItemDecoration = isShowSecondItemDecoration;
    }

    public WanItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public WanItemDecoration setMarginLeftDP(int dp) {
        leftMargin = dp;
        return this;
    }

    public WanItemDecoration setMarginRightDP(int dp) {
        this.rightMargit = dp;
        return this;
    }

    private LinearLayoutManager getLinearLayoutManger(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {


        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + dip2px(parent.getContext(), leftMargin);
        final int right = parent.getWidth() - parent.getPaddingRight() - dip2px(parent.getContext(), rightMargit);

        final int childCount = parent.getChildCount();
        LinearLayoutManager layoutManager = getLinearLayoutManger(parent);
        if (layoutManager == null) {
            return;
        }
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            if (i == 0 && firstVisiblePosition == 0 && !isShowFirstItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            if (i == 1 && firstVisiblePosition == 0 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            if (i == 0 && firstVisiblePosition == 1 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        LinearLayoutManager layoutManager = getLinearLayoutManger(parent);
        if (layoutManager == null) {
            return;
        }
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            if (i == 0 && firstVisiblePosition == 0 && !isShowFirstItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            if (i == 1 && firstVisiblePosition == 0 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            if (i == 0 && firstVisiblePosition == 1 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
