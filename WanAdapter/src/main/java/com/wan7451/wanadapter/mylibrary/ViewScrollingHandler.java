package com.wan7451.wanadapter.mylibrary;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


class ViewScrollingHandler extends RecyclerView.OnScrollListener {


    private SwipeRefreshRecyclerView pullLoadMoreRecyclerView;

    ViewScrollingHandler(SwipeRefreshRecyclerView pullLoadMoreRecyclerView) {
        this.pullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1)
                lastItem = gridLayoutManager.findLastVisibleItemPosition();

        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1)
                lastItem = linearLayoutManager.findLastVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastItem = findMax(lastPositions);
        }


        if (!pullLoadMoreRecyclerView.isRefresh()  //刷新状态
                && pullLoadMoreRecyclerView.isHasMore() //更多数据
                && (lastItem == totalItemCount - 1)   //最后一个Item
                && !pullLoadMoreRecyclerView.isLoadMore() //加载更多状态
                && (dx > 0 || dy > 0)  //滑动位置
                && (isScrollToBottom()))  //滑动到低部
        {
            pullLoadMoreRecyclerView.setIsLoadMore(true);
            pullLoadMoreRecyclerView.loadMore();
        }

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private boolean isScrollToBottom() {
        RecyclerView recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        int lastVisiblePosition = recyclerView.getChildAdapterPosition(
                recyclerView.getChildAt(recyclerView.getChildCount() - 1));

        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1) {
            return recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() <= recyclerView.getBottom();
        }
        return false;
    }

}
