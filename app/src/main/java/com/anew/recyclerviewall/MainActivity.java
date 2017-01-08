package com.anew.recyclerviewall;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mHeadList;
    private List<String> mBodyList;
    private List<String> mFootList;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHeadList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mHeadList.add("head" + i);
        }

        mBodyList = new ArrayList<>();
        for (int i = 'A'; i < 'K'; i++) {
            mBodyList.add("" + (char) i);
        }

        mFootList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mFootList.add("foot" + i);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
//        使用默认的api绘制分割线
        mRecyclerView.addItemDecoration
                (new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

//      绘制gridView的空隙时候，使用下面的方式
//        mRecyclerView.addItemDecoration
//                (new DividerGridItemDecoration(this));

//        设置增加删除item的动画效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        瀑布流
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(this, mHeadList, mBodyList, mFootList);
        mRecyclerView.setAdapter(mAdapter);

        // 当目前的可见条目是所有数据的最后一个时，开始加载新的数据

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition + 1 == mAdapter.getItemCount()) {

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> mMoreList = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                mMoreList.add("more" + i);
                            }
                            mBodyList.addAll(mMoreList);
                            mAdapter.notifyItemInserted(mAdapter.getItemCount() - 2);
                        }
                    }, 5000);

                    Log.e("qqq", "到底了" + lastCompletelyVisibleItemPosition);
                }

                int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition == 0&&dy<0) {

                    Log.e("eeeee", "开始刷新呀呀呀");


                }
            }
        });
    }

}
