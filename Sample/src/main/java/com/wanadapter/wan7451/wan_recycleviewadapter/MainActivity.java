package com.wanadapter.wan7451.wan_recycleviewadapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wan7451.wanadapter.mylibrary.WanAdapter;
import com.wan7451.wanadapter.mylibrary.WanItemDecoration;
import com.wan7451.wanadapter.mylibrary.WanViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WanAdapter.OnItemClickListener {

    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 100; i++) {
            data.add("Item" + i);
        }
        RecyclerView mainView = (RecyclerView) findViewById(R.id.mianView);

        WGAdapter adapter = new WGAdapter(this, data, android.R.layout.simple_list_item_1);
        mainView.setAdapter(adapter);


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

        mainView.addItemDecoration(item);  //添加分割线

        mainView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(this); //设置点击事件


    }

    @Override
    public void onItemClickListener(int posotion) {
        Toast.makeText(this, data.get(posotion), Toast.LENGTH_LONG).show();
    }

    class WGAdapter extends WanAdapter<String> {


        protected WGAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        /**
         * @param holder itemHolder
         * @param item   每一Item显示的数据
         */
        @Override
        public void convert(WanViewHolder holder, String item) {
            //holder.setText(android.R.id.text1, item);
            //或者
            TextView text = holder.getView(android.R.id.text1);
            text.setText(item);
        }
    }


}
