package com.wan7451.wanadapter.mylibrary;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by wan7451 on 2017/9/4.
 * desc:
 */

public class HeaderFooterViewHelper {

    private final ArrayList<View> mHeaderViews; //头视图
    private final ArrayList<View> mFooterViews;   //尾视图

    private final ArrayList<Integer> mHeaderViewTypes;
    private final ArrayList<Integer> mFooterViewTypes;

    private static final int TYPE_OFFSET = 29175;


    protected HeaderFooterViewHelper() {
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
        mHeaderViewTypes = new ArrayList<>();
        mFooterViewTypes = new ArrayList<>();
    }

    protected int getHeaderViewType(int position) {
        mHeaderViewTypes.add(position + TYPE_OFFSET);
        return position + TYPE_OFFSET;
    }

    protected int getHeaderViewPosition(int viewType) {
        if (mHeaderViewTypes.contains(viewType)) {
            return viewType - TYPE_OFFSET;
        }
        return -1;
    }

    protected int getFooterViewType(int position) {
        mFooterViewTypes.add(position + TYPE_OFFSET);
        return position + TYPE_OFFSET;
    }

    protected int getFooterViewPosition(int viewType) {
        if (mFooterViewTypes.contains(viewType)) {
            return viewType - TYPE_OFFSET;
        }
        return -1;
    }

    protected void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
    }

    protected void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }


    protected boolean isHeaderPosition(int position) {
        return mHeaderViews.size() > 0 && position < mHeaderViews.size();
    }

    protected boolean isFooterPosition(int position, int dataCount) {
        return mFooterViews.size() > 0 && position > dataCount - 1 + mHeaderViews.size();
    }

    protected int headerSize() {
        return mHeaderViews.size();
    }

    protected int footerSize() {
        return mFooterViews.size();
    }

    protected View getHeaderView(int position) {
        return mHeaderViews.get(position);
    }

    protected View getFooterView(int position) {
        return mFooterViews.get(position);
    }
}
