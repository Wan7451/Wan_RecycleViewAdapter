package com.wanadapter.wan7451.wan_recycleviewadapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.wan7451.wanadapter.mylibrary.SimpleWanAdapter;
import com.wan7451.wanadapter.mylibrary.WanAdapter;
import com.wan7451.wanadapter.mylibrary.WanItemDecoration;
import com.wan7451.wanadapter.mylibrary.WanPulltoRefreshRecycleView;
import com.wan7451.wanadapter.mylibrary.WanViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WanAdapter.OnItemClickListener {

    private ArrayList<String> data = new ArrayList<>();
    private WGAdapter adapter;
    private WanPulltoRefreshRecycleView mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (WanPulltoRefreshRecycleView) findViewById(R.id.mianView);

        adapter = new WGAdapter(this, data, android.R.layout.simple_list_item_1);
        mainView.getRefreshableView().setAdapter(adapter);


        ImageView headerView = new ImageView(this);
        headerView.setImageResource(R.mipmap.ic_launcher);

        adapter.addHeaderView(headerView); //添加头视图


        Button footerView = new Button(this);
        footerView.setText("load");
        adapter.addFooterView(footerView); //添加尾视图


        WanItemDecoration item = new WanItemDecoration(this, WanItemDecoration.VERTICAL_LIST);
        //item.setIsShowSecondItemDecoration(false); //不显示第一行 分割线
        item.setIsShowFirstItemDecoration(false);  //不显示第二行 分割线
        item.setMarginLeftDP(10);   //分割线左边距
        item.setMarginRightDP(10);  //分割线右边距

        mainView.getRefreshableView().addItemDecoration(item);  //添加分割线

        mainView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(this); //设置点击事件

        mainView.setScrollingWhileRefreshingEnabled(true);
        mainView.setMode(PullToRefreshBase.Mode.BOTH);
        mainView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //Toast.makeText(MainActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        for (int i = 0; i < 10; i++) {
                            data.add("Item" + i);
                        }
                        adapter.notifyDataSetChanged();
                        mainView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //Toast.makeText(MainActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            data.add("Item" + data.size());
                        }
                        adapter.notifyDataSetChanged();
                        mainView.onRefreshComplete();
                    }
                }, 2000);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainView.setRefreshing();
            }
        }, 300);
    }


    @Override
    public void onItemClickListener(int posotion, Object data) {
        Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
    }

    class WGAdapter extends SimpleWanAdapter<String> {


        protected WGAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        protected void onBindWanViewHolder(WanViewHolder holder, int position) {
            TextView text = holder.getView(android.R.id.text1);
            text.setText(mDatas.get(position));
        }
    }

}
