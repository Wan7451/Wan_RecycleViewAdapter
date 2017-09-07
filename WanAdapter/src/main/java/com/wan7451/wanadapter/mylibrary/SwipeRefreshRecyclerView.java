package com.wan7451.wanadapter.mylibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 通用 的 支持上拉加载更多 下拉刷新的recyclerview
 */

public class SwipeRefreshRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PullLoadMoreListener pullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private View footerView;
    private int headerDividerHeight;//顶部分隔线高度

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        initView(context);

    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pull_loadmore_recyclerview_layout, this, true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewMore);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new ViewScrollingHandler(this));
        recyclerView.setOnTouchListener(new TouchRecyclerView());

        footerView = findViewById(R.id.footerView);
        footerView.setVisibility(View.GONE);
    }


    public int getHeaderDividerHeight() {
        return headerDividerHeight;
    }

    public void setHeaderDividerHeight(int headerDividerHeight) {
        this.headerDividerHeight = headerDividerHeight;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
    }


    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    class TouchRecyclerView implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return isRefresh || isLoadMore;
        }
    }


    public void refresh() {
        if (pullLoadMoreListener != null) {
            isRefresh = true;
            pullLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (pullLoadMoreListener != null && hasMore) {
            footerView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            footerView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            swipeRefreshLayout.setEnabled(false);
            pullLoadMoreListener.onLoadMore();

        }
    }

    public void setBottomRefreshViewMargin(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) footerView.getLayoutParams();
        lp.setMargins(left, top, right, bottom);
        footerView.setLayoutParams(lp);
    }

    public void setPullLoadMoreCompleted() {

        isRefresh = false;
        setRefreshing(false);
        isLoadMore = false;
        footerView.animate()
                .translationY(footerView.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        footerView.setVisibility(View.GONE);
                        swipeRefreshLayout.setEnabled(true);
                    }
                })
                .start();
    }

    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        pullLoadMoreListener = listener;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public interface PullLoadMoreListener {

        void onRefresh();

        void onLoadMore();
    }


}
