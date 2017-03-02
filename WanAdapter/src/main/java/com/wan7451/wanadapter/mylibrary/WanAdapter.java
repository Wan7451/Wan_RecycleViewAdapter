package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class WanAdapter<T> extends RecyclerView.Adapter<WanViewHolder> {


    private final ArrayList<View> mHeaderViews = new ArrayList<>(); //头视图
    private final ArrayList<View> mFooterViews = new ArrayList<>();   //尾视图

    private final ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private final ArrayList<Integer> mFooterViewTypes = new ArrayList<>();

    private static final int TYPE_OFFSET = 29175;


    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas;
    private final int mItemLayoutId;



    protected WanAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            return getHeaderViewType(position);
        }

        if (mFooterViews.size() > 0 && position > getDataCount() - 1 + mHeaderViews.size()) {
            return getFooterViewType(position);
        }

        if (mHeaderViews.size() > 0) {
            return getViewType(position - mHeaderViews.size());
        }
        return getViewType(position);
    }


    private int getHeaderViewType(int position) {
        mHeaderViewTypes.add(position + TYPE_OFFSET);
        return position + TYPE_OFFSET;
    }

    private int getHeaderViewPosition(int viewType) {
        if (mHeaderViewTypes.contains(viewType)) {
            return viewType - TYPE_OFFSET;
        }
        return -1;
    }

    private int getFooterViewType(int position) {
        mFooterViewTypes.add(position + TYPE_OFFSET);
        return position + TYPE_OFFSET;
    }

    private int getFooterViewPosition(int viewType) {
        if (mFooterViewTypes.contains(viewType)) {
            return viewType - TYPE_OFFSET;
        }
        return -1;
    }



    protected int getViewType(int position) {
        return 1;
    }

    private int getDataCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    private void onBindWanViewHolder(WanViewHolder holder, int i) {
        convert(holder, mDatas.get(i));
    }

    public abstract void convert(WanViewHolder holder, T item);



    private WanViewHolder onCreateWanViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(mItemLayoutId, null, false);
        return new WanViewHolder(v, this);
    }

    @Override
    public WanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int headerPosition = getHeaderViewPosition(viewType);
        if (headerPosition != -1) {
            return new HeaderHolder(mHeaderViews.get(headerPosition));
        }

        int footerPosition = getFooterViewPosition(viewType);
        if (footerPosition != -1) {
            int index = footerPosition - getDataCount() - mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(index));
        }

        return onCreateWanViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(WanViewHolder holder, int position) {

        if (mFooterViews.size() > 0 && (position > getDataCount() - 1 + mHeaderViews.size())) {
            return;
        }

        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
            onBindWanViewHolder(holder, position - mHeaderViews.size());
            return;
        }
        onBindWanViewHolder(holder, position);
    }


    class HeaderHolder extends WanViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends WanViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getDataCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getDataCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getDataCount() + mFooterViews.size();
        }

        return getDataCount();
    }

    public void setDatas(List<T> mDatas) {
        if(mDatas==null)
            throw new RuntimeException("mDatas can not be null!");
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    public void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
    }

    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }


    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }


    private OnItemClickListener l;

    protected OnItemClickListener getItemClickListener() {
        return l;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.l = l;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int posotion);
    }


}
