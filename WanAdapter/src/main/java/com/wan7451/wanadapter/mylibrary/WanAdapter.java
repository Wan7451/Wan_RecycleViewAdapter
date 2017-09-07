package com.wan7451.wanadapter.mylibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 可以添加 Header,Footer的Adapter
 */
public abstract class WanAdapter<T> extends RecyclerView.Adapter<WanViewHolder> {

    protected Context mContext;
    protected List<T> mDatas;
    private LayoutInflater mInflater;
    private HeaderFooterViewHelper helper;


    protected WanAdapter(Context context, List<T> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        helper = new HeaderFooterViewHelper();
    }

    protected WanAdapter(Context context) {
        this.mContext = context;
        helper = new HeaderFooterViewHelper();
    }

    public void setDatas(List<T> mDatas) {
        if (mDatas == null)
            throw new RuntimeException("mDatas can not be null!");
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public LayoutInflater getInflater() {
        if (mInflater == null)
            mInflater = LayoutInflater.from(mContext);
        return mInflater;
    }


    public void addHeaderView(View headerView) {
        helper.addHeaderView(headerView);
    }

    public void addFooterView(View footerView) {
        helper.addFooterView(footerView);
    }

    public int getHeaderViewsCount() {
        return helper.headerSize();
    }


    @Override
    public int getItemViewType(int position) {
        if (helper.isHeaderPosition(position)) {
            return helper.getHeaderViewType(position);
        }

        if (helper.isFooterPosition(position, getDataCount())) {
            return helper.getFooterViewType(position);
        }

        if (helper.headerSize() > 0) {
            return getWanViewType(position - helper.headerSize());
        }
        return getWanViewType(position);
    }


    @Override
    public WanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int headerPosition = helper.getHeaderViewPosition(viewType);
        if (headerPosition != -1) {
            return new HeaderHolder(helper.getHeaderView(headerPosition));
        }

        int footerPosition = helper.getFooterViewPosition(viewType);
        if (footerPosition != -1) {
            int index = footerPosition - getDataCount() - helper.headerSize();
            return new FooterHolder(helper.getFooterView(index));
        }

        return onCreateWanViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(WanViewHolder holder, int position) {

        if (helper.footerSize() > 0 &&
                (position > getDataCount() - 1 + helper.headerSize())) {
            return;
        }

        if (helper.headerSize() > 0) {
            if (position < helper.headerSize()) {
                return;
            }
            onBindWanViewHolder(holder, position - helper.headerSize());
            return;
        }
        onBindWanViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return getDataCount() + helper.headerSize() + helper.footerSize();
    }


    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * ItemViewType
     *
     * @param position
     * @return return  itemViewType
     */
    protected int getWanViewType(int position) {
        return 0;
    }

    /**
     * Create View Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract WanViewHolder onCreateWanViewHolder(ViewGroup parent, int viewType);


    /**
     * bind View Holder
     *
     * @param holder
     * @param position
     */
    protected abstract void onBindWanViewHolder(WanViewHolder holder, int position);

    /**
     * ItemCount
     *
     * @return return  itemCount
     */
    protected int getDataCount() {
        return mDatas != null ? mDatas.size() : 0;
    }


    private static class HeaderHolder extends WanViewHolder {
        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private static class FooterHolder extends WanViewHolder {
        FooterHolder(View itemView) {
            super(itemView);
        }
    }

    private OnItemClickListener l;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.l = l;
    }

    public OnItemClickListener getItemClickListener() {
        return l;
    }

    public interface OnItemClickListener<T> {
        void onItemClickListener(int position, T data);
    }


}
