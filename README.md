# Wan_RecycleViewAdapter
目前封装的比较完善的RecycleView适配器

1.  可以添加多个头视图、尾视图
2.  可以设置默认的分割线
3.  可以隐藏第一个、第二个头视图的分割线
4.  简化适配器中的方法


用法如下：

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


注意: 需要添加样式设置  分割线的显示效果

 <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- ################################################ -->
        <!-- 分割线的样式有这里定义。  一般都是Drawable -->
        <item name="android:listDivider">@drawable/divider</item>
 </style>
