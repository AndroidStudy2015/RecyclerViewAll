package com.anew.recyclerviewall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by a on 2017/1/7.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> mHeadList;
    private List<String> mBodyList;
    private List<String> mFootList;
    private static final int HEAD_TYPE = 1;
    private static final int BODY_TYPE = 2;
    private static final int FOOT_TYPE = 3;

    public MyAdapter(Context context, List<String> headList, List<String> bodyList, List<String> footList) {
        this.context = context;
        this.mHeadList = headList;
        this.mBodyList = bodyList;
        this.mFootList = footList;
    }

    //   ★ 1. 定义规则，什么情况，是哪一种ViewType
    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        if (position < getHeadCount()) {
            viewType = HEAD_TYPE;
        } else if (position > getHeadCount() + getBodyCount() - 1) {
            //这里最后要减去1，慢慢地去数
            viewType = FOOT_TYPE;
        } else {
            viewType = BODY_TYPE;
        }

        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        //    ★ 2. 根据viewType来确定加载那个布局，返回哪一个ViewHolder
        switch (viewType) {
            case HEAD_TYPE:
                view = inflater.inflate(R.layout.item_head, parent, false);
                return new HeadHolder(view);
            case BODY_TYPE:
                view = inflater.inflate(R.layout.item_body, parent, false);
                return new BodyHolder(view);
            case FOOT_TYPE:
                view = inflater.inflate(R.layout.item_foot, parent, false);
                return new FootHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//  ★ 3. 根据holder是哪一个ViewHolder的实例，来确定操作哪一个布局的UI
        if (holder instanceof HeadHolder) {
            HeadHolder headHolder = (HeadHolder) holder;
            headHolder.mTvHead.setText(mHeadList.get(position) + "");

            headHolder.mTvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addData();
                }

            });
        }
        if (holder instanceof BodyHolder) {
            final BodyHolder bodyHolder = (BodyHolder) holder;
            bodyHolder.mTvBody.setText(mBodyList.get(position - getHeadCount()) + "");

            bodyHolder.mTvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(bodyHolder.getAdapterPosition());
                }

            });
        }
        if (holder instanceof FootHolder) {
            final FootHolder footHolder = (FootHolder) holder;
            footHolder.mTvFoot.setText(mFootList.get(position - getHeadCount() - getBodyCount()) + "");

            footHolder.mTvFoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, footHolder.getAdapterPosition() + "",
                            Toast.LENGTH_SHORT).show();
                }

            });
        }

    }


    @Override
    public int getItemCount() {
        return getHeadCount() + getBodyCount() + getFootCount();
    }

    private int getFootCount() {
        return mFootList.size();
    }

    private int getBodyCount() {
        return mBodyList.size();
    }

    private int getHeadCount() {
        return mHeadList.size();
    }


    public void addData(int position) {
        mBodyList.add(position, "Insert One");
//       ★★★★ 使用notifyItemInserted去更新数据，否则没有动画效果
        notifyItemInserted(position);
    }

    /**
     * 增加bodyList条目
     */
    public void addData() {
        mBodyList.add("1");
        mBodyList.add("2");
        mBodyList.add("3");
//       ★★★★ 使用notifyItemInserted去更新数据，否则没有动画效果
        notifyItemRangeInserted(0, 3);
    }

    /**
     * 删除bodyList条目
     */
    public void removeData(int position) {
//        经测试疯狂点击删除条目，会产生负数，导致越界异常
        if (!(position - getHeadCount() < 0)) {
//        ★★★★ 防止角标越界异常，要减去头布局数量，总之去数数吧
            mBodyList.remove(position - getHeadCount());
            notifyItemRemoved(position);
        }
    }

    /**
     * 头布局Holder
     */
    static class HeadHolder extends RecyclerView.ViewHolder {

        private TextView mTvHead;

        public HeadHolder(View itemView) {
            super(itemView);
            mTvHead = (TextView) itemView.findViewById(R.id.tv_head);

        }
    }

    /**
     * 身体布局Holder
     */
    static class BodyHolder extends RecyclerView.ViewHolder {

        private TextView mTvBody;

        public BodyHolder(View itemView) {
            super(itemView);
            mTvBody = (TextView) itemView.findViewById(R.id.tv_body);
        }
    }

    /**
     * 脚布局Holder
     */
    static class FootHolder extends RecyclerView.ViewHolder {

        private TextView mTvFoot;

        public FootHolder(View itemView) {
            super(itemView);
            mTvFoot = (TextView) itemView.findViewById(R.id.tv_foot);
        }
    }
}
